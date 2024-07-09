package com.example.authorizationmodule.exception;

import com.alibaba.fastjson.JSON;
import com.example.authorizationmodule.module.R;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义spring security认证异常处理类
 *
 * @author maizi
 */
@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding("UTF-8");
        // httpServletResponse.setContentType("text/html; charset=utf-8");
        response.setContentType("application/json;charset=UTF-8");
        R<Object> result = R.error(HttpServletResponse.SC_FORBIDDEN, "身份认证失败");
        response.getWriter().append(JSON.toJSONString(result));
    }
}
