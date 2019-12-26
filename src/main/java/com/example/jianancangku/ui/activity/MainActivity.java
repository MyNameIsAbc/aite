package com.example.jianancangku.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.jianancangku.R;
import com.example.jianancangku.args.Constant;
import com.example.jianancangku.bean.AllAloneBean;
import com.example.jianancangku.bean.BaseData;
import com.example.jianancangku.callback.AbsCallback;
import com.example.jianancangku.eventbus.LogInEventBus;
import com.example.jianancangku.eventbus.MsgCenterEventbus;
import com.example.jianancangku.utils.BeanConvertor;
import com.example.jianancangku.utils.LogUtils;
import com.example.jianancangku.utils.PhoneDeviceMsgUtils;
import com.example.jianancangku.utils.SharePreferencesHelper;
import com.example.jianancangku.utils.StatusBarUtils;
import com.example.jianancangku.utils.http.HttpOkgoUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.jianancangku.args.Constant.isLogin;


public class MainActivity extends BaseActivity implements TextToSpeech.OnInitListener {
    private GridView gridView;
    private ImageView people_img;
    @BindView(R.id.woker)
    TextView worker;
    @BindView(R.id.worker_name)
    TextView worker_name;

    Unbinder unbinder;
    private String[] mallItems = null;
    private int[] mallPics = null;
    private TextToSpeech textToSpeech;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        unbinder = ButterKnife.bind((Activity) context);
        StatusBarUtils.setTransparent(context);
        initViews();
        initAgainLogIn();


    }

    private void initAgainLogIn() {
        SharePreferencesHelper sharePreferencesHelper = new SharePreferencesHelper(context, "USER_INFO");
        if (sharePreferencesHelper.contain("usernumber" + "isLogIn")) {
            if ((boolean) sharePreferencesHelper.getSharePreference("usernumber" + "isLogIn", false)) {
                if (!sharePreferencesHelper.contain("usernumber") || !sharePreferencesHelper.contain("usernumber" + "userkey")) {
                    startActivity(LogInActivity.class);
                    return;
                }
                isLogin = true;
                HttpParams params = new HttpParams();
                params.put("username", (String) sharePreferencesHelper.getSharePreference("usernumber", ""));
                params.put("password", (String) sharePreferencesHelper.getSharePreference("usernumber" + "userkey", ""));
                params.put("client", Constant.device);
                params.put("device_id", PhoneDeviceMsgUtils.getDeviceid(context));
                HttpOkgoUtils.
                        getmInstance().
                        mLogInActivity(
                                context,
                                params,
                                Constant.LogInAddress,
                                (String) sharePreferencesHelper.getSharePreference("usernumber", ""),
                                (String) sharePreferencesHelper.getSharePreference("usernumber" + "userkey", ""));
            }
        }
    }

    //语音播报
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MsgCenterEventbus event) {
        if (textToSpeech != null && !textToSpeech.isSpeaking()) {
            textToSpeech.speak(event.getMsg(), TextToSpeech.QUEUE_FLUSH, null);
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(LogInEventBus event) {
        getDatas();
    }


    private void initViews() {
        EventBus.getDefault().register(this);

        textToSpeech = new TextToSpeech(MainActivity.this, MainActivity.this);
        textToSpeech.setPitch(1.0f); // 设置音调，值越大声音越尖（女生），值越小则变成男声,1.0是常规
        textToSpeech.setSpeechRate(1.0f);// 设置语速


        gridView = findViewById(R.id.gridview);
        people_img = findViewById(R.id.people_img);
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        people_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, SettingmainActivity.class));

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDatas();

    }

    private void getDatas() {
        HttpParams params = new HttpParams();
        if (Constant.KEY == null) return;
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
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!baseData.isSuccessed()) startActivity(LogInActivity.class);
                                if (!isLogin || Constant.KEY == null) return;
                                if (infoBean == null) return;
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

    /**
     *   TextToSpeech.LANG_MISSING_DATA：表示语言的数据丢失
     *   TextToSpeech.LANG_NOT_SUPPORTED：不支持
     * @param status
     */
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = textToSpeech.setLanguage(Locale.CHINA);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "数据丢失或不支持", Toast.LENGTH_SHORT).show();
            }
        }
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
            @SuppressLint("ViewHolder") View view = View.inflate(MainActivity.this,
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
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
            textToSpeech = null;
        }
        EventBus.getDefault().unregister(this);
    }

}
//    private ImageView people_img;
//    Unbinder unbinder;
//    @BindView(R.id.code_out_icon)
//    ImageView codeouticon;
//    @BindView(R.id.code_go_icon)
//    ImageView codegoicon;
//    @BindView(R.id.things_fixing_icon)
//    ImageView things_fixing_icon;
//    @BindView(R.id.msgcenter_icon)
//    ImageView msgcenter_icon;
//    @BindView(R.id.workerfix_icon)
//    ImageView workerfix_icon;
//    @BindView(R.id.thingsbook_icon)
//    ImageView thingsbook_icon;
//    @BindView(R.id.call_senndthingworker_icon)
//    ImageView call_senndthingworker_icon;
//    @BindView(R.id.woker)
//    TextView worker;
//    @BindView(R.id.worker_name)
//    TextView worker_name;
//    @BindView(R.id.problem_icon)
//    ImageView problem_icon;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main_layout);
//        unbinder = ButterKnife.bind((Activity) context);
//        initViews();
//
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        getDatas();
//    }
//
//    private void getDatas() {
//        HttpParams params = new HttpParams();
//        params.put("KEY", Constant.KEY);
////        HttpOkgoUtils.getmInstance().mMainActivity(context, params, mainuisAddress, people_img, worker, worker_name,call_senndthingworker_icon);
//    }
//
//    private void initViews() {
//        StatusBarUtils.setTransparent(this);
//        if (!Constant.isLogin && TextUtils.isEmpty(Constant.KEY)) {
//            startActivity(new Intent(context, LogInActivity.class));
//            finish();
//        }
//
//        people_img = findViewById(R.id.people_img);
//        people_img.setOnClickListener(this);
//        codegoicon.setOnClickListener(this);
//        codeouticon.setOnClickListener(this);
//        things_fixing_icon.setOnClickListener(this);
//        problem_icon.setOnClickListener(this);
//        workerfix_icon.setOnClickListener((View.OnClickListener) context);
//        thingsbook_icon.setOnClickListener((View.OnClickListener) context);
//        call_senndthingworker_icon.setOnClickListener((View.OnClickListener) context);
//        msgcenter_icon.setOnClickListener((View.OnClickListener) context);
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.code_out_icon:
//                Intent intent = new Intent(context, QrCodeActivity.class);
//                intent.putExtra("outinterface", "out");
//                startActivity(intent);
//                break;
//            case R.id.code_go_icon:
//                Intent yy = new Intent(context, QrCodeActivity.class);
//                yy.putExtra("outinterface", "go");
//                startActivity(yy);
//                break;
//            case R.id.things_fixing_icon:
//                startActivity(new Intent(context, ThingsFixActivity.class));
//                break;
//            case R.id.msgcenter_icon:
//                startActivity(new Intent(context, MsgCenterActivity.class));
//                break;
//            case R.id.workerfix_icon:
//                startActivity(new Intent(context, WorkerfixActivity.class));
//                break;
//            case R.id.thingsbook_icon:
//                startActivity(new Intent(context, ThingbookActivity.class));
//                break;
//            case R.id.call_senndthingworker_icon:
//                startActivity(new Intent(context, CallSendThingWorkerActivity.class));
//                break;
//            case R.id.people_img:
//                startActivity(new Intent(MainActivity.this, SettingmainActivity.class));
//                break;
//            case R.id.problem_icon:
//                startActivity(new Intent(MainActivity.this, ProblemActivity.class));
//                break;
//
//
//
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
////        System.gc();
//        unbinder.unbind();
//    }
//}
