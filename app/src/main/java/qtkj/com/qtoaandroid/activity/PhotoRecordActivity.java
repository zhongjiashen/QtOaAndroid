package qtkj.com.qtoaandroid.activity;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import qtkj.com.qtoaandroid.R;
import qtkj.com.qtoaandroid.adapter.recycleview.BaseRecycleViewAdapter;
import qtkj.com.qtoaandroid.adapter.recycleview.PhotoRecordAdapter;
import qtkj.com.qtoaandroid.model.PhotoRecord;
import qtkj.com.qtoaandroid.utils.LogUtils;
import qtkj.com.qtoaandroid.view.PhotoRecordP;

/**
 * Created by Administrator on 2017/8/4 0004.
 * 照片记录
 */

public class PhotoRecordActivity extends BaseActivity<PhotoRecordP> {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.SwipeRefreshLayout)
    android.support.v4.widget.SwipeRefreshLayout swipeRefreshLayout;
    PhotoRecordAdapter adapter;
    private Handler handler = new Handler();
    Map<String, String> map;
    List<PhotoRecord> list;
    private int page=1;
    boolean isLoading;
    @Override
    protected int layout() {
        return R.layout.activity_photo_record;
    }

    @Override
    public void returnData(int requestCode, Object data) {
        super.returnData(requestCode, data);
        List<PhotoRecord> l=( List<PhotoRecord>) data;
        if(l==null||l.size()==0){
            LogUtils.d("没有数据");
            adapter.setMore(true);
        }else {
            adapter.setMore(false);
        }
        switch (requestCode){
            case 0:
                list= (List<PhotoRecord>) data;
                adapter.setList(list);
                swipeRefreshLayout.setRefreshing(false);
                break;
            case 1:
                list.addAll((List<PhotoRecord>) data);
                LogUtils.d("加载更多");
                adapter.setList(list);
                isLoading = false;
                break;
        }

    }

    @Override
    public void showShortToast(String text) {
        super.showShortToast(text);
        swipeRefreshLayout.setRefreshing(false);
        isLoading = false;
    }

    @Override
    protected void Initialize() {
        presenter = new PhotoRecordP(this, this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                map.put("pageIndex", page+"");
                presenter.getphotoHistory(0, map);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter = new PhotoRecordAdapter(this));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                //当前RecyclerView显示出来的最后一个的item的position
                int lastPosition = -1;

                //当前状态为停止滑动状态SCROLL_STATE_IDLE时
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    if(layoutManager instanceof GridLayoutManager){
                        //通过LayoutManager找到当前显示的最后的item的position
                        lastPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                    }else if(layoutManager instanceof LinearLayoutManager){
                        lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    }else if(layoutManager instanceof StaggeredGridLayoutManager){
                        //因为StaggeredGridLayoutManager的特殊性可能导致最后显示的item存在多个，所以这里取到的是一个数组
                        //得到这个数组后再取到数组中position值最大的那个就是最后显示的position值了
                        int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                        ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(lastPositions);
                        lastPosition = findMax(lastPositions);
                    }

                    //时判断界面显示的最后item的position是否等于itemCount总数-1也就是最后一个item的position
                    //如果相等则说明已经滑动到最后了
                    if(lastPosition == recyclerView.getLayoutManager().getItemCount()-1){
                        LogUtils.d("滑动到底了");
                        if (adapter.isLoading()&&!adapter.isMore()) {
                        if (!isLoading) {
                            isLoading = true;
                            page=page+1;
                            map.put("pageIndex", page+"");
                            presenter.getphotoHistory(1, map);


                        }
                    }
                    }

                }



            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                LogUtils.d("onScrolled");
//
//                if (!recyclerView.canScrollVertically(1)) {
//                    LogUtils.d("loading executed");
//                    if (adapter.isLoading()&&!adapter.isMore()) {
//                        if (!isLoading) {
//                            isLoading = true;
//                            page=page+1;
//                            map.put("pageIndex", page+"");
//                            presenter.getphotoHistory(1, map);
//                            isLoading = false;
//
//                        }
//                    }
//                }
            }
        });
        presenter = new PhotoRecordP(this, this);
        map = new HashMap<>();
        map.put("userId", getIntent().getStringExtra("userId"));
        map.put("pageSize", "5");
        map.put("pageIndex", page+"");
        presenter.getphotoHistory(0, map);
    }

    @OnClick(R.id.iv_back)
    public void onClick() {
        finish();
    }
    //找到数组中的最大值
    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }
}
