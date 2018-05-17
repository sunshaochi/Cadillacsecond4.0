package cadillac.example.com.cadillac.activity;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import cadillac.example.com.cadillac.base.BaseActivity;
import cadillac.example.com.cadillac.bean.ProfitBean;
import cadillac.example.com.cadillac.http.Manage.UserManager;
import cadillac.example.com.cadillac.http.ResultCallback;
import cadillac.example.com.cadillac.utils.CadillacUtils;
import cadillac.example.com.cadillac.utils.GsonUtils;
import cadillac.example.com.cadillac.utils.MyToastUtils;
import cadillac.example.com.cadillac.view.CProgressDialog;

/**
 * Created by iris on 2018/4/24.
 */

public class NewNakeCarAct extends BaseActivity {

    @ViewInject(R.id.tv_lname)
    private TextView tv_lname;//店名
    @ViewInject(R.id.tv_lcode)
    private TextView tv_lcode;//代号
    @ViewInject(R.id.ll_luotitle)
    private LinearLayout ll_luotitle;//红色title布局

    @ViewInject(R.id.lv_list)
    private ListView lv_list;


//    private Dialog dialog;


    private List<String> ttlist;//红色title
    private List<Map<String, Object>> list;

    private List<String>notimelist=new ArrayList<>();

    @Override
    public void setLayout() {
        setContentView(R.layout.act_newnakedcar);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("裸车/综合毛利");
        ll_luotitle.setBackgroundColor(Color.parseColor("#00000000"));
        getJxsLm(CadillacUtils.getCurruser().getUserName());//获取经销商裸车毛利



    }

    private void getJxsLm(String userName) {
        final Dialog erdialog = CProgressDialog.createLoadingDialog(NewNakeCarAct.this, false);
        erdialog.show();
        UserManager.getUserManager().getJxsLm(userName, new ResultCallback<String>() {
            @Override
            public void onError(int status, String errorMsg) {
                erdialog.dismiss();
                MyToastUtils.showShortToast(NewNakeCarAct.this, errorMsg);
            }

            @Override
            public void onResponse(String response) {
                erdialog.dismiss();
                try {
                    Gson gson = new Gson();
                    JSONObject jsonobject = new JSONObject(response);
                    JSONObject data = jsonobject.getJSONObject("obj");
                    String dealerName = data.getString("dealerName");
                    String dealerCode = data.getString("dealerCode");
                    String isEdit = data.getString("isEdit");

                    tv_lname.setText(dealerName);
                    tv_lcode.setText(dealerCode);

                    if (isEdit.equals("1")) {//能编辑
                        setRightimage(R.mipmap.editor, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Bundle bundle = new Bundle();
                                bundle.putString("type", "3");
                                openActivity(BianjiAct.class, bundle);
                            }
                        });
                    }

                    JSONArray titlelist = data.getJSONArray("titles");
                    ttlist = gson.fromJson(titlelist.toString(), new TypeToken<List<String>>() {
                    }.getType());

                    ll_luotitle.removeAllViews();
                    int width = CadillacUtils.getScreenWidth(NewNakeCarAct.this) / ttlist.size();
                    for (int i = 0; i < ttlist.size(); i++) {
                        TextView textView = new TextView(NewNakeCarAct.this);
                        textView.setLayoutParams(new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.MATCH_PARENT));
                        textView.setTextColor(Color.parseColor("#ffffff"));
                        textView.setText(ttlist.get(i) + "");
                        textView.setTextSize(12);
                        textView.setGravity(Gravity.CENTER);
                        ll_luotitle.addView(textView);
                        ll_luotitle.setBackgroundColor(Color.parseColor("#8a152b"));
                    }

                    notimelist.clear();
                    for(int i=0;i<ttlist.size();i++){
                        if(ttlist.get(i).equals("日期")){

                        }else {
                            notimelist.add(ttlist.get(i));
                        }
                    }

//
                    JSONArray textlist = data.getJSONArray("list");
                    list = gson.fromJson(textlist.toString(), new TypeToken<List<Map<String, Object>>>() {
                    }.getType());
                    lv_list.setAdapter(new MyAdapter());


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    public class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = View.inflate(NewNakeCarAct.this, R.layout.act_naketitleitem, null);
            LinearLayout ll_itemtitle = (LinearLayout) view.findViewById(R.id.ll_itemtitle);
            LinearLayout ll_itemzh= (LinearLayout) view.findViewById(R.id.ll_itemzh);

            TextView tv= (TextView) view.findViewById(R.id.tv_time);

            Map<String, Object> map = list.get(i);
            int width = CadillacUtils.getScreenWidth(NewNakeCarAct.this) / ttlist.size();

            tv.setWidth(width);
            ll_itemtitle.removeAllViews();
            ll_itemzh.removeAllViews();

             for(int j=0;j<ttlist.size();j++) {
                 if (map.containsKey(ttlist.get(j))) {
                     String obj = map.get(ttlist.get(j)).toString();
                     if (!obj.contains("{")) {//表示是日期
                         tv.setText(obj);
                     }
                 }

             }

            for (int j = 0; j < notimelist.size(); j++) {
                TextView textView = new TextView(NewNakeCarAct.this);
                AbsListView.LayoutParams lp=new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT);
                lp.width=width;
                textView.setLayoutParams(lp);
                textView.setTextColor(Color.parseColor("#333333"));


                TextView textView1 = new TextView(NewNakeCarAct.this);
                AbsListView.LayoutParams zhlp=new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT);
                zhlp.width=width;
                textView1.setLayoutParams(lp);
                textView1.setTextColor(Color.parseColor("#333333"));

                if (!map.containsKey(notimelist.get(j))) {
                    String obj = "-";
                    textView.setText(obj);
                    textView1.setText(obj);
                } else {
                    String obj = map.get(notimelist.get(j)).toString();
                    if (!obj.contains("{")) {//表示是日期
                        tv.setText(obj);     //这个if其实没用了
                    } else {//表示车型数据
                        ProfitBean bean = GsonUtils.gsonToBean(obj, ProfitBean.class);
                        textView.setText(bean.getProfitData());
                        textView1.setText(bean.getComprehensiveProfit());
                    }



                }
                textView.setTextSize(12);
                textView.setGravity(Gravity.CENTER);
                View linview = new View(NewNakeCarAct.this);
                linview.setLayoutParams(new AbsListView.LayoutParams(1, AbsListView.LayoutParams.MATCH_PARENT));
                linview.setBackgroundColor(Color.parseColor("#E7E7E7"));//竖着颜色

                ll_itemtitle.addView(textView);
                ll_itemtitle.addView(linview);


                textView1.setTextSize(12);
                textView1.setGravity(Gravity.CENTER);
                View linview1 = new View(NewNakeCarAct.this);
                linview1.setLayoutParams(new AbsListView.LayoutParams(1, AbsListView.LayoutParams.MATCH_PARENT));
                linview1.setBackgroundColor(Color.parseColor("#E7E7E7"));//竖着颜色

                ll_itemzh.addView(textView1);
                ll_itemzh.addView(linview1);

            }

            return view;
        }

    }

    protected void onResume() {
        super.onResume();
        ll_luotitle.removeAllViews();
        ll_luotitle.setBackgroundColor(Color.parseColor("#00000000"));
        getJxsLm(CadillacUtils.getCurruser().getUserName());//获取经销商裸车毛利

    }
}