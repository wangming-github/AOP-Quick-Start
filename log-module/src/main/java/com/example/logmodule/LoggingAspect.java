package com.example.logmodule;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 日志记录切面类，使用 Spring AOP 实现方法的日志记录功能。
 */
@Slf4j
@Aspect
@Component
public class LoggingAspect {

    /**
     * 定义切点，匹配所有使用 @LogExecution 注解的方法。
     */
    @Pointcut("@annotation(com.example.logmodule.LogPrint)")
    public void annotatedMethods() {
    }

    /**
     * 前置通知，在方法执行前记录请求信息和参数。
     *
     * @param joinPoint 切点对象，用于获取方法签名和参数
     */
    @Before("annotatedMethods()")
    public void logMethodCall(JoinPoint joinPoint) {
        log.info("前置通知，在方法执行前记录请求信息和参数。");
        // 获取当前 HTTP 请求相关信息
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        // 获取方法的参数，并转换为字符串形式
        String args = Arrays.stream(joinPoint.getArgs()).map(Object::toString).collect(Collectors.joining(", "));

        // 打印请求相关信息
        System.out.println("请求 URL: " + request.getRequestURL().toString());
        System.out.println("HTTP 方法: " + request.getMethod());
        System.out.println("Controller 方法: " + joinPoint.getSignature().toShortString());
        System.out.println("方法参数: " + args);
    }

    /**
     * 返回通知，在方法正常返回后记录返回值。
     *
     * @param joinPoint 切点对象，用于获取方法签名和返回值
     * @param result    方法返回的对象
     */
    @AfterReturning(pointcut = "annotatedMethods()", returning = "result")
    public void logMethodReturn(JoinPoint joinPoint, Object result) {
        log.info("返回通知，在方法正常返回后记录返回值。");
        // 打印方法返回值
        System.out.println("方法返回值: " + result);
    }

    /**
     * 异常通知，在方法抛出异常时记录异常信息。
     *
     * @param joinPoint 切点对象，用于获取方法签名和异常信息
     * @param exception 抛出的异常对象
     */
    @AfterThrowing(pointcut = "annotatedMethods()", throwing = "exception")
    public void logException(JoinPoint joinPoint, Throwable exception) {
        log.info("异常通知，在方法抛出异常时记录异常信息。");
        // 打印异常信息
        System.out.println("方法抛出异常: " + exception.getMessage());
    }
}
