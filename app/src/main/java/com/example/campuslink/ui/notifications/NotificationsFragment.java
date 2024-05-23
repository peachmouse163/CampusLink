package com.example.campuslink.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.campuslink.FssBaseAdapter;
import com.example.campuslink.R;
import com.example.campuslink.databinding.FragmentNotificationsBinding;
import com.example.campuslink.link.LinkToData;
import com.example.campuslink.link.MyThread;
import com.example.campuslink.link.ReThread;
import com.example.campuslink.model.QuesToAnModel;
import com.example.campuslink.ques.QuesCreateActivity;
import com.example.campuslink.ques.QuesShowActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {

    public static ArrayList<QuesToAnModel> questions,adapterList;
    private FssBaseAdapter adapter;
    private MyThread quesThread;
    private ReThread reThread;

    private SearchView searchView;
    private ListView listView;

    private FragmentNotificationsBinding binding;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if (msg.what == MyThread.QUESTIONS){
                if ((String)msg.obj != null){
                    questions.addAll(LinkToData.getQuestions((String) msg.obj));
                }
            }
            if (msg.what == 1008){
                if ((String)msg.obj != null){
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<QuesToAnModel>>() {}.getType();
                    questions.addAll(gson.fromJson((String) msg.obj,type));
                }
            }
            return true;
        }
    });

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        searchView = requireView().findViewById(R.id.ques_sv_search);
        listView = requireView().findViewById(R.id.ques_lv_show);
        //存服务器所有的
        questions = new ArrayList<>();
        //存要显示的
        adapterList = new ArrayList<>();
        adapterList.add(new QuesToAnModel("在此新建问题"));

        /*quesThread = new MyThread();
        quesThread.setHandler(handler);
        quesThread.setMod(quesThread.GETQUES);
        quesThread.start();*/
        reThread = new ReThread(null,handler,1008);
        reThread.start();

        ArrayList<String> titles = new ArrayList<>();
        for (QuesToAnModel model :
                adapterList) {
            titles.add(model.getQuesTitle());
        }
        adapter = new FssBaseAdapter(requireContext(),titles);
        listView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //处理查询操作
                initAdapter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    startActivity(new Intent(requireActivity(), QuesCreateActivity.class));
                }else{
                    Intent intent = new Intent(requireActivity(), QuesShowActivity.class);
                    intent.putExtra("position",position);
                    startActivity(intent);
                }
            }
        });
    }

    private void initAdapter(String query) {
        adapterList = new ArrayList<>();
        adapterList.add(new QuesToAnModel("在此新建问题"));
        if (questions.size()>0){
            for (QuesToAnModel ques :
                    questions) {
                StringBuilder sbuffer = new StringBuilder(ques.getQuesTitle());
                if (sbuffer.indexOf(query) >= 0 ){
                    adapterList.add(ques);
                }
            }
        }else
            Toast.makeText(requireContext(),"服务器暂无数据",Toast.LENGTH_SHORT).show();

        ArrayList<String> titles = new ArrayList<>();
        for (QuesToAnModel model :
                adapterList) {
            titles.add(model.getQuesTitle());
        }
        adapter = new FssBaseAdapter(requireContext(),titles);
        listView.setAdapter(adapter);
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}