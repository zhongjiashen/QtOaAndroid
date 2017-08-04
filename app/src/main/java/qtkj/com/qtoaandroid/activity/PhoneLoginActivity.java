package qtkj.com.qtoaandroid.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.OnClick;
import qtkj.com.qtoaandroid.R;

/**
 * Created by Administrator on 2017/8/3 0003.
 * 验证码登录
 */

public class PhoneLoginActivity extends BaseActivity {
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_code)
    EditText etCode;

    @Override
    protected int layout() {
        return R.layout.activity_login_phone;
    }

    @Override
    protected void Initialize() {

    }

    @OnClick({R.id.tv_send_code, R.id.button, R.id.pass_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_send_code:
                break;
            case R.id.button:
                break;
            case R.id.pass_login:
                startActivity(new Intent(this,LoginActivity.class));
                finish();
                break;
        }
    }
}
