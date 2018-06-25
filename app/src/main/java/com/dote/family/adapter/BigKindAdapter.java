package com.dote.family.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dote.family.R;

import java.util.List;

/**大种类的adapter
 * Created by weihui on 2017/8/28.
 */

public class BigKindAdapter extends BaseQuickAdapter<String> {

    public BigKindAdapter( List<String> data) {
        super(R.layout.activity_kind_item, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, String s) {
        baseViewHolder.setText(R.id.tv_dotename,s);
    }
}
