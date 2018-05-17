package com.ist.cadillacpaltform.UI.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;

import com.ist.cadillacpaltform.R;
import com.ist.cadillacpaltform.UI.fragment.CarDetailEditFragment;
import com.ist.cadillacpaltform.UI.fragment.CarDetailFragment;

public class CarManagementDetailActivity extends FragmentActivity {
    private ImageView mIvBack;
    private ImageView mIvEdit;
    private int index; //判断属于哪个页面 1、非被请求车辆信息页 2、被请求车辆信息页 3、车辆编辑页 4、新增车辆页
    private CarDetailFragment mCarDetailFragment;
    private CarDetailEditFragment mCarDetailEditFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_management_detail);
        initView();
    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvEdit = (ImageView) findViewById(R.id.iv_edit);
        Intent i = getIntent();
        // TODO: 2017/1/6 默认值
        index = i.getIntExtra("pageType", 2);
        mIvBack.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   int temp = index;
                   switch (temp) {
                       case 1://非被请求车辆信息页
                           onBackPressed();
                           break;
                       case 2://被请求车辆信息页
                           onBackPressed();
                           break;
                       case 3: //车辆编辑页,后退跳转到飞被请求车辆信息页，并且刷新。
                           index = 1;
                           FragmentManager fragmentManager = getSupportFragmentManager();
                           FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                           setBar(1);
                           fragmentTransaction.remove(mCarDetailEditFragment);
                           mCarDetailEditFragment = null;
                           mCarDetailFragment = null;
                           fragmentTransaction.commit();
                           setFragment(1);
                           break;
                       case 4://新增车辆页
                           onBackPressed();
                           break;
                   }
               }
           }
        );
        mIvEdit.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   index = 3;
                   setFragment(3);
               }
           }
        );
        setBar(index);
        setFragment(index);
    }

    private void setBar(int index) {
        switch (index) {
            case 1://非被请求车辆信息页
                mIvEdit.setVisibility(View.VISIBLE);
                break;
            case 2://被请求车辆信息页
            case 3://车辆编辑页
            case 4://新增车辆页
                mIvEdit.setVisibility(View.GONE);
                break;
        }
    }

    private void setFragment(int index) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (index) {
            case 1: {//非被请求车辆信息页
                setBar(1);
                if (mCarDetailFragment == null) {
                    mCarDetailFragment = new CarDetailFragment();
                    fragmentTransaction.add(R.id.fl_carDetail, mCarDetailFragment);
                } else {
                    fragmentTransaction.show(mCarDetailFragment);
                }
                Bundle b = new Bundle();
                //// TODO: 2017/1/6 默认值
                b.putInt("pageType", 1);
                b.putLong("carId", getIntent().getLongExtra("carId", -1));
                mCarDetailFragment.setArguments(b);
                break;
            }
            case 2: {//被请求车辆信息页
                setBar(2);
                if (mCarDetailFragment == null) {
                    mCarDetailFragment = new CarDetailFragment();
                    fragmentTransaction.add(R.id.fl_carDetail, mCarDetailFragment);
                } else {
                    fragmentTransaction.show(mCarDetailFragment);
                }
                Bundle b = new Bundle();
                //// TODO: 2017/1/6 默认值
                b.putInt("pageType", 2);
                b.putLong("orderId", getIntent().getLongExtra("orderId", 1));
                mCarDetailFragment.setArguments(b);
                break;
            }
            case 3: {//车辆编辑页
                setBar(3);
                if (mCarDetailEditFragment == null) {
                    mCarDetailEditFragment = new CarDetailEditFragment();
                    mCarDetailEditFragment.setArguments(new Bundle());
                    fragmentTransaction.add(R.id.fl_carDetail, mCarDetailEditFragment);
                } else {
                    fragmentTransaction.show(mCarDetailEditFragment);
                }
                Bundle b = new Bundle();
                //// TODO: 2017/1/6 默认值
                b.putInt("pageType", 3);
                b.putLong("carId", getIntent().getLongExtra("carId", 24));
                mCarDetailEditFragment.setArguments(b);
                break;
            }
            case 4: {//新增车辆页
                setBar(4);
                if (mCarDetailEditFragment == null) {
                    mCarDetailEditFragment = new CarDetailEditFragment();
                    mCarDetailEditFragment.setArguments(new Bundle());
                    fragmentTransaction.add(R.id.fl_carDetail, mCarDetailEditFragment);
                } else {
                    fragmentTransaction.show(mCarDetailEditFragment);
                }
                Bundle b = new Bundle();
                //// TODO: 2017/1/6 默认值
                b.putInt("pageType", 4);
                mCarDetailEditFragment.setArguments(b);
                break;
            }
        }
        fragmentTransaction.commit();
    }


}
