package com.example.common;

import cn.hutool.system.SystemUtil;
import com.example.entity.Log;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class LogSystemInfo {

    public static Log getSystemInfo(HttpServletRequest request) {
        Log log = new Log();
        //获取浏览器的信息
        String userAgent = request.getHeader("User-Agent").toLowerCase();
//        System.out.println(userAgent);
        String explorer="";
        if (userAgent.contains("edg/")) {
            explorer= "Microsoft Edge";
        } else if (userAgent.contains("opr/")) {
            explorer= "Opera";
        } else if (userAgent.contains("firefox/")) {
            explorer= "Mozilla Firefox";
        } else if (userAgent.contains("chrome/")) {
            explorer= "Google Chrome";
        } else if (userAgent.contains("safari/") && !userAgent.contains("chrome/")) {
            explorer= "Apple Safari";
        } else {
            explorer= "Unknown";
        }
//        System.out.println(explorer);

        //操作系统版本
        String os = SystemUtil.get("os.name");
        String osName= SystemUtil.get("user.name");
        String language= SystemUtil.get("user.language");
        String country= SystemUtil.get("user.country");
        String hostName=SystemUtil.getHostInfo().getName();
        String hostAddress=SystemUtil.getHostInfo().getAddress();
        log.setExplorer(explorer);
        log.setOs(os);
        log.setOsName(osName);
        log.setLanguage(language);
        log.setCountry(country);
        log.setHostName(hostName);
        log.setHostAddress(hostAddress);
        return log;
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
