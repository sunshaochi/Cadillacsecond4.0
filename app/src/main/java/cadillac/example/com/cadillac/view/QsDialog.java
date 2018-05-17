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
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cadillac.example.com.cadillac.R;
import cadillac.example.com.cadillac.bean.EventDayinfo;
import cadillac.example.com.cadillac.bean.QstEventMess;

/**
 * Created by iris on 2018/2/1.
 */

public class QsDialog {

    private Context context;
    private Display display;
    private Dialog dialog;
    private List<String> list;
    private String name;
    private String type;



    private int clickTemp = -1;//标识选择的Item



    public QsDialog(Context context, List<String> list, String name, String type) {
        this.context = context;
        this.list=list;
        this.name=name;
        this.type=type;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

   public QsDialog build(){

       View view = LayoutInflater.from(context).inflate(R.layout.qs_dialog, null);
       ListView lv_qst= (ListView) view.findViewById(R.id.lv_qst);
       TextView tv_title= (TextView) view.findViewById(R.id.tv_title);
       if(type.equals("1")) {
           tv_title.setText("请选择时间");
       }else {
           tv_title.setText("请选择车型");
       }
       dialog=new Dialog(context,R.style.ActionSheetDialogStyle);
       dialog.setContentView(view);
       Window dilogwindow=dialog.getWindow();
       dilogwindow.setGravity(Gravity.BOTTOM);
       dialog.setCanceledOnTouchOutside(false);
       final QstAdapter adapter=new QstAdapter();
       lv_qst.setAdapter(adapter);

       for(int i=0;i<list.size();i++){
           if(name.equals(list.get(i))){
               adapter.setSeclection(i);
           }
       }

       WindowManager.LayoutParams lp = dilogwindow.getAttributes();
       lp.x = 0;
       lp.y = 0;
       dilogwindow.setAttributes(lp);

       // 设置Dialog最小宽度为屏幕宽度
       view.setMinimumWidth(display.getWidth());

       lv_qst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               adapter.setSeclection(i);
               adapter.notifyDataSetChanged();

               EventBus.getDefault().post(new QstEventMess(i));

               dialog.dismiss();
           }
       });
        return this;
   }

    public void show() {
        dialog.show();
    }

    public class QstAdapter extends BaseAdapter {

         public void setSeclection(int position) {
             clickTemp = position;
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
             view=View.inflate(context,R.layout.item_qst,null);
             TextView tv_name= (TextView) view.findViewById(R.id.tv_name);
             tv_name.setText(list.get(i));
             if(clickTemp==i){
                 tv_name.setTextColor(Color.parseColor("#8a152b"));
             }else {
                 tv_name.setTextColor(Color.parseColor("#333333"));

             }
             return view;
         }
     }

}





