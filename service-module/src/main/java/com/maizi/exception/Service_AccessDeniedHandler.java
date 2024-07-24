package com.maizi.exception;

import com.maizi.common.exception.RRException;
import com.maizi.common.utils.R;
import com.alibaba.fastjson.JSON;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义的访问拒绝处理器，当用户尝试访问未授权的资源时触发。
 * <p>
 * 实现了 {@link AccessDeniedHandler} 接口，用于处理访问被拒绝的情况。
 * 当用户没有访问某个资源的权限时，将返回一个自定义的403 Forbidden响应。
 * </p>
 * <p>
 * 类注解：
 * - {@link Component}：标识该类是一个Spring组件，将其注册为Spring容器中的一个Bean。
 * </p>
 *
 * @author maizi
 */
@Component
public class Service_AccessDeniedHandler implements AccessDeniedHandler {
    private static final Logger log = LoggerFactory.getLogger(Service_AccessDeniedHandler.class);

    /**
     * 处理访问被拒绝的请求。
     * <p>
     * 当用户尝试访问未授权的资源时触发该方法。
     * </p>
     *
     * @param request               HttpServletRequest 对象，表示客户端的请求
     * @param response              HttpServletResponse 对象，表示发送给客户端的响应
     * @param accessDeniedException AccessDeniedException 对象，表示访问被拒绝的异常
     * @throws IOException 如果在处理响应时发生IO异常
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws AccessDeniedException, IOException {

        // 设置响应的内容类型和字符编码
        // response.setContentType("application/json;charset=UTF-8");
        // response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 设置状态码为403 Forbidden
        //
        // log.error("无权访问:{}", accessDeniedException.getMessage());
        //
        // // 构建自定义的错误响应对象
        // R result = R.ok().put("code", HttpServletResponse.SC_FORBIDDEN);
        // // 将错误响应对象转换为JSON字符串并写入响应
        // response.getWriter().append(JSON.toJSONString(result));
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "无权访问: " + accessDeniedException.getMessage());

    }
}
