package qtkj.com.qtoaandroid.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.SupportMapFragment;
import com.baidu.mapapi.model.LatLng;
import com.baidu.trace.api.analysis.DrivingBehaviorResponse;
import com.baidu.trace.api.analysis.OnAnalysisListener;
import com.baidu.trace.api.analysis.SpeedingInfo;
import com.baidu.trace.api.analysis.StayPointResponse;
import com.baidu.trace.api.track.DistanceResponse;
import com.baidu.trace.api.track.HistoryTrackRequest;
import com.baidu.trace.api.track.HistoryTrackResponse;
import com.baidu.trace.api.track.LatestPointResponse;
import com.baidu.trace.api.track.OnTrackListener;
import com.baidu.trace.api.track.TrackPoint;
import com.baidu.trace.model.ProcessOption;
import com.baidu.trace.model.SortType;
import com.baidu.trace.model.StatusCodes;
import com.baidu.trace.model.TransportMode;

import java.util.ArrayList;
import java.util.List;

import qtkj.com.qtoaandroid.MyApplication;
import qtkj.com.qtoaandroid.R;
import qtkj.com.qtoaandroid.activity.MovementActivity;
import qtkj.com.qtoaandroid.activity.SignRecordActivity;
import qtkj.com.qtoaandroid.utils.BitmapUtil;
import qtkj.com.qtoaandroid.utils.CommonUtil;
import qtkj.com.qtoaandroid.utils.Constants;
import qtkj.com.qtoaandroid.utils.LogUtils;
import qtkj.com.qtoaandroid.utils.MapUtil;
import qtkj.com.qtoaandroid.utils.ViewUtil;

/**
 * Created by Administrator on 2017/8/9 0009.
 */

public class BaseMapFragment extends Fragment implements BaiduMap.OnMapClickListener {
    private static final String a = SupportMapFragment.class.getSimpleName();
    private MapView b;
    private BaiduMapOptions c;
    private MyApplication trackApp = null;

    private ViewUtil viewUtil = null;

    /**
     * 地图工具
     */
    private MapUtil mapUtil = null;

    /**
     * 历史轨迹请求
     */
    private HistoryTrackRequest historyTrackRequest = new HistoryTrackRequest();
    /**
     * 轨迹监听器（用于接收历史轨迹回调）
     */
    private OnTrackListener mTrackListener = null;
    /**
     * 轨迹分析监听器
     */
    private OnAnalysisListener mAnalysisListener = null;
    /**
     * 轨迹点集合
     */
    private List<LatLng> trackPoints = new ArrayList<>();
    /**
     * 查询轨迹的开始时间
     */
    private long startTime;

    /**
     * 查询轨迹的结束时间
     */
    private long endTime;
    private String entityName;
    /**
     * 轨迹排序规则
     */
    private SortType sortType = SortType.asc;
    /**
     * 轨迹分析上一次请求时间
     */
    private long lastQueryTime = 0;
    private int pageIndex = 1;
    private int stype = 0;

    public void setStype(int stype) {
        this.stype = stype;
    }

    public BaseMapFragment() {
    }

    @SuppressLint("ValidFragment")
    private BaseMapFragment(BaiduMapOptions var1) {
        this.c = var1;
    }

    public static BaseMapFragment newInstance() {
        return new BaseMapFragment();
    }

    public static BaseMapFragment newInstance(BaiduMapOptions var0) {
        return new BaseMapFragment(var0);
    }

    public BaiduMap getBaiduMap() {
        return this.b == null ? null : this.b.getMap();
    }

    public MapView getMapView() {
        return this.b;
    }


    public void onCreate(Bundle var1) {
        super.onCreate(var1);
        Log.e("onCreate", "onCreate");
        trackApp = (MyApplication) getActivity().getApplicationContext();
        viewUtil = new ViewUtil();
    }

    public View onCreateView(LayoutInflater var1, ViewGroup var2, Bundle var3) {
        this.b = new MapView(this.getActivity(), this.c);
        this.b.getMap().setOnMapClickListener(this);
        mapUtil = MapUtil.getInstance();
        mapUtil.init(b);
        mapUtil.setCenter(trackApp);
        initListener();
        trackPoints.clear();
        pageIndex = 1;
        return this.b;
    }

    public void start(long startTime, long endTime, String entityName) {
        this.startTime = startTime / 1000;
        this.endTime = endTime / 1000;
        this.entityName = entityName;
        LogUtils.d(entityName+"_"+this.startTime+"-"+this.endTime);
        trackPoints.clear();
        queryHistoryTrack();

    }

    public void onViewCreated(View var1, Bundle var2) {
        super.onViewCreated(var1, var2);
    }

    public void onActivityCreated(Bundle var1) {
        super.onActivityCreated(var1);
    }

    public void onViewStateRestored(Bundle var1) {
        super.onViewStateRestored(var1);
        if (var1 != null) {
            ;
        }
    }

    public void onStart() {
        super.onStart();
    }

    public void onResume() {
        super.onResume();
        this.b.onResume();
        mapUtil.onResume();

    }

    public void onSaveInstanceState(Bundle var1) {
        super.onSaveInstanceState(var1);
    }

    public void onPause() {
        super.onPause();
        this.b.onPause();
        mapUtil.onPause();
    }

    public void onStop() {
        super.onStop();
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.b.onDestroy();

    }

    public void onDestroy() {
        super.onDestroy();


    }

    public void onDetach() {
        super.onDetach();
    }

    public void onConfigurationChanged(Configuration var1) {
        super.onConfigurationChanged(var1);
    }

    private void initListener() {

        mTrackListener = new OnTrackListener() {
            @Override
            public void onHistoryTrackCallback(HistoryTrackResponse response) {
                int total = response.getTotal();
                if (StatusCodes.SUCCESS != response.getStatus()) {
                    LogUtils.d(response.toString());
//                    viewUtil.showToast(getActivity(), getString(R.string.no_track_data));
                } else if (0 == total) {
                    LogUtils.d(getString(R.string.no_track_data));

//                    viewUtil.showToast(getActivity(), getString(R.string.no_track_data));
                } else {
                    LogUtils.d(response.toString());

                    List<TrackPoint> points = response.getTrackPoints();
                    if (null != points) {
                        for (TrackPoint trackPoint : points) {
                            if (!CommonUtil.isZeroPoint(trackPoint.getLocation().getLatitude(),
                                    trackPoint.getLocation().getLongitude())) {
                                trackPoints.add(MapUtil.convertTrace2Map(trackPoint.getLocation()));
                            }
                        }
                    }
                }

                if (total > Constants.PAGE_SIZE * pageIndex) {
                    historyTrackRequest.setPageIndex(++pageIndex);
                    queryHistoryTrack();
                } else {

                    mapUtil.drawHistoryTrack(trackPoints, sortType);
                }
            }

            @Override
            public void onDistanceCallback(DistanceResponse response) {
                super.onDistanceCallback(response);
            }

            @Override
            public void onLatestPointCallback(LatestPointResponse response) {
                super.onLatestPointCallback(response);
            }
        };

        mAnalysisListener = new OnAnalysisListener() {
            @Override
            public void onStayPointCallback(StayPointResponse response) {
                if (StatusCodes.SUCCESS != response.getStatus()) {
                    lastQueryTime = 0;
                    viewUtil.showToast(getActivity(), response.getMessage());
                    return;
                }
                if (0 == response.getStayPointNum()) {
                    return;
                }
//                stayPoints.addAll(response.getStayPoints());
//                handleOverlays(stayPointMarkers, stayPoints, isStayPoint);
            }

            @Override
            public void onDrivingBehaviorCallback(DrivingBehaviorResponse response) {
                if (StatusCodes.SUCCESS != response.getStatus()) {
                    lastQueryTime = 0;
                    viewUtil.showToast(getActivity(), response.getMessage());
                    return;
                }

                if (0 == response.getSpeedingNum() && 0 == response.getHarshAccelerationNum()
                        && 0 == response.getHarshBreakingNum() && 0 == response.getHarshSteeringNum()) {
                    return;
                }

//                clearAnalysisList();
//                clearAnalysisOverlay();
//
//                List<SpeedingInfo> speedingInfos = response.getSpeedings();
//                for (SpeedingInfo info : speedingInfos) {
//                    speedingPoints.addAll(info.getPoints());
//                }
//                harshAccelPoints.addAll(response.getHarshAccelerationPoints());
//                harshBreakingPoints.addAll(response.getHarshBreakingPoints());
//                harshSteeringPoints.addAll(response.getHarshSteeringPoints());
//
//                handleOverlays(speedingMarkers, speedingPoints, isSpeeding);
//                handleOverlays(harshAccelMarkers, harshAccelPoints, isHarshAccel);
//                handleOverlays(harshBreakingMarkers, harshBreakingPoints, isHarshBreaking);
//                handleOverlays(harshSteeringMarkers, harshSteeringPoints, isHarshSteering);
            }
        };
    }

    /**
     * 查询历史轨迹
     */
    private void queryHistoryTrack() {
        if (trackApp == null) {
            Log.e("trackApp", "NULL");
            trackApp = (MyApplication) getActivity().getApplicationContext();
        }
        trackApp.initRequest(historyTrackRequest);
        historyTrackRequest.setEntityName(entityName);
        historyTrackRequest.setStartTime(startTime);
        historyTrackRequest.setEndTime(endTime);
        // 设置需要纠偏
        historyTrackRequest.setProcessed(true);
// 创建纠偏选项实例
        ProcessOption processOption = new ProcessOption();
// 设置需要去噪
        processOption.setNeedDenoise(true);
// 设置需要抽稀
        processOption.setNeedVacuate(true);
// 设置需要绑路
        processOption.setNeedMapMatch(true);
// 设置精度过滤值(定位精度大于100米的过滤掉)
        processOption.setRadiusThreshold(100);
// 设置交通方式为驾车
        processOption.setTransportMode(TransportMode.riding);
// 设置纠偏选项
//        historyTrackRequest.setProcessOption(processOption);
        historyTrackRequest.setPageIndex(pageIndex);
        historyTrackRequest.setPageSize(Constants.PAGE_SIZE);
        trackApp.mClient.queryHistoryTrack(historyTrackRequest, mTrackListener);
    }

    @Override
    public void onMapClick(LatLng lng) {
        switch (stype) {
            case 0:
                break;
            case 1:
                startActivity(new Intent(getActivity(), MovementActivity.class)
                        .putExtra("startTime", startTime * 1000)
                        .putExtra("endTime", endTime * 1000)
                        .putExtra("entityName", entityName));
                break;
        }


    }

    @Override
    public boolean onMapPoiClick(MapPoi poi) {
        return false;
    }
}