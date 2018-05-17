package cadillac.example.com.cadillac.fragment.adminfra;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

import cadillac.example.com.cadillac.R;
import cadillac.example.com.cadillac.adapter.LvztAdapter;
import cadillac.example.com.cadillac.base.BaseFrag;
import cadillac.example.com.cadillac.bean.SalseDateBean;
import cadillac.example.com.cadillac.bean.UserModle;
import cadillac.example.com.cadillac.http.Manage.UserManager;
import cadillac.example.com.cadillac.http.ResultCallback;
import cadillac.example.com.cadillac.utils.MyToastUtils;
import cadillac.example.com.cadillac.view.CKITaskSETimeScrollView;
import cadillac.example.com.cadillac.view.CProgressDialog;

/**
 * Created by bitch-1 on 2017/3/24.
 */
public class FristadminFra extends BaseFrag {
    @ViewInject(R.id.ck)
    private CKITaskSETimeScrollView ck;
    private String name;
    private String time;
    private String role;
    private Dialog dialog;
    private Gson gson;
    private List<SalseDateBean>list;
    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fra_fristadmin,null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Bundle bundle=getArguments();
        gson=new Gson();
        name=bundle.getString("name");
        time=bundle.getString("time");
        role=bundle.getString("role");
        dialog= CProgressDialog.createLoadingDialog(getActivity(),false);

        dialog.show();
        getSalesData(time,name,role,"是");//获取展厅数据

    }

    /**
     * @param date 时间
     * @param store 经销商名字
     * @param role  职务
     * @param show 是（展厅）否（车展）
     */
    private void getSalesData(String date, final String store, String role, String show) {
        UserManager.getUserManager().getZtData(date, store, role, show, new ResultCallback<String>() {
            @Override
            public void onError(int status, String errorMsg) {
                MyToastUtils.showShortToast(getActivity(),errorMsg);
                dialog.dismiss();
            }

            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                list = gson.fromJson(response,
                        new TypeToken<List<SalseDateBean>>() {
                        }.getType());
                if(list.size()==0){
                    MyToastUtils.showShortToast(getActivity(),"暂无数据");
                }else {
                   ck.setAdapter(new MyAdapter());

                }

            }
        });


    }

    class MyAdapter implements CKITaskSETimeScrollView.ScrollAdapter {
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public View getView(int i) {
             View view=View.inflate(getActivity(),R.layout.item_child,null);
            TextView tv_chexin= (TextView) view.findViewById(R.id.tv_chexin);
            TextView tv_laidian= (TextView) view.findViewById(R.id.tv_laidian);
            TextView tv_dindan= (TextView) view.findViewById(R.id.tv_dindan);
            TextView tv_jindian= (TextView) view.findViewById(R.id.tv_jindian);
            TextView tv_tuidin= (TextView) view.findViewById(R.id.tv_tuidin);
            TextView tv_jiaoche= (TextView) view.findViewById(R.id.tv_jiaoche);
            TextView tv_ylj= (TextView) view.findViewById(R.id.tv_ylj);
            TextView tv_lc= (TextView) view.findViewById(R.id.tv_lc);

            tv_chexin.setText(list.get(i).getModels()+"");//车型
            tv_laidian.setText(list.get(i).getCall()+"");//来电
            tv_jindian.setText(list.get(i).getShowRoom()+"");//进店
            tv_dindan.setText(list.get(i).getOrder()+"");//订单
            tv_tuidin.setText(list.get(i).getUnsubscribe()+"");//退订
            tv_jiaoche.setText(list.get(i).getRetail()+"");//交车
            tv_ylj.setText(list.get(i).getTotalOrder()+"");//月累计订单
            tv_lc.setText(list.get(i).getRetainOrder()+"");//留存订单
            return view;

        }



//        @Override
//        public long getItemId(int i) {
//            return 0;
//        }
//
//        @Override
//        public View getView(int i, View view, ViewGroup viewGroup) {
//            view=View.inflate(getActivity(),R.layout.item_child,null);
//            return view;
//        }
    }
}
