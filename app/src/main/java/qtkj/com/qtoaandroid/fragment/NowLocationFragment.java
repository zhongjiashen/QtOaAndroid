package qtkj.com.qtoaandroid.fragment;

import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import qtkj.com.qtoaandroid.MyApplication;
import qtkj.com.qtoaandroid.R;
import qtkj.com.qtoaandroid.adapter.recycleview.BaseRecycleViewAdapter;
import qtkj.com.qtoaandroid.adapter.recycleview.NowLocationAdapter;
import qtkj.com.qtoaandroid.model.AddressBook;
import qtkj.com.qtoaandroid.model.Login;
import qtkj.com.qtoaandroid.model.NowLocationF;
import qtkj.com.qtoaandroid.view.fragment.NowLocationP;

/**
 * Created by Administrator on 2017/8/3 0003.
 * 实时位置
 */

public class NowLocationFragment extends BaseFragmengt<NowLocationP> {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.SwipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    BaseRecycleViewAdapter adapter;
    @BindView(R.id.insufficient_permissions)
    ConstraintLayout insufficientPermissions;
    private Handler handler = new Handler();
    private List<NowLocationF> list;
    @Override
    protected int Rlayout() {
        return R.layout.fragment_now_location;
    }
    /**
     * 网路请求返回数据
     *
     * @param requestCode 请求码
     * @param data        数据
     */
    @Override
    public void returnData(int requestCode, Object data) {
        list= (List<NowLocationF>) data;
        if(list!=null){
            showShortToast("asdf"+list.size());
            adapter.setList(list);
        }

    }
    @Override
    protected void init() {
        Login login = MyApplication.login;
        if (login != null) {
            if(login.getType()==1){
                presenter=new NowLocationP(this,getActivity());
                insufficientPermissions.setVisibility(View.GONE);
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
                recyclerView.setAdapter(adapter = new NowLocationAdapter(getActivity()));
                map=new HashMap<>();
                presenter.getNowLocation(0,map);
            }
        }

    }
}
