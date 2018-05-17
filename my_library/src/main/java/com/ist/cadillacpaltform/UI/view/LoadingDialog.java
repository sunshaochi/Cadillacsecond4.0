package com.ist.cadillacpaltform.UI.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ist.cadillacpaltform.R;

/**
 * Created by dearlhd on 2017/3/15.
 */
public class LoadingDialog extends Dialog {
    private TextView mTextView;

    public LoadingDialog(Context context) {
        super(context, R.style.loadingDialogStyle);
    }

    public LoadingDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected LoadingDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_loading_dialog);
        mTextView = (TextView)this.findViewById(R.id.tv_dialog_content);
        mTextView.setText("正在生成试题...");
        LinearLayout linearLayout = (LinearLayout)this.findViewById(R.id.ll_loading_dialog);
        linearLayout.getBackground().setAlpha(210);
    }

}
