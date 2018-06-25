package com.dote.family.entity.BmobEntity;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobGeoPoint;

/**
 * Created by Administrator on 2017/8/16.
 */

public class DoteInfo extends BmobObject {

    //用户id
    private String userId;
    //用户手机
    private String userPhone;
    //用户头像
    private String userIconUrl;
    //用户昵称
    private String userName;
    //宠物的大种类
    private String doteBigKind;
    //宠物种类
    private String doteKind;
    //宠物性别
    private String doteSex;
    //宠物年龄
    private String doteAge;
    //宠物所在地
    private BmobGeoPoint dotePoint;
    //宠物简介
    private String doteDesc;
    //宠物照片
    private List<BmobFile> dotePicList;
    //是否有效（如果用户已经找到了宠物的伴侣，则将此字段置为false，即可）
    private Boolean isValid;

    public String getUserPhone() {
        return userPhone;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getDoteKind() {
        return doteKind;
    }

    public void setDoteKind(String doteKind) {
        this.doteKind = doteKind;
    }

    public String getDoteSex() {
        return doteSex;
    }

    public void setDoteSex(String doteSex) {
        this.doteSex = doteSex;
    }

    public String getDoteAge() {
        return doteAge;
    }

    public void setDoteAge(String doteAge) {
        this.doteAge = doteAge;
    }

    public BmobGeoPoint getDotePoint() {
        return dotePoint;
    }

    public void setDotePoint(BmobGeoPoint dotePoint) {
        this.dotePoint = dotePoint;
    }

    public String getDoteDesc() {
        return doteDesc;
    }

    public void setDoteDesc(String doteDesc) {
        this.doteDesc = doteDesc;
    }

    public List<BmobFile> getDotePicList() {
        return dotePicList;
    }

    public void setDotePicList(List<BmobFile> dotePicList) {
        this.dotePicList = dotePicList;
    }

    public Boolean getValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }

    public String getDoteBigKind() {
        return doteBigKind;
    }

    public void setDoteBigKind(String doteBigKind) {
        this.doteBigKind = doteBigKind;
    }

    public String getUserIconUrl() {
        return userIconUrl;
    }

    public void setUserIconUrl(String userIconUrl) {
        this.userIconUrl = userIconUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
