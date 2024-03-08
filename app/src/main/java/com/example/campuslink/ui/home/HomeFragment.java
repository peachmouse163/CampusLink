package com.example.campuslink.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

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
import com.example.campuslink.model.Preview;
import com.example.campuslink.model.ThinModel;
import com.example.campuslink.model.VoluModel;

import java.util.ArrayList;
import java.util.Objects;

public class HomeFragment extends Fragment{

    private ArrayList<String> dataList;
    private ArrayList<Preview> dataListThin,dataListVolu;
    private ListView listView,listThin,listVolu;
    private FssBaseAdapter adapter;
    private FssImgAdapter imgThinAdapter,imgVoluAdapter;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);

       /* final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);*/
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //super.onViewCreated(view, savedInstanceState);
        dataList = new ArrayList<>();
        dataListThin = new ArrayList<>();
        dataListVolu = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            dataList.add(i + "");
            dataListThin.add(new ThinModel());
            dataListVolu.add(new VoluModel());
        }

        listView = requireView().findViewById(R.id.home_list_news);
        listThin = requireView().findViewById(R.id.home_list_thin);
        listVolu = requireView().findViewById(R.id.home_list_volu);

        adapter = new FssBaseAdapter(this.getActivity(),dataList);
        imgThinAdapter = new FssImgAdapter(this.getActivity(),dataListThin);
        imgVoluAdapter = new FssImgAdapter(this.getActivity(),dataListVolu);

        listView.setAdapter(adapter);
        listThin.setAdapter(imgThinAdapter);
        listVolu.setAdapter(imgVoluAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}