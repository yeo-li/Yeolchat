package com.yeo_li.yeolchat.entity;

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
}
