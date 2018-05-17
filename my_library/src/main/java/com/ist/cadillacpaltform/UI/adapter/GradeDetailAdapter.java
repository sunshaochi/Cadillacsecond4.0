package com.ist.cadillacpaltform.UI.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ist.cadillacpaltform.R;
import com.ist.cadillacpaltform.SDK.util.GradeTreeUtil.AbstractItem;
import com.ist.cadillacpaltform.SDK.util.GradeTreeUtil.AbstractModule;
import com.ist.cadillacpaltform.SDK.util.GradeTreeUtil.AbstractRegion;
import com.ist.cadillacpaltform.SDK.util.GradeTreeUtil.GradeTreeParser;

import java.util.List;

/**
 * Created by dearlhd on 2017/3/9.
 * 该Adapter实现折叠效果
 * 这里必须强调的是，使用该Adapter的ListView不能在外部设置OnItemClickListener
 * 如果要设置点击效果，需要传一个对应的Listener进来
 */
public class GradeDetailAdapter extends BaseAdapter {
    private Context mContext;

    private List<AbstractModule> mAllModules;
    private List<Object> mVisibleEntries;

    OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClicked(long questionId);
    }

    public GradeDetailAdapter(Context context, ListView listView, List<AbstractModule> modules) {
        mContext = context;
        mAllModules = modules;
        mVisibleEntries = GradeTreeParser.getVisibleNodes(mAllModules);

        // 将ListView传进来，设置点击事件，保证展开和收缩
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                expandOrCollapse(position);
                if (mListener != null) {
                    Object node = mVisibleEntries.get(position);
                    if (node instanceof AbstractItem) {
                        mListener.onItemClicked(((AbstractItem)node).questionId);
                    }
                }
            }
        });
    }

    public void setOnItemClickListener (OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public int getCount() {
        return mVisibleEntries.size();
    }

    @Override
    public Object getItem(int position) {
        return mVisibleEntries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_listview_grade_detail, null);
            holder.llModule = (LinearLayout) convertView.findViewById(R.id.ll_module);
            holder.llRegion = (LinearLayout) convertView.findViewById(R.id.ll_region);
            holder.llItem = (LinearLayout) convertView.findViewById(R.id.ll_item);

            holder.tvModule = (TextView) convertView.findViewById(R.id.tv_module);
            holder.ivModuleArrow = (ImageView) convertView.findViewById(R.id.iv_module_arrow);
            holder.tvRegion = (TextView) convertView.findViewById(R.id.tv_region);
            holder.tvItem = (TextView) convertView.findViewById(R.id.tv_item);
            holder.tvScore = (TextView) convertView.findViewById(R.id.tv_score);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.llModule.setVisibility(View.GONE);
        holder.llRegion.setVisibility(View.GONE);
        holder.llItem.setVisibility(View.GONE);

        Object node = mVisibleEntries.get(position);

        if (node instanceof AbstractModule) {
            holder.llModule.setVisibility(View.VISIBLE);
            holder.tvModule.setText(((AbstractModule) node).name);

        } else if (node instanceof AbstractRegion) {
            holder.llRegion.setVisibility(View.VISIBLE);
            holder.tvRegion.setText(((AbstractRegion) node).name);

        } else if (node instanceof AbstractItem) {
            holder.llItem.setVisibility(View.VISIBLE);
            holder.tvItem.setText(((AbstractItem) node).name);
            float score = ((AbstractItem) node).score;
            if (score == -1) {
                holder.tvScore.setVisibility(View.INVISIBLE);
            } else {
                holder.tvScore.setVisibility(View.VISIBLE);
                holder.tvScore.setText(score + "分");
            }
            if (!((AbstractItem) node).isFullMark) {
                holder.tvScore.setTextColor(mContext.getResources().getColor(R.color.colorRedText));
            } else {
                holder.tvScore.setTextColor(mContext.getResources().getColor(R.color.colorBlackText));
            }
        }

        return convertView;
    }


    /**
     * 点击条目之后展开或收起
     *
     * @param position
     */
    private void expandOrCollapse(int position) {
        Object node = mVisibleEntries.get(position);

        if (node != null) {
            if (node instanceof AbstractModule) {
                ((AbstractModule) node).isExpand = !((AbstractModule) node).isExpand;
            } else if (node instanceof AbstractRegion) {
                ((AbstractRegion) node).isExpand = !((AbstractRegion) node).isExpand;
            }
            mVisibleEntries = GradeTreeParser.getVisibleNodes(mAllModules);
            notifyDataSetChanged();
        }
    }

    class ViewHolder {
        LinearLayout llModule;
        LinearLayout llRegion;
        LinearLayout llItem;

        TextView tvModule;
        ImageView ivModuleArrow;
        TextView tvRegion;
        TextView tvItem;
        TextView tvScore;
    }
}
