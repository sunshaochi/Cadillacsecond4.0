package cadillac.example.com.cadillac.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cadillac.example.com.cadillac.R;
import cadillac.example.com.cadillac.base.BaseActivity;
import cadillac.example.com.cadillac.bean.ChexinBean;
import cadillac.example.com.cadillac.bean.JpPpBean;
import cadillac.example.com.cadillac.bean.JpUloadBean;
import cadillac.example.com.cadillac.bean.JpbjChexnBean;
import cadillac.example.com.cadillac.bean.PurUloadBean;
import cadillac.example.com.cadillac.bean.ResultsBean;
import cadillac.example.com.cadillac.bean.ResultsNewBean;
import cadillac.example.com.cadillac.bean.RmkAndfinBean;
import cadillac.example.com.cadillac.http.Manage.UserManager;
import cadillac.example.com.cadillac.http.ResultCallback;
import cadillac.example.com.cadillac.utils.CadillacUtils;
import cadillac.example.com.cadillac.utils.GsonUtils;
import cadillac.example.com.cadillac.utils.MyLogUtils;
import cadillac.example.com.cadillac.utils.MyToastUtils;
import cadillac.example.com.cadillac.utils.SpUtils;
import cadillac.example.com.cadillac.view.CProgressDialog;
import cadillac.example.com.cadillac.view.MyAlertDialog;



/**竞品价格编辑界面
 * Created by bitch-1 on 2017/6/28.
 */

public class CompentBianjiAct extends BaseActivity {
    @ViewInject(R.id.tv_jpbname)
    private TextView tv_jpbname;
    @ViewInject(R.id.tv_jpbcode)
    private TextView tv_jpbcode;
    @ViewInject(R.id.tv_jpbtime)
    private TextView tv_jpbtime;
    @ViewInject(R.id.ll_pp)
    private LinearLayout ll_pp;
    @ViewInject(R.id.tv_baoc)
    private TextView tv_baoc;//保存
    @ViewInject(R.id.sc_view)
    private ScrollView sc_view;

    private String username;//登录名
    private List<JpPpBean>jppplist;//竞品品牌list;
    private List<JpPpBean>bcjppplist=new ArrayList<>();//竞品品牌list;
    private List<ChexinBean>chexinlist;//车型list;

    private List<PurUloadBean>list=new ArrayList<>();
    private List<RmkAndfinBean>rmlist=new ArrayList<>();

    private Map<Map<Integer,Integer>,List<ChexinBean>>map=new HashMap<>();

    private int ppdex;
    private int cxdex;

    private String dealerCode;
    private String quarterName;
    private JpUloadBean uloadBean;

    private Dialog dialog;


    private final Handler mHandler = new Handler();//用来时时更新scorllview的位置
    @Override
    public void setLayout() {
        setContentView(R.layout.act_compentbianji);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("竞品价格编辑界面");
        uloadBean=new JpUloadBean();
        getJinpinInfo(CadillacUtils.getCurruser().getUserName(),"","");//获取竞品数据

    }

    private void getJinpinInfo(String userName,String carBrand,String carClass) {
        UserManager.getUserManager().getJinpinInfo(userName, carBrand, carClass, new ResultCallback<String>() {
            @Override
            public void onError(int status, String errorMsg) {
                MyToastUtils.showShortToast(CompentBianjiAct.this,errorMsg);
            }

            @Override
            public void onResponse(String response) {
                Gson gson=new Gson();
                try {
                    JSONObject object=new JSONObject(response);
                    JSONObject data=object.getJSONObject("obj");
                    JSONObject dealerMap=data.getJSONObject("dealerMap");
                    String dealerName=dealerMap.getString("dealerName");//上海东昌
                    tv_jpbname.setText(dealerName);
                    dealerCode=dealerMap.getString("dealerCode");//CD1000\
                    quarterName=dealerMap.getString("quarterName");
                    if(!TextUtils.isEmpty(dealerCode)){
                        tv_jpbcode.setText(dealerCode);
                        tv_jpbcode.setVisibility(View.VISIBLE);
                    }else {
                        tv_jpbcode.setVisibility(View.GONE);
                    }
                    String endDate=dealerMap.getString("endDate");//时间
                    tv_jpbtime.setText(endDate);
                    JSONArray list=data.getJSONArray("list");
                    if(list.length()>0){
                        jppplist=gson.fromJson(list.toString(),new TypeToken<List<JpPpBean>>(){}.getType());//品牌list
                        if(jppplist!=null&&jppplist.size()>0){
                            ll_pp.removeAllViews();//品牌外成布局
                            bcjppplist=jppplist;//用来保存变化的数据

                            for (int i = 0; i < jppplist.size(); i++) {
                                final View ppview = View.inflate(CompentBianjiAct.this, R.layout.compgdview, null);//品牌item
                                TextView tv = (TextView) ppview.findViewById(R.id.tv_pp);
                                final ImageView iv = (ImageView) ppview.findViewById(R.id.iv_change);
                                final String carbrand = jppplist.get(i).getCarBrand().toString();
                                tv.setText(carbrand);

                                final LinearLayout cxlin = new LinearLayout(CompentBianjiAct.this);//品牌下面的车系布局
                                cxlin.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                                cxlin.setOrientation(LinearLayout.VERTICAL);

                                ll_pp.addView(ppview);//把品牌加入进去
                                ll_pp.addView(cxlin);//把车系的布局加进去


                                cxlin.setTag(i);//给每一个车系设置tag
                                for (int j = 0; j < jppplist.get(i).getData().size(); j++) {
                                    final View cxview = View.inflate(CompentBianjiAct.this, R.layout.item_cxview, null);//车系view如3x 5x
                                    TextView tv_cx = (TextView) cxview.findViewById(R.id.tv_jpbjcx);
                                    ImageView iv_cx = (ImageView) cxview.findViewById(R.id.iv_jpbjcxchange);
                                    RelativeLayout rl_bz = (RelativeLayout) cxview.findViewById(R.id.rl_bz);
                                    final LinearLayout ll_zc = (LinearLayout) cxview.findViewById(R.id.ll_zc);
                                    final TextView tv_zc = (TextView) cxview.findViewById(R.id.tv_zc);
                                    final String carclass = jppplist.get(i).getData().get(j).getCarClass();
                                    tv_cx.setText(tv.getText() + " " + carclass);
                                    final String remark = jppplist.get(i).getData().get(j).getRemark();//备注
                                    final String financial = jppplist.get(i).getData().get(j).getFinancial();//政策



                                    if (TextUtils.isEmpty(financial)) {
                                        tv_zc.setText("输入金融政策");
                                    } else {
                                        if (financial.length() > 6) {
                                            tv_zc.setText(financial.substring(0, 7) + "...");
                                        } else {
                                            tv_zc.setText(financial);
                                        }
                                    }

                                    final int hh = j;
                                    //点击备注
                                    rl_bz.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            ppdex = Integer.parseInt(cxlin.getTag()+"");//点击后绑定到cx的位置上后面要用到
                                            cxdex = hh;
                                            Intent intent = new Intent(CompentBianjiAct.this, EditorAct.class);
                                            intent.putExtra("remark", bcjppplist.get(ppdex).getData().get(hh).getRemark());
                                            intent.putExtra("type", "1");
                                            startActivityForResult(intent, 1);
                                        }
                                    });
                                    //金融政策

                                    ll_zc.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            ppdex = Integer.parseInt(cxlin.getTag()+"");
                                            cxdex = hh;
                                            Intent intent = new Intent(CompentBianjiAct.this, EditorAct.class);
                                            intent.putExtra("financial", bcjppplist.get(ppdex).getData().get(hh).getFinancial());
                                            intent.putExtra("type", "2");
                                            startActivityForResult(intent, 2);
                                            currentClickTv = tv_zc;
                                        }
                                    });
                                    final LinearLayout chexinlin = new LinearLayout(CompentBianjiAct.this);
                                    chexinlin.setOrientation(LinearLayout.VERTICAL);
                                    chexinlin.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));


                                    /**车系的下面数据**/
                                    //要把i,g,都传递进去确定要位置

                                    getChexin(CadillacUtils.getCurruser().getUserName(), carbrand, carclass, chexinlin, cxview,Integer.parseInt(cxlin.getTag()+""),hh,iv_cx);//通过品牌和车系获得车型

                                    cxlin.addView(cxview);
                                    cxlin.addView(chexinlin);//车系布局添加车型布局

                                }
                                /**品牌的点击事件**/
                                ppview.setTag(i);
                                final int pos = i;

                                cxlin.setVisibility(View.GONE);
                                ppview.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (ppview.getTag().toString().equals(pos + "")) {
                                            ppview.setTag("-1");
                                            cxlin.setVisibility(View.VISIBLE);
                                            mHandler.post(mScrollToBottom);
                                            iv.setImageResource(R.mipmap.expand_hover);
                                        } else {
                                            ppview.setTag(pos);
                                            cxlin.setVisibility(View.GONE);
                                            iv.setImageResource(R.mipmap.close_hover);
                                        }
                                    }
                                });
                            }

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private TextView currentClickTv;




    /**获取车型
     * @param userName
     * @param carBrand
     * @param carClass
     * @param chexinlin
     * @param cxview
     */
    private void getChexin(String userName, String carBrand, String carClass, final LinearLayout chexinlin, final View cxview, final int pos, final int j, final ImageView ivcx) {
        UserManager.getUserManager().getJinpinInfo(userName, carBrand, carClass, new ResultCallback<ResultsNewBean<JpbjChexnBean>>() {
            @Override
            public void onError(int status, String errorMsg) {
                MyToastUtils.showShortToast(CompentBianjiAct.this,errorMsg);

            }

            @Override
            public void onResponse(ResultsNewBean<JpbjChexnBean> response) {
                chexinlist=response.getObj().getList();
                chexinlin.setVisibility(View.VISIBLE);
                if(chexinlist!=null&&chexinlist.size()>0){

                    Map<Integer,Integer>mapkey=new HashMap<Integer, Integer>();
                    mapkey.put(pos,j);
                    map.put(mapkey,chexinlist);

                    chexinlin.removeAllViews();
                    for(int k=0;k<chexinlist.size();k++){
//                        chexinlist.get(k).setFinancial(bcjppplist.get(pos).getData().get(j).getFinancial());
                        View chexinview=View.inflate(CompentBianjiAct.this,R.layout.item_bjchexin,null);
                        TextView tv_cxbtchexin= (TextView) chexinview.findViewById(R.id.tv_cxbtchexn);//车型
                        TextView tv_zdj= (TextView) chexinview.findViewById(R.id.tv_zdj);//指导价
                        final EditText et_cjj= (EditText) chexinview.findViewById(R.id.et_cjj);//车型

                        tv_cxbtchexin.setText(chexinlist.get(k).getCarMode());
                        tv_zdj.setText(chexinlist.get(k).getGuidPrice()+"");//指导价
                        final String cjj=chexinlist.get(k).getPurchasePrice().toString();
                        et_cjj.setText(cjj);//成交价
                        final int ss=k;
                        et_cjj.addTextChangedListener(new TextWatcher() {

                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int i, int i1, int i2) {

                                    if (s.toString().contains(".")) {//设置可以输入两位小数
                                        if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                                            s = s.toString().subSequence(0,
                                                    s.toString().indexOf(".") + 3);
                                            et_cjj.setText(s);
                                            et_cjj.setSelection(s.length());
                                        }
                                    }


                                if (s.toString().trim().equals(".")) {
                                    s = "0" + s;
                                    et_cjj.setText(s);
                                    et_cjj.setSelection(2);

                                }

                                if (s.toString().startsWith("0")
                                        && s.toString().trim().length() > 1) {
                                    if (!s.toString().substring(1, 2).equals(".")) {
                                        et_cjj.setText(s.subSequence(0, 1));
                                        et_cjj.setSelection(1);

                                    }
                                }

                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                if(!s.toString().startsWith(".")){
                                    if(s.toString().trim().length() == 0){
                                        Map<Integer,Integer>clickkey=new HashMap<Integer, Integer>();
                                        clickkey.put(pos,j);
                                        map.get(clickkey).get(ss).setPurchasePrice("0");
                                    }

                                    if (!TextUtils.isEmpty(s.toString())) {
                                        Map<Integer,Integer>clickkey=new HashMap<Integer, Integer>();
                                        clickkey.put(pos,j);
                                        map.get(clickkey).get(ss).setPurchasePrice(s.toString());
                                    }
                                }else if(s.toString().startsWith(".")){
                                    new MyAlertDialog(CompentBianjiAct.this).builder().setTitle("输入数据格式错误").setMsg("本地单位为\"万元\",本次输入的数据\n包含错误格式。确实的格式:\n如\"35.58\"(万元)").setNegativeButton("OK", null).show();
                                    et_cjj.setText(cjj);
//                                    MyToastUtils.showShortToast(CompentBianjiAct.this,"第"+pos+"个品牌"+j+"个车型"+ss+"车系"+s.toString()+"个");

                                }

                            }
                        });
                        chexinlin.addView(chexinview);
                        chexinlin.setVisibility(View.GONE);
                        cxview.setTag(k);
                        final int posi=k;
                        cxview.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(cxview.getTag().toString().equals(posi+"")){
                                    chexinlin.setVisibility(View.VISIBLE);
                                    cxview.setTag("-1");
                                    ivcx.setImageResource(R.mipmap.expand_hover);
                                }else {
                                    chexinlin.setVisibility(View.GONE);
                                    cxview.setTag(posi);
                                    ivcx.setImageResource(R.mipmap.close_hover);
                                }
                            }
                        });
                        mHandler.post(mScrollToBottom);
                }


            }
        } });
    }

    @OnClick({R.id.tv_baoc})
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.tv_baoc:
//                int num=ijlist.size();
                dialog = CProgressDialog.createLoadingDialog(CompentBianjiAct.this,false);
                dialog.show();
               for(int i=0;i<bcjppplist.size();i++){
                   for (int j=0;j<bcjppplist.get(i).getData().size();j++){
                       Map<Integer,Integer>key=new HashMap<>();
                       key.put(i,j);
                       for (int k=0;k<map.get(key).size();k++){

//                           ijlist.get(i).get(j).get(k).setFinancial(bcjppplist.get(i).getData().get(j).getFinancial());
                           String carid=map.get(key).get(k).getCarId()+"";
                           String pu=map.get(key).get(k).getPurchasePrice()+"";
//                           String fin=ijlist.get(i).get(j).get(k).getFinancial()+"";
                           String fin=bcjppplist.get(i).getData().get(j).getFinancial();
                           String rmark=bcjppplist.get(i).getData().get(j).getRemark()+"";
                           list.add(new PurUloadBean(carid,dealerCode,pu,fin,rmark));
                       }


                   }

               }



                uloadBean.setList(list);
                uloadBean.setTextList(rmlist);
                baoChun(uloadBean);


                break;
        }
    }

    /**
     * 点击保存按钮
     * @param uloadBean
     */
    private void baoChun(JpUloadBean uloadBean) {
        UserManager.getUserManager().getbaoChun(uloadBean, new ResultCallback<String>() {
            @Override
            public void onError(int status, String errorMsg) {
                dialog.dismiss();
                MyToastUtils.showShortToast(CompentBianjiAct.this,errorMsg);
            }

            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                finish();

            }
        });
    }


    private Runnable mScrollToBottom = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            int off = ll_pp.getMeasuredHeight() - sc_view.getHeight();
            if (off > 0) {
                sc_view.scrollTo(0, off);
            }

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK) {
            String result = data.getStringExtra("result");
            if(requestCode==1){//备注
                MyToastUtils.showShortToast(CompentBianjiAct.this, "您修改了第" + ppdex + "个品牌" + cxdex + "备注");

                bcjppplist.get(ppdex).getData().get(cxdex).setRemark(result);


            }else {
                MyToastUtils.showShortToast(CompentBianjiAct.this, "您修改了第" + ppdex + "个品牌" + cxdex + "车系");
                bcjppplist.get(ppdex).getData().get(cxdex).setFinancial(result);
                MyLogUtils.info("改变后的+++" + GsonUtils.bean2Json(jppplist));

                if(currentClickTv!=null){
                    currentClickTv.setText(result);
                }
            }


        }
    }
}

