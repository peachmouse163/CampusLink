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

    int maxCharacters = 20; // 设置最大字符数

    public FssMessAdapter(Context context, List<Message> datas) {
        super(context, datas,R.layout.layout_message_connect);
    }

    @Override
    protected void onBindView(ViewImgHolder holder, Message message, int position) {
        //// 如果需要检查文本是否被截断，可以使用以下代码
        //if (textView.getLayout().getLineCount() > 1) {
        //    // 文本被截断，显示了省略号
        //} else {
        //    // 文本没有被截断
        //}
        String displayedText = message.getMessage().substring(0,Math.min(maxCharacters,message.getMessage().length()))+"...";
        holder.tvContent.setText(displayedText);
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
