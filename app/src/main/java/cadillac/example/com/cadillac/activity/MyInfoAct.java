package cadillac.example.com.cadillac.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import cadillac.example.com.cadillac.MyApplication;
import cadillac.example.com.cadillac.R;
import cadillac.example.com.cadillac.base.BaseActivity;
import cadillac.example.com.cadillac.bean.LoginBean;
import cadillac.example.com.cadillac.bean.UserBean;
import cadillac.example.com.cadillac.bean.UserModle;
import cadillac.example.com.cadillac.http.Manage.UserManager;
import cadillac.example.com.cadillac.http.ResultCallback;
import cadillac.example.com.cadillac.utils.CadillacUtils;
import cadillac.example.com.cadillac.utils.MyToastUtils;
import cadillac.example.com.cadillac.utils.SpUtils;

/**
 * 我的信息界面
 * Created by bitch-1 on 2017/3/18.
 */
public class MyInfoAct extends BaseActivity {
    @ViewInject(R.id.tv_username)
    private TextView tv_username;
    @ViewInject(R.id.et_name)
    private TextView et_name;
    @ViewInject(R.id.tv_zhiwu)
    private TextView tv_zhiwu;
    @ViewInject(R.id.et_phone)
    private EditText et_phone;
    private UserBean userbean;
    private String phone, personName;

    @Override
    public void setLayout() {
        setContentView(R.layout.act_myinfo);

    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("我的信息");
        setRight("保存", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidate()) {
                    UpdateInfo(CadillacUtils.getCurruser().getUserName(), phone, CadillacUtils.getCurruser().getMobileNo(), CadillacUtils.getCurruser().getPersonName(), personName);//修改信息
                }
//                MyToastUtils.showShortToast(MyInfoAct.this,"点击了");
//                finish();
            }
        });

        tv_username.setText(CadillacUtils.getCurruser().getUserName());
        et_name.setText(CadillacUtils.getCurruser().getPersonName());
        et_phone.setText(CadillacUtils.getCurruser().getMobileNo());
        tv_zhiwu.setText(CadillacUtils.getCurruser().getRoleName());
        et_phone.setSelection(et_phone.length());

    }


    private void UpdateInfo(String userName, String newMobileNo, String mobileNo, String PersonName, String newPersonName) {
        UserManager.getUserManager().updateinfo(userName, newMobileNo, mobileNo, PersonName, newPersonName, new ResultCallback<String>() {
            @Override
            public void onError(int status, String errorMsg) {
                MyToastUtils.showShortToast(MyInfoAct.this, errorMsg);
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.getString("code");
                    String message = jsonObject.getString("message");
                    if (code.equals("success")) {
                        JSONObject json = jsonObject.getJSONObject("obj");
                        String personName = json.getString("personName");
                        String mobileNo = json.getString("mobileNo");
                        LoginBean loginBean = CadillacUtils.getCurruser();

                        loginBean.setPersonName(personName);
                        loginBean.setMobileNo(mobileNo);
                        MyApplication.getInstances().getDaoSession().getLoginBeanDao().update(loginBean);
                        MyToastUtils.showShortToast(MyInfoAct.this,"保存成功");
                        finish();
                    } else {

                        MyToastUtils.showShortToast(MyInfoAct.this, message);
                    }
                } catch (JSONException e) {
                    MyToastUtils.showShortToast(MyInfoAct.this, "解析异常");
                    e.printStackTrace();
                }

            }
        });
    }

    private boolean isValidate() {
        phone = et_phone.getText().toString().trim();
        personName = et_name.getText().toString().trim();
        if (TextUtils.isEmpty(personName)) {
            MyToastUtils.showShortToast(MyInfoAct.this, "请输入姓名");
            et_name.requestFocus();
            return false;
        }
        if (phone.length() < 11) {
            MyToastUtils.showShortToast(MyInfoAct.this, "请正确手机号");
            et_phone.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(phone)) {
            MyToastUtils.showShortToast(MyInfoAct.this, "请输入手机号！");
            et_phone.requestFocus();
            return false;
        }

//        if(CadillacUtils.checkCellPhone(phone)){
//            et_phone.requestFocus();
//            return false;
//        }

        return true;
    }
}
