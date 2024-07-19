package com.maizi.author.feign;

import com.maizi.author.module.CustomUserUserDetails;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author maizi
 */
@FeignClient(name = "service-module", configuration = FeignConfig.class)
public interface ServiceFeignClient {

    /*
     * ----1.将【SpuBoundsTo】对象通过【@RequestBody】注解转为Json
     * ----2.找到注册中心中【momimall-coupon】服务【coupon/spubounds/save】接口
     * ----3.将Json数据放在请求体的位置，调用【coupon/spubounds/save】接口
     * ----4.对方服务收到请求体中的Json数据。 将请求体中的Json转为【SpuBoundsEntity】
     * 只需要Json属性名一一对应即可,双方服务无需使用同一个传输对象TO。
     *
     */

    @GetMapping("/admin/greet/{name}")
    String greet(@PathVariable("name") String name);

    @PostMapping("/admin/echo")
    String echo(@RequestBody String message);

    @GetMapping("/admin/sum/{a}/{b}")
    String sum(@PathVariable("a") int a, @PathVariable("b") int b);

    @GetMapping("/admin/multiply/{a}/{b}")
    String multiply(@PathVariable("a") int a, @PathVariable("b") int b);

    @PostMapping("/admin/userDetails")
    boolean saveCustomUserUserDetails(@RequestBody CustomUserUserDetails userDetails);


}
