package com.yeo_li.yeolchat.exception;

public class AlreadySignInException extends RuntimeException{
    public AlreadySignInException(){
        super();
    }

    public AlreadySignInException(String message){
        super(message);
    }
}
