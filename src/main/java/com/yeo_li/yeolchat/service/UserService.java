package com.yeo_li.yeolchat.service;

import com.yeo_li.yeolchat.dto.user.UserDto;
import com.yeo_li.yeolchat.entity.User;

public interface UserService {

    // DTO <-> Entity converter

    /**
     * convertToDTO는 repository에서 받은 user entity를 UserDTO로
     * 변환시키는 로직을 수행합니다.
     */
    UserDto convertToDTO(User user);

    /**
     * save, delete를 수행할 때, UserDTO를 entity로 변환하는 로직을 수행합니다.
     * @param userDTO
     * @return
     */
    User convertToEntity(UserDto userDTO);

    // save and delete
    void saveUser(UserDto userDTO);
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
