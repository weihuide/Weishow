package com.dote.family.entity.BmobEntity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by weihui on 2017/8/16.
 */

public class DoteKind extends BmobObject{
    //具体的种类名
    private String value;
    //所属于哪一个种类（猫，狗。。。）
    private String kind;
    //长得什么样子
    private BmobFile kindPic;

    public BmobFile getKindPic() {
        return kindPic;
    }

    public void setKindPic(BmobFile kindPic) {
        this.kindPic = kindPic;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }
}
