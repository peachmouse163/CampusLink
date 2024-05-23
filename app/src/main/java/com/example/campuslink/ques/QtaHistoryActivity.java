package com.example.campuslink.ques;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;

import com.example.campuslink.FssBaseAdapter;
import com.example.campuslink.R;
import com.example.campuslink.adapter.FssMessAdapter;
import com.example.campuslink.link.ReThread;
import com.example.campuslink.login.LoginViewModel;
import com.example.campuslink.model.MessModel;
import com.example.campuslink.model.QuesToAnModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class QtaHistoryActivity extends AppCompatActivity {

    private List<QuesToAnModel> history;
    private List<com.example.campuslink.model.Message> messageList;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            //super.handleMessage(msg);
            if (msg.what == 1008){
                Gson gson = new Gson();
                Type type = new TypeToken<List<QuesToAnModel>>() {}.getType();
                history = gson.fromJson(((String) msg.obj),type);

                for (QuesToAnModel qta :
                        history) {
                    Type endType = new TypeToken<List<String>>() {}.getType();
                    List<String> studentList = gson.fromJson(qta.getQuesEnd(),endType);
                    if (studentList.contains(""+ LoginViewModel.user.getInfoNo())){
                        //title，content，time
                        //content——>department
                        if (studentList.get(0).equals("end"))
                            messageList.add(new MessModel(qta.getTitle(),qta.getInfoNoB()+"已回复",qta.getQuesTimeB()));
                        else{
                            messageList.add(new MessModel(qta.getTitle(),"还有"+(studentList.size()-2)+"位同学等待回复",qta.getQuesTimeB()));
                        }
                    }
                }

                FssMessAdapter adapter = new FssMessAdapter(QtaHistoryActivity.this,messageList);
                listView.setAdapter(adapter);
            }
        }
    };

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qta_history);

        listView = findViewById(R.id.queshis_lv);
        history = new ArrayList<>();
        messageList = new ArrayList<>();

        new ReThread(null,handler,1008).start();
    }
}