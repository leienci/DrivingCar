package com.test.drivingcar.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.drivingcar.base.BaseFragment;
import com.test.drivingcar.databinding.FragmentHomeBinding;
import com.test.drivingcar.databinding.FragmentSubjectBinding;

public class SubjectFragment extends BaseFragment<FragmentSubjectBinding> {
    private FragmentSubjectBinding mbinding;
    private int subject;

    public static SubjectFragment getInstance(int subject) {
        SubjectFragment sf = new SubjectFragment();
        sf.subject = subject;
        return sf;

    }

    @Override
    public FragmentSubjectBinding getViewBinding(LayoutInflater inflater, ViewGroup container) {
        mbinding = FragmentSubjectBinding.inflate(inflater, container, false);
        return mbinding;
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
