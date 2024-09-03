package com.yeo_li.yeolchat.exception;

public class UserEmailAlreadyExsistsException extends RuntimeException {
    public UserEmailAlreadyExsistsException(String message){
        super(message);
    }
}
