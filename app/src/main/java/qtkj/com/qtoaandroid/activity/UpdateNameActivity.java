package qtkj.com.qtoaandroid.activity;

import android.view.View;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import qtkj.com.qtoaandroid.MyApplication;
import qtkj.com.qtoaandroid.R;
import qtkj.com.qtoaandroid.model.Login;
import qtkj.com.qtoaandroid.view.UpdadeNameP;

/**
 * Created by Administrator on 2017/8/8 0008.
 * 修改名字
 */

public class UpdateNameActivity extends BaseActivity<UpdadeNameP> {
    @BindView(R.id.et_name)
    EditText etName;
    Login login;

    @Override
    public void returnData(int requestCode, Object data) {
        showShortToast("修改名字成功！");
        login.setUserName(etName.getText().toString());
        MyApplication.mApplication.setLogin(login);
        finish();
    }

    @Override
    protected int layout() {
        return R.layout.activity_update_name;
    }

    @Override
    protected void Initialize() {
        presenter = new UpdadeNameP(this, this);
        login=MyApplication.mApplication.getLogin();

    }

    @OnClick({R.id.iv_back, R.id.tv_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_save:
                String name=etName.getText().toString();
                if(name.equals("")) {
                    showShortToast("请输入名字！");
                    return;
                }
                Map<String,String> map=new HashMap();
                map.put("userId",login.getUserId()+"");
                map.put("userName", name);
                presenter.update_name(0,map);
                break;
        }
    }
}
