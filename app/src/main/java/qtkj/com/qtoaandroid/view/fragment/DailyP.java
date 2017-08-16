package qtkj.com.qtoaandroid.view.fragment;

import android.app.Activity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import qtkj.com.qtoaandroid.model.Daily;
import qtkj.com.qtoaandroid.model.NowLocationF;
import qtkj.com.qtoaandroid.view.BasePressent;
import qtkj.com.qtoaandroid.view.BaseView;

/**
 * Created by Administrator on 2017/8/16 0016.
 */

public class DailyP extends BasePressent{
    public DailyP(BaseView view, Activity activity) {
        super(view, activity);
    }

    @Override
    protected void returnData(int requestCode, String response) {
        Gson gson = new Gson();
        Type jsonType =  new TypeToken<Daily>() {
        }.getType();
       Daily daily= gson.fromJson(response,  jsonType);
        view.returnData(0,daily);
    }
    public void getsignDay(int requestCode,Map map){
        post("signDay.json",requestCode,map);
    }
}
