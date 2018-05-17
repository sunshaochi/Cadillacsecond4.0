package com.ist.cadillacpaltform.UI.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.HeaderViewListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ist.cadillacpaltform.R;
import com.ist.cadillacpaltform.SDK.bean.Authorization;
import com.ist.cadillacpaltform.SDK.bean.Posm.DealerGrade;
import com.ist.cadillacpaltform.SDK.bean.Posm.Paper;
import com.ist.cadillacpaltform.SDK.network.PosmApi;
import com.ist.cadillacpaltform.SDK.response.Posm.DealerGradeResponse;
import com.ist.cadillacpaltform.SDK.response.Posm.GradeResponse;
import com.ist.cadillacpaltform.SDK.response.Posm.QuarterResponse;
import com.ist.cadillacpaltform.SDK.util.SQLiteHelper;
import com.ist.cadillacpaltform.SDK.util.TimeHelper;
import com.ist.cadillacpaltform.UI.adapter.PosmHomeAdapter;
import com.ist.cadillacpaltform.UI.view.LoadingDialog;
import com.ist.cadillacpaltform.UI.view.POSMTabView;
import com.ist.cadillacpaltform.UI.view.PullToRefreshListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Created by dearlhd on 2017/3/3.
 */
public class POSMHomeActivity extends Activity {

    private boolean mIsLoadMore;
    private Map<String, Object> mArgs = new HashMap<>();
    private int mYear = -1;
    private int mQuarter = -1;

    private ImageView mIvBack;

    private POSMTabView mTabView;

    private PullToRefreshListView mListView;

    private LinearLayout mLlFillBlank;

    private LoadingDialog mLoadingDialog;

    private Subscriber<QuarterResponse> mQuarterSubscriber;
    private Subscriber<DealerGradeResponse> mSubscriber;
    private Subscriber<GradeResponse> mNewGradeSubscriber;

    @Override
    public void onBackPressed() {
        if (mTabView.isPopupWindowShowing()) {
            mTabView.closePopWindow();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posm_home);
        initView();
    }

    @Override
    protected void onStart () {
        super.onStart();
        mListView.onRefresh();
    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mTabView = (POSMTabView) findViewById(R.id.v_posm_tab);
        initTabView();

        mListView = (PullToRefreshListView) findViewById(R.id.lv_posm_content);
        initListView();

        mLlFillBlank = (LinearLayout) findViewById(R.id.ll_fill_blank);
        mLlFillBlank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTabView.isPopupWindowShowing()) {
                    mTabView.closePopWindow();
                }
            }
        });

        getLastQuarter(); // 这里做了第一次的列表网络请求
    }

    private void initTabView() {
        SQLiteHelper helper = new SQLiteHelper();
        Authorization authorization = helper.getAuth();
        int type = authorization.type;

        switch (type) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                break;
            default:
                mTabView.setRegionEnabled(false);
        }

        mTabView.setDealerGradeRequestListener(new POSMTabView.DealerGradeRequestListener() {
            @Override
            public void requestDealerGrade(Map<String, Object> args) {
                mYear = (int) args.get("year");
                mQuarter = (int) args.get("quarter");
                mArgs = args;
                mIsLoadMore = false;
                requestDealerGradeList();
            }
        });

        mTabView.setPopWindowActionListener(new POSMTabView.PopupWindowActionListener() {
            @Override
            public void afterPopupWindowShow() {
                mListView.setAlpha(0.5f);
            }

            @Override
            public void afterPopupWindowDismiss() {
                mListView.setAlpha(1);
            }
        });

    }

    private void initListView() {
        mListView.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mArgs.put("page", 1);
                mIsLoadMore = false;
                requestDealerGradeList();
            }

            @Override
            public void onLoadMore() {
                if (!mArgs.containsKey("page")) {
                    mArgs.put("page", 2);
                }
                mIsLoadMore = true;
                requestDealerGradeList();
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                if (mTabView.isPopupWindowShowing()) {
                    mTabView.closePopWindow();
                    return;
                }

                DealerGrade dealerGrade = (DealerGrade) mListView.getAdapter().getItem(position);
                if (dealerGrade.getGradeDto() != null) {
                    Intent intent = new Intent(POSMHomeActivity.this, GradeDetailActivity.class);
                    intent.putExtra("dealerName", dealerGrade.getName());
                    intent.putExtra("dealerId", dealerGrade.getId());
                    intent.putExtra("year", (int) mArgs.get("year"));
                    intent.putExtra("quarter", (int) mArgs.get("quarter"));
                    intent.putExtra("gradeId", dealerGrade.getGradeDto().id);
                    intent.putExtra("score", dealerGrade.getGradeDto().score);

                    PosmHomeAdapter adapter;
                    if (mListView.getAdapter() instanceof HeaderViewListAdapter) {
                        HeaderViewListAdapter headerViewListAdapter = (HeaderViewListAdapter) mListView.getAdapter();
                        adapter = (PosmHomeAdapter) headerViewListAdapter.getWrappedAdapter();
                    } else {
                        adapter = (PosmHomeAdapter) mListView.getAdapter();
                    }
                    intent.putExtra("totalScore", adapter.getTotalScore());
                    intent.putExtra("commitTime", dealerGrade.getGradeDto().commitTime);
                    startActivity(intent);
                } else {
                    SQLiteHelper helper = new SQLiteHelper();
                    Authorization authorization = helper.getAuth();
                    int type = authorization.type;
                    if (type != 4 && type != 5) {
                        Toast.makeText(POSMHomeActivity.this, "您无法评分", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    new AlertDialog.Builder(POSMHomeActivity.this)
                            .setTitle("提示")
                            .setMessage("是否开始评分？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                    DealerGrade dealerGrade = (DealerGrade) mListView.getAdapter().getItem(position);
                                    postNewGrade(dealerGrade.getId());
                                    mLoadingDialog = new LoadingDialog(POSMHomeActivity.this);
                                    mLoadingDialog.setCanceledOnTouchOutside(false);
                                    mLoadingDialog.show();
                                }
                            })
                            .setNegativeButton("取消", null)
                            .show();
                }
            }
        });
    }

    private void requestDealerGradeList() {
        mSubscriber = new Subscriber<DealerGradeResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                mListView.setAdapter(null);
                mIsLoadMore = false;
                mListView.onRefreshComplete();
                mListView.onLoadMoreComplete(false);
            }

            @Override
            public void onNext(DealerGradeResponse response) {
                if (!mIsLoadMore) {
                    Paper paper = response.paper;
                    List<DealerGrade> dealerGrades = response.dealerGrades;
                    PosmHomeAdapter adapter = new PosmHomeAdapter(POSMHomeActivity.this, paper.getTotalScore(), dealerGrades);
                    mListView.setAdapter(adapter);
                    String time = TimeHelper.getCurrentTime();
                    mListView.onRefreshComplete(time);

                    mArgs.put("page", 2);

                } else {
                    if (response.dealerGrades.size() == 0) {
                        mListView.onLoadMoreComplete(false);
                        return;
                    }
                    PosmHomeAdapter adapter;
                    if (mListView.getAdapter() instanceof HeaderViewListAdapter) {
                        HeaderViewListAdapter headerViewListAdapter = (HeaderViewListAdapter) mListView.getAdapter();
                        adapter = (PosmHomeAdapter) headerViewListAdapter.getWrappedAdapter();
                    } else {
                        adapter = (PosmHomeAdapter) mListView.getAdapter();
                    }
                    if (adapter == null) {
                        adapter = new PosmHomeAdapter(POSMHomeActivity.this, response.paper.getTotalScore(), new ArrayList<DealerGrade>());
                    }

                    adapter.addDealerGrades(response.dealerGrades);
                    mListView.onLoadMoreComplete(true);
                    mArgs.put("page", 1 + (int) mArgs.get("page"));
                }

            }
        };
        PosmApi.getInstance().getDealerGradeList(mSubscriber, mYear, mQuarter, mArgs);
    }

    /**
     * 由于水平有限，不懂如何等待PosmTabView中的最近季度数据返回之后再做requestDealerGradeList()
     * 只好在这里再请求了一次
     */
    private void getLastQuarter() {
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
                mYear = response.quarters.get(0).getYear();
                mQuarter = response.quarters.get(0).getQuarter();
                mArgs.put("year", mYear);
                mArgs.put("quarter", mQuarter);
                mListView.onRefresh();
                mListView.onLoadMoreComplete(true);
            }
        };
        PosmApi.getInstance().getQuarterList(mQuarterSubscriber);
    }

    private void postNewGrade(long dealerId) {
        mNewGradeSubscriber = new Subscriber<GradeResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    int code =((HttpException)e).code();
                    switch (code) {
                        case 403:
                            if (mLoadingDialog.isShowing()) {
                                mLoadingDialog.dismiss();
                            }
                            Toast.makeText(POSMHomeActivity.this, "您无法评分", Toast.LENGTH_SHORT).show();
                            return;
                    }

                }

                e.printStackTrace();
                if (mLoadingDialog.isShowing()) {
                    mLoadingDialog.dismiss();
                }
                Toast.makeText(POSMHomeActivity.this, "生成问题失败，请稍后重试", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNext(GradeResponse gradeResponse) {
                if (mLoadingDialog.isShowing()) {
                    mLoadingDialog.dismiss();
                    final GradeResponse gradeResponseCopy = gradeResponse;
                    new AlertDialog.Builder(POSMHomeActivity.this).setTitle("POSM平台使用须知")
                            .setMessage("1、应事业部领导要求，经销商展厅POSM需要符合凯迪拉克POSM标准，现要求MAC、RPC对经销商展厅POSM进行监督和管理，为便于管理，开发了该平台。\n" +
                                    "2、每家经销商每季度仅需打分一次，MAC、RPC均可对所属区域经销商进行打分。\n" +
                                    "3、POSM题目依据品牌提供的经销商展厅POSM标准进行设置，每季度首月5日前会对题目进行更新。\n" +
                                    "4、题目分值设定分三档，分为满分、半分、零分，分值的评判可参考题目下方标准进行；每道题目会有1-3张示例图片供打分参考，MAC、RPC可在打分时上传相应图片作为扣分依据，上传图片数量不多于3张。")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(POSMHomeActivity.this, MarkPageActivity.class);
                                    intent.putExtra("gradeId", gradeResponseCopy.grade.getId());
                                    startActivity(intent);
                                }
                            })
                    .show();

                }
            }
        };
        PosmApi.getInstance().postGrade(mNewGradeSubscriber, dealerId);
    }
}
