package cadillac.example.com.cadillac.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by bitch-1 on 2017/9/30.
 */

public class JisuBean implements Parcelable{


    /**
     * Models : XTS
     * TotalOrder : 27
     * RetainOrder : 3
     */

    private String Models;
    private String TotalOrder;
    private String RetainOrder;

    protected JisuBean(Parcel in) {
        Models = in.readString();
        TotalOrder = in.readString();
        RetainOrder = in.readString();
    }

    public static final Creator<JisuBean> CREATOR = new Creator<JisuBean>() {
        @Override
        public JisuBean createFromParcel(Parcel in) {
            return new JisuBean(in);
        }

        @Override
        public JisuBean[] newArray(int size) {
            return new JisuBean[size];
        }
    };

    public String getModels() {
        return Models;
    }

    public void setModels(String Models) {
        this.Models = Models;
    }

    public String getTotalOrder() {
        return TotalOrder;
    }

    public void setTotalOrder(String TotalOrder) {
        this.TotalOrder = TotalOrder;
    }

    public String getRetainOrder() {
        return RetainOrder;
    }

    public void setRetainOrder(String RetainOrder) {
        this.RetainOrder = RetainOrder;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Models);
        parcel.writeString(TotalOrder);
        parcel.writeString(RetainOrder);
    }
}
