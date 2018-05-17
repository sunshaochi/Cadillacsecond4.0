package com.ist.cadillacpaltform.UI.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.HeaderViewListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ist.cadillacpaltform.R;
import com.ist.cadillacpaltform.SDK.bean.Authorization;
import com.ist.cadillacpaltform.SDK.bean.HighStockAge.CarInfo;
import com.ist.cadillacpaltform.SDK.bean.HighStockAge.Dealer;
import com.ist.cadillacpaltform.SDK.bean.HighStockAge.Order;
import com.ist.cadillacpaltform.SDK.network.HighWarehouseAgeApi;
import com.ist.cadillacpaltform.SDK.response.HighStockAge.CarInfoListResponse;
import com.ist.cadillacpaltform.SDK.response.HighStockAge.DealerResponse;
import com.ist.cadillacpaltform.SDK.response.HighStockAge.OrderResponse;
import com.ist.cadillacpaltform.SDK.util.SQLiteHelper;
import com.ist.cadillacpaltform.SDK.util.TimeHelper;
import com.ist.cadillacpaltform.UI.activity.ManagementActivity;
import com.ist.cadillacpaltform.UI.activity.DetailPageActivity;
import com.ist.cadillacpaltform.UI.activity.HomePageActivity;
import com.ist.cadillacpaltform.UI.adapter.CarInfoAdapter;
import com.ist.cadillacpaltform.UI.adapter.SearchResultAdapter;
import com.ist.cadillacpaltform.UI.view.ExpandTabView;
import com.ist.cadillacpaltform.UI.view.PullToRefreshListView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;

/**
 * Created by dearlhd on 2016/12/18.
 */
public class PlatformFragment extends Fragment implements ExpandTabView.CarRequestListener, ExpandTabView.PopupWindowActionListener {
    final private String TAG = "PlatformFragment";

    protected View mRoot;

    private ImageView mIvBack;             // “后退”图标
    private ImageView mIvSearch;           // “搜索”图标
    private RelativeLayout mRlMyMsg;       // “我的信息”图标
    private TextView mTvTipCount;          // “我的信息”右上角数量气泡

    private PullToRefreshListView mCarInfoListView;     // 车辆信息列表

    private LinearLayout mLlFillBlank;

    private boolean mIsLoadMore = false;

    private ExpandTabView mExpandTabView;

    private PopupWindow mPopWindowSearch;
    private ListView mLvSearchResult;
    private EditText mEtSearchKey;

    private Map<String, Object> mArgs = new HashMap<>();

    private Subscriber<CarInfoListResponse> mCarInfoSubscriber;   // 主列表的车辆数据请求
    private Subscriber<DealerResponse> mDealerSubscriber;         // 快速查询的经销商请求
    private Subscriber<OrderResponse> mOrderSubscriber;           // 被请求的车辆信息（这里主要是取数量）

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mRoot == null) {
            mRoot = inflater.inflate(R.layout.fragment_platform, container, false);
            initView();
        }

        ViewGroup parent = (ViewGroup) mRoot.getParent();
        if (parent != null) {
            parent.removeView(mRoot);
        }

        return mRoot;
    }

    private void initView() {
        mIvBack = (ImageView) mRoot.findViewById(R.id.iv_back);
        mIvSearch = (ImageView) mRoot.findViewById(R.id.iv_search);
        mRlMyMsg = (RelativeLayout) mRoot.findViewById(R.id.rl_self_manage);
        mTvTipCount = (TextView) mRoot.findViewById(R.id.tv_my_tip_count);

        mCarInfoListView = (PullToRefreshListView) mRoot.findViewById(R.id.lv_platform_content);

        mLlFillBlank = (LinearLayout) mRoot.findViewById(R.id.ll_fill_blank);

        mLlFillBlank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mExpandTabView.isPopupWindowShowing()) {
                    mExpandTabView.closePopupWindow();
                }
            }
        });

        mExpandTabView = (ExpandTabView) mRoot.findViewById(R.id.view_search_condition);
        mExpandTabView.setCarRequestListener(this);
        mExpandTabView.setPopActionLister(this);

        Map<String, Object> options = new HashMap<>();
        options.put("page", 1);
        options.put("size", 20);

        initSearchWindow();

        initTitle();

        initListView();

        mCarInfoListView.onRefresh();
        mCarInfoListView.onLoadMoreComplete(true);

        SQLiteHelper helper = new SQLiteHelper();
        Authorization auth = helper.getAuth();
        if (auth.type == 0) {
            requestOrder();
        }

        if (getActivity() instanceof HomePageActivity) {
            HomePageActivity.OnBackListener listener = new HomePageActivity.OnBackListener() {
                @Override
                public boolean onBackPressed() {
                    return doOnBackPressed();
                }
            };
            ((HomePageActivity) getActivity()).setOnBackListener(listener);
        }
    }

    private void initTitle () {
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        mIvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mExpandTabView.isPopupWindowShowing()) {
                    mExpandTabView.closePopupWindow();
                }


                if (!mPopWindowSearch.isShowing()) {
                    mPopWindowSearch.showAtLocation(mRoot, Gravity.LEFT | Gravity.TOP, 0, 0);
                    setBackgroundAlpha(0.7f);
                }

                // 为输入框获取焦点和弹出输入法
                mEtSearchKey.requestFocus();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
            }
        });

        mEtSearchKey.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    requestDealer(s.toString());
                } else {
                    mLvSearchResult.setAdapter(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        SQLiteHelper helper = new SQLiteHelper();
        Authorization authorization = helper.getAuth();

        if (authorization == null || (authorization.type != 0 && authorization.type != 1 && authorization.type != 2)) {
            mRlMyMsg.setVisibility(View.INVISIBLE);
        }

        mRlMyMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mExpandTabView.isPopupWindowShowing()) {
                    mExpandTabView.closePopupWindow();
                }

                Intent intent = new Intent(getActivity(), ManagementActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initListView () {
        mCarInfoListView.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i(TAG, "onRefresh");
                mIsLoadMore = false;
                mArgs.put("page", 1);
                doCarInfoRequest(mArgs);//获取车型
            }

            @Override
            public void onLoadMore() {
                Log.i(TAG, "onLoadMore");
                if (!mArgs.containsKey("page")) {
                    mArgs.put("page", 2);
                }

                mIsLoadMore = true;
                doCarInfoRequest(mArgs);
            }
        });

        mCarInfoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mExpandTabView.isPopupWindowShowing()) {
                    mExpandTabView.closePopupWindow();
                    return;
                }

                // 跳转至详情页
                CarInfo carInfo = (CarInfo) mCarInfoListView.getAdapter().getItem(position);
                Intent intent = new Intent(getActivity(), DetailPageActivity.class);
                intent.putExtra("carId", carInfo.getId());
                startActivity(intent);
            }
        });
    }

    /**
     * 收索的窗口
     */
    private void initSearchWindow() {
        View popView = LayoutInflater.from(getContext()).inflate(R.layout.ui_search, null);
        mLvSearchResult = (ListView) popView.findViewById(R.id.lv_search_result);
        mEtSearchKey = (EditText) popView.findViewById(R.id.et_research_key);

        ImageView iv_back= (ImageView) popView.findViewById(R.id.iv_back);

        mPopWindowSearch = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Drawable drawable = getResources().getDrawable(R.drawable.bg_grey);
        mPopWindowSearch.setBackgroundDrawable(drawable);
        mPopWindowSearch.setTouchable(true);
        mPopWindowSearch.setFocusable(true);
        mPopWindowSearch.setOutsideTouchable(true);

        mLvSearchResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Dealer dealer = (Dealer) mLvSearchResult.getAdapter().getItem(position);
                Map<String, Object> options = new HashMap<>();
                options.put("page", 1);
                options.put("size", 20);
                options.put("dealer", dealer.getId());
                requestCarInfo(options);
                mPopWindowSearch.dismiss();
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopWindowSearch.dismiss();
            }
        });

        mPopWindowSearch.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1.0f);
            }
        });
    }

    public void closePopupWindow () {
        if (mExpandTabView.isPopupWindowShowing()) {
            mExpandTabView.closePopupWindow();
        }
    }

    // 为ExpandTabView提供的网络请求接口
    @Override
    public void requestCarInfo(Map<String, Object> options) {
        mArgs = mExpandTabView.getArgs();
        if (!mArgs.containsKey("page")) {
            mArgs.put("page", 1);
        }
        doCarInfoRequest(options);
    }

    private void doCarInfoRequest(Map<String, Object> options) {
        mCarInfoSubscriber = new Subscriber<CarInfoListResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(getActivity(), "网络请求失败", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                mCarInfoListView.onRefreshComplete();
                mCarInfoListView.onLoadMoreComplete(true);
            }

            @Override
            public void onNext(CarInfoListResponse response) {
                List<CarInfo> carList = response.cars;

                if (mIsLoadMore) {
                    CarInfoAdapter adapter = null;

                    if (mCarInfoListView.getAdapter() instanceof HeaderViewListAdapter) {
                        HeaderViewListAdapter headerViewListAdapter = (HeaderViewListAdapter) mCarInfoListView.getAdapter();
                        adapter = (CarInfoAdapter) headerViewListAdapter.getWrappedAdapter();
                    } else {
                        adapter = (CarInfoAdapter) mCarInfoListView.getAdapter();
                    }

                    if (carList != null && carList.size() > 0) {
                        adapter.addData(carList);
                        mCarInfoListView.onLoadMoreComplete(true);
                    } else {
                        mCarInfoListView.onLoadMoreComplete(false);
                    }

                    if (!mArgs.containsKey("page")) {
                        mArgs.put("page", 1);
                    }
                    int page = 1 + (int)mArgs.get("page");
                    mArgs.put("page", page);

                } else {
                    CarInfoAdapter adapter = new CarInfoAdapter(getContext(), carList);
                    mCarInfoListView.setAdapter(adapter);
                    String time = TimeHelper.getCurrentTime();
                    mCarInfoListView.onRefreshComplete(time);
                    mArgs.put("page", 2);
                }
            }
        };

        HighWarehouseAgeApi.getInstance().searchCars(mCarInfoSubscriber, options);
    }

    private void requestDealer(String key) {
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
                List<Dealer> dealers = response.dealers;
                SearchResultAdapter adapter = new SearchResultAdapter(getContext(), dealers);

                mLvSearchResult.setAdapter(adapter);

                if (dealers.size() <= 5) {
                    mPopWindowSearch.update(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                } else {
                    int height = (int) (getActivity().getWindowManager().getDefaultDisplay().getHeight() / 2.5);
                    mPopWindowSearch.update(ViewGroup.LayoutParams.MATCH_PARENT, height);
                }

            }
        };
        HighWarehouseAgeApi.getInstance().getDealerByName(mDealerSubscriber, key);
    }

    private void requestOrder () {
        mOrderSubscriber = new Subscriber<OrderResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                mTvTipCount.setVisibility(View.GONE);
            }

            @Override
            public void onNext(OrderResponse orderResponse) {
                List<Order> orders = orderResponse.orders;
                if (orders == null || orders.size() == 0) {
                    mTvTipCount.setVisibility(View.GONE);
                } else if (orders.size() < 100) {
                    mTvTipCount.setText("" + orders.size());
                    mTvTipCount.setVisibility(View.VISIBLE);
                } else {
                    mTvTipCount.setText("9+");
                    mTvTipCount.setVisibility(View.VISIBLE);
                }
            }
        };
        HighWarehouseAgeApi.getInstance().getRequestedCars(mOrderSubscriber);
    }

    private void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getActivity().getWindow().setAttributes(lp);
    }

    @Override
    public void afterPopupWindowShow() {
        mCarInfoListView.setAlpha(0.5f);
    }

    @Override
    public void afterPopupWindowDismiss() {
        mCarInfoListView.setAlpha(1f);
        mCarInfoListView.setEnabled(true);
    }

    private boolean doOnBackPressed () {
        if (mExpandTabView.isPopupWindowShowing()) {
            mExpandTabView.closePopupWindow();
            return true;
        }
        return false;
    }
}
