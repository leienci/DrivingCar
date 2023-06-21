package com.test.drivingcar.ui;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.test.drivingcar.base.BaseActivity;
import com.test.drivingcar.databinding.ActivityLearnBinding;
import com.test.drivingcar.room.AppDB;
import com.test.drivingcar.room.bean.QuestionVo;
import com.test.drivingcar.ui.adapter.BottomListAdapter;
import com.test.drivingcar.ui.adapter.QuestionListAdapter;
import com.test.drivingcar.utils.UIUtils;
import com.xuexiang.rxutil2.rxjava.RxJavaUtils;
import com.xuexiang.rxutil2.rxjava.task.RxAsyncTask;

import java.util.ArrayList;
import java.util.List;

public class LearnActivity extends BaseActivity<ActivityLearnBinding> {
    private ActivityLearnBinding mBinding;
    private BottomSheetBehavior<View> behavior;
    private QuestionListAdapter questionListAdapter;
    private List<QuestionVo> questionVos = new ArrayList<>();
    private BottomListAdapter bottomListAdapter;

    @Override
    protected ActivityLearnBinding getViewBinding() {
        mBinding = ActivityLearnBinding.inflate(getLayoutInflater());
        return mBinding;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        behavior = BottomSheetBehavior.from(mBinding.bottomLayout);
        mBinding.ivBack.setOnClickListener(this::onBaseClick);
        mBinding.tvAnswer.setOnClickListener(v -> {
            mBinding.tvAnswer.setSelected(true);//选中状态
            mBinding.tvLook.setSelected(false);//未选中状态
            questionListAdapter.setMode(false);
        });
        mBinding.tvLook.setOnClickListener(v -> {
            mBinding.tvAnswer.setSelected(false);//未选中状态
            mBinding.tvLook.setSelected(true);//选中状态
            questionListAdapter.setMode(true);
        });
        mBinding.ivBack.setOnClickListener(v -> {
            finish();
        });
        mBinding.rlBottom.setOnClickListener(v -> {
            switchBhShow();
        });
    }

    @Override
    protected void initData() {
        getQuestions();

    }

    @Override
    public void onBaseClick(View v) {
    }

    private void switchBhShow() {
        int state = behavior.getState();//获取展开或者收起状态
        if (state == BottomSheetBehavior.STATE_COLLAPSED || state == BottomSheetBehavior.STATE_HIDDEN) {
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }

    private void initQuestionAdapter() {
        questionListAdapter = new QuestionListAdapter(questionVos);//实例化适配器
        mBinding.mViewPage.setAdapter(questionListAdapter);//绑定适配器
        mBinding.mViewPage.registerOnPageChangeCallback(onPageChangeCallback);

    }

    //viewPage切换页面的回调
    private ViewPager2.OnPageChangeCallback onPageChangeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            mBinding.mRv.scrollToPosition(position);
        }
    };

    private void getQuestions() {
        RxJavaUtils.executeAsyncTask(new RxAsyncTask<Object, Object>("") {

            @Override
            public Object doInIOThread(Object o) {
                questionVos = AppDB.getInstance().questionDao().queryAll(1, 1);
                return null;
            }

            @Override
            public void doInUIThread(Object o) {
                initQuestionAdapter();
                initBottomAdapter();
                mBinding.tvIndex.setText(questionVos.size()+"");
            }
        });

//        questionVos = AppDB.getInstance().questionDao().queryAll(1, 1);
//        initQuestionAdapter();


    }

    private void initBottomAdapter(){
        bottomListAdapter = new BottomListAdapter(questionVos);
        mBinding.mRv.setAdapter(bottomListAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(instance,6);
        mBinding.mRv.setLayoutManager(layoutManager);
        if (questionVos!=null&&questionVos.size()>=42){
            //把recycleVi的高度设置成屏幕高度-600；
            mBinding.mRv.getLayoutParams().height = getResources().getDisplayMetrics().heightPixels- UIUtils.dp2px(300);
        }

    }
}
