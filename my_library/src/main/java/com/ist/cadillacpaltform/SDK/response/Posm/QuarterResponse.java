package com.ist.cadillacpaltform.SDK.response.Posm;

import com.google.gson.annotations.SerializedName;
import com.ist.cadillacpaltform.SDK.bean.Posm.Quarter;

import java.util.List;

/**
 * Created by dearlhd on 2017/3/10.
 */
public class QuarterResponse {
    @SerializedName("data")
    public List<Quarter> quarters;
}
