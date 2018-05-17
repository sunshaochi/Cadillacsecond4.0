package cadillac.example.com.cadillac.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ist.cadillacpaltform.SDK.bean.Posm.FinalVal;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.List;
import java.util.Map;

import cadillac.example.com.cadillac.R;
import cadillac.example.com.cadillac.adapter.RetailfoAdpt;
import cadillac.example.com.cadillac.base.BaseActivity;
import cadillac.example.com.cadillac.bean.ListBean;
import cadillac.example.com.cadillac.bean.ResultsBean;
import cadillac.example.com.cadillac.bean.ResultsNewBean;
import cadillac.example.com.cadillac.bean.RetailfoBean;
import cadillac.example.com.cadillac.bean.UserBean;
import cadillac.example.com.cadillac.bean.UserModle;
import cadillac.example.com.cadillac.http.Manage.UserManager;
import cadillac.example.com.cadillac.http.ResultCallback;
import cadillac.example.com.cadillac.utils.CadillacUtils;
import cadillac.example.com.cadillac.utils.MyToastUtils;
import cadillac.example.com.cadillac.utils.SpUtils;
import cadillac.example.com.cadillac.view.CProgressDialog;
import cadillac.example.com.cadillac.view.MyListview;

/**
 * 经销商预测零售界面（角色为经销商的经销商零售预测）
 * Created by bitch-1 on 2017/5/22.
 */

public class RetailfoAct extends BaseActivity {
    @ViewInject(R.id.lv_retailfo)
    private MyListview lv_retailfo;
    private RetailfoAdpt adapter;
    @ViewInject(R.id.tv_dealerName)
    private TextView tv_dealerName;//经销商名称
    @ViewInject(R.id.tv_dealerCode)
    private TextView tv_dealerCode;//经销商编码
    @ViewInject(R.id.tv_yearmonth)
    private TextView tv_yearmonth;//时间
    private RetailfoBean retailfoBean;
    private List<ListBean>list;



    private String type;//0是经销商角色的经销商零售预测，1是mac角色的mac零售预测
    @Override
    public void setLayout() {
        setContentView(R.layout.act_retailfo);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        View view=View.inflate(RetailfoAct.this,R.layout.head_retail,null);
        TextView tv_zh= (TextView) view.findViewById(R.id.tv_zh);
        tv_zh.setText("当月零售预测");
        lv_retailfo.addHeaderView(view);
        type = getIntent().getStringExtra("type");

        if (type.equals("0")) {//角色经销商的经销商零售预测
            setTopTitle("经销商零售预测");
            getRetailfolist(CadillacUtils.getCurruser().getUserName());//获取经销商角色的经销商零售预测
        } else {//角色为mac的mac零售预测
            setTopTitle("MAC零售预测");
            getMacinfo(CadillacUtils.getCurruser().getUserName());//获取mac角色的mac零售预测
        }

    }

    /**角色为mac的mac零售预测
     * @param userName
     */
    private void getMacinfo(String userName) {
        final Dialog erdialog=CProgressDialog.createLoadingDialog(RetailfoAct.this,false);
        erdialog.show();
        UserManager.getUserManager().getMacRetaInfo(userName, new ResultCallback<ResultsNewBean<RetailfoBean>>() {
            @Override
            public void onError(int status, String errorMsg) {
                erdialog.dismiss();
             MyToastUtils.showShortToast(RetailfoAct.this,errorMsg);
            }

            @Override
            public void onResponse(ResultsNewBean<RetailfoBean> response) {
                erdialog.dismiss();
                retailfoBean=response.getObj();
                if(retailfoBean!=null){
                    tv_dealerName.setText(retailfoBean.getDealerName());
                    if(TextUtils.isEmpty(retailfoBean.getDealerCode())){
                        tv_dealerCode.setVisibility(View.GONE);
                    }else {
                        tv_dealerCode.setVisibility(View.VISIBLE);
                        tv_dealerCode.setText(retailfoBean.getDealerCode());
                    }
                    tv_yearmonth.setText(retailfoBean.getYearMonth());
                    if(retailfoBean.getIsEdit().equals("1")){//能编辑
                       setRightimage(R.mipmap.editor, new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                               Bundle bundle=new Bundle();
                               bundle.putString("type",type);
                               openActivity(BianjiAct.class,bundle);
                           }
                       });
                    }
                    list=retailfoBean.getList();
                    if(list!=null&&list.size()!=0){
                        int totle=0;
                        String item;
                        for (int i=0;i<list.size();i++){
                            if(TextUtils.isEmpty(list.get(i).getPridictNum())){
                                item=0+"";
                            }else {
                                item=list.get(i).getPridictNum();
                            }
                            totle=totle+Integer.parseInt(item);
                        }
                        adapter=new RetailfoAdpt(RetailfoAct.this,list,totle);//默认进来不可以编辑
                        lv_retailfo.setAdapter(adapter);

                    }
                }

            }
        });
    }

    /**获取角色为经销商的经销商预测零售
     * @param userName
     */
    private void getRetailfolist(String userName) {
        final Dialog erdialog=CProgressDialog.createLoadingDialog(RetailfoAct.this,false);
        erdialog.show();
        UserManager.getUserManager().getRetailfo(userName, new ResultCallback<ResultsNewBean<RetailfoBean>>() {
            @Override
            public void onError(int status, String errorMsg) {
                erdialog.dismiss();
                MyToastUtils.showShortToast(RetailfoAct.this,errorMsg);

            }

            @Override
            public void onResponse(ResultsNewBean<RetailfoBean> response) {
                erdialog.dismiss();
                retailfoBean=response.getObj();
                if(retailfoBean!=null){
                    tv_dealerName.setText(retailfoBean.getDealerName());
                    if(TextUtils.isEmpty(retailfoBean.getDealerCode())){
                        tv_dealerCode.setVisibility(View.GONE);
                    }else {
                        tv_dealerCode.setVisibility(View.VISIBLE);
                        tv_dealerCode.setText(retailfoBean.getDealerCode());
                    }
                    tv_yearmonth.setText(retailfoBean.getYearMonth());
                    if(retailfoBean.getIsEdit().equals("1")){
                        setRightimage(R.mipmap.editor, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Bundle bundle=new Bundle();
                                bundle.putString("type",type);
                                openActivity(BianjiAct.class,bundle);
                            }
                        });
                    }
                    list=retailfoBean.getList();
                    if(list!=null&&list.size()!=0){
                        int totle=0;
                        String item;
                        for (int i=0;i<list.size();i++){
                            if(TextUtils.isEmpty(list.get(i).getPridictNum())){
                                item=0+"";
                            }else {
                                item=list.get(i).getPridictNum();
                            }
                            totle=totle+Integer.parseInt(item);
                        }
                        adapter=new RetailfoAdpt(RetailfoAct.this,list,totle);//默认进来不可以编辑
                        lv_retailfo.setAdapter(adapter);

                    }
                }


            }
        });
    }

    @OnClick({R.id.iv_finsh})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.iv_finsh:
                finish();
                break;
//            case R.id.iv_bianji://编辑界面
//                Bundle bundle=new Bundle();
//                bundle.putString("type",type);
//                openActivity(BianjiAct.class,bundle);
//                break;



        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(type.equals("0")){//角色经销商的经销商零售预测
            getRetailfolist(CadillacUtils.getCurruser().getUserName());//获取零售预测详情
        }else if(type.equals("1")){//角色mac的mac零售预测
            getMacinfo(CadillacUtils.getCurruser().getUserName());
        }
//

    }
}
