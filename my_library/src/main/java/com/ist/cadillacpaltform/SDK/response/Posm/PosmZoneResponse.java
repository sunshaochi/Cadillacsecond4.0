package com.ist.cadillacpaltform.SDK.response.Posm;

import com.google.gson.annotations.SerializedName;
import com.ist.cadillacpaltform.SDK.bean.Posm.Zone;

import java.util.List;

/**
 * Created by dearlhd on 2017/3/13.
 */
public class PosmZoneResponse {
    @SerializedName("data")
    public List<Zone> zones;
}
