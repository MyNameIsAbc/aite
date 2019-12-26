package com.example.jianancangku.view.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jianancangku.R;
import com.example.jianancangku.bean.ThingfixBean;

import java.util.List;

public class ThingfixAdapter extends RecyclerView.Adapter<ThingfixAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private Context context;
    private List<ThingfixBean.ListBean> mDatas;


    public ThingfixAdapter(Context context, List<ThingfixBean.ListBean> datas) {
        //这里适配器是写给主活动互相调用的方法
        this.context = context;
        this.mDatas = datas;
        this.inflater = LayoutInflater.from(context);

    }


    @NonNull
    @Override
    public ThingfixAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.thingscar_item_layout, parent, false);
        ThingfixAdapter.ViewHolder viewHolder = new ThingfixAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ThingfixAdapter.ViewHolder holder, int position) {
        //        if (mDatas.get(position).getType().equals("2"))
//            holder.thingnow.setText("out_house_ago");
//        else if (mDatas.get(position).getType().equals("1"))
//            holder.thingnow.setText("gohouse_ago");
        holder.thingnow.setText(mDatas.get(position).getType().equals("1") ? R.string.overgohome : R.string.overouthome);
        holder.thing_number.setText((String
                .format(context.getString(R.string.shopitem_number)
                        , mDatas.get(position).getPackage_sn())));
        holder.gohouse_time.setText((String
                .format(context.getString(R.string.shopitem_go_timer)
                        , mDatas.get(position).getCreate_time())));
        holder.gohome_address.setText(String
                .format(context.getString(R.string.shopitem_adrress)
                        , mDatas.get(position).getBusiness_address()));


    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView thing_number, thingnow, gohouse_time, gohome_address;
        LinearLayout item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item=itemView.findViewById(R.id.item);
            thing_number = itemView.findViewById(R.id.thing_number);
            thingnow = itemView.findViewById(R.id.thingnow);
            gohouse_time = itemView.findViewById(R.id.gohouse_time);
            gohome_address = itemView.findViewById(R.id.gohome_address);

        }
    }
}
