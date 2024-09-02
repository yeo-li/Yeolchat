package com.yeo_li.yeolchat.service;

import com.yeo_li.yeolchat.Repository.UserRepositoryImplTest;
import com.yeo_li.yeolchat.dto.user.UserDto;
import com.yeo_li.yeolchat.entity.User;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private UserRepositoryImplTest userRepository;

    @Test
    public void createUser() {
        // Given
        UserDto user1 = new UserDto();

        user1.setUserId("100");
        user1.setUserPw("200");
        user1.setName("박성열");
        user1.setEmail("ert2143@naver.com");

        // When
        userService.saveUser(user1);

        User user2 = userRepository.findByUserId(user1.getUserId());

        // Then
        assertThat(user2.getUser_id()).isEqualTo(user1.getUserId());
    }
}
