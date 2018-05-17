package com.ist.cadillacpaltform.UI.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.ist.cadillacpaltform.R;
import com.ist.cadillacpaltform.SDK.bean.Authorization;
import com.ist.cadillacpaltform.SDK.util.SQLiteHelper;
import com.ist.cadillacpaltform.UI.fragment.DealerManageFragment;
import com.ist.cadillacpaltform.UI.fragment.MacManageFragment;

/**
 * Created by dearlhd on 2016/12/29.
 */
public class ManagementActivity extends FragmentActivity {


    enum ManagerType { MAC_MANAGER, DEALER_MANAGER}

    ManagerType mManagerType = ManagerType.DEALER_MANAGER;

    private DealerManageFragment mDealerManageFragment;
    private MacManageFragment mMacManageFragment;

    private  OnBackListener mOnBackListener;

    public interface OnBackListener {
        boolean onBackPressed();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_manage);
        judgeAuth();
        initView();
    }

    public void setOnBackListener (OnBackListener listener) {
        mOnBackListener = listener;
    }

    private void judgeAuth () {
        SQLiteHelper helper = new SQLiteHelper();
        Authorization authorization = helper.getAuth();

        if (authorization == null) {
            onBackPressed();   // 重新登录
            return;
        }

        switch (authorization.type){
            case 0:
                mManagerType = ManagerType.DEALER_MANAGER;
                break;
            case 1:
            case 2:
                mManagerType = ManagerType.MAC_MANAGER;
                break;

            default:
                break;
        }
    }

    private void initView() {
        showFragment();
    }

    private void showFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (mManagerType) {
            case MAC_MANAGER:
                if (mMacManageFragment == null) {
                    mMacManageFragment = new MacManageFragment();
                    fragmentTransaction.add(R.id.fl_content, mMacManageFragment);
                } else {
                    fragmentTransaction.show(mMacManageFragment);
                }
                break;

            case DEALER_MANAGER:
                if (mDealerManageFragment == null) {
                    mDealerManageFragment = new DealerManageFragment();
                    fragmentTransaction.add(R.id.fl_content, mDealerManageFragment);
                } else {
                    fragmentTransaction.show(mDealerManageFragment);
                }
                break;

            default:
                break;
        }

        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed () {
        if (mOnBackListener != null) {
            if (mOnBackListener.onBackPressed()) {
                return;
            }
        }

        super.onBackPressed();
    }
}
