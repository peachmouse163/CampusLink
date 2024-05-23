package com.example.campuslink.login;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.campuslink.InitAll;
import com.example.campuslink.MainActivity;
import com.example.campuslink.R;
import com.example.campuslink.TeacherActivity;
import com.example.campuslink.model.User;
import com.google.gson.Gson;

public class LoginActivity extends AppCompatActivity implements InitAll {

    private LoginViewModel loginViewModel;

    private Button btnLogin;
    private EditText etNo,etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //OverWrite
        HideTitle();
        initView();
        initData();
        initClick();

    }

    @Override
    public void HideTitle() {
        ActionBar actionBar= getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }
    }

    @Override
    public void initView() {
        etNo = findViewById(R.id.login_et_name);
        etPassword = findViewById(R.id.login_et_password);
        btnLogin = findViewById(R.id.login_btn_login);
    }

    @Override
    public void initData() {
        loginViewModel = new LoginViewModel(this);
    }

    @Override
    public void initClick() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginViewModel.isExistance(etNo.getText().toString(),etPassword.getText().toString());
            }
        });
    }


    public EditText getEtPassword() {
        return etPassword;
    }
}