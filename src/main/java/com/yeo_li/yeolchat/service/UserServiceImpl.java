package com.yeo_li.yeolchat.service;

import com.yeo_li.yeolchat.dto.user.UserDto;
import com.yeo_li.yeolchat.dto.user.UserResultDto;
import com.yeo_li.yeolchat.entity.User;
import com.yeo_li.yeolchat.exception.AlreadyLoginException;
import com.yeo_li.yeolchat.exception.UserAlreadyExsistsException;
import com.yeo_li.yeolchat.exception.YeoliException;
import com.yeo_li.yeolchat.repository.UserRepository;
import com.yeo_li.yeolchat.repository.UserRepositoryImpl;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Override
    public void signUp(UserDto userDto) {

    }

    @Override
    public void signIn(UserDto userDto) {
        User user = findUser(userDto);
        if(user.isLogin()) {
            throw new AlreadyLoginException();
        }

        user.setLogin(true);
        userRepository.save(user);
    }

    @Override
    public void logout(UserDto userDto) {

    }

    @Override
    public void createUser(UserDto userDto) {
        // 기존에 있는 회원 인지 확인
        try{
            findUser(userDto);
        } catch (RuntimeException e) {
            //TODO 왜 런타임만 되는거지... NoResultException 못 잡음
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

        throw new UserAlreadyExsistsException("이미 가입되어 있는 회원입니다.");
    }

    @Override
    public void deleteUser(UserDto userDto) {
        User user = findUser(userDto);

        userRepository.remove(user);
    }

    @Override
    public User findUser(UserDto userDTO) {
        // 1중 검증
        User user = findByUserId(userDTO.getUserId());

        return user;


        //TODO 예외처리 해야함


    }

    @Override
    public User findByUserId(String userId) {

        User user = userRepository.findByUserId(userId);

        return user;
    }

    @Override
    public User findByEmail(String email) {

        User user = userRepository.findByEmail(email);

        return user;
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
