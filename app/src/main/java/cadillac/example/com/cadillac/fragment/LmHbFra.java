package cadillac.example.com.cadillac.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import cadillac.example.com.cadillac.R;
import cadillac.example.com.cadillac.activity.LuocheMaoliAct;
import cadillac.example.com.cadillac.base.BaseFrag;
import cadillac.example.com.cadillac.bean.TimeListBean;
import cadillac.example.com.cadillac.http.Manage.UserManager;
import cadillac.example.com.cadillac.http.ResultCallback;
import cadillac.example.com.cadillac.utils.CadillacUtils;
import cadillac.example.com.cadillac.utils.MyLogUtils;
import cadillac.example.com.cadillac.utils.MyToastUtils;
import cadillac.example.com.cadillac.utils.SpUtils;
import cadillac.example.com.cadillac.view.CProgressDialog;

/**
 * Created by iris on 2018/1/16.
 */

public class LmHbFra extends BaseFrag{
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
    @ViewInject(R.id.rb_jd)
    private RadioButton rb_jd;//绝对值

    private List<TimeListBean> listtime;//时间集合
    private List<String>titlelist;//红色标题栏
    private String qgflag,dqflag,macflag;//全国flag


    private boolean dqisshow,macisshow,jxsshow;

    private String inputTimeid;//时间id
    private String dateid;
    private Dialog dialog;


    private List<Map<String, Object>> textlist;//全国集合
    private List<Map<String, Object>> dqlist;//大区数据(全国下列表)
    private List<Map<String, Object>> maclist;//maclist数据(全国下列表)
    private List<Map<String, Object>> jxslist;//经销商集合
    private final Handler mHandler = new Handler();//用来时时更新scorllview的位置
    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fra_lm,null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        inputTimeid= SpUtils.getInputTimeId(getActivity());
        dateid=SpUtils.getDateId(getActivity());
        getMacluom(CadillacUtils.getCurruser().getUserName(),"2",inputTimeid,dateid);//获取mac裸车毛利绝对值
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
        if (res.size() > 0) {
            ll_maclmqg.removeAllViews();//全国父容器
            int width = CadillacUtils.getScreenWidth(getActivity()) / titlelist.size();
            for (int i = 0; i < res.get(0).size(); i++) {
                TextView textView = new TextView(getActivity());
                textView.setLayoutParams(new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.MATCH_PARENT));
                String obj=res.get(0).get(titlelist.get(i)).toString();
                if(i==0) {
                    textView.setTextColor(Color.parseColor("#333333"));//设置颜色
                }else {
                    textView.setTextColor(Color.parseColor(changColor(obj)));
                }
                textView.setText(interCeption(obj));
                textView.setTextSize(12);
                textView.setGravity(Gravity.CENTER);

                View view = new View(getActivity());
                view.setLayoutParams(new LinearLayout.LayoutParams(1, LinearLayout.LayoutParams.MATCH_PARENT));
                view.setBackgroundColor(Color.parseColor("#bcb7b8"));//竖着先
                ll_maclmqg.addView(textView);
                ll_maclmqg.addView(view);
                ll_maclmqg.setBackgroundColor(Color.parseColor("#E4E4E4"));
                TextView tv= (TextView) ll_maclmqg.getChildAt(0);
                tv.setBackgroundResource(R.mipmap.expase_no);
                ll_maclmqg.setOnClickListener(new View.OnClickListener() {//点击全国
                    @Override
                    public void onClick(View view) {
                        if(!dqisshow){//显示大区
//                              getRegional(qgflag,"全国",inputTimeid,"1",ll_maclmqg);//得到大区数据和大区list
                            ll_maclmdqlist.setVisibility(View.VISIBLE);
                            dqisshow=true;
//                            TextView textView1= (TextView) ll_maclmqg.getChildAt(0);
////                            textView1.setCompoundDrawables(null,null,drawable1,null);
//                            textView1.setBackgroundResource(R.mipmap.expase_yes);
//                            textView1.setTextColor(Color.parseColor("#ffffff"));
                        }else {//隐藏各个大区
//                            TextView textView1= (TextView) ll_maclmqg.getChildAt(0);
////                            textView1.setCompoundDrawables(null,null,drawable1,null);
//                            textView1.setBackgroundResource(R.mipmap.expase_no);
//                            textView1.setTextColor(Color.parseColor("#333333"));
                            dqisshow=false;
//                            ll_maclmdqlist.removeAllViews();
                            ll_maclmdqlist.setVisibility(View.GONE);
                        }
                    }
                });
            }
            getRegional(qgflag,"全国",inputTimeid,"2",ll_maclmqg);//获取大区
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
//                TextView tv= (TextView) llmac.getChildAt(0);
////                tv.setCompoundDrawables(null,null,drawable,null);
//                tv.setBackgroundResource(R.mipmap.expase_yes);
//                tv.setTextColor(Color.parseColor("#ffffff"));
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
                            final LinearLayout lin = new LinearLayout(getActivity());//大区布局
                            lin.setOrientation(LinearLayout.HORIZONTAL);
                            lin.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,CadillacUtils.dip2px(getActivity(),50)));
                            lin.setBackgroundColor(Color.parseColor("#F1F1F1"));
                            View linview=new View(getActivity());
                            linview.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,1));
                            linview.setBackgroundColor(Color.parseColor("#8a152b"));//横着的颜色

                            final Map<String,Object>map=dqlist.get(i);
                            int width = CadillacUtils.getScreenWidth(getActivity()) / titlelist.size();
                            for (int j=0;j<titlelist.size();j++){
                                TextView textView = new TextView(getActivity());
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
                                View view = new View(getActivity());
                                view.setLayoutParams(new LinearLayout.LayoutParams(1, LinearLayout.LayoutParams.MATCH_PARENT));
                                view.setBackgroundColor(Color.parseColor("#bcb7b8"));//竖着颜色
                                lin.addView(textView);
                                lin.addView(view);
                            }
                            final LinearLayout maclin=new LinearLayout(getActivity());//mac布局
                            maclin.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                            maclin.setOrientation(LinearLayout.VERTICAL);
                            maclin.setBackgroundColor(Color.parseColor("#F3F3F3"));
                            lin.setTag(i);
                            final int pos=i;
                            TextView tv1= (TextView) lin.getChildAt(0);
//                            tv1.setCompoundDrawables(null,null,drawable1,null);//向下
                            tv1.setBackgroundResource(R.mipmap.expase_no);
                            lin.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //map.get("经销商")= {code=1212,name=Cadillac三区}
                                    if(lin.getTag().toString().equals(pos +"")) {
                                        String obj = map.get("经销商") + "";
//                                        MyLogUtils.info("obj"+obj.toString());
//                                        String parm = obj.split(",")[0].split("=")[1];
                                        String parm = interCeption(obj);
                                        getMaclist(dqflag,parm,inputTimeid,"2",maclin,lin);//得到大区数据和大区list

//                                        getMaclist(parm.substring(0, parm.length() - 1), dqflag,maclin,lin);//获取各个大区下的mac列表（显示各个mac）
                                    }else {
                                        lin.setTag(pos);
//                                        TextView tv= (TextView) lin.getChildAt(0);
////                                        tv.setCompoundDrawables(null,null,drawable1,null);//向下
//                                        tv.setBackgroundResource(R.mipmap.expase_no);
//                                        tv.setTextColor(Color.parseColor("#333333"));
                                        maclin.removeAllViews();

                                    }

                                }
                            });
                            ll_maclmdqlist.addView(lin);//内容
                            ll_maclmdqlist.addView(linview);//分割线
                            ll_maclmdqlist.addView(maclin);//小区布局

                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


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
     * @param lin
     */
    private void getMaclist(String flag, final String param, String inputTimeId, String dataType, final LinearLayout maclin, final LinearLayout lin) {
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
                            final LinearLayout lin = new LinearLayout(getActivity());
                            lin.setOrientation(LinearLayout.HORIZONTAL);
                            lin.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,CadillacUtils.dip2px(getActivity(),50)));
                            lin.setBackgroundColor(Color.parseColor("#f3f3f3"));
                            View linview=new View(getActivity());
                            linview.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,1));
                            linview.setBackgroundColor(Color.parseColor("#bcb7b8"));//横着的颜色

                            final Map<String,Object>map=maclist.get(i);
                            int width = CadillacUtils.getScreenWidth(getActivity()) / titlelist.size();
                            for (int j=0;j<titlelist.size();j++){
                                TextView textView = new TextView(getActivity());
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
                                View view = new View(getActivity());
                                view.setLayoutParams(new LinearLayout.LayoutParams(1, LinearLayout.LayoutParams.MATCH_PARENT));
                                view.setBackgroundColor(Color.parseColor("#bcb7b8"));//竖着颜色
                                lin.addView(textView);
                                lin.addView(view);
                            }
                            final LinearLayout jxslin=new LinearLayout(getActivity());//mac布局
                            jxslin.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                            jxslin.setOrientation(LinearLayout.VERTICAL);
                            jxslin.setBackgroundColor(Color.parseColor("#ffffff"));

                            lin.setTag(i);
                            final int pos=i;
                            TextView tv1= (TextView) lin.getChildAt(0);
//                            tv1.setCompoundDrawables(null,null,drawable1,null);//向下
                            tv1.setBackgroundResource(R.mipmap.xq_expanle);
                            lin.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //map.get("经销商")= {code=1212,name=Cadillac三区}
                                    if(lin.getTag().toString().equals(pos+"")) {
                                        String obj = map.get("经销商") + "";
//                                        String parm = obj.split(",")[0].split("=")[1];
//                                        String macname=parm.substring(0,parm.length()-1).toString();
                                        String parm = interCeption(obj);

                                        getjxslist(macflag,parm,inputTimeid, "2",jxslin,lin);//获取mac下的经销商列表（显示各个经销商）
                                    }else {
                                        lin.setTag(pos);
                                        jxslin.removeAllViews();
//                                        TextView tv1= (TextView) lin.getChildAt(0);
////                                        tv1.setCompoundDrawables(null,null,drawable1,null);//向下
//                                        tv1.setBackgroundResource(R.mipmap.expase_no);
//                                        tv1.setTextColor(Color.parseColor("#333333"));
                                    }

                                }
                            });

                            maclin.addView(lin);
                            maclin.addView(linview);
                            maclin.addView(jxslin);
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
     * @param lin
     */
    private void getjxslist(String flag, String param, String inputTimeId, final String dataType, final LinearLayout jxslin, final LinearLayout lin) {
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
//                TextView tv= (TextView) lin.getChildAt(0);
////                tv.setCompoundDrawables(null,null,drawable,null);
//                tv.setBackgroundResource(R.mipmap.expase_yes);
//                tv.setTextColor(Color.parseColor("#ffffff"));
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
                            LinearLayout lin = new LinearLayout(getActivity());
                            lin.setOrientation(LinearLayout.HORIZONTAL);
                            lin.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,CadillacUtils.dip2px(getActivity(),50)));
                            lin.setBackgroundColor(Color.parseColor("#ffffff"));
                            View linview=new View(getActivity());
                            linview.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,1));
                            linview.setBackgroundColor(Color.parseColor("#bcb7b8"));//横着的颜色

                            final Map<String,Object>map=jxslist.get(i);
                            int width = CadillacUtils.getScreenWidth(getActivity()) / titlelist.size();
                            for (int j=0;j<titlelist.size();j++){
                                TextView textView = new TextView(getActivity());
                                textView.setLayoutParams(new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.MATCH_PARENT));
                                String obj=map.get(titlelist.get(j)).toString();
                                textView.setTextColor(Color.parseColor(changColor(obj)));//设置颜色
                                textView.setText(interCeption(obj));
                                textView.setTextSize(12);
                                textView.setGravity(Gravity.CENTER);
                                View view = new View(getActivity());
                                view.setLayoutParams(new LinearLayout.LayoutParams(1, LinearLayout.LayoutParams.MATCH_PARENT));
                                view.setBackgroundColor(Color.parseColor("#E7E7E7"));//竖着颜色
                                lin.addView(textView);
                                lin.addView(view);
                            }

                            jxslin.addView(lin);
                            jxslin.addView(linview);
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
                sv_my.scrollTo(0, off);
            }

        }
    };

    public void upDate() {
        inputTimeid= SpUtils.getInputTimeId(getActivity());
        dateid=SpUtils.getDateId(getActivity());
        getMacluom(CadillacUtils.getCurruser().getUserName(),"2",inputTimeid,dateid);//获取mac裸车毛利绝对值
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            if(inputTimeid.equals(SpUtils.getInputTimeId(getActivity()))&&dateid.equals(SpUtils.getDateId(getActivity()))){

            }else {
                inputTimeid= SpUtils.getInputTimeId(getActivity());
                dateid=SpUtils.getDateId(getActivity());
                getMacluom(CadillacUtils.getCurruser().getUserName(),"2",inputTimeid,dateid);//获取mac裸车毛利绝对值
            }
        }
    }
}
