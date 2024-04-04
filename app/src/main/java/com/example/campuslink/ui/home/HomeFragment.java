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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import com.example.campuslink.FssBaseAdapter;
import com.example.campuslink.FssImgAdapter;
import com.example.campuslink.InitAll;
import com.example.campuslink.R;
import com.example.campuslink.link.LinkToData;
import com.example.campuslink.link.MyThread;
import com.example.campuslink.login.LoginViewModel;
import com.example.campuslink.model.NewsModel;
import com.example.campuslink.model.Preview;
import com.example.campuslink.model.VoluModel;
import com.example.campuslink.news.NewsActivity;
import com.example.campuslink.databinding.FragmentHomeBinding;
import com.example.campuslink.thin.ThinActivity;
import com.example.campuslink.volu.VoluActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements InitAll{

    private final String TAG = "HomeFragment";

    public static ArrayList<NewsModel> dataList;
    public static ArrayList<Preview> dataListThin,dataListVolu;
    public static ArrayList<VoluModel> myVolus;

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
                        Toast.makeText(requireContext(),"暂无新闻",Toast.LENGTH_LONG).show();
                    break;
                case MyThread.EXCEPT:
                    Toast.makeText(requireContext(),"",Toast.LENGTH_LONG).show();
                    break;
                case MyThread.GETALLTHIN:
                    if (!((String)msg.obj).equals("")){
                        dataListThin = LinkToData.getThin((String) msg.obj);
                        initData();
                    }
                    else
                        Toast.makeText(requireContext(),"暂无thin",Toast.LENGTH_LONG).show();
                    break;

                case MyThread.GETALLVOLU:
                    if (!((String)msg.obj).equals("")){
                        dataListVolu = LinkToData.getVolu((String) msg.obj);
                        for (Preview volu:
                             dataListVolu) {
                            VoluModel voluModel = (VoluModel) volu;
                            Log.d(TAG, "handleMessage: "+voluModel.getVoluId());
                        }
                        //筛选出本账户参加的活动
                        myVolus = getMyVolus(dataListVolu);
                        initData();
                    }
                    else
                        Toast.makeText(requireContext(),"暂无volu",Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

    private ArrayList<VoluModel> getMyVolus(ArrayList<Preview> dataListVolu) {
        ArrayList<VoluModel> voluModels = new ArrayList<>();
        for (Preview preview :
                dataListVolu) {
            VoluModel voluModel = (VoluModel) preview;
            if (new StringBuffer(voluModel.getVoluState()).indexOf(LoginViewModel.user.getInfoNo()+"") > -1)
                voluModels.add(voluModel);
        }
        return voluModels;
    }

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
        //开启获得volu
        startGetVoluThread();

        initView();
        initClick();
    }

    private void startGetVoluThread() {
        myVoluThread = new MyThread();
        myVoluThread.setMod(myThread.VOLU);
        myVoluThread.setHandler(handler);
        myVoluThread.setDatas(null);
        myVoluThread.start();
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
        listView = requireView().findViewById(R.id.home_list_news);
        listThin = requireView().findViewById(R.id.home_list_thin);
        listVolu = requireView().findViewById(R.id.home_list_volu);
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

        imgVoluAdapter = new FssImgAdapter(this.getActivity(),dataListVolu);
        listVolu.setAdapter(imgVoluAdapter);

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
        listThin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(requireContext(),"position:"+position,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), ThinActivity.class);
                intent.putExtra("thin",position);
                startActivity(intent);
            }
        });
        listVolu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(requireContext(),"position:"+position,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), VoluActivity.class);
                intent.putExtra("volu",position);
                startActivity(intent);
            }
        });
    }
}