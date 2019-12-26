package com.example.jianancangku.bean.basebean;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import com.lzy.okgo.model.HttpParams;

import java.io.File;
import java.io.Serializable;

public class ContentValue {

    public String key;
    public Object value;

    public ContentValue(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public Bundle fillBundle(Bundle bundle) {
        if (value instanceof String) {
            bundle.putString(key, (String) value);
        } else if (value instanceof Integer)
            bundle.putInt(key, Integer.parseInt(value.toString()));
        else if (value instanceof Boolean) {
            bundle.putBoolean(key, Boolean.parseBoolean(value.toString()));
        } else if (value instanceof Float) {
            bundle.putFloat(key, Float.parseFloat(value.toString()));
        } else if (value instanceof Serializable) {
            bundle.putSerializable(key, (Serializable) value);
        } else if (value instanceof Parcelable) {
            bundle.putParcelable(key, (Parcelable) value);
        }
        return bundle;
    }

    public HttpParams fillHttpParams(HttpParams httpParams) {
        if (value instanceof String) {
            httpParams.put(key, (String) value);
        } else if (value instanceof Integer)
            httpParams.put(key, Integer.parseInt(value.toString()));
        else if (value instanceof Boolean) {
            httpParams.put(key, Boolean.parseBoolean(value.toString()));
        } else if (value instanceof Float) {
            httpParams.put(key, Float.parseFloat(value.toString()));
        } else if (value instanceof File) {
            httpParams.put(key, (File) value);
        } else {
            httpParams.put(key, (String) value);
        }
        return httpParams;
    }

    public Intent fillIntent(Intent bundle) {
        if (value instanceof String) {
            bundle.putExtra(key, (String) value);
        } else if (value instanceof Integer)
            bundle.putExtra(key, Integer.parseInt(value.toString()));
        else if (value instanceof Boolean) {
            bundle.putExtra(key, Boolean.parseBoolean(value.toString()));
        } else if (value instanceof Float) {
            bundle.putExtra(key, Float.parseFloat(value.toString()));
        } else if (value instanceof Serializable) {
            bundle.putExtra(key, (Serializable) value);
        } else if (value instanceof Parcelable) {
            bundle.putExtra(key, (Parcelable) value);
        }
        return bundle;
    }

}
