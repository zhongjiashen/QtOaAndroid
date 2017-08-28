package qtkj.com.qtoaandroid.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

/**
 * Author:    申中佳
 * Version    V1.0
 * Date:      2017/8/28 0028 上午 9:24
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2017/8/28 0028         申中佳               V1.0
 */
public class SharedPreferencesUtils {
    private static Context context;
    private static String name;
    private static SharedPreferences mSp;

    /**
     * @param context
     * @param name    SharedPreferences文件名
     */
    public SharedPreferencesUtils(Context context, String name) {
        SharedPreferencesUtils.context = context;
        SharedPreferencesUtils.name = name;
        mSp = context.getSharedPreferences(name, Context.MODE_PRIVATE);

    }

    /**
     * 根据key和预期的value类型获取value的值
     *
     * @param key
     * @param clazz
     * @return
     */
    public <T> T getValue(String key, Class<T> clazz) {
        if (context == null) {
            throw new RuntimeException("请先调用带有context，name参数的构造！");
        }
        return getValue(key, clazz, mSp);
    }

    /**
     * 针对复杂类型存储<对象>
     *
     * @param key
     * @param object
     */
    public static void setObject(String key, Object object) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        try {

            out = new ObjectOutputStream(baos);
            out.writeObject(object);
            String objectVal = new String(android.util.Base64.encode(baos.toByteArray(), android.util.Base64.DEFAULT));
            SharedPreferences.Editor editor = mSp.edit();
            editor.putString(key, objectVal);
            editor.commit();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T getObject(String key, Class<T> clazz) {
        if (mSp.contains(key)) {
            String objectVal = mSp.getString(key, null);
            byte[] buffer = android.util.Base64.decode(objectVal, android.util.Base64.DEFAULT);
            ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(bais);
                T t = (T) ois.readObject();
                return t;
            } catch (StreamCorruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bais != null) {
                        bais.close();
                    }
                    if (ois != null) {
                        ois.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 对于外部不可见的过渡方法
     *
     * @param key
     * @param clazz
     * @param sp
     * @return
     */
    @SuppressWarnings("unchecked")
    private <T> T getValue(String key, Class<T> clazz, SharedPreferences sp) {
        T t;
        try {

            t = clazz.newInstance();

            if (t instanceof Integer) {
                return (T) Integer.valueOf(sp.getInt(key, 0));
            } else if (t instanceof String) {
                return (T) sp.getString(key, "");
            } else if (t instanceof Boolean) {
                return (T) Boolean.valueOf(sp.getBoolean(key, false));
            } else if (t instanceof Long) {
                return (T) Long.valueOf(sp.getLong(key, 0L));
            } else if (t instanceof Float) {
                return (T) Float.valueOf(sp.getFloat(key, 0L));
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
            LogUtils.e( "类型输入错误或者复杂类型无法解析[" + e.getMessage() + "]");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            LogUtils.e(  "类型输入错误或者复杂类型无法解析[" + e.getMessage() + "]");
        }
        LogUtils.e(  "无法找到" + key + "对应的值");
        return null;
    }

    //删除
    public static void clearData() {
        mSp.edit().clear().commit();
    }

}

