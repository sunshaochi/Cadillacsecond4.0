package com.ist.cadillacpaltform.SDK.bean.Posm;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dearlhd on 2017/3/10.
 */
public class DealerGrade {
    @SerializedName("id")
    private long id;  // dealerId
    @SerializedName("name")
    private String name;
    @SerializedName("gradeDto")
    private GradeDto gradeDto;

    public class GradeDto {
        @SerializedName("id")
        public long id;
        @SerializedName("commitTime")
        public String commitTime;
        @SerializedName("totalscore")
        public float score;
        @SerializedName("userDto")
        public UserDto userDto;
    }

    public class UserDto {
        @SerializedName("id")
        public long id;
        @SerializedName("truename")
        public String name;
    }

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

    public GradeDto getGradeDto() {
        return gradeDto;
    }

    public void setGradeDto(GradeDto gradeDto) {
        this.gradeDto = gradeDto;
    }
}
