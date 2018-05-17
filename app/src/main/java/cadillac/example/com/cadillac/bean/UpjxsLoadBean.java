package cadillac.example.com.cadillac.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by bitch-1 on 2017/6/22.
 */

public class UpjxsLoadBean implements Parcelable {
    private String inputTimeId;
    private String userName;
    private List<JxsBjListBean> dataList;

    public UpjxsLoadBean() {
    }

    public String getInputTimeId() {
        return inputTimeId;
    }

    public void setInputTimeId(String inputTimeId) {
        this.inputTimeId = inputTimeId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<JxsBjListBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<JxsBjListBean> dataList) {
        this.dataList = dataList;
    }

    protected UpjxsLoadBean(Parcel in) {
        inputTimeId = in.readString();
        userName = in.readString();
        dataList = in.createTypedArrayList(JxsBjListBean.CREATOR);
    }

    public static final Creator<UpjxsLoadBean> CREATOR = new Creator<UpjxsLoadBean>() {
        @Override
        public UpjxsLoadBean createFromParcel(Parcel in) {
            return new UpjxsLoadBean(in);
        }

        @Override
        public UpjxsLoadBean[] newArray(int size) {
            return new UpjxsLoadBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(inputTimeId);
        parcel.writeString(userName);
        parcel.writeTypedList(dataList);
    }
}
