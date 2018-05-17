package cadillac.example.com.cadillac.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import cadillac.example.com.cadillac.R;

/**
 * Created by bitch-1 on 2017/5/27.
 */

public class RedtitleAdpt extends BaseAdapter {
    private Context context;
    private List<String> list;

    public RedtitleAdpt(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return 1;
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
        if(list.size()==3){
            view=View.inflate(context, R.layout.item_redtitle,null);
        }
        return null;
    }
}
