package qtkj.com.qtoaandroid.activity;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import qtkj.com.qtoaandroid.Contest;
import qtkj.com.qtoaandroid.R;
import qtkj.com.qtoaandroid.model.Login;
import qtkj.com.qtoaandroid.view.AddressBookDetailP;
import qtkj.com.qtoaandroid.viewbar.CircleImageView;

/**
 * Created by Administrator on 2017/8/14 0014.
 */

public class AddressBookDetailActivity extends BaseActivity<AddressBookDetailP> {
    @BindView(R.id.iv_photo)
    CircleImageView ivPhoto;
    @BindView(R.id.tv_nick)
    TextView tvNick;
    @BindView(R.id.tv_kind)
    TextView tvKind;
    @BindView(R.id.tv_kind_number)
    TextView tvKindNumber;
    @BindView(R.id.tv_worke_time)
    TextView tvWorkeTime;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    private Login login;
    /**
     * 指定加载布局
     *
     * @return 返回布局
     */
    @Override
    protected int layout() {
        return R.layout.activity_personal_center;
    }

    @Override
    public void returnData(int requestCode, Object data) {
        login = (Login) data;
        Glide.with(this).load(Contest.baseurl + login.getImg()).error(R.mipmap.ic_photo).into(ivPhoto);
        tvNick.setText(login.getUserName());
        tvKind.setText(login.getPostName());
        tvKindNumber.setText(login.getPostName() + login.getUserId());
        tvWorkeTime.setText("工作时间：" + login.getWorkStartTime() + " — " + login.getWorkEndTime());
        tvPhone.setText("联系方式：" + login.getUserPhone());
    }

    /**
     * 初始化方法
     */
    @Override
    protected void Initialize() {
        presenter = new AddressBookDetailP(this, this);
        Map<String, String> map = new HashMap<>();
        map.put("userId", getIntent().getStringExtra("userid"));
        presenter.getAddressBookDetail(0, map);

    }

    @OnClick({R.id.ll_call_phone, R.id.iv_back, R.id.tv_sign_in_record, R.id.tv_photo_record})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_call_phone:
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" +login.getUserPhone() );
                intent.setData(data);
                startActivity(intent);
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_sign_in_record:
                startActivity(new Intent(this,SignRecordActivity.class).putExtra("userId",getIntent().getStringExtra("userid")));
                break;
            case R.id.tv_photo_record:
                startActivity(new Intent(this,PhotoRecordActivity.class).putExtra("userId",getIntent().getStringExtra("userid")));
                break;
        }
    }
}
