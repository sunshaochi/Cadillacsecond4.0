package cadillac.example.com.cadillac.fragment;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cadillac.example.com.cadillac.MyApplication;
import cadillac.example.com.cadillac.R;
import cadillac.example.com.cadillac.activity.StatDialogAct;
import cadillac.example.com.cadillac.adapter.CaldroidSampleCustomAdapter;
import cadillac.example.com.cadillac.adapter.GvAdpter;
import cadillac.example.com.cadillac.base.BaseFrag;
import cadillac.example.com.cadillac.bean.MessageEvent;
import cadillac.example.com.cadillac.bean.Reportbean;
import cadillac.example.com.cadillac.bean.StateBean;
import cadillac.example.com.cadillac.bean.UserModle;
import cadillac.example.com.cadillac.http.Manage.UserManager;
import cadillac.example.com.cadillac.http.ResultCallback;

import cadillac.example.com.cadillac.utils.DateAndTimeUtils;
import cadillac.example.com.cadillac.utils.MyLogUtils;
import cadillac.example.com.cadillac.utils.MyToastUtils;
import cadillac.example.com.cadillac.utils.SpUtils;
import cadillac.example.com.cadillac.utils.TimeUtil;
import cadillac.example.com.cadillac.view.CKLineView;
import cadillac.example.com.cadillac.view.CProgressDialog;
import cadillac.example.com.cadillac.view.Picktime.pickerview.TimePickerView;
import cadillac.example.com.cadillac.view.Picktime.pickerview.TimePickerViewDialog;
import cadillac.example.com.cadillac.view.caldroid.CaldroidFragment;
import cadillac.example.com.cadillac.view.caldroid.CaldroidListener;
import hirondelle.date4j.DateTime;

import static cadillac.example.com.cadillac.R.id.tv2;


/**
 * Created by bitch-1 on 2017/3/18.
 */
public class StatDayFra extends BaseFrag {

    @ViewInject(R.id.ll_xx)
    private LinearLayout ll_xx;

    @ViewInject(R.id.ll_h5)
    private LinearLayout ll_h5;

    private WebView webView;


    private CaldroidSampleCustomAdapter adapter;
    private Map<String, Object> map;

    private CaldroidSampleCustomFragment customFragment;

    private List<StateBean>datelist=new ArrayList<>();




    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fra_statday, null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        map = new HashMap<>();
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        customFragment = new CaldroidSampleCustomFragment();
        if (savedInstanceState != null) {
            customFragment.restoreStatesFromKey(savedInstanceState,
                    "CALDROID_SAVED_STATE");} else {
            Bundle args = new Bundle();
            Calendar cal = Calendar.getInstance();
            args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
            args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
            args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
            args.putInt(CaldroidFragment.START_DAY_OF_WEEK, CaldroidFragment.SUNDAY); // Tuesday
            args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);
            customFragment.setArguments(args);
        }

        setCustomResourceForDates();

        FragmentTransaction t = getActivity().getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendar1, customFragment);
        t.commit();

        final CaldroidListener listener = new CaldroidListener() {
            @Override
            public void onSelectDate(Date date, View view) {
                if (view.getTag().equals("no")) {
                     MyToastUtils.showShortToast(getActivity(),"暂时不能点");
                } else {
                    Intent intent = new Intent(getActivity(), StatDialogAct.class);
                    intent.putExtra("time", DateAndTimeUtils.getTimeForDate("yyyy-MM-dd",date));
                    intent.putExtra("queryType", "0");
                    intent.putExtra("biaoti",DateAndTimeUtils.getTimeForDate("yyyy-MM-dd",date));
                    startActivity(intent);

                }

            }

            @Override
            public void onChangeMonth(int month, int year) {
                    String time = year + "-" + month;
//                    MyToastUtils.showShortToast(getActivity(),time);
                    EventBus.getDefault().post(new MessageEvent("0",time));
                    SpUtils.setOneTime(getActivity(),time);
                    getDaydate(time,"0",SpUtils.getCarModleid(getActivity()),"","");
                }


            @Override
            public void onLongClickDate(Date date, View view) {

            }

            @Override
            public void onCaldroidViewCreated() {



            }

        };

        customFragment.setCaldroidListener(listener);

    }

    private void setCustomResourceForDates() {
        Calendar cal = Calendar.getInstance();

        // Min date is last 7 days
        cal.add(Calendar.DATE, -7);
        Date blueDate = cal.getTime();

        // Max date is next 7 days
        cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 7);
        Date greenDate = cal.getTime();

        if (customFragment != null) {
            ColorDrawable blue = new ColorDrawable(getResources().getColor(R.color.blue));
            ColorDrawable green = new ColorDrawable(Color.GREEN);
            customFragment.setBackgroundDrawableForDate(blue, blueDate);
            customFragment.setBackgroundDrawableForDate(green, greenDate);
            customFragment.setTextColorForDate(R.color.white, blueDate);
            customFragment.setTextColorForDate(R.color.white, greenDate);
        }
    }


    private void getDaydate(final String ds, final String qt, final String cmd, final String cpt, final String cpd) {
        final Dialog dialog = CProgressDialog.createLoadingDialog(getActivity(), false);
        dialog.show();
        UserManager.getUserManager().getdaydate(ds, qt, cmd, cpt, cpd,new ResultCallback<String>() {
            @Override
            public void onError(int status, String errorMsg) {
                MyToastUtils.showShortToast(getActivity(),errorMsg);
                dialog.dismiss();
            }

            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                datelist.clear();

                try {
                    JSONObject object=new JSONObject(response);
                    JSONObject obj=object.getJSONObject("obj");
                    for(int i=1;i<32;i++){
                        if(!obj.isNull(i+"")){//存在
//                            MyLogUtils.info(i+"true");
                                JSONObject keyobj=obj.getJSONObject(i+"");
                                String datekey=keyobj.getString("dateKey");
                                String topShow=keyobj.getString("topShow");
                                String buttonShow=keyobj.getString("buttonShow");
                                datelist.add(new StateBean(datekey,topShow,buttonShow));
                        }
                    }
//                        MyLogUtils.info("打印"+datelist.toString());
                        map.put("statbean", datelist);
                        customFragment.setExtraData(map);
                        customFragment.refreshView();

//                        setLin(datelist);//设置双折线
                          String h5url="http://127.0.0.1:8080/noneLiveWeb/appEcharts/report.html?"+
                                  "compareData="+cpd+"compareType="+cpt+"dateStr="+ds+"queryType="+qt
                                  +"dealerCode="+cmd;
                          setWeb(h5url);

                } catch (JSONException e) {
//                    MyLogUtils.info("错误答应"+datelist.toString());
                    e.printStackTrace();
                }



            }
        });
    }

    /**
     * 设置h5界面
     */
    private void setWeb(String url) {
        ll_h5.removeAllViews();
        webView = null;
        webView = new WebView(MyApplication.getInstances());
        webView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        ll_h5.addView(webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.loadUrl(url);

        webView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                return super.shouldOverrideUrlLoading(view, url);
                view.loadUrl(url);
                return true;

            }
        });

    }

    private List<List<String>> dataSource;
    private List<Integer>jdsintlist;
    private List<String>jdsstringlist;

    private List<Integer>ddintlist;
    private List<String>ddstringlist;

    String[] yData;
    String[] xData;

    private CKLineView ckLineView;



    private void setLin(List<StateBean> datelist) {
        ll_xx.removeAllViews();
        jdsintlist = new ArrayList<>();//进店
        jdsstringlist = new ArrayList<>();//进店
        ddintlist = new ArrayList<>();//订单
        ddstringlist = new ArrayList<>();//订单


        if (datelist!=null&&datelist.size()>0) {
            for (int i = 0; i < datelist.size();i++) {
                if(TextUtils.isEmpty(datelist.get(i).getTopShow())){
                    jdsintlist.add(Integer.parseInt("0"));
                    jdsstringlist.add("0");

                }else {
                    jdsintlist.add(Integer.parseInt(datelist.get(i).getTopShow()));
                    jdsstringlist.add(datelist.get(i).getTopShow());

                }


                if(TextUtils.isEmpty(datelist.get(i).getButtonShow())){
                    ddintlist.add(Integer.parseInt("0"));
                    ddstringlist.add("0");

                }else {
                    ddintlist.add(Integer.parseInt(datelist.get(i).getButtonShow()));
                    ddstringlist.add(datelist.get(i).getButtonShow());

                }


            }

            dataSource = new ArrayList<>();
            dataSource.add(jdsstringlist);
            dataSource.add(ddstringlist);

            Collections.sort(jdsintlist);//降序
            Collections.sort(ddintlist);

            String max = jdsintlist.get(jdsintlist.size() - 1).toString();//取出进店的最大值
            String min = ddintlist.get(0).toString();//取订单的最小
            yData = new String[]{min, max};

            xData = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};//x线上数据
            ckLineView = new CKLineView(dataSource, yData, xData,getActivity(),1);
            ll_xx.addView(ckLineView);

        }




}


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (customFragment != null) {
            customFragment.saveStatesToKey(outState, "CALDROID_SAVED_STATE");
        }
    }

    /**
     * 时间选择改变开始时间
     * @param selecttime
     */
    public void moveCaid(String selecttime) {
        customFragment.moveToDate(DateAndTimeUtils.getDateTimeForStr("yyyy-MM",selecttime));
    }




    /**
     * 当界面没被隐藏时候判断车型是否被改变如果改变重新加载
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden){//隐藏

        }else {
            getDaydate(SpUtils.getOneTime(getActivity()),"0",SpUtils.getCarModleid(getActivity()),"","");
        }
    }

    public void upDate() {
        getDaydate(SpUtils.getOneTime(getActivity()),"0",SpUtils.getCarModleid(getActivity()),"","");
    }
}
