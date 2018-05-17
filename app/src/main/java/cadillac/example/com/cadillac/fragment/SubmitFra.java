package cadillac.example.com.cadillac.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import cadillac.example.com.cadillac.R;
import cadillac.example.com.cadillac.activity.SmallHomeAct;
import cadillac.example.com.cadillac.activity.admin.AdminInfoAct;
import cadillac.example.com.cadillac.adapter.AdminIfAdt;
import cadillac.example.com.cadillac.base.BaseFrag;
import cadillac.example.com.cadillac.bean.ResultsNewBean;
import cadillac.example.com.cadillac.bean.ScreenBean;
import cadillac.example.com.cadillac.http.Manage.UserManager;
import cadillac.example.com.cadillac.http.ResultCallback;
import cadillac.example.com.cadillac.utils.CadillacUtils;
import cadillac.example.com.cadillac.utils.DateAndTimeUtils;
import cadillac.example.com.cadillac.utils.MyToastUtils;

/**
 * Created by iris on 2018/1/4.
 */

public class SubmitFra extends BaseFrag{
    @ViewInject(R.id.lv_info)
    private ListView lv_info;
    private String type;
    private List<ScreenBean>sclist;
    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fra_submit,null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
//      type=getArguments().getString("type");
    }

    /**
     * 给界面赋值
     * @param sclist
     */
    public void upDateinfo(List<ScreenBean> sclist) {
     lv_info.setAdapter(new MyAdapter(sclist));
    }


    public class MyAdapter extends BaseAdapter{
        private List<ScreenBean>beanlist;


        public MyAdapter(List<ScreenBean> beanlist) {
            this.beanlist = beanlist;
        }

        @Override
        public int getCount() {
            return beanlist.size();
        }

        @Override
        public Object getItem(int i) {
            return beanlist.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            view=View.inflate(getActivity(), R.layout.item_admininfo,null);
            TextView tv_adminname= (TextView) view.findViewById(R.id.tv_adminname);
            TextView tv_untijinum= (TextView) view.findViewById(R.id.tv_untjnum);
            ImageView iv_tj= (ImageView) view.findViewById(R.id.iv_tj);
            RelativeLayout rl_dj= (RelativeLayout) view.findViewById(R.id.rl_dj);
            RelativeLayout rl_dx= (RelativeLayout) view.findViewById(R.id.rl_dx);
            RelativeLayout rl_dh= (RelativeLayout) view.findViewById(R.id.rl_dh);
            RelativeLayout rl_ts= (RelativeLayout) view.findViewById(R.id.rl_ts);
            View view_dj=view.findViewById(R.id.view_dj);
            RelativeLayout rl_num= (RelativeLayout) view.findViewById(R.id.rl_num);
            tv_adminname.setText(beanlist.get(i).getName());//名字
            if(!TextUtils.isEmpty(beanlist.get(i).getUnSubmitCnt())){
                tv_untijinum.setText(beanlist.get(i).getUnSubmitCnt());
                if(beanlist.get(i).getUnSubmitCnt().equals("0")){
                    tv_untijinum.setVisibility(View.GONE);
                    iv_tj.setVisibility(View.VISIBLE);
                }else {
                    tv_untijinum.setVisibility(View.VISIBLE);
                    iv_tj.setVisibility(View.GONE);
                }
            }




//            if(TextUtils.isEmpty(beanlist.get(i).getSubmitDailyData())){//如果为空
//                rl_dj.setVisibility(View.INVISIBLE);
//                view_dj.setVisibility(View.INVISIBLE);
//            }else {
//                if (beanlist.get(i).getSubmitDailyData().equals("N")) {
//                    rl_dj.setVisibility(View.INVISIBLE);
//                    view_dj.setVisibility(View.INVISIBLE);
//                } else {
//                    rl_dj.setVisibility(View.VISIBLE);
//                    view_dj.setVisibility(View.VISIBLE);
//                }
//            }

            tv_adminname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    screenRole(beanlist.get(i).getId()+"","N");//换角色查看
                }
            });

            rl_num.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    screenRole(beanlist.get(i).getId()+"","N");//换角色查看
                }
            });

            rl_ts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putString("name",beanlist.get(i).getName());
                bundle.putString("fullcode",beanlist.get(i).getFullCode());
                openActivity(SmallHomeAct.class,bundle);
                }
            });

            rl_dx.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String content = "请及时提交" + DateAndTimeUtils.getCurrentTime("yyyy-MM-dd") + "的数据【凯迪拉克】";
                    Uri smsToUri = Uri.parse("smsto:"+beanlist.get(i).getLinkTel());
                    Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
                    intent.putExtra("sms_body", content);
                    getActivity().startActivity(intent);
                }
            });

            rl_dh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:13917702726"));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//这步骤必须带上
                    getActivity().startActivity(intent);
                }
            });
            return view;
        }
    }

    /**
     * 获取下一级
     * @param parentId
     * @param isDelete
     */
    private void screenRole(final String parentId, final String isDelete) {
        UserManager.getUserManager().screenRole(parentId, isDelete, new ResultCallback<ResultsNewBean<List<ScreenBean>>>() {
            @Override
            public void onError(int status, String errorMsg) {
                MyToastUtils.showShortToast(getActivity(),errorMsg);
            }

            @Override
            public void onResponse(ResultsNewBean<List<ScreenBean>> response) {
                sclist=response.getObj();
                if(sclist!=null&&sclist.size()>0){//说明还有下一级别
                    Bundle bundle=new Bundle();
                    bundle.putString("id",parentId);
                    openActivity(AdminInfoAct.class,bundle);
                }
            }
        });
    }
}
