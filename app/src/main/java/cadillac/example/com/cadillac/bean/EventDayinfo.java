package cadillac.example.com.cadillac.bean;

/**
 * Created by iris on 2018/1/19.
 */

public class EventDayinfo {
    private DayinfoBean infobean;

    public EventDayinfo(DayinfoBean infobean) {
        this.infobean = infobean;
    }

    public DayinfoBean getInfobean() {
        return infobean;
    }

    public void setInfobean(DayinfoBean infobean) {
        this.infobean = infobean;
    }
}
