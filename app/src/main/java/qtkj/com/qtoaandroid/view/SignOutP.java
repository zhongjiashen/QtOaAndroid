package qtkj.com.qtoaandroid.view;

import android.app.Activity;

import java.util.Map;

/**
 * Created by Administrator on 2017/8/15 0015.
 */

public class SignOutP extends BasePressent{
    public SignOutP(BaseView view, Activity activity) {
        super(view, activity);
    }

    @Override
    protected void returnData(int requestCode, String response) {
        view.returnData(requestCode, null);
    }

    public void signIn(int requestCode, Map map) {
        post("signIn.json", requestCode, map);
    }
}
