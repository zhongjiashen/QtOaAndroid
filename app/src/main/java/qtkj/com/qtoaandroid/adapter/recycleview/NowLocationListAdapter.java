package qtkj.com.qtoaandroid.adapter.recycleview;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.bumptech.glide.Glide;
import com.bumptech.glide.util.LruCache;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import qtkj.com.qtoaandroid.Contest;
import qtkj.com.qtoaandroid.R;
import qtkj.com.qtoaandroid.model.NowLocationList;
import qtkj.com.qtoaandroid.viewbar.CircleImageView;

/**
 * Created by Administrator on 2017/8/4 0004.
 */

public class NowLocationListAdapter extends BaseRecycleViewAdapter<NowLocationList> {
    private String postname;
    private RecyclerView mRecyclerView;
    private Map<Integer, String> mMap = new HashMap<>();

    public void setPostname(String postname) {
        this.postname = postname;
    }

    public NowLocationListAdapter(Activity activity) {
        super(activity);



    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(ViewGroup parent, int viewType) {
        if (mRecyclerView == null) {
            mRecyclerView = (RecyclerView) parent;
        }
        View view = LayoutInflater.from(activity).inflate(R.layout.item_now_location_list, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (list.size() > 0) {
            ViewHolder viewHolder = (ViewHolder) holder;
//            viewHolder.tvKind.setText(postname);
            viewHolder.tvName.setText(list.get(position).getUserName());
            Glide.with(activity).load(Contest.baseurl + list.get(position).getImg()).placeholder(R.mipmap.ic_photo).error(R.mipmap.ic_photo).into(viewHolder.ivPhoto);
            viewHolder.tvKindNumber.setText(postname + list.get(position).getUserId());
            viewHolder.tvAddress.setTag(position);
            if (mMap.get(position) == null) {
                if(list.get(position).getPt()!=null) {
                    LatLngToAddressTask it = new LatLngToAddressTask();
                    it.execute(position);
                }else {
                    viewHolder.tvAddress.setText("该员工的当前位置信息没有上传！");
                }
            } else {
                viewHolder.tvAddress.setText(mMap.get(position));
            }

        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_photo)
        CircleImageView ivPhoto;
        @BindView(R.id.tv_name)
        TextView tvName;
       /* @BindView(R.id.tv_kind)
        TextView tvKind;*/
        @BindView(R.id.tv_kind_number)
        TextView tvKindNumber;
        @BindView(R.id.tv_address)
        TextView tvAddress;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     *
     */
    class LatLngToAddressTask extends AsyncTask<Integer, Void, String> {
        private int i;
        String address = "未获取到详细地址信息！";

        private boolean run = true;

        @Override
        protected String doInBackground(Integer... integers) {
            i = integers[0];
            String s = LatLngToAddress(list.get(i).getPt());
            // 如果本地还没缓存该地址信息，就缓存
            if (mMap.get(i) == null) {
                mMap.put(i, s);
            }
            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            // 通过Tag找到我们需要的ImageView，如果该ImageView所在的item已被移出页面，就会直接返回null
            TextView textView = (TextView) mRecyclerView.findViewWithTag(i);
            if (textView != null && s != null) {
                textView.setText(s);
            }
        }

        /*百度地图转换
         经纬度转换为地址信息*/
        private String LatLngToAddress(LatLng pt) {

            GeoCoder mSearch = GeoCoder.newInstance();
            mSearch.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
                @Override
                public void onGetGeoCodeResult(GeoCodeResult result) {

                }

                @Override
                public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {

                    if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                        address = "未获取到详细地址信息！";
                    } else {
                        //获取点击的坐标地址
                        address = result.getAddress();
                    }
                    run = false;
                }
            });
// 反Geo搜索
            mSearch.reverseGeoCode(new ReverseGeoCodeOption()
                    .location(pt));
            while (run) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return address;
        }
    }


}
