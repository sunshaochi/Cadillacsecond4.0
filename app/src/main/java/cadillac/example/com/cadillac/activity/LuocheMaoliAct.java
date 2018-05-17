package cadillac.example.com.cadillac.activity;


import android.app.Dialog;
import android.os.Bundle;

import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import android.widget.TextView;


import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import cadillac.example.com.cadillac.R;
import cadillac.example.com.cadillac.base.BaseActivity;

import cadillac.example.com.cadillac.fragment.LmHbFra;
import cadillac.example.com.cadillac.fragment.LmJdFra;
import cadillac.example.com.cadillac.fragment.LmQsFra;
import cadillac.example.com.cadillac.fragment.LmTbFra;
import cadillac.example.com.cadillac.fragment.NewLmHbFra;
import cadillac.example.com.cadillac.fragment.NewLmJdFra;
import cadillac.example.com.cadillac.fragment.NewLmTbFra;
import cadillac.example.com.cadillac.http.Manage.UserManager;
import cadillac.example.com.cadillac.http.ResultCallback;
import cadillac.example.com.cadillac.utils.MyToastUtils;
import cadillac.example.com.cadillac.utils.SpUtils;
import cadillac.example.com.cadillac.view.CProgressDialog;


/**
 * Created by bitch-1 on 2017/5/26.
 */

public class LuocheMaoliAct extends BaseActivity {

    @ViewInject(R.id.rb_jd)
    private RadioButton rb_jd;//绝对值
    @ViewInject(R.id.rg_main)
    private RadioGroup rg_main;
    @ViewInject(R.id.tv_time)
    private TextView tv_time;


    private FragmentManager fm;
    private NewLmJdFra jdfra;//绝对
    private NewLmHbFra hbfra;//环比
    private NewLmTbFra tbfra;//同比
    private LmQsFra qsfra;//趋势图



    private String dex;
    private Dialog dialog;



    @Override
    public void setLayout() {
      setContentView(R.layout.act_luoche);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("裸车/综合毛利");

        fm=getSupportFragmentManager();
        getInputTime("","");//获取时间

        rg_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (radioGroup.getCheckedRadioButtonId()){
                    case R.id.rb_jd://绝对值
                        showFrag(0);
                        break;
                    case R.id.rb_hb://环比值
                        showFrag(1);
                        break;
                    case R.id.rb_tb://同比变化值
                        showFrag(2);
                        break;
                    case R.id.rb_qs://趋势值
                        showFrag(3);
                        break;
                }
            }
        });

    }

    /**
     * 获取inputtineid
     */
    private void getInputTime(final String id, String type) {
        dialog = CProgressDialog.createLoadingDialog(LuocheMaoliAct.this,false);
        dialog.show();
        UserManager.getUserManager().getInPutTime(id, type, new ResultCallback<String>() {
            @Override
            public void onError(int status, String errorMsg) {
                dialog.dismiss();
                MyToastUtils.showShortToast(LuocheMaoliAct.this,errorMsg);
            }

            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                try {
                    JSONObject object=new JSONObject(response);
                    String code=object.getString("code");
                    String message=object.getString("message");
                    if(code.equals("success")){
                        JSONObject obj=object.getJSONObject("obj");
                        JSONArray timeList=obj.getJSONArray("timeList");
                        JSONObject jsonObject= (JSONObject) timeList.get(0);
                        String inputTimeId=jsonObject.getString("inputTimeId");
                        String endtime=jsonObject.getString("endTime");
                        String dateId=obj.getString("dateId");
                        tv_time.setText(endtime);

                        SpUtils.setInputTimeId(LuocheMaoliAct.this,inputTimeId);
                        SpUtils.setDateId(LuocheMaoliAct.this,dateId);

                        rb_jd.setChecked(true);
                        showFrag(0);//默认显示第一个fragment
                        setRightimage(R.mipmap.uninputicon, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                 Bundle bundle=new Bundle();
                                 bundle.putString("inputtimeid",SpUtils.getInputTimeId(LuocheMaoliAct.this));
                                 bundle.putString("inputtype","2");
                                 openActivity(UninPutAct.class,bundle);

                            }
                        });
                    }else {
                        MyToastUtils.showShortToast(LuocheMaoliAct.this,message);
                    }

                } catch (JSONException e) {
                    MyToastUtils.showShortToast(LuocheMaoliAct.this,"解析异常");
                    e.printStackTrace();
                }


            }
        });
    }

    /**
     * 显示fragment
     * @param i
     */
    private void showFrag(int i) {
        FragmentTransaction transaction=fm.beginTransaction();
        hideFragment(transaction);//影长fragment

        switch (i){
            case 0:
                dex="0";
                if(jdfra==null){
//                    jdfra=new LmJdFra();
                    jdfra=new NewLmJdFra();
                    transaction.add(R.id.fl_lcml,jdfra);
                }else {
                    transaction.show(jdfra);
                }

                break;

            case 1:
                dex="1";
                if(hbfra==null){
//                    hbfra=new LmHbFra();
                    hbfra=new NewLmHbFra();
                    transaction.add(R.id.fl_lcml,hbfra);
                }else {
                    transaction.show(hbfra);
                }
                break;

            case 2:
                dex="2";
                if(tbfra==null){
//                    tbfra=new LmTbFra();
                    tbfra=new NewLmTbFra();
                    transaction.add(R.id.fl_lcml,tbfra);
                }else {
                    transaction.show(tbfra);
                }
                break;

            case 3:
                dex="3";
                if(qsfra==null){
                    qsfra=new LmQsFra();
                    transaction.add(R.id.fl_lcml,qsfra);
                }else {
                    transaction.show(qsfra);
                }
                break;
        }
        transaction.commit();
    }

    /**
     * 隐藏fragment
     * @param transaction
     */
    private void hideFragment(FragmentTransaction transaction) {
        if(jdfra!=null){
            transaction.hide(jdfra);
        }
        if(tbfra!=null){
            transaction.hide(tbfra);
        }
        if(hbfra!=null){
            transaction.hide(hbfra);
        }
        if(qsfra!=null){
            transaction.hide(qsfra);
        }
    }














@OnClick({R.id.rl_arrowleft,R.id.rl_arrowright})
public void OnClick(View v){
    switch (v.getId()){
        case R.id.rl_arrowleft://往左边调时间
            getSelectInputTime(SpUtils.getInputTimeId(LuocheMaoliAct.this),"0");
            break;
        case R.id.rl_arrowright://往右边
            getSelectInputTime(SpUtils.getInputTimeId(LuocheMaoliAct.this),"1");
            break;


    }
}

    /**
     * 切换左右时间
     * @param id
     * @param s
     */
    private void getSelectInputTime(String id, String s) {
        dialog = CProgressDialog.createLoadingDialog(LuocheMaoliAct.this,false);
        dialog.show();
        UserManager.getUserManager().getInPutTime(id, s, new ResultCallback<String>() {
            @Override
            public void onError(int status, String errorMsg) {
             dialog.dismiss();
             MyToastUtils.showShortToast(LuocheMaoliAct.this,errorMsg);
            }

            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                try {
                    JSONObject object=new JSONObject(response);
                    String code=object.getString("code");
                    String message=object.getString("message");
                    if(code.equals("success")){
                        JSONObject obj=object.getJSONObject("obj");
                        JSONArray timeList=obj.getJSONArray("timeList");
                        JSONObject jsonObject= (JSONObject) timeList.get(0);
                        String inputTimeId=jsonObject.getString("inputTimeId");
                        String endtime=jsonObject.getString("endTime");
                        String dateId=obj.getString("dateId");
                        tv_time.setText(endtime);

                        SpUtils.setInputTimeId(LuocheMaoliAct.this,inputTimeId);
                        SpUtils.setDateId(LuocheMaoliAct.this,dateId);

                        if(dex.equals("0")){
                            jdfra.upDate();
                        }else if(dex.equals("1")){
                            hbfra.upDate();
                        }else if(dex.equals("2")){
                            tbfra.upDate();
                        }else if(dex.equals("3")){
                            qsfra.upDate();
                        }

                    }else {
                        MyToastUtils.showShortToast(LuocheMaoliAct.this,message);
                    }

                } catch (JSONException e) {
                    MyToastUtils.showShortToast(LuocheMaoliAct.this,"解析异常");
                    e.printStackTrace();
                }


            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        SpUtils.setInputTimeId(LuocheMaoliAct.this,"");
        SpUtils.setDateId(LuocheMaoliAct.this,"");
    }
}
