package com.ist.cadillacpaltform.UI.fragment;


import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
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
import com.ist.cadillacpaltform.SDK.bean.Posm.Rectification;
import com.ist.cadillacpaltform.SDK.network.PosmApi;
import com.ist.cadillacpaltform.SDK.response.Posm.RectificationResponse;
import com.ist.cadillacpaltform.SDK.util.FilePathHelper;
import com.ist.cadillacpaltform.SDK.util.OnLoadViewOkListener;
import com.ist.cadillacpaltform.SDK.util.PicLoadingStyle;
import com.ist.cadillacpaltform.SDK.util.oss.ImageHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

import static com.ist.cadillacpaltform.R.drawable.icon_camera;
import static com.ist.cadillacpaltform.R.drawable.icon_placeholder;


public class RectifyResultFragment extends Fragment {
    protected View mRoot;
    private LinearLayout mLlrootView;
    private SimpleDraweeView mSdvRectificationPic1;
    private SimpleDraweeView mSdvRectificationPic2;
    private SimpleDraweeView mSdvRectificationPic3;
    private TextView mTvRectification;

    private int questionIndex = 0;
    private int rectificationPicWidth = 330;
    private int rectificationPicHeight = 300;
    private List<AbstractGradeDetail> abstractGradeDetailList;//通过gradeId获得的题目及打分信息
    List<Rectification> rectificationList;
    private OnLoadViewOkListener onLoadViewOkListener = null;

    public void setOnLoadViewOkListener(OnLoadViewOkListener onLoadViewOkListener) {
        this.onLoadViewOkListener = onLoadViewOkListener;
    }

    public RectifyResultFragment() {
    }

    public void setAbstractGradeDetailList(List<AbstractGradeDetail> argument) {
        abstractGradeDetailList = argument;
        rectificationList = new ArrayList<>();
        for (int i = 0; i < abstractGradeDetailList.size(); i++) {
            rectificationList.add(null);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.fragment_rectify_result, container, false);
        mLlrootView = (LinearLayout) mRoot.findViewById(R.id.ll_rootView);
        mLlrootView.setVisibility(View.GONE);
        mSdvRectificationPic1 = (SimpleDraweeView) mRoot.findViewById(R.id.sdv_rectificationPic1);
        mSdvRectificationPic2 = (SimpleDraweeView) mRoot.findViewById(R.id.sdv_rectificationPic2);
        mSdvRectificationPic3 = (SimpleDraweeView) mRoot.findViewById(R.id.sdv_rectificationPic3);
        GenericDraweeHierarchyBuilder hierarchyBuilder = new GenericDraweeHierarchyBuilder(getResources());
        hierarchyBuilder.setProgressBarImage(new PicLoadingStyle(getActivity()).getQuestionPicLoad2());
        hierarchyBuilder.setActualImageScaleType(ScalingUtils.ScaleType.FIT_XY);
        hierarchyBuilder.setPlaceholderImage(icon_placeholder);
        mSdvRectificationPic1.setHierarchy(hierarchyBuilder.build());
        mSdvRectificationPic2.setHierarchy(hierarchyBuilder.build());
        mSdvRectificationPic3.setHierarchy(hierarchyBuilder.build());
        mTvRectification = (TextView) mRoot.findViewById(R.id.tv_rectification);
        mSdvRectificationPic2.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        rectificationPicWidth = mSdvRectificationPic2.getWidth();
                        rectificationPicHeight = mSdvRectificationPic2.getHeight();
                        mSdvRectificationPic1.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                });
        return mRoot;
    }

    public void setRectifyResult(int index) {
        questionIndex = index;
        if (rectificationList.get(questionIndex) == null) {
            Subscriber<RectificationResponse> subscriber = new Subscriber<RectificationResponse>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                    if (e instanceof HttpException) {
                        HttpException httpException = (HttpException) e;
                        switch (httpException.code()) {
                            default:
                                Toast.makeText(getActivity(), "网络错误，无法获取整改信息", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "其他错误，无法获取整改信息", Toast.LENGTH_SHORT).show();
                    }
                    e.printStackTrace();
                }

                @Override
                public void onNext(RectificationResponse rectificationResponse) {
                    rectificationList.set(questionIndex, rectificationResponse.rectification);
                    setRectifyResultOnscreen(rectificationList.get(questionIndex));
                }
            };
            PosmApi.getInstance().getRectificationByGradeDetailId(subscriber, abstractGradeDetailList.get(questionIndex).getId());
        } else

        {
            setRectifyResultOnscreen(rectificationList.get(questionIndex));
        }

    }

    private void setRectifyResultOnscreen(Rectification rectification) {
        if (rectification == null) {
            mLlrootView.setVisibility(View.GONE);
        }
        else {
            mLlrootView.setVisibility(View.VISIBLE);
            mTvRectification.setText(rectification.getDescription());
            if (!FilePathHelper.isStringEmptyOrNull(rectification.getPicurl1())) {
//            Uri picUri = Uri.parse(rectification.getPicurl1());
//            ImageRequestBuilder imageRequestBuilder = ImageRequestBuilder.newBuilderWithSource(picUri)
//                    .setAutoRotateEnabled(true)
//                    .setLocalThumbnailPreviewsEnabled(true)
//                    .setResizeOptions(new ResizeOptions(rectificationPicWidth, rectificationPicHeight));
//            DraweeController controller = Fresco.newDraweeControllerBuilder()
//                    .setImageRequest(imageRequestBuilder.build())
//                    .build();
//            mSdvRectificationPic1.setController(controller);
                mSdvRectificationPic1.setImageURI(ImageHelper.resize(rectification.getPicurl1(), mSdvRectificationPic1.getWidth(), mSdvRectificationPic1.getHeight()));
            } else {
                mSdvRectificationPic1.setImageURI("");
            }
            if (!FilePathHelper.isStringEmptyOrNull(rectification.getPicurl2())) {
//            Uri picUri = Uri.parse(rectification.getPicurl2());
//            ImageRequestBuilder imageRequestBuilder = ImageRequestBuilder.newBuilderWithSource(picUri)
//                    .setAutoRotateEnabled(true)
//                    .setLocalThumbnailPreviewsEnabled(true)
//                    .setResizeOptions(new ResizeOptions(rectificationPicWidth, rectificationPicHeight));
//            DraweeController controller = Fresco.newDraweeControllerBuilder()
//                    .setImageRequest(imageRequestBuilder.build())
//                    .build();
//            mSdvRectificationPic2.setController(controller);
                mSdvRectificationPic2.setImageURI(ImageHelper.resize(rectification.getPicurl2(), mSdvRectificationPic2.getWidth(), mSdvRectificationPic2.getHeight()));
            } else {
                mSdvRectificationPic2.setImageURI("");
            }
            if (!FilePathHelper.isStringEmptyOrNull(rectification.getPicurl3())) {
//            Uri picUri = Uri.parse(rectification.getPicurl3());
//            ImageRequestBuilder imageRequestBuilder = ImageRequestBuilder.newBuilderWithSource(picUri)
//                    .setAutoRotateEnabled(true)
//                    .setLocalThumbnailPreviewsEnabled(true)
//                    .setResizeOptions(new ResizeOptions(rectificationPicWidth, rectificationPicHeight));
//            DraweeController controller = Fresco.newDraweeControllerBuilder()
//                    .setImageRequest(imageRequestBuilder.build())
//                    .build();
//            mSdvRectificationPic3.setController(controller);
                mSdvRectificationPic3.setImageURI(ImageHelper.resize(rectification.getPicurl3(), mSdvRectificationPic3.getWidth(), mSdvRectificationPic3.getHeight()));
            } else {
                mSdvRectificationPic3.setImageURI("");
            }
        }
        if (onLoadViewOkListener != null) {
            onLoadViewOkListener.onLoadViewOk();
        }
    }
}
