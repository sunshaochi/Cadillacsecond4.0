package com.ist.cadillacpaltform.SDK.bean.Posm;

import com.google.gson.annotations.SerializedName;
import com.ist.cadillacpaltform.SDK.bean.HighStockAge.Dealer;

/**
 * Created by czh on 2017/3/7.
 */
//user和dealer可以只存id，与数据库一致
public class Grade {
    @SerializedName("id")
    private long id;

    @SerializedName("createTime")
    private String createTime;

    @SerializedName("quarter")
    private int quarter;

    @SerializedName("year")
    private int year;

    @SerializedName("commitTime")
    private String commitTime;

    @SerializedName("totalscore")
    private float totalscore;

    @SerializedName("user")
    private User user;

    @SerializedName("dealer")
    private Dealer dealer;

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

    public int getQuarter() {
        return quarter;
    }

    public void setQuarter(int quarter) {
        this.quarter = quarter;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(String commitTime) {
        this.commitTime = commitTime;
    }

    public float getTotalscore() {
        return totalscore;
    }

    public void setTotalscore(float totalscore) {
        this.totalscore = totalscore;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Dealer getDealer() {
        return dealer;
    }

    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }
}
