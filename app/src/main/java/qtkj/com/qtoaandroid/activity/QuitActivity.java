package qtkj.com.qtoaandroid.activity;

import qtkj.com.qtoaandroid.R;

/**
 * Created by Administrator on 2017/8/17 0017.
 */

public class QuitActivity extends BaseActivity{
    /**
     * 指定加载布局
     *
     * @return 返回布局
     */
    @Override
    protected int layout() {
        return R.layout.activity_quit;
    }

    /**
     * 初始化方法
     */
    @Override
    protected void Initialize() {
        finish();
    }
}
