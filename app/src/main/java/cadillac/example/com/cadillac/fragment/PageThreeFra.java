package cadillac.example.com.cadillac.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import cadillac.example.com.cadillac.R;
import cadillac.example.com.cadillac.base.BaseFrag;
import cadillac.example.com.cadillac.utils.SpUtils;

/**
 * Created by iris on 2017/12/29.
 */

public class PageThreeFra extends BaseFrag{
    @ViewInject(R.id.vp_pageone)
    private ViewPager vp_pageone;
    @ViewInject(R.id.pageview_1)
    private View pageview_1;
    @ViewInject(R.id.pageview_2)
    private View pageview_2;
    @ViewInject(R.id.pageview_3)
    private View pageview_3;

    private List<Fragment> vplist;

    private StatoneFra statonefra;
    private StattwoFra stattwofra;
    private StatthreeFra statthreefra;
    private int index;
    private String queryType;
    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fra_pageone,null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        index=0;
        queryType=getArguments().getString("queryType");
        vplist=new ArrayList<>();
        statonefra=new StatoneFra();
        stattwofra=new StattwoFra();
        statthreefra=new StatthreeFra();
        Bundle bundle=new Bundle();
        bundle.putString("type","车展");
        bundle.putString("queryType",queryType);
        statonefra.setArguments(bundle);
        stattwofra.setArguments(bundle);
        statthreefra.setArguments(bundle);
        vplist.add(statonefra);
        vplist.add(stattwofra);
        vplist.add(statthreefra);

        vp_pageone.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return vplist.get(position);
            }

            @Override
            public int getCount() {
                return vplist.size();
            }
        });

        vp_pageone.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                index=position;
                hideTitle(position);
                SpUtils.setKpiStat(getActivity(),position+"");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        hideTitle(0);
        vp_pageone.setCurrentItem(0);
        vp_pageone.setOffscreenPageLimit(2);
    }

    private void hideTitle(int i) {
        pageview_1.setBackgroundResource(R.drawable.gray_yuan);
        pageview_2.setBackgroundResource(R.drawable.gray_yuan);
        pageview_3.setBackgroundResource(R.drawable.gray_yuan);
        if(i==0){
            pageview_1.setBackgroundResource(R.drawable.black_yuan);
        }else if(i==1){
            pageview_2.setBackgroundResource(R.drawable.black_yuan);
        }else if(i==2){
            pageview_3.setBackgroundResource(R.drawable.black_yuan);
        }
    }

    public void upDateKpitype() {
        SpUtils.setKpiStat(getActivity(),index+"");
    }
}
