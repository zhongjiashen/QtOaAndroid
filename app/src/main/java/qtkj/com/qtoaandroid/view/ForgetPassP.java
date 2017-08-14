package qtkj.com.qtoaandroid.view;

import android.app.Activity;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import qtkj.com.qtoaandroid.model.SignRecord;

/**
 * Created by Administrator on 2017/8/11 0011.
 */

public class ForgetPassP extends BasePressent{
    public ForgetPassP(BaseView view, Activity activity) {
        super(view, activity);
    }

    @Override
    protected void returnData(int requestCode, String response) {
       view.returnData(requestCode,null);
    }

    public void forgetPwd(int requestCode,Map map){
        post("forgetPwd.json",requestCode,map);
    }
    public void getCode(int requestCode,Map map){

        post("getAuthCode.json",requestCode,map);
    }
}
