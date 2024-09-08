package com.yeo_li.yeolchat.repository;

import com.yeo_li.yeolchat.entity.User;
import com.yeo_li.yeolchat.exception.YeoliException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Primary
@Repository
@RequiredArgsConstructor
@Transactional
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager em;


    @Override
    public void save(User user) {
        em.persist(user);
    }

    @Override
    public User remove(User user) {
        if(user != null) {
            em.remove(user);
        } else {
            throw new RuntimeException("UserRepository/remove: User Not Found:" + user.getUser_id());
        }

        return null;
    }

    @Override
    public User findByUserId(String user_id) {
            User user = em.createQuery("SELECT u FROM User u WHERE u.user_id = :user_id", User.class)
                    .setParameter("user_id", user_id)
                    .getSingleResult();

            return user;


    }

    @Override
    public User findByEmail(String email) {
        User user = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                .setParameter("email", email)
                .getSingleResult();

        return user;
    }

}


/* TODO 에러코드 형식을 어떻게 해야하는가??

{
    statusCode
    message
    etcDate
}
 */