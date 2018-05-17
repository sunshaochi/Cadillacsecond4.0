package com.ist.cadillacpaltform.SDK.bean.Posm;

import com.ist.cadillacpaltform.SDK.util.oss.OssService;

/**
 * Created by czh on 2017/3/27.
 */

public class FinalVal {
    public final static int ALBUM_REQUEST_CODE = 1;
    public final static int CAMERA_REQUEST_CODE = 3;
    public final static int CAMERA_PERMISSION_REQUEST = 11;
//    public final static String PIC_SUVER_URL = "http://posm.oss-cn-shanghai.aliyuncs.com/";
    public final static String PIC_SUVER_URL = "https://" + OssService.BUCKET + ".oss-cn-shanghai.aliyuncs.com/picture/"; // 这个地址请特别注意一下
}
