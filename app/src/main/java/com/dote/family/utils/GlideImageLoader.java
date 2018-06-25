package com.dote.family.utils;

import android.app.Activity;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lzy.imagepicker.loader.ImageLoader;

import java.io.File;

/**
 * Created by Administrator on 2017/8/23.
 */

public class GlideImageLoader implements ImageLoader {
    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        Glide.with(activity).load(Uri.fromFile(new File(path))).override(width,height).centerCrop().diskCacheStrategy(DiskCacheStrategy.NONE).into(imageView);
    }

    @Override
    public void clearMemoryCache() {
        //这里是清除缓存的方法
    }
}
