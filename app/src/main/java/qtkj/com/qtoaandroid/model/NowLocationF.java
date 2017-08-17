package qtkj.com.qtoaandroid.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/8/14 0014.
 */

public class NowLocationF {

    /**
     * userCount : 2
     * postName : 保安岗
     * dept : [{"user_name":"李四","img":"upload/201708/16/1502883653582.jpg","user_id":1}]
     * signCount : 1
     * postId : 2
     */

    @SerializedName("userCount")
    private int userCount;
    @SerializedName("postName")
    private String postName;
    @SerializedName("signCount")
    private int signCount;
    @SerializedName("postId")
    private int postId;
    @SerializedName("dept")
    private List<DeptBean> dept;

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public int getSignCount() {
        return signCount;
    }

    public void setSignCount(int signCount) {
        this.signCount = signCount;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public List<DeptBean> getDept() {
        return dept;
    }

    public void setDept(List<DeptBean> dept) {
        this.dept = dept;
    }

    public static class DeptBean {
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

        @Override
        public String toString() {
            return  "{"+"\"user_name\":\"" + userName  +
                    "\", \"img\":\"" + img  +
                    "\", \"user_id\":" + userId +
                   "}";
        }
    }
}
