package com.ykh.yinmeng.ymykh2.activity.jifenguanli;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.ykh.yinmeng.ymykh2.R;
import com.ykh.yinmeng.ymykh2.activity.BaseActivity;
import com.ykh.yinmeng.ymykh2.activity.jifenjindu.JifenjinduActivity;
import com.ykh.yinmeng.ymykh2.activity.jifenshouzhi.JifenshouzhiActivity;
import com.ykh.yinmeng.ymykh2.activity.jifentixian.JifenTixianActivity;
import com.ykh.yinmeng.ymykh2.activity.jifenvip.JifenVIPActivity;
import com.ykh.yinmeng.ymykh2.activity.kefuweixin.KefuActivity;
import com.ykh.yinmeng.ymykh2.utils.ToastUtils;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.dialog.DialogUtil;

public class JifenguanliActivity extends BaseActivity implements View.OnClickListener, JifenguanliMVPView {
    private LinearLayout ll_return;
    private TextView tv_title,tv_general,tv_total_money,tv_integral,tv_earn;
    private RelativeLayout rl_extract,rl_member,rl_shouzhi,rl_progress,rl_service;
    Intent intent;

    private Dialog loadingDialog;
    public JifenguanliPresenter<JifenguanliMVPView> presenter;
    private SmartRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        presenter = new JifenguanliPresenterImp<>();
        presenter.onAttach(this);
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_jifengaunli);
    }

    @Override
    public void initViews() {
        ll_return = (LinearLayout) findViewById(R.id.ll_return);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("积分管理");
        tv_general = (TextView) findViewById(R.id.tv_general);
        tv_total_money = (TextView) findViewById(R.id.tv_total_money);
        tv_integral = (TextView) findViewById(R.id.tv_integral);
        tv_earn = (TextView) findViewById(R.id.tv_earn);
        rl_extract = (RelativeLayout) findViewById(R.id.rl_extract);
        rl_member = (RelativeLayout) findViewById(R.id.rl_member);
        rl_shouzhi = (RelativeLayout) findViewById(R.id.rl_shouzhi);
        rl_progress = (RelativeLayout) findViewById(R.id.rl_progress);
        rl_service = (RelativeLayout) findViewById(R.id.rl_service);
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        loadingDialog = DialogUtil.createLoadingDialog(this, "");
        refreshLayout.setOnRefreshListener(new com.scwang.smartrefresh.layout.listener.OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                presenter.onAttach(JifenguanliActivity.this);
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
//                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });

    }

    @Override
    public void initListeners() {
        ll_return.setOnClickListener(this);
        rl_extract.setOnClickListener(this);
        rl_member.setOnClickListener(this);
        rl_shouzhi.setOnClickListener(this);
        rl_progress.setOnClickListener(this);
        rl_service.setOnClickListener(this);

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_return:
                JifenguanliActivity.this.finish();
                break;
            case R.id.rl_extract:
                intent = new Intent(this, JifenTixianActivity.class);
                intent.putExtra("coin",tv_total_money.getText().toString());
                startActivity(intent);
                break;
            case R.id.rl_member:
                intent = new Intent(this, JifenVIPActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_shouzhi:
                intent = new Intent(this, JifenshouzhiActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_progress:
                intent = new Intent(this, JifenjinduActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_service:
                intent = new Intent(this, KefuActivity.class);
                startActivity(intent);
                break;
                default:
                    break;
        }
    }


    @Override
    public void showCoinSuccess(String coin) {
        Typeface textFont1 = Typeface.createFromAsset(getAssets(), "DIN-BLACK.OTF");
        tv_total_money.setTypeface(textFont1);
        tv_total_money.setText(coin);
    }

    @Override
    public void showInCoinSuccess(String incoin) {
        tv_earn.setText(incoin);
    }

    @Override
    public void showOutCoinSuccess(String outcoin) {

    }

    @Override
    public void showInRankSuccess(String inrank) {
        tv_integral.setText(inrank);
    }

    @Override
    public void getStatusSuccess(int status) {
        if (status == 0){
            tv_general.setText("普通会员");
        }
        if (status == 1){
            tv_general.setText("黄金会员");
        }
        if (status == 2){
            tv_general.setText("钻石会员");
        }
    }

    @Override
    public void getDataFailure(String msg) {
        ToastUtils.showToast(mContext, msg, Toast.LENGTH_SHORT);
    }

    @Override
    public void showDialog() {
//        loadingDialog.show();
    }

    @Override
    public void dismissDialog() {
//        if (loadingDialog != null && loadingDialog.isShowing()) {
//            loadingDialog.dismiss();
//        }
    }
}
