package com.maizi.common.exception;

import com.maizi.common.utils.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 异常处理器
 *
 * @author Mark sunlightcs@gmail.com
 */
// @RestControllerAdvice
public class RRExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(RRException.class)
    public R handleRRException(RRException e) {
        R r = new R();
        r.put("code", e.getCode());
        r.put("msg", e.getMessage());

        return r;
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public R handlerNoFoundException(Exception e) {
        logger.error(e.getMessage(), e);
        return R.error(404, "路径不存在，请检查路径是否正确");
    }

    // @ExceptionHandler(DuplicateKeyException.class)
    // public R handleDuplicateKeyException(DuplicateKeyException e) {
    //     logger.error(e.getMessage(), e);
    //     return R.error("数据库中已存在该记录");
    // }


    // @ExceptionHandler(AuthenticationException.class)
    // public ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex) {
    //     return new ResponseEntity<>(R.error(HttpStatus.UNAUTHORIZED.value(), "您需要登录才能访问该页面"), HttpStatus.UNAUTHORIZED);
    // }
    //
    // @ExceptionHandler(AccessDeniedException.class)
    // public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {
    //     return new ResponseEntity<>(R.error(HttpStatus.FORBIDDEN.value(), "您没有权限访问该页面"), HttpStatus.FORBIDDEN);
    // }

    /**
     * 全局异常处理
     */
    @ExceptionHandler(RuntimeException.class)
    public R handleException(RuntimeException e) {
        return R.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器内部异常，请稍后重试!");
    }

}
