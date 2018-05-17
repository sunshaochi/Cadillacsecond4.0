package com.ist.cadillacpaltform.SDK.response.Posm;

import com.google.gson.annotations.SerializedName;
import com.ist.cadillacpaltform.SDK.bean.Posm.GradeDetail;

/**
 * Created by czh on 2017/3/13.
 */

public class GradeDetailResponse {
    @SerializedName("data")
    public GradeDetail gradeDetail;
    @SerializedName("totalcount")
    public int totalCount;
}
