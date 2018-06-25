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
 * 上拉刷新的时候的布局
 * Created by weihui on 2017/8/10.
 */

public class RefreshHeaderView extends LinearLayout implements SwipeRefreshTrigger,SwipeTrigger {

    private TextView tv_refreshHead;
    public RefreshHeaderView(Context context) {
        super(context);
        init(context);
    }

    public RefreshHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RefreshHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.refresh_head_layout,this);
        tv_refreshHead = (TextView)findViewById(R.id.tv_refreshhead);
    }

    @Override
    public void onRefresh() {
        tv_refreshHead.setText("正在刷新...");
    }

    @Override
    public void onPrepare() {

    }

    @Override
    public void onMove(int i, boolean b, boolean b1) {
        if (!b) {
            if (i >= getHeight()) {
                tv_refreshHead.setText("松手刷新");
            } else {
                tv_refreshHead.setText("下拉刷新");
            }
        }
    }

    @Override
    public void onRelease() {

    }

    @Override
    public void onComplete() {
        tv_refreshHead.setText("刷新完成");
    }

    @Override
    public void onReset() {

    }
}
