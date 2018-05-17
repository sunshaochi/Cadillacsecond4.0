package cadillac.example.com.cadillac.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by bitch-1 on 2017/6/26.
 */

public class CarBrandBean implements Parcelable {


    /**
     * carBrand : BMW
     * data : [{"carClass":"3系"},{"carClass":"5系"},{"carClass":"X3"}]
     */

    private String carBrand;
    private List<CarClassBean> data;

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public List<CarClassBean> getData() {
        return data;
    }

    public void setData(List<CarClassBean> data) {
        this.data = data;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.carBrand);
        dest.writeTypedList(this.data);
    }

    public CarBrandBean() {
    }

    protected CarBrandBean(Parcel in) {
        this.carBrand = in.readString();
        this.data = in.createTypedArrayList(CarClassBean.CREATOR);
    }

    public static final Creator<CarBrandBean> CREATOR = new Creator<CarBrandBean>() {
        @Override
        public CarBrandBean createFromParcel(Parcel source) {
            return new CarBrandBean(source);
        }

        @Override
        public CarBrandBean[] newArray(int size) {
            return new CarBrandBean[size];
        }
    };
}
