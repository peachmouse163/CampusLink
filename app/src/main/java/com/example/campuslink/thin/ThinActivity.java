package com.example.campuslink.thin;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.campuslink.InitAll;
import com.example.campuslink.R;
import com.example.campuslink.communicate.ComActivity;
import com.example.campuslink.model.ThinModel;
import com.example.campuslink.tran.ShowTranActivity;
import com.example.campuslink.ui.home.HomeFragment;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;

public class ThinActivity extends AppCompatActivity implements InitAll {

    private ThinModel thinModel;
    private TextView tvInfo,tvTitle,tvContent,tvDate,tvPlace,tvPhone,tvAttr;
    private ImageView imgPic,imgTX;
    private Button btnCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thin);

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
        tvAttr = findViewById(R.id.thin_img_attribute);
        tvContent =findViewById(R.id.thin_tv_content);
        tvPlace = findViewById(R.id.thin_tv_place);
        tvInfo = findViewById(R.id.thin_tv_name);
        tvDate = findViewById(R.id.thin_tv_time);
        tvPhone = findViewById(R.id.thin_tv_phone);
        tvTitle = findViewById(R.id.thin_tv_titel);

        imgPic = findViewById(R.id.thin_img_pic);
        imgTX = findViewById(R.id.thin_img_TX);

        if (thinModel.getThinAttribute().equals("0"))
            tvAttr.setText("丢");
        else
            tvAttr.setText("捡");

        tvContent.setText(thinModel.getThinContent());
        tvPlace.setText(thinModel.getThinPlace());
        tvInfo.setText(thinModel.getInfoNo());
        tvDate.setText(thinModel.getThinDate());
        tvPhone.setText(thinModel.getThinPhone());
        tvTitle.setText(thinModel.getThinTitle());
        imgTX.setImageResource(R.drawable.ic_hello);
        imgPic.setImageResource(R.drawable.ic_pic);

        btnCall = findViewById(R.id.stran_btn_toTalk);

    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        int thinNo = intent.getIntExtra("thin",0);

        thinModel = (ThinModel) HomeFragment.dataListThin.get(thinNo);

    }

    @Override
    public void initClick() {
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new XPopup.Builder(ThinActivity.this)
                        //.maxWidth(600)
                        .asCenterList("请选择一项联系方式", new String[]{"电话", "聊天"},
                                new OnSelectListener() {
                                    @Override
                                    public void onSelect(int position, String text) {
                                        Toast.makeText(ThinActivity.this, "选择："+text, Toast.LENGTH_SHORT).show();
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
        if (ContextCompat.checkSelfPermission(ThinActivity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
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
            ActivityCompat.requestPermissions(ThinActivity.this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    100
            );
        }
    }

    private void toCom(){
        Intent intent = new Intent(ThinActivity.this, ComActivity.class);
        intent.putExtra("friend",thinModel.getInfoNo());
        startActivity(intent);
    }
}