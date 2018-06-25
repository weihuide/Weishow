package com.dote.family.db.BmobDB;

import android.content.Context;
import android.text.TextUtils;

import com.baidu.platform.comapi.map.C;
import com.dote.family.entity.BmobEntity.DoteAge;
import com.dote.family.entity.BmobEntity.DoteInfo;
import com.dote.family.entity.BmobEntity.DoteKind;
import com.dote.family.entity.BmobEntity.NewVersion;
import com.dote.family.entity.EventBusEntity;
import com.dote.family.utils.CommonUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Administrator on 2017/8/16.
 */

public class BmobDBUtils {

    /**
     * 以下是查询DoteInfo表的数据
     */
    /**
     * 分页查询，查询所有的
     * @param page 需要查询的页码,默认从1开始
     */
    public static void queryAllByPage(int page){
        BmobQuery<DoteInfo> query = new BmobQuery<DoteInfo>();
        query.addWhereEqualTo("isValid",true);
        query.setLimit(10);//默认就是10条数据，分页的
        query.setSkip((page-1) * 10);
        query.findObjects(new FindListener<DoteInfo>() {
            @Override
            public void done(List<DoteInfo> list, BmobException e) {
                if(e == null ){
                    //通过eventbus 发送出去
                    EventBus.getDefault().post(new EventBusEntity("queryAllByPage",list));
                } else {
                    EventBus.getDefault().post(new EventBusEntity("exception",com.dote.family.db.BmobDB.BmobException.bmobErrorValue(e.getErrorCode())));
                }
            }
        });
    }

    /**
     * 条件查询
     * @param bigKind 大种类
     * @param kind 种类
     * @param sex  性别
     * @param age  年龄
     * @param dotePoint  地理位置
     * @param page 页码
     */
    public static void queryByCondition(String bigKind,String kind, String sex, String age, BmobGeoPoint dotePoint, int page){
        BmobQuery<DoteInfo> query = new BmobQuery<DoteInfo>();
        if(null != bigKind) {
            query.addWhereEqualTo("doteBigKind",bigKind);
        }
        if(null != kind) {
            query.addWhereEqualTo("doteKind",kind);
        }
        if(null != sex) {
            query.addWhereEqualTo("doteSex",sex);
        }
        if(null != age) {
            query.addWhereEqualTo("doteAge",age);
        }
        if(null != dotePoint) {
            query.addWhereNear("dotePoint",dotePoint);
        }
        query.addWhereEqualTo("isValid",true);
        query.setLimit(10);//默认就是10条数据，分页的
        query.setSkip((page-1) * 10);
        query.findObjects(new FindListener<DoteInfo>() {
            @Override
            public void done(List<DoteInfo> list, BmobException e) {
                if(e == null ){
                    //通过eventbus 发送出去
                    EventBus.getDefault().post(new EventBusEntity("queryByCondition",list));
                } else {
                    EventBus.getDefault().post(new EventBusEntity("exception",com.dote.family.db.BmobDB.BmobException.bmobErrorValue(e.getErrorCode())));
                }
            }
        });
    }

    /**
     * 通过用户ID查询，主要是做我的所有发布的查询
     * @param userId
     * @param page
     */
    public static void queryByUser(String userId,int page){
        BmobQuery<DoteInfo> query = new BmobQuery<DoteInfo>();
        query.addWhereEqualTo("userId",userId);
        query.setLimit(10);//默认就是10条数据，分页的
        query.setSkip((page-1) * 10);
        query.findObjects(new FindListener<DoteInfo>() {
            @Override
            public void done(List<DoteInfo> list, BmobException e) {
                if(e == null ){
                    //通过eventbus 发送出去
                    EventBus.getDefault().post(new EventBusEntity("queryByUser",list));
                } else {
                    EventBus.getDefault().post(new EventBusEntity("exception",com.dote.family.db.BmobDB.BmobException.bmobErrorValue(e.getErrorCode())));
                }
            }
        });
    }

    /**
     * 插入数据
     * @param info
     */
    public static void insertDote(DoteInfo info){
        info.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if(e == null) {
                    EventBus.getDefault().post(new EventBusEntity("insertDote","发布成功"));
                } else {
                    EventBus.getDefault().post(new EventBusEntity("exception",com.dote.family.db.BmobDB.BmobException.bmobErrorValue(e.getErrorCode())));
                }
            }
        });
    }



    //给下面更新的时候用的，不暴露出去
    private static void inQueryByUser(String userId){
        BmobQuery<DoteInfo> query = new BmobQuery<DoteInfo>();
        query.addWhereEqualTo("userId",userId);
        query.setLimit(Integer.MAX_VALUE);
        query.findObjects(new FindListener<DoteInfo>() {
            @Override
            public void done(List<DoteInfo> list, BmobException e) {
                if(e == null ){
                    //通过eventbus 发送出去
                    EventBus.getDefault().post(new EventBusEntity("queryByUser",list));
                } else {
                    EventBus.getDefault().post(new EventBusEntity("exception",com.dote.family.db.BmobDB.BmobException.bmobErrorValue(e.getErrorCode())));
                }
            }
        });
    }

    /**
     * 修改头像
     * @param userId
     * @param iconUrl
     */
    public static void updateIcon(String userId, final String iconUrl) {
        BmobQuery<DoteInfo> query = new BmobQuery<DoteInfo>();
        query.addWhereEqualTo("userId",userId);
        query.setLimit(Integer.MAX_VALUE);
        query.findObjects(new FindListener<DoteInfo>() {
            @Override
            public void done(List<DoteInfo> list, BmobException e) {
                if(e == null ){
                    List<BmobObject> iconList = new ArrayList<BmobObject>();
                    for(DoteInfo info :list) {
                        DoteInfo mInfo = new DoteInfo();
                        mInfo.setObjectId(info.getObjectId());
                        mInfo.setUserIconUrl(iconUrl);
                        iconList.add(mInfo);
                    }
                    new BmobBatch().updateBatch(iconList).doBatch(new QueryListListener<BatchResult>() {
                        @Override
                        public void done(List<BatchResult> list, BmobException e) {
                            if(e==null){

                            } else {
                                EventBus.getDefault().post(new EventBusEntity("exception",com.dote.family.db.BmobDB.BmobException.bmobErrorValue(e.getErrorCode())));
                            }
                        }
                    });
                } else {
                    EventBus.getDefault().post(new EventBusEntity("exception",com.dote.family.db.BmobDB.BmobException.bmobErrorValue(e.getErrorCode())));
                }
            }
        });
    }


    /**
     * 修改昵称
     * @param userId
     * @param nickName
     */
    public static void updateNickName(String userId, final String nickName) {
        BmobQuery<DoteInfo> query = new BmobQuery<DoteInfo>();
        query.addWhereEqualTo("userId",userId);
        query.setLimit(Integer.MAX_VALUE);
        query.findObjects(new FindListener<DoteInfo>() {
            @Override
            public void done(List<DoteInfo> list, BmobException e) {
                if(e == null ){
                    List<BmobObject> iconList = new ArrayList<BmobObject>();
                    for(DoteInfo info :list) {
                        DoteInfo mInfo = new DoteInfo();
                        mInfo.setObjectId(info.getObjectId());
                        mInfo.setUserName(nickName);
                        iconList.add(mInfo);
                    }
                    new BmobBatch().updateBatch(iconList).doBatch(new QueryListListener<BatchResult>() {
                        @Override
                        public void done(List<BatchResult> list, BmobException e) {
                            if(e==null){

                            } else {
                                EventBus.getDefault().post(new EventBusEntity("exception",com.dote.family.db.BmobDB.BmobException.bmobErrorValue(e.getErrorCode())));
                            }
                        }
                    });
                } else {
                    EventBus.getDefault().post(new EventBusEntity("exception",com.dote.family.db.BmobDB.BmobException.bmobErrorValue(e.getErrorCode())));
                }
            }
        });
    }


    /**
     * 修改数据
     * @param info
     */
    public static void updateInfo(DoteInfo info){
        info.update(info.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e == null) {
                    EventBus.getDefault().post(new EventBusEntity("updateStatus","修改成功"));
                } else {
                    EventBus.getDefault().post(new EventBusEntity("exception",com.dote.family.db.BmobDB.BmobException.bmobErrorValue(e.getErrorCode())));
                }
            }
        });
    }


    /**
     * 删除数据
     * @param info
     */
    public static void deleteInfo(DoteInfo info){
        info.delete(info.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e == null) {
                    EventBus.getDefault().post(new EventBusEntity("deleteInfo","删除成功"));
                } else {
                    EventBus.getDefault().post(new EventBusEntity("exception",com.dote.family.db.BmobDB.BmobException.bmobErrorValue(e.getErrorCode())));
                }
            }
        });
    }

    /**
     * 以下是查找种类的
     */
    /**
     * 通过大种类查找小种类列表(主要是发布的时候选择用)
     * @param bigKind
     */
    public static void quertKindByBigKind(String bigKind,boolean isNeedAll){
        BmobQuery<DoteKind> query = new BmobQuery<DoteKind>();
        query.addWhereEqualTo("kind",bigKind);
        if(!isNeedAll) {
            query.addWhereNotEqualTo("value","不限");
        }
        query.setLimit(500);
        query.findObjects(new FindListener<DoteKind>() {
            @Override
            public void done(List<DoteKind> list, BmobException e) {
                if(e == null ){
                    //通过eventbus 发送出去
                    EventBus.getDefault().post(new EventBusEntity("quertKindByBigKind",list));
                } else {
                    EventBus.getDefault().post(new EventBusEntity("exception",com.dote.family.db.BmobDB.BmobException.bmobErrorValue(e.getErrorCode())));
                }
            }
        });
    }

    /**
     * 根据条件查询所有的详情
     * @param bigKind 大种类（猫，狗）
     * @param kind（品种）
     * @param sex（性别）
     * @param age（年龄）
     * @param page（分页加载使用）
     */
    public static void queryByLimitInfos(String bigKind,String kind,String sex,String age,int page) {
        BmobQuery<DoteInfo> query = new BmobQuery<DoteInfo>();
        query.addWhereEqualTo("isValid",true);
        if(!TextUtils.isEmpty(bigKind)){
            query.addWhereEqualTo("doteBigKind",bigKind);
        }
        if(!TextUtils.isEmpty(kind) && !kind.equals("不限")){
            query.addWhereEqualTo("doteKind",kind);
        }
        if(!TextUtils.isEmpty(sex) && !sex.equals("不限")){
            query.addWhereEqualTo("doteSex",sex);
        }
        if(!TextUtils.isEmpty(age) && !age.equals("不限")){
            query.addWhereEqualTo("doteAge",age);
        }
        query.order("-createdAt");
        query.setLimit(10);//默认就是10条数据，分页的
        query.setSkip((page-1) * 10);
        query.findObjects(new FindListener<DoteInfo>() {
            @Override
            public void done(List<DoteInfo> list, BmobException e) {
                if(e == null ){
                    //通过eventbus 发送出去
                    EventBus.getDefault().post(new EventBusEntity("queryAllByPage",list));
                } else {
                    EventBus.getDefault().post(new EventBusEntity("exception",com.dote.family.db.BmobDB.BmobException.bmobErrorValue(e.getErrorCode())));
                }
            }
        });
    }

    /**
     * 以下是查找年龄的
     */
    /**
     * 查找所有的年龄段（主要是用户在发布的时候选择使用）
     */
    public static void quertAge(boolean isNeedAll){
        BmobQuery<DoteAge> query = new BmobQuery<DoteAge>();
        query.order("ageId");
        if(!isNeedAll) {
            query.addWhereNotEqualTo("doteage","不限");
        }
        query.setLimit(100);
        query.findObjects(new FindListener<DoteAge>() {
            @Override
            public void done(List<DoteAge> list, BmobException e) {
                if(e == null ){
                    //通过eventbus 发送出去
                    EventBus.getDefault().post(new EventBusEntity("quertAge",list));
                } else {
                    EventBus.getDefault().post(new EventBusEntity("exception",com.dote.family.db.BmobDB.BmobException.bmobErrorValue(e.getErrorCode())));
                }
            }
        });
    }


    /**
     * 查询是否有新版本
     */
    public static void checkNewVersion(final Context context){
        BmobQuery<NewVersion> query = new BmobQuery<NewVersion>();
        query.findObjects(new FindListener<NewVersion>() {
            @Override
            public void done(List<NewVersion> list, BmobException e) {
                if(e == null) {
                    if(list.get(0).getVersionCode() > CommonUtils.getVersion(context)) {
                        EventBus.getDefault().post(new EventBusEntity("checkNewVersionSuccess",list.get(0)));
                    }
                } else {
                    EventBus.getDefault().post(new EventBusEntity("exception",com.dote.family.db.BmobDB.BmobException.bmobErrorValue(e.getErrorCode())));
                }
            }
        });
    }

}
