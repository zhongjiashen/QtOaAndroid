package qtkj.com.qtoaandroid.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import qtkj.com.qtoaandroid.R;
import qtkj.com.qtoaandroid.viewbar.RoundProgressBar;

/**
 * Created by Administrator on 2017/8/5 0005.
 */

public class MonthReportFragment extends BaseFragmengt {
    @BindView(R.id.lv)
    ListView lv;
    BaseAdapter adapter;
    private int progress = 0;
    @Override
    protected int Rlayout() {
        return R.layout.fragment_month_report;
    }

    @Override
    protected void init() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_month_repord_head, null);
        final HeadViewHolder headViewHolder = new  HeadViewHolder(view);
        headViewHolder.tvTotalNumber.setText("/40");
        headViewHolder.rpb.setMax(40);
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (progress < 37) {
                    progress += 1;
                    headViewHolder.rpb.setProgress(progress);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //此时已在主线程中，可以更新UI了
                            headViewHolder.tvActualNumber.setText(progress+"");
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

        lv.addHeaderView(view);
        lv.setAdapter(adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return 10;
            }

            @Override
            public Object getItem(int i) {
                return null;
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int i, View convertView, ViewGroup group) {
                View view;
                ViewHolder holder;
                // 判断convertView的状态，来达到复用效果
                if (null == convertView) {
                    // 如果convertView为空，则表示第一次显示该条目，需要创建一个view
                    view =LayoutInflater.from(getActivity()).inflate( R.layout.item_month_report_subitem,
                            null);
                    //新建一个viewholder对象
                    holder = new ViewHolder(view);
                    view.setTag(holder);
                } else {
                    // 否则表示可以复用convertView
                    view = convertView;
                }
                return view;
            }
        });


    }

    static class ViewHolder {

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
    static class HeadViewHolder {
        @BindView(R.id.rpb)
        RoundProgressBar rpb;
        @BindView(R.id.tv_actual_number)
        TextView tvActualNumber;
        @BindView(R.id.tv_total_number)
        TextView tvTotalNumber;
        HeadViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
