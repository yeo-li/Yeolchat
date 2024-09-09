package com.yeo_li.yeolchat.controller;

import com.yeo_li.yeolchat.dto.user.signIn.UserSignInRequest;
import com.yeo_li.yeolchat.dto.user.signIn.UserSignInResponse;
import com.yeo_li.yeolchat.dto.user.signIn.UserSignInResult;
import com.yeo_li.yeolchat.dto.user.signOut.UserSignOutResponse;
import com.yeo_li.yeolchat.dto.user.signUp.UserSignUpRequest;
import com.yeo_li.yeolchat.exception.SignOutInfoNotFoundException;
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
    public ResponseEntity<UserSignInResponse> signIn(@RequestBody UserSignInRequest userSignInRequest, HttpServletResponse response) {

        // UserSignInReseutDto 로 반환 받기
        // token, userName
        UserSignInResult userSignInResult = userService.signIn(userSignInRequest);
        String signInToken = userSignInResult.getToken();
        String userName = userSignInResult.getUserName();

        Cookie cookie = userService.setCookie("signInToken", signInToken);
        response.addCookie(cookie);




        UserSignInResponse userSignInResponse = new UserSignInResponse();
        userSignInResponse.setMessage("로그인 성공");
        userSignInResponse.setStatusCode(200);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userSignInResponse);
    }


    @PostMapping("/signout")
    public ResponseEntity<UserSignOutResponse> signOut(HttpServletRequest request, HttpServletResponse response) throws SignOutInfoNotFoundException {
        String cookieName = "signInToken";
        // 사용자에게 토큰 가져오기
        Cookie[] cookies = request.getCookies();

        if(userService.isTokenPresent(cookieName, cookies)){
            Cookie expiredToken = userService.expireCookie(cookieName);

            // 같은 이름의 만료된 쿠키를 덮어씌워서 기존 토큰 지우기
            response.addCookie(expiredToken);


            UserSignOutResponse userSignOutResponse = new UserSignOutResponse();
            userSignOutResponse.setStatusCode(200);
            userSignOutResponse.setMessage("로그아웃 완료");

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(userSignOutResponse);
        }

        throw new SignOutInfoNotFoundException("로그아웃할 정보가 없습니다.");
    }


}