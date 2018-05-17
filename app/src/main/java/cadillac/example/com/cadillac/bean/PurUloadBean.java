package cadillac.example.com.cadillac.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**竞品价格里面上传的list里面的实体
 * Created by bitch-1 on 2017/6/30.
 */

public class PurUloadBean implements Parcelable {

    private String carId;
    private String dealerCode;
    private String purchasePrice;
    private String financial;
    private String remark;

    public PurUloadBean(String carId, String dealerCode, String purchasePrice, String financial, String remark) {
        this.carId = carId;
        this.dealerCode = dealerCode;
        this.purchasePrice = purchasePrice;
        this.financial = financial;
        this.remark = remark;
    }

    protected PurUloadBean(Parcel in) {
        carId = in.readString();
        dealerCode = in.readString();
        purchasePrice = in.readString();
        financial = in.readString();
        remark = in.readString();
    }

    public static final Creator<PurUloadBean> CREATOR = new Creator<PurUloadBean>() {
        @Override
        public PurUloadBean createFromParcel(Parcel in) {
            return new PurUloadBean(in);
        }

        @Override
        public PurUloadBean[] newArray(int size) {
            return new PurUloadBean[size];
        }
    };

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getDealerCode() {
        return dealerCode;
    }

    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }

    public String getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(String purchasePrice) {
        this.purchasePrice = purchasePrice;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(carId);
        parcel.writeString(dealerCode);
        parcel.writeString(purchasePrice);
        parcel.writeString(financial);
        parcel.writeString(remark);
    }
}
