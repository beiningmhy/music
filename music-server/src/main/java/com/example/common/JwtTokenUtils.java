package com.example.common;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.entity.Admin;
import com.example.service.AdminService;
import com.example.service.ConsumerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class JwtTokenUtils {

    private static AdminService staticAdminService;
    private static ConsumerService staticConsumerService;
    private static final Logger log = LoggerFactory.getLogger(JwtTokenUtils.class);

    @Resource
    private AdminService adminService;

    @Resource
    private ConsumerService consumerService;

    @PostConstruct
    public void setAdminService() {
        staticAdminService = adminService;
    }

    @PostConstruct
    public void setConsumerService() {
        staticConsumerService = consumerService;
    }

    /**
     * 生成token
     */
    public static String genToken(String adminId, String sign) {
        return JWT.create().withAudience(adminId) // 将 user id 保存到 token 里面,作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(), 2)) // 2小时后token过期
                .sign(Algorithm.HMAC256(sign)); // 以 password 作为 token 的密钥
    }

    /**
     * 获取当前登录的用户信息
     */
    public static Object getCurrentUser() {
        String token = null;
        String code;
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            token = request.getHeader("token");
            if (StrUtil.isBlank(token)) {
                token = request.getParameter("token");
            }
            if (StrUtil.isBlank(token)) {
                log.error("获取当前登录的token失败， token: {}", token);
                return null;
            }
            code = request.getHeader("code");
            if (StrUtil.isBlank(code)) {
                log.error("获取当前登录的code失败， code: {}", code);
                return null;
            }
            System.out.println(code);
            if (code.equals("user")) {
                String userId = JWT.decode(token).getAudience().get(0);
                return staticConsumerService.findByById(Integer.valueOf(userId));
            } else {
                // 解析token，获取用户的id
                String adminId = JWT.decode(token).getAudience().get(0);
                return staticAdminService.findByById(Integer.valueOf(adminId));
            }

        } catch (Exception e) {
            log.error("获取当前登录的管理员信息失败, token={}", token, e);
            return null;
        }
    }
}