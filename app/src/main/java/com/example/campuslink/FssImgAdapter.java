package com.example.campuslink;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.campuslink.model.Preview;
import com.example.campuslink.model.ThinModel;
import com.example.campuslink.model.VoluModel;
import com.fss.adapter.listview.BaseAdapter;
import com.fss.adapter.listview.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FssImgAdapter extends BaseAdapter<Preview, FssImgAdapter.ViewImgHolder> {
    public FssImgAdapter(Context context, List datas) {
        super(context, datas,R.layout.layout_list_home);
    }

    @Override
    protected void onBindView(ViewImgHolder holder, Preview preview, int position) {
        //super.onBindView(holder, preview, position);
        holder.tvName.setText(preview.getInfoName());
        holder.tvTitle.setText(preview.getTitle());
        holder.tvTime.setText(preview.getDateTime());
        //判断，如果是thin模型，则右上角图标状态判断。
        if (preview instanceof ThinModel){
            ThinModel thin = (ThinModel) preview;
            if (thin.getThinAttribute().equals("0"))
                holder.imgAtr.setImageResource(R.drawable.ic_lose);
            else
                holder.imgAtr.setImageResource(R.drawable.ic_get);
        }
        //默认图片
        holder.imgPic.setImageResource(R.drawable.ic_pic);

    }

    static class ViewImgHolder extends BaseViewHolder{

        TextView tvTitle,tvName,tvTime;
        ImageView imgAtr,imgPic;

        public ViewImgHolder(View itemView) {
            super(itemView);
            this.tvTitle = findViewById(R.id.listhome_tv_title);
            this.tvName = findViewById(R.id.listhome_tv_name);
            this.tvTime = findViewById(R.id.listhome_tv_time);
            this.imgAtr = findViewById(R.id.listhome_img_atr);
            this.imgPic = findViewById(R.id.listhome_img_pic);
        }
    }
}
