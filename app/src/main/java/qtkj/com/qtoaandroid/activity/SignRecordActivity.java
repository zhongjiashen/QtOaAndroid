package qtkj.com.qtoaandroid.activity;

import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.SupportMapFragment;
import com.baidu.mapapi.model.LatLng;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import qtkj.com.qtoaandroid.R;
import qtkj.com.qtoaandroid.fragment.BaseMapFragment;
import qtkj.com.qtoaandroid.fragment.MapFragment;
import qtkj.com.qtoaandroid.utils.ViewUtil;
import qtkj.com.qtoaandroid.view.SignRecordP;
import qtkj.com.qtoaandroid.viewbar.calenderview.CalendarView;
import qtkj.com.qtoaandroid.viewbar.calenderview.DayManager;

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
    Set<Integer> normalDays = new HashSet<>();
    Set<Integer> latearrivalDays = new HashSet<>();
    Set<Integer> forgetclockDays = new HashSet<>();
    Set<Integer> absenteeismDays = new HashSet<>();

    private static final LatLng GEO_SHANGHAI = new LatLng(31.227, 121.481);
    @Override
    protected int layout() {
        return R.layout.activity_sign_record;
    }

    @Override
    protected void Initialize() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
        cbMonth.setText(sdf.format(new Date()));
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
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
                Toast.makeText(SignRecordActivity.this, sdf.format(date), Toast.LENGTH_SHORT).show();
            }
        });
        BaseMapFragment map1 = BaseMapFragment.newInstance();
       map1.setStype(1);
        MapFragment map2 = MapFragment.newInstance();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.map1, map1, "map_fragment").commit();
        manager.beginTransaction().replace(R.id.map2, map2, "map_fragment").commit();
        presenter=new SignRecordP(this,this);
        presenter.getDate(0,null);

    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        Date d = new Date(millseconds);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
        cbMonth.setText(sdf.format(d));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        normalDays.add(1);
        normalDays.add(2);
        normalDays.add(4);
        normalDays.add(9);
        latearrivalDays.add(3);
        forgetclockDays.add(7);
        absenteeismDays.add(8);
        DayManager.setNormalDays(normalDays);
        DayManager.setLatearrivalDays(latearrivalDays);
        DayManager.setForgetclockDays(forgetclockDays);
        DayManager.setAbsenteeismDays(absenteeismDays);
        mCalendarView.setCalendar(calendar);
    }

    @OnClick({R.id.iv_back, R.id.cb_month, R.id.map1, R.id.map2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                break;
            case R.id.cb_month:
                mDialogYearMonth.show(getSupportFragmentManager(), "year_month");
                break;
            case R.id.map1:
                ViewUtil.startActivity(this,MovementActivity.class);
                break;
            case R.id.map2:
                ViewUtil.startActivity(this,MovementActivity.class);
                break;
        }
    }
}
