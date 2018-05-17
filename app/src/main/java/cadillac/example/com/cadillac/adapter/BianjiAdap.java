package cadillac.example.com.cadillac.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import cadillac.example.com.cadillac.R;
import cadillac.example.com.cadillac.bean.ListBean;
import cadillac.example.com.cadillac.utils.MyLogUtils;
import cadillac.example.com.cadillac.utils.MyToastUtils;

/**
 * Created by bitch-1 on 2017/5/22.
 */

public class BianjiAdap extends BaseAdapter {
    private Context context;
    private List<ListBean>list;

    private Uplist uplist;

    public void setUplist(Uplist uplist) {
        this.uplist = uplist;
    }

    public static interface Uplist{
        void update(int i,String num);
    }

    public BianjiAdap(Context context, List<ListBean> list) {
        this.context = context;
        this.list = list;
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
    public View getView(int position, View view, ViewGroup viewGroup) {
        final ViewHodle hodle;
        if(view==null) {
            view = View.inflate(context, R.layout.item_bianji, null);
            hodle=new ViewHodle();
            hodle.tv_moudle= (TextView) view.findViewById(R.id.tv_name);
            hodle.et_num= (EditText) view.findViewById(R.id.et_num);
            view.setTag(hodle);
        }else {
            hodle= (ViewHodle) view.getTag();
        }

        if(list.size()!=0&&list!=null){
                final ListBean listBean=list.get(position);
                if(!TextUtils.isEmpty(listBean.getCarType())) {
                    hodle.tv_moudle.setText(listBean.getCarType());
                }

                hodle.et_num.setTag(position);//把Bean与输入框进行绑定
                hodle.et_num.clearFocus();//清除输入框焦点
                hodle.et_num.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        if(editable.toString().length()>1&&editable.toString().startsWith("0")){
                            MyToastUtils.showShortToast(context,"非法数据");
                            hodle.et_num.setText("");
                        }else {
                            int i = (int) hodle.et_num.getTag();
                            uplist.update(i, editable + "");
                        }
                    }
                });

//             hodle.et_num.addTextChangedListener(null);//清除item的监听，防止oom内存不足
                //list中必须放在add的后面
                if(!TextUtils.isEmpty(listBean.getPridictNum())){
                    hodle.et_num.setText(listBean.getPridictNum());
                }
            }



        return view;

    }

    class ViewHodle {
        private TextView tv_moudle;
        private EditText et_num;
    }


}


