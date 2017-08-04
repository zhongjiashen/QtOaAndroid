package qtkj.com.qtoaandroid.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.OnClick;
import qtkj.com.qtoaandroid.R;

/**
 * Created by Administrator on 2017/8/3 0003.
 */

public class LoginActivity extends BaseActivity {
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_pass)
    EditText etPass;

    @Override
    protected int layout() {
        return R.layout.activity_login;
    }

    @Override
    protected void Initialize() {

    }

    @OnClick({R.id.button, R.id.tv_forget_pass, R.id.phone_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.tv_forget_pass:
                startActivity(new Intent(this,ForgetPassActivity.class));
                break;
            case R.id.phone_login:
                startActivity(new Intent(this,PhoneLoginActivity.class));
                finish();
                break;
        }
    }
}
