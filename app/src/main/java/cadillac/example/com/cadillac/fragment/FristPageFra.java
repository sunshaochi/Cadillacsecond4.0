package cadillac.example.com.cadillac.fragment;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cadillac.example.com.cadillac.R;
import cadillac.example.com.cadillac.adapter.MynoAdapter;
import cadillac.example.com.cadillac.base.BaseFrag;
import cadillac.example.com.cadillac.bean.SalseDateBean;
import cadillac.example.com.cadillac.bean.UserModle;
import cadillac.example.com.cadillac.http.Manage.UserManager;
import cadillac.example.com.cadillac.http.ResultCallback;
import cadillac.example.com.cadillac.utils.MyToastUtils;
import cadillac.example.com.cadillac.utils.TimeUtil;
import cadillac.example.com.cadillac.view.CProgressDialog;
import cadillac.example.com.cadillac.view.MyAlertDialog;
import cadillac.example.com.cadillac.view.MySelfSheetDialog;

/**
 * Created by bitch-1 on 2017/3/24.
 */
public class FristPageFra extends BaseFrag{
    @ViewInject(R.id.main_expandablelistview)
    private ExpandableListView main_expandablelistview;
    List<String> parent;//父控件数据
    List<SalseDateBean> mondle;//用来装车型放到总计里面去
    List<Map<String,List<SalseDateBean>>> result;
    private String time;//传递过来的时间2017-1，或是2017-第一季度
    private String date;//要上传的参数 2017-1-30
    private String tthis;//要上传的是否是当前时间
    private String type;//类型
    private String enter;
    private Gson gson;
    private List<SalseDateBean>list;
    private Dialog dialog;
    private String jxsnum;//经销商个数
    private String jpnum;//精品店
    private List<String>jc;//车型简称
    private String modlejc;//车型简称
    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fra_fristpage,null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        dialog= CProgressDialog.createLoadingDialog(getContext(),false);
        dialog.show();
        gson=new Gson();
        Bundle bundle=getArguments();
        time=bundle.getString("time");//2017-1
        enter=bundle.getString("enter");
        if(enter.equals("日")){//日
         date=time;
            type="日";
            Dateinfo(date,UserModle.getUserBean().getJXSName(),UserModle.getUserBean().getRole(),"全部",1);//获取日的详情
        }else if(enter.equals("月")) {//月
            if(time.contains("季度")) {//季度
                type="季度";
                if(time.contains("第一季度")){//季度要传递的值
                    if(TimeUtil.comparestringTime(time.substring(0,4)+"-3-31")<0){//比当前时间小
                        date=time.substring(0,4)+"-3-31";//例如2017-3-30
                        tthis="否";
                    }else {//大于当前时间
                        date=TimeUtil.getCurrentTime();
                        tthis="是";

                    }

                }else if(time.contains("第二季度")){
                    if(TimeUtil.comparestringTime(time.substring(0,4)+"-6-30")<0){//比当前时间小
                        date=time.substring(0,4)+"-6-30";//例如2017-3-30
                        tthis="否";//不是当前季度
                    }else {//大于当前时间
                        date=TimeUtil.getCurrentTime();
                        tthis="是";//是当前季度

                    }

                }else if(time.contains("第三季度")){
                    if(TimeUtil.comparestringTime(time.substring(0,4)+"-9-30")<0){//比当前时间小
                        date=time.substring(0,4)+"-9-30";//例如2017-3-30
                        tthis="否";
                    }else {//大于当前时间
                        date=TimeUtil.getCurrentTime();
                        tthis="是";

                    }
                }else if(time.contains("第四季度")){
                    if(TimeUtil.comparestringTime(time.substring(0,4)+"-12-31")<0){//比当前时间小
                        date=time.substring(0,4)+"-12-31";//例如2017-3-30
                        tthis="否";
                    }else {//大于当前时间
                        date=TimeUtil.getCurrentTime();
                        tthis="是";

                    }

                }
                Quarterinfo(date, UserModle.getUserBean().getJXSName(),UserModle.getUserBean().getRole(),"全部",tthis,1);//获取季度数据
            }else {//月
                type="月";
                if (TimeUtil.getCurrentTime().contains(time)) {//当前月取当前时间
                    date = TimeUtil.getCurrentTime();
                    tthis="是";

                } else {//不是当前月取每个月的最后一天
                    date = TimeUtil.getLastday(time.split("-")[0], time.split("-")[1]);
                    tthis="否";
                }
                Monthinfo(date, UserModle.getUserBean().getJXSName(),UserModle.getUserBean().getRole(),"全部",tthis,1);//

            }
        }else if(enter.equals("年")){//年
            type="年";
         if(time.equals(TimeUtil.getCurrentTime().split("-")[0])){//如果是当年
             date=TimeUtil.getCurrentTime();
             tthis="是";
         }else {//不是当年取每年的最后一天
             date=time+"-12-31";
             tthis="否";
         }
            Yearinfo(date, UserModle.getUserBean().getJXSName(),UserModle.getUserBean().getRole(),"全部",tthis,1);
        }

        jc=new ArrayList<>();

    }

    /**
     * 年
     * @param date
     * @param store
     * @param role
     * @param which
     * @param tthis
     */
    private void Yearinfo(String date, String store, String role, String which, String tthis, final int type) {
       UserManager.getUserManager().yearinfo(date, store, role, which, tthis, new ResultCallback<String>() {
           @Override
           public void onError(int status, String errorMsg) {
               dialog.dismiss();
           }

           @Override
           public void onResponse(String response) {
               dialog.dismiss();
               list=gson.fromJson(response,new TypeToken<List<SalseDateBean>>(){}.getType());
               if(list.size()==0||list==null){
                   if(type==1){
                       new MyAlertDialog(getActivity()).builder().setMsg("暂无数据").setNegativeButton("OK", null).show();
                   }else {

                   }
                   main_expandablelistview.setAdapter(new MynoAdapter(getActivity(),UserModle.getUserBean().getModels(),1));
               }else {
                   setDate();
                   main_expandablelistview.setAdapter(new MyAdapter());
               }

           }
       });
    }

    /**日
     * @param date 时间
     * @param store 经销商name
     * @param role  职务
     * @param which (全部，是，否)
     *
     */
    private void Dateinfo(String date, String store, String role, String which, final int type) {
        UserManager.getUserManager().dayinfo(date, store, role, which, new ResultCallback<String>() {
            @Override
            public void onError(int status, String errorMsg) {
              dialog.dismiss();
            }

            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                list=gson.fromJson(response,new TypeToken<List<SalseDateBean>>(){}.getType());
                if(list.size()==0){
                    if(type==1){//需要展示dialog
                        new MyAlertDialog(getActivity()).builder().setMsg("暂无数据").setNegativeButton("OK", null).show();
                    }else {
                        //不需要dialog
                    }
                    main_expandablelistview.setAdapter(new MynoAdapter(getActivity(),UserModle.getUserBean().getModels(),1));
                }else {
                    setDate();
                    main_expandablelistview.setAdapter(new MyAdapter());
                }

            }
        });
    }

    /**获取月
     * @param date
     * @param store
     * @param role
     * @param which
     * @param tthis
     */
    private void Monthinfo(String date, String store, String role, String which, String tthis, final int type) {
        UserManager.getUserManager().monthinfo(date, store, role, which, tthis, new ResultCallback<String>() {
            @Override
            public void onError(int status, String errorMsg) {
              dialog.dismiss();
            }

            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                list=gson.fromJson(response,new TypeToken<List<SalseDateBean>>(){}.getType());
                if(list.size()==0||list==null){
                    if(type==1){
                        new MyAlertDialog(getActivity()).builder().setMsg("暂无数据").setNegativeButton("OK", null).show();

                    }else {

                    }
                    main_expandablelistview.setAdapter(new MynoAdapter(getActivity(),UserModle.getUserBean().getModels(),1));
                }else {
                    setDate();
                    main_expandablelistview.setAdapter(new MyAdapter());
                }
            }
        });

    }


    /**季度
     * @param date
     * @param store
     * @param role
     * @param which
     * @param tthis
     */
    private void Quarterinfo(String date, String store, String role, String which, String tthis, final int type) {
        UserManager.getUserManager().quarterinfo(date, store, role, which, tthis, new ResultCallback<String>() {
            @Override
            public void onError(int status, String errorMsg) {
            dialog.dismiss();
            }

            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                list=gson.fromJson(response,new TypeToken<List<SalseDateBean>>(){}.getType());
                if(list.size()==0||list==null){
                    if(type==1){
                        new MyAlertDialog(getActivity()).builder().setMsg("暂无数据").setNegativeButton("OK", null).show();

                    }else {

                    }
                    main_expandablelistview.setAdapter(new MynoAdapter(getActivity(),UserModle.getUserBean().getModels(),1));

                } else {
                    setDate();
                    main_expandablelistview.setAdapter(new MyAdapter());
                }
            }
        });
    }

    /**
     * 初始化数据
     */
    private List<String>modle;//车型集合
    Map<String,List<SalseDateBean>>map=new HashMap<>();
    private void setDate() {

        result=new ArrayList<>();
        parent = new ArrayList<String>();//通过这个字段
        for (int i=0;i<list.size();i++){
            parent.add(list.get(i).getArea());
        }

        for (int i=0;i<parent.size();i++){
            for (int j = parent.size() - 1; j > i; j--) {
                if (parent.get(j).equals(parent.get(i))) {
                    parent.remove(j);//去重
                }
            }
        }


        for (int i=0;i<parent.size();i++){
            List<SalseDateBean> tempList= new ArrayList<SalseDateBean>();
            String area=parent.get(i);
            for(SalseDateBean salseDateBean:list){
                if(area.equals(salseDateBean.getArea())){
                   tempList.add(salseDateBean);//与父容器key对应的子集合
                    map.put(area,tempList);
                }
            }
            sum(area,tempList);
            result.add(map);
        }



        //总计方面的数据
        modle=new ArrayList<>();
        for (int i=0;i<list.size();i++){
            modle.add(list.get(i).getModels());
        }

        for (int i=0;i<modle.size();i++){
            for (int j = modle.size() - 1; j > i; j--) {
                if (modle.get(j).equals(modle.get(i))) {
                    modle.remove(j);//去重
                }
            }
        }

        //计算每个车型的总数据
        resultlist.clear();
        for (int i=0;i<modle.size();i++){
            ArrayList<SalseDateBean> tempList= new ArrayList<SalseDateBean>();
            String chexin=modle.get(i);
            for(SalseDateBean salseDateBean:list){
                if(chexin.equals(salseDateBean.getModels())){
                    tempList.add(salseDateBean);//与父容器key对应的子集合
                }
            }
            totosum(tempList);
        }

        ArrayList<SalseDateBean> totoList= new ArrayList<SalseDateBean>();//给总计赛对象
        for (int i=0;i<modle.size();i++){
            totoList.add(new SalseDateBean(modle.get(i),resultlist.get(i).get(0),resultlist.get(i).get(1),resultlist.get(i).get(2),resultlist.get(i).get(4),resultlist.get(i).get(5),resultlist.get(i).get(6),resultlist.get(i).get(3),"0.0%","0.0%",modle.get(i)));
        }
        map.put("总计",totoList);
        sum("总计",totoList);
        result.add(map);
        parent.add(0,"总计");

    }

    /** 往总计**/
    private List<List<String>>resultlist=new ArrayList<>();
    private void totosum(ArrayList<SalseDateBean> tempList) {
        int callsum=0;
        int roomsum=0;
        int ordersum=0;
        int tuidingsum=0;
        int jiaochesum=0;
        int lejsum=0;
        int lcsum=0;
        List<String> list= new ArrayList<String>();
        for(int i=0;i<tempList.size();i++){
            callsum=callsum+Integer.parseInt(tempList.get(i).getCall());
            roomsum=roomsum+Integer.parseInt(tempList.get(i).getShowRoom());
            ordersum=ordersum+Integer.parseInt(tempList.get(i).getOrder());
            tuidingsum=tuidingsum+Integer.parseInt(tempList.get(i).getUnsubscribe());//退订
            jiaochesum=jiaochesum+Integer.parseInt(tempList.get(i).getRetail());//交车
            lejsum=lejsum+Integer.parseInt(tempList.get(i).getTotalOrder());//累计
            lcsum=lcsum+Integer.parseInt(tempList.get(i).getRetainOrder());//留存
        }
        list.add(callsum+"");
        list.add(roomsum+"");
        list.add(ordersum+"");
        list.add(tuidingsum+"");
        list.add(jiaochesum+"");
        list.add(lejsum+"");
        list.add(lcsum+"");
        resultlist.add(list);
    }

    Map<String,List<String>>totlemap=new HashMap<>();
    private void sum(String a,List<SalseDateBean> tempList) {//设置父容器上面的数据（根据其子容器取值）
        int callsum=0;
        int roomsum=0;
        int ordersum=0;
        int tuidingsum=0;
        int jiaochesum=0;
        int lejsum=0;
        int lcsum=0;
        List<String> List= new ArrayList<String>();
        for(int i=0;i<tempList.size();i++){
            callsum=callsum+Integer.parseInt(tempList.get(i).getCall());
            roomsum=roomsum+Integer.parseInt(tempList.get(i).getShowRoom());
            ordersum=ordersum+Integer.parseInt(tempList.get(i).getOrder());
            tuidingsum=tuidingsum+Integer.parseInt(tempList.get(i).getUnsubscribe());
            jiaochesum=jiaochesum+Integer.parseInt(tempList.get(i).getRetail());
            lejsum=lejsum+Integer.parseInt(tempList.get(i).getTotalOrder());
            lcsum=lcsum+Integer.parseInt(tempList.get(i).getRetainOrder());
        }
        List.add(callsum+"");
        List.add(roomsum+"");
        List.add(ordersum+"");
        List.add(tuidingsum+"");
        List.add(jiaochesum+"");
        List.add(lejsum+"");
        List.add(lcsum+"");

        totlemap.put(a,List);

    }

    class MyAdapter extends BaseExpandableListAdapter{

        /**
         * 获取父item个数
         * @return
         */
        @Override
        public int getGroupCount() {
            return parent.size();
        }

        /**父item下子item的个数
         * @param i
         * @return
         */
        @Override
        public int getChildrenCount(int i) {
            String key=parent.get(i);
            int size=result.get(i).get(key).size();
            return size;
        }

        /**
         * 获取当前父item数据
         * @param i
         * @return
         */
        @Override
        public Object getGroup(int i) {
            return parent.get(i);
        }

        /**
         * 获取子item数据
         * @param i
         * @param i1
         * @return
         */
        @Override
        public Object getChild(int i, int i1) {
            String key=parent.get(i);
            return (result.get(i).get(key).get(i1));
        }

        /**
         * 父item id
         * @param i
         * @return
         */
        @Override
        public long getGroupId(int i) {
            return i;
        }

        /**
         * 子item id
         * @param i
         * @param i1
         * @return
         */
        @Override
        public long getChildId(int i, int i1) {
            return i1;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        /**父item 布局
         * @param i
         * @param b
         * @param view
         * @param viewGroup
         * @return
         */
        @Override
        public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
            if(view==null){
                view=View.inflate(getActivity(),R.layout.item_group,null);

            }
            String ares=parent.get(i);
            TextView tv_parent= (TextView) view.findViewById(R.id.tv_parent);
            TextView tv_laidian=(TextView) view.findViewById(R.id.tv_laidian);//来电
            TextView tv_jindian=(TextView) view.findViewById(R.id.tv_jindian);//进店
            TextView tv_dingdan=(TextView) view.findViewById(R.id.tv_dindan);//订单
            TextView tv_tuidin=(TextView) view.findViewById(R.id.tv_tuidin);//退订
            TextView tv_jiaoche=(TextView) view.findViewById(R.id.tv_jiaoche);//交车
            TextView tv_nj=(TextView) view.findViewById(R.id.tv_nj);//累计
            TextView tv_lc=(TextView) view.findViewById(R.id.tv_lc);//留存

            tv_parent.setText(parent.get(i));
            tv_laidian.setText(totlemap.get(ares).get(0));
            tv_jindian.setText(totlemap.get(ares).get(1));
            tv_dingdan.setText(totlemap.get(ares).get(2));
            tv_tuidin.setText(totlemap.get(ares).get(3));
            tv_jiaoche.setText(totlemap.get(ares).get(4));
            tv_nj.setText(totlemap.get(ares).get(5));
            tv_lc.setText(totlemap.get(ares).get(6));
            return view;
        }

        /**子item布局
         * @param i
         * @param i1
         * @param b
         * @param view
         * @param viewGroup
         * @return
         */
        @Override
        public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
            if(view==null){
                view=View.inflate(getActivity(),R.layout.item_child,null);
            }
            TextView tv_child= (TextView) view.findViewById(R.id.tv_chexin);
            TextView tv_laidian= (TextView) view.findViewById(R.id.tv_laidian);
            TextView tv_jindian= (TextView) view.findViewById(R.id.tv_jindian);
            TextView tv_oreder= (TextView) view.findViewById(R.id.tv_dindan);
            TextView tv_tuidin= (TextView) view.findViewById(R.id.tv_tuidin);
            TextView tv_jiaoc= (TextView) view.findViewById(R.id.tv_jiaoche);
            TextView tv_nlj= (TextView) view.findViewById(R.id.tv_ylj);
            TextView tv_lc= (TextView) view.findViewById(R.id.tv_lc);
            String key=parent.get(i);
            String info= result.get(i).get(key).get(i1).getModels();//车型
            tv_child.setText(info);
            tv_laidian.setText(result.get(i).get(key).get(i1).getCall());//来电
            tv_jindian.setText(result.get(i).get(key).get(i1).getShowRoom());//进店
            tv_oreder.setText(result.get(i).get(key).get(i1).getOrder());//订单
            tv_tuidin.setText(result.get(i).get(key).get(i1).getUnsubscribe());//退订
            tv_jiaoc.setText(result.get(i).get(key).get(i1).getRetail());//交车
            tv_nlj.setText(result.get(i).get(key).get(i1).getTotalOrder());//累计订单
            tv_lc.setText(result.get(i).get(key).get(i1).getRetainOrder());//留存
            return view;
        }

        /**
         * 子集是否能被点击
         * @param i
         * @param i1
         * @return
         */
        @Override
        public boolean isChildSelectable(int i, int i1) {
            return true;
        }
    }
   /**与广播相关的**/
    private MyReceiver myReceiver;
    public static final String UPDATE_QUANBU ="com.update_quanbu";
    public static final String UPDATE_ZT ="com.update_zt";
    public static final String UPDATE_CZ ="com.update_cz";
    public static final String SHOWDIALOG ="show.dialog";

    public class MyReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(UPDATE_QUANBU)){//全部数据
                dialog.show();
                if(type.equals("日")){
                    Dateinfo(date,UserModle.getUserBean().getJXSName(),UserModle.getUserBean().getRole(),"全部",0);//获取日的详情
                }else if(type.equals("季度")){
                    Quarterinfo(date, UserModle.getUserBean().getJXSName(),UserModle.getUserBean().getRole(),"全部",tthis,0);//获取季度数据
                }else if(type.equals("月")){
                    Monthinfo(date, UserModle.getUserBean().getJXSName(),UserModle.getUserBean().getRole(),"全部",tthis,0);//月
                }else if(type.equals("年")){
                    Yearinfo(date, UserModle.getUserBean().getJXSName(),UserModle.getUserBean().getRole(),"全部",tthis,0);
                }
            }else if(intent.getAction().equals(UPDATE_ZT)){//展厅数据
                dialog.show();
                if(type.equals("日")){
                    Dateinfo(date,UserModle.getUserBean().getJXSName(),UserModle.getUserBean().getRole(),"是",0);//获取日的详情
                }else if(type.equals("季度")){
                    Quarterinfo(date, UserModle.getUserBean().getJXSName(),UserModle.getUserBean().getRole(),"是",tthis,0);//获取季度数据
                }else if(type.equals("月")){
                    Monthinfo(date, UserModle.getUserBean().getJXSName(),UserModle.getUserBean().getRole(),"是",tthis,0);//月
                }else if(type.equals("年")){
                    Yearinfo(date, UserModle.getUserBean().getJXSName(),UserModle.getUserBean().getRole(),"是",tthis,0);
                }

            }else if(intent.getAction().equals(UPDATE_CZ)){//车展数据
                dialog.show();
                if(type.equals("日")){
                    Dateinfo(date,UserModle.getUserBean().getJXSName(),UserModle.getUserBean().getRole(),"否",0);//获取日的详情
                }else if(type.equals("季度")){
                    Quarterinfo(date, UserModle.getUserBean().getJXSName(),UserModle.getUserBean().getRole(),"否",tthis,0);//获取季度数据
                }else if(type.equals("月")){
                    Monthinfo(date, UserModle.getUserBean().getJXSName(),UserModle.getUserBean().getRole(),"否",tthis,0);//月
                }else if(type.equals("年")){
                    Yearinfo(date, UserModle.getUserBean().getJXSName(),UserModle.getUserBean().getRole(),"否",tthis,0);
                }

            }else if(intent.getAction().equals(SHOWDIALOG)){
                getunm("get","dealercount",UserModle.getUserBean().getJXSName());//获取金销售个数
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if(myReceiver==null){
            myReceiver=new MyReceiver();
            getActivity().registerReceiver(myReceiver,new IntentFilter(UPDATE_QUANBU));
            getActivity().registerReceiver(myReceiver,new IntentFilter(UPDATE_ZT));
            getActivity().registerReceiver(myReceiver,new IntentFilter(UPDATE_CZ));
            getActivity().registerReceiver(myReceiver,new IntentFilter(SHOWDIALOG));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (myReceiver!=null){
            getActivity().unregisterReceiver(myReceiver);
            myReceiver=null;
        }
    }


    /**
     * 获取经销商个数
     * @param op
     * @param obj
     * @param username
     */
    private void getunm(String op,String obj,String username) {
        UserManager.getUserManager().findNum(op, obj, username, new ResultCallback<String>() {
            @Override
            public void onError(int status, String errorMsg) {
                MyToastUtils.showShortToast(getActivity(),errorMsg);
            }

            @Override
            public void onResponse(String response) {
                jxsnum=response;
                getjpnum("get","elitedealercount",UserModle.getUserBean().getJXSName());//获取精品店个数
            }
        });
    }



    /**
     * 获取精品个数
     * @param op
     * @param obj
     * @param username
     */
    private void getjpnum(String op,String obj,String username) {
        UserManager.getUserManager().findNum(op, obj, username, new ResultCallback<String>() {
            @Override
            public void onError(int status, String errorMsg) {
                MyToastUtils.showShortToast(getActivity(),errorMsg);
            }

            @Override
            public void onResponse(String response) {
                jpnum=response;
                jc.clear();
//                for (int i=0;i<modle.size();i++){
//                    getmodle(modle.get(i));//获取车型
//                }
                showdialog();
            }
        });
    }


    /**
     * 显示dialog
     */
    private void showdialog() {
        MySelfSheetDialog dialog = new MySelfSheetDialog(getActivity());
        dialog.builder().setTitle("请选择发送方式").addSheetItem("发送文字", MySelfSheetDialog.SheetItemColor.Blue, new MySelfSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {
                if (UserModle.getUserBean().getRole().equals("大区经理")) {
                    buildcontent();
                }else if(UserModle.getUserBean().getRole().equals("小区经理")){
                    buildcontent();
                }else if(UserModle.getUserBean().getRole().equals("管理员")){
                    buildcontent();
                }
            }
        }).show();
    }

    /**
     *
     */
    private void   buildcontent() {
        String diannum;//经销商店数量和精品数量
        if (!TextUtils.isEmpty(jxsnum) && !TextUtils.isEmpty(jpnum)) {
            diannum = "共" + jxsnum + "家4s店" + "(" + jpnum + "家精品店" + "),";
        } else {
            diannum = "";
        }

        Uri smsUri = Uri.parse("smsto:");
        Intent intent = new Intent(Intent.ACTION_VIEW, smsUri);
        intent.setType("vnd.android-dir/mms-sms");

        if (list != null && list.size() != 0) {//有数据的时候
            List<SalseDateBean> list = null;
            String dxcon = "";//最终的短信内容
            String cxcontent ="";//各车型的数据
            List<String> listzj = totlemap.get("总计");
            String xqjlcontent = "";//小区经理要加上的下面父控件的内容
            String content = "总计;" + "来电" + listzj.get(0) + "/进店" + listzj.get(1) + "/订单" + listzj.get(2) + "/退订" + listzj.get(3) + "/交车" + listzj.get(4) + "/累计订单" + listzj.get(5) + "/留存订单" + listzj.get(6);
            for (int i = 0; i < result.size(); i++) {
                list = result.get(i).get("总计");
            }
            for (int j = 0; j < list.size(); j++) {
                //车型内容
                cxcontent = cxcontent + list.get(j).getModels() + ";" + "来电" + list.get(j).getCall() + "/进店" + list.get(j).getShowRoom() + "/订单" + list.get(j).getOrder() + "/退订" + list.get(j).getUnsubscribe() + "/交车" + list.get(j).getRetail() + "/累计订单" + list.get(j).getTotalOrder() + "/留存订单" + list.get(j).getRetainOrder() + "\n";
            }
            Map<String, List<String>> xqjlmap = new HashMap<>();//小区经理需要的map,不包括总计
            for (String key : totlemap.keySet()) {//map遍历只有小区经理有这项(这里的总计就没有了)
                if (!"总计".equals(key)) {
                    xqjlmap.put(key, totlemap.get(key));
                }

            }

            for (String xjkey : xqjlmap.keySet()) {//在遍历去掉总计的map
                List<String> xqlist = xqjlmap.get(xjkey);
                xqjlcontent = xqjlcontent + xjkey + ";" + "来电" + xqlist.get(0) + "/进店" + xqlist.get(1) + "/订单" + xqlist.get(2) + "/退订" + xqlist.get(3) + "/交车" + xqlist.get(4) + "/累计订单" + xqlist.get(5) + "/留存订单" + xqlist.get(6) + "\n";
            }


            if (UserModle.getUserBean().getRole().equals("大区经理")) {
                dxcon = time + "," + UserModle.getUserBean().getName() + "\n" + diannum + "\n" + content + "\n" + cxcontent;
            } else if (UserModle.getUserBean().getRole().equals("小区经理")) {
                dxcon = time + "," + UserModle.getUserBean().getName() + "\n" + diannum + "\n" + content + "\n" + cxcontent + xqjlcontent;
            } else if (UserModle.getUserBean().getRole().equals("管理员")) {
                dxcon = time + "," + "全国Cadi战报;" + "\n" + diannum + "\n" + content + "\n" + cxcontent;
            }
            intent.putExtra("sms_body", dxcon);
        } else {//后台没数据返回来
            String zojicontent = "总计;" + "来电" + 0 + "/进店" + 0 + "/订单" +0 + "/退订" + 0 + "/交车" + 0 + "/累计订单" + 0 + "/留存订单" + 0;
            String chexincontent = "";//各车型的总计
            String duxin;

            if(UserModle.getUserBean()!=null){
                List<String>nolist=UserModle.getUserBean().getModels();
                for(int i=0;i<nolist.size();i++){
                    chexincontent=chexincontent+nolist.get(i)+ "来电" + 0 + "/进店" + 0 + "/订单" +0 + "/退订" + 0 + "/交车" + 0 + "/累计订单" + 0 + "/留存订单" + 0;
                }
            }
            if(UserModle.getUserBean().getRole().equals("管理员")){
                duxin=time + "," + UserModle.getUserBean().getName() + "\n" + diannum + "\n" + zojicontent + "\n" + chexincontent;

            }else {
                duxin = time + "," +  "全国Cadi战报;" + "\n" + diannum + "\n" + zojicontent + "\n" + chexincontent;
            }
            intent.putExtra("sms_body", duxin);
        }

        startActivity(intent);



    }


}
