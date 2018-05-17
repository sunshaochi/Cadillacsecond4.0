package cadillac.example.com.cadillac.fragment;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.view.annotation.ViewInject;


import java.util.ArrayList;
import java.util.List;

import cadillac.example.com.cadillac.R;
import cadillac.example.com.cadillac.adapter.CzAdapter;

import cadillac.example.com.cadillac.base.BaseFrag;
import cadillac.example.com.cadillac.bean.JisuBean;
import cadillac.example.com.cadillac.bean.SalseDateBean;
import cadillac.example.com.cadillac.bean.UserBean;
import cadillac.example.com.cadillac.bean.UserModle;
import cadillac.example.com.cadillac.http.Manage.UserManager;
import cadillac.example.com.cadillac.http.ResultCallback;
import cadillac.example.com.cadillac.utils.MyLogUtils;
import cadillac.example.com.cadillac.utils.MyToastUtils;
import cadillac.example.com.cadillac.view.CKITaskSETimeScrollView;
import cadillac.example.com.cadillac.view.CProgressDialog;
import cadillac.example.com.cadillac.view.MyAlertDialog;


/**
 * 车展数据frag
 * Created by bitch-1 on 2017/3/16.
 */
public class CzdateFra extends BaseFrag {

    @ViewInject(R.id.lv_czlv)
    private CKITaskSETimeScrollView lv_czlv;
    private CzAdapter adapter;

    private String time;//从父容器传递过来的时间
    private UserBean userbean;//登录成功后的返回对象
    private String store,role;//经销商名字，职务
    private Gson gson;
    private Dialog dialog;
    List<Integer> totlelist;
    private  List<SalseDateBean> list;
    private List<JisuBean>jslist;

    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.frg_czdate,null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        gson=new Gson();
        dialog= CProgressDialog.createLoadingDialog(getActivity(),false);
        Bundle bundle=getArguments();
        time=bundle.getString("time");
        userbean= UserModle.getUserBean();
        if(userbean!=null){
            store=userbean.getJXSName();
            role=userbean.getRole();
        }
        if(!TextUtils.isEmpty(time)){
            dialog.show();
            getSalesData(time,store,role,"否" );//获取车展数据
        }

    }

    /**
     * @param date 时间
     * @param store 经销商名字
     * @param role  职务
     * @param show 是（展厅）否（车展）
     */
    private void getSalesData(String date,String store,String role,String show) {
        UserManager.getUserManager().getZtData(date, store, role, show, new ResultCallback<String>() {
            @Override
            public void onError(int status, String errorMsg) {
                MyToastUtils.showShortToast(getActivity(),errorMsg);
                dialog.dismiss();
            }

            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                list = gson.fromJson(response,
                        new TypeToken<List<SalseDateBean>>() {
                        }.getType());
                if(list.size()==0){
                    MyToastUtils.showShortToast(getActivity(),"暂时无数据");
                }else {
                    LinearLayout layout = (LinearLayout) lv_czlv.findViewById(R.id.ll_ck);// 获得子item的layout
                    layout.removeAllViews();
                    adapter=new CzAdapter(getActivity(),list);
                    lv_czlv.setAdapter(adapter);
                }
            }
        });
    }



    public void upload() {
        new MyAlertDialog(getActivity()).builder().setMsg("请确定车展订单是否录入准确？\n (此页面仅含车展订单)").setPositiveButton("确认递交", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                Jisuan(time,store,role,"否");//计算数据
                for (int i = 0; i < adapter.getCount(); i++) {
                    LinearLayout layout = (LinearLayout)lv_czlv.findViewById(R.id.ll_ck);
                    LinearLayout itemlayout= (LinearLayout) layout.getChildAt(i);
                    // 从layout中获得控件,根据其id
                    EditText dindan= (EditText) itemlayout.findViewById(R.id.et_dindan);
                    EditText ljdd= (EditText) itemlayout.findViewById(R.id.et_ljdd);
                    TextView tv_chexin= (TextView) itemlayout.findViewById(R.id.tv_cx);
                    //上传
                    pullDate(time,store,"","",dindan.getText()+"","","",ljdd.getText()+"",tv_chexin.getText().toString(),"否","",i);//这里给个i用来显示递交成功的弹出框要不然会弹出来很多递交成功
                }
            }
        }).setNegativeButton("返回填写",null).show();

    }



    public void calCulate() {
//        Jisuan();
        Jisuan(time,store,role,"否");//计算数据
    }
    /**
     * 计算
     */
    private void Jisuan(String date,String store,String role,String show) {
        UserManager.getUserManager().getCalculate(date, store, role, show, new ResultCallback<String>() {
            @Override
            public void onError(int status, String errorMsg) {
                MyToastUtils.showShortToast(getActivity(),errorMsg);
            }

            @Override
            public void onResponse(String response) {
                if(!TextUtils.isEmpty(response)){
                    jslist=gson.fromJson(response.toString(),new TypeToken<List<JisuBean>>(){}.getType());
                    List<String>dind=new ArrayList<>();//订单数集合
                    List<String>yl=new ArrayList<>();//月累计数数集合
                    totlelist=new ArrayList<>();//总计
                    dind.clear();
                    yl.clear();
                    totlelist.clear();
                    for (int i = 0; i < adapter.getCount(); i++) {
                        LinearLayout layout = (LinearLayout)lv_czlv.findViewById(R.id.ll_ck);
                        LinearLayout itemlayout= (LinearLayout) layout.getChildAt(i);
                        // 从layout中获得控件,根据其id
                        EditText dindan= (EditText) itemlayout.findViewById(R.id.et_dindan);
                        EditText ljdd= (EditText) itemlayout.findViewById(R.id.et_ljdd);
                        //月累计 计算公式
                        if(!TextUtils.isEmpty(dindan.getText().toString())){//订单不为空
                            ljdd.setText(Integer.parseInt(dindan.getText().toString())+Integer.parseInt(jslist.get(i).getTotalOrder()+"")+"");
                        }else {
                            ljdd.setText(Integer.parseInt("0")+Integer.parseInt(jslist.get(i).getTotalOrder()+"")+"");
                        }

                        //总计计算公式
                        if(list.get(i).getModels().equals("总计")){
                            dindan.setText("0");
                            ljdd.setText("0");

                        }
                        //总计
                        dind.add(dindan.getText().toString());//订单集合
                        yl.add(ljdd.getText().toString());//月集合
                    }

                    sum(0,dind);//订单
                    sum(1,yl);//累计

                    for (int i = 0; i < adapter.getCount(); i++) {
                        LinearLayout layout = (LinearLayout) lv_czlv.findViewById(R.id.ll_ck);// 获得子item的layout
                        // 从layout中获得控件
                        LinearLayout itemlayout = (LinearLayout) layout.getChildAt(i);
                        EditText dindan = (EditText) itemlayout.findViewById(R.id.et_dindan);
                        EditText ljdd = (EditText) itemlayout.findViewById(R.id.et_ljdd);

                        if(list.get(i).getModels().equals("总计")){
                            dindan.setText(totlelist.get(0)+"");
                            ljdd.setText(totlelist.get(1)+"");
                        }
                    }
                }
            }
        });

    }

    /**
     * 计算总计
     * @param templist
     */
    private void sum(int type,List<String> templist) {
        String jdn="0";
        int jdnum = 0;
        for (int j=0;j<templist.size();j++){
            if(!TextUtils.isEmpty(templist.get(j))){
                jdn=templist.get(j);
            }else {
                jdn="0";
            }
            jdnum=jdnum+Integer.parseInt(jdn);
        }
        if(type==0){//订单
            totlelist.add(0,jdnum);
        }else if(type==1){//累计
            totlelist.add(1,jdnum);
        }
    }

    /**
     * @param date 2017-3-31
     * @param store 测试经销商
     * @param call 1
     * @param room 进店
     * @param order 订单
     * @param retail 退订
     * @param total 交车
     * @param retain 月累计
     * @param model 车型
     * @param is 是（表示展厅）
     * @param unsubscribe 留存
     * @param
     */
    private void pullDate(String date, String store, String call, String room, String order, String retail, String total, String retain, String model, String is, String unsubscribe, final int i) {
        UserManager.getUserManager().pullDate(date, store, call, room, order, retail, total, retain, model, is, unsubscribe, new ResultCallback<String>() {
            @Override
            public void onError(int status, String errorMsg) {
                dialog.dismiss();

            }

            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                if (i == adapter.getCount() - 1) {
                    new MyAlertDialog(getActivity()).builder().setMsg("递交成功").setNegativeButton("ok", null).show();
                }
            }
        });
    }







}

