package cadillac.example.com.cadillac.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by bitch-1 on 2017/3/30.
 */

public class SalseDateBean implements Parcelable {

    /**
     * Models : SRX
     * Call : 0
     * ShowRoom : 0
     * Order : 0
     * Retail : 0
     * TotalOrder : 1
     * RetainOrder : 4
     * Unsubscribe : 0
     * secRoom :0 在次进店
     */

    private String Models;//车型
    private String Call;//来电
    private String ShowRoom;//进店
    private String Order;//订单
    private String Retail;//交车
    private String TotalOrder;//月累计
    private String RetainOrder;//留存
    private String Unsubscribe;//退订
    private String secRoom;//在次进店

    private String TransRate;//0.0%
    private String DealRate;//0.0%
    private String Area;//Cadillac一区


    public String getSecRoom() {
        return secRoom;
    }

    public void setSecRoom(String secRoom) {
        this.secRoom = secRoom;
    }

    public String getModels() {
        return Models;
    }

    public void setModels(String Models) {
        this.Models = Models;
    }

    public String getCall() {
        return Call;
    }

    public void setCall(String Call) {
        this.Call = Call;
    }

    public String getShowRoom() {
        return ShowRoom;
    }

    public void setShowRoom(String ShowRoom) {
        this.ShowRoom = ShowRoom;
    }

    public String getOrder() {
        return Order;
    }

    public void setOrder(String Order) {
        this.Order = Order;
    }

    public String getRetail() {
        return Retail;
    }

    public void setRetail(String Retail) {
        this.Retail = Retail;
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

    public String getUnsubscribe() {
        return Unsubscribe;
    }

    public void setUnsubscribe(String Unsubscribe) {
        this.Unsubscribe = Unsubscribe;
    }

    public String getTransRate() {
        return TransRate;
    }

    public void setTransRate(String transRate) {
        TransRate = transRate;
    }

    public String getDealRate() {
        return DealRate;
    }

    public void setDealRate(String dealRate) {
        DealRate = dealRate;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }


    public SalseDateBean() {
    }


    public SalseDateBean(String models, String call, String showRoom, String order, String retail, String totalOrder, String retainOrder, String unsubscribe, String transRate, String dealRate, String area) {
        Models = models;
        Call = call;
        ShowRoom = showRoom;
        Order = order;
        Retail = retail;
        TotalOrder = totalOrder;
        RetainOrder = retainOrder;
        Unsubscribe = unsubscribe;
        TransRate = transRate;
        DealRate = dealRate;
        Area = area;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Models);
        dest.writeString(this.Call);
        dest.writeString(this.ShowRoom);
        dest.writeString(this.Order);
        dest.writeString(this.Retail);
        dest.writeString(this.TotalOrder);
        dest.writeString(this.RetainOrder);
        dest.writeString(this.Unsubscribe);
        dest.writeString(this.secRoom);
        dest.writeString(this.TransRate);
        dest.writeString(this.DealRate);
        dest.writeString(this.Area);
    }

    protected SalseDateBean(Parcel in) {
        this.Models = in.readString();
        this.Call = in.readString();
        this.ShowRoom = in.readString();
        this.Order = in.readString();
        this.Retail = in.readString();
        this.TotalOrder = in.readString();
        this.RetainOrder = in.readString();
        this.Unsubscribe = in.readString();
        this.secRoom = in.readString();
        this.TransRate = in.readString();
        this.DealRate = in.readString();
        this.Area = in.readString();
    }

    public static final Creator<SalseDateBean> CREATOR = new Creator<SalseDateBean>() {
        @Override
        public SalseDateBean createFromParcel(Parcel source) {
            return new SalseDateBean(source);
        }

        @Override
        public SalseDateBean[] newArray(int size) {
            return new SalseDateBean[size];
        }
    };
}
