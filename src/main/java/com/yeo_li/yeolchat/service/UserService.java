package com.yeo_li.yeolchat.service;

import com.yeo_li.yeolchat.dto.user.delete.UserDeleteRequest;
import com.yeo_li.yeolchat.dto.user.signIn.UserSignInRequest;
import com.yeo_li.yeolchat.dto.user.signIn.UserSignInResult;
import com.yeo_li.yeolchat.dto.user.signOut.UserSignOutRequest;
import com.yeo_li.yeolchat.dto.user.signUp.UserSignUpRequest;
import com.yeo_li.yeolchat.entity.User;
import com.yeo_li.yeolchat.exception.SignOutInfoNotFoundException;
import jakarta.servlet.http.Cookie;

public interface UserService {
    void signUp(UserSignUpRequest userSignUpRequest);
    UserSignInResult signIn(UserSignInRequest userSignInRequest);
    void signOut(UserSignOutRequest userSignOutRequest);

    // save and delete
    void saveUser(UserSignUpRequest userSignUpRequest);
    void deleteUser(UserDeleteRequest userDeleteRequest);

    // find
    User findByUserId(String userId);
    User findByEmail(String email);

    // update
    // void updateUser(String UserId, UserSignUpRequest updateUser);

    // judge
    boolean isExistUserByUserId(String userId);
    boolean isExistUserByEmail(String email);

    //cookie
    Cookie setCookie(String name, String value);
    Cookie expireCookie(String name);
    Cookie findCookie(String name, Cookie[] cookies) throws SignOutInfoNotFoundException;
    boolean isTokenPresent(String name, Cookie[] cookies);
}
