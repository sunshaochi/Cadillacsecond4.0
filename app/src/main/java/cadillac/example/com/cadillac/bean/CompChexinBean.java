package cadillac.example.com.cadillac.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by bitch-1 on 2017/6/27.
 */

public class CompChexinBean implements Parcelable {

    /**
     * avgPurchasePrice :
     * guidPrice : 35.38
     * carMode : 2.0T 自动 C200L 运动轿车
     */

    private String avgPurchasePrice;
    private String guidPrice;
    private String carMode;

    protected CompChexinBean(Parcel in) {
        avgPurchasePrice = in.readString();
        guidPrice = in.readString();
        carMode = in.readString();
    }

    public static final Creator<CompChexinBean> CREATOR = new Creator<CompChexinBean>() {
        @Override
        public CompChexinBean createFromParcel(Parcel in) {
            return new CompChexinBean(in);
        }

        @Override
        public CompChexinBean[] newArray(int size) {
            return new CompChexinBean[size];
        }
    };

    public String getAvgPurchasePrice() {
        return avgPurchasePrice;
    }

    public void setAvgPurchasePrice(String avgPurchasePrice) {
        this.avgPurchasePrice = avgPurchasePrice;
    }

    public String getGuidPrice() {
        return guidPrice;
    }

    public void setGuidPrice(String guidPrice) {
        this.guidPrice = guidPrice;
    }

    public String getCarMode() {
        return carMode;
    }

    public void setCarMode(String carMode) {
        this.carMode = carMode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(avgPurchasePrice);
        parcel.writeString(guidPrice);
        parcel.writeString(carMode);
    }
}
