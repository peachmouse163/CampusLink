package com.example.campuslink.ui.teacher;

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
import com.example.campuslink.databinding.FragmentTeacherHomeBinding;
import com.example.campuslink.databinding.FragmentTeacherMessageBinding;
import com.example.campuslink.link.LinkToData;
import com.example.campuslink.link.MyThread;
import com.example.campuslink.link.ReThread;
import com.example.campuslink.login.LoginViewModel;
import com.example.campuslink.messages.ShowMessagesActivity;
import com.example.campuslink.model.CommunicationModel;
import com.example.campuslink.model.Message;
import com.example.campuslink.model.QuesToAnModel;
import com.example.campuslink.model.TranModel;
import com.example.campuslink.model.User;
import com.example.campuslink.ques.QuesShowActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TeacherMessageFragment extends Fragment {

    private final String TAG = "TeacherMessageFragment";

    //本页面展示用（去除com的重复对象）
    public static ArrayList<Message> messages;
    private ArrayList<Message> messList;
    //本页面获取全部数据（包含同个人多个信息）
    public static ArrayList<Message> allMessages;

    private FssMessAdapter messAdapter;
    private ListView listView;
    private MyThread comThread;
    private ReThread qtaThread;

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
                case 1005:
                    if (!((String)msg.obj).equals("")){
                        Gson gson = new Gson();
                        Type type = new TypeToken<ArrayList<QuesToAnModel>>(){}.getType();
                        List<QuesToAnModel> qtaModels = gson.fromJson(((String)msg.obj),type);

                        Log.d(TAG, "handleMessage: "+qtaModels.size());

                        if (allMessages == null)
                            allMessages = new ArrayList<Message>();
                        allMessages.addAll(qtaModels);
                        //???
                        messList.addAll(qtaModels);
                    }
                    initData();
                    break;
                case MyThread.EXCEPT:
                    Toast.makeText(requireContext(),"MessageFragment"+msg.obj,Toast.LENGTH_LONG).show();
                    break;

            }
        }
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        /*messages = new ArrayList<>();
        messList = new ArrayList<>();

        //获取com
        startGetCommunication();
        startGetMessages();*/

    }

    @Override
    public void onResume() {
        super.onResume();
        messages = new ArrayList<>();
        messList = new ArrayList<>();

        //获取com
        startGetCommunication();
        startGetMessages();
    }

    private ArrayList<QuesToAnModel> initQTAmodel(ArrayList<QuesToAnModel> questions){
        ArrayList<QuesToAnModel> resultList = new ArrayList<>();

        for (QuesToAnModel model:
             questions) {
            if (model.getInfoNoB().equals(LoginViewModel.user.getInfoNo()+"") || model.getInfoNoB().equals(LoginViewModel.user.getInfoDepartment()))
                resultList.add(model);
        }

        return resultList;
    }

    private void startGetMessages() {
        HashMap<String,Object> datas = new HashMap<>();
        datas.put("department",LoginViewModel.user.getInfoDepartment());
        qtaThread = new ReThread(datas,handler,1005);
        qtaThread.start();
    }

    private void startGetCommunication() {
        comThread = new MyThread();
        comThread.setMod(comThread.COM);
        HashMap<String,String> data = new HashMap<>();
        data.put("infoNo", LoginViewModel.user.getInfoNo()+"");
        data.put("mod","select");
        comThread.setDatas(data);
        comThread.setHandler(handler);
        comThread.start();
    }

    //将聊天信息归类，并保存展示最新的一条。
    private void initCom(ArrayList<CommunicationModel> comModels) {
        HashMap<String,CommunicationModel> endHashMap = new HashMap<>();

        //将com每个以【朋友名】命名，撞到hashmap，届时，遍历的key即可
        for (CommunicationModel comModel :
                comModels) {
            String friend = "";

            if (comModel.getTitle().equals(LoginViewModel.user.getInfoNo()+""))
            {
                friend = comModel.getInfoNoA();
                comModel.setInfoNoB(friend);
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
        if (messList!=null)
            messages.addAll(messList);

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
                    if (comModel.getTitle().equals(LoginViewModel.user.getInfoNo()+""))
                        friend = comModel.getInfoNoA();
                    else
                        friend = comModel.getTitle();

                    Toast.makeText(requireContext(),"与"+friend+"的对话。",Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(requireActivity(), ComActivity.class);
                    intent.putExtra("friend",friend);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(requireActivity(), QuesShowActivity.class);
                    intent.putExtra("messageid",position);
                    startActivity(intent);
                }

            }
        });
    }


    private TeacherMessageViewModel mViewModel;
    private FragmentTeacherMessageBinding binding;
    public static TeacherMessageFragment newInstance() {
        return new TeacherMessageFragment();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
         mViewModel = new ViewModelProvider(this).get(TeacherMessageViewModel.class);

        binding = FragmentTeacherMessageBinding.inflate(inflater,container,false);
        View root = binding.getRoot();

        return root;
        //return inflater.inflate(R.layout.fragment_teacher_message, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(TeacherMessageViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}