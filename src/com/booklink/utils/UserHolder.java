package com.booklink.utils;

import com.booklink.model.user.User;
import com.booklink.model.user.exception.UserNotFoundException;
import com.booklink.service.UserService;
import com.booklink.ui.frame.main.MainFrame;

public class UserHolder {
    private static User user;
    public static boolean isLogin() {
        return user != null;
    }

    public static void logIn(User user) {
        UserHolder.user = user;
    }

    public static void logOut() {
        user = null;
    }

    public static Long getId() {
        if (user == null) {
            return -1L;
        }
        return user.getId();
    }

    public static String getName() {
        return user.getName();
    }

    public static boolean isRoot() {
        if (user == null) {
            return false;
        }
        return user.isRoot();
    }

}
