package com.ist.cadillacpaltform.UI.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ist.cadillacpaltform.R;
import com.ist.cadillacpaltform.UI.fragment.IntegrationFragment;
import com.ist.cadillacpaltform.UI.fragment.PlatformFragment;

/**
 * Created by dearlhd on 2016/12/16.
 */
public class HomePageActivity extends FragmentActivity {

    private PlatformFragment mPlatformFragment;
    private IntegrationFragment mIntegrationFragment;

    private ImageView mIvData;
    private ImageView mIvStatement;
    private ImageView mIvPlatform;
    private ImageView mIvSetting;

    private OnBackListener mOnBackListener;

    public interface OnBackListener {
        boolean onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        initView();
        mIvPlatform.performClick();
    }

    /* ---------------------- 下面是针对按下返回键的逻辑 ------------------------*/
    // 定义一个变量，来标识是否退出
    private static boolean isExit = false;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mOnBackListener != null) {
                if (mOnBackListener.onBackPressed()) {
                    return true;
                }
            }

            if (!isExit) {
                isExit = true;
                Toast.makeText(HomePageActivity.this, "再按一次退出程序",
                        Toast.LENGTH_SHORT).show();
                // 利用handler延迟发送更改状态信息
                mHandler.sendEmptyMessageDelayed(0, 2000);
            } else {
                moveTaskToBack(true);
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void setOnBackListener(OnBackListener listener) {
        mOnBackListener = listener;
    }

    private void initView() {
        mIvData = (ImageView) findViewById(R.id.iv_tab_data);
        mIvStatement = (ImageView) findViewById(R.id.iv_tab_statement);
        mIvPlatform = (ImageView) findViewById(R.id.iv_tab_platform);
        mIvSetting = (ImageView) findViewById(R.id.iv_tab_setting);

        mIvPlatform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initTabIcon();
                mIvPlatform.setImageResource(R.drawable.icon_platform_selected);
                changeFragment(2);
            }
        });
        mIvSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initTabIcon();
                mIvSetting.setImageResource(R.drawable.icon_setting_selected);
                changeFragment(3);
            }
        });
    }

    private void changeFragment(int index) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        hideFragment(fragmentTransaction);

        //
        if (mPlatformFragment != null) {
            mPlatformFragment.closePopupWindow();
        }

        switch (index) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                if (mPlatformFragment == null) {
                    mPlatformFragment = new PlatformFragment();
                    fragmentTransaction.add(R.id.fl_content, mPlatformFragment);
                } else {
                    fragmentTransaction.show(mPlatformFragment);
                }
                break;
            case 3:
                if (mIntegrationFragment == null) {
                    mIntegrationFragment = new IntegrationFragment();
                    fragmentTransaction.add(R.id.fl_content, mIntegrationFragment);
                } else {
                    fragmentTransaction.show(mIntegrationFragment);
                }

                break;
        }

        fragmentTransaction.commit();
    }

    private void hideFragment(FragmentTransaction fragmentTransaction) {
        if (mPlatformFragment != null) {
            fragmentTransaction.hide(mPlatformFragment);
        }
        if (mIntegrationFragment != null) {
            fragmentTransaction.hide(mIntegrationFragment);
        }
    }

    private void initTabIcon () {
        mIvData.setImageResource(R.drawable.icon_data_unselected);
        mIvStatement.setImageResource(R.drawable.icon_statement_unselected);
        mIvPlatform.setImageResource(R.drawable.icon_platform_unselected);
        mIvSetting.setImageResource(R.drawable.icon_setting_unselected);
    }

}
