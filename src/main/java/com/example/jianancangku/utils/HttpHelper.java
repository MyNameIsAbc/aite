package com.example.jianancangku.utils;

import com.example.jianancangku.callback.ICallback;
import com.example.jianancangku.callback.IHttpProcessor;

import java.net.URLEncoder;
import java.util.Map;

public class HttpHelper implements IHttpProcessor {
    /**
     * 框架代理类
     */
    private static HttpHelper mInstance;
    //    private Map<String, Object> mParams;
    private static IHttpProcessor mIHttpProcessor = null;

    private HttpHelper() {
//        mParams = new HashMap<>();
    }

    public static HttpHelper obtain() {
        synchronized (HttpHelper.class) {
            if (mInstance == null)
                mInstance = new HttpHelper();
        }
        return mInstance;
    }

    public static void init(IHttpProcessor httpProcessor) {
        mIHttpProcessor = httpProcessor;
    }

    /**
     * 代理不做事 交给真正该做事的
     * @param url
     * @param params
     * @param iCallback
     */
    @Override
    public void post(String url, Map<String, Object> params, ICallback iCallback) {
        String finalurl = appendparams(url, params);
        mIHttpProcessor.post(finalurl, params, iCallback);
    }



    @Override
    public void get(String url, Map<String, Object> params, ICallback iCallback) {
        String finalurl = appendparams(url, params);
        mIHttpProcessor.get(finalurl, params, iCallback);
    }

    /**
     * append附加的意思
     * @param url
     * @param params
     * @return
     */
    private String appendparams(String url, Map<String, Object> params) {
        if (params == null || params.isEmpty())
            return url;
        StringBuilder urlBuilder = new StringBuilder(url);
        if (urlBuilder.indexOf("?") <= 0)
            urlBuilder.append("?");
        else if (!urlBuilder.toString().endsWith("?"))
            urlBuilder.append("&");

        for (Map.Entry<String, Object> entry : params.entrySet())
            urlBuilder.append(entry.getKey()).append("=").append(encode(entry.getValue().toString()));
        return urlBuilder.toString();
    }

    /**
     * throw抛出的意思
     * @param string
     * @return
     */
    public static String encode (String string){
        try{
            return URLEncoder.encode(string,"utf-8");
        }catch (Exception e){
            LogUtils.e("参数转换异常",e.toString());
            throw new RuntimeException(e);
        }
    }
}