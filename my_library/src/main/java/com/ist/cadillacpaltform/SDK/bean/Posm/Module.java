package com.ist.cadillacpaltform.SDK.bean.Posm;

import com.google.gson.annotations.SerializedName;

/**
 * Created by czh on 2017/3/6.
 */

public class Module {
    @SerializedName("id")
    private long id;
    @SerializedName("createTime")
    private String createTime;
    @SerializedName("name")
    private String name;
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



}
