package com.ykh.yinmeng.ymykh2.activity.accard;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.sdk.model.BankCardParams;
import com.baidu.ocr.sdk.model.BankCardResult;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.lljjcoder.style.citylist.utils.CityListLoader;
import com.lljjcoder.style.citythreelist.CityBean;
import com.lljjcoder.style.citythreelist.ProvinceActivity;
import com.ykh.yinmeng.ymykh2.R;
import com.ykh.yinmeng.ymykh2.activity.BaseActivity;
import com.ykh.yinmeng.ymykh2.app.App;
import com.ykh.yinmeng.ymykh2.bean.IDCardBean;
import com.ykh.yinmeng.ymykh2.utils.LogUtils;
import com.ykh.yinmeng.ymykh2.utils.ToastUtils;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.dialog.DialogUtil;

import java.io.File;


public class ACCardActivity extends BaseActivity implements View.OnClickListener, ACcardMVPView {
    private static final int REQUEST_CODE_BANKCARD = 111;
    private boolean hasGotToken = false;
    private RelativeLayout rl_location;
    private EditText et_card, et_bank, et_bankname;
    private TextView tv_title, tv_location_city;
    private ImageButton ib_flick;
    private Button bt_login;
    private LinearLayout ll_return;
    private AlertDialog.Builder alertDialog;
    private Dialog loadingDialog;
    public ACcardPresenter<ACcardMVPView> presenter;
    private String mCurrentProviceName, mCurrentCityName, mCurrentAreaName;
    IDCardBean resultZM = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        presenter = new ACcardPresenterImp<>();
        presenter.onAttach(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_card_ac);
    }

    @Override
    public void initViews() {
        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_title.setText("银行卡认证");
        ll_return = (LinearLayout)findViewById(R.id.ll_return);
        et_card = (EditText) findViewById(R.id.et_card);
        et_bank = (EditText) findViewById(R.id.et_bank);
        et_bankname = (EditText) findViewById(R.id.et_bankname);
        tv_location_city = (TextView) findViewById(R.id.tv_location_city);
        bt_login = (Button) findViewById(R.id.bt_login);
        rl_location = (RelativeLayout) findViewById(R.id.rl_location);
        ib_flick = (ImageButton) findViewById(R.id.ib_flick);
        loadingDialog = DialogUtil.createLoadingDialog(this, "");
        alertDialog = new AlertDialog.Builder(this);
    }

    @Override
    public void initListeners() {
        ll_return.setOnClickListener(this);
        rl_location.setOnClickListener(this);
        bt_login.setOnClickListener(this);
        ib_flick.setOnClickListener(this);

    }

    @Override
    public void initData() {
        initAccessToken();
        presenter.onInit(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_return:
                ACCardActivity.this.finish();
                break;
            case R.id.rl_location:
                CityListLoader.getInstance().loadCityData(this);
                CityListLoader.getInstance().loadProData(this);
                Intent intent = new Intent(getApplicationContext(), ProvinceActivity.class);
                startActivityForResult(intent, ProvinceActivity.RESULT_DATA);
                break;
            case R.id.bt_login:
                //银行卡号
                String banksNumber = et_card.getText().toString().trim();
                //银行名称
                String bankName = et_bank.getText().toString().trim();
                //支行名称

                String zhihang = et_bankname.getText().toString().trim();
                String address = tv_location_city.getText().toString().trim();
                String branch = address+"-"+zhihang;
                //开户人姓名
                String banksAccount = resultZM.getName();
                if (TextUtils.isEmpty(banksNumber)) {
                    ToastUtils.showToast(this, "请输入银行卡号", Toast.LENGTH_SHORT);
                    return;
                }

                if (TextUtils.isEmpty(bankName)) {
                    ToastUtils.showToast(this, "请输入开户银行", Toast.LENGTH_SHORT);
                    return;
                }
                if (TextUtils.isEmpty(branch)) {
                    ToastUtils.showToast(this, "请输入支行名称", Toast.LENGTH_SHORT);
                    return;
                }

                presenter.onToggleAddBtn(banksAccount, banksNumber,bankName, branch, "1");
                break;
            case R.id.ib_flick:
                if (!hasGotToken) {
                    ToastUtils.showToast(this, "识别准备中", Toast.LENGTH_SHORT);
                    return;
                }
                Intent intent2 = new Intent(mContext, CameraActivity.class);
                intent2.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH, getSaveFile(getApplicationContext()).getAbsolutePath());
                intent2.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_BANK_CARD);
                startActivityForResult(intent2, REQUEST_CODE_BANKCARD);
                break;
        }
    }

    public static File getSaveFile(Context context) {
        File file = new File(context.getFilesDir(), "pic.jpg");
        return file;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ProvinceActivity.RESULT_DATA) {
            if (resultCode == Activity.RESULT_OK) {
                if (data == null) {
                    return;
                }
                CityBean area = data.getParcelableExtra("area");
                CityBean city = data.getParcelableExtra("city");
                CityBean province = data.getParcelableExtra("province");
                mCurrentProviceName = province.getName();
                mCurrentCityName = city.getName();
                mCurrentAreaName = area.getName();
                tv_location_city.setText(mCurrentProviceName + "-" + mCurrentCityName + "-" + mCurrentAreaName);
                LogUtils.i(TAG, "所选省市区城市： " + province.getName() + " " + province.getId() + "\n" + city.getName() + " " + city.getId() + "\n" + area.getName() + " " + area.getId() + "\n");
            }
        }
        // 识别成功回调，银行卡识别
        if (requestCode == REQUEST_CODE_BANKCARD && resultCode == Activity.RESULT_OK) {
            showDialog();

            BankCardParams param = new BankCardParams();
            param.setImageFile(getSaveFile(getApplicationContext()));
            OCR.getInstance(this).recognizeBankCard(param, new OnResultListener<BankCardResult>() {
                @Override
                public void onResult(final BankCardResult result) {
                    String res = String.format("卡号：%s\n类型：%s\n发卡行：%s", result.getBankCardNumber(), result.getBankCardType().name(), result.getBankName());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dismissDialog();
                            et_card.setText(result.getBankCardNumber().replace(" ",""));
                            et_bank.setText(result.getBankName());
                        }
                    });
                }

                @Override
                public void onError(OCRError error) {
                    ToastUtils.showToast(mContext, error.getMessage(), Toast.LENGTH_SHORT);
                    dismissDialog();
                }
            });
        }
    }

    /**
     * 以license文件方式初始化
     */
    private void initAccessToken() {
//        showDialog();
        OCR.getInstance(mContext).initAccessToken(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken accessToken) {
                String token = accessToken.getAccessToken();
                hasGotToken = true;
//                dismissDialog();
            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
                alertText("licence方式获取token失败", error.getMessage());
//                dismissDialog();
            }
        }, App.getContext());
    }


    private void alertText(final String title, final String message) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                alertDialog.setTitle(title)
                        .setMessage(message)
                        .setPositiveButton("确定", null)
                        .show();
            }
        });
    }
    @Override
    public void setBankName(String bankName) {
        et_bank.append(bankName);
    }

    @Override
    public void setBanksNumber(String banksNumber) {
        et_card.append(banksNumber);
    }

    @Override
    public void setBranch(String branch) {
        et_bankname.getText();
    }

    @Override
    public void setStatus(boolean status) {
    }

    @Override
    public void setBankAccount(String bankAccount) {
    }

    @Override
    public void addBankcardSuccess() {
        ToastUtils.showToast(this, "认证银行卡成功",Toast.LENGTH_SHORT);
        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    public void addBankcardFailure(String msg) {
        ToastUtils.showToast(this, msg, Toast.LENGTH_SHORT);
    }

    @Override
    public void editBankcardSuccess() {
        ToastUtils.showToast(this, "修改银行卡成功", Toast.LENGTH_SHORT);
        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    public void editBankcardFailure(String msg) {
        ToastUtils.showToast(this, "msg",Toast.LENGTH_SHORT);
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
