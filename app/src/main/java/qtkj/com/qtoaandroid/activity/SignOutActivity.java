package qtkj.com.qtoaandroid.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;

import butterknife.BindView;
import butterknife.OnClick;
import qtkj.com.qtoaandroid.R;
import qtkj.com.qtoaandroid.utils.BitmapUtil;
import qtkj.com.qtoaandroid.utils.CommonUtil;
import qtkj.com.qtoaandroid.utils.MapUtil;
import qtkj.com.qtoaandroid.utils.MyBDLocation;

/**
 * Created by Administrator on 2017/8/8 0008.
 */

public class SignOutActivity extends BaseActivity {
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

    @Override
    protected int layout() {
        return R.layout.activity_sign_out;
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

    }

    @OnClick(R.id.iv_back)
    public void onClick() {
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
