package com.example.common;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.entity.Admin;
import com.example.entity.Consumer;
import com.example.exception.CustomException;
import com.example.service.AdminService;
import com.example.service.ConsumerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * jwt拦截器
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(JwtInterceptor.class);

    @Resource
    private AdminService adminService;
    @Resource
    private ConsumerService consumerService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String requestUrl = request.getRequestURL().toString();
        System.out.println("requestUrl:" + requestUrl);
        // 检查请求是否来自 http://127.0.0.1:8080/
        if (requestUrl.startsWith("http://127.0.0.1:8080/")) {
            return true; // 不进行拦截
        }
        // 1. 从http请求的header中获取token
        String token = request.getHeader("token");
        if (StrUtil.isBlank(token)) {
            // 如果没拿到，我再去参数里面拿一波试试  /api/admin?token=xxxxx
            token = request.getParameter("token");
        }
        // 2. 开始执行认证
        if (StrUtil.isBlank(token)) {
            throw new CustomException("无token，请重新登录");
        }
        String code = request.getHeader("code");
        if (StrUtil.isBlank(code)) {
            code = request.getParameter("code");
        }
        if (StrUtil.isBlank(code)) {
            throw new CustomException("无code，请重新登录");
        }
        if (code.equals("admin")) {
            // 获取 token 中的userId
            String userId;
            Admin admin;
            try {
                userId = JWT.decode(token).getAudience().get(0);
                // 根据token中的userid查询数据库
                admin = adminService.findByById(Integer.parseInt(userId));
            } catch (Exception e) {
                String errMsg = "token验证失败，请重新登录";
                log.error(errMsg + ", token=" + token, e);
                throw new CustomException(errMsg);
            }
            if (admin == null) {
                throw new CustomException("用户不存在，请重新登录");
            }
            try {
                // 用户密码加签验证 token
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(admin.getPassword())).build();
                jwtVerifier.verify(token); // 验证token
            } catch (JWTVerificationException e) {
                throw new CustomException("token验证失败，请重新登录");
            }
        }else if(code.equals("user")){
            // 获取 token 中的userId
            String userId;
            Consumer consumer;
            try {
                userId = JWT.decode(token).getAudience().get(0);
                // 根据token中的userid查询数据库
                consumer = consumerService.findByById(Integer.parseInt(userId));
            } catch (Exception e) {
                String errMsg = "token验证失败，请重新登录";
                log.error(errMsg + ", token=" + token, e);
                throw new CustomException(errMsg);
            }
            if (consumer == null) {
                throw new CustomException("用户不存在，请重新登录");
            }
            try {
                // 用户密码加签验证 token
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(consumer.getPassword())).build();
                jwtVerifier.verify(token); // 验证token
            } catch (JWTVerificationException e) {
                throw new CustomException("token验证失败，请重新登录");
            }
        }

//        log.info("token验证成功，允许放行");
        return true;
    }
}