package com.ist.cadillacpaltform.SDK.response.Posm;

import com.google.gson.annotations.SerializedName;
import com.ist.cadillacpaltform.SDK.bean.Posm.ModuleGrade;

import java.util.List;

/**
 * Created by dearlhd on 2017/3/10.
 */
public class ModuleGradeResponse {
    @SerializedName("data")
    public GradeInModule gradeInModule;

    public class GradeInModule {
        @SerializedName("year")
        public int year;
        @SerializedName("quarter")
        public int quarter;
        @SerializedName("grade")
        public float grade;
        @SerializedName("moduleGrade")
        public List<ModuleGrade> grades;
        @SerializedName("owned")
        public boolean owned;
    }
}
