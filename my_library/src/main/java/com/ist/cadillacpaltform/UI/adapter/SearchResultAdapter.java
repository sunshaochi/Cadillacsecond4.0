package com.ist.cadillacpaltform.UI.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ist.cadillacpaltform.R;
import com.ist.cadillacpaltform.SDK.bean.HighStockAge.Dealer;

import java.util.List;

/**
 * Created by dearlhd on 2016/12/26.
 */
public class SearchResultAdapter extends BaseAdapter {
    private Context mContext;
    private List<Dealer> mDealers;

    public SearchResultAdapter (Context context, List<Dealer> dealers) {
        mContext = context;
        mDealers = dealers;
    }

    @Override
    public int getCount() {
        return mDealers.size();
    }

    @Override
    public Object getItem(int position) {
        return mDealers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_listview_search_result, null);
            holder = new ViewHolder();
            holder.tv_content = (TextView) convertView.findViewById(R.id.tv_search_result);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_content.setText(mDealers.get(position).getName());

        return convertView;
    }

    public class ViewHolder {
        TextView tv_content;
    }
}
