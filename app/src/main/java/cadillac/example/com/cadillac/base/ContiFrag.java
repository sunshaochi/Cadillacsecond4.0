package cadillac.example.com.cadillac.base;

import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;

import cadillac.example.com.cadillac.R;

/**
 * Created by iris on 2017/12/18.
 */

public abstract class ContiFrag extends Fragment {
    private TextView tvTitle;
    /**
     * 返回的view对象
     */
    protected View view;
    //用此Activity，防止getContext为空
    protected Activity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = initView(inflater,container,savedInstanceState);
        // 注入控件
        ViewUtils.inject(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData(savedInstanceState);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity=(Activity)context;

    }

    /**
     * 初始化View 对象
     *
     * @param inflater
     *            view填充器 需要布局文件
     * @return 返回view 对象
     */
    public abstract View initView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState);

    /**
     * 初始化数据
     *
     * @param savedInstanceState
     */
    public abstract void initData(Bundle savedInstanceState);

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
        Intent intent = new Intent(getContext(), pClass);
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
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        if (title != null) {
            tvTitle.setText(title);
        }
    }


}
