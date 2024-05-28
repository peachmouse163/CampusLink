package com.example.campuslink;


import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

//import com.fss.adapter.listview.BaseAdapter;
import com.fss.adapter.listview.BaseAdapter;
import com.fss.adapter.listview.viewholder.BaseViewHolder;

import java.util.List;

public class FssBaseAdapter extends BaseAdapter<String, FssBaseAdapter.ViewHolder> {

    public FssBaseAdapter(Context context, List<String> datas) {
        super(context, datas, R.layout.layout_list_item);
    }

    @Override
    protected void onBindView(ViewHolder holder, String s, int position) {
        //super.onBindView(holder, s, position);
        holder.tvText.setText(s);
        holder.tvText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 35); // 设置字体大小为20sp
    }

    static class ViewHolder extends BaseViewHolder {
        TextView tvText;

        public ViewHolder(View view) {
            super(view);
            this.tvText = findViewById(R.id.tv_text);
        }
    }
}