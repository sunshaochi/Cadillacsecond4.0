package com.ist.cadillacpaltform.UI.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ist.cadillacpaltform.R;
import com.ist.cadillacpaltform.SDK.bean.HighStockAge.SubZone;
import com.ist.cadillacpaltform.SDK.bean.Posm.Quarter;
import com.ist.cadillacpaltform.SDK.bean.Posm.Zone;
import com.ist.cadillacpaltform.SDK.network.HighWarehouseAgeApi;
import com.ist.cadillacpaltform.SDK.network.PosmApi;
import com.ist.cadillacpaltform.SDK.response.HighStockAge.SubZoneListResponse;
import com.ist.cadillacpaltform.SDK.response.Posm.PosmZoneResponse;
import com.ist.cadillacpaltform.SDK.response.Posm.QuarterResponse;
import com.ist.cadillacpaltform.UI.adapter.ConditionAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;

/**
 * Created by dearlhd on 2017/3/3.
 */
public class POSMTabView extends LinearLayout {
    enum TextColor {BLACK, RED, GREY}

    private Context mContext;

    // 三个标签
    private LinearLayout mLlRegion;
    private LinearLayout mLlType;
    private LinearLayout mLlQuarter;

    private TextView mTvRegion;
    private TextView mTvType;
    private TextView mTvQuarter;

    private PopupWindow mPopWindow;

    private View[] mPopWindowViews;

    /* ------------ 第一个标签页下的控件 ------------ */
    private ListView mLvZone;
    private ListView mLvSubzone;

    /* ------------ 第二个标签页下的控件 ------------ */
    private TextView mTvAll;
    private TextView mTvGraded;
    private TextView mTvToGrade;

    /* ------------ 第三个标签页下的控件 ------------ */
    private TextView[] mTvQuarters = new TextView[4];

    private Map<String, Object> mArgs = new HashMap<>();
    final static private String STATUS = "status";    // 状态(0. 所有  1.已打分 2.未打分  默认为0)
    final static private String ZONE = "zone";
    final static private String SUBZONE = "subzone";
    final static private String PAGE = "page";
    final static private String SIZE = "size";
    final static private String QUARTER = "quarter";
    final static private String YEAR = "year";

    private long mZoneId;

    private Subscriber<PosmZoneResponse> mZoneSubscriber;
    private Subscriber<SubZoneListResponse> mSubZoneSubscriber;
    private Subscriber<QuarterResponse> mQuarterSubscriber;

    private List<Quarter> mLastQuarters;

    private boolean mRegionTabEnabled = true;

    public interface DealerGradeRequestListener {
        void requestDealerGrade (Map<String, Object> args);
    }

    DealerGradeRequestListener mRequestListener;


    public interface PopupWindowActionListener {
        void afterPopupWindowShow();

        void afterPopupWindowDismiss();
    }

    PopupWindowActionListener mPopActionListener;


    public POSMTabView(Context context) {
        this(context, null, 0);
    }

    public POSMTabView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public POSMTabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.ui_posm_condition_bar, this);
        initView();
    }

    public void setDealerGradeRequestListener (DealerGradeRequestListener listener) {
        mRequestListener = listener;
    }

    public boolean isPopupWindowShowing () {
        return mPopWindow.isShowing();
    }

    public void closePopWindow () {
        if (mPopWindow.isShowing()) {
            mPopWindow.dismiss();
        }
    }

    public void setPopWindowActionListener (PopupWindowActionListener listener) {
        mPopActionListener = listener;
    }

    public void setRegionEnabled (boolean enabled) {
        mRegionTabEnabled = enabled;
        resetTabColor();
    }

    private void initView() {
        mLlRegion = (LinearLayout) findViewById(R.id.ll_option_region);
        mLlType = (LinearLayout) findViewById(R.id.ll_option_type);
        mLlQuarter = (LinearLayout) findViewById(R.id.ll_option_quarter);

        mTvRegion = (TextView) findViewById(R.id.tv_option_region);
        mTvType = (TextView) findViewById(R.id.tv_option_type);
        mTvQuarter = (TextView) findViewById(R.id.tv_option_quarter);

        mPopWindowViews = new View[3];
        mPopWindowViews[0] = LayoutInflater.from(mContext).inflate(R.layout.ui_posm_region, null);
        mPopWindowViews[1] = LayoutInflater.from(mContext).inflate(R.layout.ui_posm_type, null);
        mPopWindowViews[2] = LayoutInflater.from(mContext).inflate(R.layout.ui_posm_quarter, null);

        mLvZone = (ListView) mPopWindowViews[0].findViewById(R.id.lv_zone);
        mLvSubzone = (ListView) mPopWindowViews[0].findViewById(R.id.lv_subzone);

        mTvAll = (TextView) mPopWindowViews[1].findViewById(R.id.tv_opt_all);
        mTvGraded = (TextView) mPopWindowViews[1].findViewById(R.id.tv_opt_graded);
        mTvToGrade = (TextView) mPopWindowViews[1].findViewById(R.id.tv_opt_to_grade);

        mTvQuarters[0] = (TextView) mPopWindowViews[2].findViewById(R.id.tv_quarter1);
        mTvQuarters[1] = (TextView) mPopWindowViews[2].findViewById(R.id.tv_quarter2);
        mTvQuarters[2] = (TextView) mPopWindowViews[2].findViewById(R.id.tv_quarter3);
        mTvQuarters[3] = (TextView) mPopWindowViews[2].findViewById(R.id.tv_quarter4);

        initPopupWindow();

        initTab();

        initRegionTab();
        initTypeTab();
        initQuarterTab();
    }

    private void initPopupWindow() {
        mPopWindow = new PopupWindow(mPopWindowViews[0],
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        Drawable drawable = getResources().getDrawable(R.drawable.bg_white);
        mPopWindow.setTouchable(true);
        mPopWindow.setFocusable(false);
        mPopWindow.setBackgroundDrawable(drawable);

        // TODO 设置popupWindow消失后的listener
        mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                resetTabColor();
                if (mPopActionListener != null) {
                    mPopActionListener.afterPopupWindowDismiss();
                }
            }
        });
    }

    private void initTab() {
        mLlRegion.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mRegionTabEnabled) {
                    return;
                }

                resetTabColor();
                mPopWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                int height = ((Activity) mContext).getWindowManager().getDefaultDisplay().getHeight() / 2;
                mPopWindow.setHeight(height);

                if (mPopWindow.isShowing() && mPopWindow.getContentView() == mPopWindowViews[0]) {
                    mPopWindow.dismiss();
                } else {
                    changeTextColor(mTvRegion, TextColor.RED);
                    mPopWindow.dismiss();
                    mPopWindow.setContentView(mPopWindowViews[0]);
                    mPopWindow.showAsDropDown(v);
                    if (mPopActionListener != null) {
                        mPopActionListener.afterPopupWindowShow();
                    }
                }
            }
        });

        mLlType.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTabColor();
                mPopWindow.setWidth(mLlType.getWidth());
                mPopWindow.setHeight(mLlType.getHeight() * 3);

                if (mPopWindow.isShowing() && mPopWindow.getContentView() == mPopWindowViews[1]) {
                    mPopWindow.dismiss();
                } else {
                    changeTextColor(mTvType, TextColor.RED);
                    mPopWindow.dismiss();
                    mPopWindow.setContentView(mPopWindowViews[1]);
                    mPopWindow.showAsDropDown(v);
                    if (mPopActionListener != null) {
                        mPopActionListener.afterPopupWindowShow();
                    }
                }
            }
        });

        mLlQuarter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTabColor();
                mPopWindow.setWidth(mLlQuarter.getWidth());
                mPopWindow.setHeight(mLlQuarter.getHeight() * 3);

                if (mPopWindow.isShowing() && mPopWindow.getContentView() == mPopWindowViews[2]) {
                    mPopWindow.dismiss();
                } else {
                    changeTextColor(mTvQuarter, TextColor.RED);
                    mPopWindow.dismiss();
                    mPopWindow.setContentView(mPopWindowViews[2]);
                    mPopWindow.showAsDropDown(v);
                    if (mPopActionListener != null) {
                        mPopActionListener.afterPopupWindowShow();
                    }
                }
            }
        });
    }

    private void initRegionTab() {
        mZoneSubscriber = new Subscriber<PosmZoneResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(PosmZoneResponse posmZoneResponse) {
                List<Zone> zones = posmZoneResponse.zones;
                List<String> zoneStrings = new ArrayList<>();
                zoneStrings.add("不限");
                for (int i = 0; i < zones.size(); i++) {
                    zoneStrings.add(zones.get(i).getName());
                }
                ConditionAdapter adapter = new ConditionAdapter(getContext(), zoneStrings,zones);
                mLvZone.setAdapter(adapter);
                mPopWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

            }
        };

        if (mLvZone.getAdapter() == null) {
            PosmApi.getInstance().getZones(mZoneSubscriber);
        }

        mLvZone.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ConditionAdapter adapter = (ConditionAdapter) mLvZone.getAdapter();
                resetListText(mLvZone);
                adapter.setSelectedPos(position);

                ConditionAdapter.ViewHolder holder = (ConditionAdapter.ViewHolder) view.getTag();
                changeTextColor(holder.tvContent, TextColor.RED);
                String txt = holder.tvContent.getText().toString();

                if (position > 0) {
                    Zone zone = (Zone) mLvZone.getAdapter().getItem(position - 1);
                    mZoneId = zone.getId();
                }

                if (!txt.equals("不限")) {
                    mSubZoneSubscriber = new Subscriber<SubZoneListResponse>() {
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

                                    resetListText(mLvSubzone);
                                    adapter.setSelectedPos(position);

                                    ConditionAdapter.ViewHolder holder = (ConditionAdapter.ViewHolder) view.getTag();
                                    changeTextColor(holder.tvContent, TextColor.RED);

                                    String subzoneTxt = holder.tvContent.getText().toString();
                                    if (subzoneTxt.equals("不限")) {
                                        mArgs.put(ZONE, mZoneId);
                                        mArgs.remove(SUBZONE);
                                    } else {
                                        mArgs.put(ZONE, mZoneId);
                                        SubZone subZone = (SubZone) mLvSubzone.getAdapter().getItem(position - 1);
                                        mArgs.put(SUBZONE, subZone.getId());
                                    }

                                    requestGradeList();
                                    mPopWindow.dismiss();
                                }
                            });
                        }
                    };
                    HighWarehouseAgeApi.getInstance().getSubzonesByZone(mSubZoneSubscriber, mZoneId);
                } else {
                    mLvSubzone.setAdapter(null);
                    mArgs.put(ZONE, mZoneId);
                    mArgs.remove(SUBZONE);
                    requestGradeList();

                    mPopWindow.dismiss();
                }
            }
        });
    }

    private void initTypeTab() {
        mTvAll.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTypeTextColor();
                changeTextColor(mTvAll, TextColor.RED);
                v.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_grey));
                mTvType.setText("全部");
                mArgs.put(STATUS, 0);
                requestGradeList();
                mPopWindow.dismiss();
            }
        });

        mTvGraded.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTypeTextColor();
                changeTextColor(mTvGraded, TextColor.RED);
                v.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_grey));
                mTvType.setText("已打分");
                mArgs.put(STATUS, 1);
                requestGradeList();
                mPopWindow.dismiss();
            }
        });

        mTvToGrade.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTypeTextColor();
                changeTextColor(mTvToGrade, TextColor.RED);
                v.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_grey));
                mTvType.setText("待打分");
                mArgs.put(STATUS, 2);
                requestGradeList();
                mPopWindow.dismiss();
            }
        });
    }

    private void initQuarterTab() {
        mQuarterSubscriber = new Subscriber<QuarterResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(QuarterResponse response) {
                mLastQuarters = response.quarters;
                mArgs.put(YEAR, mLastQuarters.get(0).getYear());
                mArgs.put(QUARTER, mLastQuarters.get(0).getQuarter());
                String quarter;
                for (int i = 0; i < mLastQuarters.size(); i++) {
                    quarter = mLastQuarters.get(i).getYear() + ".Q" + mLastQuarters.get(i).getQuarter();
                    mTvQuarters[i].setText(quarter);
                }
                mTvQuarter.setText(mTvQuarters[0].getText());
            }
        };
        PosmApi.getInstance().getQuarterList(mQuarterSubscriber);

        mTvQuarters[0].setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setTvQuarterOnClickListener(0);
                mTvAll.callOnClick();
                mLlType.setEnabled(true);

            }
        });

        // 当过去的季度被点击时，仅显示已打分部分
        mTvQuarters[1].setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setTvQuarterOnClickListener(1);
                mTvGraded.callOnClick();
                mLlType.setEnabled(false);
            }
        });

        mTvQuarters[2].setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setTvQuarterOnClickListener(2);
                mTvGraded.callOnClick();
                mLlType.setEnabled(false);
            }
        });

        mTvQuarters[3].setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setTvQuarterOnClickListener(3);
                mTvGraded.callOnClick();
                mLlType.setEnabled(false);
            }
        });

    }

    private void setTvQuarterOnClickListener (int index) {
        resetQuarterTextColor();
        changeTextColor(mTvQuarters[index], TextColor.RED);
        mTvQuarters[index].setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_grey));
        String quarter = mLastQuarters.get(index).getYear() + ".Q" + mLastQuarters.get(index).getQuarter();
        mTvQuarter.setText(quarter);
        mArgs.put(YEAR, mLastQuarters.get(index).getYear());
        mArgs.put(QUARTER, mLastQuarters.get(index).getQuarter());
        requestGradeList();
        mPopWindow.dismiss();
    }

    private void changeTextColor(TextView tv, TextColor color) {
        switch (color) {
            case RED:
                tv.setTextColor(getResources().getColor(R.color.colorRedText));
                break;
            case BLACK:
                tv.setTextColor(getResources().getColor(R.color.colorBlackText));
                break;
            case GREY:
                tv.setTextColor(getResources().getColor(R.color.colorGreyText));
                break;
            default:
                break;
        }
    }

    private void resetTabColor() {
        changeTextColor(mTvRegion, mRegionTabEnabled ? TextColor.BLACK : TextColor.GREY);
        changeTextColor(mTvType, TextColor.BLACK);
        changeTextColor(mTvQuarter, TextColor.BLACK);
    }

    private void resetTypeTextColor() {
        changeTextColor(mTvAll, TextColor.BLACK);
        changeTextColor(mTvGraded, TextColor.BLACK);
        changeTextColor(mTvToGrade, TextColor.BLACK);
        mTvAll.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_white));
        mTvGraded.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_white));
        mTvToGrade.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_white));
    }

    private void resetQuarterTextColor() {
        for (TextView tv : mTvQuarters) {
            changeTextColor(tv, TextColor.BLACK);
            tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_white));
        }
    }

    private void resetListText(ListView listView) {
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

    private void requestGradeList () {
        if (mRequestListener != null) {
            mArgs.put(PAGE, 1);
            mRequestListener.requestDealerGrade(mArgs);
        }
    }
}
