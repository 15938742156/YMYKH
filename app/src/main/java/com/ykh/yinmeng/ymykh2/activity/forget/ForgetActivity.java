package com.ykh.yinmeng.ymykh2.activity.forget;

import android.annotation.SuppressLint;
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
import com.ykh.yinmeng.ymykh2.activity.login.LoginActivity;
import com.ykh.yinmeng.ymykh2.app.App;
import com.ykh.yinmeng.ymykh2.utils.Constant;
import com.ykh.yinmeng.ymykh2.utils.NumberUtils;
import com.ykh.yinmeng.ymykh2.utils.SharedPreferencesUtils;
import com.ykh.yinmeng.ymykh2.utils.ToastUtils;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.dialog.DialogUtil;


/**
 * 忘记密码Activity
 * @author Angel
 */
public class ForgetActivity extends BaseActivity implements ForgetMVPView {
    private EditText et_phone,et_code,et_newpassword,et_confirm;
    private Button bt_login,bt_code;
    private LinearLayout ll_return;
    private TextView tv_title;

    private Dialog loadingDialog;
    public ForgetPresenter<ForgetMVPView> forgetPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        forgetPresenter = new ForgetPresenterImp<>();
        forgetPresenter.onAttach(this);
    }

    @Override
    protected void onDestroy() {
        forgetPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_forget);
    }

    @Override
    public void initViews() {
        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_title.setText("忘记密码");
        et_phone = (EditText) findViewById(R.id.et_phone);
        ll_return = (LinearLayout) findViewById(R.id.ll_return);
        et_code = (EditText) findViewById(R.id.et_code);
        et_newpassword = (EditText) findViewById(R.id.et_newpassword);
        et_confirm = (EditText) findViewById(R.id.et_confirm);
        bt_login = (Button) findViewById(R.id.bt_login);
        bt_code = (Button) findViewById(R.id.bt_code);
        loadingDialog = DialogUtil.createLoadingDialog(this, "");
    }

    @Override
    public void initListeners() {
        ll_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForgetActivity.this.finish();
            }
        });
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                forgetPresenter.onToggleModifyBtn(tel, password, yzm);
            }
        });
        bt_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_phone.getText().toString().isEmpty()){
                    ToastUtils.showToast(mContext,"请输入手机号", Toast.LENGTH_SHORT);
                    return;
                }
                forgetPresenter.onToggleCodeBtn(et_phone.getText().toString());
            }
        });

    }

    @Override
    public void initData() {
        String phone = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_PHONE);
        et_phone.setText(phone);
    }


    @Override
    public void modifySuccess() {
        ToastUtils.showToast(mContext,"重置密码成功", Toast.LENGTH_SHORT);
        Intent intent = new Intent(ForgetActivity.this,LoginActivity.class);
        startActivity(intent);
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

    @SuppressLint("SetTextI18n")
    @Override
    public void onTick(long millisUntilFinished) {
        bt_code.setText(millisUntilFinished / 1000 + "s");
        bt_code.setTextColor(getResources().getColor(R.color.gray));
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

