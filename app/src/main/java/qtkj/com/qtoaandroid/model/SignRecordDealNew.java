package qtkj.com.qtoaandroid.model;

/**
 * Created by Administrator on 2017/8/15 0015.
 */

public class SignRecordDealNew {
    //当天打卡统计类型
    private int type;
    /*---------------打卡状态--------------------*/
    private String day_sign_in_state="";
    private String day_sign_out_state="";
    private String night_sign_in_state="";
    private String night_sign_out_state="";
    /*------------------------------------------*/

    /*---------------打卡地址--------------------*/
    private String day_sign_in_address;
    private String day_sign_out_address;
    private String night_sign_in_address;
    private String night_sign_out_address;
    /*------------------------------------------*/
    /*---------------打卡时间--------------------*/
    private long day_sign_in_time;
    private long day_sign_out_time;
    private long night_sign_in_time;
    private long night_sign_out_time;
    /*------------------------------------------*/
    /*--------------轨迹时间表-------------------*/
    private String amEndTime;
    private String pmStartTime;
    private String pmEndTime;
    private String nmEndTime;
    /*------------------------------------------*/
    private String date;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDay_sign_in_state() {
        return day_sign_in_state;
    }

    public void setDay_sign_in_state(String day_sign_in_state) {
        this.day_sign_in_state = day_sign_in_state;
    }

    public String getDay_sign_out_state() {
        return day_sign_out_state;
    }

    public void setDay_sign_out_state(String day_sign_out_state) {
        this.day_sign_out_state = day_sign_out_state;
    }

    public String getNight_sign_in_state() {
        return night_sign_in_state;
    }

    public void setNight_sign_in_state(String night_sign_in_state) {
        this.night_sign_in_state = night_sign_in_state;
    }

    public String getNight_sign_out_state() {
        return night_sign_out_state;
    }

    public void setNight_sign_out_state(String night_sign_out_state) {
        this.night_sign_out_state = night_sign_out_state;
    }

    public String getDay_sign_in_address() {
        return day_sign_in_address;
    }

    public void setDay_sign_in_address(String day_sign_in_address) {
        this.day_sign_in_address = day_sign_in_address;
    }

    public String getDay_sign_out_address() {
        return day_sign_out_address;
    }

    public void setDay_sign_out_address(String day_sign_out_address) {
        this.day_sign_out_address = day_sign_out_address;
    }

    public String getNight_sign_in_address() {
        return night_sign_in_address;
    }

    public void setNight_sign_in_address(String night_sign_in_address) {
        this.night_sign_in_address = night_sign_in_address;
    }

    public String getNight_sign_out_address() {
        return night_sign_out_address;
    }

    public void setNight_sign_out_address(String night_sign_out_address) {
        this.night_sign_out_address = night_sign_out_address;
    }

    public long getDay_sign_in_time() {
        return day_sign_in_time;
    }

    public void setDay_sign_in_time(long day_sign_in_time) {
        this.day_sign_in_time = day_sign_in_time;
    }

    public long getDay_sign_out_time() {
        return day_sign_out_time;
    }

    public void setDay_sign_out_time(long day_sign_out_time) {
        this.day_sign_out_time = day_sign_out_time;
    }

    public long getNight_sign_in_time() {
        return night_sign_in_time;
    }

    public void setNight_sign_in_time(long night_sign_in_time) {
        this.night_sign_in_time = night_sign_in_time;
    }

    public long getNight_sign_out_time() {
        return night_sign_out_time;
    }

    public void setNight_sign_out_time(long night_sign_out_time) {
        this.night_sign_out_time = night_sign_out_time;
    }

    public String getAmEndTime() {
        return amEndTime;
    }

    public void setAmEndTime(String amEndTime) {
        this.amEndTime = amEndTime;
    }

    public String getPmStartTime() {
        return pmStartTime;
    }

    public void setPmStartTime(String pmStartTime) {
        this.pmStartTime = pmStartTime;
    }

    public String getPmEndTime() {
        return pmEndTime;
    }

    public void setPmEndTime(String pmEndTime) {
        this.pmEndTime = pmEndTime;
    }

    public String getNmEndTime() {
        return nmEndTime;
    }

    public void setNmEndTime(String nmEndTime) {
        this.nmEndTime = nmEndTime;
    }
}
