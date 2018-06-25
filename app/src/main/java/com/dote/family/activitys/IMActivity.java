package com.dote.family.activitys;

import android.Manifest;
import android.content.Intent;
import android.view.View;

import com.dote.family.R;
import com.dote.family.base.BaseActivity;
import com.dote.family.utils.CommonUtils;
import com.dote.family.view.TitleLayout;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/29.
 */

public class IMActivity extends BaseActivity {

    private EaseChatFragment chatFragment;
    private TitleLayout im_title;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_im;
    }

    @Override
    protected void initView() {
        im_title = baseFindViewById(R.id.im_title);
        // 这里直接使用EaseUI封装好的聊天界面
        chatFragment = new EaseChatFragment();
        // 将参数传递给聊天界面
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.ec_layout_container, chatFragment).commit();
        EaseUI.getInstance().setUserProfileProvider(new EaseUI.EaseUserProfileProvider() {
            @Override
            public EaseUser getUser(String username) {
                return null;
            }

        });
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        chatFragment.hideTitleBar();
    }

    @Override
    protected void initData() {
        im_title.setMoreGone();
        im_title.setBackVisible();
        im_title.setSearchGone();
        im_title.setTitle(CommonUtils.getIMName(getIntent().getStringExtra("userId")));
        im_title.setBackClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        chatFragment.setChatFragmentHelper(new EaseChatFragment.EaseChatFragmentHelper() {
            @Override
            public void onSetMessageAttributes(EMMessage message) {

            }

            @Override
            public void onEnterToChatDetails() {

            }

            @Override
            public void onAvatarClick(String username) {

            }

            @Override
            public void onAvatarLongClick(String username) {

            }

            @Override
            public boolean onMessageBubbleClick(EMMessage message) {
                if(message.getType() == EMMessage.Type.IMAGE) {
                    //((EMImageMessageBody)message.getBody()).getThumbnailUrl()
                    Intent intent = new Intent(IMActivity.this,BigPicShowActivity.class);
                    ArrayList<String> urlList = new ArrayList<String>();
                    urlList.add(((EMImageMessageBody)message.getBody()).getRemoteUrl());
                    intent.putStringArrayListExtra("urlList",urlList);
                    startActivity(intent);
                    return true;
                }
                return false;
            }

            @Override
            public void onMessageBubbleLongClick(EMMessage message) {

            }

            @Override
            public boolean onExtendMenuItemClick(int itemId, View view) {
                return false;
            }

            @Override
            public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
                return null;
            }
        });
    }

    @Override
    protected String[] usePermission() {
        return new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA};
    }

    @Override
    public void widgetClick(View v) {

    }
}
