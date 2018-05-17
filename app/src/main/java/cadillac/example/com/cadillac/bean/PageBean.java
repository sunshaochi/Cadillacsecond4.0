package cadillac.example.com.cadillac.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by bitch-1 on 2017/8/15.
 */

public class PageBean implements Parcelable {

    /**
     * reportData : {"item":"李文涛","t1XinZengJInDian":"10","t1ErCiJinDian":"0","t1DingDan":"1","t1JiaoChe":"2","t1DingDan2":"214","t1DingDanLv":"0.47000000000000003%","t1LiuCun":"208","t2XinZengJInDian":"0","t2ErCiJinDian":"0","t2ErCiJinDianLv":"0%","t2JiaoChe":"0","t2DingDanZhiFuLv":"0%","t2ShiJiaShu":"0","t2ShiJiaLv":"0%","t3TuiDing":"0","t3TuiDingLv":"0%","t3XinxiShangChuanShu":"0","t3XinxiShangChuanLv":"0%","t3WenJuanTianXie":"0","t3WenJuanTianXieLv":"0%"}
     * reportDataList : [{"item":"ATS-L","t1XinZengJInDian":"4","t1ErCiJinDian":"0","t1DingDan":"0","t1JiaoChe":"0","t1DingDan2":"59","t1DingDanLv":"0%","t1LiuCun":"61","t2XinZengJInDian":"0","t2ErCiJinDian":"0","t2ErCiJinDianLv":"0%","t2JiaoChe":"0","t2DingDanZhiFuLv":"0%","t2ShiJiaShu":"0","t2ShiJiaLv":"0%","t3TuiDing":"0","t3TuiDingLv":"0%","t3XinxiShangChuanShu":"0","t3XinxiShangChuanLv":"0%","t3WenJuanTianXie":"0","t3WenJuanTianXieLv":"0%"}]
     */

    private ReportDataBean reportData;
    private List<ReportDataBean> reportDataList;

    public PageBean() {
    }

    public PageBean(ReportDataBean reportData, List<ReportDataBean> reportDataList) {
        this.reportData = reportData;
        this.reportDataList = reportDataList;
    }

    protected PageBean(Parcel in) {
    }

    public static final Creator<PageBean> CREATOR = new Creator<PageBean>() {
        @Override
        public PageBean createFromParcel(Parcel in) {
            return new PageBean(in);
        }

        @Override
        public PageBean[] newArray(int size) {
            return new PageBean[size];
        }
    };

    public ReportDataBean getReportData() {
        return reportData;
    }

    public void setReportData(ReportDataBean reportData) {
        this.reportData = reportData;
    }

    public List<ReportDataBean> getReportDataList() {
        return reportDataList;
    }

    public void setReportDataList(List<ReportDataBean> reportDataList) {
        this.reportDataList = reportDataList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "reportData=" + reportData +
                ", reportDataList=" + reportDataList +
                '}';
    }
}
