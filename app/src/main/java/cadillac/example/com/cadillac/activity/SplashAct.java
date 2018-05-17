package cadillac.example.com.cadillac.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.ist.cadillacpaltform.SDK.bean.HighStockAge.PushInfo;
import com.ist.cadillacpaltform.UI.activity.CarManagementDetailActivity;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushManager;

import cadillac.example.com.cadillac.MainActivity;
import cadillac.example.com.cadillac.R;
import cadillac.example.com.cadillac.base.BaseActivity;
import cadillac.example.com.cadillac.bean.UserModle;
import cadillac.example.com.cadillac.utils.CadillacUtils;
import cadillac.example.com.cadillac.utils.SpUtils;

/**
 * 闪屏页面
 * Created by bitch-1 on 2017/3/16.
 */

public class SplashAct extends BaseActivity {

    private static final int sleepTime = 2000;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_splash);

    }

    @Override
    public void init(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置窗体全屏
        SpUtils.setScreenWith(getApplicationContext(), CadillacUtils.getScreenWidth(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        XGPushClickedResult clickedResult = XGPushManager.onActivityStarted(SplashAct.this);
        Log.i("dong", "Login Activity: onStart()");
        if (clickedResult != null) {//有推送
            String title = clickedResult.getTitle();
            Log.v("TPush", "title:" + title);
            String id = clickedResult.getMsgId() + "";
            Log.v("TPush", "id:" + id);
            String content = clickedResult.getContent();
            Log.v("TPush", "content:" + content);
            String customContent = clickedResult.getCustomContent();
            Log.v("TPush", "custom content:" + customContent);
            //如果有返回的内容
            Gson gson = new Gson();
            PushInfo info = gson.fromJson(customContent, PushInfo.class);
            if (info != null) {
                if (info.getType() == 1) {
                    Intent intent = new Intent(SplashAct.this, CarManagementDetailActivity.class);
                    intent.putExtra("pageType", 2);
                    intent.putExtra("orderId", info.getOrderId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(SplashAct.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            } else {//这是我们服务的推送用来提示用户上传
                if (!TextUtils.isEmpty(CadillacUtils.getCurruser().getUserName())) {
                    Intent intent = new Intent(SplashAct.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(SplashAct.this, LoginAct.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
             finish();
        }else {//正常程序打开
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    finish();
                    openActivity(LoginAct.class);
                }
            }).start();
        }
    }
}
