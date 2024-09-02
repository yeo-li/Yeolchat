package com.yeo_li.yeolchat.service;

import com.yeo_li.yeolchat.dto.user.UserDto;
import com.yeo_li.yeolchat.dto.user.UserResultDto;
import com.yeo_li.yeolchat.entity.User;
import com.yeo_li.yeolchat.repository.UserRepository;
import com.yeo_li.yeolchat.repository.UserRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto convertToDTO(User user) {
        return null;
    }

    @Override
    public User convertToEntity(UserDto userDTO) {
        return null;
    }


    @Override
    public void saveUser(UserDto userDto) {
        //TODO pw 단방향 암호화

        // userDto user Entity로 변환
        User user = new User();
        user.setName(userDto.getName());
        user.setUser_id(userDto.getUserId());
        user.setUser_pw(userDto.getUserPw());
        user.setEmail(userDto.getEmail());

        // DB 저장
        userRepository.save(user);

        return;
    }

    @Override
    public void deleteUser(UserDto userDto) {

    }

    @Override
    public User findUser(UserDto userDTO) {

        return null;
    }

    @Override
    public User findByUserId(String userId) {

        User user = userRepository.findByUserId(userId);

        return user;
    }

    @Override
    public User findByEmail(String email) {
        return null;
    }

    @Override
    public void updateUser(String UserId, UserDto updateUser) {

    }

    @Override
    public boolean isExistUserByUserId(String userId) {
        return false;
    }

    @Override
    public boolean isExistUserByEmail(String email) {
        return false;
    }

    @Override
    public boolean isIdAndPwValid(String idOrPw) {
        return false;
    }
}
