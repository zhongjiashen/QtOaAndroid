package qtkj.com.qtoaandroid.activity;

import android.view.View;
import android.widget.CheckBox;

import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;

import butterknife.BindView;
import butterknife.OnClick;
import qtkj.com.qtoaandroid.R;
import qtkj.com.qtoaandroid.viewbar.MyTimePickerDialog;

/**
 * Created by Administrator on 2017/8/5 0005.
 * 签到记录
 */

public class SignRecordActivity extends BaseActivity implements OnDateSetListener {

    TimePickerDialog mDialogYearMonth;
    @Override
    protected int layout() {
        return R.layout.activity_sign_record;
    }

    @Override
    protected void Initialize() {
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


    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {

    }

    @OnClick({R.id.iv_back, R.id.cb_month})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                break;
            case R.id.cb_month:
                mDialogYearMonth.show(getSupportFragmentManager(), "year_month");
                break;
        }
    }
}
