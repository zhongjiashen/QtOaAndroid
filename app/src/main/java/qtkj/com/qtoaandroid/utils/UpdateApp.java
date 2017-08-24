package qtkj.com.qtoaandroid.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;

import qtkj.com.qtoaandroid.BuildConfig;
import qtkj.com.qtoaandroid.Contest;

/**
 * Author:    申中佳
 * Version    V1.0
 * Date:      2017/8/24 0024 上午 11:17
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2017/8/24 0024         申中佳               V1.0
 */
public class UpdateApp {
    private Activity mActivity;
    private File file;
    private ProgressDialog progressDialog;
    public UpdateApp(Activity activity) {
        mActivity=activity;
    }


    /**
     * 下载所需文件
     */
    public void downFile() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(mActivity);
        }
        progressDialog.setMax(100); // 进度最大值
        progressDialog
                .setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("更新进度"); // 设置标题
        progressDialog.setCancelable(false); // 进度条不能用回退按钮关闭
        progressDialog
                .incrementProgressBy(-progressDialog
                        .getProgress());
        progressDialog.show();
        DownloadUtil.get().download(Contest.baseurl+"APK/android.apk", File.separatorChar +"wt", new DownloadUtil.OnDownloadListener() {
            @Override
            public void onDownloadSuccess(File file) {
                progressDialog.dismiss();
                UpdateApp.this.file =file;
                startInstall();

            }
            @Override
            public void onDownloading(int progress) {
                progressDialog.setProgress(progress);
            }
            @Override
            public void onDownloadFailed() {
                progressDialog.dismiss();
                LogUtils.d( "下载失败");

            }
        });


    }

    /**
     * 执行安装
     */
    public void startInstall() {
        if (file != null) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            //判断是否是AndroidN以及更高的版本
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri contentUri = FileProvider.getUriForFile(mActivity, BuildConfig.APPLICATION_ID + ".fileProvider", file);
                intent.setDataAndType(contentUri, "application/vnd.android.package-archive");

            } else {
                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            mActivity.startActivity(intent);
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }
}
