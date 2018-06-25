package com.dote.family.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dote.family.ProjectApplication;
import com.dote.family.R;
import com.dote.family.activitys.AddDoteActivity;
import com.dote.family.activitys.LoginActivity;
import com.dote.family.activitys.MyDescActivity;
import com.dote.family.activitys.UpdatePswActivity;
import com.dote.family.base.BaseFragment;
import com.dote.family.utils.CommonUtils;
import com.dote.family.utils.GlideCircleTransform;
import com.dote.family.view.TitleLayout;
import com.google.zxing.encoding.EncodingHandler;
import com.lzy.imagepicker.bean.ImageItem;
import com.mob.ums.OperationCallback;
import com.mob.ums.UMSSDK;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/8.
 */

public class MyFragment extends BaseFragment {

    private TitleLayout mTitleLayout;
    private TextView tv_my_name,tv_my_phone;
    private ImageView iv_my_head;
    private LinearLayout linear_information,linear_change_pwd,linear_about;
    private Button btn_switch;
    public ArrayList<ImageItem> images;

    @Override
    protected void initData(Bundle arguments) {
        mTitleLayout.setBackGone();
        mTitleLayout.setMoreGone();
        mTitleLayout.setSearchGone();
        mTitleLayout.setTitle(getResources().getString(R.string.tab03_value));
        updateLoginStatus();
    }


    public void updateLoginStatus() {
        if(ProjectApplication.mUser != null){
            tv_my_name.setText(ProjectApplication.mUser.nickname.get());
            tv_my_phone.setText(ProjectApplication.mUser.phone.get());
            Glide.with(getActivity()).load(ProjectApplication.mUser.avatar.get()[0]).transform(new GlideCircleTransform(getActivity())).into(iv_my_head);
            btn_switch.setText(R.string.my_unregist);
        }else {
            tv_my_name.setText("");
            tv_my_phone.setText("");
            Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.default_avatar);
            Glide.with(getActivity()).load(CommonUtils.Bitmap2Bytes(mBitmap)).into(iv_my_head);
            btn_switch.setText(R.string.my_login);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateLoginStatus();
    }

    @Override
    protected void initView() {
        mTitleLayout = findViewById(R.id.my_title);
        tv_my_name = findViewById(R.id.tv_my_name);
        tv_my_phone = findViewById(R.id.tv_my_phone);
        iv_my_head = findViewById(R.id.iv_my_head);
        linear_information = findViewById(R.id.linear_information);
        linear_change_pwd = findViewById(R.id.linear_change_pwd);
        linear_about = findViewById(R.id.linear_about);
        btn_switch = findViewById(R.id.btn_switch);
    }

    @Override
    protected void setListener() {
        linear_information.setOnClickListener(this);
        linear_change_pwd.setOnClickListener(this);
        linear_about.setOnClickListener(this);
        btn_switch.setOnClickListener(this);
        iv_my_head.setOnClickListener(this);
    }

    @Override
    protected void onLazyLoad() {

    }

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_my;
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.iv_my_head:
                if(ProjectApplication.mUser == null){
                    startActivity(new Intent(getActivity(),LoginActivity.class));
                    return;
                }
                CommonUtils.showPhotoDialog(getActivity(),true,images);
                break;
            case R.id.linear_information:
                if(ProjectApplication.mUser == null){
                    startActivity(new Intent(getActivity(),LoginActivity.class));
                    return;
                }
                startActivity(new Intent(getActivity(), MyDescActivity.class));
                break;
            case R.id.linear_change_pwd:
                if(ProjectApplication.mUser == null){
                    startActivity(new Intent(getActivity(),LoginActivity.class));
                    return;
                }
                startActivity(new Intent(getActivity(), UpdatePswActivity.class));
                break;
            case R.id.linear_about:
                break;
            case R.id.btn_switch:
                if(ProjectApplication.mUser != null){
                    UMSSDK.logout(new OperationCallback<Void>(){
                        @Override
                        public void onSuccess(Void aVoid) {
                            super.onSuccess(aVoid);
                            ProjectApplication.mUser = null;
                            CommonUtils.EaseUIlogout();
                            updateLoginStatus();
                        }

                        @Override
                        public void onFailed(Throwable throwable) {
                            super.onFailed(throwable);
                        }

                        @Override
                        public void onCancel() {
                            super.onCancel();
                        }
                    });
                } else {
                    startActivity(new Intent(getActivity(),LoginActivity.class));
                }
                break;
        }
    }
}
