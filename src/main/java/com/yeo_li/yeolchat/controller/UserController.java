package com.yeo_li.yeolchat.controller;

import com.yeo_li.yeolchat.dto.user.signUp.UserSignUpRequest;
import com.yeo_li.yeolchat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/save")
    public void createUser(@RequestBody UserSignUpRequest userDTO){

        userService.saveUser(userDTO);
        //User user1 = userService.findByUserId("ert215465");

    }
}


