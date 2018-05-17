package cadillac.example.com.cadillac.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import cadillac.example.com.cadillac.R;

/**
 * Created by bitch-1 on 2017/5/23.
 */

public class FirstAdap extends BaseAdapter {
    private Context context;
    private int clickTemp = -1;//标识选择的Item

    public FirstAdap(Context context) {
        this.context = context;
    }

    public void setSeclection(int position) {
        clickTemp = position;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return 2;
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
        view=View.inflate(context, R.layout.item_city,null);
        TextView tv_name= (TextView) view.findViewById(R.id.tv_name);
        if(clickTemp==i){
         tv_name.setTextColor(Color.parseColor("#990000"));
         tv_name.setBackgroundColor(Color.parseColor("#ffffff"));
        }else {
            tv_name.setTextColor(Color.parseColor("#333333"));
            tv_name.setBackgroundColor(Color.parseColor("#f4f4f4"));
        }
        return view;
    }
}
