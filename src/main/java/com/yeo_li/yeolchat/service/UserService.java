package com.yeo_li.yeolchat.service;

import com.yeo_li.yeolchat.entity.User;
import com.yeo_li.yeolchat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public User saveUser(User user) {
        return repository.save(user);
    }
}
