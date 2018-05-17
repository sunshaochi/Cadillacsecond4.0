package cadillac.example.com.cadillac.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by iris on 2017/12/27.
 */

public class Modle implements Parcelable{

    /**
     * id : 1020
     * code : XTS
     * name : XTS
     * carTypeId : 1000
     * isDelete : N
     * deleteTime : null
     * createTime : 2017-12-18 14:52:52
     * createUser : iris
     * lastUpdateTime : 2017-12-18 14:52:52
     * lastUpdateUser : iris
     */

    private String id;
    private String code;
    private String name;
    private String carTypeId;
    private String isDelete;
    private String deleteTime;
    private String createTime;
    private String createUser;
    private String lastUpdateTime;
    private String lastUpdateUser;

    public Modle(String id, String code, String name, String carTypeId, String isDelete, String deleteTime, String createTime, String createUser, String lastUpdateTime, String lastUpdateUser) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.carTypeId = carTypeId;
        this.isDelete = isDelete;
        this.deleteTime = deleteTime;
        this.createTime = createTime;
        this.createUser = createUser;
        this.lastUpdateTime = lastUpdateTime;
        this.lastUpdateUser = lastUpdateUser;
    }

    protected Modle(Parcel in) {
        id = in.readString();
        code = in.readString();
        name = in.readString();
        carTypeId = in.readString();
        isDelete = in.readString();
        deleteTime = in.readString();
        createTime = in.readString();
        createUser = in.readString();
        lastUpdateTime = in.readString();
        lastUpdateUser = in.readString();
    }

    public static final Creator<Modle> CREATOR = new Creator<Modle>() {
        @Override
        public Modle createFromParcel(Parcel in) {
            return new Modle(in);
        }

        @Override
        public Modle[] newArray(int size) {
            return new Modle[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCarTypeId() {
        return carTypeId;
    }

    public void setCarTypeId(String carTypeId) {
        this.carTypeId = carTypeId;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public String getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(String deleteTime) {
        this.deleteTime = deleteTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getLastUpdateUser() {
        return lastUpdateUser;
    }

    public void setLastUpdateUser(String lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(code);
        parcel.writeString(name);
        parcel.writeString(carTypeId);
        parcel.writeString(isDelete);
        parcel.writeString(deleteTime);
        parcel.writeString(createTime);
        parcel.writeString(createUser);
        parcel.writeString(lastUpdateTime);
        parcel.writeString(lastUpdateUser);
    }
}
