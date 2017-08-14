package qtkj.com.qtoaandroid.view;

import android.app.Activity;

import java.util.Map;

/**
 * Created by Administrator on 2017/8/11 0011.
 */

public class OkChangePassP extends BasePressent{
    public OkChangePassP(BaseView view, Activity activity) {
        super(view, activity);
    }

    @Override
    protected void returnData(int requestCode, String response) {
       view.returnData(requestCode,null);
    }

    public void modifPwd(int requestCode,Map map){
        post("modifPwd.json",requestCode,map);
    }

}
