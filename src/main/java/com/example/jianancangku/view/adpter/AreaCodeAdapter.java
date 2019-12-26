package com.example.jianancangku.view.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.jianancangku.R;
import com.example.jianancangku.bean.AreaCodeBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AreaCodeAdapter extends RecyclerView.Adapter<AreaCodeAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private Context context;
    private List<AreaCodeBean.ListBean> mDatas;


    public AreaCodeAdapter(Context context, List<AreaCodeBean.ListBean> datas) {
        this.context = context;
        this.mDatas = datas;
        this.inflater = LayoutInflater.from(context);

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.areacode_item_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.areaCodeTxt.setText(String.format("%s", mDatas.get(position).getCode()));
        Glide.with(context).load(mDatas.get(position).getIcon()).into(holder.areaIv);
        holder.itemView.setOnClickListener(v -> getfixSenderInterface.p(position));

    }

    private GetfixSenderInterface getfixSenderInterface;

    public GetfixSenderInterface getGetfixSenderInterface() {
        return getfixSenderInterface;
    }

    public void setGetfixSenderInterface(GetfixSenderInterface getfixSenderInterface) {
        this.getfixSenderInterface = getfixSenderInterface;
    }

    @Override
    public int getItemCount() {
        if (mDatas == null)
            return 0;
        else
            return mDatas.size();
    }

    static
    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.area_iv)
        ImageView areaIv;
        @BindView(R.id.area_code_txt)
        TextView areaCodeTxt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    public interface GetfixSenderInterface {
        void p(int postion);
    }

}
