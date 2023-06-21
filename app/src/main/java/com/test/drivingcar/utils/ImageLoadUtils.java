package com.test.drivingcar.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.io.File;

/**
 * 图片加载工具类
 *
 * @author duoma
 * @date 2019/02/22
 */
public class ImageLoadUtils {

    private ImageLoadUtils() {
    }

    private static volatile ImageLoadUtils mImageLoaderUtils = null;

    public static ImageLoadUtils getInstance() {
        if (mImageLoaderUtils == null) {
            synchronized (ImageLoadUtils.class) {
                if (mImageLoaderUtils == null) {
                    mImageLoaderUtils = new ImageLoadUtils();
                }
            }
        }
        return mImageLoaderUtils;
    }

    /**
     * 设置图片
     */
    public void displayImageGif(Context context, Object path, ImageView imageView) {

        RequestOptions requestOptions = new RequestOptions().centerInside()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);

        Glide.with(context.getApplicationContext())
                .asGif()
                .load(path)
                .apply(requestOptions)
                .into(imageView);
    }

    public void displayImageGif2(Context context, Object path, ImageView imageView) {
        //glide是在listener()方法中传入一个RequestListener来设置当图片资源准备好了以后自定义的操作的。
        Glide.with(context).asGif().load(path).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .listener(new RequestListener<GifDrawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
//首先设置imageView的ScaleType属性为ScaleType.FIT_XY，让图片不按比例缩放，把图片塞满整个View。
                        if (imageView.getScaleType() != ImageView.ScaleType.FIT_XY) {
                            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        }
                        // 得到当前imageView的宽度（我设置的是屏幕宽度），获取到imageView与图片宽的比例，然后通过这个比例去设置imageView的高
                        ViewGroup.LayoutParams params = imageView.getLayoutParams();
                        int vw = imageView.getWidth() - imageView.getPaddingLeft() - imageView.getPaddingRight();
                        float scale = (float) vw / (float) resource.getIntrinsicWidth();
                        int vh = Math.round(resource.getIntrinsicHeight() * scale);
                        params.height = vh + imageView.getPaddingTop() + imageView.getPaddingBottom();
                        imageView.setLayoutParams(params);
                        return false;
                    }

                })
                .into(imageView);

    }

    /**
     * 设置图片
     */
    public void displayImage(Context context, Object path, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions()
                .dontAnimate();

        Glide.with(context.getApplicationContext())
                .load(path)
                .apply(requestOptions)
                .into(imageView);
    }

    public void displayImage(Object path, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions()
                .dontAnimate();

        Glide.with(imageView.getContext())
                .load(path)
                .apply(requestOptions)
                .into(imageView);
    }

    /**
     * 根据图片原始尺寸设置宽高
     */
    public void displayImageByO(Context context, Object path, ImageView imageView) {
        //获取图片真正的宽高
        Glide.with(context)
                .asBitmap()//强制Glide返回一个Bitmap对象
                .centerInside()
                .load(path)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        int imageWidth = resource.getWidth();
                        int imageHeight = resource.getHeight();
                        int height = UIUtil.getScreenWidth(context) * imageHeight / imageWidth;
                        ViewGroup.LayoutParams para = imageView.getLayoutParams();
                        para.height = height;
                        para.width = UIUtil.getScreenWidth(context);//屏幕的宽度，没有工具类自己从网上搜
                        imageView.setImageBitmap(resource);
                    }

                });
    }


    /**
     * 设置图片，带默认图
     */
    public void displayImage(Context context, int pathDefault, Object path, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(pathDefault)
                .dontAnimate();

        Glide.with(context.getApplicationContext())
                .load(path)
                .apply(requestOptions)
                .into(imageView);
    }

    /**
     * 设置图片，设置尺寸
     */
    public void displayImage(Context context, Object path, ImageView imageView, int wigth, int height) {
        RequestOptions requestOptions = new RequestOptions()
                .dontAnimate()
                .override(wigth, height);

        Glide.with(context.getApplicationContext())
                .load(path)
                .apply(requestOptions)
                .into(imageView);
    }

    /**
     * 设置图片，设置尺寸，带默认图
     */
    public void displayImage(Context context, int pathDefault, Object path, ImageView imageView, int wigth, int height) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(pathDefault)
                .dontAnimate()
                .override(wigth, height);

        Glide.with(context.getApplicationContext())
                .load(path)
                .apply(requestOptions)
                .into(imageView);
    }

    /**
     * 设置图片，带默认图，带错误图
     */
    public void displayErrImage(Context context, int pathDefault, int error, Object path, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(pathDefault)
                .error(error)
                .dontAnimate();

        Glide.with(context.getApplicationContext())
                .load(path)
                .apply(requestOptions)
                .into(imageView);
    }

    /**
     * 设置原图图片
     */
    public void displayMaxImage(Context context, Object path, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions()
                .dontAnimate()
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);

        Glide.with(context.getApplicationContext())
                .load(path)
                .apply(requestOptions)
                .into(imageView);
    }

    /**
     * 设置原图图片，带默认图
     */
    public void displayMaxImage(Context context, int pathDefault, Object path, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(pathDefault)
                .dontAnimate()
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);

        Glide.with(context.getApplicationContext())
                .load(path)
                .apply(requestOptions)
                .into(imageView);
    }

    /**
     * 设置原图图片，带监听，带默认图
     */
    public void displayImage(Context context, int pathDefault, Object path, SimpleTarget<Bitmap> listener) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(pathDefault)
                .dontAnimate();

        Glide.with(context.getApplicationContext())
                .asBitmap()
                .load(path)
                .apply(requestOptions)
                .into(listener);
    }

    /**
     * 设置原图图片
     */
    public void displayImageDefault(Context context, Object path, SimpleTarget<Bitmap> listener) {
        RequestOptions requestOptions = new RequestOptions()
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .priority(Priority.HIGH);

        mTarget = Glide.with(context.getApplicationContext())
                .asBitmap()
                .load(path)
                .apply(requestOptions)
                .error(Glide.with(context.getApplicationContext())
                        .asBitmap()
                        .load(path)
                        .apply(requestOptions))
                .into(listener);
    }

    /**
     * 预加载原图图片
     */
    public void preloadImageDefault(Context context, String path) {
        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .priority(Priority.LOW);

        Glide.with(context.getApplicationContext())
                .load(path)
                .apply(requestOptions)
                .preload();
    }

    /**
     * 图片下载
     */
    public void downloadOnly(Context context, String path, SimpleTarget<File> listener) {
        Glide.with(context.getApplicationContext())
                .downloadOnly()
                .load(path)
                .into(listener);
    }

    private Target<Bitmap> mTarget;

    public void clear(Context context) {
        if (mTarget != null) {
            Glide.with(context.getApplicationContext()).clear(mTarget);
            mTarget = null;
        }
    }
}
