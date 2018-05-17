package cadillac.example.com.cadillac.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 竞品价格编辑界面车系bean
 * Created by bitch-1 on 2017/6/28.
 */

public class JpChexiBean implements Parcelable {

    /**
     * financial :
     * remark :
     * carClass : 3系
     */

    private String financial;
    private String remark;
    private String carClass;

    protected JpChexiBean(Parcel in) {
        financial = in.readString();
        remark = in.readString();
        carClass = in.readString();
    }

    public static final Creator<JpChexiBean> CREATOR = new Creator<JpChexiBean>() {
        @Override
        public JpChexiBean createFromParcel(Parcel in) {
            return new JpChexiBean(in);
        }

        @Override
        public JpChexiBean[] newArray(int size) {
            return new JpChexiBean[size];
        }
    };

    public String getFinancial() {
        return financial;
    }

    public void setFinancial(String financial) {
        this.financial = financial;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

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
        parcel.writeString(financial);
        parcel.writeString(remark);
        parcel.writeString(carClass);
    }
}
