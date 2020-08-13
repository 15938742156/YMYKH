package com.ykh.yinmeng.ymykh2.activity.quick;

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
import com.ykh.yinmeng.ymykh2.activity.main.MainActivity;
import com.ykh.yinmeng.ymykh2.app.App;
import com.ykh.yinmeng.ymykh2.utils.Constant;
import com.ykh.yinmeng.ymykh2.utils.NumberUtils;
import com.ykh.yinmeng.ymykh2.utils.SharedPreferencesUtils;
import com.ykh.yinmeng.ymykh2.utils.ToastUtils;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.dialog.DialogUtil;


/**
 * 快捷登录Activity
 * @author Angel
 */
public class QulickActivity extends BaseActivity implements QuickMVPView{
    private EditText et_phone,et_code;
    private Button bt_code,bt_login;
    private TextView tv_title;
    private LinearLayout ll_return;
    SharedPreferencesUtils preferences;

    private Dialog loadingDialog;
    public QuickPresenter<QuickMVPView> presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new QuickPresenterImp<>();
        presenter.onAttach(this);
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.actiity_login_qulick);
        preferences = SharedPreferencesUtils.getInstance(this);
    }

    @Override
    public void initViews() {
        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_title.setText("快捷登录");
        ll_return = (LinearLayout)findViewById(R.id.ll_return);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_code = (EditText) findViewById(R.id.et_code);
        bt_code = (Button) findViewById(R.id.bt_code);
        bt_login = (Button) findViewById(R.id.bt_login);
        loadingDialog = DialogUtil.createLoadingDialog(this, "");
    }

    @Override
    public void initListeners() {
        ll_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QulickActivity.this.finish();
            }
        });
        bt_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tel = et_phone.getText().toString().trim();
                if (TextUtils.isEmpty(tel)){
                    ToastUtils.showToast(mContext, "请输入手机号", Toast.LENGTH_SHORT);
                    return;
                }
                if (!NumberUtils.isMobile(tel)) {
                    ToastUtils.showToast(mContext, "手机号输入错误", Toast.LENGTH_SHORT);
                    return;
                }
                presenter.onToggleCodeBtn(et_phone.getText().toString());
            }
        });
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tel = et_phone.getText().toString().trim();
                if (TextUtils.isEmpty(tel)|| et_code.getText().toString().isEmpty()){
                    ToastUtils.showToast(mContext,"请输入手机号或验证码", Toast.LENGTH_SHORT);
                    return;
                }
                if (!NumberUtils.isMobile(tel)) {
                    ToastUtils.showToast(mContext, "手机号输入错误", Toast.LENGTH_SHORT);
                    return;
                }
                presenter.onToggleLoginBtn(tel, et_code.getText().toString());
            }
        });

    }

    @Override
    public void initData() {
        String phone = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_PHONE);
        et_phone.setText(phone);
    }

    @Override
    public void loginSuccess() {
        Intent intent = new Intent(this.getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finishAll();
    }

    @Override
    public void loginFailure(String msg) {
        ToastUtils.showToast(this, msg,Toast.LENGTH_SHORT);
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
    @Override
    public void autoLogin() {
        Intent intent = new Intent(QulickActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
