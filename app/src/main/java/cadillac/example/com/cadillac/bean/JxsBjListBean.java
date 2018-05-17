package cadillac.example.com.cadillac.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by bitch-1 on 2017/6/22.
 */

public class JxsBjListBean implements Parcelable {

    private String carType;
    private String profitData;
    private String comprehensiveProfit;

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getProfitData() {
        return profitData;
    }

    public void setProfitData(String profitData) {
        this.profitData = profitData;
    }

    public String getComprehensiveProfit() {
        return comprehensiveProfit;
    }

    public void setComprehensiveProfit(String comprehensiveProfit) {
        this.comprehensiveProfit = comprehensiveProfit;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.carType);
        dest.writeString(this.profitData);
        dest.writeString(this.comprehensiveProfit);
    }

    public JxsBjListBean() {
    }

    protected JxsBjListBean(Parcel in) {
        this.carType = in.readString();
        this.profitData = in.readString();
        this.comprehensiveProfit = in.readString();
    }

    public static final Creator<JxsBjListBean> CREATOR = new Creator<JxsBjListBean>() {
        @Override
        public JxsBjListBean createFromParcel(Parcel source) {
            return new JxsBjListBean(source);
        }

        @Override
        public JxsBjListBean[] newArray(int size) {
            return new JxsBjListBean[size];
        }
    };
}
