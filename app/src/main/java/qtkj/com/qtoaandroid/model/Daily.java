package qtkj.com.qtoaandroid.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/8/16 0016.
 */

public class Daily {

    /**
     * bottom : [{"child":[{"user_name":"斧王","img":"upload/201708/14/1502701059864.jpg","user_id":4,"post_name":"固定岗","sign_time":1502856254000}],"count":1,"group":"迟到"},{"child":[],"count":0,"group":"早退"},{"child":[{"user_name":"斧王","img":"upload/201708/14/1502701059864.jpg","user_id":4,"post_name":"固定岗","sign_time":1502812800000}],"count":1,"group":"忘记打卡"},{"child":[],"count":0,"group":"缺勤"}]
     * top : {"totalCount":12,"forgetCount":1,"earlyCount":0,"lateCount":1,"absentCount":0,"signCount":0}
     */

    @SerializedName("top")
    private TopBean top;
    @SerializedName("bottom")
    private List<BottomBean> bottom;

    public TopBean getTop() {
        return top;
    }

    public void setTop(TopBean top) {
        this.top = top;
    }

    public List<BottomBean> getBottom() {
        return bottom;
    }

    public void setBottom(List<BottomBean> bottom) {
        this.bottom = bottom;
    }

    public static class TopBean {
        /**
         * totalCount : 12
         * forgetCount : 1
         * earlyCount : 0
         * lateCount : 1
         * absentCount : 0
         * signCount : 0
         */

        @SerializedName("totalCount")
        private int totalCount;
        @SerializedName("forgetCount")
        private int forgetCount;
        @SerializedName("earlyCount")
        private int earlyCount;
        @SerializedName("lateCount")
        private int lateCount;
        @SerializedName("absentCount")
        private int absentCount;
        @SerializedName("signCount")
        private int signCount;

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public int getForgetCount() {
            return forgetCount;
        }

        public void setForgetCount(int forgetCount) {
            this.forgetCount = forgetCount;
        }

        public int getEarlyCount() {
            return earlyCount;
        }

        public void setEarlyCount(int earlyCount) {
            this.earlyCount = earlyCount;
        }

        public int getLateCount() {
            return lateCount;
        }

        public void setLateCount(int lateCount) {
            this.lateCount = lateCount;
        }

        public int getAbsentCount() {
            return absentCount;
        }

        public void setAbsentCount(int absentCount) {
            this.absentCount = absentCount;
        }

        public int getSignCount() {
            return signCount;
        }

        public void setSignCount(int signCount) {
            this.signCount = signCount;
        }
    }

    public static class BottomBean {
        /**
         * child : [{"user_name":"斧王","img":"upload/201708/14/1502701059864.jpg","user_id":4,"post_name":"固定岗","sign_time":1502856254000}]
         * count : 1
         * group : 迟到
         */

        @SerializedName("count")
        private int count;
        @SerializedName("group")
        private String group;
        @SerializedName("child")
        private List<ChildBean> child;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public List<ChildBean> getChild() {
            return child;
        }

        public void setChild(List<ChildBean> child) {
            this.child = child;
        }

        public static class ChildBean {
            /**
             * user_name : 斧王
             * img : upload/201708/14/1502701059864.jpg
             * user_id : 4
             * post_name : 固定岗
             * sign_time : 1502856254000
             */

            @SerializedName("user_name")
            private String userName;
            @SerializedName("img")
            private String img;
            @SerializedName("user_id")
            private int userId;
            @SerializedName("post_name")
            private String postName;
            @SerializedName("sign_time")
            private long signTime;

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
    }
}
