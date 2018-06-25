package com.dote.family.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dote.family.ProjectApplication;
import com.dote.family.R;
import com.dote.family.activitys.BigPicShowActivity;
import com.dote.family.activitys.IMActivity;
import com.dote.family.activitys.LoginActivity;
import com.dote.family.entity.BmobEntity.DoteInfo;
import com.dote.family.utils.GlideCircleTransform;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * 动物详情adapter，包含了图片adapter
 * Created by weihui on 2017/8/28.
 */

public class DoteInfoAdapter extends BaseQuickAdapter<DoteInfo> {
    private Context mContext;

    public DoteInfoAdapter(Context context, List<DoteInfo> data) {
        super(R.layout.fragment_index_item_update, data);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final DoteInfo doteInfo) {
        baseViewHolder.setText(R.id.info_kind,doteInfo.getDoteKind()/*+"("+doteInfo.getDoteBigKind()+")"*/);
        baseViewHolder.setText(R.id.info_age,"("+doteInfo.getDoteAge()+",");
        baseViewHolder.setText(R.id.info_sex,doteInfo.getDoteSex() +")");
        baseViewHolder.setText(R.id.info_desc,doteInfo.getDoteDesc());
        Glide.with(mContext).load(doteInfo.getUserIconUrl()).transform(new GlideCircleTransform(mContext)).into(((ImageView)baseViewHolder.getView(R.id.iv_user_head)));
        baseViewHolder.setText(R.id.tv_user_name,doteInfo.getUserName());
        baseViewHolder.setText(R.id.tv_time,doteInfo.getCreatedAt());
        LinearLayout layout = baseViewHolder.getView(R.id.ll_info_pic);
        RecyclerView recycleView = baseViewHolder.getView(R.id.rv_info_pic);
        if(null == doteInfo.getDotePicList()|| doteInfo.getDotePicList().size() == 0) {
            layout.setVisibility(View.GONE);
            recycleView.setVisibility(View.GONE);
        } else {
            layout.setVisibility(View.VISIBLE);
            recycleView.setVisibility(View.VISIBLE);
            showPic(recycleView,doteInfo.getDotePicList());
        }

        baseViewHolder.getView(R.id.iv_user_head).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ProjectApplication.mUser == null ){
                    mContext.startActivity(new Intent(mContext,LoginActivity.class));
                    return;
                }
                if(doteInfo.getUserPhone().equals(ProjectApplication.mUser.phone.get())){
                    Toast.makeText(mContext,mContext.getResources().getString(R.string.im_error),Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent IMIntent = new Intent(mContext, IMActivity.class);
                IMIntent.putExtra("userId", doteInfo.getUserPhone());
                IMIntent.putExtra("chatType", EMMessage.ChatType.Chat);
                mContext.startActivity(IMIntent);
            }
        });
    }

    private void showPic(RecyclerView recycleView, final List<BmobFile> files){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycleView.setLayoutManager(linearLayoutManager);
//            recycleView.addItemDecoration(new RecycleViewDivider(getActivity(),LinearLayoutManager.VERTICAL));
        DotePicAdapter adapter = new DotePicAdapter(files);
        recycleView.setAdapter(adapter);

        adapter.setOnRecyclerViewItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                Intent intent = new Intent(mContext,BigPicShowActivity.class);
                ArrayList<String> urlList = new ArrayList<String>();
                for(BmobFile file : files) {
                    urlList.add(file.getUrl());
                }
                intent.putStringArrayListExtra("urlList",urlList);
                mContext.startActivity(intent);
            }
        });
    }

}