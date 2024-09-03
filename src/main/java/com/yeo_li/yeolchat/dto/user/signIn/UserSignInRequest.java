package com.yeo_li.yeolchat.dto.user.signIn;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class UserSignInRequest {

    private String userId;
    private String userPw;
}
