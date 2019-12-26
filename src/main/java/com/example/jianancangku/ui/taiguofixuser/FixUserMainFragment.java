package com.example.jianancangku.ui.taiguofixuser;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.base.AppConstant;
import com.example.base.tBaseLazyFragment;
import com.example.jianancangku.R;
import com.example.jianancangku.args.Constant;
import com.example.jianancangku.bean.AllAloneBean;
import com.example.jianancangku.bean.BaseData;
import com.example.jianancangku.callback.AbsCallback;
import com.example.jianancangku.ui.activity.SettingmainActivity;
import com.example.jianancangku.ui.fragment.BaseFragment;
import com.example.jianancangku.utils.BeanConvertor;
import com.example.jianancangku.utils.LogUtils;
import com.example.jianancangku.utils.StatusBarUtils;
import com.example.jianancangku.utils.SystemUtil;
import com.example.jianancangku.view.adpter.BaseItemDecoration;
import com.example.jianancangku.view.adpter.MainIconRecyAdapter;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import butterknife.BindView;

import static com.example.jianancangku.args.Constant.isLogin;

/**
 * @Auther: valy
 * @datetime: 2019-11-27
 * @desc:
 */
public class FixUserMainFragment extends tBaseLazyFragment {
    @BindView(R.id.people_img)
    ImageView peopleImg;
    @BindView(R.id.woker)
    TextView woker;
    @BindView(R.id.worker_name)
    TextView workerName;
    @BindView(R.id.message_iv)
    ImageView messageIv;
    @BindView(R.id.icon_recy)
    RecyclerView iconRecy;
    private MainIconRecyAdapter mainIconRecyAdapter;


    @Override
    protected void initModel() {

    }

    @Override
    protected void initViews() {
        StatusBarUtils.setTransparent(context);
        iconRecy.setAdapter(mainIconRecyAdapter = new MainIconRecyAdapter(context, AppConstant.HOUSEFIXER.settingTv, AppConstant.HOUSEFIXER.settingImg));
        peopleImg.setOnClickListener(v -> startActivity(new Intent(context, SettingmainActivity.class)));
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.main_fragment;
    }


    @Override
    public void loadData() {

    }
}
