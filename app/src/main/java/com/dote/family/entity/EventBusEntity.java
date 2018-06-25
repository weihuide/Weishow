package com.dote.family.entity;

/**
 * Created by weihui on 2017/8/7.
 */

public class EventBusEntity {
    //用作对应关系
    private String activityName;
    //用作传递值
    private Object value;

    public EventBusEntity(String activityName, Object value){
        this.activityName = activityName;
        this.value = value;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
