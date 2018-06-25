package com.dote.family.entity.item;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by Administrator on 2017/8/22.
 */

public class InsertPicItem extends MultiItemEntity{

    private String text;

    public InsertPicItem(int type,String text){
        itemType = type;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
