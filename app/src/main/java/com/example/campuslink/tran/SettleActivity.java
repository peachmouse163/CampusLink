package com.example.campuslink.tran;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.campuslink.FssImgAdapter;
import com.example.campuslink.InitAll;
import com.example.campuslink.R;
import com.example.campuslink.login.LoginViewModel;
import com.example.campuslink.model.TranModel;
import com.example.campuslink.model.User;
import com.example.campuslink.ui.dashboard.DashboardFragment;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SettleActivity extends AppCompatActivity implements InitAll {

    private static boolean flag = true;
    private int position;
    private TranModel tranModel;
    public static Activity settleActivity;

    private TextView tvTitle,tvContent,tvInfo,tvPlace,tvTime,tvMyInfo,tvMoney;
    private Button btnBuy;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settle);


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
        tvTitle = findViewById(R.id.settle_tv_title);
        tvContent = findViewById(R.id.settle_tv_content);
        tvInfo = findViewById(R.id.settle_tv_InfoNo);
        tvPlace = findViewById(R.id.settle_tv_place);
        tvTime = findViewById(R.id.settle_tv_time);
        tvMyInfo = findViewById(R.id.settle_tv_myInfoNo);
        tvMoney = findViewById(R.id.settle_tv_money);
        btnBuy = findViewById(R.id.settle_btn_pay);
        img = findViewById(R.id.settle_img_pic);
        img.setImageResource(FssImgAdapter.pics[position]);

        tvTitle.setText(tranModel.getTranTitle());
        tvContent.setText(tranModel.getTranContent());
        tvInfo.setText(tvInfo.getText().toString()+tranModel.getInfoNo());
        //应存储本地数据——收货地址
        tvPlace.setText(tvPlace.getText().toString()+"4号宿舍楼下");

        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
        tvTime.setText(ft.format(new Date()));

        tvMyInfo.setText(tvMyInfo.getText().toString()+LoginViewModel.user.getInfoNo());
        tvMoney.setText("金额:"+tranModel.getTranMoney());

    }

    @Override
    public void initData() {
        settleActivity = this;

        Intent intent = getIntent();
        position = intent.getIntExtra("position",0);
        tranModel =(TranModel) DashboardFragment.tranList.get(position);
    }

    @Override
    public void initClick() {
        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettleActivity.this, TranResultActivity.class);
                intent.putExtra("result",flag);
                flag = false;
                startActivity(intent);
            }
        });
    }
}