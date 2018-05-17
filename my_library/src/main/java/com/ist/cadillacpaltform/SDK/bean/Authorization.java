package com.ist.cadillacpaltform.SDK.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dearlhd on 2016/12/27.
 */
public class Authorization {
    @SerializedName("authorization")
    public String authorization;

    @SerializedName("account")
    public String account;

    /**
     * 0. 经销商
     * 1. 小区经理(MAC)
     * 2. 大区经理
     * 3. 管理员
     * 4. rpc
     * 5. rpc*
     * 6. crpc
     * 7. 其他
     */
    @SerializedName("type")
    public int type;

    @SerializedName("userId")
    public long userId;
}
