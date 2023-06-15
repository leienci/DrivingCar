package com.test.drivingcar.utils;

import android.app.Activity;

import java.util.Stack;

/**
 * Activity管理器
 *
 */
public class ActivityManager {

    private static Stack<Activity> mStackActivity;
    private static ActivityManager mInstance = null;

    private ActivityManager() {
        mStackActivity = new Stack<>();
    }

    public static ActivityManager getInstance() {
        if (mInstance == null) {
            synchronized (ActivityManager.class) {
                if (mInstance == null) {
                    mInstance = new ActivityManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activity != null) {
            mStackActivity.add(activity);
        }
    }

    /**
     * 移除指定的Activity
     */
    public void removeActivity(Activity activity) {
        if (activity != null) {
            mStackActivity.remove(activity);
        }
    }

    /**
     * 获取指定的Activity
     */
    public Activity getActivity(Class<?> cls) {
        for (Activity activity : mStackActivity) {
            if (activity.getClass().equals(cls)) {
                return activity;
            }
        }
        return null;
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = mStackActivity.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = mStackActivity.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
//            mStackActivity.remove(activity);
            activity.finish();
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : mStackActivity) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0; i < mStackActivity.size(); i++) {
            if (null != mStackActivity.get(i)) {
                mStackActivity.get(i).finish();
            }
        }
        mStackActivity.clear();
    }
}