package com.ist.cadillacpaltform.UI.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.ist.cadillacpaltform.R;
import com.ist.cadillacpaltform.SDK.bean.HighStockAge.CarColor;
import com.ist.cadillacpaltform.SDK.bean.HighStockAge.CarConfig;
import com.ist.cadillacpaltform.SDK.bean.HighStockAge.CarLine;
import com.ist.cadillacpaltform.SDK.bean.HighStockAge.City;
import com.ist.cadillacpaltform.SDK.bean.HighStockAge.Province;
import com.ist.cadillacpaltform.SDK.bean.HighStockAge.Zone;
import com.ist.cadillacpaltform.SDK.network.HighWarehouseAgeApi;
import com.ist.cadillacpaltform.SDK.response.HighStockAge.CarColorListResponse;
import com.ist.cadillacpaltform.SDK.response.HighStockAge.CarConfigListResponse;
import com.ist.cadillacpaltform.SDK.response.HighStockAge.CarLineListResponse;
import com.ist.cadillacpaltform.SDK.response.HighStockAge.SubZoneListResponse;
import com.ist.cadillacpaltform.SDK.response.HighStockAge.ZoneListResponse;
import com.ist.cadillacpaltform.SDK.util.RegionParser;
import com.ist.cadillacpaltform.UI.adapter.ConditionAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;

/**
 * Created by dearlhd on 2016/12/20.
 */
public class ExpandTabView extends LinearLayout {

    enum TextColor {BLACK, RED, GREY}

    // 请求的key
    final private String ZONE = "zone";
    final private String LOCATION = "location";
    final private String DEALER = "dealer";
    final private String LINE = "line";
    final private String CONFIG = "configuration";
    final private String COLOR = "color";
    final private String ORDER_BY = "order_by";

    private Context mContext;

    // 3个标签栏
    private LinearLayout mTabRegionLayout;
    private LinearLayout mTabConfigLayout;
    private LinearLayout mTabOrderLayout;


    private boolean mRegionTabEnabled = true;

    // 3个文字标签栏
    private TextView mTvRegion;
    private TextView mTvConfig;
    private TextView mTvOrder;

    // 3个标签栏的箭头
    private ImageView mIvRegion;
    private ImageView mIvConfig;
    private ImageView mIvOrder;

    private PopupWindow mMultiLevelPopupWindow;

    private View[] mPopWindowViews;     // 3个标签(地区、配置、排序)对应的下拉视图

    /**
     * 以下为popupWindow中的控件
     */

    private TextView mTvSelectZone;
    private TextView mTvSelectProvince;

    /**
     * 按地区选择的控件
     */

    // 按大区选择的二级列表
    private LinearLayout mMultiLevelLayout1;
    private ListView mLvZones;
    private ListView mLvSubzone;

    //按省市选择的二级列表
    private LinearLayout mMultiLevelLayout2;
    private ListView mLvProvince;
    private ListView mLvCity;

    /**
     * 按配置选择的控件
     */
    private LinearLayout mConfigMultiLevelLayout;
    private ListView mLvLine;
    private ListView mLvConfig;
    private ListView mLvColor;

    /**
     * 排序的控件
     */
    private TextView mTvOrderTime;
    private TextView mTvOrderPrice;
    private TextView mTvOrderDistance;

    /**
     * 网络请求的subscriber
     */
    private Subscriber<ZoneListResponse> mZoneSubscriber;          // 请求大区
    private Subscriber<SubZoneListResponse> mSubzoneSubscriber;    // 请求小区

    private Subscriber<CarLineListResponse> mLineSubscriber;       // 请求车系
    private Subscriber<CarConfigListResponse> mConfigSubscriber;   // 请求配置
    private Subscriber<CarColorListResponse> mColorSubscriber;     // 请求颜色

    CarRequestListener mCarRequestListener;
    PopupWindowActionListener mPopActionLister;

    // 请求车辆信息的条件
    private Map<String, Object> mConditionMap = new HashMap<String, Object>();
    private String mZone = "";
    private String mLocation = "";
    private String mConfig = "";


    public ExpandTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.ui_search_condition_bar, this);

        initView();
    }

    public interface CarRequestListener {
        void requestCarInfo(Map<String, Object> opts);
    }

    public void setCarRequestListener(CarRequestListener listener) {
        mCarRequestListener = listener;
    }

    public interface PopupWindowActionListener {
        void afterPopupWindowShow();

        void afterPopupWindowDismiss();
    }

    public void setPopActionLister(PopupWindowActionListener listener) {
        mPopActionLister = listener;
    }

    public void setRegionTabEnabled (boolean enabled) {
        mRegionTabEnabled = enabled;
        recoverTabTextColor();
    }


    public boolean isPopupWindowShowing () {
        return mMultiLevelPopupWindow.isShowing();
    }

    public void closePopupWindow () {
        mMultiLevelPopupWindow.dismiss();
    }

    public Map<String, Object> getArgs() {
        mConditionMap.put("page", 1);
        mConditionMap.put("size", 20);

        return mConditionMap;
    }

    private void initView() {
        mTabRegionLayout = (LinearLayout) findViewById(R.id.ll_option_region);
        mTabConfigLayout = (LinearLayout) findViewById(R.id.ll_option_config);
        mTabOrderLayout = (LinearLayout) findViewById(R.id.ll_option_order);

        mTvRegion = (TextView) findViewById(R.id.tv_option_region);
        mTvConfig = (TextView) findViewById(R.id.tv_option_config);
        mTvOrder = (TextView) findViewById(R.id.tv_option_order);

        // 3个子页面
        mPopWindowViews = new View[3];
        mPopWindowViews[0] = LayoutInflater.from(mContext).inflate(R.layout.ui_region_condition, null);
        mPopWindowViews[1] = LayoutInflater.from(mContext).inflate(R.layout.ui_config_condition, null);
        mPopWindowViews[2] = LayoutInflater.from(mContext).inflate(R.layout.ui_order_condition, null);

        // 地区页面下
        mTvSelectZone = (TextView) mPopWindowViews[0].findViewById(R.id.tv_select_zone);
        mLvZones = (ListView) mPopWindowViews[0].findViewById(R.id.lv_first_level);
        mLvSubzone = (ListView) mPopWindowViews[0].findViewById(R.id.lv_second_level);

        // 省市页面下
        mTvSelectProvince = (TextView) mPopWindowViews[0].findViewById(R.id.tv_select_province);
        mLvProvince = (ListView) mPopWindowViews[0].findViewById(R.id.lv_province);
        mLvCity = (ListView) mPopWindowViews[0].findViewById(R.id.lv_city);

        mMultiLevelLayout1 = (LinearLayout) mPopWindowViews[0].findViewById(R.id.ll_multiLevel1);
        mMultiLevelLayout2 = (LinearLayout) mPopWindowViews[0].findViewById(R.id.ll_multiLevel2);

        // 按配置筛选
        mLvLine = (ListView) mPopWindowViews[1].findViewById(R.id.lv_select_line);
        mLvConfig = (ListView) mPopWindowViews[1].findViewById(R.id.lv_select_config);
        mLvColor = (ListView) mPopWindowViews[1].findViewById(R.id.lv_select_color);

        mConfigMultiLevelLayout = (LinearLayout) mPopWindowViews[1].findViewById(R.id.ll_config_multi_level);

        // 按不同方式排序
        mTvOrderTime = (TextView) mPopWindowViews[2].findViewById(R.id.tv_order_time);
        mTvOrderPrice = (TextView) mPopWindowViews[2].findViewById(R.id.tv_order_price);
        mTvOrderDistance = (TextView) mPopWindowViews[2].findViewById(R.id.tv_order_distance);

        initPopupWindow();
        initPopupWindowViews();

        // 按区域筛选
        mTabRegionLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mRegionTabEnabled) {
                    return;
                }

                mMultiLevelPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                int height = ((Activity) mContext).getWindowManager().getDefaultDisplay().getHeight() / 2;
                mMultiLevelPopupWindow.setHeight(height);

                if (mMultiLevelPopupWindow.isShowing() && mMultiLevelPopupWindow.getContentView() == mPopWindowViews[0]) {
                    mMultiLevelPopupWindow.dismiss();
                } else if (mMultiLevelPopupWindow.isShowing()) {
                    mMultiLevelPopupWindow.dismiss();
                    mMultiLevelPopupWindow.setContentView(mPopWindowViews[0]);
                    mMultiLevelPopupWindow.showAsDropDown(view);

                    if (mPopActionLister != null) {
                        mPopActionLister.afterPopupWindowShow();
                    }

                    changeTextColor(mTvRegion, TextColor.RED);

                    Drawable drawable = getResources().getDrawable(R.drawable.bg_grey);
                    mMultiLevelLayout1.setBackgroundDrawable(drawable);
                    mMultiLevelLayout2.setBackgroundDrawable(drawable);
                    mMultiLevelLayout1.setVisibility(VISIBLE);
                    mMultiLevelLayout2.setVisibility(GONE);
                    mTvSelectZone.callOnClick();
                } else {
                    mMultiLevelPopupWindow.setContentView(mPopWindowViews[0]);
                    mMultiLevelPopupWindow.showAsDropDown(view);

                    if (mPopActionLister != null) {
                        mPopActionLister.afterPopupWindowShow();
                    }

                    changeTextColor(mTvRegion, TextColor.RED);

                    mTvSelectZone.callOnClick();
                }
            }
        });

        // 按配置筛选
        mTabConfigLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mMultiLevelPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                int height = ((Activity) mContext).getWindowManager().getDefaultDisplay().getHeight() / 2;
                mMultiLevelPopupWindow.setHeight(height);

                if (mMultiLevelPopupWindow.isShowing() && mMultiLevelPopupWindow.getContentView() == mPopWindowViews[1]) {
                    mMultiLevelPopupWindow.dismiss();
                } else if (mMultiLevelPopupWindow.isShowing()) {
                    mMultiLevelPopupWindow.dismiss();
                    mMultiLevelPopupWindow.setContentView(mPopWindowViews[1]);
                    Drawable drawable = getResources().getDrawable(R.drawable.bg_grey);
                    mConfigMultiLevelLayout.setBackgroundDrawable(drawable);
                    mMultiLevelPopupWindow.showAsDropDown(view);

                    if (mPopActionLister != null) {
                        mPopActionLister.afterPopupWindowShow();
                    }

                    changeTextColor(mTvConfig, TextColor.RED);
                } else {
                    mMultiLevelPopupWindow.setContentView(mPopWindowViews[1]);
                    mMultiLevelPopupWindow.showAsDropDown(view);

                    if (mPopActionLister != null) {
                        mPopActionLister.afterPopupWindowShow();
                    }

                    changeTextColor(mTvConfig, TextColor.RED);
                }
            }
        });

        // 按顺序筛选
        mTabOrderLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mMultiLevelPopupWindow.setWidth(mTabOrderLayout.getWidth());
                mMultiLevelPopupWindow.setHeight(mTabOrderLayout.getHeight() * 3);

                if (mMultiLevelPopupWindow.isShowing() && mMultiLevelPopupWindow.getContentView() == mPopWindowViews[2]) {
                    mMultiLevelPopupWindow.dismiss();
                } else if (mMultiLevelPopupWindow.isShowing()) {
                    mMultiLevelPopupWindow.dismiss();
                    mMultiLevelPopupWindow.setContentView(mPopWindowViews[2]);
                    mMultiLevelPopupWindow.showAsDropDown(view);

                    if (mPopActionLister != null) {
                        mPopActionLister.afterPopupWindowShow();
                    }

                    changeTextColor(mTvOrder, TextColor.RED);
                } else {
                    mMultiLevelPopupWindow.setContentView(mPopWindowViews[2]);
                    mMultiLevelPopupWindow.showAsDropDown(view);

                    if (mPopActionLister != null) {
                        mPopActionLister.afterPopupWindowShow();
                    }

                    changeTextColor(mTvOrder, TextColor.RED);
                }
            }
        });
        initOrderViews();
    }

    private void initPopupWindow() {

        mMultiLevelPopupWindow = new PopupWindow(mPopWindowViews[0],
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Drawable drawable = getResources().getDrawable(R.drawable.bg_white);
        mMultiLevelPopupWindow.setTouchable(true);
        mMultiLevelPopupWindow.setFocusable(false);
//        mMultiLevelPopupWindow.setBackgroundDrawable(drawable);
//        mMultiLevelPopupWindow.setOutsideTouchable(true);

        mMultiLevelPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                recoverTabTextColor();
                if (mPopActionLister != null) {
                    mPopActionLister.afterPopupWindowDismiss();
                }
            }
        });
    }

    private void initPopupWindowViews() {
        initRegionView();
        initConfigView();
    }

    private void initRegionView() {
        /* 以下为按大区筛选的相关处理 */
        initZoneView();

        /* 以下为按省市筛选的相关处理 */
        initProvinceView();
    }

    /**
     *
     */
    private void initZoneView() {

        mTvSelectZone.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View view) {

                mMultiLevelLayout1.setVisibility(VISIBLE);
                mMultiLevelLayout2.setVisibility(GONE);

                changeTextColor(mTvSelectZone, TextColor.RED);
                changeTextColor(mTvSelectProvince, TextColor.BLACK);

                Drawable drawable = getResources().getDrawable(R.drawable.bg_grey);
                view.setBackgroundDrawable(drawable);
                drawable = getResources().getDrawable(R.drawable.bg_white);
                mTvSelectProvince.setBackgroundDrawable(drawable);

                mZoneSubscriber = new Subscriber<ZoneListResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(ZoneListResponse zoneListResponse) {

                        List<Zone> zones = zoneListResponse.zones;
                        List<String> data = new ArrayList<String>();
                        data.add("不限");
                        for (int i = 0; i < zones.size(); i++) {
                            data.add(zones.get(i).getName());
                        }
                        ConditionAdapter adapter = new ConditionAdapter(mContext, data, zones);
                        mLvZones.setAdapter(adapter);
                        mMultiLevelPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                    }
                };
                if (mLvZones.getAdapter() == null) {
                    HighWarehouseAgeApi.getInstance().getZones(mZoneSubscriber);
                }
            }
        });

        //大区获取小区
        mLvZones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // 改变选中item的颜色和恢复上次选中的item的颜色
                ConditionAdapter adapter = (ConditionAdapter) mLvZones.getAdapter();

                recoverListText(mLvZones);
                adapter.setSelectedPos(i);

                ConditionAdapter.ViewHolder holder = (ConditionAdapter.ViewHolder) view.getTag();
                changeTextColor(holder.tvContent, TextColor.RED);
                String txt = holder.tvContent.getText().toString();
                long id = 0;
                if (i > 0) {
                    Zone zone = (Zone) mLvZones.getAdapter().getItem(i - 1);
                    id = zone.getId();
                }

                if (!txt.equals("不限")) {
                    mSubzoneSubscriber = new Subscriber<SubZoneListResponse>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onNext(SubZoneListResponse response) {
                            List<String> data = new ArrayList<String>();
                            data.add("不限");
                            for (int i = 0; i < response.subZones.size(); i++) {
                                data.add(response.subZones.get(i).getName());
                            }

                            ConditionAdapter adapter = new ConditionAdapter(mContext, data, response.subZones);
                            mLvSubzone.setAdapter(adapter);
                            mLvSubzone.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    ConditionAdapter adapter = (ConditionAdapter) mLvSubzone.getAdapter();

                                    recoverListText(mLvSubzone);
                                    adapter.setSelectedPos(position);

                                    ConditionAdapter.ViewHolder holder = (ConditionAdapter.ViewHolder) view.getTag();
                                    changeTextColor(holder.tvContent, TextColor.RED);

                                    String subzoneTxt = holder.tvContent.getText().toString();
                                    if (subzoneTxt.equals("不限")) {
                                        mConditionMap.put(ZONE, mZone);
                                    } else {
                                        mConditionMap.put(ZONE, mZone + subzoneTxt);
                                        String completeZone = mZone + subzoneTxt;
                                    }

                                    mCarRequestListener.requestCarInfo(mConditionMap);
                                    mMultiLevelPopupWindow.dismiss();
                                }
                            });
                        }
                    };
                    HighWarehouseAgeApi.getInstance().getSubzonesByZone(mSubzoneSubscriber, id);
                    mZone = txt;
                } else {
                    mLvSubzone.setAdapter(null);
                    mConditionMap.put(ZONE, "");
                    mCarRequestListener.requestCarInfo(mConditionMap);
                    mMultiLevelPopupWindow.dismiss();
                }
            }
        });
    }

    private void initProvinceView() {

        mTvSelectProvince.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mMultiLevelLayout2.setVisibility(VISIBLE);
                mMultiLevelLayout1.setVisibility(GONE);

                changeTextColor(mTvSelectZone, TextColor.BLACK);
                changeTextColor(mTvSelectProvince, TextColor.RED);

                Drawable drawable = getResources().getDrawable(R.drawable.bg_grey);
                mTvSelectProvince.setBackgroundDrawable(drawable);
                drawable = getResources().getDrawable(R.drawable.bg_white);
                mTvSelectZone.setBackgroundDrawable(drawable);

                try {
                    final RegionParser parser = new RegionParser(mContext);
                    List<Province> provinces = parser.getProvinces();
                    List<String> provinceNames = new ArrayList<String>();
                    provinceNames.add("不限");
                    for (int i = 0; i < provinces.size(); i++) {
                        provinceNames.add(provinces.get(i).getName());
                    }
                    ConditionAdapter adapter = new ConditionAdapter(mContext, provinceNames, provinces);
                    mLvProvince.setAdapter(adapter);
                    mLvProvince.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            ConditionAdapter adapter = (ConditionAdapter) mLvProvince.getAdapter();

                            recoverListText(mLvProvince);
                            adapter.setSelectedPos(position);

                            ConditionAdapter.ViewHolder holder = (ConditionAdapter.ViewHolder) view.getTag();
                            changeTextColor(holder.tvContent, TextColor.RED);

                            String province = holder.tvContent.getText().toString();
                            if (!province.equals("不限")) {
                                mLocation = province;
                                List<City> cities = parser.getCitiesByProvince(province);
                                List<String> cityNames = new ArrayList<String>();
                                cityNames.add("不限");
                                for (int i = 0; i < cities.size(); i++) {
                                    cityNames.add(cities.get(i).getName());
                                }
                                adapter = new ConditionAdapter(mContext, cityNames, null);
                                mLvCity.setAdapter(adapter);

                                mLvCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        ConditionAdapter adapter = (ConditionAdapter) mLvCity.getAdapter();

                                        recoverListText(mLvCity);
                                        adapter.setSelectedPos(position);

                                        final ConditionAdapter.ViewHolder holder = (ConditionAdapter.ViewHolder) view.getTag();
                                        changeTextColor(holder.tvContent, TextColor.RED);

                                        String city = holder.tvContent.getText().toString();
                                        if (!city.equals("不限")) {
                                            mConditionMap.put(LOCATION, mLocation + city);
                                        } else {
                                            mConditionMap.put(LOCATION, mLocation);
                                        }
                                        mCarRequestListener.requestCarInfo(mConditionMap);
                                        mMultiLevelPopupWindow.dismiss();
                                    }
                                });
                            } else {
                                mLvCity.setAdapter(null);
                                mConditionMap.put(LOCATION, "");
                                mCarRequestListener.requestCarInfo(mConditionMap);
                                mMultiLevelPopupWindow.dismiss();
                            }

                        }
                    });

                } catch (Exception e) {
                    Toast.makeText(mContext, "系统文件损坏，请联系管理员", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        });
    }

    private void initConfigView() {
        mLineSubscriber = new Subscriber<CarLineListResponse>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(CarLineListResponse carLineListResponse) {
                List<CarLine> lines = carLineListResponse.lines;
                List<String> lineNames = new ArrayList<>();
                lineNames.add("不限");
                for (int i = 0; i < lines.size(); i++) {
                    lineNames.add(lines.get(i).getName());
                }

                ConditionAdapter adapter = new ConditionAdapter(mContext, lineNames, lines);
                mLvLine.setAdapter(adapter);
                mLvLine.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ConditionAdapter adapter = (ConditionAdapter) mLvLine.getAdapter();

                        mLvColor.setAdapter(null);
                        recoverListText(mLvLine);
                        adapter.setSelectedPos(position);

                        final ConditionAdapter.ViewHolder holder = (ConditionAdapter.ViewHolder) view.getTag();
                        changeTextColor(holder.tvContent, TextColor.RED);
                        if (!holder.tvContent.getText().equals("不限")) {
                            mConfigSubscriber = new Subscriber<CarConfigListResponse>() {
                                @Override
                                public void onCompleted() {
                                }

                                @Override
                                public void onError(Throwable e) {
                                    e.printStackTrace();
                                }

                                @Override
                                public void onNext(CarConfigListResponse carConfigListResponse) {
                                    List<CarConfig> configs = carConfigListResponse.configs;
                                    List<String> configInfos = new ArrayList<String>();
                                    configInfos.add("不限");
                                    for (int i = 0; i < configs.size(); i++) {
                                        configInfos.add(configs.get(i).getName());
                                    }
                                    ConditionAdapter configAdapter = new ConditionAdapter(mContext, configInfos, configs);
                                    mLvConfig.setAdapter(configAdapter);
                                    mLvConfig.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            ConditionAdapter adapter = (ConditionAdapter) mLvConfig.getAdapter();

                                            recoverListText(mLvConfig);
                                            adapter.setSelectedPos(position);

                                            ConditionAdapter.ViewHolder holder1 = (ConditionAdapter.ViewHolder) view.getTag();
                                            changeTextColor(holder1.tvContent, TextColor.RED);
                                            if (!holder1.tvContent.getText().equals("不限")) {
                                                mColorSubscriber = new Subscriber<CarColorListResponse>() {
                                                    @Override
                                                    public void onCompleted() {
                                                    }

                                                    @Override
                                                    public void onError(Throwable e) {
                                                        e.printStackTrace();
                                                    }

                                                    @Override
                                                    public void onNext(CarColorListResponse carColorListResponse) {
                                                        List<CarColor> colors = carColorListResponse.colors;
                                                        List<String> colorName = new ArrayList<String>();
                                                        colorName.add("不限");
                                                        for (int i = 0; i < colors.size(); i++) {
                                                            colorName.add(colors.get(i).getColor());
                                                        }
                                                        ConditionAdapter colorAdapter = new ConditionAdapter(mContext, colorName, colors);
                                                        mLvColor.setAdapter(colorAdapter);
                                                        mLvColor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                            @Override
                                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                                ConditionAdapter adapter = (ConditionAdapter) mLvColor.getAdapter();

                                                                recoverListText(mLvColor);
                                                                adapter.setSelectedPos(position);

                                                                ConditionAdapter.ViewHolder holder = (ConditionAdapter.ViewHolder) view.getTag();
                                                                changeTextColor(holder.tvContent, TextColor.RED);
                                                                String color = holder.tvContent.getText().toString();
                                                                if (!color.equals("不限")) {
                                                                    mConditionMap.put(COLOR, color);
                                                                } else {
                                                                    mConditionMap.put(COLOR, "");
                                                                }
                                                                mCarRequestListener.requestCarInfo(mConditionMap);
                                                                mMultiLevelPopupWindow.dismiss();
                                                            }
                                                        });
                                                    }
                                                };
                                                CarConfig config = (CarConfig) mLvConfig.getAdapter().getItem(position - 1);
                                                mConditionMap.put(CONFIG, config.getId());
                                                HighWarehouseAgeApi.getInstance().getColorByConfig(mColorSubscriber, config.getId());
                                            } else {
                                                mConditionMap.put(CONFIG, 0);
                                                mConditionMap.put(COLOR, "");
                                                mCarRequestListener.requestCarInfo(mConditionMap);
                                                mMultiLevelPopupWindow.dismiss();
                                            }
                                        }
                                    });
                                }
                            };
                            CarLine line = (CarLine) mLvLine.getAdapter().getItem(position - 1);
                            mConditionMap.put(LINE, line.getId());
                            HighWarehouseAgeApi.getInstance().getConfigByLine(mConfigSubscriber, line.getId());
                        } else {
                            mLvConfig.setAdapter(null);
                            mLvColor.setAdapter(null);
                            mConditionMap.put(LINE, 0);
                            mConditionMap.put(CONFIG, 0);
                            mConditionMap.put(COLOR, "");
                            mCarRequestListener.requestCarInfo(mConditionMap);
                            mMultiLevelPopupWindow.dismiss();
                        }
                    }
                });
            }
        };
        if (mLvLine.getAdapter() == null) {
            HighWarehouseAgeApi.getInstance().getAllLines(mLineSubscriber);
        }
    }

    private void initOrderViews() {
        mTvOrderTime.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mConditionMap.put(ORDER_BY, 0);
                mCarRequestListener.requestCarInfo(getArgs());
                mTvOrder.setText("时间优先");
                changeTextColor(mTvOrderTime, TextColor.RED);
                changeTextColor(mTvOrderPrice, TextColor.BLACK);
                changeTextColor(mTvOrderDistance, TextColor.BLACK);
                mMultiLevelPopupWindow.dismiss();
            }
        });

        mTvOrderPrice.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mConditionMap.put(ORDER_BY, 1);
                mCarRequestListener.requestCarInfo(getArgs());
                mTvOrder.setText("价格优先");
                changeTextColor(mTvOrderTime, TextColor.BLACK);
                changeTextColor(mTvOrderPrice, TextColor.RED);
                changeTextColor(mTvOrderDistance, TextColor.BLACK);
                mMultiLevelPopupWindow.dismiss();
            }
        });

        mTvOrderDistance.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mConditionMap.put(ORDER_BY, 2);
                mCarRequestListener.requestCarInfo(getArgs());
                mTvOrder.setText("距离优先");
                changeTextColor(mTvOrderTime, TextColor.BLACK);
                changeTextColor(mTvOrderPrice, TextColor.BLACK);
                changeTextColor(mTvOrderDistance, TextColor.RED);
                mMultiLevelPopupWindow.dismiss();
            }
        });
    }

    private void changeTextColor(TextView view, TextColor color) {
        switch (color) {
            case RED:
                view.setTextColor(getResources().getColor(R.color.colorRedText));
                break;
            case BLACK:
                view.setTextColor(getResources().getColor(R.color.colorBlackText));
                break;
            case GREY:
                view.setTextColor(getResources().getColor(R.color.colorGreyText));
                break;
            default:
                break;
        }
    }

    private void recoverTabTextColor() {
        changeTextColor(mTvRegion, mRegionTabEnabled ? TextColor.BLACK : TextColor.GREY);    // 当地区不能选择时，显示灰色
        changeTextColor(mTvConfig, TextColor.BLACK);
        changeTextColor(mTvOrder, TextColor.BLACK);
    }

    private void recoverListText(ListView listView) {
        ConditionAdapter adapter = (ConditionAdapter) listView.getAdapter();

        //adapter.notifyDataSetChanged();
        int lastPos = adapter.getSelectedPos();
        if (lastPos != -1) {
            View v = listView.getChildAt(lastPos - listView.getFirstVisiblePosition()); // 定位方式
            if (v != null) {
                ConditionAdapter.ViewHolder holder = (ConditionAdapter.ViewHolder) v.getTag();
                changeTextColor(holder.tvContent, TextColor.BLACK);
            }
        }
    }
}
