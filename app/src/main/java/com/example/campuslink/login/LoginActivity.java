package com.example.campuslink.login;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.campuslink.InitAll;
import com.example.campuslink.MainActivity;
import com.example.campuslink.R;
import com.example.campuslink.TeacherActivity;
import com.example.campuslink.link.PushNotification;
import com.example.campuslink.model.User;
import com.google.gson.Gson;

public class LoginActivity extends AppCompatActivity implements InitAll {

    private LoginViewModel loginViewModel;

    private Button btnLogin;
    private EditText etNo,etPassword;

    //test
    private NotificationManager mNManager;
    private Notification notify1;
    Bitmap LargeBitmap = null;
    private static final int NOTIFYID_1 = 1;

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

        //test();
    }

    private void test() {
        Context mContext = LoginActivity.this;
        //创建大图标的Bitmap
        LargeBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        mNManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        //定义一个PendingIntent点击Notification后启动一个Activity
        Intent it = new Intent(mContext, LoginActivity.class);
        PendingIntent pit = PendingIntent.getActivity(mContext, 0, it, 0);

        //设置图片,通知标题,发送时间,提示方式等属性
        Notification.Builder mBuilder = new Notification.Builder(this);
        mBuilder.setContentTitle("叶良辰")                        //标题
                .setContentText("我有一百种方法让你呆不下去~")      //内容
                .setSubText("——记住我叫叶良辰")                    //内容下面的一小段文字
                .setTicker("收到叶良辰发送过来的信息~")             //收到信息后状态栏显示的文字信息
                .setWhen(System.currentTimeMillis())           //设置通知时间
                .setSmallIcon(R.mipmap.ic_launcher)            //设置小图标
                .setLargeIcon(LargeBitmap)                     //设置大图标
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE)    //设置默认的三色灯与振动器
                //.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.biaobiao))  //设置自定义的提示音
                .setAutoCancel(true)                           //设置点击后取消Notification
                .setContentIntent(pit);                        //设置PendingIntent
        notify1 = mBuilder.build();
        mNManager.notify(NOTIFYID_1, notify1);
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
                //new PushNotification(LoginActivity.this,BitmapFactory.decodeResource(getResources(), R.drawable.ic_notifications_black_24dp),(NotificationManager) getSystemService(NOTIFICATION_SERVICE)).createNotification("title","text","subText","ticker",new Intent(LoginActivity.this,LoginActivity.class));
            }
        });
    }


    public EditText getEtPassword() {
        return etPassword;
    }
}