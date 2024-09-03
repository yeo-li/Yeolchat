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
import org.antlr.v4.runtime.misc.NotNull;

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
            throw new InvalidPasswordException("InvalidUserIdException: 비밀번호를 다시 입력해 주세요.(9~20자, 알파벳 대소문자 및 특수문자 사용 가능)");
        }

    }

    public void setName(String name) {
        if(validateUserName(name)) {
            this.name = name;
        } else{
            throw new InvalidUserNameException("InvalidUserNameException: 이름을 다시 입력해주세요.");
        }
    }

    public void setUser_id(String user_id) {
        if(validateUserIdAndPassword(user_id)){
            this.user_id = user_id;
        } else{
            throw new InvalidUserIdException("InvalidUserIdException: 아이디를 다시 입력해 주세요.(9~20자, 알파벳 대소문자 및 특수문자 사용 가능)");
        }
    }


    public void setEmail(String email) {
        if(validateEmail(email)){
            this.email = email;
        } else{
            throw new InvalidEmailException("이메일을 다시 입력해 주세요.");
        }

    }

    public boolean validateUserIdAndPassword(String userIdOr){
        //TODO [User entity] 비밀번호, 아이디 정책 설정
        return true;

    }

    public boolean validateEmail(String email) {
        //TODO [User entity] 이메일 주소 정책 설정
        return true;

    }

    public boolean validateUserName(String name){
        //TODO [User entity] 이름 공백 예외처리
        return true;

    }
}
