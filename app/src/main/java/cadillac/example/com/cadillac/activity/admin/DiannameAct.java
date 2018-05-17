package cadillac.example.com.cadillac.activity.admin;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;
import java.util.List;

import cadillac.example.com.cadillac.R;
import cadillac.example.com.cadillac.adapter.AdminIfAdt;
import cadillac.example.com.cadillac.base.BaseActivity;
import cadillac.example.com.cadillac.bean.InfoBean;
import cadillac.example.com.cadillac.http.Manage.UserManager;
import cadillac.example.com.cadillac.http.ResultCallback;
import cadillac.example.com.cadillac.utils.MyToastUtils;

/**
 * Created by bitch-1 on 2017/3/25.
 */
public class DiannameAct extends BaseActivity {
    @ViewInject(R.id.lv_info)
    private ListView lv_info;
    @ViewInject(R.id.tv_quanbu)
    private TextView tv_quanbu;
    @ViewInject(R.id.tv_tj)
    private TextView tv_tj;
    @ViewInject(R.id.tv_wtj)
    private TextView tv_wtj;

    private AdminIfAdt adminIfAdt;
    private String time;//传递过来的时间
    private String name;//传递过来的经销售名称
    private Gson gson;

    List<String> statelist=new ArrayList<String>();
    List<String>namelist=new ArrayList<String>();
    List<String>tlelist=new ArrayList<String>();

    List<String>yesstatelist=new ArrayList<String>();//已经提交的
    List<String>yesnamelist=new ArrayList<String>();
    List<String>yestlelist=new ArrayList<String>();
    private InfoBean infobean;

    private InfoBean tjinfobean;//已经提交的
    private InfoBean uninfobean;//未提交的
    @Override
    public void setLayout() {
        setContentView(R.layout.act_dianname);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        slectbtncolor(0);
//        adminIfAdt=new AdminIfAdt(DiannameAct.this.);
//        lv_info.setAdapter(adminIfAdt);

        time=getIntent().getStringExtra("time");//时间
        name=getIntent().getStringExtra("name");//名字
        gson=new Gson();
        tjinfobean=new InfoBean();
        uninfobean=new InfoBean();

        getInfo(name,"小区经理",time);//通过每一个不同的name(最初是大区经理name,后来是name)获取详情


    }

    private void getInfo(String name, String role, String date) {
        UserManager.getUserManager().getinfo(name, role, date, new ResultCallback<String>() {
            @Override
            public void onError(int status, String errorMsg) {
                MyToastUtils.showShortToast(DiannameAct.this,errorMsg);
            }

            @Override
            public void onResponse(String response) {
                List<InfoBean> list = gson.fromJson(response,
                        new TypeToken<List<InfoBean>>() {
                        }.getType());

                if(list.size()==0) {
                    MyToastUtils.showShortToast(DiannameAct.this,"后台异常");
                }else {
                    infobean=list.get(0);
                    adminIfAdt=new AdminIfAdt(DiannameAct.this,infobean,time,2);
                    lv_info.setAdapter(adminIfAdt);

                    statelist.clear();
                    namelist.clear();
                    tlelist.clear();
                    yesnamelist.clear();
                    yesstatelist.clear();
                    yestlelist.clear();
                    for (int i=0;i<infobean.getName().size();i++){
                        if(infobean.getState().get(i).toString().equals("0")){
                            statelist.add(infobean.getState().get(i));
                            namelist.add(infobean.getName().get(i));
                            tlelist.add(infobean.getTel().get(i));
                            uninfobean.setState(statelist);
                            uninfobean.setName(namelist);
                            uninfobean.setTel(tlelist);
                        }else {
                            yesstatelist.add(infobean.getState().get(i));
                            yesnamelist.add(infobean.getName().get(i));
                            yestlelist.add(infobean.getTel().get(i));
                            tjinfobean.setState(yesstatelist);
                            tjinfobean.setName(yesnamelist);
                            tjinfobean.setTel(yestlelist);

                        }
                    }
                }

            }
        });
    }

    @OnClick({R.id.tv_quanbu,R.id.tv_tj,R.id.tv_wtj,R.id.view_back})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.tv_quanbu:
                slectbtncolor(0);
                adminIfAdt.notify(infobean);
                break;
            case R.id.tv_tj:
                slectbtncolor(1);
                adminIfAdt.notify(tjinfobean);
                break;
            case R.id.tv_wtj:
                slectbtncolor(2);
                adminIfAdt.notify(uninfobean);
                break;
            case R.id.view_back:
                finish();
                break;
        }

    }

    /**
     * 变化btn颜色
     * @param i
     */
    private void slectbtncolor(int i) {
        tv_quanbu.setBackgroundResource(R.drawable.left_yuan);
        tv_quanbu.setTextColor(Color.parseColor("#333333"));
        tv_tj.setBackgroundColor(Color.parseColor("#00000000"));
        tv_tj.setTextColor(Color.parseColor("#333333"));
        tv_wtj.setBackgroundResource(R.drawable.right_yuan);
        tv_wtj.setTextColor(Color.parseColor("#333333"));
        if(i==0){
            tv_quanbu.setBackgroundResource(R.drawable.left_black_yuan);
            tv_quanbu.setTextColor(Color.parseColor("#ffffff"));
        }
        if(i==1){
            tv_tj.setBackgroundColor(Color.parseColor("#535353"));
            tv_tj.setTextColor(Color.parseColor("#ffffff"));
        }
        if(i==2){
            tv_wtj.setBackgroundResource(R.drawable.right_black_yuan);
            tv_wtj.setTextColor(Color.parseColor("#ffffff"));
        }
    }


}

