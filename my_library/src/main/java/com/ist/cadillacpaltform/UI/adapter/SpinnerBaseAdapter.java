package com.ist.cadillacpaltform.UI.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ist.cadillacpaltform.R;

/**
 * Created by dearlhd on 2017/1/17.
 */
public class SpinnerBaseAdapter extends BaseAdapter {
    private Context mContext;
    private String[] mData;

    public SpinnerBaseAdapter (Context context, String[] data) {
        mContext = context;
        mData = data;
    }

    @Override
    public int getCount() {
        return mData.length;
    }

    @Override
    public Object getItem(int position) {
        return mData[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
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
        holder.tvText.setText(mData[i]);

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
