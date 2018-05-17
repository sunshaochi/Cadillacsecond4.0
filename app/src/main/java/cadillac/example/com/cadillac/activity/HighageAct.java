package cadillac.example.com.cadillac.activity;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.ist.cadillacpaltform.UI.fragment.PlatformFragment;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import cadillac.example.com.cadillac.R;
import cadillac.example.com.cadillac.base.BaseActivity;

/**
 * Created by bitch-1 on 2017/5/19.
 */

public class HighageAct extends BaseActivity {
    @ViewInject(R.id.rl_finsh)
    private RelativeLayout rl_finsh;
    @ViewInject(R.id.fra_dealers)
    private FrameLayout fra_dealers;
    private FragmentManager manager;
    private PlatformFragment mPlatformFragment;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_highage);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        if(mPlatformFragment==null){
            mPlatformFragment=new PlatformFragment();
            transaction.add(R.id.fra_dealers,mPlatformFragment);
        }else {
            transaction.show(mPlatformFragment);
        }
        transaction.commit();

    }

    @OnClick({R.id.rl_finsh})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.rl_finsh:
                finish();
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mPlatformFragment!=null){
            mPlatformFragment=null;
        }
    }
}
