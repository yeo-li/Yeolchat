package com.yeo_li.yeolchat.Repository;

import com.yeo_li.yeolchat.entity.User;
import com.yeo_li.yeolchat.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

//@Repository
public class UserRepositoryImplTest implements UserRepository {

    List<User> repository = new ArrayList<>();
    Long ID = 0L;

    @Override
    public void save(User user) {
        System.out.println("THERE IS TEST save at repository");
        user.setId(ID);
        ID++;

        repository.add(user);

        return;
    }

    @Override
    public User remove(User user) {
        repository.remove(user);

        return user;
    }

    @Override
    public User findByUserId(String user_id) {
        for (User user : repository) {
           if(user.getUser_id().equals(user_id)){
               return user;
           }
        }

        User nodata = new User();
        nodata.setUser_id("null");

        return nodata;
    }

    @Override
    public User findByEmail(String email) {
        for (User user : repository) {
            if(user.getEmail().equals(email)){
                return user;
            }
        }

        User nodata = new User();
        nodata.setUser_id("null");

        return nodata;
    }
}
