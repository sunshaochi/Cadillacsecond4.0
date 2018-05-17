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
import android.widget.Toast;

import com.ist.cadillacpaltform.R;
import com.ist.cadillacpaltform.SDK.bean.HighStockAge.CarInfo;
import com.ist.cadillacpaltform.SDK.bean.HighStockAge.Order;
import com.ist.cadillacpaltform.SDK.network.HighWarehouseAgeApi;
import com.ist.cadillacpaltform.SDK.response.HighStockAge.CarInfoListResponse;
import com.ist.cadillacpaltform.SDK.response.NoBodyEntity;
import com.ist.cadillacpaltform.SDK.response.HighStockAge.OrderResponse;
import com.ist.cadillacpaltform.SDK.util.TimeHelper;
import com.ist.cadillacpaltform.UI.activity.CarManagementDetailActivity;
import com.ist.cadillacpaltform.UI.activity.ManagementActivity;
import com.ist.cadillacpaltform.UI.adapter.DealerManageCarsAdapter;
import com.ist.cadillacpaltform.UI.view.ExpandTabView;
import com.ist.cadillacpaltform.UI.view.PullToRefreshListView;
import com.ist.cadillacpaltform.UI.view.SwipeListView;

import java.io.EOFException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Created by dearlhd on 2017/1/4.
 */
public class DealerManageFragment extends Fragment implements ExpandTabView.CarRequestListener, ExpandTabView.PopupWindowActionListener {
    final private String TAG = "DealerManageFragment";
    protected View mRoot;

    private ImageView mIvBack;
    private ImageView mIvAddCar;
    private ImageView mIvSetting;  // 这是Mac管理界面显示的
    private SwipeListView mListView;
    private ExpandTabView mTabView;

    private LinearLayout mLlFillBlank;

    private Map<String, Object> mArgs = new HashMap<>();

    private Subscriber<OrderResponse> mOrderSubscriber;
    private Subscriber<CarInfoListResponse> mCarSubscriber;
    private Subscriber<NoBodyEntity> mTradeSubscriber;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mRoot == null) {
            mRoot = inflater.inflate(R.layout.fragment_dealer_manage, container, false);
            initView();
        }

        ViewGroup parent = (ViewGroup) mRoot.getParent();
        if (parent != null) {
            parent.removeView(mRoot);
        }

        return mRoot;
    }

    // -----------------下面三个是为了ExpandTabView准备的方法-------------------------
    @Override
    public void requestCarInfo(Map<String, Object> opts) {
        mArgs = mTabView.getArgs();

        requestOrderCars();
    }

    @Override
    public void afterPopupWindowShow() {
        mListView.setAlpha(0.5f);
    }

    @Override
    public void afterPopupWindowDismiss() {
        mListView.setAlpha(1f);
    }


    // ------------------------私有方法----------------------------------------

    private void initView() {
        mIvBack = (ImageView) mRoot.findViewById(R.id.iv_back);
        mIvAddCar = (ImageView) mRoot.findViewById(R.id.iv_add);
        mIvSetting = (ImageView) mRoot.findViewById(R.id.iv_setting);

        mTabView = (ExpandTabView) mRoot.findViewById(R.id.v_condition_tab);
        mTabView.setRegionTabEnabled(false);
        mTabView.setCarRequestListener(this);
        mTabView.setPopActionLister(this);

        mListView = (SwipeListView) mRoot.findViewById(R.id.lv_dealer_car_list);

        mLlFillBlank = (LinearLayout) mRoot.findViewById(R.id.ll_fill_blank);
        mLlFillBlank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mTabView.isPopupWindowShowing()) {
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

        mIvAddCar.setVisibility(View.VISIBLE);
        mIvAddCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTabView.isPopupWindowShowing()) {
                    mTabView.closePopupWindow();
                }
                Intent intent = new Intent(getActivity(), CarManagementDetailActivity.class);
                intent.putExtra("pageType", 4);
                startActivity(intent);
            }
        });

        mIvSetting.setVisibility(View.GONE);

    }

    private void initListView () {
        mListView.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i(TAG, "refresh");
                mArgs.put("page", 1);
                requestOrderCars();
            }

            @Override
            public void onLoadMore() {
                Log.i(TAG, "load more");
                requestNormalCars();
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "item " + position + " is clicked");
                if (mTabView.isPopupWindowShowing()) {
                    mTabView.closePopupWindow();
                    return;
                }

                DealerManageCarsAdapter adapter = null;
                if (mListView.getAdapter() instanceof HeaderViewListAdapter) {
                    HeaderViewListAdapter headerViewListAdapter = (HeaderViewListAdapter) mListView.getAdapter();
                    adapter = (DealerManageCarsAdapter) headerViewListAdapter.getWrappedAdapter();
                } else {
                    adapter = (DealerManageCarsAdapter) mListView.getAdapter();
                }

                Intent intent = new Intent(getActivity(), CarManagementDetailActivity.class);
                int itemPos = position - 1;   // 这里要注意一下，由于添加了一个headerView，position-1才是对应的item的序号
                if (itemPos < adapter.getRequestCarSize()) {
                    intent.putExtra("pageType", 2);
                    Order order = (Order)adapter.getItem(itemPos);
                    intent.putExtra("orderId", order.getId());
                    intent.putExtra("carId", order.getCar().getId());
                } else {
                    intent.putExtra("pageType", 1);
                    CarInfo car = (CarInfo)adapter.getItem(itemPos);
                    intent.putExtra("carId", car.getId());
                }
                startActivity(intent);
            }
        });
    }

    private void requestOrderCars() {
        mOrderSubscriber = new Subscriber<OrderResponse>() {
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
            public void onNext(OrderResponse orderResponse) {
                List<Order> orders = orderResponse.orders;

                mArgs.put("page", 1);

                if (orders.size() == 0) {
                    mListView.setAdapter(null);
                    requestNormalCars();
                    String time = TimeHelper.getCurrentTime();
                    mListView.onRefreshComplete(time);
                    return;
                }

                // 为了让界面显示更丰满，当order数量太少时，请求普通车辆信息
                if (orders.size() < 10) {
                    requestNormalCars();
                }

                // 刷新列表或第一次加载 (加载更多不会调用该请求，因为该请求一次性获得所有被请求的车辆)
                DealerManageCarsAdapter adapter = new DealerManageCarsAdapter(getContext(), orders, new ArrayList<CarInfo>());
                adapter.setRightItemClickListener(new DealerManageCarsAdapter.OnRightItemClickListener() {
                    @Override
                    public void onTradeConfirmClick(long orderId, int position) {
                        confirmTrade(orderId, position);
                    }

                    @Override
                    public void onTradeDenyClick(long orderId, int position) {
                        denyTrade(orderId, position);
                    }

                    @Override
                    public void onEditClick(long carId) {
                        editCarInfo(carId);
                    }

                    @Override
                    public void onDeleteClick(long carId, int position) {
                        deleteCar(carId, position);
                    }
                });
                mListView.setAdapter(adapter);
                String time = TimeHelper.getCurrentTime();
                mListView.onRefreshComplete(time);
            }
        };
        HighWarehouseAgeApi.getInstance().getRequestedCars(mOrderSubscriber);
    }

    private void requestNormalCars() {
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
                Log.i(TAG, "load more complete");
                if (carInfoListResponse.cars.size() == 0) {
                    mListView.onLoadMoreComplete(false);
                    return;
                }

                DealerManageCarsAdapter adapter = null;
                if (mListView.getAdapter() instanceof HeaderViewListAdapter) {
                    HeaderViewListAdapter headerViewListAdapter = (HeaderViewListAdapter) mListView.getAdapter();
                    adapter = (DealerManageCarsAdapter) headerViewListAdapter.getWrappedAdapter();
                } else {
                    adapter = (DealerManageCarsAdapter) mListView.getAdapter();
                }
                if (adapter == null) {
                    adapter = new DealerManageCarsAdapter(getContext(), new ArrayList<Order>(), new ArrayList<CarInfo>());
                    adapter.setRightItemClickListener(new DealerManageCarsAdapter.OnRightItemClickListener() {
                        @Override
                        public void onTradeConfirmClick(long orderId, int position) {
                            confirmTrade(orderId, position);
                        }

                        @Override
                        public void onTradeDenyClick(long orderId, int position) {
                            denyTrade(orderId, position);
                        }

                        @Override
                        public void onEditClick(long carId) {
                            editCarInfo(carId);
                        }

                        @Override
                        public void onDeleteClick(long carId, int position) {
                            deleteCar(carId, position);
                        }
                    });
                    mListView.setAdapter(adapter);
                }

                adapter.addCars(carInfoListResponse.cars);
                mListView.onLoadMoreComplete(true);

                int page = (int) mArgs.get("page");
                mArgs.put("page", page+1);
            }
        };
        HighWarehouseAgeApi.getInstance().getCarByDealer(mCarSubscriber, mArgs);

    }


    private void confirmTrade (long orderId, final int position) {
        mTradeSubscriber = new Subscriber<NoBodyEntity>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                onNetworkError(e);
            }

            @Override
            public void onNext(NoBodyEntity s) {
                Toast.makeText(getContext(), "确认成功", Toast.LENGTH_SHORT).show();
                mListView.onRefresh();
            }
        };
        HighWarehouseAgeApi.getInstance().ConfirmTrade(mTradeSubscriber, orderId);
    }

    private void denyTrade (long orderId, final int position) {
        mTradeSubscriber = new Subscriber<NoBodyEntity>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                onNetworkError(e);
            }

            @Override
            public void onNext(NoBodyEntity s) {
                DealerManageCarsAdapter adapter = null;
                if (mListView.getAdapter() instanceof HeaderViewListAdapter) {
                    HeaderViewListAdapter headerViewListAdapter = (HeaderViewListAdapter) mListView.getAdapter();
                    adapter = (DealerManageCarsAdapter) headerViewListAdapter.getWrappedAdapter();
                } else {
                    adapter = (DealerManageCarsAdapter) mListView.getAdapter();
                }
                adapter.deleteEntry(position);
            }
        };
        HighWarehouseAgeApi.getInstance().denyTrade(mTradeSubscriber, orderId);
    }

    private void editCarInfo (long carId) {
        Intent intent = new Intent(getActivity(), CarManagementDetailActivity.class);
        intent.putExtra("pageType", 3);
        intent.putExtra("carId", carId);
        startActivity(intent);
    }

    private void deleteCar (long carId, final int position) {
        mTradeSubscriber = new Subscriber<NoBodyEntity>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                onDeleteError(e);
            }

            @Override
            public void onNext(NoBodyEntity s) {
                Toast.makeText(getContext(), "删除成功", Toast.LENGTH_SHORT).show();
                mListView.onRefresh();
            }
        };
        HighWarehouseAgeApi.getInstance().deleteCar(mTradeSubscriber, carId);
    }

    private void onNetworkError (Throwable e) {
        e.printStackTrace();

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
                    e.printStackTrace();
            }
        }
        else {
            Toast.makeText(getContext(), "其他错误", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        Toast.makeText(getContext(), "操作失败", Toast.LENGTH_SHORT).show();
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
