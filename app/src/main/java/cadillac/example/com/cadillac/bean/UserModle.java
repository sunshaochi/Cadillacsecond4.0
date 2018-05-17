package cadillac.example.com.cadillac.bean;

/**
 * Created by bitch-1 on 2017/3/30.
 */

public class UserModle {
    private static UserBean userBean =null;

    public static UserBean getUserBean() {
        return userBean;
    }

    public static void setUserBean(UserBean userBean) {
        UserModle.userBean = userBean;
    }
}
