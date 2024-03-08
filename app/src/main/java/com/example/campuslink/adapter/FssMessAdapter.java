package com.example.campuslink.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.campuslink.R;
import com.example.campuslink.model.Message;
import com.fss.adapter.listview.BaseAdapter;
import com.fss.adapter.listview.viewholder.BaseViewHolder;

import java.util.List;

public class FssMessAdapter extends BaseAdapter<Message,FssMessAdapter.ViewImgHolder> {

    public FssMessAdapter(Context context, List<Message> datas) {
        super(context, datas,R.layout.layout_message_connect);
    }

    @Override
    protected void onBindView(ViewImgHolder holder, Message message, int position) {
        holder.tvContent.setText(message.getMessage());
        holder.tvTime.setText(message.getTime());
        holder.tvTitle.setText(message.getTitle());
        holder.imgPic.setImageResource(message.getPic());
    }

    static class ViewImgHolder extends BaseViewHolder {
        TextView tvTitle,tvTime,tvContent;
        ImageView imgPic;


        public ViewImgHolder(View view) {
            super(view);
            this.tvTitle = findViewById(R.id.connect_tv_name);
            this.tvContent = findViewById(R.id.connect_tv_message);
            this.tvTime = findViewById(R.id.connect_tv_time);
            this.imgPic = findViewById(R.id.connect_img_pic);
        }
    }
}
