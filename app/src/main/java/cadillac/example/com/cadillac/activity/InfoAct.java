package cadillac.example.com.cadillac.activity;

import android.os.Bundle;

import cadillac.example.com.cadillac.R;
import cadillac.example.com.cadillac.base.BaseActivity;

/**高库龄详情
 * 由第三方面去做
 * Created by bitch-1 on 2017/3/18.
 */
public class InfoAct extends BaseActivity {
    @Override
    public void setLayout() {
        setContentView(R.layout.act_info);



    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("详情");

    }
}
