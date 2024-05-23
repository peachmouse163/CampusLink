package com.example.campuslink.ques;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.campuslink.FssBaseAdapter;
import com.example.campuslink.InitAll;
import com.example.campuslink.R;
import com.example.campuslink.link.ReThread;
import com.example.campuslink.login.LoginViewModel;
import com.example.campuslink.model.QuesToAnModel;
import com.example.campuslink.model.User;
import com.example.campuslink.model.VoluModel;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class QuesCreateActivity extends AppCompatActivity implements InitAll {

    private List<String> departmentList;

    private Button btnAdd;
    private TextView tvInfoNo,tvTime;
    private EditText etTitle,etContent;
    private Spinner spinner;

    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){//消息机制，用来在子线程中更新UI
        @Override
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 1003){
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<String>>() {}.getType();
                departmentList = gson.fromJson((String) msg.obj, listType);

                FssBaseAdapter adapter = new FssBaseAdapter(QuesCreateActivity.this,departmentList);
                spinner.setAdapter(adapter);
            }else if (msg.what == 1004){
                Toast.makeText(QuesCreateActivity.this, "add Success", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ques_create);

        HideTitle();

        ReThread thread = new ReThread(null,handler,"getDepartment");
        thread.setFlag(1003);
        thread.start();

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
        tvInfoNo = findViewById(R.id.cques_tv_infoNo);
        tvTime = findViewById(R.id.cques_tv_time);
        etTitle = findViewById(R.id.cques_et_title);
        etContent = findViewById(R.id.cques_et_content);
        spinner = findViewById(R.id.cques_sp_info);
        btnAdd = findViewById(R.id.cques_btn_add);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initData() {
        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
        tvTime.setText(tvTime.getText().toString()+ft.format(date));

        tvInfoNo.setText(tvInfoNo.getText().toString()+ LoginViewModel.user.getInfoNo());
    }

    @Override
    public void initClick() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(QuesCreateActivity.this,"onclick"+parent.getItemAtPosition(position).toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuesToAnModel ques = new QuesToAnModel();

                ques.setInfoNoA(LoginViewModel.user.getInfoNo()+"");
                //ques.setQuesTimeA(tvTime.getText().toString());
                Date date = new Date();
                SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
                ques.setQuesTimeA(ft.format(date));
                ques.setQuesTitle(etTitle.getText().toString());
                ques.setQuesContentA(etContent.getText().toString());
                ques.setInfoNoB(spinner.getSelectedItem().toString());

                JsonArray jsonArray = new JsonArray();
                jsonArray.add("0");
                jsonArray.add(""+LoginViewModel.user.getInfoNo());
                Gson gsonEnd = new Gson();
                ques.setQuesEnd(gsonEnd.toJson(jsonArray));

                HashMap<String,Object> datas = new HashMap<>();
                Gson gson = new Gson();
                datas.put("data",gson.toJson(ques));
                ReThread thread = new ReThread(datas,handler,"");
                thread.setFlag(1004);
                thread.start();
            }
        });
    }
}