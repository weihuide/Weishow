package com.dote.family.entity.BmobEntity;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/8/16.
 */

public class DoteAge extends BmobObject{
    private String doteage;
    private Integer ageId;

    public String getDoteage() {
        return doteage;
    }

    public void setDoteage(String doteage) {
        this.doteage = doteage;
    }

    public Integer getAgeId() {
        return ageId;
    }

    public void setAgeId(Integer ageId) {
        this.ageId = ageId;
    }
}
