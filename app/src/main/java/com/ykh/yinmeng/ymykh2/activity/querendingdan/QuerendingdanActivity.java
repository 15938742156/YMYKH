package com.ykh.yinmeng.ymykh2.activity.querendingdan;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.ocr.ui.util.ImageUtil;
import com.ykh.yinmeng.ymykh2.R;
import com.ykh.yinmeng.ymykh2.activity.BaseActivity;
import com.ykh.yinmeng.ymykh2.activity.PayDialog;
import com.ykh.yinmeng.ymykh2.bean.AddressBean;
import com.ykh.yinmeng.ymykh2.bean.POSStoreBean;
import com.ykh.yinmeng.ymykh2.utils.GlideUtils;
import com.ykh.yinmeng.ymykh2.utils.ToastUtils;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.dialog.DialogPopup;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.dialog.DialogUtil;

public class QuerendingdanActivity extends BaseActivity implements View.OnClickListener,QuerendingdanMVPView {

    private TextView tv_title,tv_name,tv_num,tv_tel,tv_address,tv_pos,tv_jifen,tv_nowjifen,tv_jifentotal,tv_tianjia,tv_yun,tv_addressadd,tv_goods_name,tv_attention,tv_rebate,tv_cash,tv_buynum;
    private ImageView img_fanhui,img_dingwei,img_goods;
    private Button bt_tijiao;
    private RelativeLayout rl;
    private Dialog loadingDialog;
    private LinearLayout ll_return;
    private EditText et_zhifujifen;
    String price;
    String num;
    int type;

    public QuerendingdanPresenter<QuerendingdanMVPView> presenter;
    @Override
    protected void onCreate(Bundle arg0) {
        presenter = new QuerendingdanPresenterImp<>();
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
        setContentView(R.layout.activity_querendingdan);
    }

    @Override
    public void initViews() {
        ll_return = (LinearLayout)findViewById(R.id.ll_return);
        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_title.setText("确认订单");
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_tel = (TextView) findViewById(R.id.tv_tel);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_pos = (TextView) findViewById(R.id.tv_pos);
        tv_yun = (TextView)findViewById(R.id.tv_yun);
        tv_num = (TextView) findViewById(R.id.tv_num);
        tv_jifen = (TextView) findViewById(R.id.tv_jifen);
        tv_nowjifen = (TextView) findViewById(R.id.tv_nowjifen);
        tv_jifentotal = (TextView) findViewById(R.id.tv_jifentotal);
        tv_addressadd = (TextView)findViewById(R.id.tv_addressadd);
        tv_goods_name = (TextView)findViewById(R.id.tv_goods_name);
        tv_attention = (TextView)findViewById(R.id.tv_attention);
        tv_rebate = (TextView)findViewById(R.id.tv_rebate);
        tv_cash = (TextView)findViewById(R.id.tv_cash);
        tv_buynum = (TextView)findViewById(R.id.tv_buynum);
        et_zhifujifen = (EditText) findViewById(R.id.et_zhifujifen);
        img_fanhui = (ImageView) findViewById(R.id.img_fanhui);
        img_dingwei = (ImageView)findViewById(R.id.img_dingwei);
        img_goods = (ImageView) findViewById(R.id.img_goods);
        bt_tijiao = (Button) findViewById(R.id.bt_tijiao);
        loadingDialog = DialogUtil.createLoadingDialog(this, "");
    }

    @Override
    public void initListeners() {
        ll_return.setOnClickListener(this);
        bt_tijiao.setOnClickListener(this);
        img_fanhui.setOnClickListener(this);
        tv_addressadd.setOnClickListener(this);
        et_zhifujifen.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                presenter.onEditTextChange(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @Override
    public void initData() {
        presenter.onInit(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_return:
                QuerendingdanActivity.this.finish();
                break;
            case R.id.img_fanhui:
                presenter.onToggleSelectAddressBtn(this);
                break;
            case R.id.bt_tijiao:
                if (Double.valueOf(price) < 0){
                    ToastUtils.showToast(mContext, "抵扣金额需小于当前余额", Toast.LENGTH_SHORT);
                }else {
                    if (TextUtils.equals("1",num)){
                        new DialogPopup(mContext)
                                .setTitle("提示")
                                .setContent("单台购买需选择自用或者非自用")
                                .setConfirmText("自用")
                                .setCancelText("非自用")
                                .setClickListener(new DialogPopup.ViewClickListener() {
                                    @Override
                                    public void confirm(View view) {
                                        presenter.onToggleBtn(0);
                                        crestePayDialog();
                                    }

                                    @Override
                                    public void cancel(View view) {
                                        presenter.onToggleBtn(2);
                                        crestePayDialog();
                                    }
                                }).showPopupWindow();
                    }else {
                        crestePayDialog();
                        presenter.onToggleBtn(1);
                    }

                }
                break;
            case R.id.tv_addressadd:
                presenter.onToggleSelectAddressBtn(this);
                break;
                default:
                    break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        presenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void updatePOSStoreBean(POSStoreBean posStoreBean) {
    }

    void crestePayDialog(){
        final PayDialog payDialog = new PayDialog(this);
        payDialog.setPasswordCallback(new PayDialog.PasswordCallback() {
            @Override
            public void callback(String password) {
                if ("000000".equals(password)) {
                    payDialog.clearPasswordText();
                    Toast.makeText(QuerendingdanActivity.this, "密码为错误，请重试", Toast.LENGTH_SHORT).show();
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
    public void updateTotalPice(String price) {
        tv_jifen.setText(price);
    }

    @Override
    public void updateBalanceTotalPice(String price) {
        this.price =price;
        tv_jifentotal.setText(price);
    }

    @Override
    public void getNowbalancePrice(String nowPrice) {
        tv_nowjifen.setText(nowPrice+"元");
    }

    @Override
    public void updateAddress(AddressBean address) {
        if (address != null){
            tv_name.setText(address.getShrName());
            tv_tel.setText(address.getShrMobile());
            tv_address.setText(new StringBuilder()
                    .append(address.getShrProvince())
                    .append(address.getShrCity())
                    .append(address.getShrArea())
                    .append(address.getShrAddress())
                    .toString());
            img_dingwei.setVisibility(View.VISIBLE);
            img_fanhui.setVisibility(View.VISIBLE);
            tv_addressadd.setVisibility(View.GONE);
        }else {
            tv_name.setVisibility(View.GONE);
            tv_address.setVisibility(View.GONE);
            tv_tel.setVisibility(View.GONE);
            img_dingwei.setVisibility(View.GONE);
            img_fanhui.setVisibility(View.GONE);
            tv_addressadd.setVisibility(View.VISIBLE);

        }

    }

    @Override
    public void getAdressFailure(String msg) {
        ToastUtils.showToast(mContext, "请重新选择收货地址", Toast.LENGTH_SHORT);
    }

    @Override
    public void orderPlaceSuccess() {
    }

    @Override
    public void orderPlaceFailure(String msg) {
        ToastUtils.showToast(mContext, msg, Toast.LENGTH_SHORT);
    }

    @Override
    public void getDataFailure(String msg) {
        ToastUtils.showToast(mContext, msg, Toast.LENGTH_SHORT);
    }

    @Override
    public void showTitleSuccess(String title) {
        tv_goods_name.setText(title);
    }

    @Override
    public void showMoneySuccess(String money) {
        tv_cash.setText(money+"元");
        tv_pos.setText(money+"元");
    }

    @Override
    public void showYunSuccess(String yun) {
        tv_yun.setText(yun+"元");

    }

    @Override
    public void showNumSuccess(String num) {
        this.num =num;
        tv_num.setText(num);
        tv_buynum.setText("x"+num);
    }

    @Override
    public void showGznumSuccess(String gz_num) {
        tv_attention.setText("关注数量"+gz_num);
    }

    @Override
    public void showMiaoshuSuccess(String miaoshu) {
        tv_rebate.setText(miaoshu);
    }

    @Override
    public void showImgSuccess(String img) {
        GlideUtils.loadImageView(QuerendingdanActivity.this,img,img_goods);

    }

    @Override
    public void onPwdCorrect() {
        //密码正确，形成订单
        if (tv_name.getText().toString().isEmpty()|| tv_tel.getText().toString().isEmpty()||tv_address.getText().toString().isEmpty()){
            ToastUtils.showToast(mContext, "请添加收货地址", Toast.LENGTH_SHORT);
        }else {
            presenter.onToggleEnsureBtn(QuerendingdanActivity.this);
        }

    }

    @Override
    public void onPwdErro(String msg) {
        //密码错误，弹出输入密码
        ToastUtils.showToast(this, msg, Toast.LENGTH_LONG);
        crestePayDialog();
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
