package com.test.drivingcar.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewbinding.ViewBinding;



/**
 * @author duoma
 * @date 2019/02/22
 */
public abstract class BaseFragment<T extends ViewBinding> extends Fragment implements View.OnClickListener {

    public static final String TAG = "BaseFragment";
    private T mBinding;
    protected FragmentActivity instance;
    protected Dialog dialog;

    public abstract T getViewBinding(LayoutInflater inflater, ViewGroup container);

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract void initData();

    public abstract void onBaseClick(View v);

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        instance = (FragmentActivity) context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View rootView = inflater.inflate(getLayoutId(), container, false);
//        return rootView;
        mBinding = getViewBinding(inflater, container);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(savedInstanceState);
        initData();
    }

    public View findViewById(int id) {
        if (getView() != null) {
            return getView().findViewById(id);
        }
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onClick(View v) {
        onBaseClick(v);
    }
}
