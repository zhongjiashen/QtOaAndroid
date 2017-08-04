package qtkj.com.qtoaandroid.fragment;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import qtkj.com.qtoaandroid.R;
import qtkj.com.qtoaandroid.adapter.recycleview.BaseRecycleViewAdapter;
import qtkj.com.qtoaandroid.adapter.recycleview.NowLocationAdapter;
import qtkj.com.qtoaandroid.adapter.recycleview.PhotoRecordAdapter;

/**
 * Created by Administrator on 2017/8/3 0003.
 * 实时位置
 */

public class NowLocationFragment extends BaseFragmengt{
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.SwipeRefreshLayout)
    android.support.v4.widget.SwipeRefreshLayout swipeRefreshLayout;

    BaseRecycleViewAdapter adapter;
    private Handler handler = new Handler();
    @Override
    protected int Rlayout() {
        return R.layout.fragment_now_location;
    }

    @Override
    protected void init() {
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter=new NowLocationAdapter(getActivity()));
    }
}
