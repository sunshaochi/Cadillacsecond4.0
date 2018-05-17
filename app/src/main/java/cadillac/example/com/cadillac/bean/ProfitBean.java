package cadillac.example.com.cadillac.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by iris on 2018/4/23.
 */

public class ProfitBean implements Parcelable {
    private String comprehensiveProfit;

    private String profitData;


    public String getComprehensiveProfit() {
        return comprehensiveProfit;
    }

    public void setComprehensiveProfit(String comprehensiveProfit) {
        this.comprehensiveProfit = comprehensiveProfit;
    }

    public String getProfitData() {
        return profitData;
    }

    public void setProfitData(String profitData) {
        this.profitData = profitData;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.comprehensiveProfit);
        dest.writeString(this.profitData);
    }

    public ProfitBean() {
    }

    protected ProfitBean(Parcel in) {
        this.comprehensiveProfit = in.readString();
        this.profitData = in.readString();
    }

    public static final Parcelable.Creator<ProfitBean> CREATOR = new Parcelable.Creator<ProfitBean>() {
        @Override
        public ProfitBean createFromParcel(Parcel source) {
            return new ProfitBean(source);
        }

        @Override
        public ProfitBean[] newArray(int size) {
            return new ProfitBean[size];
        }
    };
}
