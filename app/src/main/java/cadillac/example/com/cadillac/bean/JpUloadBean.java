package cadillac.example.com.cadillac.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 要往上传的值
 * Created by bitch-1 on 2017/6/30.
 */

public class JpUloadBean implements Parcelable {
    private List<RmkAndfinBean>textList;
    private List<PurUloadBean>list;

    public JpUloadBean() {
    }

    public List<RmkAndfinBean> getTextList() {
        return textList;
    }

    public void setTextList(List<RmkAndfinBean> textList) {
        this.textList = textList;
    }

    public List<PurUloadBean> getList() {
        return list;
    }

    public void setList(List<PurUloadBean> list) {
        this.list = list;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.textList);
        dest.writeTypedList(this.list);
    }

    protected JpUloadBean(Parcel in) {
        this.textList = in.createTypedArrayList(RmkAndfinBean.CREATOR);
        this.list = in.createTypedArrayList(PurUloadBean.CREATOR);
    }

    public static final Creator<JpUloadBean> CREATOR = new Creator<JpUloadBean>() {
        @Override
        public JpUloadBean createFromParcel(Parcel source) {
            return new JpUloadBean(source);
        }

        @Override
        public JpUloadBean[] newArray(int size) {
            return new JpUloadBean[size];
        }
    };
}
