package qtkj.com.qtoaandroid.fragment;

import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import qtkj.com.qtoaandroid.R;
import qtkj.com.qtoaandroid.adapter.expandablelistview.ExpandableListViewAdapter;
import qtkj.com.qtoaandroid.model.AddressBook;
import qtkj.com.qtoaandroid.view.fragment.AddressBookP;

/**
 * Created by Administrator on 2017/8/3 0003.
 * 通讯录
 */

public class AddressBookFragment extends BaseFragmengt<AddressBookP> {
    //没有权限时显示的页面
    @BindView(R.id.insufficient_permissions)
    ConstraintLayout insufficientPermissions;
    @BindView(R.id.elv)
    ExpandableListView elv;
    ExpandableListViewAdapter adapte;
    private List<AddressBook> list;
    @BindView(R.id.SwipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private Handler handler = new Handler();
    @Override
    protected int Rlayout() {
        return R.layout.fragment_address_book;
    }

    @Override
    protected void init() {
        presenter=new AddressBookP(this,getActivity());
        map=new HashMap<>();
        presenter.getaddressList(0,map);
        elv.setAdapter(adapte=new ExpandableListViewAdapter(getActivity()));
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getaddressList(0,map);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });
    }

    /**
     * 网路请求返回数据
     *
     * @param requestCode 请求码
     * @param data        数据
     */
    @Override
    public void returnData(int requestCode, Object data) {
        swipeRefreshLayout.setRefreshing(false);
        list= (List<AddressBook>) data;
        if(list==null||list.size()==0)
            insufficientPermissions.setVisibility(View.VISIBLE);
        else {
            adapte.setList(list);
        }

    }
}
