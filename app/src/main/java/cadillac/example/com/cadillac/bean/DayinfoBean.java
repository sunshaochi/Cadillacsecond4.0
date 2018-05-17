package cadillac.example.com.cadillac.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by iris on 2017/12/18.
 */

public class DayinfoBean implements Parcelable{

    private DayinfochindBean ttlData;//总计
    private List<DayinfochindBean>detailData;//下面的list

    protected DayinfoBean(Parcel in) {
        ttlData = in.readParcelable(DayinfochindBean.class.getClassLoader());
        detailData = in.createTypedArrayList(DayinfochindBean.CREATOR);
    }

    public static final Creator<DayinfoBean> CREATOR = new Creator<DayinfoBean>() {
        @Override
        public DayinfoBean createFromParcel(Parcel in) {
            return new DayinfoBean(in);
        }

        @Override
        public DayinfoBean[] newArray(int size) {
            return new DayinfoBean[size];
        }
    };

    public DayinfochindBean getTtlData() {
        return ttlData;
    }

    public void setTtlData(DayinfochindBean ttlData) {
        this.ttlData = ttlData;
    }

    public List<DayinfochindBean> getDetailData() {
        return detailData;
    }

    public void setDetailData(List<DayinfochindBean> detailData) {
        this.detailData = detailData;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(ttlData, i);
        parcel.writeTypedList(detailData);
    }
}
