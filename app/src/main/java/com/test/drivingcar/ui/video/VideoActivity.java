package com.test.drivingcar.ui.video;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.test.drivingcar.base.BaseActivity;
import com.test.drivingcar.databinding.FragmentVideoPlayerBinding;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

public class VideoActivity extends BaseActivity<FragmentVideoPlayerBinding> {

    private static Intent intent;
    private FragmentVideoPlayerBinding mBinding;
    private StandardGSYVideoPlayer detailPlayer;
    private OrientationUtils orientationUtils;
    private GSYVideoOptionBuilder gsyVideoOption;
    private boolean isPlay;
    private boolean isPause;

    public static void start(Context context, String url){
        intent = new Intent(context, VideoActivity.class);
        intent.putExtra("url",url);
        context.startActivity(intent);
    }


    @Override
    protected FragmentVideoPlayerBinding getViewBinding() {
        mBinding = FragmentVideoPlayerBinding.inflate(getLayoutInflater());
        return mBinding;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {

        mBinding.tvBack.setOnClickListener(v -> {
            finish();
        });

        String url = intent.getStringExtra("url");

        detailPlayer = mBinding.tvPlayer;

        //增加封面
        ImageView imageView = new ImageView(instance);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);


        //增加title
        detailPlayer.getTitleTextView().setVisibility(View.GONE);
        detailPlayer.getBackButton().setVisibility(View.GONE);

        //外部辅助的旋转，帮助全屏
        orientationUtils = new OrientationUtils(instance, detailPlayer);
        //初始化不打开外部的旋转
        orientationUtils.setEnable(false);

        gsyVideoOption = new GSYVideoOptionBuilder();
        gsyVideoOption.setThumbImageView(imageView)
                .setIsTouchWiget(true)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setAutoFullWithSize(false)
                .setShowFullAnimation(false)
                .setNeedLockFull(true)
                .setUrl(url)
                .setCacheWithPlay(false)
                .setVideoTitle("视频")
                .setVideoAllCallBack(new GSYSampleCallBack() {
                    @Override
                    public void onPrepared(String url, Object... objects) {
                        super.onPrepared(url, objects);
                        //开始播放了才能旋转和全屏
                        //orientationUtils.setEnable(detailPlayer.isRotateWithSystem());
                        isPlay = true;
                    }

                    @Override
                    public void onQuitFullscreen(String url, Object... objects) {
                        super.onQuitFullscreen(url, objects);
                        // ------- ！！！如果不需要旋转屏幕，可以不调用！！！-------
                        // 不需要屏幕旋转，还需要设置 setNeedOrientationUtils(false)
                        if (orientationUtils != null) {
                            orientationUtils.backToProtVideo();
                        }
                    }
                }).setLockClickListener(new LockClickListener() {
                    @Override
                    public void onClick(View view, boolean lock) {
                        if (orientationUtils != null) {
                            //配合下方的onConfigurationChanged
                            orientationUtils.setEnable(!lock);
                        }
                    }
                }).build(detailPlayer);

        detailPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //直接横屏
                // ------- ！！！如果不需要旋转屏幕，可以不调用！！！-------
                // 不需要屏幕旋转，还需要设置 setNeedOrientationUtils(false)
                orientationUtils.resolveByClick();
                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                detailPlayer.startWindowFullscreen(instance, true, true);
            }
        });
    }

    @Override
    public void onBaseClick(View v) {

    }

    @Override
    public void onPause() {
        detailPlayer.getCurrentPlayer().onVideoPause();
        super.onPause();
        isPause = true;
    }

    @Override
    public void onResume() {
        detailPlayer.getCurrentPlayer().onVideoResume(false);
        super.onResume();
        isPause = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isPlay) {
            detailPlayer.getCurrentPlayer().release();
        }
        if (orientationUtils != null)
            orientationUtils.releaseListener();
    }


    /**
     * orientationUtils 和  detailPlayer.onConfigurationChanged 方法是用于触发屏幕旋转的
     */
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //如果旋转了就全屏
        if (isPlay && !isPause) {
            detailPlayer.onConfigurationChanged(this, newConfig, orientationUtils,
                    true, true);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // ------- ！！！如果不需要旋转屏幕，可以不调用！！！-------
        // 不需要屏幕旋转，还需要设置 setNeedOrientationUtils(false)
        if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK){
            if (orientationUtils != null) {
                orientationUtils.backToProtVideo();
            }
            if (GSYVideoManager.backFromWindowFull(this)) {
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
