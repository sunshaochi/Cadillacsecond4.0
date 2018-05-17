package com.ist.cadillacpaltform.UI.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.ist.cadillacpaltform.R;

import com.ist.cadillacpaltform.SDK.bean.Authorization;

import rx.Subscriber;

public class LoginActivity extends Activity {
    private EditText mEtAccount;
    private EditText mEtPassword;
    private Button mBtnLogin;
    private Subscriber<Authorization> mSubscriber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        initView();
    }

//    // 信鸽推送接收到推送后的处理
//    @Override
//    protected void onStart() {
//        super.onStart();
//        XGPushClickedResult clickedResult = XGPushManager.onActivityStarted(this);
//        Log.i("dong", "Login Activity: onStart()");
//        if(clickedResult != null){
//            String title = clickedResult.getTitle();
//            Log.v("TPush", "title:" + title);
//            String id = clickedResult.getMsgId() + "";
//            Log.v("TPush", "id:" + id);
//            String content = clickedResult.getContent();
//            Log.v("TPush", "content:" + content);
//            String customContent = clickedResult.getCustomContent();
//            Log.v("TPush", "custom content:" + customContent);
//
//            Gson gson = new Gson();
//            PushInfo info = gson.fromJson(customContent, PushInfo.class);
//            if (info.getType() == 1) {
//                Intent intent = new Intent(this, CarManagementDetailActivity.class);
//                intent.putExtra("pageType", 2);
//                intent.putExtra("orderId", info.getOrderId());
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//            } else {
//                Intent intent = new Intent (this, HomePageActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//            }
//        } else {
//            requestWritePermission();
//            SQLiteHelper helper = new SQLiteHelper();
//            Authorization auth = helper.getAuth();
//            if (auth != null) {
//                Log.i("dong", "auth isn't null");
//                Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//            }
//        }
//    }
//
//    private void initView() {
//        mEtAccount = (EditText) findViewById(R.id.et_account);
//        mEtPassword = (EditText) findViewById(R.id.et_password);
//        mBtnLogin = (Button) findViewById(R.id.btn_login);
//        mBtnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                login();
//            }
//        });
//    }
//
//    private void login() {
//        String username = mEtAccount.getText().toString();
//        String password = mEtPassword.getText().toString();
//        mSubscriber = new Subscriber<Authorization>() {
//            @Override
//            public void onCompleted() {
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Toast.makeText(LoginActivity.this, "网络错误，请稍后重试", Toast.LENGTH_SHORT).show();
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onNext(Authorization auth) {
//                if (auth.authorization == null) {
//                    Toast.makeText(LoginActivity.this, "用户名或密码错误，请重新输入", Toast.LENGTH_SHORT).show();
//                } else {
//                    // 将登录获得的认证信息存入本地文件
//                    requestWritePermission();
//                    SQLiteHelper helper = new SQLiteHelper();
//                    try {
//                        helper.setAuth(auth);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        Toast.makeText(LoginActivity.this, "您的手机存储无效，请联系应用提供者", Toast.LENGTH_SHORT).show();
//                    }
//
//                    auth = helper.getAuth();
//
//                    /* ----------------------下面为信鸽推送相关注册-------------------------------*/
//                    Context context = getApplicationContext();
//
//                    // 开启logcat输出，方便debug，发布时请关闭
//                    XGPushConfig.enableDebug(context, false);
//                    //注册方法
//                    XGPushManager.registerPush(context, auth.account, new XGIOperateCallback() {
//                        @Override
//                        public void onSuccess(Object data, int flag) {
//                            Log.v("TPush", "信鸽推送注册成功,Token值为：" + data);
//                        }
//
//                        @Override
//                        public void onFail(Object data, int errCode, String msg) {
//                            Log.v("TPush", "信鸽推送注册失败,错误码为：" + errCode + ",错误信息：" + msg);
//                        }
//                    });
//
//
//                    Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(intent);
//                }
//            }
//        };
//
//        Account account = new Account();
//        account.setUsername(username);
//        account.setPassword(password);
//        HighWarehouseAgeApi.getInstance().login(mSubscriber, account);
//    }
//
//    private void requestWritePermission () {
//        if (Build.VERSION.SDK_INT >= 23) {
//            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
//            if(checkCallPhonePermission != PackageManager.PERMISSION_GRANTED){
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},110);
//                return;
//            }
//        }
//    }
}
