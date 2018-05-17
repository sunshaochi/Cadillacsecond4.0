package cadillac.example.com.cadillac.bean;

/**
 * Created by bitch-1 on 2017/5/4.
 */

public class SqBean {
    private String name;
    private String username;
    private String dealname;
    private String dealgroup;
    private String teltephone;
    private String display;

    public SqBean(String name, String username, String dealname, String dealgroup, String teltephone, String display) {
        this.name = name;
        this.username = username;
        this.dealname = dealname;
        this.dealgroup = dealgroup;
        this.teltephone = teltephone;
        this.display = display;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDealname() {
        return dealname;
    }

    public void setDealname(String dealname) {
        this.dealname = dealname;
    }

    public String getDealgroup() {
        return dealgroup;
    }

    public void setDealgroup(String dealgroup) {
        this.dealgroup = dealgroup;
    }

    public String getTeltephone() {
        return teltephone;
    }

    public void setTeltephone(String teltephone) {
        this.teltephone = teltephone;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }
}
