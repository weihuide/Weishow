package com.dote.family.activitys;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dote.family.ProjectApplication;
import com.dote.family.R;
import com.dote.family.base.BaseActivity;
import com.dote.family.constant.Constants;
import com.dote.family.utils.SharedPreferenceUtils;
import com.dote.family.view.TitleLayout;
import com.mob.ums.OperationCallback;
import com.mob.ums.UMSSDK;

import org.w3c.dom.Text;

/**
 * Created by Administrator on 2017/9/1.
 */

public class UpdatePswActivity extends BaseActivity {

    private TitleLayout update_title;
    private EditText ed_psw_old;
    private EditText ed_psw;
    private EditText ed_secpsw;
    private Button bt_update;
    @Override
    protected int getLayoutId() {
        return R.layout.update_psd;
    }

    @Override
    protected void initView() {
        update_title = baseFindViewById(R.id.update_title);
        ed_psw_old = baseFindViewById(R.id.ed_psw_old);
        ed_psw = baseFindViewById(R.id.ed_psw);
        ed_secpsw = baseFindViewById(R.id.ed_secpsw);
        bt_update = baseFindViewById(R.id.bt_update);
        update_title.setBackVisible();
        update_title.setSearchGone();
        update_title.setMoreGone();
        update_title.setBackClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        update_title.setTitle(getResources().getString(R.string.updatepsw));
    }

    @Override
    protected void setListener() {
        bt_update.setOnClickListener(this);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected String[] usePermission() {
        return new String[0];
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.bt_update:
                if(TextUtils.isEmpty(ed_psw_old.getText().toString())) {
                    toast(getResources().getString(R.string.pls_enter_psw));
                    return;
                }
                if(TextUtils.isEmpty(ed_psw.getText().toString())) {
                    toast(getResources().getString(R.string.pls_enter_psw));
                    return;
                }
                if(TextUtils.isEmpty(ed_secpsw.getText().toString())) {
                    toast(getResources().getString(R.string.pls_enter_psdr));
                    return;
                }
                if(!ed_psw.getText().toString().equals(ed_secpsw.getText().toString())) {
                    toast(getResources().getString(R.string.pls_enter_same));
                    return;
                }
                UMSSDK.changePassword(ed_psw.getText().toString(), ed_psw_old.getText().toString(),new OperationCallback<Void>(){
                    @Override
                    public void onSuccess(Void aVoid) {
                        super.onSuccess(aVoid);
                        toast(getResources().getString(R.string.updatepsw_success));
                        startActivity(LoginActivity.class);
                        finish();
                    }

                    @Override
                    public void onFailed(Throwable throwable) {
                        super.onFailed(throwable);
                        toast(getResources().getString(R.string.updatepsw_error));
                    }

                    @Override
                    public void onCancel() {
                        super.onCancel();
                    }
                });
                break;
            default:
                break;
        }
    }
}
