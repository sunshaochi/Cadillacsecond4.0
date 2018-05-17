package cadillac.example.com.cadillac.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import cadillac.example.com.cadillac.R;
import cadillac.example.com.cadillac.bean.DayinfoBean;


/**
 * Created by iris on 2017/12/13.
 */

public class ViewtwoAdpt extends BaseAdapter {


    private Context context;
    private DayinfoBean bean;
    private String type;

    public ViewtwoAdpt(Context context,DayinfoBean bean,String type) {
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

            return bean.getDetailData().size()+1;

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
            view = View.inflate(context, R.layout.item_viewtwo, null);
            hodle=new ViewHodle();
            hodle.ll_itemtwo=(LinearLayout) view.findViewById(R.id.ll_itemtwo);
            hodle.lin=view.findViewById(R.id.view_v);
            hodle.tv_dycx= (TextView) view.findViewById(R.id.tv_dycx);
            hodle.tv_dyxzjd= (TextView) view.findViewById(R.id.tv_dyxzjd);
            hodle.tv_dyecjd= (TextView) view.findViewById(R.id.tv_dyecjd);
            hodle.tv_dyecjdl= (TextView) view.findViewById(R.id.tv_dyecjdl);
            hodle.tv_dyjc= (TextView) view.findViewById(R.id.tv_dyjc);
            hodle.tv_dyddjfl= (TextView) view.findViewById(R.id.tv_dyddjfl);
            hodle.tv_dysjs= (TextView) view.findViewById(R.id.tv_dysjs);
            hodle.tv_dysjl= (TextView) view.findViewById(R.id.tv_dysjl);
            view.setTag(hodle);

        }else {
            hodle= (ViewHodle) view.getTag();
        }
        if(i==0){
            hodle.lin.setVisibility(View.VISIBLE);
            hodle.ll_itemtwo.setBackgroundColor(Color.parseColor("#ececec"));
            hodle.tv_dycx.setText("总计");
            if(type.equals("全部")) {
                hodle.tv_dyxzjd.setText(bean.getTtlData().getTtlIncomeCntALL()+"");//新增加进店
                hodle.tv_dyecjd.setText(bean.getTtlData().getTtlSecondIncomeCntALL()+"");//二次进店
                hodle.tv_dyecjdl.setText(bean.getTtlData().getSecondIncomeRateALL()+"%");//二次进店率
                hodle.tv_dyjc.setText(bean.getTtlData().getTtlDeliveryCntALL()+"");//当月交车
                hodle.tv_dyddjfl.setText(bean.getTtlData().getDeliveryRateALL()+"%");//订单交付率
                hodle.tv_dysjs.setText(bean.getTtlData().getTtlTryDriveCntALL()+"");//试驾数
                hodle.tv_dysjl.setText(bean.getTtlData().getTryDriveRateALL()+"%");//试驾率
            }else if(type.equals("展厅")){
                hodle.tv_dyxzjd.setText(bean.getTtlData().getTtlIncomeCntRoom()+"");//展厅新增加进店
                hodle.tv_dyecjd.setText(bean.getTtlData().getTtlSecondIncomeCntRoom()+"");//展厅二次进店
                hodle.tv_dyecjdl.setText(bean.getTtlData().getSecondIncomeRateRoom()+"%");//展厅二次进店率
                hodle.tv_dyjc.setText(bean.getTtlData().getTtlDeliveryCntRoom()+"");//展厅当月交车
                hodle.tv_dyddjfl.setText(bean.getTtlData().getDeliveryRateRoom()+"%");//订单交付率
                hodle.tv_dysjs.setText(bean.getTtlData().getTtlTryDriveCntRoom()+"");//展厅试驾数
                hodle.tv_dysjl.setText(bean.getTtlData().getTryDriveRateRoom()+"%");//展厅试驾率

            }else if(type.equals("车展")){
                hodle.tv_dyxzjd.setText(bean.getTtlData().getTtlIncomeCntShow()+"");//车展新增加进店
                hodle.tv_dyecjd.setText(bean.getTtlData().getTtlSecondIncomeCntShow()+"");//车展二次进店
                hodle.tv_dyecjdl.setText(bean.getTtlData().getSecondIncomeRateShow()+"%");//车展二次进店率
                hodle.tv_dyjc.setText(bean.getTtlData().getTtlDeliveryCntShow()+"");//车展当月交车
                hodle.tv_dyddjfl.setText(bean.getTtlData().getDeliveryRateShow()+"%");//车展订单交付率
                hodle.tv_dysjs.setText(bean.getTtlData().getTtlTryDriveCntShow()+"");//车展试驾数
                hodle.tv_dysjl.setText(bean.getTtlData().getTryDriveRateShow()+"%");//展厅试驾率
            }
        }else {
            hodle.lin.setVisibility(View.GONE);
            if(i%2==0){
               hodle.ll_itemtwo.setBackgroundColor(Color.parseColor("#ffffff"));
            }else {
                hodle.ll_itemtwo.setBackgroundColor(Color.parseColor("#faf9f9"));
            }
            if(bean.getDetailData()!=null&&bean.getDetailData().size()>0){
                hodle.tv_dycx.setText(bean.getDetailData().get(i-1).getCarModelName());
                if(type.equals("全部")) {
                    hodle.tv_dyxzjd.setText(bean.getDetailData().get(i - 1).getTtlIncomeCntALL()+"");//新增加进店
                    hodle.tv_dyecjd.setText(bean.getDetailData().get(i - 1).getTtlSecondIncomeCntALL()+"");//二次进店
                    hodle.tv_dyecjdl.setText(bean.getDetailData().get(i - 1).getSecondIncomeRateALL()+"%");//二次进店率
                    hodle.tv_dyjc.setText(bean.getDetailData().get(i - 1).getTtlDeliveryCntALL()+"");//当月交车
                    hodle.tv_dyddjfl.setText(bean.getDetailData().get(i - 1).getDeliveryRateALL()+"%");//订单交付率
                    hodle.tv_dysjs.setText(bean.getDetailData().get(i - 1).getTtlTryDriveCntALL()+"");//试驾数
                    hodle.tv_dysjl.setText(bean.getDetailData().get(i - 1).getTryDriveRateALL()+"%");//试驾率
                }else if(type.equals("展厅")){
                    hodle.tv_dyxzjd.setText(bean.getDetailData().get(i - 1).getTtlIncomeCntRoom()+"");//展厅新增加进店
                    hodle.tv_dyecjd.setText(bean.getDetailData().get(i - 1).getTtlSecondIncomeCntRoom()+"");//展厅二次进店
                    hodle.tv_dyecjdl.setText(bean.getDetailData().get(i - 1).getSecondIncomeRateRoom()+"%");//展厅二次进店率
                    hodle.tv_dyjc.setText(bean.getDetailData().get(i - 1).getTtlDeliveryCntRoom()+"");//展厅当月交车
                    hodle.tv_dyddjfl.setText(bean.getDetailData().get(i - 1).getDeliveryRateRoom()+"%");//订单交付率
                    hodle.tv_dysjs.setText(bean.getDetailData().get(i - 1).getTtlTryDriveCntRoom()+"");//展厅试驾数
                    hodle.tv_dysjl.setText(bean.getDetailData().get(i - 1).getTryDriveRateRoom()+"%");//展厅试驾率

                }else if(type.equals("车展")){
                    hodle.tv_dyxzjd.setText(bean.getDetailData().get(i - 1).getTtlIncomeCntShow()+"");//车展新增加进店
                    hodle.tv_dyecjd.setText(bean.getDetailData().get(i - 1).getTtlSecondIncomeCntShow()+"");//车展二次进店
                    hodle.tv_dyecjdl.setText(bean.getDetailData().get(i - 1).getSecondIncomeRateShow()+"%");//车展二次进店率
                    hodle.tv_dyjc.setText(bean.getDetailData().get(i - 1).getTtlDeliveryCntShow()+"");//车展当月交车
                    hodle.tv_dyddjfl.setText(bean.getDetailData().get(i - 1).getDeliveryRateShow()+"%");//车展订单交付率
                    hodle.tv_dysjs.setText(bean.getDetailData().get(i - 1).getTtlTryDriveCntShow()+"");//车展试驾数
                    hodle.tv_dysjl.setText(bean.getDetailData().get(i - 1).getTryDriveRateShow()+"%");//展厅试驾率
                }
                }
        }

        return view;
    }

    class ViewHodle {
        private LinearLayout ll_itemtwo;
        private View lin;
        private TextView tv_dycx,tv_dyxzjd,tv_dyecjd,tv_dyecjdl,tv_dyjc,tv_dyddjfl,tv_dysjs,tv_dysjl;
    }
}
