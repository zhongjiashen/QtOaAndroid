package qtkj.com.qtoaandroid.model;

/**
 * Created by Administrator on 2017/8/15 0015.
 */

public class SignRecordDeal {
    private String day_state="";
    private String date;
    private String sign_in_address;
    private String sign_out_address;
    private long sign_in_time;
    private long sign_out_time;
    private String amEndTime;
    private String pmStartTime;
    private String pmEndTime;
    private int jop_type;

    public String getDay_state() {
        return day_state;
    }

    public void setDay_state(String day_state) {
        if(this.day_state.equals("")){
            this.day_state=day_state;
        }else {
            this.day_state = this.day_state +"、"+ day_state;
        }
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSign_in_address() {
        return sign_in_address;
    }

    public void setSign_in_address(String sign_in_address) {
        this.sign_in_address = sign_in_address;
    }

    public String getSign_out_address() {
        return sign_out_address;
    }

    public void setSign_out_address(String sign_out_address) {
        this.sign_out_address = sign_out_address;
    }

    public long getSign_in_time() {
        return sign_in_time;
    }

    public void setSign_in_time(long sign_in_time) {
        this.sign_in_time = sign_in_time;
    }

    public long getSign_out_time() {
        return sign_out_time;
    }

    public void setSign_out_time(long sign_out_time) {
        this.sign_out_time = sign_out_time;
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

    public int getJop_type() {
        return jop_type;
    }

    public void setJop_type(int jop_type) {
        this.jop_type = jop_type;
    }
}
