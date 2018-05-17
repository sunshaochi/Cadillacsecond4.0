package cadillac.example.com.cadillac.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import cadillac.example.com.cadillac.R;
import cadillac.example.com.cadillac.adapter.UninPurAdapt;
import cadillac.example.com.cadillac.base.BaseActivity;
import cadillac.example.com.cadillac.http.Manage.UserManager;
import cadillac.example.com.cadillac.http.ResultCallback;
import cadillac.example.com.cadillac.utils.CadillacUtils;
import cadillac.example.com.cadillac.utils.MyToastUtils;

/**未录入界面
 * Created by bitch-1 on 2017/7/14.
 */

public class UninPutAct extends BaseActivity{
    @ViewInject(R.id.lv_wlr)
    private ListView lv_wlr;//未录入列表
    private UninPurAdapt adapt;
    private String inputtimeid;
    private String inputtype;
    private List<Map<String,String>>datalist;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_uninput);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("未录入经销商名单");
        inputtimeid=getIntent().getExtras().getString("inputtimeid");
        inputtype=getIntent().getExtras().getString("inputtype");
        if(inputtype.equals("0")){//经销商零售预测
          getUninput(CadillacUtils.getCurruser().getUserName(),inputtimeid,"0");//获取未录入的数据
        }else if(inputtype.equals("2")){//裸车毛利
          getUninput(CadillacUtils.getCurruser().getUserName(),inputtimeid,"2");
        }

    }

    private void getUninput(String userName,String inputTimeId,String inputType) {
        UserManager.getUserManager().getUnInput(userName, inputTimeId, inputType, new ResultCallback<String>() {
            @Override
            public void onError(int status, String errorMsg) {
                MyToastUtils.showShortToast(UninPutAct.this,errorMsg);
            }

            @Override
            public void onResponse(String response) {
                Gson gosn=new Gson();
                try {
                    JSONObject object=new JSONObject(response);
                    String code=object.getString("code");
                    if(code.equals("success")){//成功
                        JSONObject data=object.getJSONObject("obj");
                        JSONArray list=data.getJSONArray("list");
                        if(list.length()>0){
                            datalist=gosn.fromJson(list.toString(),new TypeToken<List<Map<String,String>>>(){}.getType());
                            if(datalist.size()>0){
                                adapt=new UninPurAdapt(UninPutAct.this,datalist);
                                lv_wlr.setAdapter(adapt);
                            }
                        }
                    }else {

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }


}
