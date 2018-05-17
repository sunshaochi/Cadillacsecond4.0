package cadillac.example.com.cadillac.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by bitch-1 on 2017/3/30.
 */
public class TimeBean implements Parcelable {

    /**
     * Time : 19:00
     * Deadline : 22:00
     * Fire : 19:00
     * TimeArray : ["2017-3-30"]
     */

    private String Time;
    private String Deadline;
    private String Fire;
    private List<String> TimeArray;

    protected TimeBean(Parcel in) {
        Time = in.readString();
        Deadline = in.readString();
        Fire = in.readString();
        TimeArray = in.createStringArrayList();
    }

    public static final Creator<TimeBean> CREATOR = new Creator<TimeBean>() {
        @Override
        public TimeBean createFromParcel(Parcel in) {
            return new TimeBean(in);
        }

        @Override
        public TimeBean[] newArray(int size) {
            return new TimeBean[size];
        }
    };

    public String getTime() {
        return Time;
    }

    public void setTime(String Time) {
        this.Time = Time;
    }

    public String getDeadline() {
        return Deadline;
    }

    public void setDeadline(String Deadline) {
        this.Deadline = Deadline;
    }

    public String getFire() {
        return Fire;
    }

    public void setFire(String Fire) {
        this.Fire = Fire;
    }

    public List<String> getTimeArray() {
        return TimeArray;
    }

    public void setTimeArray(List<String> TimeArray) {
        this.TimeArray = TimeArray;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Time);
        parcel.writeString(Deadline);
        parcel.writeString(Fire);
        parcel.writeStringList(TimeArray);
    }
}
