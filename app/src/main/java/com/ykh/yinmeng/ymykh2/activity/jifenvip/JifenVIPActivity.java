package com.ykh.yinmeng.ymykh2.activity.jifenvip;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ykh.yinmeng.ymykh2.R;
import com.ykh.yinmeng.ymykh2.activity.BaseActivity;
import com.ykh.yinmeng.ymykh2.activity.shanghuzicaixiangqing.ShanghuzicaixiangqingActivity;
import com.ykh.yinmeng.ymykh2.utils.ToastUtils;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.dialog.DialogUtil;


public class JifenVIPActivity extends BaseActivity implements View.OnClickListener, JifenVIPMVPView {
    private TextView tv_title,tv_menu,tv_money,tv_zuanshimoney,tv_huangjin,tv_zuanshi,tv_quanyi,tv_quanyi1;
    private Button bt_huangjinchongzhi,bt_zuanshichongzhi,bt_free_huangjin,bt_free_zuanshi;
    private Dialog loadingDialog;
    private LinearLayout ll_return,ll_menu;

    private JifenVIPPresenter<JifenVIPMVPView> presenter;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        presenter = new JifenVIPPresenterImp<>();
        presenter.onAttach(this);
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_vip);
    }

    @Override
    public void initViews() {
        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_title.setText("会员升级");
        ll_return = (LinearLayout)findViewById(R.id.ll_return);
        ll_menu = (LinearLayout)findViewById(R.id.ll_menu);
        ll_menu.setVisibility(View.VISIBLE);
        tv_menu = (TextView) findViewById(R.id.tv_menu);
        tv_menu.setText("专享特权");
        tv_money = (TextView) findViewById(R.id.tv_money);
        tv_zuanshimoney = (TextView) findViewById(R.id.tv_zuanshimoney);
        tv_huangjin = (TextView) findViewById(R.id.tv_huangjin);
        tv_zuanshi = (TextView) findViewById(R.id.tv_zuanshi);
        tv_quanyi = (TextView) findViewById(R.id.tv_quanyi);
        tv_quanyi1 = (TextView) findViewById(R.id.tv_quanyi1);
        bt_huangjinchongzhi = (Button) findViewById(R.id.bt_huangjinchongzhi);
        bt_zuanshichongzhi = (Button) findViewById(R.id.bt_zuanshichongzhi);
        bt_free_huangjin = (Button) findViewById(R.id.bt_free_huangjin);
        bt_free_zuanshi = (Button) findViewById(R.id.bt_free_zuanshi);
        Typeface textFont1 = Typeface.createFromAsset(getAssets(), "FZDHTJW.TTF");
        Typeface textFont2 = Typeface.createFromAsset(getAssets(), "FZS3JW.TTF");
        tv_huangjin.setTypeface(textFont1);
        tv_zuanshi.setTypeface(textFont1);
        tv_quanyi.setTypeface(textFont2);
        tv_quanyi1.setTypeface(textFont2);
        loadingDialog = DialogUtil.createLoadingDialog(this, "");

    }

    @Override
    public void initListeners() {
        ll_return.setOnClickListener(this);
        bt_huangjinchongzhi.setOnClickListener(this);
        bt_zuanshichongzhi.setOnClickListener(this);
        bt_free_huangjin.setOnClickListener(this);
        bt_free_zuanshi.setOnClickListener(this);
        ll_menu.setOnClickListener(this);

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_return:
                JifenVIPActivity.this.finish();
                break;
            case R.id.bt_huangjinchongzhi:
                presenter.OnChongzhiBtn(this,1);
                break;
            case R.id.bt_zuanshichongzhi:
                presenter.OnChongzhiBtn(this,2);
                break;
            case R.id.ll_menu:
                Intent intent = new Intent(this,JifenVIPtequanActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_free_huangjin:
                presenter.OnFreeBtn(this,"1");
                break;
            case R.id.bt_free_zuanshi:
                presenter.OnFreeBtn(this,"2");
                break;
                default:
                    break;
        }
    }


    @Override
    public void showVIPHuangjinSuccess(String viphaungjin) {
        Typeface textFont1 = Typeface.createFromAsset(getAssets(), "DIN-MEDIUM.OTF");
        tv_money.setTypeface(textFont1);
        tv_money.setText("￥"+viphaungjin);
    }

    @Override
    public void showVIPzuanshiSuccess(String vipzuanshi) {
        Typeface textFont1 = Typeface.createFromAsset(getAssets(), "DIN-MEDIUM.OTF");
        tv_zuanshimoney.setTypeface(textFont1);
        tv_zuanshimoney.setText("￥"+vipzuanshi);
    }

    @Override
    public void getVIPSuccess(String msg) {
        ToastUtils.showToast(this, msg, Toast.LENGTH_SHORT);
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
