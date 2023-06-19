package com.test.drivingcar.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.drivingcar.base.BaseFragment;
import com.test.drivingcar.databinding.FragmentSubjectBinding;
import com.test.drivingcar.ui.video.VideoActivity;

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
        mbinding.videoView.setOnClickListener(v -> {
            VideoActivity.start(instance,"http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4");
        });
    }

    @Override
    public void onBaseClick(View v) {

    }
}
