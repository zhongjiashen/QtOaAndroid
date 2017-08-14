package qtkj.com.qtoaandroid.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import qtkj.com.qtoaandroid.MyApplication;
import qtkj.com.qtoaandroid.R;
import qtkj.com.qtoaandroid.utils.Tool;
import qtkj.com.qtoaandroid.view.OkChangePhoneP;
/**
 * Created by Administrator on 2017/8/3 0003.
 * 确认修改手机号
 */
public class OkChangePhoneActivity extends BaseActivity<OkChangePhoneP> {
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_send_code)
    TextView tvSendCode;
    @Override
    protected int layout() {
        return R.layout.avtivity_ok_change_phone;
    }
    @Override
    protected void Initialize() {
        presenter=new OkChangePhoneP(this,this);

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
                map=new HashMap();
                phone=etAccount.getText().toString();
                if(phone.equals("")) {
                    showShortToast("请输入手机号！");
                    return;
                }
                map.put("mobile",phone);
                map.put("codeType","3");
                presenter.getCode(1,map);
                break;
            case R.id.bt_ok:
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
                map.put("codeType","3");
                map.put("authCode",code);
                map.put("userId", MyApplication.login.getUser_id()+"");
                presenter.modifyPhone(0,map);
                break;
        }
    }
    @Override
    public void returnData(int requestCode, Object data) {
        switch (requestCode){
            case 0:
                showShortToast("手机号修改成功！");
                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);//清空所在栈所有activity
                startActivity(intent);
                break;
            case 1:
                Tool.ButtonCutDown(120,tvSendCode);
                break;
        }

    }
}
