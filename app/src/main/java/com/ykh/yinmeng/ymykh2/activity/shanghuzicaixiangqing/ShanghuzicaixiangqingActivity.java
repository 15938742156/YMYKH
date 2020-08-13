package com.ykh.yinmeng.ymykh2.activity.shanghuzicaixiangqing;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ykh.yinmeng.ymykh2.R;
import com.ykh.yinmeng.ymykh2.activity.BaseActivity;
import com.ykh.yinmeng.ymykh2.activity.querendingdan.QuerendingdanActivity;
import com.ykh.yinmeng.ymykh2.activity.xinshoujiangtangxiangqing.XinshoujiangtangxiangqingActivity;
import com.ykh.yinmeng.ymykh2.activity.zhifufangshi.ZhifufangshiActivity;
import com.ykh.yinmeng.ymykh2.utils.ImageTools;
import com.ykh.yinmeng.ymykh2.utils.PermissionListener;
import com.ykh.yinmeng.ymykh2.utils.ToastUtils;
import com.ykh.yinmeng.ymykh2.utils.Urls;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.dialog.DialogUtil;

import java.util.List;

import cn.sharesdk.onekeyshare.OnekeyShare;


public class ShanghuzicaixiangqingActivity extends BaseActivity implements ShanghuzicaiMVPView {

    private LinearLayout ll_return,ll_menu;
    private TextView tv_title,tv_menu;
    private WebView webview;
    private EditText et_num;
    private Button bt_buy;

    private Dialog loadingDialog;
    public ShanghuzicaiPresenter<ShanghuzicaiMVPView> presenter;
    String title;
    String money;
    String yun;
    String id;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        presenter = new ShanghuzicaiPresenterImp<>();
        presenter.onAttach(this);
        presenter.onInit(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_shanghuzicai_datel);
    }

    @Override
    public void initViews() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("商户自采详情");
        ll_return = (LinearLayout) findViewById(R.id.ll_return);
        tv_menu = (TextView)findViewById(R.id.tv_menu);
        tv_menu.setBackgroundResource(R.mipmap.share);
        ll_menu = (LinearLayout)findViewById(R.id.ll_menu);
        ll_menu.setVisibility(View.VISIBLE);
        webview = (WebView) findViewById(R.id.webview);
        et_num = (EditText)findViewById(R.id.et_num);
        et_num.setInputType(InputType.TYPE_CLASS_NUMBER);//调用数字键盘
        bt_buy = (Button)findViewById(R.id.bt_buy);
        loadingDialog = DialogUtil.createLoadingDialog(this, "");

    }

    @Override
    public void initListeners() {
        ll_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShanghuzicaixiangqingActivity.this.finish();
            }
        });
        et_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 输入前的监听
                Log.e("输入前确认执行该方法", "开始输入");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 输入的内容变化的监听
                Log.e("输入过程中执行该方法", "文字变化");
                if(TextUtils.isEmpty(s.toString())){
                    presenter.onNumEditTextChange("1");
                }else {

                }
                presenter.onNumEditTextChange(s);

            }

            @Override
            public void afterTextChanged(Editable s) {
                // 输入后的监听
                Log.e("输入结束执行该方法", "输入结束");

            }
        });
        bt_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_num.getText().toString().isEmpty()){
                    ToastUtils.showToast(ShanghuzicaixiangqingActivity.this,"请输入购买数量",Toast.LENGTH_SHORT);
                }else {
                    presenter.onBuyBtn(ShanghuzicaixiangqingActivity.this,et_num.getText().toString());
                    Log.v("IIIIIII",id+""+""+title);
                }

            }
        });
        tv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestRuntimePermission(permissions, new PermissionListener() {
                    @Override
                    public void onGranted() {
                        presenter.onToggleYaoqingBtn(ShanghuzicaixiangqingActivity.this);
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
    public void getUrlSuccess(String url) {
        Log.v("url",url);
        webview.loadUrl(url);
        webview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webview.getSettings().setJavaScriptEnabled(true);
        //不选择直接进入浏览器
        webview.setWebViewClient (new myWebclient());
        webview.clearCache(true);
    }

    @Override
    public void getUrlFailure(String msg) {
        ToastUtils.showToast(this, msg, Toast.LENGTH_SHORT);
    }

    @Override
    public void getTitleSuccess(String title) {
        this.title = title;
    }

    @Override
    public void getMoneySuccess(String money) {
        this.money = money;
    }

    @Override
    public void getYunSuccess(String yun) {
        this.yun = yun;
    }

    @Override
    public void getIdSuccess(String id) {
        this.id = id;
    }

    @Override
    public void showShareView(String title, String miaoshu, String url,String img) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，微信、QQ和QQ空间等平台使用
        oks.setTitle(title);
        // titleUrl QQ和QQ空间跳转链接
        oks.setTitleUrl(url);
//        // text是分享文本，所有平台都需要这个字段
        oks.setText(miaoshu);
//        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        oks.setImagePath("http://wx.ykh9.com"+img);
        oks.setImageUrl("http://wx.ykh9.com"+img);
//        //url在微信、微博，Facebook等平台中使用
        oks.setUrl(url);
        // comment是我对这条分享的评论，仅在人人网使用
//        oks.setComment("我是测试评论文本");
        // 启动分享GUI
        oks.show(this);
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
