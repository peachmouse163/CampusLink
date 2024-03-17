package com.example.campuslink.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.campuslink.FssBaseAdapter;
import com.example.campuslink.FssImgAdapter;
import com.example.campuslink.InitAll;
import com.example.campuslink.MainActivity;
import com.example.campuslink.R;
import com.example.campuslink.databinding.FragmentHomeBinding;
import com.example.campuslink.link.LinkToData;
import com.example.campuslink.link.MyThread;
import com.example.campuslink.model.NewsModel;
import com.example.campuslink.model.Preview;
import com.example.campuslink.model.ThinModel;
import com.example.campuslink.model.VoluModel;
import com.example.campuslink.news.NewsActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment implements InitAll{

    private final String TAG = "HomeFragment";

    private ArrayList<NewsModel> dataList;
    private ArrayList<Preview> dataListThin,dataListVolu;
    private ListView listView,listThin,listVolu;
    private MyThread myThread,myThinThread,myVoluThread;
    private FssBaseAdapter adapter;
    private FssImgAdapter imgThinAdapter,imgVoluAdapter;
    private FragmentHomeBinding binding;

    @SuppressLint("HandlerLeak")
    Handler handler=new Handler(){//消息机制，用来在子线程中更新UI
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){//具体消息，具体显示
                case MyThread.GETALLNEWS:
                    //LinkToData,转换为NewsModel类型。本机判断。
                    if (!((String)msg.obj).equals("")){
                        dataList = LinkToData.getNews((String) msg.obj);
                        initData();
                    }
                    else
                        Toast.makeText(requireContext(),"获取不到新闻",Toast.LENGTH_LONG).show();
                    break;
                case MyThread.LOGINEXCEPT:
                    Toast.makeText(requireContext(),"",Toast.LENGTH_LONG).show();
                    break;
                case MyThread.GETALLTHIN:
                    Log.d(TAG, "handleMessage: "+(String) msg.obj);
                    if (!((String)msg.obj).equals("")){
                        dataListThin = LinkToData.getThin((String) msg.obj);
                        initData();
                    }
                    else
                        Toast.makeText(requireContext(),"获取不到thin",Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //super.onViewCreated(view, savedInstanceState);
        dataList = new ArrayList<>();
        dataListThin = new ArrayList<>();
        dataListVolu = new ArrayList<>();

        //开启获得新闻
        startGetNewsThread();
        //开启获得Thin
        startGetThinThread();

        for (int i = 0; i < 50; i++) {
            dataListVolu.add(new VoluModel());
        }

        listView = requireView().findViewById(R.id.home_list_news);
        listThin = requireView().findViewById(R.id.home_list_thin);
        listVolu = requireView().findViewById(R.id.home_list_volu);

        imgVoluAdapter = new FssImgAdapter(this.getActivity(),dataListVolu);
        listVolu.setAdapter(imgVoluAdapter);
    }

    private void startGetThinThread() {
        myThinThread = new MyThread();
        myThinThread.setMod(myThinThread.THIN);
        myThinThread.setHandler(handler);
        myThinThread.setDatas(null);
        myThinThread.start();
    }

    private void startGetNewsThread() {
        myThread = new MyThread();
        myThread.setMod(myThread.NEWS);
        myThread.setHandler(handler);
        myThread.setDatas(null);
        myThread.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void HideTitle() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        List<String> titleList = new ArrayList<>();
        for (NewsModel news :
                dataList) {
            titleList.add(news.getNewTitle());
        }
        adapter = new FssBaseAdapter(this.getActivity(),titleList);
        listView.setAdapter(adapter);

        imgThinAdapter = new FssImgAdapter(this.getActivity(),dataListThin);
        listThin.setAdapter(imgThinAdapter);

    }

    @Override
    public void initClick() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(requireContext(),"position:"+position,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(requireActivity(), NewsActivity.class);
                intent.putExtra("webUrl",dataList.get(position).getNewUrl());
                startActivity(intent);
            }
        });
    }
}