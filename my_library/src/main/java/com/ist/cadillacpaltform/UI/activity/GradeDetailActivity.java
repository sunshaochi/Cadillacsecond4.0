package com.ist.cadillacpaltform.UI.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.HeaderViewListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ist.cadillacpaltform.R;
import com.ist.cadillacpaltform.SDK.bean.Posm.AbstractGradeDetail;
import com.ist.cadillacpaltform.SDK.bean.Posm.AbstractMark;
import com.ist.cadillacpaltform.SDK.bean.Posm.ModuleGrade;
import com.ist.cadillacpaltform.SDK.network.PosmApi;
import com.ist.cadillacpaltform.SDK.response.Posm.AbstractGradeDetailResponse;
import com.ist.cadillacpaltform.SDK.response.Posm.AbstractMarkResponse;
import com.ist.cadillacpaltform.SDK.response.Posm.ModuleGradeResponse;
import com.ist.cadillacpaltform.SDK.util.GradeTreeUtil.AbstractModule;
import com.ist.cadillacpaltform.SDK.util.GradeTreeUtil.GradeTreeParser;
import com.ist.cadillacpaltform.SDK.util.TimeHelper;
import com.ist.cadillacpaltform.UI.adapter.CarInfoAdapter;
import com.ist.cadillacpaltform.UI.adapter.GradeDetailAdapter;
import com.ist.cadillacpaltform.UI.adapter.PosmRectifyDetailAdapter;
import com.ist.cadillacpaltform.UI.view.CustomListView;
import com.ist.cadillacpaltform.UI.view.PieChartView;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Created by dearlhd on 2017/3/6.
 */
public class GradeDetailActivity extends FragmentActivity {

    private int mYear;
    private int mQuarter;
    private long mDealerId;
    private String mDealerName;


    private boolean mIsRectify = false;   // 是否是经销商整改

    private boolean mCanModify = true;   // 是否可以修改评分内容

    // 用mMarks记录上方的三个季度评分信息，mCurrentMark记录当前显示的评分信息，点击切换的时候，将mCurrentMark和mMarks中对应的对象互换
    List<AbstractMark> mMarks;
    AbstractMark mCurrentMark;

    /* ---------- title ------------ */
    private ImageView mIvBack;
    private TextView mTvDealerName;

    /* ---------- 上部最近三个季度的得分情况 ------------- */
    private RelativeLayout mRlQuarterTag1;
    private RelativeLayout mRlQuarterTag2;
    private RelativeLayout mRlQuarterTag3;

    private TextView mTvQuarter1;
    private TextView mTvQ1Score;
    private TextView mTvQ1TotalScore;

    private TextView mTvQuarter2;
    private TextView mTvQ2Score;
    private TextView mTvQ2TotalScore;

    private TextView mTvQuarter3;
    private TextView mTvQ3Score;
    private TextView mTvQ3TotalScore;

    /* ------------ 中部的选中季度答题概况 ------------- */
    private TextView mTvCurrentQuarter;

    private PieChartView mPieChartView;
    private TextView mTvCurrentScore;
    private TextView mTvTotalScore;

    private LinearLayout mLlModules;

    private TextView mTvGradeBtn;

    private CustomListView mListView;
    private CustomListView mRectifyListView;

    private LinearLayout mLlRectify;

    private TextView mTvRectifyTip;

    private Subscriber<AbstractMarkResponse> mAbstractMarkSubscriber;
    private Subscriber<ModuleGradeResponse> mModuleGradeSubscriber;
    private Subscriber<AbstractGradeDetailResponse> mDetailSubscriber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade_detail);
        getIntentData();
        initView();
    }

    @Override
    public void onStart() {
        super.onStart();
        onRefreshed();
    }

    private void getIntentData() {
        try {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();

            mDealerName = bundle.getString("dealerName", "POSM平台");
            mDealerId = bundle.getLong("dealerId", 0);
            mYear = bundle.getInt("year");
            mQuarter = bundle.getInt("quarter");

            mIsRectify = bundle.getBoolean("isRectify", false);

            mCurrentMark = new AbstractMark();
            mCurrentMark.setGradeId(bundle.getLong("gradeId", 0));
            mCurrentMark.setQuarter(mQuarter);
            mCurrentMark.setYear(mYear);
            mCurrentMark.setScore(String.valueOf(bundle.getFloat("score")));
            mCurrentMark.setTotalScore(String.valueOf(bundle.getFloat("totalScore")));
            mCurrentMark.setCommitTime(bundle.getString("commitTime"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mTvDealerName = (TextView) findViewById(R.id.tv_dealer_name);

        mRlQuarterTag1 = (RelativeLayout) findViewById(R.id.rl_quarter_tag1);
        mRlQuarterTag2 = (RelativeLayout) findViewById(R.id.rl_quarter_tag2);
        mRlQuarterTag3 = (RelativeLayout) findViewById(R.id.rl_quarter_tag3);

        mTvQuarter1 = (TextView) findViewById(R.id.tv_quarter_tag1);
        mTvQuarter2 = (TextView) findViewById(R.id.tv_quarter_tag2);
        mTvQuarter3 = (TextView) findViewById(R.id.tv_quarter_tag3);

        mTvQ1Score = (TextView) findViewById(R.id.tv_q1_score);
        mTvQ2Score = (TextView) findViewById(R.id.tv_q2_score);
        mTvQ3Score = (TextView) findViewById(R.id.tv_q3_score);

        mTvQ1TotalScore = (TextView) findViewById(R.id.tv_q1_total_score);
        mTvQ2TotalScore = (TextView) findViewById(R.id.tv_q2_total_score);
        mTvQ3TotalScore = (TextView) findViewById(R.id.tv_q3_total_score);

        mPieChartView = (PieChartView) findViewById(R.id.v_pie_chart);

        mTvCurrentQuarter = (TextView) findViewById(R.id.tv_current_quarter);

        mTvCurrentScore = (TextView) findViewById(R.id.tv_current_score);
        mTvTotalScore = (TextView) findViewById(R.id.tv_total_score);

        mLlModules = (LinearLayout) findViewById(R.id.ll_abstract_module);

        mTvGradeBtn = (TextView) findViewById(R.id.tv_grade_button);

        mListView = (CustomListView) findViewById(R.id.lv_grade_details);
        mRectifyListView = (CustomListView) findViewById(R.id.lv_rectify_details);
        mLlRectify = (LinearLayout) findViewById(R.id.ll_rectify_details);
        mTvRectifyTip = (TextView) findViewById(R.id.tv_no_rectify_tip);

        initTitle();
        initAbstractInfo();

        requestQuarterData();

//        onRefreshed();              // 在onStart()中做刷新行为
    }

    private void onRefreshed() {
        requestModule();
        requestGradeDetail();
    }

    private void initTitle() {
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mTvDealerName.setText(mDealerName);
    }

    private void initAbstractInfo() {
        mRlQuarterTag1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onQuarterTagClick(0);
            }
        });
        mRlQuarterTag2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onQuarterTagClick(1);
            }
        });
        mRlQuarterTag3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onQuarterTagClick(2);
            }
        });
    }

    private void setPieChart() {
        try {
            float angle = 360.0f * Float.parseFloat(mCurrentMark.getScore()) / Float.parseFloat(mCurrentMark.getTotalScore());
            mPieChartView.setSweepAngle(angle);
            mPieChartView.invalidate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 中间的简要信息部分
     */
    private void setAbstractInfo() {
        mTvCurrentQuarter.setText(mCurrentMark.getYear() + ".Q" + mCurrentMark.getQuarter());

        mTvCurrentScore.setText(mCurrentMark.getScore());
        mTvTotalScore.setText("/" + mCurrentMark.getTotalScore());

        mTvGradeBtn.setBackgroundResource(R.drawable.bg_green_button);
        if (mIsRectify) {
            mTvGradeBtn.setText("整改");
            mPieChartView.setPieChartColor(PieChartView.PieChartColor.GREEN);
            mPieChartView.invalidate();
        } else {
            if (!mCanModify) {
                mTvGradeBtn.setText("查看");
                mPieChartView.setPieChartColor(PieChartView.PieChartColor.GREEN);
                mPieChartView.invalidate();
            } else if (mCurrentMark.getCommitTime() != null) {
                mTvGradeBtn.setText("修改");
                mPieChartView.setPieChartColor(PieChartView.PieChartColor.GREEN);
                mPieChartView.invalidate();
            } else {
                mPieChartView.setPieChartColor(PieChartView.PieChartColor.RED);
                mPieChartView.invalidate();
                mTvGradeBtn.setText("打分");
            }
        }

        mTvGradeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsRectify) {
                    Intent intent = new Intent(GradeDetailActivity.this, RectificationActivity.class);
                    intent.putExtra("gradeId", mCurrentMark.getGradeId());
                    intent.putExtra("canRectify", TimeHelper.isCurrentQuarter(mCurrentMark.getYear(), mCurrentMark.getQuarter()));
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(GradeDetailActivity.this, MarkPageActivity.class);
                    intent.putExtra("gradeId", mCurrentMark.getGradeId());
                    intent.putExtra("canModify", mCanModify);
                    startActivity(intent);
                }
            }
        });
    }

    private void onQuarterTagClick(int i) {
        AbstractMark mark = mMarks.get(i);

        if (mMarks.get(i).getGradeId() == -1) {
            Toast.makeText(GradeDetailActivity.this, "该季度未打分", Toast.LENGTH_SHORT).show();
            return;
        }

        mMarks.set(i, mCurrentMark);
        mCurrentMark = mark;
        sortMarks();

        setQuarterTab();
        setAbstractInfo();
        requestModule();
        requestGradeDetail();
    }

    /**
     * 发送获取前三个季度总分情况的请求
     */
    private void requestQuarterData() {
        mAbstractMarkSubscriber = new Subscriber<AbstractMarkResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(AbstractMarkResponse response) {
                mMarks = response.marks;
                for (int i = 0; i < mMarks.size(); i++) {
                    AbstractMark temp = mMarks.get(i);
                    if (temp.getYear() == mYear && temp.getQuarter() == mQuarter) {
                        mCurrentMark = temp;
                        mMarks.remove(temp);
                        break;
                    }
                }

                sortMarks();
                setQuarterTab();
            }
        };
        PosmApi.getInstance().getRecentScoreByDealer(mAbstractMarkSubscriber, mDealerId);
    }

    private void setQuarterTab() {
        if (mMarks != null && mMarks.size() > 0) {
            AbstractMark mark;
            String quarter;
            switch (mMarks.size()) {
                // 这里每个case 都不break，顺序往下执行
                case 3:
                    mark = mMarks.get(2);
                    quarter = mark.getYear() + ".Q" + mark.getQuarter();
                    mTvQuarter3.setText(quarter);

                    try {
                        float score = Float.parseFloat(mark.getScore());
                        mTvQ3Score.setText(mark.getScore());
                        if (score < 60.0f) {
                            mTvQ3Score.setTextColor(getResources().getColor(R.color.colorRedText));
                        }
                        mTvQ3TotalScore.setText("/" + mark.getTotalScore());
                        mTvQ3TotalScore.setVisibility(View.VISIBLE);
                    } catch (NumberFormatException e) {
                        mTvQ3Score.setText(mark.getScore());
                        mTvQ3TotalScore.setVisibility(View.GONE);
                    }


                case 2:
                    mark = mMarks.get(1);
                    quarter = mark.getYear() + ".Q" + mark.getQuarter();
                    mTvQuarter2.setText(quarter);

                    try {
                        float score = Float.parseFloat(mark.getScore());
                        mTvQ2Score.setText(mark.getScore());
                        if (score < 60.0f) {
                            mTvQ2Score.setTextColor(getResources().getColor(R.color.colorRedText));
                        }
                        mTvQ2TotalScore.setText("/" + mark.getTotalScore());
                        mTvQ2TotalScore.setVisibility(View.VISIBLE);
                    } catch (NumberFormatException e) {
                        mTvQ2Score.setText(mark.getScore());
                        mTvQ2TotalScore.setVisibility(View.GONE);
                    }

                case 1:
                    mark = mMarks.get(0);
                    quarter = mark.getYear() + ".Q" + mark.getQuarter();
                    mTvQuarter1.setText(quarter);

                    try {
                        float score = Float.parseFloat(mark.getScore());
                        mTvQ1Score.setText(mark.getScore());
                        if (score < 60.0f) {
                            mTvQ1Score.setTextColor(getResources().getColor(R.color.colorRedText));
                        }
                        mTvQ1TotalScore.setText("/" + mark.getTotalScore());
                        mTvQ1TotalScore.setVisibility(View.VISIBLE);
                    } catch (NumberFormatException e) {
                        mTvQ1Score.setText(mark.getScore());
                        mTvQ1TotalScore.setVisibility(View.GONE);
                    }

                    break;
            }
        }
    }

    private void requestModule() {
        mModuleGradeSubscriber = new Subscriber<ModuleGradeResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    HttpException httpException = (HttpException) e;
                    switch (httpException.code()) {
                        case 404:
                            Calendar c = Calendar.getInstance();
                            mYear = c.get(Calendar.YEAR);
                            if(c.get(Calendar.MONTH)%3==0){
                                mQuarter = c.get(Calendar.MONTH)/3;
                            }else {
                                mQuarter = (c.get(Calendar.MONTH)) / 3 + 1;
                            }
//                            mQuarter = (1 + c.get(Calendar.MONTH)) / 3 + 1;
                            mTvCurrentQuarter.setText(mYear+ ".Q" + mQuarter);
                            mTvRectifyTip.setVisibility(View.VISIBLE);
                            mLlRectify.setVisibility(View.GONE);
                            mTvGradeBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_grey_button));
                            mLlModules.removeAllViews();
                            break;
                        default:
                            e.printStackTrace();
                    }
                } else {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNext(ModuleGradeResponse moduleGradeResponse) {
                if (moduleGradeResponse.gradeInModule == null) {
                    Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    if(c.get(Calendar.MONTH)%3==0){
                        mQuarter = c.get(Calendar.MONTH)/3;
                    }else {
                        mQuarter = (c.get(Calendar.MONTH)) / 3 + 1;
                    }
                    mTvCurrentQuarter.setText(mYear+ ".Q" + mQuarter);

                    mTvRectifyTip.setVisibility(View.VISIBLE);
                    mLlRectify.setVisibility(View.GONE);
                    mTvGradeBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_grey_button));
                    mLlModules.removeAllViews();
                    return;
                } else {
                    mTvRectifyTip.setVisibility(View.GONE);
                }

                if (!moduleGradeResponse.gradeInModule.owned || !TimeHelper.isLessThan48Hours(mCurrentMark.getCommitTime())) {
                    mCanModify = false;
                } else {
                    mCanModify = true;
                }

                mCurrentMark.setScore(String.valueOf(moduleGradeResponse.gradeInModule.grade));
                List<ModuleGrade> grades = moduleGradeResponse.gradeInModule.grades;
                mLlModules.removeAllViews();

                float totalScore = 0;
                for (int i = 0; i < grades.size(); i++) {
                    totalScore += grades.get(i).getModuleScore();
                    ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    TextView tv = new TextView(GradeDetailActivity.this);
                    tv.setLayoutParams(params);
                    tv.setTextColor(getResources().getColor(R.color.colorGreyText));
                    tv.setTextSize(11);
                    String txt = grades.get(i).getName() + "  " + grades.get(i).getScore() + "/" + grades.get(i).getModuleScore();
                    tv.setText(txt);
                    mLlModules.addView(tv);
                }

                mCurrentMark.setTotalScore(String.valueOf(totalScore));

                setPieChart();
                setAbstractInfo();
            }
        };

        PosmApi.getInstance().getModuleGrade(mModuleGradeSubscriber, mCurrentMark.getGradeId());
    }

    private void requestGradeDetail() {
        mDetailSubscriber = new Subscriber<AbstractGradeDetailResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    HttpException httpException = (HttpException) e;
                    switch (httpException.code()) {
                        case 404:
                            mLlRectify.setVisibility(View.GONE);
                            break;
                        default:
                            e.printStackTrace();
                    }
                } else {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNext(AbstractGradeDetailResponse response) {
//                mListView.setAdapter(null);
//                mRectifyListView.setAdapter(null);
                if (mIsRectify) {
                    mListView.setVisibility(View.GONE);
                    mLlRectify.setVisibility(View.VISIBLE);
                    PosmRectifyDetailAdapter adapter = new PosmRectifyDetailAdapter(GradeDetailActivity.this, response.abstractGradeDetails);
                    mRectifyListView.setAdapter(adapter);
                    mRectifyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(GradeDetailActivity.this, RectificationActivity.class);
                            intent.putExtra("gradeId", mCurrentMark.getGradeId());
                            intent.putExtra("canRectify", TimeHelper.isCurrentQuarter(mCurrentMark.getYear(), mCurrentMark.getQuarter()));
                            PosmRectifyDetailAdapter adapter = (PosmRectifyDetailAdapter) mRectifyListView.getAdapter();
                            long questionId = ((AbstractGradeDetail) adapter.getItem(position)).getQuestionId();
                            intent.putExtra("questionId", questionId);
                            startActivity(intent);
                        }
                    });

                } else {
                    mListView.setVisibility(View.VISIBLE);
                    mLlRectify.setVisibility(View.GONE);

                    List<AbstractGradeDetail> details = response.abstractGradeDetails;
                    List<AbstractModule> modules = GradeTreeParser.parseGradeTree(details);
                    GradeDetailAdapter adapter = new GradeDetailAdapter(GradeDetailActivity.this, mListView, modules);
                    adapter.setOnItemClickListener(new GradeDetailAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClicked(long questionId) {
                            if (mIsRectify) {
                                Intent intent = new Intent(GradeDetailActivity.this, RectificationActivity.class);
                                intent.putExtra("gradeId", mCurrentMark.getGradeId());
                                intent.putExtra("questionId", questionId);
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(GradeDetailActivity.this, MarkPageActivity.class);
                                intent.putExtra("gradeId", mCurrentMark.getGradeId());
                                intent.putExtra("questionId", questionId);
                                intent.putExtra("canModify", mCanModify);

                                startActivity(intent);
                            }
                        }
                    });

                    mListView.setAdapter(adapter);
                }
            }
        };
        if (mIsRectify) {
            PosmApi.getInstance().getAbstractGradeDetails(mDetailSubscriber, mCurrentMark.getGradeId(), 1); // 整改界面仅显示未满分项目
        } else {
            PosmApi.getInstance().getAbstractGradeDetails(mDetailSubscriber, mCurrentMark.getGradeId(), 0);
        }
    }

    private void sortMarks() {
        Collections.sort(mMarks, new Comparator<AbstractMark>() {
            @Override
            public int compare(AbstractMark mark1, AbstractMark mark2) {
                if (mark1.getYear() < mark2.getYear()
                        || (mark1.getYear() == mark2.getYear() && mark1.getQuarter() < mark2.getQuarter())) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
    }

}
