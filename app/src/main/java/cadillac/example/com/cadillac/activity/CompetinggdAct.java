package cadillac.example.com.cadillac.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ist.cadillacpaltform.SDK.bean.HighStockAge.City;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cadillac.example.com.cadillac.R;
import cadillac.example.com.cadillac.adapter.CityAdapter;
import cadillac.example.com.cadillac.adapter.ProAdpt;
import cadillac.example.com.cadillac.base.BaseActivity;
import cadillac.example.com.cadillac.bean.CarBrandBean;
import cadillac.example.com.cadillac.bean.CompChexinBean;
import cadillac.example.com.cadillac.bean.CompetingBean;
import cadillac.example.com.cadillac.bean.ResultsBean;
import cadillac.example.com.cadillac.bean.ResultsNewBean;
import cadillac.example.com.cadillac.http.Manage.UserManager;
import cadillac.example.com.cadillac.http.ResultCallback;
import cadillac.example.com.cadillac.utils.CadillacUtils;
import cadillac.example.com.cadillac.utils.MyToastUtils;
import cadillac.example.com.cadillac.utils.SpUtils;


/**
 * 竞品价格
 * Created by bitch-1 on 2017/5/22.
 */

public class CompetinggdAct extends BaseActivity {

    @ViewInject(R.id.tv_city)
    private TextView tv_city;
    @ViewInject(R.id.ll_select)
    private LinearLayout ll_select;//城市选择的整个布局
    @ViewInject(R.id.lv_pro)
    private ListView lv_pro;//省份的listview
    @ViewInject(R.id.lv_city)
    private ListView lv_city;//城市选择

    @ViewInject(R.id.ll_cx)
    private LinearLayout ll_cx;//车型的linearLayout;
    @ViewInject(R.id.sc_view)
    private ScrollView sc_view;


    private String isedit;//是否可编辑0是不可编辑
    private List<String> prolist;//省份集合
    private List<String> baocprolist=new ArrayList<>();//保存省份集合
    private List<CarBrandBean>carbrandlist;//品牌和品牌下面的车型如宝马品牌下面的3系5系
    private List<CompChexinBean>chexinlist;//车系下面的车型如2.0T1.8T
    private CompetingBean competingbean;
    private boolean isshow;//第一次点击
    private ProAdpt proadpt;//省份适配器
    private CityAdapter cityadpt;//城市适配器
    private List<String>citylist;//城市集合

    private String provice;//省
    private String city;//市


    private final Handler mHandler = new Handler();//用来时时更新scorllview的位置
    @Override
    public void setLayout() {
        setContentView(R.layout.act_competingggd);

    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("竞品价格");
        ll_select.setVisibility(View.GONE);//整个布局影藏
        provice="";//每次进来默认省为空
        city="";//每次进来默认市为空

        getProbrand(CadillacUtils.getCurruser().getUserName(),"","",provice,city);//获取车牌和省份

    }

    private void getProbrand(String userName, String carBrand, final String carClass, final String province, final String city) {
        UserManager.getUserManager().getProbrand(userName, carBrand, carClass, province, city, new ResultCallback<ResultsNewBean<CompetingBean>>() {
            @Override
            public void onError(int status, String errorMsg) {
                MyToastUtils.showShortToast(CompetinggdAct.this,errorMsg);
            }

            @Override
            public void onResponse(ResultsNewBean<CompetingBean> response) {
                competingbean=response.getObj();
                if(competingbean!=null){
                    isedit=competingbean.getIsEdit();
                    if(isedit.equals("1")){
                       setRightimage(R.mipmap.editor, new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                               openActivity(CompentBianjiAct.class);
                           }
                       });
                    }
                   prolist=competingbean.getProvinceList();//省份集合
                    if(prolist!=null&&prolist.size()>0){
                        baocprolist.clear();
                        for(int p=0;p<prolist.size();p++){
                            baocprolist.add(prolist.get(p));
                        }
                    }
                   carbrandlist=competingbean.getList();//品牌和品牌下面的车型
                    if(carbrandlist!=null&&carbrandlist.size()>0){
                        ll_cx.removeAllViews();
                        for(int i=0;i<carbrandlist.size();i++){
                            View linearyoutview=View.inflate(CompetinggdAct.this,R.layout.compgdview,null);//品牌
                            TextView tv= (TextView) linearyoutview.findViewById(R.id.tv_pp);
                            final ImageView iv= (ImageView) linearyoutview.findViewById(R.id.iv_change);
                            final String carbrand=carbrandlist.get(i).getCarBrand().toString();
                            tv.setText(carbrand);
                            final LinearLayout cxlin = new LinearLayout(CompetinggdAct.this);//品牌下面的车型
                            cxlin.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                            cxlin.setOrientation(LinearLayout.VERTICAL);

                            ll_cx.addView(linearyoutview);//添加品牌到容器
                            ll_cx.addView(cxlin);//车系，添加车系的容器到容器


                            for (int j=0;j<carbrandlist.get(i).getData().size();j++) {

                                /**因为直接new linneayout 出不来效果imagview不能设置居中**/
                                final View texlin=View.inflate(CompetinggdAct.this,R.layout.item_texlin,null);//车系如3x 5x
                                TextView tv_cx= (TextView) texlin.findViewById(R.id.tv_cx);
                                final ImageView iv_cx= (ImageView) texlin.findViewById(R.id.iv_cxchange);
                                final String textcx=carbrandlist.get(i).getData().get(j).getCarClass();//车系
                                tv_cx.setText(tv.getText().toString()+" "+textcx);

                                final LinearLayout chexinlin=new LinearLayout(CompetinggdAct.this);
                                chexinlin.setOrientation(LinearLayout.VERTICAL);
                                chexinlin.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));

                                cxlin.addView(texlin);//添加车系到车系容器
                                cxlin.addView(chexinlin);//添加车型的容器，到车系容器



                                texlin.setTag(j);
                                final int dex=j;
                                texlin.setOnClickListener(new View.OnClickListener() {//点击车系获得车型
                                    @Override
                                    public void onClick(View view) {
                                            if (texlin.getTag().toString().equals(dex + "")) {
                                                getChexin(CadillacUtils.getCurruser().getUserName(), carbrand, textcx, province, city, chexinlin, texlin);//通过品牌和车系获得车型
                                                iv_cx.setImageResource(R.mipmap.expand_hover);
                                            } else {
                                                texlin.setTag(dex);
                                                chexinlin.removeAllViews();
                                                iv_cx.setImageResource(R.mipmap.close_hover);
                                            }
                                        }

                                });

                            }
                            linearyoutview.setTag(i);
                            cxlin.setTag(i);
                            final int pos=i;
                            cxlin.setVisibility(View.GONE);
                            linearyoutview.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    if(cxlin.getTag().toString().equals(pos+"")){
                                        cxlin.setTag(-1);
                                        cxlin.setVisibility(View.VISIBLE);
                                        mHandler.post(mScrollToBottom);
                                        iv.setImageResource(R.mipmap.expand_hover);
                                    }else {
                                        cxlin.setTag(pos);
                                        cxlin.setVisibility(View.GONE);
                                        iv.setImageResource(R.mipmap.close_hover);
                                    }
                                }
                            });

//

                        }
                    }

                }


            }
        });
    }

    /**点击品牌和车系得到下面车型
     * @param userName
     * @param carBrand
     * @param carClass
     * @param province
     * @param city
     */
    private void getChexin(String userName, String carBrand, final String carClass, final String province, String city, final LinearLayout chexinlin, final View texlin) {
        UserManager.getUserManager().getProbrand(userName, carBrand, carClass, province, city, new ResultCallback<String>() {
            @Override
            public void onError(int status, String errorMsg) {
                MyToastUtils.showShortToast(CompetinggdAct.this,errorMsg);
            }

            @Override
            public void onResponse(String response) {

                Gson gson=new Gson();
                texlin.setTag("-1");
                try {
                    JSONObject object=new JSONObject(response);
                    JSONObject data=object.getJSONObject("obj");
                    JSONArray  chexin=data.getJSONArray("list");
                    chexinlist=gson.fromJson(chexin.toString(),new TypeToken<List<CompChexinBean>>(){}.getType());
                    if(chexinlist!=null&&chexinlist.size()>0){
                        chexinlin.removeAllViews();
                        for(int k=0;k<chexinlist.size();k++){
                            View chexinview=View.inflate(CompetinggdAct.this,R.layout.item_compchexin,null);
                            TextView tv_cxbtchexin= (TextView) chexinview.findViewById(R.id.tv_cxbtchexn);//车型
                            TextView tv_zdj= (TextView) chexinview.findViewById(R.id.tv_zdj);//指导价
                            TextView tv_cjj= (TextView) chexinview.findViewById(R.id.tv_cjj);//车型

                            tv_cxbtchexin.setText(chexinlist.get(k).getCarMode());
                            tv_zdj.setText(chexinlist.get(k).getGuidPrice());//指导价
                            tv_cjj.setText(chexinlist.get(k).getAvgPurchasePrice());//成交价
                            chexinlin.addView(chexinview);//车型里面添加车系
                            mHandler.post(mScrollToBottom);

                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @OnClick({R.id.rl_select_moudle,R.id.ll,R.id.iv_finsh})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.rl_select_moudle:
                if(!isshow) {
                    setProinfo();//设置省份
                }else {
                    isshow=false;
                    ll_select.setVisibility(View.GONE);
                }
                break;

            case R.id.ll://点击底部能收起
//                isshow=false;
//                ll_select.setVisibility(View.GONE);
                break;
            case R.id.iv_finsh:
                finish();
                break;
//            case R.id.iv_bianji:
//              openActivity(CompentBianjiAct.class);
//
//                break;
        }

    }

    private void setProinfo() {
        if(baocprolist!=null&&baocprolist.size()>0){
            isshow=true;
            proadpt=new ProAdpt(CompetinggdAct.this,baocprolist);
            ll_select.setVisibility(View.VISIBLE);
            lv_pro.setAdapter(proadpt);
            for(int i=0;i<baocprolist.size();i++){
                if(provice.equals(baocprolist.get(i))){
                    proadpt.setSeclection(i);
                }
            }

            lv_pro.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    proadpt.setSeclection(i);
                    provice=baocprolist.get(i);
                    getCitylist(baocprolist.get(i));//获取城市列表
                }
            });
        }
    }

    /**
     * 通过省份查询下面城市
     * @param province
     */
    private void getCitylist(final String province) {
        UserManager.getUserManager().getCityList(province, new ResultCallback<String>() {
            @Override
            public void onError(int status, String errorMsg) {
                MyToastUtils.showShortToast(CompetinggdAct.this,errorMsg);
            }

            @Override
            public void onResponse(String response) {
                Gson gson=new Gson();
                try {
                    JSONObject object=new JSONObject(response);
                    JSONObject data=object.getJSONObject("obj");
                    JSONArray citys=data.getJSONArray("cityList");
                    if(citys.length()>0){
                        citylist=gson.fromJson(citys.toString(),new TypeToken<List<String>>(){}.getType());
                        if(citylist!=null&&citylist.size()>0){
                            cityadpt=new CityAdapter(CompetinggdAct.this,citylist);
                            lv_city.setBackgroundColor(Color.parseColor("#ffffff"));
                            lv_city.setAdapter(cityadpt);

                            lv_city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    cityadpt.setSeclection(i);
                                    city=citylist.get(i);
                                    tv_city.setText(citylist.get(i));
                                    isshow=false;
                                    ll_select.setVisibility(View.GONE);
                                    getProbrand(CadillacUtils.getCurruser().getUserName(),"","",province,city);//获取车牌和省份


                                }
                            });
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    private Runnable mScrollToBottom = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            int off = ll_cx.getMeasuredHeight() - sc_view.getHeight();
            if (off > 0) {
                sc_view.scrollTo(0, off);
            }

        }
    };
}
