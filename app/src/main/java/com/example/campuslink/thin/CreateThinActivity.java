package com.example.campuslink.thin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.campuslink.InitAll;
import com.example.campuslink.R;
import com.example.campuslink.link.ReThread;
import com.example.campuslink.login.LoginViewModel;
import com.example.campuslink.model.ThinModel;
import com.example.campuslink.model.User;
import com.example.campuslink.tools.ImagePickerActivity;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class CreateThinActivity extends AppCompatActivity implements InitAll {

    public static Uri fileUri;

    private RadioGroup radioGroup;
    private TextView tvInfoNo;
    private EditText etTitle,etContent,etPlace,etPhone;
    private Button btnSub;
    private ImageButton ibtnPic;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.what == 1021){
                Toast.makeText(CreateThinActivity.this,"发布成功",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_thin);

        HideTitle();
        initData();
        initView();
        initClick();

    }

    public void onImagePicker(View view){
        startActivity(new Intent(CreateThinActivity.this,ImagePickerActivity.class));
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
        etTitle = findViewById(R.id.cthin_et_title);
        etContent = findViewById(R.id.cthin_et_content);
        etPlace = findViewById(R.id.cthin_et_place);
        etPhone = findViewById(R.id.cthin_et_iphone);
        tvInfoNo = findViewById(R.id.cthin_tv_infoNo);
        ibtnPic = findViewById(R.id.cthin_ibtn_pic);
        radioGroup = findViewById(R.id.cthin_rg_attribute);

        String info = tvInfoNo.getText().toString()+ LoginViewModel.user.getInfoNo();
        tvInfoNo.setText(info);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (fileUri!=null){
            Bitmap bitmap = null;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(fileUri));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            ibtnPic.setImageBitmap(bitmap);
        }
    }

    @Override
    public void initData() {

    }

    public void onSub(View view){
        HashMap<String,Object> datas = new HashMap<>();
        Gson gson = new Gson();
        ThinModel thinModel = new ThinModel();
        //(thinTitle,thinDate,infoNo,thinAttribute,thinPlace,thinPhone,thinContent,thinState)
        thinModel.setThinTitle(etTitle.getText().toString());
        Date dNow = new Date( );
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
        thinModel.setThinDate(ft.format(dNow));
        thinModel.setInfoNo(LoginViewModel.user.getInfoNo()+"");
        RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
        thinModel.setThinAttribute(radioButton.getText().toString());
        thinModel.setThinPlace(etPlace.getText().toString());
        thinModel.setThinPhone(etPhone.getText().toString());
        thinModel.setThinContent(etContent.getText().toString());
        thinModel.setThinState("0");
        datas.put("data",gson.toJson(thinModel));
        new ReThread(datas,handler,1021).start();
    }

    @Override
    public void initClick() {

    }
}