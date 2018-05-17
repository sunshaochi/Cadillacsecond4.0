package cadillac.example.com.cadillac.activity;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cadillac.example.com.cadillac.R;
import cadillac.example.com.cadillac.base.BaseActivity;
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
 * 裸车毛利
 * Created by bitch-1 on 2017/5/22.
 */

public class NakedcarAct extends BaseActivity {

    @ViewInject(R.id.tv_lname)
    private TextView tv_lname;//店名
    @ViewInject(R.id.tv_lcode)
    private TextView tv_lcode;//代号
    @ViewInject(R.id.ll_luotitle)
    private LinearLayout ll_luotitle;//红色title布局
    @ViewInject(R.id.ll_list)
    private LinearLayout ll_list;

//    private Dialog dialog;


    private List<String>ttlist;//红色title
    private List<Map<String,Object>>list;

    @Override
    public void setLayout() {
        setContentView(R.layout.act_nakedcar);

    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("裸车毛利");
        ll_luotitle.setBackgroundColor(Color.parseColor("#00000000"));
        getJxsLm(CadillacUtils.getCurruser().getUserName());//获取经销商裸车毛利

    }

    private void getJxsLm(String userName) {
        final Dialog erdialog = CProgressDialog.createLoadingDialog(NakedcarAct.this,false);
        erdialog.show();
        UserManager.getUserManager().getJxsLm(userName, new ResultCallback<String>() {
            @Override
            public void onError(int status, String errorMsg) {
                erdialog.dismiss();
                MyToastUtils.showShortToast(NakedcarAct.this,errorMsg);
            }

            @Override
            public void onResponse(String response) {
                erdialog.dismiss();
                try {
                    Gson gson=new Gson();
                    JSONObject jsonobject=new JSONObject(response);
                    JSONObject data=jsonobject.getJSONObject("obj");
                    String dealerName=data.getString("dealerName");
                    String dealerCode=data.getString("dealerCode");
                    String isEdit=data.getString("isEdit");

                    tv_lname.setText(dealerName);
                    tv_lcode.setText(dealerCode);

                        if(isEdit.equals("1")){//能编辑
                            setRightimage(R.mipmap.editor, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Bundle bundle=new Bundle();
                                    bundle.putString("type","3");
                                    openActivity(BianjiAct.class,bundle);
                                }
                            });
                        }

                    JSONArray titlelist=data.getJSONArray("titles");
                    if(titlelist.length()>0){
                        ttlist=gson.fromJson(titlelist.toString(),new TypeToken<List<String>>(){}.getType());
                        if(ttlist!=null&&ttlist.size()>0){
                            ll_luotitle.removeAllViews();
                            int width = CadillacUtils.getScreenWidth(NakedcarAct.this) / ttlist.size();
                            for (int i = 0; i < ttlist.size(); i++) {
                                TextView textView = new TextView(NakedcarAct.this);
                                textView.setLayoutParams(new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.MATCH_PARENT));
                                textView.setTextColor(Color.parseColor("#ffffff"));
                                textView.setText(ttlist.get(i) + "");
                                textView.setTextSize(12);
                                textView.setGravity(Gravity.CENTER);
                                ll_luotitle.addView(textView);
                                ll_luotitle.setBackgroundColor(Color.parseColor("#8a152b"));
                            }
                        }
                    }

                    JSONArray textlist=data.getJSONArray("list");
                    if(textlist.length()>0){
                        list=gson.fromJson(textlist.toString(),new TypeToken<List<Map<String,Object>>>(){}.getType());
                        if(list!=null&&list.size()>0){
                            ll_list.removeAllViews();
                            for (int i=0;i<list.size();i++){
                                LinearLayout lin = new LinearLayout(NakedcarAct.this);
                                lin.setOrientation(LinearLayout.HORIZONTAL);
                                lin.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,CadillacUtils.dip2px(NakedcarAct.this,50)));
                                lin.setBackgroundColor(Color.parseColor("#ffffff"));
                                View linview=new View(NakedcarAct.this);
                                linview.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,1));
                                linview.setBackgroundColor(Color.parseColor("#E7E7E7"));//横着的颜色

                                final Map<String,Object>map=list.get(i);
                                int width = CadillacUtils.getScreenWidth(NakedcarAct.this) / ttlist.size();
                                for (int j=0;j<ttlist.size();j++){
                                    TextView textView = new TextView(NakedcarAct.this);
                                    textView.setLayoutParams(new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.MATCH_PARENT));
                                    textView.setTextColor(Color.parseColor("#333333"));
                                    if(!map.containsKey(ttlist.get(j))){
                                        String obj="-";
                                        textView.setText(obj);
                                    }else {
                                        String obj = map.get(ttlist.get(j)).toString();
                                        if(!obj.contains("{")){//表示是日期
                                            textView.setText(obj);
                                        }else {//表示车型数据
                                            ProfitBean bean=GsonUtils.gsonToBean(obj,ProfitBean.class);
                                            textView.setText(bean.getProfitData());
                                        }
//                                        if(obj.contains("{")){
//                                            String profitData=obj.split(",")[1].split("=")[1].replace("}","");
//                                            textView.setText(profitData);
//                                        }else {//单纯时间
//                                            textView.setText(obj);
//                                        }
//                                        textView.setText(obj);
                                    }
//                                    if(!TextUtils.isEmpty(obj)) {
//                                        textView.setText(obj);
//                                    }else {
//                                        textView.setText("-");
//                                    }
                                    textView.setTextSize(12);
                                    textView.setGravity(Gravity.CENTER);
                                    View view = new View(NakedcarAct.this);
                                    view.setLayoutParams(new LinearLayout.LayoutParams(1, LinearLayout.LayoutParams.MATCH_PARENT));
                                    view.setBackgroundColor(Color.parseColor("#E7E7E7"));//竖着颜色
                                    lin.addView(textView);
                                    lin.addView(view);
                                }
                                ll_list.addView(linview);
                                ll_list.addView(lin);


                            }

                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }





    protected void onResume() {
        super.onResume();
        ll_luotitle.removeAllViews();
        ll_list.removeAllViews();
        ll_luotitle.setBackgroundColor(Color.parseColor("#00000000"));
        getJxsLm(CadillacUtils.getCurruser().getUserName());//获取经销商裸车毛利

    }
}
