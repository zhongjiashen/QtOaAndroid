package qtkj.com.qtoaandroid.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import qtkj.com.qtoaandroid.R;
import qtkj.com.qtoaandroid.adapter.expandablelistview.DailyExpandableListViewAdapter;
import qtkj.com.qtoaandroid.model.Daily;
import qtkj.com.qtoaandroid.utils.DateUtil;
import qtkj.com.qtoaandroid.view.fragment.DailyP;
import qtkj.com.qtoaandroid.viewbar.RoundProgressBar;

/**
 * Created by Administrator on 2017/8/5 0005.
 */

public class DailyFragment extends BaseFragmengt<DailyP> implements OnDateSetListener {
    @BindView(R.id.elv)
    ExpandableListView elv;
    DailyExpandableListViewAdapter adapte;
    @BindView(R.id.tv_date)
    TextView tvDate;



    private int progress = 0;
    private boolean thread = true;
    TimePickerDialog mDialogYearMonth;
    Map<String, String> map = new HashMap<>();
    ViewHolder viewHolder;
    public boolean first = false;

    @Override
    protected int Rlayout() {
        return R.layout.fragment_daily;
    }

    /**
     * 网路请求返回数据
     *
     * @param requestCode 请求码
     * @param data        数据
     */
    @Override
    public void returnData(int requestCode, Object data) {
        super.returnData(requestCode, data);
        first = true;
        final Daily daily = (Daily) data;
        viewHolder.tvTotalNumber.setText("/" + daily.getTop().getTotalCount());
        viewHolder.tvActualNumber.setText(daily.getTop().getSignCount() + "");
        viewHolder.tvShould.setText("应到" + daily.getTop().getTotalCount()+"人");
        viewHolder.tvRealized.setText("实到" + daily.getTop().getSignCount()+"人");
        viewHolder.tvLate.setText(daily.getTop().getLateCount() + "");
        viewHolder.tvEarly.setText(daily.getTop().getEarlyCount() + "");
        viewHolder.tvForget.setText(daily.getTop().getForgetCount() + "");
        viewHolder.tvAbsenteeism.setText(daily.getTop().getAbsentCount() + "");
        viewHolder.rpb.setMax(daily.getTop().getTotalCount());
        adapte.setList(daily.getBottom());
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (progress < daily.getTop().getSignCount() && thread) {
                    progress += 1;
                    viewHolder.rpb.setProgress(progress);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //此时已在主线程中，可以更新UI了
                            viewHolder.tvActualNumber.setText(progress + "");
                        }
                    });
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }

    @Override
    protected void init() {
        presenter = new DailyP(this, getActivity());
        mDialogYearMonth = new TimePickerDialog.Builder()
                .setType(Type.YEAR_MONTH_DAY)
                .setThemeColor(getResources().getColor(R.color.colorPrimary))
                .setWheelItemTextNormalColor(getResources().getColor(R.color.colorPrimary))
                .setWheelItemTextSize(16)
                .setMinMillseconds(System.currentTimeMillis() - (100L * 365 * 1000 * 60 * 60 * 24L))
                .setMaxMillseconds(System.currentTimeMillis() + (100L * 365 * 1000 * 60 * 60 * 24L))
                .setCallBack(this)
                .setTitleStringId("")
                .build();
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_daily_head, null);
        viewHolder = new ViewHolder(view);
        elv.addHeaderView(view);
        elv.setAdapter(adapte = new DailyExpandableListViewAdapter(getActivity()));
        lazyInit();

    }

    @OnClick(R.id.tv_date)
    public void onClick() {
        mDialogYearMonth.show(getActivity().getSupportFragmentManager(), "year_month_day");
    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        Date d = new Date(millseconds);
        tvDate.setText(DateUtil.DateToString(d, "yyyy年MM月dd日"));
        map.put("time", DateUtil.DateToString(d, "yyyy-MM-dd"));
        presenter.getsignDay(0, map);
    }

    @Override
    public void lazyInit() {
        super.lazyInit();
        if (!first) {

            map.put("time", DateUtil.DateToString(new Date(), "yyyy-MM-dd"));
            tvDate.setText(DateUtil.DateToString(new Date(), "yyyy年MM月dd日"));
            presenter.getsignDay(0, map);
        }
    }

    static class ViewHolder {
        @BindView(R.id.rpb)
        RoundProgressBar rpb;
        @BindView(R.id.tv_actual_number)
        TextView tvActualNumber;
        @BindView(R.id.tv_total_number)
        TextView tvTotalNumber;
        @BindView(R.id.tv_late)
        TextView tvLate;
        @BindView(R.id.tv_early)
        TextView tvEarly;
        @BindView(R.id.tv_forget)
        TextView tvForget;
        @BindView(R.id.tv_absenteeism)
        TextView tvAbsenteeism;
        @BindView(R.id.tv_should)
        TextView tvShould;
        @BindView(R.id.tv_realized)
        TextView tvRealized;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        thread = false;

    }
}
