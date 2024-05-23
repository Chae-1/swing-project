package com.booklink.model.user;

import java.time.LocalDateTime;

public class User {
    private Long id;
    private String name;
    private String password;
    private String loginId;
    private LocalDateTime registrationDate;
    private String image;

    public User() {
        this.id = id;
        this.name = name;
        this.password = password;
        this.loginId = loginId;
        this.registrationDate = registrationDate;
        this.image = image;
    }
    // 래퍼 vs 기본형 user table 보고 클래스로 옮기기.
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
