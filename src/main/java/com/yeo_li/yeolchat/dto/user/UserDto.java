package com.yeo_li.yeolchat.dto.user;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class UserDto {
    private String name;
    private String userId;
    private String userPw;
    private String email;


}
