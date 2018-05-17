package cadillac.example.com.cadillac.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.lidroid.xutils.view.annotation.ViewInject;

import cadillac.example.com.cadillac.R;
import cadillac.example.com.cadillac.base.BaseActivity;
import cadillac.example.com.cadillac.http.Manage.UserManager;

/**
 * 我的喜好
 * Created by bitch-1 on 2017/4/17.
 */
public class MyloveAct extends BaseActivity {
    @ViewInject(R.id.lv)
    private ListView lv;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_mylove);
    }

    @Override
    public void init(Bundle savedInstanceState) {
      getType();//获取类型
    }

    private void getType() {
    }
}
