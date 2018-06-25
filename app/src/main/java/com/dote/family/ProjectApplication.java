package com.dote.family;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.dote.family.utils.GlideImageLoader;
import com.google.zxing.multi.qrcode.detector.MultiDetector;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseUI;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.MemoryCookieStore;
import com.lzy.okgo.https.HttpsUtils;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.mob.MobSDK;
import com.mob.ums.User;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.concurrent.TimeUnit;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import cn.jpush.android.api.JPushInterface;
import okhttp3.OkHttpClient;

/**
 * 项目主要的application
 * Created by weihui on 2017/8/7.
 */

public class ProjectApplication extends MultiDexApplication {

    public static User mUser;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MobSDK.init(this);
        //内存检测库加载
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            return;
//        }
//        LeakCanary.install(this);
        //初始化 OkGo
        initOkGo();
        //初始化Bmob
        initBmob();
        //初始化ImagePicker
        initImagePicker();

        //初始化环信
        EaseUI.getInstance().init(this,null);
        EMClient.getInstance().setDebugMode(true);
        //bugly 初始化
        CrashReport.initCrashReport(getApplicationContext(), "e357f73e18", true);
        //极光推送初始化
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
    }


    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(9);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
    }



    private void initBmob() {
        //bmob 初始化
        BmobConfig config =new BmobConfig.Builder(this)
                //设置appkey
                .setApplicationId("cb6d2e76ce64d1b48ab28db1ac7945a6")
                //请求超时时间（单位为秒）：默认15s
                .setConnectTimeout(30)
                //文件分片上传时每片的大小（单位字节），默认512*1024
                .setUploadBlockSize(1024*1024)
                //文件的过期时间(单位为秒)：默认1800s
                .setFileExpiration(2500)
                .build();
        Bmob.initialize(config);
    }

    private void initOkGo() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        /**
         * 超时时间的配置（OkHttpClient的配置）
         */
        //全局的读取超时时间
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        //全局的写入超时时间
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
       //全局的连接超时时间
        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        /**
         * cookie的配置（OkHttpClient的配置）
         */
//        //使用sp保持cookie，如果cookie不过期，则一直有效
//        builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));
//        //使用数据库保持cookie，如果cookie不过期，则一直有效
//        builder.cookieJar(new CookieJarImpl(new DBCookieStore(this)));
       //使用内存保持cookie，app退出后，cookie消失
        builder.cookieJar(new CookieJarImpl(new MemoryCookieStore()));

        /**
         * https 的配置（OkHttpClient的配置）
         */
        //方法一：信任所有证书,不安全有风险
        HttpsUtils.SSLParams sslParams1 = HttpsUtils.getSslSocketFactory();
        //方法二：自定义信任规则，校验服务端证书
//        HttpsUtils.SSLParams sslParams2 = HttpsUtils.getSslSocketFactory(new SafeTrustManager());
        //方法三：使用预埋证书，校验服务端证书（自签名证书）
//        HttpsUtils.SSLParams sslParams3 = HttpsUtils.getSslSocketFactory(getAssets().open("srca.cer"));
        //方法四：使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
//        HttpsUtils.SSLParams sslParams4 = HttpsUtils.getSslSocketFactory(getAssets().open("xxx.bks"), "123456", getAssets().open("yyy.cer"));
        builder.sslSocketFactory(sslParams1.sSLSocketFactory, sslParams1.trustManager);
        //配置https的域名匹配规则，详细看demo的初始化介绍，不需要就不要加入，使用不当会导致https握手失败
//        builder.hostnameVerifier(new SafeHostnameVerifier());

        /**
         * 以下是OkGo需要配置的
         */
        //实际使用的时候,根据需要传-------------//
        HttpHeaders headers = new HttpHeaders();
        headers.put("commonHeaderKey1", "commonHeaderValue1");    //header不支持中文，不允许有特殊字符
        headers.put("commonHeaderKey2", "commonHeaderValue2");
        HttpParams params = new HttpParams();
        params.put("commonParamsKey1", "commonParamsValue1");     //param支持中文,直接传,不要自己编码
        params.put("commonParamsKey2", "这里支持中文参数");
        OkGo.getInstance().init(this)                       //必须调用初始化
                .setOkHttpClient(builder.build())               //建议设置OkHttpClient，不设置将使用默认的
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(3);                               //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
                //.addCommonHeaders(headers)                      //全局公共头
                //.addCommonParams(params);
    }

    // OKGo的使用方法
//    OkGo.<String>get(UrlConstants.DEMO_URL).tag(this).execute(new StringCallback() {
//        //网络请求开始前
//        @Override
//        public void onStart(Request<String, ? extends Request> request) {
//            super.onStart(request);
//        }
//
//        //返回成功的回调
//        @Override
//        public void onSuccess(Response<String> response) {
//            android.util.Log.e("aaa","res = "+response.body().toString());
//        }
//
//        //返回缓存成功的回调
//        @Override
//        public void onCacheSuccess(Response<String> response) {
//            super.onCacheSuccess(response);
//        }
//
//        //请求失败的回调
//        @Override
//        public void onError(Response<String> response) {
//            super.onError(response);
//        }
//
//        //请求网络结束后的回调
//        @Override
//        public void onFinish() {
//            super.onFinish();
//        }
//
//        //上传过程中的进度回调，get请求不回调
//        @Override
//        public void uploadProgress(Progress progress) {
//            super.uploadProgress(progress);
//        }
//
//        //下载过程中的进度回调
//        @Override
//        public void downloadProgress(Progress progress) {
//            super.downloadProgress(progress);
//        }
//    });
}
