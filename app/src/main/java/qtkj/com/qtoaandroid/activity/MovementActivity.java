package qtkj.com.qtoaandroid.activity;

import android.support.v4.app.FragmentManager;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import qtkj.com.qtoaandroid.Contest;
import qtkj.com.qtoaandroid.R;
import qtkj.com.qtoaandroid.fragment.BaseMapFragment;
import qtkj.com.qtoaandroid.model.Login;
import qtkj.com.qtoaandroid.utils.BitmapUtil;
import qtkj.com.qtoaandroid.utils.DateUtil;
import qtkj.com.qtoaandroid.view.AddressBookDetailP;
import qtkj.com.qtoaandroid.viewbar.CircleImageView;

/**
 * Created by Administrator on 2017/8/10 0010.
 */

public class MovementActivity extends BaseActivity<AddressBookDetailP> {
    BaseMapFragment map;
    @BindView(R.id.tv_nick)
    TextView tvNick;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.iv_photo)
    CircleImageView ivPhoto;
    private String userId;
    private long startTime;
    private long endTime;

    @Override
    protected int layout() {
        return R.layout.activity_movement;
    }

    @Override
    public void returnData(int requestCode, Object data) {
        Login login = (Login) data;
        Glide.with(this).load(Contest.baseurl + login.getImg()).error(R.mipmap.ic_photo).into(ivPhoto);
        tvNick.setText(login.getUser_name());
    }

    @Override
    protected void Initialize() {
        map = BaseMapFragment.newInstance();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().add(R.id.map, map, "map_fragment").commit();
        userId = getIntent().getStringExtra("entityName");
        startTime = getIntent().getLongExtra("startTime", 0);
        endTime = getIntent().getLongExtra("endTime", 0);
        tvTime.setText(DateUtil.longDateToString(startTime, "yyyy-MM-dd  HH:mm -") + DateUtil.longDateToString(endTime, "HH:mm"));
        presenter = new AddressBookDetailP(this, this);
        Map<String, String> map = new HashMap<>();
        map.put("userId", userId);
        presenter.getAddressBookDetail(0, map);
    }

    @Override
    protected void onStart() {
        super.onStart();
        map.start(startTime, endTime, userId);
    }

    @OnClick(R.id.iv_back)
    public void onClick() {
        finish();
    }

}
