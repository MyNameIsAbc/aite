package com.example.jianancangku.callback;

import java.util.List;

public interface ICallback {
    void onSuccess(String result, List<?> list);
    void onFailure(String e);
}