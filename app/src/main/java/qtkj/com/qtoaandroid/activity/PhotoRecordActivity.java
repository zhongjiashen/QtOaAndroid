package qtkj.com.qtoaandroid.activity;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import qtkj.com.qtoaandroid.R;
import qtkj.com.qtoaandroid.adapter.recycleview.BaseRecycleViewAdapter;
import qtkj.com.qtoaandroid.adapter.recycleview.PhotoRecordAdapter;
import qtkj.com.qtoaandroid.model.PhotoRecord;
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

    BaseRecycleViewAdapter adapter;
    private Handler handler = new Handler();
    Map<String, String> map;
    List<PhotoRecord> list;
    @Override
    protected int layout() {
        return R.layout.activity_photo_record;
    }

    @Override
    public void returnData(int requestCode, Object data) {
        super.returnData(requestCode, data);
        list= (List<PhotoRecord>) data;
        adapter.setList(list);
    }

    @Override
    protected void Initialize() {
        presenter = new PhotoRecordP(this, this);
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
        recyclerView.setAdapter(adapter = new PhotoRecordAdapter(this));
        presenter = new PhotoRecordP(this, this);
        map = new HashMap<>();
        map.put("userId", getIntent().getStringExtra("userId"));
        presenter.getphotoHistory(0, map);
    }

    @OnClick(R.id.iv_back)
    public void onClick() {
        finish();
    }
}
