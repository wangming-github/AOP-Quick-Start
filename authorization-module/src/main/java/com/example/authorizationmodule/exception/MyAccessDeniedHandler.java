package com.example.authorizationmodule.exception;

import com.alibaba.fastjson.JSON;
import com.example.authorizationmodule.module.R;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 自定义spring security授权异常处理类
 *
 * @author maizi
 */
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // 设置响应的内容类型和字符编码
        response.setContentType("text/plain;charset=UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        R<Object> result = R.error(HttpServletResponse.SC_FORBIDDEN, "无权限访问");
        response.getWriter().append(JSON.toJSONString(result));
    }
}