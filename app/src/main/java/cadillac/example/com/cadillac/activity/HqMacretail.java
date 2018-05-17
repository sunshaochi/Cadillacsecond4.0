package cadillac.example.com.cadillac.activity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ist.cadillacpaltform.SDK.bean.Posm.User;
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
 * 角色为总部大区的Mac零售预测
 * Created by bitch-1 on 2017/6/19.
 */

public class HqMacretail extends BaseActivity {
    @ViewInject(R.id.ll_mtitle)
    private LinearLayout ll_mtitle;
    @ViewInject(R.id.ll_mqg)
    private LinearLayout ll_mqg;
    @ViewInject(R.id.ll_mdq)
    private LinearLayout ll_mdq;
    @ViewInject(R.id.tv_mtime)
    private TextView tv_mtime;
    @ViewInject(R.id.sv_my)
    private ScrollView sv_my;
    @ViewInject(R.id.ll_out)
    private LinearLayout ll_out;
    private String userName;//登录名

    private List<String> titlelist;//红色标题（经销商，总计，XTS....）
    private List<Map<String, Object>> textlist;//全国集合
    private List<Map<String, Object>> dqlist;//大区数据(全国下列表)
    private List<Map<String, Object>> maclist;//大区数据(全国下列表)
    private String qgflag,dqflag, macflag, jxsflag;
    private boolean dqisshow,macisshow,jxsshow;
    private Drawable drawable,drawable1;//向下向上的箭头
    private Dialog dialog;

    private final Handler mHandler = new Handler();//用来时时更新scorllview的位置
    @Override
    public void setLayout() {
     setContentView(R.layout.act_hqmacretail);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("MAC零售预测");
        getMacinfo(CadillacUtils.getCurruser().getUserName());//获取MAC和大区总部的经销商零售预测详情
        ll_mtitle.setBackgroundColor(Color.parseColor("#00000000"));
        ll_mqg.setBackgroundColor(Color.parseColor("#00000000"));

    }

    /**角色为mac的mac零售预测
     * @param userName
     */
    private void getMacinfo(String userName) {
        dialog = CProgressDialog.createLoadingDialog(HqMacretail.this,false);
        dialog.show();
        UserManager.getUserManager().getMacRetaInfo(userName, new ResultCallback<String>() {
            @Override
            public void onError(int status, String errorMsg) {
                dialog.dismiss();
                MyToastUtils.showShortToast(HqMacretail.this,errorMsg);
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
                    if (!TextUtils.isEmpty(time)) {
                        tv_mtime.setText(time);
                    }
                    if (!TextUtils.isEmpty(flag)) {
                        qgflag = flag;//得到全国flag
                    }
                    JSONArray title = data.getJSONArray("titles");
                    if (title.length() > 0) {
                        titlelist = gson.fromJson(title.toString(), new TypeToken<List<String>>() {
                        }.getType());
                        if (titlelist != null && titlelist.size() > 0) {
                            ll_mtitle.removeAllViews();
                            int width = CadillacUtils.getScreenWidth(HqMacretail.this) / titlelist.size();
                            for (int i = 0; i < titlelist.size(); i++) {
                                TextView textView = new TextView(HqMacretail.this);
                                textView.setLayoutParams(new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.MATCH_PARENT));
                                textView.setTextColor(Color.parseColor("#ffffff"));
                                textView.setText(titlelist.get(i) + "");
                                textView.setTextSize(14);
                                textView.setGravity(Gravity.CENTER);
                                View view=new View(HqMacretail.this);
                                view.setLayoutParams(new LinearLayout.LayoutParams(1, LinearLayout.LayoutParams.MATCH_PARENT));
                                view.setBackgroundColor(Color.parseColor("#bcb7b8"));//竖着先
                                ll_mtitle.addView(textView);
                                ll_mtitle.addView(view);
                                ll_mtitle.setBackgroundColor(Color.parseColor("#8a152b"));
                            }
                        }

                        JSONArray list = data.getJSONArray("list");//全国数据
                        textlist = gson.fromJson(list.toString(), new TypeToken<List<Map<String, Object>>>() {
                        }.getType());
                        if (textlist.size() > 0) {
                            ll_mqg.removeAllViews();
                            int width = CadillacUtils.getScreenWidth(HqMacretail.this) / titlelist.size();
                            for (int i = 0; i < titlelist.size(); i++) {
                                TextView textView = new TextView(HqMacretail.this);
                                textView.setLayoutParams(new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.MATCH_PARENT));
                                String obj=textlist.get(0).get(titlelist.get(i)).toString();
                                if(i==0) {
                                    textView.setTextColor(Color.parseColor("#333333"));//设置颜色
                                }else {
                                    textView.setTextColor(Color.parseColor(changColor(obj)));
                                }
                                textView.setText(interCeption(obj));
                                textView.setTextSize(12);
                                textView.setGravity(Gravity.CENTER);

                                View view = new View(HqMacretail.this);
                                view.setLayoutParams(new LinearLayout.LayoutParams(1, LinearLayout.LayoutParams.MATCH_PARENT));
                                view.setBackgroundColor(Color.parseColor("#bcb7b8"));//竖着先
                                ll_mqg.addView(textView);
                                ll_mqg.addView(view);
                                ll_mqg.setBackgroundColor(Color.parseColor("#E4E4E4"));
//
                                TextView tv= (TextView) ll_mqg.getChildAt(0);
                                tv.setBackgroundResource(R.mipmap.expase_no);
                                ll_mqg.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if(!dqisshow){//显示大区
                                            ll_mdq.setVisibility(View.VISIBLE);
                                            dqisshow=true;
//                                            TextView textView1= (TextView) ll_mqg.getChildAt(0);
//                                            textView1.setBackgroundResource(R.mipmap.expase_yes);
//                                            textView1.setTextColor(Color.parseColor("#ffffff"));
                                        }else {//隐藏各个大区
                                            dqisshow=false;
                                            ll_mdq.setVisibility(View.GONE);
//                                            TextView tv= (TextView) ll_mqg.getChildAt(0);
//                                            tv.setBackgroundResource(R.mipmap.expase_no);
//                                            tv.setTextColor(Color.parseColor("#333333"));
                                        }
                                    }
                                });
                            }
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                getHqMacRegional("", qgflag,ll_mqg);//得到大区数据和大区list
            }

        });
    }

    /**得到大区数据和大区的集合
     * @param
     * @param
     */
    private void getHqMacRegional(final String param, String flag,LinearLayout linearLayout) {
        dialog = CProgressDialog.createLoadingDialog(HqMacretail.this,false);
        dialog.show();
        UserManager.getUserManager().getHqMacRegional(param, flag, new ResultCallback<String>() {
            @Override
            public void onError(int status, String errorMsg) {
                dialog.dismiss();
                MyToastUtils.showShortToast(HqMacretail.this,errorMsg);
            }

            @Override
            public void onResponse(String response) {
                dialog.dismiss();
//                TextView tv= (TextView) ll_mqg.getChildAt(0);
////                tv.setCompoundDrawables(null,null,drawable,null);
//                tv.setBackgroundResource(R.mipmap.expase_yes);
//                tv.setTextColor(Color.parseColor("#ffffff"));
                dqisshow = true;//大区显示
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
                        ll_mdq.removeAllViews();
                        for (int i = 0; i < dqlist.size(); i++) {
                            final LinearLayout lin = new LinearLayout(HqMacretail.this);//大区布局
                            lin.setOrientation(LinearLayout.HORIZONTAL);
                            lin.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,CadillacUtils.dip2px(HqMacretail.this,50)));
                            lin.setBackgroundColor(Color.parseColor("#F1F1F1"));
                            View linview=new View(HqMacretail.this);
                            linview.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,1));
                            linview.setBackgroundColor(Color.parseColor("#8a152b"));//横着的颜色

                            final Map<String,Object>map=dqlist.get(i);
                            int width = CadillacUtils.getScreenWidth(HqMacretail.this) / titlelist.size();
                            for (int j=0;j<titlelist.size();j++){
                                TextView textView = new TextView(HqMacretail.this);
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
                                View view = new View(HqMacretail.this);
                                view.setLayoutParams(new LinearLayout.LayoutParams(1, LinearLayout.LayoutParams.MATCH_PARENT));
                                view.setBackgroundColor(Color.parseColor("#bcb7b8"));//竖着颜色
                                lin.addView(textView);
                                lin.addView(view);
                            }

                            final LinearLayout maclin=new LinearLayout(HqMacretail.this);//mac布局
                            maclin.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                            maclin.setOrientation(LinearLayout.VERTICAL);
                            maclin.setBackgroundColor(Color.parseColor("#F3F3F3"));
                            lin.setTag(i);
                            final int pos=i;
                            TextView textView= (TextView) lin.getChildAt(0);
//                            textView.setCompoundDrawables(null,null,drawable1,null);
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
                            ll_mdq.addView(linview);
                            ll_mdq.addView(lin);
                            ll_mdq.addView(maclin);

                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**获取各大区下面的mac数据和集合
     * @param
     * @param
     * @param maclin
     * @param lin
     */
    private void getMaclist(String param, String flag, final LinearLayout maclin, final LinearLayout lin) {
        dialog = CProgressDialog.createLoadingDialog(HqMacretail.this,false);
        dialog.show();
        UserManager.getUserManager().getHqMacRegional(param, flag, new ResultCallback<String>() {
            @Override
            public void onError(int status, String errorMsg) {
                dialog.dismiss();
                MyToastUtils.showShortToast(HqMacretail.this,errorMsg);
            }

            @Override
            public void onResponse(String response) {
                dialog.dismiss();
//                TextView tv= (TextView) lin.getChildAt(0);
////                tv.setCompoundDrawables(null,null,drawable,null);
//                tv.setBackgroundResource(R.mipmap.expase_yes);
//                tv.setTextColor(Color.parseColor("#ffffff"));
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
                            final LinearLayout lin = new LinearLayout(HqMacretail.this);
                            lin.setOrientation(LinearLayout.HORIZONTAL);
                            lin.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,CadillacUtils.dip2px(HqMacretail.this,50)));
                            lin.setBackgroundColor(Color.parseColor("#f3f3f3"));
                            View linview=new View(HqMacretail.this);
                            linview.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,1));
                            linview.setBackgroundColor(Color.parseColor("#bcb7b8"));//横着的颜色

                            final Map<String,Object>map=maclist.get(i);
                            int width = CadillacUtils.getScreenWidth(HqMacretail.this) / titlelist.size();
                            for (int j=0;j<titlelist.size();j++){
                                TextView textView = new TextView(HqMacretail.this);
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
                                View view = new View(HqMacretail.this);
                                view.setLayoutParams(new LinearLayout.LayoutParams(1, LinearLayout.LayoutParams.MATCH_PARENT));
                                view.setBackgroundColor(Color.parseColor("#bcb7b8"));//竖着颜色
                                lin.addView(textView);
                                lin.addView(view);
                            }

                            maclin.addView(linview);
                            maclin.addView(lin);
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
