package qtkj.com.qtoaandroid.view.fragment;

import android.app.Activity;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import qtkj.com.qtoaandroid.model.AddressBook;
import qtkj.com.qtoaandroid.model.NowLocationF;
import qtkj.com.qtoaandroid.view.BasePressent;
import qtkj.com.qtoaandroid.view.BaseView;

/**
 * Created by Administrator on 2017/8/14 0014.
 */

public class NowLocationP extends BasePressent{
    public NowLocationP(BaseView view, Activity activity) {
        super(view, activity);
    }

    @Override
    protected void returnData(int requestCode, String response) {
        Log.e("s",response);
        Gson gson = new Gson();
        Type jsonType =  new TypeToken<List<NowLocationF>>() {
        }.getType();
        List<NowLocationF> list= gson.fromJson(response,  jsonType);
        view.returnData(0,list);
    }
    public void getNowLocation(int requestCode,Map map){
        post("userSign.json",requestCode,map);
    }
}
