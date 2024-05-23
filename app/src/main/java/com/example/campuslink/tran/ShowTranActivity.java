package com.example.campuslink.tran;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.campuslink.FssImgAdapter;
import com.example.campuslink.InitAll;
import com.example.campuslink.R;
import com.example.campuslink.communicate.ComActivity;
import com.example.campuslink.login.LoginViewModel;
import com.example.campuslink.model.TranModel;
import com.example.campuslink.model.User;
import com.example.campuslink.ui.dashboard.DashboardFragment;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ShowTranActivity extends AppCompatActivity implements InitAll {

    private int position;
    private TranModel tranModel;
    public static Activity showTranActivity;

    private Button btnToSettle,btnToTalk;
    private TextView tvTitle,tvContent,tvInfo,tvPlace,tvTime,tvMyInfo,tvMoney;
    private ImageView img;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_tran);

        HideTitle();
        initData();
        initView();
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

        btnToSettle = findViewById(R.id.stran_btn_toPay);
        btnToTalk = findViewById(R.id.stran_btn_toTalk);

        linearLayout = findViewById(R.id.tran_ll_layout);

        tvTitle = findViewById(R.id.settle_tv_title);
        tvContent = findViewById(R.id.settle_tv_content);
        tvInfo = findViewById(R.id.settle_tv_InfoNo);
        tvPlace = findViewById(R.id.settle_tv_place);
        tvTime = findViewById(R.id.settle_tv_time);
        tvMyInfo = findViewById(R.id.settle_tv_myInfoNo);
        img = findViewById(R.id.settle_img_pic);
        img.setImageResource(FssImgAdapter.pics[position]);

        tvTitle.setText(tranModel.getTranTitle());
        tvContent.setText(tranModel.getTranContent());
        tvInfo.setText(tvInfo.getText().toString()+tranModel.getInfoNo());

        tvTime.setText("发布时间:"+tranModel.getDateTime());
        tvMyInfo.setText(tvMyInfo.getText().toString()+ LoginViewModel.user.getInfoNo());
        //tvMoney.setText(tranModel.getTranMoney());
        tvMoney = new TextView(this);
        tvMoney.setText("金额:"+tranModel.getTranMoney());
        linearLayout.addView(tvMoney);
        linearLayout.removeView(tvPlace);
    }

    @Override
    public void initData() {
        showTranActivity = this;
        Intent intent = getIntent();
        position = intent.getIntExtra("position",0);
        tranModel =(TranModel) DashboardFragment.tranList.get(position);
    }

    @Override
    public void initClick() {
        btnToSettle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowTranActivity.this, SettleActivity.class);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });
        btnToTalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowTranActivity.this, ComActivity.class);
                intent.putExtra("position",position);
                intent.putExtra("friend",tranModel.getInfoNo());
                startActivity(intent);
            }
        });
        tvInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new XPopup.Builder(ShowTranActivity.this)
                        //.maxWidth(600)
                        .asCenterList("请选择一项联系方式", new String[]{"电话", "聊天"},
                                new OnSelectListener() {
                                    @Override
                                    public void onSelect(int position, String text) {
                                        Toast.makeText(ShowTranActivity.this, "选择："+text, Toast.LENGTH_SHORT).show();
                                        if (text.equals("电话"))
                                            toCall();
                                        else
                                            toCom();
                                    }
                                })
                        .show();
            }
        });
    }

    private void toCall(){
        if (ContextCompat.checkSelfPermission(ShowTranActivity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            // 已经获得权限，可以拨打电话
            // 获取用户输入的号码
            String phoneNumber = "1234567890";
            // 创建一个Intent
            Intent intent = new Intent(Intent.ACTION_CALL);
            // 将号码包装成一个Uri对象
            Uri data = Uri.parse("tel:" + phoneNumber);
            // 将Uri对象设置到Intent中
            intent.setData(data);
            // 启动该Intent
            startActivity(intent);
        } else {
            // 向用户请求权限
            ActivityCompat.requestPermissions(ShowTranActivity.this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    100
            );
        }
    }

    private void toCom(){
        Intent intent = new Intent(ShowTranActivity.this,ComActivity.class);
        intent.putExtra("friend",tranModel.getInfoNo());
        startActivity(intent);
    }
}