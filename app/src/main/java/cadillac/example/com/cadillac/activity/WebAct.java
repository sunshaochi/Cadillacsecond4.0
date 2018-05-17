package cadillac.example.com.cadillac.activity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lidroid.xutils.view.annotation.ViewInject;

import cadillac.example.com.cadillac.R;
import cadillac.example.com.cadillac.base.BaseActivity;

/**
 * Created by bitch-1 on 2017/3/30.
 */
public class WebAct extends BaseActivity {
    @ViewInject(R.id.wv)
    private WebView wv;

    private String url;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_web);
    }

    @Override
    public void init(Bundle savedInstanceState) {
      url="file:///android_asset/agreement.html";

        WebSettings webSettings = wv.getSettings();
//        webSettings.setUseWideViewPort(true);//将图片调整到适合webview的大小
//        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);
//        webSettings.setBuiltInZoomControls(true);//// 设置显示缩放按钮
//        webSettings.setSupportZoom(true);//支持缩放
//        wv.requestFocusFromTouch();
        wv.loadUrl(url);
        wv.setWebViewClient(new WebViewClient());
    }
}
