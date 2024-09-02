package com.yeo_li.yeolchat.service;

import com.yeo_li.yeolchat.dto.user.UserDto;
import com.yeo_li.yeolchat.entity.User;

public interface UserService {

    // TODO DTO <-> Entity converter

    void signUp(UserDto userDto);
    void signIn(UserDto userDto);
    void logout(UserDto userDto);

    // save and delete
    void createUser(UserDto userDTO);
    void deleteUser(UserDto userDTO);

    // find
    User findUser(UserDto userDTO);
    User findByUserId(String userId);
    User findByEmail(String email);

    // update
    void updateUser(String UserId, UserDto updateUser);

    // judge
    boolean isExistUserByUserId(String userId);
    boolean isExistUserByEmail(String email);
    boolean isIdAndPwValid(String idOrPw);
}
