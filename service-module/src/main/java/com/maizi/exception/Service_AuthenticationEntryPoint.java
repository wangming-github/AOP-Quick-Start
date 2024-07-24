package com.maizi.exception;

import com.alibaba.fastjson.JSON;
import com.maizi.common.constants.ModuleType;
import com.maizi.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 自定义的Spring Security认证异常处理类。
 * <p>
 * 实现了 {@link AuthenticationEntryPoint} 接口，用于处理认证异常。
 * 当用户尝试访问需要认证的资源但未通过认证时，将触发该处理器。
 * </p>
 * <p>
 * 类注解：
 * - {@link Component}：标识该类是一个Spring组件，将其注册为Spring容器中的一个Bean。
 * </p>
 */
@Slf4j
@Component
public class Service_AuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final String ALLOW_ORIGIN = "*";
    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";

    /**
     * 处理认证异常请求。
     * <p>
     * 当用户尝试访问需要认证的资源但未通过认证时触发该方法。
     * </p>
     *
     * @param request  HttpServletRequest 对象，表示客户端的请求
     * @param response HttpServletResponse 对象，表示发送给客户端的响应
     * @param e        AuthenticationException 对象，表示认证异常
     * @throws IOException 如果在处理响应时发生IO异常
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        // 设置响应头，允许所有来源的跨域请求
        response.setHeader("Access-Control-Allow-Origin", ALLOW_ORIGIN);

        // 设置响应的字符编码和内容类型
        response.setContentType(CONTENT_TYPE);

        // 记录认证异常的日志
        log.error(ModuleType.SERVICE_MODULE + " - Authentication failed: {}", e.getMessage());

        // 构建自定义的错误响应对象
        R result = R.error(HttpServletResponse.SC_FORBIDDEN, "您需要登录才能访问!");

        // 将错误响应对象转换为JSON字符串并写入响应
        response.getWriter().append(JSON.toJSONString(result));
    }
}