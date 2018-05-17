package cadillac.example.com.cadillac.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

import cadillac.example.com.cadillac.R;
import cadillac.example.com.cadillac.bean.SalseDateBean;
import cadillac.example.com.cadillac.bean.UserModle;
import cadillac.example.com.cadillac.view.CKITaskSETimeScrollView;

/**
 * Created by bitch-1 on 2017/3/20.
 */
//public class CzAdapter extends BaseAdapter{
//    private Context context;
//    private List<SalseDateBean>list;
//
//    public CzAdapter(Context context,List<SalseDateBean>list) {
//        this.context = context;
//        this.list=list;
//    }
//
//    @Override
//    public int getCount() {
//        return list.size();
//    }
//
//    @Override
//    public Object getItem(int i) {
//        return list.get(i);
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return i;
//    }
//
//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//        ViewHodle hodle;
//        if(view==null){
//            view=View.inflate(context, R.layout.item_czlv,null);
//            hodle=new ViewHodle();
//            hodle.tv_cx= (TextView) view.findViewById(R.id.tv_cx);
//            hodle.et_diandan= (EditText) view.findViewById(R.id.et_dindan);
//            hodle.et_ljdd= (EditText) view.findViewById(R.id.et_ljdd);
//            view.setTag(hodle);
//        }else {
//            hodle= (ViewHodle) view.getTag();
//        }
//        hodle.tv_cx.setText(list.get(i).getModels());//车型
//        hodle.et_diandan.setText(list.get(i).getOrder());//订单
//        hodle.et_ljdd.setText(list.get(i).getRetainOrder());//月累计订单
//        if(UserModle.getUserBean().getRole().equals("经销商销售经理")){
//            hodle.et_diandan.setEnabled(true);//进店数
//
//        }else {
//            hodle.et_diandan.setEnabled(false);//进店数
//        }
//
//        return view;
//    }
//    class ViewHodle{
//        private TextView tv_cx;
//        private EditText et_diandan,et_ljdd;
//
//    }
//}

public class CzAdapter implements CKITaskSETimeScrollView.ScrollAdapter{
    private Context context;
    private List<SalseDateBean>list;

    public CzAdapter(Context context,List<SalseDateBean>list) {
        this.context = context;
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public View getView(int i) {
        View view=View.inflate(context, R.layout.item_czlv,null);
        TextView tv_cx= (TextView) view.findViewById(R.id.tv_cx);
        EditText et_diandan= (EditText) view.findViewById(R.id.et_dindan);
        EditText et_ljdd= (EditText) view.findViewById(R.id.et_ljdd);

        tv_cx.setText(list.get(i).getModels());//车型
        et_diandan.setText(list.get(i).getOrder());//订单
        et_ljdd.setText(list.get(i).getTotalOrder());//月累计订单
        if(UserModle.getUserBean().getRole().equals("经销商销售经理")&&i!=list.size()-1){
           et_diandan.setEnabled(true);//进店数

        }else {
           et_diandan.setEnabled(false);//进店数
        }

        return view;

    }


}
