package com.dote.family.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dote.family.R;
import com.dote.family.base.BaseFragment;
import com.dote.family.view.TitleLayout;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.ui.EaseConversationListFragment;

import java.util.List;

/**
 * Created by Administrator on 2017/8/8.
 */

public class IMFragment extends EaseConversationListFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void setUpView() {
        super.setUpView();
    }

    @Override
    protected void onConnectionConnected() {
        super.onConnectionConnected();
    }

    @Override
    protected void onConnectionDisconnected() {
        super.onConnectionDisconnected();
    }

    @Override
    public void refresh() {
        super.refresh();
    }

    @Override
    protected List<EMConversation> loadConversationList() {
        return super.loadConversationList();
    }

    @Override
    protected void hideSoftKeyboard() {
        super.hideSoftKeyboard();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void setConversationListItemClickListener(EaseConversationListItemClickListener listItemClickListener) {
        super.setConversationListItemClickListener(listItemClickListener);
    }
}
