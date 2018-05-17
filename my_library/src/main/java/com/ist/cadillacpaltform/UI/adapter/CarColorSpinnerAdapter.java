package com.ist.cadillacpaltform.UI.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ist.cadillacpaltform.R;
import com.ist.cadillacpaltform.SDK.bean.HighStockAge.CarColor;

import java.util.List;

/**
 * Created by czh on 2017/1/5.
 */

public class CarColorSpinnerAdapter extends BaseAdapter {
    private Context mContext;

    public List<CarColor> getmCarColorList() {
        return mCarColorList;
    }

    public void setmCarColorList(List<CarColor> mCarColorList) {
        this.mCarColorList = mCarColorList;
    }

    private List<CarColor> mCarColorList;

    public CarColorSpinnerAdapter(Context context, List<CarColor> carColorList) {
        mContext = context;
        mCarColorList = carColorList;
    }

    @Override
    public int getCount() {
        return mCarColorList.size();
    }

    @Override
    public Object getItem(int i) {
        return mCarColorList.get(i);
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
        holder.tvText.setText(mCarColorList.get(i).getColor());

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
