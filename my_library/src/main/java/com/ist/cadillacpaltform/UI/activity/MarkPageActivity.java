package com.ist.cadillacpaltform.UI.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ist.cadillacpaltform.R;
import com.ist.cadillacpaltform.SDK.bean.Posm.AbstractGradeDetail;
import com.ist.cadillacpaltform.SDK.network.PosmApi;
import com.ist.cadillacpaltform.SDK.response.Posm.AbstractGradeDetailResponse;
import com.ist.cadillacpaltform.SDK.response.Posm.GradeDetailResponse;
import com.ist.cadillacpaltform.SDK.response.Posm.RectificationResponse;
import com.ist.cadillacpaltform.SDK.util.FilePathHelper;
import com.ist.cadillacpaltform.SDK.util.OnLoadViewOkListener;
import com.ist.cadillacpaltform.SDK.util.TimeHelper;
import com.ist.cadillacpaltform.UI.fragment.MarkFragment;
import com.ist.cadillacpaltform.UI.fragment.MarkResultFragment;
import com.ist.cadillacpaltform.UI.fragment.PrevAndNextFragment;
import com.ist.cadillacpaltform.UI.fragment.QuestionFragment;
import com.ist.cadillacpaltform.UI.fragment.RectifyResultFragment;

import java.util.List;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Created by czh on 2017/3/1.
 */

public class MarkPageActivity extends Activity {
    private Context mContext;
    private ImageView mIvBack;
    QuestionFragment questionFragment;
    PrevAndNextFragment prevAndNextFragment;
    RectifyResultFragment rectifyResultFragment;//整改


    MarkFragment markFragment;
    MarkResultFragment markResultFragment;

    private final int fragmentNum = 3;//有几个fragment需要等待更新
    private int fragmentOk = 0;//有几个fragment已经更新好了
    private boolean canModify = false;//是否已经打分
    private long gradeId;//打分ID，paperId和dealerId唯一确定
    private long questionId;//进入页面应该显示的question
    private int questionIndex = 0;//当前题目在abstractGradeDetailList的下标
    private List<AbstractGradeDetail> abstractGradeDetailList;//通过gradeId获得的题目及打分信息

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = MarkPageActivity.this;
        setContentView(R.layout.activity_mark_page);
        initView();
    }

    private void initView() {
        Intent it = getIntent();
        gradeId = it.getLongExtra("gradeId", 1);
        questionId = it.getLongExtra("questionId", -1);
        canModify = it.getBooleanExtra("canModify", true);
        mIvBack = (ImageView) findViewById(R.id.iv_back);


        questionFragment = (QuestionFragment) getFragmentManager().findFragmentById(R.id.fragment_question);
        prevAndNextFragment = (PrevAndNextFragment) getFragmentManager().findFragmentById(R.id.fragment_prevAndNext);
        rectifyResultFragment = (RectifyResultFragment) getFragmentManager().findFragmentById(R.id.fragment_rectify_result);
        initAbstractGradeDetailList();

        prevAndNextFragment.setOnNextClickListener(new PrevAndNextFragment.OnNextClickListener() {
            @Override
            public boolean onNextClick() {
                if (questionIndex < abstractGradeDetailList.size() - 1) {//不是最后一题
                    fragmentOk = 0;
                    if (!canModify) {
                        markResultFragment.setAdvice(questionIndex);
                    } else {
                        if (!markFragment.next()) {//因为没有上传扣分理由没有打分成功
                            fragmentOk = fragmentNum;
                            return false;
                        }
                    }
                    questionIndex++;
                    prevAndNextFragment.setClickable(false);
                    questionFragment.setQuestion(questionIndex);
                    rectifyResultFragment.setRectifyResult(questionIndex);
                    return true;
                } else {//最后一题
                    quitDialog();
                    return false;
                }
            }
        });
        prevAndNextFragment.setOnPrevClickListener(new PrevAndNextFragment.OnPrevClickListener() {
            @Override
            public boolean onPrevClick() {
                fragmentOk = 0;
                if (questionIndex > 0) {
                    if (!canModify) {
                        markResultFragment.setAdvice(questionIndex);
                    } else {
                        if (!markFragment.prev()) {
                            fragmentOk = fragmentNum;
                            return false;
                        }
                    }
                    questionIndex--;
                    prevAndNextFragment.setClickable(false);
                    questionFragment.setQuestion(questionIndex);
                    rectifyResultFragment.setRectifyResult(questionIndex);
                    return true;
                }
                return false;
            }
        });

        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void fragmentViewOkCount() {
        fragmentOk++;
        if (fragmentOk == fragmentNum) {//fragment加载完毕后才可以点击上下题
            prevAndNextFragment.setClickable(true);
        }
    }

    @Override
    public void onBackPressed() {
        //quitDialog();
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        final LayoutInflater inflater = MarkPageActivity.this.getLayoutInflater();
        View view = inflater.inflate(R.layout.view_quit_dialog, null, false);
        builder.setCancelable(true);
        final AlertDialog alert = builder.create();
        alert.setView(view, 0, 0, 0, 0);
        TextView textView = (TextView) view.findViewById(R.id.tv_content);
        textView.setText("是否返回到上一页面？");
        TextView textView1 = (TextView) view.findViewById(R.id.tv_quit);
        textView1.setText("返回");
        TextView textView2 = (TextView) view.findViewById(R.id.tv_moveOn);
        textView2.setText("取消");
        view.findViewById(R.id.tv_moveOn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.dismiss();
            }
        });
        view.findViewById(R.id.tv_quit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.dismiss();
                finish();
            }
        });
        alert.show();
    }

    private void quitDialog() {
        if (!canModify) {
            finish();
        } else {
            markFragment.syncSummit(new MarkFragment.AfterSummitListener() {
                @Override
                public void afterSummit() {
                    int questionFinished = markFragment.getTotalCount();
                    if (questionFinished == abstractGradeDetailList.size()) {//最后一题
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        final LayoutInflater inflater = MarkPageActivity.this.getLayoutInflater();
                        View view = inflater.inflate(R.layout.view_quit_dialog, null, false);
                        builder.setCancelable(true);
                        final AlertDialog alert = builder.create();
                        alert.setView(view, 0, 0, 0, 0);
                        TextView textView = (TextView) view.findViewById(R.id.tv_content);
                        textView.setText("你已经打分" + questionFinished + "/" + abstractGradeDetailList.size() + "道题目，\n" +
                                "现在的分数为" + markFragment.getNowScore() + "/" + countTotalScore() + "分。\n" +
                                "您可以返回页面继续打分或者提交已打分内容");
                        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(textView.getText().toString());
                        int temp = 5 + (questionFinished + "").length() + 1 + (abstractGradeDetailList.size() + "").length();//“道”的下标
                        spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.RED), 5, temp, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        int temp1 = temp + 11 + (markFragment.getNowScore() + "").length() + 1 + (countTotalScore() + "").length();//“分”的下标
                        spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.RED), temp + 11, temp1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        textView.setText(spannableStringBuilder);
                        TextView textView1 = (TextView) view.findViewById(R.id.tv_quit);
                        textView1.setText("提交");
                        view.findViewById(R.id.tv_moveOn).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alert.dismiss();
                            }
                        });
                        view.findViewById(R.id.tv_quit).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alert.dismiss();
                                finish();
                            }
                        });
                        alert.show();
                    } else {//不是最后一题
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        final LayoutInflater inflater = MarkPageActivity.this.getLayoutInflater();
                        View view = inflater.inflate(R.layout.view_quit_dialog, null, false);
                        builder.setCancelable(true);
                        final AlertDialog alert = builder.create();
                        alert.setView(view, 0, 0, 0, 0);
                        TextView textView = (TextView) view.findViewById(R.id.tv_content);
                        textView.setText("你已经打分" + questionFinished + "/" + abstractGradeDetailList.size() + "道题目，\n" +
                                "现在的分数为" + markFragment.getNowScore() + "/" + countTotalScore() + "分。\n" +
                                "您可以返回页面继续打分或者保存已打分内容");
                        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(textView.getText().toString());
                        int temp = 5 + (questionFinished + "").length() + 1 + (abstractGradeDetailList.size() + "").length();//“道”的下标
                        spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.RED), 5, temp, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        int temp1 = temp + 11 + (markFragment.getNowScore() + "").length() + 1 + (countTotalScore() + "").length();//“分”的下标
                        spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.RED), temp + 11, temp1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        textView.setText(spannableStringBuilder);
                        view.findViewById(R.id.tv_moveOn).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alert.dismiss();
                            }
                        });
                        view.findViewById(R.id.tv_quit).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alert.dismiss();
                                finish();
                            }
                        });
                        alert.show();
                    }
                }
            });
        }
    }

    private void initAbstractGradeDetailList() {
        Subscriber<AbstractGradeDetailResponse> mSubscriber = new Subscriber<AbstractGradeDetailResponse>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    HttpException httpException = (HttpException) e;
                    switch (httpException.code()) {
                        case 404:
                            Toast.makeText(mContext, "未找到题目列表", Toast.LENGTH_SHORT).show();
                            break;
                        case 400:
                            Toast.makeText(mContext, "选定时间错误，无法找到题目列表", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(mContext, "网络错误，无法找到题目列表", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mContext, "其他错误，无法找到题目列表", Toast.LENGTH_SHORT).show();
                }
                e.printStackTrace();
                finish();//由于错误没有题目直接退出Actitvity
            }

            @Override
            public void onNext(AbstractGradeDetailResponse abstractGradeDetail) {
                abstractGradeDetailList = abstractGradeDetail.abstractGradeDetails;
                questionFragment.setAbstractGradeDetailList(abstractGradeDetailList);
                rectifyResultFragment.setAbstractGradeDetailList(abstractGradeDetailList);
                if (questionId != -1) {//进入指定questionId的题目
                    for (int i = 0; i < abstractGradeDetailList.size(); i++) {
                        if (questionId == abstractGradeDetailList.get(i).getQuestionId()) {
                            questionIndex = i;
                            break;
                        }
                    }
                    initFragment();
                } else {
                    enterFirstNotMark(0);
                }
            }
        };
        PosmApi.getInstance().getAbstractGradeDetails(mSubscriber, gradeId, 0);
    }

    private void enterFirstNotMark(final int index) {//递归
        long gradeDetailId = abstractGradeDetailList.get(index).getId();
        Subscriber<GradeDetailResponse> subscriber = new Subscriber<GradeDetailResponse>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    HttpException httpException = (HttpException) e;
                    switch (httpException.code()) {
                        case 404:
                            Toast.makeText(mContext, "网络错误，无法获取已打分信息", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(mContext, "网络错误，无法获取已打分信息", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mContext, "其他错误，无法获取已打分信息", Toast.LENGTH_SHORT).show();
                }
                e.printStackTrace();
            }

            @Override
            public void onNext(GradeDetailResponse gradeDetailResponse) {
                if (!FilePathHelper.isStringEmptyOrNull(gradeDetailResponse.gradeDetail.getGrade().getCommitTime())) {//提交过的肯定已经打分了
                    questionIndex = 0;
                    initFragment();
                    return;
                }
                if (gradeDetailResponse.gradeDetail.getScore() == -1) {//进入未打分的第一题
                    questionIndex = index;
                    initFragment();
                    return;
                }
                if (index == abstractGradeDetailList.size() - 1) {//最后一题而且分数不是-1，说明已经打分完了
                    questionIndex = 0;
                    initFragment();
                    return;
                }
                enterFirstNotMark(index + 1);
            }
        };
        PosmApi.getInstance().getGradeDetails(subscriber, gradeDetailId);
    }

    private void initFragment() {
        questionFragment.setOnLoadViewOkListener(new OnLoadViewOkListener() {
            @Override
            public void onLoadViewOk() {
                fragmentViewOkCount();
            }
        });
        rectifyResultFragment.setOnLoadViewOkListener(new OnLoadViewOkListener() {
            @Override
            public void onLoadViewOk() {
                fragmentViewOkCount();
            }
        });
        questionFragment.setQuestion(questionIndex);
        rectifyResultFragment.setRectifyResult(questionIndex);
        prevAndNextFragment.setQuestionIndex(questionIndex);
        prevAndNextFragment.setClickable(false);
        initIsMarkFragment();
    }

    private void initIsMarkFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (canModify) {
            markFragment = new MarkFragment();
            markFragment.setOnLoadViewOkListener(new OnLoadViewOkListener() {
                @Override
                public void onLoadViewOk() {
                    fragmentViewOkCount();
                }
            });
            fragmentTransaction.add(R.id.ll_mark, markFragment);
            fragmentTransaction.commit();
            markFragment.setAbstractGradeDetailList(abstractGradeDetailList);
            markFragment.setMark(questionIndex);
        } else {
            markResultFragment = new MarkResultFragment();
            markResultFragment.setOnLoadViewOkListener(new OnLoadViewOkListener() {
                @Override
                public void onLoadViewOk() {
                    fragmentViewOkCount();
                }
            });
            fragmentTransaction.add(R.id.ll_mark, markResultFragment);
            fragmentTransaction.commit();
            markResultFragment.setAbstractGradeDetailList(abstractGradeDetailList);
            markResultFragment.setAdvice(questionIndex);
        }
    }

    private String countTotalScore() {//整份试卷的总分
        int score = 0;
        for (int i = 0; i < abstractGradeDetailList.size(); i++) {
            score += abstractGradeDetailList.get(i).getTotalScore();
        }
        return score + "";
    }
}
