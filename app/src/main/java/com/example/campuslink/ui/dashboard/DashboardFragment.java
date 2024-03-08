package com.example.campuslink.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.campuslink.FssBaseAdapter;
import com.example.campuslink.FssImgAdapter;
import com.example.campuslink.R;
import com.example.campuslink.databinding.FragmentDashboardBinding;
import com.example.campuslink.model.Pay;
import com.example.campuslink.model.Preview;
import com.example.campuslink.model.ThinModel;
import com.example.campuslink.model.VoluModel;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private ArrayList<Preview> payList;
    private FssImgAdapter payAdapter;
    private GridView gridView;
    private FragmentDashboardBinding binding;

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

        payList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            payList.add(new Pay());
        }
        gridView = requireView().findViewById(R.id.pay_list_pay);
        payAdapter = new FssImgAdapter(this.getContext(),payList);
        gridView.setAdapter(payAdapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}