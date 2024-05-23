package com.example.campuslink.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.campuslink.FssImgAdapter;
import com.example.campuslink.R;
import com.example.campuslink.databinding.FragmentDashboardBinding;
import com.example.campuslink.link.LinkToData;
import com.example.campuslink.link.MyThread;
import com.example.campuslink.model.Preview;
import com.example.campuslink.model.TranModel;
import com.example.campuslink.tran.SettleActivity;
import com.example.campuslink.tran.ShowTranActivity;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    public static ArrayList<Preview> tranList;
    private FssImgAdapter payAdapter;
    private GridView gridView;
    private FragmentDashboardBinding binding;

    private MyThread tranThread;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if (msg.what == MyThread.ALLTRAN){
                if (msg.obj != null ){
                    tranList = LinkToData.getTrans((String) msg.obj);
                    initData();
                }
            }
            return true;
        }
    });

    private void initData() {
        payAdapter = new FssImgAdapter(this.getContext(),tranList);
        gridView.setAdapter(payAdapter);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tranList = new ArrayList<>();
        gridView = requireView().findViewById(R.id.pay_list_pay);

        tranThread = new MyThread();
        tranThread.setMod(tranThread.GETTRAN);
        tranThread.setHandler(handler);
        tranThread.start();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(requireActivity(), ShowTranActivity.class);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}