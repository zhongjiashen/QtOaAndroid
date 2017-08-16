package qtkj.com.qtoaandroid.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import java.util.Map;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import qtkj.com.qtoaandroid.view.BaseView;

/**
 * Created by 1363655717 on 2017-06-01.
 */

public abstract class BaseFragmengt<T> extends Fragment implements BaseView {
    protected T presenter;
    Unbinder unbinder;
    protected Map<String, String> map;

    /**
     * 显示显示吐司
     *
     * @param text 吐司显示文本
     */
    @Override
    public void showShortToast(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 网路请求返回数据
     *
     * @param requestCode 请求码
     * @param data        数据
     */
    @Override
    public void returnData(int requestCode, Object data) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(Rlayout(), container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    /**
     * 加载布局
     *
     * @return 返回布局
     */
    protected abstract int Rlayout();

    /**
     * 初始化方法
     */
    protected abstract void init();

    public void lazyInit() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
