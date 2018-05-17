package cadillac.example.com.cadillac.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import cadillac.example.com.cadillac.R;
import cadillac.example.com.cadillac.adapter.BianjiAdap;
import cadillac.example.com.cadillac.adapter.BianjisenAdap;
import cadillac.example.com.cadillac.adapter.RetailfoAdpt;
import cadillac.example.com.cadillac.base.BaseActivity;
import cadillac.example.com.cadillac.bean.JxsBianjiBean;
import cadillac.example.com.cadillac.bean.JxsBjListBean;
import cadillac.example.com.cadillac.bean.ListBean;
import cadillac.example.com.cadillac.bean.ResultsBean;
import cadillac.example.com.cadillac.bean.ResultsNewBean;
import cadillac.example.com.cadillac.bean.RetailfoBean;
import cadillac.example.com.cadillac.bean.UpLoadBean;
import cadillac.example.com.cadillac.bean.UpjxsLoadBean;
import cadillac.example.com.cadillac.http.Manage.UserManager;
import cadillac.example.com.cadillac.http.ResultCallback;
import cadillac.example.com.cadillac.utils.CadillacUtils;
import cadillac.example.com.cadillac.utils.GsonUtils;
import cadillac.example.com.cadillac.utils.MyLogUtils;
import cadillac.example.com.cadillac.utils.MyToastUtils;
import cadillac.example.com.cadillac.utils.SpUtils;
import cadillac.example.com.cadillac.view.MyListview;

/**
 * Created by bitch-1 on 2017/5/22.
 */

public class BianjiAct extends BaseActivity implements BianjiAdap.Uplist,BianjisenAdap.Notlist {
    @ViewInject(R.id.lv_bianji)
    private MyListview lv_bianji;
    private BianjiAdap adapter;
    private BianjisenAdap senadapter;
    @ViewInject(R.id.iv_finsh)
    private ImageView iv_finsh;
    @ViewInject(R.id.ll_mess)
    private LinearLayout ll_mess;
    @ViewInject(R.id.tv_bname)
    private TextView tv_bname;
    @ViewInject(R.id.tv_bcode)
    private TextView tv_bcode;
    @ViewInject(R.id.tv_btime)
    private TextView tv_btime;
    @ViewInject(R.id.rl_totle)
    private RelativeLayout rl_totle;//总计哪行布局裸车毛利进来隐藏
    @ViewInject(R.id.tv_totlenum)
    private TextView tv_totlenum;//总计车数目
    private String type;
    private RetailfoBean retailfoBean;
    private List<ListBean>list;
    private List<JxsBjListBean>luolist;//角色为经销商的裸车毛利编辑界面
    private String inputTimeId;//上传时候需要带的id

    private UpLoadBean uploadbean;//保存之后需要上传的对象

    private UpjxsLoadBean upjxsloadbean;//角色为经销商的裸车毛利保存之后需要上传的对象

    private JxsBianjiBean jxsBianjiBean;//角色为经销商的裸车毛利编辑界面

    @Override
    public void setLayout() {
        setContentView(R.layout.act_bianji);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        View view=View.inflate(this,R.layout.head_retail,null);
        TextView tv_sm= (TextView) view.findViewById(R.id.tv_sm);
        TextView tv_zh= (TextView) view.findViewById(R.id.tv_zh);
        tv_sm.setVisibility(View.GONE);

        type=getIntent().getExtras().getString("type");
        if(!TextUtils.isEmpty(type)){
            if(type.equals("0")){//经销商角色经销商零售预测进来
                uploadbean=new UpLoadBean();
                rl_totle.setVisibility(View.VISIBLE);//总计哪行显示
                ll_mess.setVisibility(View.GONE);
                tv_zh.setText("当月零售预测数");
                getRetailfolist(CadillacUtils.getCurruser().getUserName());//获取零售预测详情
                setTopTitle("经销商零售预测编辑");

            }else if(type.equals("1")){//mac角色从mac零售预测进来
                uploadbean=new UpLoadBean();//当点击保存时候要上传的对象
                rl_totle.setVisibility(View.VISIBLE);//总计哪行显示
                ll_mess.setVisibility(View.GONE);
                tv_zh.setText("当月零售预测数");
                setTopTitle("MAC零售预测编辑");
                getMacinfo(CadillacUtils.getCurruser().getUserName());//获取mac角色的mac零售预测

            }else if(type.equals("3")){//角色为经销商的从裸车毛利进来
                upjxsloadbean=new UpjxsLoadBean();
                rl_totle.setVisibility(View.GONE);//总计哪行隐藏
                ll_mess.setVisibility(View.VISIBLE);
                tv_sm.setVisibility(View.VISIBLE);
                tv_sm.setText("裸车毛利");
                tv_zh.setText("综合毛利");
                setTopTitle("裸车毛利编辑");
                getEditorLm(CadillacUtils.getCurruser().getUserName());//获取经销商角色的裸车毛利编辑数据

            }
            lv_bianji.addHeaderView(view);
        }


    }

    /**角色为经销商的裸车毛利编辑界面
     * @param userName
     */
    private void getEditorLm(String userName) {
        UserManager.getUserManager().getEditorLm(userName, new ResultCallback<ResultsNewBean<JxsBianjiBean>>() {
            @Override
            public void onError(int status, String errorMsg) {
                MyToastUtils.showShortToast(BianjiAct.this,errorMsg);

            }

            @Override
            public void onResponse(ResultsNewBean<JxsBianjiBean> response) {
                jxsBianjiBean=response.getObj();
                if(jxsBianjiBean!=null){
                    if(!TextUtils.isEmpty(jxsBianjiBean.getDealerName())){
                        tv_bname.setText(jxsBianjiBean.getDealerName());
                    }
                    if(TextUtils.isEmpty(jxsBianjiBean.getDealerCode())){
                        tv_bcode.setVisibility(View.GONE);
                    }else {
                        tv_bcode.setVisibility(View.VISIBLE);
                        tv_bcode.setText(jxsBianjiBean.getDealerCode());
                    }
                    if(!TextUtils.isEmpty(jxsBianjiBean.getYearMonth())){
                        tv_btime.setText(jxsBianjiBean.getYearMonth());
                    }
                    if(!TextUtils.isEmpty(jxsBianjiBean.getInputTimeId())){
                        inputTimeId=jxsBianjiBean.getInputTimeId();
                    }

                    luolist=jxsBianjiBean.getList();
                    if(luolist!=null&&luolist.size()>0){
                        senadapter=new BianjisenAdap(BianjiAct.this,luolist);//因为list里面返回一个字段不一样
                        senadapter.setNotlist(BianjiAct.this);
                        lv_bianji.setAdapter(senadapter);
                        }


                }


            }
        });

    }

    /**角色为mac的mac零售预测
     * @param userName
     */
    private void getMacinfo(String userName) {
        UserManager.getUserManager().getMacRetaInfo(userName, new ResultCallback<ResultsNewBean<RetailfoBean>>() {
            @Override
            public void onError(int status, String errorMsg) {
                MyToastUtils.showShortToast(BianjiAct.this,errorMsg);
            }

            @Override
            public void onResponse(ResultsNewBean<RetailfoBean> response) {
                retailfoBean=response.getObj();
                if(retailfoBean!=null){
                    tv_bname.setText(retailfoBean.getDealerName());
                    if(TextUtils.isEmpty(retailfoBean.getDealerCode())){
                        tv_bcode.setVisibility(View.GONE);
                    }else {
                        tv_bcode.setVisibility(View.VISIBLE);
                        tv_bcode.setText(retailfoBean.getDealerCode());
                    }
                    tv_btime.setText(retailfoBean.getYearMonth());
                    inputTimeId=retailfoBean.getInputTimeId();
                    list=retailfoBean.getList();
                    if(list!=null&&list.size()!=0){
                        int totle=0;
                        String num;
                        for (int i=0;i<list.size();i++){
                            if(TextUtils.isEmpty(list.get(i).getPridictNum())){
                                num="0";
                            }else {
                                num=list.get(i).getPridictNum();
                            }
                            totle=totle+Integer.parseInt(num);
                        }
                        tv_totlenum.setText(totle+"");
                        adapter=new BianjiAdap(BianjiAct.this,list);
                        adapter.setUplist(BianjiAct.this);
                        lv_bianji.setAdapter(adapter);

                    }
                }

            }
        });
    }

    /**
     * 获取经销商零售预测原始数据
     * @param userName
     */
    private void getRetailfolist(String userName) {
        UserManager.getUserManager().getRetailfo(userName, new ResultCallback<ResultsNewBean<RetailfoBean>>() {
            @Override
            public void onError(int status, String errorMsg) {
                MyToastUtils.showShortToast(BianjiAct.this,errorMsg);

            }

            @Override
            public void onResponse(ResultsNewBean<RetailfoBean> response) {
                retailfoBean=response.getObj();
                if(retailfoBean!=null){
                    tv_bname.setText(retailfoBean.getDealerName());
                    if(TextUtils.isEmpty(retailfoBean.getDealerCode())){
                        tv_bcode.setVisibility(View.GONE);
                    }else {
                        tv_bcode.setVisibility(View.VISIBLE);
                        tv_bcode.setText(retailfoBean.getDealerCode());
                    }
                    tv_btime.setText(retailfoBean.getYearMonth());
                    inputTimeId=retailfoBean.getInputTimeId();
                    list=retailfoBean.getList();
                    if(list!=null&&list.size()!=0){
                        int totle=0;
                        String num;
                        for (int i=0;i<list.size();i++){
                            if(TextUtils.isEmpty(list.get(i).getPridictNum())){
                                num="0";
                            }else {
                                num=list.get(i).getPridictNum();
                            }
                            totle=totle+Integer.parseInt(num);
                        }
                        tv_totlenum.setText(totle+"");
                        adapter=new BianjiAdap(BianjiAct.this,list);
                        adapter.setUplist(BianjiAct.this);
                        lv_bianji.setAdapter(adapter);

                    }
                }


            }
        });
    }

    @OnClick({R.id.iv_finsh,R.id.tv_button})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.iv_finsh:
                finish();
                break;
            case R.id.tv_button:
                if(type.equals("3")){
                    upjxsloadbean.setDataList(luolist);
                    upjxsloadbean.setInputTimeId(inputTimeId);
                    upjxsloadbean.setUserName(CadillacUtils.getCurruser().getUserName());
                    upLoadLuoc(upjxsloadbean);//上传经销商角色的裸车毛利
                }else {
                    uploadbean.setDataList(list);
                    uploadbean.setInputTimeId(inputTimeId);
                    uploadbean.setUserName(CadillacUtils.getCurruser().getUserName());
                    upLoadRe(uploadbean);//保存上传
                }
                break;
        }

    }

    /**经销商角色的裸车毛利上传
     * @param upjxsloadbean
     */
    private void upLoadLuoc(UpjxsLoadBean upjxsloadbean) {
        UserManager.getUserManager().upLoadLuoc(upjxsloadbean, new ResultCallback<String>() {
            @Override
            public void onError(int status, String errorMsg) {

            }

            @Override
            public void onResponse(String response) {
                finish();

            }
        });

    }

    /**
     * 保存经销商零售预测数据
     * @param uploadbean
     */
    private void upLoadRe(UpLoadBean uploadbean) {
        UserManager.getUserManager().upLoadRe(uploadbean, new ResultCallback<String>() {
            @Override
            public void onError(int status, String errorMsg) {
              MyToastUtils.showShortToast(BianjiAct.this,errorMsg);
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object=new JSONObject(response);
                    String code=object.getString("code");
                    String message=object.getString("message");
                    if(code.equals("success")){
                        MyToastUtils.showShortToast(BianjiAct.this,message);
                        finish();
                    }else {
                        MyToastUtils.showShortToast(BianjiAct.this,message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                finish();
            }
        });
    }


    /**
     *
     * @param i
     * @param num
     */
    @Override
    public void update(int i, String num) {
        int totle=0;
        list.get(i).setPridictNum(num);//把修改的位置的值换过来
        for (int p=0;p<list.size();p++){
            if(TextUtils.isEmpty(list.get(p).getPridictNum())){
                list.get(p).setPridictNum("0");
            }
            totle=totle+Integer.parseInt(list.get(p).getPridictNum());

        }
        tv_totlenum.setText(totle+"");

    }

    @Override
    public void notdate(int i, String num,String type) {
        if(type.equals("0")) {
            luolist.get(i).setProfitData(num);
            for (int j = 0; j < luolist.size(); j++) {
                if (TextUtils.isEmpty(luolist.get(j).getProfitData())) {
                    luolist.get(j).setProfitData("0");
                }


            }
        }else {
            luolist.get(i).setComprehensiveProfit(num);
            for (int j = 0; j < luolist.size(); j++) {
                if (TextUtils.isEmpty(luolist.get(j).getProfitData())) {
                    luolist.get(j).setProfitData("0");
                }


            }
        }

    }
}
