package com.example.common;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.system.SystemUtil;
import com.example.entity.Admin;
import com.example.entity.Consumer;
import com.example.entity.Log;
import com.example.service.LogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 处理切面的“监控”
 */
@Component
@Aspect
public class LogAspect {

    @Resource
    private LogService logService;

    @Around("@annotation(autoLog)")
    public Object doAround(ProceedingJoinPoint joinPoint, AutoLog autoLog) throws Throwable {

        // 操作内容，我们在注解里已经定义了value()，然后再需要切入的接口上面去写上对应的操作内容即可
        String name = autoLog.value();
        // 操作时间（当前时间）
        String time = DateUtil.now();
        // 操作人
        String username = "";
        Object user = JwtTokenUtils.getCurrentUser();
        //获取user中的name


        System.out.println("user----------------" + JwtTokenUtils.getCurrentUser());
        if (ObjectUtil.isNotNull(user)) {
            if (user instanceof Admin) {
                Admin adminTmp = (Admin) user;
                username = adminTmp.getName();
            }
            if (user instanceof Consumer) {
                Consumer consumerTmp = (Consumer) user;
                username = consumerTmp.getUsername();
            }
        }
        // 操作人IP
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = request.getRemoteAddr() + "/" + getClientIpAddress();


        // 执行具体的接口
        Result result = (Result) joinPoint.proceed();

        Object data = result.getData();
        if (data instanceof Admin) {
            Admin admin = (Admin) data;
            username = admin.getName();
        }
        if(data instanceof Consumer){
            Consumer consumer = (Consumer) data;
            username = consumer.getUsername();
        }
        if (username.equals("")) {
            username = "系统操作";
        }


        // 再去往日志表里写一条日志记录
        Log log = LogSystemInfo.getSystemInfo(request);
        log.setName(name);
        log.setUsername(username);
        log.setIp(ip);
        log.setTime(time);


        logService.add(log);

        // 你可以走了，去返回前台报到吧~
        return result;
    }

    public static String getClientIpAddress() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if (request != null) {
            return getIpAddressFromRequest(request);
        }
        return "Unknown IP";
    }

    private static String getIpAddressFromRequest(HttpServletRequest request) {
        // 尝试从 X-Forwarded-For 头中获取 IP 地址
        String ipAddress = request.getHeader("X-Forwarded-For");

        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            // 如果 X-Forwarded-For 头不存在或无效，尝试从 X-Real-IP 头中获取
            ipAddress = request.getHeader("X-Real-IP");
        }

        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            // 如果 X-Real-IP 头也不存在或无效，使用 getRemoteAddr()
            ipAddress = request.getRemoteAddr();
        }
        // 特殊处理回环地址
        if ("0:0:0:0:0:0:0:1".equals(ipAddress) || "::1".equals(ipAddress)) {
            return "127.0.0.1";
        }

        // 如果 IP 地址是 IPv6 格式，尝试转换为 IPv4 格式
        if (isIPv6(ipAddress)) {
            ipAddress = convertIPv6ToIPv4(ipAddress);
        }

        return ipAddress;
    }

    private static boolean isIPv6(String ipAddress) {
        return ipAddress != null && ipAddress.contains(":");
    }

    private static String convertIPv6ToIPv4(String ipAddress) {
        try {
            InetAddress inetAddress = InetAddress.getByName(ipAddress);
            if (inetAddress instanceof java.net.Inet6Address) {
                // 如果是 IPv6 映射的 IPv4 地址，提取 IPv4 地址部分
                if (inetAddress.getHostAddress().startsWith("::ffff:")) {
                    String ipv4Part = inetAddress.getHostAddress().substring(7); // 去掉前缀 "::ffff:"
                    return ipv4Part;
                }
            }
        } catch (UnknownHostException e) {
            // 处理异常
        }
        return ipAddress;
    }

}