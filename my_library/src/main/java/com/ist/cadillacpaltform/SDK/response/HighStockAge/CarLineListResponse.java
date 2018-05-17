package com.ist.cadillacpaltform.SDK.response.HighStockAge;

import com.google.gson.annotations.SerializedName;
import com.ist.cadillacpaltform.SDK.bean.HighStockAge.CarLine;

import java.util.List;

/**
 * Created by dearlhd on 2016/12/15.
 */
public class CarLineListResponse {
    @SerializedName("data")
    public List<CarLine> lines;
}
