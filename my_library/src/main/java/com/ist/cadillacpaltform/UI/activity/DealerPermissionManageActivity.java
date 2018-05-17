package com.ist.cadillacpaltform.UI.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ist.cadillacpaltform.R;
import com.ist.cadillacpaltform.SDK.network.HighWarehouseAgeApi;
import com.ist.cadillacpaltform.SDK.response.HighStockAge.DealerResponse;
import com.ist.cadillacpaltform.UI.adapter.DealerPermissionAdapter;

import java.util.HashMap;
import java.util.Map;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Created by dearlhd on 2017/1/10.
 */
public class DealerPermissionManageActivity extends Activity {
    private ImageView mIvBack;
    private TextView mTvTitle;
    private ListView mListView;

    private Subscriber<DealerResponse> mDealerSubscriber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_permission_manage);
        initView();
    }

    private void initView () {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mTvTitle = (TextView) findViewById(R.id.tv_management_title);
        mTvTitle.setText("经销商权限管理");

        mListView = (ListView) findViewById(R.id.lv_dealer_permission);

        requestDealers();
    }

    private void requestDealers () {
        mDealerSubscriber = new Subscriber<DealerResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(DealerResponse response) {
                DealerPermissionAdapter adapter = new DealerPermissionAdapter(getBaseContext(), response.dealers);
                adapter.setSwitchStatusChangeListener(new DealerPermissionAdapter.SwitchStatusChangeListener() {
                    @Override
                    public void onStatusChanged(CompoundButton button, boolean isChecked, long dealerId) {
                        onSwitchStatusChanged(button, isChecked, dealerId);
                    }
                });
                mListView.setAdapter(adapter);
            }
        };

        Map<String, Object> args = new HashMap<>();
        args.put("page", 1);
        args.put("size", 100);
        HighWarehouseAgeApi.getInstance().getDealerByMac(mDealerSubscriber, args);
    }

    private void onSwitchStatusChanged (final CompoundButton button, final boolean isChecked, long dealerId) {
        mDealerSubscriber = new Subscriber<DealerResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                if (e instanceof HttpException) {
                    HttpException httpException = (HttpException) e;
                    switch (httpException.code()) {
                        case 403:
                            Toast.makeText(getBaseContext(), "没有操作权限", Toast.LENGTH_SHORT).show();
                            break;
                        case 409:
                            Toast.makeText(getBaseContext(), "更改冲突", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(getBaseContext(), "网络错误", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                    }
                }
            }

            @Override
            public void onNext(DealerResponse response) {
                button.setChecked(!isChecked);
            }
        };
        HighWarehouseAgeApi.getInstance().changeDealerStatus(mDealerSubscriber, dealerId);
    }
}
