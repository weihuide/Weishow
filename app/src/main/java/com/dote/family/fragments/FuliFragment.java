package com.dote.family.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.dote.family.ProjectApplication;
import com.dote.family.R;
import com.dote.family.activitys.LoginActivity;
import com.dote.family.adapter.FuliAdapter;
import com.dote.family.base.BaseFragment;
import com.dote.family.constant.UrlConstants;
import com.dote.family.entity.OkGoEntity;
import com.dote.family.utils.JsonOkGoCallback;
import com.dote.family.view.RecycleViewDivider;
import com.dote.family.view.TitleLayout;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/8.
 */

public class FuliFragment extends BaseFragment{

    private LinearLayout login_show;
    private Button bt_login;
    private TitleLayout mTitleLayout;
    private SwipeToLoadLayout swipeToLoadLayout;
    private RecyclerView swipe_target;
    private int currentPage = 1;
    private FuliAdapter adapter;
    private List<OkGoEntity.Info> infoList = new ArrayList<OkGoEntity.Info>();
    private int lastPosition = 0;

    @Override
    protected void initData(Bundle arguments) {
        mTitleLayout.setBackGone();
        mTitleLayout.setMoreGone();
        mTitleLayout.setSearchGone();
        mTitleLayout.setTitle(getResources().getString(R.string.tab02_value));
        swipe_target.setLayoutManager(new LinearLayoutManager(getActivity()));
        swipe_target.addItemDecoration(new RecycleViewDivider(getActivity(),LinearLayoutManager.VERTICAL,20,getResources().getColor(R.color.transparent)));
        adapter = new FuliAdapter(getActivity(),infoList);
        swipe_target.setAdapter(adapter);
        request(currentPage);
        swipeToLoadLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                lastPosition = 0;
                request(1);
            }
        });

        swipeToLoadLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                currentPage ++;
                lastPosition = ((LinearLayoutManager)swipe_target.getLayoutManager()).findLastVisibleItemPosition();
                request(currentPage);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        updatePage();
    }

    @Override
    protected void initView() {
        login_show = findViewById(R.id.login_show);
        bt_login = findViewById(R.id.bt_login);
        mTitleLayout = findViewById(R.id.im_title);
        swipeToLoadLayout = findViewById(R.id.swipeToLoadLayout);
        swipe_target = findViewById(R.id.swipe_target);
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),LoginActivity.class));
            }
        });
    }

    @Override
    protected void setListener() {
    }

    public void updatePage() {
        if(ProjectApplication.mUser == null) {
            login_show.setVisibility(View.VISIBLE);
            swipeToLoadLayout.setVisibility(View.GONE);
        } else {
            login_show.setVisibility(View.GONE);
            swipeToLoadLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onLazyLoad() {
      updatePage();
    }

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_im;
    }

    @Override
    public void widgetClick(View v) {

    }

    private void request(final int page) {

        OkGo.<OkGoEntity>get(UrlConstants.DEMO_URL+page).tag(this).execute(new JsonOkGoCallback<OkGoEntity>(){
            @Override
            public void onSuccess(Response<OkGoEntity> response) {
                OkGoEntity entity = response.body();
                if(page == 1) {
                    currentPage = 1;
                    infoList.clear();
                    infoList.addAll(entity.results);
                    swipeToLoadLayout.setRefreshing(false);
                } else {
                    infoList.addAll(entity.results);
                    swipeToLoadLayout.setLoadingMore(false);
                }
                adapter.notifyDataSetChanged();
//                ((LinearLayoutManager )swipe_target.getLayoutManager()).scrollToPositionWithOffset(lastPosition,0);
            }
        });
    }
}
