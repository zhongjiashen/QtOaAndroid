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
import qtkj.com.qtoaandroid.model.PhotoRecord;
import qtkj.com.qtoaandroid.model.SignRecord;

/**
 * Created by Administrator on 2017/8/11 0011.
 */

public class PhotoRecordP extends BasePressent {
    public PhotoRecordP(BaseView view, Activity activity) {
        super(view, activity);
    }

    @Override
    protected void returnData(int requestCode, String response) {
        Gson gson = new Gson();
        Type jsonType = new TypeToken<List<PhotoRecord>>() {
        }.getType();
        List<PhotoRecord> list = gson.fromJson(response, jsonType);
        view.returnData(0, list);
    }

    public void getphotoHistory(int requestCode, Map map) {
        post("photoHistory.json", requestCode, map);
    }
}
