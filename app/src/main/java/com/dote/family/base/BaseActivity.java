package com.dote.family.base;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dote.family.constant.Constants;
import com.dote.family.entity.EventBusEntity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * Created by weihui on 2017/8/7.
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener{

    protected boolean isDebug = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(getLayoutId());
        initView();
        checkPermission(usePermission());
        EventBus.getDefault().register(this);
    }

    /**
     * 检查权限
     * @param permissions
     */
    private void checkPermission(String[] permissions){
        //如果系统版本在M及以上，则动态申请权限
        if (permissions.length > 0 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //判断权限是否被申请，PERMISSION_GRANTED 表示有权限，则什么都不走了，否则需要去申请权限
            for(int i = 0;i<permissions.length;i++) {
                if(ContextCompat.checkSelfPermission(this,permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,permissions, Constants.PERMISSION_REQUESTCODE);
                    return;
                }
            }
        }
        initData();
        setListener();
    }


    // Log
    protected void Log(String msg) {
        if (isDebug) {
            Log.d(this.getClass().getName(), msg);
        }
    }

    // Toast
    protected void toast(CharSequence msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    // startActivity
    protected void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    // startActivity
    protected void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    // startActivityForResult
    protected void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    // startActivityForResult
    protected void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    // getIntent
    protected Bundle getIntentExtra() {
        Intent intent = getIntent();
        Bundle bundle = null;
        if (null != intent)
            bundle = intent.getExtras();
        return bundle;
    }

    // findViewById
    public <T extends View> T baseFindViewById(int resId) {
        return (T) findViewById(resId);
    }

    /**
     * 设置ContentView
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化View
     */
    protected abstract void initView();

    /**
     * add Listener
     */
    protected abstract void setListener();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 检测权限
     */
    protected abstract String[] usePermission();

    @Subscribe(threadMode  = ThreadMode.MAIN)
    public void getEventBusValue(EventBusEntity entity){
        if(entity.getActivityName().equals("exception")) {
            toast(entity.getValue().toString());
        }
    };

    /**
     * view点击
     * @param v
     */
    public abstract void widgetClick(View v);

    private long lastClick = 0;

    private boolean fastClick() {
        if (System.currentTimeMillis() - lastClick <= 1000) {
            return false;
        }
        lastClick = System.currentTimeMillis();
        return true;
    }

    @Override
    public void onClick(View v) {
        if (fastClick())
            widgetClick(v);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == Constants.PERMISSION_REQUESTCODE) {
            boolean isAllGranted = true;
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                    break;
                }
            }
            if (isAllGranted) {
                //做下一步操作
                initData();
                setListener();
            } else {
                finish();
                //手动去设置权限
//                openAppDetails();
            }
        }
    }

    private void openAppDetails() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("请到 “应用信息 -> 权限” 中授予！");
        builder.setPositiveButton("去手动授权", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setData(Uri.parse("package:" + getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


}
