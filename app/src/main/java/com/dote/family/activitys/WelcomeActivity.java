package com.dote.family.activitys;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.dote.family.R;
import com.dote.family.base.BaseActivity;
import com.dote.family.constant.Constants;
import com.dote.family.utils.SharedPreferenceUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/14.
 */

public class WelcomeActivity extends BaseActivity {

    private ViewPager vp_welocom;
    private int[] welcom_imgs = new int[]{R.drawable.bg_page_01, R.drawable.bg_page_02, R.drawable.bg_page_03,
            R.drawable.bg_page_04, R.drawable.bg_page_05};
    private List<View> page_views = new ArrayList<View>();

    @Override
    protected int getLayoutId() {
        if((boolean)SharedPreferenceUtils.getParam(this, Constants.HAS_WELCOME,false)){
            startActivity(IndexActivity.class);
            finish();
        }
        return R.layout.activity_welcome;
    }

    @Override
    protected void initView() {
        vp_welocom = (ViewPager) findViewById(R.id.vp_welocom);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        for (int i = 0; i < welcom_imgs.length; i++) {
            final View view = View.inflate(this, R.layout.welcomepage_item, null);
            Glide.with(this).load(welcom_imgs[i]).into(new SimpleTarget<GlideDrawable>() {
                @Override
                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                    view.setBackground(resource);
                }
            });
            page_views.add(view);
            if (i == welcom_imgs.length - 1) {
                View viewin = view.findViewById(R.id.v_in);
                viewin.setVisibility(View.VISIBLE);
                viewin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SharedPreferenceUtils.setParam(WelcomeActivity.this,Constants.HAS_WELCOME,true);
                        startActivity(IndexActivity.class);
                        finish();
                    }
                });
            }
        }
        WelcomPagerAdapter welcomPagerAdapter = new WelcomPagerAdapter(page_views);
        vp_welocom.setAdapter(welcomPagerAdapter);
    }

    @Override
    protected String[] usePermission() {
        return new String[0];
    }

    @Override
    public void widgetClick(View v) {

    }



    public class WelcomPagerAdapter extends PagerAdapter {
        private final List<View> mListViews;

        public WelcomPagerAdapter(List<View> mListViews) {
            this.mListViews = mListViews;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mListViews.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            container.addView(mListViews.get(position), 0);
            return mListViews.get(position);
        }

        @Override
        public int getCount() {
            return mListViews.size();
        }

        public View getItem(int position) {
            return mListViews.get(position);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
    }
}
