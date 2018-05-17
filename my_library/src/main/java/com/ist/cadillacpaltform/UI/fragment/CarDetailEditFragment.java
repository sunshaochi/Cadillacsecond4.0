package com.ist.cadillacpaltform.UI.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.ist.cadillacpaltform.R;
import com.ist.cadillacpaltform.SDK.bean.HighStockAge.CarColor;
import com.ist.cadillacpaltform.SDK.bean.HighStockAge.CarConfig;
import com.ist.cadillacpaltform.SDK.bean.HighStockAge.CarInfo;
import com.ist.cadillacpaltform.SDK.bean.HighStockAge.CarLine;
import com.ist.cadillacpaltform.SDK.network.HighWarehouseAgeApi;
import com.ist.cadillacpaltform.SDK.response.HighStockAge.CarColorListResponse;
import com.ist.cadillacpaltform.SDK.response.HighStockAge.CarConfigListResponse;
import com.ist.cadillacpaltform.SDK.response.HighStockAge.CarInfoResponse;
import com.ist.cadillacpaltform.SDK.response.HighStockAge.CarLineListResponse;
import com.ist.cadillacpaltform.SDK.util.TimeHelper;
import com.ist.cadillacpaltform.UI.adapter.CarColorSpinnerAdapter;
import com.ist.cadillacpaltform.UI.adapter.CarConfigSpinnerAdapter;
import com.ist.cadillacpaltform.UI.adapter.CarLineSpinnerAdapter;
import com.ist.cadillacpaltform.UI.adapter.SpinnerBaseAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Created by czh on 2017/1/4.
 */

public class CarDetailEditFragment extends Fragment {
    protected View mRoot;
    private TextView mTvAgency;
    private Spinner mSpCarType;
    private Spinner mSpConfigure;
    private Spinner mSpColor;
    private EditText mEtPrice;
    private TextView mTvAddTime;
    private Spinner mSpReported;
    private Spinner mSpType;
    private TextView mTvSave;

    private LinearLayout ll_agency;
    private LinearLayout ll_type;

    private CarLineSpinnerAdapter mCarLineSpinnerAdapter;
    private CarConfigSpinnerAdapter mCarConfigSpinnerAdapter;
    private CarColorSpinnerAdapter mCarColorSpinnerAdapter;

    private Subscriber<CarLineListResponse> mCarTypeSubscriber;
    private Subscriber<CarConfigListResponse> mCarConfigSubscriber;
    private Subscriber<CarColorListResponse> mCarColorSubscriber;
    private Subscriber<CarInfoResponse> mCarInfoSubscriber;
    private Subscriber<CarInfoResponse> mCarInfoSubscriber_addCar;

    private int index;//判断是修改页还是新增页
    private long carId;//修改页属性
    private CarInfo carInfo = null;//修改页属性
    //// TODO: 2017/1/7 判断是否第一次需要更新。。。。
    private boolean lineFlag = true;//判断是否是第一次进入
    private boolean configFlag = true;
    private boolean colorFlag = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mRoot == null) {
            mRoot = inflater.inflate(R.layout.fragment_car_edit, container, false);
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
        mSpCarType = (Spinner) mRoot.findViewById(R.id.sp_carType);
        mSpConfigure = (Spinner) mRoot.findViewById(R.id.sp_configure);
        mSpColor = (Spinner) mRoot.findViewById(R.id.sp_color);
        mEtPrice = (EditText) mRoot.findViewById(R.id.et_price);
        mTvAddTime = (TextView) mRoot.findViewById(R.id.tv_addTime);
        mSpReported = (Spinner) mRoot.findViewById(R.id.sp_reported);

        String[] spReportedData = getResources().getStringArray(R.array.reported);
        SpinnerBaseAdapter reportAdapter = new SpinnerBaseAdapter(getContext(), spReportedData);
        mSpReported.setAdapter(reportAdapter);

        mSpType = (Spinner) mRoot.findViewById(R.id.sp_type);
        String[] spTypedData = getResources().getStringArray(R.array.type);
        SpinnerBaseAdapter typeAdapter = new SpinnerBaseAdapter(getContext(), spTypedData);
        mSpType.setAdapter(typeAdapter);
        mTvSave = (TextView) mRoot.findViewById(R.id.tv_save);
        ll_agency = (LinearLayout) mRoot.findViewById(R.id.ll_agency);
        ll_type = (LinearLayout) mRoot.findViewById(R.id.ll_type);
        Bundle b = getArguments();
        index = b.getInt("pageType", 3);
        switch (index) {
            case 3: {//编辑页
                //// TODO: 2017/1/7 默认值记得修改
                carId = b.getLong("carId", 30);
                ll_agency.setVisibility(View.VISIBLE);
                ll_type.setVisibility(View.GONE);
                setCarInfo();
                break;
            }
            case 4: {//新增页
                ll_agency.setVisibility(View.GONE);
                ll_type.setVisibility(View.VISIBLE);
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
                mTvAddTime.setText(sdf.format(date));
                setSpinner();
                break;
            }
        }
        mTvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (index) {
                    case 3://修改车辆信息
                        saveCar(carId);
                        break;
                    case 4://增加车辆信息
                        addCar();
                        break;
                }
            }
        });
        mTvAddTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String time = mTvAddTime.getText().toString();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
                Date date = null;
                try {
                    date = sdf.parse(time);
                } catch (ParseException e) {
                    date = new Date();
                }
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        //这里获取到的月份需要加上1
                        mTvAddTime.setText(year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日");
                    }
                }
                        , calendar.get(Calendar.YEAR)
                        , calendar.get(Calendar.MONTH)
                        , calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void setCarInfo() {
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
                carInfo = carInfoResponse.carInfo;
                mTvAgency.setText(carInfo.getDealer().getName());
                mEtPrice.setText(String.valueOf(carInfo.getPrice()));
                mTvAddTime.setText(TimeHelper.showAddTime(carInfo.getAddTime()));
                if (carInfo.getReported() == 1) {
                    mSpReported.setSelection(0);
                } else {
                    mSpReported.setSelection(1);
                }
                if(carInfo.getType()==0){//该字段虽然在编辑页不显示，但在提交编辑信息的时因为和新增车辆共用getNowCarInfo，所以要用到mSpType的值
                    mSpType.setSelection(0);
                }
                if(carInfo.getType()==1){
                    mSpType.setSelection(1);
                }
                if(carInfo.getType()==2){
                    mSpType.setSelection(2);
                }
                setSpinner();
            }
        };
        HighWarehouseAgeApi.getInstance().getCarById(mCarInfoSubscriber, carId);
    }

    private void setSpinner() {
        setCarTypeSpinner();
    }

    private void setCarTypeSpinner() {
        mCarTypeSubscriber = new Subscriber<CarLineListResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.i("setCarTypeSpinner", "setCarTypeSpinner error");
                setCarTypeSpinner();
            }

            @Override
            public void onNext(CarLineListResponse carLineListResponse) {
                ArrayList<CarLine> carLineList = new ArrayList<CarLine>(carLineListResponse.lines);
                if (mCarLineSpinnerAdapter == null) {
                    mCarLineSpinnerAdapter = new CarLineSpinnerAdapter(getContext(), carLineList);
                    mSpCarType.setAdapter(mCarLineSpinnerAdapter);
                } else {
                    mCarLineSpinnerAdapter.setmCarLineList(carLineList);
                    mCarLineSpinnerAdapter.notifyDataSetChanged();
                }
                if (index == 3 && lineFlag) {//修改页设置默认车系
                    int size = mSpCarType.getAdapter().getCount();
                    for (int i = 0; i < size; i++) {
                        if (carInfo.getLine().getId() == ((CarLine) mSpCarType.getItemAtPosition(i)).getId()) {
                            mSpCarType.setSelection(i);//如果在veiw初始化前调用，则系统不会再默认调用一次
                            break;
                        }
                    }
                    lineFlag = false;
                }
                mSpCarType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        CarLine carLine = (CarLine) adapterView.getItemAtPosition(i);
                        setConfigureSpinner(carLine.getId());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        };
        HighWarehouseAgeApi.getInstance().getAllLines(mCarTypeSubscriber);
    }

    private void setConfigureSpinner(long carTypeId) {
        mCarConfigSubscriber = new Subscriber<CarConfigListResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.i("setConfigureSpinner", "network error!");
                e.printStackTrace();
            }

            @Override
            public void onNext(CarConfigListResponse carConfigListResponse) {
                Log.i("setConfigureSpinner", "onNext");
                ArrayList<CarConfig> carConfigArrayList = new ArrayList<CarConfig>(carConfigListResponse.configs);
                if (mCarConfigSpinnerAdapter == null) {
                    mCarConfigSpinnerAdapter = new CarConfigSpinnerAdapter(getContext(), carConfigArrayList);
                    mSpConfigure.setAdapter(mCarConfigSpinnerAdapter);
                } else {
                    mCarConfigSpinnerAdapter.setmCarConfigList(carConfigArrayList);
                    mCarConfigSpinnerAdapter.notifyDataSetChanged();
                }
                if (index == 3 && configFlag) {
                    int size2 = mSpConfigure.getAdapter().getCount();
                    for (int i = 0; i < size2; i++) {
                        if (carInfo.getConfig().getId() == ((CarConfig) mSpConfigure.getItemAtPosition(i)).getId()) {
                            mSpConfigure.setSelection(i);
                            break;
                        }
                    }
                    configFlag = false;
                }
                mSpConfigure.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        CarConfig carConfig = (CarConfig) adapterView.getItemAtPosition(i);
                        setColorSpinner(carConfig.getId());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        };
        HighWarehouseAgeApi.getInstance().getConfigByLine(mCarConfigSubscriber, carTypeId);
    }

    private void setColorSpinner(long configId) {
        mCarColorSubscriber = new Subscriber<CarColorListResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(CarColorListResponse carColorListResponse) {
                ArrayList<CarColor> carColorArrayList = new ArrayList<CarColor>(carColorListResponse.colors);
                if (mCarColorSpinnerAdapter == null) {
                    mCarColorSpinnerAdapter = new CarColorSpinnerAdapter(getContext(), carColorArrayList);
                    mSpColor.setAdapter(mCarColorSpinnerAdapter);
                } else {
                    mCarColorSpinnerAdapter.setmCarColorList(carColorArrayList);
                    mCarColorSpinnerAdapter.notifyDataSetChanged();
                }
                if (index == 3 && colorFlag) {
                    int size3 = mSpColor.getAdapter().getCount();
                    for (int i = 0; i < size3; i++) {
                        if (carInfo.getColor().equals(((CarColor) mSpColor.getItemAtPosition(i)).getColor())) {
                            mSpColor.setSelection(i);
                            break;
                        }
                    }
                    colorFlag = false;
                }
            }
        };
        HighWarehouseAgeApi.getInstance().getColorByConfig(mCarColorSubscriber, configId);
    }

    private void saveCar(long carId) {
        mCarInfoSubscriber = new Subscriber<CarInfoResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    HttpException httpException = (HttpException) e;
                    switch (httpException.code()) {
                        case 403:
                            Toast.makeText(getContext(), "修改失败，无权限操作", Toast.LENGTH_SHORT).show();
                            break;
                        case 400:
                            Toast.makeText(getContext(), "修改失败，数据格式有误", Toast.LENGTH_SHORT).show();
                            break;
                        case 409:
                            Toast.makeText(getContext(), "修改失败，冲突修改", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(getContext(), "网络错误", Toast.LENGTH_SHORT).show();
                            Log.i("saveCar", "network fail1!");
                            e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getContext(), "其他错误", Toast.LENGTH_SHORT).show();
                    Log.i("saveCar", "network fail2!");
                    e.printStackTrace();
                }
            }

            @Override
            public void onNext(CarInfoResponse carInfoResponse) {
                Toast.makeText(getContext(), "修改成功！", Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();
            }
        };
        Gson gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {//排除无用字段
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return f.getName().contains("status");
            }
            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        }).create();
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("data", gson.toJsonTree(getNowCarInfo()));
        HighWarehouseAgeApi.getInstance().saveCar(mCarInfoSubscriber, carId, jsonObject);
    }

    private void addCar() {
        mCarInfoSubscriber_addCar = new Subscriber<CarInfoResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    HttpException httpException = (HttpException) e;
                    switch (httpException.code()) {
                        case 403:
                            Toast.makeText(getContext(), "添加失败，无权限操作", Toast.LENGTH_SHORT).show();
                            break;
                        case 400:
                            Toast.makeText(getContext(), "添加失败，数据格式有误", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(getContext(), "网络错误", Toast.LENGTH_SHORT).show();
                            Log.i("addCar", "network fail1!");
                            e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getContext(), "其他错误", Toast.LENGTH_SHORT).show();
                    Log.i("addCar", "network fail2!");
                    e.printStackTrace();
                }
            }

            @Override
            public void onNext(CarInfoResponse carInfoResponse) {
                Toast.makeText(getContext(), "添加成功！", Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();
            }
        };
        Gson gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {//排除无用字段,系统内置类型会自动生成0如id和status,而String或Dealer类不会生成默认值传过去,而排除id会把configure的id也排除了，经测试传id没事
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return f.getName().contains("status");
            }
            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        }).create();
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("data", gson.toJsonTree(getNowCarInfo()));
        HighWarehouseAgeApi.getInstance().addCar(mCarInfoSubscriber_addCar, jsonObject);
    }

    private CarInfo getNowCarInfo(){
        CarInfo carInfo = new CarInfo();
        carInfo.setColor(((CarColor) mSpColor.getSelectedItem()).getColor());
        carInfo.setPrice(Integer.parseInt(mEtPrice.getText().toString()));
        carInfo.setAddTime(TimeHelper.ToAddTime(mTvAddTime.getText().toString()));
        int reported = mSpReported.getSelectedItem().toString().equals("是") ? 1 : 0;
        carInfo.setReported(reported);
        String typeStr = mSpType.getSelectedItem().toString();
        int type = 0;
        if (typeStr.equals("良好")) {
            type = 0;
        }
        if (typeStr.equals("一般")) {
            type = 1;
        }
        if (typeStr.equals("差")) {
            type = 2;
        }
        carInfo.setType(type);
        carInfo.setLine((CarLine) mSpCarType.getSelectedItem());
        carInfo.setConfig((CarConfig) mSpConfigure.getSelectedItem());
        return carInfo;
    }
}

