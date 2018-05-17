package cadillac.example.com.cadillac.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;
import java.util.List;

import cadillac.example.com.cadillac.R;
import cadillac.example.com.cadillac.base.BaseActivity;
import cadillac.example.com.cadillac.bean.UserModle;

/**
 * 筛选车型activitty
 * Created by bitch-1 on 2017/3/23.
 */
public class DialogAct extends BaseActivity{
    @ViewInject(R.id.lv_cx)
    private ListView lv_cx;
    private List<String>list;//车型集合
    private String cx;//车型要返回去的
    private String chexin;//传递过来的车型

    CxAdapter cxAdapter;
    private int clickTemp = -1;//标识选择的Item

    @Override
    public void setLayout() {
        setContentView(R.layout.act_dialog);

    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("筛选");
        chexin=getIntent().getExtras().getString("chexin");

        list= UserModle.getUserBean().getModels();
        if(list.size()!=0) {
            list.add(0, "全部");
        }
        cxAdapter=new CxAdapter();
        if(list.size()!=0&&list!=null) {
            lv_cx.setAdapter(cxAdapter);
        }
        if(chexin.equals("全部车型")){
            cxAdapter.setSeclection(0);
            cx=list.get(0).toString();
        }else {
            for (int i=0;i<list.size();i++){
                if(chexin.equals(list.get(i))){
                    cxAdapter.setSeclection(i);
                    cx=list.get(i).toString();
                }
            }
        }

        lv_cx.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                cxAdapter.setSeclection(i);
                cxAdapter.notifyDataSetChanged();
                cx=list.get(i).toString();
            }
        });

    }
    @OnClick({R.id.view_back})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.view_back:
                Intent intent=new Intent();
                intent.putExtra("car",cx);
                this.setResult(0,intent);
                finish();
                break;
        }
    }

    private class CxAdapter extends BaseAdapter {

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
            TabHolder holder;
            if(view==null){
                view=View.inflate(getApplicationContext(),R.layout.item_lvcx,null);
                holder=new TabHolder();
                holder.tv_name= (TextView) view.findViewById(R.id.tv_name);
//                holder.iv_duigou= (TextView) view.findViewById(R.id.iv_duigou);
                holder.ll_item= (RelativeLayout) view.findViewById(R.id.ll_item);
                view.setTag(holder);
            }else {
                holder= (TabHolder) view.getTag();
            }
            if(list.size()!=0) {
                holder.tv_name.setText(list.get(i));
            }

            if(clickTemp==i){
                holder.ll_item.setBackgroundColor(Color.parseColor("#99ccff"));
                holder.iv_duigou.setVisibility(View.VISIBLE);

            }else {
                holder.ll_item.setBackgroundColor(Color.parseColor("#ffffff"));
                holder.iv_duigou.setVisibility(View.GONE);
            }

            return view;
        }
    }

    class TabHolder {
        private TextView tv_name;
        private TextView iv_duigou;
        private RelativeLayout ll_item;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(list.size()!=0&&list!=null&&list.get(0).equals("全部")){
            list.remove(0);
        }
    }
}
