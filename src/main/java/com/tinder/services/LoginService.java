package com.tinder.services;

import com.tinder.dao.DAOUser;
import com.tinder.entities.User;

import java.time.LocalDate;
import java.util.Optional;

public class LoginService {
    private final DAOUser DAO_USER;

    public LoginService(DAOUser DAO_USER) {
        this.DAO_USER = DAO_USER;
    }


    public Optional<User> isRegisteredUser(String username, String password) {
        String SQL = String.format("SELECT * FROM users WHERE username = '%s' AND password = '%s'", username, password);
        return DAO_USER.getBy(SQL).stream().findFirst();
    }

    public void updateLastLoginDate(User user, LocalDate last_login) {
        user.setLast_login(last_login);
        DAO_USER.update(user);
    }
}
