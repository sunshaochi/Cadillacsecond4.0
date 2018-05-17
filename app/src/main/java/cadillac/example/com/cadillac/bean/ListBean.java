package cadillac.example.com.cadillac.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by bitch-1 on 2017/6/7.
 */

public class ListBean implements Parcelable{

    /**
     * pridictNum : 0
     * carType : ATS-L
     */
    private String pridictNum;
    private String carType;

    public String getPridictNum() {
        return pridictNum;
    }

    public void setPridictNum(String pridictNum) {
        this.pridictNum = pridictNum;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    protected ListBean(Parcel in) {
        pridictNum = in.readString();
        carType = in.readString();
    }

    public static final Creator<ListBean> CREATOR = new Creator<ListBean>() {
        @Override
        public ListBean createFromParcel(Parcel in) {
            return new ListBean(in);
        }

        @Override
        public ListBean[] newArray(int size) {
            return new ListBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(pridictNum);
        parcel.writeString(carType);
    }
}
