package qtkj.com.qtoaandroid.activity;

import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import qtkj.com.qtoaandroid.R;
import qtkj.com.qtoaandroid.fragment.BaseMapFragment;
import qtkj.com.qtoaandroid.fragment.MapFragment;
import qtkj.com.qtoaandroid.model.SignRecordDeal;
import qtkj.com.qtoaandroid.utils.DateUtil;
import qtkj.com.qtoaandroid.utils.LogUtils;
import qtkj.com.qtoaandroid.view.SignRecordP;
import qtkj.com.qtoaandroid.viewbar.calenderview.CalendarView;

/**
 * Created by Administrator on 2017/8/5 0005.
 * 签到记录
 */

public class SignRecordActivity extends BaseActivity<SignRecordP> implements OnDateSetListener {

    TimePickerDialog mDialogYearMonth;
    @BindView(R.id.cb_month)
    TextView cbMonth;
    @BindView(R.id.calendar)
    CalendarView mCalendarView;

    Map<String, String> map;
    Map<String, SignRecordDeal> day_map = new HashMap<>();
    @BindView(R.id.ll_sign_out)
    LinearLayout llSignOut;
    private String userId = "";

    private static final LatLng GEO_SHANGHAI = new LatLng(31.227, 121.481);
    @BindView(R.id.ll_all)
    LinearLayout llAll;
    @BindView(R.id.tv_day_state)
    TextView tvDayState;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_sign_in_time)
    TextView tvSignInTime;
    @BindView(R.id.tv_sign_in_address)
    TextView tvSignInAddress;
    @BindView(R.id.tv_sign_out_address)
    TextView tvSignOutAddress;
    @BindView(R.id.tv_sign_out_time)
    TextView tvSignOutTime;
    @BindView(R.id.tv_am_time)
    TextView tvAmTime;
    @BindView(R.id.tv_pm_time)
    TextView tvPmTime;
    BaseMapFragment mapF1;
    MapFragment mapF2;
    @BindView(R.id.map1)
    FrameLayout map1;
    @BindView(R.id.map2)
    FrameLayout map2;
    FragmentManager manager;
    @Override
    protected int layout() {
        return R.layout.activity_sign_record;
    }

    @Override
    public void returnData(int requestCode, Object data) {
        super.returnData(requestCode, data);
        day_map = (Map<String, SignRecordDeal>) data;
        mCalendarView.invalidate();
        init(new Date());
    }

    @Override
    protected void Initialize() {
        cbMonth.setText(DateUtil.DateToString(new Date(), "yyyy年MM月"));
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
        mCalendarView.setOnSelectChangeListener(new CalendarView.OnSelectChangeListener() {
            @Override
            public void selectChange(CalendarView calendarView, Date date) {
                init(date);
            }
        });
        mapF1 = BaseMapFragment.newInstance();
        mapF1.setStype(1);
        mapF2 = MapFragment.newInstance();
        mapF2.setStype(1);
         manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.map1, mapF1, "map_fragment").commit();
        manager.beginTransaction().replace(R.id.map2, mapF2, "map_fragment").commit();
        presenter = new SignRecordP(this, this);
        map = new HashMap<>();
        userId = getIntent().getStringExtra("userId");
        map.put("userId", userId);
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM");
        map.put("time", sd.format(new Date()));
        presenter.getDate(0, map);

    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        Date d = new Date(millseconds);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
        cbMonth.setText(sdf.format(d));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        mCalendarView.setCalendar(calendar);
        map.put("time", DateUtil.DateToString(d, "yyyy-MM"));
        presenter.getDate(0, map);
    }

    @OnClick({R.id.iv_back, R.id.cb_month, R.id.map1, R.id.map2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.cb_month:
                mDialogYearMonth.show(getSupportFragmentManager(), "year_month");
                break;
        }
    }

    private void init(Date date) {
        String day = DateUtil.DateToString(date, "yyyy-MM-dd");
        SignRecordDeal signRecordDeal = day_map.get(DateUtil.DateToString(date, "dd"));
        if (signRecordDeal == null) {
            llAll.setVisibility(View.GONE);
        } else {
            llAll.setVisibility(View.VISIBLE);
            tvDayState.setText(signRecordDeal.getDay_state());
            tvDate.setText(signRecordDeal.getDate());
            tvSignInAddress.setText(signRecordDeal.getSign_in_address());
            tvSignInTime.setText(DateUtil.longDateToString(signRecordDeal.getSign_in_time(), "HH:mm"));
            if (signRecordDeal.getSign_out_address().equals("无位置信息")) {
                llSignOut.setVisibility(View.GONE);
            } else {
                tvSignOutAddress.setText(signRecordDeal.getSign_out_address());
                tvSignOutTime.setText(DateUtil.longDateToString(signRecordDeal.getSign_out_time(), "HH:mm"));
            }

            long pmStart = DateUtil.StringTolongDate(day + signRecordDeal.getPmStartTime(), "yyyy-MM-ddHH");
            switch (signRecordDeal.getJop_type()) {
                case 0:
                    long amEnd = DateUtil.StringTolongDate(day + signRecordDeal.getAmEndTime(), "yyyy-MM-ddHH");
                    LogUtils.d(amEnd + "");
                    //未签退，轨迹一直记录，直接查询到下午下班时间段的轨迹信息

                    //签到时间大于中午下班时间，上午有轨迹显示地图
                    if (amEnd > signRecordDeal.getSign_in_time()) {
                        Log.e("S", amEnd + "");
                        tvAmTime.setVisibility(View.VISIBLE);
                        map1.setVisibility(View.VISIBLE);
                        tvAmTime.setText(DateUtil.longDateToString(signRecordDeal.getSign_in_time(), "HH:mm") + " - " + signRecordDeal.getAmEndTime());
                        mapF1.start(signRecordDeal.getSign_in_time(), amEnd, userId);


                    } else {
                        tvAmTime.setVisibility(View.GONE);
                        map1.setVisibility(View.GONE);
                    }

                    if (pmStart < signRecordDeal.getSign_out_time()) {
                        tvPmTime.setVisibility(View.VISIBLE);
                        map2.setVisibility(View.VISIBLE);

                        tvPmTime.setText(signRecordDeal.getPmStartTime() + " - " + DateUtil.longDateToString(signRecordDeal.getSign_out_time(), "HH:mm"));
                        mapF2.start(pmStart, signRecordDeal.getSign_out_time(), userId);

                    } else {
                        tvPmTime.setVisibility(View.GONE);
                        map2.setVisibility(View.GONE);
                    }

                    break;
                case 1:
                    tvAmTime.setVisibility(View.VISIBLE);
                    map1.setVisibility(View.VISIBLE);

                    tvPmTime.setVisibility(View.GONE);
                    map2.setVisibility(View.GONE);
                    tvAmTime.setText(signRecordDeal.getSign_in_time() + " - " + signRecordDeal.getSign_out_time());
                    mapF1.start(signRecordDeal.getSign_in_time(), signRecordDeal.getSign_out_time(), userId);

                    break;
            }

        }
    }
}
