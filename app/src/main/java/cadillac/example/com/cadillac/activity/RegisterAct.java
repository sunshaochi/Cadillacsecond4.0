package cadillac.example.com.cadillac.activity;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cadillac.example.com.cadillac.R;
import cadillac.example.com.cadillac.base.BaseActivity;
import cadillac.example.com.cadillac.bean.RegistBean;
import cadillac.example.com.cadillac.bean.RegistNameBean;
import cadillac.example.com.cadillac.bean.RegistZwBean;
import cadillac.example.com.cadillac.bean.ResultsNewBean;
import cadillac.example.com.cadillac.http.Manage.UserManager;
import cadillac.example.com.cadillac.http.ResultCallback;
import cadillac.example.com.cadillac.utils.CadillacUtils;
import cadillac.example.com.cadillac.utils.MyLogUtils;
import cadillac.example.com.cadillac.utils.MyToastUtils;
import cadillac.example.com.cadillac.view.RegistDialog;

/**
 * 注册activity
 * Created by bitch-1 on 2017/3/28.
 */
public class RegisterAct extends BaseActivity implements RegistDialog.upDateUi {
    @ViewInject(R.id.et_name)//姓名
    private EditText et_name;
    @ViewInject(R.id.et_phone)//手机
    private EditText et_phone;
    @ViewInject(R.id.et_account)
    private EditText et_account;
    @ViewInject(R.id.ck_regist)
    private CheckBox ck_regist;//选择勾选
    @ViewInject(R.id.tv_qy)
    private TextView tv_qy;
    @ViewInject(R.id.tv_mc)
    private TextView tv_mc;
    @ViewInject(R.id.tv_zw)
    private TextView tv_zw;
    private boolean check;//用来判断是否选中


    private String  name, phone,account,qy,mc,zw;
    private String code;
    private String type;
    private List<String>list=new ArrayList<>();

    private List<RegistBean>dqlist;
    private List<RegistNameBean>namelist;
    private List<RegistZwBean>zwlist;
    private String dearid,roleid;
    private String zwcode;

    @Override
    public void setLayout() {
        setContentView(R.layout.act_register);

    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("注册");
        ck_regist.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    //被选中
                    check = true;
                } else {
                    //没被选中
                    check = false;
                }

            }
        });



    }


    @OnClick({R.id.tv_regist,R.id.ll_qy,R.id.ll_mc,R.id.ll_zw})
    public void OnClick(View v) {
        switch (v.getId()) {

            case R.id.tv_regist:
                if (isValidate()) {
                  saveRegist(dearid,roleid,account,name,phone,"Android 11.00");//注册
                }
                break;

            case R.id.ll_qy://获取区域
                getQyDate();
                break;

            case R.id.ll_mc://获取名称
                if(tv_qy.getText().toString().equals("选择所属区域")){
                    MyToastUtils.showShortToast(RegisterAct.this,"请先选择所属区域");
                }else {
                    getJxsName(code);
                }
                break;

            case R.id.ll_zw:
                getRole();//获取职务
                break;

        }
    }

    /**
     * 注册
     */
    private void saveRegist(String dealerId,String roleId,String userName,String personName,String mobileNo,String loginVerson) {
     UserManager.getUserManager().toRegistUse(dealerId, roleId, userName, personName, mobileNo, loginVerson, new ResultCallback<String>() {
         @Override
         public void onError(int status, String errorMsg) {
          MyToastUtils.showShortToast(RegisterAct.this,errorMsg);
         }

         @Override
         public void onResponse(String response) {
             try {
                 JSONObject jsonObject=new JSONObject(response);
                 String code=jsonObject.getString("code");
                 String message=jsonObject.getString("message");
                 MyToastUtils.showShortToast(RegisterAct.this,message);
                 if(code.equals("success")){
                     finish();
                 }
             } catch (JSONException e) {
                 e.printStackTrace();
             }

         }
     });
    }

    /**
     * 获取职务
     */
    private void getRole() {
        UserManager.getUserManager().getZhiwulist(new ResultCallback<ResultsNewBean<List<RegistZwBean>>>() {
            @Override
            public void onError(int status, String errorMsg) {
             MyToastUtils.showShortToast(RegisterAct.this,errorMsg);
            }

            @Override
            public void onResponse(ResultsNewBean<List<RegistZwBean>> response) {
            zwlist=response.getObj();
            type="2";
            if(zwlist.size()>0&&zwlist!=null){
                list.clear();
                for(int i=0;i<zwlist.size();i++){
                    list.add(zwlist.get(i).getName());
                }
                showReDialog(type,list);
            }
            }
        });
    }

    /**
     * 获取经销商名称
     */
    private void getJxsName(String code) {
        UserManager.getUserManager().getJxsNamelist(code, new ResultCallback<ResultsNewBean<List<RegistNameBean>>>() {
            @Override
            public void onError(int status, String errorMsg) {
               MyToastUtils.showShortToast(RegisterAct.this,errorMsg);
            }

            @Override
            public void onResponse(ResultsNewBean<List<RegistNameBean>> response) {
             namelist=response.getObj();
             if(namelist!=null&&namelist.size()>0){
                 type="1";
                 list.clear();
                 for(int i=0;i<namelist.size();i++){
                     list.add(namelist.get(i).getName());
                 }
                 showReDialog(type,list);
             }
            }
        });
    }

    /**
     * 获取区域的选择列表
     */
    private void getQyDate() {
        UserManager.getUserManager().getQylist(new ResultCallback<ResultsNewBean<List<RegistBean>>>() {
            @Override
            public void onError(int status, String errorMsg) {
                MyToastUtils.showShortToast(RegisterAct.this,errorMsg);
            }

            @Override
            public void onResponse(ResultsNewBean<List<RegistBean>> response) {
             dqlist=response.getObj();
             list.clear();
             if(dqlist!=null&&dqlist.size()>0){
                 type="0";
                 for(int i=0;i<dqlist.size();i++){
                     list.add(dqlist.get(i).getName());
                 }
                 showReDialog(type,list);
             }
            }
        });

    }

    private void showReDialog(String type,List<String>list) {
        RegistDialog dialog=new RegistDialog(RegisterAct.this,list,type).buidler();
        dialog.show();
    }


    /**
     * 判断调取注册的条件
     *
     * @return
     */
    private boolean isValidate() {
        name = et_name.getText().toString().trim();
        phone = et_phone.getText().toString().trim();
        account=et_account.getText().toString().trim();
        qy=tv_qy.getText().toString();
        mc=tv_mc.getText().toString();
        zw=tv_zw.getText().toString();
        if (TextUtils.isEmpty(name)) {
            MyToastUtils.showShortToast(RegisterAct.this, "请输入姓名！");
            et_name.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(phone)) {
            MyToastUtils.showShortToast(RegisterAct.this, "请输入手机号!");
            et_phone.requestFocus();
            return false;
        }

        if (phone.length() != 11) {
            MyToastUtils.showShortToast(RegisterAct.this,"请输入正确的手机号码格式！");
            et_phone.requestFocus();
            et_phone.setSelection(et_phone.length());
            return false;
        }
        if (!CadillacUtils.checkCellPhone(phone)) {
            MyToastUtils.showShortToast(RegisterAct.this,"请输入正确的手机号码格式！");
            return false;
        }

        if(TextUtils.isEmpty(account)){
            MyToastUtils.showShortToast(RegisterAct.this, "请输入用户名!");
            return false;
        }

        if (!check) {
            MyToastUtils.showShortToast(RegisterAct.this, "请阅读协议!");
            return false;
        }
        if(qy.equals("选择所属区域")){
            MyToastUtils.showShortToast(RegisterAct.this, "请选择所属区域");
            return false;
        }

        if(mc.equals("选择经销商名称")){
            MyToastUtils.showShortToast(RegisterAct.this, "请选择经销商名称");
            return false;
        }

        if(zw.equals("选择职务")){
            MyToastUtils.showShortToast(RegisterAct.this, "请选择职务");
            return false;
        }
        return true;
    }


    @Override
    public void updateui(int info) {
        if(type.equals("0")) {
            tv_qy.setText(dqlist.get(info).getName());
            code = dqlist.get(info).getCode() + "";
            hideBottem("0");
        }else if(type.equals("1")){
            dearid=namelist.get(info).getId()+"";
            tv_mc.setText(namelist.get(info).getName());
            zwcode=namelist.get(info).getCode();
            hideBottem("1");
        }else if(type.equals("2")){
            roleid=zwlist.get(info).getId()+"";
            tv_zw.setText(zwlist.get(info).getName());
            if(zwlist.get(info).getCode().equals("00007")){
                et_account.setText(zwcode+"GM");
            }else if(zwlist.get(info).getCode().equals("00001")){
                et_account.setText(zwcode+"MM");
            }else if(zwlist.get(info).getCode().equals("00008")){
                et_account.setText(zwcode+"SM");
            }
        }
    }

    /**
     * 当大区被换掉时候下面全部换掉
     */
    private void hideBottem(String rotype) {
        if(rotype.equals("0")) {
            dearid = "";
            tv_mc.setText("选择经销商名称");
            zwcode = "";
            roleid = "";
            tv_zw.setText("选择职务");
            et_account.setText("");
        }else if(rotype.equals("1")){
            roleid = "";
            tv_zw.setText("选择职务");
            et_account.setText("");
        }
    }
}
