package com.ist.cadillacpaltform.SDK.response.HighStockAge;

import com.google.gson.annotations.SerializedName;
import com.ist.cadillacpaltform.SDK.bean.HighStockAge.Order;

/**
 * Created by czh on 2017/1/7.
 */

public class OrderInfoResponse {
    @SerializedName("data")
    public Order orderInfo;
}
