package com.ist.cadillacpaltform.SDK.util.oss;

import android.content.Context;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.GetObjectRequest;
import com.alibaba.sdk.android.oss.model.GetObjectResult;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;

/**
 * Created by oss on 2015/12/7 0007.
 * 支持普通上传，普通下载和断点上传
 */
public class OssService {
    public final static String ENDPOINT = "http://oss-cn-shanghai.aliyuncs.com";
    public final static String BUCKET = "posmonline";

    private final static String TAG = "OssService";

    private OSS mOss;
    private ClientConfiguration mConfig;
    private MultiPartUploadManager mMultiPartUploadManager;
    private String mCallbackAddress;
    private Context mContext;

    //根据实际需求改变分片大小
    private final static int partSize = 256 * 1024;

    public OssService(Context context) {
        mContext = context;

        OSSCredentialProvider credentialProvider = new STSGetter();

        mConfig = new ClientConfiguration();
        mConfig.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        mConfig.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        mConfig.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        mConfig.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次

        mOss = new OSSClient(mContext, ENDPOINT, credentialProvider, mConfig);
        mMultiPartUploadManager = new MultiPartUploadManager(mOss, BUCKET, partSize);
    }

    public void requestSTS () {
        OSSCredentialProvider credentialProvider = new STSGetter();
        mOss = new OSSClient(mContext, ENDPOINT, credentialProvider, mConfig);
        mMultiPartUploadManager = new MultiPartUploadManager(mOss, BUCKET, partSize);
    }

    public void asyncGetImage(String object) {
        if ((object == null) || object.equals("")) {
            Log.w(TAG, "ObjectNull");
            return;
        }

        object = "picture/" + object;         // 这里注意一下，图片都保存在picture目录下

        GetObjectRequest get = new GetObjectRequest(BUCKET, object);

        OSSAsyncTask task = mOss.asyncGetObject(get, new OSSCompletedCallback<GetObjectRequest, GetObjectResult>() {
            @Override
            public void onSuccess(GetObjectRequest request, GetObjectResult result) {
                // 请求成功
                InputStream inputStream = result.getObjectContent();
                //重载InputStream来获取读取进度信息
                ProgressInputStream progressStream = new ProgressInputStream(inputStream, new OSSProgressCallback<GetObjectRequest>() {
                    @Override
                    public void onProgress(GetObjectRequest o, long currentSize, long totalSize) {
                        Log.d(TAG, "currentSize: " + currentSize + " totalSize: " + totalSize);
                        // 这是下载进度
                        int progress = (int) (100 * currentSize / totalSize);
                    }
                }, result.getContentLength());

                //Bitmap bm = BitmapFactory.decodeStream(inputStream);

            }

            @Override
            public void onFailure(GetObjectRequest request, ClientException clientException, ServiceException serviceException) {
                String info = "";
                // 请求异常
                if (clientException != null) {
                    // 本地异常如网络异常等
                    clientException.printStackTrace();
                    info = clientException.toString();
                }
                if (serviceException != null) {
                    // 服务异常
                    Log.e(TAG, "ErrorCode " + serviceException.getErrorCode());
                    Log.e(TAG, "RequestId " + serviceException.getRequestId());
                    Log.e(TAG, "HostId " + serviceException.getHostId());
                    Log.e(TAG, "RawMessage" + serviceException.getRawMessage());
                    info = serviceException.toString();
                }
            }
        });
    }


    public void asyncPutImage(String object, String localFile) {
        if (object.equals("")) {
            Log.w(TAG, "AsyncPutImage ObjectNull");
            return;
        }

        object = "picture/" + object;         // 这里注意一下，图片都保存在picture目录下
        Log.i(TAG, "AsyncPutImage " + object);

        File file = new File(localFile);
        if (!file.exists()) {
            Log.w(TAG, "AsyncPutImage FileNotExist");
            Log.w(TAG, "LocalFile " + localFile);
            return;
        }


        // 构造上传请求
        PutObjectRequest put = new PutObjectRequest(BUCKET, object, localFile);

        if (mCallbackAddress != null) {
            // 传入对应的上传回调参数，这里默认使用OSS提供的公共测试回调服务器地址
            put.setCallbackParam(new HashMap<String, String>() {
                {
                    put(TAG, "callbackUrl " + mCallbackAddress);
                    //callbackBody可以自定义传入的信息
                    put(TAG, "callbackBody filename=${object}");
                }
            });
        }

        // 异步上传时可以设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                // 上传进度
                int progress = (int) (100 * currentSize / totalSize);
            }
        });

        OSSAsyncTask task = mOss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                Log.d(TAG, "PutObject UploadSuccess");

                Log.d(TAG, "ETag " + result.getETag());
                Log.d(TAG, "RequestId " + result.getRequestId());

            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                String info = "";
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                    info = clientExcepion.toString();
                }
                if (serviceException != null) {
                    // 服务异常
                    Log.e(TAG, "ErrorCode " + serviceException.getErrorCode());
                    Log.e(TAG, "RequestId" + serviceException.getRequestId());
                    Log.e(TAG, "HostId " + serviceException.getHostId());
                    Log.e(TAG, "RawMessage " + serviceException.getRawMessage());
                    info = serviceException.toString();
                }
            }
        });
    }

    //断点上传，返回的task可以用来暂停任务
    public BreakpointUploadTask asyncMultiPartUpload(String object, String localFile) {
        if (object.equals("")) {
            Log.w(TAG, "AsyncMultiPartUpload ObjectNull");
            return null;
        }

        File file = new File(localFile);
        if (!file.exists()) {
            Log.w(TAG, "AsyncMultiPartUpload FileNotExist");
            Log.w(TAG, "LocalFile " + localFile);
            return null;
        }

        Log.d(TAG, "MultiPartUpload " + localFile);
        BreakpointUploadTask task = mMultiPartUploadManager.asyncUpload(object, localFile);
        return task;
    }

}
