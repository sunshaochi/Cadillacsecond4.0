package com.ist.cadillacpaltform.SDK.bean.Posm;

import com.google.gson.annotations.SerializedName;
import com.ist.cadillacpaltform.SDK.bean.Posm.Module;

/**
 * Created by czh on 2017/3/6.
 */

public class Region {
    @SerializedName("id")
    private long id;
    @SerializedName("createTime")
    private String createTime;
    @SerializedName("name")
    private String name;
    @SerializedName("module")
    private Module module;
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

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }


}
