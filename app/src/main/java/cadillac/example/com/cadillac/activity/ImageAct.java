package cadillac.example.com.cadillac.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import cadillac.example.com.cadillac.R;
import cadillac.example.com.cadillac.base.BaseActivity;

/**
 *帮助的点击后的界面
 * Created by bitch-1 on 2017/4/17.
 */
public class ImageAct extends BaseActivity {
    @ViewInject(R.id.iv_help)
    private ImageView iv_help;
    private int type;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_image);
    }

    @Override
    public void init(Bundle savedInstanceState) {
     type=getIntent().getExtras().getInt("type");
        switch (type){
            case 1:
                iv_help.setImageResource(R.mipmap.helpone);
                break;
            case 2:
                iv_help.setImageResource(R.mipmap.helptwo);
                break;
            case 3:
                iv_help.setImageResource(R.mipmap.helpthree);
                break;
            case 4:
                iv_help.setImageResource(R.mipmap.helpfour);
                break;
            case 5:
                iv_help.setImageResource(R.mipmap.helpfive);
                break;
            case 6:
                iv_help.setImageResource(R.mipmap.helpsix);
                break;
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
}
