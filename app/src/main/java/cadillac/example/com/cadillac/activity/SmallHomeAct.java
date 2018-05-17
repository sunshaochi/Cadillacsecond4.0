package cadillac.example.com.cadillac.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import cadillac.example.com.cadillac.MyApplication;
import cadillac.example.com.cadillac.R;
import cadillac.example.com.cadillac.activity.admin.AdminInfoAct;
import cadillac.example.com.cadillac.base.BaseActivity;
import cadillac.example.com.cadillac.bean.DayinfoBean;
import cadillac.example.com.cadillac.bean.EventDayinfo;
import cadillac.example.com.cadillac.bean.ResultsNewBean;
import cadillac.example.com.cadillac.fragment.OneFra;
import cadillac.example.com.cadillac.fragment.ThreeFra;
import cadillac.example.com.cadillac.fragment.TwoFra;
import cadillac.example.com.cadillac.fragment.Viewonefra;
import cadillac.example.com.cadillac.fragment.Viewthreefra;
import cadillac.example.com.cadillac.fragment.Viewtwofra;
import cadillac.example.com.cadillac.http.Manage.UserManager;
import cadillac.example.com.cadillac.http.ResultCallback;
import cadillac.example.com.cadillac.utils.CadillacUtils;
import cadillac.example.com.cadillac.utils.DateAndTimeUtils;
import cadillac.example.com.cadillac.utils.MyToastUtils;
import cadillac.example.com.cadillac.utils.SpUtils;
import cadillac.example.com.cadillac.utils.TimeUtil;
import cadillac.example.com.cadillac.view.CProgressDialog;
import cadillac.example.com.cadillac.view.KpiDialog;

/**
 * Created by iris on 2018/1/4.
 */

public class SmallHomeAct extends BaseActivity {
    @ViewInject(R.id.tv_qb)
    private TextView tv_qb;
    @ViewInject(R.id.tv_zt)
    private TextView tv_zt;
    @ViewInject(R.id.tv_cz)
    private TextView tv_cz;
    @ViewInject(R.id.view_qb)
    private View view_qb;
    @ViewInject(R.id.view_zt)
    private View view_zt;
    @ViewInject(R.id.view_cz)
    private View view_cz;
    @ViewInject(R.id.vp_main)
    private ViewPager vp_main;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.tv_daytime)
    private TextView tv_daytime;

    private List<Fragment> fralist;
    private OneFra qonefra;//全部
    private TwoFra zonefra;//展厅
    private ThreeFra conefra;//车展
    private DayinfoBean infobean;
    private String name;
    private String fullcode;
    @Override
    public void setLayout() {
         setContentView(R.layout.act_smallhome);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        name=getIntent().getExtras().getString("name");
        fullcode=getIntent().getExtras().getString("fullcode");
        tv_title.setText(name);
        tv_daytime.setText(DateAndTimeUtils.getCurrentTime("yyyy-MM-dd"));
        setTabAndColor(0);//设置前面的改变
        vp_main.setOffscreenPageLimit(2);
        fralist = new ArrayList<>();
        qonefra = new OneFra();
        zonefra = new TwoFra();
        conefra = new ThreeFra();
        Bundle bundle=new Bundle();
        bundle.putString("from","small");
        qonefra.setArguments(bundle);//用来区分从那里去到，以此来保存kpi的type值
        zonefra.setArguments(bundle);
        conefra.setArguments(bundle);
        fralist.add(qonefra);
        fralist.add(zonefra);
        fralist.add(conefra);
        vp_main.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fralist.get(position);
            }

            @Override
            public int getCount() {
                return fralist.size();
            }
        });

        vp_main.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setTabAndColor(position);//改变顶部标题

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        vp_main.setCurrentItem(0);
        getDayinfo(fullcode,DateAndTimeUtils.getCurrentTime("yyyy-MM-dd"));//获取当天时间的数据

    }


    /**获取当天数据（首页三屏）
     * @param dealerCode
     * @param dateStr
     */
    private void getDayinfo(String dealerCode, String dateStr) {
        final Dialog dialog= CProgressDialog.createLoadingDialog(SmallHomeAct.this,false);
        dialog.show();
        UserManager.getUserManager().getDayinfo(dealerCode, dateStr, new ResultCallback<ResultsNewBean<DayinfoBean>>() {
            @Override
            public void onError(int status, String errorMsg) {
                dialog.dismiss();
                MyToastUtils.showShortToast(SmallHomeAct.this, errorMsg);
            }

            @Override
            public void onResponse(ResultsNewBean<DayinfoBean> response) {
                infobean = response.getObj();
                dialog.dismiss();
                if (infobean != null) {
//                    Intent intent1=new Intent(Viewonefra.UPDATEONEUI);
//                    Intent intent2=new Intent(Viewtwofra.UPDATETWOUI);
//                    Intent intent3=new Intent(Viewthreefra.UPDATEUI);
//                    intent1.putExtra("infobean",infobean);
//                    intent2.putExtra("infobean",infobean);
//                    intent3.putExtra("infobean",infobean);
//                    sendBroadcast(intent1);
//                    sendBroadcast(intent2);
//                    sendBroadcast(intent3);
                    EventBus.getDefault().post(new EventDayinfo(infobean));
                }
            }
        });
    }



    @OnClick({R.id.ll_qb, R.id.ll_zt, R.id.ll_cz, R.id.rl_kpi,R.id.rl_finsh})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.ll_qb:
                setTabAndColor(0);
                vp_main.setCurrentItem(0);
                qonefra.upDateType();
                break;

            case R.id.ll_zt:
                setTabAndColor(1);
                vp_main.setCurrentItem(1);
                zonefra.upDateType();
                break;

            case R.id.ll_cz:
                setTabAndColor(2);
                vp_main.setCurrentItem(2);
                conefra.upDateType();
                break;
            case R.id.rl_kpi:
                String type = SpUtils.getKpiinfo(SmallHomeAct.this);
                KpiDialog dialog = new KpiDialog(SmallHomeAct.this, type).buidler();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                break;

            case R.id.rl_finsh:
                finish();
                break;


        }
    }


    /**
     * 设置顶部tab变色
     *
     * @param i
     */
    private void setTabAndColor(int i) {
        tv_qb.setTextColor(Color.parseColor("#4b4b4b"));
        tv_cz.setTextColor(Color.parseColor("#4b4b4b"));
        tv_zt.setTextColor(Color.parseColor("#4b4b4b"));
        view_qb.setVisibility(View.GONE);
        view_zt.setVisibility(View.GONE);
        view_cz.setVisibility(View.GONE);
        switch (i) {
            case 0:
                tv_qb.setTextColor(Color.parseColor("#8c1229"));
                view_qb.setVisibility(View.VISIBLE);
                break;

            case 1:
                tv_zt.setTextColor(Color.parseColor("#8c1229"));
                view_zt.setVisibility(View.VISIBLE);
                break;
            case 2:
                tv_cz.setTextColor(Color.parseColor("#8c1229"));
                view_cz.setVisibility(View.VISIBLE);
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SpUtils.setKpiinfo(SmallHomeAct.this,"0");
    }
}
