package com.maizi.common.exception;

import com.maizi.common.constants.ModuleType;
import com.maizi.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

/**
 * 全局异常处理器，用于处理应用程序中的各种异常。
 * 该类使用 Spring 的 {@link RestControllerAdvice} 注解，自动捕获并处理控制器中的异常。
 * 可以根据不同的异常类型定义特定的处理方法，返回统一的响应格式。
 * <p>
 *
 * @author maizi
 */
@Slf4j
// @RestControllerAdvice
// @ControllerAdvice
public class RRExceptionHandler {

    /**
     * 处理自定义异常 {@link RRException}。
     *
     * @param e 自定义异常对象
     * @return 包含错误码和错误信息的响应对象
     */
    @ExceptionHandler(RRException.class)
    public R handleRRException(RRException e) {
        log.error(ModuleType.COMMON_MODULE + " - 自定义运行时异常: {}", e.getMessage());  // 记录异常信息
        R r = new R();  // 创建响应对象
        r.put("code", e.getCode());  // 设置错误码
        r.put("msg", e.getMessage());  // 设置错误信息

        return r;  // 返回响应对象
    }

    @ExceptionHandler(AccessDeniedException.class)
    public void accessDeniedException(AccessDeniedException e) throws AccessDeniedException {
        log.info("捕获到了AccessDeniedException======" + e.getMessage());
        throw e;
    }


    /**
     * 全局异常处理，处理所有未捕获的运行时异常 {@link RuntimeException}。
     *
     * @param e 异常对象
     * @return 包含错误码和错误信息的响应对象
     */
    @ExceptionHandler(Exception.class)
    public R handleException(Exception e) {
        log.info("======" + e.getMessage());
        log.error(ModuleType.COMMON_MODULE + " - 未捕获的运行时异常: {}", e.getMessage());  // 记录异常信息
        return R.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统异常！请稍后再试...");  // 返回内部服务器错误响应
    }
}
