package cadillac.example.com.cadillac.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by iris on 2017/12/27.
 */

public class StateBean implements Parcelable{
    private String dateKey;
    private String topShow;
    private String buttonShow;

    public StateBean(String dateKey, String topShow, String buttonShow) {
        this.dateKey = dateKey;
        this.topShow = topShow;
        this.buttonShow = buttonShow;
    }

    protected StateBean(Parcel in) {
        dateKey = in.readString();
        topShow = in.readString();
        buttonShow = in.readString();
    }

    public static final Creator<StateBean> CREATOR = new Creator<StateBean>() {
        @Override
        public StateBean createFromParcel(Parcel in) {
            return new StateBean(in);
        }

        @Override
        public StateBean[] newArray(int size) {
            return new StateBean[size];
        }
    };

    public String getDateKey() {
        return dateKey;
    }

    public void setDateKey(String dateKey) {
        this.dateKey = dateKey;
    }

    public String getTopShow() {
        return topShow;
    }

    public void setTopShow(String topShow) {
        this.topShow = topShow;
    }

    public String getButtonShow() {
        return buttonShow;
    }

    public void setButtonShow(String buttonShow) {
        this.buttonShow = buttonShow;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(dateKey);
        parcel.writeString(topShow);
        parcel.writeString(buttonShow);
    }

    @Override
    public String toString() {
        return "StateBean{" +
                "dateKey='" + dateKey + '\'' +
                ", topShow='" + topShow + '\'' +
                ", buttonShow='" + buttonShow + '\'' +
                '}';
    }
}
