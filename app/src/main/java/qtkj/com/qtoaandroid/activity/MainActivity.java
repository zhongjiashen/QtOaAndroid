package qtkj.com.qtoaandroid.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import android.widget.RadioGroup;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.baidu.trace.api.fence.FenceAlarmPushInfo;
import com.baidu.trace.api.fence.MonitoredAction;
import com.baidu.trace.model.OnTraceListener;
import com.baidu.trace.model.PushMessage;
import com.baidu.trace.model.StatusCodes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import qtkj.com.qtoaandroid.MyApplication;
import qtkj.com.qtoaandroid.R;
import qtkj.com.qtoaandroid.adapter.fragment.FragmentTabAdapter;
import qtkj.com.qtoaandroid.fragment.AddressBookFragment;
import qtkj.com.qtoaandroid.fragment.BaseFragmengt;
import qtkj.com.qtoaandroid.fragment.NowLocationFragment;
import qtkj.com.qtoaandroid.fragment.PersonalCenterFragment;
import qtkj.com.qtoaandroid.fragment.SignInFragment;
import qtkj.com.qtoaandroid.receiver.TrackReceiver;
import qtkj.com.qtoaandroid.utils.BitmapUtil;
import qtkj.com.qtoaandroid.utils.CommonUtil;
import qtkj.com.qtoaandroid.utils.LogUtils;
import qtkj.com.qtoaandroid.utils.MyBDLocation;
import qtkj.com.qtoaandroid.utils.ViewUtil;
import qtkj.com.qtoaandroid.view.MainP;

/**
 * Created by Administrator on 2017/8/3 0003.
 */

public class MainActivity extends BaseActivity<MainP> {
    private PowerManager powerManager = null;
    private MyApplication trackApp = null;
    private PowerManager.WakeLock wakeLock = null;
    private TrackReceiver trackReceiver = null;
    private ViewUtil viewUtil = null;
    private MyBDLocation myBDLocation;
    private MyBroadcastReciver myBroadcastReciver;
    private int type = -1;


    private String imgStr;
    public void setImgStr(String imgStr) {
        this.imgStr = imgStr;
    }




    /**
     * 轨迹服务监听器
     */
    private OnTraceListener traceListener = null;

    @BindView(R.id.rg_bottom)
    RadioGroup rgBottom;
    public List<BaseFragmengt> fragments = new ArrayList<BaseFragmengt>();

    @Override
    protected int layout() {
        return R.layout.activity_main;
    }

    @Override
    public void returnData(int requestCode, Object data) {
        super.returnData(requestCode, data);
        switch (requestCode) {
            case 0:
//                showShortToast("签到成功！");
//                MyApplication.login.setIs_sign(1);
//                startTrac();
//                type = -1;
//                SignInFragment signInFragment= (SignInFragment) fragments.get(0);
//                signInFragment.setSignType(1);
            case 1:
                type = -1;
                break;

        }
    }

    @Override
    protected void Initialize() {
        trackApp = (MyApplication) getApplicationContext();
        powerManager = (PowerManager) trackApp.getSystemService(Context.POWER_SERVICE);
        fragments.add(new SignInFragment());
        fragments.add(new NowLocationFragment());
        fragments.add(new AddressBookFragment());
        fragments.add(new PersonalCenterFragment());
        FragmentTabAdapter tabAdapter = new FragmentTabAdapter(this, fragments, R.id.fl_main, rgBottom);
        myBDLocation = new MyBDLocation(getApplicationContext());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.qtoaandroid.myLocation");
        this.registerReceiver(myBroadcastReciver = new MyBroadcastReciver(), intentFilter);
        presenter = new MainP(this, this);

    }

    public void startBDLocation(int type) {
        this.type = type;
        myBDLocation.start();
    }

    /**
     * 开启鹰眼轨迹服务和采集
     */
    public void startTrac() {
        trackApp.initTrace();
        initListener();
        if (!trackApp.isTraceStarted) {
            trackApp.mClient.startTrace(trackApp.mTrace, traceListener);
            LogUtils.d("开启服务");
        }
        if (!trackApp.isGatherStarted)
            trackApp.mClient.startGather(traceListener);


    }

    /**
     * 开启鹰眼轨迹服务和采集
     */
    public void stopTrac() {
        initListener();
        if (trackApp.isTraceStarted) {
            trackApp.mClient.stopTrace(trackApp.mTrace, traceListener);
        }

    }

    /**
     * 注册广播（电源锁、GPS状态）
     */
    private void registerReceiver() {
        if (trackApp.isRegisterReceiver) {
            return;
        }

        if (null == wakeLock) {
            wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "track upload");
        }
        if (null == trackReceiver) {
            trackReceiver = new TrackReceiver(wakeLock);
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        filter.addAction(StatusCodes.GPS_STATUS_ACTION);
        trackApp.registerReceiver(trackReceiver, filter);
        trackApp.isRegisterReceiver = true;

    }

    @Override
    protected void onResume() {
        super.onResume();
        trackApp = (MyApplication) getApplicationContext();
        // 在Android 6.0及以上系统，若定制手机使用到doze模式，请求将应用添加到白名单。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String packageName = trackApp.getPackageName();
            boolean isIgnoring = powerManager.isIgnoringBatteryOptimizations(packageName);
            if (!isIgnoring) {
                Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:" + packageName));
                try {
                    startActivity(intent);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private void unregisterPowerReceiver() {
        if (!trackApp.isRegisterReceiver) {
            return;
        }
        if (null != trackReceiver) {
            trackApp.unregisterReceiver(trackReceiver);
        }
        trackApp.isRegisterReceiver = false;
    }

    private void initListener() {
        if (traceListener == null) {
            viewUtil = new ViewUtil();
            traceListener = new OnTraceListener() {

                /**
                 * 绑定服务回调接口
                 * @param errorNo  状态码
                 * @param message 消息
                 *                <p>
                 *                <pre>0：成功 </pre>
                 *                <pre>1：失败</pre>
                 */
                @Override
                public void onBindServiceCallback(int errorNo, String message) {
//                    viewUtil.showToast(MainActivity.this,
//                            String.format("onBindServiceCallback, errorNo:%d, message:%s ", errorNo, message));
                }

                /**
                 * 开启服务回调接口
                 * @param errorNo 状态码
                 * @param message 消息
                 *                <p>
                 *                <pre>0：成功 </pre>
                 *                <pre>10000：请求发送失败</pre>
                 *                <pre>10001：服务开启失败</pre>
                 *                <pre>10002：参数错误</pre>
                 *                <pre>10003：网络连接失败</pre>
                 *                <pre>10004：网络未开启</pre>
                 *                <pre>10005：服务正在开启</pre>
                 *                <pre>10006：服务已开启</pre>
                 */
                @Override
                public void onStartTraceCallback(int errorNo, String message) {
                    if (StatusCodes.SUCCESS == errorNo || StatusCodes.START_TRACE_NETWORK_CONNECT_FAILED <= errorNo) {
                        trackApp.isTraceStarted = true;
                        SharedPreferences.Editor editor = trackApp.trackConf.edit();
                        editor.putBoolean("is_trace_started", true);
                        editor.apply();
                        trackApp.mClient.startGather(traceListener);
                        registerReceiver();
                    }else {
                        viewUtil.showToast(MainActivity.this,
                                String.format("onStartTraceCallback, errorNo:%d, message:%s ", errorNo, message));
                    }
                }

                /**
                 * 停止服务回调接口
                 * @param errorNo 状态码
                 * @param message 消息
                 *                <p>
                 *                <pre>0：成功</pre>
                 *                <pre>11000：请求发送失败</pre>
                 *                <pre>11001：服务停止失败</pre>
                 *                <pre>11002：服务未开启</pre>
                 *                <pre>11003：服务正在停止</pre>
                 */
                @Override
                public void onStopTraceCallback(int errorNo, String message) {
                    if (StatusCodes.SUCCESS == errorNo || StatusCodes.CACHE_TRACK_NOT_UPLOAD == errorNo) {
                        trackApp.isTraceStarted = false;
                        trackApp.isGatherStarted = false;
                        // 停止成功后，直接移除is_trace_started记录（便于区分用户没有停止服务，直接杀死进程的情况）
                        SharedPreferences.Editor editor = trackApp.trackConf.edit();
                        editor.remove("is_trace_started");
                        editor.remove("is_gather_started");
                        editor.apply();

                        unregisterPowerReceiver();
                    }else {
                        viewUtil.showToast(MainActivity.this,
                                String.format("onStopTraceCallback, errorNo:%d, message:%s ", errorNo, message));
                    }
                }

                /**
                 * 开启采集回调接口
                 * @param errorNo 状态码
                 * @param message 消息
                 *                <p>
                 *                <pre>0：成功</pre>
                 *                <pre>12000：请求发送失败</pre>
                 *                <pre>12001：采集开启失败</pre>
                 *                <pre>12002：服务未开启</pre>
                 */
                @Override
                public void onStartGatherCallback(int errorNo, String message) {
                    if (StatusCodes.SUCCESS == errorNo || StatusCodes.GATHER_STARTED == errorNo) {
                        trackApp.isGatherStarted = true;
                        SharedPreferences.Editor editor = trackApp.trackConf.edit();
                        editor.putBoolean("is_gather_started", true);
                        editor.apply();


                    }else {
                        viewUtil.showToast(MainActivity.this,
                                String.format("onStartGatherCallback, errorNo:%d, message:%s ", errorNo, message));
                    }
                }

                /**
                 * 停止采集回调接口
                 * @param errorNo 状态码
                 * @param message 消息
                 *                <p>
                 *                <pre>0：成功</pre>
                 *                <pre>13000：请求发送失败</pre>
                 *                <pre>13001：采集停止失败</pre>
                 *                <pre>13002：服务未开启</pre>
                 */
                @Override
                public void onStopGatherCallback(int errorNo, String message) {
                    if (StatusCodes.SUCCESS == errorNo || StatusCodes.GATHER_STOPPED == errorNo) {
                        trackApp.isGatherStarted = false;
                        SharedPreferences.Editor editor = trackApp.trackConf.edit();
                        editor.remove("is_gather_started");
                        editor.apply();

                    }else {
                        viewUtil.showToast(MainActivity.this,
                                String.format("onStopGatherCallback, errorNo:%d, message:%s ", errorNo, message));
                    }
                }

                /**
                 * 推送消息回调接口
                 *
                 * @param messageType 状态码
                 * @param pushMessage 消息
                 *                  <p>
                 *                  <pre>0x01：配置下发</pre>
                 *                  <pre>0x02：语音消息</pre>
                 *                  <pre>0x03：服务端围栏报警消息</pre>
                 *                  <pre>0x04：本地围栏报警消息</pre>
                 *                  <pre>0x05~0x40：系统预留</pre>
                 *                  <pre>0x41~0xFF：开发者自定义</pre>
                 */
                @Override
                public void onPushCallback(byte messageType, PushMessage pushMessage) {

                }
            };
        }
    }
    private class MyBroadcastReciver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("com.qtoaandroid.myLocation")) {
                BDLocation bdLocation = intent.getParcelableExtra("location");
                Map<String, String> map = new HashMap<>();
                map.put("userId", MyApplication.login.getUserId() + "");
                map.put("position", bdLocation.getAddrStr());
                switch (type) {
                    case 0:
                        map.put("type", "0");
                        map.put("workStartTime", MyApplication.login.getWorkStartTime());
                        map.put("workEndTime", MyApplication.login.getWorkEndTime());
                        presenter.signIn(0, map);
                        break;
                    case 1:
                        map.put("imgStr",imgStr);
                        presenter.imgByUserId(1,map);
                        break;
                }
                LatLng latLng = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());


            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myBroadcastReciver);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    startTrac();
                    SignInFragment signInFragment= (SignInFragment) fragments.get(0);
                    signInFragment.setSignType(1);
                }
                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    stopTrac();
                    SignInFragment signInFragment= (SignInFragment) fragments.get(0);
                    signInFragment.setSignType(0);
                }
                break;

            default:

        }

    }

}
