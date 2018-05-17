package cadillac.example.com.cadillac.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cadillac.example.com.cadillac.MainActivity;
import cadillac.example.com.cadillac.MyApplication;
import cadillac.example.com.cadillac.R;
import cadillac.example.com.cadillac.activity.StatDialogAct;
import cadillac.example.com.cadillac.activity.admin.AdminInfoAct;
import cadillac.example.com.cadillac.adapter.ViewoneAdap;
import cadillac.example.com.cadillac.base.BaseFrag;
import cadillac.example.com.cadillac.base.ContiFrag;
import cadillac.example.com.cadillac.bean.DayinfoBean;
import cadillac.example.com.cadillac.bean.EventDayinfo;
import cadillac.example.com.cadillac.bean.ResultsBean;
import cadillac.example.com.cadillac.bean.ResultsNewBean;
import cadillac.example.com.cadillac.bean.UserModle;
import cadillac.example.com.cadillac.http.Manage.UserManager;
import cadillac.example.com.cadillac.http.ResultCallback;
import cadillac.example.com.cadillac.utils.CadillacUtils;
import cadillac.example.com.cadillac.utils.DateAndTimeUtils;
import cadillac.example.com.cadillac.utils.MyLogUtils;
import cadillac.example.com.cadillac.utils.MyToastUtils;
import cadillac.example.com.cadillac.utils.SpUtils;
import cadillac.example.com.cadillac.utils.TimeUtil;
import cadillac.example.com.cadillac.view.CProgressDialog;
import cadillac.example.com.cadillac.view.KpiDialog;

/**
 * Created by bitch-1 on 2017/10/12.
 */
public class NewDataFra extends BaseFrag {
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
    @ViewInject(R.id.iv_lleft)
    private ImageView iv_lleft;

    private List<Fragment> fralist;
    private ShowEdit showdit;


    private OneFra qonefra;//全部
    private TwoFra zonefra;//展厅
    private ThreeFra conefra;//车展

    private DayinfoBean infobean;


    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.otherfra, null);
    }


    @Override
    public void initData(Bundle savedInstanceState) {
        tv_title.setText(MyApplication.getInstances().getDaoSession().getLoginBeanDao().loadAll().get(0).getDealerName());
        tv_daytime.setText(TimeUtil.getCurrentTime());
        showdit = (ShowEdit) getActivity();
        MyLogUtils.info("是否可以编辑"+CadillacUtils.getCurruser().getEditData());
//        if(!TextUtils.isEmpty(CadillacUtils.getCurruser().getEditData())) {
            if (CadillacUtils.getCurruser().getEditData().equals("N")) {
                iv_lleft.setImageResource(R.mipmap.image_xq);
            } else {
                iv_lleft.setImageResource(R.mipmap.editor);
            }
//        }
//        else {
//            iv_lleft.setImageResource(R.mipmap.image_xq);//不能编辑
//        }
        setTabAndColor(0);//设置前面的改变
        vp_main.setOffscreenPageLimit(2);
        fralist = new ArrayList<>();
        qonefra = new OneFra();
        zonefra = new TwoFra();
        conefra = new ThreeFra();
        Bundle bundle=new Bundle();
        bundle.putString("from","new");
        qonefra.setArguments(bundle);//用来区分从那里去到，以此来保存kpi的type值
        zonefra.setArguments(bundle);
        conefra.setArguments(bundle);
        fralist.add(qonefra);
        fralist.add(zonefra);
        fralist.add(conefra);
        vp_main.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
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
        if (CadillacUtils.getCurruser() != null) {
            getDayinfo(CadillacUtils.getCurruser().getDealerFullCode(), TimeUtil.getCurrentTime());//获取当天时间的数据
        }
    }

    /**获取当天数据（首页三屏）
     * @param dealerCode
     * @param dateStr
     */
    private void getDayinfo(String dealerCode, String dateStr) {
        final Dialog dialog= CProgressDialog.createLoadingDialog(getActivity(),false);
        dialog.show();
        UserManager.getUserManager().getDayinfo(dealerCode, dateStr, new ResultCallback<ResultsNewBean<DayinfoBean>>() {
            @Override
            public void onError(int status, String errorMsg) {
                dialog.dismiss();
                MyToastUtils.showShortToast(getActivity(), errorMsg);
            }

            @Override
            public void onResponse(ResultsNewBean<DayinfoBean> response) {
                infobean = response.getObj();
                dialog.dismiss();
                if (infobean != null) {
                    EventBus.getDefault().post(new EventDayinfo(infobean));
                }
            }
        });
    }




    @OnClick({R.id.ll_qb, R.id.ll_zt, R.id.ll_cz, R.id.rl_kpi, R.id.rl_xq,R.id.rl_sx})
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
                String type = SpUtils.getKpitype(getActivity());
                KpiDialog dialog = new KpiDialog(getActivity(), type).buidler();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                break;

            case R.id.rl_xq:
                if(CadillacUtils.getCurruser().getEditData().equals("Y")) {//可以编辑
                    showdit.showEdit();
                }else {//不可以编辑
                    Bundle bundle = new Bundle();
                    bundle.putString("id",CadillacUtils.getCurruser().getDealerId()+"");
                    openActivity(AdminInfoAct.class, bundle);
                }
                break;

            case R.id.rl_sx:
                getDayinfo(CadillacUtils.getCurruser().getDealerFullCode(), TimeUtil.getCurrentTime());//获取当天时间的数据
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

    /**
     * 抽象接口给mainactivity去实现
     */
    public interface ShowEdit {
        void showEdit();
    }


}
