package cadillac.example.com.cadillac.activity.admin;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;


import java.util.ArrayList;
import java.util.List;

import cadillac.example.com.cadillac.R;

import cadillac.example.com.cadillac.activity.StatDialogAct;
import cadillac.example.com.cadillac.base.BaseActivity;

import cadillac.example.com.cadillac.bean.ResultsNewBean;
import cadillac.example.com.cadillac.bean.ScreenBean;
import cadillac.example.com.cadillac.fragment.SubmitFra;
import cadillac.example.com.cadillac.http.Manage.UserManager;
import cadillac.example.com.cadillac.http.ResultCallback;
import cadillac.example.com.cadillac.utils.CadillacUtils;
import cadillac.example.com.cadillac.utils.MyToastUtils;

/**
 * 管理员详情activity
 * Created by bitch-1 on 2017/3/24.
 */

public class AdminInfoAct extends BaseActivity {
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
    @ViewInject(R.id.vp_info)
    private ViewPager vp_info;

    private String id;

    private List<Fragment>list;
    private SubmitFra qbfra;//全部
    private SubmitFra djfra;//递交
    private SubmitFra wdjfra;//递交

    private List<ScreenBean>sclist;
    private List<ScreenBean>djlist;
    private List<ScreenBean>wdjlist;

    @Override
    public void setLayout() {
        setContentView(R.layout.act_admininfo);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        id=getIntent().getExtras().getString("id");
        setTabAndColor(0);//设置前面的改变
        list=new ArrayList<>();
        qbfra=new SubmitFra();//全部
        djfra=new SubmitFra();//已经提交
        wdjfra=new SubmitFra();//未递交
//        Bundle bundle1=new Bundle();
//        bundle1.putString("type","0");
//        Bundle bundle2=new Bundle();
//        bundle2.putString("type","1");
//        Bundle bundle3=new Bundle();
//        bundle3.putString("type","2");
//        qbfra.setArguments(bundle1);
//        djfra.setArguments(bundle2);
//        wdjfra.setArguments(bundle3);

        list.add(qbfra);
        list.add(djfra);
        list.add(wdjfra);

        vp_info.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });

        vp_info.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
             setTabAndColor(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vp_info.setOffscreenPageLimit(2);
        vp_info.setCurrentItem(0);
        screenRole(id+"","N");//换角色查看

    }


    /**
     * 获取第一级
     * @param parentId
     * @param isDelete
     */
    private void screenRole(String parentId,String isDelete) {
        UserManager.getUserManager().screenRole(parentId, isDelete, new ResultCallback<ResultsNewBean<List<ScreenBean>>>() {
            @Override
            public void onError(int status, String errorMsg) {
                MyToastUtils.showShortToast(AdminInfoAct.this,errorMsg);
            }

            @Override
            public void onResponse(ResultsNewBean<List<ScreenBean>> response) {
                sclist=response.getObj();
                djlist=new ArrayList<>();
                wdjlist=new ArrayList<>();
                if(sclist!=null&&sclist.size()>0) {
                    for(int i=0;i<sclist.size();i++){
                        if(TextUtils.isEmpty(sclist.get(i).getSubmitDailyData())){//如果是null判断为空未提交
                            wdjlist.add(sclist.get(i));
                        }else {
                            if (sclist.get(i).getSubmitDailyData().equals("N")) {
                                wdjlist.add(sclist.get(i));
                            }else {
                                djlist.add(sclist.get(i));
                            }
                        }

                    }
                    qbfra.upDateinfo(sclist);
                    djfra.upDateinfo(djlist);
                    wdjfra.upDateinfo(wdjlist);
                }
            }
        });
    }





    @OnClick({R.id.tv_qb,R.id.tv_zt,R.id.tv_cz,R.id.rl_finsh})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.tv_qb:
                setTabAndColor(0);
                vp_info.setCurrentItem(0);
                break;
            case R.id.tv_zt://提交的
                setTabAndColor(1);
                vp_info.setCurrentItem(1);
                break;
            case R.id.tv_cz://未提交
                setTabAndColor(2);
                vp_info.setCurrentItem(2);
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
        view_qb.setBackgroundColor(Color.parseColor("#cccccc"));
        view_zt.setBackgroundColor(Color.parseColor("#cccccc"));
        view_cz.setBackgroundColor(Color.parseColor("#cccccc"));
        switch (i) {
            case 0:
                tv_qb.setTextColor(Color.parseColor("#8c1229"));
                view_qb.setBackgroundColor(Color.parseColor("#8A152B"));
                break;

            case 1:
                tv_zt.setTextColor(Color.parseColor("#8c1229"));
                view_zt.setBackgroundColor(Color.parseColor("#8A152B"));
                break;
            case 2:
                tv_cz.setTextColor(Color.parseColor("#8c1229"));
                view_cz.setBackgroundColor(Color.parseColor("#8A152B"));
                break;
        }

    }


}
