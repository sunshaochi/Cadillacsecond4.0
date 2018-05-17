package com.ist.cadillacpaltform.SDK.response.HighStockAge;

import com.google.gson.annotations.SerializedName;
import com.ist.cadillacpaltform.SDK.bean.HighStockAge.CarConfig;

import java.util.List;

/**
 * Created by dearlhd on 2016/12/22.
 */
public class CarConfigListResponse {
    @SerializedName("data")
    public List<CarConfig> configs;
}
