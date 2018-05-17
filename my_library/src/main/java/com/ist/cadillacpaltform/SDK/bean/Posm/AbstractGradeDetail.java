package com.ist.cadillacpaltform.SDK.bean.Posm;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dearlhd on 2017/3/7.
 */
public class AbstractGradeDetail {
    @SerializedName("gradeDetailId")
    private long id;

    @SerializedName("gradeDetailScore")
    private float score;

    @SerializedName("gradeDetailFull")
    private boolean isFullMark;

    @SerializedName("questionId")
    private long questionId;

    @SerializedName("questionItem")
    private String questionItem;

    @SerializedName("questionScore")
    private float totalScore;

    @SerializedName("regionId")
    private long regionId;

    @SerializedName("regionName")
    private String regionName;

    @SerializedName("moduleId")
    private long moduleId;

    @SerializedName("moduleName")
    private String moduleName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public boolean isFullMark() {
        return isFullMark;
    }

    public void setFullMark(boolean fullMark) {
        isFullMark = fullMark;
    }

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

    public float getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(float totalScore) {
        this.totalScore = totalScore;
    }

    public long getRegionId() {
        return regionId;
    }

    public void setRegionId(long regionId) {
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

    public void setModuleId(long moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
}
