package cadillac.example.com.cadillac.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import java.util.List;

import cadillac.example.com.cadillac.R;

/**
 * Created by iris on 2017/12/14.
 */

public class KpiDialog implements View.OnClickListener{
    private Context context;
    private Display display;
    private Dialog dialog;
    private String type;
    private String[]leftlist;
    private String[]rightlist;

    public KpiDialog(Context context,String type) {
        this.context = context;
        this.type=type;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        if(type.equals("0")){
          leftlist=context.getResources().getStringArray(R.array.oneleftdate);
          rightlist=context.getResources().getStringArray(R.array.onerightdate);
        }else if(type.equals("1")){
            leftlist=context.getResources().getStringArray(R.array.twoleftdate);
            rightlist=context.getResources().getStringArray(R.array.tworightdate);
        }else {
            leftlist=context.getResources().getStringArray(R.array.threeleftdate);
            rightlist=context.getResources().getStringArray(R.array.threerightdate);
        }
    }

    public KpiDialog buidler(){
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_kpi, null);

        // 设置Dialog最小宽度为屏幕宽度
        view.setMinimumWidth(display.getWidth());
        ListView lv= (ListView) view.findViewById(R.id.lv_kpi);
        TextView tv_btn= (TextView) view.findViewById(R.id.tv_btn);

        tv_btn.setOnClickListener(this);

        dialog=new Dialog(context,R.style.ActionSheetDialogStyle);
        dialog.setContentView(view);
        Window dilogwindow=dialog.getWindow();
        dilogwindow.setGravity(Gravity.CENTER);
        dialog.setCanceledOnTouchOutside(false);
        lv.setAdapter(new KpiAdapter());
        WindowManager.LayoutParams lp = dilogwindow.getAttributes();
        lp.x = 0;
        lp.y = 0;
        dilogwindow.setAttributes(lp);

        return this;
    }

    public KpiDialog setCanceledOnTouchOutside(boolean b){

        dialog.setCanceledOnTouchOutside(b);
        return this;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_btn:
                dialog.dismiss();
                break;
        }

    }

    public void show() {
        dialog.show();
    }

    private class KpiAdapter extends BaseAdapter{


        @Override
        public int getCount() {
            return leftlist.length;
        }

        @Override
        public Object getItem(int i) {
            return leftlist[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view=View.inflate(context,R.layout.item_dialog,null);
            LinearLayout ll_itemdilog= (LinearLayout) view.findViewById(R.id.ll_itemdilog);
            TextView tv_left= (TextView) view.findViewById(R.id.tv_left);
            TextView tv_right= (TextView) view.findViewById(R.id.tv_right);
            if(i%2==0){
                ll_itemdilog.setBackgroundColor(Color.parseColor("#ffffff"));
            }else {
                ll_itemdilog.setBackgroundColor(Color.parseColor("#f3f3f3"));
            }
            tv_left.setText(leftlist[i]);
            tv_right.setText(rightlist[i]);
            return view;
        }
    }
}



