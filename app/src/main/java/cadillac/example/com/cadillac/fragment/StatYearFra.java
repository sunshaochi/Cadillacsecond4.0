package cadillac.example.com.cadillac.fragment;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cadillac.example.com.cadillac.R;
import cadillac.example.com.cadillac.activity.StatDialogAct;
import cadillac.example.com.cadillac.adapter.GvAdpter;
import cadillac.example.com.cadillac.adapter.GvYearAdapter;
import cadillac.example.com.cadillac.base.BaseFrag;
import cadillac.example.com.cadillac.bean.MessageEvent;
import cadillac.example.com.cadillac.bean.Reportbean;
import cadillac.example.com.cadillac.bean.StateBean;
import cadillac.example.com.cadillac.bean.UserModle;
import cadillac.example.com.cadillac.http.Manage.UserManager;
import cadillac.example.com.cadillac.http.ResultCallback;
import cadillac.example.com.cadillac.utils.DateAndTimeUtils;
import cadillac.example.com.cadillac.utils.MyToastUtils;
import cadillac.example.com.cadillac.utils.SpUtils;
import cadillac.example.com.cadillac.utils.TimeUtil;
import cadillac.example.com.cadillac.view.CKLineView;
import cadillac.example.com.cadillac.view.CProgressDialog;

/**
 * Created by bitch-1 on 2017/3/18.
 */
public class StatYearFra extends BaseFrag implements View.OnTouchListener,GestureDetector.OnGestureListener {
    @ViewInject(R.id.gv_my)
    private GridView gv_my;
    @ViewInject(R.id.ll_xx)
    private LinearLayout ll_xx;

    private GvYearAdapter adpter;
    private int year;//转换成int类型 当翻页时自增长
    private Dialog dialog;
    private int curryear;

    private List<StateBean>list=new ArrayList<>();
    private List<StateBean>linlist=new ArrayList<>();


    //创建一个用于识别收拾的GestureDetector对象waiyuwu.blogcn.com
    private GestureDetector detector = new GestureDetector(this);
    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fra_statyear,null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        year=Integer.parseInt(SpUtils.getThreeTime(getActivity()));
        adpter=new GvYearAdapter(getActivity(),list);
        gv_my.setAdapter(adpter);

        curryear=Integer.parseInt(DateAndTimeUtils.getCurrentTime("yyyy"));
        if(curryear-year==0) {
            getYeardate(DateAndTimeUtils.getCurrentTime("yyyy-MM-dd"), "2", SpUtils.getCarModleid(getActivity()), "", "");//获取年的数据
        }else {
            getYeardate(SpUtils.getThreeTime(getActivity())+"-12-31", "2", SpUtils.getCarModleid(getActivity()), "", "");//获取年的数据
        }


        //监听这个ImageView组件上的触摸屏时间
//        gv_my.setOnTouchListener(this);
        //下面两个要记得设哦，不然就没法处理轻触以外的事件了，例如抛掷动作。
//        gv_my.setLongClickable(true);
//        detector.setIsLongpressEnabled(true);
    }





    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
//        if(v<0){//从右往左边
//
//            year=year+4;
//
//
//        }else if(v>0){//从左边往右边
//
//            year=year-4;
//
//        }
//
//        EventBus.getDefault().post(new MessageEvent("3",year+""));
//        SpUtils.setThreeTime(getActivity(),year+"");
//        if(curryear-year==0) {
//            getYeardate(DateAndTimeUtils.getCurrentTime("yyyy-MM-dd"), "2", SpUtils.getCarModleid(getActivity()), "", "");//获取年的数据
//        }else {
//            getYeardate(SpUtils.getThreeTime(getActivity())+"-12-31", "2", SpUtils.getCarModleid(getActivity()), "", "");//获取年的数据
//        }
        return false;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        detector.onTouchEvent(motionEvent);
        return true;
    }


    /**
     * 主fragment跟新了时间
     */
    public void upDateinfo() {
        year=Integer.parseInt(SpUtils.getThreeTime(getActivity()));
        if(curryear-year==0) {
            getYeardate(DateAndTimeUtils.getCurrentTime("yyyy-MM-dd"), "2", SpUtils.getCarModleid(getActivity()), "", "");//获取年的数据
        }else {
            getYeardate(SpUtils.getThreeTime(getActivity())+"-12-31", "2", SpUtils.getCarModleid(getActivity()), "", "");//获取年的数据
        }
    }

    private void getYeardate(String dateStr,String queryType,String carModelId,String compareType,String compareData) {
        dialog= CProgressDialog.createLoadingDialog(getActivity(),false);
        dialog.show();
        UserManager.getUserManager().getyeardate(dateStr, queryType, carModelId, compareType,compareData, new ResultCallback<String>() {
            @Override
            public void onError(int status, String errorMsg) {
                dialog.dismiss();
            }

            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                list.clear();
                linlist.clear();
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONObject obj=jsonObject.getJSONObject("obj");
                    for(int i=1949;i<2100;i++){
                        if(!obj.isNull(i+"")){//存在
                            JSONObject keyobj=obj.getJSONObject(i+"");
                            String datekey=keyobj.getString("dateKey");
                            String topShow=keyobj.getString("topShow");
                            String buttonShow=keyobj.getString("buttonShow");
                            list.add(new StateBean(datekey,topShow,buttonShow));
                            linlist.add(new StateBean(datekey,topShow,buttonShow));
                        }
                    }
                    adpter.setChange(list);
                    setLin(linlist);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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


        if (datelist != null && datelist.size() > 0) {
            for (int i = 0; i < datelist.size(); i++) {
                if (TextUtils.isEmpty(datelist.get(i).getTopShow())) {
                    jdsintlist.add(Integer.parseInt("0"));
                    jdsstringlist.add("0");

                } else {
                    jdsintlist.add(Integer.parseInt(datelist.get(i).getTopShow()));
                    jdsstringlist.add(datelist.get(i).getTopShow());

                }


                if (TextUtils.isEmpty(datelist.get(i).getButtonShow())) {
                    ddintlist.add(Integer.parseInt("0"));
                    ddstringlist.add("0");

                } else {
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
            String yearo=Integer.parseInt(SpUtils.getThreeTime(getActivity()))-3+"";
            String yeart=Integer.parseInt(SpUtils.getThreeTime(getActivity()))-2+"";
            String yeartr=Integer.parseInt(SpUtils.getThreeTime(getActivity()))-1+"";
            String yearf=Integer.parseInt(SpUtils.getThreeTime(getActivity()))+"";
            xData = new String[]{yearo, yeart, yeartr, yearf};//x线上数据
            ckLineView = new CKLineView(dataSource, yData, xData,getActivity(),1);
            ll_xx.addView(ckLineView);
        }

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
            if(curryear-year==0) {
                getYeardate(DateAndTimeUtils.getCurrentTime("yyyy-MM-dd"), "2", SpUtils.getCarModleid(getActivity()), "", "");//获取年的数据
            }else {
                getYeardate(SpUtils.getThreeTime(getActivity())+"-12-31", "2", SpUtils.getCarModleid(getActivity()), "", "");//获取年的数据
            }        }
    }

}

