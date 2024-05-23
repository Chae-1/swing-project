package com.booklink.service;

import com.booklink.dao.UserDao;
import com.booklink.model.user.User;
<<<<<<< HEAD
=======
import com.booklink.model.user.UserRegistrationDto;
import com.booklink.model.user.exception.UserNotFoundException;

import java.util.Optional;

import java.time.LocalDateTime;
import java.util.List;

public class UserService {
    private final UserDao userDao = new UserDao();

    public void registrationUser(UserRegistrationDto userRegistrationDto) {
        if (userRegistrationDto.getName() == null || userRegistrationDto.getName().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }

        if (userRegistrationDto.getPassword() == null || userRegistrationDto.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        if (userRegistrationDto.getLoginId() == null || userRegistrationDto.getLoginId().isEmpty()) {
            throw new IllegalArgumentException("Login ID cannot be null or empty");
        }

        if (userRegistrationDto.getRegistrationDate() == null) {
            throw new IllegalArgumentException("Registration Date cannot be null");
        }
        // DAO 접근해서 저장
        userDao.registerUser(userRegistrationDto);
    }

    public List<User> getAllUsers(){
        return userDao.findAll();

    public void registrationUser(UserRegistrationDto userRegistrationDto) {

    }

    public Optional<User> findUserByLogIdAndPassword(String logId, String password) {
        if (logId.isBlank() || password.isBlank()) {
            throw new UserNotFoundException("존재하지 않는 회원입니다.");
        }

        return null;
    }

    private void deleteUserById(long userId) {
        userDao.deleteUserById(userId);
    }

    public static void main(String[] args) {
        UserService userService = new UserService();

        UserRegistrationDto userDto = new UserRegistrationDto(
                "김호영2",
                "1272138",
                "377112",
                LocalDateTime.now(),
                "url:user사진이들어갈url"

        );
        userService.registrationUser(userDto);

        //모든 사용자 조회
        List<User> users = userService.getAllUsers();
        for (User user : users) {
            System.out.println("ID: " + user.getId());
            System.out.println("Name: " + user.getName());
            System.out.println("Login ID: " + user.getLoginId());
            System.out.println("Registration Date: " + user.getRegistrationDate());
            System.out.println("Image: " + user.getImage());
            System.out.println();
        }

        //사용자 삭제
        long userIdToDelete = 5; // 삭제할 사용자 ID
        userService.deleteUserById(userIdToDelete);
    }
}


