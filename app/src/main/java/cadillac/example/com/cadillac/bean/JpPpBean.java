package cadillac.example.com.cadillac.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 竞品价格品牌bean
 * Created by bitch-1 on 2017/6/28.
 */

public class JpPpBean implements Parcelable{

    /**
     * carBrand : BMW
     * data : [{"financial":"","remark":"","carClass":"3系"},{"financial":"","remark":"","carClass":"5系"},{"financial":"","remark":"","carClass":"X3"}]
     */

    private String carBrand;
    private List<JpChexiBean> data;

    protected JpPpBean(Parcel in) {
        carBrand = in.readString();
        data = in.createTypedArrayList(JpChexiBean.CREATOR);
    }

    public static final Creator<JpPpBean> CREATOR = new Creator<JpPpBean>() {
        @Override
        public JpPpBean createFromParcel(Parcel in) {
            return new JpPpBean(in);
        }

        @Override
        public JpPpBean[] newArray(int size) {
            return new JpPpBean[size];
        }
    };

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public List<JpChexiBean> getData() {
        return data;
    }

    public void setData(List<JpChexiBean> data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(carBrand);
        parcel.writeTypedList(data);
    }
}
