package com.booklink.service;

import com.booklink.dao.UserDao;
import com.booklink.model.user.User;
import com.booklink.model.user.UserRegistrationDto;
import com.booklink.model.user.exception.UserNotFoundException;


import java.util.Optional;

import java.time.LocalDateTime;
import java.util.List;

public class UserService {
    private final UserDao userDao = new UserDao();

    public void registrationUser(UserRegistrationDto userRegistrationDto) {
        if (userRegistrationDto.name() == null || userRegistrationDto.name().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        // DAO 접근해서 저장
        userDao.registerUser(userRegistrationDto);
    }

    public Optional<User> findUserByLogIdAndPassword(String logId, String password) {
        if (logId.isBlank() || password.isBlank()) {
            throw new UserNotFoundException();
        }

        return userDao.findByLogIdAndPassword(logId, password);
    }


    public void deleteUserById(long userId) {
        userDao.deleteUserById(userId);
    }


}


