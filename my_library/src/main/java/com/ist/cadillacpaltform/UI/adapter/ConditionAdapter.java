package com.ist.cadillacpaltform.UI.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ist.cadillacpaltform.R;

import java.util.List;

/**
 * Created by dearlhd on 2016/12/20.
 */
public class ConditionAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mData;
    private List mObjs;
    private int mSelectedPos = -1;    // 用于标记被选中的位置

    public ConditionAdapter(Context context, List<String> data, List objs) {
        mContext = context;
        mData = data;
        mObjs = objs;
    }

    public int getSelectedPos() {
        return mSelectedPos;
    }

    public void setSelectedPos(int selectedPos) {
        this.mSelectedPos = selectedPos;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    // 这里要注意的是，由于选项中多了一项“不限”，mObjs对应的位置比mData少1
    @Override
    public Object getItem(int i) {
        if (mObjs != null && mObjs.size() > i) {
            return mObjs.get(i);
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;

        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_listview_select_condition, null);
            holder = new ViewHolder();
            holder.tvContent = (TextView) view.findViewById(R.id.tv_condition_content);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.tvContent.setText(mData.get(i));

        if (i == mSelectedPos) {
            holder.tvContent.setTextColor(Color.parseColor("#85182D"));     // 红色
        } else {
            holder.tvContent.setTextColor(Color.parseColor("#333333"));     // 黑色
        }

        return view;
    }

    public class ViewHolder {
        public TextView tvContent;
    }
}
