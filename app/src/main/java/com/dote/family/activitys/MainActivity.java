package com.dote.family.activitys;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.zxing.WriterException;
import com.google.zxing.activity.CaptureActivity;
import com.google.zxing.encoding.EncodingHandler;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.dote.family.R;
import com.dote.family.base.BaseActivity;
import com.dote.family.constant.Constants;
import com.dote.family.constant.UrlConstants;
import com.dote.family.entity.EventBusEntity;
import com.dote.family.entity.OkGoEntity;
import com.dote.family.utils.CommonUtils;
import com.dote.family.utils.JsonOkGoCallback;
import com.dote.family.view.RadoView;

import org.greenrobot.eventbus.EventBus;

import java.util.LinkedHashMap;

public class MainActivity extends BaseActivity {

    private Button btnEventbus;
    private TextView tvEventbus;
    private Button btnGlide;
    private ImageView ivGlide;
    private Button btnOkGo;
    private TextView tvOkGo;
    private Button btnZxing;
    private TextView tvZxing;
    private Button btnCreateZxing;
    private ImageView ivZxing;
    private Button btnPolygon;
    private RadoView rv_polygon;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        ivGlide = baseFindViewById(R.id.iv_glide);
        btnEventbus = baseFindViewById(R.id.btn_eventbus);
        tvEventbus = baseFindViewById(R.id.tv_eventbus);
        btnGlide = baseFindViewById(R.id.btn_glide);
        btnOkGo = baseFindViewById(R.id.btn_okgo);
        tvOkGo = baseFindViewById(R.id.tv_okgo);
        btnZxing = baseFindViewById(R.id.btn_zxing);
        tvZxing = baseFindViewById(R.id.tv_zxing);
        btnCreateZxing = baseFindViewById(R.id.btn_createzxing);
        ivZxing = baseFindViewById(R.id.iv_zxing);
        rv_polygon = baseFindViewById(R.id.rv_polygon);
        btnPolygon = baseFindViewById(R.id.bt_polygon);
    }

    @Override
    protected void setListener() {
        btnEventbus.setOnClickListener(this);
        btnGlide.setOnClickListener(this);
        btnOkGo.setOnClickListener(this);
        btnZxing.setOnClickListener(this);
        btnCreateZxing.setOnClickListener(this);
        btnPolygon.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected String[] usePermission() {
        return new String[]{Manifest.permission.INTERNET,Manifest.permission.CAMERA,Manifest.permission.VIBRATE};
    }

    @Override
    public void getEventBusValue(EventBusEntity entity) {
        super.getEventBusValue(entity);
        if(entity.getActivityName().equals("MainActivity")) {
            tvEventbus.setText(entity.getValue().toString());
        }
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.btn_eventbus:
                EventBus.getDefault().post(new EventBusEntity("MainActivity","eventbus show"));
                break;
            case R.id.btn_glide:
                Glide.with(this).load("https://ws1.sinaimg.cn/large/610dc034gy1fi2okd7dtjj20u011h40b.jpg").into(ivGlide);
                break;
            case R.id.btn_okgo:
                startActivity(IndexActivity.class);
//                request();
                break;
            case R.id.btn_zxing:
                if(CommonUtils.isCameraCanUse()){
                    Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
                    startActivityForResult(intent, Constants.ZXING_REQUEST_CODE);
                } else {
                    toast("请打开相应的权限");
                }
                break;
            case R.id.btn_createzxing:
                try {
                    Bitmap mBitmap = EncodingHandler.createQRCode("http://bmob-cdn-13679.b0.upaiyun.com/2017/08/31/5fa7f9d940a2728280f4a739c0c2625e.apk", 300);//300表示宽高
                    Glide.with(this).load(CommonUtils.Bitmap2Bytes(mBitmap)).into(ivZxing);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.bt_polygon:
                LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
                map.put("语文", 200);
                map.put("数学", 300);
                map.put("外语", 500);
                map.put("文科", 450);
                map.put("理科", 270);
                map.put("其他", 320);
                rv_polygon.setMap(map);
                rv_polygon.setMaxRadius(500);
                rv_polygon.setStrokeCount(7);
                rv_polygon.invalidate();
                break;
            default:
                break;
        }
    }

    private void request() {
        OkGo.<OkGoEntity>get(UrlConstants.DEMO_URL).tag(this).execute(new JsonOkGoCallback<OkGoEntity>(){
            @Override
            public void onSuccess(Response<OkGoEntity> response) {
                OkGoEntity entity = response.body();
                tvOkGo.setText("response:"+response);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case Constants.ZXING_REQUEST_CODE:
                if(resultCode == Constants.ZXING_RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    toast(bundle.getString("qr_scan_result"));
                    tvZxing.setText(bundle.getString("qr_scan_result"));
                }
                break;
            default:
                break;
        }
    }
}
