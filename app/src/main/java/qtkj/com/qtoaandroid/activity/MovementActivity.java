package qtkj.com.qtoaandroid.activity;

import android.support.v4.app.FragmentManager;

import qtkj.com.qtoaandroid.R;
import qtkj.com.qtoaandroid.fragment.BaseMapFragment;
import qtkj.com.qtoaandroid.fragment.MapFragment;

/**
 * Created by Administrator on 2017/8/10 0010.
 */

public class MovementActivity extends BaseActivity{
    @Override
    protected int layout() {
        return R.layout.activity_movement;
    }

    @Override
    protected void Initialize() {
        BaseMapFragment map = BaseMapFragment.newInstance();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().add(R.id.map, map, "map_fragment").commit();
    }
}
