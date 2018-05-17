package com.ist.cadillacpaltform.UI.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ist.cadillacpaltform.R;
import com.ist.cadillacpaltform.SDK.bean.HighStockAge.CarInfo;
import com.ist.cadillacpaltform.SDK.bean.HighStockAge.Order;
import com.ist.cadillacpaltform.SDK.network.HighWarehouseAgeApi;
import com.ist.cadillacpaltform.SDK.response.HighStockAge.CarInfoResponse;
import com.ist.cadillacpaltform.SDK.response.NoBodyEntity;
import com.ist.cadillacpaltform.SDK.response.HighStockAge.OrderInfoResponse;
import com.ist.cadillacpaltform.SDK.util.TimeHelper;

import java.io.EOFException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Created by czh on 2017/1/3.
 */

public class CarDetailFragment extends Fragment {
    protected View mRoot;
    private TextView mTvAgency;
    private TextView mTvCarType;
    private TextView mTvConfigure;
    private TextView mTvColor;
    private TextView mTvPrice;
    private TextView mTvCreateTime;
    private TextView mTvAddTime;
    private TextView mTvId;
    private TextView mTvFromAgency;
    private TextView mTvConfirm;
    private TextView mTvDeny;
    private TextView mTvDelete;
    private LinearLayout mLlRequestCarBottom;
    private LinearLayout mLlNotRequestCarBottom;
    private Subscriber<CarInfoResponse> mCarInfoSubscriber;
    private Subscriber<OrderInfoResponse> mOrderInfoSubscriber;
    private Subscriber<NoBodyEntity> mConfirmSubscriber;
    private Subscriber<NoBodyEntity> mDenySubscriber;
    private Subscriber<NoBodyEntity> mDeleteSubscriber;

    private long carId;//页面汽车Id,可能为空
    private long orderId;//页面订单Id，可能为空

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mRoot == null) {
            mRoot = inflater.inflate(R.layout.fragment_car_detail, container, false);
            initView();
        }
        ViewGroup parent = (ViewGroup) mRoot.getParent();
        if (parent != null) {
            parent.removeView(mRoot);
        }
        return mRoot;
    }

    private void initView() {
        mTvAgency = (TextView) mRoot.findViewById(R.id.tv_agency);
        mTvCarType = (TextView) mRoot.findViewById(R.id.tv_carType);
        mTvConfigure = (TextView) mRoot.findViewById(R.id.tv_configure);
        mTvColor = (TextView) mRoot.findViewById(R.id.tv_color);
        mTvPrice = (TextView) mRoot.findViewById(R.id.tv_price);
        mTvAddTime = (TextView) mRoot.findViewById(R.id.tv_addTime);
        mTvCreateTime = (TextView) mRoot.findViewById(R.id.tv_createTime);
        mTvId = (TextView) mRoot.findViewById(R.id.tv_id);
        mTvFromAgency = (TextView) mRoot.findViewById(R.id.tv_FromAgency);
        mTvConfirm = (TextView) mRoot.findViewById(R.id.tv_confirm);
        mTvDeny = (TextView) mRoot.findViewById(R.id.tv_deny);
        mTvDelete = (TextView) mRoot.findViewById(R.id.tv_delete);
        mLlNotRequestCarBottom = (LinearLayout) mRoot.findViewById(R.id.ll_notRequestCar_bottom);
        mLlRequestCarBottom = (LinearLayout) mRoot.findViewById(R.id.ll_requestCar_bottom);
        Bundle b = getArguments();
        int index = b.getInt("pageType", 1);
        switch (index){
            case 1:{//非被请求
                carId = b.getLong("carId", 5);
                setCarData();
                break;
            }
            case 2:{//被请求，在setBottom函数里面设置车辆信息，从订单获取车辆数据
                orderId = b.getLong("orderId", 1);
                break;
            }
        }
        setBottom(index);
        //// TODO: 2017/1/7 未处理按下后页面跳转
        mTvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmTrade(orderId);
            }
        });
        mTvDeny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                denyTrade(orderId);
            }
        });
        mTvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //二次删除确认
                new AlertDialog.Builder(getContext()).setTitle("确认删除吗？")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 点击“确认”后的操作
                                deleteCar(carId);
                                //// TODO: 2017/1/9 需要通知一下已经被删除否则车还在
                                getActivity().findViewById(R.id.iv_back).performClick();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 点击“返回”后的操作,这里不设置没有任何操作
                            }
                        }).show();
            }
        });
    }

    private void setCarData() {
        mCarInfoSubscriber = new Subscriber<CarInfoResponse>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Log.i("setCarData", "setCarData network error!");
            }

            @Override
            public void onNext(CarInfoResponse carInfoResponse) {
                CarInfo carInfo = carInfoResponse.carInfo;
                mTvAgency.setText(carInfo.getDealer().getName());
                mTvCarType.setText(carInfo.getLine().getName());
                mTvConfigure.setText(carInfo.getConfig().getName());
                mTvColor.setText(carInfo.getColor());
                mTvPrice.setText(carInfo.getPrice() + "万");
                mTvAddTime.setText(TimeHelper.showAddTime(carInfo.getAddTime()));
                mTvCreateTime.setText(TimeHelper.showCreateTime(carInfo.getCreateTime()));
                mTvId.setText(carInfo.getSerial());
            }
        };
        HighWarehouseAgeApi.getInstance().getCarById(mCarInfoSubscriber, carId);
    }

    private void setBottom(int index) {
        switch (index) {
            case 1://非被请求车辆信息页
                mLlNotRequestCarBottom.setVisibility(View.VISIBLE);
                mLlRequestCarBottom.setVisibility(View.GONE);
                break;
            case 2: {//被请求车辆信息页
                mLlNotRequestCarBottom.setVisibility(View.GONE);
                mLlRequestCarBottom.setVisibility(View.VISIBLE);
                mOrderInfoSubscriber = new Subscriber<OrderInfoResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("setBottom", "setBottom network error!");
                    }

                    @Override
                    public void onNext(OrderInfoResponse orderInfoResponse) {
                        Order order = orderInfoResponse.orderInfo;
                        mTvFromAgency.setText("客户："+order.getSourceDealer().getName());
                        CarInfo carInfo = orderInfoResponse.orderInfo.getCar();
                        mTvAgency.setText(carInfo.getDealer().getName());
                        mTvCarType.setText(carInfo.getLine().getName());
                        mTvConfigure.setText(carInfo.getConfig().getName());
                        mTvColor.setText(carInfo.getColor());
                        mTvPrice.setText(carInfo.getPrice() + "万");
                        mTvAddTime.setText(TimeHelper.showAddTime(carInfo.getAddTime()));
                        mTvCreateTime.setText(TimeHelper.showCreateTime(carInfo.getCreateTime()));
                        mTvId.setText(carInfo.getSerial());
                    }
                };
                HighWarehouseAgeApi.getInstance().getOrderById(mOrderInfoSubscriber, orderId);
                break;
            }
        }
    }

    private void confirmTrade(long orderId) {//传入订单ID
        mConfirmSubscriber = new Subscriber<NoBodyEntity>() {
            @Override
            public void onCompleted() {
                Log.i("confirmTrade", "network success!");
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    HttpException httpException = (HttpException) e;
                    switch (httpException.code()) {
                        case 404:
                            Toast.makeText(getContext(), "订单不存在或不是待确认状态", Toast.LENGTH_SHORT).show();
                            break;
                        case 403:
                            Toast.makeText(getContext(), "没有操作权限", Toast.LENGTH_SHORT).show();
                            break;
                        case 409:
                            Toast.makeText(getContext(), "操作冲突", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(getContext(), "网络错误", Toast.LENGTH_SHORT).show();
                            Log.i("confirmTrade", "network fail1!");
                            e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getContext(), "其他错误", Toast.LENGTH_SHORT).show();
                    Log.i("confirmTrade", "network fail2!");
                    e.printStackTrace();
                }
            }

            @Override
            public void onNext(NoBodyEntity str) {
                Toast.makeText(getContext(), "确认成功", Toast.LENGTH_SHORT).show();
            }
        };
        HighWarehouseAgeApi.getInstance().ConfirmTrade(mConfirmSubscriber, orderId);
    }

    private void denyTrade(long orderId) {//传入订单ID
        mDenySubscriber = new Subscriber<NoBodyEntity>() {
            @Override
            public void onCompleted() {
                Log.i("denyTrade", "network success!");
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    HttpException httpException = (HttpException) e;
                    switch (httpException.code()) {
                        case 404:
                            Toast.makeText(getContext(), "订单不存在或不是待确认状态", Toast.LENGTH_SHORT).show();
                            break;
                        case 403:
                            Toast.makeText(getContext(), "没有操作权限", Toast.LENGTH_SHORT).show();
                            break;
                        case 409:
                            Toast.makeText(getContext(), "操作冲突", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(getContext(), "网络错误", Toast.LENGTH_SHORT).show();
                            Log.i("denyTrade", "network fail1!");
                            e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getContext(), "其他错误", Toast.LENGTH_SHORT).show();
                    Log.i("denyTrade", "network fail2!");
                    e.printStackTrace();
                }
            }

            @Override
            public void onNext(NoBodyEntity str) {
                Toast.makeText(getContext(), "拒绝成功", Toast.LENGTH_SHORT).show();
            }
        };
        HighWarehouseAgeApi.getInstance().denyTrade(mDenySubscriber, orderId);
    }

    private void deleteCar(long carId) {
        mDeleteSubscriber = new Subscriber<NoBodyEntity>() {
            @Override
            public void onCompleted() {
                Log.i("deleteCar", "network success!");
            }

            @Override
            public void onError(Throwable e) {
                if(e instanceof EOFException){//因为返回值为空，这也算删除成功
                    Toast.makeText(getContext(), "删除成功", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                    return;
                }
                if (e instanceof HttpException) {
                    HttpException httpException = (HttpException) e;
                    switch (httpException.code()) {
                        case 403:
                            Toast.makeText(getContext(), "没有操作权限", Toast.LENGTH_SHORT).show();
                            break;
                        case 409:
                            Toast.makeText(getContext(), "操作冲突", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(getContext(), "网络错误", Toast.LENGTH_SHORT).show();
                            Log.i("deleteCar", "network fail1!");
                            e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getContext(), "其他错误", Toast.LENGTH_SHORT).show();
                    Log.i("deleteCar", "network fail2!");
                    e.printStackTrace();
                }
            }

            @Override
            public void onNext(NoBodyEntity str) {
                Toast.makeText(getContext(), "删除成功", Toast.LENGTH_SHORT).show();
            }
        };
        HighWarehouseAgeApi.getInstance().deleteCar(mDeleteSubscriber, carId);
    }

}
