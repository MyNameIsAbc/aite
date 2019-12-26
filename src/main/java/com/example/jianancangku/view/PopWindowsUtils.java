package com.example.jianancangku.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jianancangku.R;
import com.example.jianancangku.args.Constant;
import com.example.jianancangku.bean.AreaCodeBean;
import com.example.jianancangku.bean.BaseData;
import com.example.jianancangku.ui.activity.FindMsgActivity;
import com.example.jianancangku.ui.activity.QrCodeActivity;
import com.example.jianancangku.view.adpter.AreaCodeAdapter;

import java.util.List;


public class PopWindowsUtils {
    private static PopWindowsUtils mInstance;
    private int popDialoglayoutId = R.layout.number_poplayout;
    private int popcenterlayoutid = R.layout.fixthing_success_pop_layout;
    private int popqrMsglayoutid = R.layout.qrcode_msg_layout;
    private int popbottomlayoutid = R.layout.bottom_pop_two_btn_layout;
    private int bookbarlayoutId = R.layout.bookbar_pop_layout;
    private int showdialoglayoutid = R.layout.unuser_pop_layout;
    private int showfindWorkerlayoutid = R.layout.find_worker_poplayout;
    private int findkeyLayoutid = R.layout.findkey_pop_layout;
    private int recyLayoutid = R.layout.pop_list_layout;
    private int backpostion = 0;


    private PopupWindow popupWindow;

    public static PopWindowsUtils getmInstance() {
        if (mInstance == null) {
            synchronized (PopWindowsUtils.class) {
                if (mInstance == null) {
                    mInstance = new PopWindowsUtils();
                }
            }
        }
        return mInstance;
    }

    private PopWindowsUtils() {

    }

    public void showfindkeyPopupWindow(final Context context) {
        @SuppressLint("InflateParams") View more_icon_view = LayoutInflater.from(context).inflate(findkeyLayoutid, null);
        setBackGroundAlpha(0.6f, context);
        popupWindow = new PopupWindow(more_icon_view, 500, 230, true);
        popupWindow.setContentView(more_icon_view);
        popupWindow.showAtLocation(more_icon_view, Gravity.CENTER, 0, 0);
        Button phone = more_icon_view.findViewById(R.id.phone);
        Button email = more_icon_view.findViewById(R.id.email);
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FindMsgActivity.class);
                intent.putExtra("findkeyway", "phone");
                context.startActivity(intent);
                dismissPopWindow();
            }
        });
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FindMsgActivity.class);
                intent.putExtra("findkeyway", "email");
                context.startActivity(intent);
                dismissPopWindow();
            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackGroundAlpha(1.0f, context);

            }
        });

    }

    public void showfindWorkerPopupWindow(final Context context) {
        @SuppressLint("InflateParams") View more_icon_view = LayoutInflater.from(context).inflate(showfindWorkerlayoutid, null);
        setBackGroundAlpha(0.6f, context);
        popupWindow = new PopupWindow(more_icon_view, 500, 300, true);
        popupWindow.setContentView(more_icon_view);
        popupWindow.showAtLocation(more_icon_view, Gravity.CENTER, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackGroundAlpha(1.0f, context);

            }
        });

    }

    public void showdialogPopwindow(final Context context, showdialogPopwindowIcallback showdialogPopwindowIcallback, String name) {
        @SuppressLint("InflateParams") View more_icon_view = LayoutInflater.from(context).inflate(showdialoglayoutid, null);
        setBackGroundAlpha(0.6f, context);
        popupWindow = new PopupWindow(more_icon_view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setContentView(more_icon_view);
        TextView title = more_icon_view.findViewById(R.id.title);
        title.setText(name);
        Button ok_btn = more_icon_view.findViewById(R.id.ok_btn);
        Button un_btn = more_icon_view.findViewById(R.id.un_btn);
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialogPopwindowIcallback.call(true);
                popupWindow.dismiss();
            }
        });
        un_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialogPopwindowIcallback.call(false);
                popupWindow.dismiss();

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


    public void showcenterPopupWindow(final Context context, String msg) {
        @SuppressLint("InflateParams") View more_icon_view = LayoutInflater.from(context).inflate(popcenterlayoutid, null);
//        setBackGroundAlpha(1.0f, context);
        popupWindow = new PopupWindow(more_icon_view, LinearLayout.LayoutParams.WRAP_CONTENT, 450, true);
        popupWindow.setContentView(more_icon_view);
        TextView msg_center = more_icon_view.findViewById(R.id.msg_txt);
        if (msg == null) msg = "包裹打包成功";
        msg_center.setText(msg);
        popupWindow.showAtLocation(more_icon_view, Gravity.CENTER, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
//                setBackGroundAlpha(1.0f, context);
                if (context instanceof Activity)
                    ((Activity) context).finish();

            }
        });

    }

    public void showQRerrorPopupWindow(final Context context, String msg) {
        @SuppressLint("InflateParams") View more_icon_view = LayoutInflater.from(context).inflate(popqrMsglayoutid, null);
//        setBackGroundAlpha(1.0f, context);
        popupWindow = new PopupWindow(more_icon_view, LinearLayout.LayoutParams.WRAP_CONTENT, 450, true);
        popupWindow.setContentView(more_icon_view);
        TextView title = more_icon_view.findViewById(R.id.title);
        EditText edit_number_pop = more_icon_view.findViewById(R.id.edit_number_pop);
        Button btn_number_pop = more_icon_view.findViewById(R.id.btn_number_pop);
        btn_number_pop.setVisibility(View.GONE);
        edit_number_pop.setVisibility(View.GONE);
        title.setVisibility(View.GONE);
        TextView msg_txt = more_icon_view.findViewById(R.id.message_txt);
        msg_txt.setVisibility(View.VISIBLE);
        msg_txt.setText(msg);
        ImageView qr_img = more_icon_view.findViewById(R.id.qr_img);
        qr_img.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.qrcode_fail));
        qr_img.setVisibility(View.VISIBLE);
        popupWindow.showAtLocation(more_icon_view, Gravity.CENTER, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
//                setBackGroundAlpha(1.0f, context);
                if (context instanceof Activity)
                    ((Activity) context).finish();

            }
        });

    }

    public int showChoiceAreaPopupwindow(final Context context, View ui, AreaCodeAdapter areaCodeAdapter) {
        @SuppressLint("InflateParams") View more_icon_view = LayoutInflater.from(context).inflate(recyLayoutid, null);
        setBackGroundAlpha(0.6f, context);
        popupWindow = new PopupWindow(more_icon_view, 270, 450, true);
        popupWindow.setContentView(more_icon_view);
        RecyclerView recyclerView = more_icon_view.findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(areaCodeAdapter);
        popupWindow.showAsDropDown(ui, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackGroundAlpha(1.0f, context);
                dismissPopWindow();


            }
        });
        return backpostion;

    }


    public void showDownPopupWindow(final Context context, View mView, int fatherLayoutId, float backAlpha, String txt, View.OnClickListener numberClick) {
        @SuppressLint("InflateParams") View more_icon_view = LayoutInflater.from(context).inflate(popDialoglayoutId, null);
        setBackGroundAlpha(backAlpha, context);
        popupWindow = new PopupWindow(more_icon_view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setContentView(more_icon_view);
        TextView topTxt = more_icon_view.findViewById(R.id.phone_header_txt);
        topTxt.setText(txt);
        topTxt.setOnClickListener(numberClick);
        popupWindow.showAsDropDown(mView);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackGroundAlpha(1.0f, context);

            }
        });

    }

    public void showbokbarPopupWindow(final Context context, View mView, Icallback icallback) {
        @SuppressLint("InflateParams") View more_icon_view = LayoutInflater.from(context).inflate(bookbarlayoutId, null);
        setBackGroundAlpha(1f, context);
        popupWindow = new PopupWindow(more_icon_view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setContentView(more_icon_view);
        TextView my = more_icon_view.findViewById(R.id.my_pop);
        TextView worker = more_icon_view.findViewById(R.id.worker_pop);
        my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                icallback.call("1");
                popupWindow.dismiss();

            }
        });
        worker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                icallback.call("2");
                popupWindow.dismiss();

            }
        });
        popupWindow.showAsDropDown(mView);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackGroundAlpha(1.0f, context);
                popupWindow.dismiss();

            }
        });

    }

    public void showfixthingPopupWindow(final Context context, View mView, int fatherLayoutId, float backAlpha, String txt, View.OnClickListener numberClick) {
        @SuppressLint("InflateParams") View more_icon_view = LayoutInflater.from(context).inflate(popDialoglayoutId, null);
        setBackGroundAlpha(1f, context);
        popupWindow = new PopupWindow(more_icon_view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setContentView(more_icon_view);
        TextView topTxt = more_icon_view.findViewById(R.id.phone_header_txt);
        topTxt.setText("员工订单");
        topTxt.setOnClickListener(numberClick);
        popupWindow.showAsDropDown(mView);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackGroundAlpha(1.0f, context);

            }
        });

    }

    public void dismissPopWindow() {
        if (popupWindow != null) popupWindow.dismiss();
    }

    void setBackGroundAlpha(float alpha, Context context) {

        WindowManager.LayoutParams layoutParams = ((AppCompatActivity) context).getWindow().getAttributes();
        layoutParams.alpha = alpha;
        ((AppCompatActivity) context).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        ((AppCompatActivity) context).getWindow().setAttributes(layoutParams);
    }


    public void showQRPopupWindow(Context context, BaseData baseData) {
        if (baseData.isSuccessed()) {
            showcenterPopupWindow(context, "");
        } else {
            showQRerrorPopupWindow(context, baseData.getErrorMsg());
        }
    }

    public void showBottomPopupWindow(Context context, Icallback icallback) {

        @SuppressLint("InflateParams") View more_icon_view = LayoutInflater.from(context).inflate(popbottomlayoutid, null);
        setBackGroundAlpha(0.6f, context);
        popupWindow = new PopupWindow(more_icon_view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        Button gohouseworker = more_icon_view.findViewById(R.id.gohouseworker);
        Button outhouse = more_icon_view.findViewById(R.id.outhouse);
        gohouseworker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                icallback.call("入库员");
                popupWindow.dismiss();
            }
        });
        outhouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                icallback.call("出库员");
                popupWindow.dismiss();
            }
        });
        popupWindow.setContentView(more_icon_view);
        popupWindow.showAtLocation(more_icon_view, Gravity.BOTTOM, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackGroundAlpha(1.0f, context);
                popupWindow.dismiss();

            }
        });
    }

    public interface Icallback {
        String call(String msg);
    }

    public interface showdialogPopwindowIcallback {
        void call(boolean isok);
    }


}
