package cadillac.example.com.cadillac.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lidroid.xutils.view.annotation.event.OnClick;

import cadillac.example.com.cadillac.R;
import cadillac.example.com.cadillac.base.BaseActivity;
import cadillac.example.com.cadillac.utils.MyLogUtils;

/**
 * 帮助界面
 * Created by bitch-1 on 2017/3/17.
 */

public class HelpAct extends BaseActivity {
    @Override
    public void setLayout() {
        setContentView(R.layout.act_help);

    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("帮助");
        LoginAct.isshow=true;

    }

    @OnClick({R.id.tv_gx,R.id.tv_tj,R.id.tv_zt,R.id.tv_sj,R.id.tv_xx,R.id.tv_sp,R.id.rl_finsh})
    public void OnClick(View view){
        Intent intent=new Intent(HelpAct.this,ImageAct.class);
        switch (view.getId()){
            case R.id.tv_gx:
                intent.putExtra("type",1);
                startActivity(intent);
                break;
            case R.id.tv_tj:
                intent.putExtra("type",2);
                startActivity(intent);
                break;
            case R.id.tv_zt:
                intent.putExtra("type",3);
                startActivity(intent);
                break;
            case R.id.tv_sj:
                intent.putExtra("type",4);
                startActivity(intent);
                break;
            case R.id.tv_xx:
                intent.putExtra("type",5);
                startActivity(intent);
                break;
            case R.id.tv_sp:
                intent.putExtra("type",6);
                startActivity(intent);
                break;
            case R.id.rl_finsh:
                finish();
                break;
        }
    }
}
