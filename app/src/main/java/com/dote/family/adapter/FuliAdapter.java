package com.dote.family.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dote.family.R;
import com.dote.family.entity.OkGoEntity;

import java.util.List;

/**
 * Created by Administrator on 2017/8/31.
 */

public class FuliAdapter extends BaseQuickAdapter<OkGoEntity.Info> {

    private Context mContext;
    public FuliAdapter(Context context, List<OkGoEntity.Info> data) {
        super(R.layout.fragment_fuli_item,data);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, OkGoEntity.Info o) {
        Glide.with(mContext).load(o.url).into((ImageView)baseViewHolder.getView(R.id.iv_fuli));

    }
}
