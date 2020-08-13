package com.ykh.yinmeng.ymykh2.activity.xieyi;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ykh.yinmeng.ymykh2.R;
import com.ykh.yinmeng.ymykh2.activity.BaseActivity;
import com.ykh.yinmeng.ymykh2.utils.Urls;


public class XieyiShimingActivity extends BaseActivity {

    private LinearLayout ll_return;
    private TextView tv_title;
    private WebView webview;
    String url;


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);

    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_xieyi_shiming);
    }

    @Override
    public void initViews() {
        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_title.setText("隐私保护协议");
        ll_return = (LinearLayout) findViewById(R.id.ll_return);
        webview = (WebView) findViewById(R.id.webview);

    }

    @Override
    public void initListeners() {
        ll_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XieyiShimingActivity.this.finish();
            }
        });
    }

    @Override
    public void initData() {
        url= Urls.URL_PROTOCOL_SHIMING;
        webview.loadUrl(url);
        webview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webview.getSettings().setJavaScriptEnabled(true);
        //不选择直接进入浏览器
        webview.setWebViewClient (new myWebclient());
        webview.clearCache(true);
    }



    private class myWebclient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            if(webview.canGoBack())
            {
                webview.goBack();//返回上一页面
                return true;
            }
            else
            {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
