package cadillac.example.com.cadillac.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by iris on 2017/12/29.
 */

public class DatilBean implements Parcelable{

    private String dataDateStr;
    private String carModelCode;
    private String carModelName;
    private int orderCntALL;
    private int incomeCntALL;
    private int secondIncomeCntALL;
    private int deliveryCntALL;
    private int tryDriveCntALL;
    private int tryDriveAllCntALL;
    private int infoUploadCntALL;
    private int questionCntALL;
    private int cancelOrderCntALL;
    private int remainOrderCntALL;
    private int ttlOrderCntALL;
    private int ttlIncomeCntALL;
    private int ttlSecondIncomeCntALL;
    private int ttlDeliveryCntALL;
    private int ttlTryDriveCntALL;
    private int ttlTryDriveAllCntALL;
    private int ttlInfoUploadCntALL;
    private int ttlQuestionCntALL;
    private int ttlCancelOrderCntALL;
    private int orderRateALL;
    private int secondIncomeRateALL;
    private int deliveryRateALL;
    private int tryDriveRateALL;
    private int cancelOrderRateALL;
    private int infoUploadRateALL;
    private int questionRateALL;
    private int orderCntRoom;
    private int incomeCntRoom;
    private int secondIncomeCntRoom;
    private int deliveryCntRoom;
    private int tryDriveCntRoom;
    private int tryDriveAllCntRoom;
    private int infoUploadCntRoom;
    private int questionCntRoom;
    private int cancelOrderCntRoom;
    private int remainOrderCntRoom;
    private int ttlOrderCntRoom;
    private int ttlIncomeCntRoom;
    private int ttlSecondIncomeCntRoom;
    private int ttlDeliveryCntRoom;
    private int ttlTryDriveCntRoom;
    private int ttlTryDriveAllCntRoom;
    private int ttlInfoUploadCntRoom;
    private int ttlQuestionCntRoom;
    private int ttlCancelOrderCntRoom;
    private int orderRateRoom;
    private int secondIncomeRateRoom;
    private int deliveryRateRoom;
    private int tryDriveRateRoom;
    private int cancelOrderRateRoom;
    private int infoUploadRateRoom;
    private int questionRateRoom;
    private int orderCntShow;
    private int incomeCntShow;
    private int secondIncomeCntShow;
    private int deliveryCntShow;
    private int tryDriveCntShow;
    private int tryDriveAllCntShow;
    private int infoUploadCntShow;
    private int questionCntShow;
    private int cancelOrderCntShow;
    private int remainOrderCntShow;
    private int ttlOrderCntShow;
    private int ttlIncomeCntShow;
    private int ttlSecondIncomeCntShow;
    private int ttlDeliveryCntShow;
    private int ttlTryDriveCntShow;
    private int ttlTryDriveAllCntShow;
    private int ttlInfoUploadCntShow;
    private int ttlQuestionCntShow;
    private int ttlCancelOrderCntShow;
    private int orderRateShow;
    private int secondIncomeRateShow;
    private int deliveryRateShow;
    private int tryDriveRateShow;
    private int cancelOrderRateShow;
    private int infoUploadRateShow;
    private int questionRateShow;

    private List<DayinfochindBean>detailList;

    protected DatilBean(Parcel in) {
        dataDateStr = in.readString();
        carModelCode = in.readString();
        carModelName = in.readString();
        orderCntALL = in.readInt();
        incomeCntALL = in.readInt();
        secondIncomeCntALL = in.readInt();
        deliveryCntALL = in.readInt();
        tryDriveCntALL = in.readInt();
        tryDriveAllCntALL = in.readInt();
        infoUploadCntALL = in.readInt();
        questionCntALL = in.readInt();
        cancelOrderCntALL = in.readInt();
        remainOrderCntALL = in.readInt();
        ttlOrderCntALL = in.readInt();
        ttlIncomeCntALL = in.readInt();
        ttlSecondIncomeCntALL = in.readInt();
        ttlDeliveryCntALL = in.readInt();
        ttlTryDriveCntALL = in.readInt();
        ttlTryDriveAllCntALL = in.readInt();
        ttlInfoUploadCntALL = in.readInt();
        ttlQuestionCntALL = in.readInt();
        ttlCancelOrderCntALL = in.readInt();
        orderRateALL = in.readInt();
        secondIncomeRateALL = in.readInt();
        deliveryRateALL = in.readInt();
        tryDriveRateALL = in.readInt();
        cancelOrderRateALL = in.readInt();
        infoUploadRateALL = in.readInt();
        questionRateALL = in.readInt();
        orderCntRoom = in.readInt();
        incomeCntRoom = in.readInt();
        secondIncomeCntRoom = in.readInt();
        deliveryCntRoom = in.readInt();
        tryDriveCntRoom = in.readInt();
        tryDriveAllCntRoom = in.readInt();
        infoUploadCntRoom = in.readInt();
        questionCntRoom = in.readInt();
        cancelOrderCntRoom = in.readInt();
        remainOrderCntRoom = in.readInt();
        ttlOrderCntRoom = in.readInt();
        ttlIncomeCntRoom = in.readInt();
        ttlSecondIncomeCntRoom = in.readInt();
        ttlDeliveryCntRoom = in.readInt();
        ttlTryDriveCntRoom = in.readInt();
        ttlTryDriveAllCntRoom = in.readInt();
        ttlInfoUploadCntRoom = in.readInt();
        ttlQuestionCntRoom = in.readInt();
        ttlCancelOrderCntRoom = in.readInt();
        orderRateRoom = in.readInt();
        secondIncomeRateRoom = in.readInt();
        deliveryRateRoom = in.readInt();
        tryDriveRateRoom = in.readInt();
        cancelOrderRateRoom = in.readInt();
        infoUploadRateRoom = in.readInt();
        questionRateRoom = in.readInt();
        orderCntShow = in.readInt();
        incomeCntShow = in.readInt();
        secondIncomeCntShow = in.readInt();
        deliveryCntShow = in.readInt();
        tryDriveCntShow = in.readInt();
        tryDriveAllCntShow = in.readInt();
        infoUploadCntShow = in.readInt();
        questionCntShow = in.readInt();
        cancelOrderCntShow = in.readInt();
        remainOrderCntShow = in.readInt();
        ttlOrderCntShow = in.readInt();
        ttlIncomeCntShow = in.readInt();
        ttlSecondIncomeCntShow = in.readInt();
        ttlDeliveryCntShow = in.readInt();
        ttlTryDriveCntShow = in.readInt();
        ttlTryDriveAllCntShow = in.readInt();
        ttlInfoUploadCntShow = in.readInt();
        ttlQuestionCntShow = in.readInt();
        ttlCancelOrderCntShow = in.readInt();
        orderRateShow = in.readInt();
        secondIncomeRateShow = in.readInt();
        deliveryRateShow = in.readInt();
        tryDriveRateShow = in.readInt();
        cancelOrderRateShow = in.readInt();
        infoUploadRateShow = in.readInt();
        questionRateShow = in.readInt();
        detailList = in.createTypedArrayList(DayinfochindBean.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(dataDateStr);
        dest.writeString(carModelCode);
        dest.writeString(carModelName);
        dest.writeInt(orderCntALL);
        dest.writeInt(incomeCntALL);
        dest.writeInt(secondIncomeCntALL);
        dest.writeInt(deliveryCntALL);
        dest.writeInt(tryDriveCntALL);
        dest.writeInt(tryDriveAllCntALL);
        dest.writeInt(infoUploadCntALL);
        dest.writeInt(questionCntALL);
        dest.writeInt(cancelOrderCntALL);
        dest.writeInt(remainOrderCntALL);
        dest.writeInt(ttlOrderCntALL);
        dest.writeInt(ttlIncomeCntALL);
        dest.writeInt(ttlSecondIncomeCntALL);
        dest.writeInt(ttlDeliveryCntALL);
        dest.writeInt(ttlTryDriveCntALL);
        dest.writeInt(ttlTryDriveAllCntALL);
        dest.writeInt(ttlInfoUploadCntALL);
        dest.writeInt(ttlQuestionCntALL);
        dest.writeInt(ttlCancelOrderCntALL);
        dest.writeInt(orderRateALL);
        dest.writeInt(secondIncomeRateALL);
        dest.writeInt(deliveryRateALL);
        dest.writeInt(tryDriveRateALL);
        dest.writeInt(cancelOrderRateALL);
        dest.writeInt(infoUploadRateALL);
        dest.writeInt(questionRateALL);
        dest.writeInt(orderCntRoom);
        dest.writeInt(incomeCntRoom);
        dest.writeInt(secondIncomeCntRoom);
        dest.writeInt(deliveryCntRoom);
        dest.writeInt(tryDriveCntRoom);
        dest.writeInt(tryDriveAllCntRoom);
        dest.writeInt(infoUploadCntRoom);
        dest.writeInt(questionCntRoom);
        dest.writeInt(cancelOrderCntRoom);
        dest.writeInt(remainOrderCntRoom);
        dest.writeInt(ttlOrderCntRoom);
        dest.writeInt(ttlIncomeCntRoom);
        dest.writeInt(ttlSecondIncomeCntRoom);
        dest.writeInt(ttlDeliveryCntRoom);
        dest.writeInt(ttlTryDriveCntRoom);
        dest.writeInt(ttlTryDriveAllCntRoom);
        dest.writeInt(ttlInfoUploadCntRoom);
        dest.writeInt(ttlQuestionCntRoom);
        dest.writeInt(ttlCancelOrderCntRoom);
        dest.writeInt(orderRateRoom);
        dest.writeInt(secondIncomeRateRoom);
        dest.writeInt(deliveryRateRoom);
        dest.writeInt(tryDriveRateRoom);
        dest.writeInt(cancelOrderRateRoom);
        dest.writeInt(infoUploadRateRoom);
        dest.writeInt(questionRateRoom);
        dest.writeInt(orderCntShow);
        dest.writeInt(incomeCntShow);
        dest.writeInt(secondIncomeCntShow);
        dest.writeInt(deliveryCntShow);
        dest.writeInt(tryDriveCntShow);
        dest.writeInt(tryDriveAllCntShow);
        dest.writeInt(infoUploadCntShow);
        dest.writeInt(questionCntShow);
        dest.writeInt(cancelOrderCntShow);
        dest.writeInt(remainOrderCntShow);
        dest.writeInt(ttlOrderCntShow);
        dest.writeInt(ttlIncomeCntShow);
        dest.writeInt(ttlSecondIncomeCntShow);
        dest.writeInt(ttlDeliveryCntShow);
        dest.writeInt(ttlTryDriveCntShow);
        dest.writeInt(ttlTryDriveAllCntShow);
        dest.writeInt(ttlInfoUploadCntShow);
        dest.writeInt(ttlQuestionCntShow);
        dest.writeInt(ttlCancelOrderCntShow);
        dest.writeInt(orderRateShow);
        dest.writeInt(secondIncomeRateShow);
        dest.writeInt(deliveryRateShow);
        dest.writeInt(tryDriveRateShow);
        dest.writeInt(cancelOrderRateShow);
        dest.writeInt(infoUploadRateShow);
        dest.writeInt(questionRateShow);
        dest.writeTypedList(detailList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DatilBean> CREATOR = new Creator<DatilBean>() {
        @Override
        public DatilBean createFromParcel(Parcel in) {
            return new DatilBean(in);
        }

        @Override
        public DatilBean[] newArray(int size) {
            return new DatilBean[size];
        }
    };

    public String getDataDateStr() {
        return dataDateStr;
    }

    public void setDataDateStr(String dataDateStr) {
        this.dataDateStr = dataDateStr;
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

    public int getOrderCntALL() {
        return orderCntALL;
    }

    public void setOrderCntALL(int orderCntALL) {
        this.orderCntALL = orderCntALL;
    }

    public int getIncomeCntALL() {
        return incomeCntALL;
    }

    public void setIncomeCntALL(int incomeCntALL) {
        this.incomeCntALL = incomeCntALL;
    }

    public int getSecondIncomeCntALL() {
        return secondIncomeCntALL;
    }

    public void setSecondIncomeCntALL(int secondIncomeCntALL) {
        this.secondIncomeCntALL = secondIncomeCntALL;
    }

    public int getDeliveryCntALL() {
        return deliveryCntALL;
    }

    public void setDeliveryCntALL(int deliveryCntALL) {
        this.deliveryCntALL = deliveryCntALL;
    }

    public int getTryDriveCntALL() {
        return tryDriveCntALL;
    }

    public void setTryDriveCntALL(int tryDriveCntALL) {
        this.tryDriveCntALL = tryDriveCntALL;
    }

    public int getTryDriveAllCntALL() {
        return tryDriveAllCntALL;
    }

    public void setTryDriveAllCntALL(int tryDriveAllCntALL) {
        this.tryDriveAllCntALL = tryDriveAllCntALL;
    }

    public int getInfoUploadCntALL() {
        return infoUploadCntALL;
    }

    public void setInfoUploadCntALL(int infoUploadCntALL) {
        this.infoUploadCntALL = infoUploadCntALL;
    }

    public int getQuestionCntALL() {
        return questionCntALL;
    }

    public void setQuestionCntALL(int questionCntALL) {
        this.questionCntALL = questionCntALL;
    }

    public int getCancelOrderCntALL() {
        return cancelOrderCntALL;
    }

    public void setCancelOrderCntALL(int cancelOrderCntALL) {
        this.cancelOrderCntALL = cancelOrderCntALL;
    }

    public int getRemainOrderCntALL() {
        return remainOrderCntALL;
    }

    public void setRemainOrderCntALL(int remainOrderCntALL) {
        this.remainOrderCntALL = remainOrderCntALL;
    }

    public int getTtlOrderCntALL() {
        return ttlOrderCntALL;
    }

    public void setTtlOrderCntALL(int ttlOrderCntALL) {
        this.ttlOrderCntALL = ttlOrderCntALL;
    }

    public int getTtlIncomeCntALL() {
        return ttlIncomeCntALL;
    }

    public void setTtlIncomeCntALL(int ttlIncomeCntALL) {
        this.ttlIncomeCntALL = ttlIncomeCntALL;
    }

    public int getTtlSecondIncomeCntALL() {
        return ttlSecondIncomeCntALL;
    }

    public void setTtlSecondIncomeCntALL(int ttlSecondIncomeCntALL) {
        this.ttlSecondIncomeCntALL = ttlSecondIncomeCntALL;
    }

    public int getTtlDeliveryCntALL() {
        return ttlDeliveryCntALL;
    }

    public void setTtlDeliveryCntALL(int ttlDeliveryCntALL) {
        this.ttlDeliveryCntALL = ttlDeliveryCntALL;
    }

    public int getTtlTryDriveCntALL() {
        return ttlTryDriveCntALL;
    }

    public void setTtlTryDriveCntALL(int ttlTryDriveCntALL) {
        this.ttlTryDriveCntALL = ttlTryDriveCntALL;
    }

    public int getTtlTryDriveAllCntALL() {
        return ttlTryDriveAllCntALL;
    }

    public void setTtlTryDriveAllCntALL(int ttlTryDriveAllCntALL) {
        this.ttlTryDriveAllCntALL = ttlTryDriveAllCntALL;
    }

    public int getTtlInfoUploadCntALL() {
        return ttlInfoUploadCntALL;
    }

    public void setTtlInfoUploadCntALL(int ttlInfoUploadCntALL) {
        this.ttlInfoUploadCntALL = ttlInfoUploadCntALL;
    }

    public int getTtlQuestionCntALL() {
        return ttlQuestionCntALL;
    }

    public void setTtlQuestionCntALL(int ttlQuestionCntALL) {
        this.ttlQuestionCntALL = ttlQuestionCntALL;
    }

    public int getTtlCancelOrderCntALL() {
        return ttlCancelOrderCntALL;
    }

    public void setTtlCancelOrderCntALL(int ttlCancelOrderCntALL) {
        this.ttlCancelOrderCntALL = ttlCancelOrderCntALL;
    }

    public int getOrderRateALL() {
        return orderRateALL;
    }

    public void setOrderRateALL(int orderRateALL) {
        this.orderRateALL = orderRateALL;
    }

    public int getSecondIncomeRateALL() {
        return secondIncomeRateALL;
    }

    public void setSecondIncomeRateALL(int secondIncomeRateALL) {
        this.secondIncomeRateALL = secondIncomeRateALL;
    }

    public int getDeliveryRateALL() {
        return deliveryRateALL;
    }

    public void setDeliveryRateALL(int deliveryRateALL) {
        this.deliveryRateALL = deliveryRateALL;
    }

    public int getTryDriveRateALL() {
        return tryDriveRateALL;
    }

    public void setTryDriveRateALL(int tryDriveRateALL) {
        this.tryDriveRateALL = tryDriveRateALL;
    }

    public int getCancelOrderRateALL() {
        return cancelOrderRateALL;
    }

    public void setCancelOrderRateALL(int cancelOrderRateALL) {
        this.cancelOrderRateALL = cancelOrderRateALL;
    }

    public int getInfoUploadRateALL() {
        return infoUploadRateALL;
    }

    public void setInfoUploadRateALL(int infoUploadRateALL) {
        this.infoUploadRateALL = infoUploadRateALL;
    }

    public int getQuestionRateALL() {
        return questionRateALL;
    }

    public void setQuestionRateALL(int questionRateALL) {
        this.questionRateALL = questionRateALL;
    }

    public int getOrderCntRoom() {
        return orderCntRoom;
    }

    public void setOrderCntRoom(int orderCntRoom) {
        this.orderCntRoom = orderCntRoom;
    }

    public int getIncomeCntRoom() {
        return incomeCntRoom;
    }

    public void setIncomeCntRoom(int incomeCntRoom) {
        this.incomeCntRoom = incomeCntRoom;
    }

    public int getSecondIncomeCntRoom() {
        return secondIncomeCntRoom;
    }

    public void setSecondIncomeCntRoom(int secondIncomeCntRoom) {
        this.secondIncomeCntRoom = secondIncomeCntRoom;
    }

    public int getDeliveryCntRoom() {
        return deliveryCntRoom;
    }

    public void setDeliveryCntRoom(int deliveryCntRoom) {
        this.deliveryCntRoom = deliveryCntRoom;
    }

    public int getTryDriveCntRoom() {
        return tryDriveCntRoom;
    }

    public void setTryDriveCntRoom(int tryDriveCntRoom) {
        this.tryDriveCntRoom = tryDriveCntRoom;
    }

    public int getTryDriveAllCntRoom() {
        return tryDriveAllCntRoom;
    }

    public void setTryDriveAllCntRoom(int tryDriveAllCntRoom) {
        this.tryDriveAllCntRoom = tryDriveAllCntRoom;
    }

    public int getInfoUploadCntRoom() {
        return infoUploadCntRoom;
    }

    public void setInfoUploadCntRoom(int infoUploadCntRoom) {
        this.infoUploadCntRoom = infoUploadCntRoom;
    }

    public int getQuestionCntRoom() {
        return questionCntRoom;
    }

    public void setQuestionCntRoom(int questionCntRoom) {
        this.questionCntRoom = questionCntRoom;
    }

    public int getCancelOrderCntRoom() {
        return cancelOrderCntRoom;
    }

    public void setCancelOrderCntRoom(int cancelOrderCntRoom) {
        this.cancelOrderCntRoom = cancelOrderCntRoom;
    }

    public int getRemainOrderCntRoom() {
        return remainOrderCntRoom;
    }

    public void setRemainOrderCntRoom(int remainOrderCntRoom) {
        this.remainOrderCntRoom = remainOrderCntRoom;
    }

    public int getTtlOrderCntRoom() {
        return ttlOrderCntRoom;
    }

    public void setTtlOrderCntRoom(int ttlOrderCntRoom) {
        this.ttlOrderCntRoom = ttlOrderCntRoom;
    }

    public int getTtlIncomeCntRoom() {
        return ttlIncomeCntRoom;
    }

    public void setTtlIncomeCntRoom(int ttlIncomeCntRoom) {
        this.ttlIncomeCntRoom = ttlIncomeCntRoom;
    }

    public int getTtlSecondIncomeCntRoom() {
        return ttlSecondIncomeCntRoom;
    }

    public void setTtlSecondIncomeCntRoom(int ttlSecondIncomeCntRoom) {
        this.ttlSecondIncomeCntRoom = ttlSecondIncomeCntRoom;
    }

    public int getTtlDeliveryCntRoom() {
        return ttlDeliveryCntRoom;
    }

    public void setTtlDeliveryCntRoom(int ttlDeliveryCntRoom) {
        this.ttlDeliveryCntRoom = ttlDeliveryCntRoom;
    }

    public int getTtlTryDriveCntRoom() {
        return ttlTryDriveCntRoom;
    }

    public void setTtlTryDriveCntRoom(int ttlTryDriveCntRoom) {
        this.ttlTryDriveCntRoom = ttlTryDriveCntRoom;
    }

    public int getTtlTryDriveAllCntRoom() {
        return ttlTryDriveAllCntRoom;
    }

    public void setTtlTryDriveAllCntRoom(int ttlTryDriveAllCntRoom) {
        this.ttlTryDriveAllCntRoom = ttlTryDriveAllCntRoom;
    }

    public int getTtlInfoUploadCntRoom() {
        return ttlInfoUploadCntRoom;
    }

    public void setTtlInfoUploadCntRoom(int ttlInfoUploadCntRoom) {
        this.ttlInfoUploadCntRoom = ttlInfoUploadCntRoom;
    }

    public int getTtlQuestionCntRoom() {
        return ttlQuestionCntRoom;
    }

    public void setTtlQuestionCntRoom(int ttlQuestionCntRoom) {
        this.ttlQuestionCntRoom = ttlQuestionCntRoom;
    }

    public int getTtlCancelOrderCntRoom() {
        return ttlCancelOrderCntRoom;
    }

    public void setTtlCancelOrderCntRoom(int ttlCancelOrderCntRoom) {
        this.ttlCancelOrderCntRoom = ttlCancelOrderCntRoom;
    }

    public int getOrderRateRoom() {
        return orderRateRoom;
    }

    public void setOrderRateRoom(int orderRateRoom) {
        this.orderRateRoom = orderRateRoom;
    }

    public int getSecondIncomeRateRoom() {
        return secondIncomeRateRoom;
    }

    public void setSecondIncomeRateRoom(int secondIncomeRateRoom) {
        this.secondIncomeRateRoom = secondIncomeRateRoom;
    }

    public int getDeliveryRateRoom() {
        return deliveryRateRoom;
    }

    public void setDeliveryRateRoom(int deliveryRateRoom) {
        this.deliveryRateRoom = deliveryRateRoom;
    }

    public int getTryDriveRateRoom() {
        return tryDriveRateRoom;
    }

    public void setTryDriveRateRoom(int tryDriveRateRoom) {
        this.tryDriveRateRoom = tryDriveRateRoom;
    }

    public int getCancelOrderRateRoom() {
        return cancelOrderRateRoom;
    }

    public void setCancelOrderRateRoom(int cancelOrderRateRoom) {
        this.cancelOrderRateRoom = cancelOrderRateRoom;
    }

    public int getInfoUploadRateRoom() {
        return infoUploadRateRoom;
    }

    public void setInfoUploadRateRoom(int infoUploadRateRoom) {
        this.infoUploadRateRoom = infoUploadRateRoom;
    }

    public int getQuestionRateRoom() {
        return questionRateRoom;
    }

    public void setQuestionRateRoom(int questionRateRoom) {
        this.questionRateRoom = questionRateRoom;
    }

    public int getOrderCntShow() {
        return orderCntShow;
    }

    public void setOrderCntShow(int orderCntShow) {
        this.orderCntShow = orderCntShow;
    }

    public int getIncomeCntShow() {
        return incomeCntShow;
    }

    public void setIncomeCntShow(int incomeCntShow) {
        this.incomeCntShow = incomeCntShow;
    }

    public int getSecondIncomeCntShow() {
        return secondIncomeCntShow;
    }

    public void setSecondIncomeCntShow(int secondIncomeCntShow) {
        this.secondIncomeCntShow = secondIncomeCntShow;
    }

    public int getDeliveryCntShow() {
        return deliveryCntShow;
    }

    public void setDeliveryCntShow(int deliveryCntShow) {
        this.deliveryCntShow = deliveryCntShow;
    }

    public int getTryDriveCntShow() {
        return tryDriveCntShow;
    }

    public void setTryDriveCntShow(int tryDriveCntShow) {
        this.tryDriveCntShow = tryDriveCntShow;
    }

    public int getTryDriveAllCntShow() {
        return tryDriveAllCntShow;
    }

    public void setTryDriveAllCntShow(int tryDriveAllCntShow) {
        this.tryDriveAllCntShow = tryDriveAllCntShow;
    }

    public int getInfoUploadCntShow() {
        return infoUploadCntShow;
    }

    public void setInfoUploadCntShow(int infoUploadCntShow) {
        this.infoUploadCntShow = infoUploadCntShow;
    }

    public int getQuestionCntShow() {
        return questionCntShow;
    }

    public void setQuestionCntShow(int questionCntShow) {
        this.questionCntShow = questionCntShow;
    }

    public int getCancelOrderCntShow() {
        return cancelOrderCntShow;
    }

    public void setCancelOrderCntShow(int cancelOrderCntShow) {
        this.cancelOrderCntShow = cancelOrderCntShow;
    }

    public int getRemainOrderCntShow() {
        return remainOrderCntShow;
    }

    public void setRemainOrderCntShow(int remainOrderCntShow) {
        this.remainOrderCntShow = remainOrderCntShow;
    }

    public int getTtlOrderCntShow() {
        return ttlOrderCntShow;
    }

    public void setTtlOrderCntShow(int ttlOrderCntShow) {
        this.ttlOrderCntShow = ttlOrderCntShow;
    }

    public int getTtlIncomeCntShow() {
        return ttlIncomeCntShow;
    }

    public void setTtlIncomeCntShow(int ttlIncomeCntShow) {
        this.ttlIncomeCntShow = ttlIncomeCntShow;
    }

    public int getTtlSecondIncomeCntShow() {
        return ttlSecondIncomeCntShow;
    }

    public void setTtlSecondIncomeCntShow(int ttlSecondIncomeCntShow) {
        this.ttlSecondIncomeCntShow = ttlSecondIncomeCntShow;
    }

    public int getTtlDeliveryCntShow() {
        return ttlDeliveryCntShow;
    }

    public void setTtlDeliveryCntShow(int ttlDeliveryCntShow) {
        this.ttlDeliveryCntShow = ttlDeliveryCntShow;
    }

    public int getTtlTryDriveCntShow() {
        return ttlTryDriveCntShow;
    }

    public void setTtlTryDriveCntShow(int ttlTryDriveCntShow) {
        this.ttlTryDriveCntShow = ttlTryDriveCntShow;
    }

    public int getTtlTryDriveAllCntShow() {
        return ttlTryDriveAllCntShow;
    }

    public void setTtlTryDriveAllCntShow(int ttlTryDriveAllCntShow) {
        this.ttlTryDriveAllCntShow = ttlTryDriveAllCntShow;
    }

    public int getTtlInfoUploadCntShow() {
        return ttlInfoUploadCntShow;
    }

    public void setTtlInfoUploadCntShow(int ttlInfoUploadCntShow) {
        this.ttlInfoUploadCntShow = ttlInfoUploadCntShow;
    }

    public int getTtlQuestionCntShow() {
        return ttlQuestionCntShow;
    }

    public void setTtlQuestionCntShow(int ttlQuestionCntShow) {
        this.ttlQuestionCntShow = ttlQuestionCntShow;
    }

    public int getTtlCancelOrderCntShow() {
        return ttlCancelOrderCntShow;
    }

    public void setTtlCancelOrderCntShow(int ttlCancelOrderCntShow) {
        this.ttlCancelOrderCntShow = ttlCancelOrderCntShow;
    }

    public int getOrderRateShow() {
        return orderRateShow;
    }

    public void setOrderRateShow(int orderRateShow) {
        this.orderRateShow = orderRateShow;
    }

    public int getSecondIncomeRateShow() {
        return secondIncomeRateShow;
    }

    public void setSecondIncomeRateShow(int secondIncomeRateShow) {
        this.secondIncomeRateShow = secondIncomeRateShow;
    }

    public int getDeliveryRateShow() {
        return deliveryRateShow;
    }

    public void setDeliveryRateShow(int deliveryRateShow) {
        this.deliveryRateShow = deliveryRateShow;
    }

    public int getTryDriveRateShow() {
        return tryDriveRateShow;
    }

    public void setTryDriveRateShow(int tryDriveRateShow) {
        this.tryDriveRateShow = tryDriveRateShow;
    }

    public int getCancelOrderRateShow() {
        return cancelOrderRateShow;
    }

    public void setCancelOrderRateShow(int cancelOrderRateShow) {
        this.cancelOrderRateShow = cancelOrderRateShow;
    }

    public int getInfoUploadRateShow() {
        return infoUploadRateShow;
    }

    public void setInfoUploadRateShow(int infoUploadRateShow) {
        this.infoUploadRateShow = infoUploadRateShow;
    }

    public int getQuestionRateShow() {
        return questionRateShow;
    }

    public void setQuestionRateShow(int questionRateShow) {
        this.questionRateShow = questionRateShow;
    }

    public List<DayinfochindBean> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<DayinfochindBean> detailList) {
        this.detailList = detailList;
    }
}
