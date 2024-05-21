package com.booklink.model.user;

import java.time.LocalDateTime;

public class UserRegistrationDto {
    private String name;
    private String password;
    private String loginId;
    private LocalDateTime registrationDate;
    private String image;

    public UserRegistrationDto(String name, String password, String loginId, LocalDateTime registrationDate, String image) {
        this.name = name;
        this.password = password;
        this.loginId = loginId;
        this.registrationDate = registrationDate;
        this.image = image;
    }
}
