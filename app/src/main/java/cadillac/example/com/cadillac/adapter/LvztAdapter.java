package cadillac.example.com.cadillac.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cadillac.example.com.cadillac.R;
import cadillac.example.com.cadillac.bean.LoadBean;
import cadillac.example.com.cadillac.bean.SalseDateBean;
import cadillac.example.com.cadillac.bean.UserModle;
import cadillac.example.com.cadillac.view.CKITaskSETimeScrollView;

import static com.lidroid.xutils.util.core.CompatibleAsyncTask.init;

public class LvztAdapter implements CKITaskSETimeScrollView.ScrollAdapter {
    private Context context;
    private List<LoadBean>list;


    public LvztAdapter(Context context,List<LoadBean>list) {
        this.context = context;
        this.list=list;

    }

    public void update(List<LoadBean>list){
        this.list=list;
        notify();

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public View getView(int i) {
        View view=View.inflate(context,R.layout.item_lvzt,null);
        LinearLayout ll_item= (LinearLayout) view.findViewById(R.id.ll_item);
        TextView tv_carname= (TextView) view.findViewById(R.id.tv_carname);
        EditText et_xzjd= (EditText) view.findViewById(R.id.et_xzjd);
        EditText et_ecjd= (EditText) view.findViewById(R.id.et_ecjd);
        EditText et_dd= (EditText) view.findViewById(R.id.et_dd);//展厅订单
        EditText et_jc= (EditText) view.findViewById(R.id.et_jc);
        EditText et_czdds= (EditText) view.findViewById(R.id.et_czdds);
        EditText et_jrtd= (EditText) view.findViewById(R.id.et_jetd);

        if(i%2==0){
            ll_item.setBackgroundColor(Color.parseColor("#FAF9F9"));
        }else {
            ll_item.setBackgroundColor(Color.parseColor("#ffffff"));
        }

        if(list!=null&&list.size()>0){
            tv_carname.setText(list.get(i).getCarModelName());
            et_xzjd.setText(list.get(i).getIncomeCnt());//新增进店
            et_ecjd.setText(list.get(i).getSecondIncomeCnt());//二次进店
            et_dd.setText(list.get(i).getOrderCnt());//展厅订单
            et_jc.setText(list.get(i).getDeliveryCnt());//交车
            et_czdds.setText(list.get(i).getOrderCntShow());//车展订单
            et_jrtd.setText(list.get(i).getCancelOrderCnt());//退订
        }


        return view;
    }

}





