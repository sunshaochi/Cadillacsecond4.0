package cadillac.example.com.cadillac.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by iris on 2018/4/25.
 */

public class LmJxsBean implements Parcelable {
    private String code;
    private String name;

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
        dest.writeString(this.name);
    }

    public LmJxsBean() {
    }

    protected LmJxsBean(Parcel in) {
        this.code = in.readString();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<LmJxsBean> CREATOR = new Parcelable.Creator<LmJxsBean>() {
        @Override
        public LmJxsBean createFromParcel(Parcel source) {
            return new LmJxsBean(source);
        }

        @Override
        public LmJxsBean[] newArray(int size) {
            return new LmJxsBean[size];
        }
    };
}
