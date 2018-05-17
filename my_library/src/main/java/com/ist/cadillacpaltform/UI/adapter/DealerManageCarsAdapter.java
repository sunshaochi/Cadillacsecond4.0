package com.ist.cadillacpaltform.UI.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ist.cadillacpaltform.R;
import com.ist.cadillacpaltform.SDK.bean.HighStockAge.CarInfo;
import com.ist.cadillacpaltform.SDK.bean.HighStockAge.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dearlhd on 2017/1/4.
 */
public class DealerManageCarsAdapter extends BaseAdapter{
    private Context mContext;

    private OnRightItemClickListener mListener;   // 右滑菜单

    /* 这里将被请求和未被请求的车辆分开存储 */
    private List<Order> mOrders;                  // 被请求的车辆
    private List<CarInfo> mCars;           // 未被请求的车辆

    public interface OnRightItemClickListener {
        void onTradeConfirmClick (long orderId, int position);
        void onTradeDenyClick (long orderId, int position);
        void onEditClick (long carId);
        void onDeleteClick (long carId, int position);
    }

    public DealerManageCarsAdapter (Context context, List<Order> orders, List<CarInfo> cars) {
        mContext = context;
        if (orders == null) orders = new ArrayList<>();
        if (cars == null) cars = new ArrayList<>();

        mOrders = orders;
        mCars = cars;
    }

    public void setRightItemClickListener (OnRightItemClickListener listener) {
        mListener = listener;
    }

    // 加载更多车辆信息后调用以刷新列表
    public void addCars (List<CarInfo> cars) {
        mCars.addAll(cars);
        notifyDataSetChanged();
    }

    // 删除某条数据
    public void deleteEntry (int i) {
        if (i < mOrders.size()) {
            mOrders.remove(i);
        } else {
            mCars.remove(i - mOrders.size());
        }
        notifyDataSetChanged();
    }

    public int getRequestCarSize() {
        return mOrders.size();
    }

    @Override
    public int getCount() {
        int cnt1 = mOrders.size();
        int cnt2 = mCars.size();
        return cnt1 + cnt2;
    }

    @Override
    public Object getItem(int position) {
        return position < mOrders.size() ? mOrders.get(position) : mCars.get(position - mOrders.size());
    }

    @Override
    public long getItemId(int position) {
        return position < mOrders.size() ? mOrders.get(position).getId() : mCars.get(position - mOrders.size()).getId();
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
            holder.tvRightBtn2 = (TextView) convertView.findViewById(R.id.tv_right_button2);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }



        /* 接下来的部分将根据车辆是否被请求和有不同的显示，先是被请求的，后为未被请求的*/
        if (position < mOrders.size()) {
            final CarInfo carInfo = mOrders.get(position).getCar();

            String intro = carInfo.getLine().getName() + " " + carInfo.getConfig().getName();
            holder.tvCarModel.setText(intro);

            holder.tvCarColor.setText(carInfo.getColor());
            holder.tvCarPrice.setText(carInfo.getPrice() + "万");
            holder.tvDealer.setText(carInfo.getDealer().getName());
            holder.tvRightBtn1.setText("交易确认");
            holder.tvRightBtn2.setText("交易拒绝");

            holder.tvRightBtn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onTradeConfirmClick(mOrders.get(position).getId(), position);
                    }
                    mOrders.remove(position);
                    notifyDataSetChanged();
                }
            });

            holder.tvRightBtn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onTradeDenyClick(mOrders.get(position).getId(), position);
                    }
                    mOrders.remove(position);
                    notifyDataSetChanged();
                }
            });

            convertView.setBackgroundColor(mContext.getResources().getColor(R.color.colorPink));
        } else {
            final CarInfo carInfo = mCars.get(position - mOrders.size());

            String intro = carInfo.getLine().getName() + " " + carInfo.getConfig().getName();
            holder.tvCarModel.setText(intro);

            holder.tvCarColor.setText(carInfo.getColor());
            holder.tvCarPrice.setText(carInfo.getPrice() + "万");
            holder.tvDealer.setText(carInfo.getDealer().getName());
            holder.tvRightBtn1.setText("编辑");
            holder.tvRightBtn2.setText("删除");

            holder.tvRightBtn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onEditClick(carInfo.getId());
                    }
//                    mCars.remove(position-mOrders.size());
                    notifyDataSetChanged();
                }
            });

            holder.tvRightBtn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onDeleteClick(carInfo.getId(), position);
                    }
                    mCars.remove(position-mOrders.size());
                    notifyDataSetChanged();
                }
            });

            convertView.setBackgroundColor(mContext.getResources().getColor(R.color.colorWhite));
        }

        return convertView;
    }


    private class ViewHolder {
        TextView tvCarModel;
        TextView tvCarColor;
        TextView tvCarPrice;
        TextView tvDealer;
        TextView tvRightBtn1;
        TextView tvRightBtn2;
    }
}
