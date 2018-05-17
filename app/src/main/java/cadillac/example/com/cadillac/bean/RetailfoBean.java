package cadillac.example.com.cadillac.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by bitch-1 on 2017/6/6.
 */

public class RetailfoBean implements Parcelable{

    /**
     * dealerName : 上海中致远凯迪
     * dealerCode : CD1001
     * isEdit : 0
     * yearMonth : 2017-06
     * inputTimeId :
     * titles : ["经销商","总计","ATS-L","CT 6","XT 5","XTS"]
     * list : [{"pridictNum":"0","carType":"ATS-L"},{"pridictNum":"0","carType":"CT 6"},{"pridictNum":"0","carType":"XT 5"},{"pridictNum":"0","carType":"XTS"}]
     * userRole : 经销商销售经理
     */

    private String dealerName;
    private String dealerCode;
    private String isEdit;
    private String yearMonth;
    private String inputTimeId;
    private String userRole;
    private List<String> titles;
    private List<ListBean> list;



    protected RetailfoBean(Parcel in) {
        dealerName = in.readString();
        dealerCode = in.readString();
        isEdit = in.readString();
        yearMonth = in.readString();
        inputTimeId = in.readString();
        userRole = in.readString();
        titles = in.createStringArrayList();
    }

    public static final Creator<RetailfoBean> CREATOR = new Creator<RetailfoBean>() {
        @Override
        public RetailfoBean createFromParcel(Parcel in) {
            return new RetailfoBean(in);
        }

        @Override
        public RetailfoBean[] newArray(int size) {
            return new RetailfoBean[size];
        }
    };

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

    public String getIsEdit() {
        return isEdit;
    }

    public void setIsEdit(String isEdit) {
        this.isEdit = isEdit;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public String getInputTimeId() {
        return inputTimeId;
    }

    public void setInputTimeId(String inputTimeId) {
        this.inputTimeId = inputTimeId;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public List<String> getTitles() {
        return titles;
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(dealerName);
        parcel.writeString(dealerCode);
        parcel.writeString(isEdit);
        parcel.writeString(yearMonth);
        parcel.writeString(inputTimeId);
        parcel.writeString(userRole);
        parcel.writeStringList(titles);
    }


}
