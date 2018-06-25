package com.dote.family.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dote.family.R;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/** 详情界面的图片adapter
 * Created by weihui on 2017/8/28.
 */


public class DotePicAdapter extends BaseQuickAdapter<BmobFile> {

    public DotePicAdapter(List<BmobFile> data) {
        super(R.layout.picshow_item, data);
    }

    @Override
    protected void convert(final BaseViewHolder baseViewHolder, BmobFile files) {
        Glide.with(mContext).load(files.getUrl()).into(new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                ((ImageView) baseViewHolder.getView(R.id.pic_item)).setBackground(resource);
            }
        });
    }
}