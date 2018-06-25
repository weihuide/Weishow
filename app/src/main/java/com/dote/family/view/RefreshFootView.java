package com.dote.family.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.SwipeRefreshTrigger;
import com.aspsine.swipetoloadlayout.SwipeTrigger;
import com.dote.family.R;

/**
 * 上拉加载更多的时候的布局
 * Created by weihui on 2017/8/10.
 */

public class RefreshFootView extends LinearLayout implements SwipeRefreshTrigger,SwipeTrigger {

    private TextView tv_refreshFoot;
    public RefreshFootView(Context context) {
        super(context);
        init(context);
    }

    public RefreshFootView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RefreshFootView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.refresh_foot_layout,this);
        tv_refreshFoot = (TextView)findViewById(R.id.tv_refreshfoot);
    }

    @Override
    public void onRefresh() {
        tv_refreshFoot.setText("正在刷新...");
    }

    @Override
    public void onPrepare() {

    }

    @Override
    public void onMove(int i, boolean b, boolean b1) {
        if (!b) {
            if (i >= getHeight()) {
                tv_refreshFoot.setText("松手刷新");
            } else {
                tv_refreshFoot.setText("上拉刷新");
            }
        }
    }

    @Override
    public void onRelease() {

    }

    @Override
    public void onComplete() {
        tv_refreshFoot.setText("刷新完成");
    }

    @Override
    public void onReset() {

    }
}
