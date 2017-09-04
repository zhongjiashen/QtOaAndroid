package qtkj.com.qtoaandroid.activity;

import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import qtkj.com.qtoaandroid.fragment.MapFragment;
import qtkj.com.qtoaandroid.model.SignRecordDealNew;
import qtkj.com.qtoaandroid.utils.BitmapUtil;
import qtkj.com.qtoaandroid.utils.DateUtil;
import qtkj.com.qtoaandroid.utils.LogUtils;
import qtkj.com.qtoaandroid.view.SignRecordNewP;
import qtkj.com.qtoaandroid.viewbar.calenderview.CalendarView;
import qtkj.com.qtoaandroid.viewbar.calenderview.DayManager;

/**
 * Created by Administrator on 2017/8/5 0005.
 * 签到记录
 */

public class SignRecordNewActivity extends BaseActivity<SignRecordNewP> implements OnDateSetListener {

    TimePickerDialog mDialogYearMonth;
    @BindView(R.id.cb_month)
    TextView cbMonth;
    @BindView(R.id.calendar)
    CalendarView mCalendarView;

    Map<String, String> map;
    Map<String, SignRecordDealNew> day_map = new HashMap<>();

    @BindView(R.id.tv_day_sign_in_time)
    TextView tvDaySignInTime;
    @BindView(R.id.tv_day_sign_in_state)
    TextView tvDaySignInState;
    @BindView(R.id.tv_day_sign_in_address)
    TextView tvDaySignInAddress;
    @BindView(R.id.rl_day_sign_in)
    RelativeLayout rlDaySignIn;
    @BindView(R.id.tv_day_sign_out_time)
    TextView tvDaySignOutTime;
    @BindView(R.id.tv_day_sign_out_state)
    TextView tvDaySignOutState;
    @BindView(R.id.tv_day_sign_out_address)
    TextView tvDaySignOutAddress;
    @BindView(R.id.rl_day_sign_out)
    RelativeLayout rlDaySignOut;
    @BindView(R.id.tv_am_time)
    TextView tvAmTime;
    @BindView(R.id.map1)
    FrameLayout map1;
    @BindView(R.id.tv_pm_time)
    TextView tvPmTime;
    @BindView(R.id.map2)
    FrameLayout map2;
    @BindView(R.id.ll_day)
    LinearLayout llDay;
    @BindView(R.id.tv_night_sign_in_time)
    TextView tvNightSignInTime;
    @BindView(R.id.tv_night_sign_in_state)
    TextView tvNightSignInState;
    @BindView(R.id.tv_night_sign_in_address)
    TextView tvNightSignInAddress;
    @BindView(R.id.tv_night_sign_out_time)
    TextView tvNightSignOutTime;
    @BindView(R.id.tv_night_sign_out_state)
    TextView tvNightSignOutState;
    @BindView(R.id.tv_night_sign_out_address)
    TextView tvNightSignOutAddress;
    @BindView(R.id.rl_night_sign_out)
    RelativeLayout rlNightSignOut;
    @BindView(R.id.tv_night_time)
    TextView tvNightTime;
    @BindView(R.id.map3)
    FrameLayout map3;
    @BindView(R.id.ll_night)
    LinearLayout llNight;
    @BindView(R.id.ll_all)
    LinearLayout llAll;
    @BindView(R.id.tv_day_date)
    TextView tvDayDate;
    @BindView(R.id.tv_night_date)
    TextView tvNightDate;
    @BindView(R.id.ll_day_title)
    LinearLayout llDayTitle;
    @BindView(R.id.ll_night_title)
    LinearLayout llNightTitle;
    private String userId = "";
    MapFragment mapF1;
    MapFragment mapF2;
    MapFragment mapF3;
    FragmentManager manager;
    Date lastdate;

    @Override
    protected int layout() {
        return R.layout.activity_sign_record_new;
    }

    @Override
    public void returnData(int requestCode, Object data) {
        super.returnData(requestCode, data);
        day_map = (Map<String, SignRecordDealNew>) data;
        if (map.get("time").equals(DateUtil.DateToString(new Date(), "yyyy-MM"))) {
            init(new Date());
            int d = Integer.parseInt(DateUtil.DateToString(new Date(), "dd"));
            DayManager.setSelect(d);
        } else {
            DayManager.setSelect(-1);

        }
        mCalendarView.invalidate();

    }

    @Override
    protected void Initialize() {
        LogUtils.d("Initialize()");
        BitmapUtil.init();
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
        mapF1 = MapFragment.newInstance();
        mapF1.setStype(1);
        mapF2 = MapFragment.newInstance();
        mapF2.setStype(1);
        mapF3 = MapFragment.newInstance();
        mapF3.setStype(1);
        manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.map1, mapF1, "map_fragment1").commit();
        manager.beginTransaction().replace(R.id.map2, mapF2, "map_fragment2").commit();
        manager.beginTransaction().replace(R.id.map3, mapF3, "map_fragment3").commit();
        presenter = new SignRecordNewP(this, this);
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
        llAll.setVisibility(View.GONE);
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
        if (lastdate == null || !DateUtil.DateToString(lastdate, "yyyy-MM-dd").equals(DateUtil.DateToString(date, "yyyy-MM-dd"))) {
            lastdate = date;
            SignRecordDealNew signRecordDeal = day_map.get(DateUtil.DateToString(date, "dd"));
            if (signRecordDeal == null) {
                llAll.setVisibility(View.GONE);
            } else {
                llAll.setVisibility(View.VISIBLE);
                switch (signRecordDeal.getType()) {
                    case 1://白班
                        llNight.setVisibility(View.GONE);
                        llNightTitle.setVisibility(View.GONE);
                        day(signRecordDeal);
                        break;
                    case 2://夜班
                        llDay.setVisibility(View.GONE);
                        llDayTitle.setVisibility(View.GONE);
                        night(signRecordDeal);
                        break;
                    case 3://白班和夜班
                        day(signRecordDeal);
                        night(signRecordDeal);
                        break;

                }
            }
        }
    }

    /**
     * 白班信息处理
     *
     * @param signRecordDeal
     */
    private void day(SignRecordDealNew signRecordDeal) {
        if (!signRecordDeal.getDay_sign_in_state().equals("缺勤")) {
            llDayTitle.setVisibility(View.VISIBLE);
            llDay.setVisibility(View.VISIBLE);
            tvDayDate.setText(DateUtil.DateToString(lastdate, "yyyy-MM-dd"));
            tvDaySignInTime.setText(DateUtil.longDateToString(signRecordDeal.getDay_sign_in_time(), "MM-dd HH:mm"));
            tvDaySignInAddress.setText(signRecordDeal.getDay_sign_in_address());
            tvDaySignInState.setText(signRecordDeal.getDay_sign_in_state());
            LogUtils.d(signRecordDeal.getDay_sign_in_state());
            if (signRecordDeal.getDay_sign_out_address().equals("无位置信息")) {
                rlDaySignOut.setVisibility(View.GONE);
            } else {
                rlDaySignOut.setVisibility(View.VISIBLE);
                tvDaySignOutTime.setText(DateUtil.longDateToString(signRecordDeal.getDay_sign_out_time(), "MM-dd HH:mm"));
                tvDaySignOutAddress.setText(signRecordDeal.getDay_sign_out_address());
                tvDaySignOutState.setText(signRecordDeal.getDay_sign_out_state());
            }
            long amEnd = DateUtil.StringTolongDate(signRecordDeal.getAmEndTime(), "yyyy-MM-dd HH:mm");
            long pmStart = DateUtil.StringTolongDate(signRecordDeal.getPmStartTime(), "yyyy-MM-dd HH:mm");
            //签到时间大于中午下班时间，上午有轨迹显示地图
            if (amEnd > signRecordDeal.getDay_sign_in_time()) {
                tvAmTime.setVisibility(View.VISIBLE);
                map1.setVisibility(View.VISIBLE);
                //当签退时间小于上午下班时间，已签退时间为准
                if (signRecordDeal.getDay_sign_out_time() < amEnd) {
                    tvAmTime.setText(DateUtil.longDateToString(signRecordDeal.getDay_sign_in_time(), "MM-dd HH:mm")
                            + " —— " + DateUtil.longDateToString(signRecordDeal.getDay_sign_out_time(), "MM-dd HH:mm"));
                    mapF1.start(signRecordDeal.getDay_sign_in_time(), signRecordDeal.getDay_sign_out_time(), userId);
                } else {
                    tvAmTime.setText(DateUtil.longDateToString(signRecordDeal.getDay_sign_in_time(), "MM-dd HH:mm")
                            + " —— " + DateUtil.longDateToString(amEnd, "MM-dd HH:mm"));
                    mapF1.start(signRecordDeal.getDay_sign_in_time(), amEnd, userId);
                }
            } else {
                tvAmTime.setVisibility(View.GONE);
                map1.setVisibility(View.GONE);
            }

            //判断签退时间是否在下午上班时间之前，如果在下午上班之前，则下午轨迹没有，否则下午轨迹有
            if (pmStart < signRecordDeal.getDay_sign_out_time()) {
                tvPmTime.setVisibility(View.VISIBLE);
                map2.setVisibility(View.VISIBLE);
                LogUtils.d("wdeaw");
                //判断签到时间是在下午上班之前还是之后，之前轨迹从下午上班时间开始统计，否则一签到时间开始统计
                if (pmStart > signRecordDeal.getDay_sign_in_time()) {
                    tvPmTime.setText(DateUtil.longDateToString(pmStart, "MM-dd HH:mm")
                            + " —— "+ DateUtil.longDateToString(signRecordDeal.getDay_sign_out_time(), "MM-dd HH:mm"));
                    mapF2.start(pmStart, signRecordDeal.getDay_sign_out_time(), userId);
                } else {
                    tvPmTime.setText(DateUtil.longDateToString(signRecordDeal.getDay_sign_in_time(), "MM-dd HH:mm")
                            + " —— " + DateUtil.longDateToString(signRecordDeal.getDay_sign_out_time(), "MM-dd HH:mm"));
                    mapF2.start(signRecordDeal.getDay_sign_in_time(), signRecordDeal.getDay_sign_out_time(), userId);
                }

            } else {
                tvPmTime.setVisibility(View.GONE);
                map2.setVisibility(View.GONE);
            }
        } else {
            llDayTitle.setVisibility(View.GONE);
            llDay.setVisibility(View.GONE);
        }
    }

    /**
     * 夜班信息处理
     *
     * @param signRecordDeal
     */
    private void night(SignRecordDealNew signRecordDeal) {
        if (!signRecordDeal.getNight_sign_in_state().equals("缺勤")) {
            llNightTitle.setVisibility(View.VISIBLE);
            llNight.setVisibility(View.VISIBLE);
            tvNightDate.setText(DateUtil.DateToString(lastdate, "yyyy-MM-dd") );
            tvNightSignInTime.setText(DateUtil.longDateToString(signRecordDeal.getNight_sign_in_time(), "MM-dd HH:mm"));
            tvNightSignInAddress.setText(signRecordDeal.getNight_sign_in_address());
            tvNightSignInState.setText(signRecordDeal.getNight_sign_in_state());
            LogUtils.d(signRecordDeal.getNight_sign_in_state());
            if (signRecordDeal.getNight_sign_out_address().equals("无位置信息")) {
                rlNightSignOut.setVisibility(View.GONE);
            } else {
                rlNightSignOut.setVisibility(View.VISIBLE);
                tvNightSignOutTime.setText(DateUtil.longDateToString(signRecordDeal.getNight_sign_out_time(), "MM-dd HH:mm"));
                tvNightSignOutAddress.setText(signRecordDeal.getNight_sign_out_address());
                tvNightSignOutState.setText(signRecordDeal.getNight_sign_out_state());

            }
            tvNightTime.setText(DateUtil.longDateToString(signRecordDeal.getNight_sign_in_time(), "MM-dd HH:mm")
                    + " —— "+ DateUtil.longDateToString(signRecordDeal.getNight_sign_out_time(), "MM-dd HH:mm"));
            mapF3.start(signRecordDeal.getNight_sign_in_time(), signRecordDeal.getNight_sign_out_time(), userId);
        } else {
            llNightTitle.setVisibility(View.GONE);
            llNight.setVisibility(View.GONE);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BitmapUtil.clear();
    }
}
