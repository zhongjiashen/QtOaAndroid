package qtkj.com.qtoaandroid;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.DisplayMetrics;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.trace.LBSTraceClient;
import com.baidu.trace.Trace;
import com.baidu.trace.api.entity.LocRequest;
import com.baidu.trace.api.entity.OnEntityListener;
import com.baidu.trace.api.track.LatestPointRequest;
import com.baidu.trace.api.track.OnTrackListener;
import com.baidu.trace.model.BaseRequest;
import com.baidu.trace.model.OnCustomAttributeListener;
import com.baidu.trace.model.ProcessOption;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import qtkj.com.qtoaandroid.utils.CommonUtil;
import qtkj.com.qtoaandroid.utils.NetUtil;

/**
 * Created by Administrator on 2017/8/7 0007.
 */

public class MyApplication extends Application {

    private LocRequest locRequest = null;
    public Context mContext = null;
    public SharedPreferences trackConf = null;
    private AtomicInteger mSequenceGenerator = new AtomicInteger();
    /**
     * 轨迹客户端
     */
    public LBSTraceClient mClient = null;

    /**
     * 轨迹服务
     */
    public Trace mTrace = null;
    /**
     * 轨迹服务ID
     */
    public long serviceId = 147652;

    /**
     * Entity标识
     */
    public String entityName = "myTrace";

    public static int screenWidth = 0;

    public static int screenHeight = 0;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        // 若为创建独立进程，则不初始化成员变量
        if ("com.baidu.track:remote".equals(CommonUtil.getCurProcessName(mContext))) {
            return;
        }
        SDKInitializer.initialize(mContext);
        mClient = new LBSTraceClient(mContext);
        mTrace = new Trace(serviceId, entityName);
        trackConf = getSharedPreferences("track_conf", MODE_PRIVATE);
        mClient.setOnCustomAttributeListener(new OnCustomAttributeListener() {
            @Override
            public Map<String, String> onTrackAttributeCallback() {
                Map<String, String> map = new HashMap<>();
                map.put("key1", "value1");
                map.put("key2", "value2");
                return map;
            }
        });
        getScreenSize();
        clearTraceStatus();
    }

    /**
     * 初始化请求公共参数
     *
     * @param request
     */
    public void initRequest(BaseRequest request) {
        request.setTag(getTag());
        request.setServiceId(serviceId);
    }
    /**
     * 获取当前位置
     */
    public void getCurrentLocation(OnEntityListener entityListener, OnTrackListener trackListener) {
        // 网络连接正常，开启服务及采集，则查询纠偏后实时位置；否则进行实时定位
        if (NetUtil.isNetworkAvailable(mContext)
                && trackConf.contains("is_trace_started")
                && trackConf.contains("is_gather_started")
                && trackConf.getBoolean("is_trace_started", false)
                && trackConf.getBoolean("is_gather_started", false)) {
            LatestPointRequest request = new LatestPointRequest(getTag(), serviceId, entityName);
            ProcessOption processOption = new ProcessOption();
            processOption.setNeedDenoise(true);
            processOption.setRadiusThreshold(100);
            request.setProcessOption(processOption);
            mClient.queryLatestPoint(request, trackListener);
        } else {
            mClient.queryRealTimeLoc(locRequest, entityListener);
        }
    }
    /**
     * 获取屏幕尺寸
     */
    private void getScreenSize() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenHeight = dm.heightPixels;
        screenWidth = dm.widthPixels;
    }

    /**
     * 清除Trace状态：初始化app时，判断上次是正常停止服务还是强制杀死进程，根据trackConf中是否有is_trace_started字段进行判断。
     * <p>
     * 停止服务成功后，会将该字段清除；若未清除，表明为非正常停止服务。
     */
    private void clearTraceStatus() {
        if (trackConf.contains("is_trace_started") || trackConf.contains("is_gather_started")) {
            SharedPreferences.Editor editor = trackConf.edit();
            editor.remove("is_trace_started");
            editor.remove("is_gather_started");
            editor.apply();
        }
    }
    /**
     * 获取请求标识
     *
     * @return
     */
    public int getTag() {
        return mSequenceGenerator.incrementAndGet();
    }

}
