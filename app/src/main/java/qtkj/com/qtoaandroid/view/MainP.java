package qtkj.com.qtoaandroid.view;

import android.app.Activity;

import java.util.Map;

/**
 * Created by Administrator on 2017/8/14 0014.
 */

public class MainP extends BasePressent {
    public MainP(BaseView view, Activity activity) {
        super(view, activity);
    }

    @Override
    protected void returnData(int requestCode, String response) {
        view.returnData(requestCode, null);
    }

    public void signIn(int requestCode, Map map) {
        post("signIn.json", requestCode, map);
    }

    public void imgByUserId(int requestCode, Map map) {
        post("imgByUserId.json", requestCode, map);
    }

}
