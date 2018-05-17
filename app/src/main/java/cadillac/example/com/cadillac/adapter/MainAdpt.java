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

public class MainAdpt extends BaseAdapter {
    private Context context;
    private boolean ischeck;
    private SecondAdpt adpt;

    public MainAdpt(Context context) {
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
        final ViewHodle viewhodle;
        if(view==null){
            view=View.inflate(context, R.layout.item_main,null);
            viewhodle=new ViewHodle();
            viewhodle.rl_scrl= (RelativeLayout) view.findViewById(R.id.rl_serl);
            viewhodle.lv_sclv= (MyListview) view.findViewById(R.id.lv_selv);
            view.setTag(viewhodle);
        }else {
            viewhodle= (ViewHodle) view.getTag();
        }

        viewhodle.rl_scrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!ischeck){
                    ischeck=true;
                    viewhodle.lv_sclv.setVisibility(View.VISIBLE);
                    adpt=new SecondAdpt(context);
                    viewhodle.lv_sclv.setAdapter(adpt);
                }else {
                    ischeck=false;
                    viewhodle.lv_sclv.setVisibility(View.GONE);
                }
            }
        });
        return view;
    }

    class ViewHodle{
        private RelativeLayout rl_scrl;
        private MyListview lv_sclv;
    }
}
