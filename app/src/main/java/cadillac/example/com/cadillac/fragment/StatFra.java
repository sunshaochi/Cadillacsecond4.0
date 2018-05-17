package cadillac.example.com.cadillac.fragment;


import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cadillac.example.com.cadillac.R;
import cadillac.example.com.cadillac.activity.DialogAct;
import cadillac.example.com.cadillac.base.BaseFrag;
import cadillac.example.com.cadillac.bean.MessageEvent;
import cadillac.example.com.cadillac.bean.Modle;
import cadillac.example.com.cadillac.bean.ResultsNewBean;
import cadillac.example.com.cadillac.http.Manage.UserManager;
import cadillac.example.com.cadillac.http.ResultCallback;
import cadillac.example.com.cadillac.utils.CadillacUtils;
import cadillac.example.com.cadillac.utils.DateAndTimeUtils;
import cadillac.example.com.cadillac.utils.MyLogUtils;
import cadillac.example.com.cadillac.utils.MyToastUtils;
import cadillac.example.com.cadillac.utils.SpUtils;
import cadillac.example.com.cadillac.view.Picktime.pickerview.TimePickerView;
import cadillac.example.com.cadillac.view.Picktime.pickerview.TimePickerViewDialog;

import static android.content.Context.WINDOW_SERVICE;

/**
 * 报表fragment
 * Created by bitch-1 on 2017/3/16.
 */
public class StatFra extends BaseFrag{

    List<Modle>list;

    @ViewInject(R.id.tv_statt)
    private TextView tv_statt;//十一月 2017
    @ViewInject(R.id.tv_statd)
    private TextView tv_statd;
    @ViewInject(R.id.tv_shuaix)
    private TextView tv_shuaix;
    @ViewInject(R.id.iv_statt)
    private ImageView iv_statt;
    @ViewInject(R.id.iv_statd)
    private ImageView iv_statd;
    @ViewInject(R.id.iv_shuaix)
    private ImageView iv_shuaix;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.ll_stitle)
    private LinearLayout ll_stitle;

    @ViewInject(R.id.view_top)
    private View view_top;//总布局的占位，popuwindow显示在其之下


    private StatDayFra statDayFra;//天
    private StatMonthFra statmonthfra;//月
    private StatYearFra statyearfra;//年



    private String tb,kl;//同比，客流
    private FragmentManager fragmentManager;

    private PopupWindow popupWindow;
    private PopupWindow popupWindowselect;
    private RelativeLayout rl_ri,rl_yue,rl_nian;
    private TextView tv_ri,tv_yue,tv_nian;
    private ImageView iv_ri,iv_yue,iv_nian;
    private String poputype;
    private int clickTemp=-1;
    private boolean flag=false;
    private String select;//帅选后的车型
    private String dex;//判断显示的在哪个fragment



    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.frg_stat,null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        EventBus.getDefault().register(this);//注册事件

        fragmentManager=getActivity().getSupportFragmentManager();
        tv_title.setText(CadillacUtils.getCurruser().getDealerName());
        tv_statt.setText(CadillacUtils.forMatetime(SpUtils.getOneTime(getActivity())));
        slectfra(0);

    }

    @OnClick({R.id.ll_statd,R.id.ll_statt,R.id.ll_shuaix,R.id.rl_sx})
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.ll_statd://日月年
                iv_statd.setImageDrawable(getResources().getDrawable(R.mipmap.up_jt));
                showPopupWindow(tv_statd.getText().toString());
                break;
            case R.id.ll_statt://选择时间
                iv_statt.setImageDrawable(getResources().getDrawable(R.mipmap.up_jt));
                showTiemDialog();//时间选择器
                break;

            case R.id.ll_shuaix://帅选车型
                iv_shuaix.setImageDrawable(getResources().getDrawable(R.mipmap.up_jt));
                getCarModle();
                break;


            case R.id.rl_sx://刷新
                if(dex.equals("0")){
                statDayFra.upDate();
                }else if(dex.equals("1")){
                   statmonthfra.upDateinfo();
                }else if(dex.equals("2")){
                    statyearfra.upDateinfo();
                }
                break;
        }

    }

    /**
     * 获取车型
     */
    private void getCarModle() {
        if(list==null||list.size()==0){//空的要请求车型
            UserManager.getUserManager().getCarModle(new ResultCallback<ResultsNewBean<List<Modle>>>() {
                @Override
                public void onError(int status, String errorMsg) {
                  MyToastUtils.showShortToast(getActivity(),errorMsg);
                }

                @Override
                public void onResponse(ResultsNewBean<List<Modle>> response) {
                 list=response.getObj();
                 if(list!=null&&list.size()>0){
                     list.add(0,new Modle("","","全部","","","","","","",""));
                     showSelectPop(tv_shuaix.getText().toString());
                 }else {
                     MyToastUtils.showShortToast(getActivity(),"暂无数据");
                 }
                }
            });
        }else {
            showSelectPop(tv_shuaix.getText().toString());
        }
    }


    /**日月年的选择器
     * @param
     * @param role
     */
    private void showPopupWindow(String role) {
        LinearLayout layout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(
                R.layout.popuwindowst_dialog, null);

        // 实例化popupWindow
        popupWindow = new PopupWindow(layout, AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT);
        //控制键盘是否可以获得焦点(如果能获得比如说点击一下变图标，在点击消息，在点击一下才会再变图标)
        popupWindow.setFocusable(false);
        //设置popupWindow弹出窗体的背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable(null, ""));
        WindowManager manager = (WindowManager)getActivity(). getSystemService(WINDOW_SERVICE);
        //获取xoff
        int xpos = manager.getDefaultDisplay().getWidth() / 2 - popupWindow.getWidth() / 2;
        //xoff,yoff基于anchor的左下角进行偏移。
        popupWindow.showAsDropDown(view_top, 0, 0);
        popupWindow.setOutsideTouchable(false);
         rl_ri= (RelativeLayout) layout.findViewById(R.id.rl_ri);
         rl_yue= (RelativeLayout) layout.findViewById(R.id.rl_yue);
         rl_nian= (RelativeLayout) layout.findViewById(R.id.rl_nian);
         tv_ri= (TextView) layout.findViewById(R.id.tv_ri);
         tv_yue= (TextView) layout.findViewById(R.id.tv_yue);
         tv_nian= (TextView) layout.findViewById(R.id.tv_nian);
         iv_ri= (ImageView) layout.findViewById(R.id.iv_ri);
         iv_yue= (ImageView) layout.findViewById(R.id.iv_yue);
         iv_nian= (ImageView) layout.findViewById(R.id.iv_nian);
         LinearLayout ll_notop= (LinearLayout) layout.findViewById(R.id.ll_notop);//占位控件
         LinearLayout ll_nobot= (LinearLayout) layout.findViewById(R.id.ll_nobot);//占位控件
         TextView tv_button= (TextView) layout.findViewById(R.id.tv_btn);
         tv_button.setVisibility(View.GONE);
        if(role.equals("日")){
            setColor(0);
        }else if(role.equals("月")){
            setColor(1);
        }else if(role.equals("年")){
            setColor(2);
        }

        rl_ri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              setColor(0);
              showText(poputype);
            }
        });

        rl_yue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setColor(1);
                showText(poputype);
            }
        });

        rl_nian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setColor(2);
                showText(poputype);
            }
        });


        ll_notop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                iv_statd.setImageDrawable(getResources().getDrawable(R.mipmap.down_jt));
            }
        });

        ll_nobot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                iv_statd.setImageDrawable(getResources().getDrawable(R.mipmap.down_jt));
            }
        });

    }

    private void showText(String poputype) {
        popupWindow.dismiss();
        iv_statd.setImageDrawable(getResources().getDrawable(R.mipmap.down_jt));
        switch (poputype){
            case "0":
                tv_statd.setText("日");
                tv_statt.setText(CadillacUtils.forMatetime(SpUtils.getOneTime(getActivity())));
                slectfra(0);
                break;

            case "1":
                tv_statd.setText("月");
                slectfra(1);
                tv_statt.setText(SpUtils.getTwoTime(getActivity()));
                break;

            case "2":
                tv_statd.setText("年");
                slectfra(2);
                tv_statt.setText(SpUtils.getThreeTime(getActivity()));
                break;
        }
    }


    /**
     * 当删选日月年的时候字体变色
     * @param i
     */
    private void setColor(int i) {
        tv_ri.setTextColor(Color.parseColor("#000000"));
        tv_yue.setTextColor(Color.parseColor("#000000"));
        tv_nian.setTextColor(Color.parseColor("#000000"));
//        iv_ri.setVisibility(View.GONE);
//        iv_yue.setVisibility(View.GONE);
//        iv_nian.setVisibility(View.GONE);
        switch (i){
            case 0:
                tv_ri.setTextColor(Color.parseColor("#8a152b"));
//                iv_ri.setVisibility(View.VISIBLE);
                poputype="0";
                break;

            case 1:
                tv_yue.setTextColor(Color.parseColor("#8a152b"));
//                iv_yue.setVisibility(View.VISIBLE);
                poputype="1";
                break;

            case 2:
                tv_nian.setTextColor(Color.parseColor("#8a152b"));
//                iv_nian.setVisibility(View.VISIBLE);
                poputype="2";
                break;
        }

    }


    /**
     * 车型筛选
     */
    private void showSelectPop(String text) {
        LinearLayout layout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(
                R.layout.popuwindowsshuaix_dialog, null);

        // 实例化popupWindow
        popupWindowselect = new PopupWindow(layout, AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT);
        //控制键盘是否可以获得焦点(如果能获得比如说点击一下变图标，在点击消息，在点击一下才会再变图标)
        popupWindowselect.setFocusable(false);
        //设置popupWindow弹出窗体的背景
        popupWindowselect.setBackgroundDrawable(new BitmapDrawable(null, ""));
        WindowManager manager = (WindowManager)getActivity(). getSystemService(WINDOW_SERVICE);
        //获取xoff
        int xpos = manager.getDefaultDisplay().getWidth() / 2 - popupWindowselect.getWidth() / 2;
        //xoff,yoff基于anchor的左下角进行偏移。
        popupWindowselect.showAsDropDown(view_top, 0, 0);
        popupWindowselect.setOutsideTouchable(true);

        ListView lv_cx= (ListView) layout.findViewById(R.id.lv_cx);
        TextView tv_btn= (TextView) layout.findViewById(R.id.tv_btn);//点击确认的
        LinearLayout ll_tb= (LinearLayout) layout.findViewById(R.id.ll_tb);//同比环比的整个按钮
        RadioGroup rg_statmain= (RadioGroup) layout.findViewById(R.id.rg_statmain);//同比环比radiograop
        final RadioButton rb_tb= (RadioButton) layout.findViewById(R.id.rb_tb);//同比
        final RadioButton rb_hb= (RadioButton) layout.findViewById(R.id.rb_hb);//环比
         RadioGroup rd_select= (RadioGroup) layout.findViewById(R.id.rd_select);
        final RadioButton rb_kl= (RadioButton) layout.findViewById(R.id.rb_kl);//客流
        final RadioButton rb_dd= (RadioButton) layout.findViewById(R.id.rb_dd);//订单
//        LinearLayout ll_btnck= (LinearLayout) layout.findViewById(R.id.ll_btnck);//同比的按钮
//        final ImageView iv_tb= (ImageView) layout.findViewById(R.id.iv_tb);
//        final TextView tv_tb= (TextView) layout.findViewById(R.id.tv_tb);
        LinearLayout ll_selecttop= (LinearLayout) layout.findViewById(R.id.ll_selecttop);
        LinearLayout ll_selectbot= (LinearLayout) layout.findViewById(R.id.ll_selectbot);


        if(dex.equals("1")){//表示在月的时候选择的筛选显示
            ll_tb.setVisibility(View.VISIBLE);//
            if(!SpUtils.getTbOrHb(getActivity()).equals("-1")) {
                if (SpUtils.getTbOrHb(getActivity()).equals("0")) {//同比
                    rb_tb.setChecked(true);
                    tb = "0";
                } else if (SpUtils.getTbOrHb(getActivity()).equals("1")) {//环比
                    rb_hb.setChecked(true);
                    tb = "1";
                }
                if (SpUtils.getKlOrDd(getActivity()).equals("0")) {//客流
                    rb_kl.setChecked(true);
                    kl = "0";
                } else {//订单
                    rb_dd.setChecked(true);
                    kl = "1";
                }
            }else {
                tb=SpUtils.getTbOrHb(getActivity());
                kl=SpUtils.getKlOrDd(getActivity());
            }

        }else {//表示在其他的时候不显示
            ll_tb.setVisibility(View.GONE);
        }

        final CxAdapter adapter=new CxAdapter();
        lv_cx.setAdapter(adapter);
        select=text;
        for(int i=0;i<list.size();i++){
            if(list.get(i).getName().equals(text)){
                adapter.setSeclection(i);
            }
        }
//        adapter.setSeclection(0);

        /*监控同比环比的点击*/
        rg_statmain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (SpUtils.getKlOrDd(getActivity()).equals("0")) {//客流
                    rb_kl.setChecked(true);
                } else {//订单
                    rb_dd.setChecked(true);
                }
            }
        });

        tv_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dex.equals("1")){
                    if(rb_tb.isChecked()){
                        SpUtils.setTbOrHb(getActivity(),"0");
                    }
                    if(rb_hb.isChecked()){
                        SpUtils.setTbOrHb(getActivity(),"1");
                    }

                    if(rb_kl.isChecked()){//客流
                        SpUtils.setKlOrDd(getActivity(),"0");
                    }
                    if(rb_dd.isChecked()){//订单
                        SpUtils.setKlOrDd(getActivity(),"1");
                    }

                    if(!tv_shuaix.getText().equals(SpUtils.getCarName(getActivity()))||!tb.equals(SpUtils.getTbOrHb(getActivity()))||!kl.equals(SpUtils.getKlOrDd(getActivity()))){
                        tb=SpUtils.getTbOrHb(getActivity());
                        kl=SpUtils.getKlOrDd(getActivity());
                        statmonthfra.upDateinfo();//通知月的更新界面
                    }
                }else {
                    if(dex.equals("0")&&!tv_shuaix.getText().equals(SpUtils.getCarName(getActivity()))){
                        statDayFra.upDate();
                    }
                    if(dex.equals("2")&&!tv_shuaix.getText().equals(SpUtils.getCarName(getActivity()))){
                        statyearfra.upDateinfo();
                    }
                }

                tv_shuaix.setText(select);
                iv_shuaix.setImageDrawable(getResources().getDrawable(R.mipmap.down_jt));
                popupWindowselect.dismiss();


            }
        });



            lv_cx.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    adapter.setSeclection(i);
                    adapter.notifyDataSetChanged();
                    select =list.get(i).getName();
                    SpUtils.setCarModleid(getActivity(),list.get(i).getId());//保存所选的车型的id
                    SpUtils.setCarName(getActivity(),list.get(i).getName());
                }
            });


            ll_selecttop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iv_shuaix.setImageDrawable(getResources().getDrawable(R.mipmap.down_jt));
                    popupWindowselect.dismiss();
                }
            });

        ll_selectbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_shuaix.setImageDrawable(getResources().getDrawable(R.mipmap.down_jt));
                popupWindowselect.dismiss();
            }
        });

        rg_statmain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
               switch (radioGroup.getCheckedRadioButtonId()){
                   case R.id.rb_tb:
                       SpUtils.setTbOrHb(getActivity(),"0");//同比
                       break;

                   case R.id.rb_hb:
                       SpUtils.setTbOrHb(getActivity(),"1");//环比
                       break;
               }
            }
        });

        rd_select.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (radioGroup.getCheckedRadioButtonId()){
                    case R.id.rb_kl:
                        SpUtils.setKlOrDd(getActivity(),"0");
                        break;
                    case R.id.rb_dd:
                        SpUtils.setKlOrDd(getActivity(),"1");
                        break;
                }
            }
        });


    }

    /**
     * 时间弹出框选择时间
     */
    private void showTiemDialog() {
        TimePickerViewDialog timedialog = new TimePickerViewDialog(getActivity(), TimePickerView.Type.YEAR_MONTH);
        timedialog.setCyclic(false);//设置不循环
        timedialog.setButtonLeftText("取消");
        timedialog.setButtonRightText("确定");
        timedialog.setButtonLeftTextColor("#999999");
        timedialog.setButtonRightTextColor("#ffffff");
        timedialog.setTitleColor("#8a152b");
        timedialog.setTitleBackgroundColor("#8a152b");
        timedialog.setCanceledOnTouchOutside(false);
        if(dex.equals("0")){//看日的数据
            timedialog.setMothGone(false);
        }else {//看月和年
            timedialog.setMothGone(true);
        }
        timedialog.getDialog().show();
        timedialog.setOnTimeSelectListener(new TimePickerViewDialog.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date sdate) {
                if(dex.equals("0")) {
                    String selecttime = DateAndTimeUtils.getTimeForDate("yyyy-MM", sdate);
                    tv_statt.setText(CadillacUtils.forMatetime(selecttime));
                    statDayFra.moveCaid(selecttime);//通知日的开始时间变了
                    SpUtils.setOneTime(getActivity(), selecttime);

                }else {
                    String selecttime = DateAndTimeUtils.getTimeForDate("yyyy", sdate);
                    tv_statt.setText(selecttime);
                    if(dex.equals("1")){
                        SpUtils.setTwoTime(getActivity(),selecttime);
                        statmonthfra.upDateinfo();
                    }else {
                        SpUtils.setThreeTime(getActivity(),selecttime);
                        statyearfra.upDateinfo();
                    }
                }
                iv_statt.setImageDrawable(getResources().getDrawable(R.mipmap.down_jt));

            }

            @Override
            public void onCancel() {
               iv_statt.setImageDrawable(getResources().getDrawable(R.mipmap.down_jt));
            }
        });
    }





    /**
     * 设置显示frament
     * @param i
     */
    private void slectfra(int i) {
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        hideFragment(transaction);
        switch (i){
            case 0://天
                dex="0";
                if(statDayFra==null){
                    statDayFra=new StatDayFra();
                    transaction.add(R.id.fl_stat,statDayFra);
                }else {
                    transaction.show(statDayFra);
                }
                break;
            case 1://月
                dex="1";
                if(statmonthfra==null){
                    statmonthfra=new StatMonthFra();
                    transaction.add(R.id.fl_stat,statmonthfra);
                }else {
                    transaction.show(statmonthfra);
                }

                break;
            case 2://年
                dex="2";
                if(statyearfra==null){
                    statyearfra=new StatYearFra();
                    transaction.add(R.id.fl_stat,statyearfra);
                }else {
                    transaction.show(statyearfra);
                }

                break;
        }
        transaction.commit();
    }

    /**隐藏
     * @param transaction
     */
    private void hideFragment(FragmentTransaction transaction) {
        if(statyearfra!=null){
            transaction.hide(statyearfra);
        }
        if(statmonthfra!=null){
            transaction.hide(statmonthfra);
        }
        if(statDayFra!=null){
            transaction.hide(statDayFra);
        }
    }




    /**
     * 车型适配器
     */
    private class CxAdapter extends BaseAdapter {

        public void setSeclection(int position) {
            clickTemp = position;
        }
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

                view=View.inflate(getActivity(),R.layout.item_lvcx,null);
                TextView tv_name= (TextView) view.findViewById(R.id.tv_name);
                ImageView iv_duigou= (ImageView) view.findViewById(R.id.iv_duig);
            if(clickTemp==i){
                tv_name.setTextColor(Color.parseColor("#8a152b"));
                iv_duigou.setVisibility(View.VISIBLE);

            }else {
                tv_name.setTextColor(Color.parseColor("#000000"));
                iv_duigou.setVisibility(View.GONE);
            }
            tv_name.setText(list.get(i).getName());

            return view;


        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(MessageEvent messageEvent){
        String type=messageEvent.getType();
        if(type.equals("0")){
            tv_statt.setText(CadillacUtils.forMatetime(messageEvent.getMessage()));
        }else {
            tv_statt.setText(messageEvent.getMessage());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
