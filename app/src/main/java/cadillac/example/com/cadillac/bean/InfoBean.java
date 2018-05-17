package cadillac.example.com.cadillac.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by bitch-1 on 2017/4/1.
 */

public class InfoBean implements Parcelable {

    private List<String> Name;//姓名
    private List<String> Tel;//手机
    private List<String> State;//电话

    public InfoBean() {
    }

    protected InfoBean(Parcel in) {
        Name = in.createStringArrayList();
        Tel = in.createStringArrayList();
        State = in.createStringArrayList();
    }

    public static final Creator<InfoBean> CREATOR = new Creator<InfoBean>() {
        @Override
        public InfoBean createFromParcel(Parcel in) {
            return new InfoBean(in);
        }

        @Override
        public InfoBean[] newArray(int size) {
            return new InfoBean[size];
        }
    };

    public List<String> getName() {
        return Name;
    }

    public void setName(List<String> Name) {
        this.Name = Name;
    }

    public List<String> getTel() {
        return Tel;
    }

    public void setTel(List<String> Tel) {
        this.Tel = Tel;
    }

    public List<String> getState() {
        return State;
    }

    public void setState(List<String> State) {
        this.State = State;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringList(Name);
        parcel.writeStringList(Tel);
        parcel.writeStringList(State);
    }
}
