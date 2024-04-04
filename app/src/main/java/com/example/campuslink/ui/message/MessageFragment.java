package com.example.campuslink.ui.message;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.campuslink.R;
import com.example.campuslink.adapter.FssMessAdapter;
import com.example.campuslink.communicate.ComActivity;
import com.example.campuslink.link.LinkToData;
import com.example.campuslink.link.MyThread;
import com.example.campuslink.model.CollectModel;
import com.example.campuslink.model.CommunicationModel;
import com.example.campuslink.model.MessModel;
import com.example.campuslink.model.Message;
import com.example.campuslink.model.SignModel;
import com.example.campuslink.model.User;

import java.util.ArrayList;
import java.util.HashMap;

public class MessageFragment extends Fragment {

    private final String TAG = "MessageFragment";

    //本页面展示用（去除com的重复对象）
    private ArrayList<Message> messages;
    //本页面获取全部数据（包含同个人多个信息）
    public static ArrayList<Message> allMessages;

    private FssMessAdapter messAdapter;
    private ListView listView;
    private MyThread comThread;

    @SuppressLint("HandlerLeak")
    Handler handler=new Handler(){//消息机制，用来在子线程中更新UI
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what){
                case MyThread.GETMYCOM:
                    if (!((String)msg.obj).equals("")){
                        ArrayList<CommunicationModel> comModels = LinkToData.getCom((String) msg.obj);
                        allMessages = new ArrayList<>();
                        allMessages.addAll(comModels);
                        initCom(comModels);
                        initData();
                    }
                    else
                        Toast.makeText(requireContext(),"获取不到Com",Toast.LENGTH_LONG).show();
                    break;


                case MyThread.EXCEPT:
                    Toast.makeText(requireContext(),"MessageFragment"+msg.obj,Toast.LENGTH_LONG).show();
                    break;

            }
        }
    };

    //将聊天信息归类，并保存展示最新的一条。
    private void initCom(ArrayList<CommunicationModel> comModels) {
        HashMap<String,CommunicationModel> endHashMap = new HashMap<>();

        //将com每个以【朋友名】命名，撞到hashmap，届时，遍历的key即可
        for (CommunicationModel comModel :
                comModels) {
            String friend = "";

            if (comModel.getTitle().equals(User.infoNo+""))
            {
                friend = comModel.getInfoAnother();
                comModel.setInfoName(friend);
            }
            else
                friend = comModel.getTitle();
            //每个com都标记名字然后存入hashmap中，由于特性，重复则覆盖，保留最新的。
            endHashMap.put(friend,comModel);
        }

        //
        messages = new ArrayList<>();
        //展示用的messages保存所有聊天人的最后一条消息
        messages.addAll(endHashMap.values());
    }

    private void initData() {
        for (Message message :
                messages) {
            Log.d(TAG, "initData: "+message.getTitle()+">"+message.getMessage()+">"+message.getTime());
        }
        //获取数据后，进行排序再导入绑定
        //四部分，排好第一部分后，剩下的进行检索插入
        //装填数据
        messAdapter = new FssMessAdapter(this.getContext(),messages);
        listView = requireView().findViewById(R.id.message_list_connect);
        listView.setAdapter(messAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Message message = messages.get(position);
                if (message instanceof CommunicationModel){
                    CommunicationModel comModel = (CommunicationModel) message;

                    String friend = "";
                    if (comModel.getTitle().equals(User.infoNo+""))
                        friend = comModel.getInfoAnother();
                    else
                        friend = comModel.getTitle();

                    Toast.makeText(requireContext(),"与"+friend+"的对话。",Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(requireActivity(), ComActivity.class);
                    intent.putExtra("friend",friend);
                    startActivity(intent);
                }

            }
        });
    }

    private MessageViewModel mViewModel;

    public static MessageFragment newInstance() {
        return new MessageFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_message, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        messages = new ArrayList<>();
        //allMessages = new ArrayList<>();

        //获取com
        startGetCommunication();

    }

    private void startGetCommunication() {
        comThread = new MyThread();
        comThread.setMod(comThread.COM);
        HashMap<String,String> data = new HashMap<>();
        data.put("infoNo", User.infoNo+"");
        data.put("mod","select");
        comThread.setDatas(data);
        comThread.setHandler(handler);
        comThread.start();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MessageViewModel.class);
        // TODO: Use the ViewModel
    }

}