package com.dote.family.entity.BmobEntity;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/9/5.
 */

public class NewVersion extends BmobObject {
    //版本号
    private int versionCode;
    //版本名称
    private String versionName;
    //版本简介
    private String versionDesc;
    //版本下载地址
    private String versionUrl;

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getVersionDesc() {
        return versionDesc;
    }

    public void setVersionDesc(String versionDesc) {
        this.versionDesc = versionDesc;
    }

    public String getVersionUrl() {
        return versionUrl;
    }

    public void setVersionUrl(String versionUrl) {
        this.versionUrl = versionUrl;
    }
}
