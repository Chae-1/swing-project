package com.booklink.service;

import com.booklink.dao.UserDao;
import com.booklink.model.User;
import com.booklink.model.UserRegistrationDto;

import java.time.LocalDateTime;
import java.util.List;

public class UserService {
    private final UserDao userDao = new UserDao();

    public void registrationUser(UserRegistrationDto userRegistrationDto) {
        // 검증
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


