package com.dote.family.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import com.dote.family.entity.EventBusEntity;
import com.dote.family.entity.PageChangeEntity;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by weihui on 2017/8/8.
 */

public class BaseFragmentStatePageAdapter extends FragmentStatePagerAdapter {


    //传递给哪一个界面的eventbus
    private String mEventBusName;
    //fragment集合
    private List<Fragment> mFragmentList;
    //fragment的父布局
    private ViewPager mViewPager;

    //上下文
    private FragmentActivity mContext;

    public BaseFragmentStatePageAdapter(FragmentActivity context,String eventBusName,ViewPager viewPager,List<Fragment> fragmentList){
        super(context.getSupportFragmentManager());
        mContext = context;
        mFragmentList = fragmentList;
        mViewPager = viewPager;
        mEventBusName = eventBusName;
    }
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        super.finishUpdate(container);
        int currentItem = mViewPager.getCurrentItem();
        EventBus.getDefault().post(new EventBusEntity(mEventBusName,new PageChangeEntity(currentItem,"")));
    }
}
