package com.ist.cadillacpaltform.UI.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.ist.cadillacpaltform.R;
import com.ist.cadillacpaltform.SDK.bean.HighStockAge.Dealer;

import java.util.List;

/**
 * Created by dearlhd on 2017/1/10.
 */
public class DealerPermissionAdapter extends BaseAdapter {
    private Context mContext;
    private List<Dealer> mDealers;

    private SwitchStatusChangeListener mListener;

    public DealerPermissionAdapter(Context context, List<Dealer> dealers) {
        mContext = context;
        mDealers = dealers;
    }

    public interface SwitchStatusChangeListener {
        void onStatusChanged(CompoundButton button, boolean isChecked, long dealerId);
    }

    public void setSwitchStatusChangeListener(SwitchStatusChangeListener listener) {
        mListener = listener;
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
        return mDealers.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_dealer_permission, null);
            holder = new ViewHolder();
            holder.tvDealer = (TextView) convertView.findViewById(R.id.tv_dealer);
            holder.permissionSwitch = (Switch) convertView.findViewById(R.id.switch_permission);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvDealer.setText(mDealers.get(position).getName());

        // 这里遇到了一个问题，由于listView对控件的复用，这里的listener可能在之前就被赋值了，会造成错误
        holder.permissionSwitch.setOnCheckedChangeListener(null);
        if (mDealers.get(position).getFlag()) {
            holder.permissionSwitch.setChecked(true);
        } else {
            holder.permissionSwitch.setChecked(false);
        }

        holder.permissionSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mListener != null) {
                    mListener.onStatusChanged(buttonView, isChecked, mDealers.get(position).getId());
                }
            }
        });

        return convertView;
    }

    class ViewHolder {
        TextView tvDealer;
        Switch permissionSwitch;
    }
}
