package cadillac.example.com.cadillac.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 查看申请
 * Created by iris on 2018/1/26.
 */

public class CheckApplyBean implements Parcelable{

    /**
     * id : 1670
     * userName : y34214
     * password : 111111
     * roleId : 7
     * roleCode : 00007
     * roleName : 经销商总经理
     * mobileNo : 16721258189
     * dealerId : 28
     * dealerCode : CD1000
     * dealerFullCode : 01.001.008.CD1000
     * dealerType : dealer
     * dealerName : 上海东昌
     * groupName : 上海东昌集团
     * personName : 324124
     * isLogin : N
     * isLock : Y
     * lockTime : 11.1
     * loginTime : 11.1
     * loginVerson : 11.1
     * admin : 11.1
     * editData : 11.1
     * isDelete : N
     * deleteTime : 11.1
     * createTime : 2018-01-23 20:54:20
     * createUser : system
     * lastUpdateTime : 2018-01-23 20:54:20
     * lastUpdateUser : system
     * state : 3
     */

    private String id;
    private String userName;
    private String password;
    private String roleId;
    private String roleCode;
    private String roleName;
    private String mobileNo;
    private String dealerId;
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

    protected CheckApplyBean(Parcel in) {
        id = in.readString();
        userName = in.readString();
        password = in.readString();
        roleId = in.readString();
        roleCode = in.readString();
        roleName = in.readString();
        mobileNo = in.readString();
        dealerId = in.readString();
        dealerCode = in.readString();
        dealerFullCode = in.readString();
        dealerType = in.readString();
        dealerName = in.readString();
        groupName = in.readString();
        personName = in.readString();
        isLogin = in.readString();
        isLock = in.readString();
        lockTime = in.readString();
        loginTime = in.readString();
        loginVerson = in.readString();
        admin = in.readString();
        editData = in.readString();
        isDelete = in.readString();
        deleteTime = in.readString();
        createTime = in.readString();
        createUser = in.readString();
        lastUpdateTime = in.readString();
        lastUpdateUser = in.readString();
        state = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(userName);
        dest.writeString(password);
        dest.writeString(roleId);
        dest.writeString(roleCode);
        dest.writeString(roleName);
        dest.writeString(mobileNo);
        dest.writeString(dealerId);
        dest.writeString(dealerCode);
        dest.writeString(dealerFullCode);
        dest.writeString(dealerType);
        dest.writeString(dealerName);
        dest.writeString(groupName);
        dest.writeString(personName);
        dest.writeString(isLogin);
        dest.writeString(isLock);
        dest.writeString(lockTime);
        dest.writeString(loginTime);
        dest.writeString(loginVerson);
        dest.writeString(admin);
        dest.writeString(editData);
        dest.writeString(isDelete);
        dest.writeString(deleteTime);
        dest.writeString(createTime);
        dest.writeString(createUser);
        dest.writeString(lastUpdateTime);
        dest.writeString(lastUpdateUser);
        dest.writeString(state);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CheckApplyBean> CREATOR = new Creator<CheckApplyBean>() {
        @Override
        public CheckApplyBean createFromParcel(Parcel in) {
            return new CheckApplyBean(in);
        }

        @Override
        public CheckApplyBean[] newArray(int size) {
            return new CheckApplyBean[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
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

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
