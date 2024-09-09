package com.yeo_li.yeolchat.exception.signUpException;

public class UserSignUpAlreadyExsistsException extends RuntimeException {
    public UserSignUpAlreadyExsistsException(String message){
        super(message);
    }
}