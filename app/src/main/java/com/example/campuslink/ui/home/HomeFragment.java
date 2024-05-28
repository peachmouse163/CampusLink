package com.example.campuslink.ui.home;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.example.campuslink.link.LinkToData;
import com.example.campuslink.link.MyThread;
import com.example.campuslink.link.PollingService;
import com.example.campuslink.link.PushNotification;
import com.example.campuslink.link.ReThread;
import com.example.campuslink.login.LoginViewModel;
import com.example.campuslink.model.NewsModel;
import com.example.campuslink.model.Preview;
import com.example.campuslink.model.VoluModel;
import com.example.campuslink.news.NewsActivity;
import com.example.campuslink.databinding.FragmentHomeBinding;
import com.example.campuslink.thin.CreateThinActivity;
import com.example.campuslink.thin.ThinActivity;
import com.example.campuslink.volu.VoluActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.interfaces.OnInputConfirmListener;
import com.lxj.xpopup.util.XPopupUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class HomeFragment extends Fragment implements InitAll {

    private final String TAG = "HomeFragment";

    private String newPwd = "";
    public static ArrayList<NewsModel> dataList;
    public static ArrayList<Preview> dataListThin, dataListVolu;
    public static ArrayList<VoluModel> myVolus;
    public static Context mContext;
    public static boolean flag = false;

    private HashSet<String> containVolu;

    private ImageButton btnCreateThin, btnAddVolu, btnAddMess;
    private LinearLayout linearLayout;

    private ListView listView, listThin;
    private static ListView listVolu;
    private MyThread myThread, myThinThread;
    private ReThread voluThread;
    private FssBaseAdapter adapter;
    private FssImgAdapter imgThinAdapter;
    private static FssImgAdapter imgVoluAdapter;
    private FragmentHomeBinding binding;

    private static PollingService pollingService;
    //notification
    private NotificationManager mNManager;
    private Notification notify1;
    private Bitmap LargeBitmap = null;
    private static final int NOTIFYID_1 = 1;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(Looper.getMainLooper()) {//消息机制，用来在子线程中更新UI
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {//具体消息，具体显示
                case MyThread.GETALLNEWS:
                    //LinkToData,转换为NewsModel类型。本机判断。
                    if (!((String) msg.obj).equals("")) {
                        dataList = LinkToData.getNews((String) msg.obj);
                        initData();
                    } else
                        Toast.makeText(requireContext(), "暂无新闻", Toast.LENGTH_LONG).show();
                    break;
                case MyThread.EXCEPT:
                    Toast.makeText(requireContext(), "", Toast.LENGTH_LONG).show();
                    break;
                case MyThread.GETALLTHIN:
                    if (!((String) msg.obj).equals("")) {
                        dataListThin = LinkToData.getThin((String) msg.obj);
                        initData();
                    } else
                        Toast.makeText(requireContext(), "暂无thin", Toast.LENGTH_LONG).show();
                    break;
                case 1000:
                    break;
                case 1001:
                    Log.d(TAG, "handleMessage: get 1001");
                    //获得所有志愿活动
                    Gson gson = new Gson();
                    dataListVolu = gson.fromJson(((String) msg.obj), new TypeToken<List<VoluModel>>() {}.getType());
                    imgVoluAdapter = new FssImgAdapter(requireActivity(), getMyVolus(dataListVolu));
                    listVolu.setAdapter(imgVoluAdapter);
                    initVoluNotificaiton();
                    break;
                case 1023:
                    Log.d(TAG, "handleMessage: get 1023");
                    initVoluNotificaiton();
                    break;
            }
        }
    };

    public void initVoluNotificaiton() {
        //如果是第一次获得数据，则，加入contain中，以供之后检查是否增加。
        if (!flag) {
            Log.d(TAG, "initVoluNotificaiton: first");
            flag = true;
            for (Preview model :
                    dataListVolu) {
                VoluModel volu = (VoluModel) model;
                containVolu.add(volu.getVoluId());
            }
        } else {
            Log.d(TAG, "initVoluNotificaiton: second+++");
            //不是第一次了，则，循环数据，查看是否都存在
            //                          遇到不存在的——>发布notification，并加入contain中。
            for (Preview model :
                    dataListVolu) {
                VoluModel volu = (VoluModel) model;
                if (containVolu.add(volu.getVoluId())) {
                    Log.d(TAG, "initVoluNotificaiton: add new Volu");
                    //发布notification
                    //new PushNotification(LoginActivity.this,BitmapFactory.decodeResource(getResources(), R.drawable.ic_notifications_black_24dp),(NotificationManager) getSystemService(NOTIFICATION_SERVICE)).createNotification("title","text","subText","ticker",new Intent(LoginActivity.this,LoginActivity.class));
                    new PushNotification(requireContext(), BitmapFactory.decodeResource(getResources(),R.drawable.ic_notifications_black_24dp),(NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE)).createNotification(volu.getTitle(),volu.getVoluContent(),"补充信息","新-志愿活动",new Intent(requireContext(), MainActivity.class));
                }
            }
        }
    }

    public static ArrayList<VoluModel> getMyVolus(ArrayList<Preview> dataListVolu) {
        Gson gson = new Gson();
        ArrayList<VoluModel> voluModels = new ArrayList<>();
        for (Preview preview :
                dataListVolu) {
            VoluModel voluModel = (VoluModel) preview;
            List<String> list = gson.fromJson(voluModel.getVoluState(), new TypeToken<List<String>>() {
            }.getType());
            if (!list.contains(LoginViewModel.user.getInfoNo() + ""))
                voluModels.add(voluModel);
        }
        return voluModels;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        //仅进行一次
        mContext = requireContext();
        containVolu = new HashSet<>();

        pollingService = new PollingService();
        pollingService.setNextPoll(mContext);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //super.onViewCreated(view, savedInstanceState);
        if (LoginViewModel.user.getInfoRefresh().equals("0")) {
            new XPopup
                    .Builder(getContext())
                    .dismissOnBackPressed(false)
                    .dismissOnTouchOutside(false)
                    .asInputConfirm("新密码", "由于首次登陆或已重置密码，请重新输入自定义密码.",
                            new OnInputConfirmListener() {
                                @Override
                                public void onConfirm(String text) {
                                    newPwd = text;
                                    HashMap<String, Object> datas = new HashMap<>();
                                    LoginViewModel.user.setInfoPassword(newPwd);
                                    datas.put("user", LoginViewModel.user);
                                    ReThread cThread = new ReThread(datas, handler, "");
                                    cThread.setFlag(1000);
                                    cThread.start();
                                }
                            })
                    .show();
        }

        dataList = new ArrayList<>();
        dataListThin = new ArrayList<>();
        dataListVolu = new ArrayList<>();
        myVolus = new ArrayList<>();

        //开启获得新闻
        startGetNewsThread();
        //开启获得Thin
        startGetThinThread();
        //开启获得volu
        startGetVoluThread();

        initView();
        initClick();
    }

    public void createNotification(String title, String text, String subText, String ticker,Intent intent){

        LargeBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_notifications_black_24dp);
        mNManager =(NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

        //定义一个PendingIntent点击Notification后启动一个Activity
        PendingIntent pit = PendingIntent.getActivity(mContext, 0, intent, 0);

        //设置图片,通知标题,发送时间,提示方式等属性
        Notification.Builder mBuilder = new Notification.Builder(mContext);
        mBuilder.setContentTitle(title)                         //标题
                .setContentText(text)                           //内容
                .setSubText(subText)                            //内容下面的一小段文字
                .setTicker(ticker)                              //收到信息后状态栏显示的文字信息
                .setWhen(System.currentTimeMillis())            //设置通知时间
                .setSmallIcon(R.mipmap.ic_launcher)             //设置小图标
                .setLargeIcon(LargeBitmap)                      //设置大图标
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE)    //设置默认的三色灯与振动器
                //.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.biaobiao))  //设置自定义的提示音
                .setAutoCancel(true)                           //设置点击后取消Notification
                .setContentIntent(pit);                        //设置PendingIntent
        notify1 = mBuilder.build();
        mNManager.notify(NOTIFYID_1, notify1);
    }

    private void startGetVoluThread() {
        voluThread = new ReThread(null, handler, 1001);
        voluThread.start();
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

        btnCreateThin = requireView().findViewById(R.id.home_ibtn_thin);
        btnAddVolu = requireView().findViewById(R.id.home_ibtn_add_volu);
        btnAddMess = requireView().findViewById(R.id.home_ibtn_add_mess);

        linearLayout = requireView().findViewById(R.id.home_ll_layout);
        linearLayout.removeView(btnAddMess);
        linearLayout.removeView(btnAddVolu);
    }

    @Override
    public void initData() {
        List<String> titleList = new ArrayList<>();
        for (NewsModel news :
                dataList) {
            titleList.add(news.getNewTitle());
        }
        adapter = new FssBaseAdapter(this.getActivity(), titleList);
        listView.setAdapter(adapter);

        imgThinAdapter = new FssImgAdapter(this.getActivity(), dataListThin);
        listThin.setAdapter(imgThinAdapter);

        imgVoluAdapter = new FssImgAdapter(this.getActivity(), dataListVolu);
        listVolu.setAdapter(imgVoluAdapter);

    }

    @Override
    public void initClick() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(requireContext(), "position:" + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(requireActivity(), NewsActivity.class);
                intent.putExtra("webUrl", dataList.get(position).getNewUrl());
                startActivity(intent);
            }
        });
        listThin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(requireContext(), "position:" + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), ThinActivity.class);
                intent.putExtra("thin", position);
                startActivity(intent);
            }
        });
        listVolu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(requireContext(), "position:" + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), VoluActivity.class);
                intent.putExtra("volu", position);
                startActivity(intent);
            }
        });
        btnCreateThin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireContext(), CreateThinActivity.class));
            }
        });
    }
}