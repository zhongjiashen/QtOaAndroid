package qtkj.com.qtoaandroid.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.model.LatLng;
import com.baidu.trace.api.entity.EntityInfo;
import com.baidu.trace.api.entity.EntityListRequest;
import com.baidu.trace.api.entity.EntityListResponse;
import com.baidu.trace.api.entity.FilterCondition;
import com.baidu.trace.api.entity.OnEntityListener;
import com.baidu.trace.api.track.HistoryTrackRequest;
import com.baidu.trace.api.track.TrackPoint;
import com.baidu.trace.model.CoordType;
import com.baidu.trace.model.SortType;
import com.baidu.trace.model.StatusCodes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import qtkj.com.qtoaandroid.MyApplication;
import qtkj.com.qtoaandroid.R;
import qtkj.com.qtoaandroid.model.Login;
import qtkj.com.qtoaandroid.utils.BitmapUtil;
import qtkj.com.qtoaandroid.utils.CommonUtil;
import qtkj.com.qtoaandroid.utils.Constants;
import qtkj.com.qtoaandroid.utils.MapUtil;
import qtkj.com.qtoaandroid.utils.ViewUtil;

/**
 * Created by Administrator on 2017/8/8 0008.
 */

public class NowLocationActivity extends BaseActivity implements BaiduMap.OnMarkerClickListener {
    /**
     * 地图工具
     */
    private MapUtil mapUtil = null;
    private MyApplication trackApp = null;
    /**
     * 实时位置查询请求
     */
    private EntityListRequest mEntityListRequest = new EntityListRequest();
    private int pageIndex = 1;
    OnEntityListener entityListener;
    private ViewUtil viewUtil = null;
    /**
     * 实时位置点集合
     */
    private List<LatLng> entityPoints = new ArrayList<>();
    List list;
    @Override
    protected int layout() {
        return R.layout.activity_now_location;
    }

    @Override
    protected void Initialize() {
        viewUtil = new ViewUtil();
        trackApp = (MyApplication) getApplicationContext();
        mapUtil = MapUtil.getInstance();
        mapUtil.init((MapView) findViewById(R.id.mapView));
        mapUtil.baiduMap.setOnMarkerClickListener(this);
        mapUtil.setCenter(trackApp);
        initListener();
        String list_torsing=getIntent().getStringExtra("list");
        Gson gson = new Gson();
        Type jsonType =  new TypeToken<List<String>>() {
        }.getType();
       list= gson.fromJson(list_torsing,  jsonType);
        queryEntityList();
        BitmapUtil.init();
    }

    /**
     * 查询实时位置
     *
     */
    private void queryEntityList(){
        trackApp.initRequest(mEntityListRequest);
        mEntityListRequest.setPageIndex(pageIndex);
        mEntityListRequest.setPageSize(100);
        mEntityListRequest.setTag(5);
        mEntityListRequest.setCoordTypeOutput(CoordType.bd09ll);
        //设置活跃时间
        long activeTime = System.currentTimeMillis() / 1000 - 5 * 60;
// 过滤条件
        FilterCondition filterCondition = new FilterCondition();
// 查找当前时间5分钟之内有定位信息上传的entity
//        filterCondition.setActiveTime(activeTime);

        filterCondition.setEntityNames(list);
        mEntityListRequest.setFilterCondition(filterCondition);
        trackApp.mClient.queryEntityList(mEntityListRequest, entityListener);
    }
    private void initListener() {
        // 初始化监听器
        entityListener = new OnEntityListener() {
            @Override
            public void onEntityListCallback(EntityListResponse response) {
                int total = response.getTotal();
                if (StatusCodes.SUCCESS != response.getStatus()) {
                    viewUtil.showToast(NowLocationActivity.this, response.getMessage());
                } else if (0 == total) {
                    viewUtil.showToast(NowLocationActivity.this, getString(R.string.no_entity_data));
                } else {
                    Log.e("实时位置","1");
                    List<EntityInfo> entities = response.getEntities();
                    if (null != entities) {
                        Log.e("实时位置","2");
                        for (EntityInfo entityInfo : entities) {
                            if (!CommonUtil.isZeroPoint(entityInfo.getLatestLocation().getLocation().latitude,
                                    entityInfo.getLatestLocation().getLocation().longitude)) {
                                entityPoints.add(MapUtil.convertTrace2Map(entityInfo.getLatestLocation().getLocation()));
                                Log.e("实时位置","3");
                            }
                        }
                    }
                }
                if (total > 100* pageIndex) {
                    mEntityListRequest.setPageIndex(++pageIndex);
                    queryEntityList();
                } else {
//                    mapUtil.updateStatus(entityPoints.get(0), true);
//                    mapUtil.drawHistoryTrack(entityPoints, SortType.asc);
                }
                Log.e("实时位置",response.toString());
                Toast.makeText(NowLocationActivity.this,response.toString(),Toast.LENGTH_SHORT).show();
            }
        };
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
        BitmapUtil.clear();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapUtil.onResume();

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}
