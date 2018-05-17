package cadillac.example.com.cadillac;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import com.lidroid.xutils.view.annotation.ViewInject;

import cadillac.example.com.cadillac.base.BaseActivity;
import cadillac.example.com.cadillac.fragment.DataFra;
import cadillac.example.com.cadillac.fragment.DealersFra;
import cadillac.example.com.cadillac.fragment.NewDataFra;
import cadillac.example.com.cadillac.fragment.SettFra;
import cadillac.example.com.cadillac.fragment.StatFra;
import cadillac.example.com.cadillac.utils.CadillacUtils;

public class MainActivity extends BaseActivity implements NewDataFra.ShowEdit,DataFra.ShowNewFra{

    @ViewInject(R.id.rg_tab)
    private RadioGroup ra_tab;
    @ViewInject(R.id.rb_data)
    private RadioButton rb_data;
    @ViewInject(R.id.rb_dealer)
    private RadioButton rb_dealer;

    private FragmentManager fragmentManager;
    private DataFra datafra;//数据
    private StatFra statfra;//报表
    private SettFra settfra;//设置
    private DealersFra dealersfra;//销售类frag
    private NewDataFra newdatafra;


    /**
     * 按两次退出键时间小于2秒退出
     */
    private final static long WAITTIME = 2000;
    private long touchTime = 0;

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_main);

    }

    @Override
    public void init(Bundle savedInstanceState) {
        if(CadillacUtils.getCurruser().getRoleName().equals("RPC人员")||CadillacUtils.getCurruser().getRoleName().equals("CRPC人员")){
            rb_dealer.setVisibility(View.GONE);
        }else{
            rb_dealer.setVisibility(View.VISIBLE);
        }

        fragmentManager=getSupportFragmentManager();//申明
        rb_data.setChecked(true);
        showFrag(0);
        ra_tab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (radioGroup.getCheckedRadioButtonId()){
                    case R.id.rb_data://数据
                        showFrag(0);//显示不同fragmenet
                        break;
                    case R.id.rb_stat://报表
                        showFrag(1);
                        break;
                    case R.id.rb_dealer://经销商
                        showFrag(2);
                        break;
                    case R.id.rb_more://设置(更多)
                        showFrag(3);
                        break;
                }
            }
        });


    }

    private void showFrag(int i) {
        FragmentTransaction transaction= fragmentManager.beginTransaction();
        hideFragment(transaction);//影长fragment
        switch (i){
            case 0:
                if(newdatafra==null){
                    newdatafra=new NewDataFra();
                    transaction.add(R.id.fra_main,newdatafra);
                }else {
                    transaction.show(newdatafra);
                }
                break;

            case 1:
                if(statfra==null){
                    statfra=new StatFra();
                    transaction.add(R.id.fra_main,statfra);
                }else {
                    transaction.show(statfra);
                }
                break;


            case 2:
                if(dealersfra==null){
                    dealersfra=new DealersFra();
                    transaction.add(R.id.fra_main,dealersfra);
                }else {
                    transaction.show(dealersfra);
                }
                break;

            case 3:
                if(settfra==null){
                    settfra=new SettFra();
                    transaction.add(R.id.fra_main,settfra);
                }else {
                    transaction.show(settfra);
                }
                break;
            case 4:
                if(datafra==null){//上传那个fragment
                    datafra=new DataFra();
                    transaction.add(R.id.fra_main,datafra);
                }else {
                    transaction.show(datafra);
                }
                break;
        }

        transaction.commit();
    }



    /**
     * 隐藏
     * @param transaction
     */
    private void hideFragment(FragmentTransaction transaction) {
        if (datafra != null) {
            transaction.hide(datafra);
        }
        if(newdatafra!=null){
            transaction.hide(newdatafra);
        }
        if (statfra != null) {
            transaction.hide(statfra);
        }
        if (settfra != null) {
            transaction.hide(settfra);
        }
        if (dealersfra != null) {
            transaction.hide(dealersfra);
        }

    }






    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if ((currentTime - touchTime) >= WAITTIME) {
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            touchTime = currentTime;
        } else {
            moveTaskToBack(false);
        }
    }

    /**
     * 实现显示编辑的fragment
     */
    @Override
    public void showEdit() {
        showFrag(4);//显示编辑fragment
    }

    @Override
    public void shownewfra() {
     showFrag(0);//显示
    }
}

