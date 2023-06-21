package com.test.drivingcar.utils;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.DrawableRes;

import com.test.drivingcar.base.App;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 16/6/15 下午12:27.
 *
 * @author binwin20
 */
public class SpannableWrap {
    public static SpannableConfig setText(String text) {
        return new SpannableConfig(text);
    }

    public static class SpannableConfig {
        private List<SpannableString> mSpannableStringList;
        private SpannableString mCurrentString;
        private boolean mNeedMoveMethod = false;

        private SpannableConfig(String text) {
            if (mSpannableStringList == null) {
                mSpannableStringList = new ArrayList<>();
            }
            mCurrentString = new SpannableString(text);
            mSpannableStringList.add(mCurrentString);
        }

        /**
         * 拼接下一段文字
         *
         * @param text
         * @return
         */
        public SpannableConfig append(String text) {
            mCurrentString = new SpannableString(text);
            mSpannableStringList.add(mCurrentString);
            return this;
        }

        /**
         * 字体颜色
         *
         * @param color
         * @return
         */
        public SpannableConfig textColor(int color) {
            ForegroundColorSpan span = new ForegroundColorSpan(color);
            mCurrentString.setSpan(span, 0, mCurrentString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return this;
        }

        /**
         * 背景颜色
         *
         * @param color
         * @return
         */
        public SpannableConfig backgroundColor(int color) {
            BackgroundColorSpan span = new BackgroundColorSpan(color);
            mCurrentString.setSpan(span, 0, mCurrentString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return this;
        }

        /**
         * 粗体
         *
         * @return
         */
        public SpannableConfig bold() {
            StyleSpan span = new StyleSpan(Typeface.BOLD);
            mCurrentString.setSpan(span, 0, mCurrentString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return this;
        }

        /**
         * 斜体
         *
         * @return
         */
        public SpannableConfig italic() {
            StyleSpan span = new StyleSpan(Typeface.ITALIC);
            mCurrentString.setSpan(span, 0, mCurrentString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return this;
        }

        /**
         * 粗斜体
         *
         * @return
         */
        public SpannableConfig bloditalic() {
            StyleSpan span = new StyleSpan(Typeface.BOLD_ITALIC);
            mCurrentString.setSpan(span, 0, mCurrentString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return this;
        }

        /**
         * 设置图片
         */
        public SpannableConfig image(@DrawableRes int drawableId) {
            Drawable drawable = App.getmContext().getResources().getDrawable(drawableId);
            image(drawable,drawableId);
            return this;
        }


        /**
         * 设置图片
         */
        public SpannableConfig image(Drawable drawable,int drawableId) {
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            ImageSpan span = new CenterImageSpan(App.getmContext(),drawableId);
            mCurrentString.setSpan(span, 0, mCurrentString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return this;
        }

        /**
         * 设置字体大小（相对值,单位：像素） 参数表示为默认字体大小的多少倍  ,0.5表示一半
         *
         * @param proportion
         * @return
         */
        public SpannableConfig relativeSize(float proportion) {
            RelativeSizeSpan span = new RelativeSizeSpan(proportion);
            mCurrentString.setSpan(span, 0, mCurrentString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return this;
        }

        /**
         * 设置字体大小
         *
         * @param size  像素/DIP
         * @param isDip
         * @return
         */
        public SpannableConfig size(int size, boolean isDip) {
            AbsoluteSizeSpan span = new AbsoluteSizeSpan(size, isDip);
            mCurrentString.setSpan(span, 0, mCurrentString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return this;
        }
//
//        /**
//         * 设置字体大小
//         *
//         * @param size    像素/DIP
//         * @param context
//         * @return
//         */
//        public SpannableConfig sizeSp(int size, Context context) {
//            AbsoluteSizeSpan span = new AbsoluteSizeSpan(DisplayUtil.sp2px(context, size), false);
//            mCurrentString.setSpan(span, 0, mCurrentString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            return this;
//        }

        /**
         * 下划线
         *
         * @return
         */
        public SpannableConfig underline() {
            UnderlineSpan span = new UnderlineSpan();
            mCurrentString.setSpan(span, 0, mCurrentString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return this;
        }

        /**
         * 超链接
         *
         * @return
         */
        public SpannableConfig url(URLType type, String url) {
            URLSpan span = new URLSpan(type + url);
            mCurrentString.setSpan(span, 0, mCurrentString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            mNeedMoveMethod = true;
            return this;
        }

        public SpannableConfig onclick(View.OnClickListener onClickListener, boolean underLine) {
            ClickableSpan span = new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    onClickListener.onClick(widget);
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    ds.setColor(ds.linkColor);
                    ds.setUnderlineText(underLine);
                }
            };
            mCurrentString.setSpan(span, 0, mCurrentString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            mNeedMoveMethod = true;
            return this;
        }


        public void into(TextView textView) {
            textView.setText("");
            for (SpannableString string : mSpannableStringList) {
                textView.append(string);
            }

            if (mNeedMoveMethod) {
                textView.setMovementMethod(LinkMovementMethod.getInstance());
            }
        }
    }

    public enum URLType {

        TELEPHONE("tal:"),
        EMAIL("mailto:"),
        WEB(""),
        /**
         * 短信
         */
        SMS("sms:"),
        /**
         * 彩信
         */
        MMS("mms:"),
        MAP("geo:");
        private final String mMethod;

        URLType(String method) {
            mMethod = method;
        }

        @Override
        public String toString() {
            return mMethod;
        }
    }
}
