package com.aite.alipaylibrary.wechat.wxapi;

import android.app.Activity;
import android.content.Context;

import com.lzy.basemodule.base.BaseApp;
import com.lzy.basemodule.logcat.LogUtils;
import com.lzy.basemodule.util.toast.ToastUtils;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

/**
 * @Auther: liziyang
 * @datetime: 2019-12-16
 * @desc:
 */
public class WechatUtils {

    //授权
    public static void authorization(SHARE_MEDIA share_media, Activity context,WechatInformationInterface wechatInformationInterface) {
        UMShareAPI.get(context).getPlatformInfo(context, share_media, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                LogUtils.d("onStart " + "授权开始");
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                LogUtils.d("onComplete " + "授权完成");

                //sdk是6.4.4的,但是获取值的时候用的是6.2以前的(access_token)才能获取到值,未知原因
                String uid = map.get("uid");
                String openid = map.get("openid");//微博没有
                String unionid = map.get("unionid");//微博没有
                String access_token = map.get("access_token");
                String refresh_token = map.get("refresh_token");//微信,qq,微博都没有获取到
                String expires_in = map.get("expires_in");
                String name = map.get("name");
                String gender = map.get("gender");
                String iconurl = map.get("iconurl");
                wechatInformationInterface.ongetMsg(map);

                ToastUtils.showToast(BaseApp.getContext(), "name=" + name + ",gender=" + gender);

                //拿到信息去请求登录接口。。。

            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                LogUtils.d("onError " + "授权失败");
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                LogUtils.d("onCancel " + "授权取消");
            }
        });
    }
    public interface WechatInformationInterface {
        void ongetMsg(Map<String, String> map);
    }


}
