package com.ykh.yinmeng.ymykh2.activity.login;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ykh.yinmeng.ymykh2.R;
import com.ykh.yinmeng.ymykh2.activity.BaseActivity;
import com.ykh.yinmeng.ymykh2.activity.main.MainActivity;
import com.ykh.yinmeng.ymykh2.activity.forget.ForgetActivity;
import com.ykh.yinmeng.ymykh2.activity.quick.QulickActivity;
import com.ykh.yinmeng.ymykh2.activity.register.RegisterActivity;
import com.ykh.yinmeng.ymykh2.app.App;
import com.ykh.yinmeng.ymykh2.utils.Constant;
import com.ykh.yinmeng.ymykh2.utils.NumberUtils;
import com.ykh.yinmeng.ymykh2.utils.SharedPreferencesUtils;
import com.ykh.yinmeng.ymykh2.utils.ToastUtils;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.dialog.DialogUtil;


/**
 * 登录activity
 * @author Angel
 */
public class LoginActivity extends BaseActivity implements LoginMVPView {
    private EditText et_code,et_phone,et_password;
    private TextView tv_shell,tv_forget,tv_register;
    private Button bt_login;
    SharedPreferencesUtils preferences;
    Context context;
    String username="";
    String yqtel="";
    String passwd="";
    String yzm="";

    private Dialog loadingDialog;
    public LoginPresenter<LoginMVPView> loginPresenter;
    private final static int REQUESTCODE = 1; // 返回的结果码
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        loginPresenter = new LoginPresenterImp<>();
        loginPresenter.onAttach(this);
    }



    @Override
    protected void onDestroy() {
        loginPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_login);
        preferences = SharedPreferencesUtils.getInstance(this);
    }

    @Override
    public void initViews() {
        et_code = (EditText) findViewById(R.id.et_code);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_password = (EditText) findViewById(R.id.et_password);
        tv_shell = (TextView) findViewById(R.id.tv_shell);
        tv_forget = (TextView) findViewById(R.id.tv_forget);
        tv_register = (TextView) findViewById(R.id.tv_register);
        bt_login = (Button) findViewById(R.id.bt_login);
        loadingDialog = DialogUtil.createLoadingDialog(this, "");
    }

    @Override
    public void initListeners() {
        tv_shell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,QulickActivity.class);
                startActivity(intent);
            }
        });
        tv_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,ForgetActivity.class);
                startActivity(intent);
            }
        });
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(intent, 1);
            }
        } );
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String code = et_code.getText().toString().trim();
                String tel = et_phone.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                if (TextUtils.isEmpty(tel)) {
                    ToastUtils.showToast(mContext, "请输入手机号", Toast.LENGTH_SHORT);
                    return;
                }
                if (!NumberUtils.isMobile(tel)) {
                    ToastUtils.showToast(mContext, "手机号输入错误", Toast.LENGTH_SHORT);
                    return;
                }
//                if (TextUtils.isEmpty(code)) {
//                    ToastUtils.showToast(mContext, "请输入验证码", Toast.LENGTH_SHORT);
//                    return;
//                }
//                if (!NumberUtils.isYzm(tel)) {
//                    ToastUtils.showToast(mContext, "验证码输入错误", Toast.LENGTH_SHORT);
//                    return;
//                }
                if (!NumberUtils.isPassword(mContext,password)){
                    return;
                }
                loginPresenter.onToggleLoginBtn(et_phone.getText().toString(),et_password.getText().toString());
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        HashMap<String,String> params = new HashMap<String, String>();
//                        params.put("username",et_phone.getText().toString());
//                        params.put("passwd",et_password.getText().toString());
//                        getServer(params);
//
//
//                    }
//                });
//
//                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
//                startActivity(intent);
            }
        });

    }

    @Override
    public void initData() {
        String phone = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_PHONE);
        et_phone.setText(phone);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 2) {
            if (requestCode == REQUESTCODE) {
                String phone = data.getStringExtra("phone");
                //设置结果显示框的显示数值
                et_phone.setText(phone);
            }
        }
    }
    @Override
    public void loginSuccess() {
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void loginFailure(String msg) {
        ToastUtils.showToast(getApplicationContext(), msg,Toast.LENGTH_SHORT);
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
    public void autoLogin() {
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}

