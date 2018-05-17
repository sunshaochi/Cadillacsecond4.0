package cadillac.example.com.cadillac.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by bitch-1 on 2017/6/28.
 */

public class ChexinBean implements Parcelable{

    /**
     * guidPrice : 35.39
     * financial :
     * remark :
     * purchasePrice : 0
     * carMode : 2.0T 自动 320Li M运动型
     * carId : 1
     */

    private double guidPrice;
    private String financial;
    private String remark;
    private String purchasePrice;
    private String carMode;
    private int carId;

    protected ChexinBean(Parcel in) {
        guidPrice = in.readDouble();
        financial = in.readString();
        remark = in.readString();
        purchasePrice = in.readString();
        carMode = in.readString();
        carId = in.readInt();
    }

    public static final Creator<ChexinBean> CREATOR = new Creator<ChexinBean>() {
        @Override
        public ChexinBean createFromParcel(Parcel in) {
            return new ChexinBean(in);
        }

        @Override
        public ChexinBean[] newArray(int size) {
            return new ChexinBean[size];
        }
    };

    public double getGuidPrice() {
        return guidPrice;
    }

    public void setGuidPrice(double guidPrice) {
        this.guidPrice = guidPrice;
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

    public String getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(String purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public String getCarMode() {
        return carMode;
    }

    public void setCarMode(String carMode) {
        this.carMode = carMode;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(guidPrice);
        parcel.writeString(financial);
        parcel.writeString(remark);
        parcel.writeString(purchasePrice);
        parcel.writeString(carMode);
        parcel.writeInt(carId);
    }
}
