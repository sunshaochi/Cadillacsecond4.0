package cadillac.example.com.cadillac.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.lidroid.xutils.view.annotation.ViewInject;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import cadillac.example.com.cadillac.R;
import cadillac.example.com.cadillac.adapter.ViewthreeAdpt;
import cadillac.example.com.cadillac.adapter.ViewtwoAdpt;
import cadillac.example.com.cadillac.base.BaseFrag;
import cadillac.example.com.cadillac.base.ContiFrag;
import cadillac.example.com.cadillac.bean.DayinfoBean;
import cadillac.example.com.cadillac.bean.EventDayinfo;
import cadillac.example.com.cadillac.bean.ResultsNewBean;
import cadillac.example.com.cadillac.http.Manage.UserManager;
import cadillac.example.com.cadillac.http.ResultCallback;
import cadillac.example.com.cadillac.utils.CadillacUtils;
import cadillac.example.com.cadillac.utils.MyToastUtils;
import cadillac.example.com.cadillac.utils.TimeUtil;
import cadillac.example.com.cadillac.view.CProgressDialog;

/**
 * Created by iris on 2017/12/13.
 */


public class Viewthreefra extends BaseFrag {
    @ViewInject(R.id.lv_viewthree)
    private ListView lv_viewthree;
    private ViewthreeAdpt adpt;
    private String type;
    private DayinfoBean infobean;



    @Override
    public View initView(LayoutInflater inflater) {
        View view=inflater.inflate(R.layout.fra_viewthree,null);
        return view;
    }




    @Override
    public void initData(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);//注册事件
        type=getArguments().getString("type");


    }





    @Subscribe
    public void onMoonEvent(EventDayinfo eventDayinfo){
        infobean=eventDayinfo.getInfobean();
        if(infobean!=null){
            adpt=new ViewthreeAdpt(getActivity(),infobean,type);
            lv_viewthree.setAdapter(adpt);
            adpt.notifyDataSetChanged();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
