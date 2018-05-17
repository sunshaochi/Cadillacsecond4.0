package cadillac.example.com.cadillac.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 竞品价格保存时候的textlist里面的bean
 * Created by bitch-1 on 2017/6/30.
 */

public class RmkAndfinBean implements Parcelable {
    private String dealerCode;
    private String carBrand;
    private String carClass;
    private String financial;
    private String remark;
    private String quarterName;

    public RmkAndfinBean(String dealerCode, String carBrand, String carClass, String financial, String remark, String quarterName) {
        this.dealerCode = dealerCode;
        this.carBrand = carBrand;
        this.carClass = carClass;
        this.financial = financial;
        this.remark = remark;
        this.quarterName = quarterName;
    }

    protected RmkAndfinBean(Parcel in) {
        dealerCode = in.readString();
        carBrand = in.readString();
        carClass = in.readString();
        financial = in.readString();
        remark = in.readString();
        quarterName = in.readString();
    }

    public static final Creator<RmkAndfinBean> CREATOR = new Creator<RmkAndfinBean>() {
        @Override
        public RmkAndfinBean createFromParcel(Parcel in) {
            return new RmkAndfinBean(in);
        }

        @Override
        public RmkAndfinBean[] newArray(int size) {
            return new RmkAndfinBean[size];
        }
    };

    public String getDealerCode() {
        return dealerCode;
    }

    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getCarClass() {
        return carClass;
    }

    public void setCarClass(String carClass) {
        this.carClass = carClass;
    }

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

    public String getQuarterName() {
        return quarterName;
    }

    public void setQuarterName(String quarterName) {
        this.quarterName = quarterName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(dealerCode);
        parcel.writeString(carBrand);
        parcel.writeString(carClass);
        parcel.writeString(financial);
        parcel.writeString(remark);
        parcel.writeString(quarterName);
    }
}
