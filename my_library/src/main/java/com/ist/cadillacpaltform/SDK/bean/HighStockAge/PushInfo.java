package com.ist.cadillacpaltform.SDK.bean.HighStockAge;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dearlhd on 2017/2/20.
 * 被请求车辆推送 传过来的自定义信息
 */
public class PushInfo {
    @SerializedName("order_id")
    private long orderId;

    @SerializedName("car_id")
    private long carId;

    @SerializedName("name")
    private String dealerName;

    @SerializedName("fname")
    private String dealerFullName;

    @SerializedName("phone")
    private String phone;

    @SerializedName("dealer_id")
    private long dealerId;

    @SerializedName("type")
    private int type;        // 推送消息类型 1是车辆被请求 2是车辆被删除

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getCarId() {
        return carId;
    }

    public void setCarId(long carId) {
        this.carId = carId;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getDealerFullName() {
        return dealerFullName;
    }

    public void setDealerFullName(String dealerFullName) {
        this.dealerFullName = dealerFullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getDealerId() {
        return dealerId;
    }

    public void setDealerId(long dealerId) {
        this.dealerId = dealerId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
