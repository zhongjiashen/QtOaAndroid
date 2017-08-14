package qtkj.com.qtoaandroid.view;

import android.app.Activity;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import qtkj.com.qtoaandroid.MyApplication;
import qtkj.com.qtoaandroid.model.Login;
import qtkj.com.qtoaandroid.model.SignRecord;

/**
 * Created by Administrator on 2017/8/11 0011.
 */

public class SignRecordP extends BasePressent{
    public SignRecordP(BaseView view, Activity activity) {
        super(view, activity);
    }

    @Override
    protected void returnData(int requestCode, String response) {
        Log.e("SignRecordP",response);
        Gson gson = new Gson();
        Type jsonType =  new TypeToken<List<SignRecord>>() {
        }.getType();
        List<SignRecord> list= gson.fromJson(response,  jsonType);
//
       view.returnData(0,null);
    }

    public void getDate(int requestCode,Map map){
        map=new HashMap();
        map.put("userId","1");
        map.put("time","2017-08");
        post("signHistory.json",requestCode,map);
    }
}
