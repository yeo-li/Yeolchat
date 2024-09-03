package com.yeo_li.yeolchat.dto.user.delete;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserDeleteRequest {

    private String name;
    private String userId;
    private String userPw;
    private String email;
}
