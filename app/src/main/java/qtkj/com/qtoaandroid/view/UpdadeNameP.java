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

public class UpdadeNameP extends BasePressent{
    public UpdadeNameP(BaseView view, Activity activity) {
        super(view, activity);
    }

    @Override
    protected void returnData(int requestCode, String response) {
       view.returnData(0,null);
    }

    public void update_name(int requestCode,Map map){
        post("modifyUserName.json",requestCode,map);
    }
}
