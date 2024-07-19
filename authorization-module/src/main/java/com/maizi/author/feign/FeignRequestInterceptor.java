package com.maizi.author.feign;

import com.maizi.common.constants.ModuleType;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;


@Slf4j
@Component
public class FeignRequestInterceptor implements RequestInterceptor {


    // @Override
    public void apply(RequestTemplate template) {
        try {
            // 获取对象
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (requestAttributes != null) {
                // 获取请求对象
                HttpServletRequest request = requestAttributes.getRequest();
                // 获取当前请求的header，获取到jwt令牌
                Enumeration<String> headerNames = request.getHeaderNames();
                if (headerNames != null) {
                    while (headerNames.hasMoreElements()) {
                        String headerName = headerNames.nextElement();
                        String headerValue = request.getHeader(headerName);
                        log.info(ModuleType.AUTHORIZATION_MODULE + "将header向下传递k:{}v:{}", headerName, headerValue);
                        // 将header向下传递
                        template.header(headerName, headerValue);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
