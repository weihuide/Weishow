package com.dote.family.activitys;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dote.family.R;
import com.dote.family.adapter.BigPicPagerAdapter;
import com.dote.family.base.BaseActivity;
import com.dote.family.view.PinchImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/30.
 */

public class BigPicShowActivity extends BaseActivity {

    private ViewPager vp_bigpic;
    private List<String> urlList;
    private int currentPic;
    private List<View> page_views = new ArrayList<View>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bigpic;
    }

    @Override
    protected void initView() {
        vp_bigpic = baseFindViewById(R.id.vp_bigpic);
        urlList = getIntent().getStringArrayListExtra("urlList");
        currentPic = getIntent().getIntExtra("current",0);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        for (int i = 0; i < urlList.size(); i++) {
            View view = View.inflate(this, R.layout.scale_pic_item, null);
            PinchImageView scale_pic_item = (PinchImageView)view.findViewById(R.id.scale_pic_item);
            TextView text = (TextView)view.findViewById(R.id.text);
            Glide.with(this).load(urlList.get(i))
                    .fitCenter()
                    .dontAnimate()
                    .into(scale_pic_item);
            text.setText((i+1)+"/"+urlList.size());
            if(urlList.size() == 1) {
                text.setVisibility(View.GONE);
            } else {
                text.setVisibility(View.VISIBLE);
            }
            page_views.add(view);
        }
        BigPicPagerAdapter adapter = new BigPicPagerAdapter(page_views);
        vp_bigpic.setAdapter(adapter);
        vp_bigpic.setCurrentItem(currentPic);
    }

    @Override
    protected String[] usePermission() {
        return new String[0];
    }

    @Override
    public void widgetClick(View v) {

    }
}
