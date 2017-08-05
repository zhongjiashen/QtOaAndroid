package qtkj.com.qtoaandroid.fragment;

import android.view.LayoutInflater;
import android.widget.ExpandableListView;

import butterknife.BindView;
import qtkj.com.qtoaandroid.R;
import qtkj.com.qtoaandroid.adapter.expandablelistview.DailyExpandableListViewAdapter;
import qtkj.com.qtoaandroid.adapter.expandablelistview.ExpandableListViewAdapter;

/**
 * Created by Administrator on 2017/8/5 0005.
 */

public class DailyFragment extends BaseFragmengt {
    @BindView(R.id.elv)
    ExpandableListView elv;
    DailyExpandableListViewAdapter adapte;
    @Override
    protected int Rlayout() {
        return R.layout.fragment_daily;
    }

    @Override
    protected void init() {
        elv.addHeaderView( LayoutInflater.from(getActivity()).inflate(R.layout.item_daily_head, null) );
        elv.setAdapter(adapte=new DailyExpandableListViewAdapter(getActivity()));
    }
}
