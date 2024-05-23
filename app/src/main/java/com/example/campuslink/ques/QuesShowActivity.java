package com.example.campuslink.ques;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.campuslink.InitAll;
import com.example.campuslink.R;
import com.example.campuslink.link.ReThread;
import com.example.campuslink.login.LoginViewModel;
import com.example.campuslink.model.QuesToAnModel;
import com.example.campuslink.model.User;
import com.example.campuslink.ui.notifications.NotificationsFragment;
import com.example.campuslink.ui.teacher.TeacherMessageFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class QuesShowActivity extends AppCompatActivity implements InitAll {

    private final String TAG = "QuesShowActivity";

    private int position;
    private String[] departments;
    private List<String> studentList;

    private QuesToAnModel quesModel;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1003:
                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<String>>() {}.getType();
                    List<String> departmentList = gson.fromJson((String) msg.obj, listType);
                    departmentList.remove(LoginViewModel.user.getInfoDepartment());
                    departments = departmentList.toArray(new String[0]);
                    break;
                case 1006:
                    Toast.makeText(QuesShowActivity.this, "已传递", Toast.LENGTH_SHORT).show();
                    break;
                case 1007:
                    Toast.makeText(QuesShowActivity.this, "已回复", Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    };

    private TextView tvTitle,tvContentA,tvContentB,tvInfoNoA,tvInfoNoB,tvTimeA,tvTimeB;
    private Button btnState,btnRe,btnChange;
    private LinearLayout linearLayout;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ques_show);

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
        tvTitle = findViewById(R.id.sques_et_title);
        tvContentA = findViewById(R.id.sques_tv_content);
        tvContentB = findViewById(R.id.sques_tv_contentB);
        tvInfoNoA = findViewById(R.id.sques_tv_infoNo);
        tvInfoNoB = findViewById(R.id.sques_tv_infoNoB);
        tvTimeA = findViewById(R.id.sques_tv_time);
        tvTimeB = findViewById(R.id.sques_tv_timeB);
        btnState = findViewById(R.id.sques_tv_state);

        linearLayout = findViewById(R.id.sques_linearLayout_reply);

        tvTitle.setText(quesModel.getQuesTitle());
        tvInfoNoA.setText(quesModel.getInfoNoA());
        tvTimeA.setText(quesModel.getQuesTimeA());
        tvContentA.setText(quesModel.getQuesContentA());

        btnState.setText("等待回复中");

        String list = quesModel.getQuesEnd();
        Gson gson = new Gson();
        Type listType = new TypeToken<List<String>>() {}.getType();
        studentList = gson.fromJson(list,listType);

        if (studentList.get(0).equals("end")){
            btnState.setText("已完成");
            tvInfoNoB.setText(quesModel.getInfoNoB());
            tvContentB.setText(quesModel.getQuesContentB());
            tvTimeB.setText(quesModel.getQuesTimeB());
        }else {
            btnState.setText(""+(studentList.size()-1));

            tvInfoNoB.setText(quesModel.getInfoNoB());
            tvContentB.setText("尚未回复...");
            tvTimeB.setText("");
        }

        //工号转部门名
        tvInfoNoB.setText(LoginViewModel.user.getInfoDepartment());

        if (LoginViewModel.user.getInfoIdentity() > 0){
            btnState.setBackgroundResource(R.drawable.shape_button);
            initBtn(studentList,gson);
            if (tvContentB.getText().toString().equals("尚未回复...")){
                int number = list.split(";").length;
                btnState.setText("有"+number+"位同学\n等待您的回复");
                linearLayout.removeView(tvContentB);
                editText = new EditText(QuesShowActivity.this);
                linearLayout.addView(editText,1);
                tvInfoNoB.setText("当前部门:"+LoginViewModel.user.getInfoDepartment());

                SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd \n hh:mm:ss");
                tvTimeB.setText("当前时间:\n"+ft.format(new Date()));

                linearLayout.addView(btnChange);
                linearLayout.addView(btnRe);
            }else {
                btnState.setText("感谢您的回复");
            }
        }
    }

    private void initBtn(List<String> studentList,Gson gson) {
        btnChange = new Button(QuesShowActivity.this);
        btnRe = new Button(QuesShowActivity.this);

        btnRe.setText("确认");
        btnChange.setText("转递");

        btnRe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //补全ques
                String content = editText.getText().toString();

                if (content.equals("")){
                    Toast.makeText(QuesShowActivity.this, "回复内容未填写", Toast.LENGTH_SHORT).show();
                    return;
                }
                quesModel.setQuesContentB(content);
                Date date = new Date();
                SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
                quesModel.setQuesTimeB(ft.format(date));
                studentList.set(0,"end");
                quesModel.setQuesEnd(gson.toJson(studentList));

                HashMap<String,Object> datas = new HashMap<>();
                datas.put("ques",quesModel);
                new ReThread(datas,handler,1007).start();
            }
        });
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new XPopup.Builder(QuesShowActivity.this)
                        //.maxWidth(600)
                        .asCenterList("请选择其他部门", departments,
                                new OnSelectListener() {
                                    @Override
                                    public void onSelect(int position, String text) {
                                        //修改ques
                                        HashMap<String,Object> datas = new HashMap<>();
                                        QuesToAnModel ques =quesModel;
                                        ques.setInfoNoB(text);
                                        Gson gson = new Gson();
                                        datas.put("department",gson.toJson(ques));
                                        new ReThread(datas,handler,1006).start();
                                    }
                                })
                        .show();
            }
        });
    }

    @Override
    public void initData() {
        new ReThread(null,handler,1003).start();

        if (LoginViewModel.user.getInfoIdentity() > 0){
            Intent intent = getIntent();
            position = intent.getIntExtra("messageid",0);
            quesModel = (QuesToAnModel) TeacherMessageFragment.messages.get(position);
            return;
        }
        Intent intent = getIntent();
        position = intent.getIntExtra("position",1);
        quesModel = NotificationsFragment.adapterList.get(position);

    }

    @Override
    public void initClick() {
        btnState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!studentList.get(0).equals("end")){
                    String infoNo = LoginViewModel.user.getInfoNo()+"";
                    if (!studentList.contains(infoNo)){
                        studentList.add(infoNo);
                        Gson gson = new Gson();

                        HashMap<String,Object> datas = new HashMap<>();
                        datas.put("id",quesModel.getQuesId());

                        Log.d(TAG, "onClick: "+quesModel.getQuesId());

                        datas.put("end",gson.toJson(studentList));
                        new ReThread(datas,handler,1009).start();

                        int count = Integer.parseInt(btnState.getText().toString());
                        btnState.setText(""+(count+1));
                        Toast.makeText(QuesShowActivity.this, "成功增加一份关注度", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(QuesShowActivity.this, "您已经增加了一份关注度，请勿重复增加。", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}