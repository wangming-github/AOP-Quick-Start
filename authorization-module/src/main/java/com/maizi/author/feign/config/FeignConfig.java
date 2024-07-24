package com.maizi.author.feign.config;

import com.maizi.common.constants.ModuleType;
import feign.Logger;
import feign.Request;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.codec.ErrorDecoder;
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
public class FeignConfig {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }

    // @Bean
    // public Request.Options options() {
    //     int connectTimeoutMillis = 5000; // 连接超时时间
    //     int readTimeoutMillis = 10000;   // 读取超时时间
    //     return new Request.Options(connectTimeoutMillis, readTimeoutMillis);
    // }

    @Bean
    Logger.Level feignLoggerLevel() {

        /*
         * NONE: 不记录任何日志（默认）。
         * BASIC: 仅记录请求方法和 URL，响应状态码及执行时间。
         * HEADERS: 记录 BASIC 级别的基础信息，并且记录请求和响应的头信息。
         * FULL: 记录 HEADERS 级别的信息，并且记录请求和响应的正文（body）以及元数据。
         */
        return Logger.Level.HEADERS; // 设置 Feign 的日志级别为 FULL
    }

    /**
     * 用于在Feign请求发送之前修改请求模板（例如添加HTTP头）。
     * <p>
     * 这个拦截器非常简单，
     * 它静态地将一个固定的头部（test-Key）和固定的值（test-Value） 添加到每个Feign请求中。
     */
    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> template.header("test-Key", "test-Value");
    }

}
