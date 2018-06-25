package com.dote.family.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.dote.family.R;
import com.dote.family.activitys.LoginActivity;
import com.dote.family.constant.Constants;
import com.dote.family.entity.EventBusEntity;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.mob.ums.OperationCallback;
import com.mob.ums.UMSSDK;
import com.mob.ums.User;

import net.lemonsoft.lemonbubble.LemonBubble;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by weihui on 2017/8/10. 常用工具类
 */
public class CommonUtils {
    private static String currentTime = "";
    private static String tempFile_head_path = "";
    private static String tempFile_headCrop_path = "";

    /**
     * 判断照相机是否可用 @return
     */
    public static boolean isCameraCanUse() {
        boolean canUse = true;
        Camera mCamera = null;
        try {
            mCamera = Camera.open();
        } catch (Exception e) {
            canUse = false;
        }
        if (canUse) {
            if (mCamera != null) mCamera.release();
            mCamera = null;
        }
        return canUse;
    }

    /**
     * 把Bitmap转Byte
     */
    public static byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * 获取头像或者上传照片用
     * @param activity 上下文
     * @param isSingle 是否是只能单选图片
     * @param images  传回来的images集合，如果有则在选择界面直接打上勾
     */
    public static void showPhotoDialog(final FragmentActivity activity, final boolean isSingle,final ArrayList<ImageItem> images) {
        ImagePicker imagePicker = ImagePicker.getInstance();
        if(isSingle) {
            imagePicker.setSelectLimit(1);
        } else {
            imagePicker.setSelectLimit(9);
        }
        View view = activity.getLayoutInflater().inflate(R.layout.dialog_photo_choose, null);
        final Dialog dialog = new Dialog(activity, R.style.transparentFrameWindowStyle);
        dialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow(); /* 设置显示动画*/
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = activity.getWindowManager().getDefaultDisplay().getHeight(); /* 以下这两句是为了保证按钮可以水平满屏*/
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        Button cancel = (Button) view.findViewById(R.id.bt_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Button photograph = (Button) view.findViewById(R.id.bt_photograph);
        Button photo_select = (Button) view.findViewById(R.id.bt_photo_select);
        //拍照
        photograph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ImageGridActivity.class);
                intent.putExtra(ImageGridActivity.EXTRAS_IMAGES,images);
                intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS,true); // 是否是直接打开相机
                activity.startActivityForResult(intent, Constants.PHOTO_REQUEST_CALLBACK);
                dialog.dismiss();
            }
        });
        //从相册选择
        photo_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ImageGridActivity.class);
                intent.putExtra(ImageGridActivity.EXTRAS_IMAGES,images);
                activity.startActivityForResult(intent, Constants.PHOTO_REQUEST_CALLBACK);
                dialog.dismiss();
            }
        });
        // 设置显示位置
        dialog.onWindowAttributesChanged(wl);
        // 设置点击外围解散
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }


    //环信用户注册和登录
    public static void EaseUILogin(final String username, final String password) {
        if(!EMClient.getInstance().isLoggedInBefore()){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        EMClient.getInstance().createAccount(username,password);
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            EMClient.getInstance().login(username, password, new EMCallBack() {
                @Override
                public void onSuccess() {
                }
                @Override
                public void onError(int i, String s) {
                }

                @Override
                public void onProgress(int i, String s) {
                }
            });
        }
    }

    //环信退出登录
    public static void EaseUIlogout(){
        EMClient.getInstance().logout(false, new EMCallBack() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }

    public static String getIMName(String value){
        return "用户"+value.substring(value.length()-4,value.length());
    }

    /**
     * 获取版本号
     * @return 当前应用的版本号
     */
    public static int getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            int version = info.versionCode;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
