package com.example.campuslink.tran;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.campuslink.InitAll;
import com.example.campuslink.R;
import com.example.campuslink.link.MyThread;

public class TranResultActivity extends AppCompatActivity implements InitAll {

    private boolean result;
    private MyThread fiveSecondThread;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if (msg.what == MyThread.fiveSecond){
                finish();
                SettleActivity.settleActivity.finish();
                ShowTranActivity.showTranActivity.finish();
            }
            return true;
        }
    });

    private TextView tvResult;
    private ImageView imgResult;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tran_result);

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
        tvResult = findViewById(R.id.rtran_tv_result);
        imgResult = findViewById(R.id.rtran_img_pic);

        if (result){
            tvResult.setText("支付成功，即将回到首页。");
            imgResult.setImageResource(R.drawable.ic_result_true);
        }else {
            tvResult.setText("支付失败，如有问题请反馈。\n即将回到首页。");
            imgResult.setImageResource(R.drawable.ic_result_false);
        }

        fiveSecondThread = new MyThread();
        fiveSecondThread.setMod(fiveSecondThread.SECOND5);
        fiveSecondThread.setHandler(handler);
        fiveSecondThread.start();
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        result = intent.getBooleanExtra("result",true);
    }

    @Override
    public void initClick() {

    }
}