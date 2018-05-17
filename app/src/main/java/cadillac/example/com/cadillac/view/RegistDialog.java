package cadillac.example.com.cadillac.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import cadillac.example.com.cadillac.R;
import cadillac.example.com.cadillac.bean.RegistBean;

/**
 * Created by iris on 2018/1/22.
 */

public class RegistDialog {
    private Context context;
    private Display display;
    private Dialog dialog;
    private List<String>list;
    private String type;
    private upDateUi updateui;


    public RegistDialog(Context context,List<String>list,String type) {
        this.context = context;
        this.list=list;
        this.type=type;
        updateui=(upDateUi) context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public RegistDialog buidler(){
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_regist, null);
        // 设置Dialog最小宽度为屏幕宽度
        view.setMinimumWidth(display.getWidth());
        TextView tv_title= (TextView) view.findViewById(R.id.tv_title);
        ListView lv_select= (ListView) view.findViewById(R.id.lv_select);
        if(type.equals("0")){
            tv_title.setText("请选择区域");
        }else if(type.equals("1")){
            tv_title.setText("请选择经销商");
        }else if(type.equals("2")){
            tv_title.setText("请选择职务");
        }
        dialog=new Dialog(context,R.style.ActionSheetDialogStyle);
        dialog.setContentView(view);
        Window dilogwindow=dialog.getWindow();
        dilogwindow.setGravity(Gravity.BOTTOM);
        dialog.setCanceledOnTouchOutside(true);
        lv_select.setAdapter(new MyRegAdapter());
        lv_select.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                updateui.updateui(i);
                dialog.dismiss();
            }
        });
        WindowManager.LayoutParams lp = dilogwindow.getAttributes();
        lp.x = 0;
        lp.y = 0;
        dilogwindow.setAttributes(lp);
        return this;
    }



    public class MyRegAdapter extends BaseAdapter{

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
            view =View.inflate(context,R.layout.item_regist,null);
            TextView tv_dq= (TextView) view.findViewById(R.id.tv_dq);
            tv_dq.setText(list.get(i));
            return view;
        }
    }

    public void show() {
        dialog.show();
    }

    public void setCanceledOnTouchOutside(boolean b){
        dialog.setCanceledOnTouchOutside(b);
    }


    public interface upDateUi{
       void updateui(int dex);
    }

}
