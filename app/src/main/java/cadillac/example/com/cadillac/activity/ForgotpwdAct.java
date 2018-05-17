package cadillac.example.com.cadillac.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONException;
import org.json.JSONObject;

import cadillac.example.com.cadillac.R;
import cadillac.example.com.cadillac.base.BaseActivity;
import cadillac.example.com.cadillac.http.Manage.UserManager;
import cadillac.example.com.cadillac.http.ResultCallback;
import cadillac.example.com.cadillac.utils.CadillacUtils;
import cadillac.example.com.cadillac.utils.MyToastUtils;

/**
 * 忘记密码
 * Created by bitch-1 on 2017/3/30.
 */
public class ForgotpwdAct extends BaseActivity {
    @ViewInject(R.id.et_username)
    private EditText et_username;
    @ViewInject(R.id.et_phone)
    private EditText et_phone;

    private String username,phone;//用户名，手机号
    @Override
    public void setLayout() {
        setContentView(R.layout.act_forgotpwd);
    }

    @Override
    public void init(Bundle savedInstanceState) {
     setTopTitle("忘记密码");
    }
    @OnClick({R.id.tv_bc})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.tv_bc:
                if(isValidate()){
                    findpwd(username,phone);//找回密码
                }
                break;
        }
    }

    private void findpwd(String op,String obj) {
        UserManager.getUserManager().findPwd(op,obj,new ResultCallback<String>() {
            @Override
            public void onError(int status, String errorMsg) {
              MyToastUtils.showShortToast(ForgotpwdAct.this,errorMsg);
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String code=jsonObject.getString("code");
                    String message=jsonObject.getString("message");
                    MyToastUtils.showShortToast(ForgotpwdAct.this,message);
                    if(code.equals("success")){
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private boolean isValidate() {
        username = et_username.getText().toString().trim();
        phone = et_phone.getText().toString().toString();
        if (TextUtils.isEmpty(username)) {
            MyToastUtils.showShortToast(ForgotpwdAct.this,"请输入用户名！");
            et_username.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(phone)) {
            MyToastUtils.showShortToast(ForgotpwdAct.this,"请输入手机号!");
            et_phone.requestFocus();
            return false;
        }
        if(!CadillacUtils.checkCellPhone(phone)){
            MyToastUtils.showShortToast(ForgotpwdAct.this,"请输入正确手机格式!");
            et_phone.requestFocus();
            return false;

        }
        return true;
    }
}
