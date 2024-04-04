package com.example.campuslink.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.campuslink.R;
import com.example.campuslink.model.CommunicationModel;

import java.util.ArrayList;

/**
 * Created by Jay on 2015/9/23 0023.
 */
public class FssComAdapter extends BaseAdapter {

    //定义两个类别标志
    private static final int TYPE_RECEIVE = 0;
    private static final int TYPE_SEND = 1;
    private Context mContext;
    private ArrayList<CommunicationModel> mData = null;


    public FssComAdapter(Context mContext,ArrayList<CommunicationModel> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //多布局的核心，通过这个判断类别
    @Override
    public int getItemViewType(int position) {
        CommunicationModel model = mData.get(position);

        if (!model.getTitle().equals(model.getInfoAnother()))
            return TYPE_RECEIVE;
        else
            return TYPE_SEND;

    }

    //类别数目
    @Override
    public int getViewTypeCount() {
        return 2;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        ViewHolder1 holder1 = null;
        ViewHolder2 holder2 = null;
        if(convertView == null){
            switch (type){
                case TYPE_RECEIVE:
                    holder1 = new ViewHolder1();
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_list_com_you, parent, false);
                    holder1.imgIcon1 = (ImageView) convertView.findViewById(R.id.comyou_img_pic);
                    holder1.tvContent1 = (TextView) convertView.findViewById(R.id.comyou_tv_text);
                    convertView.setTag(R.id.Tag_RECEIVE,holder1);
                    break;
                case TYPE_SEND:
                    holder2 = new ViewHolder2();
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_list_com_me, parent, false);
                    holder2.imgIcon2 = (ImageView) convertView.findViewById(R.id.comme_img_pic);
                    holder2.tvContent2 = (TextView) convertView.findViewById(R.id.comme_tv_text);
                    convertView.setTag(R.id.Tag_SEND,holder2);
                    break;
            }
        }else{
            switch (type){
                case TYPE_RECEIVE:
                    holder1 = (ViewHolder1) convertView.getTag(R.id.Tag_RECEIVE);
                    break;
                case TYPE_SEND:
                    holder2 = (ViewHolder2) convertView.getTag(R.id.Tag_SEND);
                    break;
            }
        }

        CommunicationModel comModel = mData.get(position);
        //设置下控件的值
        switch (type){
            case TYPE_RECEIVE:
                if(comModel != null){
                    holder1.imgIcon1.setImageResource(R.drawable.ic_hello);
                    holder1.tvContent1.setText(comModel.getMessage());
                }
                break;
            case TYPE_SEND:
                if(comModel != null){
                    holder2.imgIcon2.setImageResource(R.drawable.ic_hello);
                    holder2.tvContent2.setText(comModel.getMessage());
                }
                break;
        }
        return convertView;
    }


    //两个不同的ViewHolder
    private static class ViewHolder1{
        ImageView imgIcon1;
        TextView tvContent1;
    }

    private static class ViewHolder2{
        ImageView imgIcon2;
        TextView tvContent2;
    }
}
