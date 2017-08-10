package qtkj.com.qtoaandroid.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import qtkj.com.qtoaandroid.R;

/**
 * Created by Administrator on 2017/8/3 0003.
 */

public abstract class BaseActivity extends AppCompatActivity {
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

}
