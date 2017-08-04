package qtkj.com.qtoaandroid.view;

/**
 * Created by Administrator on 2017/8/3 0003.
 */

public interface BaseView<T> {
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
     * @param key
     */

    void returnData(int key, T data);
}
