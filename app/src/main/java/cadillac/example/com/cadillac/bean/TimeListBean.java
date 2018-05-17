package cadillac.example.com.cadillac.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by bitch-1 on 2017/6/22.
 */

public class TimeListBean implements Parcelable {

    /**
     * inputTimeId : 1879
     * endTime : 2017-06-22
     */

    private String inputTimeId;
    private String endTime;

    protected TimeListBean(Parcel in) {
        inputTimeId = in.readString();
        endTime = in.readString();
    }

    public static final Creator<TimeListBean> CREATOR = new Creator<TimeListBean>() {
        @Override
        public TimeListBean createFromParcel(Parcel in) {
            return new TimeListBean(in);
        }

        @Override
        public TimeListBean[] newArray(int size) {
            return new TimeListBean[size];
        }
    };

    public String getInputTimeId() {
        return inputTimeId;
    }

    public void setInputTimeId(String inputTimeId) {
        this.inputTimeId = inputTimeId;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(inputTimeId);
        parcel.writeString(endTime);
    }
}
