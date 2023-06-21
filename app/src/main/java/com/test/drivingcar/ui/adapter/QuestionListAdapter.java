package com.test.drivingcar.ui.adapter;


import android.annotation.SuppressLint;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.test.drivingcar.R;
import com.test.drivingcar.base.App;
import com.test.drivingcar.room.bean.QuestionVo;
import com.test.drivingcar.ui.view.ViewLearnChoose;
import com.test.drivingcar.utils.ImageLoadUtils;
import com.test.drivingcar.utils.SpannableWrap;

import java.util.HashMap;
import java.util.List;

public class QuestionListAdapter extends BaseQuickAdapter<QuestionVo, BaseViewHolder> {
    private boolean isLearn;
    private HashMap<Integer,String> userAnswer = new HashMap<>();

    public QuestionListAdapter(@Nullable List<QuestionVo> data) {
        super(R.layout.item_question, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, QuestionVo questionVo) {
        baseViewHolder.setText(R.id.tv_question, questionVo.getQuestion());//填充题目

        ImageView pic = baseViewHolder.getView(R.id.iv_pic);
        TextView tv_answer =  baseViewHolder.getView(R.id.tv_answer);
        if (TextUtils.isEmpty(questionVo.getPic())) {//判断图片地址是不是空的
            pic.setVisibility(View.GONE);
        } else {
            pic.setVisibility(View.VISIBLE);
            ImageLoadUtils.getInstance().displayImage(pic.getContext(), R.mipmap.default_loadimage,
                    questionVo.getPic(), pic);
        }
        //填充四个选项的数据
        ViewLearnChoose viewLearnChoose = baseViewHolder.getView(R.id.mViewLearnChoose);
        viewLearnChoose.setData(questionVo, "",isLearn);
        viewLearnChoose.setOnAnswerClickListener(userPick -> {

            ansewrClick.onClick(userPick,questionVo);
        });

        //填充答案的数据
        setQuestionAnswer(tv_answer, questionVo, "");
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setMode(boolean isLearn) {
        this.isLearn = isLearn;
        notifyDataSetChanged();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setQuestionAnswer(TextView textView, QuestionVo item, String pick) {
        int text_primary = App.getmContext().getResources().getColor(R.color.ang_text_base);
        int text_primary_color1 = App.getmContext().getResources().getColor(R.color.ang_09A6FE);
        int text_primary_color2 = App.getmContext().getResources().getColor(R.color.ang_FE6063);
        String trueAnswer = item.getAnswer();
        if ("对".equals(trueAnswer)) {
            trueAnswer = "A";
        }
        if ("错".equals(trueAnswer)) {
            trueAnswer = "B";
        }
        boolean b = (trueAnswer.endsWith(pick));

        if (TextUtils.isEmpty(pick) || isLearn) {
            SpannableWrap.setText("答案 ")
                    .textColor(text_primary)
                    .append(trueAnswer)
                    .textColor(text_primary_color1)
                    .into(textView);
        } else {
            SpannableWrap.setText("答案 ")
                    .textColor(text_primary)
                    .append(trueAnswer)
                    .textColor(text_primary_color1)
                    .append("  您选择 ")
                    .textColor(text_primary)
                    .append(pick)
                    .textColor(b ? text_primary_color1 : text_primary_color2)
                    .into(textView);
        }

    }

    public void setUserAnswer(HashMap<Integer,String> userAnswer){
        this.userAnswer = userAnswer;
    }

    private OnAnswerClickListener ansewrClick;

    public interface OnAnswerClickListener {
        void onClick(String userPick,QuestionVo questionVo);
    }

    public void setOnAnswerClickListener(OnAnswerClickListener ansewrClick) {
        this.ansewrClick = ansewrClick;
    }
}
