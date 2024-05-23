package com.booklink.model.user;

<<<<<<< HEAD
=======
import java.time.LocalDate;
>>>>>>> feature-branch
import java.time.LocalDateTime;

public class User {
    private Long id;
    private String name;
    private String password;
    private String loginId;
    private LocalDateTime registrationDate;
    private String image;

    public User() {

    public User(Long id, String name, String password, String loginId, LocalDateTime registrationDate, String image) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.loginId = loginId;
        this.registrationDate = registrationDate;
        this.image = image;
    }
    // 래퍼 vs 기본형 user table 보고 클래스로 옮기기.
}
