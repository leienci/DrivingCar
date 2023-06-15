package com.test.drivingcar.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.drivingcar.base.BaseFragment;
import com.test.drivingcar.databinding.FragmentHomeBinding;
import com.test.drivingcar.databinding.FragmentMineBinding;

public class TabMineFragment extends BaseFragment<FragmentMineBinding> {
    private FragmentMineBinding mBinding;

    @Override
    public FragmentMineBinding getViewBinding(LayoutInflater inflater, ViewGroup container) {
        mBinding = FragmentMineBinding.inflate(inflater, container, false);
        return mBinding;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {
        mBinding.tvYszc.setOnClickListener(v -> {
            WebViewActivity.start(instance, "https://www.cbg.cn/privacyPolicy.html");
        });

        mBinding.tvYhxy.setOnClickListener(v -> {
            WebViewActivity.start(instance, "https://admin.qidian.qq.com/template/blue/website/agreement.html");
        });

    }

    @Override
    public void onBaseClick(View v) {

    }
}
