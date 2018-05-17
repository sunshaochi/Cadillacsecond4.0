package cadillac.example.com.cadillac.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import java.io.File;

import cadillac.example.com.cadillac.MainActivity;
import cadillac.example.com.cadillac.http.CadillacUrl;
import cadillac.example.com.cadillac.view.MyAlertDialog;

/**
 * Created by bitch-1 on 2017/5/4.
 */

public class GeneralUtils {


    public void showIsDownLoad(final Context context) {
        MyAlertDialog dialog = new MyAlertDialog(context).builder();
        dialog.setTitle("发现新版本");
        dialog.setMsg("建议在WIFI环境下载");
        dialog.setCancelable(false);
        dialog.setPositiveButton("立即更新", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downLoadApk(context);//从服务器下载apk
            }
        });
//        dialog.setNegativeButton("稍后在说", null);
        dialog.show();
    }

    protected  void downLoadApk(final Context context) {
        final ProgressDialog pd;    //进度条对话框
        pd = new ProgressDialog(context);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMax(100);
        pd.setProgress(0);
        pd.setCanceledOnTouchOutside(false);
        pd.setMessage("正在下载更新");
        pd.show();
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    File file = DownLoadManager.getFileFromServer(CadillacUrl.DOWNAPK, pd);
                    sleep(500);
                    installApk(context, file);
                    pd.dismiss(); //结束掉进度条对话框
                }catch (Exception e){
                    Message msg = new Message();
                    msg.what = DOWN_ERROR;
                    msg.obj = context;
                    handler.sendMessage(msg);
                    e.printStackTrace();
                }

            }
        }.start();
    }
    private final int DOWN_ERROR = 1;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            switch (msg.what) {

                case DOWN_ERROR:
                    Context context = (Context) msg.obj;
                    //下载apk失败
                    Toast.makeText(context, "下载新版本失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    //安装apk
    protected void installApk(Context context, File file) {
        Intent intent = new Intent();
        //执行动作
        intent.setAction(Intent.ACTION_VIEW);
        //安装完成后可选择打开或者完成
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //执行的数据类型
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");//编者按：此处Android应为android，否则造成安装不了
        context.startActivity(intent);
    }

}
