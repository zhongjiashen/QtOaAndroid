package qtkj.com.qtoaandroid.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import qtkj.com.qtoaandroid.Contest;
import qtkj.com.qtoaandroid.MyApplication;
import qtkj.com.qtoaandroid.R;
import qtkj.com.qtoaandroid.activity.ChangePassActivity;
import qtkj.com.qtoaandroid.activity.ChangePhoneActivity;
import qtkj.com.qtoaandroid.activity.LoginActivity;
import qtkj.com.qtoaandroid.activity.PersonalInformationActivity;
import qtkj.com.qtoaandroid.activity.QuitActivity;
import qtkj.com.qtoaandroid.model.Login;

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

    /**
     * Called when the fragment is visible to the user and actively running.
     * This is generally
     * Activity's lifecycle.
     */
    @Override
    public void onResume() {
        super.onResume();
        Login login= MyApplication.login;
        if(login!=null){
            tvNick.setText(login.getUser_name());
            tvKind.setText(login.getPost_name());
            tvKindNumber.setText(login.getPost_name()+login.getUser_id());
            Glide.with(this).load(Contest.baseurl+login.getImg()).error(R.mipmap.ic_photo).bitmapTransform(new CropCircleTransformation(getActivity())).into(ivPhoto);
        }
    }

    @OnClick({ R.id.tv_personal_information, R.id.tv_change_phone, R.id.tv_change_pass, R.id.tv_longzihu_office, R.id.tv_contact_us, R.id.tv_log_out})
    public void onClick(View view) {
        switch (view.getId()) {
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
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);//清空所在栈所有activity
                startActivity(intent);
                break;
        }
    }
}
