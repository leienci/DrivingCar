package com.test.drivingcar.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.viewbinding.ViewBinding;

import com.test.drivingcar.R;
import com.test.drivingcar.utils.ActivityManager;
import com.test.drivingcar.utils.StatusBarUtils;


/**
 * @author duoma
 * @date 2019/02/22
 */
public abstract class BaseActivity<T extends ViewBinding> extends FragmentActivity implements View.OnClickListener {

    public static final String TAG = "BaseActivity";
    private T mBinding;
    protected FragmentActivity instance;
    protected abstract T getViewBinding();

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract void initData();

    public abstract void onBaseClick(View v);

    protected void setStatusBar() {
        StatusBarUtils.setColor(this, ContextCompat.getColor(this, R.color.white));
    }


    protected void onCreateBefore() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        onCreateBefore();
        super.onCreate(savedInstanceState);

        // 强制指定纵向(解决设置 windowIsTranslucent = true, screenOrientation 屏幕固定同时设置，安卓8.0闪退的问题)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

        mBinding = getViewBinding();
        setContentView(mBinding.getRoot());

        instance = this;
        initView(savedInstanceState);
        // 放在初始化控件后面
        setStatusBar();
        initData();

        // 添加当前Activity到堆栈
        ActivityManager.getInstance().addActivity(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 移除Activity
        ActivityManager.getInstance().removeActivity(this);
    }

    @Override
    public void onClick(View v) {
        onBaseClick(v);
    }

    @Override
    public void finish() {
        super.finish();
    }



}
