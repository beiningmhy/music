package com.example.common;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.example.entity.Admin;
import com.example.entity.Log;
import com.example.service.LogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class LogExceptionAspect {
    @Resource
    private LogService logService;

    private static final Logger logger = LoggerFactory.getLogger(LogExceptionAspect.class);

    @AfterThrowing(pointcut = "@annotation(autoLog)", throwing = "ex")
    public void logException(JoinPoint joinPoint, AutoLog autoLog, Throwable ex) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

//        logger.error("方法 {} 发生异常: {}", methodName, ex.getMessage());
//        logger.error("异常详情: ", ex);
//        logger.error("方法参数: {}", args);
//        logger.error("注解信息: {}", autoLog.value());

        // 操作时间（当前时间）
        String time = DateUtil.now();
        // 操作人
        String username = "";
        Admin user = JwtTokenUtils.getCurrentUser();
        if (ObjectUtil.isNotNull(user)) {
            username = user.getName();
        }
        if(username.equals("")){
            username="系统操作";
        }
        // 操作人IP
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = request.getRemoteAddr()+"/"+LogAspect.getClientIpAddress();






        // 再去往日志表里写一条日志记录
        Log log = LogSystemInfo.getSystemInfo(request);
        log.setName("方法:"+methodName+"发生异常: "+ ex.getMessage()+"\n"+
               " 方法参数:"+ Arrays.toString(args) +"\n"+
                "注解信息:"+autoLog.value());
        log.setUsername(username);
        log.setIp(ip);
        log.setTime(time);


        logService.add(log);

    }
}