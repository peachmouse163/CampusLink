package com.example.campuslink.ui.person;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.campuslink.FssBaseAdapter;
import com.example.campuslink.R;
import com.example.campuslink.login.LoginViewModel;
import com.example.campuslink.ques.QtaHistoryActivity;
import com.example.campuslink.tran.TranHistoryActivity;
import com.example.campuslink.volu.VoluHistoryActivity;

import java.util.ArrayList;

public class PersonFragment extends Fragment {

    private ArrayList<String> data;
    private FssBaseAdapter adapter;
    private TextView tvName,tvVolu,tvPay;
    private ListView listView;
    private PersonViewModel mViewModel;

    public static PersonFragment newInstance() {
        return new PersonFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_person, container, false);
    }

  /*  @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PersonViewModel.class);
        // TODO: Use the ViewModel
    }*/

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        data = new ArrayList<>();
        data.add("修改信息");
        data.add("问题回复");
        data.add("联系我们");
        adapter = new FssBaseAdapter(this.getContext(),data);
        listView = requireView().findViewById(R.id.person_list_other);
        listView.setAdapter(adapter);

        tvName = requireView().findViewById(R.id.person_tv_name);
        tvName.setText(LoginViewModel.user.getInfoName());

        tvVolu = requireView().findViewById(R.id.person_tv_volu);
        tvVolu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入已参与的活动选择菜单栏
                //显示名字+进行中/已结束
                //再进入volu的详细展示界面
                startActivity(new Intent(requireActivity(), VoluHistoryActivity.class));
            }
        });

        tvPay = requireView().findViewById(R.id.person_tv_pay);
        tvPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireActivity(), TranHistoryActivity.class));
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        //修改信息
                        break;
                    case 1:
                        //问题回复
                        startActivity(new Intent(requireActivity(), QtaHistoryActivity.class));
                        break;
                    case 2:
                        //联系我们
                        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                            // 已经获得权限，可以拨打电话
                            // 获取用户输入的号码
                            String phoneNumber = "1234567890";
                            // 创建一个Intent
                            Intent intent = new Intent(Intent.ACTION_CALL);
                            // 将号码包装成一个Uri对象
                            Uri data = Uri.parse("tel:" + phoneNumber);
                            // 将Uri对象设置到Intent中
                            intent.setData(data);
                            // 启动该Intent
                            startActivity(intent);
                        } else {
                            // 向用户请求权限
                            ActivityCompat.requestPermissions(requireActivity(),
                                    new String[]{Manifest.permission.CALL_PHONE},
                                    100
                                    );
                        }
                        break;
                }
            }
        });

    }
}