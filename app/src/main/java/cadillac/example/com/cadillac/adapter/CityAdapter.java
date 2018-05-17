package cadillac.example.com.cadillac.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cadillac.example.com.cadillac.R;

/**
 * Created by bitch-1 on 2017/5/23.
 */

public class CityAdapter extends BaseAdapter {
    private int clickTemp=-1;
    private Context context;
    private List<String>list;

    public CityAdapter(Context context,List<String>list) {
        this.context = context;
        this.list=list;
    }

    public void setSeclection(int position) {
        clickTemp = position;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view=View.inflate(context, R.layout.item_city,null);
        TextView tv_name= (TextView) view.findViewById(R.id.tv_name);
        tv_name.setText(list.get(i));
        if(clickTemp==i){
            tv_name.setTextColor(Color.parseColor("#990000"));
        }else {
            tv_name.setTextColor(Color.parseColor("#333333"));
        }
        return view;
    }
}
