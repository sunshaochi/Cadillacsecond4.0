package com.ist.cadillacpaltform.SDK.bean.HighStockAge;

import com.google.gson.annotations.SerializedName;

/**
 * Created by czh on 2017/1/3.
 */

public class PurchaseCar {
    @SerializedName("order_id")
    private long orderId;           // 订单id
    @SerializedName("car_id")
    private long carId;             // 车辆id
    @SerializedName("name")
    private String dealerName;    // 经销商简称
    @SerializedName("fname")
    private String dealerFullName;// 经销商全称
    @SerializedName("phone")
    private String dealerPhone;
    @SerializedName("dealer_id")
    private long dealerId;         // 经销商id
    @SerializedName("type")
    private long type;              // 推送消息类型 1是车辆被请求 2是车辆被删除

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

    public String getDealerPhone() {
        return dealerPhone;
    }

    public void setDealerPhone(String dealerPhone) {
        this.dealerPhone = dealerPhone;
    }

    public long getDealerId() {
        return dealerId;
    }

    public void setDealerId(long dealerId) {
        this.dealerId = dealerId;
    }

    public long getType() {
        return type;
    }

    public void setType(long type) {
        this.type = type;
    }
}
