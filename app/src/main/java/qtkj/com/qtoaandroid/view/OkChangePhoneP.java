package qtkj.com.qtoaandroid.view;

import android.app.Activity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

import qtkj.com.qtoaandroid.MyApplication;
import qtkj.com.qtoaandroid.model.Login;

/**
 * Created by Administrator on 2017/8/11 0011.
 */

public class OkChangePhoneP extends BasePressent{
    public OkChangePhoneP(BaseView view, Activity activity) {
        super(view, activity);
    }

    @Override
    protected void returnData(int requestCode, String response) {
       view.returnData(requestCode,null);
    }

    public void modifyPhone(int requestCode,Map map){
        post("modifyPhone.json",requestCode,map);
    }
    public void getCode(int requestCode,Map map){
        post("getAuthCode.json",requestCode,map);
    }
}
