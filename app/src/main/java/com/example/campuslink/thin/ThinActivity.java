package com.example.campuslink.thin;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.campuslink.InitAll;
import com.example.campuslink.R;
import com.example.campuslink.model.ThinModel;
import com.example.campuslink.ui.home.HomeFragment;

public class ThinActivity extends AppCompatActivity implements InitAll {

    private ThinModel thinModel;
    private TextView tvInfo,tvTitle,tvContent,tvDate,tvPlace,tvPhone,tvAttr;
    private ImageView imgPic,imgTX;

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
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        int thinNo = intent.getIntExtra("thin",0);

        thinModel = (ThinModel) HomeFragment.dataListThin.get(thinNo);

    }

    @Override
    public void initClick() {

    }
}