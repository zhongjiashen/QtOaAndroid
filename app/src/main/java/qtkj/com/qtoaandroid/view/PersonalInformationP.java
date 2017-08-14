package qtkj.com.qtoaandroid.view;

import android.app.Activity;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import qtkj.com.qtoaandroid.model.Login;
import qtkj.com.qtoaandroid.model.SignRecord;

/**
 * Created by Administrator on 2017/8/11 0011.
 */

public class PersonalInformationP extends BasePressent{
    public PersonalInformationP(BaseView view, Activity activity) {
        super(view, activity);
    }

    @Override
    protected void returnData(int requestCode, String response) {
        Log.e("PersonalInformationP", response);
        Gson gson = new Gson();
        Type jsonType =  new TypeToken<String>() {
        }.getType();
       view.returnData(requestCode,gson.fromJson(response,  jsonType));
    }


    public void getImgUpload64(int requestCode,Map map){
        post("getImgUpload64.json",requestCode,map);
    }
}
