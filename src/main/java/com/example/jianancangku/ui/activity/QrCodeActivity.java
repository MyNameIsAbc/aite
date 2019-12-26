package com.example.jianancangku.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.jianancangku.R;
import com.example.jianancangku.args.Constant;
import com.example.jianancangku.bean.BaseData;
import com.example.jianancangku.bean.QrBean;
import com.example.jianancangku.utils.BeanConvertor;
import com.example.jianancangku.utils.LogUtils;
import com.example.jianancangku.utils.StatusBarUtils;
import com.example.jianancangku.view.PopWindowsUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bingoogolapple.qrcode.core.BarcodeType;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

import static com.example.jianancangku.args.Constant.qrgohomeAddress;
import static com.example.jianancangku.args.Constant.qroutThingHouseAdrress;
import static com.example.jianancangku.args.Constant.qrouthomeAddress;

public class QrCodeActivity extends BaseActivity implements QRCodeView.Delegate, View.OnClickListener {
    @BindView(R.id.iv_back_qrcode)
    ImageView iv_back;
    @BindView(R.id.thingnumber_txt)
    TextView thingnumber_txt;

    private Unbinder unbinder;
    private String qrinterface;
    private ZXingView zXingView;
    private int popeditnumberrlayoutid = R.layout.qrcode_msg_layout;
    private static final int REQUEST_CODE = 453876;


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrcode_layout);
        unbinder = ButterKnife.bind((Activity) context);
        StatusBarUtils.setColor(this, getColor(R.color.glay));
        init();


    }


    private void init() {
        initBundle();
        initViews();
    }

    @SuppressLint("NewApi")
    private void initViews() {
        iv_back.setOnClickListener((View.OnClickListener) this);
        thingnumber_txt.setOnClickListener((View.OnClickListener) context);

        zXingView = findViewById(R.id.zxingview);
        zXingView.setDelegate(this);
    }

    private void initBundle() {
        String a = getIntent().getStringExtra("outinterface");
        assert a != null;
        if (a.equals("go"))
            qrinterface = qrgohomeAddress;
        if (a.equals("out"))
            qrinterface = qrouthomeAddress;
        if (a.equals("houseAll"))
            qrinterface= qroutThingHouseAdrress;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        zXingView.startSpotAndShowRect(); // 显示扫描框，并开始识别
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
//               getDatas(data);
            }


        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        zXingView.closeFlashlight();
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        PopWindowsUtils.getmInstance().showQRerrorPopupWindow(context,"二维码异常 请重新扫描二维码");
//        getDatas("2000000000130401");
//        PopWindowsUtils.getmInstance().showcenterPopupWindow(context);
//        PopWindowsUtils.getmInstance().showQRerrorPopupWindow(context, "test");

    }

    @Override
    protected void onStop() {
        zXingView.closeFlashlight();
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        zXingView.startCamera();
        zXingView.startSpotAndShowRect();
        zXingView.startSpot();
        zXingView.setType(BarcodeType.ALL, null);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        getDatas(result);
    }

    private void showPopwindow() {
        showeditnumberPopupWindow(context, R.layout.qrcode_layout, 1.0f);
    }

    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {
        String tipText = zXingView.getScanBoxView().getTipText();
        String ambientBrightnessTip = "\n环境过暗，请打开闪光灯";
        if (isDark) {
            //在这里加入了根据传感器光线暗的时候自动打开闪光灯
            if (!tipText.contains(ambientBrightnessTip)) {
                zXingView.getScanBoxView().setTipText(tipText + ambientBrightnessTip);
                zXingView.openFlashlight();
            }
        } else {
            if (tipText.contains(ambientBrightnessTip)) {
                tipText = tipText.substring(0, tipText.indexOf(ambientBrightnessTip));
                zXingView.getScanBoxView().setTipText(tipText);
            }
        }

    }

    /**
     * 处理打开相机出错
     */
    @Override
    public void onScanQRCodeOpenCameraError() {
        PopWindowsUtils.getmInstance().showQRerrorPopupWindow(context,"二维码异常 请重新扫描二维码");
//        ToastUtils.showToast(QrCodeActivity.this, "扫描二维码出错");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back_qrcode:
                onBackPressed();
                break;
            case R.id.thingnumber_txt:
                showPopwindow();
                break;

        }
    }

    private void getDatas(String result) {
        if (TextUtils.isEmpty(qrinterface) || qrinterface == null)
            return;

        HttpParams params = initHttpParams(result);

        OkGo.<BaseData<QrBean>>post(qrinterface)
                .tag(context)
                .params(params)
                .execute(new com.lzy.okgo.callback.AbsCallback<BaseData<QrBean>>() {
                    @Override
                    public BaseData<QrBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response);
                        final BaseData baseData = BeanConvertor.convertBean(response.body().string(), BaseData.class);
                        final QrBean qrBean = BeanConvertor.convertBean(baseData.getDatas(), QrBean.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                               if (!baseData.isSuccessed())
                                    PopWindowsUtils.getmInstance().showQRerrorPopupWindow(context, qrBean.getError());
                               else{
                                   String msg=null;
                                   if (qrinterface.equals(qrgohomeAddress))
                                       msg="扫描入库成功" ;
                                   else if (qrinterface.equals(qrouthomeAddress))
                                       msg="扫描出库成功" ;
                                   else if (qrinterface.equals(qroutThingHouseAdrress))
                                       msg="包裹打包成功" ;

                                   PopWindowsUtils.getmInstance().showcenterPopupWindow(context,msg);
                               }
                            }
                        });

                        return baseData;
                    }

                    @Override
                    public void onStart(Request<BaseData<QrBean>, ? extends Request> request) {

                    }

                    @Override
                    public void onSuccess(Response<BaseData<QrBean>> response) {

                    }
                });

    }

    private HttpParams initHttpParams(String result) {
        HttpParams params=new HttpParams();
        params.put("KEY", Constant.KEY);
        params.put("package_sn", result);
        return params;
    }

    public void showeditnumberPopupWindow(final Context context, int fatherLayoutId, float backAlpha) {
        @SuppressLint("InflateParams")
        View more_icon_view = LayoutInflater.from(context).inflate(popeditnumberrlayoutid, null);
        setBackGroundAlpha(1.0f, context);
        PopupWindow popupWindow = new PopupWindow(more_icon_view, LinearLayout.LayoutParams.WRAP_CONTENT, 450, true);
        popupWindow.setContentView(more_icon_view);
        EditText editText = more_icon_view.findViewById(R.id.edit_number_pop);
        Button surebtn = more_icon_view.findViewById(R.id.btn_number_pop);
        surebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getDatas(editText.getText().toString().trim());
                        popupWindow.dismiss();
                    }
                });
            }
        });
        popupWindow.showAtLocation(more_icon_view, Gravity.CENTER, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackGroundAlpha(1.0f, context);

            }
        });


    }

}
