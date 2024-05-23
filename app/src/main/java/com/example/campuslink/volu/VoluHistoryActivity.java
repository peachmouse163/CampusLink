package com.example.campuslink.volu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.campuslink.R;
import com.example.campuslink.adapter.FssMessAdapter;
import com.example.campuslink.link.MyThread;
import com.example.campuslink.link.ReThread;
import com.example.campuslink.login.LoginViewModel;
import com.example.campuslink.model.MessModel;
import com.example.campuslink.model.VoluModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class VoluHistoryActivity extends AppCompatActivity {

    private final String TAG = "VoluHistoryActivity";
    private int score = 0;

    private List<VoluModel> voluList;
    private List<com.example.campuslink.model.Message> messageList;
    @SuppressLint("HandlerLeak")
    Handler handler=new Handler(){//消息机制，用来在子线程中更新UI
        @Override
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 1001){
                Gson gson = new Gson();
                Type userListType = new TypeToken<ArrayList<VoluModel>>(){}.getType();
                voluList = gson.fromJson((String)msg.obj,userListType);
                Log.d(TAG, "handleMessage: volu-number:"+voluList.size());
                Log.d(TAG, "handleMessage: volu1"+voluList.get(0).toString());
                initData();
            }
        }
    };

    private TextView tvScore;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volu_history);

        HashMap<String,Object> datas = new HashMap<>();
        datas.put("infoNo", LoginViewModel.user.getInfoNo());
        ReThread thread = new ReThread(datas,handler,"getAllVolu");
        thread.setFlag(1001);
        thread.start();

        voluList = new ArrayList<>();
        messageList = new ArrayList<>();

        tvScore = findViewById(R.id.voluhis_tv_score);
        listView = findViewById(R.id.voluhis_lv);
    }

    //处理数据，获得总分并将volu转为message，作为数据源传入adapter显示
    private void initData() {
        if (voluList.size() > 0){
            for (VoluModel volumodel :
                    voluList) {
                //构造方法：标题，内容，右下角信息
                //志愿：   标题，开始时间+结束时间+分数，状态
                //
                String content = volumodel.getVoluStart()+"至"+volumodel.getVoluEnd()+"分数"+volumodel.getVoluPoint();
                //当前时间超过end，即为结束。
                String state = "";
                Date now = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date end = new Date();
                Date start = new Date();
                try {
                    start = dateFormat.parse(volumodel.getVoluStart());
                    end = dateFormat.parse(volumodel.getVoluEnd());
                    if (now.compareTo(start) < 0) {
                        state = "未开始";
                    } else if (now.compareTo(end) > 0) {
                        state = "已结束";
                        Log.d(TAG, "initData: point:"+volumodel.getVoluPoint());
                        score += Integer.parseInt(volumodel.getVoluPoint());
                    } else
                        state = "正在进行";
                } catch (ParseException | NullPointerException e) {
                    e.printStackTrace();
                }
                messageList.add(new MessModel(volumodel.getTitle(),content,state));
            }

            FssMessAdapter adapter = new FssMessAdapter(VoluHistoryActivity.this,messageList);
            listView.setAdapter(adapter);
            tvScore.setText(tvScore.getText().toString()+",总分:"+score);
        }else {
            Toast.makeText(VoluHistoryActivity.this,"暂无志愿记录",Toast.LENGTH_SHORT).show();
        }
    }
}