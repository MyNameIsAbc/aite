package com.example.jianancangku.view.adpter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jianancangku.R;
import com.example.jianancangku.bean.MsgCenterbean;

import java.util.List;

public class MsgCenterRecyAdapter extends RecyclerView.Adapter<MsgCenterRecyAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private Context context;
    private List<MsgCenterbean.ListBean> mDatas;

    public MsgCenterRecyAdapter(Context context, List<MsgCenterbean.ListBean> datas) {
        //这里适配器是写给主活动互相调用的方法
        this.context = context;
        this.mDatas = datas;
        this.inflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.msgcenter_itemlayout, parent, false);
        MsgCenterRecyAdapter.ViewHolder viewHolder = new MsgCenterRecyAdapter.ViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (mDatas.get(position).getStatus().equals("0"))
        holder.unsee_txt.setBackgroundColor(R.mipmap.msgcome);
        holder.msgcenter_item_time.setText(mDatas.get(position).getAdd_time());
        holder.msgcenter_item_msg.setText(mDatas.get(position).getContent());
        holder.msgcenter_item_title.setText(mDatas.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView unsee_txt, msgcenter_item_title, msgcenter_item_time, msgcenter_item_msg;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            unsee_txt = itemView.findViewById(R.id.unsee_txt);
            msgcenter_item_time = itemView.findViewById(R.id.msgcenter_item_time);
            msgcenter_item_msg = itemView.findViewById(R.id.msgcenter_item_msg);
            msgcenter_item_title = itemView.findViewById(R.id.msgcenter_item_title);

        }
    }

}

