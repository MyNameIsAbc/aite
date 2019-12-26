package com.example.jianancangku.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.jianancangku.args.Constant;
import com.example.jianancangku.utils.NetworkUtils;
import com.example.jianancangku.utils.SharePreferencesHelper;
import com.example.jianancangku.utils.StatusBarUtils;
import com.example.jianancangku.utils.ToastUtils;

import java.util.Arrays;

public abstract class BaseActivity extends AppCompatActivity implements
        View.OnTouchListener,
        ActivityCompat.OnRequestPermissionsResultCallback,
        View.OnClickListener {
    private static final int XSPEED_MIN = 700;
    private static final int XDISTANCE_MIN = 460;
    private float xDown;
    private float xMove;
    protected Context context;
    private VelocityTracker mVelocityTracker;

    public static final String PERMISSION_RECORD_AUDIO = android.Manifest.permission.RECORD_AUDIO;
    public static final String PERMISSION_GET_ACCOUNTS = android.Manifest.permission.GET_ACCOUNTS;
    public static final String PERMISSION_READ_PHONE_STATE = android.Manifest.permission.READ_PHONE_STATE;
    public static final String PERMISSION_CALL_PHONE = android.Manifest.permission.CALL_PHONE;
    public static final String PERMISSION_CAMERA = android.Manifest.permission.CAMERA;
    public static final String PERMISSION_ACCESS_FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String PERMISSION_COARSE_LOCATION = android.Manifest.permission.ACCESS_COARSE_LOCATION;
    public static final String PERMISSION_READ_EXTERNAL_STORAGE = android.Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String PERMISSION_WRITE_EXTERNAL_STORAGE = android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private static final String[] requestPermissions = {
            PERMISSION_RECORD_AUDIO, PERMISSION_GET_ACCOUNTS,
            PERMISSION_READ_PHONE_STATE, PERMISSION_CALL_PHONE,
            PERMISSION_CAMERA, PERMISSION_ACCESS_FINE_LOCATION,
            PERMISSION_COARSE_LOCATION, PERMISSION_READ_EXTERNAL_STORAGE,
            PERMISSION_WRITE_EXTERNAL_STORAGE,};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        StatusBarUtils.setColor(context, Color.WHITE);

    }

    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(this, clz));
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }


    public void applypermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkpermission = ContextCompat.checkSelfPermission(getApplicationContext(),
                    Arrays.toString(requestPermissions));
            if (checkpermission != PackageManager.PERMISSION_GRANTED) {//没有给权限
                Log.e("permission", "动态申请");
                ActivityCompat.requestPermissions((Activity) context, requestPermissions, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            Toast.makeText(context,"已授权",Toast.LENGTH_SHORT).show();
        } else {
//            Toast.makeText(context,"拒绝授权",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!NetworkUtils.isNetworkAvailable(context))
            ToastUtils.showToast(context, "请检查网络");
//        if (!Constant.isLogin) {
//            if (!(context instanceof LogInActivity) && !(context instanceof FindMsgActivity)) {
//                finish();
//                startActivity(new Intent(context, LogInActivity.class));
//            }
//        }

    }

    void setBackGroundAlpha(float alpha, Context context) {

        WindowManager.LayoutParams layoutParams = ((AppCompatActivity) context).getWindow().getAttributes();
        layoutParams.alpha = alpha;
        ((AppCompatActivity) context).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        ((AppCompatActivity) context).getWindow().setAttributes(layoutParams);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        createVelocityTracker(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDown = event.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
                xMove = event.getRawX();
                int distanceX = (int) (xMove - xDown);
                int xSpeed = getScrollVelocity();
                if (distanceX > XDISTANCE_MIN && xSpeed > XSPEED_MIN && xDown < 50) {
                    finish();
                }
                break;
            case MotionEvent.ACTION_UP:
                recycleVelocityTracker();
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 创建VelocityTracker对象，并将触摸content界面的滑动事件加入到VelocityTracker当中。
     *
     * @param event
     */
    private void createVelocityTracker(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }

    /**
     * 回收VelocityTracker对象。
     */
    private void recycleVelocityTracker() {
        mVelocityTracker.recycle();
        mVelocityTracker = null;
    }

    /**
     * 获取手指在content界面滑动的速度。
     *
     * @return 滑动速度，以每秒钟移动了多少像素值为单位。
     */
    private int getScrollVelocity() {
        mVelocityTracker.computeCurrentVelocity(1000);
        int velocity = (int) mVelocityTracker.getXVelocity();
        return Math.abs(velocity);
    }


}
//    protected abstract int getLayoutResId();
//        setContentView(getLayoutResId());
