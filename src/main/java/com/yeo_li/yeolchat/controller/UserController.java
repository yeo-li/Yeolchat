package com.yeo_li.yeolchat.controller;

import com.yeo_li.yeolchat.dto.user.signUp.UserSignUpRequest;
import com.yeo_li.yeolchat.entity.User;
import com.yeo_li.yeolchat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("")
    public ResponseEntity<String> createUser(@RequestBody UserSignUpRequest userDTO){

        userService.signUp(userDTO);


        HttpHeaders header = new HttpHeaders();
        return new ResponseEntity<>("회원가입이 정상적으로 수행되었습니다.", HttpStatus.OK);
    }
}