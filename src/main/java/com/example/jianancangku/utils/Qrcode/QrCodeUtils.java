package com.example.jianancangku.utils.Qrcode;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;

import com.example.jianancangku.utils.LogUtils;
import com.example.jianancangku.utils.http.HttpOkgoUtils;

import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

public class QrCodeUtils {
    private static QrCodeUtils mInstance;

    public static QrCodeUtils getmInstance() {
        if (mInstance == null) {
            synchronized (QrCodeUtils.class) {
                if (mInstance == null) {
                    mInstance = new QrCodeUtils();
                }
            }
        }
        return mInstance;
    }

    private QrCodeUtils() {

    }

    private static boolean isActivity(Context context) {
        return context instanceof Activity;
    }


    /**
     *
     * @param context
     * @param english
     * @param view
     */
    @SuppressLint("StaticFieldLeak")
    public final void createEnglishQRCode(Context context, String english, ImageView view) {
        createEnglishQRCode(context,english,view,0,null);
    }

    /**
     *
     * @param context
     * @param english
     * @param view
     * @param heightwidth
     * @param color "#ff0000"
     */
    @SuppressLint("StaticFieldLeak")
    public final void createEnglishQRCode(Context context, String english, ImageView view, float heightwidth, String color) {
        if (!isActivity(context)) return;
        if (heightwidth==0)heightwidth=200;
        if (color==null||color.equals(""))color="#FF333333";
        float finalHeightwidth = heightwidth;
        String finalColor = color;

        new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... params) {
                return QRCodeEncoder.syncEncodeQRCode(english, BGAQRCodeUtil.dp2px(context, finalHeightwidth), Color.parseColor(finalColor));
            }
            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (bitmap != null) {
                    view.setImageBitmap(bitmap);
                } else {
                    LogUtils.e("生成二维码出错");

                }
            }
        }.execute();
    }
}
