package com.yeo_li.yeolchat.exception;

public class AlreadyLoginException extends RuntimeException{
    public AlreadyLoginException(){
        super();
    }

    public AlreadyLoginException(String message){
        super(message);
    }
}
