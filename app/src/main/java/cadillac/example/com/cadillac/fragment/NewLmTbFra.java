package cadillac.example.com.cadillac.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cadillac.example.com.cadillac.R;
import cadillac.example.com.cadillac.base.BaseFrag;
import cadillac.example.com.cadillac.bean.LmJxsBean;
import cadillac.example.com.cadillac.bean.ProfitBean;
import cadillac.example.com.cadillac.http.Manage.UserManager;
import cadillac.example.com.cadillac.http.ResultCallback;
import cadillac.example.com.cadillac.utils.CadillacUtils;
import cadillac.example.com.cadillac.utils.GsonUtils;
import cadillac.example.com.cadillac.utils.MyLogUtils;
import cadillac.example.com.cadillac.utils.MyToastUtils;
import cadillac.example.com.cadillac.utils.SpUtils;
import cadillac.example.com.cadillac.view.CProgressDialog;

/**
 * Created by iris on 2018/4/25.
 */

public class NewLmTbFra extends BaseFrag {
    @ViewInject(R.id.ll_maclmtitle)
    private LinearLayout ll_maclmtitle;//红色titile
    @ViewInject(R.id.ll_maclmqg)
    private LinearLayout ll_maclmqg;//全国
    @ViewInject(R.id.ll_maclmdqlist)
    private LinearLayout ll_maclmdqlist;//大区
    @ViewInject(R.id.rl_qsg)
    private RelativeLayout rl_qsg;//前三个布局
    @ViewInject(R.id.sc_view)
    private ScrollView sv_my;
    @ViewInject(R.id.ll_out)
    private LinearLayout ll_out;



    private List<String> titlelist;//红色标题栏
    private String qgflag,dqflag,macflag;//全国flag

    private boolean dqisshow;

    private String inputTimeid;//时间id
    private String dateid;

    private Dialog dialog;
    private int width;

    private List<String>nojxlist=new ArrayList<>();


    private List<Map<String, Object>> textlist;//全国集合
    private List<Map<String, Object>> dqlist;//大区数据(全国下列表)
    private List<Map<String, Object>> maclist;//maclist数据(全国下列表)
    private List<Map<String, Object>> jxslist;//经销商集合
    private final Handler mHandler = new Handler();//用来时时更新scorllview的位置

    @Override
    public View initView(LayoutInflater inflater)
    {
        return inflater.inflate(R.layout.fra_newjdlm,null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        inputTimeid= SpUtils.getInputTimeId(getActivity());
        dateid=SpUtils.getDateId(getActivity());
        getMacluom(CadillacUtils.getCurruser().getUserName(),"3",inputTimeid,dateid);//获取mac裸车毛利绝对值

    }


    /**获取红色车型和全国数据
     * @param userName
     * @param dataType
     * @param inputTimeId
     * @param timeType
     */
    private void getMacluom(String userName, final String dataType, final String inputTimeId, String timeType) {
        dialog = CProgressDialog.createLoadingDialog(getActivity(),false);
        dialog.show();
        UserManager.getUserManager().getMacluoc(userName, dataType, inputTimeId, timeType, new ResultCallback<String>() {
            @Override
            public void onError(int status, String errorMsg) {
                dialog.dismiss();
                MyToastUtils.showShortToast(getActivity(),errorMsg);
            }

            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                Gson gson=new Gson();
                try {
                    JSONObject object = new JSONObject(response);
                    JSONObject data = object.getJSONObject("obj");

                    JSONArray titles=data.getJSONArray("titles");
                    if(titles.length()>0){
                        titlelist=gson.fromJson(titles.toString(),new TypeToken<List<String>>(){}.getType());
                    }

                    nojxlist.clear();
                    for(int i=0;i<titlelist.size();i++){
                        if(titlelist.get(i).equals("经销商")){

                        }else {
                            nojxlist.add(titlelist.get(i));
                        }
                    }

                    width = CadillacUtils.getScreenWidth(getActivity()) / titlelist.size();


                    String flag = data.getString("flag");
                    if(!TextUtils.isEmpty(flag)){
                        qgflag=flag;
                    }



                    JSONArray list = data.getJSONArray("list");//全国数据
                    textlist = gson.fromJson(list.toString(), new TypeToken<List<Map<String, Object>>>() {
                    }.getType());

                    setJiemianData(textlist);//给界面设置数据

                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }
        });
    }

    /**
     * 设置界面
     * @param res
     */
    private void setJiemianData(List<Map<String, Object>> res) {
        if(titlelist.size()>0){
            rl_qsg.setVisibility(View.VISIBLE);//红色栏显示
            ll_maclmtitle.removeAllViews();
            int width = CadillacUtils.getScreenWidth(getActivity()) / titlelist.size();
            for (int i = 0; i < titlelist.size(); i++) {
                TextView textView = new TextView(getActivity());
                textView.setLayoutParams(new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.MATCH_PARENT));
                textView.setTextColor(Color.parseColor("#ffffff"));
                textView.setText(titlelist.get(i) + "");
                textView.setTextSize(12);
                textView.setGravity(Gravity.CENTER);
                View view=new View(getActivity());
                view.setLayoutParams(new LinearLayout.LayoutParams(1, LinearLayout.LayoutParams.MATCH_PARENT));
                view.setBackgroundColor(Color.parseColor("#bcb7b8"));//竖着先
                ll_maclmtitle.addView(textView);
                ll_maclmtitle.addView(view);
                ll_maclmtitle.setBackgroundColor(Color.parseColor("#8a152b"));
            }

        }

         /* 以上是设置红色车型栏*/

       /* 全国父容器*/
        if (res.size() > 0) {
            ll_maclmqg.removeAllViews();//全国父容器
            for(int j=0;j<res.size();j++) {
                View qgview = View.inflate(getActivity(), R.layout.act_naketitleitem, null);
                LinearLayout ll_itemtitle = (LinearLayout) qgview.findViewById(R.id.ll_itemtitle);
                LinearLayout ll_itemzh = (LinearLayout) qgview.findViewById(R.id.ll_itemzh);
                TextView tvtime= (TextView) qgview.findViewById(R.id.tv_time);
                tvtime.setWidth(width);
                tvtime.setBackgroundResource(R.mipmap.expase_no);
                View view_sl=qgview.findViewById(R.id.view_sl);
                view_sl.setBackgroundColor(Color.parseColor("#bcb7b8"));
                View view_hl=qgview.findViewById(R.id.view_hl);
                view_hl.setBackgroundColor(Color.parseColor("#bcb7b8"));
                Map<String,Object>map=res.get(j);
                for (int i = 0; i < titlelist.size(); i++) {
                    if(map.containsKey(titlelist.get(i))){
                        String obj=map.get(titlelist.get(i)).toString();
                        if(obj.contains("name")){
                            obj=obj.replace("code=","").replace(",","");
                            LmJxsBean bean= GsonUtils.gsonToBean(obj,LmJxsBean.class);
                            tvtime.setText(bean.getName());
                            tvtime.setText(bean.getName());
                        }
                    }




                }

                for(int i=0;i<nojxlist.size();i++){
                    TextView textView = new TextView(getActivity());
                    textView.setLayoutParams(new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.MATCH_PARENT));
                    textView.setTextSize(12);
                    textView.setGravity(Gravity.CENTER);

                    TextView textView1 = new TextView(getActivity());
                    textView1.setLayoutParams(new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.MATCH_PARENT));
                    textView1.setTextSize(12);
                    textView1.setGravity(Gravity.CENTER);

                    if(map.containsKey(nojxlist.get(i))){
                        String obj=map.get(nojxlist.get(i)).toString();
                        ProfitBean bean=GsonUtils.gsonToBean(obj,ProfitBean.class);
                        String profitdata=bean.getProfitData();
                        String com=bean.getComprehensiveProfit();
                        textView.setText(profitdata);
                        textView1.setText(com);
                        if(profitdata.contains("-")&&profitdata.length()>1){
                            textView.setTextColor(Color.parseColor("#3aa01d"));
                        }else {
                            textView.setTextColor(Color.parseColor("#333333"));
                        }

                        if(com.contains("-")&&com.length()>1){
                            textView1.setTextColor(Color.parseColor("#3aa01d"));
                        }else {
                            textView1.setTextColor(Color.parseColor("#333333"));
                        }
                    }

                    View view = new View(getActivity());
                    view.setLayoutParams(new LinearLayout.LayoutParams(1, LinearLayout.LayoutParams.MATCH_PARENT));
                    view.setBackgroundColor(Color.parseColor("#bcb7b8"));//竖着先

                    View view1 = new View(getActivity());
                    view1.setLayoutParams(new LinearLayout.LayoutParams(1, LinearLayout.LayoutParams.MATCH_PARENT));
                    view1.setBackgroundColor(Color.parseColor("#bcb7b8"));//竖着先

                    ll_itemtitle.addView(textView);
                    ll_itemtitle.addView(view);
                    ll_itemzh.addView(textView1);
                    ll_itemzh.addView(view1);

                }

                ll_maclmqg.addView(qgview);
                ll_maclmqg.setBackgroundColor(Color.parseColor("#E4E4E4"));

                dqisshow=false;//每次调取这个接口都要重新设置一下
                ll_maclmqg.setOnClickListener(new View.OnClickListener() {//点击全国
                    @Override
                    public void onClick(View view) {
                        if(!dqisshow){//显示大区
                            ll_maclmdqlist.setVisibility(View.VISIBLE);
                            dqisshow=true;
                        }else {//隐藏各个大区
                            dqisshow=false;
                            ll_maclmdqlist.setVisibility(View.GONE);
                        }
                    }
                });
            }

            getRegional(qgflag,"全国",inputTimeid,"3",ll_maclmqg);//获取大区
        }


    }


    /**获取大区list和数据
     * @param flag
     * @param param
     * @param inputTimeId
     * @param dataType
     */
    private void getRegional(String flag, final String param, String inputTimeId, String dataType, final LinearLayout llmac) {
        dialog = CProgressDialog.createLoadingDialog(getActivity(),false);
        dialog.show();
        UserManager.getUserManager().getMacluocDqlist(flag, param, inputTimeId, dataType, new ResultCallback<String>() {
            @Override
            public void onError(int status, String errorMsg) {
                dialog.dismiss();
                MyToastUtils.showShortToast(getActivity(),errorMsg);
//            dialog.dismiss();
            }

            @Override
            public void onResponse(String response) {
                dialog.dismiss();

                dqisshow=true;//大区显示
                Gson gson = new Gson();
                try {
                    JSONObject object = new JSONObject(response);
                    JSONObject data = object.getJSONObject("obj");
                    String flag = data.getString("flag");
                    if (!TextUtils.isEmpty(flag)) {
                        dqflag = flag;//等到一区 二区
                    }

                    JSONArray list = data.getJSONArray("list");//大区列表加数据
                    dqlist = gson.fromJson(list.toString(), new TypeToken<List<Map<String, Object>>>() {
                    }.getType());
                    if (dqlist.size() > 0) {
                        ll_maclmdqlist.removeAllViews();
                        for (int i = 0; i < dqlist.size(); i++) {
                            final View dqview = View.inflate(getActivity(), R.layout.act_naketitleitem, null);
                            LinearLayout ll_itemtitle = (LinearLayout) dqview.findViewById(R.id.ll_itemtitle);
                            LinearLayout ll_itemzh = (LinearLayout) dqview.findViewById(R.id.ll_itemzh);
                            TextView tvtime= (TextView) dqview.findViewById(R.id.tv_time);
                            tvtime.setWidth(width);
                            tvtime.setBackgroundResource(R.mipmap.expase_no);
                            View view_sl=dqview.findViewById(R.id.view_sl);
                            view_sl.setBackgroundColor(Color.parseColor("#bcb7b8"));
                            View view_hl=dqview.findViewById(R.id.view_hl);
                            view_hl.setBackgroundColor(Color.parseColor("#bcb7b8"));


                            dqview.setBackgroundColor(Color.parseColor("#F1F1F1"));
                            View linview=new View(getActivity());
                            linview.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,1));
                            linview.setBackgroundColor(Color.parseColor("#8a152b"));//横着的颜色
                            final Map<String,Object>map=dqlist.get(i);


                            for (int j = 0; j < titlelist.size(); j++) {
                                if(map.containsKey(titlelist.get(j))){
                                    String obj=map.get(titlelist.get(j)).toString();
                                    if(obj.contains("name")){
                                        obj=obj.replace("code=","").replace(",","");
                                        LmJxsBean bean= GsonUtils.gsonToBean(obj,LmJxsBean.class);
                                        tvtime.setText(bean.getName());
                                    }
                                }


                            }

                            for(int j=0;j<nojxlist.size();j++) {
                                TextView textView = new TextView(getActivity());
                                textView.setLayoutParams(new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.MATCH_PARENT));
                                textView.setTextSize(12);
                                textView.setGravity(Gravity.CENTER);

                                TextView textView1 = new TextView(getActivity());
                                textView1.setLayoutParams(new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.MATCH_PARENT));
                                textView1.setTextSize(12);
                                textView1.setGravity(Gravity.CENTER);

                                if (map.containsKey(nojxlist.get(j))) {
                                    String obj = map.get(nojxlist.get(j)).toString();
                                    ProfitBean bean = GsonUtils.gsonToBean(obj, ProfitBean.class);
                                    String profitdata = bean.getProfitData();
                                    String com = bean.getComprehensiveProfit();
                                    textView.setText(profitdata);
                                    textView1.setText(com);
                                    if (profitdata.contains("-") && profitdata.length() > 1) {
                                        textView.setTextColor(Color.parseColor("#3aa01d"));
                                    } else {
                                        textView.setTextColor(Color.parseColor("#333333"));
                                    }

                                    if (com.contains("-") && com.length() > 1) {
                                        textView1.setTextColor(Color.parseColor("#3aa01d"));
                                    } else {
                                        textView1.setTextColor(Color.parseColor("#333333"));
                                    }
                                }

                                View view = new View(getActivity());
                                view.setLayoutParams(new LinearLayout.LayoutParams(1, LinearLayout.LayoutParams.MATCH_PARENT));
                                view.setBackgroundColor(Color.parseColor("#bcb7b8"));//竖着先

                                View view1 = new View(getActivity());
                                view1.setLayoutParams(new LinearLayout.LayoutParams(1, LinearLayout.LayoutParams.MATCH_PARENT));
                                view1.setBackgroundColor(Color.parseColor("#bcb7b8"));//竖着先

                                ll_itemtitle.addView(textView);
                                ll_itemtitle.addView(view);
                                ll_itemzh.addView(textView1);
                                ll_itemzh.addView(view1);
                            }

                            final LinearLayout maclin=new LinearLayout(getActivity());//mac布局
                            maclin.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                            maclin.setOrientation(LinearLayout.VERTICAL);
                            maclin.setBackgroundColor(Color.parseColor("#F3F3F3"));
                            dqview.setTag(i);
                            final int pos=i;

                            dqview.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //map.get("经销商")= {code=1212,name=Cadillac三区}
                                    if(dqview.getTag().toString().equals(pos +"")) {
                                        String obj = map.get("经销商").toString().replace("code=","").replace(",","");
                                        LmJxsBean parm =GsonUtils.gsonToBean(obj,LmJxsBean.class);
                                        getMaclist(dqflag,parm.getName(),inputTimeid,"3",maclin,dqview);//得到大区数据和大区list

                                    }else {
                                        dqview.setTag(pos);
                                        maclin.removeAllViews();


                                    }

                                }
                            });

                            ll_maclmdqlist.addView(dqview);//内容
                            ll_maclmdqlist.addView(linview);//分割线
                            ll_maclmdqlist.addView(maclin);//小区布局


                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

 /* 以上是设置全国下面的大区数据*/
            }
        });
    }


    /**
     * 获取小区
     * @param flag
     * @param param
     * @param inputTimeId
     * @param dataType
     * @param maclin
     * @param
     */
    private void getMaclist(String flag, final String param, String inputTimeId, String dataType, final LinearLayout maclin, final View dqview) {
        dialog = CProgressDialog.createLoadingDialog(getActivity(),false);
        dialog.show();
        UserManager.getUserManager().getMacluocDqlist(flag, param, inputTimeId, dataType, new ResultCallback<String>() {
            @Override
            public void onError(int status, String errorMsg) {
                dialog.dismiss();
                MyToastUtils.showShortToast(getActivity(),errorMsg);
            }

            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                dqview.setTag("-1");
                Gson gson = new Gson();
                try {
                    JSONObject object = new JSONObject(response);
                    JSONObject data = object.getJSONObject("obj");
                    String flag = data.getString("flag");
                    if (!TextUtils.isEmpty(flag)) {
                        macflag = flag;//macflag
                    }

                    JSONArray list = data.getJSONArray("list");//mac数据和列表
                    maclist = gson.fromJson(list.toString(), new TypeToken<List<Map<String, Object>>>() {
                    }.getType());
                    if(maclist.size()>0){
                        maclin.removeAllViews();
                        for (int i=0;i<maclist.size();i++){

                            final View macview = View.inflate(getActivity(), R.layout.act_naketitleitem, null);
                            LinearLayout ll_itemtitle = (LinearLayout) macview.findViewById(R.id.ll_itemtitle);
                            LinearLayout ll_itemzh = (LinearLayout) macview.findViewById(R.id.ll_itemzh);
                            TextView tvtime= (TextView) macview.findViewById(R.id.tv_time);
                            tvtime.setWidth(width);
                            tvtime.setBackgroundResource(R.mipmap.xq_expanle);
                            View view_sl=macview.findViewById(R.id.view_sl);
                            view_sl.setBackgroundColor(Color.parseColor("#bcb7b8"));
                            View view_hl=macview.findViewById(R.id.view_hl);
                            view_hl.setBackgroundColor(Color.parseColor("#bcb7b8"));

                            macview.setBackgroundColor(Color.parseColor("#ffffff"));
                            View linview=new View(getActivity());
                            linview.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,1));
                            linview.setBackgroundColor(Color.parseColor("#bcb7b8"));//横着的颜色

                            final Map<String,Object>map=maclist.get(i);

                            for (int j = 0; j < titlelist.size(); j++) {
                                if(map.containsKey(titlelist.get(j))){
                                    String obj=map.get(titlelist.get(j)).toString();
                                    if(obj.contains("name")){
                                        obj=obj.replace("code=","").replace(",","");
                                        LmJxsBean bean= GsonUtils.gsonToBean(obj,LmJxsBean.class);
                                        tvtime.setText(bean.getName());
                                    }
                                }


                            }

                            for(int j=0;j<nojxlist.size();j++) {
                                TextView textView = new TextView(getActivity());
                                textView.setLayoutParams(new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.MATCH_PARENT));
                                textView.setTextSize(12);
                                textView.setGravity(Gravity.CENTER);

                                TextView textView1 = new TextView(getActivity());
                                textView1.setLayoutParams(new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.MATCH_PARENT));
                                textView1.setTextSize(12);
                                textView1.setGravity(Gravity.CENTER);

                                if (map.containsKey(nojxlist.get(j))) {
                                    String obj = map.get(nojxlist.get(j)).toString();
                                    ProfitBean bean = GsonUtils.gsonToBean(obj, ProfitBean.class);
                                    String profitdata = bean.getProfitData();
                                    String com = bean.getComprehensiveProfit();
                                    textView.setText(profitdata);
                                    textView1.setText(com);
                                    if (profitdata.contains("-") && profitdata.length() > 1) {
                                        textView.setTextColor(Color.parseColor("#3aa01d"));
                                    } else {
                                        textView.setTextColor(Color.parseColor("#333333"));
                                    }

                                    if (com.contains("-") && com.length() > 1) {
                                        textView1.setTextColor(Color.parseColor("#3aa01d"));
                                    } else {
                                        textView1.setTextColor(Color.parseColor("#333333"));
                                    }
                                }

                                View view = new View(getActivity());
                                view.setLayoutParams(new LinearLayout.LayoutParams(1, LinearLayout.LayoutParams.MATCH_PARENT));
                                view.setBackgroundColor(Color.parseColor("#bcb7b8"));//竖着先

                                View view1 = new View(getActivity());
                                view1.setLayoutParams(new LinearLayout.LayoutParams(1, LinearLayout.LayoutParams.MATCH_PARENT));
                                view1.setBackgroundColor(Color.parseColor("#bcb7b8"));//竖着先

                                ll_itemtitle.addView(textView);
                                ll_itemtitle.addView(view);
                                ll_itemzh.addView(textView1);
                                ll_itemzh.addView(view1);
                            }

                            final LinearLayout jxslin=new LinearLayout(getActivity());//mac布局
                            jxslin.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                            jxslin.setOrientation(LinearLayout.VERTICAL);
                            jxslin.setBackgroundColor(Color.parseColor("#ffffff"));

                            macview.setTag(i);
                            final int pos=i;
                            macview.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //map.get("经销商")= {code=1212,name=Cadillac三区}
                                    if(macview.getTag().toString().equals(pos+"")) {
                                        String obj = map.get("经销商").toString().replace("code=","").replace(",","");
                                        LmJxsBean parm =GsonUtils.gsonToBean(obj,LmJxsBean.class);
                                        getjxslist(macflag,parm.getName(),inputTimeid, "3",jxslin,macview);//获取mac下的经销商列表（显示各个经销商）
                                    }else {
                                        macview.setTag(pos);
                                        jxslin.removeAllViews();
//
                                    }

                                }
                            });

                            maclin.addView(macview);
                            maclin.addView(linview);
                            maclin.addView(jxslin);
                            /*以上是设置个小区*/
                            if(i==maclist.size()-1) {
                                mHandler.post(mScrollToBottom);//将scorllview显示在最底部
                            }
                        }

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }


    /**
     * 获取小区列表
     * @param flag
     * @param param
     * @param inputTimeId
     * @param dataType
     * @param jxslin
     * @param
     */
    private void getjxslist(String flag, String param, String inputTimeId, final String dataType, final LinearLayout jxslin, final View macview) {
        dialog = CProgressDialog.createLoadingDialog(getActivity(),false);
        dialog.show();
        UserManager.getUserManager().getMacluocDqlist(flag, param, inputTimeId, dataType, new ResultCallback<String>() {
            @Override
            public void onError(int status, String errorMsg) {
                dialog.dismiss();
                MyToastUtils.showShortToast(getActivity(),errorMsg);
            }

            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                macview.setTag("-1");
                Gson gson = new Gson();
                try {
                    JSONObject object = new JSONObject(response);
                    JSONObject data = object.getJSONObject("obj");

                    JSONArray list = data.getJSONArray("list");//全国数据
                    jxslist = gson.fromJson(list.toString(), new TypeToken<List<Map<String, Object>>>() {
                    }.getType());
                    if(jxslist.size()>0){
                        jxslin.removeAllViews();
                        for (int i=0;i<jxslist.size();i++){

                            final View jxsview = View.inflate(getActivity(), R.layout.act_naketitleitem, null);
                            LinearLayout ll_itemtitle = (LinearLayout) jxsview.findViewById(R.id.ll_itemtitle);
                            LinearLayout ll_itemzh = (LinearLayout) jxsview.findViewById(R.id.ll_itemzh);
                            TextView tvtime= (TextView) jxsview.findViewById(R.id.tv_time);
                            tvtime.setWidth(width);
//                            tvtime.setBackgroundResource(R.mipmap.xq_expanle);
                            View view_sl=jxsview.findViewById(R.id.view_sl);
                            view_sl.setBackgroundColor(Color.parseColor("#bcb7b8"));
                            View view_hl=jxsview.findViewById(R.id.view_hl);
                            view_hl.setBackgroundColor(Color.parseColor("#bcb7b8"));

                            jxsview.setBackgroundColor(Color.parseColor("#ffffff"));
                            View linview=new View(getActivity());
                            linview.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,1));
                            linview.setBackgroundColor(Color.parseColor("#bcb7b8"));//横着的颜色
                            final Map<String,Object>map=jxslist.get(i);

                            for (int j = 0; j < titlelist.size(); j++) {
                                if(map.containsKey(titlelist.get(j))){
                                    String obj=map.get(titlelist.get(j)).toString();
                                    if(obj.contains("name")){
                                        obj=obj.replace("code=","").replace(",","").replace("null","");
                                        LmJxsBean bean= GsonUtils.gsonToBean(obj,LmJxsBean.class);
                                        tvtime.setText(bean.getName());
                                    }
                                }


                            }

                            for(int j=0;j<nojxlist.size();j++) {
                                TextView textView = new TextView(getActivity());
                                textView.setLayoutParams(new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.MATCH_PARENT));
                                textView.setTextSize(12);
                                textView.setGravity(Gravity.CENTER);

                                TextView textView1 = new TextView(getActivity());
                                textView1.setLayoutParams(new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.MATCH_PARENT));
                                textView1.setTextSize(12);
                                textView1.setGravity(Gravity.CENTER);

                                if (map.containsKey(nojxlist.get(j))) {
                                    String obj = map.get(nojxlist.get(j)).toString();
                                    ProfitBean bean = GsonUtils.gsonToBean(obj, ProfitBean.class);
                                    String profitdata = bean.getProfitData();
                                    String com = bean.getComprehensiveProfit();
                                    textView.setText(profitdata);
                                    textView1.setText(com);
                                    if (profitdata.contains("-") && profitdata.length() > 1) {
                                        textView.setTextColor(Color.parseColor("#3aa01d"));
                                    } else {
                                        textView.setTextColor(Color.parseColor("#333333"));
                                    }

                                    if (com.contains("-") && com.length() > 1) {
                                        textView1.setTextColor(Color.parseColor("#3aa01d"));
                                    } else {
                                        textView1.setTextColor(Color.parseColor("#333333"));
                                    }
                                }

                                View view = new View(getActivity());
                                view.setLayoutParams(new LinearLayout.LayoutParams(1, LinearLayout.LayoutParams.MATCH_PARENT));
                                view.setBackgroundColor(Color.parseColor("#bcb7b8"));//竖着先

                                View view1 = new View(getActivity());
                                view1.setLayoutParams(new LinearLayout.LayoutParams(1, LinearLayout.LayoutParams.MATCH_PARENT));
                                view1.setBackgroundColor(Color.parseColor("#bcb7b8"));//竖着先

                                ll_itemtitle.addView(textView);
                                ll_itemtitle.addView(view);
                                ll_itemzh.addView(textView1);
                                ll_itemzh.addView(view1);
                            }



                            jxslin.addView(jxsview);
                            jxslin.addView(linview);
                           /* 以上是设置各经销商*/
                            if(i==jxslist.size()-1) {
                                mHandler.post(mScrollToBottom);//将scorllview显示在最底部
                            }
                        }

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });

    }




    /**
     *  因为textlist.get(0).get("经销商").toString()={code=,name=全国}
     * @param obj
     */
    private String interCeption(String obj) {
        String test;
        MyLogUtils.info("obj"+obj);
        if(obj.startsWith("{")){

            String parm = obj.split(",")[1].split("=")[1];
            String hah=parm.substring(0,parm.length()-1);
            test=hah;
//            String code=obj.split(",")[0].substring(1);
//            if(code.length()<=5){
//                test=parm.substring(0,parm.length()-1);
//            }else {
//                test = parm.substring(0, parm.length() - 1) + "\n" + code.split("=")[1];
//            }
        }else {
            test=obj;
        }
        return test;
    }

    /**
     * 变化颜色的
     * @return
     */
    private String changColor(String obj){
        String color = null;
        if (obj.contains("-") && obj.length() > 1) {
            color="#3aa01d";//绿色
        } else {
            color="#333333";//黑色
        }

        return color;
    }

    private Runnable mScrollToBottom = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            int off = ll_out.getMeasuredHeight() - sv_my.getHeight();
            if (off > 0) {
//                sv_my.scrollTo(0, off);
            }

        }
    };

    public void upDate() {
        inputTimeid= SpUtils.getInputTimeId(getActivity());
        dateid=SpUtils.getDateId(getActivity());
        getMacluom(CadillacUtils.getCurruser().getUserName(),"3",inputTimeid,dateid);//获取mac裸车毛利绝对值
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            if(inputTimeid.equals(SpUtils.getInputTimeId(getActivity()))&&dateid.equals(SpUtils.getDateId(getActivity()))){

            }else {
                inputTimeid= SpUtils.getInputTimeId(getActivity());
                dateid=SpUtils.getDateId(getActivity());
                getMacluom(CadillacUtils.getCurruser().getUserName(),"3",inputTimeid,dateid);//获取mac裸车毛利绝对值
            }
        }
    }
}


