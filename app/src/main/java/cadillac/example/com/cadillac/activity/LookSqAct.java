package cadillac.example.com.cadillac.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;
import java.util.List;

import cadillac.example.com.cadillac.R;
import cadillac.example.com.cadillac.adapter.SqAdapter;
import cadillac.example.com.cadillac.base.BaseActivity;
import cadillac.example.com.cadillac.bean.CheckApplyBean;
import cadillac.example.com.cadillac.bean.ResultsNewBean;
import cadillac.example.com.cadillac.bean.SqBean;
import cadillac.example.com.cadillac.http.Manage.UserManager;
import cadillac.example.com.cadillac.http.ResultCallback;
import cadillac.example.com.cadillac.utils.CadillacUtils;
import cadillac.example.com.cadillac.utils.MyToastUtils;
import cadillac.example.com.cadillac.utils.SpUtils;

/**
 * 查看申请列表
 * Created by bitch-1 on 2017/5/4.
 */

public class LookSqAct extends BaseActivity implements SqAdapter.Update {
    @ViewInject(R.id.lv_sq)
    private ListView lv_sq;//查看申请的列表

    private List<CheckApplyBean>list;
    private SqAdapter sqAdapter;
    @Override
    public void setLayout() {
      setContentView(R.layout.act_looksq);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("查看申请");

        getDate(CadillacUtils.getCurruser().getAdmin());
    }

    private void getDate(String admin) {
        UserManager.getUserManager().getlist(admin, new ResultCallback<ResultsNewBean<List<CheckApplyBean>>>() {
            @Override
            public void onError(int status, String errorMsg) {
                MyToastUtils.showShortToast(LookSqAct.this,errorMsg);

            }

            @Override
            public void onResponse(ResultsNewBean<List<CheckApplyBean>> response) {
                 list=response.getObj();
                 if(list!=null&&list.size()>0){
                     sqAdapter=new SqAdapter(LookSqAct.this,list);
                     sqAdapter.setUpdate(LookSqAct.this);//adpter里面加的
                     lv_sq.setAdapter(sqAdapter);
                 }else {
                     MyToastUtils.showShortToast(LookSqAct.this,"暂时无申请");
                 }


            }
        });
    }

    @OnClick({R.id.view_back})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.view_back:
                finish();
        }

    }


    @Override
    public void getdate() {
        getDate(CadillacUtils.getCurruser().getAdmin());
    }
}
