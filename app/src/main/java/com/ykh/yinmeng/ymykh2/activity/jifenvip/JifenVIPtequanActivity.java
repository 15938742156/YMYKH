package com.ykh.yinmeng.ymykh2.activity.jifenvip;

import android.os.Build;
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


public class JifenVIPtequanActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_title;
    private LinearLayout ll_return;
    private WebView webview;
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_shouyishuoming);
    }

    @Override
    public void initViews() {
        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_title.setText("专享特权");
        ll_return = (LinearLayout)findViewById(R.id.ll_return);
        webview = (WebView) findViewById(R.id.webview);

    }

    @Override
    public void initListeners() {
        ll_return.setOnClickListener(this);
        webview.loadUrl(Urls.URL_JIFEN_VIP);
        webview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.clearCache(true);//清除缓存
        //不选择直接进入浏览器
        webview.setWebViewClient(new myWebclient());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webview.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

    }


    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_return:
                JifenVIPtequanActivity.this.finish();
                break;
        }
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
