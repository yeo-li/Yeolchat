package com.yeo_li.yeolchat.exception.signUpException;

public class UserSignUpEmailAlreadyExsistsException extends RuntimeException {
    public UserSignUpEmailAlreadyExsistsException(String message){
        super(message);
    }
}
