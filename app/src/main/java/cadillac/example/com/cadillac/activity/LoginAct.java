package cadillac.example.com.cadillac.activity;


import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ist.cadillacpaltform.SDK.bean.Account;
import com.ist.cadillacpaltform.SDK.bean.Authorization;
import com.ist.cadillacpaltform.SDK.bean.HighStockAge.PushInfo;
import com.ist.cadillacpaltform.SDK.network.HighWarehouseAgeApi;
import com.ist.cadillacpaltform.SDK.util.SQLiteHelper;
import com.ist.cadillacpaltform.UI.activity.CarManagementDetailActivity;
import com.ist.cadillacpaltform.UI.activity.HomePageActivity;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;


import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import cadillac.example.com.cadillac.MainActivity;
import cadillac.example.com.cadillac.MyApplication;
import cadillac.example.com.cadillac.R;
import cadillac.example.com.cadillac.base.BaseActivity;
import cadillac.example.com.cadillac.bean.LoginBean;
import cadillac.example.com.cadillac.bean.ResultsBean;
import cadillac.example.com.cadillac.bean.ResultsNewBean;
import cadillac.example.com.cadillac.bean.UserBean;
import cadillac.example.com.cadillac.bean.UserInfoBean;
import cadillac.example.com.cadillac.bean.UserModle;
import cadillac.example.com.cadillac.http.Manage.UserManager;
import cadillac.example.com.cadillac.http.ResultCallback;
import cadillac.example.com.cadillac.utils.CadillacUtils;
import cadillac.example.com.cadillac.utils.GeneralUtils;
import cadillac.example.com.cadillac.utils.GsonUtils;
import cadillac.example.com.cadillac.utils.MyLogUtils;
import cadillac.example.com.cadillac.utils.MyToastUtils;
import cadillac.example.com.cadillac.utils.SpUtils;
import cadillac.example.com.cadillac.view.CProgressDialog;
import rx.Subscriber;

/**登录界面
 * Created by bitch-1 on 2017/3/16.
 */

public class LoginAct extends BaseActivity {
    @ViewInject(R.id.et_account)
    private EditText et_account;
    @ViewInject(R.id.et_password)
    private EditText et_password;
    @ViewInject(R.id.iv_uselear)
    private ImageView iv_uselear;

    @ViewInject(R.id.iv_pwdlear)
    private ImageView iv_pwdlear;

    private LoginBean loginbean;

    private String android_id;//设备id
    private String ver;//版本号
    private String user, pass;//用户，密码
    private UserBean userbean;
    private Gson gson;
    private String info;//返回的版本更新
    private GeneralUtils genralUtils;

    private Subscriber<Authorization> mSubscriber;//高库龄里面用到的
    public static boolean isshow;//用来给点击推送的时候总是跳到mainactivity

    private Dialog dialog;

    @Override
    public void setLayout() {
        setContentView(R.layout.act_login);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        // 将登录获得的认证信息存入本地文件
        requestWritePermission();
        gson = new Gson();

        android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);//获取设备id;
        getVersonId();//获取版本号
        getUserpwd();//获取保存在本地的登录名和密码


        et_account.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!TextUtils.isEmpty(charSequence)){
                    iv_uselear.setVisibility(View.VISIBLE);
                }else {
                    iv_uselear.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!TextUtils.isEmpty(charSequence)){
                    iv_uselear.setVisibility(View.VISIBLE);
                }else {
                    iv_uselear.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!TextUtils.isEmpty(editable.toString())){
                    iv_uselear.setVisibility(View.VISIBLE);
                }else {
                    iv_uselear.setVisibility(View.INVISIBLE);
                }

            }
        });


        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!TextUtils.isEmpty(charSequence)){
                    iv_pwdlear.setVisibility(View.VISIBLE);

                }else {
                    iv_pwdlear.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!TextUtils.isEmpty(charSequence)){
                    iv_pwdlear.setVisibility(View.VISIBLE);

                }else {
                    iv_pwdlear.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!TextUtils.isEmpty(editable.toString())){
                    iv_pwdlear.setVisibility(View.VISIBLE);

                }else {
                    iv_pwdlear.setVisibility(View.INVISIBLE);
                }
            }
        });

    }

    /**
     * 获取版本号
     */
    private void getVersonId() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            ver = info.versionCode + "";
            MyLogUtils.info("版本号" + ver);
            genralUtils=new GeneralUtils();
        } catch (Exception e) {
            e.printStackTrace();

            Toast.makeText(this, "版本号未知", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 获取保存在本地的用户
     */
    private void getUserpwd() {
            user=SpUtils.getAcount(LoginAct.this);
            pass=SpUtils.getPwd(LoginAct.this);
            et_account.setText(user);
            et_password.setText(pass);
            if(!TextUtils.isEmpty(user)){
                iv_uselear.setVisibility(View.VISIBLE);
            }else {
                iv_uselear.setVisibility(View.INVISIBLE);
            }
            if(!TextUtils.isEmpty(pass)){
            iv_pwdlear.setVisibility(View.VISIBLE);
            }else {
            iv_pwdlear.setVisibility(View.INVISIBLE);
             }

            if(!TextUtils.isEmpty(user)&&!TextUtils.isEmpty(pass)){
                Login(user, pass);//登录
            }




    }

    @OnClick({R.id.btn_login, R.id.tv_register, R.id.tv_findPassword,R.id.textView2,R.id.iv_uselear,R.id.iv_pwdlear})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                if (isValidate()) {
                    Login(user, pass);//登录
                }

                break;
            case R.id.tv_register://注册
                openActivity(RegisterAct.class);

                break;
            case R.id.tv_findPassword://忘记密码
                openActivity(ForgotpwdAct.class);
                break;
            case R.id.textView2://拨打电话
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:4007285660"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.iv_pwdlear:
                et_password.setText("");
                break;

            case R.id.iv_uselear:
                et_account.setText("");
                break;
        }
    }


    /**
     * @param userName
     * @param password
     */
    private void Login(final String userName, final String password) {
        dialog = CProgressDialog.createLoadingDialog(LoginAct.this,false);
        dialog.show();
        UserManager.getUserManager().toLogin(userName, password, new ResultCallback<ResultsNewBean<UserInfoBean>>() {
            @Override
            public void onError(int status, String errorMsg) {
                MyToastUtils.showShortToast(LoginAct.this,errorMsg);
                dialog.dismiss();
            }

            @Override
            public void onResponse( ResultsNewBean<UserInfoBean> response) {

                SpUtils.setAcount(LoginAct.this,userName);
                SpUtils.setPwd(LoginAct.this,password);
                loginbean=response.getObj().getUserWeb();
                if(TextUtils.isEmpty(loginbean.getEditData())){
                    loginbean.setEditData("N");
                }
                toLoginGao(userName,password);//登录稿库龄


//                openActivity(MainActivity.class);
//                finish();
            }
        });

    }

        private void toLoginGao(String aa, String bb) {
        final String username = aa;
        String password = bb;
        mSubscriber = new Subscriber<Authorization>() {
            @Override
            public void onCompleted() {
                dialog.dismiss();
            }

            @Override
            public void onError(Throwable e) {
                dialog.dismiss();
                Toast.makeText(LoginAct.this, "网络错误，请稍后重试", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

            @Override
            public void onNext(Authorization auth) {
                dialog.dismiss();
                if (auth.authorization == null) {
                    Toast.makeText(LoginAct.this, "用户名或密码错误，请重新输入", Toast.LENGTH_SHORT).show();
                } else {
                    MyApplication.getInstances().getDaoSession().getLoginBeanDao().insert(loginbean);//我们这边的保存
                    SQLiteHelper helper = new SQLiteHelper();
                    try {
                        helper.setAuth(auth);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(LoginAct.this, "您的手机存储无效，请联系应用提供者", Toast.LENGTH_SHORT).show();
                    }

                    auth = helper.getAuth();
                    MyLogUtils.info("高库龄返回"+ GsonUtils.bean2Json(auth));

                    /* ----------------------下面为信鸽推送相关注册-------------------------------*/

                    Context context = getApplicationContext();



                    // 开启logcat输出，方便debug，发布时请关闭
                    XGPushConfig.enableDebug(context, false);
                    final String acc=auth.account;
//                    MyLogUtils.info("注册"+account);
                    //注册方法
//                    if(!TextUtils.isEmpty(acc)) {
//                        XGPushManager.registerPush(context, acc, new XGIOperateCallback() {
//                            @Override
//                            public void onSuccess(Object data, int flag) {
//                                MyLogUtils.info("注册成功token值"+data.toString()+acc);
//                            }
//
//                            @Override
//                            public void onFail(Object data, int errCode, String msg) {
//                                Log.v("TPush", "信鸽推送注册失败,错误码为：" + errCode + ",错误信息：" + msg);
//                            }
//                        });
//
//                    }

                    XGPushManager.registerPush(context, username, new XGIOperateCallback() {
                        @Override
                        public void onSuccess(Object data, int flag) {
                            MyLogUtils.info("注册成功token值"+data.toString()+acc);
                        }

                        @Override
                        public void onFail(Object data, int errCode, String msg) {
                            Log.v("TPush", "信鸽推送注册失败,错误码为：" + errCode + ",错误信息：" + msg);
                        }
                    });



                    Intent intent = new Intent(LoginAct.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                    finish();
                }
            }
        };

        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        HighWarehouseAgeApi.getInstance().login(mSubscriber, account);
    }












    private boolean isValidate() {
        user = et_account.getText().toString().trim();
        pass = et_password.getText().toString().toString();
        if (TextUtils.isEmpty(user)) {
            MyToastUtils.showShortToast(LoginAct.this, "请输入用户名！");
            et_account.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(pass)) {
            MyToastUtils.showShortToast(LoginAct.this, "请输入密码!");
            et_password.requestFocus();
            return false;
        }
        return true;
    }

    private void requestWritePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 110);
                return;
            }
        }
    }


}





