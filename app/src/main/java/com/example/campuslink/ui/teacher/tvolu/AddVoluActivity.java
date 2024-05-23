package com.example.campuslink.ui.teacher.tvolu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.campuslink.InitAll;
import com.example.campuslink.R;
import com.example.campuslink.link.ReThread;
import com.example.campuslink.login.LoginViewModel;
import com.example.campuslink.model.Login;
import com.example.campuslink.model.User;
import com.example.campuslink.model.VoluModel;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddVoluActivity extends AppCompatActivity implements InitAll {

    private TextView tvInfoName,tvTime;
    private ImageView imgPoint;
    private EditText etTitle,etContent,etPlace;
    private Spinner spNumber,spPoint;
    private RadioGroup rgLine;
    private Button btnSub,btnStart,btnEnd;

    private DatePickerDialog.OnDateSetListener startListener,endListener;
    private String point,online,numVolu;
    private String infoName,infoTime;
    private String startDate,endDate;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 1022){
                Toast.makeText(AddVoluActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_volu);

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
        tvInfoName = findViewById(R.id.tvolu_tv_name);
        tvTime = findViewById(R.id.tvolu_tv_time);
        imgPoint = findViewById(R.id.tvolu_img_attribute);
        etTitle = findViewById(R.id.tvolu_et_titel);
        etContent = findViewById(R.id.tvolu_et_content);
        etPlace = findViewById(R.id.tvolu_et_place);
        spNumber = findViewById(R.id.tvolu_sp_number);
        spPoint = findViewById(R.id.tvolu_sp_point);
        rgLine = findViewById(R.id.tvolu_rg_online);
        btnSub = findViewById(R.id.volu_btn_sign);
        btnStart = findViewById(R.id.volu_btn_start);
        btnEnd = findViewById(R.id.volu_btn_end);

        tvInfoName.setText(infoName);
        tvTime.setText(infoTime);
        imgPoint.setImageResource(R.drawable.ic_num1);

        startListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                startDate = year+"-"+month+"-"+dayOfMonth+" 12:30:00";
                btnStart.setText(startDate);
            }
        };
        endListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                endDate = year+"-"+month+"-"+dayOfMonth+" 12:30:00";
                btnEnd.setText(endDate);
            }
        };

    }

    @Override
    public void initData() {
        //infoName = User.infoName;
        infoName = LoginViewModel.user.getInfoName();

        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
        infoTime = ft.format(new Date());
    }

    @Override
    public void initClick() {
        spPoint.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                point = parent.getItemAtPosition(position).toString();
                switch (point){
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
                    default:break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spNumber.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                numVolu = parent.getItemAtPosition(position).toString();
                Toast.makeText(AddVoluActivity.this, ""+numVolu, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        rgLine.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.tvolu_rb_online:
                        online = "1";
                        Toast.makeText(AddVoluActivity.this, "线上", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.tvolu_rb_offline:
                        online = "0";
                        Toast.makeText(AddVoluActivity.this, "线下", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                VoluModel voluModel = new VoluModel();
                try {
                    voluModel.setVoluTitle(etTitle.getText().toString());
                    Date dNow = new Date();
                    SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    voluModel.setVoluTime(ft.format(dNow));
                    voluModel.setInfoNo(LoginViewModel.user.getInfoNo() + "");
                    voluModel.setVoluNum(numVolu);
                    voluModel.setVoluOnline(online);
                    voluModel.setVoluPlace(etPlace.getText().toString());
                    voluModel.setVoluContent(etContent.getText().toString());
                    voluModel.setVoluStart(startDate);
                    voluModel.setVoluEnd(endDate);
                    voluModel.setVoluPoint(point);
                    List<String> list = new ArrayList<>();
                    list.add("0");
                    voluModel.setVoluState(gson.toJson(list));

                    HashMap<String, Object> datas = new HashMap<>();
                    datas.put("data", gson.toJson(voluModel));
                    new ReThread(datas, handler, 1022).start();
                } catch (NullPointerException ignored) {
                    ;
                }
            }
        });
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddVoluActivity.this,startListener,year, month, day);
                datePickerDialog.show();
            }
        });
        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddVoluActivity.this,endListener,year, month, day);
                datePickerDialog.show();
            }
        });
    }
}