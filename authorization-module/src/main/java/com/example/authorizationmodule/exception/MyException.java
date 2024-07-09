package com.example.authorizationmodule.exception;

public class MyException extends RuntimeException {

    // 默认构造函数
    public MyException() {
        super();
    }

    // 带有详细信息的构造函数
    public MyException(String message) {
        super(message);
    }

    // 带有详细信息和原因的构造函数
    public MyException(String message, Throwable cause) {
        super(message, cause);
    }

    // 带有原因的构造函数
    public MyException(Throwable cause) {
        super(cause);
    }

    // 带有详细信息、原因、是否启用抑制和是否可写堆栈跟踪的构造函数
    protected MyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
