package com.yeo_li.yeolchat.service;

import com.yeo_li.yeolchat.dto.user.delete.UserDeleteRequest;
import com.yeo_li.yeolchat.dto.user.signIn.UserSignInRequest;
import com.yeo_li.yeolchat.dto.user.signOut.UserSignOutRequest;
import com.yeo_li.yeolchat.dto.user.signUp.UserSignUpParam;
import com.yeo_li.yeolchat.dto.user.signUp.UserSignUpRequest;
import com.yeo_li.yeolchat.entity.User;
import com.yeo_li.yeolchat.exception.InvalidPasswordException;
import com.yeo_li.yeolchat.exception.UserAlreadyExsistsException;
import com.yeo_li.yeolchat.exception.UserEmailAlreadyExsistsException;
import com.yeo_li.yeolchat.repository.UserRepository;
import com.yeo_li.yeolchat.util.Sha256PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

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
            throw new UserAlreadyExsistsException("유효하지 않은 아이디에요. 다시 입력해 주세요.");
        }
        if (isExistUserByEmail(newUserEmail)) {
            throw new UserEmailAlreadyExsistsException("이메일 중복 다시 입력해 주세요.");
        }

        // save user
        saveUser(userSignUpRequestDto);

    }




    @Override
    public String signIn(UserSignInRequest userSignInRequest) {
        String userId = userSignInRequest.getUserId();
        User user = findByUserId(userId); // 여기선 예외가 터져도 심각하지 않음. 혹시 있더라도 controllerAdvice에서 catch. 따라서 유저 정보가 있다고 가정.

        // 여기까지 왔다는건 저.. 요청자가 제시한 사용자 ID에 대응되는 사용자가 존재는 한다는 것

        /*
            1. 사용자를 id로 조회함
            2. 그 뒤 pw도 맞는지 확인, 아니라면 throw InvalidPasswordException!
            3. pw도 맞다면, 토큰 발급 후 로그인 처리
         */

        // TODO 이 부분에 대해서 정리할 필요가 있음.
        // passwordEncoder를 인자로 넣어주는지. 그 이유는 책임을 분리하여 서비스의 크기가 비대해지는 것을 막기 위해서임.
        if (!user.passwordMatches(userSignInRequest.getUserPw(), passwordEncoder)) {
            throw new InvalidPasswordException("비밀번호가 일치하지 않아요.");
        }

        // 여기까지 왔다는 것은, 로그인에 결격사유가 없다는 것!
        // 이제 인증 성공한 사용자라는 뜻으로 이를 증명하는 증표를 발급해서 리턴해쥬야겟쥬???
        // 토ㄱ큰이라등가 세션키라등가,,,???????????????????????????
        // TODO 예아

        // 이 토큰은 오직 로그인에 성공한, 자신의 아이디와 비밀번호를 알고 있는,
        // 즉슨 사용자 본인에게 발급되는겁니다.
        // 다음번 요청에 이 토큰을 들고 오면은!
        // 그 요청을 날린 사람은 바로 로그인에 성공한 사용자라는 것을 의미함니다.
        String token = String.format("yeolchatToken:%s", user.getUser_id());

        
        // controller에서 토큰을 UserId로 변환하여 서비스로 넘김! 사실상 컨트롤러가 문지기 역할을 해주는거임.
        return token;
    }

    @Override
    public void signOut(UserSignOutRequest userSignOutRequest) {
        User user = findByUserId(userSignOutRequest.getUserId());
        // 토큰 삭제 요청 보내기?
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

    @Override
    public void updateUser(String UserId, UserSignUpRequest updateUser) {

    }

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


    //////////////////////////////////// etc methods

    public User convertToEntityAtSignUp(UserSignUpRequest userSignUpRequest, Sha256PasswordEncoder passwordEncoder) {
        User user = new User();
        user.setName(userSignUpRequest.getName());
        user.setUser_id(userSignUpRequest.getUserId());
        user.setUser_pw(userSignUpRequest.getUserPw(), passwordEncoder);
        user.setEmail(userSignUpRequest.getEmail());
        user.setCreated_at(new Date());

        return user;
    }


}