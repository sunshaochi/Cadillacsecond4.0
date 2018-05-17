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
import cadillac.example.com.cadillac.base.BaseFrag;
import cadillac.example.com.cadillac.bean.SalseDateBean;
import cadillac.example.com.cadillac.http.Manage.UserManager;
import cadillac.example.com.cadillac.http.ResultCallback;
import cadillac.example.com.cadillac.utils.MyToastUtils;
import cadillac.example.com.cadillac.view.CKITaskSETimeScrollView;
import cadillac.example.com.cadillac.view.CProgressDialog;

/**
 * Created by bitch-1 on 2017/3/24.
 */
public class SecondadminFra  extends BaseFrag {
    @ViewInject(R.id.ck)
    private CKITaskSETimeScrollView ck;
    private String name;
    private String time;
    private String role;
    private Dialog dialog;
    private Gson gson;
    private List<SalseDateBean> list;
    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fra_secondadmin,null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
//        lv.setAdapter(new MyAdapter());
        Bundle bundle=getArguments();
        gson=new Gson();
        name=bundle.getString("name");
        time=bundle.getString("time");
        role=bundle.getString("role");
        dialog= CProgressDialog.createLoadingDialog(getActivity(),false);

        dialog.show();
        getSalesData(time,name,role,"否");//获取车展数据

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
                    MyToastUtils.showShortToast(getActivity(),"后台异常");
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
            View view=View.inflate(getActivity(),R.layout.item_czchild,null);
            TextView tv_chexin= (TextView) view.findViewById(R.id.tv_cz);
            TextView tv_dindan= (TextView) view.findViewById(R.id.tv_dindan);
            TextView tv_ylj= (TextView) view.findViewById(R.id.tv_ljdd);
            tv_chexin.setText(list.get(i).getModels()+"");//车型
            tv_dindan.setText(list.get(i).getOrder()+"");//订单
            tv_ylj.setText(list.get(i).getTotalOrder()+"");//月累计订单
            return view;

        }



    }
}
