package com.ist.cadillacpaltform.SDK.response.HighStockAge;

import com.google.gson.annotations.SerializedName;
import com.ist.cadillacpaltform.SDK.bean.HighStockAge.SubZone;

import java.util.List;

/**
 * Created by dearlhd on 2016/12/20.
 */
public class SubZoneListResponse {
    @SerializedName("data")
    public List<SubZone> subZones;

}
