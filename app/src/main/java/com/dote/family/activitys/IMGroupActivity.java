package com.dote.family.activitys;

import android.Manifest;
import android.content.Intent;
import android.view.View;

import com.dote.family.R;
import com.dote.family.base.BaseActivity;
import com.dote.family.entity.EventBusEntity;
import com.dote.family.utils.CommonUtils;
import com.dote.family.view.TitleLayout;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/29.
 */

public class IMGroupActivity extends BaseActivity {

    private EaseConversationListFragment easeConversationListFragment;
    private TitleLayout im_title;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_im;
    }

    @Override
    protected void initView() {
        im_title = baseFindViewById(R.id.im_title);
        easeConversationListFragment = new EaseConversationListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.ec_layout_container, easeConversationListFragment).commit();

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        easeConversationListFragment.hideTitleBar();
    }

    @Override
    protected void initData() {
        im_title.setMoreGone();
        im_title.setBackVisible();
        im_title.setSearchGone();
        im_title.setTitle("通讯列表");
        im_title.setBackClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        easeConversationListFragment.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {
            @Override
            public void onListItemClicked(EMConversation conversation) {
                startActivity(new Intent(IMGroupActivity.this, IMActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, conversation.conversationId()));
            }
        });
    }


    @Override
    public void getEventBusValue(EventBusEntity entity) {
        super.getEventBusValue(entity);
        if(entity.getActivityName().equals("getMessage")) {
            easeConversationListFragment.refresh();
        }
    }

    @Override
    protected String[] usePermission() {
        return new String[0];
    }

    @Override
    public void widgetClick(View v) {

    }
}
