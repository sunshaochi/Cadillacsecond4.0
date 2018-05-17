package cadillac.example.com.cadillac.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import cadillac.example.com.cadillac.R;
import cadillac.example.com.cadillac.bean.DayinfoBean;

/**
 * Created by iris on 2017/12/13.
 */

public class ViewoneAdap extends BaseAdapter {
    private Context context;
    private DayinfoBean bean;
    private String type;


    public ViewoneAdap(Context context,DayinfoBean bean,String type) {
        this.context = context;
        this.bean=bean;
        this.type=type;

    }

    public void setChange(DayinfoBean bean){
        this.bean=bean;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {

            return bean.getDetailData().size() + 1;

    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHodle hodle;
        if(view==null) {
            view = View.inflate(context, R.layout.item_viewone, null);
            hodle=new ViewHodle();
            hodle.ll_itemone= (LinearLayout) view.findViewById(R.id.ll_itemone);
            hodle.lin= view.findViewById(R.id.view_v);
            hodle.tv_mode= (TextView) view.findViewById(R.id.tv_mode);
            hodle.tv_xz= (TextView) view.findViewById(R.id.tv_xz);
            hodle.tv_ecjd= (TextView) view.findViewById(R.id.tv_ecjd);
            hodle.tv_dtdd= (TextView) view.findViewById(R.id.tv_dtdd);
            hodle.tv_dtjc= (TextView) view.findViewById(R.id.tv_dtjc);
            hodle.tv_dydd= (TextView) view.findViewById(R.id.tv_dydd);
            hodle.tv_dyddl= (TextView) view.findViewById(R.id.tv_dyddl);
            hodle.tv_dylc= (TextView) view.findViewById(R.id.tv_dylc);//当月留存
            view.setTag(hodle);
        }else {
           hodle= (ViewHodle) view.getTag();
        }

        if(i==0){
            hodle.lin.setVisibility(View.VISIBLE);
            hodle.ll_itemone.setBackgroundColor(Color.parseColor("#ececec"));
            hodle.tv_mode.setText("总计");
            if(type.equals("全部")) {
                hodle.tv_xz.setText(bean.getTtlData().getIncomeCntALL()+"");//新增进店
                hodle.tv_ecjd.setText(bean.getTtlData().getSecondIncomeCntALL()+"");//二次进店数
                hodle.tv_dtdd.setText(bean.getTtlData().getOrderCntALL()+"");//当天订单
                hodle.tv_dtjc.setText(bean.getTtlData().getDeliveryCntALL()+"");//当天交车
                hodle.tv_dydd.setText(bean.getTtlData().getTtlOrderCntALL()+"");//月订单数
                hodle.tv_dyddl.setText(bean.getTtlData().getOrderRateALL()+"%");//月订单率
                hodle.tv_dylc.setText(bean.getTtlData().getRemainOrderCntALL()+"");//留存
            }else if(type.equals("展厅")){
                hodle.tv_xz.setText(bean.getTtlData().getIncomeCntRoom()+"");//新增进店
                hodle.tv_ecjd.setText(bean.getTtlData().getSecondIncomeCntRoom()+"");//二次进店数
                hodle.tv_dtdd.setText(bean.getTtlData().getOrderCntRoom()+"");//当天订单
                hodle.tv_dtjc.setText(bean.getTtlData().getDeliveryCntRoom()+"");//当天交车
                hodle.tv_dydd.setText(bean.getTtlData().getTtlOrderCntRoom()+"");//月订单数
                hodle.tv_dyddl.setText(bean.getTtlData().getOrderRateRoom()+"%");//月订单率
                hodle.tv_dylc.setText(bean.getTtlData().getRemainOrderCntRoom()+"");//留存

            }else if(type.equals("车展")){
                hodle.tv_xz.setText(bean.getTtlData().getIncomeCntShow()+"");//新增进店
                hodle.tv_ecjd.setText(bean.getTtlData().getSecondIncomeCntShow()+"");//二次进店数
                hodle.tv_dtdd.setText(bean.getTtlData().getOrderCntShow()+"");//当天订单
                hodle.tv_dtjc.setText(bean.getTtlData().getDeliveryCntShow()+"");//当天交车
                hodle.tv_dydd.setText(bean.getTtlData().getTtlOrderCntShow()+"");//月订单数
                hodle.tv_dyddl.setText(bean.getTtlData().getOrderRateShow()+"%");//月订单率
                hodle.tv_dylc.setText(bean.getTtlData().getRemainOrderCntShow()+"");//留存
            }
        }else {
            hodle.lin.setVisibility(View.GONE);
            if(i%2==0){
                hodle.ll_itemone.setBackgroundColor(Color.parseColor("#ffffff"));
            }else{
                hodle.ll_itemone.setBackgroundColor(Color.parseColor("#faf9f9"));
            }
            if(bean.getDetailData()!=null&&bean.getDetailData().size()>0){
                hodle.tv_mode.setText(bean.getDetailData().get(i-1).getCarModelName());
                if(type.equals("全部")) {
                    hodle.tv_xz.setText(bean.getDetailData().get(i - 1).getIncomeCntALL()+"");//新增进店
                    hodle.tv_ecjd.setText(bean.getDetailData().get(i - 1).getSecondIncomeCntALL()+"");//二次进店数
                    hodle.tv_dtdd.setText(bean.getDetailData().get(i - 1).getOrderCntALL()+"");//当天订单
                    hodle.tv_dtjc.setText(bean.getDetailData().get(i - 1).getDeliveryCntALL()+"");//当天交车
                    hodle.tv_dydd.setText(bean.getDetailData().get(i - 1).getTtlOrderCntALL()+"");//月订单数
                    hodle.tv_dyddl.setText(bean.getDetailData().get(i - 1).getOrderRateALL()+"%");//月订单率
                    hodle.tv_dylc.setText(bean.getDetailData().get(i - 1).getRemainOrderCntALL()+"");//留存
                }else if(type.equals("展厅")){
                    hodle.tv_xz.setText(bean.getDetailData().get(i - 1).getIncomeCntRoom()+"");//新增进店
                    hodle.tv_ecjd.setText(bean.getDetailData().get(i - 1).getSecondIncomeCntRoom()+"");//二次进店数
                    hodle.tv_dtdd.setText(bean.getDetailData().get(i - 1).getOrderCntRoom()+"");//当天订单
                    hodle.tv_dtjc.setText(bean.getDetailData().get(i - 1).getDeliveryCntRoom()+"");//当天交车
                    hodle.tv_dydd.setText(bean.getDetailData().get(i - 1).getTtlOrderCntRoom()+"");//月订单数
                    hodle.tv_dyddl.setText(bean.getDetailData().get(i - 1).getOrderRateRoom()+"%");//月订单率
                    hodle.tv_dylc.setText(bean.getDetailData().get(i - 1).getRemainOrderCntRoom()+"");//留存
                }else if(type.equals("车展")){
                    hodle.tv_xz.setText(bean.getDetailData().get(i - 1).getIncomeCntShow()+"");//新增进店
                    hodle.tv_ecjd.setText(bean.getDetailData().get(i - 1).getSecondIncomeCntShow()+"");//二次进店数
                    hodle.tv_dtdd.setText(bean.getDetailData().get(i - 1).getOrderCntShow()+"");//当天订单
                    hodle.tv_dtjc.setText(bean.getDetailData().get(i - 1).getDeliveryCntShow()+"");//当天交车
                    hodle.tv_dydd.setText(bean.getDetailData().get(i - 1).getTtlOrderCntShow()+"");//月订单数
                    hodle.tv_dyddl.setText(bean.getDetailData().get(i - 1).getOrderRateShow()+"%");//月订单率
                    hodle.tv_dylc.setText(bean.getDetailData().get(i - 1).getRemainOrderCntShow()+"");//留存
                }
            }
        }

        return view;
    }

    class ViewHodle {
        private LinearLayout ll_itemone;
        private View lin;
        private TextView tv_mode,tv_xz,tv_ecjd,tv_dtdd,tv_dtjc,tv_dydd,tv_dyddl,tv_dylc;
    }
}
