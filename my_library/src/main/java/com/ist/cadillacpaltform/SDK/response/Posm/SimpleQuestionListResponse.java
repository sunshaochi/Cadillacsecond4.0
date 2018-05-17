package com.ist.cadillacpaltform.SDK.response.Posm;

import com.google.gson.annotations.SerializedName;
import com.ist.cadillacpaltform.SDK.bean.Posm.SimpleQuestion;

import java.util.List;

/**
 * Created by czh on 2017/3/5.
 */

public class SimpleQuestionListResponse {
    @SerializedName("data")
    public List<SimpleQuestion> simpleQuestionList;
}
