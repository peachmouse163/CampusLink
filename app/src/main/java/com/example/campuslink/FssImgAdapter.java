package com.example.campuslink;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.campuslink.model.ThinModel;
import com.fss.adapter.listview.BaseAdapter;
import com.fss.adapter.listview.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public class FssImgAdapter extends BaseAdapter<ThinModel,FssImgAdapter.ViewImgHolder> {

    public FssImgAdapter(Context context, List<ThinModel> datas) {
        super(context, datas, R.layout.layout_list_home);
    }

    @Override
    protected void onBindView(ViewImgHolder holder, ThinModel thinModel, int position) {
        //super.onBindView(holder, thinModel, position);

        /*
        等待链接数据库
        holder.tvName.setText(thinModel.getThinNo()+"");
        holder.tvTime.setText(thinModel.getThinDate()+"");
        holder.tvTitle.setText(thinModel.getThinTitle());

        if (thinModel.getThinAttribute() == 0)
            holder.imgAtr.setImageResource(R.drawable.ic_lose);
        else
            holder.imgAtr.setImageResource(R.drawable.ic_get);

        holder.imgPic.setImageResource(thinModel.getThinPic());*/

        holder.tvName.setText("thinModel.getThinNo()+");
        holder.tvTime.setText("thinModel.getThinDate()+");
        holder.tvTitle.setText("thinModel.getThinTitle()");

        holder.imgAtr.setImageResource(R.drawable.ic_get);
        holder.imgPic.setImageResource(R.drawable.ic_pic);

    }

    static class ViewImgHolder extends BaseViewHolder {
        TextView tvTitle,tvName,tvTime;
        ImageView imgPic,imgAtr;


        public ViewImgHolder(View view) {
            super(view);
            this.tvTitle = findViewById(R.id.listhome_tv_title);
            this.tvName = findViewById(R.id.listhome_tv_name);
            this.tvTime = findViewById(R.id.listhome_tv_time);
            this.imgAtr = findViewById(R.id.listhome_img_atr);
            this.imgPic = findViewById(R.id.listhome_img_pic);
        }
    }
}
