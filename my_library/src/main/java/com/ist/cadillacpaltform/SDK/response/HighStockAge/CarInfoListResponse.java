package com.ist.cadillacpaltform.SDK.response.HighStockAge;

import com.google.gson.annotations.SerializedName;
import com.ist.cadillacpaltform.SDK.bean.HighStockAge.CarInfo;

import java.util.List;

/**
 * Created by dearlhd on 2016/12/19.
 */
public class CarInfoListResponse {
    @SerializedName("data")
    public List<CarInfo> cars;

    @SerializedName("count")
    public int count;
}
