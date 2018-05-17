package com.ist.cadillacpaltform.UI.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ist.cadillacpaltform.R;
import com.ist.cadillacpaltform.SDK.bean.Posm.AbstractGradeDetail;
import com.ist.cadillacpaltform.SDK.bean.Posm.GradeDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dearlhd on 2017/3/24.
 */
public class PosmRectifyDetailAdapter extends BaseAdapter {

    private Context mContext;
    private List<AbstractGradeDetail> mDetails;

    public PosmRectifyDetailAdapter (Context context, List<AbstractGradeDetail> details) {
        mContext = context;
        if (details == null) details = new ArrayList<>();
        mDetails = details;
    }

    @Override
    public int getCount() {
        return mDetails.size();
    }

    @Override
    public Object getItem(int position) {
        return mDetails.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mDetails.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_listview_rectify_detail, null);
            holder = new ViewHolder();
            holder.tvItem = (TextView) convertView.findViewById(R.id.tv_item);
            holder.tvScore = (TextView) convertView.findViewById(R.id.tv_score);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvItem.setText(mDetails.get(position).getQuestionItem());
        holder.tvScore.setText(mDetails.get(position).getScore() + "分");

        return convertView;
    }

    private class ViewHolder {
        TextView tvItem;
        TextView tvScore;
    }
}
