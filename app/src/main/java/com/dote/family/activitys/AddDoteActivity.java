package com.dote.family.activitys;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.dote.family.ProjectApplication;
import com.dote.family.R;
import com.dote.family.base.BaseActivity;
import com.dote.family.constant.Constants;
import com.dote.family.db.BmobDB.BmobDBUtils;
import com.dote.family.entity.BmobEntity.DoteAge;
import com.dote.family.entity.BmobEntity.DoteInfo;
import com.dote.family.entity.BmobEntity.DoteKind;
import com.dote.family.entity.EventBusEntity;
import com.dote.family.entity.item.InsertPicItem;
import com.dote.family.entity.item.PicItem;
import com.dote.family.utils.CommonUtils;
import com.dote.family.view.TitleLayout;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;

import net.lemonsoft.lemonbubble.LemonBubble;
import net.lemonsoft.lemonhello.LemonHelloAction;
import net.lemonsoft.lemonhello.LemonHelloInfo;
import net.lemonsoft.lemonhello.LemonHelloView;
import net.lemonsoft.lemonhello.interfaces.LemonHelloActionDelegate;

import org.greenrobot.eventbus.EventBus;
import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UploadBatchListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by Administrator on 2017/8/16.
 */

public class AddDoteActivity extends BaseActivity {
    private TitleLayout mTitleLayout;
    private EditText et_addkind,et_addsex,et_addage,et_adddesc;
    private RecyclerView rv_addpic;
    private Button btn_submit;
    public static final String TAG = "AddDoteActivity";
    private InsertPicAdapter adapter;
    private List<MultiItemEntity> mData = new ArrayList<MultiItemEntity>();
    private ArrayList<ImageItem> images;
    private String bigKind;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_add;
    }

    @Override
    protected void initView() {
        mTitleLayout = baseFindViewById(R.id.add_title);
        et_addkind = baseFindViewById(R.id.et_addkind);
        et_addsex = baseFindViewById(R.id.et_addsex);
        et_addage = baseFindViewById(R.id.et_addage);
        et_adddesc = baseFindViewById(R.id.et_adddesc);
        rv_addpic = baseFindViewById(R.id.rv_addpic);
        btn_submit = baseFindViewById(R.id.btn_submit);
    }

    @Override
    protected void setListener() {
        mTitleLayout.setBackClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        et_addkind.setOnClickListener(this);
        et_addsex.setOnClickListener(this);
        et_addage.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        adapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                if(mData.size()-1 == i) {
                    CommonUtils.showPhotoDialog(AddDoteActivity.this,false,images);
                }
            }
        });
    }

    @Override
    protected void initData() {
        mTitleLayout.setMoreGone();
        mTitleLayout.setSearchGone();
        mTitleLayout.setTitle(getResources().getString(R.string.add_title));
        //隐藏光标
        et_addkind.setCursorVisible(false);
        et_addsex.setCursorVisible(false);
        et_addage.setCursorVisible(false);
        //不弹出软键盘
        et_addkind.setInputType(InputType.TYPE_NULL);
        et_addsex.setInputType(InputType.TYPE_NULL);
        et_addage.setInputType(InputType.TYPE_NULL);
        rv_addpic.setLayoutManager(new GridLayoutManager(this,4));
        mData.add(new InsertPicItem(0,"照片"));
        adapter = new InsertPicAdapter(mData);
        rv_addpic.setAdapter(adapter);
    }

    @Override
    protected String[] usePermission() {
        return new String[0];
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.et_addkind:
                LemonHelloInfo bookMarkInfo = new LemonHelloInfo()
                        .setTitle(getResources().getString(R.string.add_kind_title))
                        .setContent("")
                        .addAction(new LemonHelloAction(getResources().getString(R.string.add_kind_cancel), Color.RED, new LemonHelloActionDelegate() {
                            @Override
                            public void onClick(LemonHelloView helloView, LemonHelloInfo helloInfo, LemonHelloAction helloAction) {
                                helloView.hide();
                                bigKind = "";
                            }
                        }))
                        .addAction(new LemonHelloAction(getResources().getString(R.string.add_kind_cat), new LemonHelloActionDelegate() {
                            @Override
                            public void onClick(LemonHelloView helloView, LemonHelloInfo helloInfo, LemonHelloAction helloAction) {
                                //点击猫咪后显示的数据库数据
                                Intent intent = new Intent(AddDoteActivity.this,DoteKindChooseActivity.class);
                                bigKind = "猫";
                                intent.putExtra("bigkind",bigKind);
                                startActivity(intent);
                                helloView.hide();
                            }
                        }))
                        .addAction(new LemonHelloAction(getResources().getString(R.string.add_kind_dog), new LemonHelloActionDelegate() {
                            @Override
                            public void onClick(LemonHelloView helloView, LemonHelloInfo helloInfo, LemonHelloAction helloAction) {
                                //点击小狗后显示的数据库数据
                                Intent intent = new Intent(AddDoteActivity.this,DoteKindChooseActivity.class);
                                bigKind = "狗";
                                intent.putExtra("bigkind",bigKind);
                                startActivity(intent);
                                helloView.hide();
                            }
                        }));
                bookMarkInfo.show(this);
                break;
            case R.id.et_addsex:
                LemonHelloInfo doteSex = new LemonHelloInfo()
                        .setTitle(getResources().getString(R.string.add_sex_title))
                        .setContent("")
                        .addAction(new LemonHelloAction(getResources().getString(R.string.add_kind_cancel), Color.RED, new LemonHelloActionDelegate() {
                            @Override
                            public void onClick(LemonHelloView helloView, LemonHelloInfo helloInfo, LemonHelloAction helloAction) {
                                helloView.hide();
                            }
                        }))
                        .addAction(new LemonHelloAction(getResources().getString(R.string.add_sex_boy), new LemonHelloActionDelegate() {
                            @Override
                            public void onClick(LemonHelloView helloView, LemonHelloInfo helloInfo, LemonHelloAction helloAction) {
                                EventBus.getDefault().post(new EventBusEntity(TAG,getResources().getString(R.string.add_sex_boy)));
                                helloView.hide();
                            }
                        }))
                        .addAction(new LemonHelloAction(getResources().getString(R.string.add_sex_girl), new LemonHelloActionDelegate() {
                            @Override
                            public void onClick(LemonHelloView helloView, LemonHelloInfo helloInfo, LemonHelloAction helloAction) {
                                EventBus.getDefault().post(new EventBusEntity(TAG,getResources().getString(R.string.add_sex_girl)));
                                helloView.hide();
                            }
                        }));
                doteSex.show(this);
                break;
            case R.id.et_addage:
                Intent intent = new Intent(AddDoteActivity.this,DoteAgeChooseActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_submit:
                if(TextUtils.isEmpty(et_addsex.getText().toString())){
                    toast(getResources().getString(R.string.add_sex_warn));
                    return;
                }
                if(TextUtils.isEmpty(et_addage.getText().toString())){
                    toast(getResources().getString(R.string.add_age_warn));
                    return;
                }
                if(TextUtils.isEmpty(et_addkind.getText().toString())){
                    toast(getResources().getString(R.string.add_kind_warn));
                    return;
                }
                if(TextUtils.isEmpty(et_adddesc.getText().toString())){
                    toast(getResources().getString(R.string.add_desc_warn));
                    return;
                }
                LemonBubble.showRoundProgress(this, getResources().getString(R.string.add_loading));
                final DoteInfo info = new DoteInfo();
                info.setUserId(ProjectApplication.mUser.id.get());
                info.setUserPhone(ProjectApplication.mUser.phone.get());
                info.setValid(true);
                info.setDoteSex(et_addsex.getText().toString());
                info.setDoteAge(et_addage.getText().toString());
                info.setDoteKind(et_addkind.getText().toString());
                info.setDoteBigKind(bigKind);
                info.setUserIconUrl(ProjectApplication.mUser.avatar.get()[0]);
                if(ProjectApplication.mUser.nickname.get().equals(getResources().getString(R.string.default_user))){
                    info.setUserName(CommonUtils.getIMName(ProjectApplication.mUser.phone.get()));
                } else {
                    info.setUserName(ProjectApplication.mUser.nickname.get());
                }
                info.setDoteDesc(et_adddesc.getText().toString());
                if (images != null) {
                    final String[] filePaths = new String[images.size()];
                    for(int i = 0;i<images.size();i++ ) {
                        filePaths[i] = images.get(i).path;
                    }
                    BmobFile.uploadBatch(filePaths, new UploadBatchListener() {
                        @Override
                        public void onSuccess(List<BmobFile> list, List<String> list1) {
                            if(list1.size()==filePaths.length) {
                                info.setDotePicList(list);
                                BmobDBUtils.insertDote(info);
                            }
                        }

                        @Override
                        public void onProgress(int i, int i1, int i2, int i3) {

                        }

                        @Override
                        public void onError(int i, String s) {
                            toast(s);
                        }
                    });
                } else {
                    BmobDBUtils.insertDote(info);
                }

//                List<BmobFile> fileList =new ArrayList<BmobFile>();
//                if (images != null) {
//                for(int i = 0;i<images.size();i++ ) {
//                    BmobFile bmobFile = new BmobFile(new File(images.get(i).path));
//                    fileList.add(bmobFile);
//                }
//                info.setDotePicList(fileList);
//            }
//                BmobDBUtils.insertDote(info);
                break;
        }
    }

    @Override
    public void getEventBusValue(EventBusEntity entity) {
        super.getEventBusValue(entity);
        if(entity.getActivityName().equals(DoteKindChooseActivity.TAG)){
            et_addkind.setText(((DoteKind)entity.getValue()).getValue());
        } else if(entity.getActivityName().equals(AddDoteActivity.TAG)) {
            et_addsex.setText(entity.getValue().toString());
        } else if(entity.getActivityName().equals(DoteAgeChooseActivity.TAG)) {
            et_addage.setText(((DoteAge)entity.getValue()).getDoteage());
        } else if(entity.getActivityName().equals("insertDote")) {
            LemonBubble.showRight(this, getResources().getString(R.string.add_success), 500);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            },500);
        }
    }

    public class InsertPicAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity>{

        public InsertPicAdapter(List<MultiItemEntity> data) {
            super(data);
            addItemType(0,R.layout.insertpic_item);
            addItemType(1,R.layout.picshow_item);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, MultiItemEntity multiItemEntity) {
            switch (multiItemEntity.getItemType()){
                case 0:
                    baseViewHolder.setText(R.id.tv_show,((InsertPicItem)multiItemEntity).getText());
                    break;
                case 1:
                    Glide.with(mContext).load(((PicItem)multiItemEntity).getUrl()).into((ImageView) baseViewHolder.getView(R.id.pic_item));
                    break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode ==  Constants.PHOTO_REQUEST_CALLBACK) {
               images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                mData.clear();
                if (images != null) {
                    for(ImageItem item: images) {
                        PicItem picItem = new PicItem();
                        picItem.setItemType(1);
                        picItem.setUrl(item.path);
                        mData.add(0,picItem);
                    }
                    mData.add(new InsertPicItem(0,"照片"));
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }
}
