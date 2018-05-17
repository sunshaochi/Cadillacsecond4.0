package com.ist.cadillacpaltform.UI.fragment;


import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.ist.cadillacpaltform.R;
import com.ist.cadillacpaltform.SDK.bean.Posm.AbstractGradeDetail;
import com.ist.cadillacpaltform.SDK.bean.Posm.GradeDetail;
import com.ist.cadillacpaltform.SDK.network.PosmApi;
import com.ist.cadillacpaltform.SDK.response.Posm.GradeDetailResponse;
import com.ist.cadillacpaltform.SDK.util.FilePathHelper;
import com.ist.cadillacpaltform.SDK.util.OnLoadViewOkListener;
import com.ist.cadillacpaltform.SDK.util.PicLoadingStyle;
import com.ist.cadillacpaltform.SDK.util.oss.ImageHelper;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

import static com.ist.cadillacpaltform.R.drawable.icon_placeholder;


public class MarkResultFragment extends Fragment {
    protected View mRoot;
    private RadioGroup mRgMark;
    private RadioButton mRbFullScore;
    private RadioButton mRbHalfScore;
    private RadioButton mRbZeroScore;
    private LinearLayout mLlEvidencePic;
    private SimpleDraweeView mSdvEvidence1;
    private SimpleDraweeView mSdvEvidence2;
    private SimpleDraweeView mSdvEvidence3;
    private TextView mTvAdvice;

    private int evidencePicWidth = 330;
    private int evidencePicHeight = 300;
    private int questionIndex = 0;
    private List<AbstractGradeDetail> abstractGradeDetailList;//通过gradeId获得的题目及打分信息
    private List<GradeDetail> gradeDetailList;//用于存储打分信息
    private OnLoadViewOkListener onLoadViewOkListener = null;

    public void setOnLoadViewOkListener(OnLoadViewOkListener onLoadViewOkListener) {
        this.onLoadViewOkListener = onLoadViewOkListener;
    }

    public MarkResultFragment() {
    }

    public void setAbstractGradeDetailList(List<AbstractGradeDetail> argument) {
        abstractGradeDetailList = argument;
        gradeDetailList = new ArrayList<>();
        for (int i = 0; i < abstractGradeDetailList.size(); i++) {
            gradeDetailList.add(null);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.fragment_mark_result, container, false);
        mRgMark = (RadioGroup) mRoot.findViewById(R.id.rg_mark);
        mRbFullScore = (RadioButton) mRoot.findViewById(R.id.rb_full_score);
        mRbHalfScore = (RadioButton) mRoot.findViewById(R.id.rb_half_score);
        mRbZeroScore = (RadioButton) mRoot.findViewById(R.id.rb_zero_score);
        mRbFullScore.setEnabled(false);
        mRbHalfScore.setEnabled(false);
        mRbZeroScore.setEnabled(false);
        mLlEvidencePic = (LinearLayout) mRoot.findViewById(R.id.ll_evidencePic);
        mSdvEvidence1 = (SimpleDraweeView) mRoot.findViewById(R.id.sdv_evidence1);
        mSdvEvidence2 = (SimpleDraweeView) mRoot.findViewById(R.id.sdv_evidence2);
        mSdvEvidence3 = (SimpleDraweeView) mRoot.findViewById(R.id.sdv_evidence3);
        GenericDraweeHierarchyBuilder hierarchyBuilder = new GenericDraweeHierarchyBuilder(getResources());
        hierarchyBuilder.setProgressBarImage(new PicLoadingStyle(getActivity()).getQuestionPicLoad2());
        hierarchyBuilder.setActualImageScaleType(ScalingUtils.ScaleType.FIT_XY);
        hierarchyBuilder.setPlaceholderImage(icon_placeholder);
        mSdvEvidence1.setHierarchy(hierarchyBuilder.build());
        mSdvEvidence2.setHierarchy(hierarchyBuilder.build());
        mSdvEvidence3.setHierarchy(hierarchyBuilder.build());
        mTvAdvice = (TextView) mRoot.findViewById(R.id.tv_suggestion);
        //获取缩略图长宽
        mSdvEvidence1.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        evidencePicWidth = mSdvEvidence1.getWidth();
                        evidencePicHeight = mSdvEvidence1.getHeight();
                        mSdvEvidence1.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                });
        return mRoot;
    }

    public void setAdvice(int index) {
        questionIndex = index;
        if (gradeDetailList.get(questionIndex) == null) {
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
                                Toast.makeText(getActivity(), "网络错误，无法获取已打分信息", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Toast.makeText(getActivity(), "网络错误，无法获取已打分信息", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "其他错误，无法获取已打分信息", Toast.LENGTH_SHORT).show();
                    }
                    e.printStackTrace();
                }

                @Override
                public void onNext(GradeDetailResponse gradeDetailResponse) {
                    gradeDetailList.set(questionIndex, gradeDetailResponse.gradeDetail);
                    setAdviceOnScreen(gradeDetailResponse.gradeDetail);
                }
            };
            AbstractGradeDetail abstractGradeDetail = abstractGradeDetailList.get(questionIndex);
            PosmApi.getInstance().getGradeDetails(subscriber, abstractGradeDetail.getId());
        } else {
            setAdviceOnScreen(gradeDetailList.get(questionIndex));
        }
    }

    private void setAdviceOnScreen(GradeDetail gradeDetail) {
        setRgMark();
        mLlEvidencePic.setVisibility(View.VISIBLE);
        if (gradeDetail == null) {
            mRbFullScore.setChecked(true);
            mLlEvidencePic.setVisibility(View.GONE);
            mTvAdvice.setText("");
            mSdvEvidence1.setImageURI("");
            mSdvEvidence2.setImageURI("");
            mSdvEvidence3.setImageURI("");
        } else {
            mTvAdvice.setText(gradeDetail.getSuggestion());
            float score = gradeDetail.getScore();
            if (score == 0) {
                mRbZeroScore.setChecked(true);
            } else {
                if (score == abstractGradeDetailList.get(questionIndex).getTotalScore()) {
                    mRbFullScore.setChecked(true);
                    //mLlEvidencePic.setVisibility(View.GONE);
//                    if (onLoadViewOkListener != null) {
//                        onLoadViewOkListener.onLoadViewOk();
//                    }
//                    return;
                } else {
                    mRbHalfScore.setChecked(true);
                }
            }
            if (!FilePathHelper.isStringEmptyOrNull(gradeDetail.getPicurl1())) {
//                Uri picUri = Uri.parse(gradeDetail.getPicurl1());
//                ImageRequestBuilder imageRequestBuilder = ImageRequestBuilder.newBuilderWithSource(picUri)
//                        .setAutoRotateEnabled(true)
//                        .setLocalThumbnailPreviewsEnabled(true)
//                        .setResizeOptions(new ResizeOptions(mSdvEvidence1.getWidth(), mSdvEvidence1.getHeight()));
//                DraweeController controller = Fresco.newDraweeControllerBuilder()
//                        .setImageRequest(imageRequestBuilder.build())
//                        .build();
//                mSdvEvidence1.setController(controller);
                mSdvEvidence1.setImageURI(ImageHelper.resize(gradeDetail.getPicurl1(), evidencePicWidth, evidencePicHeight));
            } else {
                mSdvEvidence1.setImageURI("");
            }
            if (!FilePathHelper.isStringEmptyOrNull(gradeDetail.getPicurl2())) {
//                Uri picUri = Uri.parse(gradeDetail.getPicurl2());
//                ImageRequestBuilder imageRequestBuilder = ImageRequestBuilder.newBuilderWithSource(picUri)
//                        .setAutoRotateEnabled(true)
//                        .setLocalThumbnailPreviewsEnabled(true)
//                        .setResizeOptions(new ResizeOptions(mSdvEvidence1.getWidth(), mSdvEvidence1.getHeight()));
//                DraweeController controller = Fresco.newDraweeControllerBuilder()
//                        .setImageRequest(imageRequestBuilder.build())
//                        .build();
//                mSdvEvidence2.setController(controller);
                mSdvEvidence2.setImageURI(ImageHelper.resize(gradeDetail.getPicurl2(), evidencePicWidth, evidencePicHeight));
            } else {
                mSdvEvidence2.setImageURI("");
            }
            if (!FilePathHelper.isStringEmptyOrNull(gradeDetail.getPicurl3())) {
//                Uri picUri = Uri.parse(gradeDetail.getPicurl3());
//                ImageRequestBuilder imageRequestBuilder = ImageRequestBuilder.newBuilderWithSource(picUri)
//                        .setAutoRotateEnabled(true)
//                        .setLocalThumbnailPreviewsEnabled(true)
//                        .setResizeOptions(new ResizeOptions(mSdvEvidence1.getWidth(), mSdvEvidence1.getHeight()));
//                DraweeController controller = Fresco.newDraweeControllerBuilder()
//                        .setImageRequest(imageRequestBuilder.build())
//                        .build();
//                mSdvEvidence3.setController(controller);
                mSdvEvidence3.setImageURI(ImageHelper.resize(gradeDetail.getPicurl3(), evidencePicWidth, evidencePicHeight));
            } else {
                mSdvEvidence3.setImageURI("");
            }
        }
        if (onLoadViewOkListener != null) {
            onLoadViewOkListener.onLoadViewOk();
        }
    }

    private void setRgMark() {
        AbstractGradeDetail simpleQuestion = abstractGradeDetailList.get(questionIndex);
        float fullScore = simpleQuestion.getTotalScore();
        mRbZeroScore.setText("0分");
        mRbFullScore.setText(String.valueOf((int) fullScore) + "分");
        double halfScore = (double) fullScore / 2;
        DecimalFormat df = new DecimalFormat("#0.0");
        if (Math.ceil(halfScore) == (int) halfScore) {
            mRbHalfScore.setText((int) halfScore + "分");
        } else {
            mRbHalfScore.setText(df.format(halfScore) + "分");
        }
    }
}
