package qtkj.com.qtoaandroid;

import android.os.Environment;

import java.io.File;

/**
 * Created by Administrator on 2017/8/12 0012.
 */

public class Contest {
//    public static String baseurl="http://192.168.1.158:8080/";
    public static String baseurl="http://longzh.qtren.cn/";
    // apk存储路径
    public static final String SDCARD_PATH = Environment
            .getExternalStorageDirectory().getAbsolutePath()
            + File.separatorChar ;
    public static final String PHOTO_PATH = SDCARD_PATH + "wtphoto" + File.separatorChar;// 拍照,调用相册,下载的缩略图
    public static final String NEW_APK = SDCARD_PATH + "wt" + File.separatorChar;//apk下载地址
}
