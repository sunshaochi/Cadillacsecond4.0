package com.ist.cadillacpaltform.SDK.bean.HighStockAge;

import com.google.gson.annotations.SerializedName;
import com.ist.cadillacpaltform.SDK.bean.HighStockAge.CarInfo;
import com.ist.cadillacpaltform.SDK.bean.HighStockAge.Dealer;

/**
 * Created by dearlhd on 2016/12/28.
 */
public class Order {
    @SerializedName("id")
    private long id;
    @SerializedName("createTime")
    private String createTime;
    @SerializedName("dealer")
    private Dealer sourceDealer;    // 发起请求的经销商
    @SerializedName("car")
    private CarInfo car;

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

    public Dealer getSourceDealer() {
        return sourceDealer;
    }

    public void setSourceDealer(Dealer sourceDealer) {
        this.sourceDealer = sourceDealer;
    }

    public CarInfo getCar() {
        return car;
    }

    public void setCar(CarInfo car) {
        this.car = car;
    }
}
