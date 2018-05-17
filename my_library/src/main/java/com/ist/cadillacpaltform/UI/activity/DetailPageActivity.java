package com.ist.cadillacpaltform.UI.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ist.cadillacpaltform.R;
import com.ist.cadillacpaltform.SDK.bean.HighStockAge.CarInfo;
import com.ist.cadillacpaltform.SDK.bean.HighStockAge.PurchaseCar;
import com.ist.cadillacpaltform.SDK.network.HighWarehouseAgeApi;
import com.ist.cadillacpaltform.SDK.response.HighStockAge.CarInfoResponse;
import com.ist.cadillacpaltform.SDK.util.TimeHelper;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

public class DetailPageActivity extends Activity {
    private TextView mTvAgency;
    private TextView mTvCarType;
    private TextView mTvConfigure;
    private TextView mTvColor;
    private TextView mTvPrice;
    private TextView mTvAge;
    private TextView mTvDateOfPublish;
    private TextView mTvId;
    private TextView mBtnSummit;
    private ImageView mIvTel;
    private ImageView mIvMessage;
    private ImageView mIvBack;
    private ImageView mIvEdit;
    private String mTelNumber;
    private Subscriber<CarInfoResponse> mCarInfoSubscriber;
    private Subscriber<PurchaseCar> mSubscriber;
    private long carId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_page);
        initView();
    }

    private void initView() {
        mTvAgency = (TextView) findViewById(R.id.tv_agency);
        mTvCarType = (TextView) findViewById(R.id.tv_carType);
        mTvConfigure = (TextView) findViewById(R.id.tv_configure);
        mTvColor = (TextView) findViewById(R.id.tv_color);
        mTvPrice = (TextView) findViewById(R.id.tv_price);
        mTvAge = (TextView) findViewById(R.id.tv_age);
        mTvDateOfPublish = (TextView) findViewById(R.id.tv_dateOfPublish);
        mTvId = (TextView) findViewById(R.id.tv_id);
        mBtnSummit = (TextView) findViewById(R.id.btn_submit);
        mIvTel = (ImageView) findViewById(R.id.iv_tel);
        mIvMessage = (ImageView) findViewById(R.id.iv_message);
        mIvBack=(ImageView)findViewById(R.id.iv_back);
        mIvEdit = (ImageView) findViewById(R.id.iv_edit);
        setData();
        mIvEdit.setVisibility(View.GONE);
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        mBtnSummit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                purchaseCar();
            }
        });
        mIvTel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                telDealer();
            }
        });
        mIvMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });
    }

    private void setData() {
        Intent it = getIntent();
        carId = it.getLongExtra("carId", -1);
        if (carId == -1) {
            //// TODO: 2017/1/6  传参出错
        } else {
            setCarData(carId);
        }
    }

    private void setCarData(long carId) {
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
                mTvAge.setText(TimeHelper.calculateAge(carInfo.getAddTime()));
                mTvDateOfPublish.setText(TimeHelper.showCreateTime(carInfo.getCreateTime()));
                mTvId.setText(carInfo.getSerial());
                mTelNumber = carInfo.getDealer().getTelephone();
            }
        };
        HighWarehouseAgeApi.getInstance().getCarById(mCarInfoSubscriber, carId);
    }

    private void purchaseCar() {
        mSubscriber = new Subscriber<PurchaseCar>() {
            @Override
            public void onCompleted() {
                Log.i("purchaseCar", "network success!");
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    HttpException httpException = (HttpException) e;
                    switch (httpException.code()) {
                        case 404:
                            Toast.makeText(DetailPageActivity.this, "车辆不存在或不是可交易状态", Toast.LENGTH_SHORT).show();
                            break;
                        case 403:
                            Toast.makeText(DetailPageActivity.this, "没有操作权限", Toast.LENGTH_SHORT).show();
                            break;
                        case 409:
                            Toast.makeText(DetailPageActivity.this, "操作冲突", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(DetailPageActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                            Log.i("purchaseCar", "network fail1!");
                            e.printStackTrace();
                    }
                } else {
                    Toast.makeText(DetailPageActivity.this, "其他错误", Toast.LENGTH_SHORT).show();
                    Log.i("purchaseCar", "network fail2!");
                    e.printStackTrace();
                }
            }

            @Override
            public void onNext(PurchaseCar purchaseCar) {
                Toast.makeText(DetailPageActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
            }
        };
        //// TODO: 2017/1/6 会网络错误410
        HighWarehouseAgeApi.getInstance().purchaseCar(mSubscriber, carId);
    }

    private void telDealer() {
        Uri uri = Uri.parse("tel:"+mTelNumber);
        Intent intent = new Intent(Intent.ACTION_DIAL, uri);
        startActivity(intent);
    }

    private void sendMessage() {
        Uri uri = Uri.parse("smsto:"+mTelNumber);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", "");
        startActivity(intent);
    }
}
