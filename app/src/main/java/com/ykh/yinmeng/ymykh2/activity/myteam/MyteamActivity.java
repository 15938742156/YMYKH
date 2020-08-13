package com.ykh.yinmeng.ymykh2.activity.myteam;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ykh.yinmeng.ymykh2.R;
import com.ykh.yinmeng.ymykh2.activity.BaseActivity;
import com.ykh.yinmeng.ymykh2.activity.ShouyishuomingActivity;
import com.ykh.yinmeng.ymykh2.activity.jihuomingxi.JihuomingxiActivity;
import com.ykh.yinmeng.ymykh2.activity.mymember.MymemberActivity;
import com.ykh.yinmeng.ymykh2.activity.shanghuchaxun.ShanghuchaxunActivity;
import com.ykh.yinmeng.ymykh2.activity.tuanduipaiming.TuanduipaimingActivity;
import com.ykh.yinmeng.ymykh2.utils.ToastUtils;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.dialog.DialogUtil;


public class MyteamActivity extends BaseActivity implements View.OnClickListener, MyteamMVPView {
    private LinearLayout ll_return,layout_member,layout_team,layout_business,layout_activate,layout_earnings;
    private TextView tv_title,tv_huiyuanren,tv_huiyuan,tv_jihuo,tv_money,tv_num,tv_zhiyingnum;
    Intent intent;

    private Dialog loadingDialog;
    public MyteamPresenter<MyteamMVPView> presenter;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        presenter = new MyteamPresenterImp<>();
        presenter.onAttach(this);
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_team);
    }

    @Override
    public void initViews() {
        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_title.setText("我的团队");
        ll_return = (LinearLayout)findViewById(R.id.ll_return);
        layout_member = (LinearLayout)findViewById(R.id.layout_member);
        layout_team = (LinearLayout)findViewById(R.id.layout_team);
        layout_business = (LinearLayout)findViewById(R.id.layout_business);
        layout_activate = (LinearLayout)findViewById(R.id.layout_activate);
        layout_earnings = (LinearLayout)findViewById(R.id.layout_earnings);
        tv_huiyuanren = (TextView) findViewById(R.id.tv_huiyuanren);
        tv_huiyuan = (TextView) findViewById(R.id.tv_huiyuan);
        tv_jihuo = (TextView) findViewById(R.id.tv_jihuo);
        tv_money = (TextView) findViewById(R.id.tv_money);
        tv_num = (TextView) findViewById(R.id.tv_num);
        tv_zhiyingnum = (TextView) findViewById(R.id.tv_zhiyingnum);
        loadingDialog = DialogUtil.createLoadingDialog(this, "");
    }

    @Override
    public void initListeners() {
        ll_return.setOnClickListener(this);
        layout_member.setOnClickListener(this);
        layout_team.setOnClickListener(this);
        layout_business.setOnClickListener(this);
        layout_activate.setOnClickListener(this);
        layout_earnings.setOnClickListener(this);
        tv_huiyuanren.setOnClickListener(this);
        tv_huiyuan.setOnClickListener(this);
        tv_jihuo.setOnClickListener(this);
        tv_money.setOnClickListener(this);
        tv_num.setOnClickListener(this);
        tv_zhiyingnum.setOnClickListener(this);

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_return:
                MyteamActivity.this.finish();
                break;
            case R.id.layout_member:
                intent = new Intent(MyteamActivity.this, MymemberActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_team:
                intent = new Intent(MyteamActivity.this,TuanduipaimingActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_business:
                intent = new Intent(MyteamActivity.this, ShanghuchaxunActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_activate:
                intent = new Intent(MyteamActivity.this, JihuomingxiActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_earnings:
                intent = new Intent(MyteamActivity.this, ShouyishuomingActivity.class);
                startActivity(intent);
                break;
                default:
                    break;
        }
    }

    @Override
    public void getDataSuccess() {
        tv_huiyuanren.getText().toString().trim();
        tv_huiyuan.getText().toString().trim();
        tv_jihuo.getText().toString().trim();
        tv_money.getText().toString().trim();
        tv_num.getText().toString().trim();
        tv_zhiyingnum.getText().toString().trim();
    }

    @Override
    public void getDataFailure(String msg) {
        ToastUtils.showToast(this, msg, Toast.LENGTH_SHORT);
    }

    @Override
    public void showSumDeal(String msg) {
        tv_money.setText(msg);
    }

    @Override
    public void showDayUser(String msg) {
        tv_huiyuan.setText(msg);
    }

    @Override
    public void showZShop(String msg) {
        tv_jihuo.setText(msg);
    }

    @Override
    public void showZJqCount(String msg) {
        tv_zhiyingnum.setText(msg);
    }

    @Override
    public void showActiveCount(String msg) {
        tv_num.setText(msg);
    }

    @Override
    public void showInfo(String msg) {
        tv_huiyuanren.setText(msg);
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
