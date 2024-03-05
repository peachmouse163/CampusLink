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
import com.example.campuslink.model.ThinModel;

import java.util.ArrayList;
import java.util.Objects;

public class HomeFragment extends Fragment{

    private ArrayList<String> dataList,dataListVolu;
    private ArrayList<ThinModel> dataListThin;
    private ListView listView,listThin,listVolu;
    private FssBaseAdapter adapter;
    private FssImgAdapter imgAdapter;
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
        for (int i = 0; i < 50; i++) {
            dataList.add(i + "");
            dataListThin.add(new ThinModel());
        }

        listView = requireView().findViewById(R.id.home_list_news);
        listThin = requireView().findViewById(R.id.home_list_thin);
        adapter = new FssBaseAdapter(this.getActivity(),dataList);
        imgAdapter = new FssImgAdapter(this.getActivity(),dataListThin);
        listView.setAdapter(adapter);
        listThin.setAdapter(imgAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}