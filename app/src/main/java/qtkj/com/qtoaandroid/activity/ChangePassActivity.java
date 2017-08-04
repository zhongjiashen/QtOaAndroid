package qtkj.com.qtoaandroid.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import qtkj.com.qtoaandroid.R;

/**
 * Created by Administrator on 2017/8/3 0003.
 * 修改密码
 */

public class ChangePassActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_account)
    TextView etAccount;
    @BindView(R.id.et_code)
    EditText etCode;

    @Override
    protected int layout() {
        return R.layout.avtivity_change_phone;
    }

    @Override
    protected void Initialize() {
        tvTitle.setText("修改密码");

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
