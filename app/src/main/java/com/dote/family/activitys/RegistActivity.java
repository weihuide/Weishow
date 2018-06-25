package com.dote.family.activitys;

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
import com.dote.family.view.TitleLayout;
import com.mob.ums.OperationCallback;
import com.mob.ums.UMSSDK;
import com.mob.ums.User;

import net.lemonsoft.lemonbubble.LemonBubble;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2017/8/15.
 */

public class RegistActivity extends BaseActivity {

    private TitleLayout mTitleLayout;
    private EditText ed_phone,ed_vertifycode,ed_psw,ed_secpsw;
    private TextView send,bt_returnlogin;
    private Button bt_reg;
    private boolean isForget = false;
    private int totalTime = 60;

    @Override
    protected int getLayoutId() {
        return R.layout.regist;
    }

    @Override
    protected void initView() {
        isForget = getIntent().getExtras().getBoolean("LoginActivity");
        mTitleLayout = baseFindViewById(R.id.regist_title);
        ed_phone = baseFindViewById(R.id.ed_phone);
        ed_vertifycode = baseFindViewById(R.id.ed_vertifycode);
        ed_psw = baseFindViewById(R.id.ed_psw);
        ed_secpsw = baseFindViewById(R.id.ed_secpsw);
        send = baseFindViewById(R.id.send);
        bt_returnlogin = baseFindViewById(R.id.bt_returnlogin);
        bt_reg = baseFindViewById(R.id.bt_reg);
    }

    @Override
    protected void setListener() {
        mTitleLayout.setBackClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        send.setOnClickListener(this);
        bt_returnlogin.setOnClickListener(this);
        bt_reg.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mTitleLayout.setMoreGone();
        mTitleLayout.setSearchGone();
        mTitleLayout.setTitle(getResources().getString(R.string.regist));
        if(isForget){
            bt_reg.setText(getResources().getString(R.string.resetpsw));
            mTitleLayout.setTitle(getResources().getString(R.string.resetpsw));
        }
    }

    @Override
    protected String[] usePermission() {
        return new String[0];
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.send:
                getCode();
                break;
            case R.id.bt_returnlogin:
                finish();
                break;
            case R.id.bt_reg:
                regist();
                break;
        }
    }

    //注册
    private void regist() {
        if(TextUtils.isEmpty(ed_phone.getText().toString())){
            Toast.makeText(this,getResources().getString(R.string.pls_enter_phone),Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(ed_vertifycode.getText().toString())){
            Toast.makeText(this,getResources().getString(R.string.pls_enter_code),Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(ed_psw.getText().toString())){
            Toast.makeText(this,getResources().getString(R.string.pls_enter_psw),Toast.LENGTH_SHORT).show();
            return;
        }  if(TextUtils.isEmpty(ed_secpsw.getText().toString())){
            Toast.makeText(this,getResources().getString(R.string.pls_enter_psdr),Toast.LENGTH_SHORT).show();
            return;
        }
        if(!ed_secpsw.getText().toString().equals(ed_psw.getText().toString())){
            Toast.makeText(this,getResources().getString(R.string.pls_enter_same),Toast.LENGTH_SHORT).show();
            return;
        }
        if(isForget){
            UMSSDK.resetPasswordWithPhoneNumber(Constants.COUNTRY_CODE,ed_phone.getText().toString(),ed_vertifycode.getText().toString(),ed_psw.getText().toString(),new OperationCallback<Void>(){
                @Override
                public void onSuccess(Void aVoid) {
                    super.onSuccess(aVoid);
                    EventBus.getDefault().post(new EventBusEntity("RegistActivity",new UserInfo(ed_phone.getText().toString(),ed_psw.getText().toString())));
                    finish();
                }

                @Override
                public void onFailed(Throwable throwable) {
                    super.onFailed(throwable);
                    Toast.makeText(RegistActivity.this,throwable.getMessage().toString(),Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancel() {
                    super.onCancel();
                }
            });
        } else {
            UMSSDK.registerWithPhoneNumber(Constants.COUNTRY_CODE,ed_phone.getText().toString(),ed_vertifycode.getText().toString(),ed_psw.getText().toString(),null,new OperationCallback<User>(){
                @Override
                public void onSuccess(User user) {
                    super.onSuccess(user);
                    EventBus.getDefault().post(new EventBusEntity("RegistActivity",new UserInfo(ed_phone.getText().toString(),ed_psw.getText().toString())));
                    finish();
                }

                @Override
                public void onFailed(Throwable throwable) {
                    super.onFailed(throwable);
                    Toast.makeText(RegistActivity.this,throwable.getMessage().toString(),Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancel() {
                    super.onCancel();
                }
            });
        }
    }

    // 请求验证码
    private void getCode() {
        if(TextUtils.isEmpty(ed_phone.getText().toString())){
            Toast.makeText(this,getResources().getString(R.string.pls_enter_phone),Toast.LENGTH_SHORT).show();
            return;
        }
        if(ed_phone.getText().toString().length() != 11){
            Toast.makeText(this,getResources().getString(R.string.pls_enter_rightphone),Toast.LENGTH_SHORT).show();
            return;
        }
        timeShow();
        if(isForget){
            UMSSDK.sendVerifyCodeForResetPassword(Constants.COUNTRY_CODE,ed_phone.getText().toString(),new OperationCallback<Boolean>(){
                @Override
                public void onSuccess(Boolean aBoolean) {
                    super.onSuccess(aBoolean);
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
            UMSSDK.sendVerifyCodeForRegitser(Constants.COUNTRY_CODE,ed_phone.getText().toString(),new OperationCallback<Boolean>(){
                @Override
                public void onSuccess(Boolean aBoolean) {
                    super.onSuccess(aBoolean);
                }

                @Override
                public void onFailed(Throwable throwable) {
                    super.onFailed(throwable);
                    Toast.makeText(RegistActivity.this,throwable.getMessage(),Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancel() {
                    super.onCancel();
                }
            });
        }
    }

    /**
     * 倒计时
     */
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(totalTime == 0) {
                totalTime = 60;
                send.setText(getResources().getString(R.string.code_send));
                send.setFocusable(true);
                send.setClickable(true);
                send.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                return;
            }
            totalTime --;
            send.setText(totalTime+getResources().getString(R.string.send_show));
            send.setTextColor(getResources().getColor(R.color.T2));
            send.setFocusable(false);
            send.setClickable(false);
            handler.postDelayed(runnable,1000);
        }
    };

    private void timeShow(){
        toast(getResources().getString(R.string.verifycode_send));
        handler.postDelayed(runnable,1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
}
