package qtkj.com.qtoaandroid.activity;

import android.view.View;

import butterknife.OnClick;
import qtkj.com.qtoaandroid.R;

/**
 * Created by Administrator on 2017/8/3 0003.
 * 修改手机号
 */

public class ChangePhoneActivity extends BaseActivity{
    @Override
    protected int layout() {
        return R.layout.avtivity_change_phone;
    }

    @Override
    protected void Initialize() {

    }
    @OnClick({R.id.iv_back, R.id.tv_send_code, R.id.bt_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_send_code:
                break;
            case R.id.bt_ok:
                break;
        }
    }
}
