package com.example.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.mvp.BasePresenterImpl;
import com.example.mvp.BaseView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import java.lang.reflect.ParameterizedType;

public abstract class mBaseFragment<V extends BaseView, T extends BasePresenterImpl<V>> extends Fragment {
    public Context context;
    public T mPresenter;
    //下拉刷新
    protected abstract boolean isUseMvp();
    protected abstract void onSmartRefresh();
    protected abstract void onSmartLoadMore();

    protected int mCurrentPage = 1;

    protected boolean hasMore = false;

    private SmartRefreshLayout smartRefreshLayout;

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public Context getContext() {
        return super.getContext();
    }




    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(context, clz));
    }

    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    public void startActivity(Class<?> clz, String tag, String extra) {
        Intent intent = new Intent(context, clz);
        intent.putExtra(tag, extra);
        startActivity(intent);
    }

    public <T> T getInstance(Object o, int i) {
        try {
            return ((Class<T>) ((ParameterizedType) (o.getClass()
                    .getGenericSuperclass())).getActualTypeArguments()[i])
                    .newInstance();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(context, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }



}
