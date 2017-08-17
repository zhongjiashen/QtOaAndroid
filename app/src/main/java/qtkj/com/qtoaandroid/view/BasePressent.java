package qtkj.com.qtoaandroid.view;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

import qtkj.com.qtoaandroid.Contest;
import qtkj.com.qtoaandroid.dialog.LoadingDialog;
import qtkj.com.qtoaandroid.httputils.NetWorkRequest;
import qtkj.com.qtoaandroid.httputils.api.ApiService;
import qtkj.com.qtoaandroid.httputils.cach.RxRetrofitCache;
import qtkj.com.qtoaandroid.httputils.rxrequest.RxHelper;
import qtkj.com.qtoaandroid.httputils.rxrequest.RxSubscribe;
import qtkj.com.qtoaandroid.model.BaseModel;
import qtkj.com.qtoaandroid.model.Login;
import rx.Observable;

/**
 * Created by Administrator on 2017/8/11 0011.
 */

public abstract class BasePressent {
    protected BaseView view;
    protected Activity mActivity;
    private ApiService apiService;
    private RxSubscribe mRxSubscribe;
    public final static String REQUEST_TITLE = "正在加载";
    protected LoadingDialog mDialog;
    public BasePressent(BaseView view, Activity activity) {
        this.view = view;
        mActivity = activity;
        mDialog=new LoadingDialog(activity);
    }

    protected void post(String url,final int requestCode, Map map) {
        mDialog.show();
        mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface anInterface) {
                mDesory();
            }
        });
        apiService = NetWorkRequest.getInstance().apiService;
        if (apiService == null) {
            NetWorkRequest.getInstance().init(mActivity.getApplicationContext(), Contest.baseurl+"api/user/");
            apiService = NetWorkRequest.getInstance().apiService;
        }
        Observable<Object> fromNetwrok = apiService.executePost(url, map)
                .compose(RxHelper.<Object>handleResult());
        RxRetrofitCache.load(fromNetwrok)
                .subscribe(mRxSubscribe=new RxSubscribe<Object>(mActivity,
                        REQUEST_TITLE) {
                    @Override
                    protected void _onNext(Object model) {
                        returnData(requestCode,new Gson().toJson(model));
                        mDialog.dismiss();

                    }

                    @Override
                    protected void _onError(String message) {
                        view.showShortToast(message);
                        mDialog.dismiss();
                    }

                });
    }

    protected abstract void returnData(int requestCode, String response);

    public void mDesory(){
       if(mRxSubscribe!=null)
        mRxSubscribe.mUnsubscribe();
    }
}
