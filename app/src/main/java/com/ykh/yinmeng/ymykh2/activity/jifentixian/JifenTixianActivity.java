package com.ykh.yinmeng.ymykh2.activity.jifentixian;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ykh.yinmeng.ymykh2.R;
import com.ykh.yinmeng.ymykh2.activity.BaseActivity;
import com.ykh.yinmeng.ymykh2.activity.PayDialog;
import com.ykh.yinmeng.ymykh2.bean.CardBean;
import com.ykh.yinmeng.ymykh2.utils.StringReplaceUtil;
import com.ykh.yinmeng.ymykh2.utils.ToastUtils;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.dialog.DialogUtil;



public class JifenTixianActivity extends BaseActivity implements View.OnClickListener, JifenTixianMVPView {
    private TextView tv_money,tv_card,tv_ka,tv_cardno,tv_title,tv_addcard;
    private EditText et_tixian;
    private Button bt_queren;
    LinearLayout ll_return;
    RelativeLayout ll_select_card;
    ImageButton ib_yinhangka;
    private Dialog loadingDialog;

    private JifenTixianPresenter<JifenTixianMVPView> presenter;

    @Override
    protected void onCreate(Bundle arg0) {
        presenter = new JifenTixianPresenterImp<>();
        presenter.onAttach(this);
        super.onCreate(arg0);
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_jifentixian);
    }


    @Override
    public void initViews() {
        ll_return = (LinearLayout) findViewById(R.id.ll_return);
        ll_select_card = (RelativeLayout) findViewById(R.id.ll_select_card);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("提现");
        tv_money = (TextView)findViewById(R.id.tv_money);
        tv_cardno = (TextView) findViewById(R.id.tv_cardno);
        tv_ka = (TextView) findViewById(R.id.tv_ka);
        tv_card = (TextView) findViewById(R.id.tv_card);
        tv_addcard = (TextView) findViewById(R.id.tv_addcard);
        et_tixian = (EditText) findViewById(R.id.et_tixian);
        bt_queren = (Button) findViewById(R.id.bt_queren);
        ib_yinhangka = (ImageButton) findViewById(R.id.ib_yinhangka);
        loadingDialog = DialogUtil.createLoadingDialog(this, "");
    }

    @Override
    public void initListeners() {
        ll_return.setOnClickListener(this);
        ll_select_card.setOnClickListener(this);
        bt_queren.setOnClickListener(this);
        tv_addcard.setOnClickListener(this);
        et_tixian.setOnClickListener(this);
        ettixian();

    }

    @Override
    public void initData() {
        presenter.onInit(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_return:
                JifenTixianActivity.this.finish();
                break;
            case R.id.ll_select_card:
                presenter.onToggleSelectCardBtn(this);
                break;
            case R.id.bt_queren:
                if(et_tixian.getText().toString().isEmpty()){
                    ToastUtils.showToast(mContext, "请输入提现金额", Toast.LENGTH_SHORT);
                }else {
                    crestePayDialog();
                }
                break;
            case R.id.tv_addcard:
                presenter.onToggleSelectCardBtn(this);
                break;
                default:
                    break;
        }
    }

    public void ettixian(){
        et_tixian.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.onTextChanged(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    void crestePayDialog(){
        final PayDialog payDialog = new PayDialog(this);
        payDialog.setPasswordCallback(new PayDialog.PasswordCallback() {
            @Override
            public void callback(String password) {
                if ("000000".equals(password)) {
                    payDialog.clearPasswordText();
                    Toast.makeText(JifenTixianActivity.this, "密码为错误，请重试", Toast.LENGTH_SHORT).show();
                } else {
                    presenter.confirmPayPwd(password);
                    payDialog.dismiss();
                }
            }
        });
        payDialog.clearPasswordText();
        payDialog.show();


    }
    @Override
    public void onPwdCorrect() {
        //密码正确，形成订单
        presenter.onConfirmBtn(this);

    }

    @Override
    public void onPwdErro(String msg) {
        //密码错误，弹出输入密码
        ToastUtils.showToast(this, msg, Toast.LENGTH_SHORT);
        crestePayDialog();
    }
    @Override
    public void updateCard(CardBean card) {
        if (card != null){
            tv_card.setText(card.getBanks());
            String cardno = StringReplaceUtil.bankCardReplaceWithStar(card.getBanksNumber().replace(" ",""));
            tv_cardno.setText(cardno);
            tv_ka.setText("储蓄卡");
            ib_yinhangka.setVisibility(View.VISIBLE);
            tv_addcard.setVisibility(View.GONE);
        }else {
            ib_yinhangka.setVisibility(View.GONE);
            tv_card.setVisibility(View.GONE);
            tv_cardno.setVisibility(View.GONE);
            tv_ka.setVisibility(View.GONE);
            tv_addcard.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void getMoneySuccess(String money) {
        tv_money.setText(money);
    }


    @Override
    public void getDataSuccess(String msg) {
        ToastUtils.showToast(mContext, msg, Toast.LENGTH_SHORT);
    }

    @Override
    public void getDataFailure(String msg) {
        ToastUtils.showToast(mContext, msg, Toast.LENGTH_SHORT);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.onActivityResult(requestCode, resultCode, data);
    }


}
