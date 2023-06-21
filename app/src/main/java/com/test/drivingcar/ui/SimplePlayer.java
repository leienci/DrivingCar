package com.test.drivingcar.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.test.drivingcar.R;

public class SimplePlayer extends AppCompatActivity {

    StandardGSYVideoPlayer videoPlayer;
    OrientationUtils orientationUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videoplayer);
        init();
    }

    private void init() {
        videoPlayer = (StandardGSYVideoPlayer) findViewById(R.id.video_player);

        String source1 = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
        videoPlayer.setUp(source1, true, "测试视频");

        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(R.mipmap.bm_back_white);
        videoPlayer.setThumbImageView(imageView);

        videoPlayer.getTitleTextView().setVisibility(View.VISIBLE);
        videoPlayer.getBackButton().setVisibility(View.VISIBLE);

        orientationUtils = new OrientationUtils(this, videoPlayer);


        videoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orientationUtils.resolveByClick();
                videoPlayer.startWindowFullscreen(SimplePlayer.this, true, true);
            }
        });

        videoPlayer.setIsTouchWiget(true);
        videoPlayer.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        videoPlayer.startPlayLogic();
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoPlayer.onVideoPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoPlayer.onVideoResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
        if (orientationUtils != null)
            orientationUtils.releaseListener();
    }

    @Override
    public void onBackPressed() {
        videoPlayer.setVideoAllCallBack(null);
        super.onBackPressed();
    }
}
