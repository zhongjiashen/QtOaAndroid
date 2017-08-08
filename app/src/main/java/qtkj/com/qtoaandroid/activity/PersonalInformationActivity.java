package qtkj.com.qtoaandroid.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import qtkj.com.qtoaandroid.R;

/**
 * Created by Administrator on 2017/8/4 0004.
 * 个人信息界面
 */

public class PersonalInformationActivity extends BaseActivity implements TakePhoto.TakeResultListener, InvokeListener {
    @BindView(R.id.iv_head_portrait)
    ImageView ivHeadPortrait;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_jobs)
    TextView tvJobs;
    @BindView(R.id.tv_jobs_numbers)
    TextView tvJobsNumbers;
    @BindView(R.id.tv_working_time)
    TextView tvWorkingTime;
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;

    @Override
    protected int layout() {
        return R.layout.activity_personal_information;
    }

    @Override
    protected void Initialize() {

    }

    @Override
    protected void savedInstanceState(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.savedInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
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

    @Override
    public void takeSuccess(TResult result) {

    }

    @Override
    public void takeFail(TResult result, String msg) {

    }

    @Override
    public void takeCancel() {

    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    @OnClick({R.id.iv_back, R.id.iv_head_portrait, R.id.ll_head_portrait, R.id.ll_name})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_head_portrait:
                startActivity(new Intent(this, DragImageActivity.class));
                break;
            case R.id.ll_head_portrait:
                showDialog();
                break;
            case R.id.ll_name:
                startActivity(new Intent(this, UpdateNameActivity.class));
                break;
        }
    }

    private void showDialog() {
        View view = getLayoutInflater().inflate(R.layout.photo_choose_dialog, null);
        ViewHolder viewHolder = new ViewHolder(view);

        final Dialog dialog = new Dialog(this, R.style.transparentFrameWindowStyle);
        viewHolder.btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setContentView(view, new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = getWindowManager().getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = LayoutParams.MATCH_PARENT;
        wl.height = LayoutParams.WRAP_CONTENT;

        // 设置显示位置
        dialog.onWindowAttributesChanged(wl);
        // 设置点击外围解散
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    static class ViewHolder {
        @BindView(R.id.bt_gallery)
        Button btGallery;
        @BindView(R.id.bt_taking_pictures)
        Button btTakingPictures;
        @BindView(R.id.bt_cancel)
        Button btCancel;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
