package cadillac.example.com.cadillac.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ist.cadillacpaltform.SDK.bean.Posm.User;

import java.util.List;

import cadillac.example.com.cadillac.R;
import cadillac.example.com.cadillac.activity.LookSqAct;
import cadillac.example.com.cadillac.bean.CheckApplyBean;
import cadillac.example.com.cadillac.bean.SqBean;
import cadillac.example.com.cadillac.http.Manage.UserManager;
import cadillac.example.com.cadillac.http.ResultCallback;
import cadillac.example.com.cadillac.utils.MyToastUtils;
import cadillac.example.com.cadillac.view.MyAlertDialog;

/**
 * 申请列表适配器
 * Created by bitch-1 on 2017/5/4.
 */

public class SqAdapter extends BaseAdapter {
    private Context context;
    private List<CheckApplyBean>list;
    private Update update;

   public interface Update{
     void getdate();
    }

    public void setUpdate(Update update) {
        this.update = update;
    }

    public SqAdapter(Context context, List<CheckApplyBean> list) {
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        Viewhodle viewhodle;
        if(view==null) {
            view = View.inflate(context, R.layout.item_looksq, null);
            viewhodle=new Viewhodle();
            viewhodle.tv_name = (TextView) view.findViewById(R.id.tv_name);
            viewhodle.tv_phone= (TextView) view.findViewById(R.id.tv_phone);
            viewhodle.tv_tg = (TextView) view.findViewById(R.id.tv_tg);
            viewhodle.tv_jj = (TextView) view.findViewById(R.id.tv_jj);
            view.setTag(viewhodle);
        }else {
            viewhodle= (Viewhodle) view.getTag();
        }

        if(list!=null&&list.size()!=0){
            viewhodle.tv_name.setText(list.get(i).getPersonName());
            viewhodle.tv_phone.setText(list.get(i).getMobileNo());
        }
        viewhodle.tv_tg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tongguo(list.get(i).getId(),"1");
            }
        });
        viewhodle.tv_jj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Juejue(list.get(i).getId());//拒绝通过
            }
        });
        return view;
    }

    /**
     * 拒绝通过
     */
    private void Juejue(final String id) {
        new MyAlertDialog(context).builder().setMsg("确认拒绝?")
                .setPositiveButton("是", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tongguo(id,"0");
                    }
                }).setNegativeButton("否", null).show();

    }


    /**
     * 原来是获取用户名唯一不唯一的接口新的不要了，注册的时候后台就验证了用户名是不是唯一
     * 调取提示框的内容
     */
//    private void check(String id, String state) {//type后面要用到
//        UserManager.getUserManager().checkDialog(op, obj, username, telephone, new ResultCallback<String>() {
//            @Override
//            public void onError(int status, String errorMsg) {
//
//            }
//
//            @Override
//            public void onResponse(String response) {
//                if(!TextUtils.isEmpty(response)){//没内容
//                    new MyAlertDialog(context).builder().setTitle("警告").setMsg("该经销商账号已经存在\n请确认需要删除之前注册的账号?")
//                    .setPositiveButton("是", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            tongguo("approve","usr",list.get(type).getUsername(),list.get(type).getTeltephone(),"1","1");
//                        }
//                    }).setNegativeButton("否", null).show();
//                }else {
//                    new MyAlertDialog(context).builder().setMsg("是否确认要通过")
//                            .setPositiveButton("是", new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    tongguo("approve","usr",list.get(type).getUsername(),list.get(type).getTeltephone(),"1","1");
//                                }
//                            }).setNegativeButton("否", null).show();
//
//
//                }
//
//
//            }
//        });
//
//    }

    /**
     * 通过按钮
     */
    private void tongguo(String id,String state) {
        UserManager.getUserManager().through(id, state,new ResultCallback<String>() {
            @Override
            public void onError(int status, String errorMsg) {
                MyToastUtils.showShortToast(context,errorMsg);
            }

            @Override
            public void onResponse(String response) {
             update.getdate();

            }
        });
    }

    class Viewhodle{
        private TextView tv_name,tv_tg,tv_jj,tv_phone;
    }



}
