package cadillac.example.com.cadillac.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 零售预测上传的对象
 * Created by bitch-1 on 2017/6/8.
 */

public class UpLoadBean implements Parcelable {
    private String inputTimeId;
    private String userName;
    private List<ListBean>dataList;

    public UpLoadBean() {
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

    public List<ListBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<ListBean> dataList) {
        this.dataList = dataList;
    }

    protected UpLoadBean(Parcel in) {
        inputTimeId = in.readString();
        userName = in.readString();
        dataList = in.createTypedArrayList(ListBean.CREATOR);
    }

    public static final Creator<UpLoadBean> CREATOR = new Creator<UpLoadBean>() {
        @Override
        public UpLoadBean createFromParcel(Parcel in) {
            return new UpLoadBean(in);
        }

        @Override
        public UpLoadBean[] newArray(int size) {
            return new UpLoadBean[size];
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
