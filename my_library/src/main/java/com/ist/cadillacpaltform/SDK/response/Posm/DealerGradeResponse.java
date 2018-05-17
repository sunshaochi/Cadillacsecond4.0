package com.ist.cadillacpaltform.SDK.response.Posm;

import com.google.gson.annotations.SerializedName;
import com.ist.cadillacpaltform.SDK.bean.Posm.DealerGrade;
import com.ist.cadillacpaltform.SDK.bean.Posm.Paper;

import java.util.List;

/**
 * Created by dearlhd on 2017/3/10.
 */
public class DealerGradeResponse {
    @SerializedName("paper")
    public Paper paper;

    @SerializedName("dealerGradeList")
    public List<DealerGrade> dealerGrades;

    @SerializedName("totalpages")
    public int totalPages;
}
