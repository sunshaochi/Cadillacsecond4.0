package com.ist.cadillacpaltform.SDK.response.Posm;

import com.google.gson.annotations.SerializedName;
import com.ist.cadillacpaltform.SDK.bean.Posm.AbstractGradeDetail;

import java.util.List;

/**
 * Created by dearlhd on 2017/3/7.
 */
public class AbstractGradeDetailResponse {
    @SerializedName("data")
    public List<AbstractGradeDetail> abstractGradeDetails;
}
