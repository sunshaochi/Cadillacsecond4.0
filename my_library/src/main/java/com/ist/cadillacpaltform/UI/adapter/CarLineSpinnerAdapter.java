package com.ist.cadillacpaltform.UI.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ist.cadillacpaltform.R;
import com.ist.cadillacpaltform.SDK.bean.HighStockAge.CarLine;

import java.util.List;

/**
 * Created by czh on 2017/1/5.
 */

public class CarLineSpinnerAdapter extends BaseAdapter {
    private Context mContext;

    public List<CarLine> getmCarLineList() {
        return mCarLineList;
    }

    public void setmCarLineList(List<CarLine> mCarLineList) {
        this.mCarLineList = mCarLineList;
    }

    private List<CarLine> mCarLineList;

    public CarLineSpinnerAdapter(Context context, List<CarLine> carLineList) {
        mContext = context;
        mCarLineList = carLineList;
    }

    @Override
    public int getCount() {
        return mCarLineList.size();
    }

    @Override
    public Object getItem(int i) {
        return mCarLineList.get(i);
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
        holder.tvText.setText(mCarLineList.get(i).getName());

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
