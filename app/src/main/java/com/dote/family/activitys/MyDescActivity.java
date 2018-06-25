package com.dote.family.activitys;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.dote.family.ProjectApplication;
import com.dote.family.R;
import com.dote.family.base.BaseActivity;
import com.dote.family.db.BmobDB.BmobDBUtils;
import com.dote.family.entity.EventBusEntity;
import com.dote.family.utils.CommonUtils;
import com.dote.family.utils.DateUtils;
import com.dote.family.view.TitleLayout;
import com.mob.ums.OperationCallback;
import com.mob.ums.UMSSDK;
import com.mob.ums.User;
import com.mob.ums.datatype.Gender;

import net.lemonsoft.lemonhello.LemonHelloAction;
import net.lemonsoft.lemonhello.LemonHelloInfo;
import net.lemonsoft.lemonhello.LemonHelloView;
import net.lemonsoft.lemonhello.interfaces.LemonHelloActionDelegate;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by Administrator on 2017/8/24.
 */

public class MyDescActivity extends BaseActivity {

    private TitleLayout myinfo_title;
    private EditText et_nikename,et_sex,et_birthday,et_age/*,et_city*/;
    private Button btn_change;
    private static final String TAG = "MyDescActivity";
    private User saveUser = new User();
    private String dateStr,mouth1,day1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_myinfo;
    }

    @Override
    protected void initView() {
        myinfo_title = baseFindViewById(R.id.myinfo_title);
        et_nikename = baseFindViewById(R.id.et_nikename);
        et_sex = baseFindViewById(R.id.et_sex);
        et_birthday = baseFindViewById(R.id.et_birthday);
        et_age = baseFindViewById(R.id.et_age);
//        et_city = baseFindViewById(R.id.et_city);
        btn_change = baseFindViewById(R.id.btn_change);
        et_sex.setCursorVisible(false);
        et_sex.setInputType(InputType.TYPE_NULL);
        et_birthday.setCursorVisible(false);
        et_birthday.setInputType(InputType.TYPE_NULL);
    }

    @Override
    protected void setListener() {
        et_sex.setOnClickListener(this);
        et_birthday.setOnClickListener(this);
//        et_city.setOnClickListener(this);
        btn_change.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        myinfo_title.setMoreGone();
        myinfo_title.setSearchGone();
        myinfo_title.setTitle(getResources().getString(R.string.mydesc_title));
        myinfo_title.setBackClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if(!TextUtils.isEmpty(ProjectApplication.mUser.nickname.get())) {
            et_nikename.setText(ProjectApplication.mUser.nickname.get());
        }
        if(null != ProjectApplication.mUser.gender.get()) {
            et_sex.setText(formatGender(ProjectApplication.mUser.gender.get()));
        }
        if(null != ProjectApplication.mUser.birthday.get()) {
            et_birthday.setText(DateUtils.date2Str(ProjectApplication.mUser.birthday.get(),"yyyy-MM-dd"));
        }
//        if(null != ProjectApplication.mUser.city.get()) {
//           android.util.Log.e("aaa","-->"+ProjectApplication.mUser.city.get().code());
//            et_city.setText(ProjectApplication.mUser.city.get().resName());
//        }
        if(null != ProjectApplication.mUser.age.get()) {
            et_age.setText(ProjectApplication.mUser.age.get().toString());
        }
    }

    /**
     * 格式化性别
     * @param gender
     * @return
     */
    private String formatGender(Gender gender){
        if(gender.code() == 1) {
            return getResources().getString(R.string.mydesc_sex_value_male);
        } else if(gender.code() == 2) {
            return getResources().getString(R.string.mydesc_sex_value_female);
        } else if(gender.code() == 3) {
            return getResources().getString(R.string.mydesc_sex_value_secret);
        }
        return null;
    }

    @Override
    protected String[] usePermission() {
        return new String[0];
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.et_sex:
                LemonHelloInfo doteSex = new LemonHelloInfo()
                        .setTitle(getResources().getString(R.string.mydesc_sex))
                        .setContent("")
                        .addAction(new LemonHelloAction(getResources().getString(R.string.mydesc_sex_value_male), new LemonHelloActionDelegate() {
                            @Override
                            public void onClick(LemonHelloView helloView, LemonHelloInfo helloInfo, LemonHelloAction helloAction) {
                                saveUser.gender.set(new Gender() {
                                    @Override
                                    public int code() {
                                        return 1;
                                    }

                                    @Override
                                    public String resName() {
                                        return "umssdk_gender_male";
                                    }
                                });
                                EventBus.getDefault().post(new EventBusEntity(TAG,getResources().getString(R.string.mydesc_sex_value_male)));
                                helloView.hide();
                            }
                        }))
                        .addAction(new LemonHelloAction(getResources().getString(R.string.mydesc_sex_value_female), new LemonHelloActionDelegate() {
                            @Override
                            public void onClick(LemonHelloView helloView, LemonHelloInfo helloInfo, LemonHelloAction helloAction) {
                                saveUser.gender.set(new Gender() {
                                    @Override
                                    public int code() {
                                        return 2;
                                    }

                                    @Override
                                    public String resName() {
                                        return "umssdk_gender_female";
                                    }
                                });
                                EventBus.getDefault().post(new EventBusEntity(TAG,getResources().getString(R.string.mydesc_sex_value_female)));
                                helloView.hide();
                            }
                        }))
                        .addAction(new LemonHelloAction(getResources().getString(R.string.mydesc_sex_value_secret), new LemonHelloActionDelegate() {
                            @Override
                            public void onClick(LemonHelloView helloView, LemonHelloInfo helloInfo, LemonHelloAction helloAction) {
                                saveUser.gender.set(new Gender() {
                                    @Override
                                    public int code() {
                                        return 3;
                                    }

                                    @Override
                                    public String resName() {
                                        return "umssdk_gender_secret";
                                    }
                                });
                                EventBus.getDefault().post(new EventBusEntity(TAG,getResources().getString(R.string.mydesc_sex_value_secret)));
                                helloView.hide();
                            }
                        }));
                doteSex.show(this);
                break;
            case R.id.et_birthday:
                final Calendar calender = Calendar.getInstance();
                if(null != ProjectApplication.mUser.birthday.get()) {
                    calender.set(Integer.parseInt(et_birthday.getText().toString().split("-")[0]),
                            Integer.parseInt(et_birthday.getText().toString().split("-")[1])-1,
                                    Integer.parseInt(et_birthday.getText().toString().split("-")[2]));
                }
                DatePickerDialog dialog = new
                        DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int
                            dayOfMonth) {
                        if(monthOfYear<=9){
                            mouth1="0"+(monthOfYear+1);
                        }else{
                            mouth1=String.valueOf(monthOfYear+1);
                        }
                        if(dayOfMonth<=9){
                            day1= "0"+dayOfMonth;
                        }else{
                            day1=String.valueOf(dayOfMonth);
                        }
                        dateStr = String.valueOf(year)+"-"+mouth1+"-"+day1;
                        et_birthday.setText(dateStr);
                        saveUser.birthday.set(DateUtils.StringToDate(dateStr,"yyyy-MM-dd"));
                    }
                }, calender.get(Calendar.YEAR), calender.get(Calendar.MONTH),
                        calender.get(Calendar.DAY_OF_MONTH));
                dialog.show();
                break;
//            case R.id.et_city:
//                break;
            case R.id.btn_change:
                saveUser.nickname.set(et_nikename.getText().toString());
                final HashMap<String,Object> updateMaps = new HashMap<String,Object>();
                updateMaps.put("nickname",saveUser.nickname.get());
                if(null != saveUser.gender && null != saveUser.gender.get()) {
                    updateMaps.put("gender", saveUser.gender.get());
                }
                saveUser.age.set(Integer.parseInt(et_age.getText().toString()));
                updateMaps.put("age",saveUser.age.get());
                if(null != saveUser.birthday && null != saveUser.birthday.get()) {
                    updateMaps.put("birthday",saveUser.birthday.get());
                }
                UMSSDK.updateUserInfo(updateMaps,new OperationCallback<Void>(){
                    @Override
                    public void onSuccess(Void aVoid) {
                        super.onSuccess(aVoid);
                        toast(getResources().getString(R.string.mydesc_updatesuccess));
//                        ProjectApplication.mUser.parseFromMap(updateMaps);
                        ProjectApplication.mUser.nickname.set(saveUser.nickname.get());
                        ProjectApplication.mUser.gender.set(saveUser.gender.get());
                        ProjectApplication.mUser.birthday.set(saveUser.birthday.get());
                        ProjectApplication.mUser.age.set(saveUser.age.get());
                        if(ProjectApplication.mUser.nickname.get().equals(getResources().getString(R.string.default_user))){
                            BmobDBUtils.updateNickName(ProjectApplication.mUser.id.get(),CommonUtils.getIMName(ProjectApplication.mUser.phone.get()));
                        } else {
                            BmobDBUtils.updateNickName(ProjectApplication.mUser.id.get(),saveUser.nickname.get());
                        }
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
                break;

        }
    }

    @Override
    public void getEventBusValue(EventBusEntity entity) {
        super.getEventBusValue(entity);
        if(entity.getActivityName().equals(TAG)){
            et_sex.setText(entity.getValue().toString());
        }
    }
}
