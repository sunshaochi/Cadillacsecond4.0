package com.ist.cadillacpaltform.UI.fragment;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
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
import com.google.gson.JsonObject;
import com.ist.cadillacpaltform.R;
import com.ist.cadillacpaltform.SDK.bean.Posm.AbstractGradeDetail;
import com.ist.cadillacpaltform.SDK.bean.Posm.FinalVal;
import com.ist.cadillacpaltform.SDK.bean.Posm.GradeDetail;
import com.ist.cadillacpaltform.SDK.network.PosmApi;
import com.ist.cadillacpaltform.SDK.response.Posm.GradeDetailResponse;
import com.ist.cadillacpaltform.SDK.util.FilePathHelper;
import com.ist.cadillacpaltform.SDK.util.OnLoadViewOkListener;
import com.ist.cadillacpaltform.SDK.util.PicLoadingStyle;
import com.ist.cadillacpaltform.SDK.util.oss.ImageHelper;
import com.ist.cadillacpaltform.SDK.util.oss.OssService;

import org.w3c.dom.Text;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

import static com.ist.cadillacpaltform.R.drawable.bg_grey_frame;
import static com.ist.cadillacpaltform.R.drawable.icon_camera;
import static com.ist.cadillacpaltform.SDK.bean.Posm.FinalVal.CAMERA_PERMISSION_REQUEST;
import static com.ist.cadillacpaltform.SDK.bean.Posm.FinalVal.CAMERA_REQUEST_CODE;
import static com.ist.cadillacpaltform.SDK.bean.Posm.FinalVal.PIC_SUVER_URL;


public class MarkFragment extends Fragment {
    private View mRoot;
    private RadioGroup mRgMark;
    private RadioButton mRbFullScore;
    private RadioButton mRbHalfScore;
    private RadioButton mRbZeroScore;
    private LinearLayout mLlAdvice;
    private SimpleDraweeView mSdvEvidence1;
    private SimpleDraweeView mSdvEvidence2;
    private SimpleDraweeView mSdvEvidence3;
    private EditText mEtAdvice;

    private int evidenceId = 0;//判断哪个evidence调用了摄像头;
    private int questionIndex = 0;
    private float score = 0;//存储当前得分
    private int totalCount = 0;//存储当前已打分题目
    private int evidencePicWidth = 330;
    private int evidencePicHeight = 300;
    private List<AbstractGradeDetail> abstractGradeDetailList;//通过gradeId获得的题目及打分信息
    private List<GradeDetail> gradeDetailList;//存储已打分情况
    private List<String> picUrlList;//图片本地路径URl
    private OnLoadViewOkListener onLoadViewOkListener = null;

    public void setOnLoadViewOkListener(OnLoadViewOkListener onLoadViewOkListener) {
        this.onLoadViewOkListener = onLoadViewOkListener;
    }


    public MarkFragment() {
    }

    public void setAbstractGradeDetailList(List<AbstractGradeDetail> argument) {
        abstractGradeDetailList = argument;
        gradeDetailList = new ArrayList<>();
        for (int i = 0; i < abstractGradeDetailList.size(); i++) {
            gradeDetailList.add(null);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        picUrlList = new ArrayList<>();
        picUrlList.add(null);
        picUrlList.add(null);
        picUrlList.add(null);
        mRoot = inflater.inflate(R.layout.fragment_mark, container, false);
        mRgMark = (RadioGroup) mRoot.findViewById(R.id.rg_mark);
        mRbFullScore = (RadioButton) mRoot.findViewById(R.id.rb_full_score);
        mRbHalfScore = (RadioButton) mRoot.findViewById(R.id.rb_half_score);
        mRbZeroScore = (RadioButton) mRoot.findViewById(R.id.rb_zero_score);
        mLlAdvice = (LinearLayout) mRoot.findViewById(R.id.ll_advice);
        mSdvEvidence1 = (SimpleDraweeView) mRoot.findViewById(R.id.iv_evidence1);
        mSdvEvidence2 = (SimpleDraweeView) mRoot.findViewById(R.id.iv_evidence2);
        mSdvEvidence3 = (SimpleDraweeView) mRoot.findViewById(R.id.iv_evidence3);
        GenericDraweeHierarchyBuilder hierarchyBuilder = new GenericDraweeHierarchyBuilder(getResources());
        hierarchyBuilder.setProgressBarImage(new PicLoadingStyle(getActivity()).getQuestionPicLoad2());
        hierarchyBuilder.setActualImageScaleType(ScalingUtils.ScaleType.FIT_XY);
        hierarchyBuilder.setPlaceholderImage(icon_camera);
        mSdvEvidence1.setHierarchy(hierarchyBuilder.build());
        mSdvEvidence2.setHierarchy(hierarchyBuilder.build());
        mSdvEvidence3.setHierarchy(hierarchyBuilder.build());
        mEtAdvice = (EditText) mRoot.findViewById(R.id.et_advice);
        mSdvEvidence1.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        evidencePicWidth = mSdvEvidence1.getWidth();
                        evidencePicHeight = mSdvEvidence1.getHeight();
                        mSdvEvidence1.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                });

        mSdvEvidence1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evidenceId = 0;
                cameraOrAlbum();
            }
        });
        mSdvEvidence2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evidenceId = 1;
                cameraOrAlbum();
            }
        });
        mSdvEvidence3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evidenceId = 2;
                cameraOrAlbum();
            }
        });
//        mRgMark.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
//                RadioButton checkedBtn = (RadioButton) mRoot.findViewById(checkedId);
//                if (checkedBtn.getTag().toString().equals("fullScore")) {
//                    mLlAdvice.setVisibility(View.GONE);
//                } else {
//                    mLlAdvice.setVisibility(View.VISIBLE);
//                }
//            }
//        });

        return mRoot;
    }


    public float getNowScore() {
//        float nowScore = 0;
//        for (int i = 0; i < mRgMark.getChildCount(); i++) {
//            RadioButton rb = (RadioButton) mRgMark.getChildAt(i);
//            if (rb.isChecked()) {
//                nowScore = Float.parseFloat(rb.getText().toString().substring(0, rb.getText().toString().length() - 1));
//            }
//        }
//        float result = score + nowScore;
        return score;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public boolean prev() {
        if (questionIndex > 0) {
            if (checkMarkResult()) {
                if (gradeDetailList.get(questionIndex) == null || isGradeDetailModified()) {//没有打过分或者修改过
                    updateGradeDetail();
                    uploadGradeDetail();
                }
                questionIndex--;
                setMark(questionIndex);
                return true;
            } else {
                reMarkDialog();
                return false;
            }
        }
        return false;
    }

    public boolean next() {
        if (checkMarkResult()) {
            if (questionIndex < abstractGradeDetailList.size() - 1) {//不是最后一题
                if (gradeDetailList.get(questionIndex) == null || isGradeDetailModified()) {//判断有没有打过分或者修改过
                    updateGradeDetail();
                    uploadGradeDetail();
                }
                questionIndex++;
                setMark(questionIndex);
                return true;
            }
        } else {
            reMarkDialog();
            return false;
        }
        return false;
    }

    private boolean checkMarkResult() {
        for (int i = 0; i < picUrlList.size(); i++) {
            if (FilePathHelper.isStringEmptyOrNull(picUrlList.get(i))) {
                return false;
            }
        }
        return true;
    }

    private void reMarkDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final AlertDialog alert = builder
                .setTitle("提示")
                .setMessage("请上传三张图片表明扣分情况")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create();
        alert.show();
    }

    public interface AfterSummitListener {
        public void afterSummit();
    }

    public void syncSummit(final AfterSummitListener afterSummitListener) {
        if (!checkMarkResult()) {
            reMarkDialog();
            return;
        }
        if (gradeDetailList.get(questionIndex) == null || isGradeDetailModified()) {//判断有没有打过分或者修改过
            updateGradeDetail();
            syncUploadGradeDetail(new AfterUploadGradeDetailListener() {
                @Override
                public void afterUploadGradeDetail() {
                    afterSummitListener.afterSummit();
                }
            });
        }
    }

    public void setMark(int index) {
        questionIndex = index;
        GradeDetail gradeDetail = gradeDetailList.get(questionIndex);
        if (gradeDetail == null) {
            long gradeDetailId = abstractGradeDetailList.get(questionIndex).getId();
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
                                Toast.makeText(getActivity(), "网络错误,无法获取已打分信息", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Toast.makeText(getActivity(), "网络错误,无法获取已打分信息", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "其他错误,无法获取已打分信息", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onNext(GradeDetailResponse gradeDetailResponse) {
                    GradeDetail gradeDetail = gradeDetailResponse.gradeDetail;
                    gradeDetailList.set(questionIndex, gradeDetail);
                    if (gradeDetail != null) {
                        score = gradeDetail.getGrade().getTotalscore();
                    }
                    setMarkOnScreen(gradeDetail);
                }
            };
            PosmApi.getInstance().getGradeDetails(subscriber, gradeDetailId);
        } else {
            setMarkOnScreen(gradeDetail);
        }
    }

    private void setMarkOnScreen(GradeDetail gradeDetail) {
        setRgMark();//设置分值选项
        if (gradeDetail == null) {//将分数，建议，图片设为默认
            mEtAdvice.setText("");
            picUrlList.set(0, null);
            picUrlList.set(1, null);
            picUrlList.set(2, null);
            mRbFullScore.setChecked(true);
            mSdvEvidence1.setImageURI("");
            mSdvEvidence2.setImageURI("");
            mSdvEvidence3.setImageURI("");
        } else {//将分数，建议，图片设为gradeDetail的值
            float score = gradeDetail.getScore();
            //gradedetail不会为空，数据库已经全部创建了，默认分数为-1
            if (score == -1) {
                mRbFullScore.setChecked(true);
            } else {
                if (score == 0) {
                    mRbZeroScore.setChecked(true);
                } else {
                    if (score == abstractGradeDetailList.get(questionIndex).getTotalScore()) {
                        mRbFullScore.setChecked(true);
                    } else {
                        mRbHalfScore.setChecked(true);
                    }
                }
            }
            mEtAdvice.setText(gradeDetail.getSuggestion());
            picUrlList.set(0, serverPathToLocalPath(gradeDetail.getPicurl1()));
            picUrlList.set(1, serverPathToLocalPath(gradeDetail.getPicurl2()));
            picUrlList.set(2, serverPathToLocalPath(gradeDetail.getPicurl3()));
            if (!FilePathHelper.isStringEmptyOrNull(picUrlList.get(0))) {
                File file = new File(picUrlList.get(0));
                if (file.exists()) {
                    Uri picUri = Uri.fromFile(file);
                    ImageRequestBuilder imageRequestBuilder = ImageRequestBuilder.newBuilderWithSource(picUri)
                            .setAutoRotateEnabled(true)
                            .setLocalThumbnailPreviewsEnabled(true)
                            .setResizeOptions(new ResizeOptions(mSdvEvidence1.getWidth(), mSdvEvidence1.getHeight()));
                    DraweeController controller = Fresco.newDraweeControllerBuilder()
                            .setImageRequest(imageRequestBuilder.build())
                            .build();
                    mSdvEvidence1.setController(controller);
                } else {
                    mSdvEvidence1.setImageURI(ImageHelper.resize(gradeDetail.getPicurl1(), evidencePicWidth, evidencePicHeight));
                }
                mSdvEvidence1.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        deletePic(1);
                        mSdvEvidence1.setOnLongClickListener(null);
                        return true;
                    }
                });
            } else {
                mSdvEvidence1.setImageURI("");
            }
            if (!FilePathHelper.isStringEmptyOrNull(picUrlList.get(1))) {
                File file = new File(picUrlList.get(1));
                if (file.exists()) {
                    Uri picUri = Uri.fromFile(file);
                    ImageRequestBuilder imageRequestBuilder = ImageRequestBuilder.newBuilderWithSource(picUri)
                            .setAutoRotateEnabled(true)
                            .setLocalThumbnailPreviewsEnabled(true)
                            .setResizeOptions(new ResizeOptions(mSdvEvidence1.getWidth(), mSdvEvidence1.getHeight()));
                    DraweeController controller = Fresco.newDraweeControllerBuilder()
                            .setImageRequest(imageRequestBuilder.build())
                            .build();
                    mSdvEvidence2.setController(controller);
                } else {
                    mSdvEvidence2.setImageURI(ImageHelper.resize(gradeDetail.getPicurl2(), evidencePicWidth, evidencePicHeight));
                }
                mSdvEvidence2.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        deletePic(2);
                        mSdvEvidence2.setOnLongClickListener(null);
                        return true;
                    }
                });
            } else {
                mSdvEvidence2.setImageURI("");
            }
            if (!FilePathHelper.isStringEmptyOrNull(picUrlList.get(2))) {
                File file = new File(picUrlList.get(2));
                if (file.exists()) {
                    Uri picUri = Uri.fromFile(file);
                    ImageRequestBuilder imageRequestBuilder = ImageRequestBuilder.newBuilderWithSource(picUri)
                            .setAutoRotateEnabled(true)
                            .setLocalThumbnailPreviewsEnabled(true)
                            .setResizeOptions(new ResizeOptions(mSdvEvidence1.getWidth(), mSdvEvidence1.getHeight()));
                    DraweeController controller = Fresco.newDraweeControllerBuilder()
                            .setImageRequest(imageRequestBuilder.build())
                            .build();
                    mSdvEvidence3.setController(controller);
                } else {
                    mSdvEvidence3.setImageURI(ImageHelper.resize(gradeDetail.getPicurl3(), evidencePicWidth, evidencePicHeight));
                }
                mSdvEvidence3.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        deletePic(3);
                        mSdvEvidence3.setOnLongClickListener(null);
                        return true;
                    }
                });
            } else {
                mSdvEvidence3.setImageURI("");
            }
        }
        //setAdviceView();
        if (onLoadViewOkListener != null) {
            onLoadViewOkListener.onLoadViewOk();
        }
    }

//    private void setAdviceView() {
//        for (int i = 0; i < mRgMark.getChildCount(); i++) {
//            RadioButton rb = (RadioButton) mRgMark.getChildAt(i);
//            if (rb.isChecked()) {
//                if (rb.getTag().toString().equals("fullScore")) {
//                    mLlAdvice.setVisibility(View.GONE);
//                } else {
//                    mLlAdvice.setVisibility(View.VISIBLE);
//                }
//                break;
//            }
//        }
//    }

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

    private void uploadGradeDetail() {
        AbstractGradeDetail abstractGradeDetail = abstractGradeDetailList.get(questionIndex);
        long abstractGradeDetailId = abstractGradeDetail.getId();
        JsonObject GradeDetailData = new JsonObject();
        final float score;
        for (int i = 0; i < mRgMark.getChildCount(); i++) {
            RadioButton rb = (RadioButton) mRgMark.getChildAt(i);
            if (rb.isChecked()) {
                score = Float.parseFloat(rb.getText().toString().substring(0, rb.getText().toString().length() - 1));
                GradeDetailData.addProperty("score", score);
//                if (score == abstractGradeDetailList.get(questionIndex).getTotalScore()) {//满分
//                    GradeDetailData.addProperty("picurl1", "");
//                    GradeDetailData.addProperty("picurl2", "");
//                    GradeDetailData.addProperty("picurl3", "");
//                } else {
                uploadPic();
                GradeDetailData.addProperty("suggestion", mEtAdvice.getText().toString());
                GradeDetailData.addProperty("picurl1", localPathToServerPath(picUrlList.get(0)));
                GradeDetailData.addProperty("picurl2", localPathToServerPath(picUrlList.get(1)));
                GradeDetailData.addProperty("picurl3", localPathToServerPath(picUrlList.get(2)));
                break;
            }
        }
        Subscriber<GradeDetailResponse> mSubscriberUploadGradeDetail = new Subscriber<GradeDetailResponse>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    HttpException httpException = (HttpException) e;
                    switch (httpException.code()) {
                        case 403:
                            Toast.makeText(getActivity(), "权限错误，无法打分", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(getActivity(), "网络错误，无法打分", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "其他错误，无法打分", Toast.LENGTH_SHORT).show();
                }
                e.printStackTrace();
            }

            @Override
            public void onNext(GradeDetailResponse gradeDetailResponse) {
                totalCount = gradeDetailResponse.totalCount;
                MarkFragment.this.score = gradeDetailResponse.gradeDetail.getGrade().getTotalscore();
            }
        };
        JsonObject uploadData = new JsonObject();
        uploadData.add("data", GradeDetailData);
        PosmApi.getInstance().modifyGradeDetails(mSubscriberUploadGradeDetail, abstractGradeDetailId, uploadData);
    }

    public interface AfterUploadGradeDetailListener {
        public void afterUploadGradeDetail();
    }

    private void syncUploadGradeDetail(final AfterUploadGradeDetailListener afterUploadGradeDetailListener) {
        AbstractGradeDetail abstractGradeDetail = abstractGradeDetailList.get(questionIndex);
        long abstractGradeDetailId = abstractGradeDetail.getId();
        JsonObject GradeDetailData = new JsonObject();
        final float score;
        for (int i = 0; i < mRgMark.getChildCount(); i++) {
            RadioButton rb = (RadioButton) mRgMark.getChildAt(i);
            if (rb.isChecked()) {
                score = Float.parseFloat(rb.getText().toString().substring(0, rb.getText().toString().length() - 1));
                GradeDetailData.addProperty("score", score);
//                if (score == abstractGradeDetailList.get(questionIndex).getTotalScore()) {//满分
//                    GradeDetailData.addProperty("picurl1", "");
//                    GradeDetailData.addProperty("picurl2", "");
//                    GradeDetailData.addProperty("picurl3", "");
//                } else {
                uploadPic();
                GradeDetailData.addProperty("suggestion", mEtAdvice.getText().toString());
                GradeDetailData.addProperty("picurl1", localPathToServerPath(picUrlList.get(0)));
                GradeDetailData.addProperty("picurl2", localPathToServerPath(picUrlList.get(1)));
                GradeDetailData.addProperty("picurl3", localPathToServerPath(picUrlList.get(2)));
                break;
            }
        }
        Subscriber<GradeDetailResponse> mSubscriberUploadGradeDetail = new Subscriber<GradeDetailResponse>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    HttpException httpException = (HttpException) e;
                    switch (httpException.code()) {
                        case 403:
                            Toast.makeText(getActivity(), "权限错误，无法打分", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(getActivity(), "网络错误，无法打分", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "其他错误，无法打分", Toast.LENGTH_SHORT).show();
                }
                e.printStackTrace();
            }

            @Override
            public void onNext(GradeDetailResponse gradeDetailResponse) {
                totalCount = gradeDetailResponse.totalCount;
                MarkFragment.this.score = gradeDetailResponse.gradeDetail.getGrade().getTotalscore();
                afterUploadGradeDetailListener.afterUploadGradeDetail();
            }
        };
        JsonObject uploadData = new JsonObject();
        uploadData.add("data", GradeDetailData);
        PosmApi.getInstance().modifyGradeDetails(mSubscriberUploadGradeDetail, abstractGradeDetailId, uploadData);
    }

    private void updateGradeDetail() {
        GradeDetail gradeDetail = new GradeDetail();
        float score;
        for (int i = 0; i < mRgMark.getChildCount(); i++) {
            RadioButton rb = (RadioButton) mRgMark.getChildAt(i);
            if (rb.isChecked()) {
                score = Float.parseFloat(rb.getText().toString().substring(0, rb.getText().toString().length() - 1));
                gradeDetail.setScore(score);
//                if (score == abstractGradeDetailList.get(questionIndex).getTotalScore()) {//满分
//                } else {
                gradeDetail.setSuggestion(mEtAdvice.getText().toString());
                gradeDetail.setPicurl1(localPathToServerPath(picUrlList.get(0)));
                gradeDetail.setPicurl2(localPathToServerPath(picUrlList.get(1)));
                gradeDetail.setPicurl3(localPathToServerPath(picUrlList.get(2)));
                break;
            }
        }
        gradeDetailList.set(questionIndex, gradeDetail);
    }

    private boolean isGradeDetailModified() {
        if (gradeDetailList.get(questionIndex) == null) {
            return true;
        } else {
            GradeDetail gradeDetail = gradeDetailList.get(questionIndex);
            for (int i = 0; i < mRgMark.getChildCount(); i++) {
                RadioButton rb = (RadioButton) mRgMark.getChildAt(i);
                if (rb.isChecked()) {
                    float score = Float.parseFloat(rb.getText().toString().substring(0, rb.getText().toString().length() - 1));
                    if (gradeDetail.getScore() != score)
                        return true;
                    break;
                }
            }
            if (gradeDetail.getSuggestion() != mEtAdvice.getText().toString())
                return true;
            if (gradeDetail.getPicurl1() != localPathToServerPath(picUrlList.get(0))) {
                return true;
            }
            if (gradeDetail.getPicurl2() != localPathToServerPath(picUrlList.get(1))) {
                return true;
            }
            if (gradeDetail.getPicurl3() != localPathToServerPath(picUrlList.get(2))) {
                return true;
            }
        }
        return false;
    }

    private void uploadPic() {
        OssService ossService = new OssService(getActivity());
        if (!FilePathHelper.isStringEmptyOrNull(picUrlList.get(0))) {
            String url = picUrlList.get(0);
            ossService.asyncPutImage(url.substring(url.lastIndexOf('/') + 1, url.length()), picUrlList.get(0));
        }
        if (!FilePathHelper.isStringEmptyOrNull(picUrlList.get(1))) {
            String url = picUrlList.get(1);
            ossService.asyncPutImage(url.substring(url.lastIndexOf('/') + 1, url.length()), picUrlList.get(1));
        }
        if (!FilePathHelper.isStringEmptyOrNull(picUrlList.get(2))) {
            String url = picUrlList.get(2);
            ossService.asyncPutImage(url.substring(url.lastIndexOf('/') + 1, url.length()), picUrlList.get(2));
        }
    }

    private void cameraOrAlbum() {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.NoBackGroundDialog));
        View view = View.inflate(getActivity(), R.layout.view_camera_album_dialog, null);
        builder.setCancelable(true);
        final AlertDialog alert = builder.create();
        alert.setCanceledOnTouchOutside(true);
        Window window = alert.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.ShowAlertDialogStyle);

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);//设置横向全屏
        TextView tvCamera = (TextView) view.findViewById(R.id.tv_camera);
        tvCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCamera();
                alert.dismiss();
            }
        });
        TextView tvAlbum = (TextView) view.findViewById(R.id.tv_album);
        tvAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAlbum();
                alert.dismiss();
            }
        });
        TextView tvCancel = (TextView) view.findViewById(R.id.tv_cancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.dismiss();
            }
        });
        alert.show();
        window.setContentView(view);
    }

    private void openAlbum() {
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, FinalVal.ALBUM_REQUEST_CODE);
    }

    //android6  之后需要动态申请权限
    private void openCamera() {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST);
                return;
            } else {
                takeEvidence();
            }
        } else {
            takeEvidence();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //判断请求码
        if (requestCode == CAMERA_PERMISSION_REQUEST) {
            //grantResults授权结果
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //成功，开启摄像头
                takeEvidence();
            } else {
                //授权失败
                Toast.makeText(getActivity(), "无法使用相机，权限被禁用", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_REQUEST_CODE) {
                Uri picUri = Uri.fromFile(new File(picUrlList.get(evidenceId)));
                ImageRequestBuilder imageRequestBuilder = ImageRequestBuilder.newBuilderWithSource(picUri)
                        .setAutoRotateEnabled(true)
                        .setLocalThumbnailPreviewsEnabled(true)
                        .setResizeOptions(new ResizeOptions(mSdvEvidence1.getWidth(), mSdvEvidence1.getHeight()));
                DraweeController controller = Fresco.newDraweeControllerBuilder()
                        .setImageRequest(imageRequestBuilder.build())
                        .build();
                switch (evidenceId) {
                    case 0:
                        mSdvEvidence1.setController(controller);
                        mSdvEvidence1.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View view) {
                                deletePic(1);
                                mSdvEvidence1.setOnLongClickListener(null);
                                return true;
                            }
                        });
                        break;
                    case 1:
                        mSdvEvidence2.setController(controller);
                        mSdvEvidence2.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View view) {
                                deletePic(2);
                                mSdvEvidence2.setOnLongClickListener(null);
                                return true;
                            }
                        });
                        break;
                    case 2:
                        mSdvEvidence3.setController(controller);
                        mSdvEvidence3.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View view) {
                                deletePic(3);
                                mSdvEvidence3.setOnLongClickListener(null);
                                return true;
                            }
                        });
                        break;
                }
            }
            if (requestCode == FinalVal.ALBUM_REQUEST_CODE) {
                Uri picUri = data.getData();
                picUrlList.set(evidenceId, FilePathHelper.getRealFilePathFromUri(getActivity(), picUri));
                ImageRequestBuilder imageRequestBuilder = ImageRequestBuilder.newBuilderWithSource(picUri)
                        .setAutoRotateEnabled(true)
                        .setLocalThumbnailPreviewsEnabled(true)
                        .setResizeOptions(new ResizeOptions(mSdvEvidence1.getWidth(), mSdvEvidence1.getHeight()));
                DraweeController controller = Fresco.newDraweeControllerBuilder()
                        .setImageRequest(imageRequestBuilder.build())
                        .build();
                switch (evidenceId) {
                    case 0:
                        mSdvEvidence1.setController(controller);
                        mSdvEvidence1.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View view) {
                                deletePic(1);
                                mSdvEvidence1.setOnLongClickListener(null);
                                return true;
                            }
                        });
                        break;
                    case 1:
                        mSdvEvidence2.setController(controller);
                        mSdvEvidence2.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View view) {
                                deletePic(2);
                                mSdvEvidence2.setOnLongClickListener(null);
                                return true;
                            }
                        });
                        break;
                    case 2:
                        mSdvEvidence3.setController(controller);
                        mSdvEvidence3.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View view) {
                                deletePic(3);
                                mSdvEvidence3.setOnLongClickListener(null);
                                return true;
                            }
                        });
                        break;
                }
            }
        }
    }

    private void deletePic(final int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.view_quit_dialog, null, false);
        builder.setCancelable(true);
        final AlertDialog alert = builder.create();
        alert.setView(view, 0, 0, 0, 0);
        TextView textView = (TextView) view.findViewById(R.id.tv_content);
        textView.setText("确定删除该图片吗？");
        TextView tvMoveOn = (TextView) view.findViewById(R.id.tv_moveOn);
        tvMoveOn.setText("取消");
        tvMoveOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.dismiss();
            }
        });
        TextView tvQuit = (TextView) view.findViewById(R.id.tv_quit);
        tvQuit.setText("确定");
        tvQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (id) {
                    case 1:
                        mSdvEvidence1.setImageURI("");
                        picUrlList.set(0, null);
                        mSdvEvidence1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                evidenceId = 0;
                                cameraOrAlbum();
                            }
                        });
                        break;
                    case 2:
                        mSdvEvidence2.setImageURI("");
                        picUrlList.set(1, null);
                        mSdvEvidence2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                evidenceId = 1;
                                cameraOrAlbum();
                            }
                        });
                        break;
                    case 3:
                        mSdvEvidence3.setImageURI("");
                        picUrlList.set(2, null);
                        mSdvEvidence3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                evidenceId = 2;
                                cameraOrAlbum();
                            }
                        });
                        break;
                }
                alert.dismiss();
            }
        });
        alert.show();
    }

    private void takeEvidence() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            String appPicPath = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath();
            File dir = new File(appPicPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String fileName = FilePathHelper.createUploadPicName();
            String cameraPath = appPicPath + '/' + fileName;
            Intent intent = new Intent();
            // 指定开启系统相机的Action
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            // 把文件地址转换成Uri格式
            Uri picUri = Uri.fromFile(new File(cameraPath));
            picUrlList.set(evidenceId, cameraPath);
            // 设置系统相机拍摄照片完成后图片文件的存放地址
            intent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
            startActivityForResult(intent, CAMERA_REQUEST_CODE);
        } else {
            Toast.makeText(getActivity(), "请确认已经插入SD卡",
                    Toast.LENGTH_LONG).show();
        }
    }

    private String localPathToServerPath(String localPath) {
        if (localPath == null || localPath.isEmpty()) {
            return null;
        }
        String fileName = localPath.substring(localPath.lastIndexOf('/') + 1, localPath.length());
        return PIC_SUVER_URL + fileName;
    }

    private String serverPathToLocalPath(String serverPath) {
        if (serverPath == null || serverPath.isEmpty()) {
            return null;
        }
        String fileName = serverPath.substring(serverPath.lastIndexOf('/') + 1, serverPath.length());
        String appPicPath = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath();
        return appPicPath + '/' + fileName;
    }
}
