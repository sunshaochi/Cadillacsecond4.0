package cadillac.example.com.cadillac.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import cadillac.example.com.cadillac.R;


/**
 * 基础类
 */
public abstract class BaseActivity extends FragmentActivity {
    private TextView tv_title;
    private RelativeLayout rlRight,rl_title,rl_ivright;
    private TextView tvRight;
    private ImageView iv_back,iv_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            savedInstanceState.putParcelable("android:support:fragments",null);
        }
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setLayout();
        // 注入控件
        ViewUtils.inject(this);
        init(savedInstanceState);
    }

    /**
     * 设置布局
     */
    public abstract void setLayout();

    /**
     * 填充数据
     */
    public abstract void init(Bundle savedInstanceState);


    /**
     * 通过类名启动Activity
     *
     * @param pClass
     */
    protected void openActivity(Class<?> pClass) {
        openActivity(pClass, null);
    }

    /**
     * 通过类名启动Activity，并且含有Bundle数据
     *
     * @param pClass
     * @param pBundle
     */
    protected void openActivity(Class<?> pClass, Bundle pBundle) {
        Intent intent = new Intent(this, pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivity(intent);
    }


    /**
     * 设置顶部标题
     *
     * @param title
     */
    public void setTopTitle(String title) {
        tv_title = (TextView) findViewById(R.id.tv_title);
        if (title != null) {
            tv_title.setText(title);
        }
    }


    /**
     * 设置右边字体
     * @param rightText
     * @param onClick
     */
    public void setRight(String rightText, View.OnClickListener onClick){
        rlRight= (RelativeLayout) findViewById(R.id.rlRight);
        tvRight= (TextView) findViewById(R.id.tvRight);
        rlRight.setVisibility(View.VISIBLE);
        tvRight.setText(rightText);
        rlRight.setOnClickListener(onClick);
    }


    /**
     * 设置右边字体
     * @param id
     * @param onClick
     */
    public void setRightimage(int id, View.OnClickListener onClick){
        rl_ivright= (RelativeLayout) findViewById(R.id.rl_ivright);
        iv_right= (ImageView) findViewById(R.id.iv_right);
        rl_ivright.setVisibility(View.VISIBLE);
        iv_right.setImageResource(id);
        rl_ivright.setOnClickListener(onClick);
    }






    /**
     * 返回
     *
     * @param view
     */
    public void goback(View view) {
        finish();
    }

    protected void onResume() {
        super.onResume();

    }
    protected void onPause() {
        super.onPause();

    }
}
