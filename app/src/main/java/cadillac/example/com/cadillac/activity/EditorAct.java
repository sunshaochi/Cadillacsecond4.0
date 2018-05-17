package cadillac.example.com.cadillac.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import cadillac.example.com.cadillac.R;
import cadillac.example.com.cadillac.base.BaseActivity;

/**
 * Created by bitch-1 on 2017/6/28.
 */

public class EditorAct extends BaseActivity{
    @ViewInject(R.id.et_banji)
    private EditText et_banji;
    private String type;
    private String remark;//备注
    private  String financial;//政策
    @Override
    public void setLayout() {
        setContentView(R.layout.act_editor);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        Intent intent=getIntent();
        type=intent.getStringExtra("type");
        if(!TextUtils.isEmpty(type)){
            if(type.equals("1")){//编辑
                remark=intent.getStringExtra("remark");
                if(!TextUtils.isEmpty(remark)){
                    et_banji.setText(remark);
                }else {
                    et_banji.setHint("请填写备注");
                }

            }else {//编辑政策
                financial=intent.getStringExtra("financial");
                if(!TextUtils.isEmpty(financial)){
                    et_banji.setText(financial);
                }else {
                    et_banji.setHint("请填写金融政策");
                }
            }
        }

    }

    @OnClick({R.id.iv_finsh,R.id.tv_bc})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.iv_finsh:
                finish();
                break;
            case R.id.tv_bc:
                String text=et_banji.getText().toString();
                Intent intentp=new Intent();
                intentp.putExtra("result",text);
                setResult(RESULT_OK,intentp);
                finish();
                break;
        }
    }


}
