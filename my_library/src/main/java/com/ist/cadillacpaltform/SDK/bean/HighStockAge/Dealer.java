package com.ist.cadillacpaltform.SDK.bean.HighStockAge;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dearlhd on 2016/12/19.
 */
public class Dealer {
    @SerializedName("id")
    private long id;

    @SerializedName("createTime")
    private String createTime;

    @SerializedName("name")
    private String name;

    @SerializedName("fullname")
    private String fullName;

    @SerializedName("code")
    private String code;

    @SerializedName("telephone")
    private String telephone;

    @SerializedName("location")
    private String location;

    @SerializedName("zone")
    private String zone;

    @SerializedName("flag")
    private boolean flag;

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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean getFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
