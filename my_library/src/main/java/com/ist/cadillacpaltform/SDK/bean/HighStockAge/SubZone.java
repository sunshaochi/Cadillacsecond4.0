package com.ist.cadillacpaltform.SDK.bean.HighStockAge;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dearlhd on 2016/12/20.
 */
public class SubZone {
    @SerializedName("id")
    private long id;

    @SerializedName("createTime")
    private String createTime;

    @SerializedName("name")
    private String name;

    @SerializedName("zone")
    private Zone zone;

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

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }
}
