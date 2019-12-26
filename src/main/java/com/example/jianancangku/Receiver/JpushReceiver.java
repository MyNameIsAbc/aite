package com.example.jianancangku.Receiver;

import android.content.Context;
import android.util.Log;

import com.example.jianancangku.utils.AudioUtils;

import cn.jpush.android.api.CmdMessage;
import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.service.JPushMessageReceiver;

public class JpushReceiver extends JPushMessageReceiver {
    @Override
    public void onTagOperatorResult(Context context, JPushMessage jPushMessage) {
        Log.e("m_tag", "MyJPushMessageReceiver1: " );
        super.onTagOperatorResult(context, jPushMessage);
    }
    @Override
    public void onCheckTagOperatorResult(Context context, JPushMessage jPushMessage){
        Log.e("m_tag", "MyJPushMessageReceiver2: " );
        super.onCheckTagOperatorResult(context, jPushMessage);
    }
    @Override
    public void onAliasOperatorResult(Context context, JPushMessage jPushMessage) {
        Log.e("m_tag", "MyJPushMessageReceiver3: " );
        super.onAliasOperatorResult(context, jPushMessage);
    }

    @Override
    public void onMobileNumberOperatorResult(Context context, JPushMessage jPushMessage) {
        Log.e("m_tag", "MyJPushMessageReceiver4: " );
        super.onMobileNumberOperatorResult(context, jPushMessage);
    }

    //    private static final String TAG = "JIGUANG-jiannan";
//
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        try {
//            Bundle bundle = intent.getExtras();
//            assert bundle != null;
//            Logger.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
//
//            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
//                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
//                Logger.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
//                //send the Registration Id to your server...
//
//            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
//                Logger.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
//
//            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
//                Logger.d(TAG, "[MyReceiver] 接收到推送下来的通知");
//                int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
//                Logger.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
//
//            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
//                Logger.d(TAG, "[MyReceiver] 用户点击打开了通知");
//                //在这里做点击事件的处理
//                // 打开MainActivity，应用在后台时，会唤起至前台
//                Intent i = new Intent(context, MsgCenterActivity.class);
//                i.putExtras(bundle);
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(i);
//
//            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
//                Logger.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
//                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..
//
//            } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
//                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
//                Logger.w(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
//            } else {
//                Logger.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
//            }
//        } catch (Exception e) {
//
//        }
//
//    }
//
//    // 打印所有的 intent extra 数据
//    private static String printBundle(Bundle bundle) {
//        StringBuilder sb = new StringBuilder();
//        for (String KEY : bundle.keySet()) {
//            if (KEY.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
//                sb.append("\nKEY:").append(KEY).append(", value:").append(bundle.getInt(KEY));
//            } else if (KEY.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
//                sb.append("\nKEY:").append(KEY).append(", value:").append(bundle.getBoolean(KEY));
//            } else if (KEY.equals(JPushInterface.EXTRA_EXTRA)) {
//                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
//                    Logger.i(TAG, "This message has no Extra data");
//                    continue;
//                }
//
//                try {
//                    JSONObject json = new JSONObject(Objects.requireNonNull(bundle.getString(JPushInterface.EXTRA_EXTRA)));
//                    Iterator<String> it = json.keys();
//
//                    while (it.hasNext()) {
//                        String myKey = it.next();
//                        sb.append("\nKEY:").append(KEY).append(", value: [").append(myKey).append(" - ").append(json.optString(myKey)).append("]");
//                    }
//                } catch (JSONException e) {
//                    Logger.e(TAG, "Get message extra JSON error!");
//                }
//
//            } else {
//                sb.append("\nKEY:").append(KEY).append(", value:").append(bundle.get(KEY));
//            }
//        }
//        return sb.toString();
//    }
}
