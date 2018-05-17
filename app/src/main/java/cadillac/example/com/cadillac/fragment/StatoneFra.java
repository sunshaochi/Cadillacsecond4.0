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

public class StatoneFra extends BaseFrag {
    @ViewInject(R.id.ex_one)
    private ExpandableListView ex_one;
    @ViewInject(R.id.tv_drsj)
    private TextView tv_drsj;
    @ViewInject(R.id.tv_ylsj)
    private TextView tv_ylsj;

    private List<DatilBean> list;
    private String type;
    private String queryType;

    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.statonefra, null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        type = getArguments().getString("type");
        queryType=getArguments().getString("queryType");
        if(queryType.equals("0")){//日
            tv_drsj.setText("当日数据");
            tv_ylsj.setText("月累数据");
        }else if(queryType.equals("1")){//月
          tv_drsj.setText("当月数据");
          tv_ylsj.setText("季度数据");
        }else if(queryType.equals("2")){//年
            tv_drsj.setText("当年数据");
            tv_ylsj.setText("年度数据");
        }else if(queryType.equals("3")){//季度
            tv_drsj.setText("当季数据");
            tv_ylsj.setText("年度数据");
        }
    }


    private Myrecive myrecive;
    public static final String UPDATEONEUI = "com.statonefra";

    @Override
    public void onStart() {
        super.onStart();
        if (myrecive == null) {
            myrecive = new Myrecive();
            getActivity().registerReceiver(myrecive, new IntentFilter(UPDATEONEUI));
        }
    }

    public class Myrecive extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(UPDATEONEUI)) {
                list = intent.getParcelableArrayListExtra("list");
                if(list==null||list.size()==0){
                    list=new ArrayList<>();
                    list.clear();
                }
                ex_one.setAdapter(new MyAdapter());
//                if (list!=null && list.size() >0) {
//                    ex_one.setAdapter(new MyAdapter());
//                } else {
//                    MyToastUtils.showShortToast(getActivity(), "暂无数据");
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
            if (view == null) {
                view = View.inflate(getActivity(), R.layout.item_pageone, null);
            }
            LinearLayout ll_itemone = (LinearLayout) view.findViewById(R.id.ll_itemone);
            ImageView iv_stat = (ImageView) view.findViewById(R.id.iv_redstat);
            View lin = view.findViewById(R.id.view_v);
            TextView tv_mode = (TextView) view.findViewById(R.id.tv_mode);
            TextView tv_xz = (TextView) view.findViewById(R.id.tv_xz);
            TextView tv_ecjd = (TextView) view.findViewById(R.id.tv_ecjd);
            TextView tv_dtdd = (TextView) view.findViewById(R.id.tv_dtdd);
            TextView tv_dtjc = (TextView) view.findViewById(R.id.tv_dtjc);
            TextView tv_dydd = (TextView) view.findViewById(R.id.tv_dydd);
            TextView tv_dyddl = (TextView) view.findViewById(R.id.tv_dyddl);
            TextView tv_dylc = (TextView) view.findViewById(R.id.tv_dylc);//当月留存
            ll_itemone.setBackgroundColor(Color.parseColor("#ffffff"));
            iv_stat.setVisibility(View.VISIBLE);

            if (i == 0) {
                lin.setBackgroundColor(Color.parseColor("#8a152b"));
            } else {
                lin.setBackgroundColor(Color.parseColor("#BCB7B8"));
            }
            //设置值
            tv_mode.setText(list.get(i).getCarModelName());
            if (type.equals("全部")) {

                tv_xz.setText(list.get(i).getIncomeCntALL() + "");//新增进店
                tv_ecjd.setText(list.get(i).getSecondIncomeCntALL() + "");//二次进店数
                tv_dtdd.setText(list.get(i).getOrderCntALL() + "");//当天订单
                tv_dtjc.setText(list.get(i).getDeliveryCntALL() + "");//当天交车
                tv_dydd.setText(list.get(i).getTtlOrderCntALL() + "");//月订单数
                tv_dyddl.setText(list.get(i).getOrderRateALL() + "%");//月订单率
                tv_dylc.setText(list.get(i).getRemainOrderCntALL() + "");//留存
            } else if (type.equals("展厅")) {
                tv_xz.setText(list.get(i).getIncomeCntRoom() + "");//新增进店
                tv_ecjd.setText(list.get(i).getSecondIncomeCntRoom() + "");//二次进店数
                tv_dtdd.setText(list.get(i).getOrderCntRoom() + "");//当天订单
                tv_dtjc.setText(list.get(i).getDeliveryCntRoom() + "");//当天交车
                tv_dydd.setText(list.get(i).getTtlOrderCntRoom() + "");//月订单数
                tv_dyddl.setText(list.get(i).getOrderRateRoom() + "%");//月订单率
                tv_dylc.setText(list.get(i).getRemainOrderCntRoom() + "");//留存
            } else if (type.equals("车展")) {
                tv_xz.setText(list.get(i).getIncomeCntShow() + "");//新增进店
                tv_ecjd.setText(list.get(i).getSecondIncomeCntShow() + "");//二次进店数
                tv_dtdd.setText(list.get(i).getOrderCntShow() + "");//当天订单
                tv_dtjc.setText(list.get(i).getDeliveryCntShow() + "");//当天交车
                tv_dydd.setText(list.get(i).getTtlOrderCntShow() + "");//月订单数
                tv_dyddl.setText(list.get(i).getOrderRateShow() + "%");//月订单率
                tv_dylc.setText(list.get(i).getRemainOrderCntShow() + "");//留存
            }

            return view;
        }

        @Override
        public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = View.inflate(getActivity(), R.layout.item_pageone, null);
            }
            LinearLayout ll_itemone = (LinearLayout) view.findViewById(R.id.ll_itemone);
            View lin = view.findViewById(R.id.view_v);
            TextView tv_mode = (TextView) view.findViewById(R.id.tv_mode);
            TextView tv_xz = (TextView) view.findViewById(R.id.tv_xz);
            TextView tv_ecjd = (TextView) view.findViewById(R.id.tv_ecjd);
            TextView tv_dtdd = (TextView) view.findViewById(R.id.tv_dtdd);
            TextView tv_dtjc = (TextView) view.findViewById(R.id.tv_dtjc);
            TextView tv_dydd = (TextView) view.findViewById(R.id.tv_dydd);
            TextView tv_dyddl = (TextView) view.findViewById(R.id.tv_dyddl);
            TextView tv_dylc = (TextView) view.findViewById(R.id.tv_dylc);//当月留存
            ll_itemone.setBackgroundColor(Color.parseColor("#f1f1f1"));
            lin.setVisibility(View.VISIBLE);
            lin.setBackgroundColor(Color.parseColor("#bcb7b8"));
            //赋值
            tv_mode.setText(list.get(i).getDetailList().get(i1).getCarModelName());
            if (type.equals("全部")) {
                tv_xz.setText(list.get(i).getDetailList().get(i1).getIncomeCntALL() + "");//新增进店
                tv_ecjd.setText(list.get(i).getDetailList().get(i1).getSecondIncomeCntALL() + "");//二次进店数
                tv_dtdd.setText(list.get(i).getDetailList().get(i1).getOrderCntALL() + "");//当天订单
                tv_dtjc.setText(list.get(i).getDetailList().get(i1).getDeliveryCntALL() + "");//当天交车
                tv_dydd.setText(list.get(i).getDetailList().get(i1).getTtlOrderCntALL() + "");//月订单数
                tv_dyddl.setText(list.get(i).getDetailList().get(i1).getOrderRateALL() + "%");//月订单率
                tv_dylc.setText(list.get(i).getDetailList().get(i1).getRemainOrderCntALL() + "");//留存

            }else if(type.equals("展厅")){
                tv_xz.setText(list.get(i).getDetailList().get(i1).getIncomeCntRoom() + "");//新增进店
                tv_ecjd.setText(list.get(i).getDetailList().get(i1).getSecondIncomeCntRoom() + "");//二次进店数
                tv_dtdd.setText(list.get(i).getDetailList().get(i1).getOrderCntRoom() + "");//当天订单
                tv_dtjc.setText(list.get(i).getDetailList().get(i1).getDeliveryCntRoom() + "");//当天交车
                tv_dydd.setText(list.get(i).getDetailList().get(i1).getTtlOrderCntRoom() + "");//月订单数
                tv_dyddl.setText(list.get(i).getDetailList().get(i1).getOrderRateRoom() + "%");//月订单率
                tv_dylc.setText(list.get(i).getDetailList().get(i1).getRemainOrderCntRoom() + "");//留存
            }else if(type.equals("车展")){
                tv_xz.setText(list.get(i).getDetailList().get(i1).getIncomeCntShow() + "");//新增进店
                tv_ecjd.setText(list.get(i).getDetailList().get(i1).getSecondIncomeCntShow() + "");//二次进店数
                tv_dtdd.setText(list.get(i).getDetailList().get(i1).getOrderCntShow() + "");//当天订单
                tv_dtjc.setText(list.get(i).getDetailList().get(i1).getDeliveryCntShow() + "");//当天交车
                tv_dydd.setText(list.get(i).getDetailList().get(i1).getTtlOrderCntShow() + "");//月订单数
                tv_dyddl.setText(list.get(i).getDetailList().get(i1).getOrderRateShow() + "%");//月订单率
                tv_dylc.setText(list.get(i).getDetailList().get(i1).getRemainOrderCntShow() + "");//留存
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
        if (myrecive != null) {
            getActivity().unregisterReceiver(myrecive);
            myrecive = null;
        }
    }
}





