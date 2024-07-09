package com.example.logmodule;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LogPrint {
    String value() default ""; // 可选的描述信息
}