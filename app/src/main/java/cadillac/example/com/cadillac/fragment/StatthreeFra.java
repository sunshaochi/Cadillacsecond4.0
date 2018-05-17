package cadillac.example.com.cadillac.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import cadillac.example.com.cadillac.R;
import cadillac.example.com.cadillac.base.BaseFrag;
import cadillac.example.com.cadillac.bean.DatilBean;
import cadillac.example.com.cadillac.utils.MyToastUtils;

/**
 * Created by iris on 2017/12/29.
 */

public class StatthreeFra extends BaseFrag{
    @ViewInject(R.id.ex_three)
    private ExpandableListView ex_three;
    @ViewInject(R.id.tv_threetitle)
    private TextView tv_threetitle;
    private List<DatilBean> list;
    private String type;
    private String queryType;
    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.statthreefra,null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
     type=getArguments().getString("type");
        queryType=getArguments().getString("queryType");
        if(queryType.equals("0")){//日
            tv_threetitle.setText("月累计数据");
        }else if(queryType.equals("1")){//月
            tv_threetitle.setText("季度累计数据");

        }else if(queryType.equals("2")){//年
            tv_threetitle.setText("年累计数据");

        }else if(queryType.equals("3")){//季度
            tv_threetitle.setText("季度累计数据");

        }
    }

    public static final String UPDATEUI = "com.statthreefra";
    private Myrecive myrecive;

    @Override
    public void onStart() {
        super.onStart();
        if(myrecive==null){
            myrecive=new Myrecive();
            getActivity().registerReceiver(myrecive,new IntentFilter(UPDATEUI));
        }
    }

    public class Myrecive extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(UPDATEUI)){
                list=intent.getParcelableArrayListExtra("list");
                if(list==null||list.size()==0){
                    list=new ArrayList<>();
                    list.clear();
                }
                ex_three.setAdapter(new MyAdapter());

//                if(list!=null&&list.size()>0){
//                    ex_three.setAdapter(new MyAdapter());
//                }else {
//                    MyToastUtils.showShortToast(getActivity(),"暂无数据");
//                }
            }
        }
    }

    /**
     * 可以扩展的适配器
     */
    class MyAdapter extends BaseExpandableListAdapter {
        @Override
        public int getGroupCount() {
            return list.size();
        }

        @Override
        public int getChildrenCount(int i) {
            return list.get(i).getDetailList().size();
        }

        @Override
        public Object getGroup(int i) {
            return list.get(i);
        }

        @Override
        public Object getChild(int i, int i1) {
            return list.get(i).getDetailList().get(i1);
        }

        @Override
        public long getGroupId(int i) {
            return i;
        }

        @Override
        public long getChildId(int i, int i1) {
            return i1;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
            if(view==null){
                view=View.inflate(getActivity(),R.layout.item_pagethree,null);
            }
            LinearLayout ll_itemthree=(LinearLayout)view.findViewById(R.id.ll_itemthree);
            View lin=view.findViewById(R.id.view_v);
            ImageView iv_redstat= (ImageView) view.findViewById(R.id.iv_redstat);
            TextView tv_ycx= (TextView) view.findViewById(R.id.tv_ycx);
            TextView tv_ytd= (TextView) view.findViewById(R.id.tv_ytd);
            TextView tv_ytdl= (TextView) view.findViewById(R.id.tv_ytdl);
            TextView tv_yxxscsz= (TextView) view.findViewById(R.id.tv_yxxscsz);
            TextView tv_yxxscl= (TextView) view.findViewById(R.id.tv_yxxscl);
            TextView tv_ywjtx= (TextView) view.findViewById(R.id.tv_ywjtx);
            TextView tv_ywjtxl= (TextView) view.findViewById(R.id.tv_ywjtxl);
            ll_itemthree.setBackgroundColor(Color.parseColor("#ffffff"));
            iv_redstat.setVisibility(View.VISIBLE);


            if(i==0){
                lin.setBackgroundColor(Color.parseColor("#8a152b"));
            }else {
                lin.setBackgroundColor(Color.parseColor("#BCB7B8"));
            }
            //赋值
            tv_ycx.setText(list.get(i).getCarModelName());
            if(type.equals("全部")) {
                tv_ytd.setText(list.get(i).getTtlCancelOrderCntALL() + "");//退订
                tv_ytdl.setText(list.get(i).getCancelOrderRateALL() + "%");//退订lv
                tv_yxxscsz.setText(list.get(i).getTtlInfoUploadCntALL() + "");//上传数
                tv_yxxscl.setText(list.get(i).getInfoUploadRateALL() + "%");//上传率
                tv_ywjtx.setText(list.get(i).getTtlQuestionCntALL() + "");//问卷填写
                tv_ywjtxl.setText(list.get(i).getQuestionRateALL() + "%");//
            }else if(type.equals("展厅")){
                tv_ytd.setText(list.get(i).getTtlCancelOrderCntRoom()+"");//退订
                tv_ytdl.setText(list.get(i).getCancelOrderRateRoom()+"%");//退订lv
                tv_yxxscsz.setText(list.get(i).getTtlInfoUploadCntRoom()+"");//上传数
                tv_yxxscl.setText(list.get(i).getInfoUploadRateRoom()+"%");//上传率
                tv_ywjtx.setText(list.get(i).getTtlQuestionCntRoom()+"");//问卷填写
                tv_ywjtxl.setText(list.get(i).getQuestionRateRoom()+"%");//
            }else if(type.equals("车展")){
                tv_ytd.setText(list.get(i).getTtlCancelOrderCntShow()+"");//退订
                tv_ytdl.setText(list.get(i).getCancelOrderRateShow()+"%");//退订lv
                tv_yxxscsz.setText(list.get(i).getTtlInfoUploadCntShow()+"");//上传数
                tv_yxxscl.setText(list.get(i).getInfoUploadRateShow()+"%");//上传率
                tv_ywjtx.setText(list.get(i).getTtlQuestionCntShow()+"");//问卷填写
                tv_ywjtxl.setText(list.get(i).getQuestionRateShow()+"%");//填写率
            }

            return view;
        }

        @Override
        public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
            if(view==null){
                view=View.inflate(getActivity(),R.layout.item_pagethree,null);
            }
            LinearLayout ll_itemthree=(LinearLayout)view.findViewById(R.id.ll_itemthree);
            View lin=view.findViewById(R.id.view_v);
            ImageView iv_redstat= (ImageView) view.findViewById(R.id.iv_redstat);
            TextView tv_ycx= (TextView) view.findViewById(R.id.tv_ycx);
            TextView tv_ytd= (TextView) view.findViewById(R.id.tv_ytd);
            TextView tv_ytdl= (TextView) view.findViewById(R.id.tv_ytdl);
            TextView tv_yxxscsz= (TextView) view.findViewById(R.id.tv_yxxscsz);
            TextView tv_yxxscl= (TextView) view.findViewById(R.id.tv_yxxscl);
            TextView tv_ywjtx= (TextView) view.findViewById(R.id.tv_ywjtx);
            TextView tv_ywjtxl= (TextView) view.findViewById(R.id.tv_ywjtxl);
            iv_redstat.setVisibility(View.GONE);
            ll_itemthree.setBackgroundColor(Color.parseColor("#f1f1f1"));
            lin.setBackgroundColor(Color.parseColor("#bcb7b8"));
            //付值
            tv_ycx.setText(list.get(i).getDetailList().get(i1).getCarModelName());
            if(type.equals("全部")) {
                tv_ytd.setText(list.get(i).getDetailList().get(i1).getTtlCancelOrderCntALL() + "");//退订
                tv_ytdl.setText(list.get(i).getDetailList().get(i1).getCancelOrderRateALL() + "%");//退订lv
                tv_yxxscsz.setText(list.get(i).getDetailList().get(i1).getTtlInfoUploadCntALL() + "");//上传数
                tv_yxxscl.setText(list.get(i).getDetailList().get(i1).getInfoUploadRateALL() + "%");//上传率
                tv_ywjtx.setText(list.get(i).getDetailList().get(i1).getTtlQuestionCntALL() + "");//问卷填写
                tv_ywjtxl.setText(list.get(i).getDetailList().get(i1).getQuestionRateALL() + "%");//退订
            }else if(type.equals("展厅")){
                tv_ytd.setText(list.get(i).getDetailList().get(i1).getTtlCancelOrderCntRoom()+"");//退订
                tv_ytdl.setText(list.get(i).getDetailList().get(i1).getCancelOrderRateRoom()+"%");//退订lv
                tv_yxxscsz.setText(list.get(i).getDetailList().get(i1).getTtlInfoUploadCntRoom()+"");//上传数
                tv_yxxscl.setText(list.get(i).getDetailList().get(i1).getInfoUploadRateRoom()+"%");//上传率
                tv_ywjtx.setText(list.get(i).getDetailList().get(i1).getTtlQuestionCntRoom()+"");//问卷填写
                tv_ywjtxl.setText(list.get(i).getDetailList().get(i1).getQuestionRateRoom()+"%");//填写率
            }else if(type.equals("车展")){
                tv_ytd.setText(list.get(i).getDetailList().get(i1).getTtlCancelOrderCntShow()+"");//退订
                tv_ytdl.setText(list.get(i).getDetailList().get(i1).getCancelOrderRateShow()+"%");//退订lv
                tv_yxxscsz.setText(list.get(i).getDetailList().get(i1).getTtlInfoUploadCntShow()+"");//上传数
                tv_yxxscl.setText(list.get(i).getDetailList().get(i1).getInfoUploadRateShow()+"%");//上传率
                tv_ywjtx.setText(list.get(i).getDetailList().get(i1).getTtlQuestionCntShow()+"");//问卷填写
                tv_ywjtxl.setText(list.get(i).getDetailList().get(i1).getQuestionRateShow()+"%");//填写率
            }

            return view;
        }

        @Override
        public boolean isChildSelectable(int i, int i1) {
            return false;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if(myrecive!=null){
            getActivity().unregisterReceiver(myrecive);
            myrecive=null;
        }
    }
}
