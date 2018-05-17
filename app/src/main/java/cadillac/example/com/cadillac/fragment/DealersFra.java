package cadillac.example.com.cadillac.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import javax.crypto.Mac;

import cadillac.example.com.cadillac.R;
import cadillac.example.com.cadillac.activity.CompetinggdAct;
import cadillac.example.com.cadillac.activity.HighageAct;
import cadillac.example.com.cadillac.activity.HqMacretail;
import cadillac.example.com.cadillac.activity.LuocheMaoliAct;
import cadillac.example.com.cadillac.activity.MacReatilAct;
import cadillac.example.com.cadillac.activity.NakedcarAct;
import cadillac.example.com.cadillac.activity.NewNakeCarAct;
import cadillac.example.com.cadillac.activity.RetailfoAct;
import cadillac.example.com.cadillac.base.BaseFrag;
import cadillac.example.com.cadillac.bean.UserModle;
import cadillac.example.com.cadillac.utils.CadillacUtils;

/**
 * 经销商frg
 * Created by bitch-1 on 2017/5/19.
 */

public class DealersFra extends BaseFrag {
    @ViewInject(R.id.ll_fgkl)
    private LinearLayout ll_fgkl;//第一排高库龄
    @ViewInject(R.id.ll_dep)
    private LinearLayout ll_dep;//第二排高库龄
    @ViewInject(R.id.ll_maclsyc)
    private LinearLayout ll_maclsyc;//mac零售预测
    @ViewInject(R.id.tv_title)
    private TextView tv_title;

    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.frg_dealers,null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
         tv_title.setText(CadillacUtils.getCurruser().getDealerName());
        if(CadillacUtils.getCurruser().getRoleName().contains("经销商")) {//只要包含经销商
            ll_dep.setVisibility(View.GONE);//第二排的高库龄隐藏
            ll_fgkl.setVisibility(View.VISIBLE);//第一排高库龄显示
            ll_maclsyc.setVisibility(View.GONE);//MAC隐藏
        }else {
            ll_dep.setVisibility(View.VISIBLE);//第二排的高库龄显示
            ll_fgkl.setVisibility(View.GONE);//第一排高库龄影藏
            ll_maclsyc.setVisibility(View.VISIBLE);
        }




    }

    @OnClick({R.id.ll_jxslsyc,R.id.ll_maclsyc,R.id.ll_lcml,R.id.ll_jpjg,R.id.ll_fgkl,R.id.ll_sgkl})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.ll_jxslsyc://经销商零售预测
                if(CadillacUtils.getCurruser().getRoleName().contains("经销商")) {//经销商销售经理
                    Bundle bundle=new Bundle();
                    bundle.putString("type","0");
                    openActivity(RetailfoAct.class,bundle);//经销商角色的经销商零售预测
                }else{
                    openActivity(MacReatilAct.class);//mac和总部的经销商零售预测
                }

                break;
            case R.id.ll_maclsyc://mac零售预测
                if(CadillacUtils.getCurruser().getRoleName().contains("小区经理")){//mac用户的mac零售预测
                    Bundle bundle=new Bundle();
                    bundle.putString("type","1");
                    openActivity(RetailfoAct.class,bundle);
                }else {//大区总部的mac零售预测
                    openActivity(HqMacretail.class);
                }

                break;
            case R.id.ll_lcml://裸车毛利
                if(CadillacUtils.getCurruser().getRoleName().contains("经销商")) {//角色为经销商的裸车毛利
//                    openActivity(NakedcarAct.class);
                    openActivity(NewNakeCarAct.class);
                }else {//角色为非经销商的裸车毛利
                    openActivity(LuocheMaoliAct.class);
                }
                break;
            case R.id.ll_jpjg://竞品价格
                openActivity(CompetinggdAct.class);
                break;
            case R.id.ll_fgkl://第一排高库龄
            openActivity(HighageAct.class);
                break;
            case R.id.ll_sgkl://第二排高库龄
                openActivity(HighageAct.class);
                break;
        }


    }
}
