package com.dote.family.activitys;

import android.Manifest;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dote.family.ProjectApplication;
import com.dote.family.R;
import com.dote.family.base.BaseActivity;
import com.dote.family.base.BaseFragmentStatePageAdapter;
import com.dote.family.constant.Constants;
import com.dote.family.constant.UrlConstants;
import com.dote.family.db.BmobDB.BmobDBUtils;
import com.dote.family.entity.BmobEntity.DoteAge;
import com.dote.family.entity.BmobEntity.DoteInfo;
import com.dote.family.entity.BmobEntity.DoteKind;
import com.dote.family.entity.BmobEntity.NewVersion;
import com.dote.family.entity.EventBusEntity;
import com.dote.family.fragments.FuliFragment;
import com.dote.family.fragments.IMFragment;
import com.dote.family.fragments.IndexFragments;
import com.dote.family.fragments.MyFragment;
import com.dote.family.utils.CommonUtils;
import com.dote.family.utils.SharedPreferenceUtils;
import com.hyphenate.EMConversationListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.mob.ums.OperationCallback;
import com.mob.ums.UMSSDK;
import com.mob.ums.User;

import net.lemonsoft.lemonbubble.LemonBubble;
import net.lemonsoft.lemonhello.LemonHello;
import net.lemonsoft.lemonhello.LemonHelloAction;
import net.lemonsoft.lemonhello.LemonHelloInfo;
import net.lemonsoft.lemonhello.LemonHelloView;
import net.lemonsoft.lemonhello.interfaces.LemonHelloActionDelegate;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.security.Permission;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/8/8.
 */

public class IndexActivity extends BaseActivity {

    private ViewPager mViewPage;
    private LinearLayout linear_tab01;//首页
    private LinearLayout linear_tab02;//聊天
    private LinearLayout linear_tab03;//我的
    private TextView tv_tab01;
    private TextView tv_tab02;
    private TextView tv_tab03;
    private ImageView iv_tab01;
    private ImageView iv_tab02;
    private ImageView iv_tab03;
//    private ImageView iv_red_point;
    private List<Fragment> fragmentList;
    private IndexFragments indexFragment;
//    private IMFragment IMFragment;
    private FuliFragment fuliFragment;
    private MyFragment myFragment;
    private String TAG = "IndexActivity";
    private static final int NOTIFICATION =0x3;
    private NotificationCompat.Builder builder;
    private NotificationManager manager;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_index;
    }

    @Override
    protected void initView() {
        mViewPage = baseFindViewById(R.id.mViewPager);
        tv_tab01 = baseFindViewById(R.id.tv_tab01);
        tv_tab02 = baseFindViewById(R.id.tv_tab02);
        tv_tab03 = baseFindViewById(R.id.tv_tab03);
//        iv_red_point = baseFindViewById(R.id.iv_red_point);
        linear_tab01 = baseFindViewById(R.id.linear_tab01);
        linear_tab02 = baseFindViewById(R.id.linear_tab02);
        linear_tab03 = baseFindViewById(R.id.linear_tab03);
        iv_tab01 = baseFindViewById(R.id.iv_tab01);
        iv_tab02 = baseFindViewById(R.id.iv_tab02);
        iv_tab03 = baseFindViewById(R.id.iv_tab03);
        fragmentList = new ArrayList<Fragment>();
        indexFragment = new IndexFragments();
        myFragment = new MyFragment();
//        IMFragment = new IMFragment();
        fuliFragment = new FuliFragment();
        //检查更新
        checkVersion();
        //初始化notify
        builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle(getResources().getString(R.string.version_down));
        builder.setContentText(getResources().getString(R.string.version_downloading));
        builder.setProgress(100,0,false);
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        manager.notify(NOTIFICATION, builder.build());
    }

    private void checkVersion() {
        BmobDBUtils.checkNewVersion(this.getApplicationContext());
    }

    @Override
    protected void setListener() {
        linear_tab01.setOnClickListener(this);
        linear_tab02.setOnClickListener(this);
        linear_tab03.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        fragmentList.add(indexFragment);
//        fragmentList.add(IMFragment);
        fragmentList.add(fuliFragment);
        fragmentList.add(myFragment);
        mViewPage.setAdapter(new BaseFragmentStatePageAdapter(this,TAG,mViewPage,fragmentList));
        mViewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setChecked(position,false);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
//        IMFragment.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {
//            @Override
//            public void onListItemClicked(EMConversation conversation) {
//                startActivity(new Intent(IndexActivity.this, IMActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, conversation.conversationId()));
//            }
//        });
        autoLogin();
        registMsgListener();

    }

    @Override
    protected String[] usePermission() {
        return new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.linear_tab01:
                setChecked(0,true);
                break;
            case R.id.linear_tab02:
                setChecked(1,true);
                break;
            case R.id.linear_tab03:
                setChecked(2,true);
                break;
            default:
                break;
        }
    }

    @Override
    public void getEventBusValue(EventBusEntity entity) {
        super.getEventBusValue(entity);
        if(entity.getActivityName().equals("LoginActivity")) {
            ProjectApplication.mUser = (User)entity.getValue();
            myFragment.updateLoginStatus();
            CommonUtils.EaseUILogin(ProjectApplication.mUser.phone.get(),
                    Constants.EASE_PSW);
            fuliFragment.updatePage();
        } else if(entity.getActivityName().equals("queryAllByPage")){
            indexFragment.queryInfoSuccess((List<DoteInfo>)entity.getValue());
        } else if(entity.getActivityName().equals("insertDote")) {
            BmobDBUtils.queryAllByPage(1);
        } else if(entity.getActivityName().equals("quertAge")) {
            indexFragment.queryAgeSuccess((List<DoteAge>)entity.getValue());
        } else if(entity.getActivityName().equals("quertKindByBigKind")) {
            indexFragment.queryKindsSuccess((List<DoteKind>)entity.getValue());
        } else if(entity.getActivityName().equals("getMessage")) {
//            iv_red_point.setVisibility(View.VISIBLE);
            indexFragment.setUnreadPointisVisible(true);
        } else if(entity.getActivityName().equals("checkNewVersionSuccess")) {
            NewVersion version = (NewVersion) entity.getValue();
            createUpdateDialog(version);
        }
    }

    //创建升级提示dialog
    private void createUpdateDialog(final NewVersion version) {
        final LemonHelloInfo dialog = new LemonHelloInfo()
                .setTitle(getResources().getString(R.string.version_findnew))
                .setContent(version.getVersionDesc())
                .addAction(new LemonHelloAction(getResources().getString(R.string.version_cancel), new LemonHelloActionDelegate() {
                    @Override
                    public void onClick(LemonHelloView lemonHelloView, LemonHelloInfo lemonHelloInfo, LemonHelloAction lemonHelloAction) {
                        lemonHelloView.hide();
                    }
                }))
                .addAction(new LemonHelloAction(getResources().getString(R.string.version_download), Color.RED, new LemonHelloActionDelegate() {
                    @Override
                    public void onClick(LemonHelloView lemonHelloView, LemonHelloInfo lemonHelloInfo, LemonHelloAction lemonHelloAction) {
                        //notification 下载
                        download(version);
                        lemonHelloView.hide();
                    }
                }));
        dialog.show(this);
    }

    private void download(NewVersion version) {
        OkGo.<File>get(version.getVersionUrl()).tag(this).execute(new FileCallback() {

            @Override
            public void onSuccess(Response<File> response) {
                manager.cancel(NOTIFICATION);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(response.body()), "application/vnd.android.package-archive");
                startActivity(intent);
            }

            @Override
            public void downloadProgress(Progress progress) {
                super.downloadProgress(progress);
                builder.setProgress(100,(int)(progress.fraction*100),false);
                builder.setContentText(String.format(getResources().getString(R.string.version_progress),(int)(progress.fraction*100)+"%"));
                manager.notify(NOTIFICATION,builder.build());
            }

            @Override
            public void onError(Response<File> response) {
                super.onError(response);
            }
        });
    }

    public void setChecked(int i,boolean isClicked) {

        if (i == 0) {
            tv_tab01.setTextColor(getResources().getColor(R.color.T4));
            tv_tab02.setTextColor(getResources().getColor(R.color.T9));
            tv_tab03.setTextColor(getResources().getColor(R.color.T9));
            iv_tab01.setImageResource(R.drawable.bottom_home_p_ic);
            iv_tab02.setImageResource(R.drawable.bottom_yy_d_ic);
            iv_tab03.setImageResource(R.drawable.bottom_my_d_ic);
            mViewPage.setCurrentItem(0,true);
        } else if (i == 1) {
            tv_tab01.setTextColor(getResources().getColor(R.color.T9));
            tv_tab02.setTextColor(getResources().getColor(R.color.T4));
            tv_tab03.setTextColor(getResources().getColor(R.color.T9));
            iv_tab01.setImageResource(R.drawable.bottom_home_d_ic);
            iv_tab02.setImageResource(R.drawable.bottom_yy_p_ic);
            iv_tab03.setImageResource(R.drawable.bottom_my_d_ic);
            mViewPage.setCurrentItem(1,true);
        } else if (i == 2) {
//            if(ProjectApplication.mUser == null){
//                startActivity(LoginActivity.class);
//                if(!isClicked){
//                    mViewPage.setCurrentItem(1,true);
//                }
//                return;
//            }
            tv_tab01.setTextColor(getResources().getColor(R.color.T9));
            tv_tab02.setTextColor(getResources().getColor(R.color.T9));
            tv_tab03.setTextColor(getResources().getColor(R.color.T4));
            iv_tab01.setImageResource(R.drawable.bottom_home_d_ic);
            iv_tab02.setImageResource(R.drawable.bottom_yy_d_ic);
            iv_tab03.setImageResource(R.drawable.bottom_my_p_ic);
            mViewPage.setCurrentItem(2,true);
        }
    }

    private void autoLogin(){
        if(!TextUtils.isEmpty((String)SharedPreferenceUtils.getParam(this,Constants.USERNAME,""))){
            UMSSDK.loginWithPhoneNumber(Constants.COUNTRY_CODE,(String)SharedPreferenceUtils.getParam(this,Constants.USERNAME,""),
                    (String)SharedPreferenceUtils.getParam(this,Constants.PASSWORD,""),new OperationCallback<User>(){
                @Override
                public void onSuccess(User user) {
                    super.onSuccess(user);
                    EventBus.getDefault().post(new EventBusEntity("LoginActivity",user));
                }

                @Override
                public void onFailed(Throwable throwable) {
                    super.onFailed(throwable);
                }

                @Override
                public void onCancel() {
                    super.onCancel();
                }
            });
        }
    }


    EMMessageListener msgListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            //收到消息
//            IMFragment.refresh();
            EventBus.getDefault().post(new EventBusEntity("getMessage",null));
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //收到透传消息
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
            //收到已读回执
        }

        @Override
        public void onMessageDelivered(List<EMMessage> message) {
            //收到已送达回执
        }
        @Override
        public void onMessageRecalled(List<EMMessage> messages) {
            //消息被撤回
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
            //消息状态变动
        }
    };
    private void registMsgListener(){
        EMClient.getInstance().chatManager().addMessageListener(msgListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().chatManager().removeMessageListener(msgListener);
    }

    final HashMap<String,Object> updateMaps = new HashMap<String,Object>();
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode ==  Constants.PHOTO_REQUEST_CALLBACK) {
                myFragment.images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (myFragment.images != null) {
                    UMSSDK.uploadAvatar(myFragment.images.get(0).path,new OperationCallback<HashMap<String, Object>>(){
                        @Override
                        public void onSuccess(HashMap<String, Object> stringObjectHashMap) {
                            super.onSuccess(stringObjectHashMap);
                            List<String> list = (List<String>)stringObjectHashMap.get("avatar");
                            final String[] strings = new String[list.size()];
                            for(int i = 0;i<list.size();i++){
                                strings[i] = list.get(i);
                            }
                            updateMaps.put("avatarId",stringObjectHashMap.get("id"));
                            updateMaps.put("avatar",strings);
                            ProjectApplication.mUser.avatarId.set((String)stringObjectHashMap.get("id"));
                            ProjectApplication.mUser.avatar.set(strings);
                            myFragment.updateLoginStatus();
                            UMSSDK.updateUserInfo(updateMaps,new OperationCallback<Void>(){
                                @Override
                                public void onSuccess(Void aVoid) {
                                    super.onSuccess(aVoid);
                                    BmobDBUtils.updateIcon(ProjectApplication.mUser.id.get(),strings[0]);
                                }

                                @Override
                                public void onFailed(Throwable throwable) {
                                    super.onFailed(throwable);
                                }

                                @Override
                                public void onCancel() {
                                    super.onCancel();
                                }
                            });
                        }

                        @Override
                        public void onFailed(Throwable throwable) {
                            super.onFailed(throwable);
                        }

                        @Override
                        public void onCancel() {
                            super.onCancel();
                        }
                    });
                }
            }
        }
    }
}
