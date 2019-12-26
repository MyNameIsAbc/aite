package com.example.jianancangku.view.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.jianancangku.R;
import com.example.jianancangku.bean.OutedHouseBean;
import com.example.jianancangku.bean.Thingbookbean;
import com.example.jianancangku.ui.fragment.NoSendFragment;

import java.util.List;
import java.util.Map;

public class OutedHouseAdapter extends RecyclerView.Adapter<OutedHouseAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private Context context;
    private List<OutedHouseBean.ListBean> mDatas;
    private String type;

    public void setOnitemClickListener(OnItemClickListener onitemClickListener) {
        this.onitemClickListener = onitemClickListener;
    }

    private OnItemClickListener onitemClickListener;

    public interface OnItemClickListener {
        void click(int position);
        void p(int postion,String type);
    }


    public OutedHouseAdapter(Context context, List<OutedHouseBean.ListBean> datas,String type) {
        //这里适配器是写给主活动互相调用的方法
        this.context = context;
        this.mDatas = datas;
        this.inflater = LayoutInflater.from(context);
        this.type=type;

    }


    @NonNull
    @Override
    public OutedHouseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.thingscar_item_layout, parent, false);
        OutedHouseAdapter.ViewHolder viewHolder = new OutedHouseAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OutedHouseAdapter.ViewHolder holder, int position) {
        //        if (mDatas.get(position).getType().equals("2"))
//            holder.thingnow.setText("out_house_ago");
//        else if (mDatas.get(position).getType().equals("1"))
//            holder.thingnow.setText("gohouse_ago");
        holder.thingnow.setVisibility(View.GONE);
        if (type!=null&&type.equals("ok"))
        holder.checke.setVisibility(View.VISIBLE);
        holder.checke.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                onitemClickListener.p(position,b?"add":"out");
            }
        });
        holder.thing_number.setText((String
                .format(context.getString(R.string.shopitem_number)
                        , mDatas.get(position).getPackage_sn())));
        holder.gohouse_time.setText((String
                .format(context.getString(R.string.shopitem_out_timer)
                        , mDatas.get(position).getCreate_time())));
        holder.gohome_address.setText(String
                .format(context.getString(R.string.shopitem_adrress)
                        , mDatas.get(position).getBusiness_address()));
        if (onitemClickListener != null)
            holder.alllayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onitemClickListener.click(position);
                }
            });


    }

    @Override
    public int getItemCount() {
        if (mDatas == null)
            return 0;
        else
            return mDatas.size();
    }

    public GetCallSenderInterface getGetCallSenderInterface() {
        return getCallSenderInterface;
    }

    public void setGetCallSenderInterface(GetCallSenderInterface getCallSenderInterface) {
        this.getCallSenderInterface = getCallSenderInterface;
    }

    private GetCallSenderInterface getCallSenderInterface;
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView thing_number, gohouse_time, gohome_address, thingnow;
        LinearLayout alllayout;
        CheckBox checke;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            thingnow = itemView.findViewById(R.id.thingnow);
            thing_number = itemView.findViewById(R.id.thing_number);
            gohouse_time = itemView.findViewById(R.id.gohouse_time);
            gohome_address = itemView.findViewById(R.id.gohome_address);
            alllayout = itemView.findViewById(R.id.item);
            checke = itemView.findViewById(R.id.check);


        }
    }
    public interface GetCallSenderInterface{
        void getList(String allpackageNumber);
    }
}
