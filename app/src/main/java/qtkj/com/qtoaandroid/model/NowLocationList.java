package qtkj.com.qtoaandroid.model;

import com.baidu.mapapi.model.LatLng;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/8/22 0022.
 */

public class NowLocationList {
    /**
     * user_name : 李四
     * img : upload/201708/16/1502883653582.jpg
     * user_id : 1
     */

    @SerializedName("user_name")
    private String userName;
    @SerializedName("img")
    private String img;
    @SerializedName("user_id")
    private int userId;
    @SerializedName("pt")
    LatLng pt;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LatLng getPt() {
        return pt;
    }

    public void setPt(LatLng pt) {
        this.pt = pt;
    }
}
