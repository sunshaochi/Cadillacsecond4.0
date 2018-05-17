package cadillac.example.com.cadillac.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by iris on 2017/12/28.
 */

public class LoadBean implements Parcelable{


    /**
     * id : 1718603
     * dataDate : 2017-12-15 00:00:00
     * dealerId : 238
     * dealerFullCode : 01.002.010.CD1797
     * dealerName : 芜湖亚德
     * carModelId : 1001
     * carModelCode : ATS-L
     * carModelName : ATS-L
     * year : 2017
     * season : 4
     * month : 12
     * weekSeq : 1750
     * orderCnt : 0
     * orderCntShow : 0
     * callCnt : 0
     * incomeCnt : 0
     * deliveryCnt : 0
     * secondIncomeCnt : 0
     * tryDriveCnt : 0
     * tryDriveAllCnt : 0
     * infoUploadCnt : 0
     * questionCnt : 0
     * cancelOrderCnt : 0
     * remainOrderCnt : 20
     * ttlOrderCnt : 4
     * ttlOrderCnttShow : 0
     * ttlCallCnt : 0
     * ttlIncomeCnt : 30
     * ttlDeliveryCnt : 3
     * ttlSecondIncomeCnt : 4
     * ttlTryDriveCnt : 5
     * ttlTryDriveAllCnt : 5
     * ttlInfoUploadCnt : 6
     * ttlQuestionCnt : 4
     * ttlCancelOrderCnt : 0
     * preRemainOrderCnt : 20
     * createTime : 2017-12-18 10:11:52
     * createUser : system
     * lastUpdateTime : 2017-12-18 10:11:52
     * lastUpdateUser : system
     */

    private String id;
    private String dataDate;
    private String dealerId;
    private String dealerFullCode;
    private String dealerName;
    private String carModelId;
    private String carModelCode;
    private String carModelName;
    private String year;
    private String season;
    private String month;
    private String weekSeq;
    private String orderCnt;
    private String orderCntShow;
    private String callCnt;
    private String incomeCnt;
    private String deliveryCnt;
    private String secondIncomeCnt;
    private String tryDriveCnt;
    private String tryDriveAllCnt;
    private String infoUploadCnt;
    private String questionCnt;
    private String cancelOrderCnt;
    private String remainOrderCnt;
    private String ttlOrderCnt;
    private String ttlOrderCnttShow;
    private String ttlCallCnt;
    private String ttlIncomeCnt;
    private String ttlDeliveryCnt;
    private String ttlSecondIncomeCnt;
    private String ttlTryDriveCnt;
    private String ttlTryDriveAllCnt;
    private String ttlInfoUploadCnt;
    private String ttlQuestionCnt;
    private String ttlCancelOrderCnt;
    private String preRemainOrderCnt;
    private String createTime;
    private String createUser;
    private String lastUpdateTime;
    private String lastUpdateUser;

    protected LoadBean(Parcel in) {
        id = in.readString();
        dataDate = in.readString();
        dealerId = in.readString();
        dealerFullCode = in.readString();
        dealerName = in.readString();
        carModelId = in.readString();
        carModelCode = in.readString();
        carModelName = in.readString();
        year = in.readString();
        season = in.readString();
        month = in.readString();
        weekSeq = in.readString();
        orderCnt = in.readString();
        orderCntShow = in.readString();
        callCnt = in.readString();
        incomeCnt = in.readString();
        deliveryCnt = in.readString();
        secondIncomeCnt = in.readString();
        tryDriveCnt = in.readString();
        tryDriveAllCnt = in.readString();
        infoUploadCnt = in.readString();
        questionCnt = in.readString();
        cancelOrderCnt = in.readString();
        remainOrderCnt = in.readString();
        ttlOrderCnt = in.readString();
        ttlOrderCnttShow = in.readString();
        ttlCallCnt = in.readString();
        ttlIncomeCnt = in.readString();
        ttlDeliveryCnt = in.readString();
        ttlSecondIncomeCnt = in.readString();
        ttlTryDriveCnt = in.readString();
        ttlTryDriveAllCnt = in.readString();
        ttlInfoUploadCnt = in.readString();
        ttlQuestionCnt = in.readString();
        ttlCancelOrderCnt = in.readString();
        preRemainOrderCnt = in.readString();
        createTime = in.readString();
        createUser = in.readString();
        lastUpdateTime = in.readString();
        lastUpdateUser = in.readString();
    }

    public static final Creator<LoadBean> CREATOR = new Creator<LoadBean>() {
        @Override
        public LoadBean createFromParcel(Parcel in) {
            return new LoadBean(in);
        }

        @Override
        public LoadBean[] newArray(int size) {
            return new LoadBean[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDataDate() {
        return dataDate;
    }

    public void setDataDate(String dataDate) {
        this.dataDate = dataDate;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public String getDealerFullCode() {
        return dealerFullCode;
    }

    public void setDealerFullCode(String dealerFullCode) {
        this.dealerFullCode = dealerFullCode;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getCarModelId() {
        return carModelId;
    }

    public void setCarModelId(String carModelId) {
        this.carModelId = carModelId;
    }

    public String getCarModelCode() {
        return carModelCode;
    }

    public void setCarModelCode(String carModelCode) {
        this.carModelCode = carModelCode;
    }

    public String getCarModelName() {
        return carModelName;
    }

    public void setCarModelName(String carModelName) {
        this.carModelName = carModelName;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getWeekSeq() {
        return weekSeq;
    }

    public void setWeekSeq(String weekSeq) {
        this.weekSeq = weekSeq;
    }

    public String getOrderCnt() {
        return orderCnt;
    }

    public void setOrderCnt(String orderCnt) {
        this.orderCnt = orderCnt;
    }

    public String getOrderCntShow() {
        return orderCntShow;
    }

    public void setOrderCntShow(String orderCntShow) {
        this.orderCntShow = orderCntShow;
    }

    public String getCallCnt() {
        return callCnt;
    }

    public void setCallCnt(String callCnt) {
        this.callCnt = callCnt;
    }

    public String getIncomeCnt() {
        return incomeCnt;
    }

    public void setIncomeCnt(String incomeCnt) {
        this.incomeCnt = incomeCnt;
    }

    public String getDeliveryCnt() {
        return deliveryCnt;
    }

    public void setDeliveryCnt(String deliveryCnt) {
        this.deliveryCnt = deliveryCnt;
    }

    public String getSecondIncomeCnt() {
        return secondIncomeCnt;
    }

    public void setSecondIncomeCnt(String secondIncomeCnt) {
        this.secondIncomeCnt = secondIncomeCnt;
    }

    public String getTryDriveCnt() {
        return tryDriveCnt;
    }

    public void setTryDriveCnt(String tryDriveCnt) {
        this.tryDriveCnt = tryDriveCnt;
    }

    public String getTryDriveAllCnt() {
        return tryDriveAllCnt;
    }

    public void setTryDriveAllCnt(String tryDriveAllCnt) {
        this.tryDriveAllCnt = tryDriveAllCnt;
    }

    public String getInfoUploadCnt() {
        return infoUploadCnt;
    }

    public void setInfoUploadCnt(String infoUploadCnt) {
        this.infoUploadCnt = infoUploadCnt;
    }

    public String getQuestionCnt() {
        return questionCnt;
    }

    public void setQuestionCnt(String questionCnt) {
        this.questionCnt = questionCnt;
    }

    public String getCancelOrderCnt() {
        return cancelOrderCnt;
    }

    public void setCancelOrderCnt(String cancelOrderCnt) {
        this.cancelOrderCnt = cancelOrderCnt;
    }

    public String getRemainOrderCnt() {
        return remainOrderCnt;
    }

    public void setRemainOrderCnt(String remainOrderCnt) {
        this.remainOrderCnt = remainOrderCnt;
    }

    public String getTtlOrderCnt() {
        return ttlOrderCnt;
    }

    public void setTtlOrderCnt(String ttlOrderCnt) {
        this.ttlOrderCnt = ttlOrderCnt;
    }

    public String getTtlOrderCnttShow() {
        return ttlOrderCnttShow;
    }

    public void setTtlOrderCnttShow(String ttlOrderCnttShow) {
        this.ttlOrderCnttShow = ttlOrderCnttShow;
    }

    public String getTtlCallCnt() {
        return ttlCallCnt;
    }

    public void setTtlCallCnt(String ttlCallCnt) {
        this.ttlCallCnt = ttlCallCnt;
    }

    public String getTtlIncomeCnt() {
        return ttlIncomeCnt;
    }

    public void setTtlIncomeCnt(String ttlIncomeCnt) {
        this.ttlIncomeCnt = ttlIncomeCnt;
    }

    public String getTtlDeliveryCnt() {
        return ttlDeliveryCnt;
    }

    public void setTtlDeliveryCnt(String ttlDeliveryCnt) {
        this.ttlDeliveryCnt = ttlDeliveryCnt;
    }

    public String getTtlSecondIncomeCnt() {
        return ttlSecondIncomeCnt;
    }

    public void setTtlSecondIncomeCnt(String ttlSecondIncomeCnt) {
        this.ttlSecondIncomeCnt = ttlSecondIncomeCnt;
    }

    public String getTtlTryDriveCnt() {
        return ttlTryDriveCnt;
    }

    public void setTtlTryDriveCnt(String ttlTryDriveCnt) {
        this.ttlTryDriveCnt = ttlTryDriveCnt;
    }

    public String getTtlTryDriveAllCnt() {
        return ttlTryDriveAllCnt;
    }

    public void setTtlTryDriveAllCnt(String ttlTryDriveAllCnt) {
        this.ttlTryDriveAllCnt = ttlTryDriveAllCnt;
    }

    public String getTtlInfoUploadCnt() {
        return ttlInfoUploadCnt;
    }

    public void setTtlInfoUploadCnt(String ttlInfoUploadCnt) {
        this.ttlInfoUploadCnt = ttlInfoUploadCnt;
    }

    public String getTtlQuestionCnt() {
        return ttlQuestionCnt;
    }

    public void setTtlQuestionCnt(String ttlQuestionCnt) {
        this.ttlQuestionCnt = ttlQuestionCnt;
    }

    public String getTtlCancelOrderCnt() {
        return ttlCancelOrderCnt;
    }

    public void setTtlCancelOrderCnt(String ttlCancelOrderCnt) {
        this.ttlCancelOrderCnt = ttlCancelOrderCnt;
    }

    public String getPreRemainOrderCnt() {
        return preRemainOrderCnt;
    }

    public void setPreRemainOrderCnt(String preRemainOrderCnt) {
        this.preRemainOrderCnt = preRemainOrderCnt;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(dataDate);
        parcel.writeString(dealerId);
        parcel.writeString(dealerFullCode);
        parcel.writeString(dealerName);
        parcel.writeString(carModelId);
        parcel.writeString(carModelCode);
        parcel.writeString(carModelName);
        parcel.writeString(year);
        parcel.writeString(season);
        parcel.writeString(month);
        parcel.writeString(weekSeq);
        parcel.writeString(orderCnt);
        parcel.writeString(orderCntShow);
        parcel.writeString(callCnt);
        parcel.writeString(incomeCnt);
        parcel.writeString(deliveryCnt);
        parcel.writeString(secondIncomeCnt);
        parcel.writeString(tryDriveCnt);
        parcel.writeString(tryDriveAllCnt);
        parcel.writeString(infoUploadCnt);
        parcel.writeString(questionCnt);
        parcel.writeString(cancelOrderCnt);
        parcel.writeString(remainOrderCnt);
        parcel.writeString(ttlOrderCnt);
        parcel.writeString(ttlOrderCnttShow);
        parcel.writeString(ttlCallCnt);
        parcel.writeString(ttlIncomeCnt);
        parcel.writeString(ttlDeliveryCnt);
        parcel.writeString(ttlSecondIncomeCnt);
        parcel.writeString(ttlTryDriveCnt);
        parcel.writeString(ttlTryDriveAllCnt);
        parcel.writeString(ttlInfoUploadCnt);
        parcel.writeString(ttlQuestionCnt);
        parcel.writeString(ttlCancelOrderCnt);
        parcel.writeString(preRemainOrderCnt);
        parcel.writeString(createTime);
        parcel.writeString(createUser);
        parcel.writeString(lastUpdateTime);
        parcel.writeString(lastUpdateUser);
    }
}
