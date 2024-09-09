package com.yeo_li.yeolchat.service;

import com.yeo_li.yeolchat.dto.user.delete.UserDeleteRequest;
import com.yeo_li.yeolchat.dto.user.signIn.UserSignInRequest;
import com.yeo_li.yeolchat.dto.user.signIn.UserSignInResult;
import com.yeo_li.yeolchat.dto.user.signOut.UserSignOutRequest;
import com.yeo_li.yeolchat.dto.user.signUp.UserSignUpRequest;
import com.yeo_li.yeolchat.entity.User;
import com.yeo_li.yeolchat.exception.*;
import com.yeo_li.yeolchat.exception.signUpException.UserSignUpAlreadyExsistsException;
import com.yeo_li.yeolchat.exception.signUpException.UserSignUpEmailAlreadyExsistsException;
import com.yeo_li.yeolchat.repository.UserRepository;
import com.yeo_li.yeolchat.util.Sha256PasswordEncoder;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final Sha256PasswordEncoder passwordEncoder;


    @Override
    public void signUp(UserSignUpRequest userSignUpRequestDto) {
        String newUserId = userSignUpRequestDto.getUserId();
        String newUserEmail = userSignUpRequestDto.getEmail();

        if (isExistUserByUserId(newUserId)) {
            throw new UserSignUpAlreadyExsistsException("유효하지 않은 아이디에요. 다시 입력해 주세요.");
        }
        if (isExistUserByEmail(newUserEmail)) {
            throw new UserSignUpEmailAlreadyExsistsException("이메일 중복 다시 입력해 주세요.");
        }

        // save user
        saveUser(userSignUpRequestDto);

    }

    @Override
    public UserSignInResult signIn(UserSignInRequest userSignInRequest) {
        String userId = userSignInRequest.getUserId();

        // 회원 유무 조회
        if(!isExistUserByUserId(userId)){
            throw new InvalidUserIdException("아이디가 일치하지 않아요.");
        }

        User user = findByUserId(userId); // 여기선 예외가 터져도 심각하지 않음. 혹시 있더라도 controllerAdvice에서 catch. 따라서 유저 정보가 있다고 가정.
        // 여기까지 왔다는건 저.. 요청자가 제시한 사용자 ID에 대응되는 사용자가 존재는 한다는 것


        // TODO 이 부분에 대해서 정리할 필요가 있음.
        // passwordEncoder를 인자로 넣어주는지. 그 이유는 책임을 분리하여 서비스의 크기가 비대해지는 것을 막기 위해서임.
        if (!user.passwordMatches(userSignInRequest.getUserPw(), passwordEncoder)) {
            throw new InvalidPasswordException("비밀번호가 일치하지 않아요.");
        }

        // 여기까지 왔다는 것은, 로그인에 결격사유가 없다는 것!
        // 이제 인증 성공한 사용자라는 뜻으로 이를 증명하는 증표를 발급해서 리턴해쥬야겟쥬???
        // 토ㄱ큰이라등가 세션키라등가,,,???????????????????????????

        // 이 토큰은 오직 로그인에 성공한, 자신의 아이디와 비밀번호를 알고 있는,
        // 즉슨 사용자 본인에게 발급되는겁니다.
        // 다음번 요청에 이 토큰을 들고 오면은!
        // 그 요청을 날린 사람은 바로 로그인에 성공한 사용자라는 것을 의미함니다.

        // tokem 발급
        String token = createToken(user);
        // TODO 허용 토큰 DB에 저장
        // TODO 허용 토큰 DB에 있다면 기존에 있던 토큰 삭제
        // TODO 이건 모듈로 빼야할 것 같은데, 사용자에게 요청이 들어올 때 마다 허용 토큰인지 계속 확인하기
        //  만약 만료된 토큰이라면 로그아웃 및 허용 토큰 리스트에서 삭제


        // UserSignInResultDto 전환
        UserSignInResult userSignInResult = new UserSignInResult();
        userSignInResult.setUserName(user.getName());
        userSignInResult.setToken(token);

        // controller에서 토큰을 UserId로 변환하여 서비스로 넘김! 사실상 컨트롤러가 문지기 역할을 해주는거임.
        return userSignInResult;
    }

    @Override
    public void signOut(UserSignOutRequest userSignOutRequest) {
        User user = findByUserId(userSignOutRequest.getUserId());
        // 토큰 삭제 요청 보내기?
        // 혀옹 토큰 리스트에서 토큰 삭제 하기
    }


    @Override
    public void saveUser(UserSignUpRequest userSignUpRequest) {
        // userDto user Entity로 변환
        User user = convertToEntityAtSignUp(userSignUpRequest, passwordEncoder);
        // DB 저장
        userRepository.save(user);
    }

    @Override
    public void deleteUser(UserDeleteRequest userDeleteRequest) {
        String deleteUserId = userDeleteRequest.getUserId();

        User user = findByUserId(deleteUserId);

        userRepository.remove(user);
    }


    @Override
    public User findByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    // TODO updateUser는 시간 남으면 개발해~
//    @Override
//    public void updateUser(String UserId, UserSignUpRequest updateUser) {
//
//    }

    @Override
    public boolean isExistUserByUserId(String userId) {
        try {
            findByUserId(userId);
            return true;
        } catch (EmptyResultDataAccessException e) {
            //TODO 왜 런타임만 되는거지... NoResultException 못 잡음
            return false;
        }
    }

    @Override
    public boolean isExistUserByEmail(String email) {
        try {
            findByEmail(email);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    @Override
    public Cookie setCookie(String name, String value) {

        
        Cookie cookie = new Cookie(name, value);
        cookie.setDomain("localhost");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(6*60*60); // 로그인 토큰 만료 기간은 6시간
        cookie.setPath("/");

        return cookie;
    }

    @Override
    public Cookie expireCookie(String name) {
        Cookie cookie = new Cookie(name, "");
        cookie.setPath("/");
        cookie.setMaxAge(0);  // 유효기간을 0으로 설정하여 삭제

        return cookie;
    }

    @Override
    public Cookie findCookie(String name, Cookie[] cookies) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    return cookie;
                }
            }
        }

        throw new SignOutInfoNotFoundException("로그인 정보가 없습니다.");
    }

    @Override
    public boolean isTokenPresent(String name, Cookie[] cookies) {
        try{
            findCookie(name, cookies);
        } catch (SignOutInfoNotFoundException e){
            return false;
        }

        return true;
    }


    //////////////////////////////////// etc methods

    private User convertToEntityAtSignUp(UserSignUpRequest userSignUpRequest, Sha256PasswordEncoder passwordEncoder) {
        User user = new User();
        user.setName(userSignUpRequest.getName());
        user.setUser_id(userSignUpRequest.getUserId());
        user.setUser_pw(userSignUpRequest.getUserPw(), passwordEncoder);
        user.setEmail(userSignUpRequest.getEmail());
        user.setCreated_at(new Date());

        return user;
    }

    private String createToken(User user){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        LocalDateTime atSignIn = LocalDateTime.now();
        LocalDateTime expiresAt = atSignIn.plusMinutes(30);
        String dateString = expiresAt.format(formatter);

        return dateString+":"+user.getUser_id();
    }




}