package com.yeo_li.yeolchat.exception;

public class YeoliException extends RuntimeException {
    private int errorCode;

    public YeoliException() {
        super("미안하다.. 이거 보여주려고 예외 만들었다");
        this.errorCode = 0;
    }

    public YeoliException(String message) {
        super(message);
        this.errorCode = 0;
    }
}
