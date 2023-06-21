package com.test.drivingcar.ui.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.test.drivingcar.R;
import com.test.drivingcar.room.bean.QuestionVo;

import java.util.List;

public class BottomListAdapter extends BaseQuickAdapter<QuestionVo, BaseViewHolder> {
    public BottomListAdapter(List<QuestionVo> data) {
        super(R.layout.item_num, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, QuestionVo questionVo) {
        baseViewHolder.setText(R.id.tv_num, baseViewHolder.getBindingAdapterPosition() + 1 + "");

    }
}
