package com.dote.family.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dote.family.R;
import com.dote.family.entity.BmobEntity.DoteAge;

import java.util.List;

/**
 * 动物年龄adapter
 * Created by weihui on 2017/8/28.
 */

public class DoteAgeAdapter extends BaseQuickAdapter<DoteAge> {

    public DoteAgeAdapter( List<DoteAge> data) {
        super(R.layout.activity_age_item, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, DoteAge doteAge) {
        baseViewHolder.setText(R.id.tv_doteage,doteAge.getDoteage());
    }

}