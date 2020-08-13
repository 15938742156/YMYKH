package com.ykh.yinmeng.ymykh2.activity.bangdingzhifubao;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ykh.yinmeng.ymykh2.R;
import com.ykh.yinmeng.ymykh2.activity.BaseActivity;
import com.ykh.yinmeng.ymykh2.app.App;
import com.ykh.yinmeng.ymykh2.utils.Constant;
import com.ykh.yinmeng.ymykh2.utils.NumberUtils;
import com.ykh.yinmeng.ymykh2.utils.SharedPreferencesUtils;
import com.ykh.yinmeng.ymykh2.utils.ToastUtils;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.dialog.DialogUtil;


/***
 * 绑定支付宝页面
 * @author Angel
 */
public class ZhifubaoActivity extends BaseActivity implements View.OnClickListener, ZhifubaoMVPView {
    private EditText et_phone,et_code;
    private Button bt_code,bt_queding;
    Intent intent;
    private LinearLayout ll_return;
    private TextView tv_title,tv_name;
    private Dialog loadingDialog;
    public ZhifubaoPresenter<ZhifubaoMVPView> resetphonePresenter;


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        resetphonePresenter = new ZhifubaoPresenterImp<>();
        resetphonePresenter.onAttach(this);
    }

    @Override
    protected void onDestroy() {
        resetphonePresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_zhifubao);
    }

    @Override
    public void initViews() {
        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_title.setText("绑定支付宝");
        ll_return = (LinearLayout) findViewById(R.id.ll_return);
        tv_name = (TextView) findViewById(R.id.tv_name);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_code = (EditText) findViewById(R.id.et_code);
        bt_code = (Button) findViewById(R.id.bt_code);
        bt_queding = (Button) findViewById(R.id.bt_queding);
        loadingDialog = DialogUtil.createLoadingDialog(this, "");
    }

    @Override
    public void initListeners() {
        ll_return.setOnClickListener(this);
        tv_name.setOnClickListener(this);
        et_phone.setOnClickListener(this);
        et_code.setOnClickListener(this);
        bt_code.setOnClickListener(this);
        bt_queding.setOnClickListener(this);
    }

    @Override
    public void initData() {
        String name = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_NAME);
        tv_name.setText(name);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_return:
                ZhifubaoActivity.this.finish();
                break;
            case R.id.bt_code:
                if (et_phone.getText().toString().isEmpty()){
                    ToastUtils.showToast(mContext,"请输入手机号", Toast.LENGTH_SHORT);
                    return;
                }
                resetphonePresenter.onToggleCodeBtn(et_phone.getText().toString());
                break;
            case R.id.bt_queding:
                String name = tv_name.getText().toString().trim();
                String tel = et_phone.getText().toString().trim();
                String yzm = et_code.getText().toString().trim();
                if (TextUtils.isEmpty(tel)) {
                    ToastUtils.showToast(mContext, "请输入手机号", Toast.LENGTH_SHORT);
                    return;
                }
                if (!NumberUtils.isMobile(tel)) {
                    ToastUtils.showToast(mContext, "手机号输入错误", Toast.LENGTH_SHORT);
                    return;
                }
//                if (TextUtils.isEmpty(yzm)){
//                    ToastUtils.showToast(mContext,"请输入验证码", Toast.LENGTH_SHORT);
//                    return;
//                }
//                if (!NumberUtils.isYzm(yzm)){
//                    ToastUtils.showToast(mContext,"请输入正确的验证码", Toast.LENGTH_SHORT);
//                    return;
//                }
                resetphonePresenter.onToggleModifyBtn(this,name,tel,yzm);

                break;
        }

    }

    @Override
    public void modifySuccess() {
        ToastUtils.showToast(mContext,"手机号修改成功", Toast.LENGTH_SHORT);
        ZhifubaoActivity.this.finish();
    }

    @Override
    public void modifyFailure(String msg) {
        ToastUtils.showToast(mContext,msg, Toast.LENGTH_SHORT);
    }

    @Override
    public void getYzmSuccess() {
        ToastUtils.showToast(mContext,"获取验证码成功", Toast.LENGTH_SHORT);
    }

    @Override
    public void getYzmFailure(String msg) {
        ToastUtils.showToast(mContext,msg, Toast.LENGTH_SHORT);
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
    public void onTick(long millisUntilFinished) {
        bt_code.setText(millisUntilFinished / 1000 + "s");
        bt_code.setTextColor(getResources().getColor(R.color.text_gray));
        bt_code.setClickable(false);//防止重复点击
        bt_code.setBackgroundResource(R.drawable.btn_bg_corner_gray);
    }

    @Override
    public void onTickFinish() {
        bt_code.setText(getResources().getString(R.string.get_code));
        bt_code.setTextColor(getResources().getColor(R.color.main));
        bt_code.setClickable(true);
        bt_code.setBackgroundResource(R.drawable.btn_bg_corner_yellow);
    }
}
