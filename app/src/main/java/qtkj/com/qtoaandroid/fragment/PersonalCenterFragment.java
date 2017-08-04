package qtkj.com.qtoaandroid.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import qtkj.com.qtoaandroid.R;
import qtkj.com.qtoaandroid.activity.ChangePassActivity;
import qtkj.com.qtoaandroid.activity.ChangePhoneActivity;
import qtkj.com.qtoaandroid.activity.PersonalInformationActivity;

/**
 * Created by Administrator on 2017/8/3 0003.
 * 个人中心
 */

public class PersonalCenterFragment extends BaseFragmengt {
    @BindView(R.id.iv_photo)
    ImageView ivPhoto;
    @BindView(R.id.tv_nick)
    TextView tvNick;
    @BindView(R.id.tv_kind)
    TextView tvKind;
    @BindView(R.id.tv_kind_number)
    TextView tvKindNumber;

    @Override
    protected int Rlayout() {
        return R.layout.fragment_personal_center;
    }

    @Override
    protected void init() {

    }

    @OnClick({R.id.iv_back, R.id.tv_personal_information, R.id.tv_change_phone, R.id.tv_change_pass, R.id.tv_longzihu_office, R.id.tv_contact_us, R.id.tv_log_out})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                break;
            case R.id.tv_personal_information:
                startActivity(new Intent(getActivity(), PersonalInformationActivity.class));
                break;
            case R.id.tv_change_phone:
                startActivity(new Intent(getActivity(), ChangePhoneActivity.class));
                break;
            case R.id.tv_change_pass:
                startActivity(new Intent(getActivity(), ChangePassActivity.class));
                break;
            case R.id.tv_longzihu_office:
                break;
            case R.id.tv_contact_us:
                break;
            case R.id.tv_log_out:
                break;
        }
    }
}
