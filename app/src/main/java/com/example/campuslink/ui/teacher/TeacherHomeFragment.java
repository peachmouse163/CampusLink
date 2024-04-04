package com.example.campuslink.ui.teacher;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.campuslink.R;
import com.example.campuslink.databinding.FragmentTeacherHomeBinding;

public class TeacherHomeFragment extends Fragment {

    private TeacherHomeViewModel mViewModel;

    private FragmentTeacherHomeBinding binding;

    public static TeacherHomeFragment newInstance() {
        return new TeacherHomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(TeacherHomeViewModel.class);

        binding = FragmentTeacherHomeBinding.inflate(inflater,container,false);
        View root = binding.getRoot();

        return root;
        //return inflater.inflate(R.layout.fragment_teacher_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(TeacherHomeViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}