package com.ist.cadillacpaltform.SDK.response.HighStockAge;

import com.google.gson.annotations.SerializedName;
import com.ist.cadillacpaltform.SDK.bean.HighStockAge.Dealer;

import java.util.List;

/**
 * Created by dearlhd on 2016/12/26.
 */
public class DealerResponse {
    @SerializedName("data")
    public List<Dealer> dealers;
}
