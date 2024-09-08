package com.yeo_li.yeolchat.controller;

import com.yeo_li.yeolchat.dto.user.signIn.UserSignInRequest;
import com.yeo_li.yeolchat.dto.user.signIn.UserSignInResult;
import com.yeo_li.yeolchat.dto.user.signUp.UserSignUpRequest;
import com.yeo_li.yeolchat.exception.AlreadySigninOutException;
import com.yeo_li.yeolchat.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("")
    public ResponseEntity<String> signUp(@RequestBody UserSignUpRequest userDTO){

        userService.signUp(userDTO);

        return new ResponseEntity<>("회원가입이 정상적으로 수행되었습니다.", HttpStatus.OK);
    }


    @PostMapping("/signin")
    public ResponseEntity<String> signIn(@RequestBody UserSignInRequest userSignInRequest, HttpServletResponse response) {

        // UserSignInReseutDto 로 반환 받기
        // token, userName
        UserSignInResult userSignInResult = userService.signIn(userSignInRequest);
        String signInToken = userSignInResult.getToken();
        String userName = userSignInResult.getUserName();

        Cookie cookie = userService.setCookie("signInToken", signInToken);
        response.addCookie(cookie);

        return new ResponseEntity<>("환영합니다. "+userName+"님!", HttpStatus.OK);
    }


    @PostMapping("/signout")
    public ResponseEntity<String> signOut(HttpServletRequest request, HttpServletResponse response) throws AlreadySigninOutException {
            // TODO 로그아웃 구현하기

        String cookieName = "signInToken";

        Cookie[] cookies = request.getCookies();


        // UserService 영역인가
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    // 토큰 찾기
                    Cookie expiredCookie = userService.expireCookie(cookie);
                    // 토큰 만료 시키기
                    response.addCookie(expiredCookie);
                    return new ResponseEntity<>("로그아웃 되었습니다.", HttpStatus.OK);
                }
            }
        }

        throw new AlreadySigninOutException("이미 로그아웃 되었습니다.");

    }


}