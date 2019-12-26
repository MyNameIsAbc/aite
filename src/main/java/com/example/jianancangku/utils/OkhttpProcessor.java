package com.example.jianancangku.utils;

import com.example.jianancangku.callback.ICallback;
import com.example.jianancangku.callback.IHttpProcessor;

import org.greenrobot.greendao.annotation.NotNull;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkhttpProcessor implements IHttpProcessor {
    /**
     * 执行任务类okhttp
     * ctrl+n 重写方法
     */
    private static final String TAG = "okhttpProcessor";
    private OkHttpClient mOkHttpClient;
    private android.os.Handler handler;


    public OkhttpProcessor() {
        mOkHttpClient = new OkHttpClient();
        handler = new android.os.Handler();
    }

    @Override
    public void post(String url, Map<String, Object> params, final ICallback iCallback) {
        RequestBody requestBody = appendbody(params);
//        final Request request = new Request.Builder().url(url).loginpost(requestBody).header("User-Agent","a").build();
        final Request request = new Request.Builder().url(url).post(requestBody).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iCallback.onFailure(e.getMessage());
                    }
                });

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                final String result = response.body().string();
                if (response.isSuccessful()) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {

//                            iCallback.onSuccess(result);

                        }
                    });

                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            iCallback.onFailure(result);

                        }
                    });

                }

            }
        });

    }

    private RequestBody appendbody(Map<String, Object> params) {
        FormBody.Builder body = new FormBody.Builder();
        if (params == null || params.isEmpty())
            return body.build();
        for (Map.Entry<String, Object> entry : params.entrySet())
            body.add(entry.getKey(), entry.getValue().toString());
        return body.build();
    }

    @Override
    public void get(String url, Map<String, Object> params, ICallback iCallback) {

    }
}