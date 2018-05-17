package com.ist.cadillacpaltform.UI.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ist.cadillacpaltform.R;
import com.ist.cadillacpaltform.SDK.bean.Posm.DealerGrade;

import java.util.List;

/**
 * Created by dearlhd on 2017/3/10.
 */
public class PosmHomeAdapter extends BaseAdapter{
    private Context mContext;
    private float mTotalScore;
    private List<DealerGrade> mDealerGrades;

    public PosmHomeAdapter (Context context, float totalScore, List<DealerGrade> dealers) {
        mContext = context;
        mTotalScore = totalScore;
        mDealerGrades = dealers;
    }

    public void addDealerGrades (List<DealerGrade> grades) {
        if (mDealerGrades != null) {
            mDealerGrades.addAll(grades);
            notifyDataSetChanged();
        }
    }

    public float getTotalScore () {
        return mTotalScore;
    }

    @Override
    public int getCount() {
        if (mDealerGrades == null) {
            return 0;
        }
        return mDealerGrades.size();
    }

    @Override
    public Object getItem(int position) {
        return mDealerGrades.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mDealerGrades.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_listview_posm, null);
            holder = new ViewHolder();
            holder.tvDealer = (TextView) convertView.findViewById(R.id.tv_dealer);
            holder.tvGrader = (TextView) convertView.findViewById(R.id.tv_grader);
            holder.llScore = (LinearLayout) convertView.findViewById(R.id.ll_score);
            holder.tvScore = (TextView) convertView.findViewById(R.id.tv_score);
            holder.tvTotalScore = (TextView) convertView.findViewById(R.id.tv_total_score);
            holder.tvToGrade = (TextView) convertView.findViewById(R.id.tv_to_grade);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        DealerGrade dealerGrade = mDealerGrades.get(position);
        holder.tvDealer.setText(dealerGrade.getName());

        if (dealerGrade.getGradeDto() != null) {
            holder.tvToGrade.setVisibility(View.GONE);
            holder.llScore.setVisibility(View.VISIBLE);
            holder.tvGrader.setText("打分人:" + dealerGrade.getGradeDto().userDto.name);
            holder.tvScore.setText(dealerGrade.getGradeDto().score + "");
            if (dealerGrade.getGradeDto().score < 60) {
                holder.tvScore.setTextColor(mContext.getResources().getColor(R.color.colorRedText));
            } else {
                holder.tvScore.setTextColor(mContext.getResources().getColor(R.color.colorBlackText));
            }
            holder.tvTotalScore.setText("/" + mTotalScore);
        } else {
            holder.llScore.setVisibility(View.GONE);
            holder.tvToGrade.setVisibility(View.VISIBLE);
            holder.tvGrader.setText("打分人:无");
        }

        return convertView;
    }

    class ViewHolder {
        TextView tvDealer;
        TextView tvGrader;
        LinearLayout llScore;
        TextView tvScore;
        TextView tvTotalScore;
        TextView tvToGrade;
    }
}
