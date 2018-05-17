package com.ist.cadillacpaltform.SDK.bean.HighStockAge;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dearlhd on 2016/12/19.
 */
public class CarConfig {
    @SerializedName("id")
    private long id;

    @SerializedName("createTime")
    private String createTime;

    @SerializedName("name")
    private String name;

//    @SerializedName("flag")
//    private boolean flag;

    @SerializedName("flag")
    private String flag;
    @SerializedName("version")
    private String version;
    @SerializedName("isDeleted")
    private String isDeleted;
    @SerializedName("lid")
    private String lid;


    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getLid() {
        return lid;
    }

    public void setLid(String lid) {
        this.lid = lid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public boolean isFlag() {
//        return flag;
//    }
//
//    public void setFlag(boolean flag) {
//        this.flag = flag;
//    }


    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
