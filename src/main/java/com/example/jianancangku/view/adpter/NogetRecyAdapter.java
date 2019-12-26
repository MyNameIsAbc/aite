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
import com.example.jianancangku.bean.NogetBean;
import com.example.jianancangku.bean.OutedHouseBean;

import java.util.List;

public class NogetRecyAdapter  extends RecyclerView.Adapter<NogetRecyAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private Context context;
    private List<NogetBean.ListBean> mDatas;

    public void setOnitemClickListener(OutedHouseAdapter.OnItemClickListener onitemClickListener) {
        this.onitemClickListener = onitemClickListener;
    }

    private OutedHouseAdapter.OnItemClickListener onitemClickListener;

    public interface OnItemClickListener {
        void click(int position);
        int p(int postion);
    }


    public NogetRecyAdapter(Context context, List<NogetBean.ListBean> datas) {
        //这里适配器是写给主活动互相调用的方法
        this.context = context;
        this.mDatas = datas;
        this.inflater = LayoutInflater.from(context);

    }


    @NonNull
    @Override
    public NogetRecyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.nosend_item_layout, parent, false);
        NogetRecyAdapter.ViewHolder viewHolder = new NogetRecyAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NogetRecyAdapter.ViewHolder holder, int position) {
        //        if (mDatas.get(position).getType().equals("2"))
//            holder.thingnow.setText("out_house_ago");
//        else if (mDatas.get(position).getType().equals("1"))
//            holder.thingnow.setText("gohouse_ago");

//        holder.thingnow.setVisibility(View.GONE);
        holder.worker_name.setText((String
                .format(context.getString(R.string.sendworker)
                        , mDatas.get(position).getDelivery_name())));
        holder.sendtime.setText(mDatas.get(position).getDelivery_time());
        holder.number_txt.setText(String
                .format(context.getString(R.string.shopitem_adrress)
                        , mDatas.get(position).getPackage_sn()));
        holder.time_txt.setText((String
                .format(context.getString(R.string.shopitem_out_timer)
                        , mDatas.get(position).getCreate_time())));
        holder.address_txt.setText((String
                .format(context.getString(R.string.shopitem_adrress)
                        , mDatas.get(position).getBusiness_address())));


    }

    @Override
    public int getItemCount() {
        if (mDatas == null)
            return 0;
        else
            return mDatas.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView worker_name, sendtime, number_txt,time_txt, address_txt;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            worker_name = itemView.findViewById(R.id.worker_name);
            sendtime = itemView.findViewById(R.id.sendtime);
            number_txt = itemView.findViewById(R.id.number_txt);
            time_txt = itemView.findViewById(R.id.time_txt);
            address_txt = itemView.findViewById(R.id.address_txt);

        }
    }
}
