package com.ist.cadillacpaltform.SDK.bean.HighStockAge;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dearlhd on 2016/12/19.
 */
public class CarInfo {
    @SerializedName("id")
    private long id;

    @SerializedName("createTime")
    private String createTime;

    @SerializedName("price")
    private double price;

    @SerializedName("color")
    private String color;

    @SerializedName("serial")
    private String serial;

    @SerializedName("status")
    private int status;

    @SerializedName("addTime")
    private String addTime;

    @SerializedName("line")
    private CarLine line;

    @SerializedName("configuration")
    private CarConfig config;

    @SerializedName("dealer")
    private Dealer dealer;

    @SerializedName("reported")
    private int reported;

    @SerializedName("type")
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getReported() {
        return reported;
    }

    public void setReported(int reported) {
        this.reported = reported;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public CarLine getLine() {
        return line;
    }

    public void setLine(CarLine line) {
        this.line = line;
    }

    public CarConfig getConfig() {
        return config;
    }

    public void setConfig(CarConfig config) {
        this.config = config;
    }

    public Dealer getDealer() {
        return dealer;
    }

    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }
}
