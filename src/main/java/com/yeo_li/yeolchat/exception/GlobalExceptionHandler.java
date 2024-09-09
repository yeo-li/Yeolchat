package com.yeo_li.yeolchat.exception;

import com.yeo_li.yeolchat.dto.ExceptionResponse;
import com.yeo_li.yeolchat.exception.signUpException.UserSignUpAlreadyExsistsException;
import com.yeo_li.yeolchat.exception.signUpException.UserSignUpEmailAlreadyExsistsException;
import jakarta.persistence.NoResultException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<ExceptionResponse> handleException(NoResultException e, HttpServletRequest request) {
        String path = request.getRequestURI();


        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setStatus(404);
        exceptionResponse.setMessage(e.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exceptionResponse);
    }


    @ExceptionHandler(UserSignUpAlreadyExsistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT) // 409
    public ResponseEntity<ExceptionResponse> userAlreadyExistsExceptionHandler(UserSignUpAlreadyExsistsException e){
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setStatus(409);
        exceptionResponse.setMessage(e.getMessage());

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exceptionResponse);
    }

    @ExceptionHandler(UserSignUpEmailAlreadyExsistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ExceptionResponse> userEmailAlreadyExistsExceptionHandler(UserSignUpEmailAlreadyExsistsException e){
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setStatus(409);
        exceptionResponse.setMessage(e.getMessage());


        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exceptionResponse);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionResponse> invalidPasswordHandler(InvalidPasswordException e){
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setStatus(400);
        exceptionResponse.setMessage(e.getMessage());


        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exceptionResponse);
    }

    @ExceptionHandler(InvalidUserIdException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionResponse> InvalidUserIdExceptionHandler(InvalidUserIdException e){
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setStatus(400);
        exceptionResponse.setMessage(e.getMessage());


        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exceptionResponse);
    }

    @ExceptionHandler(InvalidEmailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionResponse> InvalidEmailExceptionHandler(InvalidEmailException e){
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setStatus(400);
        exceptionResponse.setMessage(e.getMessage());


        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exceptionResponse);
    }

    @ExceptionHandler(InvalidUserNameException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionResponse> InvalidUserNameExceptionHandler(InvalidUserNameException e){
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setStatus(400);
        exceptionResponse.setMessage(e.getMessage());


        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exceptionResponse);
    }

    @ExceptionHandler(SignOutInfoNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ExceptionResponse> AlreadySigOutExceptionHandler(SignOutInfoNotFoundException e){
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setStatus(401);
        exceptionResponse.setMessage(e.getMessage());


        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(exceptionResponse);
    }
}
