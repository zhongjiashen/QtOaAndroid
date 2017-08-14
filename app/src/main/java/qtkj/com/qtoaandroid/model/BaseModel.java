package qtkj.com.qtoaandroid.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/8/11 0011.
 */

public class BaseModel<T> {
    @SerializedName("data")
    private T data;
    /**
     * msg : 您的手机号匹配失败，请更换手机号
     * status : 2
     */

    private String msg;
    private int status;

    public T getData() {
        return data;
    }

    public boolean success() {
        if(status==0)
            return true;
        else
            return false;

    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
