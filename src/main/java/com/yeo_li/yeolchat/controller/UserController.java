package com.yeo_li.yeolchat.controller;

import com.yeo_li.yeolchat.dto.user.signIn.UserSignInRequest;
import com.yeo_li.yeolchat.dto.user.signOut.UserSignOutRequest;
import com.yeo_li.yeolchat.dto.user.signUp.UserSignUpRequest;
import com.yeo_li.yeolchat.entity.User;
import com.yeo_li.yeolchat.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("")
    public ResponseEntity<String> createUser(@RequestBody UserSignUpRequest userDTO){

        userService.signUp(userDTO);

        return new ResponseEntity<>("회원가입이 정상적으로 수행되었습니다.", HttpStatus.OK);
    }


    @PostMapping("/signin")
    public ResponseEntity<String> signIn(@RequestBody UserSignInRequest userSignInRequest, HttpServletResponse response) {

        String token = userService.signIn(userSignInRequest);

        Cookie cookie = new Cookie("signInToken", token);
        cookie.setDomain("localhost");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(30*60);
        cookie.setPath("/");

        response.addCookie(cookie);

        return new ResponseEntity<>("환영합니다. "+userSignInRequest.getUserId()+"님!", HttpStatus.OK);
    }


    @PostMapping("/signout/{id}")
    public ResponseEntity<String> signOut(HttpServletRequest request, HttpServletResponse response){
            // TODO 로그아웃 구현하기
    }

    @GetMapping("/signin/test")
    public String getCookie(HttpServletRequest request) {
        // 쿠키 배열 가져오기
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            System.out.println("cookies.length = " + cookies.length);
            for (Cookie cookie : cookies) {
                if ("signInToken".equals(cookie.getName())) {
                    return "Cookie value: " + cookie.getValue();
                }
            }
        }
        return "Cookie not found";
    }
}