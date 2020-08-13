package com.ykh.yinmeng.ymykh2.activity.jifenaddcard;

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
import android.widget.CheckBox;
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
import com.ykh.yinmeng.ymykh2.utils.Constant;
import com.ykh.yinmeng.ymykh2.utils.LogUtils;
import com.ykh.yinmeng.ymykh2.utils.SharedPreferencesUtils;
import com.ykh.yinmeng.ymykh2.utils.ToastUtils;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.dialog.DialogUtil;

import java.io.File;


public class AddcardActivity extends BaseActivity implements View.OnClickListener, AddcardMVPView {

    private static final int REQUEST_CODE_BANKCARD = 111;
    private boolean hasGotToken = false;

    private EditText et_card,et_bank,et_bankname,et_name;
    private RelativeLayout rl_suozaidi;
    private CheckBox cb_moren;
    private Button bt_queding;
    private ImageButton ib_flick;
    private TextView tv_title,tv_location_city;
    private AlertDialog.Builder alertDialog;
    private LinearLayout ll_return;

    private Dialog loadingDialog;
    public AddcardPresenter<AddcardMVPView> presenter;

    private String mCurrentProviceName,mCurrentCityName,mCurrentAreaName;
    @Override
    protected void onCreate(Bundle arg0) {
        presenter = new AddcardPresenterImp<>();
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
        setContentView(R.layout.activity_jifenaddcard);
    }

    @Override
    public void initViews() {
        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_title.setText("添加银行卡");
        ll_return = (LinearLayout) findViewById(R.id.ll_return);
        et_name = (EditText) findViewById(R.id.et_name);
        et_card = (EditText) findViewById(R.id.et_card);
        et_bank =(EditText)findViewById(R.id.et_bank);
        et_bankname = (EditText)findViewById(R.id.et_bankname);
        rl_suozaidi = (RelativeLayout)findViewById(R.id.rl_suozaidi);
        cb_moren = (CheckBox)findViewById(R.id.cb_moren);
        bt_queding = (Button) findViewById(R.id.bt_queding);
        ib_flick = (ImageButton)findViewById(R.id.ib_flick);
        tv_location_city = (TextView) findViewById(R.id.tv_location_city);
        loadingDialog = DialogUtil.createLoadingDialog(this, "");
        alertDialog = new AlertDialog.Builder(this);

    }

    @Override
    public void initListeners() {
        ll_return.setOnClickListener(this);
        rl_suozaidi.setOnClickListener(this);
        cb_moren.setOnClickListener(this);
        bt_queding.setOnClickListener(this);
        ib_flick.setOnClickListener(this);

    }

    @Override
    public void initData() {
        initAccessToken();
        presenter.onInit(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_return:
                AddcardActivity.this.finish();
                break;
            case R.id.rl_suozaidi:
                CityListLoader.getInstance().loadCityData(this);
                CityListLoader.getInstance().loadProData(this);
                Intent intent = new Intent(getApplicationContext(), ProvinceActivity.class);
                startActivityForResult(intent, ProvinceActivity.RESULT_DATA);
                break;
            case R.id.bt_queding:
                //开户人姓名
                String banksAccount =et_name.getText().toString().trim();
                //银行卡号
                String banksNumber = et_card.getText().toString().trim();
                //银行名称
                String bankName = et_bank.getText().toString().trim();
                //支行名称
                String zhihang = et_bankname.getText().toString().trim();
                String address = tv_location_city.getText().toString().trim();
                String status = cb_moren.isChecked()?"1":"0";
                if (TextUtils.isEmpty(banksAccount)){
                    ToastUtils.showToast(this,"请输入持卡人姓名",Toast.LENGTH_SHORT);
                    return;
                }
                if (TextUtils.isEmpty(banksNumber)){
                    ToastUtils.showToast(this,"请输入银行卡号",Toast.LENGTH_SHORT);
                    return;
                }
                if (TextUtils.isEmpty(bankName)){
                    ToastUtils.showToast(this,"请输入开户银行",Toast.LENGTH_SHORT);
                    return;
                }
                if (TextUtils.isEmpty(zhihang)){
                    ToastUtils.showToast(this,"请输入支行名称",Toast.LENGTH_SHORT);
                    return;
                }
                if (TextUtils.isEmpty(address)){
                    ToastUtils.showToast(this,"请选择开户银行省市区",Toast.LENGTH_SHORT);
                    return;
                }
                String branch = address+"-"+zhihang;
                presenter.onToggleAddBtn(banksAccount,banksNumber,bankName,branch,status);
                break;
            case R.id.ib_flick:
                if (!hasGotToken) {
                    ToastUtils.showToast(this,"识别准备中",Toast.LENGTH_SHORT);
                    return;
                }
                Intent intent2 = new Intent(mContext, CameraActivity.class);
                intent2.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        getSaveFile(getApplicationContext()).getAbsolutePath());
                intent2.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_BANK_CARD);
                startActivityForResult(intent2, REQUEST_CODE_BANKCARD);
                break;
        }
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
                LogUtils.i(TAG, "所选省市区城市： " + province.getName() + " " + province.getId() + "\n" + city.getName()
                        + " " + city.getId() + "\n" + area.getName() + " " + area.getId() + "\n");
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
                    String res = String.format("卡号：%s\n类型：%s\n发卡行：%s",
                            result.getBankCardNumber(),
                            result.getBankCardType().name(),
                            result.getBankName());
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
                    ToastUtils.showToast(mContext,error.getMessage(),Toast.LENGTH_SHORT);
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
        et_bank.setText(bankName);
    }

    @Override
    public void setBanksNumber(String banksNumber) {
        et_card.setText(banksNumber);
    }

    @Override
    public void setBranch(String branch) {
        et_bankname.setText(branch);
    }

    @Override
    public void setStatus(boolean status) {
        cb_moren.setChecked(status);
    }

//    @Override
//    public void setBankAccount(String bankAccount) {
//        et_name.setText(bankAccount);
//    }

    @Override
    public void addBankcardSuccess() {
        ToastUtils.showToast(this, "添加银行卡成功",Toast.LENGTH_SHORT);
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
        ToastUtils.showToast(this, msg,Toast.LENGTH_SHORT);
    }

    @Override
    public void delDialogSuccess() {

    }

    @Override
    public void delDoalogFailure(String msg) {
        ToastUtils.showToast(this, msg,Toast.LENGTH_SHORT);
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

    public static File getSaveFile(Context context) {
        File file = new File(context.getFilesDir(), "pic.jpg");
        return file;
    }
}
