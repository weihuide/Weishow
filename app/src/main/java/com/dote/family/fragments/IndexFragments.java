package com.dote.family.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dote.family.ProjectApplication;
import com.dote.family.activitys.AddDoteActivity;
import com.dote.family.activitys.IMActivity;
import com.dote.family.activitys.IMGroupActivity;
import com.dote.family.activitys.LoginActivity;
import com.dote.family.adapter.BigKindAdapter;
import com.dote.family.adapter.DoteAgeAdapter;
import com.dote.family.adapter.DoteInfoAdapter;
import com.dote.family.adapter.DoteKindAdapter;
import com.dote.family.db.BmobDB.BmobDBUtils;
import com.dote.family.entity.BmobEntity.DoteAge;
import com.dote.family.entity.BmobEntity.DoteInfo;
import com.dote.family.entity.BmobEntity.DoteKind;
import com.dote.family.utils.CommonUtils;
import com.dote.family.view.DropDownMenu;
import com.dote.family.view.RecycleViewDivider;
import com.dote.family.view.TitleLayout;
import com.dote.family.R;
import com.dote.family.base.BaseFragment;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.mob.ums.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by weihui on 2017/8/7.
 */

public class IndexFragments extends BaseFragment {

    private DropDownMenu dropDownMenu;
    private TitleLayout mTitleLayout;
    private String headers[] = {"种类", "品种", "性别", "年龄"};
    private List<View> popupViews = new ArrayList<>();
    private View sonView;
    private RecyclerView bigKindRecycleView;
    private RecyclerView kindRecycleView;
    private RecyclerView sexRecycleView;
    private RecyclerView ageRecycleView;
    List<String> bigKindList = new ArrayList<String>();
    List<DoteKind> kindList = new ArrayList<DoteKind>();
    List<String> sexList = new ArrayList<String>();
    List<DoteAge> ageList = new ArrayList<DoteAge>();


    //子recycleview 的内容
    private SwipeToLoadLayout swipeToLoadLayout;
    private RecyclerView swipe_target;
    private DoteInfoAdapter adapter;
    private List<DoteInfo> infoList = new ArrayList<DoteInfo>();
    private int mCurrentPage = 1;

    @Override
    protected void initData(Bundle arguments) {
        mTitleLayout.setBackGone();
        mTitleLayout.setUnreadVisible();
        mTitleLayout.setSearchGone();
        mTitleLayout.setMoreVisible();
        mTitleLayout.setTitle(getResources().getString(R.string.tab01_value));
        mTitleLayout.setMoreClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ProjectApplication.mUser == null){
                    startActivity(new Intent(getActivity(),LoginActivity.class));
                }  else {
                    startActivity(new Intent(getActivity(),AddDoteActivity.class));
                }
            }
        });
        mTitleLayout.setUnreadClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ProjectApplication.mUser == null){
                    startActivity(new Intent(getActivity(),LoginActivity.class));
                }  else {
                    startActivity(new Intent(getActivity(),IMGroupActivity.class));
                }
            }
        });
        popupViews.clear();
        bigKindRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        bigKindRecycleView.addItemDecoration(new RecycleViewDivider(getActivity(),LinearLayoutManager.VERTICAL));
        kindRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        kindRecycleView.addItemDecoration(new RecycleViewDivider(getActivity(),LinearLayoutManager.VERTICAL));
        sexRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        sexRecycleView.addItemDecoration(new RecycleViewDivider(getActivity(),LinearLayoutManager.VERTICAL));
        ageRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ageRecycleView.addItemDecoration(new RecycleViewDivider(getActivity(),LinearLayoutManager.VERTICAL));
        popupViews.add(bigKindRecycleView);
        popupViews.add(kindRecycleView);
        popupViews.add(sexRecycleView);
        popupViews.add(ageRecycleView);
        initDropDownValues();
        dropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, sonView);

        adapter = new DoteInfoAdapter(getActivity(),infoList);
        swipe_target.setLayoutManager(new LinearLayoutManager(getActivity()));
        swipe_target.addItemDecoration(new RecycleViewDivider(getActivity(),LinearLayoutManager.VERTICAL,20,getResources().getColor(R.color.transparent)));
        swipe_target.setAdapter(adapter);
        //查询数据
        BmobDBUtils.queryByLimitInfos(mBigKind,mKind,mSex,mAge,mCurrentPage);
        swipeToLoadLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCurrentPage = 1;
                BmobDBUtils.queryByLimitInfos(mBigKind,mKind,mSex,mAge,mCurrentPage);
//                BmobDBUtils.queryAllByPage(mCurrentPage);
            }
        });
        swipeToLoadLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mCurrentPage ++;
//                BmobDBUtils.queryAllByPage(mCurrentPage);
                BmobDBUtils.queryByLimitInfos(mBigKind,mKind,mSex,mAge,mCurrentPage);
            }
        });
//        adapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
//            @Override
//            public void onItemClick(View view, int i) {
//                if(ProjectApplication.mUser == null ){
//                    startActivity(new Intent(getActivity(),LoginActivity.class));
//                    return;
//                }
//                if(infoList.get(i).getUserPhone().equals(ProjectApplication.mUser.phone.get())){
//                    Toast.makeText(getActivity(),getResources().getString(R.string.im_error),Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                Intent IMIntent = new Intent(getActivity(), IMActivity.class);
//                IMIntent.putExtra("userId", infoList.get(i).getUserPhone());
//                IMIntent.putExtra("chatType", EMMessage.ChatType.Chat);
//                startActivity(IMIntent);
//            }
//        });
    }



    private BigKindAdapter dropDownBigKindAdapter;
    private BigKindAdapter dropDownSexAdapter;
    private DoteAgeAdapter dropDownAgeAdapter;
    private DoteKindAdapter dropDownKindAdapter;
    private boolean isBigKindChoose = false;
    private String mBigKind;
    private String mKind;
    private String mAge;
    private String mSex;

    private void initDropDownValues() {
        bigKindList.clear();
        sexList.clear();
        kindList.clear();
        ageList.clear();
        bigKindList.add("狗");
        bigKindList.add("猫");
        dropDownBigKindAdapter = new BigKindAdapter(bigKindList);
        bigKindRecycleView.setAdapter(dropDownBigKindAdapter);
        sexList.add(getResources().getString(R.string.nolimit));
        sexList.add(getResources().getString(R.string.add_sex_boy));
        sexList.add(getResources().getString(R.string.add_sex_girl));
        dropDownSexAdapter = new BigKindAdapter(sexList);
        sexRecycleView.setAdapter(dropDownSexAdapter);
        BmobDBUtils.quertAge(true);
        dropDownBigKindAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                dropDownMenu.setTabText(bigKindList.get(i));
                isBigKindChoose = true;
                mBigKind = bigKindList.get(i);
                BmobDBUtils.queryByLimitInfos(mBigKind,mKind,mSex,mAge,1);
                BmobDBUtils.quertKindByBigKind(mBigKind,true);
                dropDownMenu.closeMenu();
            }
        });
        dropDownSexAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                dropDownMenu.setTabText(sexList.get(i));
                mSex = sexList.get(i);
                BmobDBUtils.queryByLimitInfos(mBigKind,mKind,mSex,mAge,1);
                dropDownMenu.closeMenu();
            }
        });
    }

    public void queryAgeSuccess(List<DoteAge> list) {
        ageList.addAll(list);
        dropDownAgeAdapter = new DoteAgeAdapter(ageList);
        ageRecycleView.setAdapter(dropDownAgeAdapter);
        dropDownAgeAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                dropDownMenu.setTabText(ageList.get(i).getDoteage());
                mAge = ageList.get(i).getDoteage();
                BmobDBUtils.queryByLimitInfos(mBigKind,mKind,mSex,mAge,1);
                dropDownMenu.closeMenu();
            }
        });
    }

    public void queryKindsSuccess(List<DoteKind> list) {
        kindList.clear();
        kindList.addAll(list);
        dropDownKindAdapter = new DoteKindAdapter(kindList);
        kindRecycleView.setAdapter(dropDownKindAdapter);
        dropDownKindAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                dropDownMenu.setTabText(kindList.get(i).getValue());
                mKind = kindList.get(i).getValue();
                BmobDBUtils.queryByLimitInfos(mBigKind,mKind,mSex,mAge,1);
                dropDownMenu.closeMenu();
            }
        });
    }

    /**
     * 查询详情成功
     * @param infos
     */
    public void queryInfoSuccess(List<DoteInfo> infos){
        if(infos.size() <= 10){
            swipeToLoadLayout.setLoadMoreEnabled(false);
        } else {
            swipeToLoadLayout.setLoadMoreEnabled(true);
        }
        if(mCurrentPage == 1){
            infoList.clear();
            swipeToLoadLayout.setRefreshing(false);
        } else {
            swipeToLoadLayout.setLoadingMore(false);
        }
        infoList.addAll(infos);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void initView() {
        dropDownMenu = findViewById(R.id.dropDownMenu);
        mTitleLayout = findViewById(R.id.index_title);
        sonView = View.inflate(getActivity(),R.layout.fragment_index,null);
        swipeToLoadLayout = (SwipeToLoadLayout)sonView.findViewById(R.id.swipeToLoadLayout);
        swipe_target = (RecyclerView)sonView.findViewById(R.id.swipe_target);
        bigKindRecycleView = new RecyclerView(getActivity());
        kindRecycleView = new RecyclerView(getActivity());
        sexRecycleView = new RecyclerView(getActivity());
        ageRecycleView = new RecyclerView(getActivity());
    }


    @Override
    protected void setListener() {
    }

    @Override
    protected void onLazyLoad() {
        //获取未读消息个数
        if(ProjectApplication.mUser != null){
            int i = EMClient.getInstance().chatManager().getUnreadMessageCount();
            if(i != 0) {
                setUnreadPointisVisible(true);
            } else {
                setUnreadPointisVisible(false);
            }
        } else {
            setUnreadPointisVisible(false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //获取未读消息个数
        if(ProjectApplication.mUser != null){
            int i = EMClient.getInstance().chatManager().getUnreadMessageCount();
            if(i != 0) {
                setUnreadPointisVisible(true);
            } else {
                setUnreadPointisVisible(false);
            }
        } else {
            setUnreadPointisVisible(false);
        }
    }

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_index_main;
    }

    @Override
    public void widgetClick(View v) {

    }


    public void setUnreadPointisVisible(boolean flag) {
        if(flag) {
            mTitleLayout.setUnreadPointVisible();
        } else {
            mTitleLayout.setUnreadPointGone();
        }
    }



}
