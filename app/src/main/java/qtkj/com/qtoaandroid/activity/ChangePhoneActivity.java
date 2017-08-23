package qtkj.com.qtoaandroid.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import qtkj.com.qtoaandroid.MyApplication;
import qtkj.com.qtoaandroid.R;
import qtkj.com.qtoaandroid.model.Login;
import qtkj.com.qtoaandroid.utils.Tool;
import qtkj.com.qtoaandroid.view.ChangePassOrPhoneP;

/**
 * Created by Administrator on 2017/8/3 0003.
 * 修改手机号
 */

public class ChangePhoneActivity extends BaseActivity<ChangePassOrPhoneP> {
    @BindView(R.id.et_account)
    TextView etAccount;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_send_code)
    TextView tvSendCode;

    @Override
    protected int layout() {
        return R.layout.avtivity_change_phone;
    }

    @Override
    protected void Initialize() {
        presenter = new ChangePassOrPhoneP(this, this);
        Login login = MyApplication.login;
        if (login != null) {
            etAccount.setText(login.getUserPhone());
        }
    }

    @OnClick({R.id.iv_back, R.id.tv_send_code, R.id.bt_ok})
    public void onClick(View view) {
        Map map;

        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_send_code:
                map = new HashMap();
                map.put("mobile",etAccount.getText().toString());
                map.put("codeType","3");
                presenter.getCode(1,map);
                break;
            case R.id.bt_ok:
                String code = etCode.getText().toString();
                if (code.equals("")) {
                    showShortToast("请输入验证码！");
                    return;
                }
                map = new HashMap();
                map.put("mobile",etAccount.getText().toString());
                map.put("codeType","3");
                map.put("authCode",code);
                presenter.checkPhoneCode(0,map);
                break;
        }
    }
    @Override
    public void returnData(int requestCode, Object data) {
        switch (requestCode) {
            case 0:
                startActivity(new Intent(this, OkChangePhoneActivity.class));
                break;
            case 1:
                Tool.ButtonCutDown(120, tvSendCode);
                break;
        }

    }
}
