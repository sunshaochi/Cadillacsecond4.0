package com.ist.cadillacpaltform.SDK.response.Posm;

import com.google.gson.annotations.SerializedName;
import com.ist.cadillacpaltform.SDK.bean.Posm.AbstractMark;

import java.util.List;

/**
 * Created by dearlhd on 2017/3/10.
 */
public class AbstractMarkResponse {
    @SerializedName("data")
    public List<AbstractMark> marks;
}
