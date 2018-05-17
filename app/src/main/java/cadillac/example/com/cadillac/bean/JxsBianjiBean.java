package cadillac.example.com.cadillac.bean;

import java.util.List;

/**
 * 角色为经销商的裸车毛利编辑
 * Created by bitch-1 on 2017/6/22.
 */

public class JxsBianjiBean {


    /**
     * dealerName : 上海东昌
     * dealerCode : CD1000
     * yearMonth : 2017-06-22
     * isEdit : 1
     * inputTimeId : 1879
     * list : [{"carType":"ATS-L","profitData":"0"},{"carType":"CT 6","profitData":"0"},{"carType":"XT 5","profitData":"0"},{"carType":"XTS","profitData":"0"}]
     * userRole : 经销商销售经理
     */

    private String dealerName;
    private String dealerCode;
    private String yearMonth;
    private String isEdit;
    private String inputTimeId;
    private String userRole;
    private List<JxsBjListBean> list;

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

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public String getIsEdit() {
        return isEdit;
    }

    public void setIsEdit(String isEdit) {
        this.isEdit = isEdit;
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

    public List<JxsBjListBean> getList() {
        return list;
    }

    public void setList(List<JxsBjListBean> list) {
        this.list = list;
    }
}
