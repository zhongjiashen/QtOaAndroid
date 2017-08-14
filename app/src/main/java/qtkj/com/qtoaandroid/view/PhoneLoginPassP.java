package qtkj.com.qtoaandroid.view;

import android.app.Activity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import qtkj.com.qtoaandroid.MyApplication;
import qtkj.com.qtoaandroid.activity.MainActivity;
import qtkj.com.qtoaandroid.model.Login;

/**
 * Created by Administrator on 2017/8/11 0011.
 */

public class PhoneLoginPassP extends BasePressent{
    public PhoneLoginPassP(BaseView view, Activity activity) {
        super(view, activity);
    }

    @Override
    protected void returnData(int requestCode, String response) {
        if (requestCode==0) {
            Gson gson = new Gson();
            Type jsonType = new TypeToken<Login>() {
            }.getType();
            Login login = gson.fromJson(response, jsonType);
            MyApplication.login = login;

        }
       view.returnData(requestCode,null);
    }

    public void login(int requestCode,Map map){
        post("loginByCode.json",requestCode,map);
    }
    public void getCode(int requestCode,Map map){

        post("getAuthCode.json",requestCode,map);
    }
}
