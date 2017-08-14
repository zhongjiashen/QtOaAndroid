package qtkj.com.qtoaandroid.view;

import java.util.Objects;

/**
 * Created by Administrator on 2017/8/3 0003.
 */

public interface BaseView {
    /**
     * 显示显示吐司
     *
     * @param text 吐司显示文本
     */
    void showShortToast(String text);
    /**
     * 网路请求返回数据
     *
     * @param data 数据
     * @param requestCode 请求码

     */
    void returnData(int requestCode, Object data);
}
