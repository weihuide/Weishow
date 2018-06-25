package com.dote.family.activitys;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dote.family.R;
import com.dote.family.adapter.DoteKindAdapter;
import com.dote.family.base.BaseActivity;
import com.dote.family.db.BmobDB.BmobDBUtils;
import com.dote.family.entity.BmobEntity.DoteKind;
import com.dote.family.entity.EventBusEntity;
import com.dote.family.view.RecycleViewDivider;
import com.dote.family.view.TitleLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by Administrator on 2017/8/21.
 */

public class DoteKindChooseActivity extends BaseActivity {

    private TitleLayout mTitleLayout;
    private List<DoteKind> kindList;
    private String bigKind;
    private SwipeToLoadLayout kind_swipeToLoadLayout;
    private RecyclerView swipe_target;
    public static final String TAG = "DoteKindChooseActivity";
    private DoteKindAdapter adapter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_kind;
    }

    @Override
    protected void initView() {
        mTitleLayout = baseFindViewById(R.id.kind_title);
        kind_swipeToLoadLayout = baseFindViewById(R.id.kind_swipeToLoadLayout);
        swipe_target = baseFindViewById(R.id.swipe_target);
        kind_swipeToLoadLayout.setLoadMoreEnabled(false);
        kind_swipeToLoadLayout.setRefreshEnabled(false);
    }

    @Override
    protected void setListener() {
    }

    @Override
    protected void initData() {
        mTitleLayout.setBackVisible();
        mTitleLayout.setMoreGone();
        mTitleLayout.setSearchGone();
        mTitleLayout.setTitle(getResources().getString(R.string.kind_desc_name));
        mTitleLayout.setBackClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        bigKind = getIntent().getStringExtra("bigkind");
        swipe_target.setLayoutManager(new LinearLayoutManager(this));
        swipe_target.addItemDecoration(new RecycleViewDivider(this,LinearLayoutManager.VERTICAL));
        BmobDBUtils.quertKindByBigKind(bigKind,false);
    }

    @Override
    protected String[] usePermission() {
        return new String[0];
    }

    @Override
    public void widgetClick(View v) {

    }

    @Override
    public void getEventBusValue(EventBusEntity entity) {
        super.getEventBusValue(entity);
        if(entity.getActivityName().equals("quertKindByBigKind")) {
            kindList = (List<DoteKind>)entity.getValue();
            adapter = new DoteKindAdapter(kindList);
            adapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, int i) {
                    EventBus.getDefault().post(new EventBusEntity(TAG,kindList.get(i)));
                    finish();
                }
            });
            swipe_target.setAdapter(adapter);
        }
    }

}
