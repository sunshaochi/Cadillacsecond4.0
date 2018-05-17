package com.ist.cadillacpaltform.SDK.bean.Posm;

import com.google.gson.annotations.SerializedName;

/**
 * Created by czh on 2017/3/7.
 */
//没有is_delete,version,其他和数据库一致
public class User {
    @SerializedName("id")
    private long id;

    @SerializedName("createTime")
    private String createTime;

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    @SerializedName("truename")
    private String truename;

    @SerializedName("telephone")
    private String telephone;

    @SerializedName("type")
    private String type;

    @SerializedName("dealer")
    private long dealer;

    @SerializedName("subzone")
    private long subzone;

    @SerializedName("zone")
    private long zone;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getDealer() {
        return dealer;
    }

    public void setDealer(long dealer) {
        this.dealer = dealer;
    }

    public long getSubzone() {
        return subzone;
    }

    public void setSubzone(long subzone) {
        this.subzone = subzone;
    }

    public long getZone() {
        return zone;
    }

    public void setZone(long zone) {
        this.zone = zone;
    }
}
