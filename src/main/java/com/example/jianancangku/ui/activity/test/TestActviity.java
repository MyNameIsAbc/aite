package com.example.jianancangku.ui.activity.test;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.jianancangku.R;
import com.example.jianancangku.args.Constant;
import com.example.jianancangku.bean.AllAloneBean;
import com.example.jianancangku.bean.BaseData;
import com.example.jianancangku.callback.AbsCallback;
import com.example.jianancangku.ui.activity.BaseActivity;
import com.example.jianancangku.ui.activity.CallSendThingWorkerActivity;
import com.example.jianancangku.ui.activity.MsgCenterActivity;
import com.example.jianancangku.ui.activity.ProblemActivity;
import com.example.jianancangku.ui.activity.QrCodeActivity;
import com.example.jianancangku.ui.activity.SettingmainActivity;
import com.example.jianancangku.ui.activity.ThingbookActivity;
import com.example.jianancangku.ui.activity.ThingsFixActivity;
import com.example.jianancangku.ui.activity.WorkerfixActivity;
import com.example.jianancangku.utils.BeanConvertor;
import com.example.jianancangku.utils.LogUtils;
import com.example.jianancangku.utils.StatusBarUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TestActviity extends BaseActivity {
    private GridView gridView;
    private ImageView people_img;
    @BindView(R.id.woker)
    TextView worker;
    @BindView(R.id.worker_name)
    TextView worker_name;
    Unbinder unbinder;
    private String[] mallItems = null;
    private int[] mallPics = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        StatusBarUtils.setTransparent(context);
        initViews();
        getDatas();

    }

    private void initViews() {
        gridView = findViewById(R.id.gridview);
        unbinder = ButterKnife.bind((Activity) context);
        people_img = findViewById(R.id.people_img);
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        people_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, SettingmainActivity.class));
            }
        });
    }

    private void getDatas() {
        HttpParams params = new HttpParams();
        params.put("KEY", Constant.KEY);
        OkGo.<BaseData<AllAloneBean.InfoBean>>post(Constant.mainuiallAddress)
                .tag(context)
                .params(params)
                .execute(new AbsCallback<BaseData<AllAloneBean.InfoBean>>() {
                    @Override
                    public BaseData<AllAloneBean.InfoBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response);
                        assert response.body() != null;
                        final BaseData baseData = BeanConvertor.convertBean(response.body().string(), BaseData.class);
                        assert baseData != null;
                        final AllAloneBean allAloneBean = BeanConvertor.convertBean(baseData.getDatas(), AllAloneBean.class);
                        AllAloneBean.InfoBean infoBean = BeanConvertor.convertBean(allAloneBean.getInfo(), AllAloneBean.InfoBean.class);
                        LogUtils.d(baseData.getDatas());
                        LogUtils.d(allAloneBean.getInfo());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!Constant.isLogin) return;
                                Glide.with(context).load(infoBean.getAvatar()).transform(new CircleCrop()).into(people_img);
                                Constant.peopeliconUrl = infoBean.getAvatar();
                                Constant.peopelwoker = infoBean.getPosition();
                                Constant.peopelname = infoBean.getName();
                                Constant.type = infoBean.getType();
                                worker.setText(infoBean.getPosition());
                                worker_name.setText(infoBean.getName());
                                //1柬埔寨管理员 2柬埔寨入库员 3柬埔寨出库员 4泰国管理员 5泰国入库员 6泰国出库员
                                switch (infoBean.getType()) {
                                    case "1":
                                        mallItems = new String[]{"消息中心", "订单管理", "扫描入库", "扫描出库",
                                                "员工管理", "订单打包", "呼叫配送员", "包裹异常"};
                                        mallPics = new int[]{R.mipmap.messagemain,
                                                R.mipmap.thingsworkerfix,
                                                R.mipmap.housego, R.mipmap.houseout,
                                                R.mipmap.workerfix, R.mipmap.thingfix,
                                                R.mipmap.callsender, R.mipmap.problems_icon};
                                        gridView.setAdapter(new HomeAdapter());
                                        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                switch (position) {
                                                    case 0:
                                                        startActivity(MsgCenterActivity.class);
                                                        break;
                                                    case 1:
                                                        startActivity(ThingsFixActivity.class);
                                                        break;
                                                    case 2:
                                                        Intent yy = new Intent(context, QrCodeActivity.class);
                                                        yy.putExtra("outinterface", "go");
                                                        startActivity(yy);
                                                        break;
                                                    case 3:
                                                        Intent intent = new Intent(context, QrCodeActivity.class);
                                                        intent.putExtra("outinterface", "out");
                                                        startActivity(intent);
                                                        break;
                                                    case 4:
                                                        startActivity(WorkerfixActivity.class);
                                                        break;
                                                    case 5:
                                                        startActivity(ThingbookActivity.class);
                                                        break;
                                                    case 6:
                                                        startActivity(CallSendThingWorkerActivity.class);

                                                        break;
                                                    case 7:
                                                        startActivity(ProblemActivity.class);
                                                        break;

                                                }
                                            }
                                        });
                                        break;
                                    case "2":
                                    case "5":
                                        mallItems = new String[]{"消息中心", "订单管理", "扫描入库", "包裹异常"};
                                        mallPics = new int[]{R.mipmap.messagemain,
                                                R.mipmap.thingsworkerfix,
                                                R.mipmap.housego, R.mipmap.problems_icon};
                                        gridView.setAdapter(new HomeAdapter());
                                        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                switch (position) {
                                                    case 0:
                                                        startActivity(MsgCenterActivity.class);
                                                        break;
                                                    case 1:
                                                        startActivity(ThingsFixActivity.class);
                                                        break;
                                                    case 2:
                                                        Intent yy = new Intent(context, QrCodeActivity.class);
                                                        yy.putExtra("outinterface", "go");
                                                        startActivity(yy);
                                                        break;
                                                    case 3:
                                                        startActivity(ProblemActivity.class);
                                                        break;


                                                }
                                            }
                                        });
                                        break;
                                    case "3":
                                    case "6":
                                        mallItems = new String[]{"消息中心", "订单管理", "扫描出库", "包裹异常"};
                                        mallPics = new int[]{R.mipmap.messagemain,
                                                R.mipmap.thingsworkerfix,
                                                R.mipmap.houseout, R.mipmap.problems_icon};
                                        gridView.setAdapter(new HomeAdapter());
                                        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                switch (position) {
                                                    case 0:
                                                        startActivity(MsgCenterActivity.class);
                                                        break;
                                                    case 1:
                                                        startActivity(ThingsFixActivity.class);
                                                        break;
                                                    case 2:
                                                        Intent yy = new Intent(context, QrCodeActivity.class);
                                                        yy.putExtra("outinterface", "out");
                                                        startActivity(yy);
                                                        break;
                                                    case 3:
                                                        startActivity(ProblemActivity.class);
                                                        break;

                                                }
                                            }
                                        });
                                        break;
                                    case "4":
                                        mallItems = new String[]{"消息中心", "订单管理", "扫描入库", "扫描出库",
                                                "员工管理", "订单打包", "包裹异常"};
                                        mallPics = new int[]{R.mipmap.messagemain,
                                                R.mipmap.thingsworkerfix,
                                                R.mipmap.housego, R.mipmap.houseout,
                                                R.mipmap.workerfix, R.mipmap.thingfix, R.mipmap.problems_icon};
                                        gridView.setAdapter(new HomeAdapter());
                                        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                switch (position) {
                                                    case 0:
                                                        startActivity(MsgCenterActivity.class);
                                                        break;
                                                    case 1:
                                                        startActivity(ThingsFixActivity.class);
                                                        break;
                                                    case 2:
                                                        Intent yy = new Intent(context, QrCodeActivity.class);
                                                        yy.putExtra("outinterface", "go");
                                                        startActivity(yy);
                                                        break;
                                                    case 3:
                                                        Intent intent = new Intent(context, QrCodeActivity.class);
                                                        intent.putExtra("outinterface", "out");
                                                        startActivity(intent);
                                                        break;
                                                    case 4:
                                                        startActivity(WorkerfixActivity.class);
                                                        break;
                                                    case 5:
                                                        startActivity(ThingbookActivity.class);
                                                        break;
                                                    case 6:
                                                        startActivity(ProblemActivity.class);
                                                        break;

                                                }
                                            }
                                        });
                                        break;
                                }

                            }
                        });
                        return baseData;
                    }

                    @Override
                    public void onStart(Request<BaseData<AllAloneBean.InfoBean>, ? extends Request> request) {
                    }

                    @Override
                    public void onSuccess(Response<BaseData<AllAloneBean.InfoBean>> response) {
                    }
                });
    }

    @Override
    public void onClick(View v) {

    }

    private class HomeAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mallItems == null ? 0 : mallItems.length;
        }

        @Override
        public Object getItem(int position) {
            return mallItems[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            @SuppressLint("ViewHolder") View view = View.inflate(TestActviity.this,
                    R.layout.test_itemlayout, null);
            ImageView icon = (ImageView) view.findViewById(R.id.icon);
            TextView title = (TextView) view.findViewById(R.id.title);
            if (mallItems != null && mallPics != null) {
                title.setText(mallItems[position]);
                icon.setImageResource(mallPics[position]);
            }
            return view;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


//    //东浦寨主管
//    private String[] mItems = new String[]{"消息中心", "订单管理", "扫描入库", "扫描出库",
//            "员工管理", "订单打包", "呼叫配送员", "包裹异常"};
//
//    private int[] mPics = new int[]{R.mipmap.messagemain,
//            R.mipmap.thingsworkerfix,
//            R.mipmap.housego, R.mipmap.houseout,
//            R.mipmap.workerfix, R.mipmap.thingfix,
//            R.mipmap.callsender, R.mipmap.problems_icon};
//    //     if (position == 0) {
////        startActivity(new Intent(context, MsgCenterActivity.class));
////    }
////                if (position == 1) {
////        startActivity(new Intent(context, ThingsFixActivity.class));
////
////    }
////                if (position == 2) {
////        Intent yy = new Intent(context, QrCodeActivity.class);
////        yy.putExtra("outinterface", "go");
////        startActivity(yy);
////    }
////                if (position == 3) {
////        Intent intent = new Intent(context, QrCodeActivity.class);
////        intent.putExtra("outinterface", "out");
////        startActivity(intent);
////
////    }
////                if (position == 4) {
////        startActivity(new Intent(context, WorkerfixActivity.class));
////    }
////                if (position == 5) {
////        startActivity(new Intent(context, ThingbookActivity.class));
////
////    }
////                if (position == 6) {
////        startActivity(new Intent(context, CallSendThingWorkerActivity.class));
////    }
////                if (position == 7) {
////        startActivity(new Intent(context, ProblemActivity.class));
////
////    }
//    //泰国主管
//    private String[] mItems2 = new String[]{"消息中心", "订单管理", "扫描入库", "扫描出库",
//            "员工管理", "订单打包", "包裹异常"};
//
//    private int[] mPics2 = new int[]{R.mipmap.messagemain,
//            R.mipmap.thingsworkerfix,
//            R.mipmap.housego, R.mipmap.houseout,
//            R.mipmap.workerfix, R.mipmap.thingfix, R.mipmap.problems_icon};
////     if (position == 0) {
////        startActivity(new Intent(context, MsgCenterActivity.class));
////    }
////                if (position == 1) {
////        startActivity(new Intent(context, ThingsFixActivity.class));
////
////    }
////                if (position == 2) {
////        Intent yy = new Intent(context, QrCodeActivity.class);
////        yy.putExtra("outinterface", "go");
////        startActivity(yy);
////    }
////                if (position == 3) {
////        Intent intent = new Intent(context, QrCodeActivity.class);
////        intent.putExtra("outinterface", "out");
////        startActivity(intent);
////
////    }
////                if (position == 4) {
////        startActivity(new Intent(context, WorkerfixActivity.class));
////    }
////                if (position == 5) {
////        startActivity(new Intent(context, ThingbookActivity.class));
////
////    }
////                if (position == 6) {
////        startActivity(new Intent(context, ProblemActivity.class));
////    }
//
//    //入库员
//    private String[] mItems3 = new String[]{"消息中心", "订单管理", "扫描入库", "包裹异常"};
//
//    private int[] mPics3 = new int[]{R.mipmap.messagemain,
//            R.mipmap.thingsworkerfix,
//            R.mipmap.housego, R.mipmap.problems_icon};
////     if (position == 0) {
////        startActivity(new Intent(context, MsgCenterActivity.class));
////    }
////                if (position == 1) {
////        startActivity(new Intent(context, ThingsFixActivity.class));
////
////    }
////                if (position == 2) {
////        Intent yy = new Intent(context, QrCodeActivity.class);
////        yy.putExtra("outinterface", "go");
////        startActivity(yy);
////    }
////                if (position == 3) {
////        startActivity(new Intent(context, ProblemActivity.class));
////
////    }
//
//    //泰国出库员 东浦寨出货员
//    private String[] mItems4 = new String[]{"消息中心", "订单管理", "扫描出库", "包裹异常"};
//
//    private int[] mPics4 = new int[]{R.mipmap.messagemain,
//            R.mipmap.thingsworkerfix,
//            R.mipmap.houseout, R.mipmap.problems_icon};
////     if (position == 0) {
////        startActivity(new Intent(context, MsgCenterActivity.class));
////    }
////                if (position == 1) {
////        startActivity(new Intent(context, ThingsFixActivity.class));
////
////    }
////                if (position == 2) {
////        Intent yy = new Intent(context, QrCodeActivity.class);
////        yy.putExtra("outinterface", "out");
////        startActivity(yy);
////    }
////                if (position == 3) {
////        startActivity(new Intent(context, ProblemActivity.class));
////
////    }
}
