package com.ykh.yinmeng.ymykh2.activity.shanghutuozhan;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.ykh.yinmeng.ymykh2.R;
import com.ykh.yinmeng.ymykh2.activity.BaseActivity;
import com.ykh.yinmeng.ymykh2.activity.ScarnActivity;
import com.ykh.yinmeng.ymykh2.utils.NumberUtils;
import com.ykh.yinmeng.ymykh2.utils.ToastUtils;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.dialog.DialogUtil;


public class ShanghutuozhanActivity extends BaseActivity implements View.OnClickListener,ShanghutuozhanMVPView {
    private TextView tv_title;
    private Button bt_queding;
    private EditText et_name,et_SN,et_tel,et_phone;
    private ImageButton ib_flick;
    private LinearLayout ll_return;

    private Dialog loadingDialog;
    private ShanghutuozhanPresenter<ShanghutuozhanMVPView>presenter;


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        presenter = new ShanghutuozhanPresenterImp<>();
        presenter.onAttach(this);
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();

    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_shanghutuozhan);
    }

    @Override
    public void initViews() {
        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_title.setText("商户拓展");
        ll_return = (LinearLayout)findViewById(R.id.ll_return);
        bt_queding = (Button) findViewById(R.id.bt_queding);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_name = (EditText) findViewById(R.id.et_name);
        et_SN = (EditText) findViewById(R.id.et_SN);
        et_tel = (EditText) findViewById(R.id.et_tel);
        ib_flick = (ImageButton) findViewById(R.id.ib_flick);
        loadingDialog = DialogUtil.createLoadingDialog(this, "");
    }

    @Override
    public void initListeners() {
        ll_return.setOnClickListener(this);
        bt_queding.setOnClickListener(this);
        ib_flick.setOnClickListener(this);

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_return:
                ShanghutuozhanActivity.this.finish();
                break;
            case R.id.ib_flick:
                IntentIntegrator integrator = new IntentIntegrator(this);
                // 设置要扫描的条码类型，ONE_D_CODE_TYPES：一维码，QR_CODE_TYPES-二维码
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
                integrator.setCaptureActivity(ScarnActivity.class); //设置打开摄像头的Activity
                integrator.setPrompt("请扫描ISBN"); //底部的提示文字，设为""可以置空
                integrator.setCameraId(0); //前置或者后置摄像头
                integrator.setBeepEnabled(true); //扫描成功的「哔哔」声，默认开启
                integrator.setBarcodeImageEnabled(true);
                integrator.initiateScan();
                break;
            case R.id.bt_queding:
                String inName = et_name.getText().toString();
                String sn = et_SN.getText().toString();
                String tel = et_tel.getText().toString();
                if (TextUtils.isEmpty(inName)){
                    ToastUtils.showToast(mContext, "请输入商户姓名", Toast.LENGTH_SHORT);
                    return;
                }
                if (TextUtils.isEmpty(sn)){
                    ToastUtils.showToast(mContext, "请输入机具SN号", Toast.LENGTH_SHORT);
                    return;
                }
                if (TextUtils.isEmpty(tel)){
                    ToastUtils.showToast(mContext, "请输入手机号", Toast.LENGTH_SHORT);
                    return;
                }
                if (!NumberUtils.isMobile(tel)) {
                    ToastUtils.showToast(mContext, "手机号输入错误", Toast.LENGTH_SHORT);
                    return;
                }
                String gid = null;
                presenter.onQuedingBtn(tel,inName,sn);

                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (scanResult != null) {
                String result = scanResult.getContents();
                et_SN.setText(result);
            }
        }


        @Override
    public void modifySuccess() {
        ToastUtils.showToast(this,"商户拓展成功", Toast.LENGTH_SHORT);
        this.finish();
    }

    @Override
    public void modifyFailure(String msg) {
        ToastUtils.showToast(this,msg, Toast.LENGTH_SHORT);
    }



    @Override
    public void getYzmSuccess() {
        ToastUtils.showToast(this, "获取验证码成功",Toast.LENGTH_SHORT);
    }

    @Override
    public void getYzmFailure(String msg) {
        ToastUtils.showToast(this, msg,Toast.LENGTH_SHORT);
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
