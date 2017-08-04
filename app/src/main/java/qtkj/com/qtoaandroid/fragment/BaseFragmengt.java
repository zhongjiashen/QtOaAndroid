package qtkj.com.qtoaandroid.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 1363655717 on 2017-06-01.
 */

public abstract class BaseFragmengt extends Fragment  {

    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( Rlayout(), container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    /**
     * 加载布局
     * @return 返回布局
     */
    protected abstract int Rlayout();

    /**
     * 初始化方法
     */
    protected abstract void init();



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
