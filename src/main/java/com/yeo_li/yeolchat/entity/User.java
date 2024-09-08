package com.yeo_li.yeolchat.entity;

import com.yeo_li.yeolchat.exception.InvalidEmailException;
import com.yeo_li.yeolchat.exception.InvalidPasswordException;
import com.yeo_li.yeolchat.exception.InvalidUserIdException;
import com.yeo_li.yeolchat.exception.InvalidUserNameException;
import com.yeo_li.yeolchat.util.Sha256PasswordEncoder;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class User {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String user_id;

    @Column(nullable = false)
    private String user_pw;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private Date created_at;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatroomList> chatroomLists;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages;


    public boolean passwordMatches(String plainPassword, Sha256PasswordEncoder encoder) {
        String rawPassword = plainPassword;
        String hashedPassword = this.user_pw;

        return encoder.matches(rawPassword, hashedPassword);
    }


    public void setUser_pw(String user_pw, Sha256PasswordEncoder encoder) {
        if(validateUserIdAndPassword(user_pw)){
            this.user_pw = encoder.encode(user_pw);
        } else{
            throw new InvalidPasswordException("비밀번호를 다시 입력해 주세요.(9~20자, 알파벳 대소문자 및 특수문자 사용 가능)");
        }

    }

    public void setName(String name) {
        if(validateUserName(name)) {
            this.name = name;
        } else{
            throw new InvalidUserNameException("이름을 다시 입력해주세요.");
        }
    }

    public void setUser_id(String user_id) {
        if(validateUserIdAndPassword(user_id)){
            this.user_id = user_id;
        } else{
            throw new InvalidUserIdException("아이디를 다시 입력해 주세요.(9~20자, 알파벳 대소문자 및 특수문자 사용 가능)");
        }
    }


    public void setEmail(String email) {
        if(validateEmail(email)){
            this.email = email;
        } else{
            throw new InvalidEmailException("이메일을 다시 입력해 주세요.");
        }

    }



    private boolean validateUserIdAndPassword(String userIdOr){
        // 9~20 사이의 공백을 제외한 문자열
        if(existSpaces(userIdOr)){
            return false;
        }

        if(existSpaces(userIdOr)){
            return false;
        }

        int lengthOfUserIdOrPassword = userIdOr.length();
        return 9 <= lengthOfUserIdOrPassword && lengthOfUserIdOrPassword <= 20;
    }

    private boolean validateEmail(String email) {
        // 이메일에 공백이 있는지 확인
        if(existSpaces(email)){
            return false;
        }
        // @와 .이 각각 하나씩만 포함되어 있는지 확인
        int atCount = email.length() - email.replace("@", "").length();
        int dotCount = email.length() - email.replace(".", "").length();

        // @와 .이 각각 정확히 하나씩만 있는지 확인
        if (atCount == 1 && dotCount == 1) {
            return true;
        }

        return false;

    }

    private boolean validateUserName(String name){
        if(!name.isEmpty() && name.length() <= 20) {
            if(!existSpaces(name)){
                return true;
            }
        }
        return false;
    }



    private boolean existSpaces(String str) {
        if(str.contains(" ")){
            return true;
        }
        return false;
    }

    private boolean existKorean(String str) {
        // 한글이 포함되어 있는지 확인하는 정규표현식
        return str.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*");
    }

}
