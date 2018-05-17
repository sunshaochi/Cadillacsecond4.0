package cadillac.example.com.cadillac.activity.admin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;
import java.util.List;

import cadillac.example.com.cadillac.R;
import cadillac.example.com.cadillac.base.BaseActivity;
import cadillac.example.com.cadillac.fragment.adminfra.FristadminFra;
import cadillac.example.com.cadillac.fragment.adminfra.SecondadminFra;

/**
 *点击感叹号后的数据
 * Created by bitch-1 on 2017/3/24.
 */
public class EachdataAct extends BaseActivity {
    @ViewInject(R.id.vp_stat)
    private ViewPager vp_stat;
    @ViewInject(R.id.view_1)
    private View view_1;
    @ViewInject(R.id.view_2)
    private View view_2;
    @ViewInject(R.id.rl_zt)
    private RelativeLayout rl_zt;
    @ViewInject(R.id.rl_cz)
    private RelativeLayout rl_cz;
    @ViewInject(R.id.tv_top)
    private TextView tv_top;
    private String name;
    private String time;
    private String role;//


    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragments = new ArrayList<Fragment>();
    @Override
    public void setLayout() {
        setContentView(R.layout.act_eachdata);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        name=getIntent().getStringExtra("name");
        time=getIntent().getStringExtra("time");
        role=getIntent().getStringExtra("role");

        if(!TextUtils.isEmpty(name)){
            tv_top.setText(name);
        }
        HideTitle(0);
        FristadminFra fristadminFra=new FristadminFra();//第一个fra
        SecondadminFra secondadminFra=new SecondadminFra();//第二个fra
        Bundle bundle=new Bundle();
        if(!TextUtils.isEmpty(name)) {
            bundle.putString("name", name);
        }else {
            bundle.putString("name", "");
        }

        bundle.putString("time",time);
        if(!TextUtils.isEmpty(role)) {
            bundle.putString("role", role);
        }else {
            bundle.putString("role","");
        }
        fristadminFra.setArguments(bundle);
        secondadminFra.setArguments(bundle);
        mFragments.add(fristadminFra);
        mFragments.add(secondadminFra);

        mAdapter=new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        };

        vp_stat.setAdapter(mAdapter);
        vp_stat.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        HideTitle(0);//隐藏车展和展厅标题框和下面圆点变色
                        break;
                    case 1:
                        HideTitle(1);//隐藏车展和展厅标题框和下面圆点变色
                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



    }

    /**
     *
     */
    private void HideTitle(int i) {
        rl_cz.setVisibility(View.GONE);
        rl_zt.setVisibility(View.GONE);
        view_1.setBackgroundResource(R.drawable.gray_yuan);
        view_2.setBackgroundResource(R.drawable.gray_yuan);
        if(i==0){
            rl_zt.setVisibility(View.VISIBLE);
            view_1.setBackgroundResource(R.drawable.black_yuan);
        }else if(i==1){
            rl_cz.setVisibility(View.VISIBLE);
            view_2.setBackgroundResource(R.drawable.black_yuan);
        }
    }

    @OnClick({R.id.view_back})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.view_back:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
