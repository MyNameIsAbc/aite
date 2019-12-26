package com.example.base;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.mvp.BasePresenterImpl;
import com.example.mvp.BaseView;

/**
 * @Auther: liziyang
 * @datetime: 2019-12-03
 * @desc:
 */
public abstract class tBaseLazyFragment<V extends BaseView, T extends BasePresenterImpl<V>> extends tBaseFragment<V, T> implements View.OnClickListener, BaseView  {
    /**
     * Fragment 中的 View 是否创建完成
     */
    protected boolean isViewCreated;

    /**
     * Fragment 是否对用户可见
     */
    protected boolean isVisible = true;

    /**
     * Fragment 左右切换时，只在第一次显示时请求数据
     */
    protected boolean isFirstLoad = true;

    private boolean isVisibleToUser;


    public tBaseLazyFragment() {
    }

    /**
     * Fragment 中创建完成的回调方法
     *
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
        lazyLoad();
    }
    /**
     * 使用 {@link
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            this.isVisibleToUser = true;
            onVisible();
        } else {
            this.isVisibleToUser = false;
            onInvisible();
        }
    }



    public void onInvisible() {

    }

    /**
     * 使用FragmentTransaction show/hide
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden){
            isVisible = false;
            onInvisible();
        }else {
            isVisible = true;
            onVisible();
        }
    }

    /**
     * 使用{@link FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT}
     * 顾名思义 就是只走当前展示的Fragment 的onResume 切换过程中if (targetFragment 在缓存栈中)
     * 当前curFragment onPause targetFragment onResume  else targetFragment 走全部启动生命周期
     * @see androidx.fragment.app.FragmentPagerAdapter 在加载Adapter数据是对缓存栈外的Fragmen不做销毁
     * ，只销毁view即 onDestoryView 不 onDestory
     * @see FragmentStatePagerAdapter 在加载Adpter数据时对缓存栈外的Fragment完全销毁
     * ，即onDestory onDetach
     */
    @Override
    public void onResume() {
        super.onResume();
        isVisibleToUser = true;
        onVisible();
    }

    @Override
    public void onPause() {
        super.onPause();
        isVisibleToUser = false;
        onInvisible();

    }

    public void onVisible() {
        lazyLoad();
    }


    /**
     * (1) isViewCreated 参数在系统调用 onViewCreated 时设置为 true,这时onCreateView方法已调用完毕(一般我们在这方法
     * 里执行findviewbyid等方法),确保 loadData()方法不会报空指针异常。
     * <p>
     * (2) isVisible 参数在 fragment 可见时通过系统回调 setUserVisibileHint 方法设置为true,不可见时为false，
     * 这是 fragment 实现懒加载的关键。
     * <p>
     * (3) isFirstLoad 确保 ViewPager 来回切换时 TabFragment 的 loadData()方法不会被重复调用，loadData()在该
     * Fragment 的整个生命周期只调用一次,第一次调用 loadData()方法后马上执行 isFirst = false。
     */
    private void lazyLoad() {
        /**
         *这里进行双重标记判断,是因为setUserVisibleHint会多次回调,
         * 并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
         */
        if (isViewCreated && isVisible && isFirstLoad && isVisibleToUser) {
            loadData();
            isFirstLoad = firstLoad();
        }
    }

    protected boolean firstLoad(){
        return !isFirstLoad;
    }

    /**
     * 子类实现加载数据
     */
    public abstract void loadData();
    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onSmartLoadMore() {

    }

    @Override
    protected void onSmartRefresh() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dimissLoading() {

    }

    @Override
    public void showError(String msg) {

    }
}
