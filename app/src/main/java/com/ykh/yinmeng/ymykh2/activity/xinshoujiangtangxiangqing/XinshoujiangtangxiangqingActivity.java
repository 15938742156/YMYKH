package com.ykh.yinmeng.ymykh2.activity.xinshoujiangtangxiangqing;

import android.Manifest;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ykh.yinmeng.ymykh2.R;
import com.ykh.yinmeng.ymykh2.activity.BaseActivity;
import com.ykh.yinmeng.ymykh2.utils.ImageTools;
import com.ykh.yinmeng.ymykh2.utils.PermissionListener;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.dialog.DialogUtil;

import java.util.List;

import cn.sharesdk.onekeyshare.OnekeyShare;

public class XinshoujiangtangxiangqingActivity extends BaseActivity implements XinshoujiangtangxiangqingMVPView {

    private LinearLayout ll_return,ll_menu;
    private TextView tv_title,tv_menu;
    private WebView webview;

    private Dialog loadingDialog;
    public XinshoujiangtangxiangqingPresenter<XinshoujiangtangxiangqingMVPView> presenter;
    String title = null;
    String text = null;
    String url = null;


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        presenter = new XinshoujiangtangxiangqingPresenterImp<>();
        presenter.onAttach(this);
        presenter.onInit(this);

    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_xinshoujiangtangxiangqing);
    }

    @Override
    public void initViews() {
        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_title.setText("新手讲堂详情");
        tv_menu = (TextView)findViewById(R.id.tv_menu);
        tv_menu.setBackgroundResource(R.mipmap.share);
        ll_return = (LinearLayout)findViewById(R.id.ll_return);
        ll_menu = (LinearLayout)findViewById(R.id.ll_menu);
        ll_menu.setVisibility(View.VISIBLE);
        webview = (WebView) findViewById(R.id.webview);
        loadingDialog = DialogUtil.createLoadingDialog(this, "");

    }

    @Override
    public void initListeners() {
        ll_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XinshoujiangtangxiangqingActivity.this.finish();
            }
        });
        tv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestRuntimePermission(permissions, new PermissionListener() {
                    @Override
                    public void onGranted() {
                        presenter.onToggleYaoqingBtn(XinshoujiangtangxiangqingActivity.this);
                    }

                    @Override
                    public void onDenied(List<String> deniedPermissions) {
//                    ToastUtils.showToast(mContext, "权限被拒绝,请进入设置打开权限", Toast.LENGTH_SHORT);
                    }
                });

            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void getTitleSuccess(String title) {
        this.title = title;
    }

    @Override
    public void getTextSuccess(String text) {
        this.text = text;
    }

    @Override
    public void showShareView(String title, String text, String url) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，微信、QQ和QQ空间等平台使用
        oks.setTitle(title);
        // titleUrl QQ和QQ空间跳转链接
        oks.setTitleUrl(url);
//        // text是分享文本，所有平台都需要这个字段
        oks.setText(text);
//        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath(ImageTools.getLogoPath());
//        //url在微信、微博，Facebook等平台中使用
        oks.setUrl(url);
        // comment是我对这条分享的评论，仅在人人网使用
//        oks.setComment("我是测试评论文本");
        // 启动分享GUI
        oks.show(this);
    }

    @Override
    public void getUrlSuccess(String url) {
        this.url = url;
        webview.loadUrl(url);
        webview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.clearCache(true);//清除缓存
        //不选择直接进入浏览器
        webview.setWebViewClient (new myWebclient());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webview.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }

    @Override
    public void showDialog() {
        loadingDialog.show();
    }

    @Override
    public void dismissDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
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
