package com.ist.cadillacpaltform.SDK.response.HighStockAge;

import com.google.gson.annotations.SerializedName;
import com.ist.cadillacpaltform.SDK.bean.HighStockAge.CarColor;

import java.util.List;

/**
 * Created by dearlhd on 2016/12/22.
 */
public class CarColorListResponse {
    @SerializedName("data")
    public List<CarColor> colors;
}
