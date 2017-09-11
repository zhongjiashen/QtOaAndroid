package qtkj.com.qtoaandroid.activity;

import android.Manifest;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import qtkj.com.qtoaandroid.R;
import qtkj.com.qtoaandroid.adapter.fragment.FragmentTabAdapter;
import qtkj.com.qtoaandroid.fragment.AddressBookFragment;
import qtkj.com.qtoaandroid.fragment.NowLocationFragment;
import qtkj.com.qtoaandroid.fragment.PersonalCenterFragment;
import qtkj.com.qtoaandroid.fragment.SignInFragment;
import qtkj.com.qtoaandroid.permission.PermissionsChecker;
import qtkj.com.qtoaandroid.utils.LogUtils;
import qtkj.com.qtoaandroid.utils.SPUtils;
import qtkj.com.qtoaandroid.utils.StatusBarUtil;
import qtkj.com.qtoaandroid.view.LoginPassP;

/**
 * Created by Administrator on 2017/8/3 0003.
 */

public class LoginActivity extends BaseActivity<LoginPassP> {
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
        LogUtils.d("你好LogUtils工具类！");
        StatusBarUtil.transparencyBar(this);
        StatusBarUtil.StatusBarLightMode(this);
        presenter = new LoginPassP(this, this);
        PERMISSIONS = new String[]{
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        mPermissionsChecker = new PermissionsChecker(this);
        switch (getIntent().getIntExtra("stype",0)){
            case 0:
                presenter.update(1);
                break;
            case 1:
                break;
        }
//        presenter.update(1);
    }

    @OnClick({R.id.button, R.id.tv_forget_pass, R.id.phone_login})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.button:
                Map map=new HashMap();
                String phone=etAccount.getText().toString();
                String pass=etPass.getText().toString();
                if(phone.equals("")) {
                    showShortToast("请输入账号！");
                    return;
                }
                if(pass.equals("")) {
                    showShortToast("请输入账号！");
                    return;
                }
                map.put("mobile", phone);
                map.put("password",pass);
                presenter.login(0,map);
//                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.tv_forget_pass:
                startActivity(new Intent(this, ForgetPassActivity.class));
                break;
            case R.id.phone_login:
                startActivity(new Intent(this, PhoneLoginActivity.class));
                finish();
                break;
        }
    }

    @Override
    public void returnData(int requestCode, Object data) {
        super.returnData(requestCode, data);
        switch (requestCode){
            case 0:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        // 缺少权限时, 进入权限配置页面
        if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
            startPermissionsActivity();
        }
    }
}
