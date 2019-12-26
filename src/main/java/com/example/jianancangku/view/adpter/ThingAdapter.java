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
import com.example.jianancangku.bean.GothingBean;
import com.example.jianancangku.bean.Thingbookbean;

import java.util.List;

public class ThingAdapter extends RecyclerView.Adapter<ThingAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private Context context;
    private List<GothingBean.ListBean> mDatas;
    private String type;


    public ThingAdapter(Context context, List<GothingBean.ListBean> datas,String type) {
        //这里适配器是写给主活动互相调用的方法
        this.context = context;
        this.mDatas = datas;
        this.inflater = LayoutInflater.from(context);
        this.type=type;

    }


    @NonNull
    @Override
    public ThingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.shopping_item_layout, parent, false);
        ThingAdapter.ViewHolder viewHolder = new ThingAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ThingAdapter.ViewHolder holder, int position) {
        //        if (mDatas.get(position).getType().equals("2"))
//            holder.thingnow.setText("out_house_ago");
//        else if (mDatas.get(position).getType().equals("1"))
//            holder.thingnow.setText("gohouse_ago");
//        holder.thingnow.setVisibility(View.GONE);
        holder.thing_number.setText((String
                .format(context.getString(R.string.shopitem_number)
                        , mDatas.get(position).getPackage_sn())));

        holder.gohome_address.setText(String
                .format(context.getString(R.string.shopitem_adrress)
                        , mDatas.get(position).getBusiness_address()));

        if (type!=null&&type.equals("out")){
            holder.name.setText(String
                    .format(context.getString(R.string.shopitemout_man)
                            , mDatas.get(position).getWarehouse_clerk_name()));
            holder.gohouse_time.setText((String
                    .format(context.getString(R.string.shopitem_out_timer)
                            , mDatas.get(position).getCreate_time())));
        }
        else{
            holder.gohouse_time.setText((String
                    .format(context.getString(R.string.shopitem_go_timer)
                            , mDatas.get(position).getCreate_time())));
            holder.name.setText(String
                    .format(context.getString(R.string.shopitem_man)
                            , mDatas.get(position).getWarehouse_clerk_name()));
        }


    }

    @Override
    public int getItemCount() {
        if (mDatas==null)
            return 0;
        else
            return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView thing_number, gohouse_time, gohome_address,thingnow,name;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            thingnow = itemView.findViewById(R.id.thingnow);
            name=itemView.findViewById(R.id.name);
            thing_number = itemView.findViewById(R.id.thing_number);
            gohouse_time = itemView.findViewById(R.id.gohouse_time);
            gohome_address = itemView.findViewById(R.id.gohome_address);

        }
    }
}
