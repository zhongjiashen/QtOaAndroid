package qtkj.com.qtoaandroid.activity;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import butterknife.OnClick;
import qtkj.com.qtoaandroid.R;
import qtkj.com.qtoaandroid.adapter.recycleview.BaseRecycleViewAdapter;
import qtkj.com.qtoaandroid.adapter.recycleview.PhotoRecordAdapter;
import qtkj.com.qtoaandroid.view.PhotoRecordP;

/**
 * Created by Administrator on 2017/8/4 0004.
 * 照片记录
 */

public class PhotoRecordActivity extends BaseActivity <PhotoRecordP>{
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.SwipeRefreshLayout)
    android.support.v4.widget.SwipeRefreshLayout swipeRefreshLayout;

    BaseRecycleViewAdapter adapter;
    private Handler handler = new Handler();
    @Override
    protected int layout() {
        return R.layout.activity_photo_record;
    }

    @Override
    protected void Initialize() {
        presenter=new PhotoRecordP(this,this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter=new PhotoRecordAdapter(this));
    }

    @OnClick(R.id.iv_back)
    public void onClick() {
    }
}
