package com.ist.cadillacpaltform.UI.fragment;

import android.app.Fragment;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.ist.cadillacpaltform.R;
import com.ist.cadillacpaltform.SDK.bean.Posm.AbstractGradeDetail;
import com.ist.cadillacpaltform.SDK.bean.Posm.Question;
import com.ist.cadillacpaltform.SDK.network.PosmApi;
import com.ist.cadillacpaltform.SDK.response.Posm.QuestionResponse;
import com.ist.cadillacpaltform.SDK.util.OnLoadViewOkListener;
import com.ist.cadillacpaltform.SDK.util.PicLoadingStyle;
import com.ist.cadillacpaltform.SDK.util.ZoomableDraweeView;
import com.ist.cadillacpaltform.SDK.util.oss.ImageHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

import static com.ist.cadillacpaltform.R.drawable.icon_placeholder;


public class QuestionFragment extends Fragment {
    protected View mRoot;
    private TextView mTvItem;//题目标题
    private TextView mTvStandard;//题目标准
    private SimpleDraweeView mSdvStandardPhoto1;
    private SimpleDraweeView mSdvStandardPhoto2;
    private SimpleDraweeView mSdvStandardPhoto3;
    private View PopupWindowView;
    private PopupWindow popupWindow;

    private int StandardPhotoWidth;
    private int StandardPhotoHeight;
    private Subscriber<QuestionResponse> mSubscriberQuestion;
    private int questionIndex = 0;//当前题目在abstractGradeDetailList的下标
    private List<AbstractGradeDetail> abstractGradeDetailList;//通过gradeId获得的题目及打分信息
    private List<Question> questionList;//用于存储题目信息
    private OnLoadViewOkListener onLoadViewOkListener = null;

    public void setOnLoadViewOkListener(OnLoadViewOkListener onLoadViewOkListener) {
        this.onLoadViewOkListener = onLoadViewOkListener;
    }

    public QuestionFragment() {
    }

    public void setAbstractGradeDetailList(List<AbstractGradeDetail> argument) {
        abstractGradeDetailList = argument;
        questionList = new ArrayList<>();
        for (int i = 0; i < abstractGradeDetailList.size(); i++) {
            questionList.add(null);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Fresco.initialize(getActivity());//初始化Fresco框架
        mRoot = inflater.inflate(R.layout.fragment_question, container, false);
        mTvItem = (TextView) mRoot.findViewById(R.id.tv_item);
        mTvStandard = (TextView) mRoot.findViewById(R.id.tv_standard);
        mSdvStandardPhoto1 = (SimpleDraweeView) mRoot.findViewById(R.id.sdv_standard1);
        mSdvStandardPhoto2 = (SimpleDraweeView) mRoot.findViewById(R.id.sdv_standard2);
        mSdvStandardPhoto3 = (SimpleDraweeView) mRoot.findViewById(R.id.sdv_standard3);
        GenericDraweeHierarchyBuilder hierarchyBuilder = new GenericDraweeHierarchyBuilder(getResources());
        hierarchyBuilder.setProgressBarImage(new PicLoadingStyle(getActivity()).getQuestionPicLoad2());
        hierarchyBuilder.setActualImageScaleType(ScalingUtils.ScaleType.FIT_XY);
        hierarchyBuilder.setPlaceholderImage(icon_placeholder);
        mSdvStandardPhoto1.setHierarchy(hierarchyBuilder.build());
        mSdvStandardPhoto2.setHierarchy(hierarchyBuilder.build());
        mSdvStandardPhoto3.setHierarchy(hierarchyBuilder.build());
        //获取缩略图长宽
        mSdvStandardPhoto1.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        StandardPhotoWidth = mSdvStandardPhoto1.getWidth();
                        StandardPhotoHeight = mSdvStandardPhoto1.getHeight();
                        mSdvStandardPhoto1.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                });
        initPopupWindow();
        return mRoot;
    }

    private void initPopupWindow() {
        PopupWindowView = LayoutInflater.from(getActivity()).inflate(R.layout.item_popupwindow_show_full_photo, null);
        ZoomableDraweeView sdvFullScreenPic = (ZoomableDraweeView) PopupWindowView.findViewById(R.id.sdv_full_screen_pic);
        GenericDraweeHierarchyBuilder hierarchyBuilder = new GenericDraweeHierarchyBuilder(getResources());
        hierarchyBuilder.setProgressBarImage(new PicLoadingStyle(getActivity()).getQuestionPicLoad2());
        hierarchyBuilder.setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER);
        sdvFullScreenPic.setHierarchy(hierarchyBuilder.build());
        sdvFullScreenPic.setOnClickListener(new ZoomableDraweeView.OnClickListener() {
            @Override
            public void onClick() {
                popupWindow.dismiss();
            }
        });
        PopupWindowView.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        popupWindow = new PopupWindow(PopupWindowView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
    }

    public void setQuestion(int index) {
        questionIndex = index;
        if (questionList.get(questionIndex) == null) {
            //设置分值
            mSubscriberQuestion = new Subscriber<QuestionResponse>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    if (e instanceof HttpException) {
                        HttpException httpException = (HttpException) e;
                        switch (httpException.code()) {
                            case 404:
                                Toast.makeText(getActivity(), "网络错误,无法获取题目信息", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Toast.makeText(getActivity(), "网络错误,无法获取题目信息", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "其他错误", Toast.LENGTH_SHORT).show();
                    }
                    e.printStackTrace();
                }

                @Override
                public void onNext(QuestionResponse questionResponse) {
                    questionList.set(questionIndex, questionResponse.question);
                    setQuestionOnScreen(questionResponse.question);
                }
            };
            AbstractGradeDetail simpleQuestion = abstractGradeDetailList.get(questionIndex);
            PosmApi.getInstance().getQuestionById(mSubscriberQuestion, simpleQuestion.getQuestionId());
        } else {
            setQuestionOnScreen(questionList.get(questionIndex));
        }
    }

    private void setQuestionOnScreen(final Question question) {
        //设置题目标题
        mTvItem.setText(String.valueOf(questionIndex + 1) + "." + question.getItem());
        mTvStandard.setText(question.getStandard());
        //设置打分标准图片
        if (question.getPicurl1() != null) {
            Uri uri1 = Uri.parse(ImageHelper.resize(question.getPicurl1(), StandardPhotoWidth, StandardPhotoHeight));
            mSdvStandardPhoto1.setImageURI(uri1);
        } else {
            mSdvStandardPhoto1.setImageURI("");
        }
        if (question.getPicurl2() != null) {
            Uri uri2 = Uri.parse(ImageHelper.resize(question.getPicurl2(), StandardPhotoWidth, StandardPhotoHeight));
            mSdvStandardPhoto2.setImageURI(uri2);
        } else {
            mSdvStandardPhoto2.setImageURI("");
        }
        if (question.getPicurl3() != null) {
            Uri uri3 = Uri.parse(ImageHelper.resize(question.getPicurl3(), StandardPhotoWidth, StandardPhotoHeight));
            mSdvStandardPhoto3.setImageURI(uri3);
        } else {
            mSdvStandardPhoto3.setImageURI("");
        }
        mSdvStandardPhoto1.setOnClickListener(new View.OnClickListener() {
                                                  @Override
                                                  public void onClick(View view) {
                                                      if (question.getPicurl1() == null) {
                                                          return;
                                                      }
                                                      //用height=1dp测试getDrawable不会损失像素
                                                      ZoomableDraweeView temp = (ZoomableDraweeView) PopupWindowView.findViewById(R.id.sdv_full_screen_pic);
                                                      //在popoupwindow中设置的scaletype不起作用，setImageDrawable只会和getdrawable中的scaletype一样,且子线程无法更新UI
                                                      Uri uri = Uri.parse(question.getPicurl1());
                                                      temp.setImageURI(uri);
                                                      popupWindow.showAtLocation(getActivity().findViewById(R.id.ll_root), Gravity.CENTER, 0, 0);
                                                  }
                                              }
        );
        mSdvStandardPhoto2.setOnClickListener(new View.OnClickListener() {
                                                  @Override
                                                  public void onClick(View view) {
                                                      if (question.getPicurl2() == null) {
                                                          return;
                                                      }
                                                      ZoomableDraweeView temp = (ZoomableDraweeView) PopupWindowView.findViewById(R.id.sdv_full_screen_pic);
                                                      Uri uri = Uri.parse(question.getPicurl2());
                                                      temp.setImageURI(uri);
                                                      popupWindow.showAtLocation(getActivity().findViewById(R.id.ll_root), Gravity.CENTER, 0, 0);
                                                  }
                                              }
        );
        mSdvStandardPhoto3.setOnClickListener(new View.OnClickListener() {
                                                  @Override
                                                  public void onClick(View view) {
                                                      if (question.getPicurl3() == null) {
                                                          return;
                                                      }
                                                      ZoomableDraweeView temp = (ZoomableDraweeView) PopupWindowView.findViewById(R.id.sdv_full_screen_pic);
                                                      Uri uri = Uri.parse(question.getPicurl3());
                                                      temp.setImageURI(uri);
                                                      popupWindow.showAtLocation(getActivity().findViewById(R.id.ll_root), Gravity.CENTER, 0, 0);
                                                  }
                                              }
        );
        if (onLoadViewOkListener != null) {
            onLoadViewOkListener.onLoadViewOk();
        }
    }
}
