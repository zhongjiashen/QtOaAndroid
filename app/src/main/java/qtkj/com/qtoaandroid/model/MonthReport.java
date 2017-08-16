package qtkj.com.qtoaandroid.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/8/16 0016.
 */

public class MonthReport {


    /**
     * totalSignCount : 880
     * bottom : [{"user_name":"李四","time":"201708","userFact":4,"userForget":1,"userEarly":1,"userId":1,"img":"upload/201708/14/1502682135138.jpg","userAbsent":1,"post_name":"固定岗","userLate":3},{"user_name":"王二","time":"201708","userFact":1,"userForget":0,"userEarly":0,"userId":2,"userAbsent":0,"post_name":"固定岗","userLate":1},null,{"user_name":"斧王","time":"201708","userFact":2,"userForget":1,"userEarly":0,"userId":4,"img":"upload/201708/14/1502701059864.jpg","userAbsent":1,"post_name":"固定岗","userLate":2},null,null,null,null,null,null,null,null]
     * factSignCount : 7
     * top : [{"late":4,"normal":5,"a":"2017-08","absent":2,"m":"08","forget":2,"early":1,"y":"2017"}]
     */

    @SerializedName("totalSignCount")
    private int totalSignCount;
    @SerializedName("factSignCount")
    private int factSignCount;
    @SerializedName("bottom")
    private List<BottomBean> bottom;
    @SerializedName("top")
    private List<TopBean> top;

    public int getTotalSignCount() {
        return totalSignCount;
    }

    public void setTotalSignCount(int totalSignCount) {
        this.totalSignCount = totalSignCount;
    }

    public int getFactSignCount() {
        return factSignCount;
    }

    public void setFactSignCount(int factSignCount) {
        this.factSignCount = factSignCount;
    }

    public List<BottomBean> getBottom() {
        return bottom;
    }

    public void setBottom(List<BottomBean> bottom) {
        this.bottom = bottom;
    }

    public List<TopBean> getTop() {
        return top;
    }

    public void setTop(List<TopBean> top) {
        this.top = top;
    }

    public static class BottomBean {
        /**
         * user_name : 李四
         * time : 201708
         * userFact : 4
         * userForget : 1
         * userEarly : 1
         * userId : 1
         * img : upload/201708/14/1502682135138.jpg
         * userAbsent : 1
         * post_name : 固定岗
         * userLate : 3
         */

        @SerializedName("user_name")
        private String userName;
        @SerializedName("time")
        private String time;
        @SerializedName("userFact")
        private int userFact;
        @SerializedName("userForget")
        private int userForget;
        @SerializedName("userEarly")
        private int userEarly;
        @SerializedName("userId")
        private int userId;
        @SerializedName("img")
        private String img;
        @SerializedName("userAbsent")
        private int userAbsent;
        @SerializedName("post_name")
        private String postName;
        @SerializedName("userLate")
        private int userLate;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getUserFact() {
            return userFact;
        }

        public void setUserFact(int userFact) {
            this.userFact = userFact;
        }

        public int getUserForget() {
            return userForget;
        }

        public void setUserForget(int userForget) {
            this.userForget = userForget;
        }

        public int getUserEarly() {
            return userEarly;
        }

        public void setUserEarly(int userEarly) {
            this.userEarly = userEarly;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getUserAbsent() {
            return userAbsent;
        }

        public void setUserAbsent(int userAbsent) {
            this.userAbsent = userAbsent;
        }

        public String getPostName() {
            return postName;
        }

        public void setPostName(String postName) {
            this.postName = postName;
        }

        public int getUserLate() {
            return userLate;
        }

        public void setUserLate(int userLate) {
            this.userLate = userLate;
        }
    }

    public static class TopBean {
        /**
         * late : 4
         * normal : 5
         * a : 2017-08
         * absent : 2
         * m : 08
         * forget : 2
         * early : 1
         * y : 2017
         */

        @SerializedName("late")
        private int late;
        @SerializedName("normal")
        private int normal;
        @SerializedName("a")
        private String a;
        @SerializedName("absent")
        private int absent;
        @SerializedName("m")
        private String m;
        @SerializedName("forget")
        private int forget;
        @SerializedName("early")
        private int early;
        @SerializedName("y")
        private String y;

        public int getLate() {
            return late;
        }

        public void setLate(int late) {
            this.late = late;
        }

        public int getNormal() {
            return normal;
        }

        public void setNormal(int normal) {
            this.normal = normal;
        }

        public String getA() {
            return a;
        }

        public void setA(String a) {
            this.a = a;
        }

        public int getAbsent() {
            return absent;
        }

        public void setAbsent(int absent) {
            this.absent = absent;
        }

        public String getM() {
            return m;
        }

        public void setM(String m) {
            this.m = m;
        }

        public int getForget() {
            return forget;
        }

        public void setForget(int forget) {
            this.forget = forget;
        }

        public int getEarly() {
            return early;
        }

        public void setEarly(int early) {
            this.early = early;
        }

        public String getY() {
            return y;
        }

        public void setY(String y) {
            this.y = y;
        }
    }
}
