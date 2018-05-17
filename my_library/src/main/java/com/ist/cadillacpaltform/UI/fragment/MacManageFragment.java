package com.ist.cadillacpaltform.UI.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.HeaderViewListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ist.cadillacpaltform.R;
import com.ist.cadillacpaltform.SDK.bean.HighStockAge.CarInfo;
import com.ist.cadillacpaltform.SDK.network.HighWarehouseAgeApi;
import com.ist.cadillacpaltform.SDK.response.HighStockAge.CarInfoListResponse;
import com.ist.cadillacpaltform.SDK.response.NoBodyEntity;
import com.ist.cadillacpaltform.SDK.util.TimeHelper;
import com.ist.cadillacpaltform.UI.activity.DealerPermissionManageActivity;
import com.ist.cadillacpaltform.UI.activity.DetailPageActivity;
import com.ist.cadillacpaltform.UI.activity.ManagementActivity;
import com.ist.cadillacpaltform.UI.adapter.MacManageCarsAdapter;
import com.ist.cadillacpaltform.UI.view.ExpandTabView;
import com.ist.cadillacpaltform.UI.view.PullToRefreshListView;
import com.ist.cadillacpaltform.UI.view.SwipeListView;

import java.io.EOFException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Created by dearlhd on 2017/1/10.
 */
public class MacManageFragment extends Fragment implements ExpandTabView.CarRequestListener, ExpandTabView.PopupWindowActionListener {
    final private String TAG = "MacManageFragment";
    protected View mRoot;

    private ImageView mIvBack;
    private TextView mTvTitle;
    private ImageView mIvAdd;    // 这是dealer管理界面的按钮
    private ImageView mIvSetting;

    private SwipeListView mListView;
    private ExpandTabView mTabView;

    private LinearLayout mLlFillBlank;

    private Map<String, Object> mArgs = new HashMap<>();

    private boolean mIsLoadMore = false;
    private Subscriber<CarInfoListResponse> mCarSubscriber;
    private Subscriber<NoBodyEntity> mDeleteSubscriber;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mRoot == null) {
            mRoot = inflater.inflate(R.layout.fragment_mac_manage, container, false);
            initView();
        }

        ViewGroup parent = (ViewGroup) mRoot.getParent();
        if (parent != null) {
            parent.removeView(mRoot);
        }

        return mRoot;
    }

    private void initView () {
        mIvBack = (ImageView) mRoot.findViewById(R.id.iv_back);
        mTvTitle = (TextView) mRoot.findViewById(R.id.tv_management_title);
        mIvAdd = (ImageView) mRoot.findViewById(R.id.iv_add);
        mIvSetting = (ImageView) mRoot.findViewById(R.id.iv_setting);


        mTabView  = (ExpandTabView) mRoot.findViewById(R.id.v_condition_tab);
        mTabView.setRegionTabEnabled(false);
        mTabView.setCarRequestListener(new ExpandTabView.CarRequestListener() {
            @Override
            public void requestCarInfo(Map<String, Object> opts) {
                mIsLoadMore = false;
                mArgs = mTabView.getArgs();
                mArgs.put("page", 1);
                doRequestCars();
            }
        });

        mListView = (SwipeListView) mRoot.findViewById(R.id.lv_mac_car_list);

        mLlFillBlank = (LinearLayout) mRoot.findViewById(R.id.ll_fill_blank);
        mLlFillBlank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTabView.isPopupWindowShowing()) {
                    mTabView.closePopupWindow();
                }
            }
        });

        initTitle();
        initListView();

        mListView.onRefresh();
        mListView.onLoadMoreComplete(true);

        if (getActivity() instanceof ManagementActivity) {
            ManagementActivity.OnBackListener listener = new ManagementActivity.OnBackListener() {
                @Override
                public boolean onBackPressed() {
                    return doOnBackPressed();
                }
            };
            ((ManagementActivity) getActivity()).setOnBackListener(listener);
        }
    }

    private void initTitle () {
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        mTvTitle.setText("MAC车辆管理");
        mIvAdd.setVisibility(View.GONE);

        mIvSetting.setVisibility(View.VISIBLE);
        mIvSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DealerPermissionManageActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initListView () {
        mListView.setRightButtonCount(1);
        mListView.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i(TAG, "onRefresh");
                mIsLoadMore = false;
                mArgs.put("page", 1);
                doRequestCars();
            }

            @Override
            public void onLoadMore() {
                Log.i(TAG, "onLoadMore");
                mIsLoadMore = true;

                if (!mArgs.containsKey("page")) {
                    mArgs.put("page", 2);
                }

                doRequestCars();
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mTabView.isPopupWindowShowing()) {
                    mTabView.closePopupWindow();
                    return;
                }

                CarInfo carInfo = (CarInfo) mListView.getAdapter().getItem(position);
                Intent intent = new Intent(getActivity(), DetailPageActivity.class);
                intent.putExtra("carId", carInfo.getId());
                startActivity(intent);
            }
        });
    }

    private void doRequestCars () {
        mCarSubscriber = new Subscriber<CarInfoListResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                mListView.onRefreshComplete();
                mListView.onLoadMoreComplete(true);
            }

            @Override
            public void onNext(CarInfoListResponse carInfoListResponse) {
                if (mIsLoadMore) {
                    if (carInfoListResponse.cars.size() == 0) {
                        mListView.onLoadMoreComplete(false);
                        return;
                    }

                    MacManageCarsAdapter adapter;
                    if (mListView.getAdapter() instanceof HeaderViewListAdapter) {
                        HeaderViewListAdapter headerViewListAdapter = (HeaderViewListAdapter) mListView.getAdapter();
                        adapter = (MacManageCarsAdapter) headerViewListAdapter.getWrappedAdapter();
                    } else {
                        adapter = (MacManageCarsAdapter)mListView.getAdapter();
                    }

                    if (adapter == null) {
                        adapter = new MacManageCarsAdapter(getContext(), new ArrayList<CarInfo>());
                    }

                    mArgs.put("page", 1 + (int)mArgs.get("page"));

                    adapter.addCars(carInfoListResponse.cars);
                    mListView.onLoadMoreComplete(true);

                } else {
                    MacManageCarsAdapter adapter = new MacManageCarsAdapter(getContext(), carInfoListResponse.cars);
                    adapter.setOnRightItemClickListener(new MacManageCarsAdapter.OnRightItemClickListener() {
                        @Override
                        public void onDeleteClick(long carId) {
                            deleteCar(carId);
                        }
                    });
                    mArgs.put("page", 2);
                    mListView.setAdapter(adapter);
                    String time = TimeHelper.getCurrentTime();
                    mListView.onRefreshComplete(time);
                }

            }
        };
        HighWarehouseAgeApi.getInstance().getCarsByMac(mCarSubscriber, mArgs);
    }

    // -----------------下面三个是为了ExpandTabView准备的方法-------------------------
    @Override
    public void requestCarInfo(Map<String, Object> opts) {
        mArgs = mTabView.getArgs();
        doRequestCars();
    }

    @Override
    public void afterPopupWindowShow() {
        mListView.setAlpha(0.5f);
    }

    @Override
    public void afterPopupWindowDismiss() {
        mListView.setAlpha(1f);
    }

    private void deleteCar (long carId) {
        mDeleteSubscriber = new Subscriber<NoBodyEntity>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                onDeleteError(e);
            }

            @Override
            public void onNext(NoBodyEntity s) {
                Toast.makeText(getContext(), "删除成功", Toast.LENGTH_SHORT).show();
                mListView.onRefresh();
            }
        };
        HighWarehouseAgeApi.getInstance().deleteCar(mDeleteSubscriber, carId);
    }

    private void onDeleteError (Throwable e) {
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
        } else if (e instanceof EOFException) {
            Toast.makeText(getContext(), "删除成功", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getContext(), "其他错误", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private boolean doOnBackPressed () {
        if (mTabView.isPopupWindowShowing()) {
            mTabView.closePopupWindow();
            return true;
        }
        return false;
    }
}
