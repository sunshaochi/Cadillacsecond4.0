package com.ist.cadillacpaltform.UI.activity;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
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
import com.ist.cadillacpaltform.SDK.util.OnLoadViewOkListener;
import com.ist.cadillacpaltform.UI.fragment.MarkResultFragment;
import com.ist.cadillacpaltform.UI.fragment.PrevAndNextFragment;
import com.ist.cadillacpaltform.UI.fragment.QuestionFragment;
import com.ist.cadillacpaltform.UI.fragment.RectifyFragment;
import com.ist.cadillacpaltform.UI.fragment.RectifyResultFragment;

import java.util.List;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

public class RectificationActivity extends FragmentActivity {
    private Context mContext;
    private ImageView mIvBack;
    private QuestionFragment questionFragment;
    private MarkResultFragment markResultFragment;
    private PrevAndNextFragment prevAndNextFragment;
    private RectifyFragment rectifyFragment;
    private RectifyResultFragment rectifyResultFragment;

    private final int fragmentNum = 3;//有几个fragment需要等待更新
    private int fragmentOk = 0;//有几个fragment已经更新好了
    private long gradeId;
    private long questionId;
    private boolean canRectify = false;//是否能整改

    private int questionIndex;
    List<AbstractGradeDetail> abstractGradeDetailList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rectification);
        mContext = RectificationActivity.this;
        initView();
    }

    private void initView() {
        Intent it = getIntent();
        canRectify = it.getBooleanExtra("canRectify", false);
        if(canRectify) {
            gradeId = 0;//整改用0
        }
        else {
            gradeId = it.getLongExtra("gradeId", 1);
        }
        questionId = it.getLongExtra("questionId", -1);
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        questionFragment = (QuestionFragment) getFragmentManager().findFragmentById(R.id.fragment_question);
        markResultFragment = (MarkResultFragment) getFragmentManager().findFragmentById(R.id.fragment_mark_result);
        prevAndNextFragment = (PrevAndNextFragment) getFragmentManager().findFragmentById(R.id.fragment_prevAndNext);
        initAbstractGradeDetailList();
        prevAndNextFragment.setOnNextClickListener(new PrevAndNextFragment.OnNextClickListener() {
            @Override
            public boolean onNextClick() {
                if (questionIndex < abstractGradeDetailList.size() - 1) {//不是最后一题
                    fragmentOk = 0;
                    questionIndex++;
                    questionFragment.setQuestion(questionIndex);
                    markResultFragment.setAdvice(questionIndex);
                    if (canRectify) {
                        rectifyFragment.next();
                    } else {
                        rectifyResultFragment.setRectifyResult(questionIndex);
                    }
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
                if (questionIndex > 0) {
                    fragmentOk = 0;
                    questionIndex--;
                    questionFragment.setQuestion(questionIndex);
                    markResultFragment.setAdvice(questionIndex);
                    if (canRectify) {
                        rectifyFragment.prev();
                    } else {
                        rectifyResultFragment.setRectifyResult(questionIndex);
                    }
                    return true;
                }
                return false;
            }
        });
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quitDialog();
            }
        });
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
                markResultFragment.setAbstractGradeDetailList(abstractGradeDetailList);
                if (questionId != -1) {//进入指定questionId的题目
                    for (int i = 0; i < abstractGradeDetailList.size(); i++) {
                        if (questionId == abstractGradeDetailList.get(i).getQuestionId()) {
                            questionIndex = i;
                            break;
                        }
                    }
                }
                questionFragment.setQuestion(questionIndex);
                markResultFragment.setAdvice(questionIndex);
                initIsRectifyFragment();
            }
        };
        PosmApi.getInstance().getAbstractGradeDetails(mSubscriber, gradeId, 1);
    }

    private void initIsRectifyFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (canRectify) {
            rectifyFragment = new RectifyFragment();
            rectifyFragment.setOnLoadViewOkListener(new OnLoadViewOkListener() {
                @Override
                public void onLoadViewOk() {
                    fragmentViewOkCount();
                }
            });
            fragmentTransaction.add(R.id.fl_rectify, rectifyFragment);
            fragmentTransaction.commit();
            rectifyFragment.setAbstractGradeDetailList(abstractGradeDetailList);
            rectifyFragment.setRectify(questionIndex);
        } else {
            rectifyResultFragment = new RectifyResultFragment();
            rectifyResultFragment.setOnLoadViewOkListener(new OnLoadViewOkListener() {
                @Override
                public void onLoadViewOk() {
                    fragmentViewOkCount();
                }
            });
            fragmentTransaction.add(R.id.fl_rectify, rectifyResultFragment);
            fragmentTransaction.commit();
            rectifyResultFragment.setAbstractGradeDetailList(abstractGradeDetailList);
            rectifyResultFragment.setRectifyResult(questionIndex);
        }
    }

    private void fragmentViewOkCount() {
        fragmentOk++;
        if (fragmentOk == fragmentNum) {//fragment加载完毕后才可以点击上下题
            prevAndNextFragment.setClickable(true);
        }
    }

    @Override
    public void onBackPressed() {
        quitDialog();
    }

    private void quitDialog() {
        if (!canRectify) {
            finish();
            return;
        }
        rectifyFragment.summit();
//        if (questionIndex == abstractGradeDetailList.size() - 1) {//最后一题
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        final LayoutInflater inflater = RectificationActivity.this.getLayoutInflater();
        View view = inflater.inflate(R.layout.view_quit_dialog, null, false);
        builder.setCancelable(true);
        final AlertDialog alert = builder.create();
        alert.setView(view, 0, 0, 0, 0);
        TextView textView = (TextView) view.findViewById(R.id.tv_content);
//        int questionFinished = questionIndex + 1;
        textView.setText("您可以返回页面继续整改或者提交已整改内容,共有" + abstractGradeDetailList.size() + "道题。");
//        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(textView.getText().toString());
//        int temp = 5 + (questionFinished + "").length() + 1 + (abstractGradeDetailList.size() + "").length();//“道”的下标
//        spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.RED), 5, temp, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        textView.setText(spannableStringBuilder);
        TextView textView1 = (TextView) view.findViewById(R.id.tv_quit);
        textView1.setText("提交");
        TextView tvMoveOn = (TextView) view.findViewById(R.id.tv_moveOn);
        tvMoveOn.setText("继续整改");
        tvMoveOn.setOnClickListener(new View.OnClickListener() {
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
//        } else {//不是最后一题
//            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//            final LayoutInflater inflater = RectificationActivity.this.getLayoutInflater();
//            View view = inflater.inflate(R.layout.view_quit_dialog, null, false);
//            builder.setCancelable(true);
//            final AlertDialog alert = builder.create();
//            alert.setView(view, 0, 0, 0, 0);
//            TextView textView = (TextView) view.findViewById(R.id.tv_content);
//            int questionFinished = questionIndex + 1;
//            textView.setText("你已经整改" + questionFinished + "/" + abstractGradeDetailList.size() + "道题目，\n" +
//                    "您可以返回页面继续整改或者保存已整改内容");
//            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(textView.getText().toString());
//            int temp = 5 + (questionFinished + "").length() + 1 + (abstractGradeDetailList.size() + "").length();//“道”的下标
//            spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.RED), 5, temp, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            textView.setText(spannableStringBuilder);
//            TextView tvMoveOn = (TextView) view.findViewById(R.id.tv_moveOn);
//            tvMoveOn.setText("继续整改");
//            tvMoveOn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    alert.dismiss();
//                }
//            });
//            view.findViewById(R.id.tv_quit).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    alert.dismiss();
//                    finish();
//                }
//            });
//            alert.show();
//        }
    }
}
