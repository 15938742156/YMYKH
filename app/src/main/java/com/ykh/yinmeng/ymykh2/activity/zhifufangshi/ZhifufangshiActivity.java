package com.ykh.yinmeng.ymykh2.activity.zhifufangshi;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.ykh.yinmeng.ymykh2.R;
import com.ykh.yinmeng.ymykh2.activity.BaseActivity;
import com.ykh.yinmeng.ymykh2.activity.myorder.MyOrderActivity;
import com.ykh.yinmeng.ymykh2.utils.PermissionListener;
import com.ykh.yinmeng.ymykh2.utils.ToastUtils;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.dialog.DialogUtil;

import java.util.ArrayList;
import java.util.List;

public class ZhifufangshiActivity extends BaseActivity implements View.OnClickListener,ZhifufangshiMVPView {
    private Button bt_queding;
    private TextView tv_title,tv_money;
    private LinearLayout ll_return;
    private CheckBox checkbox_weixin,checkbox_zhifubao;

    private Dialog loadingDialog;
    public ZhifufangshiPresenter<ZhifufangshiMVPView> presenter;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        presenter = new ZhifufangshiPresenterImp<>();
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
        setContentView(R.layout.activity_zhifufangshi);
    }

    @Override
    public void initViews() {
        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_title.setText("支付方式");
        ll_return = (LinearLayout)findViewById(R.id.ll_return);
        bt_queding = (Button) findViewById(R.id.bt_queding);
        tv_money = (TextView) findViewById(R.id.tv_money);
        checkbox_weixin = (CheckBox)findViewById(R.id.checkbox_weixin);
        checkbox_zhifubao = (CheckBox)findViewById(R.id.checkbox_zhifubao);
        loadingDialog = DialogUtil.createLoadingDialog(this, "");
    }

    @Override
    public void initListeners() {
        ll_return.setOnClickListener(this);
        bt_queding.setOnClickListener(this);
        checkbox_weixin.setOnClickListener(this);
        checkbox_zhifubao.setOnClickListener(this);
    }

    @Override
    public void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_return:
                ZhifufangshiActivity.this.finish();
                break;
            case R.id.bt_queding:
                if (checkbox_zhifubao.isChecked()){
                    checkbox_weixin.setChecked(false);
                    String[] permissions = new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    requestRuntimePermission(permissions, new PermissionListener() {
                        @Override
                        public void onGranted() {
                            presenter.onToggleEnsureBtn(ZhifufangshiActivity.this);
                        }

                        @Override
                        public void onDenied(List<String> deniedPermissions) {
                            ToastUtils.showToast(ZhifufangshiActivity.this, "权限被拒绝,支付失败", Toast.LENGTH_SHORT);
                        }
                    });
                }if (checkbox_weixin.isChecked()){
                    checkbox_zhifubao.setChecked(false);
                    String[] permissions = new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    requestRuntimePermission(permissions, new PermissionListener() {
                    @Override
                    public void onGranted() {
                        presenter.onWeixinBtn(ZhifufangshiActivity.this);
                    }

                    @Override
                    public void onDenied(List<String> deniedPermissions) {
                        ToastUtils.showToast(ZhifufangshiActivity.this, "权限被拒绝,支付失败", Toast.LENGTH_SHORT);
                    }
                });

            }
                break;
            case R.id.checkbox_weixin:
                if (checkbox_weixin.isChecked()){
                    checkbox_zhifubao.setChecked(false);
                }else {
                    checkbox_zhifubao.setChecked(true);
                }
                break;
            case R.id.checkbox_zhifubao:
                if (checkbox_zhifubao.isChecked()){
                    checkbox_weixin.setChecked(false);
                }else {
                    checkbox_weixin.setChecked(true);
                }
                break;
                default:
                    break;
        }
    }

    @Override
    public void paySuccess(String msg) {
        ToastUtils.showToast(this, msg, Toast.LENGTH_SHORT);
    }

    @Override
    public void showErro(String msg) {
        ToastUtils.showToast(this, msg, Toast.LENGTH_SHORT);
    }

    @Override
    public void showPayMoney(String money) {
        tv_money.setText(money+"元");
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
}
