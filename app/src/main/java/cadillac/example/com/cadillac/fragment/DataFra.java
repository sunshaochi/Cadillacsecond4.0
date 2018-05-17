package cadillac.example.com.cadillac.fragment;


import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cadillac.example.com.cadillac.MyApplication;
import cadillac.example.com.cadillac.R;

import cadillac.example.com.cadillac.adapter.LvztAdapter;
import cadillac.example.com.cadillac.base.BaseFrag;
import cadillac.example.com.cadillac.bean.JisuBean;
import cadillac.example.com.cadillac.bean.LoadBean;
import cadillac.example.com.cadillac.bean.ResultsNewBean;
import cadillac.example.com.cadillac.bean.SalseDateBean;
import cadillac.example.com.cadillac.http.Manage.UserManager;
import cadillac.example.com.cadillac.http.ResultCallback;
import cadillac.example.com.cadillac.utils.CadillacUtils;
import cadillac.example.com.cadillac.utils.DateAndTimeUtils;
import cadillac.example.com.cadillac.utils.MyLogUtils;
import cadillac.example.com.cadillac.utils.MyToastUtils;
import cadillac.example.com.cadillac.view.CKITaskSETimeScrollView;
import cadillac.example.com.cadillac.view.CProgressDialog;
import cadillac.example.com.cadillac.view.MyAlertDialog;


/**
 * 经销商销售经理的数据界面
 * Created by bitch-1 on 2017/3/16.
 */
public class DataFra extends BaseFrag {
    @ViewInject(R.id.ck)
    private CKITaskSETimeScrollView ckiTaskSETimeScrollView;//展厅listview显示数据
    @ViewInject(R.id.tv_uptime)
    private TextView tv_uptime;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    private LvztAdapter adapter;
    private ShowNewFra show;
    List<LoadBean> list;


    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.frg_data,null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        tv_uptime.setText(DateAndTimeUtils.getCurrentTime("yyyy-MM-dd"));
        show= (ShowNewFra) getActivity();
        tv_title.setText(CadillacUtils.getCurruser().getDealerName());
        getLoadDaily(DateAndTimeUtils.getCurrentTime("yyyy-MM-dd"), CadillacUtils.getCurruser().getDealerId()+"");//获取上传数据
    }



    @OnClick({R.id.rl_back,R.id.rl_upload})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.rl_back://返回
                 show.shownewfra();
                break;


            case R.id.rl_upload://上传
                changeDate();//获取每一个输入框里面的内容
                break;
        }

    }

    /**
     * 获取输入框里面的数字
     */
    private void changeDate() {
        for(int i=0;i<adapter.getCount();i++){
            LinearLayout layout = (LinearLayout) ckiTaskSETimeScrollView.findViewById(R.id.ck_ll);// 获得子item的layout
            // 从layout中获得控件
            LinearLayout itemlayout= (LinearLayout) layout.getChildAt(i);
            TextView carname= (TextView) itemlayout.findViewById(R.id.tv_carname);
            EditText et_xzjd= (EditText) itemlayout.findViewById(R.id.et_xzjd);//（新增进店）
            EditText et_ecjd = (EditText) itemlayout.findViewById(R.id.et_ecjd);//（二次进店）
            EditText et_dd= (EditText) itemlayout.findViewById(R.id.et_dd);//当日订单
            EditText et_jiaoche= (EditText) itemlayout.findViewById(R.id.et_jc);//交车
            EditText et_czdd= (EditText) itemlayout.findViewById(R.id.et_czdds);//车展订单数字
            EditText et_jetd= (EditText) itemlayout.findViewById(R.id.et_jetd);//今日退订
            list.get(i).setCarModelName(carname.getText().toString());//车名
            list.get(i).setIncomeCnt(et_xzjd.getText().toString());//新增进店
            list.get(i).setSecondIncomeCnt(et_ecjd.getText().toString());//二次进店
            list.get(i).setOrderCnt(et_dd.getText().toString());//展厅订单
            list.get(i).setDeliveryCnt(et_jiaoche.getText().toString());//交车
            list.get(i).setOrderCntShow(et_czdd.getText().toString());//车展订单
            list.get(i).setCancelOrderCnt(et_jetd.getText().toString());
        }

               upLoad(list);//上传数据

    }



    /**
     * 获取上传数据
     * @param dateStr
     * @param dealerid
     */
    private void getLoadDaily(String dateStr,String dealerid) {
        final Dialog dialog= CProgressDialog.createLoadingDialog(getActivity(),false);
        dialog.show();
        UserManager.getUserManager().getLoadDaily(dateStr, dealerid, new ResultCallback<ResultsNewBean<List<LoadBean>>>() {
            @Override
            public void onError(int status, String errorMsg) {
                dialog.dismiss();
                MyToastUtils.showShortToast(getActivity(),errorMsg);
            }

            @Override
            public void onResponse(ResultsNewBean<List<LoadBean>> response) {
                dialog.dismiss();
                list=response.getObj();
                LinearLayout layout = (LinearLayout) ckiTaskSETimeScrollView.findViewById(R.id.ck_ll);// 获得子item的layout
                layout.removeAllViews();
                adapter = new LvztAdapter(getActivity(), list);
                ckiTaskSETimeScrollView.setAdapter(adapter);
//                adapter.notify();
            }
        });
    }


    /**
     * 上传
     * @param uplist
     */
    private void upLoad(final List<LoadBean>uplist) {
        new MyAlertDialog(getActivity()).builder().setMsg("请确定数据录入准确").
                setPositiveButton("确认提交", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Dialog updialog= CProgressDialog.createLoadingDialog(getActivity(),false);
                        updialog.show();
                        UserManager.getUserManager().upLoadinfo(uplist, new ResultCallback<String>() {
                            @Override
                            public void onError(int status, String errorMsg) {
                                updialog.dismiss();
                                MyToastUtils.showShortToast(getActivity(),errorMsg);
                            }

                            @Override
                            public void onResponse(String response) {
                                updialog.dismiss();
                                try {
                                    JSONObject jsonObject=new JSONObject(response);
                                    String code=jsonObject.getString("code");
                                    String message=jsonObject.getString("message");
                                    if(code.equals("success")){
                                        new MyAlertDialog(getActivity()).builder().setMsg("递交成功").setNegativeButton("ok", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                getLoadDaily(DateAndTimeUtils.getCurrentTime("yyyy-MM-dd"), CadillacUtils.getCurruser().getDealerId()+"");//获取上传数据
                                            }
                                        }).show();
                                    }else {
                                        MyToastUtils.showShortToast(getActivity(),message);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        });
                    }
                }).setNegativeButton("返回填写",null).show();


    }




    /**
     * 给主页面实现的抽象方法
     */
    public interface ShowNewFra{
        void shownewfra();
    }


}