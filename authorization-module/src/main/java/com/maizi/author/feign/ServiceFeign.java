package com.maizi.author.feign;

import com.maizi.common.dto.UserDTO;
import com.maizi.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * @author maizi
 */
// 要远程调用接口的系统在Nacos中的服务名
@FeignClient(name = "authorization")
public interface ServiceFeign {

    /*
     * ----1.将【SpuBoundsTo】对象通过【@RequestBody】注解转为Json
     * ----2.找到注册中心中【momimall-coupon】服务【coupon/spubounds/save】接口
     * ----3.将Json数据放在请求体的位置，调用【coupon/spubounds/save】接口
     * ----4.对方服务收到请求体中的Json数据。 将请求体中的Json转为【SpuBoundsEntity】
     * 只需要Json属性名一一对应即可,双方服务无需使用同一个传输对象TO。
     *
     */

    /**
     * 根据用户名查询用户信息及其角色和权限
     */
    @PostMapping("/findAuthorByUsername")
    R findAuthorByUsername(@RequestBody UserDTO user);

}