package qtkj.com.qtoaandroid.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import qtkj.com.qtoaandroid.R;
import qtkj.com.qtoaandroid.adapter.expandablelistview.DailyExpandableListViewAdapter;
import qtkj.com.qtoaandroid.viewbar.RoundProgressBar;

/**
 * Created by Administrator on 2017/8/5 0005.
 */

public class DailyFragment extends BaseFragmengt {
    @BindView(R.id.elv)
    ExpandableListView elv;
    DailyExpandableListViewAdapter adapte;

    private int progress = 0;

    @Override
    protected int Rlayout() {
        return R.layout.fragment_daily;
    }

    @Override
    protected void init() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_daily_head, null);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.tvTotalNumber.setText("/40");
        viewHolder.rpb.setMax(40);
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (progress < 37) {
                    progress += 1;
                    viewHolder.rpb.setProgress(progress);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //此时已在主线程中，可以更新UI了
                            viewHolder.tvActualNumber.setText(progress+"");
                        }
                    });
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();

        elv.addHeaderView(view);
        elv.setAdapter(adapte = new DailyExpandableListViewAdapter(getActivity()));
    }

    static class ViewHolder {
        @BindView(R.id.rpb)
        RoundProgressBar rpb;
        @BindView(R.id.tv_actual_number)
        TextView tvActualNumber;
        @BindView(R.id.tv_total_number)
        TextView tvTotalNumber;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
