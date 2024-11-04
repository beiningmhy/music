package com.example.common;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import javax.servlet.MultipartConfigElement;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        // 指定controller统一的接口前缀


        configurer.addPathPrefix("/api", clazz -> clazz.isAnnotationPresent(RestController.class));
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // 单个文件最大10MB
        factory.setMaxFileSize(DataSize.parse("100MB"));
        // 整个请求最大10MB
        factory.setMaxRequestSize(DataSize.parse("100MB"));
        return factory.createMultipartConfig();
    }

    @Resource
    private JwtInterceptor jwtInterceptor;

    // 加自定义拦截器JwtInterceptor，设置拦截规则
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor).addPathPatterns("/api/**")
                .excludePathPatterns("/api/admin/login")
                .excludePathPatterns("/api/admin/register")
                .excludePathPatterns("/api/files/**")
                .excludePathPatterns("/api/captcha")
                .excludePathPatterns("/api/song/upload")
                .excludePathPatterns("/api/system/**")
                .excludePathPatterns("/api/singer/search")
                .excludePathPatterns("/api/songList/search")
                .excludePathPatterns("/api/banner")
                .excludePathPatterns("/api/song/search")
                .excludePathPatterns("/api/comment/search")
                .excludePathPatterns("/api/comment/up")
                .excludePathPatterns("/api/comment/down");
    }
}