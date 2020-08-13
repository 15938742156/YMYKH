package com.ykh.yinmeng.ymykh2.activity.mydata;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ykh.yinmeng.ymykh2.R;
import com.ykh.yinmeng.ymykh2.activity.AuthenBaseActivity;
import com.ykh.yinmeng.ymykh2.activity.BaseActivity;
import com.ykh.yinmeng.ymykh2.utils.StringReplaceUtil;
import com.ykh.yinmeng.ymykh2.utils.ToastUtils;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.dialog.DialogUtil;


public class MydataActivity extends BaseActivity implements View.OnClickListener,MydataMVPView {
    private TextView tv_title,tv_name,tv_tel,tv_gender,tv_idNo;
    private Dialog loadingDialog;
    private LinearLayout ll_return;

    private MydataPresenter<MydataMVPView> presenter;


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        presenter = new MydataPresenterImp<>();
        presenter.onAttach(this);
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_mydata);
    }

    @Override
    public void initViews() {
        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_title.setText("我的资料");
        ll_return = (LinearLayout)findViewById(R.id.ll_return);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_tel = (TextView) findViewById(R.id.tv_tel);
        tv_gender = (TextView) findViewById(R.id.tv_gender);
        tv_idNo = (TextView) findViewById(R.id.tv_idNo);
        loadingDialog = DialogUtil.createLoadingDialog(this, "");

    }

    @Override
    public void initListeners() {
        ll_return.setOnClickListener(this);

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_return:
                MydataActivity.this.finish();
                break;
                default:
                    break;
        }
    }

    @Override
    public void showNameSuccess(String name) {
        tv_name.setText(name);
    }

    @Override
    public void showTelSuccess(String tel) {
        tv_tel.setText(StringReplaceUtil.isMobileNum(tel));
    }

    @Override
    public void showSexSuccess(String sex) {
        tv_gender.setText(sex);
    }

    @Override
    public void showIdNumber(String idNumber) {
        tv_idNo.setText(StringReplaceUtil.idCardReplaceWithStar(idNumber));
    }

    @Override
    public void getDataFailure(String msg) {
        ToastUtils.showToast(this, msg, Toast.LENGTH_SHORT);
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
