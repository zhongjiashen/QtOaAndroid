package qtkj.com.qtoaandroid.fragment;

import android.support.constraint.ConstraintLayout;
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

    }

    /**
     * 网路请求返回数据
     *
     * @param requestCode 请求码
     * @param data        数据
     */
    @Override
    public void returnData(int requestCode, Object data) {
        list= (List<AddressBook>) data;
        if(list==null||list.size()==0)
            insufficientPermissions.setVisibility(View.VISIBLE);
        else {
            adapte.setList(list);
        }

    }
}
