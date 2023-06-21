package com.test.drivingcar.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.drivingcar.base.BaseFragment;
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
        mbinding.btnLook.setOnClickListener(v -> {
            startActivity(new Intent(instance, VideoPlayActivity.class).putExtra("videoUrl","https://whois.pconline.com.cn/ipJson.jsp?json=true"));
        });
        mbinding.slPractice.setOnClickListener(v -> {
          startActivity(new Intent(instance,LearnActivity.class));
        });

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onBaseClick(View v) {

    }
}
