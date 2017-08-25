package qtkj.com.qtoaandroid.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.trace.api.entity.EntityInfo;
import com.baidu.trace.api.entity.EntityListRequest;
import com.baidu.trace.api.entity.EntityListResponse;
import com.baidu.trace.api.entity.FilterCondition;
import com.baidu.trace.api.entity.OnEntityListener;
import com.baidu.trace.model.CoordType;
import com.baidu.trace.model.StatusCodes;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import qtkj.com.qtoaandroid.Contest;
import qtkj.com.qtoaandroid.MyApplication;
import qtkj.com.qtoaandroid.R;
import qtkj.com.qtoaandroid.dialog.LoadingDialog;
import qtkj.com.qtoaandroid.model.NowLocationF;
import qtkj.com.qtoaandroid.model.NowLocationList;
import qtkj.com.qtoaandroid.utils.BitmapUtil;
import qtkj.com.qtoaandroid.utils.CommonUtil;
import qtkj.com.qtoaandroid.utils.LogUtils;
import qtkj.com.qtoaandroid.utils.MapUtil;
import qtkj.com.qtoaandroid.utils.ViewUtil;
import qtkj.com.qtoaandroid.viewbar.CircleImageView;

/**
 * Created by Administrator on 2017/8/8 0008.
 */

public class NowLocationActivity extends BaseActivity implements BaiduMap.OnMarkerClickListener {
    @BindView(R.id.tv_right)
    TextView tvRight;
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
    List<NowLocationF.DeptBean> list;
    List<String> EntityNames = new ArrayList<>();
//    Map<String, NowLocationF.DeptBean> map = new HashMap();
    Map<String, NowLocationList> map = new HashMap();
    private boolean first = true;
    private String postname;
    protected LoadingDialog mDialog;
    @Override
    protected int layout() {
        return R.layout.activity_now_location;
    }

    @Override
    protected void Initialize() {
        mDialog=new LoadingDialog(this);
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<NowLocationList> list=new ArrayList<>(map.values());
                startActivity(new Intent(NowLocationActivity.this,NowLocationListActivity.class)
                        .putExtra("list",new Gson().toJson(list))
                        .putExtra("name",postname));
            }
        });
//        tvRight.setText(getIntent().getStringExtra("number"));
        viewUtil = new ViewUtil();
        trackApp = (MyApplication) getApplicationContext();
        mapUtil = MapUtil.getInstance();
        mapUtil.init((MapView) findViewById(R.id.mapView));
        mapUtil.baiduMap.setOnMarkerClickListener(this);
        mapUtil.setCenter(trackApp);
        initListener();
        String list_torsing = getIntent().getStringExtra("list");
        postname = getIntent().getStringExtra("name");
        Gson gson = new Gson();
        Type jsonType = new TypeToken<List<NowLocationF.DeptBean>>() {
        }.getType();
        list = gson.fromJson(list_torsing, jsonType);
        Log.e("NOW", list.toString() + "---------" + list_torsing);
        for (int i = 0; i < list.size(); i++) {
            EntityNames.add(list.get(i).getUserId() + "");
            NowLocationList nowLocationList=new NowLocationList();
            nowLocationList.setUserId(list.get(i).getUserId());
            nowLocationList.setUserName(list.get(i).getUserName());
            nowLocationList.setImg(list.get(i).getImg());
            map.put(list.get(i).getUserId() + "",nowLocationList );
        }

        queryEntityList();
        BitmapUtil.init();
        mapUtil.baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (marker != null) {
                    NowLocationList bean = map.get(marker.getExtraInfo().getInt("des") + "");
//                    LatLng pt = new LatLng(marker.getExtraInfo().getDouble("latitude"), marker.getExtraInfo().getDouble("longitude"));
//                    LogUtils.d( pt.toString());
                    showPopwindow(Contest.baseurl + bean.getImg(), bean.getUserName(),  bean.getUserId() + "", marker.getExtraInfo().getString("time"),bean.getPt());
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    /**
     * 查询实时位置
     */
    private void queryEntityList() {
        mDialog.show();
        trackApp.initRequest(mEntityListRequest);
        mEntityListRequest.setPageIndex(pageIndex);
        mEntityListRequest.setPageSize(10);
        mEntityListRequest.setTag(5);
        mEntityListRequest.setCoordTypeOutput(CoordType.bd09ll);
        //设置活跃时间
        long activeTime = System.currentTimeMillis() / 1000 - 5 * 60;
// 过滤条件
        FilterCondition filterCondition = new FilterCondition();
// 查找当前时间5分钟之内有定位信息上传的entity
        filterCondition.setActiveTime(activeTime);

        filterCondition.setEntityNames(EntityNames);
        Log.e("NOW", EntityNames.get(0) + "444" + EntityNames.toString());
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
                    viewUtil.showToast(NowLocationActivity.this, "员工未上传实时位置！");
                    LogUtils.d(response.getMessage());
                } else if (0 == total) {
                    LogUtils.d(getString(R.string.no_entity_data));
                    viewUtil.showToast(NowLocationActivity.this, "员工未上传实时位置！");
                } else {
                    Log.e("实时位置", "1");
                    List<EntityInfo> entities = response.getEntities();
                    if (null != entities) {
                        Log.e("实时位置", "2");
                        for (EntityInfo entityInfo : entities) {
                            NowLocationList bean = map.get(entityInfo.getEntityName());
                            if (!CommonUtil.isZeroPoint(entityInfo.getLatestLocation().getLocation().latitude,
                                    entityInfo.getLatestLocation().getLocation().longitude)) {
                                LatLng pt = MapUtil.convertTrace2Map(entityInfo.getLatestLocation().getLocation());
                                bean.setPt(pt);
                                map.put(bean.getUserId()+"",bean);
                                View contentView = LayoutInflater.from(NowLocationActivity.this).inflate(R.layout.overlay_view, null);
                                CircleImageView circleImageView=contentView.findViewById(R.id.civ_view);
                                Glide.with(NowLocationActivity.this).load(Contest.baseurl + bean.getImg()).placeholder(R.mipmap.ic_photo).into(circleImageView);
                                BitmapDescriptor bd = BitmapDescriptorFactory.fromView(contentView);
                                MarkerOptions oo = new MarkerOptions().icon(bd).
                                        position(pt).zIndex(100);
                                Bundle des = new Bundle();
                                des.putInt("des", bean.getUserId());
                                des.putDouble("latitude", entityInfo.getLatestLocation().getLocation().latitude);
                                des.putDouble("longitude", entityInfo.getLatestLocation().getLocation().longitude);
                                des.putString("time", entityInfo.getModifyTime());
                                oo.extraInfo(des);
                                mapUtil.baiduMap.addOverlay(oo);
                                if (first) {
                                    mapUtil.setMapStatus(pt, 15.0f);
                                    first = false;
                                }
                                Log.e("实时位置", "3");
                            }
                        }
                    }
                }
                if (total > 10 * pageIndex) {
                    mEntityListRequest.setPageIndex(++pageIndex);
                    queryEntityList();
                }else {
                    mDialog.dismiss();
                }
                Log.e("实时位置", response.toString());

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

    @OnClick(R.id.iv_back)
    public void onClick() {
        finish();
    }


    static class ViewHolder {
        @BindView(R.id.civ_view)
        CircleImageView civView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public Bitmap getBitmapFromView(View view) {
        view.destroyDrawingCache();
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = view.getDrawingCache(true);
        return bitmap;
    }

    /**
     * 显示popupWindow
     */
    private void showPopwindow(String url, String name, String userId, String time, LatLng pt) {
        //加载弹出框的布局
        View contentView = LayoutInflater.from(NowLocationActivity.this).inflate(
                R.layout.popwindow_view, null);
        popViewHolder popViewHolder = new popViewHolder(contentView);
        popViewHolder.tvNick.setText(name);
        popViewHolder.tvKind.setText(postname);
        popViewHolder.tvKindNumber.setText(postname + userId);
        popViewHolder.tvTime.setText(time.substring(10, 16));


        Glide.with(this).load(url).into(popViewHolder.ivPhoto);

        //设置弹出框的宽度和高度
        PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        LatLngToAddress(pt,popViewHolder.tvAddress,popupWindow);
        popupWindow.setFocusable(true);// 取得焦点
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //点击外部消失
        popupWindow.setOutsideTouchable(true);
        //设置可以点击
        popupWindow.setTouchable(true);
        //进入退出的动画
        popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
//从底部显示
        popupWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);

    }
    /*百度地图转换
               经纬度转换为地址信息*/
    private void LatLngToAddress(LatLng pt, final TextView textView, PopupWindow window) {
        final GeoCoder mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult result) {

            }
            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
                String address;
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    address = "未获取到详细地址信息！";
                } else {
                    //获取点击的坐标地址
                    address = result.getAddress();
                }
                textView.setText(address);
            }
        });
// 反Geo搜索
        mSearch.reverseGeoCode(new ReverseGeoCodeOption()
                .location(pt));
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mSearch.destroy();
            }
        });

    }

    class popViewHolder {
        @BindView(R.id.iv_photo)
        CircleImageView ivPhoto;
        @BindView(R.id.tv_nick)
        TextView tvNick;
        @BindView(R.id.tv_kind)
        TextView tvKind;
        @BindView(R.id.tv_kind_number)
        TextView tvKindNumber;
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.tv_time)
        TextView tvTime;

        popViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    /*百度地图转换
            经纬度转换为地址信息*/
    public void LatLngToAddress(LatLng latLng, final String url, final String name, final String userId, final String time) {
        GeoCoder mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult result) {

            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
                String address;
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    address = "未获取到详细地址信息！";
                } else {
                    //获取点击的坐标地址
                    address = result.getAddress();
                }

            }
        });
// 反Geo搜索
        mSearch.reverseGeoCode(new ReverseGeoCodeOption()
                .location(latLng));


    }

}
