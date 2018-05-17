package cadillac.example.com.cadillac.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by bitch-1 on 2017/8/16.
 */

public class ShaiXuanBean implements Parcelable {
    private String region;
    private String area;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public ShaiXuanBean() {
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.region);
        dest.writeString(this.area);
    }

    protected ShaiXuanBean(Parcel in) {
        this.region = in.readString();
        this.area = in.readString();
    }

    public static final Creator<ShaiXuanBean> CREATOR = new Creator<ShaiXuanBean>() {
        @Override
        public ShaiXuanBean createFromParcel(Parcel source) {
            return new ShaiXuanBean(source);
        }

        @Override
        public ShaiXuanBean[] newArray(int size) {
            return new ShaiXuanBean[size];
        }
    };
}
