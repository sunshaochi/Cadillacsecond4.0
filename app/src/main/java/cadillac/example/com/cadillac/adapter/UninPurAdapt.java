package cadillac.example.com.cadillac.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import cadillac.example.com.cadillac.R;

/**
 * Created by bitch-1 on 2017/7/14.
 */

public class UninPurAdapt extends BaseAdapter {
    private Context context;
    private List<Map<String,String>>list;

    public UninPurAdapt(Context context,List<Map<String,String>>list) {
        this.context = context;
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHodele hodele;
        if(view==null) {
            view = View.inflate(context, R.layout.item_uninput, null);
            hodele=new ViewHodele();
            hodele.tv_mingc= (TextView) view.findViewById(R.id.tv_mingc);
            hodele.tv_shul= (TextView) view.findViewById(R.id.tv_shul);
            view.setTag(hodele);
        }else {
            hodele= (ViewHodele) view.getTag();
        }
        if(list!=null&&list.size()>0){
            hodele.tv_mingc.setText(list.get(i).get("区域"));
            hodele.tv_shul.setText(list.get(i).get("未录入经销商数"));
        }
        return view;
    }

    class ViewHodele{
        private TextView tv_mingc,tv_shul;//名称和数量
    }
}
