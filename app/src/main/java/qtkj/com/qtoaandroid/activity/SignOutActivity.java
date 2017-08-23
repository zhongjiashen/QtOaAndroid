package qtkj.com.qtoaandroid.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import qtkj.com.qtoaandroid.MyApplication;
import qtkj.com.qtoaandroid.R;
import qtkj.com.qtoaandroid.utils.BitmapUtil;
import qtkj.com.qtoaandroid.utils.MapUtil;
import qtkj.com.qtoaandroid.utils.MyBDLocation;
import qtkj.com.qtoaandroid.view.SignOutP;

/**
 * Created by Administrator on 2017/8/8 0008.
 */

public class SignOutActivity extends BaseActivity<SignOutP> {
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    private MyBDLocation myBDLocation;
    private MyBroadcastReciver myBroadcastReciver;
    /**
     * 地图工具
     */
    private MapUtil mapUtil = null;
    Map<String,String> map;
    @Override
    protected int layout() {
        return R.layout.activity_sign_out;
    }
    @Override
    public void returnData(int requestCode, Object data) {
        super.returnData(requestCode, data);
        switch (requestCode){
            case 0:
                showShortToast("签退成功！");
                MyApplication.login.setIsSign(0);
                setResult(RESULT_OK, null);
                finish();
                break;
        }
    }
    @Override
    protected void Initialize() {
        mapUtil = MapUtil.getInstance();
        mapUtil.init((MapView) findViewById(R.id.mapView));
        BitmapUtil.init();
        myBDLocation = new MyBDLocation(getApplicationContext());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.qtoaandroid.myLocation");
        this.registerReceiver(myBroadcastReciver = new MyBroadcastReciver(), intentFilter);
        myBDLocation.start();
        presenter=new SignOutP(this,this);

    }
    @OnClick({R.id.iv_back, R.id.bt_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.bt_ok:
                presenter.signIn(0,map);
                break;
        }
    }

    private class MyBroadcastReciver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("com.qtoaandroid.myLocation")) {
                BDLocation bdLocation = intent.getParcelableExtra("location");
                LatLng latLng = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
                mapUtil.updateStatus(latLng, true);
                tvAddress.setText(bdLocation.getAddrStr());
                map=new HashMap<>();
                map.put("type","1");
                map.put("userId", MyApplication.login.getUserId()+"");
                map.put("position",bdLocation.getAddrStr());
                map.put("workStartTime",MyApplication.login.getWorkStartTime());
                map.put("workEndTime",MyApplication.login.getWorkEndTime());


            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapUtil.onPause();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapUtil.clear();
        unregisterReceiver(myBroadcastReciver);
        BitmapUtil.clear();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mapUtil.onResume();

    }
}
