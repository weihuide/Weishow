package com.dote.family.constant;

/**
 * Created by weihui on 2017/8/7.
 */

public class Constants {

    //动态申请权限的code
    public static final int PERMISSION_REQUESTCODE =  0x001;

    //(二维码)打开扫描界面请求码
    public static final  int ZXING_REQUEST_CODE = 0x002;

    //(二维码)扫描成功返回码
    public static final int ZXING_RESULT_OK = 0xA1;

    //是否已经走了引导页
    public static final String HAS_WELCOME = "hasWelcome";

    public static final String COUNTRY_CODE = "86";
    //APP存储路径
    public static String AppPath = "com.dote.family";
    // 从相册中选择
    public static final int PHOTO_REQUEST_CALLBACK = 0x003;

    public static final String USERNAME ="username";

    public static final String PASSWORD = "password";

    //环信的密码固定
    public static final String EASE_PSW = "123456";
}
