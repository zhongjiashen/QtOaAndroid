package qtkj.com.qtoaandroid.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import qtkj.com.qtoaandroid.MyApplication;
import qtkj.com.qtoaandroid.R;
import qtkj.com.qtoaandroid.utils.Tool;
import qtkj.com.qtoaandroid.view.OkChangePassP;

/**
 * Created by Administrator on 2017/8/3 0003.
 * 确认修改手机号
 */
public class OkChangePassActivity extends BaseActivity<OkChangePassP> {

    @BindView(R.id.et_old_pass)
    EditText etOldPass;
    @BindView(R.id.new_pass)
    EditText newPass;
    @BindView(R.id.et_ok_pass)
    EditText etOkPass;

    @Override
    protected int layout() {
        return R.layout.avtivity_ok_change_pass;
    }

    @Override
    protected void Initialize() {
        presenter = new OkChangePassP(this, this);

    }



    @Override
    public void returnData(int requestCode, Object data) {

        showShortToast("密码修改成功，请重新登录！");
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);//清空所在栈所有activity
        startActivity(intent);
    }

    @OnClick({R.id.iv_back, R.id.bt_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.bt_ok:
                Map map = new HashMap();

                String oldpass = etOldPass.getText().toString();
                String newpass = newPass.getText().toString();
                String okpass = etOkPass.getText().toString();
                if (oldpass.equals("")) {
                    showShortToast("请输入旧密码！");
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
                map.put("oldPwd",oldpass);
                map.put("mobile",MyApplication.login.getUserPhone());
                map.put("password",newpass);
                map.put("userId",MyApplication.login.getUserId()+"");
                presenter.modifPwd(0,map);
                break;
        }
    }
}
