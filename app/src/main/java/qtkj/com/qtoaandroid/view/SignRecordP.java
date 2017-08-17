package qtkj.com.qtoaandroid.view;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import qtkj.com.qtoaandroid.MyApplication;
import qtkj.com.qtoaandroid.model.Login;
import qtkj.com.qtoaandroid.model.SignRecord;
import qtkj.com.qtoaandroid.model.SignRecordDeal;
import qtkj.com.qtoaandroid.viewbar.calenderview.DayManager;

/**
 * Created by Administrator on 2017/8/11 0011.
 */

public class SignRecordP extends BasePressent{

    public SignRecordP(BaseView view, Activity activity) {
        super(view, activity);
    }
    private String moth;

    @Override
    protected void returnData(int requestCode, String response) {
        Set<Integer> normalDays = new HashSet<>();
        Set<Integer> latearrivalDays = new HashSet<>();
        Set<Integer> forgetclockDays = new HashSet<>();
        Set<Integer> absenteeismDays = new HashSet<>();
        Log.e("SignRecordP",response);
        Gson gson = new Gson();
        Type jsonType =  new TypeToken<List<SignRecord>>() {
        }.getType();
        List<SignRecord> list= gson.fromJson(response,  jsonType);
        Map<String,SignRecordDeal> map=new HashMap();
        for( int i = 0 ; i < list.size() ; i++) {//内部不锁定，效率最高，但在多线程要考虑并发操作的问题。
            SignRecord signRecord=list.get(i);
            SignRecordDeal signRecordDeal=new SignRecordDeal();
            String key=signRecord.getDay()+"";
            switch (signRecord.getState()){
                case 0:
                    switch (signRecord.getOutState()){
                        case 0:
                            normalDays.add(signRecord.getDay());
                            break;
                        case 2:
                            latearrivalDays.add(signRecord.getDay());
                            break;
                        case 3:
                            forgetclockDays.add(signRecord.getDay());
                            break;
                        case 4:
                            absenteeismDays.add(signRecord.getDay());
                            break;

                    }
                    break;
                case 1:
                    signRecordDeal.setDay_state("迟到、");
                    latearrivalDays.add(signRecord.getDay());
                    break;
                case 3:
                    signRecordDeal.setDay_state("忘记打卡、");
                    forgetclockDays.add(signRecord.getDay());
                    break;
                case 4:
                    absenteeismDays.add(signRecord.getDay());
                    break;
            }
            switch (signRecord.getOutState()) {
                case 0:

                    break;
                case 2:
                    signRecordDeal.setDay_state("早退");
                    break;
                case 3:
                    signRecordDeal.setDay_state("忘记打卡");
                    break;
                case 4:
                    signRecordDeal.setDay_state("缺勤");
                    break;
            }
            signRecordDeal.setDate(moth+"-"+key);
            signRecordDeal.setSign_in_address(signRecord.getPosition());
            signRecordDeal.setSign_out_address(signRecord.getOutPosition());
            signRecordDeal.setSign_in_time(signRecord.getSignTime());
            signRecordDeal.setSign_out_time(signRecord.getOutSignTime());
            signRecordDeal.setAmEndTime(signRecord.getAmEndTime());
            signRecordDeal.setPmStartTime(signRecord.getPmStartTime());
            signRecordDeal.setJop_type(signRecord.getJobType());
            map.put(key,signRecordDeal);
        }
        DayManager.setNormalDays(normalDays);
        DayManager.setLatearrivalDays(latearrivalDays);
        DayManager.setForgetclockDays(forgetclockDays);
        DayManager.setAbsenteeismDays(absenteeismDays);
       view.returnData(0,map);
    }

    public void getDate(int requestCode,Map<String,String> map){
        moth=map.get("time");
        post("signHistory.json",requestCode,map);
    }
}
