package com.booklink.service;

import com.booklink.dao.UserDao;
import com.booklink.model.user.User;
import com.booklink.model.user.UserRegistrationDto;
import com.booklink.model.user.exception.UserNotFoundException;

import java.util.Optional;

public class UserService {
    private UserDao userDao;
    public void registrationUser(UserRegistrationDto userRegistrationDto) {
//        userDao.find
//        userRegistrationDto.loginId()
    }

    public Optional<User> findUserByLogIdAndPassword(String logId, String password) {
        if (logId.isBlank() || password.isBlank()) {
            throw new UserNotFoundException("존재하지 않는 회원입니다.");
        }

        return null;
    }

}
