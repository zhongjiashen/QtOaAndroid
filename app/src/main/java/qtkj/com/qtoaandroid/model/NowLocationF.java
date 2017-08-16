package qtkj.com.qtoaandroid.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/8/14 0014.
 */

public class NowLocationF {


    /**
     * userCount : 8.0
     * postName : 固定岗
     * dept : [{"user_id":1},{"user_id":4}]
     * signCount : 2.0
     * postId : 1.0
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
         * user_id : 1.0
         */

        @SerializedName("user_id")
        private int userId;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        @Override
        public String toString() {
            return  userId+"";
        }
    }
}
