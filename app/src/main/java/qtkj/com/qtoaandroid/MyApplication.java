package qtkj.com.qtoaandroid;

import android.annotation.TargetApi;
import android.app.Application;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.widget.RemoteViews;

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
import com.tencent.bugly.crashreport.CrashReport;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import qtkj.com.qtoaandroid.activity.MainActivity;
import qtkj.com.qtoaandroid.model.Login;
import qtkj.com.qtoaandroid.utils.CommonUtil;
import qtkj.com.qtoaandroid.utils.MyBDLocation;
import qtkj.com.qtoaandroid.utils.NetUtil;
import qtkj.com.qtoaandroid.utils.SharedPreferencesUtils;

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
    public boolean isRegisterReceiver = false;
    /**
     * 服务是否开启标识
     */
    public boolean isTraceStarted = false;

    /**
     * 采集是否开启标识
     */
    public boolean isGatherStarted = false;

    /**
     * 轨迹服务
     */
    public Trace mTrace = null;
    private Notification notification = null;
    /**
     * 轨迹服务ID
     */
    public long serviceId = 148016;
//    public long serviceId = 147652;

    /**
     * Entity标识
     */
    public static String entityName = "5151515151";

    public static int screenWidth = 0;

    public static int screenHeight = 0;

    private  Login login;
    private SharedPreferencesUtils mPreferencesUtils;
    public  Login getLogin() {
        if(login==null){
            login=  mPreferencesUtils.getObject("login",Login.class);
        }
        return login;
    }

    public  void setLogin(Login login) {
        this.login = login;
        if(mPreferencesUtils==null){
            mPreferencesUtils=new SharedPreferencesUtils(mContext,"qtoa");
        }
        else {
            mPreferencesUtils.setObject("login",login);
        }
    }
   public static MyApplication mApplication;
    @Override
    public void onCreate() {
        super.onCreate();
        mApplication=this;
        mContext = getApplicationContext();
        if(mPreferencesUtils==null){
            mPreferencesUtils=new SharedPreferencesUtils(mContext,"qtoa");
        }
        //腾讯Bugly,崩溃日志跟踪
        CrashReport.initCrashReport(mContext, "a153d156a3", false);
        if(login==null){
            login=  mPreferencesUtils.getObject("login",Login.class);
        }
        // 若为创建独立进程，则不初始化成员变量
        if ("com.baidu.track:remote".equals(CommonUtil.getCurProcessName(mContext))) {
            return;
        }
        SDKInitializer.initialize(mContext);
        trackConf = getSharedPreferences("track_conf", MODE_PRIVATE);
        mClient = new LBSTraceClient(mContext);
        getScreenSize();
        clearTraceStatus();
    }

    public void initTrace() {
        if (login != null) {
            trackConf.edit().putString("entityName", login.getUserId() + "");
            trackConf.edit().putString("post_id", login.getPostId() + "");
        }
        initNotification();
        mTrace = new Trace(serviceId, trackConf.getString("entityName", entityName));
        mTrace.setNotification(notification);
        mClient.setOnCustomAttributeListener(new OnCustomAttributeListener() {
            @Override
            public Map<String, String> onTrackAttributeCallback() {
                //上传自定义参数
                Map<String, String> map = new HashMap<>();
                map.put("post_id", trackConf.getString("post_id", login.getPostId() + ""));
                return map;
            }
        });
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
    @TargetApi(16)
    private void initNotification() {
        Notification.Builder builder = new Notification.Builder(this);
        Intent notificationIntent = new Intent(this, MainActivity.class);

        Bitmap icon = BitmapFactory.decodeResource(this.getResources(),
                R.mipmap.ic_notific);

        // 设置PendingIntent
        builder.setContentIntent(PendingIntent.getActivity(this, 0, notificationIntent, 0))
                .setLargeIcon(icon)  // 设置下拉列表中的图标(大图标)
                .setContentTitle("龙子湖街道办事处") // 设置下拉列表里的标题
                .setSmallIcon(R.mipmap.ic_notific) // 设置状态栏内的小图标
                .setContentText("服务正在运行...") // 设置上下文内容
                .setWhen(System.currentTimeMillis()); // 设置该通知发生的时间

        notification = builder.build(); // 获取构建好的Notification
        notification.defaults = Notification.DEFAULT_SOUND; //设置为默认的声音
    }
}
