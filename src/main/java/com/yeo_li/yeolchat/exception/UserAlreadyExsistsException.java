package com.yeo_li.yeolchat.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class UserAlreadyExsistsException extends RuntimeException {
    public UserAlreadyExsistsException(String message){
        super(message);
    }
}
