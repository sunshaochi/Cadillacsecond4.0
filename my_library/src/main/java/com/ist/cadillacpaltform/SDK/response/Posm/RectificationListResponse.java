package com.ist.cadillacpaltform.SDK.response.Posm;

import com.google.gson.annotations.SerializedName;
import com.ist.cadillacpaltform.SDK.bean.Posm.Rectification;

import java.util.List;

/**
 * Created by czh on 2017/4/7.
 */

public class RectificationListResponse {
    @SerializedName("data")
    public List<Rectification> rectification;
}
