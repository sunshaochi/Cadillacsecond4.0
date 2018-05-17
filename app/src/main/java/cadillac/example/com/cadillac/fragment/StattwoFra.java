package cadillac.example.com.cadillac.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
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

public class StattwoFra extends BaseFrag {
    public static final String UPDATETWOUI ="com.stattwofra";
    @ViewInject(R.id.ex_two)
    private ExpandableListView ex_two;
    @ViewInject(R.id.tv_twotitle)
    private TextView tv_twotitle;
    private String type;
    private String queryType;

    private List<DatilBean> list;
    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.stattwofra,null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
     type=getArguments().getString("type");
        queryType=getArguments().getString("queryType");
        if(queryType.equals("0")){//日
            tv_twotitle.setText("月累计数据");
        }else if(queryType.equals("1")){//月
            tv_twotitle.setText("季度累计数据");

        }else if(queryType.equals("2")){//年
            tv_twotitle.setText("年累计数据");

        }else if(queryType.equals("3")){//季度
            tv_twotitle.setText("季度累计数据");

        }
    }


    private Myrecive myrecive;

    @Override
    public void onStart() {
        super.onStart();
        if(myrecive==null){
            myrecive=new Myrecive();
            getActivity().registerReceiver(myrecive,new IntentFilter(UPDATETWOUI));
        }
    }


    public class Myrecive extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(UPDATETWOUI)){
                list=intent.getParcelableArrayListExtra("list");
                if(list==null||list.size()==0){
                    list=new ArrayList<>();
                    list.clear();
                }
                ex_two.setAdapter(new MyAdapter());
//                if(list!=null&&list.size()>0){
//                    ex_two.setAdapter(new MyAdapter());
//                }else {
//                    MyToastUtils.showShortToast(getActivity(),"暂无数据");
//
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
                view=View.inflate(getActivity(),R.layout.item_pagetwo,null);
            }
            LinearLayout ll_itemtwo= (LinearLayout) view.findViewById(R.id.ll_itemtwo);
            ImageView iv_stat= (ImageView) view.findViewById(R.id.iv_redstat);
            View lin= view.findViewById(R.id.view_v);
            TextView tv_dycx= (TextView) view.findViewById(R.id.tv_dycx);
            TextView tv_dyxzjd= (TextView) view.findViewById(R.id.tv_dyxzjd);
            TextView tv_dyecjd= (TextView) view.findViewById(R.id.tv_dyecjd);
            TextView tv_dyecjdl= (TextView) view.findViewById(R.id.tv_dyecjdl);
            TextView tv_dyjc= (TextView) view.findViewById(R.id.tv_dyjc);
            TextView tv_dyddjfl= (TextView) view.findViewById(R.id.tv_dyddjfl);
            TextView tv_dysjs= (TextView) view.findViewById(R.id.tv_dysjs);
            TextView tv_dysjl= (TextView) view.findViewById(R.id.tv_dysjl);
            ll_itemtwo.setBackgroundColor(Color.parseColor("#ffffff"));
            iv_stat.setVisibility(View.VISIBLE);

            if(i==0){
                lin.setBackgroundColor(Color.parseColor("#8a152b"));
            }else {
                lin.setBackgroundColor(Color.parseColor("#BCB7B8"));
            }
            //赋值
            tv_dycx.setText(list.get(i).getCarModelName());
            if(type.equals("全部")){
                tv_dyxzjd.setText(list.get(i).getTtlIncomeCntALL()+"");//新增加进店
                tv_dyecjd.setText(list.get(i).getTtlSecondIncomeCntALL()+"");//二次进店
                tv_dyecjdl.setText(list.get(i).getSecondIncomeRateALL()+"%");//二次进店率
                tv_dyjc.setText(list.get(i).getTtlDeliveryCntALL()+"");//当月交车
                tv_dyddjfl.setText(list.get(i).getDeliveryRateALL()+"%");//订单交付率
                tv_dysjs.setText(list.get(i).getTtlTryDriveCntALL()+"");//试驾数
                tv_dysjl.setText(list.get(i).getTryDriveRateALL()+"%");//试驾率
            }else if(type.equals("展厅")){
                tv_dyxzjd.setText(list.get(i).getTtlIncomeCntRoom()+"");//展厅新增加进店
                tv_dyecjd.setText(list.get(i).getTtlSecondIncomeCntRoom()+"");//展厅二次进店
                tv_dyecjdl.setText(list.get(i).getSecondIncomeRateRoom()+"%");//展厅二次进店率
                tv_dyjc.setText(list.get(i).getTtlDeliveryCntRoom()+"");//展厅当月交车
                tv_dyddjfl.setText(list.get(i).getDeliveryRateRoom()+"%");//订单交付率
                tv_dysjs.setText(list.get(i).getTtlTryDriveCntRoom()+"");//展厅试驾数
                tv_dysjl.setText(list.get(i).getTryDriveRateRoom()+"%");//展厅试驾率
            }else if(type.equals("车展")){
                tv_dyxzjd.setText(list.get(i).getTtlIncomeCntShow()+"");//车展新增加进店
                tv_dyecjd.setText(list.get(i).getTtlSecondIncomeCntShow()+"");//车展二次进店
                tv_dyecjdl.setText(list.get(i).getSecondIncomeRateShow()+"%");//车展二次进店率
                tv_dyjc.setText(list.get(i).getTtlDeliveryCntShow()+"");//车展当月交车
                tv_dyddjfl.setText(list.get(i).getDeliveryRateShow()+"%");//车展订单交付率
                tv_dysjs.setText(list.get(i).getTtlTryDriveCntShow()+"");//车展试驾数
                tv_dysjl.setText(list.get(i).getTryDriveRateShow()+"%");//展厅试驾率
            }


            return view;
        }

        @Override
        public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
            if(view==null){
                view=View.inflate(getActivity(),R.layout.item_pagetwo,null);
            }
            LinearLayout ll_itemtwo= (LinearLayout) view.findViewById(R.id.ll_itemtwo);
            ImageView iv_stat= (ImageView) view.findViewById(R.id.iv_redstat);
            View lin= view.findViewById(R.id.view_v);
            TextView tv_dycx= (TextView) view.findViewById(R.id.tv_dycx);
            TextView tv_dyxzjd= (TextView) view.findViewById(R.id.tv_dyxzjd);
            TextView tv_dyecjd= (TextView) view.findViewById(R.id.tv_dyecjd);
            TextView tv_dyecjdl= (TextView) view.findViewById(R.id.tv_dyecjdl);
            TextView tv_dyjc= (TextView) view.findViewById(R.id.tv_dyjc);
            TextView tv_dyddjfl= (TextView) view.findViewById(R.id.tv_dyddjfl);
            TextView tv_dysjs= (TextView) view.findViewById(R.id.tv_dysjs);
            TextView tv_dysjl= (TextView) view.findViewById(R.id.tv_dysjl);
            iv_stat.setVisibility(View.GONE);
            //赋值
            tv_dycx.setText(list.get(i).getDetailList().get(i1).getCarModelName());
            if(type.equals("全部")) {
                tv_dyxzjd.setText(list.get(i).getDetailList().get(i1).getTtlIncomeCntALL() + "");//新增加进店
                tv_dyecjd.setText(list.get(i).getDetailList().get(i1).getTtlSecondIncomeCntALL() + "");//二次进店
                tv_dyecjdl.setText(list.get(i).getDetailList().get(i1).getSecondIncomeRateALL() + "%");//二次进店率
                tv_dyjc.setText(list.get(i).getDetailList().get(i1).getTtlDeliveryCntALL() + "");//当月交车
                tv_dyddjfl.setText(list.get(i).getDetailList().get(i1).getDeliveryRateALL() + "%");//订单交付率
                tv_dysjs.setText(list.get(i).getDetailList().get(i1).getTtlTryDriveCntALL() + "");//试驾数
                tv_dysjl.setText(list.get(i).getDetailList().get(i1).getTryDriveRateALL() + "%");//试驾率
            }else if(type.equals("展厅")){
                tv_dyxzjd.setText(list.get(i).getDetailList().get(i1).getTtlIncomeCntRoom()+"");//展厅新增加进店
                tv_dyecjd.setText(list.get(i).getDetailList().get(i1).getTtlSecondIncomeCntRoom()+"");//展厅二次进店
                tv_dyecjdl.setText(list.get(i).getDetailList().get(i1).getSecondIncomeRateRoom()+"%");//展厅二次进店率
                tv_dyjc.setText(list.get(i).getDetailList().get(i1).getTtlDeliveryCntRoom()+"");//展厅当月交车
                tv_dyddjfl.setText(list.get(i).getDetailList().get(i1).getDeliveryRateRoom()+"%");//订单交付率
                tv_dysjs.setText(list.get(i).getDetailList().get(i1).getTtlTryDriveCntRoom()+"");//展厅试驾数
                tv_dysjl.setText(list.get(i).getDetailList().get(i1).getTryDriveRateRoom()+"%");//展厅试驾率
            }else if(type.equals("车展")){
                tv_dyxzjd.setText(list.get(i).getDetailList().get(i1).getTtlIncomeCntShow()+"");//车展新增加进店
                tv_dyecjd.setText(list.get(i).getDetailList().get(i1).getTtlSecondIncomeCntShow()+"");//车展二次进店
                tv_dyecjdl.setText(list.get(i).getDetailList().get(i1).getSecondIncomeRateShow()+"%");//车展二次进店率
                tv_dyjc.setText(list.get(i).getDetailList().get(i1).getTtlDeliveryCntShow()+"");//车展当月交车
                tv_dyddjfl.setText(list.get(i).getDetailList().get(i1).getDeliveryRateShow()+"%");//车展订单交付率
                tv_dysjs.setText(list.get(i).getDetailList().get(i1).getTtlTryDriveCntShow()+"");//车展试驾数
                tv_dysjl.setText(list.get(i).getDetailList().get(i1).getTryDriveRateShow()+"%");//展厅试驾率
            }
            ll_itemtwo.setBackgroundColor(Color.parseColor("#f1f1f1"));
            lin.setBackgroundColor(Color.parseColor("#bcb7b8"));
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
