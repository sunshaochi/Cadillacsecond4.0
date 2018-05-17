package cadillac.example.com.cadillac.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by bitch-1 on 2017/8/15.
 */

public class ReportDataBean implements Parcelable{

    /**
     * item : 李文涛
     * t1XinZengJInDian : 10
     * t1ErCiJinDian : 0
     * t1DingDan : 1
     * t1JiaoChe : 2
     * t1DingDan2 : 214
     * t1DingDanLv : 0.47000000000000003%
     * t1LiuCun : 208
     * t2XinZengJInDian : 0
     * t2ErCiJinDian : 0
     * t2ErCiJinDianLv : 0%
     * t2JiaoChe : 0
     * t2DingDanZhiFuLv : 0%
     * t2ShiJiaShu : 0
     * t2ShiJiaLv : 0%
     * t3TuiDing : 0
     * t3TuiDingLv : 0%
     * t3XinxiShangChuanShu : 0
     * t3XinxiShangChuanLv : 0%
     * t3WenJuanTianXie : 0
     * t3WenJuanTianXieLv : 0%
     */

    private String item;
    private String t1XinZengJInDian;
    private String t1ErCiJinDian;
    private String t1DingDan;
    private String t1JiaoChe;
    private String t1DingDan2;
    private String t1DingDanLv;
    private String t1LiuCun;
    private String t2XinZengJInDian;
    private String t2ErCiJinDian;
    private String t2ErCiJinDianLv;
    private String t2JiaoChe;
    private String t2DingDanZhiFuLv;
    private String t2ShiJiaShu;
    private String t2ShiJiaLv;
    private String t3TuiDing;
    private String t3TuiDingLv;
    private String t3XinxiShangChuanShu;
    private String t3XinxiShangChuanLv;
    private String t3WenJuanTianXie;
    private String t3WenJuanTianXieLv;

    public ReportDataBean(String item, String t1XinZengJInDian, String t1ErCiJinDian, String t1DingDan, String t1JiaoChe, String t1DingDan2, String t1DingDanLv, String t1LiuCun, String t2XinZengJInDian, String t2ErCiJinDian, String t2ErCiJinDianLv, String t2JiaoChe, String t2DingDanZhiFuLv, String t2ShiJiaShu, String t2ShiJiaLv, String t3TuiDing, String t3TuiDingLv, String t3XinxiShangChuanShu, String t3XinxiShangChuanLv, String t3WenJuanTianXie, String t3WenJuanTianXieLv) {
        this.item = item;
        this.t1XinZengJInDian = t1XinZengJInDian;
        this.t1ErCiJinDian = t1ErCiJinDian;
        this.t1DingDan = t1DingDan;
        this.t1JiaoChe = t1JiaoChe;
        this.t1DingDan2 = t1DingDan2;
        this.t1DingDanLv = t1DingDanLv;
        this.t1LiuCun = t1LiuCun;
        this.t2XinZengJInDian = t2XinZengJInDian;
        this.t2ErCiJinDian = t2ErCiJinDian;
        this.t2ErCiJinDianLv = t2ErCiJinDianLv;
        this.t2JiaoChe = t2JiaoChe;
        this.t2DingDanZhiFuLv = t2DingDanZhiFuLv;
        this.t2ShiJiaShu = t2ShiJiaShu;
        this.t2ShiJiaLv = t2ShiJiaLv;
        this.t3TuiDing = t3TuiDing;
        this.t3TuiDingLv = t3TuiDingLv;
        this.t3XinxiShangChuanShu = t3XinxiShangChuanShu;
        this.t3XinxiShangChuanLv = t3XinxiShangChuanLv;
        this.t3WenJuanTianXie = t3WenJuanTianXie;
        this.t3WenJuanTianXieLv = t3WenJuanTianXieLv;
    }

    protected ReportDataBean(Parcel in) {
        item = in.readString();
        t1XinZengJInDian = in.readString();
        t1ErCiJinDian = in.readString();
        t1DingDan = in.readString();
        t1JiaoChe = in.readString();
        t1DingDan2 = in.readString();
        t1DingDanLv = in.readString();
        t1LiuCun = in.readString();
        t2XinZengJInDian = in.readString();
        t2ErCiJinDian = in.readString();
        t2ErCiJinDianLv = in.readString();
        t2JiaoChe = in.readString();
        t2DingDanZhiFuLv = in.readString();
        t2ShiJiaShu = in.readString();
        t2ShiJiaLv = in.readString();
        t3TuiDing = in.readString();
        t3TuiDingLv = in.readString();
        t3XinxiShangChuanShu = in.readString();
        t3XinxiShangChuanLv = in.readString();
        t3WenJuanTianXie = in.readString();
        t3WenJuanTianXieLv = in.readString();
    }

    public static final Creator<ReportDataBean> CREATOR = new Creator<ReportDataBean>() {
        @Override
        public ReportDataBean createFromParcel(Parcel in) {
            return new ReportDataBean(in);
        }

        @Override
        public ReportDataBean[] newArray(int size) {
            return new ReportDataBean[size];
        }
    };

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getT1XinZengJInDian() {
        return t1XinZengJInDian;
    }

    public void setT1XinZengJInDian(String t1XinZengJInDian) {
        this.t1XinZengJInDian = t1XinZengJInDian;
    }

    public String getT1ErCiJinDian() {
        return t1ErCiJinDian;
    }

    public void setT1ErCiJinDian(String t1ErCiJinDian) {
        this.t1ErCiJinDian = t1ErCiJinDian;
    }

    public String getT1DingDan() {
        return t1DingDan;
    }

    public void setT1DingDan(String t1DingDan) {
        this.t1DingDan = t1DingDan;
    }

    public String getT1JiaoChe() {
        return t1JiaoChe;
    }

    public void setT1JiaoChe(String t1JiaoChe) {
        this.t1JiaoChe = t1JiaoChe;
    }

    public String getT1DingDan2() {
        return t1DingDan2;
    }

    public void setT1DingDan2(String t1DingDan2) {
        this.t1DingDan2 = t1DingDan2;
    }

    public String getT1DingDanLv() {
        return t1DingDanLv;
    }

    public void setT1DingDanLv(String t1DingDanLv) {
        this.t1DingDanLv = t1DingDanLv;
    }

    public String getT1LiuCun() {
        return t1LiuCun;
    }

    public void setT1LiuCun(String t1LiuCun) {
        this.t1LiuCun = t1LiuCun;
    }

    public String getT2XinZengJInDian() {
        return t2XinZengJInDian;
    }

    public void setT2XinZengJInDian(String t2XinZengJInDian) {
        this.t2XinZengJInDian = t2XinZengJInDian;
    }

    public String getT2ErCiJinDian() {
        return t2ErCiJinDian;
    }

    public void setT2ErCiJinDian(String t2ErCiJinDian) {
        this.t2ErCiJinDian = t2ErCiJinDian;
    }

    public String getT2ErCiJinDianLv() {
        return t2ErCiJinDianLv;
    }

    public void setT2ErCiJinDianLv(String t2ErCiJinDianLv) {
        this.t2ErCiJinDianLv = t2ErCiJinDianLv;
    }

    public String getT2JiaoChe() {
        return t2JiaoChe;
    }

    public void setT2JiaoChe(String t2JiaoChe) {
        this.t2JiaoChe = t2JiaoChe;
    }

    public String getT2DingDanZhiFuLv() {
        return t2DingDanZhiFuLv;
    }

    public void setT2DingDanZhiFuLv(String t2DingDanZhiFuLv) {
        this.t2DingDanZhiFuLv = t2DingDanZhiFuLv;
    }

    public String getT2ShiJiaShu() {
        return t2ShiJiaShu;
    }

    public void setT2ShiJiaShu(String t2ShiJiaShu) {
        this.t2ShiJiaShu = t2ShiJiaShu;
    }

    public String getT2ShiJiaLv() {
        return t2ShiJiaLv;
    }

    public void setT2ShiJiaLv(String t2ShiJiaLv) {
        this.t2ShiJiaLv = t2ShiJiaLv;
    }

    public String getT3TuiDing() {
        return t3TuiDing;
    }

    public void setT3TuiDing(String t3TuiDing) {
        this.t3TuiDing = t3TuiDing;
    }

    public String getT3TuiDingLv() {
        return t3TuiDingLv;
    }

    public void setT3TuiDingLv(String t3TuiDingLv) {
        this.t3TuiDingLv = t3TuiDingLv;
    }

    public String getT3XinxiShangChuanShu() {
        return t3XinxiShangChuanShu;
    }

    public void setT3XinxiShangChuanShu(String t3XinxiShangChuanShu) {
        this.t3XinxiShangChuanShu = t3XinxiShangChuanShu;
    }

    public String getT3XinxiShangChuanLv() {
        return t3XinxiShangChuanLv;
    }

    public void setT3XinxiShangChuanLv(String t3XinxiShangChuanLv) {
        this.t3XinxiShangChuanLv = t3XinxiShangChuanLv;
    }

    public String getT3WenJuanTianXie() {
        return t3WenJuanTianXie;
    }

    public void setT3WenJuanTianXie(String t3WenJuanTianXie) {
        this.t3WenJuanTianXie = t3WenJuanTianXie;
    }

    public String getT3WenJuanTianXieLv() {
        return t3WenJuanTianXieLv;
    }

    public void setT3WenJuanTianXieLv(String t3WenJuanTianXieLv) {
        this.t3WenJuanTianXieLv = t3WenJuanTianXieLv;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(item);
        parcel.writeString(t1XinZengJInDian);
        parcel.writeString(t1ErCiJinDian);
        parcel.writeString(t1DingDan);
        parcel.writeString(t1JiaoChe);
        parcel.writeString(t1DingDan2);
        parcel.writeString(t1DingDanLv);
        parcel.writeString(t1LiuCun);
        parcel.writeString(t2XinZengJInDian);
        parcel.writeString(t2ErCiJinDian);
        parcel.writeString(t2ErCiJinDianLv);
        parcel.writeString(t2JiaoChe);
        parcel.writeString(t2DingDanZhiFuLv);
        parcel.writeString(t2ShiJiaShu);
        parcel.writeString(t2ShiJiaLv);
        parcel.writeString(t3TuiDing);
        parcel.writeString(t3TuiDingLv);
        parcel.writeString(t3XinxiShangChuanShu);
        parcel.writeString(t3XinxiShangChuanLv);
        parcel.writeString(t3WenJuanTianXie);
        parcel.writeString(t3WenJuanTianXieLv);
    }

    @Override
    public String toString() {
        return "ReportDataBean{" +
                "item='" + item + '\'' +
                ", t1XinZengJInDian='" + t1XinZengJInDian + '\'' +
                ", t1ErCiJinDian='" + t1ErCiJinDian + '\'' +
                ", t1DingDan='" + t1DingDan + '\'' +
                ", t1JiaoChe='" + t1JiaoChe + '\'' +
                ", t1DingDan2='" + t1DingDan2 + '\'' +
                ", t1DingDanLv='" + t1DingDanLv + '\'' +
                ", t1LiuCun='" + t1LiuCun + '\'' +
                ", t2XinZengJInDian='" + t2XinZengJInDian + '\'' +
                ", t2ErCiJinDian='" + t2ErCiJinDian + '\'' +
                ", t2ErCiJinDianLv='" + t2ErCiJinDianLv + '\'' +
                ", t2JiaoChe='" + t2JiaoChe + '\'' +
                ", t2DingDanZhiFuLv='" + t2DingDanZhiFuLv + '\'' +
                ", t2ShiJiaShu='" + t2ShiJiaShu + '\'' +
                ", t2ShiJiaLv='" + t2ShiJiaLv + '\'' +
                ", t3TuiDing='" + t3TuiDing + '\'' +
                ", t3TuiDingLv='" + t3TuiDingLv + '\'' +
                ", t3XinxiShangChuanShu='" + t3XinxiShangChuanShu + '\'' +
                ", t3XinxiShangChuanLv='" + t3XinxiShangChuanLv + '\'' +
                ", t3WenJuanTianXie='" + t3WenJuanTianXie + '\'' +
                ", t3WenJuanTianXieLv='" + t3WenJuanTianXieLv + '\'' +
                '}';
    }
}
