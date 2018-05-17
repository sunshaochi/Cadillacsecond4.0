package cadillac.example.com.cadillac.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cadillac.example.com.cadillac.MyApplication;
import cadillac.example.com.cadillac.R;
import cadillac.example.com.cadillac.base.BaseActivity;
import cadillac.example.com.cadillac.bean.DatilBean;
import cadillac.example.com.cadillac.bean.ResultsNewBean;
import cadillac.example.com.cadillac.bean.ScreenBean;
import cadillac.example.com.cadillac.bean.ShaiXuanBean;
import cadillac.example.com.cadillac.fragment.OneFra;
import cadillac.example.com.cadillac.fragment.PageOneFra;
import cadillac.example.com.cadillac.fragment.PageThreeFra;
import cadillac.example.com.cadillac.fragment.PageTwoFra;
import cadillac.example.com.cadillac.fragment.StatoneFra;
import cadillac.example.com.cadillac.fragment.StatthreeFra;
import cadillac.example.com.cadillac.fragment.StattwoFra;
import cadillac.example.com.cadillac.fragment.ThreeFra;
import cadillac.example.com.cadillac.fragment.TwoFra;
import cadillac.example.com.cadillac.fragment.Viewonefra;
import cadillac.example.com.cadillac.fragment.Viewthreefra;
import cadillac.example.com.cadillac.fragment.Viewtwofra;
import cadillac.example.com.cadillac.http.Manage.UserManager;
import cadillac.example.com.cadillac.http.ResultCallback;
import cadillac.example.com.cadillac.utils.CadillacUtils;
import cadillac.example.com.cadillac.utils.MyToastUtils;
import cadillac.example.com.cadillac.utils.SpUtils;
import cadillac.example.com.cadillac.utils.TimeUtil;
import cadillac.example.com.cadillac.view.CProgressDialog;
import cadillac.example.com.cadillac.view.KpiDialog;
import cadillac.example.com.cadillac.view.ShareDialog;


/**
 * 报表弹出的对话框
 * Created by bitch-1 on 2017/3/23.
 */
public class StatDialogAct extends BaseActivity {
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
    @ViewInject(R.id.iv_title)
    private ImageView iv_title;
    @ViewInject(R.id.ll_title)
    private LinearLayout ll_title;
    @ViewInject(R.id.view_topline)
    private View view_topline;
    @ViewInject(R.id.rl_share)
    private RelativeLayout rl_share;
    private List<Fragment> pagelist;

    private PageOneFra pageonefra;//全部
    private PageTwoFra pagetwofra;//展厅
    private PageThreeFra pagetrhreefra;//车展
    private List<DatilBean> list;

    private String time;//传递过来的时间
    private String queryType;//日月年季度
    private String biaoti;


    private List<ScreenBean> sclist;//上下级查看的第一级
    private List<ScreenBean> xqlist;//第二级
    private PopupWindow popupWindow;
    private ListView lv_dq;//第二级的控件


    private boolean isShow;


    @Override
    public void setLayout() {
        setContentView(R.layout.act_statdialog);
    }

    @Override
    public void init(Bundle savedInstanceState) {

        tv_title.setText(MyApplication.getInstances().getDaoSession().getLoginBeanDao().loadAll().get(0).getDealerName());
        time = getIntent().getStringExtra("time");
        queryType = getIntent().getStringExtra("queryType");//0.1.2.3
        biaoti = getIntent().getStringExtra("biaoti");
        tv_daytime.setText(biaoti);

        if (CadillacUtils.getCurruser().getRoleName().contains("经销商")) {
            rl_share.setVisibility(View.GONE);

        } else {
            rl_share.setVisibility(View.VISIBLE);
        }

        Bundle bundle = new Bundle();
        pagelist = new ArrayList<>();
        pageonefra = new PageOneFra();
        pagetwofra = new PageTwoFra();
        pagetrhreefra = new PageThreeFra();
        bundle.putString("queryType", queryType);
        pageonefra.setArguments(bundle);
        pagetwofra.setArguments(bundle);
        pagetrhreefra.setArguments(bundle);
        pagelist.add(pageonefra);
        pagelist.add(pagetwofra);
        pagelist.add(pagetrhreefra);

        setTabAndColor(0);//设置前面的改变
        vp_main.setOffscreenPageLimit(2);

        vp_main.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return pagelist.get(position);
            }

            @Override
            public int getCount() {
                return pagelist.size();
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
        getDailyInfo(time, queryType, CadillacUtils.getCurruser().getDealerFullCode());


    }


    @OnClick({R.id.ll_qb, R.id.ll_zt, R.id.ll_cz, R.id.rl_kpi, R.id.ll_title, R.id.rl_finsh, R.id.rl_share})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.ll_qb:
                setTabAndColor(0);
                vp_main.setCurrentItem(0);
                pageonefra.upDateKpitype();
                break;

            case R.id.ll_zt:
                setTabAndColor(1);
                vp_main.setCurrentItem(1);
                pagetwofra.upDateKpitype();
                break;

            case R.id.ll_cz:
                setTabAndColor(2);
                vp_main.setCurrentItem(2);
                pagetrhreefra.upDateKpitype();
                break;
            case R.id.rl_kpi:
                String type = SpUtils.getKpiStat(StatDialogAct.this);
                KpiDialog dialog = new KpiDialog(StatDialogAct.this, type).buidler();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                break;
            case R.id.ll_title:
                if(!isShow) {//第一次请求
                    iv_title.setImageDrawable(getResources().getDrawable(R.mipmap.up_jt));
                    screenRole(CadillacUtils.getCurruser().getDealerId() + "", "N");//换角色查看
                }else {
                    showPopuWindow();
                }
                break;

            case R.id.rl_finsh:
                finish();
                break;

            case R.id.rl_share:
                ShowShareDialog();
                break;

        }
    }

    /**
     * 分享dialog
     */
    private void ShowShareDialog() {
        ShareDialog dialog = new ShareDialog(StatDialogAct.this, biaoti, queryType).build();
        dialog.show();
    }

    /**
     * 获取第一级
     *
     * @param parentId
     * @param isDelete
     */
    private void screenRole(String parentId, String isDelete) {
        UserManager.getUserManager().screenRole(parentId, isDelete, new ResultCallback<ResultsNewBean<List<ScreenBean>>>() {
            @Override
            public void onError(int status, String errorMsg) {
                isShow=false;
                iv_title.setImageDrawable(getResources().getDrawable(R.mipmap.down_jt));
                MyToastUtils.showShortToast(StatDialogAct.this, errorMsg);
            }

            @Override
            public void onResponse(ResultsNewBean<List<ScreenBean>> response) {
                isShow=true;
                sclist = response.getObj();
                if (sclist != null && sclist.size() > 0) {//有
                    sclist.add(0, new ScreenBean("不限"));
                    showPopuWindow();//弹出对话框
                } else {//没有
                    iv_title.setImageDrawable(getResources().getDrawable(R.mipmap.down_jt));
                }
            }
        });
    }

    /**
     *
     */
    private void showPopuWindow() {
        View view = View.inflate(StatDialogAct.this, R.layout.popu, null);
        ListView lv_zb = (ListView) view.findViewById(R.id.lv_zb);
        lv_dq = (ListView) view.findViewById(R.id.lv_dq);
        lv_dq.setVisibility(View.GONE);
        LinearLayout ll_topno = (LinearLayout) view.findViewById(R.id.ll_topno);
        LinearLayout ll_diss = (LinearLayout) view.findViewById(R.id.ll_diss);//占位符
        // 实例化popupWindow
        popupWindow = new PopupWindow(view, AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT);
        //控制键盘是否可以获得焦点
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);//点击外部可以消失
        //设置popupWindow弹出窗体的背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable(null, ""));
        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);

        //获取xoff
        int xpos = manager.getDefaultDisplay().getWidth() / 2 - popupWindow.getWidth() / 2;
        //xoff,yoff基于anchor的左下角进行偏移。
        popupWindow.showAsDropDown(view_topline, 0, 0);
        lv_zb.setAdapter(new MyAdapter(sclist));
        lv_zb.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    tv_title.setText(CadillacUtils.getCurruser().getDealerName());
                    popupWindow.dismiss();
                    iv_title.setImageDrawable(getResources().getDrawable(R.mipmap.down_jt));
                    getDailyInfo(time, queryType, CadillacUtils.getCurruser().getDealerFullCode());
                } else {
                    getDqsc(sclist.get(i).getId(), "N", sclist.get(i).getName(), sclist.get(i).getFullCode());//获取大区下面的
                }
            }
        });


        ll_topno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                iv_title.setImageDrawable(getResources().getDrawable(R.mipmap.down_jt));
            }
        });

        ll_diss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                iv_title.setImageDrawable(getResources().getDrawable(R.mipmap.down_jt));
            }
        });

    }

    /**
     * 获取第二级
     *
     * @param parentId
     * @param isDelete
     */
    private void getDqsc(String parentId, String isDelete, final String name, final String fullcode) {
        UserManager.getUserManager().screenRole(parentId, isDelete, new ResultCallback<ResultsNewBean<List<ScreenBean>>>() {
            @Override
            public void onError(int status, String errorMsg) {
                popupWindow.dismiss();
                iv_title.setImageDrawable(getResources().getDrawable(R.mipmap.down_jt));
                MyToastUtils.showShortToast(StatDialogAct.this, errorMsg);
            }

            @Override
            public void onResponse(ResultsNewBean<List<ScreenBean>> response) {
                xqlist = response.getObj();
                if (xqlist != null && xqlist.size() > 0) {//说明有
                    xqlist.add(0, new ScreenBean("不限"));
                    lv_dq.setVisibility(View.VISIBLE);
                    lv_dq.setAdapter(new MyAdapter(xqlist));

                    //第二级
                    lv_dq.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            if (i == 0) {
                                tv_title.setText(name);
                                getDailyInfo(time, queryType, fullcode);
                            } else {
                                tv_title.setText(xqlist.get(i).getName());
                                getDailyInfo(time, queryType, xqlist.get(i).getFullCode());

                            }
                            popupWindow.dismiss();
                            iv_title.setImageDrawable(getResources().getDrawable(R.mipmap.down_jt));
                        }
                    });
                } else {//说明没有
                    popupWindow.dismiss();
                    tv_title.setText(name);
                    iv_title.setImageDrawable(getResources().getDrawable(R.mipmap.down_jt));
                    getDailyInfo(time, queryType, fullcode);
                }
            }
        });


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
     * 获取三屏数据
     */
    private void getDailyInfo(String dateStr, String queryType, String dealerCode) {
        final Dialog dialog = CProgressDialog.createLoadingDialog(StatDialogAct.this, false);
        dialog.show();
        UserManager.getUserManager().getDailyInfo(dateStr, queryType, dealerCode, new ResultCallback<ResultsNewBean<List<DatilBean>>>() {
            @Override
            public void onError(int status, String errorMsg) {
                dialog.dismiss();
                MyToastUtils.showShortToast(StatDialogAct.this, errorMsg);
            }

            @Override
            public void onResponse(ResultsNewBean<List<DatilBean>> response) {
                dialog.dismiss();
                list = response.getObj();
                if (list == null || list.size() == 0) {
                    MyToastUtils.showShortToast(StatDialogAct.this, "暂无数据");
                }
                Intent intent1 = new Intent(StatoneFra.UPDATEONEUI);
                Intent intent2 = new Intent(StattwoFra.UPDATETWOUI);
                Intent intent3 = new Intent(StatthreeFra.UPDATEUI);
                intent1.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) list);
                intent2.putExtra("list", (ArrayList<? extends Parcelable>) list);
                intent3.putExtra("list", (ArrayList<? extends Parcelable>) list);
                sendBroadcast(intent1);
                sendBroadcast(intent2);
                sendBroadcast(intent3);

            }
        });
    }


    class MyAdapter extends BaseAdapter {
        private List<ScreenBean> beanlist;

        public MyAdapter(List<ScreenBean> beanlist) {
            this.beanlist = beanlist;
        }

        @Override
        public int getCount() {
            return beanlist.size();
        }

        @Override
        public Object getItem(int i) {
            return beanlist.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = View.inflate(StatDialogAct.this, R.layout.item_lv_popu, null);
            TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
            if (beanlist != null && beanlist.size() > 0) {
                tv_name.setText(beanlist.get(i).getName());
            }
            return view;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SpUtils.setKpiStat(StatDialogAct.this, "0");//结束时候设为0
    }
}
