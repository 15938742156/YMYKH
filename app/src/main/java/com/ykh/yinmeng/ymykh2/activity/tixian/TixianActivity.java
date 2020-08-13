package com.ykh.yinmeng.ymykh2.activity.tixian;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ykh.yinmeng.ymykh2.R;
import com.ykh.yinmeng.ymykh2.activity.BaseActivity;
import com.ykh.yinmeng.ymykh2.activity.PayDialog;
import com.ykh.yinmeng.ymykh2.activity.TixianjiluActivity;
import com.ykh.yinmeng.ymykh2.activity.bangdingzhifubao.ZhifubaoActivity;
import com.ykh.yinmeng.ymykh2.activity.zhifufangshi.ZhifufangshiActivity;
import com.ykh.yinmeng.ymykh2.app.App;
import com.ykh.yinmeng.ymykh2.fragment.tixianjilu.TixianjiluFragment;
import com.ykh.yinmeng.ymykh2.bean.CardBean;
import com.ykh.yinmeng.ymykh2.utils.Constant;
import com.ykh.yinmeng.ymykh2.utils.MD5.MD5Util;
import com.ykh.yinmeng.ymykh2.utils.PermissionListener;
import com.ykh.yinmeng.ymykh2.utils.SharedPreferencesUtils;
import com.ykh.yinmeng.ymykh2.utils.StringReplaceUtil;
import com.ykh.yinmeng.ymykh2.utils.ToastUtils;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.dialog.DialogUtil;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class TixianActivity extends BaseActivity implements View.OnClickListener,TixianMVPView {
    private TextView tv_money,tv_card,tv_ka,tv_cardno,tv_title,tv_menu,tv_addcard,tv_zhifubao;
    private EditText et_tixian;
    private Button bt_queren;
    LinearLayout ll_return,ll_menu;
    RelativeLayout ll_select_card,rl_zhifubao;
    ImageButton ib_yinhangka;
    private Dialog loadingDialog;
    Intent intent;
    private CheckBox checkbox_card,checkbox_zhifubao;

    private TixianPresenter<TixianMVPView> presenter;
    int isReal;

    @Override
    protected void onCreate(Bundle arg0) {
        presenter = new TixianPresenterImp<>();
        presenter.onAttach(this);
        super.onCreate(arg0);
        ToastUtils.longToast(this,"每次提现不能少于100元");
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_tixian);
    }


    @Override
    public void initViews() {
        ll_return = (LinearLayout) findViewById(R.id.ll_return);
        ll_menu = (LinearLayout)findViewById(R.id.ll_menu);
        ll_menu.setVisibility(View.VISIBLE);
        ll_select_card = (RelativeLayout) findViewById(R.id.ll_select_card);
        rl_zhifubao = (RelativeLayout) findViewById(R.id.rl_zhifubao);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("提现");
        tv_menu = (TextView)findViewById(R.id.tv_menu);
        tv_menu.setText("来往明细");
        tv_money = (TextView)findViewById(R.id.tv_money);
        tv_cardno = (TextView) findViewById(R.id.tv_cardno);
        tv_ka = (TextView) findViewById(R.id.tv_ka);
        tv_card = (TextView) findViewById(R.id.tv_card);
        tv_addcard = (TextView) findViewById(R.id.tv_addcard);
        tv_zhifubao = (TextView) findViewById(R.id.tv_zhifubao);
        String aliAccount = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_ALIACCOUNT);
        if (aliAccount.isEmpty()|| aliAccount == null){
            tv_zhifubao.setText("未绑定");
        }else {
            tv_zhifubao.setText(StringReplaceUtil.isMobileNum(aliAccount));
        }
        et_tixian = (EditText) findViewById(R.id.et_tixian);
        bt_queren = (Button) findViewById(R.id.bt_queren);
        ib_yinhangka = (ImageButton) findViewById(R.id.ib_yinhangka);
        checkbox_card = (CheckBox)findViewById(R.id.checkbox_card);
        checkbox_zhifubao = (CheckBox)findViewById(R.id.checkbox_zhifubao);
        checkbox_card.setChecked(true);
        ll_select_card.setVisibility(View.VISIBLE);
        loadingDialog = DialogUtil.createLoadingDialog(this, "");
    }

    @Override
    public void initListeners() {
        ll_return.setOnClickListener(this);
        ll_select_card.setOnClickListener(this);
        rl_zhifubao.setOnClickListener(this);
        tv_menu.setOnClickListener(this);
        bt_queren.setOnClickListener(this);
        tv_addcard.setOnClickListener(this);
        et_tixian.setOnClickListener(this);
        checkbox_card.setOnClickListener(this);
        checkbox_zhifubao.setOnClickListener(this);
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
                TixianActivity.this.finish();
                break;
            case R.id.ll_select_card:
                presenter.onToggleSelectCardBtn(this);
                break;
            case R.id.tv_menu:
                intent = new Intent(TixianActivity.this, TixianjiluActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_queren:
                isReal = SharedPreferencesUtils.getInstance(this).get(Constant.SHARED_AUTHENTICATION, -1);
                if (isReal !=1 ){
                    ToastUtils.showToast(this, "亲爱的用户,为确保您的账户安全,进行此业务之前请先进行用户认证。", Toast.LENGTH_SHORT);
                }else {
                    if(et_tixian.getText().toString().isEmpty()){
                        ToastUtils.showToast(mContext, "请输入提现金额", Toast.LENGTH_SHORT);
                    }else {
                        if (Double.valueOf(et_tixian.getText().toString()) < 0){
                            ToastUtils.showToast(mContext, "提现的金额不能小于100元", Toast.LENGTH_SHORT);
                        }else {
                            crestePayDialog();
                        }

                    }
                }
                break;
            case R.id.tv_addcard:
                presenter.onToggleSelectCardBtn(this);
                break;
            case R.id.checkbox_card:
                if (checkbox_card.isChecked()){
                    ll_select_card.setVisibility(View.VISIBLE);
                    checkbox_zhifubao.setChecked(false);
                }else {
                    checkbox_card.setChecked(false);
                    checkbox_zhifubao.setChecked(true);
                    ll_select_card.setVisibility(View.GONE);
                }
                break;
            case R.id.checkbox_zhifubao:
                if (checkbox_zhifubao.isChecked()){
                    if (TextUtils.equals(tv_zhifubao.getText(),"未绑定")){
                        intent = new Intent(TixianActivity.this, ZhifubaoActivity.class);
                        startActivity(intent);
                    }
                    checkbox_card.setChecked(false);
                    ll_select_card.setVisibility(View.GONE);
                }else {
                    checkbox_zhifubao.setChecked(false);
                    checkbox_card.setChecked(true);
                    ll_select_card.setVisibility(View.VISIBLE);
                }


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
                    Toast.makeText(TixianActivity.this, "密码为错误，请重试", Toast.LENGTH_SHORT).show();
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
        if (checkbox_zhifubao.isChecked()){
            checkbox_card.setChecked(false);
            ll_select_card.setVisibility(View.GONE);
            presenter.onZhifubaoBtn(this);
        }
        if (checkbox_card.isChecked()){
            checkbox_zhifubao.setChecked(false);
            presenter.onConfirmBtn(this);
        }

    }

    @Override
    public void onPwdErro(String msg) {
        //密码错误，弹出输入密码
        ToastUtils.showToast(this, msg, Toast.LENGTH_LONG);
        crestePayDialog();
    }

    @Override
    public void OnZhifubaoSuccess(String msg) {
        ToastUtils.showToast(this, msg, Toast.LENGTH_SHORT);
    }

    @Override
    public void OnZhifubaoFailure(String msg) {
        ToastUtils.showToast(this, msg, Toast.LENGTH_SHORT);
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
