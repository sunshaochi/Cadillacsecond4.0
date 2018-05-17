package com.ist.cadillacpaltform.SDK.bean.Posm;

import com.google.gson.annotations.SerializedName;

/**
 * Created by czh on 2017/3/5.
 */

public class SimpleQuestion {
    @SerializedName("questionId")
    private long questionId;
    @SerializedName("questionItem")
    private String questionItem;
    @SerializedName("questionScore")
    private int questionScore;
    @SerializedName("regionId")
    private long regionId;
    @SerializedName("regionName")
    private String regionName;
    @SerializedName("moduleId")
    private long moduleId;
    @SerializedName("moduleName")
    private String moduleName;

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public String getQuestionItem() {
        return questionItem;
    }

    public void setQuestionItem(String questionItem) {
        this.questionItem = questionItem;
    }

    public int getQuestionScore() {
        return questionScore;
    }

    public void setQuestionScore(int questionScore) {
        this.questionScore = questionScore;
    }

    public long getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public long getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
}
