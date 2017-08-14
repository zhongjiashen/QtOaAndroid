package qtkj.com.qtoaandroid.model;

/**
 * Created by Administrator on 2017/8/11 0011.
 */

public class Login {

    /**
     * pm_start_time : 14:00:00
     * is_sign : 0
     * am_start_time : 08:30:00
     * am_end_time : 12:00:00
     * type : 0
     * post_id : 1
     * password : 123456
     * workStartTime : 08:30
     * pm_end_time : 18:00:00
     * user_name : 王大
     * srate : 0
     * job_type : 0
     * user_phone : 18537312427
     * create_time : 1501578667000
     * user_id : 1
     * post_name : 固定岗
     * workEndTime : 18:00
     */

    private String pm_start_time;
    private int is_sign;
    private String am_start_time;
    private String am_end_time;
    private int type;//权限字段1管理员0普通员工
    private int post_id;
    private String img;
    private String password;
    private String workStartTime;
    private String pm_end_time;
    private String user_name;
    private int srate;
    private int job_type;//夜班或者白班
    private String user_phone;
    private long create_time;
    private int user_id;
    private String post_name;
    private String workEndTime;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPm_start_time() {
        return pm_start_time;
    }

    public void setPm_start_time(String pm_start_time) {
        this.pm_start_time = pm_start_time;
    }

    public int getIs_sign() {
        return is_sign;
    }

    public void setIs_sign(int is_sign) {
        this.is_sign = is_sign;
    }

    public String getAm_start_time() {
        return am_start_time;
    }

    public void setAm_start_time(String am_start_time) {
        this.am_start_time = am_start_time;
    }

    public String getAm_end_time() {
        return am_end_time;
    }

    public void setAm_end_time(String am_end_time) {
        this.am_end_time = am_end_time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getWorkStartTime() {
        return workStartTime;
    }

    public void setWorkStartTime(String workStartTime) {
        this.workStartTime = workStartTime;
    }

    public String getPm_end_time() {
        return pm_end_time;
    }

    public void setPm_end_time(String pm_end_time) {
        this.pm_end_time = pm_end_time;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getSrate() {
        return srate;
    }

    public void setSrate(int srate) {
        this.srate = srate;
    }

    public int getJob_type() {
        return job_type;
    }

    public void setJob_type(int job_type) {
        this.job_type = job_type;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getPost_name() {
        return post_name;
    }

    public void setPost_name(String post_name) {
        this.post_name = post_name;
    }

    public String getWorkEndTime() {
        return workEndTime;
    }

    public void setWorkEndTime(String workEndTime) {
        this.workEndTime = workEndTime;
    }
}
