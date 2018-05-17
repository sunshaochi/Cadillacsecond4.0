package com.ist.cadillacpaltform.UI.fragment;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;
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
import com.ist.cadillacpaltform.SDK.bean.Posm.Rectification;
import com.ist.cadillacpaltform.SDK.network.PosmApi;
import com.ist.cadillacpaltform.SDK.response.Posm.RectificationListResponse;
import com.ist.cadillacpaltform.SDK.response.Posm.RectificationResponse;
import com.ist.cadillacpaltform.SDK.util.FilePathHelper;
import com.ist.cadillacpaltform.SDK.util.OnLoadViewOkListener;
import com.ist.cadillacpaltform.SDK.util.PicLoadingStyle;
import com.ist.cadillacpaltform.SDK.util.oss.ImageHelper;
import com.ist.cadillacpaltform.SDK.util.oss.OssService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

import static com.ist.cadillacpaltform.R.drawable.icon_camera;
import static com.ist.cadillacpaltform.SDK.bean.Posm.FinalVal.CAMERA_PERMISSION_REQUEST;
import static com.ist.cadillacpaltform.SDK.bean.Posm.FinalVal.CAMERA_REQUEST_CODE;
import static com.ist.cadillacpaltform.SDK.bean.Posm.FinalVal.PIC_SUVER_URL;


public class RectifyFragment extends Fragment {
    private View mRoot;
    private SimpleDraweeView mSdvRectificationPic1;
    private SimpleDraweeView mSdvRectificationPic2;
    private SimpleDraweeView mSdvRectificationPic3;
    private EditText mEtDescription;

    private int evidenceId = 0;//判断哪个evidence调用了摄像头;
    private int questionIndex = 0;
    private int RectificationPicWidth = 330;
    private int RectificationPic1Height = 300;
    private List<AbstractGradeDetail> abstractGradeDetailList;//通过gradeId获得的题目及打分信息
    private List<Rectification> rectificationList;//存储已整改情况
    private List<String> picUrlList;//图片本地路径URl
    private OnLoadViewOkListener onLoadViewOkListener = null;

    public void setOnLoadViewOkListener(OnLoadViewOkListener onLoadViewOkListener) {
        this.onLoadViewOkListener = onLoadViewOkListener;
    }


    public RectifyFragment() {
    }

    public void setAbstractGradeDetailList(List<AbstractGradeDetail> argument) {
        abstractGradeDetailList = argument;
        rectificationList = new ArrayList<>();
        for (int i = 0; i < abstractGradeDetailList.size(); i++) {
            rectificationList.add(null);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.fragment_rectify, container, false);
        picUrlList = new ArrayList<>();
        picUrlList.add(null);
        picUrlList.add(null);
        picUrlList.add(null);
        mSdvRectificationPic1 = (SimpleDraweeView) mRoot.findViewById(R.id.sdv_rectificationPic1);
        mSdvRectificationPic2 = (SimpleDraweeView) mRoot.findViewById(R.id.sdv_rectificationPic2);
        mSdvRectificationPic3 = (SimpleDraweeView) mRoot.findViewById(R.id.sdv_rectificationPic3);
        GenericDraweeHierarchyBuilder hierarchyBuilder = new GenericDraweeHierarchyBuilder(getResources());
        hierarchyBuilder.setProgressBarImage(new PicLoadingStyle(getActivity()).getQuestionPicLoad2());
        hierarchyBuilder.setActualImageScaleType(ScalingUtils.ScaleType.FIT_XY);
        hierarchyBuilder.setPlaceholderImage(icon_camera);
        mSdvRectificationPic1.setHierarchy(hierarchyBuilder.build());
        mSdvRectificationPic2.setHierarchy(hierarchyBuilder.build());
        mSdvRectificationPic3.setHierarchy(hierarchyBuilder.build());
        mEtDescription = (EditText) mRoot.findViewById(R.id.et_description);
        mSdvRectificationPic1.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        RectificationPicWidth = mSdvRectificationPic1.getWidth();
                        RectificationPic1Height = mSdvRectificationPic1.getHeight();
                        mSdvRectificationPic1.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                });
        mSdvRectificationPic1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evidenceId = 0;
                cameraOrAlbum();
            }
        });
        mSdvRectificationPic2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evidenceId = 1;
                cameraOrAlbum();
            }
        });
        mSdvRectificationPic3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evidenceId = 2;
                cameraOrAlbum();
            }
        });
        return mRoot;
    }

    public void prev() {
        if (questionIndex > 0) {
            if (rectificationList.get(questionIndex) == null || isRectificationlModified()) {//没有打过分或者修改过
                updateRectification();
                uploadRectification();
            }
            questionIndex--;
            setRectify(questionIndex);
        }
    }

    public void next() {
        if (questionIndex < abstractGradeDetailList.size() - 1) {//不是最后一题
            if (rectificationList.get(questionIndex) == null || isRectificationlModified()) {//判断有没有打过分或者修改过
                updateRectification();
                uploadRectification();
            }
            questionIndex++;
            setRectify(questionIndex);
        }
    }

    public void summit() {
        if (rectificationList.get(questionIndex) == null || isRectificationlModified()) {//判断有没有打过分或者修改过
            updateRectification();
            uploadRectification();
        }
    }

    private boolean isRectificationlModified() {
        if (rectificationList.get(questionIndex) == null) {
            return true;
        } else {
            Rectification rectification = rectificationList.get(questionIndex);
            if (rectification.getDescription() != mEtDescription.getText().toString())
                return true;
            if (rectification.getPicurl1() != localPathToServerPath(picUrlList.get(0))) {
                return true;
            }
            if (rectification.getPicurl2() != localPathToServerPath(picUrlList.get(1))) {
                return true;
            }
            if (rectification.getPicurl3() != localPathToServerPath(picUrlList.get(2))) {
                return true;
            }
        }
        return false;
    }

    private void uploadRectification() {
        AbstractGradeDetail abstractGradeDetail = abstractGradeDetailList.get(questionIndex);
        long gradeDetailId = abstractGradeDetail.getId();
        final Rectification rectification = new Rectification();
        GradeDetail gradeDetail = new GradeDetail();
        gradeDetail.setId(gradeDetailId);
        rectification.setGradeDetail(gradeDetail);
        rectification.setDescription(mEtDescription.getText().toString());
        rectification.setPicurl1(localPathToServerPath(picUrlList.get(0)));
        rectification.setPicurl2(localPathToServerPath(picUrlList.get(1)));
        rectification.setPicurl3(localPathToServerPath(picUrlList.get(2)));
        uploadPic();
        Subscriber<RectificationResponse> subscriber = new Subscriber<RectificationResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(RectificationResponse rectificationResponse) {
                if (rectificationResponse.rectification == null) {//还没有整改，所以post
                    Subscriber<RectificationListResponse> subscriber = new Subscriber<RectificationListResponse>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            if (e instanceof HttpException) {
                                HttpException httpException = (HttpException) e;
                                switch (httpException.code()) {
                                    default:
                                        Toast.makeText(getActivity(), "网络错误，无法上传整改", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getActivity(), "其他错误，无法上传整改", Toast.LENGTH_SHORT).show();
                            }
                            e.printStackTrace();
                        }

                        @Override
                        public void onNext(RectificationListResponse rectificationListResponse) {

                        }
                    };
                    PosmApi.getInstance().postRectification(subscriber, rectification);
                } else {//已经有整改了，所以patch
                    long rectificationId = rectificationResponse.rectification.getId();
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
                                        Toast.makeText(getActivity(), "网络错误，无法上传整改", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getActivity(), "其他错误，无法上传整改", Toast.LENGTH_SHORT).show();
                            }
                            e.printStackTrace();
                        }

                        @Override
                        public void onNext(RectificationResponse rectificationResponse) {

                        }
                    };
                    PosmApi.getInstance().patchRectification(subscriber, rectificationId, rectification);
                }
            }
        };
        PosmApi.getInstance().getRectificationByGradeDetailId(subscriber, gradeDetailId);
    }

    private void updateRectification() {
        Rectification rectification = new Rectification();
        rectification.setDescription(mEtDescription.getText().toString());
        rectification.setPicurl1(localPathToServerPath(picUrlList.get(0)));
        rectification.setPicurl2(localPathToServerPath(picUrlList.get(1)));
        rectification.setPicurl3(localPathToServerPath(picUrlList.get(2)));
        rectificationList.set(questionIndex, rectification);
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

    public void setRectify(int index) {
        questionIndex = index;
        Rectification rectification = rectificationList.get(questionIndex);
        if (rectification == null) {
            long gradeDetailId = abstractGradeDetailList.get(questionIndex).getId();
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
                    Rectification rectification = rectificationResponse.rectification;
                    rectificationList.set(questionIndex, rectification);
                    setRectifyOnScreen(rectification);
                }
            };
            PosmApi.getInstance().getRectificationByGradeDetailId(subscriber, gradeDetailId);
        } else {
            setRectifyOnScreen(rectification);
        }

    }

    private void setRectifyOnScreen(Rectification rectification) {
        if (rectification == null) {
            mEtDescription.setText("");
            picUrlList.set(0, null);
            picUrlList.set(1, null);
            picUrlList.set(2, null);
            mSdvRectificationPic1.setImageURI("");
            mSdvRectificationPic2.setImageURI("");
            mSdvRectificationPic3.setImageURI("");
        } else {
            mEtDescription.setText(rectification.getDescription());
            picUrlList.set(0, serverPathToLocalPath(rectification.getPicurl1()));
            picUrlList.set(1, serverPathToLocalPath(rectification.getPicurl2()));
            picUrlList.set(2, serverPathToLocalPath(rectification.getPicurl3()));
            if (!FilePathHelper.isStringEmptyOrNull(picUrlList.get(0))) {
                File file = new File(picUrlList.get(0));
                if (file.exists()) {
                    Uri picUri = Uri.fromFile(file);
                    ImageRequestBuilder imageRequestBuilder = ImageRequestBuilder.newBuilderWithSource(picUri)
                            .setAutoRotateEnabled(true)
                            .setLocalThumbnailPreviewsEnabled(true)
                            .setResizeOptions(new ResizeOptions(RectificationPicWidth, RectificationPic1Height));
                    DraweeController controller = Fresco.newDraweeControllerBuilder()
                            .setImageRequest(imageRequestBuilder.build())
                            .build();
                    mSdvRectificationPic1.setController(controller);
                } else {
                    mSdvRectificationPic1.setImageURI(ImageHelper.resize(rectification.getPicurl1(), RectificationPicWidth, RectificationPic1Height));
                }
                mSdvRectificationPic1.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        deletePic(1);
                        mSdvRectificationPic1.setOnLongClickListener(null);
                        return true;
                    }
                });
            } else {
                mSdvRectificationPic1.setImageURI("");
            }
            if (!FilePathHelper.isStringEmptyOrNull(picUrlList.get(1))) {
                File file = new File(picUrlList.get(1));
                if (file.exists()) {
                    Uri picUri = Uri.fromFile(file);
                    ImageRequestBuilder imageRequestBuilder = ImageRequestBuilder.newBuilderWithSource(picUri)
                            .setAutoRotateEnabled(true)
                            .setLocalThumbnailPreviewsEnabled(true)
                            .setResizeOptions(new ResizeOptions(RectificationPicWidth, RectificationPic1Height));
                    DraweeController controller = Fresco.newDraweeControllerBuilder()
                            .setImageRequest(imageRequestBuilder.build())
                            .build();
                    mSdvRectificationPic2.setController(controller);
                } else {
                    mSdvRectificationPic2.setImageURI(ImageHelper.resize(rectification.getPicurl2(), RectificationPicWidth, RectificationPic1Height));
                }
                mSdvRectificationPic2.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        deletePic(2);
                        mSdvRectificationPic2.setOnLongClickListener(null);
                        return true;
                    }
                });
            } else {
                mSdvRectificationPic2.setImageURI("");
            }
            if (!FilePathHelper.isStringEmptyOrNull(picUrlList.get(2))) {
                File file = new File(picUrlList.get(2));
                if (file.exists()) {
                    Uri picUri = Uri.fromFile(file);
                    ImageRequestBuilder imageRequestBuilder = ImageRequestBuilder.newBuilderWithSource(picUri)
                            .setAutoRotateEnabled(true)
                            .setLocalThumbnailPreviewsEnabled(true)
                            .setResizeOptions(new ResizeOptions(RectificationPicWidth, RectificationPic1Height));
                    DraweeController controller = Fresco.newDraweeControllerBuilder()
                            .setImageRequest(imageRequestBuilder.build())
                            .build();
                    mSdvRectificationPic3.setController(controller);
                } else {
                    mSdvRectificationPic3.setImageURI(ImageHelper.resize(rectification.getPicurl3(), RectificationPicWidth, RectificationPic1Height));
                }
                mSdvRectificationPic3.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        deletePic(3);
                        mSdvRectificationPic3.setOnLongClickListener(null);
                        return true;
                    }
                });
            } else {
                mSdvRectificationPic3.setImageURI("");
            }
        }
        if (onLoadViewOkListener != null) {
            onLoadViewOkListener.onLoadViewOk();
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

    private void openCamera() {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST);
                return;
            } else {
                takeRectificationPic();
            }
        } else {
            takeRectificationPic();
        }
    }

    private void takeRectificationPic() {
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //判断请求码
        if (requestCode == CAMERA_PERMISSION_REQUEST) {
            //grantResults授权结果
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //成功，开启摄像头
                takeRectificationPic();
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
                        .setResizeOptions(new ResizeOptions(RectificationPicWidth, RectificationPic1Height));
                DraweeController controller = Fresco.newDraweeControllerBuilder()
                        .setImageRequest(imageRequestBuilder.build())
                        .build();
                switch (evidenceId) {
                    case 0:
                        mSdvRectificationPic1.setController(controller);
                        mSdvRectificationPic1.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View view) {
                                deletePic(1);
                                mSdvRectificationPic1.setOnLongClickListener(null);
                                return true;
                            }
                        });
                        break;
                    case 1:
                        mSdvRectificationPic2.setController(controller);
                        mSdvRectificationPic2.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View view) {
                                deletePic(2);
                                mSdvRectificationPic2.setOnLongClickListener(null);
                                return true;
                            }
                        });
                        break;
                    case 2:
                        mSdvRectificationPic3.setController(controller);
                        mSdvRectificationPic3.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View view) {
                                deletePic(3);
                                mSdvRectificationPic3.setOnLongClickListener(null);
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
                        .setResizeOptions(new ResizeOptions(RectificationPicWidth, RectificationPic1Height));
                DraweeController controller = Fresco.newDraweeControllerBuilder()
                        .setImageRequest(imageRequestBuilder.build())
                        .build();
                switch (evidenceId) {
                    case 0:
                        mSdvRectificationPic1.setController(controller);
                        mSdvRectificationPic1.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View view) {
                                deletePic(1);
                                mSdvRectificationPic1.setOnLongClickListener(null);
                                return true;
                            }
                        });
                        break;
                    case 1:
                        mSdvRectificationPic2.setController(controller);
                        mSdvRectificationPic2.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View view) {
                                deletePic(2);
                                mSdvRectificationPic2.setOnLongClickListener(null);
                                return true;
                            }
                        });
                        break;
                    case 2:
                        mSdvRectificationPic3.setController(controller);
                        mSdvRectificationPic3.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View view) {
                                deletePic(3);
                                mSdvRectificationPic3.setOnLongClickListener(null);
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
                        mSdvRectificationPic1.setImageURI("");
                        picUrlList.set(0, null);
                        mSdvRectificationPic1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                evidenceId = 0;
                                cameraOrAlbum();
                            }
                        });
                        break;
                    case 2:
                        mSdvRectificationPic2.setImageURI("");
                        picUrlList.set(1, null);
                        mSdvRectificationPic2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                evidenceId = 1;
                                cameraOrAlbum();
                            }
                        });
                        break;
                    case 3:
                        mSdvRectificationPic3.setImageURI("");
                        picUrlList.set(2, null);
                        mSdvRectificationPic3.setOnClickListener(new View.OnClickListener() {
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
