package com.example.helloworld.dao;

import com.example.helloworld.entities.core.User;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

/**
 * Created by vaidyanathan.s on 10/05/15.
 */
public class UserDAO extends AbstractDAO<User> {
    public UserDAO(SessionFactory factory) {
        super(factory);
    }

    public User findById(Long id) {
        return get(id);
    }

    public long create(User user) {
        return persist(user).getId();
    }

    public void updateUserVerification(User.VerificationStatus code, User user) {
        user.setVerified(code);
        persist(user);
    }

    public void updateEmailToken(User user, String emailToken) {
        user.setEmailToken(emailToken);
        persist(user);
    }
}
