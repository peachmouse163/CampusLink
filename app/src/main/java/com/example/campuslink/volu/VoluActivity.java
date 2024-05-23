package com.example.campuslink.volu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.campuslink.InitAll;
import com.example.campuslink.R;
import com.example.campuslink.link.MyThread;
import com.example.campuslink.login.LoginViewModel;
import com.example.campuslink.model.ThinModel;
import com.example.campuslink.model.User;
import com.example.campuslink.model.VoluModel;
import com.example.campuslink.ui.home.HomeFragment;
import com.example.campuslink.ui.teacher.TeacherHomeFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VoluActivity extends AppCompatActivity implements InitAll {

    private VoluModel voluModel;
    private TextView tvInfo,tvTitle,tvContent,tvDate,tvPlace,tvOnline,tvNum,tvStart,tvEnd;
    private ImageView imgTX,imgPoint;
    private Button btnSign;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what)//具体消息，具体显示
                {
                    case MyThread.VOLUSECC:
                        btnSign.setText("√");
                        Toast.makeText(VoluActivity.this,"报名成功",Toast.LENGTH_SHORT).show();
                        break;
                    default:break;
                }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volu);

        HideTitle();
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
        /*voluId，voluState*/

        imgPoint = findViewById(R.id.volu_img_attribute);
        tvContent =findViewById(R.id.volu_tv_content);
        tvPlace = findViewById(R.id.volu_tv_place);
        tvInfo = findViewById(R.id.volu_tv_name);
        tvDate = findViewById(R.id.volu_tv_time);
        tvOnline = findViewById(R.id.volu_tv_online);
        tvTitle = findViewById(R.id.volu_tv_titel);
        tvNum = findViewById(R.id.volu_tv_num);
        tvStart = findViewById(R.id.volu_tv_start);
        tvEnd = findViewById(R.id.volu_tv_end);

        btnSign = findViewById(R.id.volu_btn_sign);

        imgTX = findViewById(R.id.volu_img_TX);


    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        int thinNo = intent.getIntExtra("volu",0);

        if (LoginViewModel.user.getInfoIdentity() > 0){
            voluModel = (VoluModel) TeacherHomeFragment.dataListVolu.get(thinNo);
        }else {
            voluModel = (VoluModel) HomeFragment.dataListVolu.get(thinNo);

            if (HomeFragment.myVolus.size()>0){
                for (VoluModel volu :
                        HomeFragment.myVolus) {
                    if (volu.getVoluId().equals(voluModel.getVoluId()))
                        btnSign.setText("√");
                }
            }
        }



        switch (voluModel.getVoluPoint()){
            case "1":
                imgPoint.setImageResource(R.drawable.ic_num1);
                break;
            case "2":
                imgPoint.setImageResource(R.drawable.ic_num2);
                break;
            case "3":
                imgPoint.setImageResource(R.drawable.ic_num3);
                break;
            case "4":
                imgPoint.setImageResource(R.drawable.ic_num4);
                break;
            default:
                imgPoint.setImageResource(R.drawable.ic_num1);
                break;
        }

        tvContent.setText("\t"+voluModel.getVoluContent());
        tvPlace.setText("地点："+voluModel.getVoluPlace());
        tvInfo.setText(voluModel.getInfoNo()+",发布：");
        //
        tvInfo.setText("路老师,发布：");

        tvDate.setText("发布时间"+voluModel.getVoluTime());
        if (voluModel.getVoluOnline().equals("0"))
            tvOnline.setText("线下");
        else
            tvOnline.setText("线上");
        tvTitle.setText(voluModel.getVoluTitle());
        imgTX.setImageResource(R.drawable.ic_hello);
        tvNum.setText("所需人数"+voluModel.getVoluNum());
        tvStart.setText("开始时间"+voluModel.getVoluStart());
        tvEnd.setText("结束时间"+voluModel.getVoluEnd());

        if (LoginViewModel.user.getInfoIdentity() != 0){
            btnSign.setText("仅查看");
        }

    }

    @Override
    public void initClick() {
        if (LoginViewModel.user.getInfoIdentity() != 0)
            return;

        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!btnSign.getText().toString().equals("√")){
                    //String voluId, String voluTitle, String voluTime, String infoNo, String voluNum, String voluOnline
                    // , String voluPlace, String voluContent, String voluStart, String voluEnd, String voluPoint,String voluState
                    MyThread myThread = new MyThread();
                    myThread.setMod(myThread.VOLUSIGN);
                    HashMap<String, String> datas = new HashMap<>();
                    datas.put("mod", "updata");
                    datas.put("voluId", voluModel.getVoluId());
                    Gson gson = new Gson();
                    List<String> list = gson.fromJson(voluModel.getVoluState(),new TypeToken<List<String>>(){}.getType());
                    list.add(LoginViewModel.user.getInfoNo()+"");
                    String voluState = gson.toJson(list);
                    datas.put("voluState", voluState);
                    myThread.setDatas(datas);
                    myThread.setHandler(handler);
                    myThread.start();
                }else
                    Toast.makeText(VoluActivity.this, "您已经报名成功。", Toast.LENGTH_SHORT).show();
            }
        });
    }
}