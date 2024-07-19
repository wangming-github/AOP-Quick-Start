package com.maizi.author.feign;

import com.maizi.common.constants.ModuleType;
import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @author maizi
 */ /*
 * Feign拦截器
 */
@Slf4j
// @Configuration
public class FeignConfig {

    @Bean
    Logger.Level feignLoggerLevel() {

        /*
         * NONE: 不记录任何日志（默认）。
         * BASIC: 仅记录请求方法和 URL，响应状态码及执行时间。
         * HEADERS: 记录 BASIC 级别的基础信息，并且记录请求和响应的头信息。
         * FULL: 记录 HEADERS 级别的信息，并且记录请求和响应的正文（body）以及元数据。
         */
        return Logger.Level.FULL; // 设置 Feign 的日志级别为 FULL
    }

    
    /**
     * 如果 token 是动态生成的，或者需要从上下文中获取，
     * 可以在拦截器中使用 Spring 的 SecurityContext 或者其他方式来获取当前用户的 token：
     */
    @Bean
    public RequestInterceptor requestInterceptor() {
        log.info(ModuleType.AUTHORIZATION_MODULE + "从上下文中获取token，提供给feign继续使用...");

        return template -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getCredentials() != null) {
                String token = (String) authentication.getCredentials();
                template.header("Authorization", token);
            }
        };
    }


}
