package com.yeo_li.yeolchat.exception;

import jakarta.persistence.NoResultException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NoResultException.class)
    public String handleException(NoResultException e, HttpServletRequest request) {
        String path = request.getRequestURI();


        return "[DEFAULT]"+e.getMessage()+" : User Not Found";
    }



    @ExceptionHandler(AlreadySignInException.class)
    public String alredyLoginExceptionHandler(AlreadySignInException e){
        return "AleadyLoginException: "+e.getMessage();
    }

    @ExceptionHandler(UserAlreadyExsistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String userAlreadyExsistsExceptionHandler(UserAlreadyExsistsException e){
        return "UserAlreadyExsistsException: "+e.getMessage();
    }

    @ExceptionHandler(UserEmailAlreadyExsistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String userEmailAlreadyExsistsExceptionHandler(UserEmailAlreadyExsistsException e){
        return "UserEmailAlreadyExsistsException: "+e.getMessage();
    }
}
