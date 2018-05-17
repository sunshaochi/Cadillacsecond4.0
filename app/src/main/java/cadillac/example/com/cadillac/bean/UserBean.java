package cadillac.example.com.cadillac.bean;

import android.os.Parcel;
import android.os.Parcelable;


import java.util.List;
import java.util.Map;

/**
 * Created by bitch-1 on 2017/3/28.
 */

public class UserBean implements Parcelable {

    /**
     * Role : 经销商销售经理
     * Tel :
     * JXSCode : AB1234
     * JXSName : 测试经销商
     * Group :
     * UpName :
     * UpLevel : 小区经理
     * Name : 朱思捷
     * Info : 成功
     * Ver : 4
     * Models : ["SRX","XTS","ATS-L","CT 6","XT 5","CTS"]
     */

    private String Role;

    private String Tel;

    private String JXSCode;

    private String JXSName;

    private String Group;

    private String UpName;

    private String UpLevel;

    private String Name;

    private String Info;

    private String Ver;


    private List<String> Models;

    protected UserBean(Parcel in) {
        Role = in.readString();
        Tel = in.readString();
        JXSCode = in.readString();
        JXSName = in.readString();
        Group = in.readString();
        UpName = in.readString();
        UpLevel = in.readString();
        Name = in.readString();
        Info = in.readString();
        Ver = in.readString();
        Models = in.createStringArrayList();

    }

    public static final Creator<UserBean> CREATOR = new Creator<UserBean>() {
        @Override
        public UserBean createFromParcel(Parcel in) {
            return new UserBean(in);
        }

        @Override
        public UserBean[] newArray(int size) {
            return new UserBean[size];
        }
    };

    public UserBean() {
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String Role) {
        this.Role = Role;
    }

    public String getTel() {
        return Tel;
    }

    public void setTel(String Tel) {
        this.Tel = Tel;
    }

    public String getJXSCode() {
        return JXSCode;
    }

    public void setJXSCode(String JXSCode) {
        this.JXSCode = JXSCode;
    }

    public String getJXSName() {
        return JXSName;
    }

    public void setJXSName(String JXSName) {
        this.JXSName = JXSName;
    }

    public String getGroup() {
        return Group;
    }

    public void setGroup(String Group) {
        this.Group = Group;
    }

    public String getUpName() {
        return UpName;
    }

    public void setUpName(String UpName) {
        this.UpName = UpName;
    }

    public String getUpLevel() {
        return UpLevel;
    }

    public void setUpLevel(String UpLevel) {
        this.UpLevel = UpLevel;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getInfo() {
        return Info;
    }

    public void setInfo(String Info) {
        this.Info = Info;
    }

    public String getVer() {
        return Ver;
    }

    public void setVer(String Ver) {
        this.Ver = Ver;
    }

    public List<String> getModels() {
        return Models;
    }

    public void setModels(List<String> Models) {
        this.Models = Models;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Role);
        parcel.writeString(Tel);
        parcel.writeString(JXSCode);
        parcel.writeString(JXSName);
        parcel.writeString(Group);
        parcel.writeString(UpName);
        parcel.writeString(UpLevel);
        parcel.writeString(Name);
        parcel.writeString(Info);
        parcel.writeString(Ver);
        parcel.writeStringList(Models);
    }
}
