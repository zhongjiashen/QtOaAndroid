package qtkj.com.qtoaandroid.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/8/14 0014.
 */

public class NowLocationF {

    /**
     * userCount : 7
     * postName : 固定岗
     * dept : []
     * signCount : 0
     * postId : 1
     */

    @SerializedName("userCount")
    private int userCount;
    @SerializedName("postName")
    private String postName;
    @SerializedName("signCount")
    private int signCount;
    @SerializedName("postId")
    private int postId;


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


}
