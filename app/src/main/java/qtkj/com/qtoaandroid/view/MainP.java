package qtkj.com.qtoaandroid.view;

import android.app.Activity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

import qtkj.com.qtoaandroid.MyApplication;
import qtkj.com.qtoaandroid.model.Login;
import qtkj.com.qtoaandroid.utils.SPUtils;

/**
 * Created by Administrator on 2017/8/14 0014.
 */

public class MainP extends BasePressent {
    public MainP(BaseView view, Activity activity) {
        super(view, activity);
    }

    @Override
    protected void returnData(int requestCode, String response) {
        Gson gson = new Gson();
        switch (requestCode) {
            case 2:
                Type jsonType = new TypeToken<Login>() {
                }.getType();
                Login login = gson.fromJson(response, jsonType);
                MyApplication.mApplication.setLogin(login);
                MyApplication.entityName = login.getUserId() + "";

                break;
        }
        view.returnData(requestCode, null);
    }
    public void login(int requestCode, Map map) {
        post("login.json", requestCode, map);
    }
    public void signIn(int requestCode, Map map) {
        post("signIn.json", requestCode, map);
    }

    public void imgByUserId(int requestCode, Map map) {
        post("imgByUserId.json", requestCode, map);
    }

}
