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
import qtkj.com.qtoaandroid.R;
import qtkj.com.qtoaandroid.utils.Tool;
import qtkj.com.qtoaandroid.view.ForgetPassP;

/**
 * Created by Administrator on 2017/8/3 0003.
 * 忘记密码
 */

public class ForgetPassActivity extends BaseActivity<ForgetPassP> {
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.new_pass)
    EditText newPass;
    @BindView(R.id.et_ok_pass)
    EditText etOkPass;
    @BindView(R.id.tv_send_code)
    TextView tvSendCode;

    @Override
    protected int layout() {
        return R.layout.avtivity_forget_pass;
    }

    @Override
    protected void Initialize() {
        presenter = new ForgetPassP(this, this);

    }

    @OnClick({R.id.iv_back, R.id.tv_send_code, R.id.bt_ok})
    public void onClick(View view) {
        Map map;
        String phone;
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_send_code:
                map = new HashMap();
                phone = etAccount.getText().toString();
                if (phone.equals("")) {
                    showShortToast("请输入手机号！");
                    return;
                }
                map.put("mobile", phone);
                map.put("codeType", "2");
                presenter.getCode(1, map);
                break;
            case R.id.bt_ok:
                map = new HashMap();
                phone = etAccount.getText().toString();
                String code = etCode.getText().toString();
                String newpass = newPass.getText().toString();
                String okpass = etOkPass.getText().toString();
                if (phone.equals("")) {
                    showShortToast("请输入手机号！");
                    return;
                }
                if (code.equals("")) {
                    showShortToast("请输入验证码！");
                    return;
                }
                if (newpass.equals("") || okpass.equals("")) {
                    showShortToast("请输入密码！");
                    return;
                }
                if (newpass.length() < 6 || okpass.length() < 6) {
                    showShortToast("密码长度不够！");
                    return;
                }
                if (!newpass.equals(okpass)) {
                    showShortToast("两次密码输入不一致！");
                    return;
                }
                map.put("mobile", phone);
                map.put("codeType", "2");
                map.put("authCode",code);
                map.put("password",newpass);
                presenter.forgetPwd(0,map);
                break;
        }
    }

    @Override
    public void returnData(int requestCode, Object data) {
        switch (requestCode) {
            case 0:
                finish();
                break;
            case 1:
                Tool.ButtonCutDown(120, tvSendCode);
                break;
        }

    }
}
