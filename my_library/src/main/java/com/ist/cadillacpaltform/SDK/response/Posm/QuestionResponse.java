package com.ist.cadillacpaltform.SDK.response.Posm;

import com.google.gson.annotations.SerializedName;
import com.ist.cadillacpaltform.SDK.bean.Posm.Question;

/**
 * Created by czh on 2017/3/22.
 */

public class QuestionResponse {
    @SerializedName("data")
    public Question question;
}
