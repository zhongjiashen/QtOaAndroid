package qtkj.com.qtoaandroid.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
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

import butterknife.OnClick;
import qtkj.com.qtoaandroid.R;
import qtkj.com.qtoaandroid.activity.AttendanceManagementActivity;
import qtkj.com.qtoaandroid.activity.PhotoRecordActivity;
import qtkj.com.qtoaandroid.activity.SignOutActivity;
import qtkj.com.qtoaandroid.activity.SignRecordActivity;

/**
 * Created by Administrator on 2017/8/3 0003.
 * 签到
 */

public class SignInFragment extends BaseFragmengt implements TakePhoto.TakeResultListener,InvokeListener {
    private InvokeParam invokeParam;
    private TakePhoto takePhoto;
    @Override
    protected int Rlayout() {
        return R.layout.fragment_sign_in;
    }

    @Override
    protected void init() {

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
        PermissionManager.TPermissionType type=PermissionManager.onRequestPermissionsResult(requestCode,permissions,grantResults);
        PermissionManager.handlePermissionsResult(getActivity(),type,invokeParam,this);
    }
    /**
     *  获取TakePhoto实例
     * @return
     */
    public TakePhoto getTakePhoto(){
        if (takePhoto==null){
            takePhoto= (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this,this));
        }
        return takePhoto;
    }
    @OnClick({R.id.tv_sign_in_record, R.id.tv_photo_record, R.id.tv_attendance_management, R.id.iv_take_photto, R.id.iv_sign_in})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_sign_in_record:
                startActivity(new Intent(getActivity(), SignRecordActivity.class));
                break;
            case R.id.tv_photo_record:
                startActivity(new Intent(getActivity(), PhotoRecordActivity.class));
                break;
            case R.id.tv_attendance_management:
                startActivity(new Intent(getActivity(), AttendanceManagementActivity.class));
                break;
            case R.id.iv_take_photto:
                File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
                if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
                Uri  imageUri = Uri.fromFile(file);
                CompressConfig config = new CompressConfig.Builder()
                        .setMaxSize(51200)
                        .setMaxPixel(800)
                        .enableReserveRaw(true)
                        .create();
                takePhoto.onEnableCompress(config, true);
                takePhoto.onPickFromCaptureWithCrop(imageUri,getCropOptions());
                break;
            case R.id.iv_sign_in:
                startActivity(new Intent(getActivity(), SignOutActivity.class));
                break;
        }
    }

    @Override
    public void takeSuccess(TResult result) {
        Toast.makeText(getActivity(),"成功",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void takeFail(TResult result, String msg) {

    }

    @Override
    public void takeCancel() {

    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type=PermissionManager.checkPermission(TContextWrap.of(this),invokeParam.getMethod());
        if(PermissionManager.TPermissionType.WAIT.equals(type)){
            this.invokeParam=invokeParam;
        }
        return type;
    }
    private CropOptions getCropOptions(){
        int height= Integer.parseInt("50");
        int width= Integer.parseInt("50");
        boolean withWonCrop=true;

        CropOptions.Builder builder=new CropOptions.Builder();


//            builder.setAspectX(width).setAspectY(height);//宽/高

            builder.setOutputX(width).setOutputY(height);//宽*高

        builder.setWithOwnCrop(withWonCrop);
        return builder.create();
    }
}
