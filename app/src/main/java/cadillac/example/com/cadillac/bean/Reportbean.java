package cadillac.example.com.cadillac.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by bitch-1 on 2017/4/6.
 */

public class Reportbean implements Parcelable {

    private List<String> InRooms;
    private List<String> Orders;

    public Reportbean() {
    }

    protected Reportbean(Parcel in) {
        InRooms = in.createStringArrayList();
        Orders = in.createStringArrayList();
    }

    public static final Creator<Reportbean> CREATOR = new Creator<Reportbean>() {
        @Override
        public Reportbean createFromParcel(Parcel in) {
            return new Reportbean(in);
        }

        @Override
        public Reportbean[] newArray(int size) {
            return new Reportbean[size];
        }
    };

    public List<String> getInRooms() {
        return InRooms;
    }

    public void setInRooms(List<String> InRooms) {
        this.InRooms = InRooms;
    }

    public List<String> getOrders() {
        return Orders;
    }

    public void setOrders(List<String> Orders) {
        this.Orders = Orders;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringList(InRooms);
        parcel.writeStringList(Orders);
    }
}
