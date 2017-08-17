package qtkj.com.qtoaandroid.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import qtkj.com.qtoaandroid.R;
import qtkj.com.qtoaandroid.utils.StatusBarUtil;
import qtkj.com.qtoaandroid.utils.Tool;
import qtkj.com.qtoaandroid.view.LoginPassP;
import qtkj.com.qtoaandroid.view.PhoneLoginPassP;

/**
 * Created by Administrator on 2017/8/3 0003.
 * 验证码登录
 */

public class PhoneLoginActivity extends BaseActivity<PhoneLoginPassP> {
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.tv_send_code)
    TextView tvSendCode;

    @Override
    protected int layout() {
        return R.layout.activity_login_phone;
    }

    @Override
    protected void Initialize() {
        StatusBarUtil.transparencyBar(this);
        StatusBarUtil.StatusBarLightMode(this);
        presenter = new PhoneLoginPassP(this, this);
    }

    @OnClick({R.id.tv_send_code, R.id.button, R.id.pass_login})
    public void onClick(View view) {
        Map map;
        String phone;
        switch (view.getId()) {
            case R.id.tv_send_code:
                map=new HashMap();
                phone=etAccount.getText().toString();
                if(phone.equals("")) {
                    showShortToast("请输入手机号！");
                    return;
                }
                map.put("mobile",phone);
                map.put("codeType","1");
                presenter.getCode(1,map);
                break;
            case R.id.button:
               phone=etAccount.getText().toString();
                String code=etCode.getText().toString();
                if(phone.equals("")) {
                    showShortToast("请输入手机号！");
                    return;
                }
                if(code.equals("")) {
                    showShortToast("请输入验证码！");
                    return;
                }
                map=new HashMap();
                map.put("mobile",phone);
                map.put("codeType","1");
                map.put("authCode",code);
                presenter.login(0,map);
                break;
            case R.id.pass_login:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
        }
    }
    @Override
    public void returnData(int requestCode, Object data) {
        switch (requestCode){
            case 0:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
            case 1:
                Tool.ButtonCutDown(120,tvSendCode);
                break;
        }

    }

}
