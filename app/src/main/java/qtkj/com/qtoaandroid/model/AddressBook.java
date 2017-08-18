package qtkj.com.qtoaandroid.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/8/14 0014.
 */

public class AddressBook {


    /**
     * postName : 固定岗
     * number : 8
     * dept : [{"is_sign":0,"user_name":"fgg","user_phone":"15698563244","user_id":17},{"is_sign":0,"user_name":"ofjkm","user_phone":"15326987456","user_id":14},{"is_sign":0,"user_name":"oppo","user_phone":"15611112222","user_id":10},{"is_sign":0,"user_name":"ttop","user_phone":"18536925666","user_id":11},{"is_sign":0,"user_name":"斧王","user_phone":"13325896541","img":"upload/201708/14/1502701059864.jpg","user_id":4},{"is_sign":0,"user_name":"环保","user_phone":"18759687865","user_id":16},{"is_sign":0,"user_name":"申中甲","user_phone":"18336302752","img":"upload/201708/16/1502896272365.jpg","user_id":1},{"is_sign":0,"user_name":"王二","user_phone":"13303737962","user_id":2}]
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
         * user_name : fgg
         * user_phone : 15698563244
         * user_id : 17
         * img : upload/201708/14/1502701059864.jpg
         */

        @SerializedName("is_sign")
        private int isSign;
        @SerializedName("user_name")
        private String userName;
        @SerializedName("user_phone")
        private String userPhone;
        @SerializedName("user_id")
        private int userId;
        @SerializedName("img")
        private String img;

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

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }
}
