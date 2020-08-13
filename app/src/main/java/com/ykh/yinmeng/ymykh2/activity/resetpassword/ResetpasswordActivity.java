package com.ykh.yinmeng.ymykh2.activity.resetpassword;

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


/***
 * 重置交易密码
 * @author Angel
 */
public class ResetpasswordActivity extends BaseActivity implements View.OnClickListener,ResetpasswordMVPView {
    private EditText et_code,et_setpassword,et_confirm_password;
    private Button bt_code,bt_queding;
    private LinearLayout ll_return;
    private TextView tv_title,et_phone;

    private Dialog loadingDialog;
    public ResetpasswordPresenter<ResetpasswordMVPView> presenter;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        presenter = new ResetpasswordPresenterImp<>();
        presenter.onAttach(this);
    }
    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_reset_password);
    }

    @Override
    public void initViews() {
        ll_return = (LinearLayout) findViewById(R.id.ll_return);
        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_title.setText("重置交易密码");
        et_phone = (TextView) findViewById(R.id.et_phone);
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
                ResetpasswordActivity.this.finish();
                break;
            case R.id.et_phone:
                break;
            case R.id.et_code:
                break;
            case R.id.et_setpassword:
                break;
            case R.id.et_confirm_password:
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
                String pwd = et_setpassword.getText().toString().trim();
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
                if (TextUtils.isEmpty(pwd)){
                    ToastUtils.showToast(mContext,"请输入密码", Toast.LENGTH_SHORT);
                    return;
                }
                if (pwd.length() != 6) {
                    ToastUtils.showToast(mContext, "请输入6位交易密码", Toast.LENGTH_SHORT);
                    return;
                }
                if (!pwd.equals(confirm_password)){
                    ToastUtils.showToast(mContext,"密码输入不一致", Toast.LENGTH_SHORT);
                    return;
                }
                presenter.onToggleQuedingBtn(tel,yzm,pwd);
                break;

        }
    }

    @Override
    public void resetjiaoyipasswordSuccess() {
        ToastUtils.showToast(mContext,"重置密码成功", Toast.LENGTH_SHORT);
        ResetpasswordActivity.this.finish();
    }

    @Override
    public void resetjiaoyipasswordFailure(String msg) {
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
