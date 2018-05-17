package com.ist.cadillacpaltform.UI.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ist.cadillacpaltform.R;
import com.ist.cadillacpaltform.SDK.bean.HighStockAge.CarInfo;

import java.util.List;

/**
 * Created by dearlhd on 2016/12/18.
 */
public class CarInfoAdapter extends BaseAdapter {

    private LayoutInflater mInflater;

    private List<CarInfo> mCarInfoList;

    public CarInfoAdapter (Context context, List<CarInfo> carInfoList) {
        mInflater = LayoutInflater.from(context);
        mCarInfoList = carInfoList;
    }

    public void addData (List<CarInfo> cars) {
        mCarInfoList.addAll(cars);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mCarInfoList.size();
    }

    @Override
    public Object getItem(int i) {
        return mCarInfoList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;

        if (view == null) {
            view = mInflater.inflate(R.layout.item_listview_car_info, null);
            holder = new ViewHolder();
            holder.tvCarModel = (TextView) view.findViewById(R.id.tv_car_model);
            holder.tvCarColor = (TextView) view.findViewById(R.id.tv_car_color);
            holder.tvCarPrice = (TextView) view.findViewById(R.id.tv_car_price);
            holder.tvDealer = (TextView) view.findViewById(R.id.tv_dealer);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        CarInfo carInfo = mCarInfoList.get(i);

        String intro = carInfo.getLine().getName() + " " + carInfo.getConfig().getName();
        holder.tvCarModel.setText(intro);

        holder.tvCarColor.setText(carInfo.getColor());
        holder.tvCarPrice.setText(carInfo.getPrice() + "万");
        holder.tvDealer.setText(carInfo.getDealer().getName());

        return view;
    }

    private class ViewHolder {
        TextView tvCarModel;
        TextView tvCarColor;
        TextView tvCarPrice;
        TextView tvDealer;
    }
}
