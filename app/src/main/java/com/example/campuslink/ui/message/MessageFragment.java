package com.example.campuslink.ui.message;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.campuslink.R;
import com.example.campuslink.adapter.FssMessAdapter;
import com.example.campuslink.model.CollectModel;
import com.example.campuslink.model.CommunicationModel;
import com.example.campuslink.model.MessModel;
import com.example.campuslink.model.Message;
import com.example.campuslink.model.SignModel;

import java.util.ArrayList;

public class MessageFragment extends Fragment {

    private ArrayList<Message> messages;
    private FssMessAdapter messAdapter;

    private ListView listView;

    private MessageViewModel mViewModel;

    public static MessageFragment newInstance() {
        return new MessageFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_message, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        messages = new ArrayList<>();
        //装填数据
        for (int i = 0; i < 10; i++) {
            messages.add(new CollectModel());
            messages.add(new MessModel());
            messages.add(new SignModel());
            messages.add(new CommunicationModel());
        }
        messAdapter = new FssMessAdapter(this.getContext(),messages);
        listView = requireView().findViewById(R.id.message_list_connect);
        listView.setAdapter(messAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MessageViewModel.class);
        // TODO: Use the ViewModel
    }

}