package com.example.campuslink.ui.person;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.campuslink.FssBaseAdapter;
import com.example.campuslink.R;
import com.example.campuslink.login.LoginViewModel;

import java.util.ArrayList;

public class PersonFragment extends Fragment {

    private ArrayList<String> data;
    private FssBaseAdapter adapter;
    private TextView tvName;
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
        for (int i = 0; i < 20; i++) {
            data.add(""+i);
        }
        adapter = new FssBaseAdapter(this.getContext(),data);
        listView = requireView().findViewById(R.id.person_list_other);
        listView.setAdapter(adapter);

        tvName = requireView().findViewById(R.id.person_tv_name);
        tvName.setText(LoginViewModel.user.getInfoName());
    }
}