package cadillac.example.com.cadillac.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cadillac.example.com.cadillac.R;


/**
 * Created by bitch-1 on 2017/4/21.
 */
public class MynoAdapter extends BaseExpandableListAdapter {
    List<String> parent = null;
    Map<String, List<String>> map = null;
    private List<String>list;
    private Context context;
    private int type;

    public MynoAdapter(Context context,List<String> list,int type) {
        this.list = list;
        this.context=context;
        this.type=type;
        parent=new ArrayList<>();
        parent.add("总计");
        map=new HashMap<>();
        map.put("总计",list);
    }

    @Override
    public int getGroupCount() {
        return parent.size();
    }

    @Override
    public int getChildrenCount(int i) {
        String key=parent.get(i);
        return map.get(key).size();
    }

    @Override
    public Object getGroup(int i) {
        return parent.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        String key=parent.get(i);
        return map.get(key).get(i1);
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
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        if(type==1) {
            if (view == null) {
                view = View.inflate(context, R.layout.item_group, null);

            }
            TextView tv_parent = (TextView) view.findViewById(R.id.tv_parent);
            TextView tv_laidian = (TextView) view.findViewById(R.id.tv_laidian);//来电
            TextView tv_jindian = (TextView) view.findViewById(R.id.tv_jindian);//进店
            TextView tv_dingdan = (TextView) view.findViewById(R.id.tv_dindan);//订单
            TextView tv_tuidin = (TextView) view.findViewById(R.id.tv_tuidin);//退订
            TextView tv_jiaoche = (TextView) view.findViewById(R.id.tv_jiaoche);//交车
            TextView tv_nj = (TextView) view.findViewById(R.id.tv_nj);//累计
            TextView tv_lc = (TextView) view.findViewById(R.id.tv_lc);//留存

            tv_parent.setText(parent.get(i));
            tv_laidian.setText("0");
            tv_jindian.setText("0");
            tv_dingdan.setText("0");
            tv_tuidin.setText("0");
            tv_jiaoche.setText("0");
            tv_nj.setText("0");
            tv_lc.setText("0");
        }else {
            if(view==null){
                view=View.inflate(context,R.layout.item_czgroup,null);

            }
            TextView tv_parent= (TextView) view.findViewById(R.id.tv_cz);//车型
            TextView tv_zh=(TextView) view.findViewById(R.id.editText);//转换率
            TextView tv_cj=(TextView) view.findViewById(R.id.tv_cj);//成交

            tv_parent.setText(parent.get(i));
            tv_zh.setText("0.0%");
            tv_cj.setText("0.0%");

        }
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        if(type==1) {
            if (view == null) {
                view = View.inflate(context, R.layout.item_child, null);
            }
            TextView tv_child = (TextView) view.findViewById(R.id.tv_chexin);
            TextView tv_laidian = (TextView) view.findViewById(R.id.tv_laidian);
            TextView tv_jindian = (TextView) view.findViewById(R.id.tv_jindian);
            TextView tv_oreder = (TextView) view.findViewById(R.id.tv_dindan);
            TextView tv_tuidin = (TextView) view.findViewById(R.id.tv_tuidin);
            TextView tv_jiaoc = (TextView) view.findViewById(R.id.tv_jiaoche);
            TextView tv_nlj = (TextView) view.findViewById(R.id.tv_ylj);
            TextView tv_lc = (TextView) view.findViewById(R.id.tv_lc);
            String key = parent.get(i);
            String info = map.get(key).get(i1);//车型
            tv_child.setText(info);
            tv_laidian.setText("0");//来电
            tv_jindian.setText("0");//进店
            tv_oreder.setText("0");//订单
            tv_tuidin.setText("0");//退订
            tv_jiaoc.setText("0");//交车
            tv_nlj.setText("0");//累计订单
            tv_lc.setText("0");//留存
        }else {
            if(view==null){
                view=View.inflate(context,R.layout.item_czchild,null);
            }
            TextView tv_child= (TextView) view.findViewById(R.id.tv_cz);//车型
            TextView tv_zh= (TextView) view.findViewById(R.id.tv_dindan);//转换率
            TextView tv_cj= (TextView) view.findViewById(R.id.tv_ljdd);//成交率

            String key=parent.get(i);
            String info= map.get(key).get(i1);//车型
            tv_child.setText(info);
            tv_zh.setText("0.0%");//转换率
            tv_cj.setText("0.0%");//成交率
        }
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
