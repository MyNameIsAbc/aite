package com.example.jianancangku.view.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jianancangku.App;
import com.example.jianancangku.R;
import com.example.jianancangku.args.Constant;
import com.example.jianancangku.bean.BaseData;
import com.example.jianancangku.bean.Thingbookbean;
import com.example.jianancangku.callback.AbsCallback;
import com.example.jianancangku.utils.BeanConvertor;
import com.example.jianancangku.utils.LogUtils;
import com.example.jianancangku.utils.ToastUtils;
import com.example.jianancangku.view.PopWindowsUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.util.List;

import static com.lzy.okgo.utils.HttpUtils.runOnUiThread;

public class ThingbookAdapter extends RecyclerView.Adapter<ThingbookAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private Context context;
    private List<Thingbookbean.ListBean> mDatas;
    private String type;

    public ThingbookAdapter(Context context, List<Thingbookbean.ListBean> datas, String type) {
        //这里适配器是写给主活动互相调用的方法
        this.context = context;
        this.mDatas = datas;
        this.inflater = LayoutInflater.from(context);
        this.type = type;

    }


    @NonNull
    @Override
    public ThingbookAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.thingscar_item_layout, parent, false);
        ThingbookAdapter.ViewHolder viewHolder = new ThingbookAdapter.ViewHolder(view);
        return viewHolder;
    }

    private GetfixSenderInterface getfixSenderInterface;

    public GetfixSenderInterface getGetfixSenderInterface() {
        return getfixSenderInterface;
    }

    public void setGetfixSenderInterface(GetfixSenderInterface getfixSenderInterface) {
        this.getfixSenderInterface = getfixSenderInterface;
    }

    @Override
    public void onBindViewHolder(@NonNull ThingbookAdapter.ViewHolder holder, int position) {
        //        if (mDatas.get(position).getType().equals("2"))
//            holder.thingnow.setText("out_house_ago");
//        else if (mDatas.get(position).getType().equals("1"))
//            holder.thingnow.setText("gohouse_ago");
        holder.thingnow.setVisibility(View.GONE);
        if (type != null && type.equals("others")) {

            holder.delete.setVisibility(View.VISIBLE);
            holder.checke.setVisibility(View.VISIBLE);
            holder.checke.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    getfixSenderInterface.p(position,isChecked?"add":"out");
                }
            });

            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopWindowsUtils.getmInstance().showdialogPopwindow(App.getContext(), new PopWindowsUtils.showdialogPopwindowIcallback() {
                        @Override
                        public void call(boolean isok) {
                            if (isok) deletePost(mDatas.get(position).getPackage_sn());


                        }
                    }, "确定要删除" + mDatas.get(position).getPackage_sn()+"?");
                }
            });

        }
        if (type != null && type.equals("others")) {
            holder.thing_number.setText((String
                    .format(context.getString(R.string.shopitem_number)
                            , mDatas.get(position).getPackage_sn())));

        } else

            holder.thing_number.setText((String
                    .format(context.getString(R.string.bigitem_number)
                            , mDatas.get(position).getPackage_sn())));
        holder.gohouse_time.setText((String
                .format(context.getString(R.string.shopitem_out_timer)
                        , mDatas.get(position).getCreate_time())));
        holder.gohome_address.setText(String
                .format(context.getString(R.string.shopitem_adrress)
                        , mDatas.get(position).getBusiness_address()));

    }

    private void deletePost(String package_sn) {
        HttpParams params = new HttpParams();
        params.put("KEY", Constant.KEY);
        params.put("warehouse_order_id", package_sn);
        OkGo.<BaseData<Thingbookbean.ListBean>>post(Constant.deletethingbooksAdrress)
                .tag(App.getContext())
                .params(params)
                .execute(new AbsCallback<BaseData<Thingbookbean.ListBean>>() {
                    @Override
                    public BaseData<Thingbookbean.ListBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response);
                        final BaseData baseData = BeanConvertor.convertBean(response.body().string(), BaseData.class);
                        final Thingbookbean thingbookbean = BeanConvertor.convertBean(baseData.getDatas(), Thingbookbean.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!baseData.isSuccessed())
                                    ToastUtils.showToast(App.getContext(), baseData.getErrorMsg());

                            }
                        });

                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<Thingbookbean.ListBean>, ? extends Request> request) {

                    }

                    @Override
                    public void onSuccess(Response<BaseData<Thingbookbean.ListBean>> response) {


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

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView thing_number, gohouse_time, gohome_address, thingnow;
        ImageView delete;
        CheckBox checke;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            thingnow = itemView.findViewById(R.id.thingnow);
            delete = itemView.findViewById(R.id.delete);
            checke = itemView.findViewById(R.id.check);
            thing_number = itemView.findViewById(R.id.thing_number);
            gohouse_time = itemView.findViewById(R.id.gohouse_time);
            gohome_address = itemView.findViewById(R.id.gohome_address);

        }
    }
    public interface GetfixSenderInterface{
        void p(int postion,String type);
    }
}
