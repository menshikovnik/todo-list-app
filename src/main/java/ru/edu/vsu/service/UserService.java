package ru.edu.vsu.service;

import ru.edu.vsu.dao.UserDao;
import ru.edu.vsu.model.User;
import ru.edu.vsu.util.PasswordUtil;

import java.sql.SQLException;

public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void register(String email, String password) throws SQLException {
        String hash = PasswordUtil.hash(password);
        User user = new User();
        user.setEmail(email);
        user.setPasswordHash(hash);
        user.setEnabled(true);
        userDao.save(user);
    }

    public User findByEmail(String email) throws SQLException {
        return userDao.findByEmail(email);
    }


    public boolean checkPassword(User user, String plainPassword) {
        return PasswordUtil.check(plainPassword, user.getPasswordHash());
    }
}
