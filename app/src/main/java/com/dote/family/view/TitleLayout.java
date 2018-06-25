package com.dote.family.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dote.family.R;

/**
 * Created by weihui on 2017/8/15.
 */

public class TitleLayout extends LinearLayout {

    private Button btnSearch, btnMore;
    private TextView title;
    private LinearLayout llBack;
    private RelativeLayout rl_unread;
    private ImageView iv_unread_point;

    public TitleLayout(Context context) {
        super(context);
        init();
    }

    public TitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TitleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        // TODO Auto-generated method stub
        LayoutInflater f = LayoutInflater.from(getContext());
        View v = f.inflate(R.layout.base_title, this);
        llBack = (LinearLayout) findViewById(R.id.title_back);
        btnSearch = (Button) findViewById(R.id.title_search);
        btnMore = (Button) findViewById(R.id.title_more);
        title = (TextView) findViewById(R.id.title_value);
        rl_unread = (RelativeLayout)findViewById(R.id.rl_unread);
        iv_unread_point = (ImageView)findViewById(R.id.iv_unread_point);
    }

    public void setTitle(String s) {
        title.setText(s);
    }

    public void setBackClick(OnClickListener on) {
        llBack.setOnClickListener(on);
    }

    public void setSearchClick(OnClickListener on) {
        btnSearch.setOnClickListener(on);
    }

    public void setMoreClick(OnClickListener on) {
        btnMore.setOnClickListener(on);
    }

    public void setUnreadClick(OnClickListener on) {
        rl_unread.setOnClickListener(on);
    }

    public void setBackGone() {
        llBack.setVisibility(View.GONE);
    }

    public void setUnreadGone(){
        rl_unread.setVisibility(View.GONE);
    }

    public void setUnreadVisible(){
        rl_unread.setVisibility(View.VISIBLE);
    }

    public void setUnreadPointGone() {
        iv_unread_point.setVisibility(View.GONE);
    }

    public void setUnreadPointVisible() {
        iv_unread_point.setVisibility(View.VISIBLE);
    }

    public void setSearchGone() {
        btnSearch.setVisibility(View.GONE);
    }

    public void setMoreGone() {
        btnMore.setVisibility(View.GONE);
    }

    public void setBackVisible() {
        llBack.setVisibility(View.VISIBLE);
    }

    public void setSearchVisible() {
        btnSearch.setVisibility(View.VISIBLE);
    }

    public void setMoreVisible() {
        btnMore.setVisibility(View.VISIBLE);
    }
}
