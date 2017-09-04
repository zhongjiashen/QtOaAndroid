package qtkj.com.qtoaandroid.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/8/12 0012.
 */

public class SignRecordNew {

    /**
     * day : 30
     * type : dayAndNight
     * info : {"nightOutPosition":"adsadasf","am_start_time":"08:30:00","pm_start_time":"15:00:00","nightOutState":2,"dayOutSignTime":1503763200000,"nightOutSignTime":1503923264000,"nightState":0,"night_start_time":"20:30:00","dayOutPosition":"","am_end_time":"12:00:00","nightSignTime":1503921991000,"night_end_time":"08:30:00","daySignTime":1503763200000,"nightPosition":"adsadasf","pm_end_time":"18:30:00","PostName":"混合岗","dayState":4,"userId":18,"userName":"任","userPhone":"18537312427","dayPosition":null,"dayOutState":4}
     */

    @SerializedName("day")
    private String day;
    @SerializedName("type")
    private String type;
    @SerializedName("info")
    private InfoBean info;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * nightOutPosition : adsadasf
         * am_start_time : 08:30:00
         * pm_start_time : 15:00:00
         * nightOutState : 2
         * dayOutSignTime : 1503763200000
         * nightOutSignTime : 1503923264000
         * nightState : 0
         * night_start_time : 20:30:00
         * dayOutPosition :
         * am_end_time : 12:00:00
         * nightSignTime : 1503921991000
         * night_end_time : 08:30:00
         * daySignTime : 1503763200000
         * nightPosition : adsadasf
         * pm_end_time : 18:30:00
         * PostName : 混合岗
         * dayState : 4
         * userId : 18
         * userName : 任
         * userPhone : 18537312427
         * dayPosition : null
         * dayOutState : 4
         */

        @SerializedName("nightOutPosition")
        private String nightOutPosition;
        @SerializedName("am_start_time")
        private String amStartTime;
        @SerializedName("pm_start_time")
        private String pmStartTime;
        @SerializedName("nightOutState")
        private int nightOutState;
        @SerializedName("dayOutSignTime")
        private long dayOutSignTime;
        @SerializedName("nightOutSignTime")
        private long nightOutSignTime;
        @SerializedName("nightState")
        private int nightState;
        @SerializedName("night_start_time")
        private String nightStartTime;
        @SerializedName("dayOutPosition")
        private String dayOutPosition;
        @SerializedName("am_end_time")
        private String amEndTime;
        @SerializedName("nightSignTime")
        private long nightSignTime;
        @SerializedName("night_end_time")
        private String nightEndTime;
        @SerializedName("daySignTime")
        private long daySignTime;
        @SerializedName("nightPosition")
        private String nightPosition;
        @SerializedName("pm_end_time")
        private String pmEndTime;
        @SerializedName("PostName")
        private String PostName;
        @SerializedName("dayState")
        private int dayState;
        @SerializedName("userId")
        private int userId;
        @SerializedName("userName")
        private String userName;
        @SerializedName("userPhone")
        private String userPhone;
        @SerializedName("dayPosition")
        private String dayPosition;
        @SerializedName("dayOutState")
        private int dayOutState;

        public String getNightOutPosition() {
            return nightOutPosition;
        }

        public void setNightOutPosition(String nightOutPosition) {
            this.nightOutPosition = nightOutPosition;
        }

        public String getAmStartTime() {
            return amStartTime;
        }

        public void setAmStartTime(String amStartTime) {
            this.amStartTime = amStartTime;
        }

        public String getPmStartTime() {
            return pmStartTime;
        }

        public void setPmStartTime(String pmStartTime) {
            this.pmStartTime = pmStartTime;
        }

        public int getNightOutState() {
            return nightOutState;
        }

        public void setNightOutState(int nightOutState) {
            this.nightOutState = nightOutState;
        }

        public long getDayOutSignTime() {
            return dayOutSignTime;
        }

        public void setDayOutSignTime(long dayOutSignTime) {
            this.dayOutSignTime = dayOutSignTime;
        }

        public long getNightOutSignTime() {
            return nightOutSignTime;
        }

        public void setNightOutSignTime(long nightOutSignTime) {
            this.nightOutSignTime = nightOutSignTime;
        }

        public int getNightState() {
            return nightState;
        }

        public void setNightState(int nightState) {
            this.nightState = nightState;
        }

        public String getNightStartTime() {
            return nightStartTime;
        }

        public void setNightStartTime(String nightStartTime) {
            this.nightStartTime = nightStartTime;
        }

        public String getDayOutPosition() {
            return dayOutPosition;
        }

        public void setDayOutPosition(String dayOutPosition) {
            this.dayOutPosition = dayOutPosition;
        }

        public String getAmEndTime() {
            return amEndTime;
        }

        public void setAmEndTime(String amEndTime) {
            this.amEndTime = amEndTime;
        }

        public long getNightSignTime() {
            return nightSignTime;
        }

        public void setNightSignTime(long nightSignTime) {
            this.nightSignTime = nightSignTime;
        }

        public String getNightEndTime() {
            return nightEndTime;
        }

        public void setNightEndTime(String nightEndTime) {
            this.nightEndTime = nightEndTime;
        }

        public long getDaySignTime() {
            return daySignTime;
        }

        public void setDaySignTime(long daySignTime) {
            this.daySignTime = daySignTime;
        }

        public String getNightPosition() {
            return nightPosition;
        }

        public void setNightPosition(String nightPosition) {
            this.nightPosition = nightPosition;
        }

        public String getPmEndTime() {
            return pmEndTime;
        }

        public void setPmEndTime(String pmEndTime) {
            this.pmEndTime = pmEndTime;
        }

        public String getPostName() {
            return PostName;
        }

        public void setPostName(String PostName) {
            this.PostName = PostName;
        }

        public int getDayState() {
            return dayState;
        }

        public void setDayState(int dayState) {
            this.dayState = dayState;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
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

        public String getDayPosition() {
            return dayPosition;
        }

        public void setDayPosition(String dayPosition) {
            this.dayPosition = dayPosition;
        }

        public int getDayOutState() {
            return dayOutState;
        }

        public void setDayOutState(int dayOutState) {
            this.dayOutState = dayOutState;
        }
    }
}
