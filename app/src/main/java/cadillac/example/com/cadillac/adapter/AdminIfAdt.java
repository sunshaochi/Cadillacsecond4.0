package cadillac.example.com.cadillac.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cadillac.example.com.cadillac.R;
import cadillac.example.com.cadillac.activity.admin.EachdataAct;
import cadillac.example.com.cadillac.bean.InfoBean;
import cadillac.example.com.cadillac.bean.UserModle;
import cadillac.example.com.cadillac.http.Manage.UserManager;
import cadillac.example.com.cadillac.http.ResultCallback;
import cadillac.example.com.cadillac.utils.MyLogUtils;
import cadillac.example.com.cadillac.view.MyAlertDialog;

/**
 * Created by bitch-1 on 2017/3/24.
 */
public class AdminIfAdt extends BaseAdapter {
    private Context context;
    private InfoBean infobean;
    private String time;
    private int type;
    private String role;

    public AdminIfAdt(Context context,InfoBean infobean,String time,int type) {
        this.context = context;
        this.infobean=infobean;
        this.time=time;
        this.type=type;
    }

    public void notify(InfoBean infobean){
        this.infobean=infobean;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if(infobean.getName()!=null){
            return infobean.getName().size();
        }else
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return infobean.getName().get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHodle hodle;
        if(view==null){
            view=View.inflate(context, R.layout.item_admininfo,null);
            hodle=new ViewHodle();
            hodle.tv_adminname= (TextView) view.findViewById(R.id.tv_adminname);
            hodle.iv_duanxin= (ImageView) view.findViewById(R.id.iv_duanxin);
            hodle.iv_phone= (ImageView) view.findViewById(R.id.iv_phone);
            hodle.iv_wtj= (ImageView) view.findViewById(R.id.iv_wtj);
            hodle.iv_tijiao= (ImageView) view.findViewById(R.id.iv_tijiao);
            view.setTag(hodle);
        }else {
            hodle= (ViewHodle) view.getTag();
        }
        if(infobean.getName()!=null) {
            hodle.tv_adminname.setText(infobean.getName().get(i));
            if (infobean.getState().get(i).equals("0")) {//未提交
                hodle.iv_tijiao.setVisibility(View.GONE);
            } else if (infobean.getState().get(i).equals("1")) {//已经提交
                hodle.iv_tijiao.setVisibility(View.VISIBLE);
            }
            hodle.iv_duanxin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //调取发短信接口
                    String content = "请及时提交" + time + "的数据【凯迪拉克】";
                    sendMessage("sendsms", "message", infobean.getTel().get(i), content);
                }
            });

            hodle.iv_phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + infobean.getTel().get(i)));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
            if(type==0){//一进来的页面
                if(UserModle.getUserBean().getRole().equals("总部人员")||UserModle.getUserBean().getRole().equals("管理人员")){
                    role="大区经理";
                }

                if(UserModle.getUserBean().getRole().equals("大区经理")||UserModle.getUserBean().getRole().equals("MSS人员")){
                    role="小区经理";
                }

                if(UserModle.getUserBean().getRole().equals("小区经理")||UserModle.getUserBean().getRole().equals("集团人员")){
                    role="经销商总经理";
                }
            }else if (type==1){//第二个界面
                if(UserModle.getUserBean().getRole().equals("总部人员")||UserModle.getUserBean().getRole().equals("管理人员")){
                    role="小区经理";
                }

                if(UserModle.getUserBean().getRole().equals("大区经理")||UserModle.getUserBean().getRole().equals("MSS人员")){
                    role="经销商总经理";
                }
            }else if(type==2){//第三个界面
                    role="经销商总经理";

            }
            hodle.iv_wtj.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, EachdataAct.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("name",infobean.getName().get(i));
                    intent.putExtra("time",time);
                    intent.putExtra("role",role);
                    context.startActivity(intent);

                }
            });
        }
        return view;
    }

    /**发送短信
     * @param op sendsms
     * @param obj message
     * @param telephone 电话
     * @param content  内容
     */
    private void sendMessage(String op, String obj, String telephone, final String content) {
        UserManager.getUserManager().sendMessage(op, obj, telephone, content, new ResultCallback<String>() {
            @Override
            public void onError(int status, String errorMsg) {

            }

            @Override
            public void onResponse(String response) {
               if(response.contains(" <message>ok</message>")){
                   new MyAlertDialog(context).builder().setMsg("短信发送成功").
                           setNegativeButton("OK",null).show();
               }
            }
        });
    }

    class ViewHodle{
        private ImageView iv_duanxin,iv_phone,iv_wtj,iv_tijiao;
        private TextView tv_adminname;
    }


}
