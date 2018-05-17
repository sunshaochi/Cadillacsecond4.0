package com.ist.cadillacpaltform.SDK.bean.HighStockAge;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dearlhd on 2016/12/15.
 */
public class CarLine {
    @SerializedName("id")
    private long id;
    @SerializedName("createTime")
    private String createTime;
    @SerializedName("name")
    private String name;
    @SerializedName("checked")
    private boolean checked;



    @SerializedName("flag")
    private String flag;


    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
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

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

}
