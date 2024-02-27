package com.example.campuslink.login;

import android.widget.EditText;
import android.widget.Toast;

import com.example.campuslink.model.User;

public final class LoginViewModel {

    private final User user;
    private final LoginActivity loginActivity;

    public LoginViewModel(LoginActivity loginActivity) {
        user = new User();
        this.loginActivity = loginActivity;
    }

    boolean isExistance(String userNo, String userPassword){
        return user.isExistence(userNo,userPassword);
    }

    //清除user中的数据
    void clearUser(){
        user.setInfoNo(0);
        user.setInfoPassword("");
        loginActivity.getEtPassword().setText("");
        Toast.makeText(loginActivity,"密码错误",Toast.LENGTH_SHORT).show();
    }

}
