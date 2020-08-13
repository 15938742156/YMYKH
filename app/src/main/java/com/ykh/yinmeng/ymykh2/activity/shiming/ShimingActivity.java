package com.ykh.yinmeng.ymykh2.activity.shiming;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.ykh.yinmeng.ymykh2.R;
import com.ykh.yinmeng.ymykh2.activity.BaseActivity;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.dialog.DialogUtil;


public class ShimingActivity extends BaseActivity implements View.OnClickListener,ShimingMVPView {

    private ImageButton ib_zhengmian,ib_fanmian;
    private Button bt_next;
    private AlertDialog.Builder alertDialog;
    private LinearLayout ll_return;
    private TextView tv_title,tv_shimingxieyi,tv_yinsixieyi;

    private Dialog loadingDialog;
    public ShimingPresenter<ShimingMVPView> shimingPresenter;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        shimingPresenter = new ShimingPresenterImp<>();
        shimingPresenter.onAttach(this);
    }

    @Override
    protected void onDestroy() {
        shimingPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_accard);
    }

    @Override
    public void initViews() {
        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_title.setText("实名认证");
        ll_return = (LinearLayout) findViewById(R.id.ll_return);
        ib_zhengmian = (ImageButton) findViewById(R.id.ib_zhengmian);
        ib_fanmian = (ImageButton) findViewById(R.id.ib_fanmian);
        bt_next= (Button) findViewById(R.id.bt_next);
        tv_shimingxieyi = (TextView) findViewById(R.id.tv_shimingxieyi);
        tv_yinsixieyi = (TextView) findViewById(R.id.tv_yinsixieyi);
        alertDialog = new AlertDialog.Builder(this);
        loadingDialog = DialogUtil.createLoadingDialog(this, "");
    }

    @Override
    public void initListeners() {
        ll_return.setOnClickListener(this);
        ib_zhengmian.setOnClickListener(this);
        ib_fanmian.setOnClickListener(this);
        tv_shimingxieyi.setOnClickListener(this);
        tv_yinsixieyi.setOnClickListener(this);
        bt_next.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_return:
                ShimingActivity.this.finish();
                break;
            case R.id.ib_zhengmian:
                shimingPresenter.onToggleZhengMian(this);
                break;
            case R.id.ib_fanmian:
                shimingPresenter.onToggleFanMian(this);
                break;
            case R.id.bt_next:
                shimingPresenter.onToggleNext(this);
                break;
            case R.id.tv_shimingxieyi:
                shimingPresenter.onXieYiShiming(this);
                break;
            case R.id.tv_yinsixieyi:
                shimingPresenter.onXieYiYinsi(this);
                break;
                default:
                    break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        shimingPresenter.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void showZhengMian(Bitmap bitmap) {
        ib_zhengmian.setImageBitmap(bitmap);
    }

    @Override
    public void showFanMian(Bitmap bitmap) {
        ib_fanmian.setImageBitmap(bitmap);
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

    @Override
    public void showAlert(String title, String message) {
        alertText(title, message);
    }

    private void alertText(final String title, final String message) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                alertDialog.setTitle(title)
                        .setMessage(message)
                        .setPositiveButton("确定", null)
                        .show();
            }
        });
    }
}
