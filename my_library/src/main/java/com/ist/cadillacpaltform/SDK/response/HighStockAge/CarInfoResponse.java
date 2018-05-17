package com.ist.cadillacpaltform.SDK.response.HighStockAge;

import com.google.gson.annotations.SerializedName;
import com.ist.cadillacpaltform.SDK.bean.HighStockAge.CarInfo;

/**
 * Created by czh on 2017/1/6.
 */

public class CarInfoResponse {
    @SerializedName("data")
    public CarInfo carInfo;
}
