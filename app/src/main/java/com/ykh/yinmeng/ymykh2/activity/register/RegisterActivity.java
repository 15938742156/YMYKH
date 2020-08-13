package com.ykh.yinmeng.ymykh2.activity.register;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ykh.yinmeng.ymykh2.R;
import com.ykh.yinmeng.ymykh2.activity.BaseActivity;
import com.ykh.yinmeng.ymykh2.utils.NumberUtils;
import com.ykh.yinmeng.ymykh2.utils.ToastUtils;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.dialog.DialogUtil;


/**
 * 注册Activity
 * @author Angel
 */
public class RegisterActivity extends BaseActivity implements RegisterMVPView {
    private EditText et_phone,et_code,et_referrer,et_newpassword,et_confirm;
    private Button bt_code,bt_login;
    private LinearLayout ll_return;
    private TextView tv_title;

    private Dialog loadingDialog;
    public RegisterPresenter<RegisterMVPView> registerPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerPresenter = new RegisterPresenterImp<>();
        registerPresenter.onAttach(this);
    }

    @Override
    protected void onDestroy() {
        registerPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_register);
    }

    @Override
    public void initViews() {
        ll_return = (LinearLayout) findViewById(R.id.ll_return);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("注册");
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_code = (EditText) findViewById(R.id.et_code);
        et_referrer = (EditText) findViewById(R.id.et_referrer);
        et_newpassword = (EditText) findViewById(R.id.et_newpassword);
        et_confirm = (EditText) findViewById(R.id.et_confirm);
        bt_code = (Button) findViewById(R.id.bt_code);
        bt_login = (Button) findViewById(R.id.bt_login);
        loadingDialog = DialogUtil.createLoadingDialog(this, "");
    }

    @Override
    public void initListeners() {
        ll_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterActivity.this.finish();
            }
        });
        bt_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tel = et_phone.getText().toString().trim();
                if (TextUtils.isEmpty(tel)){
                    ToastUtils.showToast(mContext,"请输入手机号", Toast.LENGTH_SHORT);
                    return;
                }
                if (!NumberUtils.isMobile(tel)) {
                    ToastUtils.showToast(mContext, "手机号输入错误", Toast.LENGTH_SHORT);
                    return;
                }
                registerPresenter.onToggleCodeBtn(tel);
            }
        });
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String yqtel = et_referrer.getText().toString().trim();
                String tel = et_phone.getText().toString().trim();
                String password = et_newpassword.getText().toString().trim();
                String confirm_password = et_confirm.getText().toString().trim();
                String yzm = et_code.getText().toString().trim();
                if (TextUtils.isEmpty(tel)) {
                    ToastUtils.showToast(mContext, "请输入手机号", Toast.LENGTH_SHORT);
                    return;
                }
                if (!NumberUtils.isMobile(tel)) {
                    ToastUtils.showToast(mContext, "手机号输入错误", Toast.LENGTH_SHORT);
                    return;
                }
                if (TextUtils.isEmpty(yzm)){
                    ToastUtils.showToast(mContext,"请输入验证码", Toast.LENGTH_SHORT);
                    return;
                }
                if (!NumberUtils.isYzm(yzm)){
                    ToastUtils.showToast(mContext,"请输入正确的验证码", Toast.LENGTH_SHORT);
                    return;
                }
                if (!NumberUtils.isPassword(mContext,password)){
                    return;
                }
                if (TextUtils.isEmpty(confirm_password)){
                    ToastUtils.showToast(mContext,"请输入密码", Toast.LENGTH_SHORT);
                    return;
                }
                if (!password.equals(confirm_password)){
                    ToastUtils.showToast(mContext,"密码输入不一致", Toast.LENGTH_SHORT);
                    return;
                }
                registerPresenter.onToggleRegisterBtn(yqtel, tel, password, yzm);
            }
        });

    }

    @Override
    public void initData() {

    }

    @Override
    public void registerSuccess() {
        ToastUtils.showToast(mContext,"注册成功", Toast.LENGTH_SHORT);
        Intent intent = new Intent();
        intent.putExtra("phone", et_phone.getText().toString().trim());
        setResult(2, intent);
        finish();
    }

    @Override
    public void registerFailure(String msg) {
        ToastUtils.showToast(mContext,msg, Toast.LENGTH_SHORT);
    }

    @Override
    public void getYzmSuccess() {
        ToastUtils.showToast(mContext,"发送验证码成功", Toast.LENGTH_SHORT);
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

    @SuppressLint("SetTextI18n")
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
