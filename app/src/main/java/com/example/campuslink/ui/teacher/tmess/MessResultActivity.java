package com.example.campuslink.ui.teacher.tmess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.campuslink.FssBaseAdapter;
import com.example.campuslink.R;
import com.example.campuslink.link.ReThread;
import com.example.campuslink.login.LoginViewModel;
import com.example.campuslink.model.CollectModel;
import com.example.campuslink.model.MessModel;
import com.example.campuslink.model.SignModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.lxj.xpopup.interfaces.OnSelectListener;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessResultActivity extends AppCompatActivity {

    private List<String> titles;
    private ListView listView;
    private List<com.example.campuslink.model.Message> messageList;

    private boolean[] flag = new boolean[3];

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            //super.handleMessage(msg);
            switch (msg.what){
                case 1018:
                    if (!((String)msg.obj).equals("")){
                        Gson gson = new Gson();
                        Type messListType = new TypeToken<ArrayList<MessModel>>(){}.getType();
                        messageList.addAll(gson.fromJson((String) msg.obj,messListType));
                    }
                    flag[0] = true;
                    break;
                case 1019:
                    if (!((String)msg.obj).equals("")){
                        Gson gson = new Gson();
                        Type messListType = new TypeToken<ArrayList<CollectModel>>(){}.getType();
                        messageList.addAll(gson.fromJson((String) msg.obj,messListType));
                    }
                    flag[1] = true;
                    break;
                case 1020:
                    if (!((String)msg.obj).equals("")){
                        Gson gson = new Gson();
                        Type messListType = new TypeToken<ArrayList<SignModel>>(){}.getType();
                         messageList.addAll(gson.fromJson((String) msg.obj,messListType));
                    }
                    flag[2] = true;
                    break;
            }

            if (flag[0] && flag[1] && flag[2]){
                for (com.example.campuslink.model.Message message:
                        messageList) {
                    titles.add(message.getTitle());
                }
                FssBaseAdapter adapter = new FssBaseAdapter(MessResultActivity.this,titles);
                listView.setAdapter(adapter);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess_result);

        messageList = new ArrayList<>();
        HashMap<String,Object> datas = new HashMap<>();
        datas.put("data", LoginViewModel.user.getInfoDepartment());

        new ReThread(datas,handler,1018).start();
        new ReThread(datas,handler,1019).start();
        new ReThread(datas,handler,1020).start();

        listView = findViewById(R.id.mess_lv);
        titles = new ArrayList<>();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<String> readList = messageList.get(position).getReadList();

                if (readList.size()>0)
                    new XPopup.Builder(MessResultActivity.this)
                            //.maxWidth(600)
                            .asCenterList("名单", readList.toArray(new String[0]),
                                    new OnSelectListener() {
                                        @Override
                                        public void onSelect(int position, String text) {
                                            Toast.makeText(MessResultActivity.this, "选中"+text, Toast.LENGTH_SHORT).show();
                                        }
                                    })
                            .show();
                else
                    new XPopup.Builder(MessResultActivity.this).asConfirm("导出数据", "是否选择导出数据",
                                    new OnConfirmListener() {
                                        @Override
                                        public void onConfirm() {
                                            Toast.makeText(MessResultActivity.this, "导出成功", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                            .show();
            }
        });

    }
}