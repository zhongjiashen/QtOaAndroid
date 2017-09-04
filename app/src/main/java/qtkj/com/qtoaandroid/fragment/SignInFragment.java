package qtkj.com.qtoaandroid.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import qtkj.com.qtoaandroid.MyApplication;
import qtkj.com.qtoaandroid.R;
import qtkj.com.qtoaandroid.activity.AttendanceManagementActivity;
import qtkj.com.qtoaandroid.activity.MainActivity;
import qtkj.com.qtoaandroid.activity.PhotoRecordActivity;
import qtkj.com.qtoaandroid.activity.SignActivity;

import qtkj.com.qtoaandroid.activity.SignRecordActivity;
import qtkj.com.qtoaandroid.activity.SignRecordNewActivity;
import qtkj.com.qtoaandroid.model.Login;
import qtkj.com.qtoaandroid.utils.Base64;
import qtkj.com.qtoaandroid.utils.LogUtils;


/**
 * Created by Administrator on 2017/8/3 0003.
 * 签到
 */

public class SignInFragment extends BaseFragmengt implements TakePhoto.TakeResultListener, InvokeListener {
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_woke_time)
    TextView tvWokeTime;
    @BindView(R.id.iv_sign_in)
    ImageView ivSignIn;
    Login mLogin;

    private InvokeParam invokeParam;
    private TakePhoto takePhoto;
    private BroadcastReceiver mTimeRefreshReceiver;

    @Override
    protected int Rlayout() {
        return R.layout.fragment_sign_in;
    }

    @Override
    protected void init() {
        mLogin = MyApplication.mApplication.getLogin();
        updateTime(new Date());
        if(mLogin!=null) {
            if(mLogin.getJobType()==0){

                tvWokeTime.setText("工作时间：" +mLogin.getAmStartTime().substring(0,5) + " — " +mLogin.getAmEndTime().substring(0,5)+ "   " + mLogin.getPmStartTime().substring(0,5)+ " — " +mLogin.getPmEndTime().substring(0,5));
            }else {
                tvWokeTime.setText("工作时间：" + mLogin.getWorkStartTime().substring(0,5) + " — " + mLogin.getWorkEndTime().substring(0,5));
            }
        }
        mTimeRefreshReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (Intent.ACTION_TIME_TICK.equals(intent.getAction())) {
                    updateTime(new Date());
                }
            }
        };
       getActivity().registerReceiver(mTimeRefreshReceiver, new IntentFilter(Intent.ACTION_TIME_TICK));
    }

    /**
     * Called when the fragment is no longer in use.  This is called
     * after {@link #onStop()} and before {@link #onDetach()}.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mTimeRefreshReceiver);
    }

    public void updateTime(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("MM月dd日 E HH:mm");
        tvDate.setText(formatter.format(date));
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        tvTime.setText(formater.format(date));
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(getActivity(), type, invokeParam, this);
    }

    /**
     * 获取TakePhoto实例
     *
     * @return
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }

    /**
     * Called when the fragment is visible to the user and actively running.
     * This is generally
     * Activity's lifecycle.
     */
    @Override
    public void onResume() {
        super.onResume();
        mLogin = MyApplication.mApplication.getLogin();
        MainActivity mainActivity = (MainActivity) getActivity();
            if (mLogin.getIsSign() == 1) {
                setSignType(1);
                mainActivity.startTrac();
                LogUtils.d("签到状态");
            }else {
                setSignType(0);
                mainActivity.stopTrac();
            }

    }

    public void setSignType(int i) {
        switch (i) {
            case 0:
                ivSignIn.setBackgroundResource(R.mipmap.ic_sign_in);
                break;
            case 1:
                ivSignIn.setBackgroundResource(R.mipmap.ic_sign_out);
                break;
        }

    }

    @OnClick({R.id.tv_sign_in_record, R.id.tv_photo_record, R.id.tv_attendance_management, R.id.iv_take_photto, R.id.iv_sign_in})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_sign_in_record:
                startActivity(new Intent(getActivity(), SignRecordNewActivity.class).putExtra("userId", mLogin.getUserId() + ""));
                break;
            case R.id.tv_photo_record:
                startActivity(new Intent(getActivity(), PhotoRecordActivity.class).putExtra("userId", mLogin.getUserId() + ""));
                break;
            case R.id.tv_attendance_management:

                startActivity(new Intent(getActivity(), AttendanceManagementActivity.class));
                break;
            case R.id.iv_take_photto:
                File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
                if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
                Uri imageUri = Uri.fromFile(file);
                CompressConfig config = new CompressConfig.Builder()
                        .setMaxSize(51200)
                        .setMaxPixel(800)
                        .enableReserveRaw(true)
                        .create();
                takePhoto.onEnableCompress(config, true);
                takePhoto.onPickFromCapture(imageUri);
//                takePhoto.onPickFromDocumentsWithCrop(imageUri,getCropOptions());
                break;
            case R.id.iv_sign_in:
                if (mLogin.getIsSign() == 1) {
                    startActivityForResult(new Intent(getActivity(), SignActivity.class).putExtra("type", 1), 1);
                } else {
                    startActivityForResult(new Intent(getActivity(), SignActivity.class).putExtra("type", 0), 0);
                }
                break;
        }
    }

    @Override
    public void takeSuccess(TResult result) {
        Toast.makeText(getActivity(), "成功", Toast.LENGTH_SHORT).show();
        MainActivity mainActivity = (MainActivity) getActivity();
        try {
            String imagefile = Base64.encodeBase64File(result.getImage().getCompressPath());
            mainActivity.setImgStr(imagefile);
            mainActivity.startBDLocation(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void takeFail(TResult result, String msg) {
        Toast.makeText(getActivity(), "失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void takeCancel() {
        Toast.makeText(getActivity(), "取消当前操作！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    private CropOptions getCropOptions() {
        int height = Integer.parseInt("400");
        int width = Integer.parseInt("400");
        boolean withWonCrop = true;

        CropOptions.Builder builder = new CropOptions.Builder();


//            builder.setAspectX(width).setAspectY(height);//宽/高

        builder.setOutputX(width).setOutputY(height);//宽*高

        builder.setWithOwnCrop(withWonCrop);
        return builder.create();
    }
}
