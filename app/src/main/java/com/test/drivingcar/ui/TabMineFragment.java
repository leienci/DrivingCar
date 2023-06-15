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

    }

    @Override
    public void onBaseClick(View v) {

    }
}
