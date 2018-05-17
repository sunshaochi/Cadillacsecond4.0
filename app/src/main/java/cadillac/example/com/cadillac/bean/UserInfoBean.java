package cadillac.example.com.cadillac.bean;

import com.ist.cadillacpaltform.SDK.bean.Authorization;

/**
 * Created by iris on 2018/1/26.
 */

public class UserInfoBean {
    private LoginBean userWeb;
    private Authorization userCarPlatform;

    public LoginBean getUserWeb() {
        return userWeb;
    }

    public void setUserWeb(LoginBean userWeb) {
        this.userWeb = userWeb;
    }

    public Authorization getUserCarPlatform() {
        return userCarPlatform;
    }

    public void setUserCarPlatform(Authorization userCarPlatform) {
        this.userCarPlatform = userCarPlatform;
    }
}
