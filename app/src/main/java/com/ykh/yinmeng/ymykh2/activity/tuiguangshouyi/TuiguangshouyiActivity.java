package com.ykh.yinmeng.ymykh2.activity.tuiguangshouyi;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ykh.yinmeng.ymykh2.R;
import com.ykh.yinmeng.ymykh2.activity.AuthenBaseActivity;
import com.ykh.yinmeng.ymykh2.activity.BaseActivity;
import com.ykh.yinmeng.ymykh2.activity.shouyixiangqing.ShouyixiangqingActivity;
import com.ykh.yinmeng.ymykh2.utils.ToastUtils;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.ChartView;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.dialog.DialogUtil;


public class TuiguangshouyiActivity extends BaseActivity implements View.OnClickListener,TuiguangshouyiMVPView {

    private LinearLayout ll_return,ll_xiangqing;
    TextView tv_title,tv_money,members_month,business_month,activate_month,activate_today;
    ChartView chart_view;
    Intent intent;


    private Dialog loadingDialog;
    public TuiguangshouyiPresenter<TuiguangshouyiMVPView> presenter;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        presenter = new TuiguangshouyiPresenterImp<>();
        presenter.onAttach(this);
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    public void setContentView() {
        setContentView(R.layout.activity_tuiguangshouyi);
    }

    @Override
    public void initViews() {
        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_title.setText("推广收益");
        ll_return = (LinearLayout)findViewById(R.id.ll_return);
        ll_xiangqing = (LinearLayout) findViewById(R.id.ll_xiangqing);
        tv_money = (TextView) findViewById(R.id.tv_money);
        members_month = (TextView) findViewById(R.id.members_month);
        business_month = (TextView) findViewById(R.id.business_month);
        activate_month = (TextView) findViewById(R.id.activate_month);
        activate_today = (TextView) findViewById(R.id.activate_today);
        chart_view = (ChartView) findViewById(R.id.chart_view);
        loadingDialog = DialogUtil.createLoadingDialog(this, "");

    }

    @Override
    public void initListeners() {
        ll_return.setOnClickListener(this);
        ll_xiangqing.setOnClickListener(this);

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_return:
                TuiguangshouyiActivity.this.finish();
                break;
            case R.id.ll_xiangqing:
                intent = new Intent(TuiguangshouyiActivity.this, ShouyixiangqingActivity.class);
                startActivity(intent);
                break;
                default:
                    break;
        }
    }

    @Override
    public void getDataSuccess() {

    }

    @Override
    public void getDataFailure(String msg) {
        ToastUtils.showToast(this, msg, Toast.LENGTH_SHORT);
    }


    @Override
    public void showChartView(String[] xLabel,String[] data) {
        chart_view.setTitle("推广收益(单位：万元)");
        chart_view.setxLabel(xLabel);
        chart_view.setData(data);
        chart_view.fresh();
    }
    @Override
    public void showUserAccount(String msg) {
        members_month.setText(msg+"人");
    }

    @Override
    public void showActiveShop(String msg) {
        business_month.setText(msg+"人");
    }

    @Override
    public void showActiveCount(String msg) {
        tv_money.setText(msg);
    }

    @Override
    public void showZSumAccount(String msg) {
        activate_month.setText(msg+"人");
    }

    @Override
    public void showActiveZShop(String msg) {
        activate_today.setText(msg+"人");
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
