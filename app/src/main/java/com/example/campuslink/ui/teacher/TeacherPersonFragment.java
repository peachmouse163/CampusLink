package com.example.campuslink.ui.teacher;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.campuslink.R;
import com.example.campuslink.databinding.FragmentTeacherPersonBinding;
import com.example.campuslink.tran.TranHistoryActivity;
import com.example.campuslink.ui.teacher.tmess.MessResultActivity;

public class TeacherPersonFragment extends Fragment {

    private TeacherPersonViewModel mViewModel;

    private FragmentTeacherPersonBinding binding;

    public static TeacherPersonFragment newInstance() {
        return new TeacherPersonFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mViewModel = new ViewModelProvider(this).get(TeacherPersonViewModel.class);

        binding = FragmentTeacherPersonBinding.inflate(inflater,container,false);
        View root = binding.getRoot();

        return root;
        //return inflater.inflate(R.layout.fragment_teacher_person, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(TeacherPersonViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //super.onViewCreated(view, savedInstanceState);

        TextView textView = requireView().findViewById(R.id.person_tv_volu);
        textView.setText("通 知\n结 果");
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireActivity(), MessResultActivity.class));
            }
        });

        TextView textView1 = requireView().findViewById(R.id.person_tv_pay);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireActivity(), TranHistoryActivity.class));
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}