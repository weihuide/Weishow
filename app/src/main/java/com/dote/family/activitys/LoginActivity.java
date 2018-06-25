package com.dote.family.activitys;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dote.family.R;
import com.dote.family.base.BaseActivity;
import com.dote.family.constant.Constants;
import com.dote.family.entity.EventBusEntity;
import com.dote.family.entity.UserInfo;
import com.dote.family.utils.CommonUtils;
import com.dote.family.utils.SharedPreferenceUtils;
import com.dote.family.view.TitleLayout;
import com.mob.ums.OperationCallback;
import com.mob.ums.UMSSDK;
import com.mob.ums.User;

import net.lemonsoft.lemonbubble.LemonBubble;
import net.lemonsoft.lemonbubble.LemonBubbleInfo;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2017/8/15.
 */

public class LoginActivity extends BaseActivity {

    private TitleLayout mTitleLayout;
    private Button mLoginButton;
    private TextView mForgetTv,mRegistTv;
    private EditText mPhoneEt,mPswEt;

    @Override
    protected int getLayoutId() {
        return R.layout.login;
    }

    @Override
    protected void initView() {
        mTitleLayout = baseFindViewById(R.id.login_title);
        mForgetTv = baseFindViewById(R.id.getpsw);
        mRegistTv = baseFindViewById(R.id.reg);
        mLoginButton = baseFindViewById(R.id.bt_login);
        mPhoneEt = baseFindViewById(R.id.ed_phone);
        mPswEt = baseFindViewById(R.id.ed_psw);
    }

    @Override
    protected void setListener() {
        mTitleLayout.setBackClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mForgetTv.setOnClickListener(this);
        mRegistTv.setOnClickListener(this);
        mLoginButton.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mTitleLayout.setMoreGone();
        mTitleLayout.setSearchGone();
        mTitleLayout.setTitle(getResources().getString(R.string.login));
    }

    @Override
    public void getEventBusValue(EventBusEntity entity) {
        super.getEventBusValue(entity);
        if(entity.getActivityName().equals("RegistActivity")){
            UserInfo  info = (UserInfo)entity.getValue();
            mPhoneEt.setText(info.getUserName());
            mPswEt.setText(info.getPassWord());
        }
    }

    @Override
    protected String[] usePermission() {
        return new String[0];
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.getpsw:
                Bundle bundle = new Bundle();
                bundle.putBoolean("LoginActivity",true);
                startActivity(RegistActivity.class,bundle);
                break;
            case R.id.reg:
                Bundle bundle2 = new Bundle();
                bundle2.putBoolean("LoginActivity",false);
                startActivity(RegistActivity.class,bundle2);
                break;
            case R.id.bt_login:
                if(TextUtils.isEmpty(mPhoneEt.getText().toString())){
                    Toast.makeText(this,getResources().getString(R.string.pls_enter_phone),Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(mPswEt.getText().toString())){
                    Toast.makeText(this,getResources().getString(R.string.pls_enter_psw),Toast.LENGTH_SHORT).show();
                    return;
                }
                login();
                break;
        }
    }

    private void login() {
        LemonBubble.showRoundProgress(LoginActivity.this, getResources().getString(R.string.login_loading));
        UMSSDK.loginWithPhoneNumber(Constants.COUNTRY_CODE,mPhoneEt.getText().toString(),mPswEt.getText().toString(),new OperationCallback<User>(){
            @Override
            public void onSuccess(User user) {
                super.onSuccess(user);
                SharedPreferenceUtils.setParam(LoginActivity.this,Constants.USERNAME,mPhoneEt.getText().toString());
                SharedPreferenceUtils.setParam(LoginActivity.this,Constants.PASSWORD,mPswEt.getText().toString());
                LemonBubble.showRight(LoginActivity.this, getResources().getString(R.string.login_success), 1000);
                EventBus.getDefault().post(new EventBusEntity("LoginActivity",user));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                },1000);
            }

            @Override
            public void onFailed(Throwable throwable) {
                super.onFailed(throwable);
                Toast.makeText(LoginActivity.this,throwable.getMessage(),Toast.LENGTH_SHORT).show();
                LemonBubble.forceHide();
            }

            @Override
            public void onCancel() {
                super.onCancel();
            }
        });
    }

}
