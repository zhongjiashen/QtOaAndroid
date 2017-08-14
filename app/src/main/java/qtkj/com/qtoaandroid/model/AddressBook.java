package qtkj.com.qtoaandroid.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/8/14 0014.
 */

public class AddressBook {

    /**
     * postName : 固定岗
     * number : 5
     * dept : [{"is_sign":0,"user_name":"李四","user_phone":"18336302752","user_id":1},{"is_sign":0,"user_name":"王二","user_phone":"13303737962","user_id":2},{"is_sign":0,"user_name":"斧王","user_phone":"13325896541","user_id":4},{"is_sign":0,"user_name":"ofjkm","user_phone":"15326987456","user_id":14},{"is_sign":0,"user_name":"环保","user_phone":"18759687865","user_id":16}]
     */

    @SerializedName("postName")
    private String postName;
    @SerializedName("number")
    private int number;
    @SerializedName("dept")
    private List<DeptBean> dept;

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<DeptBean> getDept() {
        return dept;
    }

    public void setDept(List<DeptBean> dept) {
        this.dept = dept;
    }

    public static class DeptBean {
        /**
         * is_sign : 0
         * user_name : 李四
         * user_phone : 18336302752
         * user_id : 1
         */

        @SerializedName("is_sign")
        private int isSign;
        @SerializedName("user_name")
        private String userName;
        @SerializedName("user_phone")
        private String userPhone;
        @SerializedName("user_id")
        private int userId;

        public int getIsSign() {
            return isSign;
        }

        public void setIsSign(int isSign) {
            this.isSign = isSign;
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

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }
}
