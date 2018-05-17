package com.ist.cadillacpaltform.SDK.bean.Posm;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dearlhd on 2017/3/10.
 */
public class ModuleGrade {
    @SerializedName("id")
    private long id;

    @SerializedName("name")
    private String name;

    @SerializedName("score")
    private float score;

    @SerializedName("moduleScore")
    private float moduleScore;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public float getModuleScore() {
        return moduleScore;
    }

    public void setModuleScore(float moduleScore) {
        this.moduleScore = moduleScore;
    }
}
