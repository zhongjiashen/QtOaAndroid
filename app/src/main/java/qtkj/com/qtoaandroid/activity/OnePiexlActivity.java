package qtkj.com.qtoaandroid.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import qtkj.com.qtoaandroid.MyApplication;

/**
 * Author:    申中佳
 * Version    V1.0
 * Date:      2017/9/5 0005 上午 11:56
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2017/9/5 0005         申中佳               V1.0
 */
public class OnePiexlActivity extends Activity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //设置1像素
            Window window = getWindow();
            window.setGravity(Gravity.LEFT | Gravity.TOP);
            WindowManager.LayoutParams params = window.getAttributes();
            params.x = 0;
            params.y = 0;
            params.height = 1;
            params.width = 1;
            window.setAttributes(params);
            MyApplication.mApplication.setOnePiexlActivity(this);
        }

        @Override
        protected void onResume() {
            super.onResume();
        }

}


