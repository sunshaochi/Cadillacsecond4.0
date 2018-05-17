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
import cadillac.example.com.cadillac.bean.UserModle;
import cadillac.example.com.cadillac.http.Manage.UserManager;
import cadillac.example.com.cadillac.http.ResultCallback;
import cadillac.example.com.cadillac.utils.MyLogUtils;
import cadillac.example.com.cadillac.utils.MyToastUtils;

/**
 * 各大区点击进去后的销售经理列表
 * Created by bitch-1 on 2017/3/25.
 */
public class PeopleAct extends BaseActivity {
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

    List<String>statelist=new ArrayList<String>();
    List<String>namelist=new ArrayList<String>();
    List<String>tlelist=new ArrayList<String>();

    List<String>yesstatelist=new ArrayList<String>();
    List<String>yesnamelist=new ArrayList<String>();
    List<String>yestlelist=new ArrayList<String>();
    private InfoBean infobean;

    private InfoBean tjinfobean;//已经提交的
    private InfoBean uninfobean;//未提交的
    private String role;//判断职务来穿（接口这样设计的）

    @Override
    public void setLayout() {
        setContentView(R.layout.act_people);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        slectbtncolor(0);
        time=getIntent().getStringExtra("time");//时间
        name=getIntent().getStringExtra("name");//名字
        gson=new Gson();
        tjinfobean=new InfoBean();
        uninfobean=new InfoBean();

        if(UserModle.getUserBean().getRole().equals("总部人员")||UserModle.getUserBean().getRole().equals("管理员")){
            role="大区经理";
        }

        if(UserModle.getUserBean().getRole().equals("大区经理")||UserModle.getUserBean().getRole().equals("MSS人员")){
            role="小区经理";
        }

        if(UserModle.getUserBean().getRole().equals("小区经理")||UserModle.getUserBean().getRole().equals("集团人员")){
            role="经销商总经理";
        }



        getInfo(name,role,time);//通过每一个不同的name(最初是金销售name,后来是name)获取详情


//        adminIfAdt=new AdminIfAdt(getApplicationContext());
//        lv_info.setAdapter(adminIfAdt);

        lv_info.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(role.equals("小区经理")){//大区经理进来

                }else {
                    Bundle bundle = new Bundle();
                    bundle.putString("time", time);
                    bundle.putString("name", adapterView.getItemAtPosition(i).toString());
                    openActivity(DiannameAct.class, bundle);
                }
            }
        });

    }

    /**
     * 通过name去获取详情
     * @param name
     * @param role
     * @param date
     */
    private void getInfo(String name, String role, String date) {
        UserManager.getUserManager().getinfo(name, role, date, new ResultCallback<String>() {
            @Override
            public void onError(int status, String errorMsg) {
                MyToastUtils.showShortToast(PeopleAct.this,errorMsg);

            }

            @Override
            public void onResponse(String response) {
                List<InfoBean> list = gson.fromJson(response,
                        new TypeToken<List<InfoBean>>() {
                        }.getType());

                if(list.size()==0) {
                    MyToastUtils.showShortToast(PeopleAct.this,"后台异常");
                }else {
                    infobean=list.get(0);
                    adminIfAdt=new AdminIfAdt(PeopleAct.this,infobean,time,1);
                    lv_info.setAdapter(adminIfAdt);

                    statelist.clear();
                    namelist.clear();
                    tlelist.clear();
                    yesstatelist.clear();
                    yesnamelist.clear();
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

