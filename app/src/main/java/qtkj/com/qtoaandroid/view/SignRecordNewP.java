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

import qtkj.com.qtoaandroid.model.SignRecord;
import qtkj.com.qtoaandroid.model.SignRecordDeal;
import qtkj.com.qtoaandroid.model.SignRecordDealNew;
import qtkj.com.qtoaandroid.model.SignRecordNew;
import qtkj.com.qtoaandroid.viewbar.calenderview.DayManager;

/**
 * Created by Administrator on 2017/8/11 0011.
 */

public class SignRecordNewP extends BasePressent {

    public SignRecordNewP(BaseView view, Activity activity) {
        super(view, activity);
    }

    private String moth;
    private Map<String, SignRecordDealNew> map;
    Set<Integer> normalDays;
    Set<Integer> latearrivalDays;
    Set<Integer> forgetclockDays;
    Set<Integer> absenteeismDays;

    @Override
    protected void returnData(int requestCode, String response) {
        normalDays = new HashSet<>();
        latearrivalDays = new HashSet<>();
        forgetclockDays = new HashSet<>();
        absenteeismDays = new HashSet<>();
        Log.e("SignRecordNewP", response);
        Gson gson = new Gson();
        Type jsonType = new TypeToken<List<SignRecordNew>>() {
        }.getType();
        List<SignRecordNew> list = gson.fromJson(response, jsonType);
        map = new HashMap();
        for (int i = 0; i < list.size(); i++) {//内部不锁定，效率最高，但在多线程要考虑并发操作的问题。
            SignRecordNew signRecord = list.get(i);
            SignRecordDealNew signRecordDeal = null;
            //只有白班记录
            if (signRecord.getType().equals("day")) {

                signRecordDeal = day(signRecord);
                if (signRecord.getInfo().getDayState() == 0 && signRecord.getInfo().getDayOutState() == 0) {
                    normalDays.add(Integer.parseInt(signRecord.getDay()));
                }
                signRecordDeal.setType(1);
            }
            //只有夜班记录
            if (signRecord.getType().equals("night")) {
                signRecordDeal = new SignRecordDealNew();
                signRecordDeal.setType(2);
                signRecordDeal = night(signRecord, signRecordDeal, true);
                if (signRecord.getInfo().getNightState() == 0 && signRecord.getInfo().getNightOutState() == 0) {
                    normalDays.add(Integer.parseInt(signRecord.getDay()));
                }
            }
            //白班和夜班都有
            if (signRecord.getType().equals("dayAndNight")) {
                signRecordDeal = day(signRecord);
                if (signRecord.getInfo().getDayState() == 0 && signRecord.getInfo().getDayOutState() == 0) {
                    signRecordDeal = night(signRecord, signRecordDeal, true);
                    if (signRecord.getInfo().getNightState() == 0 && signRecord.getInfo().getNightOutState() == 0) {
                        normalDays.add(Integer.parseInt(signRecord.getDay()));
                    }
                } else {
                    signRecordDeal = night(signRecord, signRecordDeal, false);
                }
                signRecordDeal.setType(3);
            }
            map.put(signRecord.getDay(), signRecordDeal);
        }
        DayManager.setNormalDays(normalDays);
        DayManager.setLatearrivalDays(latearrivalDays);
        DayManager.setForgetclockDays(forgetclockDays);
        DayManager.setAbsenteeismDays(absenteeismDays);

        view.returnData(0, map);
    }

    public void getDate(int requestCode, Map<String, String> map) {
        moth = map.get("time");
        post("signHistory.json", requestCode, map);
    }

    /**
     * 处理白班日历状态
     *
     * @param signRecord 签到信息
     */
    private SignRecordDealNew day(SignRecordNew signRecord) {
        int state_in = signRecord.getInfo().getDayState();
        int state_out = signRecord.getInfo().getDayOutState();
        int day = Integer.parseInt(signRecord.getDay());
        SignRecordDealNew signRecordDeal = new SignRecordDealNew();
        switch (state_in) {
            case 0:
                signRecordDeal.setDay_sign_in_state("");
                break;
            case 1:
                latearrivalDays.add(day);
                signRecordDeal.setDay_sign_in_state("迟到");
                break;
            case 3:
                forgetclockDays.add(day);
                signRecordDeal.setDay_sign_in_state("忘记打卡");
                break;
            case 4:
                absenteeismDays.add(day);

                break;
            default:
                break;
        }
        if (state_in == 0) {
            switch (state_out) {
                case 2:
                    latearrivalDays.add(day);
                    break;
                case 3:
                    forgetclockDays.add(day);
                    break;
                default:
                    break;

            }
        }
        if (state_in != 4) {
            switch (state_out) {
                case 0:
                    signRecordDeal.setDay_sign_out_state("");
                    break;
                case 2:
                    signRecordDeal.setDay_sign_out_state("早退");
                    break;
                case 3:
                    signRecordDeal.setDay_sign_out_state("忘记打卡");
                    break;
                default:
                    break;

            }
            signRecordDeal.setAmEndTime(moth + "-" + day + " " + signRecord.getInfo().getAmEndTime());
            signRecordDeal.setPmStartTime(moth + "-" + day + " " + signRecord.getInfo().getPmStartTime());
            signRecordDeal.setPmEndTime(moth + "-" + day + " " + signRecord.getInfo().getPmEndTime());
            signRecordDeal.setDay_sign_in_time(signRecord.getInfo().getDaySignTime());
            signRecordDeal.setDay_sign_in_address(signRecord.getInfo().getDayPosition());
            signRecordDeal.setDay_sign_out_time(signRecord.getInfo().getDayOutSignTime());
            signRecordDeal.setDay_sign_out_address(signRecord.getInfo().getDayOutPosition());
        }else {
            signRecordDeal.setDay_sign_in_state("缺勤");
        }
        return signRecordDeal;

    }

    /**
     * 处理夜班日历状态
     *
     * @param signRecord 签到信息
     */
    private SignRecordDealNew night(SignRecordNew signRecord, SignRecordDealNew signRecordDeal, boolean b) {
        int state_in = signRecord.getInfo().getNightState();
        int state_out = signRecord.getInfo().getNightOutState();
        int day = Integer.parseInt(signRecord.getDay());
        switch (state_in) {
            case 0:
                signRecordDeal.setNight_sign_in_state("");
                break;
            case 1:
                if (b) {
                    latearrivalDays.add(day);
                }
                signRecordDeal.setNight_sign_in_state("迟到");
                break;
            case 3:
                if (b) {
                    forgetclockDays.add(day);
                }
                signRecordDeal.setNight_sign_in_state("忘记打卡");
                break;
            case 4:
                if (b) {
                    absenteeismDays.add(day);
                }
                break;
            default:
                break;
        }
        if (state_in == 0 && b) {
            switch (state_out) {
                case 2:
                    latearrivalDays.add(day);
                    break;
                case 3:
                    forgetclockDays.add(day);
                    break;
                default:
                    break;

            }
        }
        if (state_in != 4) {
            switch (state_out) {
                case 0:
                    signRecordDeal.setNight_sign_out_state("");
                    break;
                case 2:
                    signRecordDeal.setNight_sign_out_state("早退");
                    break;
                case 3:
                    signRecordDeal.setNight_sign_out_state("忘记打卡");
                    break;
                default:
                    break;

            }
            signRecordDeal.setNmEndTime(moth + "-" + day + " " + signRecord.getInfo().getNightEndTime());
            signRecordDeal.setNight_sign_in_time(signRecord.getInfo().getNightSignTime());
            signRecordDeal.setNight_sign_in_address(signRecord.getInfo().getNightPosition());
            signRecordDeal.setNight_sign_out_time(signRecord.getInfo().getNightOutSignTime());
            signRecordDeal.setNight_sign_out_address(signRecord.getInfo().getNightOutPosition());
        }else {
            signRecordDeal.setNight_sign_in_state("缺勤");
        }
        return signRecordDeal;

    }
}
