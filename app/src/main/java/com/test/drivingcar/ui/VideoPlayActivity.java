package com.test.drivingcar.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.test.drivingcar.R;
import com.test.drivingcar.base.BaseActivity;
import com.test.drivingcar.databinding.ActivityVideoPlayBinding;
import com.test.drivingcar.utils.ImageLoadUtils;
import com.test.drivingcar.utils.StatusBarUtils;

import java.util.HashMap;
import java.util.Map;

public class VideoPlayActivity extends BaseActivity<ActivityVideoPlayBinding> {
    private ActivityVideoPlayBinding mBinding;
    private com.test.drivingcar.ui.view.SampleCoverVideo video_player;
    private GSYVideoOptionBuilder gsyVideoOptionBuilder;
    private String videoUrl, videoTitle = "驾考全流程 ";

    @Override
    protected void setStatusBar() {
        StatusBarUtils.setColor(instance, ContextCompat.getColor(this, R.color.black));
    }


    @Override
    protected ActivityVideoPlayBinding getViewBinding() {
        mBinding = ActivityVideoPlayBinding.inflate(getLayoutInflater());
        return mBinding;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        video_player = mBinding.videoPlayer;
        gsyVideoOptionBuilder = new GSYVideoOptionBuilder();

    }

    @Override
    protected void initData() {
        videoUrl = getIntent().getStringExtra("videoUrl");
//        videoUrl = ApiConst.HOME_BANNER_1;
        if (!TextUtils.isEmpty(videoUrl)) {
            initVideo();
        }
    }

    @Override
    public void onBaseClick(View v) {

    }


    @Override
    public void onPause() {
        super.onPause();
        video_player.onVideoPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        video_player.onVideoResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initVideo() {
        Map<String, String> header = new HashMap<>();
        header.put("ee", "33");

//        String coverUrl = videoVipDto.getCover();
//        ImageView imageView = new ImageView(instance);
//        ImageLoadUtils.getInstance().displayImage(instance, coverUrl, imageView);
        //防止错位，离开释放
        //gsyVideoPlayer.initUIState();
        ImageView imageView;
        imageView = new ImageView(this);
        String coverUrl = videoUrl + "?x-oss-process=video/snapshot,t_800,f_jpg,m_fast";
        ImageLoadUtils.getInstance().displayImage(this, coverUrl, imageView);
        gsyVideoOptionBuilder
                .setIsTouchWiget(false)
                .setThumbImageView(imageView)
                .setUrl(videoUrl)
                .setVideoTitle(videoTitle)
                .setCacheWithPlay(true)
                .setRotateViewAuto(true)
                .setLockLand(true)
                .setPlayTag(TAG)
                .setMapHeadData(header)
                .setShowFullAnimation(true)
                .setNeedLockFull(true)
//                .setPlayPosition(position)
                .setVideoAllCallBack(new GSYSampleCallBack() {
                    @Override
                    public void onAutoComplete(String url, Object... objects) {
                        super.onAutoComplete(url, objects);
                    }
                })
                .build(video_player);
        video_player.startPlayLogic();


        //增加title
//        video_player.getTitleTextView().setVisibility(View.GONE);

        //设置返回键
        video_player.getBackButton().setOnClickListener(v -> finish());

        //隐藏全屏按钮
        video_player.getFullscreenButton().setVisibility(View.GONE);

        video_player.getBtnReplay().setVisibility(View.VISIBLE);
        video_player.getBtnReplay().setOnClickListener(v -> {
            video_player.setSeekOnStart(0);
            video_player.startPlayLogic();
        });

        //设置全屏按键功能
//        video_player.getFullscreenButton().setVisibility(View.VISIBLE);
    }
}
