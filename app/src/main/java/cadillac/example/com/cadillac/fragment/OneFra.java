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
 * 首页第一个页面
 * Created by bitch-1 on 2017/10/12.
 */

public class OneFra extends BaseFrag{
    @ViewInject(R.id.vp_one)
    private ViewPager vp_one;
    @ViewInject(R.id.view_1)
    private View view_1;
    @ViewInject(R.id.view_2)
    private View view_2;
    @ViewInject(R.id.view_3)
    private View view_3;

    private List<Fragment>vplist;
    private String from;

    private Viewonefra viewonefra;
    private Viewtwofra twofra;
    private Viewthreefra threefra;

    private int index;


    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fra_one,null);
    }


    @Override
    public void initData(Bundle savedInstanceState) {
        from=getArguments().getString("from");
        index=0;
        vplist=new ArrayList<>();
        viewonefra=new Viewonefra();
        twofra=new Viewtwofra();
        threefra=new Viewthreefra();
        Bundle bundle=new Bundle();
        bundle.putString("type","全部");
        viewonefra.setArguments(bundle);
        twofra.setArguments(bundle);
        threefra.setArguments(bundle);
        vplist.add(viewonefra);
        vplist.add(twofra);
        vplist.add(threefra);
        vp_one.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return vplist.get(position);
            }

            @Override
            public int getCount() {
                return vplist.size();
            }
        });

        vp_one.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                index=position;
                hideTitle(position);
                if(from.equals("new")) {
                    SpUtils.setKpitype(getActivity(), position + "");
                }else {
                    SpUtils.setKpiinfo(getActivity(), position + "");

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        hideTitle(0);
        vp_one.setCurrentItem(0);
        vp_one.setOffscreenPageLimit(2);
    }



    private void hideTitle(int i) {
        view_1.setBackgroundResource(R.drawable.gray_yuan);
        view_2.setBackgroundResource(R.drawable.gray_yuan);
        view_3.setBackgroundResource(R.drawable.gray_yuan);
        if(i==0){
            view_1.setBackgroundResource(R.drawable.black_yuan);
        }else if(i==1){
            view_2.setBackgroundResource(R.drawable.black_yuan);
        }else if(i==2){
            view_3.setBackgroundResource(R.drawable.black_yuan);
        }
    }


    public void upDateType() {
        if(from.equals("new")) {
            SpUtils.setKpitype(getActivity(), index + "");
        }else {
            SpUtils.setKpiinfo(getActivity(), index + "");
        }
    }
}