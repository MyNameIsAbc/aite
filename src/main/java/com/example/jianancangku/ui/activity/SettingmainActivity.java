package com.example.jianancangku.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.jianancangku.R;
import com.example.jianancangku.args.Constant;
import com.example.jianancangku.bean.BaseData;
import com.example.jianancangku.bean.MsgCenterbean;
import com.example.jianancangku.callback.AbsCallback;
import com.example.jianancangku.utils.BeanConvertor;
import com.example.jianancangku.utils.FileUtils;
import com.example.jianancangku.utils.LogUtils;
import com.example.jianancangku.utils.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SettingmainActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.number_setting_rala)
    RelativeLayout number_stting;
    @BindView(R.id.setting_rala)
    RelativeLayout setting_rela;
    @BindView(R.id.people_img)
    ImageView people_img;
    @BindView(R.id.woker)
    TextView worker;
    @BindView(R.id.worker_name)
    TextView worker_name;

    private int REQUEST_CODE_CHOOSE=1021;
    List<Uri> mSelected;
    Unbinder unbinder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_main_layout);
        unbinder = ButterKnife.bind((Activity) context);
        init();
    }

    private void init() {
        iv_back.setOnClickListener(this);
        number_stting.setOnClickListener(this);
        setting_rela.setOnClickListener(this);
        people_img.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Constant.peopeliconUrl != null)
            Glide.with(context).load(Constant.peopeliconUrl).transform(new CircleCrop()).into(people_img);
        if (Constant.peopelwoker != null)
            worker.setText(Constant.peopelwoker);
        if (Constant.peopelname != null)
            worker_name.setText(Constant.peopelname);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.number_setting_rala:
                startActivity(new Intent(context, UserfixActivity.class));
                break;
            case R.id.setting_rala:
                startActivity(new Intent(context, SettingActivity.class));
                break;
            case R.id.people_img:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Matisse.from(SettingmainActivity.this)
                                .choose(MimeType.ofImage(), false) // 选择 mime 的类型
                                .countable(true)
                                .maxSelectable(1) // 图片选择的最多数量
//                                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                                .thumbnailScale(0.85f) // 缩略图的比例
                                .imageEngine(new GlideEngine()) // 使用的图片加载引擎
                                .theme( R.style.Matisse_Dracula)
                                .forResult(REQUEST_CODE_CHOOSE); // 设置作为标记的请求码
                    }
                });

                break;


        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
            LogUtils.d(mSelected.get(0));
            initpostPicture(mSelected.get(0));
        }
    }

    private void initpostPicture(Uri uri) {
        File file= FileUtils.getFileByUri(uri,context);
        if (file==null){
            ToastUtils.showToast(context,"上传失败");
            return;
        }
        HttpParams params = new HttpParams();
        params.put("KEY", Constant.KEY);
        params.put("member_avatar", file);
        OkGo.<BaseData<MsgCenterbean.ListBean>>post(Constant.postPeopleIconAdrress)
                .tag(context)
                .params(params)
                .execute(new AbsCallback<BaseData<MsgCenterbean.ListBean>>() {
                    @Override
                    public BaseData<MsgCenterbean.ListBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response);
                        final BaseData baseData = BeanConvertor.convertBean(response.body().string(), BaseData.class);
                        LogUtils.d(baseData.getCode());
                        if (!baseData.isSuccessed())
                            ToastUtils.showToast(context,"上传失败");
                        else {
                            ToastUtils.showToast(context,"上传成功");
                            startActivity(new Intent(context,LogInActivity.class));
                        }
                            LogUtils.d(baseData.getErrorMsg());
                        return baseData;
                    }

                    @Override
                    public void onStart(Request<BaseData<MsgCenterbean.ListBean>, ? extends Request> request) {

                    }

                    @Override
                    public void onSuccess(Response<BaseData<MsgCenterbean.ListBean>> response) {

                    }
                });

    }
}
