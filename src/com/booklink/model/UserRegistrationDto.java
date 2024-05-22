package com.booklink.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class UserRegistrationDto {
   // private Long id;
    private String name;
    private String password;
    private String loginId;
    private LocalDateTime registrationDate;
    private String image;

    public UserRegistrationDto(String name, String password, String loginId,
                               LocalDateTime registrationDate, String image) {
        this.name = name;
        this.password = password;
        this.loginId = loginId;
        this.registrationDate = registrationDate;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
