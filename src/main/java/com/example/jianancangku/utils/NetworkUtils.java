package com.example.jianancangku.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.example.jianancangku.App;


/**
 * Created by l on 2018/3/6.
 */

public class NetworkUtils {
    /**
     * 得到当前的手机蜂窝网络信号强度
     * 获取LTE网络和3G/2G网络的信号强度的方式有一点不同，
     * LTE网络强度是通过解析字符串获取的，
     * 3G/2G网络信号强度是通过API接口函数完成的。
     * asu 与 dbm 之间的换算关系是 dbm=-113 + 2*asu
     */

    public static int getDdmLevel(int i) {
        if (i > -85) {
            return 4;
        } else if (i > -95) {
            return 3;
        } else if (i > -105) {
            return 2;
        } else {
            return 1;
        }
    }

    /**
     * 获取运营商
     */
    public static String getOperatorName(Context context) {
        String provider = "unknown";
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            @SuppressLint("MissingPermission") String IMSI = telephonyManager.getSubscriberId();
            //Log.v("tag", "getProvider.IMSI:" + IMSI);
            if (IMSI == null) {
                if (TelephonyManager.SIM_STATE_READY == telephonyManager
                        .getSimState()) {
                    String operator = telephonyManager.getSimOperator();
                    Log.v("tag", "getProvider.operator:" + operator);
                    if (operator != null) {
                        if (operator.equals("46000")
                                || operator.equals("46002")
                                || operator.equals("46007")) {
                            provider = "中国移动";
                        } else if (operator.equals("46001")) {
                            provider = "中国联通";
                        } else if (operator.equals("46003")) {
                            provider = "中国电信";
                        }
                    }
                }
            } else {
                if (IMSI.startsWith("46000") || IMSI.startsWith("46002")
                        || IMSI.startsWith("46007")) {
                    provider = "中国移动";
                } else if (IMSI.startsWith("46001")) {
                    provider = "中国联通";
                } else if (IMSI.startsWith("46003")) {
                    provider = "中国电信";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return provider;
    }

    public static int getWifiDbm(Context context) {
        Context mContext;
        if (context == null) {
            mContext = App.getContext().getApplicationContext();
        } else {
            mContext = context;
        }
        return ((WifiManager) mContext.getSystemService(Context.WIFI_SERVICE)).getConnectionInfo().getRssi();
    }

    public static int getWifiLevel(int dbm) {
        if (dbm < 0 && dbm > -55) {
            return 3;
        } else if (dbm > -70) {
            return 2;
        } else if (dbm >= -100) {
            return 1;
        } else {
            return 0;
        }
    }

    public static boolean isNetworkAvailable(Context mContext) {
        Context context = mContext;
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        } else {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
