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

public class ViewthreeAdpt extends BaseAdapter {
    private Context context;
    private DayinfoBean bean;
    private String type;

    public ViewthreeAdpt(Context context,DayinfoBean bean,String type) {
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
            view = View.inflate(context, R.layout.item_viewthree, null);
            hodle=new ViewHodle();
            hodle.ll_itemthree=(LinearLayout)view.findViewById(R.id.ll_itemthree);
            hodle.lin=view.findViewById(R.id.view_v);
            hodle.tv_ycx= (TextView) view.findViewById(R.id.tv_ycx);
            hodle.tv_ytd= (TextView) view.findViewById(R.id.tv_ytd);
            hodle.tv_ytdl= (TextView) view.findViewById(R.id.tv_ytdl);
            hodle.tv_yxxscsz= (TextView) view.findViewById(R.id.tv_yxxscsz);
            hodle.tv_yxxscl= (TextView) view.findViewById(R.id.tv_yxxscl);
            hodle.tv_ywjtx= (TextView) view.findViewById(R.id.tv_ywjtx);
            hodle.tv_ywjtxl= (TextView) view.findViewById(R.id.tv_ywjtxl);
            view.setTag(hodle);
        }else {
            hodle= (ViewHodle) view.getTag();
        }

        if(i==0){
            hodle.lin.setVisibility(View.VISIBLE);
            hodle.ll_itemthree.setBackgroundColor(Color.parseColor("#ececec"));
            hodle.tv_ycx.setText("总计");
            if(type.equals("全部")) {
                hodle.tv_ytd.setText(bean.getTtlData().getTtlCancelOrderCntALL()+"");//退订
                hodle.tv_ytdl.setText(bean.getTtlData().getCancelOrderRateALL()+"%");//退订lv
                hodle.tv_yxxscsz.setText(bean.getTtlData().getTtlInfoUploadCntALL()+"");//上传数
                hodle.tv_yxxscl.setText(bean.getTtlData().getInfoUploadRateALL()+"%");//上传率
                hodle.tv_ywjtx.setText(bean.getTtlData().getTtlQuestionCntALL()+"");//问卷填写
                hodle.tv_ywjtxl.setText(bean.getTtlData().getQuestionRateALL()+"%");//退订
            }else if(type.equals("展厅")){
                hodle.tv_ytd.setText(bean.getTtlData().getTtlCancelOrderCntRoom()+"");//退订
                hodle.tv_ytdl.setText(bean.getTtlData().getCancelOrderRateRoom()+"%");//退订lv
                hodle.tv_yxxscsz.setText(bean.getTtlData().getTtlInfoUploadCntRoom()+"");//上传数
                hodle.tv_yxxscl.setText(bean.getTtlData().getInfoUploadRateRoom()+"%");//上传率
                hodle.tv_ywjtx.setText(bean.getTtlData().getTtlQuestionCntRoom()+"");//问卷填写
                hodle.tv_ywjtxl.setText(bean.getTtlData().getQuestionRateRoom()+"%");//填写率
            }else if(type.equals("车展")){
                hodle.tv_ytd.setText(bean.getTtlData().getTtlCancelOrderCntShow()+"");//退订
                hodle.tv_ytdl.setText(bean.getTtlData().getCancelOrderRateShow()+"%");//退订lv
                hodle.tv_yxxscsz.setText(bean.getTtlData().getTtlInfoUploadCntShow()+"");//上传数
                hodle.tv_yxxscl.setText(bean.getTtlData().getInfoUploadRateShow()+"%");//上传率
                hodle.tv_ywjtx.setText(bean.getTtlData().getTtlQuestionCntShow()+"");//问卷填写
                hodle.tv_ywjtxl.setText(bean.getTtlData().getQuestionRateShow()+"%");//填写率
            }
        }else {
            hodle.lin.setVisibility(View.GONE);
            if(i%2==0){
                hodle.ll_itemthree.setBackgroundColor(Color.parseColor("#ffffff"));
            }else{
                hodle.ll_itemthree.setBackgroundColor(Color.parseColor("#faf9f9"));
            }
            if(bean!=null&&bean.getDetailData().size()>0){
                hodle.tv_ycx.setText(bean.getDetailData().get(i-1).getCarModelName());
                if(type.equals("全部")) {
                    hodle.tv_ytd.setText(bean.getDetailData().get(i - 1).getTtlCancelOrderCntALL()+"");//退订
                    hodle.tv_ytdl.setText(bean.getDetailData().get(i - 1).getCancelOrderRateALL()+"%");//退订lv
                    hodle.tv_yxxscsz.setText(bean.getDetailData().get(i - 1).getTtlInfoUploadCntALL()+"");//上传数
                    hodle.tv_yxxscl.setText(bean.getDetailData().get(i - 1).getInfoUploadRateALL()+"%");//上传率
                    hodle.tv_ywjtx.setText(bean.getDetailData().get(i - 1).getTtlQuestionCntALL()+"");//问卷填写
                    hodle.tv_ywjtxl.setText(bean.getDetailData().get(i - 1).getQuestionRateALL()+"%");//问卷填写率
                }else if(type.equals("展厅")){
                    hodle.tv_ytd.setText(bean.getDetailData().get(i-1).getTtlCancelOrderCntRoom()+"");//退订
                    hodle.tv_ytdl.setText(bean.getDetailData().get(i-1).getCancelOrderRateRoom()+"%");//退订lv
                    hodle.tv_yxxscsz.setText(bean.getDetailData().get(i-1).getTtlInfoUploadCntRoom()+"");//上传数
                    hodle.tv_yxxscl.setText(bean.getDetailData().get(i-1).getInfoUploadRateRoom()+"%");//上传率
                    hodle.tv_ywjtx.setText(bean.getDetailData().get(i-1).getTtlQuestionCntRoom()+"");//问卷填写
                    hodle.tv_ywjtxl.setText(bean.getDetailData().get(i-1).getQuestionRateRoom()+"%");//填写率
                }else if(type.equals("车展")){
                    hodle.tv_ytd.setText(bean.getDetailData().get(i-1).getTtlCancelOrderCntShow()+"");//退订
                    hodle.tv_ytdl.setText(bean.getDetailData().get(i-1).getCancelOrderRateShow()+"%");//退订lv
                    hodle.tv_yxxscsz.setText(bean.getDetailData().get(i-1).getTtlInfoUploadCntShow()+"");//上传数
                    hodle.tv_yxxscl.setText(bean.getDetailData().get(i-1).getInfoUploadRateShow()+"%");//上传率
                    hodle.tv_ywjtx.setText(bean.getDetailData().get(i-1).getTtlQuestionCntShow()+"");//问卷填写
                    hodle.tv_ywjtxl.setText(bean.getDetailData().get(i-1).getQuestionRateShow()+"%");//填写率
                }
            }
        }

        return view;
    }

    class ViewHodle {
        private LinearLayout ll_itemthree;
        private View lin;
        private TextView tv_ycx,tv_ytd,tv_ytdl,tv_yxxscsz,tv_yxxscl,tv_ywjtx,tv_ywjtxl;
    }
}
