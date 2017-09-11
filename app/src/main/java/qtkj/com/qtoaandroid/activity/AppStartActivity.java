package qtkj.com.qtoaandroid.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import qtkj.com.qtoaandroid.R;
import qtkj.com.qtoaandroid.utils.LogUtils;
import qtkj.com.qtoaandroid.utils.SPUtils;
import qtkj.com.qtoaandroid.view.BaseView;
import qtkj.com.qtoaandroid.view.LoginPassP;


/**
 * Created by 1363655717 on 2017/3/23.
 */

public class AppStartActivity extends AppCompatActivity implements BaseView {
     View view;
    LoginPassP  presenter;
    /**
     * 显示显示吐司
     *
     * @param text 吐司显示文本
     */
    @Override
    public void showShortToast(String text) {

    }

    @Override
    public void returnData(int requestCode, Object data) {
        LogUtils.e("一道页33");
        //渐变展示启动屏
        AlphaAnimation aa = new AlphaAnimation(0.5f, 1.0f);
        aa.setDuration(3000);
        view.startAnimation(aa);
        aa.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation arg0) {
                redirectTo();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationStart(Animation animation) {
            }

        });

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            //设置虚拟按键颜色
            window.setNavigationBarColor(Color.BLACK);
        }
       view = View.inflate(this, R.layout.activity_start, null);
        setContentView(view);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        presenter=new LoginPassP(this,this);
        presenter.update(1);


    }



    /**
     * 跳转到...
     */
    private void redirectTo() {
        Intent intent;
        LogUtils.d("一道页2");
        if( SPUtils.contains(this,"phone")){
            intent = new Intent(this, MainActivity.class);
            intent.putExtra("stype",1);
            LogUtils.d("一道页");
        }else{
            intent = new Intent(this, LoginActivity.class);
            intent.putExtra("stype",1);
            LogUtils.d("denglu");
        }
        startActivity(intent);
        finish();

    }

}