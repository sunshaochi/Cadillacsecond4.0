package cadillac.example.com.cadillac.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import cadillac.example.com.cadillac.R;
import cadillac.example.com.cadillac.http.Manage.UserManager;
import cadillac.example.com.cadillac.http.ResultCallback;
import cadillac.example.com.cadillac.utils.CadillacUtils;
import cadillac.example.com.cadillac.utils.DateAndTimeUtils;
import cadillac.example.com.cadillac.utils.MyToastUtils;

/**
 * Created by iris on 2018/1/24.
 */

public class ShareDialog {
    private Context context;
    private Display display;
    private Dialog dialog;
    private String time;
    private String dateType;
    private String queryType;//传递过来的




    public ShareDialog(Context context,String time,String queryType) {
        this.context = context;
        this.time=time;
        this.queryType=queryType;
        if(queryType.equals("0")){//表示天
            dateType="3";
        }else if(queryType.equals("1")){//表示月
            dateType="2";
        }else if(queryType.equals("2")){//表示年
            dateType="0";
        }else if(queryType.equals("3")){//表示季度
            dateType="1";
        }

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public ShareDialog build(){
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_share, null);
        // 设置Dialog最小宽度为屏幕宽度
        view.setMinimumWidth(display.getWidth());
        TextView tv_fs= (TextView) view.findViewById(R.id.tv_fs);
        TextView tv_qx= (TextView) view.findViewById(R.id.tv_qx);

        dialog=new Dialog(context,R.style.ActionSheetDialogStyle);
        dialog.setContentView(view);
        Window dilogwindow=dialog.getWindow();
        dilogwindow.setGravity(Gravity.CENTER);
        dialog.setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams lp = dilogwindow.getAttributes();
        lp.x = 0;
        lp.y = 0;
        dilogwindow.setAttributes(lp);

        tv_qx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        tv_fs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              postDx(CadillacUtils.getCurruser().getUserName(),change(time),dateType);//调取发送短信的功能
            }
        });
        return this;
    }


    /**
     * 时间的匹配
     * @param time
     * @return
     */
    private String change(String time) {
        String pstime=time;
        if(time.contains("一月")&&time.length()==6){
            pstime=time.replace("一月","-01");
        }else if(time.contains("二月")&&time.length()==6){
            pstime=time.replace("二月","-02");
        }else if(time.contains("三月")){
            pstime=time.replace("三月","-03");
        }else if(time.contains("四月")){
            pstime=time.replace("四月","-04");
        }else if(time.contains("五月")){
            pstime=time.replace("五月","-05");
        }else if(time.contains("六月")){
            pstime=time.replace("六月","-06");
        }else if(time.contains("七月")){
            pstime=time.replace("七月","-07");
        }else if(time.contains("八月")){
            pstime=time.replace("八月","-08");
        }else if(time.contains("九月")){
            pstime=time.replace("九月","-09");
        }else if(time.contains("十月")){
            pstime=time.replace("十月","-10");
        }else if(time.contains("十一月")){
            pstime=time.replace("十一月","-11");
        }else if(time.contains("十二月")){
            pstime=time.replace("十二月","-12");
        }else if(time.contains("第一季度")){
            pstime=time.replace("第一季度","Q1");
        }else if(time.contains("第二季度")){
            pstime=time.replace("第二季度","Q2");
        }else if(time.contains("第三季度")){
            pstime=time.replace("第三季度","Q3");
        }else if(time.contains("第四季度")){
            pstime=time.replace("第四季度","Q4");
        }
        return pstime;
    }


    private void postDx(String userName,String dateTime,String dateType) {
        UserManager.getUserManager().dealerCount(userName,dateTime,dateType,new ResultCallback<String>() {
            @Override
            public void onError(int status, String errorMsg) {
                MyToastUtils.showShortToast(context,errorMsg);
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String code=jsonObject.getString("code");
                    String message=jsonObject.getString("message");
                    if(code.equals("success")){
                        dialog.dismiss();
                        String obj=jsonObject.getString("obj");
                        String content=obj.replace("&","\n");

                        Uri smsToUri = Uri.parse("smsto:"+"");
                        Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
                        intent.putExtra("sms_body", content);
                        context.startActivity(intent);
                    }else {
                        MyToastUtils.showShortToast(context,message);
                    }
                } catch (JSONException e) {
                    MyToastUtils.showShortToast(context,"解析异常");
                    e.printStackTrace();
                }


            }
        });
    }


    public void show(){
        dialog.show();
    }
}
