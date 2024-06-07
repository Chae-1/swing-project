package com.booklink.ui.panel.menu;

import java.util.ArrayList;

public class UserDataSet {
    private ArrayList<UserLoginForm> userLoginForms;

    public UserDataSet() {
        userLoginForms = new ArrayList<>();
    }

    // 회원 추가
    public void addUsers(UserLoginForm userLoginForm) {
        userLoginForms.add(userLoginForm);
    }
    // 아이디 중복 확인

    public boolean isIdOverlap(String id) {
        return userLoginForms.contains(new UserLoginForm(id));
    }

    // 회원 삭제
    public void withdraw(String id) {
        userLoginForms.remove(getUser(id));
    }
    // 유저 정보 가져오기
    public UserLoginForm getUser(String id) {
        return userLoginForms.get(userLoginForms.indexOf(new UserLoginForm(id)));
    }
    // 회원인지 아닌지 확인
    public boolean contains(UserLoginForm userLoginForm) {
        return userLoginForms.contains(userLoginForm);
    }

}
