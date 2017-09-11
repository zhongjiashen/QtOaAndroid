package qtkj.com.qtoaandroid.view;

import android.app.Activity;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import qtkj.com.qtoaandroid.BuildConfig;
import qtkj.com.qtoaandroid.MyApplication;
import qtkj.com.qtoaandroid.dialog.SampleDialog;
import qtkj.com.qtoaandroid.model.Login;
import qtkj.com.qtoaandroid.model.Update;
import qtkj.com.qtoaandroid.utils.LogUtils;
import qtkj.com.qtoaandroid.utils.SPUtils;
import qtkj.com.qtoaandroid.utils.UpdateApp;

/**
 * Created by Administrator on 2017/8/11 0011.
 */

public class LoginPassP extends BasePressent {
    Map map;
    SampleDialog sampleDialog;
    public LoginPassP(BaseView view, Activity activity) {
        super(view, activity);
    }
    @Override
    protected void returnData(int requestCode, String response) {
        Gson gson = new Gson();
        switch (requestCode) {
            case 0:
                Type jsonType = new TypeToken<Login>() {
                }.getType();
                Login login = gson.fromJson(response, jsonType);
                MyApplication.mApplication.setLogin(login);
                MyApplication.entityName = login.getUserId() + "";
                SPUtils.put(mActivity,"phone",map.get("mobile"));
                SPUtils.put(mActivity,"password",map.get("password"));
                view.returnData(requestCode, null);
                break;
            case 1:
                LogUtils.d(response);
                Type jsType = new TypeToken<Update>() {
                }.getType();
                Update update = gson.fromJson(response, jsType);
                if(update.getVersion()!= BuildConfig.VERSION_CODE) {
                    //强制升级
                showDialog("升级提示", "检测到您的版本过低，请升级！", 1, "否", "是", new SampleDialog.OnClickListener() {
                    @Override
                    public void onPositive() {
                        dismissDialog();
                        new UpdateApp(mActivity).downFile();
                    }
                    @Override
                    public void onNegative() {
                        dismissDialog();
                        mActivity.finish();
                    }
                });
                }else {
                    view.returnData(requestCode, null);
                }
                break;
        }

    }

    public void login(int requestCode, Map map) {
        this.map=map;
        post("login.json", requestCode, map);
    }
    public void update(int requestCode) {
        Map map=new HashMap();
        post("androidVersion.json", requestCode, map);
    }
    public void showDialog(String title, String msg, int type, String leftBtn, String rightBtn, SampleDialog.OnClickListener onClickListener) {

        sampleDialog = new SampleDialog(mActivity, title, msg, type);
        sampleDialog.setCanceledOnTouchOutside(false);
        sampleDialog.setCanceledOnTouchOutside(false);
        sampleDialog.setCancelable(false);
        sampleDialog.setOnClickListener(onClickListener);
        sampleDialog.show();
        sampleDialog.setButtonText(leftBtn, rightBtn);
    }

    public void dismissDialog() {
        if (sampleDialog != null) {
            sampleDialog.dismiss();
            sampleDialog = null;
        }
    }
}
