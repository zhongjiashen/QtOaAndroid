package qtkj.com.qtoaandroid.fragment;

import android.support.constraint.ConstraintLayout;
import android.widget.ExpandableListView;

import butterknife.BindView;
import qtkj.com.qtoaandroid.R;
import qtkj.com.qtoaandroid.adapter.expandablelistview.ExpandableListViewAdapter;

/**
 * Created by Administrator on 2017/8/3 0003.
 * 通讯录
 */

public class AddressBookFragment extends BaseFragmengt {
    //没有权限时显示的页面
    @BindView(R.id.insufficient_permissions)
    ConstraintLayout insufficientPermissions;
    @BindView(R.id.elv)
    ExpandableListView elv;
    ExpandableListViewAdapter adapte;

    @Override
    protected int Rlayout() {
        return R.layout.fragment_address_book;
    }

    @Override
    protected void init() {

        elv.setAdapter(adapte=new ExpandableListViewAdapter(getActivity()));

    }


}
