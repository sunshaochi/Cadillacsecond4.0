package com.ist.cadillacpaltform.SDK.util.oss;

import com.alibaba.sdk.android.oss.model.CompleteMultipartUploadResult;

/**
 * Created by Administrator on 2015/12/8 0008.
 */
public class BreakPointUploadResult extends CompleteMultipartUploadResult {

    public BreakPointUploadResult(CompleteMultipartUploadResult completeResult) {
        this.setBucketName(completeResult.getBucketName());
        this.setObjectKey(completeResult.getObjectKey());
        this.setETag(completeResult.getETag());
        this.setLocation(completeResult.getLocation());
        this.setRequestId(completeResult.getRequestId());
        this.setResponseHeader(completeResult.getResponseHeader());
        this.setStatusCode(completeResult.getStatusCode());
    }
}
