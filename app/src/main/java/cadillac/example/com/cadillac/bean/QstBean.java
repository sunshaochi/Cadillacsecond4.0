package cadillac.example.com.cadillac.bean;

import java.util.List;

/**趋势图bean
 * Created by bitch-1 on 2017/7/4.
 */

public class QstBean {


    /**
     * dealerName : 总部
     * dealerCode : 01
     * dateItems : ["10.30","11.06","11.13","11.20","11.27","12.04","12.11","12.18","12.25","01.01","01.08","01.15","01.22","01.29","01.31"]
     * isEdit : 0
     * carTypeList : ["全部","ATS-L","CT6","XTS","XT5"]
     * list : [{"values":[-43434,-62652,-74251,-45325,-57361,-61054,-73609,-58646,-80522,-69060,-77467,-80566,10654],"name":"ATS-L"},{"values":[-864579,-832614,-867146,-894129,-921155,-866507,-910715,-1010110,-1006319,-964624,-963850,-998137,20000],"name":"CT6"},{"values":[-313978,-130205,192573,322381,304364,289297,230315,254046,269555,199597,193597,205558,30000],"name":"XTS"},{"values":[163434,116270,139665,146939,171093,100844,82481,114071,89042,96621,67390,69180,40000],"name":"XT5"}]
     * userRole : CRPC人员
     */

    private String dealerName;
    private String dealerCode;
    private String isEdit;
    private String userRole;
    private List<String> dateItems;
    private List<String> carTypeList;
    private List<QstVauleBean> list;

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

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public List<String> getDateItems() {
        return dateItems;
    }

    public void setDateItems(List<String> dateItems) {
        this.dateItems = dateItems;
    }

    public List<String> getCarTypeList() {
        return carTypeList;
    }

    public void setCarTypeList(List<String> carTypeList) {
        this.carTypeList = carTypeList;
    }

    public List<QstVauleBean> getList() {
        return list;
    }

    public void setList(List<QstVauleBean> list) {
        this.list = list;
    }


}
