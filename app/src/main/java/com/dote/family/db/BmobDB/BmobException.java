package com.dote.family.db.BmobDB;

/**
 * Created by weihui on 2017/8/16.
 */

public class BmobException {

    public static String bmobErrorValue(int errorCode){
        String errorValue = "";
        switch (errorCode){
            case 9001:
                errorValue = "Application Id为空，请初始化";
                break;
            case 9002:
                errorValue = "解析返回数据出错";
                break;
            case 9003:
                errorValue = "上传文件出错";
                break;
            case 9004:
                errorValue = "文件上传失败";
                break;
            case 9005:
                errorValue = "批量操作只支持最多50条";
                break;
            case 9006:
                errorValue = "objectId为空";
                break;
            case 9007:
                errorValue = "文件大小超过10M";
                break;
            case 9008:
                errorValue = "上传文件不存在";
                break;
            case 9009:
                errorValue = "没有缓存数据";
                break;
            case 9010:
                errorValue = "网络超时";
                break;
            case 9011:
                errorValue = "BmobUser类不支持批量操作";
                break;
            case 9012:
                errorValue = "上下文为空";
                break;
            case 9013:
                errorValue = "BmobObject（数据表名称）格式不正确";
                break;
            case 9014:
                errorValue = "第三方账号授权失败";
                break;
            case 9015:
                errorValue = "其他错误均返回此code";
                break;
            case 9016:
                errorValue = "无网络连接，请检查您的手机网络";
                break;
            case 9017:
                errorValue = "与第三方登录有关的错误，具体请看对应的错误描述";
                break;
            case 9018:
                errorValue = "参数不能为空";
                break;
            case 9019:
                errorValue = "格式不正确：手机号码、邮箱地址、验证码";
                break;
            case 9020:
                errorValue = "保存CDN信息失败";
                break;
            case 9021:
                errorValue = "文件上传缺少wakelock权限";
                break;
            case 9022:
                errorValue = "文件上传失败，请重新上传";
                break;
            case 9023:
                errorValue = "请调用Bmob类的initialize方法去初始化SDK";
                break;
            case 9024:
                errorValue = "调用BmobUser的fetchUserInfo方法前请先确定用户是已经登录的";
                break;
        }
        return errorValue;
    }

}
