package com.yeo_li.yeolchat.repository;

import com.yeo_li.yeolchat.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository {

    // save
    void save(User user);

    // remove
    User remove(User user);

    // find
    User findByUserId(String user_id);

    User findByEmail(String email);

}
