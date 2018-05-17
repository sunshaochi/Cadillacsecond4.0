package cadillac.example.com.cadillac.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * 新的登录后返回的对象
 * Created by iris on 2017/12/15.
 */
@Entity
public class LoginBean implements Parcelable {
     @Id
     private Long _id;
    /**
     * id : 827
     * userName : Alex
     * password : 111111
     * roleId : 2
     * roleCode : 00002
     * roleName : 总部人员
     * mobileNo : 13916313813
     * dealerId : 1
     * dealerCode : 01
     * dealerFullCode : 01
     * dealerType : hq
     * dealerName : 总部
     * groupName : 安徽惠风投资集团
     * personName : 刘震
     * isLogin : Y
     * isLock : N
     * lockTime : 2017-12-06 14:59:11
     * loginTime : 2017-12-15 15:08:01
     * loginVerson : PC
     * admin : N
     * editData : N
     * isDelete : N
     * deleteTime : null
     * createTime : 2017-12-06 13:15:35
     * createUser : system
     * lastUpdateTime : 2017-12-06 13:15:35
     * lastUpdateUser : system
     */


    private int id;
    private String userName;
    private String password;
    private int roleId;
    private String roleCode;
    private String roleName;
    private String mobileNo;
    private int dealerId;
    private String dealerCode;
    private String dealerFullCode;
    private String dealerType;
    private String dealerName;
    private String groupName;
    private String personName;
    private String isLogin;
    private String isLock;
    private String lockTime;
    private String loginTime;
    private String loginVerson;
    private String admin;
    private String editData;
    private String isDelete;
    private String deleteTime;
    private String createTime;
    private String createUser;

    private String lastUpdateTime;
    private String lastUpdateUser;

    private String state;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public int getDealerId() {
        return dealerId;
    }

    public void setDealerId(int dealerId) {
        this.dealerId = dealerId;
    }

    public String getDealerCode() {
        return dealerCode;
    }

    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }

    public String getDealerFullCode() {
        return dealerFullCode;
    }

    public void setDealerFullCode(String dealerFullCode) {
        this.dealerFullCode = dealerFullCode;
    }

    public String getDealerType() {
        return dealerType;
    }

    public void setDealerType(String dealerType) {
        this.dealerType = dealerType;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(String isLogin) {
        this.isLogin = isLogin;
    }

    public String getIsLock() {
        return isLock;
    }

    public void setIsLock(String isLock) {
        this.isLock = isLock;
    }

    public String getLockTime() {
        return lockTime;
    }

    public void setLockTime(String lockTime) {
        this.lockTime = lockTime;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getLoginVerson() {
        return loginVerson;
    }

    public void setLoginVerson(String loginVerson) {
        this.loginVerson = loginVerson;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getEditData() {
        return editData;
    }

    public void setEditData(String editData) {
        this.editData = editData;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public String getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(String deleteTime) {
        this.deleteTime = deleteTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getLastUpdateUser() {
        return lastUpdateUser;
    }

    public void setLastUpdateUser(String lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    public LoginBean() {
    }

    @Generated(hash = 1525564300)
    public LoginBean(Long _id, int id, String userName, String password, int roleId, String roleCode,
            String roleName, String mobileNo, int dealerId, String dealerCode, String dealerFullCode,
            String dealerType, String dealerName, String groupName, String personName, String isLogin,
            String isLock, String lockTime, String loginTime, String loginVerson, String admin,
            String editData, String isDelete, String deleteTime, String createTime, String createUser,
            String lastUpdateTime, String lastUpdateUser, String state) {
        this._id = _id;
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.roleId = roleId;
        this.roleCode = roleCode;
        this.roleName = roleName;
        this.mobileNo = mobileNo;
        this.dealerId = dealerId;
        this.dealerCode = dealerCode;
        this.dealerFullCode = dealerFullCode;
        this.dealerType = dealerType;
        this.dealerName = dealerName;
        this.groupName = groupName;
        this.personName = personName;
        this.isLogin = isLogin;
        this.isLock = isLock;
        this.lockTime = lockTime;
        this.loginTime = loginTime;
        this.loginVerson = loginVerson;
        this.admin = admin;
        this.editData = editData;
        this.isDelete = isDelete;
        this.deleteTime = deleteTime;
        this.createTime = createTime;
        this.createUser = createUser;
        this.lastUpdateTime = lastUpdateTime;
        this.lastUpdateUser = lastUpdateUser;
        this.state = state;
    }

    public Long get_id() {
        return this._id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this._id);
        dest.writeInt(this.id);
        dest.writeString(this.userName);
        dest.writeString(this.password);
        dest.writeInt(this.roleId);
        dest.writeString(this.roleCode);
        dest.writeString(this.roleName);
        dest.writeString(this.mobileNo);
        dest.writeInt(this.dealerId);
        dest.writeString(this.dealerCode);
        dest.writeString(this.dealerFullCode);
        dest.writeString(this.dealerType);
        dest.writeString(this.dealerName);
        dest.writeString(this.groupName);
        dest.writeString(this.personName);
        dest.writeString(this.isLogin);
        dest.writeString(this.isLock);
        dest.writeString(this.lockTime);
        dest.writeString(this.loginTime);
        dest.writeString(this.loginVerson);
        dest.writeString(this.admin);
        dest.writeString(this.editData);
        dest.writeString(this.isDelete);
        dest.writeString(this.deleteTime);
        dest.writeString(this.createTime);
        dest.writeString(this.createUser);
        dest.writeString(this.lastUpdateTime);
        dest.writeString(this.lastUpdateUser);
        dest.writeString(this.state);
    }

    protected LoginBean(Parcel in) {
        this._id = (Long) in.readValue(Long.class.getClassLoader());
        this.id = in.readInt();
        this.userName = in.readString();
        this.password = in.readString();
        this.roleId = in.readInt();
        this.roleCode = in.readString();
        this.roleName = in.readString();
        this.mobileNo = in.readString();
        this.dealerId = in.readInt();
        this.dealerCode = in.readString();
        this.dealerFullCode = in.readString();
        this.dealerType = in.readString();
        this.dealerName = in.readString();
        this.groupName = in.readString();
        this.personName = in.readString();
        this.isLogin = in.readString();
        this.isLock = in.readString();
        this.lockTime = in.readString();
        this.loginTime = in.readString();
        this.loginVerson = in.readString();
        this.admin = in.readString();
        this.editData = in.readString();
        this.isDelete = in.readString();
        this.deleteTime = in.readString();
        this.createTime = in.readString();
        this.createUser = in.readString();
        this.lastUpdateTime = in.readString();
        this.lastUpdateUser = in.readString();
        this.state = in.readString();
    }

    public static final Creator<LoginBean> CREATOR = new Creator<LoginBean>() {
        @Override
        public LoginBean createFromParcel(Parcel source) {
            return new LoginBean(source);
        }

        @Override
        public LoginBean[] newArray(int size) {
            return new LoginBean[size];
        }
    };
}
