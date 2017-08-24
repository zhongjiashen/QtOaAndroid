package qtkj.com.qtoaandroid.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/8/12 0012.
 */

public class SignRecord {

    /**
     * pm_start_time : 14:00:00
     * am_start_time : 08:30:00
     * position : 中国河南省郑州市金水区相济路
     * outState : 4.0
     * am_end_time : 12:00:00
     * outSignTime : 1.502775963E12
     * state : 1.0
     * type : 0.0
     * post_id : 1.0
     * id : 69.0
     * pm_end_time : 18:00:00
     * user_name : 斧王
     * job_type : 0.0
     * user_phone : 13325896541
     * user_id : 4.0
     * day : 15
     * ym : 2017年08月
     * outPosition : 中国河南省郑州市金水区相济路
     * post_name : 固定岗
     * sign_time : 1.502767381E12
     */

    @SerializedName("pm_start_time")
    private String pmStartTime;
    @SerializedName("am_start_time")
    private String amStartTime;
    @SerializedName("position")
    private String position;
    @SerializedName("outState")
    private int outState;
    @SerializedName("am_end_time")
    private String amEndTime;
    @SerializedName("outSignTime")
    private long outSignTime;
    @SerializedName("state")
    private int state;//签到状态
    @SerializedName("type")
    private int type;
    @SerializedName("post_id")
    private int postId;
    @SerializedName("id")
    private int id;
    @SerializedName("pm_end_time")
    private String pmEndTime;
    @SerializedName("user_name")
    private String userName;
    @SerializedName("job_type")
    private int jobType;
    @SerializedName("user_phone")
    private String userPhone;
    @SerializedName("user_id")
    private double userId;
    @SerializedName("day")
    private int day;
    @SerializedName("ym")
    private String ym;
    @SerializedName("outPosition")
    private String outPosition="无位置信息";
    @SerializedName("post_name")
    private String postName;
    @SerializedName("sign_time")
    private long signTime;

    public String getPmStartTime() {
        return pmStartTime;
    }

    public void setPmStartTime(String pmStartTime) {
        this.pmStartTime = pmStartTime;
    }

    public String getAmStartTime() {
        return amStartTime;
    }

    public void setAmStartTime(String amStartTime) {
        this.amStartTime = amStartTime;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getOutState() {
        return outState;
    }

    public void setOutState(int outState) {
        this.outState = outState;
    }

    public String getAmEndTime() {
        return amEndTime;
    }

    public void setAmEndTime(String amEndTime) {
        this.amEndTime = amEndTime;
    }

    public long getOutSignTime() {
        return outSignTime;
    }

    public void setOutSignTime(long outSignTime) {
        this.outSignTime = outSignTime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPmEndTime() {
        return pmEndTime;
    }

    public void setPmEndTime(String pmEndTime) {
        this.pmEndTime = pmEndTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getJobType() {
        return jobType;
    }

    public void setJobType(int jobType) {
        this.jobType = jobType;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public double getUserId() {
        return userId;
    }

    public void setUserId(double userId) {
        this.userId = userId;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getYm() {
        return ym;
    }

    public void setYm(String ym) {
        this.ym = ym;
    }

    public String getOutPosition() {
        return outPosition;
    }

    public void setOutPosition(String outPosition) {
        this.outPosition = outPosition;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public long getSignTime() {
        return signTime;
    }

    public void setSignTime(long signTime) {
        this.signTime = signTime;
    }
}
