package com.ykh.yinmeng.ymykh2.activity.setLoginpassword;

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


/***
 * 设置登录密码Activity
 * @author Angel
 */
public class SetLoginpasswordActivity extends BaseActivity implements View.OnClickListener, SetLoginpasswordMVPView {
    private EditText et_setpassword,et_confirm_password;
    private Button bt_queding;
    private Dialog loadingDialog;
    private LinearLayout ll_return;
    private TextView tv_title;

    SetLoginpasswordPresenter<SetLoginpasswordMVPView> resetLoginpasswordPresenter;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        resetLoginpasswordPresenter = new SetLoginpasswordPresenterImp<>();
        resetLoginpasswordPresenter.onAttach(this);

    }

    @Override
    protected void onDestroy() {
        resetLoginpasswordPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_set_loginpassword);
    }

    @Override
    public void initViews() {
        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_title.setText("设置登录密码");
        ll_return = (LinearLayout)findViewById(R.id.ll_return);
        et_setpassword = (EditText) findViewById(R.id.et_setpassword);
        et_confirm_password = (EditText) findViewById(R.id.et_confirm_password);
        bt_queding = (Button) findViewById(R.id.bt_queding);
        loadingDialog = DialogUtil.createLoadingDialog(this, "");
    }

    @Override
    public void initListeners() {
        ll_return.setOnClickListener(this);
        et_setpassword.setOnClickListener(this);
        et_confirm_password.setOnClickListener(this);
        bt_queding.setOnClickListener(this);

    }

    @Override
    public void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_return:
                SetLoginpasswordActivity.this.finish();
                break;
            case R.id.bt_queding:
                String password = et_setpassword.getText().toString().trim();
                String confirm_password = et_confirm_password.getText().toString().trim();

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
                resetLoginpasswordPresenter.onToggleModifyBtn(password);
                break;

        }
    }

    @Override
    public void modifySuccess() {
        ToastUtils.showToast(mContext,"登录密码设置成功", Toast.LENGTH_SHORT);
        startActivity(new Intent(this, MainActivity.class));
        SharedPreferencesUtils.getInstance(App.getContext()).put(Constant.SHARED_TOKEN, "");
        finishAll();
    }

    @Override
    public void modifyFailure(String msg) {
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

}
