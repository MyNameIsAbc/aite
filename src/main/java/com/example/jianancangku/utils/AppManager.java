package com.example.jianancangku.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;

import java.util.Stack;

/**
 * APP管理
 *
 * @author CAOYOU
 */
public class AppManager {
    public static Stack<Activity> mActivityStack;
    private static Stack<Service> mServiceStack;
    private static AppManager mAppManager;

    private AppManager() {
    }

    /**
     * 单一实例
     */
    public static AppManager getInstance() {
        if (mAppManager == null) {
            mAppManager = new AppManager();
        }
        if (mActivityStack == null) {
            mActivityStack = new Stack<Activity>();
        }
        return mAppManager;
    }

    /**
     * 添加Service到堆栈
     */
    public void addService(Service service) {
        if (mServiceStack == null) {
            mServiceStack = new Stack<Service>();
        }
        mServiceStack.add(service);
    }

    public void addActivity(Activity activity) {
        if (mActivityStack == null) {
            mActivityStack = new Stack<Activity>();
        }
        mActivityStack.add(activity);
    }

    /**
     * 获取栈顶Activity（堆栈中最后一个压入的）
     */
    public Activity getTopActivity() {
        Activity activity = mActivityStack.lastElement();
        return activity;
    }

    /**
     * 结束栈顶Activity（堆栈中最后一个压入的）
     */
    public void killTopActivity() {
        Activity activity = mActivityStack.lastElement();
        killActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void killActivity(Activity activity) {
        if (activity != null) {
            mActivityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定的Service
     */
    public void killService(Service service) {
        if (service != null) {
            mServiceStack.remove(service);
            service.stopSelf();
            service = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void killActivity(Class<?> cls) {
        for (Activity activity : mActivityStack) {
            if (activity.getClass().equals(cls)) {
                killActivity(activity);
            }
        }
    }

    /**
     * 结束所有Service
     */
    public void killAllService() {
        for (int i = 0, size = mServiceStack.size(); i < size; i++) {
            if (null != mServiceStack.get(i)) {
                mServiceStack.get(i).stopSelf();
            }
        }
        mServiceStack.clear();
    }

    /**
     * 结束所有Activity
     */
    public void killAllActivity() {
        for (int i = 0, size = mActivityStack.size(); i < size; i++) {
            if (null != mActivityStack.get(i)) {
                mActivityStack.get(i).finish();
            }
        }
        mActivityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {
        try {
            killAllActivity();
            killAllService();
            ActivityManager activityMgr = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.restartPackage(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
        }
    }
}
