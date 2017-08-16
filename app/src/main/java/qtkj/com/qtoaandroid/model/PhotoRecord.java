package qtkj.com.qtoaandroid.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/8/15 0015.
 */

public class PhotoRecord {

    /**
     * position : 中国河南省郑州市金水区相济路
     * id : 4
     * user_name : 斧王
     * user_phone : 13325896541
     * photo_url : upload/201708/15/1502779182021.jpg
     * create_time : 1502779182000
     * user_id : 4
     */

    @SerializedName("position")
    private String position;
    @SerializedName("id")
    private int id;
    @SerializedName("user_name")
    private String userName;
    @SerializedName("user_phone")
    private String userPhone;
    @SerializedName("photo_url")
    private String photoUrl;
    @SerializedName("create_time")
    private long createTime;
    @SerializedName("user_id")
    private int userId;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
