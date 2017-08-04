package qtkj.com.qtoaandroid.activity;

import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import qtkj.com.qtoaandroid.R;
import qtkj.com.qtoaandroid.adapter.fragment.FragmentTabAdapter;
import qtkj.com.qtoaandroid.fragment.AddressBookFragment;
import qtkj.com.qtoaandroid.fragment.BaseFragmengt;
import qtkj.com.qtoaandroid.fragment.NowLocationFragment;
import qtkj.com.qtoaandroid.fragment.PersonalCenterFragment;
import qtkj.com.qtoaandroid.fragment.SignInFragment;

/**
 * Created by Administrator on 2017/8/3 0003.
 */

public class MainActivity extends BaseActivity{
    @BindView(R.id.rg_bottom)
    RadioGroup rgBottom;
    public List<BaseFragmengt> fragments = new ArrayList<BaseFragmengt>();
    @Override
    protected int layout() {
        return R.layout.activity_main;
    }

    @Override
    protected void Initialize() {
        fragments.add(new SignInFragment());
        fragments.add(new NowLocationFragment());
        fragments.add(new AddressBookFragment());
        fragments.add(new PersonalCenterFragment());
        FragmentTabAdapter tabAdapter = new FragmentTabAdapter(this, fragments, R.id.fl_main, rgBottom);
    }
}
