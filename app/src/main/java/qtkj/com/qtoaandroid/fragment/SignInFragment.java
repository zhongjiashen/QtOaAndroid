package qtkj.com.qtoaandroid.fragment;

import android.content.Intent;
import android.view.View;

import butterknife.OnClick;
import qtkj.com.qtoaandroid.R;
import qtkj.com.qtoaandroid.activity.PhotoRecordActivity;

/**
 * Created by Administrator on 2017/8/3 0003.
 * 签到
 */

public class SignInFragment extends BaseFragmengt {
    @Override
    protected int Rlayout() {
        return R.layout.fragment_sign_in;
    }

    @Override
    protected void init() {

    }

    @OnClick({R.id.tv_sign_in_record, R.id.tv_photo_record, R.id.tv_attendance_management, R.id.iv_take_photto, R.id.iv_sign_in})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_sign_in_record:
                break;
            case R.id.tv_photo_record:
                startActivity(new Intent(getActivity(), PhotoRecordActivity.class));
                break;
            case R.id.tv_attendance_management:
                break;
            case R.id.iv_take_photto:
                break;
            case R.id.iv_sign_in:
                break;
        }
    }
}
