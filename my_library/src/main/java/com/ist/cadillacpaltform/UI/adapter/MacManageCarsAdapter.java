package com.ist.cadillacpaltform.UI.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ist.cadillacpaltform.R;
import com.ist.cadillacpaltform.SDK.bean.HighStockAge.CarInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dearlhd on 2017/1/10.
 */
public class MacManageCarsAdapter extends BaseAdapter {
    private Context mContext;
    private List<CarInfo> mCars;

    private OnRightItemClickListener mListener;

    public interface OnRightItemClickListener {
        void onDeleteClick (long carId);
    }

    public MacManageCarsAdapter (Context context, List<CarInfo> cars) {
        mContext = context;
        mCars = cars;
        if (mCars == null) {
            mCars = new ArrayList<CarInfo>();
        }
    }

    public void setOnRightItemClickListener (OnRightItemClickListener listener) {
        mListener = listener;
    }

    public void addCars (List<CarInfo> cars) {
        if (cars != null) {
            mCars.addAll(cars);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mCars.size();
    }

    @Override
    public Object getItem(int position) {
        return mCars.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mCars.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_listview_car_info, null);
            holder = new ViewHolder();
            holder.tvCarModel = (TextView) convertView.findViewById(R.id.tv_car_model);
            holder.tvCarColor = (TextView) convertView.findViewById(R.id.tv_car_color);
            holder.tvCarPrice = (TextView) convertView.findViewById(R.id.tv_car_price);
            holder.tvDealer = (TextView) convertView.findViewById(R.id.tv_dealer);
            holder.tvRightBtn1 = (TextView) convertView.findViewById(R.id.tv_right_button1);
            holder.tvDeleteBtn = (TextView) convertView.findViewById(R.id.tv_right_button2);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final CarInfo car = mCars.get(position);
        String intro = car.getLine().getName() + " " + car.getConfig().getName();
        holder.tvCarModel.setText(intro);

        holder.tvCarColor.setText(car.getColor());
        holder.tvCarPrice.setText(car.getPrice() + "万");
        if(car.getDealer()!=null) {
            holder.tvDealer.setText(car.getDealer().getName());
        }
        holder.tvRightBtn1.setVisibility(View.GONE);
        holder.tvDeleteBtn.setText("删除");

        holder.tvDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onDeleteClick(car.getId());
                }
                mCars.remove(position);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    private class ViewHolder {
        TextView tvCarModel;
        TextView tvCarColor;
        TextView tvCarPrice;
        TextView tvDealer;
        TextView tvRightBtn1;
        TextView tvDeleteBtn;
    }
}
