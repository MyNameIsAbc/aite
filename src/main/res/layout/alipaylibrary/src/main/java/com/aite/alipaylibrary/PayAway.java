package com.aite.alipaylibrary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.aite.alipaylibrary.alppay.PayResult;
import com.alipay.sdk.app.PayTask;

import java.util.Map;

/**
 * @Auther: liziyang
 * @datetime: 2019-12-14
 * @desc:
 */
public class PayAway {

    private static Toast toast;

    public static void Alipay(final String order_id, final Activity activity, final Class cls) {
        new Thread(() -> {
            PayTask alipay = new PayTask(activity);
            final Map<String, String> result = alipay.payV2(order_id, true);
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    paraseResult(result, activity, cls);
                }
            });
        }).start();


    }

    private static void paraseResult(Map<String, String> map, Activity activity, Class cls) {
        PayResult payResult = new PayResult(map);
        // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
        @SuppressWarnings("unused")
        String resultInfo = payResult.getResult();

        String resultStatus = payResult.getResultStatus();
        if (toast == null)
            toast = Toast.makeText(activity, "", Toast.LENGTH_SHORT);
        // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
        if (TextUtils.equals(resultStatus, "9000")) {
            toast.setText("支付成功");
            toast.show();
            Intent intent2 = new Intent(activity, cls);
            activity.startActivity(intent2);
            activity.finish();
        } else {
            // 判断resultStatus 为非“9000”则代表可能支付失败
            // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
            if (TextUtils.equals(resultStatus, "8000")) {
                toast.setText("等待支付结果确认,最终交易是否成功以界面显示为准");
                toast.show();
            } else {
                // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                toast.setText("支付失败");
                toast.show();
            }
        }
    }

}
