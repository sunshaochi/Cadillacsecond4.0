package cadillac.example.com.cadillac.activity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import cadillac.example.com.cadillac.R;
import cadillac.example.com.cadillac.base.BaseActivity;
import cadillac.example.com.cadillac.http.Manage.UserManager;
import cadillac.example.com.cadillac.http.ResultCallback;
import cadillac.example.com.cadillac.utils.CadillacUtils;
import cadillac.example.com.cadillac.utils.MyLogUtils;
import cadillac.example.com.cadillac.utils.MyToastUtils;
import cadillac.example.com.cadillac.utils.SpUtils;
import cadillac.example.com.cadillac.view.CProgressDialog;

/**
 * Mac和大区总部的零售预测
 * Created by bitch-1 on 2017/6/8.
 */

public class MacReatilAct extends BaseActivity {
    @ViewInject(R.id.tv_mactime)
    private TextView tv_mactime;//时间
    @ViewInject(R.id.ll_title)
    private LinearLayout ll_title;
    @ViewInject(R.id.ll_qg)
    private LinearLayout ll_qg;//全国
    @ViewInject(R.id.ll_dq)
    private LinearLayout ll_dq;//大区
    @ViewInject(R.id.sv_my)
    private ScrollView sv_my;
    @ViewInject(R.id.ll_out)
    private LinearLayout ll_out;



    private List<String> titlelist;//红色标题（经销商，总计，XTS....）
    private List<Map<String, Object>> textlist;//全国集合
    private List<Map<String, Object>> dqlist;//大区数据(全国下列表)
    private List<Map<String, Object>> maclist;//mac数据（大区下列表）
    private List<Map<String, Object>> jxslist;//经销商数据（mac下列表）
    private String qgflag,dqflag, macflag, jxsflag;
    private boolean dqisshow,macisshow,jxsshow;

    private String inputtimeid;//时间id
    private Dialog dialog;

    private final Handler mHandler = new Handler();//用来时时更新scorllview的位置


    @Override
    public void setLayout() {
        setContentView(R.layout.act_macreatil);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("经销商零售预测");
        getRetailfolist(CadillacUtils.getCurruser().getUserName());//获取MAC和大区总部的经销商零售预测详情
        ll_title.setBackgroundColor(Color.parseColor("#00000000"));
        ll_qg.setBackgroundColor(Color.parseColor("#00000000"));

    }

    /**
     * 全国数据和红色title()
     * @param userName
     */
    private void getRetailfolist(final String userName) {
        dialog = CProgressDialog.createLoadingDialog(MacReatilAct.this,false);
        dialog.show();
        UserManager.getUserManager().getRetailfo(userName, new ResultCallback<String>() {
            @Override
            public void onError(int status, String errorMsg) {
                dialog.dismiss();
                MyToastUtils.showShortToast(MacReatilAct.this, errorMsg);

            }

            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                Gson gson = new Gson();
                try {
                    JSONObject object = new JSONObject(response);
                    JSONObject data = object.getJSONObject("obj");
                    String time = data.getString("yearMonth");
                    String flag = data.getString("flag");
                    if(!TextUtils.isEmpty(data.getString("inputTimeId"))){
                        inputtimeid=data.getString("inputTimeId");
                        setRightimage(R.mipmap.uninputicon, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Bundle bundle=new Bundle();
                                bundle.putString("inputtimeid",inputtimeid);
                                bundle.putString("inputtype","0");
                                openActivity(UninPutAct.class,bundle);
                            }
                        });
                    }
                    if (!TextUtils.isEmpty(time)) {
                        tv_mactime.setText(time);
                    }
                    if (!TextUtils.isEmpty(flag)) {
                        qgflag = flag;//得到全国flag
                    }
                    JSONArray title = data.getJSONArray("titles");
                    if (title.length() > 0) {
                        titlelist = gson.fromJson(title.toString(), new TypeToken<List<String>>() {
                        }.getType());
                        if (titlelist != null && titlelist.size() > 0) {
                            ll_title.removeAllViews();
                            int width = CadillacUtils.getScreenWidth(MacReatilAct.this) / titlelist.size();
                            for (int i = 0; i < titlelist.size(); i++) {
                                TextView textView = new TextView(MacReatilAct.this);
                                textView.setLayoutParams(new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.MATCH_PARENT));
                                textView.setTextColor(Color.parseColor("#ffffff"));
                                textView.setText(titlelist.get(i) + "");
                                textView.setTextSize(14);
                                textView.setGravity(Gravity.CENTER);
                                View view=new View(MacReatilAct.this);
                                view.setLayoutParams(new LinearLayout.LayoutParams(1, LinearLayout.LayoutParams.MATCH_PARENT));
                                view.setBackgroundColor(Color.parseColor("#bcb7b8"));//竖着先
                                ll_title.addView(textView);
                                ll_title.addView(view);
                                ll_title.setBackgroundColor(Color.parseColor("#8A152B"));
                            }
                        }

                    }
                    JSONArray list = data.getJSONArray("list");//全国数据
                    textlist = gson.fromJson(list.toString(), new TypeToken<List<Map<String, Object>>>() {
                    }.getType());
                    if (textlist.size() > 0) {
                        ll_qg.removeAllViews();
                        int width = CadillacUtils.getScreenWidth(MacReatilAct.this) / titlelist.size();
                        for (int i = 0; i < titlelist.size(); i++) {
                            TextView textView = new TextView(MacReatilAct.this);
                            textView.setLayoutParams(new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.MATCH_PARENT));
//                            textView.setTextColor(Color.parseColor("#333333"));
                            String obj=textlist.get(0).get(titlelist.get(i)).toString();
                            if(i==0) {
                                textView.setTextColor(Color.parseColor("#333333"));//设置颜色
                            }else {
                                textView.setTextColor(Color.parseColor(changColor(obj)));
                            }
                            textView.setText(interCeption(obj));
                            textView.setTextSize(12);
                            textView.setGravity(Gravity.CENTER);

                            View view = new View(MacReatilAct.this);
                            view.setLayoutParams(new LinearLayout.LayoutParams(1, LinearLayout.LayoutParams.MATCH_PARENT));
                            view.setBackgroundColor(Color.parseColor("#bcb7b8"));//竖着先
                            ll_qg.addView(textView);
                            ll_qg.addView(view);
                            ll_qg.setBackgroundColor(Color.parseColor("#E4E4E4"));
                            TextView tv= (TextView) ll_qg.getChildAt(0);
                            tv.setBackgroundResource(R.mipmap.expase_no);
                            ll_qg.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if(!dqisshow){//显示大区
//
                                        ll_dq.setVisibility(View.VISIBLE);
                                        dqisshow=true;
//                                        TextView textView1= (TextView) ll_qg.getChildAt(0);
////
//                                        textView1.setBackgroundResource(R.mipmap.expase_yes);
//                                        textView1.setTextColor(Color.parseColor("#ffffff"));
                                    }else {//隐藏各个大区
//                                        TextView textView1= (TextView) ll_qg.getChildAt(0);
//                                        textView1.setBackgroundResource(R.mipmap.expase_no);
//                                        textView1.setTextColor(Color.parseColor("#333333"));
                                        dqisshow=false;
                                        ll_dq.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                getRegional("", qgflag,ll_qg);//得到大区数据和大区list
            }
        });
    }



    /**
     * 获取大区列表和大区数据
     *
     * @param param
     * @param flag
     */
    private void getRegional(final String param, String flag, final LinearLayout ll_qg) {
        dialog = CProgressDialog.createLoadingDialog(MacReatilAct.this,false);
        dialog.show();
        UserManager.getUserManager().getRegional(param, flag, new ResultCallback<String>() {
            @Override
            public void onError(int status, String errorMsg) {
                dialog.dismiss();
                MyToastUtils.showShortToast(MacReatilAct.this,errorMsg);
            }
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
//                TextView tv= (TextView) ll_qg.getChildAt(0);
//                tv.setBackgroundResource(R.mipmap.expase_yes);
//                tv.setTextColor(Color.parseColor("#ffffff"));                dqisshow=true;//大区显示
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
                        ll_dq.removeAllViews();
                        for (int i = 0; i < dqlist.size(); i++) {
                            final LinearLayout lin = new LinearLayout(MacReatilAct.this);//大区布局
                            lin.setOrientation(LinearLayout.HORIZONTAL);
                            lin.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,CadillacUtils.dip2px(MacReatilAct.this,50)));
                            lin.setBackgroundColor(Color.parseColor("#F1F1F1"));
                            View linview=new View(MacReatilAct.this);
                            linview.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,1));
                            linview.setBackgroundColor(Color.parseColor("#8a152b"));//横着的颜色

                            final Map<String,Object>map=dqlist.get(i);
                            int width = CadillacUtils.getScreenWidth(MacReatilAct.this) / titlelist.size();
                            for (int j=0;j<titlelist.size();j++){
                                TextView textView = new TextView(MacReatilAct.this);
                                textView.setLayoutParams(new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.MATCH_PARENT));

                                String obj=map.get(titlelist.get(j)).toString();
                                if(j==0) {
                                    textView.setTextColor(Color.parseColor("#333333"));//设置颜色
                                }else {
                                    textView.setTextColor(Color.parseColor(changColor(obj)));//设置颜色
                                }
                                textView.setText(interCeption(obj));
                                textView.setTextSize(12);
                                textView.setGravity(Gravity.CENTER);
                                View view = new View(MacReatilAct.this);
                                view.setLayoutParams(new LinearLayout.LayoutParams(1, LinearLayout.LayoutParams.MATCH_PARENT));
                                view.setBackgroundColor(Color.parseColor("#bcb7b8"));//竖着颜色
                                lin.addView(textView);
                                lin.addView(view);
                            }

                            final LinearLayout maclin=new LinearLayout(MacReatilAct.this);//mac布局
                            maclin.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                            maclin.setOrientation(LinearLayout.VERTICAL);
                            maclin.setBackgroundColor(Color.parseColor("#F3F3F3"));
                            lin.setTag(i);
                            final int pos=i;
//                            lin.getChildAt(0).setBackgroundResource(R.mipmap.states);
                            TextView textView= (TextView) lin.getChildAt(0);
                            textView.setBackgroundResource(R.mipmap.expase_no);
                            lin.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //map.get("经销商")= {code=1212,name=Cadillac三区}
                                    if(lin.getTag().toString().equals(pos +"")) {
                                        String obj = map.get("经销商") + "";
//                                        String parm = obj.split(",")[0].split("=")[1];
                                        String parm = interCeption(obj);

                                        getMaclist(parm, dqflag,maclin,lin);//获取各个大区下的mac列表（显示各个mac）
                                    }else {
                                        lin.setTag(pos);
                                        maclin.removeAllViews();
//                                        TextView textView= (TextView) lin.getChildAt(0);
//                                        textView.setBackgroundResource(R.mipmap.expase_no);
//                                        textView.setTextColor(Color.parseColor("#333333"));

                                    }

                                    }
                            });
                            ll_dq.addView(lin);
                            ll_dq.addView(linview);
                            ll_dq.addView(maclin);

                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    /**
     * 获取各大区下面的mac列表
     * @param param
     * @param flag
     */
    private void getMaclist(String param, String flag, final LinearLayout maclin, final LinearLayout lin) {
        dialog = CProgressDialog.createLoadingDialog(MacReatilAct.this,false);
        dialog.show();
        UserManager.getUserManager().getRegional(param, flag, new ResultCallback<String>() {
            @Override
            public void onError(int status, String errorMsg) {
                dialog.dismiss();
                MyToastUtils.showShortToast(MacReatilAct.this,errorMsg);

            }

            @Override
            public void onResponse(String response) {
                dialog.dismiss();
//                TextView tv= (TextView) lin.getChildAt(0);
//                tv.setBackgroundResource(R.mipmap.expase_yes);
//                tv.setTextColor(Color.parseColor("#ffffff"));
                macisshow=true;//mac显示
                lin.setTag("-1");
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
                            final LinearLayout lin = new LinearLayout(MacReatilAct.this);
                            lin.setOrientation(LinearLayout.HORIZONTAL);
                            lin.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,CadillacUtils.dip2px(MacReatilAct.this,50)));
                            lin.setBackgroundColor(Color.parseColor("#f3f3f3"));
                            View linview=new View(MacReatilAct.this);
                            linview.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,1));
                            linview.setBackgroundColor(Color.parseColor("#bcb7b8"));//横着的颜色

                            final Map<String,Object>map=maclist.get(i);
                            int width = CadillacUtils.getScreenWidth(MacReatilAct.this) / titlelist.size();
                            for (int j=0;j<titlelist.size();j++){
                                TextView textView = new TextView(MacReatilAct.this);
                                textView.setLayoutParams(new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.MATCH_PARENT));
                                String obj=map.get(titlelist.get(j)).toString();
                                if(j==0) {
                                    textView.setTextColor(Color.parseColor("#333333"));//设置颜色
                                }else {
                                    textView.setTextColor(Color.parseColor(changColor(obj)));//设置颜色
                                }
                                textView.setText(interCeption(obj));
                                textView.setTextSize(12);
                                textView.setGravity(Gravity.CENTER);
                                View view = new View(MacReatilAct.this);
                                view.setLayoutParams(new LinearLayout.LayoutParams(1, LinearLayout.LayoutParams.MATCH_PARENT));
                                view.setBackgroundColor(Color.parseColor("#bcb7b8"));//竖着颜色
                                lin.addView(textView);
                                lin.addView(view);
                            }
                            final LinearLayout jxslin=new LinearLayout(MacReatilAct.this);//mac布局
                            jxslin.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                            jxslin.setOrientation(LinearLayout.VERTICAL);
                            jxslin.setBackgroundColor(Color.parseColor("#ffffff"));

                            lin.setTag(i);
                            final int pos=i;
//                            lin.getChildAt(0).setBackgroundResource(R.mipmap.states);
                            TextView textView= (TextView) lin.getChildAt(0);
                            textView.setBackgroundResource(R.mipmap.xq_expanle);
                            lin.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //map.get("经销商")= {code=1212,name=Cadillac三区}
                                    if(lin.getTag().toString().equals(pos+"")) {
                                        String obj = map.get("经销商") + "";
//                                        String parm = obj.split(",")[0].split("=")[1];
                                        String parm = interCeption(obj);

                                        getjxslist(parm, macflag,jxslin,lin);//获取mac下的经销商列表（显示各个经销商）
                                    }else {
                                        lin.setTag(pos);
                                        jxslin.removeAllViews();
//                                        TextView textView= (TextView) lin.getChildAt(0);
//                                        textView.setBackgroundResource(R.mipmap.expase_no);
//                                        textView.setTextColor(Color.parseColor("#333333"));
                                    }

                                }
                            });

                            maclin.addView(linview);
                            maclin.addView(lin);
                            maclin.addView(jxslin);
                            mHandler.post(mScrollToBottom);//将scorllview显示在最底部
                        }

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    /**获取小区下面的列表和数据
     * @param param
     * @param flag
     * @param jxslin
     */
    private void getjxslist(String param, String flag, final LinearLayout jxslin, final LinearLayout lin) {
        dialog = CProgressDialog.createLoadingDialog(MacReatilAct.this,false);
        dialog.show();
        UserManager.getUserManager().getRegional(param, flag, new ResultCallback<String>() {
            @Override
            public void onError(int status, String errorMsg) {
                dialog.dismiss();
                MyToastUtils.showShortToast(MacReatilAct.this,errorMsg);

            }

            @Override
            public void onResponse(String response) {
                dialog.dismiss();
//                TextView tv= (TextView) lin.getChildAt(0);
//                tv.setBackgroundResource(R.mipmap.expase_yes);
//                tv.setTextColor(Color.parseColor("#ffffff"));
                jxsshow=true;
                lin.setTag("-1");
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
                            LinearLayout lin = new LinearLayout(MacReatilAct.this);
                            lin.setOrientation(LinearLayout.HORIZONTAL);
                            lin.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,CadillacUtils.dip2px(MacReatilAct.this,50)));
                            lin.setBackgroundColor(Color.parseColor("#ffffff"));
                            View linview=new View(MacReatilAct.this);
                            linview.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,1));
                            linview.setBackgroundColor(Color.parseColor("#bcb7b8"));//横着的颜色

                            final Map<String,Object>map=jxslist.get(i);
                            int width = CadillacUtils.getScreenWidth(MacReatilAct.this) / titlelist.size();
                            for (int j=0;j<titlelist.size();j++){
                                TextView textView = new TextView(MacReatilAct.this);
                                textView.setLayoutParams(new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.MATCH_PARENT));
                                textView.setTextColor(Color.parseColor("#333333"));
                                String obj=map.get(titlelist.get(j)).toString();
                                textView.setText(interCeption(obj));
                                textView.setTextSize(12);
                                textView.setGravity(Gravity.CENTER);
                                View view = new View(MacReatilAct.this);
                                view.setLayoutParams(new LinearLayout.LayoutParams(1, LinearLayout.LayoutParams.MATCH_PARENT));
                                view.setBackgroundColor(Color.parseColor("#E7E7E7"));//竖着颜色
                                lin.addView(textView);
                                lin.addView(view);
                            }

                            jxslin.addView(linview);
                            jxslin.addView(lin);
                            mHandler.post(mScrollToBottom);//将scorllview显示在最底部
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
//    private String interCeption(String obj) {
//        String test;
//        if(obj.contains(".0")){
//            test=obj.substring(0,obj.length()-2);
//        }else if(obj.startsWith("{")){
//            String parm = obj.split(",")[1].split("=")[1];
//            String code=obj.split(",")[0].substring(1);
//            if(code.length()<=5){
//                test=parm.substring(0,parm.length()-1);
//            }else {
//                test = parm.substring(0, parm.length() - 1) + "\n" + code.split("=")[1];
//            }
//        }else {
//            test=obj;
//        }
//        return test;
//    }

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
                sv_my.scrollTo(0, off);
            }

        }
    };

}
