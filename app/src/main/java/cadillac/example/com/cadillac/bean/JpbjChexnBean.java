package cadillac.example.com.cadillac.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by bitch-1 on 2017/6/28.
 */

public class JpbjChexnBean implements Parcelable {

    /**
     * list : [{"guidPrice":35.39,"financial":"","remark":"","purchasePrice":"0","carMode":"2.0T 自动 320Li M运动型","carId":1},{"guidPrice":34.99,"financial":"","remark":"","purchasePrice":"0","carMode":"2.0T 自动 320Li xDrive 时尚型","carId":2},{"guidPrice":32.59,"financial":"","remark":"","purchasePrice":"0","carMode":"2.0T 自动 320Li 时尚型","carId":3}]
     * dealerMap : {"dealerName":"上海东昌","dealerCode":"CD1000","endDate":"2017-06-28","quarterName":"2017Q2"}
     */

    private DealerMapBean dealerMap;
    private List<ChexinBean> list;

    protected JpbjChexnBean(Parcel in) {
        list = in.createTypedArrayList(ChexinBean.CREATOR);
    }

    public static final Creator<JpbjChexnBean> CREATOR = new Creator<JpbjChexnBean>() {
        @Override
        public JpbjChexnBean createFromParcel(Parcel in) {
            return new JpbjChexnBean(in);
        }

        @Override
        public JpbjChexnBean[] newArray(int size) {
            return new JpbjChexnBean[size];
        }
    };

    public DealerMapBean getDealerMap() {
        return dealerMap;
    }

    public void setDealerMap(DealerMapBean dealerMap) {
        this.dealerMap = dealerMap;
    }

    public List<ChexinBean> getList() {
        return list;
    }

    public void setList(List<ChexinBean> list) {
        this.list = list;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(list);
    }

    public static class DealerMapBean {
        /**
         * dealerName : 上海东昌
         * dealerCode : CD1000
         * endDate : 2017-06-28
         * quarterName : 2017Q2
         */

        private String dealerName;
        private String dealerCode;
        private String endDate;
        private String quarterName;

        public String getDealerName() {
            return dealerName;
        }

        public void setDealerName(String dealerName) {
            this.dealerName = dealerName;
        }

        public String getDealerCode() {
            return dealerCode;
        }

        public void setDealerCode(String dealerCode) {
            this.dealerCode = dealerCode;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getQuarterName() {
            return quarterName;
        }

        public void setQuarterName(String quarterName) {
            this.quarterName = quarterName;
        }
    }

    public static class ListBean {
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
    }
}
