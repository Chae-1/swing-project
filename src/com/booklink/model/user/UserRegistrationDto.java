package com.booklink.model.user;

import java.time.LocalDateTime;

public record UserRegistrationDto(String name,
        String password,
        String loginId,
        LocalDateTime registrationDate,
        String image) {

}
