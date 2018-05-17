package cadillac.example.com.cadillac.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import cadillac.example.com.cadillac.R;
import cadillac.example.com.cadillac.activity.StatDialogAct;
import cadillac.example.com.cadillac.base.BaseActivity;
import cadillac.example.com.cadillac.bean.Reportbean;
import cadillac.example.com.cadillac.bean.StateBean;
import cadillac.example.com.cadillac.utils.CadillacUtils;
import cadillac.example.com.cadillac.utils.MyToastUtils;
import cadillac.example.com.cadillac.utils.SpUtils;

/**
 * Created by bitch-1 on 2017/3/25.
 */
public class GvAdpter extends BaseAdapter{
    private Context context;
    private List<StateBean> list;
    private String time;//用来传递到activity中的年
    private String yueli[]={"Jan","Feb","Mar","Q1","Apr","May","Jun","Q2","Jul","Aug","Sep","Q3","Oct","Nov","Dec","Q4"};
    public GvAdpter(Context context,List<StateBean>list) {
        this.context = context;
        this.list=list;

    }


    public void setChange(List<StateBean>list){
        this.list=list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return yueli.length;
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
        view=View.inflate(context, R.layout.item_gvmonth,null);
        TextView tv_month= (TextView) view.findViewById(R.id.tv_month);
        TextView tv_room= (TextView) view.findViewById(R.id.tv_room);
        TextView tv_order= (TextView) view.findViewById(R.id.tv_order);
        LinearLayout ll_view= (LinearLayout) view.findViewById(R.id.ll_view);

        /** 给全部的赋值**/
        tv_month.setText(yueli[i]);
        tv_room.setText("-");
        tv_order.setText("-");

        if(i==3||i==7||i==11||i==15){
            tv_month.setBackgroundColor(Color.parseColor("#8a152b"));
        }else {
            tv_month.setBackgroundColor(Color.parseColor("#979797"));

        }

        if(list!=null&&list.size()>0){
            for(int j=0;j<list.size();j++){
                if(tv_month.getText().equals(CadillacUtils.monthFromat(list.get(j).getDateKey()))){
                    if(SpUtils.getTbOrHb(context).equals("-1")) {//表示没有同比环比
                        tv_room.setText(list.get(j).getTopShow());
                        tv_order.setText(list.get(j).getButtonShow());
                    }else {
                        tv_room.setText(list.get(j).getTopShow()+"%");
                        tv_order.setText(list.get(j).getButtonShow());
                    }
                }
            }

        }else {
            tv_room.setText("-");
            tv_order.setText("-");
        }

        ll_view.setOnClickListener(new View.OnClickListener() {
            Intent intent = new Intent(context, StatDialogAct.class);
            @Override
            public void onClick(View view) {
                if(i==3){//第一季度
                    intent.putExtra("time", SpUtils.getTwoTime(context)+"-03-01");
                    intent.putExtra("queryType", "3");
                    intent.putExtra("biaoti",SpUtils.getTwoTime(context)+"第一季度");
                }else if(i==7){//第二季度
                    intent.putExtra("time", SpUtils.getTwoTime(context)+"-06-01");
                    intent.putExtra("queryType", "3");
                    intent.putExtra("biaoti",SpUtils.getTwoTime(context)+"第二季度");

                }else if(i==11){//第三季度
                    intent.putExtra("time", SpUtils.getTwoTime(context)+"-09-01");
                    intent.putExtra("queryType", "3");
                    intent.putExtra("biaoti",SpUtils.getTwoTime(context)+"第三季度");
                }else if(i==15){//第四季度
                    intent.putExtra("time", SpUtils.getTwoTime(context)+"-12-01");
                    intent.putExtra("queryType", "3");
                    intent.putExtra("biaoti",SpUtils.getTwoTime(context)+"第四季度");
                }else {
                    if(i==0) {
                        intent.putExtra("time", SpUtils.getTwoTime(context) +"-01-01");
                        intent.putExtra("biaoti",SpUtils.getTwoTime(context)+"一月");
                    }
                    if(i==1){
                        intent.putExtra("time", SpUtils.getTwoTime(context) + "-02-01");
                        intent.putExtra("biaoti",SpUtils.getTwoTime(context)+"二月");

                    }
                    if(i==2){
                        intent.putExtra("time", SpUtils.getTwoTime(context) + "-03-01");
                        intent.putExtra("biaoti",SpUtils.getTwoTime(context)+"三月");

                    }
                    if(i==4){
                        intent.putExtra("time", SpUtils.getTwoTime(context) + "-04-01");
                        intent.putExtra("biaoti",SpUtils.getTwoTime(context)+"四月");

                    }
                    if(i==5){
                        intent.putExtra("time", SpUtils.getTwoTime(context) + "-05-01");
                        intent.putExtra("biaoti",SpUtils.getTwoTime(context)+"五月");

                    }
                    if(i==6){
                        intent.putExtra("time", SpUtils.getTwoTime(context) + "-06-01");
                        intent.putExtra("biaoti",SpUtils.getTwoTime(context)+"六月");

                    }
                    if(i==8){
                        intent.putExtra("time", SpUtils.getTwoTime(context) + "-07-01");
                        intent.putExtra("biaoti",SpUtils.getTwoTime(context)+"七月");

                    }
                    if(i==9){
                        intent.putExtra("time", SpUtils.getTwoTime(context) + "-08-01");
                        intent.putExtra("biaoti",SpUtils.getTwoTime(context)+"八月");

                    }
                    if(i==10){
                        intent.putExtra("time", SpUtils.getTwoTime(context) + "-09-01");
                        intent.putExtra("biaoti",SpUtils.getTwoTime(context)+"九月");

                    }
                    if(i==12){
                        intent.putExtra("time", SpUtils.getTwoTime(context) + "-10-01");
                        intent.putExtra("biaoti",SpUtils.getTwoTime(context)+"十月");

                    }
                    if(i==13){
                        intent.putExtra("time", SpUtils.getTwoTime(context) + "-11-01");
                        intent.putExtra("biaoti",SpUtils.getTwoTime(context)+"十一月");

                    }
                    if(i==14){
                        intent.putExtra("time", SpUtils.getTwoTime(context) + "-12-01");
                        intent.putExtra("biaoti",SpUtils.getTwoTime(context)+"十二月");
                    }
                    intent.putExtra("queryType", "1");
                }
               context.startActivity(intent);
            }
        });

//        if(list!=null&&list.size()>0){
//            tv_room.setText("-");
//            tv_order.setText("-");
//        }
//        if(i<list.get(0).getInRooms().size()) {
//            tv_room.setText(list.get(0).getInRooms().get(i));
//            tv_order.setText(list.get(0).getOrders().get(i));
//        }else {
//            tv_room.setText("-");
//            tv_order.setText("-");
//        }
//        //跳掉 Q1 Q2 Q3 Q4 赋值
//        if(i==4&&list.get(0).getInRooms().size()>3){
//            tv_room.setText(list.get(0).getInRooms().get(3));
//            tv_order.setText(list.get(0).getOrders().get(3));
//
//        }
//        if(i==5&&list.get(0).getInRooms().size()>4){
//            tv_room.setText(list.get(0).getInRooms().get(4));
//            tv_order.setText(list.get(0).getOrders().get(4));
//
//        }
//        if(i==6&&list.get(0).getInRooms().size()>5){
//            tv_room.setText(list.get(0).getInRooms().get(5));
//            tv_order.setText(list.get(0).getOrders().get(5));
//
//        }
//
////        if(i==7&&list.get(0).getInRooms().size()>6){
////            tv_room.setText(list.get(0).getInRooms().get(6));
////        }
//        if(i==8&&list.get(0).getInRooms().size()>6){
//            tv_room.setText(list.get(0).getInRooms().get(6));
//            tv_order.setText(list.get(0).getOrders().get(6));
//
//        }
//        if(i==9&&list.get(0).getInRooms().size()>7){
//            tv_room.setText(list.get(0).getInRooms().get(7));
//            tv_order.setText(list.get(0).getOrders().get(7));
//
//        }
//        if(i==10&&list.get(0).getInRooms().size()>8){
//            tv_room.setText(list.get(0).getInRooms().get(8));
//            tv_order.setText(list.get(0).getOrders().get(8));
//
//        }
//        if(i==12&&list.get(0).getInRooms().size()>9) {
//            tv_room.setText(list.get(0).getInRooms().get(9));
//            tv_order.setText(list.get(0).getOrders().get(9));
//        }
//        if(i==13&&list.get(0).getInRooms().size()>10) {
//            tv_room.setText(list.get(0).getInRooms().get(10));
//            tv_order.setText(list.get(0).getOrders().get(10));
//        }
//        if(i==14&&list.get(0).getInRooms().size()>11) {
//            tv_room.setText(list.get(0).getInRooms().get(11));
//            tv_order.setText(list.get(0).getOrders().get(11));
//
//        }
//        //给Q1 Q2 Q3 Q4 赋值
//
//        /** 一季度**/
//        if(list.get(0).getInRooms().size()==1){
//            if(i==3){
//                tv_room.setText(list.get(0).getInRooms().get(0));
//                tv_order.setText(list.get(0).getOrders().get(0));
//            }
//        }
//        if(list.get(0).getInRooms().size()==2){
//            if(i==3){
//                tv_room.setText(add(list.get(0).getInRooms().get(0),list.get(0).getInRooms().get(1),"0"));
//                tv_order.setText(add(list.get(0).getOrders().get(0),list.get(0).getOrders().get(1),"0"));
//            }
//        }
//
//        if(list.get(0).getInRooms().size()==3){
//            if(i==3){
//                tv_room.setText(add(list.get(0).getInRooms().get(0),list.get(0).getInRooms().get(1),list.get(0).getInRooms().get(2)));
//                tv_order.setText(add(list.get(0).getOrders().get(0),list.get(0).getOrders().get(1),list.get(0).getOrders().get(2)));
//            }
//        }
//
//        /** 2季度**/
//        if(list.get(0).getInRooms().size()==4){
//            if(i==3){
//                tv_room.setText(add(list.get(0).getInRooms().get(0),list.get(0).getInRooms().get(1),list.get(0).getInRooms().get(2)));
//                tv_order.setText(add(list.get(0).getOrders().get(0),list.get(0).getOrders().get(1),list.get(0).getOrders().get(2)));
//            }
//            if(i==7){
//                tv_room.setText(list.get(0).getInRooms().get(3));
//                tv_order.setText(list.get(0).getOrders().get(3));
//            }
//        }
//        if(list.get(0).getInRooms().size()==5){
//            if(i==3){
//                tv_room.setText(add(list.get(0).getInRooms().get(0),list.get(0).getInRooms().get(1),list.get(0).getInRooms().get(2)));
//                tv_order.setText(add(list.get(0).getOrders().get(0),list.get(0).getOrders().get(1),list.get(0).getOrders().get(2)));
//            }
//            if(i==7){
//
//                tv_room.setText(add(list.get(0).getInRooms().get(3),list.get(0).getInRooms().get(4),"0"));
//                tv_order.setText(add(list.get(0).getOrders().get(3),list.get(0).getOrders().get(4),"0"));
//
//            }
//        }
//
//        if(list.get(0).getInRooms().size()==6){
//            if(i==3){
//                tv_room.setText(add(list.get(0).getInRooms().get(0),list.get(0).getInRooms().get(1),list.get(0).getInRooms().get(2)));
//                tv_order.setText(add(list.get(0).getOrders().get(0),list.get(0).getOrders().get(1),list.get(0).getOrders().get(2)));
//            }
//            if(i==7){
//                tv_room.setText(add(list.get(0).getInRooms().get(3),list.get(0).getInRooms().get(4),list.get(0).getInRooms().get(5)));
//                tv_order.setText(add(list.get(0).getOrders().get(3),list.get(0).getOrders().get(4),list.get(0).getOrders().get(5)));
//            }
//        }
//
//        /** 3季度**/
//        if(list.get(0).getInRooms().size()==7){
//            if(i==3){
//                tv_room.setText(add(list.get(0).getInRooms().get(0),list.get(0).getInRooms().get(1),list.get(0).getInRooms().get(2)));
//                tv_order.setText(add(list.get(0).getOrders().get(0),list.get(0).getOrders().get(1),list.get(0).getOrders().get(2)));
//            }
//            if(i==7){
//                tv_room.setText(add(list.get(0).getInRooms().get(3),list.get(0).getInRooms().get(4),list.get(0).getInRooms().get(5)));
//                tv_order.setText(add(list.get(0).getOrders().get(3),list.get(0).getOrders().get(4),list.get(0).getOrders().get(5)));
//            }
//            if(i==11){
//                tv_room.setText(list.get(0).getInRooms().get(6));
//                tv_order.setText(list.get(0).getOrders().get(6));
//            }
//        }
//        if(list.get(0).getInRooms().size()==8){
//            if(i==3){
//                tv_room.setText(add(list.get(0).getInRooms().get(0),list.get(0).getInRooms().get(1),list.get(0).getInRooms().get(2)));
//                tv_order.setText(add(list.get(0).getOrders().get(0),list.get(0).getOrders().get(1),list.get(0).getOrders().get(2)));
//            }
//            if(i==7){
//                tv_room.setText(add(list.get(0).getInRooms().get(3),list.get(0).getInRooms().get(4),list.get(0).getInRooms().get(5)));
//                tv_order.setText(add(list.get(0).getOrders().get(3),list.get(0).getOrders().get(4),list.get(0).getOrders().get(5)));
//            }
//            if(i==11){
//                tv_room.setText(add(list.get(0).getInRooms().get(6),list.get(0).getInRooms().get(7),"0"));
//                tv_order.setText(add(list.get(0).getOrders().get(6),list.get(0).getOrders().get(7),"0"));
//            }
//        }
//
//        if(list.get(0).getInRooms().size()==9){
//            if(i==3){
//                tv_room.setText(add(list.get(0).getInRooms().get(0),list.get(0).getInRooms().get(1),list.get(0).getInRooms().get(2)));
//                tv_order.setText(add(list.get(0).getOrders().get(0),list.get(0).getOrders().get(1),list.get(0).getOrders().get(2)));
//            }
//            if(i==7){
//                tv_room.setText(add(list.get(0).getInRooms().get(3),list.get(0).getInRooms().get(4),list.get(0).getInRooms().get(5)));
//                tv_order.setText(add(list.get(0).getOrders().get(3),list.get(0).getOrders().get(4),list.get(0).getOrders().get(5)));
//            }
//            if(i==11){
//                tv_room.setText(add(list.get(0).getInRooms().get(6),list.get(0).getInRooms().get(7),list.get(0).getInRooms().get(8)));
//                tv_order.setText(add(list.get(0).getOrders().get(6),list.get(0).getOrders().get(7),list.get(0).getOrders().get(8)));
//            }
//        }
//        /** 4季度**/
//        if(list.get(0).getInRooms().size()==10){
//            if(i==3){
//                tv_room.setText(add(list.get(0).getInRooms().get(0),list.get(0).getInRooms().get(1),list.get(0).getInRooms().get(2)));
//                tv_order.setText(add(list.get(0).getOrders().get(0),list.get(0).getOrders().get(1),list.get(0).getOrders().get(2)));
//            }
//            if(i==7){
//                tv_room.setText(add(list.get(0).getInRooms().get(3),list.get(0).getInRooms().get(4),list.get(0).getInRooms().get(5)));
//                tv_order.setText(add(list.get(0).getOrders().get(3),list.get(0).getOrders().get(4),list.get(0).getOrders().get(5)));
//            }
//            if(i==11){
//                tv_room.setText(add(list.get(0).getInRooms().get(6),list.get(0).getInRooms().get(7),list.get(0).getInRooms().get(8)));
//                tv_order.setText(add(list.get(0).getOrders().get(6),list.get(0).getOrders().get(7),list.get(0).getOrders().get(8)));
//            }
//            if(i==15){
//                tv_room.setText(list.get(0).getInRooms().get(9));
//                tv_order.setText(list.get(0).getOrders().get(9));
//            }
//        }
//        if(list.get(0).getInRooms().size()==11){
//            if(i==3){
//                tv_room.setText(add(list.get(0).getInRooms().get(0),list.get(0).getInRooms().get(1),list.get(0).getInRooms().get(2)));
//                tv_order.setText(add(list.get(0).getOrders().get(0),list.get(0).getOrders().get(1),list.get(0).getOrders().get(2)));
//            }
//            if(i==7){
//                tv_room.setText(add(list.get(0).getInRooms().get(3),list.get(0).getInRooms().get(4),list.get(0).getInRooms().get(5)));
//                tv_order.setText(add(list.get(0).getOrders().get(3),list.get(0).getOrders().get(4),list.get(0).getOrders().get(5)));
//            }
//            if(i==11){
//                tv_room.setText(add(list.get(0).getInRooms().get(6),list.get(0).getInRooms().get(7),list.get(0).getInRooms().get(8)));
//                tv_order.setText(add(list.get(0).getOrders().get(6),list.get(0).getOrders().get(7),list.get(0).getOrders().get(8)));
//            }
//            if(i==15){
//
//                tv_room.setText(add(list.get(0).getInRooms().get(9),list.get(0).getInRooms().get(10),"0"));
//                tv_order.setText(add(list.get(0).getOrders().get(9),list.get(0).getOrders().get(10),"0"));
//            }
//        }
//
//        if(list.get(0).getInRooms().size()==12){
//            if(i==3){
//                tv_room.setText(add(list.get(0).getInRooms().get(0),list.get(0).getInRooms().get(1),list.get(0).getInRooms().get(2)));
//                tv_order.setText(add(list.get(0).getOrders().get(0),list.get(0).getOrders().get(1),list.get(0).getOrders().get(2)));
//            }
//            if(i==7){
//                tv_room.setText(add(list.get(0).getInRooms().get(3),list.get(0).getInRooms().get(4),list.get(0).getInRooms().get(5)));
//                tv_order.setText(add(list.get(0).getOrders().get(3),list.get(0).getOrders().get(4),list.get(0).getOrders().get(5)));
//            }
//            if(i==11){
//                tv_room.setText(add(list.get(0).getInRooms().get(6),list.get(0).getInRooms().get(7),list.get(0).getInRooms().get(8)));
//                tv_order.setText(add(list.get(0).getOrders().get(6),list.get(0).getOrders().get(7),list.get(0).getOrders().get(8)));
//            }
//            if(i==15){
//                tv_room.setText(add(list.get(0).getInRooms().get(9),list.get(0).getInRooms().get(10),list.get(0).getInRooms().get(11)));
//                tv_order.setText(add(list.get(0).getOrders().get(9),list.get(0).getOrders().get(10),list.get(0).getOrders().get(11)));
//            }
//        }
//        /** 设置是否可点击**/
//        if(tv_room.getText().equals("-")){
//            ll_view.setEnabled(false);
//        }else {
//            ll_view.setEnabled(true);
//            ll_view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent=new Intent(context, StatDialogAct.class);
//                    intent.putExtra("Enter","月");//用来在详情界面区分从日月年哪一个进来
//                    switch (i){
//                        case 0:
//                            intent.putExtra("time",time+"-01");
//                            context.startActivity(intent);
//                            break;
//
//                        case 1:
//                            intent.putExtra("time",time+"-02");
//                            context.startActivity(intent);
//                            break;
//
//                        case 2:
//                            intent.putExtra("time",time+"-03");
//                            context.startActivity(intent);
//                            break;
//
//                        case 3:
//                            intent.putExtra("time",time+" 第一季度");
//                            context.startActivity(intent);
//                            break;
//
//                        case 4:
//                            intent.putExtra("time",time+"-04");
//                            context.startActivity(intent);
//                            break;
//
//                        case 5:
//                            intent.putExtra("time",time+"-05");
//                            context.startActivity(intent);
//                            break;
//
//                        case 6:
//                            intent.putExtra("time",time+"-06");
//                            context.startActivity(intent);
//                            break;
//
//                        case 7:
//                            intent.putExtra("time",time+" 第二季度");
//                            context.startActivity(intent);
//                            break;
//
//                        case 8:
//                            intent.putExtra("time",time+"-07");
//                            context.startActivity(intent);
//                            break;
//
//                        case 9:
//                            intent.putExtra("time",time+"-08");
//                            context.startActivity(intent);
//                            break;
//                        case 10:
//                            intent.putExtra("time",time+"-09");
//                            context.startActivity(intent);
//                            break;
//                        case 11:
//                            intent.putExtra("time",time+" 第三季度");
//                            context.startActivity(intent);
//                            break;
//                        case 12:
//                            intent.putExtra("time",time+"-10");
//                            context.startActivity(intent);
//                            break;
//                        case 13:
//                            intent.putExtra("time",time+"-11");
//                            context.startActivity(intent);
//                            break;
//                        case 14:
//                            intent.putExtra("time",time+"-12");
//                            context.startActivity(intent);
//                            break;
//                        case 15:
//                            intent.putExtra("time",time+" 第四季度");
//                            context.startActivity(intent);
//                            break;
//                    }
//                }
//            });
//        }
//
//        if(i==3||i==7||i==11||i==15){
//            ll_view.setBackgroundResource(R.mipmap.jili);
//        }else {
//            ll_view.setBackgroundResource(R.mipmap.month);
//        }

        return view;
    }

    /**
     * 转换成int相加
     */
//    private String add(String first,String second,String third) {
//        int yroom=Integer.parseInt(first);
//        int yorder=Integer.parseInt(second);
//        int ythird=Integer.parseInt(third);
//        return (yorder+yroom+ythird)+"";
//    }
}
