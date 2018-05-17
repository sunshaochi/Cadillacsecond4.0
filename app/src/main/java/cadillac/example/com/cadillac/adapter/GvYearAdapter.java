package cadillac.example.com.cadillac.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import cadillac.example.com.cadillac.R;
import cadillac.example.com.cadillac.activity.StatDialogAct;
import cadillac.example.com.cadillac.bean.Reportbean;
import cadillac.example.com.cadillac.bean.StateBean;
import cadillac.example.com.cadillac.utils.SpUtils;

/**
 * Created by bitch-1 on 2017/3/27.
 */
public class GvYearAdapter extends BaseAdapter {
    private Context context;
    private List<StateBean>list;


    public GvYearAdapter(Context context,List<StateBean>list) {
        this.context = context;
        this.list=list;
    }

    public void setChange(List<StateBean> list) {
        this.list=list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return 4;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view=View.inflate(context, R.layout.item_gvyear,null);
        final TextView tv_yyear= (TextView) view.findViewById(R.id.tv_yyear);
        TextView tv_yroom= (TextView) view.findViewById(R.id.tv_yroom);
        TextView tv_yorder= (TextView) view.findViewById(R.id.tv_yorder);
        LinearLayout ll_year= (LinearLayout) view.findViewById(R.id.ll_year);
        if(i==0) {
            tv_yyear.setText(Integer.parseInt(SpUtils.getThreeTime(context))-3+"");
        }else
        if(i==1) {
            tv_yyear.setText(Integer.parseInt(SpUtils.getThreeTime(context))-2+"");
        }else
        if(i==2) {
            tv_yyear.setText(Integer.parseInt(SpUtils.getThreeTime(context))-1+"");
        }else
        if(i==3) {
            tv_yyear.setText(SpUtils.getThreeTime(context));
        }
        tv_yorder.setText("-");
        tv_yroom.setText("-");
        if(list!=null&&list.size()>0){
            for(int j=0;j<list.size();j++){
                if(tv_yyear.getText().equals(list.get(j).getDateKey())){
                    tv_yroom.setText(list.get(j).getTopShow());
                    tv_yorder.setText(list.get(j).getButtonShow());
                }
            }

        }else {
            tv_yroom.setText("-");
            tv_yorder.setText("-");
        }

        ll_year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, StatDialogAct.class);
                int time=Integer.parseInt(SpUtils.getThreeTime(context));
                if(i==0){
                    intent.putExtra("time",time-3+"-01-01");
                    intent.putExtra("biaoti",time-3+"");

                }
                if(i==1){
                    intent.putExtra("time",time-2+"-01-01");
                    intent.putExtra("biaoti",time-2+"");
                }
                if(i==2){
                    intent.putExtra("time",time-1+"-01-01");
                    intent.putExtra("biaoti",time-1+"");

                }
                if(i==3){
                    intent.putExtra("time",time+"-01-01");
                    intent.putExtra("biaoti",time+"");

                }
                intent.putExtra("queryType", "2");//年
               context.startActivity(intent);

            }
        });


//        if(i==0){
//            tv_yyear.setText(String.valueOf(year));
//        }else if(i==1){
//            tv_yyear.setText(String.valueOf(year+1));
//        }else if(i==2){
//            tv_yyear.setText(String.valueOf(year+2));
//        }else if(i==3){
//            tv_yyear.setText(String.valueOf(year+3));
//        }
//        if(i<list.get(0).getInRooms().size()){
//            tv_yroom.setText(list.get(0).getInRooms().get(i));
//            tv_yorder.setText(list.get(0).getOrders().get(i));
//
//        }else {
//            tv_yroom.setText("-");
//            tv_yorder.setText("-");
//        }
//        if(tv_yroom.getText().equals("-")){
//            ll_year.setEnabled(false);
//        }else {
//            ll_year.setEnabled(true);
//            ll_year.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent=new Intent(context, StatDialogAct.class);
//                    intent.putExtra("time",tv_yyear.getText().toString());
//                    intent.putExtra("Enter","年");
//                    context.startActivity(intent);
//                }
//            });
//        }
        return view;
    }


}
