package com.yeo_li.yeolchat.controller;

import com.yeo_li.yeolchat.dto.user.UserDto;
import com.yeo_li.yeolchat.entity.User;
import com.yeo_li.yeolchat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/save")
    public User createUser(@RequestBody UserDto userDTO){

        //userService.saveUser(userDTO);
        User user1 = userService.findByUserId("ert2154");
        System.out.println("user1 = " + user1);

        return user1;
    }
}


