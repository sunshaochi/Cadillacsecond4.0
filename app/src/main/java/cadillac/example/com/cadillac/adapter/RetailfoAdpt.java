package cadillac.example.com.cadillac.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cadillac.example.com.cadillac.R;
import cadillac.example.com.cadillac.bean.ListBean;

/**
 * Created by bitch-1 on 2017/5/22.
 */

public class RetailfoAdpt extends BaseAdapter {
    private Context context;
    private List<ListBean> list;
    private int totle;


    public RetailfoAdpt(Context context, List<ListBean> list, int totle) {
        this.context = context;
        this.list = list;
        this.totle = totle;

    }

    @Override
    public int getCount() {
        return list.size() + 1;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHodle hodle;
        if(view==null) {
            view = View.inflate(context, R.layout.item_retaifo, null);
            hodle=new ViewHodle();
            hodle.tv_moudle= (TextView) view.findViewById(R.id.tv_name);
            hodle.tv_num = (TextView) view.findViewById(R.id.tv_num);
            view.setTag(hodle);
        }else {
            hodle= (ViewHodle) view.getTag();
        }
        if (list != null && list.size() != 0) {
            if (i == list.size()) {
                hodle.tv_moudle.setText("总计");
                hodle.tv_num.setText(totle + "");
            } else {
                hodle.tv_moudle.setText(list.get(i).getCarType());
                hodle.tv_num.setText(list.get(i).getPridictNum());
            }
        }


        return view;

    }

    class ViewHodle {
        private TextView tv_moudle;
        private TextView tv_num;
    }


}
