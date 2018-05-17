package com.ist.cadillacpaltform.UI.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.ist.cadillacpaltform.R;
import com.ist.cadillacpaltform.SDK.bean.Authorization;
import com.ist.cadillacpaltform.SDK.util.SQLiteHelper;
import com.ist.cadillacpaltform.UI.activity.GradeDetailActivity;
import com.ist.cadillacpaltform.UI.activity.LoginActivity;
import com.ist.cadillacpaltform.UI.activity.POSMHomeActivity;

import java.util.Calendar;

/**
 * Created by dearlhd on 2017/2/23.
 */
public class IntegrationFragment extends Fragment {

    protected View mRoot;

    private TextView mTvLogout;

    private ImageView mTvPosm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mRoot == null) {
            Fresco.initialize(getContext());//初始化Fresco框架
            mRoot = inflater.inflate(R.layout.fragment_integration, container, false);
            initView();
        }

        ViewGroup parent = (ViewGroup) mRoot.getParent();
        if (parent != null) {
            parent.removeView(mRoot);
        }

        return mRoot;
    }

    private void initView() {
        mTvLogout = (TextView) mRoot.findViewById(R.id.tv_logout);

        mTvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteHelper helper = new SQLiteHelper();
                try {
                    helper.setAuth(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(getContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        mTvPosm = (ImageView) mRoot.findViewById(R.id.tv_posm_entry);
        mTvPosm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteHelper helper = new SQLiteHelper();
                Authorization authorization = helper.getAuth();
                int type = authorization.type;
                if (type == 0) {
                    Intent intent = new Intent(getActivity(), GradeDetailActivity.class);
                    Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int quarter = (calendar.get(Calendar.MONTH)+1) / 4 + 1;
                    intent.putExtra("year", year);
                    intent.putExtra("quarter", quarter);
                    intent.putExtra("isRectify", true);
                    startActivity(intent);
                } else if (type == 1 || type == 2 || type == 3 || type == 4 || type == 5 || type == 6) {
                    Intent intent = new Intent(getActivity(), POSMHomeActivity.class);
                    startActivity(intent);
                }

            }
        });
    }


}
