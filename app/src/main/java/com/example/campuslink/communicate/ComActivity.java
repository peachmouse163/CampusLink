package com.example.campuslink.communicate;

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
import android.widget.ListView;
import android.widget.Toast;

import com.example.campuslink.InitAll;
import com.example.campuslink.R;
import com.example.campuslink.adapter.FssComAdapter;
import com.example.campuslink.link.LinkToData;
import com.example.campuslink.link.MyThread;
import com.example.campuslink.model.CommunicationModel;
import com.example.campuslink.model.Message;
import com.example.campuslink.model.User;
import com.example.campuslink.ui.message.MessageFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ComActivity extends AppCompatActivity  implements InitAll {

    private final String TAG = "ComActivity";

    private String friend;
    private ArrayList<CommunicationModel> comList;
    private FssComAdapter comAdapter;
    private MyThread sendThread;
    public static HashMap<String,String> data;
    @SuppressLint("HandlerLeak")
    Handler handler=new Handler(){//消息机制，用来在子线程中更新UI
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what){
                case MyThread.INITDATA:
                case MyThread.GETMYCOM:
                    initData();
                    listView.setAdapter(comAdapter);
                    break;
                case MyThread.EXCEPT:
                    Toast.makeText(getApplicationContext(),"ComActivity"+msg.obj,Toast.LENGTH_LONG).show();
                    break;

            }
        }
    };

    private ListView listView;
    private Button btnSend;
    private EditText etText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com);

        HideTitle();

        Intent intent = getIntent();
        friend = intent.getStringExtra("friend");
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
        listView = findViewById(R.id.com_lv_list);
        listView.setAdapter(comAdapter);

        etText = findViewById(R.id.com_et_text);
        btnSend = findViewById(R.id.com_btn_send);
    }

    @Override
    public void initData() {
        comList = new ArrayList<>();

        Log.d(TAG, "initData: friend"+friend);

        for (Message message : MessageFragment.allMessages) {
            if (message instanceof CommunicationModel){
                CommunicationModel comModel = (CommunicationModel) message;
                if (comModel.getTitle().equals(friend))
                    comList.add(comModel);
            }
        }

        for (CommunicationModel com :
                comList) {
            Log.d(TAG, "initData: "+com.getTitle()+">"+com.getInfoAnother()+">"+com.getTime()+">"+com.getMessage());
        }

        comAdapter = new FssComAdapter(getApplicationContext(),comList);

        sendThread = new MyThread();
        sendThread.setHandler(handler);
        sendThread.setMod(sendThread.SENDCOM);
    }

    @Override
    public void initClick() {
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = etText.getText().toString();

                data = new HashMap<>();
                Date date = new Date();
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                data.put("mod","insert");
                data.put("infoNo", User.infoNo+"");
                data.put("datetime",format.format(date));
                data.put("infoNoB",friend);
                data.put("Content",content);
                sendThread.setDatas(data);

                if (!sendThread.isAlive())
                    sendThread.start();
            }
        });
    }

    @Override
    protected void onDestroy() {
        MyThread.sendComFlag = false;
        super.onDestroy();
    }
}