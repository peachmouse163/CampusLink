package com.example.campuslink.messages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.example.campuslink.model.CollectModel;
import com.example.campuslink.model.Login;
import com.example.campuslink.model.MessModel;
import com.example.campuslink.model.Message;
import com.example.campuslink.model.Question;
import com.example.campuslink.model.SignModel;
import com.example.campuslink.ui.message.MessageFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class ShowMessagesActivity extends AppCompatActivity implements InitAll {

    private final String TAG = "ShowMessagesActivity";
    private int messagePosition;
    private Message message;
    private MessModel messModel;
    private CollectModel collectModel;
    private SignModel signModel;
    private List<View> viewList;
    private List<String> readList;
    private List<Question> questionList,personList,existList,nonExistList;
    private Gson gson;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull android.os.Message msg) {
            //super.handleMessage(msg);
            switch (msg.what) {
                case 1015:
                    Toast.makeText(ShowMessagesActivity.this, "已确认查看", Toast.LENGTH_SHORT).show();
                    btnSub.setText("√");
                    break;
                case 1016:
                    personList = gson.fromJson((String)msg.obj,new TypeToken<List<Question>>(){}.getType() );
                    Log.d(TAG, "handleMessage: personList:"+personList.size());
                    initView();
                    break;
                case 1017:
                    //更新coll表和对应的pers表
                    //无返回结果
                    finish();
                    break;
            }
        }
    };

    private TextView tvTitle, tvContent, tvTime, tvDepartment;
    private Button btnSub;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_messages);

        HideTitle();
        initData();
        initClick();
        //initView();


    }

    @Override
    public void HideTitle() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    @Override
    public void initView() {
        linearLayout = findViewById(R.id.message_ll_questions);

        tvTitle = findViewById(R.id.lmt_tv_title);
        tvContent = findViewById(R.id.lmt_tv_content);
        tvTime = findViewById(R.id.lmt_tv_time);
        tvDepartment = findViewById(R.id.lmt_tv_department);

        String title = "", content = "", time = "", department = "";
        if (message instanceof MessModel) {
            messModel = (MessModel) message;
            title = messModel.getMessTitle();
            content = messModel.getMessContent();
            time = messModel.getMessTime();
            department = messModel.getMessDepartment();
            readList = messModel.getMessList();
            if (!readList.contains(LoginViewModel.user.getInfoNo() + "")) {
                btnSub.setText("√");
                btnSub.setOnClickListener(null);
            }
        }
        if (message instanceof CollectModel) {
            collectModel = (CollectModel) message;
            title = collectModel.getCollTitle();
            content = collectModel.getCollContent();
            time = collectModel.getCollTime().toString();
            department = collectModel.getCollDepartment();

            questionList = collectModel.getCollQuestions();
            Log.d(TAG, "initView: " + questionList.size());

            for (Question ques :
                    questionList) {
                if (!historyQues(ques)) {
                    //不存在,则添加问题
                    switch (ques.getType()) {
                        case "单选题":
                            //break;
                        case "多选题":
                            //break;
                        case "时间题":
                            //break;
                        case "填空题":
                            EditText editText = new EditText(this);
                            editText.setHint(ques.getQuestion());
                            editText.setTag(ques.getQuestion());
                            linearLayout.addView(editText);
                            viewList.add(editText);
                            break;
                    }
                }else {
                    EditText editText = new EditText(this);
                    editText.setText(ques.getAnswer());
                    editText.setTag(ques.getQuestion());
                    linearLayout.addView(editText);
                    viewList.add(editText);
                }
            }
            readList = collectModel.getCollList();
            if (!readList.contains(LoginViewModel.user.getInfoNo()+"")){
                btnSub.setText("√");
                btnSub.setOnClickListener(null);
            }
        }
        if (message instanceof SignModel) {
            signModel = (SignModel) message;
            title = signModel.getSignTitle();
            content = signModel.getSignContent();
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            time = signModel.getSignTime();
            department = signModel.getSignDepartment();

            questionList = signModel.getSignQuestions();
            Log.d(TAG, "initView: " + questionList.size());

            for (Question ques :
                    questionList) {
                if (!historyQues(ques)) {
                    //不存在,则添加问题
                    switch (ques.getType()) {
                        case "单选题":
                            //break;
                        case "多选题":
                            //break;
                        case "时间题":
                            //break;
                        case "填空题":
                            EditText editText = new EditText(this);
                            editText.setHint(ques.getQuestion());
                            editText.setTag(ques.getQuestion());
                            linearLayout.addView(editText);
                            viewList.add(editText);
                            break;
                    }
                }
            }
        }

        tvContent.setText(content);
        tvTitle.setText(title);
        tvDepartment.setText(department);
        tvTime.setText(time);

    }

    private boolean historyQues(Question question) {
        for (Question person:
             personList) {
            if (person.equals(question)){
                existList.add(person);
                question.setAnswer(person.getAnswer());
                return true;
            }
        }
        return false;
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        messagePosition = intent.getIntExtra("messageid", 0);
        message = MessageFragment.messages.get(messagePosition);

        readList = new ArrayList<>();
        personList = new ArrayList<>();
        existList = new ArrayList<>();
        nonExistList = new ArrayList<>();
        viewList = new ArrayList<>();
        gson = new Gson();

        HashMap<String,Object> datas = new HashMap<String,Object>();
        datas.put("data",LoginViewModel.user.getInfoNo());
        new ReThread(datas,handler,1016).start();
    }

    @Override
    public void initClick() {
        btnSub = findViewById(R.id.message_btn_sub);
        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> datas = new HashMap<>();

                if (message instanceof MessModel) {
                    messModel.getMessList().remove(LoginViewModel.user.getInfoNo() + "");
                    datas.put("data", gson.toJson(messModel));
                    new ReThread(datas, handler, 1015).start();
                }
                if (message instanceof CollectModel){
                    initColl();
                    collectModel.getCollList().remove(LoginViewModel.user.getInfoNo() + "");
                    datas.put("data",gson.toJson(collectModel));
                    datas.put("info",""+LoginViewModel.user.getInfoNo());
                    Log.d(TAG, "onClick: "+gson.toJson(collectModel));
                    new ReThread(datas,handler,1017).start();
                }


            }
        });
    }

    private void initColl() {

        for (View view :
                viewList) {
            for (Question question :
                    questionList) {
                if (((String)view.getTag()).equals(question.getQuestion())){
                    //不同组件，不同获取数据方法，当前默认editview
                    /*if (view instanceof EditText){

                    }*/
                    question.setAnswer(((EditText)view).getText().toString());
                }
            }
        }
        for (Question exist :
                existList) {
            for (Question question :
                    questionList) {
                if (question.getQuestion().equals(exist.getQuestion())) {
                    question.setAnswer(exist.getAnswer());
                }
            }
        }

    }
}