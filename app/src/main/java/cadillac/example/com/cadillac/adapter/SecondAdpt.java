package cadillac.example.com.cadillac.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import cadillac.example.com.cadillac.R;
import cadillac.example.com.cadillac.view.MyListview;

/**
 * Created by bitch-1 on 2017/5/23.
 */

public class SecondAdpt extends BaseAdapter {
    private Context context;

    public SecondAdpt(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 5;
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
        view=View.inflate(context, R.layout.item_sec,null);
        RelativeLayout rl_scrl= (RelativeLayout) view.findViewById(R.id.rl_serl);
        final MyListview lv_sclv= (MyListview) view.findViewById(R.id.lv_selv);
        return view;
    }
}
