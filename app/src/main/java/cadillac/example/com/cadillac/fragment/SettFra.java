package cadillac.example.com.cadillac.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ist.cadillacpaltform.SDK.bean.Authorization;
import com.ist.cadillacpaltform.SDK.util.SQLiteHelper;
import com.ist.cadillacpaltform.UI.activity.GradeDetailActivity;

import com.ist.cadillacpaltform.UI.activity.POSMHomeActivity;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushManager;

import java.util.Calendar;
import java.util.Map;

import cadillac.example.com.cadillac.MyApplication;
import cadillac.example.com.cadillac.R;
import cadillac.example.com.cadillac.activity.HelpAct;
import cadillac.example.com.cadillac.activity.LoginAct;
import cadillac.example.com.cadillac.activity.LookSqAct;
import cadillac.example.com.cadillac.activity.MyInfoAct;
import cadillac.example.com.cadillac.activity.MyloveAct;
import cadillac.example.com.cadillac.activity.UpdatePwdAct;
import cadillac.example.com.cadillac.base.BaseFrag;
import cadillac.example.com.cadillac.bean.UserModle;

import cadillac.example.com.cadillac.utils.CadillacUtils;
import cadillac.example.com.cadillac.utils.MyLogUtils;
import cadillac.example.com.cadillac.utils.SpUtils;
import cadillac.example.com.cadillac.view.MyAlertDialog;

/**设置fragment
 * Created by bitch-1 on 2017/3/16.
 */
public class SettFra extends BaseFrag {
    private String user;
    private String pass;
    @ViewInject(R.id.tv_sq)
    private TextView tv_sq;
    @ViewInject(R.id.view_lin1)
    private View view_lin1;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    private String role;//职务
    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.frg_sett,null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        tv_title.setText(CadillacUtils.getCurruser().getDealerName());
//        if(UserModle.getUserBean()!=null){
//            role=UserModle.getUserBean().getRole();
//        }
//        if(!TextUtils.isEmpty(role)){
//            if(role.contains("经销商")){
//                tv_sq.setVisibility(View.GONE);
//                view_lin1.setVisibility(View.GONE);
//            }else {
//                tv_sq.setVisibility(View.VISIBLE);
//                view_lin1.setVisibility(View.VISIBLE);
//            }
//        }
//        Map<String, String> params1 =SpUtils.getnameandpwd(getActivity());
//        user=params1.get("user");
//        pass=params1.get("pswd");
    }

    @OnClick({R.id.tv_myinfo,R.id.tv_updatepwd,R.id.tv_mylove,R.id.tv_loginout,R.id.tv_sq,R.id.ll_help,R.id.ll_posm})
     public void todo(View v){
        switch (v.getId()){
            case R.id.tv_myinfo://我的信息
                openActivity(MyInfoAct.class);

                break;
            case R.id.tv_updatepwd://修改密码
                openActivity(UpdatePwdAct.class);

                break;
            case R.id.tv_mylove://我的喜好
                openActivity(MyloveAct.class);
                break;
            case R.id.tv_sq://查看申请
                openActivity(LookSqAct.class);
                break;
            case R.id.tv_loginout://登出
                new MyAlertDialog(getActivity()).builder().setTitle("提示").setMsg("确认退出吗？")
                        .setPositiveButton("退出", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
//

//                                SpUtils.savenameandpwd(getActivity(),"","");//将用户名和密码设置为空
//                                UserModle.setUserBean(null);
                                SpUtils.setAcount(getActivity(),"");
                                SpUtils.setPwd(getActivity(),"");
                                SpUtils.clearSp(getActivity());
                                loginout();//退出高库龄
                                MyApplication.getInstances().getDaoSession().getLoginBeanDao().deleteAll();
//                                openActivity(LoginAct.class);
                                getActivity().finish();


                            }
                        }).setNegativeButton("取消",null ).show();

                break;

            case R.id.ll_help:
                openActivity(HelpAct.class);
                break;
            case R.id.ll_posm:
                SQLiteHelper helper = new SQLiteHelper();
                Authorization authorization = helper.getAuth();
                int type = authorization.type;
                if (type == 0) {
                    Intent intent = new Intent(getActivity(), GradeDetailActivity.class);
                    Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int quarter = (calendar.get(Calendar.MONTH)+1) / 4 + 1;
                    intent.putExtra("year", year);
                    intent.putExtra("quarter", quarter);
                    intent.putExtra("isRectify", true);
                    startActivity(intent);
                } else if (type == 1 || type == 2 || type == 3 || type == 4 || type == 5 || type == 6) {
                    Intent intent = new Intent(getActivity(), POSMHomeActivity.class);
                    startActivity(intent);
                }


                break;
        }

    }

    /**
     * 退出高库林
     */
    private void loginout() {
        SQLiteHelper helper = new SQLiteHelper();
        try {
            helper.setAuth(null);
            XGPushManager.registerPush(getActivity(), "*", new XGIOperateCallback() {
                @Override
                public void onSuccess(Object o, int i) {
                    MyLogUtils.info("解绑成功");
                }

                @Override
                public void onFail(Object o, int i, String s) {
                    MyLogUtils.info(i+s);

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(getContext(), LoginAct.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        getActivity().finish();
    }
}
