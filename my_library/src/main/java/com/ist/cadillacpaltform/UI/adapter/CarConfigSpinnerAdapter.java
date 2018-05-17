package com.ist.cadillacpaltform.UI.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ist.cadillacpaltform.R;
import com.ist.cadillacpaltform.SDK.bean.HighStockAge.CarConfig;

import java.util.List;

/**
 * Created by czh on 2017/1/5.
 */

public class CarConfigSpinnerAdapter extends BaseAdapter {
    private Context mContext;

    public List<CarConfig> getmCarConfigList() {
        return mCarConfigList;
    }

    public void setmCarConfigList(List<CarConfig> mCarConfigList) {
        this.mCarConfigList = mCarConfigList;
    }

    private List<CarConfig> mCarConfigList;

    public CarConfigSpinnerAdapter(Context context, List<CarConfig> CarConfigList){
        mContext = context;
        mCarConfigList = CarConfigList;
    }
    @Override
    public int getCount() {
        return mCarConfigList.size();
    }

    @Override
    public Object getItem(int i) {
        return mCarConfigList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.spinner_item, null);
            holder = new ViewHolder();
            holder.tvText = (TextView) view.findViewById(R.id.text);
            holder.ivArrow = (ImageView) view.findViewById(R.id.iv_arrow_down);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.tvText.setText(mCarConfigList.get(i).getName());

        if (i != 0) {
            holder.ivArrow.setVisibility(View.INVISIBLE);
        }

        return view;
    }
    private class ViewHolder {
        TextView tvText;
        ImageView ivArrow;
    }
}
