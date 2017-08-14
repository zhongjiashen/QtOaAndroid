package qtkj.com.qtoaandroid.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import qtkj.com.qtoaandroid.R;
import qtkj.com.qtoaandroid.permission.PermissionsActivity;
import qtkj.com.qtoaandroid.permission.PermissionsChecker;
import qtkj.com.qtoaandroid.view.BasePressent;
import qtkj.com.qtoaandroid.view.BaseView;

/**
 * Created by Administrator on 2017/8/3 0003.
 */

public abstract class BaseActivity<T extends BasePressent> extends AppCompatActivity implements BaseView {
    protected T presenter;
    private static final int REQUEST_CODE = 0; // 请求码
    protected PermissionsChecker mPermissionsChecker; // 权限检测器
    protected String[] PERMISSIONS;
    @Override
    public void returnData(int requestCode,Object data) {

    }

    @Override
    public void showShortToast(String text) {
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        savedInstanceState(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(layout());
        ButterKnife.bind(this);
        Initialize();
    }
    protected void savedInstanceState(Bundle savedInstanceState){

    };
    /**
     * 指定加载布局
     * @return 返回布局
     */
    protected abstract int layout();

    /**
     * 初始化方法
     */
    protected abstract void Initialize();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(presenter!=null)
        presenter.mDesory();
    }
    protected void startPermissionsActivity() {
        PermissionsActivity.startActivityForResult(this, REQUEST_CODE, PERMISSIONS);
    }
}
