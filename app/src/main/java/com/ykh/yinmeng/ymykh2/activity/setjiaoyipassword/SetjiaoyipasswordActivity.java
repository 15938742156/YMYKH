package com.ykh.yinmeng.ymykh2.activity.setjiaoyipassword;

import android.app.Dialog;
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
import com.ykh.yinmeng.ymykh2.app.App;
import com.ykh.yinmeng.ymykh2.utils.Constant;
import com.ykh.yinmeng.ymykh2.utils.NumberUtils;
import com.ykh.yinmeng.ymykh2.utils.SharedPreferencesUtils;
import com.ykh.yinmeng.ymykh2.utils.ToastUtils;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.dialog.DialogUtil;


public class SetjiaoyipasswordActivity extends BaseActivity implements View.OnClickListener,SetjiaoyipasswordMVPView {
    private EditText et_phone,et_code,et_setpassword,et_confirm_password;
    private Button bt_code,bt_queding;
    private LinearLayout ll_return;
    private TextView tv_title;

    private Dialog loadingDialog;
    public SetjiaoyipasswordPresenter<SetjiaoyipasswordMVPView> presenter;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        presenter = new SetjiaoyipasswordPresenterImp<>();
        presenter.onAttach(this);
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_setjiaoyipassword);
    }

    @Override
    public void initViews() {
        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_title.setText("设置交易密码");
        ll_return = (LinearLayout) findViewById(R.id.ll_return);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_code = (EditText) findViewById(R.id.et_code);
        et_setpassword = (EditText) findViewById(R.id.et_setpassword);
        et_confirm_password = (EditText) findViewById(R.id.et_confirm_password);
        bt_code = (Button) findViewById(R.id.bt_code);
        bt_queding = (Button) findViewById(R.id.bt_queding);
        loadingDialog = DialogUtil.createLoadingDialog(this, "");
    }

    @Override
    public void initListeners() {
        ll_return.setOnClickListener(this);
        et_phone.setOnClickListener(this);
        et_code.setOnClickListener(this);
        et_setpassword.setOnClickListener(this);
        et_confirm_password.setOnClickListener(this);
        bt_code.setOnClickListener(this);
        bt_queding.setOnClickListener(this);

    }

    @Override
    public void initData() {
        String phone = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_PHONE);
        et_phone.setText(phone);
    }

    @Override
    public void onClick(View v) {
        String tel;
        switch (v.getId()){
            case R.id.ll_return:
                SetjiaoyipasswordActivity.this.finish();
                break;
            case R.id.bt_code:
                tel = et_phone.getText().toString().trim();
                if (TextUtils.isEmpty(tel)){
                    ToastUtils.showToast(mContext, "请输入手机号", Toast.LENGTH_SHORT);
                    return;
                }
                if (!NumberUtils.isMobile(tel)) {
                    ToastUtils.showToast(mContext, "手机号输入错误", Toast.LENGTH_SHORT);
                    return;
                }
                presenter.onToggleCodeBtn(tel);
                break;
            case R.id.bt_queding:
                tel = et_phone.getText().toString().trim();
                String yzm = et_code.getText().toString().trim();
                String password = et_setpassword.getText().toString().trim();
                String confirm_password = et_confirm_password.getText().toString().trim();
                if (TextUtils.isEmpty(tel)|| et_code.getText().toString().isEmpty()){
                    ToastUtils.showToast(mContext,"请输入手机号或验证码", Toast.LENGTH_SHORT);
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
                if (TextUtils.isEmpty(password)){
                    ToastUtils.showToast(mContext,"请输入密码", Toast.LENGTH_SHORT);
                    return;
                }
                if (password.length() != 6) {
                    ToastUtils.showToast(mContext, "请输入6位交易密码", Toast.LENGTH_SHORT);
                    return;
                }
                if (!password.equals(confirm_password)){
                    ToastUtils.showToast(mContext,"密码输入不一致", Toast.LENGTH_SHORT);
                    return;
                }
                presenter.onToggleQuedingBtn(this, tel, yzm, password);
                break;

        }
    }

    @Override
    public void setjiaoyipasswordSuccess() {
    }

    @Override
    public void setjiaoyipasswordFailure(String msg) {
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
