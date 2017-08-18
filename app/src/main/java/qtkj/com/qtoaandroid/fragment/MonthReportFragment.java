package qtkj.com.qtoaandroid.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import qtkj.com.qtoaandroid.Contest;
import qtkj.com.qtoaandroid.R;
import qtkj.com.qtoaandroid.model.MonthReport;
import qtkj.com.qtoaandroid.utils.DateUtil;
import qtkj.com.qtoaandroid.view.fragment.MonthReportP;
import qtkj.com.qtoaandroid.viewbar.RoundProgressBar;

/**
 * Created by Administrator on 2017/8/5 0005.
 */

public class MonthReportFragment extends BaseFragmengt<MonthReportP> implements OnDateSetListener {
    @BindView(R.id.lv)
    ListView lv;
    BaseAdapter adapter;
    TimePickerDialog mDialogYearMonth;
    @BindView(R.id.tv_date)
    TextView tvDate;
    private int progress = 0;
    private boolean thread = true;
    HeadViewHolder headViewHolder;
    Map<String, String> map = new HashMap<>();
    public boolean first = false;

    @Override
    protected int Rlayout() {
        return R.layout.fragment_month_report;
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
        final MonthReport monthReport = (MonthReport) data;
        headViewHolder.tvTotalNumber.setText("/" + monthReport.getTotalSignCount());
        headViewHolder.tvActualNumber.setText(monthReport.getFactSignCount() + "");
        headViewHolder.tvShould.setText("应到" +  monthReport.getTotalSignCount()+"人");
        headViewHolder.tvRealized.setText("实到" + monthReport.getFactSignCount()+"人");
        headViewHolder.tvLate.setText(monthReport.getTop().get(0).getLate() + "");
        headViewHolder.tvEarly.setText(monthReport.getTop().get(0).getEarly() + "");
        headViewHolder.tvForget.setText(monthReport.getTop().get(0).getForget() + "");
        headViewHolder.tvAbsenteeism.setText(monthReport.getTop().get(0).getAbsent() + "");
        headViewHolder.rpb.setMax(monthReport.getTotalSignCount());
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (progress < monthReport.getFactSignCount() && thread) {
                    progress += 1;
                    headViewHolder.rpb.setProgress(progress);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //此时已在主线程中，可以更新UI了
                            headViewHolder.tvActualNumber.setText(progress + "");
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
        lv.setAdapter(adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return monthReport.getBottom().size();
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
                    view = LayoutInflater.from(getActivity()).inflate(R.layout.item_month_report_subitem,
                            null);
                    //新建一个viewholder对象
                    holder = new ViewHolder(view);
                    view.setTag(holder);
                } else {
                    // 否则表示可以复用convertView
                    view = convertView;
                    holder = (ViewHolder) convertView.getTag();
                }
                MonthReport.BottomBean bean = monthReport.getBottom().get(i);
                if (bean != null) {
                    holder.tvName.setText(bean.getUserName());
                    holder.tvKind.setText(bean.getPostName());
                    holder.tvKindNumber.setText(bean.getPostName() + bean.getUserId());
                    holder.tvDaysNumber.setText(bean.getUserFact() + "");
                    holder.tvLate.setText(bean.getUserLate() + "");
                    holder.tvEarly.setText(bean.getUserEarly() + "");
                    holder.tvForget.setText(bean.getUserForget() + "");
                    holder.tvAbsenteeism.setText(bean.getUserAbsent() + "");
                    Glide.with(getActivity()).load(Contest.baseurl + bean.getImg()).error(R.mipmap.ic_photo).into(holder.ivPhoto);
                }

                return view;
            }

        });
    }

    @Override
    protected void init() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_month_repord_head, null);
        headViewHolder = new HeadViewHolder(view);
        lv.addHeaderView(view);
        mDialogYearMonth = new TimePickerDialog.Builder()
                .setType(Type.YEAR_MONTH)
                .setThemeColor(getResources().getColor(R.color.colorPrimary))
                .setWheelItemTextNormalColor(getResources().getColor(R.color.colorPrimary))
                .setWheelItemTextSize(16)
                .setMinMillseconds(System.currentTimeMillis() - (100L * 365 * 1000 * 60 * 60 * 24L))
                .setMaxMillseconds(System.currentTimeMillis() + (100L * 365 * 1000 * 60 * 60 * 24L))
                .setCallBack(this)
                .setTitleStringId("")
                .build();
        presenter = new MonthReportP(this, getActivity());

    }

    @Override
    public void lazyInit() {
        super.lazyInit();
        if (!first) {
            map.put("time", DateUtil.DateToString(new Date(), "yyyy-MM"));
            presenter.getsignMonth(0, map);

        }
    }

    @OnClick(R.id.tv_date)
    public void onClick() {
        mDialogYearMonth.show(getActivity().getSupportFragmentManager(), "year_month");
    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        Date d = new Date(millseconds);
        tvDate.setText(DateUtil.DateToString(d, "yyyy年MM月"));
        map.put("time", DateUtil.DateToString(d, "yyyy-MM"));
        presenter.getsignMonth(0, map);
    }

    static class ViewHolder {
        @BindView(R.id.iv_photo)
        ImageView ivPhoto;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_kind)
        TextView tvKind;
        @BindView(R.id.tv_kind_number)
        TextView tvKindNumber;
        @BindView(R.id.tv_days_number)
        TextView tvDaysNumber;
        @BindView(R.id.tv_late)
        TextView tvLate;
        @BindView(R.id.tv_early)
        TextView tvEarly;
        @BindView(R.id.tv_forget)
        TextView tvForget;
        @BindView(R.id.tv_absenteeism)
        TextView tvAbsenteeism;


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
        HeadViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        thread = false;

    }
}
