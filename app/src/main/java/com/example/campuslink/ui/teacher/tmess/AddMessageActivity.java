package com.example.campuslink.ui.teacher.tmess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.campuslink.InitAll;
import com.example.campuslink.R;
import com.example.campuslink.link.ReThread;
import com.example.campuslink.login.LoginViewModel;
import com.example.campuslink.model.CollectModel;
import com.example.campuslink.model.MessModel;
import com.example.campuslink.model.Question;
import com.example.campuslink.model.SignModel;
import com.example.campuslink.model.User;
import com.example.campuslink.thin.CreateThinActivity;
import com.example.campuslink.tools.ImagePickerActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnInputConfirmListener;
import com.lxj.xpopup.interfaces.OnSelectListener;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class AddMessageActivity extends AppCompatActivity implements InitAll {

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1010:
                    AddMessageActivity.this.finish();
                    Toast.makeText(AddMessageActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
                    break;
                case 1012:
                    Type listType = new TypeToken<ArrayList<User>>() {}.getType();
                    userDatas = gson.fromJson((String) msg.obj, listType);
                    for (User user :
                            userDatas) {
                        departmentList.add(user.getInfoDepartment());
                    }
                    break;
            }
        }
    };
    private List<Question> questions;
    private Gson gson;
    //存所有用户的数据，用于检索部门，存infoNo
    private List<User> userDatas;
    //检索部门，用于选择范围的院系范围
    private HashSet<String> departmentList;
    //scope的暂存
    private List<String> quesScope;
    private String title = "";

    private EditText etTitle,etContent;
    private TextView tvDepartment,tvTime,tvScope;
    private Button btnScope,btnSub;
    private RadioGroup radioGroup;
    private Spinner spinner;
    private LinearLayout layout,linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_message);


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
        etTitle = findViewById(R.id.amess_tv_title);
        etContent = findViewById(R.id.amess_tv_content);
        tvDepartment = findViewById(R.id.amess_tv_department);
        tvTime = findViewById(R.id.amess_tv_time);
        tvScope = findViewById(R.id.amess_tv_scope);
        btnScope = findViewById(R.id.amess_btn_scope);
        btnSub = findViewById(R.id.amess_btn_sub);
        spinner = findViewById(R.id.amess_spinner_add);
        radioGroup = findViewById(R.id.amess_rg_define);
        linearLayout = findViewById(R.id.amess_ll_layout);
        layout = findViewById(R.id.amess_ll_questions);

        tvDepartment.setText(tvDepartment.getText().toString()+ LoginViewModel.user.getInfoDepartment());

        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
        tvTime.setText(tvTime.getText().toString()+ft.format(new Date()));

        //tvScope.setText("12001259-12001260");
    }

    @Override
    public void initData() {
        questions = new ArrayList<>();
        gson = new Gson();
        new ReThread(null,handler,1012).start();
        quesScope = new ArrayList<>();
        departmentList = new HashSet<>();
    }

    @Override
    public void initClick() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.amess_rb_mess)
                    layout.removeView(spinner);
                else{
                    if (!containsView(layout,spinner)){
                        layout.addView(spinner,0);
                    }
                }
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = new TextView(AddMessageActivity.this);
                switch (parent.getItemAtPosition(position).toString()){
                    case "填空题":
                    case "单选题":
                    case "多选题":
                    case "时间题":
                        TextView textView4 = new TextView(AddMessageActivity.this);
                        new XPopup.Builder(AddMessageActivity.this).asInputConfirm("标题", "请输入。",
                                        new OnInputConfirmListener() {
                                            @Override
                                            public void onConfirm(String text) {
                                                textView4.setText(text);
                                                linearLayout.addView(textView4);
                                                questions.add(new Question(parent.getItemAtPosition(position).toString(),text));
                                            }
                                        })
                                .show();
                        break;
                    default:
                        textView.setText("请输入"+parent.getItemAtPosition(position).toString()+":");
                        linearLayout.addView(textView);
                        questions.add(new Question("填空题",textView.getText().toString()));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSub.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onClick(View v) {
                //遍历linnerLayoutQuestion布局中的所有子组件，将edit中的文本获取出来。
                //Toast.makeText(AddMessageActivity.this, "本次通知内含问题"+countNumber(linearLayout)+"个", Toast.LENGTH_SHORT).show();
                RadioButton radioButton =  findViewById(radioGroup.getCheckedRadioButtonId());

                com.example.campuslink.model.Message message = null;
                String mod = radioButton.getTag().toString();
                switch (mod){
                    case "mess":
                        message = new MessModel();
                        ((MessModel)message).setMessTitle(etTitle.getText().toString());
                        ((MessModel)message).setMessContent(etContent.getText().toString());
                        ((MessModel)message).setMessDepartment(LoginViewModel.user.getInfoDepartment());
                        ((MessModel)message).setMessTime(new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss").format(new Date()));
                        ((MessModel)message).setMessScope(quesScope);
                        ((MessModel)message).setMessList(quesScope);//该通知为仅查看，所以设为所有，有查看的就删掉，便于教师端看未读名单
                        break;
                    case "coll":
                        message = new CollectModel();
                        ((CollectModel)message).setCollQuestions(questions);
                        ((CollectModel)message).setCollTitle(etTitle.getText().toString());
                        ((CollectModel)message).setCollContent(etContent.getText().toString());
                        ((CollectModel)message).setCollDepartment(LoginViewModel.user.getInfoDepartment());
                        ((CollectModel)message).setCollTime(new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss").format(new Date()));
                        ((CollectModel)message).setCollScope(quesScope);
                        ((CollectModel)message).setCollList(quesScope);//该通知为收集信息，所以设为所有，有提交的就删掉，便于教师端看未读名单
                        break;
                    case "sign":
                        message = new SignModel();
                        ((SignModel)message).setSignQuestions(questions);
                        ((SignModel)message).setSignTitle(etTitle.getText().toString());
                        ((SignModel)message).setSignContent(etContent.getText().toString());
                        ((SignModel)message).setSignDepartment(LoginViewModel.user.getInfoDepartment());
                        ((SignModel)message).setSignTime(new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss").format(new Date()));
                        ((SignModel)message).setSignScope(quesScope);
                        ((SignModel)message).setSignList(null);//报名类，只看报名人
                        break;
                }

                HashMap<String,Object> datas = new HashMap<>();
                datas.put("type",mod);
                //json
                datas.put("data",gson.toJson(message));
                new ReThread(datas,handler,1010).start();
            }
        });

        btnScope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 这种弹窗从 1.0.0版本开始实现了优雅的手势交互和智能嵌套滚动
                new XPopup.Builder(AddMessageActivity.this)
                        .asBottomList("请选择范围", departmentList.toArray(new String[0]),
                                new OnSelectListener() {
                                    @Override
                                    public void onSelect(int position, String text) {
                                        btnScope.setText(text);
                                        for (User user :
                                                userDatas) {
                                            if (user.getInfoDepartment().equals(text) && user.getInfoIdentity() == 0) {
                                                quesScope.add(user.getInfoNo()+"");
                                            }
                                        }
                                    }
                                })
                        .show();
            }
        });
    }

    private int countNumber(ViewGroup viewGroup){
        int count = 0;

        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);
            if (child instanceof EditText){
                count++;
            }
            if (child instanceof ViewGroup){
                count += countNumber((ViewGroup) child);
            }
        }

        return count;
    }

    public boolean containsView(ViewGroup root, View view) {
        for (int i = 0; i < root.getChildCount(); i++) {
            View child = root.getChildAt(i);
            if (child == view) {
                return true;
            }
            if (child instanceof ViewGroup) {
                ViewGroup group = (ViewGroup) child;
                if (containsView(group, view)) {
                    return true;
                }
            }
        }
        return false;
    }

}