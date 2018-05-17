package com.ist.cadillacpaltform.SDK.bean.Posm;

import com.google.gson.annotations.SerializedName;
import com.ist.cadillacpaltform.SDK.bean.HighStockAge.Dealer;

/**
 * Created by czh on 2017/3/27.
 */

public class Rectification {
    @SerializedName("id")
    private long id;
    @SerializedName("createTime")
    private String createTime;
    @SerializedName("detail")
    private GradeDetail gradeDetail;
    @SerializedName("picurl1")
    private String picurl1;
    @SerializedName("picurl2")
    private String picurl2;
    @SerializedName("picurl3")
    private String picurl3;
    @SerializedName("description")
    private String description;
    @SerializedName("user")
    private Dealer user;

    public Dealer getUser() {
        return user;
    }

    public void setUser(Dealer user) {
        this.user = user;
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

    public GradeDetail getGradeDetail() {
        return gradeDetail;
    }

    public void setGradeDetail(GradeDetail gradeDetail) {
        this.gradeDetail = gradeDetail;
    }

    public String getPicurl1() {
        return picurl1;
    }

    public void setPicurl1(String picurl1) {
        this.picurl1 = picurl1;
    }

    public String getPicurl2() {
        return picurl2;
    }

    public void setPicurl2(String picurl2) {
        this.picurl2 = picurl2;
    }

    public String getPicurl3() {
        return picurl3;
    }

    public void setPicurl3(String picurl3) {
        this.picurl3 = picurl3;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
