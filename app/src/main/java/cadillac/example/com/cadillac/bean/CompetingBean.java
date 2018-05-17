package cadillac.example.com.cadillac.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by bitch-1 on 2017/6/26.
 */

public class CompetingBean implements Parcelable{

    /**
     * provinceList : ["不限","上海市","江西省","广东省","山东省","江苏省","浙江省","新疆维吾尔自治区","四川省","云南省","河北省","河南省","贵州省","甘肃省","内蒙古自治区","北京市","湖北省","广西壮族自治区","福建省","黑龙江省","辽宁省","天津市","山西省","宁夏回族自治区","安徽省","湖南省","陕西省","吉林省","海南省","重庆市","青海省"]
     * isEdit : 0
     * list : [{"carBrand":"BMW","data":[{"carClass":"3系"},{"carClass":"5系"},{"carClass":"X3"}]},{"carBrand":"BENZ","data":[{"carClass":"C级"},{"carClass":"E级"},{"carClass":"GLC"}]},{"carBrand":"Audi","data":[{"carClass":"A4L"},{"carClass":"A6L"},{"carClass":"Q5"}]},{"carBrand":"Volvo","data":[{"carClass":"S60"},{"carClass":"XC60"}]},{"carBrand":"Lexus","data":[{"carClass":"ES"}]}]
     */

    private String isEdit;
    private List<String> provinceList;
    private List<CarBrandBean> list;
    private List<CompChexinBean>chexinlist;

    public String getIsEdit() {
        return isEdit;
    }

    public void setIsEdit(String isEdit) {
        this.isEdit = isEdit;
    }

    public List<String> getProvinceList() {
        return provinceList;
    }

    public void setProvinceList(List<String> provinceList) {
        this.provinceList = provinceList;
    }

    public List<CarBrandBean> getList() {
        return list;
    }

    public void setList(List<CarBrandBean> list) {
        this.list = list;
    }

    public List<CompChexinBean> getChexinlist() {
        return chexinlist;
    }

    public void setChexinlist(List<CompChexinBean> chexinlist) {
        this.chexinlist = chexinlist;
    }

    public CompetingBean() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.isEdit);
        dest.writeStringList(this.provinceList);
        dest.writeTypedList(this.list);
        dest.writeTypedList(this.chexinlist);
    }

    protected CompetingBean(Parcel in) {
        this.isEdit = in.readString();
        this.provinceList = in.createStringArrayList();
        this.list = in.createTypedArrayList(CarBrandBean.CREATOR);
        this.chexinlist = in.createTypedArrayList(CompChexinBean.CREATOR);
    }

    public static final Creator<CompetingBean> CREATOR = new Creator<CompetingBean>() {
        @Override
        public CompetingBean createFromParcel(Parcel source) {
            return new CompetingBean(source);
        }

        @Override
        public CompetingBean[] newArray(int size) {
            return new CompetingBean[size];
        }
    };
}
