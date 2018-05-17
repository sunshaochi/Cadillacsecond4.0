package com.ist.cadillacpaltform.SDK.response.HighStockAge;

import com.google.gson.annotations.SerializedName;
import com.ist.cadillacpaltform.SDK.bean.HighStockAge.Order;

import java.util.List;

/**
 * Created by dearlhd on 2016/12/28.
 */
public class OrderResponse {
    @SerializedName("data")
    public List<Order> orders;
}
