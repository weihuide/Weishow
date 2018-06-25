package com.dote.family.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dote.family.R;
import com.dote.family.entity.BmobEntity.DoteKind;

import java.util.List;

/**
 * 动物种类adapter
 * Created by weihui on 2017/8/28.
 */

public class DoteKindAdapter extends BaseQuickAdapter<DoteKind> {

    public DoteKindAdapter(List<DoteKind> data) {
        super(R.layout.activity_kind_item, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, DoteKind doteKind) {
        baseViewHolder.setText(R.id.tv_dotename,doteKind.getValue());
    }

}
