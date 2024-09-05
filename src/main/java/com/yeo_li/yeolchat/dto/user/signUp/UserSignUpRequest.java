package com.yeo_li.yeolchat.dto.user.signUp;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter @Setter
public class UserSignUpRequest {

    private String name;
    private String userId;
    private String userPw;
    private String email;
}
