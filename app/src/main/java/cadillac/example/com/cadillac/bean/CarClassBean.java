package cadillac.example.com.cadillac.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by bitch-1 on 2017/6/26.
 */

public class CarClassBean implements Parcelable {

    /**
     * carClass : 3系
     */

    private String carClass;

    protected CarClassBean(Parcel in) {
        carClass = in.readString();
    }

    public static final Creator<CarClassBean> CREATOR = new Creator<CarClassBean>() {
        @Override
        public CarClassBean createFromParcel(Parcel in) {
            return new CarClassBean(in);
        }

        @Override
        public CarClassBean[] newArray(int size) {
            return new CarClassBean[size];
        }
    };

    public String getCarClass() {
        return carClass;
    }

    public void setCarClass(String carClass) {
        this.carClass = carClass;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(carClass);
    }
}
