package qtkj.com.qtoaandroid.activity;

import android.support.annotation.IdRes;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioGroup;

import butterknife.BindView;
import butterknife.OnClick;
import qtkj.com.qtoaandroid.MyApplication;
import qtkj.com.qtoaandroid.R;
import qtkj.com.qtoaandroid.fragment.BaseFragmengt;
import qtkj.com.qtoaandroid.fragment.DailyFragment;
import qtkj.com.qtoaandroid.fragment.MonthReportFragment;
import qtkj.com.qtoaandroid.model.Login;

/**
 * Created by Administrator on 2017/8/4 0004.
 * 考勤管理
 */

public class AttendanceManagementActivity extends BaseActivity {
    @BindView(R.id.viewpage)
    ViewPager viewpage;
    @BindView(R.id.radiogroup)
    RadioGroup radiogroup;
    @BindView(R.id.insufficient_permissions)
    ConstraintLayout insufficientPermissions;
    private FragmentStatePagerAdapter adapter;
    private BaseFragmengt[] basefragment = new BaseFragmengt[2];

    @Override
    protected int layout() {
        return R.layout.activity_attendance_management;
    }

    @Override
    protected void Initialize() {
        Login login = MyApplication.mApplication.getLogin();
        if (login != null) {
            if (login.getType() == 1) {
                insufficientPermissions.setVisibility(View.GONE);
                basefragment[0] = new DailyFragment();
                basefragment[1] = new MonthReportFragment();
                adapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
                    @Override
                    public Fragment getItem(int position) {

                        return basefragment[position];
                    }

                    @Override
                    public int getCount() {
                        return 2;
                    }

                };
                viewpage.setAdapter(adapter);
                viewpage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        switch (position) {
                            case 0:
                                radiogroup.check(R.id.rb_day);
                                basefragment[0].lazyInit();
                                break;
                            case 1:
                                radiogroup.check(R.id.rb_month);
                                basefragment[1].lazyInit();
                                break;
                        }

                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
                radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, @IdRes int i) {
                        switch (i) {
                            case R.id.rb_day:
                                viewpage.setCurrentItem(0);
                                break;
                            case R.id.rb_month:
                                viewpage.setCurrentItem(1);
                                break;
                        }

                    }
                });
            }
        }

    }

    @OnClick(R.id.iv_back)
    public void onClick() {
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}
