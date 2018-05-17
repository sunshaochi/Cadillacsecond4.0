package cadillac.example.com.cadillac.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cadillac.example.com.cadillac.MyApplication;
import cadillac.example.com.cadillac.R;
import cadillac.example.com.cadillac.adapter.ViewoneAdap;
import cadillac.example.com.cadillac.base.BaseFrag;
import cadillac.example.com.cadillac.bean.EventDayinfo;
import cadillac.example.com.cadillac.bean.Modle;
import cadillac.example.com.cadillac.bean.QstBean;
import cadillac.example.com.cadillac.bean.QstEventMess;
import cadillac.example.com.cadillac.bean.QstVauleBean;
import cadillac.example.com.cadillac.bean.ResultsNewBean;
import cadillac.example.com.cadillac.http.Manage.UserManager;
import cadillac.example.com.cadillac.http.ResultCallback;
import cadillac.example.com.cadillac.utils.CadillacUtils;
import cadillac.example.com.cadillac.utils.MyLogUtils;
import cadillac.example.com.cadillac.utils.MyToastUtils;
import cadillac.example.com.cadillac.utils.SpUtils;
import cadillac.example.com.cadillac.view.CKLineView;
import cadillac.example.com.cadillac.view.CProgressDialog;
import cadillac.example.com.cadillac.view.QsDialog;

/**趋势图的fragment
 * Created by iris on 2018/1/25.
 */

public class LmQsFra extends BaseFrag {
    @ViewInject(R.id.tv_pptime)
    private TextView tv_pptime;//时间
    @ViewInject(R.id.tv_ppmoudle)
    private TextView tv_ppmoudle;//车型
    @ViewInject(R.id.view_line)
    private View view_line;
    @ViewInject(R.id.ll_xx)
    private LinearLayout ll_xx;
    @ViewInject(R.id.ll_dc)
    private LinearLayout ll_dc;
    @ViewInject(R.id.tv_dc)
    private TextView tv_dc;

    @ViewInject(R.id.tv_dq)
    private TextView tv_dq;

    @ViewInject(R.id.tv_qg)
    private TextView tv_qg;

    @ViewInject(R.id.ll_all)
    private LinearLayout ll_all;

    @ViewInject(R.id.ll_web)
    private LinearLayout ll_web;

    private WebView webView;

    private List<String>timelist=new ArrayList<>();

    List<Modle>list;
    private List<String>modlelist=new ArrayList<>();

    private String inputTimeid;
    private String dateId;
    private String time;
    private String modle;
    private List<QstVauleBean>datelist;
    private List<String>xlist;

    private CKLineView ckLineView;

    private Dialog dialog;
    private String type;







    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fra_qst,null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);//注册事件

        timelist.add("近3个月");
        timelist.add("近6个月");
        inputTimeid= SpUtils.getInputTimeId(getActivity());
        dateId=SpUtils.getDateId(getActivity());
        time="3";//默认是三个月
        modle="全部";//默认是全部车型

//        getQstdata(CadillacUtils.getCurruser().getUserName(),modle,time,inputTimeid,dateId);
          webSet();//设置h5
    }

    private void webSet() {
        ll_web.removeAllViews();
        webView = null;
        webView = new WebView(getActivity());
        webView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        ll_web.addView(webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setDomStorageEnabled(true);
//        String url="http://192.168.2.108:8080/cadillac/appEcharts/grossProfit.html?"+
//                "inputTimeId="+inputTimeid+"&userName="+CadillacUtils.getCurruser().getUserName()+
//                "&dateId="+dateId+"&carType="+modle+"&month="+time;
        String url="http://baidu.com";
        MyLogUtils.info(url);
        webView.loadUrl(url);

        webView.setWebViewClient(new WebViewClient(){
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                return super.shouldOverrideUrlLoading(view, request);
                view.loadUrl(request.getUrl().toString());
                return true;
            }
        });

//        webView.setWebViewClient(new WebViewClient(){
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
////                return super.shouldOverrideUrlLoading(view, url);
//                view.loadUrl(url);
//                return true;
//            }
//        });

    }

    @OnClick({R.id.ll_time,R.id.ll_cx})
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.ll_time:
                type="1";
                showQsDialog(timelist,tv_pptime.getText().toString(),type);//显示趋势图的dialog
                break;


            case R.id.ll_cx:
                type="2";
                getCarModle();
                break;
        }
    }

    private void showQsDialog(List<String> diloglist,String dilogname,String dtype) {
        QsDialog dialog=new QsDialog(getActivity(),diloglist,dilogname,dtype).build();
        dialog.show();

    }

    /**
     * 获取车型
     */
    private void getCarModle() {
        if(modlelist==null||modlelist.size()==0){//空的要请求车型
            UserManager.getUserManager().getCarModle(new ResultCallback<ResultsNewBean<List<Modle>>>() {
                @Override
                public void onError(int status, String errorMsg) {
                    MyToastUtils.showShortToast(getActivity(),errorMsg);
                }

                @Override
                public void onResponse(ResultsNewBean<List<Modle>> response) {
                    list=response.getObj();
                    if(list!=null&&list.size()>0){
//                        list.add(0,new Modle("","","全部","","","","","","",""));
                    for(int i=0;i<list.size();i++){
                        modlelist.add(list.get(i).getCode());
                    }
                        modlelist.add(0,"全部");
                        showQsDialog(modlelist,tv_ppmoudle.getText().toString(),type);

                    }else {
                        MyToastUtils.showShortToast(getActivity(),"暂无数据");
                    }
                }
            });
        }else {
            showQsDialog(modlelist,tv_ppmoudle.getText().toString(),type);
        }
    }






    @Subscribe
    public void onMoonEvent(QstEventMess s){
        if(type.equals("1")){//表示选择的是时间
            if(!tv_pptime.getText().toString().equals(timelist.get(s.getMess()))){
                tv_pptime.setText(timelist.get(s.getMess()));
                if(tv_pptime.getText().toString().equals("近3个月")){
                    time="3";
                }else {
                    time="6";
                }

//                getQstdata(CadillacUtils.getCurruser().getUserName(),modle,time,inputTimeid,dateId);
                 webSet();
            }

        }else if(type.equals("2")){
            if(!tv_ppmoudle.getText().toString().equals(modlelist.get(s.getMess()))){
                tv_ppmoudle.setText(modlelist.get(s.getMess()));
                modle=modlelist.get(s.getMess()).toString().trim();
//                getQstdata(CadillacUtils.getCurruser().getUserName(),modle,time,inputTimeid,dateId);
                 webSet();
            }
        }
    }




    /**
     * 获取趋势图
     * @param userName
     * @param carType
     * @param month
     * @param inputTimeId
     */
    private void getQstdata(String userName,String carType,String month,String inputTimeId,String dateId) {
        dialog = CProgressDialog.createLoadingDialog(getActivity(),false);
        dialog.show();
        UserManager.getUserManager().getQstData(userName, carType, month, inputTimeId,dateId, new ResultCallback<ResultsNewBean<QstBean>>() {
            @Override
            public void onError(int status, String errorMsg) {
                dialog.dismiss();
                MyToastUtils.showShortToast(getActivity(),errorMsg);
                ll_xx.removeAllViews();
            }

            @Override
            public void onResponse(ResultsNewBean<QstBean> response) {
                dialog.dismiss();
             datelist=response.getObj().getList();
             xlist=response.getObj().getDateItems();
             setLin(datelist,xlist);
            }

        });
    }

    private Map<String,List<Integer>>map=new HashMap<>();
    private List<List<String>> dataSource=new ArrayList<>();
    String[] yData;
    String[] xData;
    private List<Integer>maxlist=new ArrayList<>();
    /**
     * 设置线
     * @param qslist
     */
    private void setLin(List<QstVauleBean>qslist,List<String>xsz) {
        ll_xx.removeAllViews();
        dataSource.clear();
        maxlist.clear();
        if(qslist.size()>0&&qslist!=null){
          for(int i=0;i<qslist.size();i++){
              dataSource.add(qslist.get(i).getValues());
              List<String>list=qslist.get(i).getValues();
              for(int j=0;j<list.size();j++){
                  maxlist.add(Integer.parseInt(list.get(j)));
              }
          }

            Collections.sort(maxlist);//降序
            String max=maxlist.get(maxlist.size()-1)+"";
            String min=maxlist.get(0)+"";
            yData = new String[]{min, max};
            xData = new String[xsz.size()];//x线上数据
           for(int i=0;i<xsz.size();i++){
              xData[i] = xsz.get(i);
           }

            ll_dc.setVisibility(View.GONE);
            ll_all.setVisibility(View.GONE);
            if(dataSource!=null&&dataSource.size()>0){
               if (dataSource.size()==3){
                 ll_dc.setVisibility(View.VISIBLE);
                 tv_dc.setText(datelist.get(0).getName());
                 tv_dq.setText(datelist.get(1).getName());
                 tv_qg.setText(datelist.get(2).getName());
               }else {
                ll_all.setVisibility(View.VISIBLE);
               }
            }

            ckLineView = new CKLineView(dataSource, yData, xData,getActivity(),2);
            ll_xx.addView(ckLineView);



        }else {
            MyToastUtils.showShortToast(getActivity(),"暂时无趋势图数据");
        }



    }

    public void upDate() {
        inputTimeid= SpUtils.getInputTimeId(getActivity());
        dateId=SpUtils.getDateId(getActivity());
//        getQstdata(CadillacUtils.getCurruser().getUserName(),modle,time,inputTimeid,dateId);
        webSet();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            if(inputTimeid.equals(SpUtils.getInputTimeId(getActivity()))&&dateId.equals(SpUtils.getDateId(getActivity()))){

            }else {
                inputTimeid= SpUtils.getInputTimeId(getActivity());
                dateId=SpUtils.getDateId(getActivity());
//                getQstdata(CadillacUtils.getCurruser().getUserName(),modle,time,inputTimeid,dateId);
                webSet();
            }
        }
    }
}
