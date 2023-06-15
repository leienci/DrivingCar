package com.test.drivingcar.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.test.drivingcar.R;
import com.test.drivingcar.base.BaseActivity;
import com.test.drivingcar.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    private ActivityMainBinding mBinding;
    protected TabHomeFragment tabHomeFragment;
    protected TabMineFragment tabMineFragment;

    @Override
    protected ActivityMainBinding getViewBinding() {
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        return mBinding;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initBottom();
        setIndexFragment(1);

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onBaseClick(View v) {

    }

    private void setIndexFragment(int position) {
        //开启事务
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideFragment(transaction);
        switch (position) {
            case 1:
                if (tabHomeFragment == null) {
                    tabHomeFragment = new TabHomeFragment();
                    transaction.add(R.id.mContent, tabHomeFragment);
                } else {
                    transaction.show(tabHomeFragment);
                }

                break;
            case 2:
                if (tabMineFragment == null) {
                    tabMineFragment = new TabMineFragment();
                    transaction.add(R.id.mContent, tabMineFragment);
                } else {
                    transaction.show(tabMineFragment);
                }
                break;
        }
        transaction.commitAllowingStateLoss();

    }

    private void hideFragment(FragmentTransaction transaction){
        if (tabHomeFragment!=null){
            transaction.hide(tabHomeFragment);
        }
        if (tabMineFragment!=null){
            transaction.hide(tabMineFragment);
        }

    }

    private void initBottom() {
        mBinding.navigation.setItemIconTintList(null);
        mBinding.navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.navigation_home) {
                    setIndexFragment(1);
                    return true;
                } else if (id == R.id.navigation_mine) {
                    setIndexFragment(2);
                    return true;
                }
                return false;
            }
        });
    }

}