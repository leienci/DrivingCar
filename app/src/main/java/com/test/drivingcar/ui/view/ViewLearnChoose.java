package com.test.drivingcar.ui.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.test.drivingcar.R;
import com.test.drivingcar.room.bean.QuestionVo;

public class ViewLearnChoose extends LinearLayout {
    private Context context;
    private LinearLayout itemA, itemB, itemC, itemD;
    private ImageView ivNumA, ivNumB, ivNumC, ivNumD;
    private TextView tvTitleA, tvTitleB, tvTitleC, tvTitleD;
    private Button itemMultBtn;
    private boolean isCheckA = false, isCheckB = false, isCheckC = false, isCheckD = false;

    boolean isLearnMode;

    public ViewLearnChoose(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public ViewLearnChoose(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public ViewLearnChoose(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        View.inflate(context, R.layout.view_learn_choose, this);
        itemA = findViewById(R.id.item_a);
        itemB = findViewById(R.id.item_b);
        itemC = findViewById(R.id.item_c);
        itemD = findViewById(R.id.item_d);
        ivNumA = findViewById(R.id.item_a_num);
        ivNumB = findViewById(R.id.item_b_num);
        ivNumC = findViewById(R.id.item_c_num);
        ivNumD = findViewById(R.id.item_d_num);
        tvTitleA = findViewById(R.id.item_a_title);
        tvTitleB = findViewById(R.id.item_b_title);
        tvTitleC = findViewById(R.id.item_c_title);
        tvTitleD = findViewById(R.id.item_d_title);
        itemMultBtn = findViewById(R.id.item_mult_btn);
        itemA.setOnClickListener(onclick);
        itemB.setOnClickListener(onclick);
        itemC.setOnClickListener(onclick);
        itemD.setOnClickListener(onclick);
        itemMultBtn.setOnClickListener(onclick);
    }

    //设置数据
    public void setData(QuestionVo questionVo, String userPick, boolean isLearnMode) {
        this.isLearnMode = isLearnMode;
        //设置默认状态
        isCheckA = false;
        isCheckB = false;
        isCheckC = false;
        isCheckD = false;
        setTheme();
        setMultItem();
        itemMultBtn.setVisibility(View.GONE);
        /***************************/
        if (questionVo.getQuestion_type() == 1) {//判断题
            itemC.setVisibility(View.GONE);
            itemD.setVisibility(View.GONE);
            tvTitleA.setText("正确");
            tvTitleB.setText("错误");
        } else {
            itemC.setVisibility(View.VISIBLE);
            itemD.setVisibility(View.VISIBLE);
            tvTitleA.setText(questionVo.getOption1());
            tvTitleB.setText(questionVo.getOption2());
            tvTitleC.setText(questionVo.getOption3());
            tvTitleD.setText(questionVo.getOption4());
            if (questionVo.getQuestion_type() == 3) {//是多选题的话
                itemMultBtn.setVisibility(View.VISIBLE);
            }
        }
        setAnswer(questionVo.getAnswer(), userPick, isLearnMode);

    }

    /**
     * 显示答案
     */
    private void setAnswer(String truePick, String userPick, boolean isLearnMode) {
        if (TextUtils.isEmpty(truePick)) {
            return;
        }

        if (truePick.contains("A") || truePick.equals("对")) {//正确答案包含A或者对
            ivNumA.setImageResource(R.mipmap.bm_practise_a_true_day);
            tvTitleA.setTextColor(context.getResources().getColor(R.color.ang_09A6FE));
        }
        if (truePick.contains("B") || truePick.equals("错")) {
            ivNumB.setImageResource(R.mipmap.bm_practise_b_true_day);
            tvTitleB.setTextColor(context.getResources().getColor(R.color.ang_09A6FE));
        }
        if (truePick.contains("C")) {
            ivNumC.setImageResource(R.mipmap.bm_practise_c_true_day);
            tvTitleC.setTextColor(context.getResources().getColor(R.color.ang_09A6FE));
        }
        if (truePick.contains("D")) {
            ivNumD.setImageResource(R.mipmap.bm_practise_d_true_day);
            tvTitleD.setTextColor(context.getResources().getColor(R.color.ang_09A6FE));
        }

        if (!isLearnMode) {
            if (userPick.contains("A")) {
                if (truePick.contains("A") || truePick.equals("对")) {
                    ivNumA.setImageResource(R.mipmap.bm_practise_true_day);
                } else {
                    ivNumA.setImageResource(R.mipmap.bm_practise_false_day);
                    tvTitleA.setTextColor(context.getResources().getColor(R.color.ang_FE6063));
                }
            }
            if (userPick.contains("B")) {
                if (truePick.contains("B") || truePick.equals("错")) {
                    ivNumB.setImageResource(R.mipmap.bm_practise_true_day);
                } else {
                    ivNumB.setImageResource(R.mipmap.bm_practise_false_day);
                    tvTitleB.setTextColor(context.getResources().getColor(R.color.ang_FE6063));
                }
            }
            if (userPick.contains("C")) {
                if (truePick.contains("C")) {
                    ivNumC.setImageResource(R.mipmap.bm_practise_true_day);
                } else {
                    ivNumC.setImageResource(R.mipmap.bm_practise_false_day);
                    tvTitleC.setTextColor(context.getResources().getColor(R.color.ang_FE6063));
                }
            }
            if (userPick.contains("D")) {
                if (truePick.contains("D")) {
                    ivNumD.setImageResource(R.mipmap.bm_practise_true_day);
                } else {
                    ivNumD.setImageResource(R.mipmap.bm_practise_false_day);
                    tvTitleD.setTextColor(context.getResources().getColor(R.color.ang_FE6063));
                }
            }
        }

    }

    private void setTheme() {
        tvTitleA.setTextColor(context.getResources().getColor(R.color.ang_text_base));
        tvTitleB.setTextColor(context.getResources().getColor(R.color.ang_text_base));
        tvTitleC.setTextColor(context.getResources().getColor(R.color.ang_text_base));
        tvTitleD.setTextColor(context.getResources().getColor(R.color.ang_text_base));
    }

    private void setMultItem() {
        ivNumA.setImageResource(isCheckA ? R.mipmap.bm_practise_a_s_day : R.mipmap.bm_practise_a_n_day);
        ivNumB.setImageResource(isCheckB ? R.mipmap.bm_practise_b_s_day : R.mipmap.bm_practise_b_n_day);
        ivNumC.setImageResource(isCheckC ? R.mipmap.bm_practise_c_s_day : R.mipmap.bm_practise_c_n_day);
        ivNumD.setImageResource(isCheckD ? R.mipmap.bm_practise_d_s_day : R.mipmap.bm_practise_d_n_day);
        itemMultBtn.setEnabled((isCheckA ? 1 : 0) + (isCheckB ? 1 : 0) + (isCheckC ? 1 : 0) + (isCheckD ? 1 : 0) >= 2);
    }

    private OnClickListener onclick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isLearnMode) {
                return;
            }
            if (v.getId() == R.id.item_a) {
                onAnswerClickListener.onClick("A");

            } else if (v.getId() == R.id.item_b) {
                onAnswerClickListener.onClick("B");

            } else if (v.getId() == R.id.item_c) {
                onAnswerClickListener.onClick("C");

            } else if (v.getId() == R.id.item_d) {
                onAnswerClickListener.onClick("D");

            }

        }
    };

    private OnAnswerClickListener onAnswerClickListener;

    public interface OnAnswerClickListener {
        void onClick(String userPick);
    }

    public void setOnAnswerClickListener(OnAnswerClickListener onAnswerClickListener) {
        this.onAnswerClickListener = onAnswerClickListener;
    }

}
