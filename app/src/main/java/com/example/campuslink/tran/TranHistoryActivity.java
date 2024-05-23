package com.example.campuslink.tran;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.campuslink.R;
import com.example.campuslink.adapter.FssMessAdapter;
import com.example.campuslink.link.ReThread;
import com.example.campuslink.login.LoginViewModel;
import com.example.campuslink.model.MessModel;
import com.example.campuslink.model.Preview;
import com.example.campuslink.model.TranModel;
import com.example.campuslink.model.VoluModel;
import com.example.campuslink.volu.VoluHistoryActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class TranHistoryActivity extends AppCompatActivity {

    private final String TAG = "";

    private List<Preview> tranList;
    private List<com.example.campuslink.model.Message> messageList;
    private HashMap<String,Object> datas;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 1002){
                if (((String)msg.obj).length()>0){
                    Gson gson = new Gson();
                    Type userListType = new TypeToken<ArrayList<TranModel>>(){}.getType();
                    tranList = gson.fromJson((String)msg.obj,userListType);
                    Log.d(TAG, "handleMessage: tran-number:"+tranList.size());
                    initData();
                }else
                    Toast.makeText(TranHistoryActivity.this, "没有交易记录", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void initData() {
        for (Preview preview :
                tranList) {
            TranModel tranModel = (TranModel)preview;
            //构造方法：标题，内容，右下角信息
            //志愿：   标题，金额，状态
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date time = new Date();
            try {
                time = dateFormat.parse(tranModel.getDateTime());
            } catch (ParseException | NullPointerException e) {
                e.printStackTrace();
            }
            String content = tranModel.getTranMoney()+"元,时间:"+dateFormat.format(time);
            String state = "";
            String tranState = tranModel.getTranState();
            if (tranState.equals("0")){
                state = "上架中";
            }else if (tranState.equals(""+LoginViewModel.user.getInfoNo())){
                state = "已支付";
            }else {
                state = "已售出";
            }

            messageList.add(new MessModel(preview.getTitle(),content,state));
        }

        FssMessAdapter adapter = new FssMessAdapter(TranHistoryActivity.this,messageList);
        listView.setAdapter(adapter);
    }

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tran_history);

        datas = new HashMap<>();
        datas.put("infoNo", LoginViewModel.user.getInfoNo());
        ReThread reThread = new ReThread(datas,handler,"getAllPay");
        reThread.setFlag(1002);
        reThread.start();

        tranList = new ArrayList<>();
        messageList = new ArrayList<>();

        listView = findViewById(R.id.payhis_lv);
    }
}