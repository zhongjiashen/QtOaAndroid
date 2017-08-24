package qtkj.com.qtoaandroid.model;

import com.google.gson.annotations.SerializedName;

/**
 * Author:    申中佳
 * Version    V1.0
 * Date:      2017/8/24 0024 下午 3:43
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2017/8/24 0024         申中佳               V1.0
 */
public class Update {

    /**
     * id : 1
     * version : 1213213
     * addTime : null
     */

    @SerializedName("id")
    private int id;
    @SerializedName("version")
    private int version;
    @SerializedName("addTime")
    private Object addTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Object getAddTime() {
        return addTime;
    }

    public void setAddTime(Object addTime) {
        this.addTime = addTime;
    }
}
