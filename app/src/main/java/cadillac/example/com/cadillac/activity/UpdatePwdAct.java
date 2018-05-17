package cadillac.example.com.cadillac.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import cadillac.example.com.cadillac.AppManager;
import cadillac.example.com.cadillac.MyApplication;
import cadillac.example.com.cadillac.R;
import cadillac.example.com.cadillac.base.BaseActivity;
import cadillac.example.com.cadillac.bean.LoginBean;
import cadillac.example.com.cadillac.http.Manage.UserManager;
import cadillac.example.com.cadillac.http.ResultCallback;
import cadillac.example.com.cadillac.utils.CadillacUtils;
import cadillac.example.com.cadillac.utils.MyToastUtils;
import cadillac.example.com.cadillac.utils.SpUtils;
import cadillac.example.com.cadillac.view.Picktime.pickerview.TimePickerView;

/**
 * 修改密码
 * Created by bitch-1 on 2017/3/18.
 */
public class UpdatePwdAct extends BaseActivity {
    @ViewInject(R.id.et_pass)
    private EditText et_pass;

    @ViewInject(R.id.et_newpass)
    private EditText et_newpass;

    @ViewInject(R.id.iv_pclear)
    private ImageView iv_pclear;

    @ViewInject(R.id.iv_npclear)
    private ImageView iv_npclear;


    @ViewInject(R.id.iv_plook)
    private ImageView iv_plook;

    @ViewInject(R.id.iv_nplook)
    private ImageView iv_nplook;



    private String pass,newpass;

    private boolean isShowpass=false;
    private boolean isShownpass=false;


    @Override
    public void setLayout() {
        setContentView(R.layout.act_updatepwd);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("修改密码");

        et_pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!TextUtils.isEmpty(charSequence)){
                    iv_pclear.setVisibility(View.VISIBLE);
                }else {
                    iv_pclear.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!TextUtils.isEmpty(charSequence)){
                    iv_pclear.setVisibility(View.VISIBLE);
                }else {
                    iv_pclear.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!TextUtils.isEmpty(editable.toString())){
                    iv_pclear.setVisibility(View.VISIBLE);
                }else {
                    iv_pclear.setVisibility(View.INVISIBLE);
                }

            }
        });


        et_newpass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!TextUtils.isEmpty(charSequence)){
                    iv_npclear.setVisibility(View.VISIBLE);

                }else {
                    iv_npclear.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!TextUtils.isEmpty(charSequence)){
                    iv_npclear.setVisibility(View.VISIBLE);

                }else {
                    iv_npclear.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!TextUtils.isEmpty(editable.toString())){
                    iv_npclear.setVisibility(View.VISIBLE);

                }else {
                    iv_npclear.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @OnClick({R.id.tv_bc,R.id.iv_pclear,R.id.iv_npclear,R.id.iv_plook,R.id.iv_nplook})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.tv_bc:
                if(isValidate()){
                  Updatepass(CadillacUtils.getCurruser().getId()+"",pass,newpass);//修改密码
                }
                break;

            case R.id.iv_pclear:
                et_pass.setText("");
                break;

            case R.id.iv_npclear:
                et_newpass.setText("");
                break;

            case R.id.iv_plook:
                if(!isShowpass) {
                    isShowpass=true;
                    iv_plook.setImageResource(R.mipmap.pwd_show);
                    et_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    et_pass.setSelection(et_pass.getText().length());
                }else {
                    isShowpass=false;
                    iv_plook.setImageResource(R.mipmap.pwd_hid);
                    et_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    et_pass.setSelection(et_pass.getText().length());
                }
                break;

            case R.id.iv_nplook:
                if(!isShownpass) {
                    isShownpass=true;
                    iv_nplook.setImageResource(R.mipmap.pwd_show);
                    et_newpass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                    et_newpass.setSelection(et_newpass.getText().length());
                }else {
                    isShownpass=false;
                    iv_nplook.setImageResource(R.mipmap.pwd_hid);
                    et_newpass.setTransformationMethod(PasswordTransformationMethod.getInstance());

                    et_newpass.setSelection(et_newpass.getText().length());
                }
                break;

        }
    }

    private void Updatepass(String userid, String passwad, final String newpasswad) {
        UserManager.getUserManager().updatePwd(userid, passwad, newpasswad,new ResultCallback<String>() {
            @Override
            public void onError(int status, String errorMsg) {
             MyToastUtils.showShortToast(UpdatePwdAct.this,errorMsg);
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String code=jsonObject.getString("code");
                    String message=jsonObject.getString("message");
                    MyToastUtils.showShortToast(UpdatePwdAct.this,message);
                    if(code.equals("success")){
                        finish();
                        LoginBean loginBean=CadillacUtils.getCurruser();
                        loginBean.setPassword(newpasswad);
                        MyApplication.getInstances().getDaoSession().getLoginBeanDao().update(loginBean);
                    }else {

                    }
                } catch (JSONException e) {
                    MyToastUtils.showShortToast(UpdatePwdAct.this,"解析异常");
                    e.printStackTrace();
                }
            }
        });
    }

    private boolean isValidate() {
        pass = et_pass.getText().toString().trim();
        newpass = et_newpass.getText().toString().toString();
        if (TextUtils.isEmpty(pass)) {
            MyToastUtils.showShortToast(UpdatePwdAct.this,"请输入旧密码！");
            et_pass.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(newpass)) {
            MyToastUtils.showShortToast(UpdatePwdAct.this,"请输入新密码!");
            et_newpass.requestFocus();
            return false;
        }
        return true;
    }
}
