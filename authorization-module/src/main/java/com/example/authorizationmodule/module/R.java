package com.example.authorizationmodule.module;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class R<T> {
    private Integer code; // 编码：1成功，0和其它数字为失败

    private String msg; // 错误信息

    private T data; // 数据

    // 成功处理方法，包含自定义消息
    public static <T> R<T> success(Integer code, String msg, T object) {
        R<T> r = new R<>();
        r.code = code;
        r.msg = msg;
        r.data = object;
        return r;
    }

    // 成功处理方法，包含自定义消息
    public static <T> R<T> success(Integer code, String msg) {
        R<T> r = new R<>();
        r.code = code;
        r.msg = msg;
        return r;
    }


    // 失败处理方法，包含错误消息和错误码
    public static <T> R<T> error(Integer code, String msg) {
        R<T> r = new R<>();
        r.msg = msg;
        r.code = code;
        return r;
    }


}
