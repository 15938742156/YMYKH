package com.ykh.yinmeng.ymykh2.activity.addressadd;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lljjcoder.style.citythreelist.CityBean;
import com.lljjcoder.style.citythreelist.ProvinceActivity;
import com.ykh.yinmeng.ymykh2.R;
import com.ykh.yinmeng.ymykh2.activity.BaseActivity;
import com.ykh.yinmeng.ymykh2.utils.LogUtils;
import com.ykh.yinmeng.ymykh2.utils.ToastUtils;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.dialog.DialogUtil;


public class AddaddressActivity extends BaseActivity implements View.OnClickListener,AddaddressMVPView {
    private EditText et_name,et_tel,et_address;
    private RelativeLayout rl_location;
    private CheckBox cb_moren;
    private Button bt_baocun;
    private LinearLayout ll_return;
    private TextView tv_title,tv_location_city;
    Intent intent;

    /**
     * 当前省的名称
     */
    private String mCurrentProviceName;
    /**
     * 当前市的名称
     */
    private String mCurrentCityName;
    /**
     * 当前区的名称
     */
    private String mCurrentAreaName = "";


    private Dialog loadingDialog;
    public AddaddressPresenter<AddaddressMVPView> presenter;
    @Override
    protected void onCreate(Bundle arg0) {
        presenter = new AddaddressPresenterImp<>();
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
        setContentView(R.layout.activity_addaddress);
    }

    @Override
    public void initViews() {
        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_title.setText("添加收货地址");
        ll_return = (LinearLayout)findViewById(R.id.ll_return);
        et_name = (EditText) findViewById(R.id.et_name);
        et_tel = (EditText)findViewById(R.id.et_tel);
        et_address = (EditText)findViewById(R.id.et_address);
        rl_location = (RelativeLayout)findViewById(R.id.rl_location);
        cb_moren = (CheckBox) findViewById(R.id.cb_moren);
        bt_baocun = (Button)findViewById(R.id.bt_baocun);
        tv_location_city = (TextView) findViewById(R.id.tv_location_city);
        loadingDialog = DialogUtil.createLoadingDialog(this, "");

    }

    @Override
    public void initListeners() {
        ll_return.setOnClickListener(this);
        rl_location.setOnClickListener(this);
        bt_baocun.setOnClickListener(this);
    }

    @Override
    public void initData() {
        presenter.onInit(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_return:
                AddaddressActivity.this.finish();
                break;
            case R.id.rl_location:
                presenter.onToggleCityPickerBtn(this);
                break;
            case R.id.bt_baocun:
                String name = et_name.getText().toString().trim();
                String tel = et_tel.getText().toString().trim();
                String address = et_address.getText().toString().trim();
                String isDefault = cb_moren.isChecked() ? "1" : "0";

                if (TextUtils.isEmpty(name)) {
                    ToastUtils.showToast(this, "请输入姓名", Toast.LENGTH_SHORT);
                    return;
                }
                if (TextUtils.isEmpty(tel)) {
                    ToastUtils.showToast(this, "请输入电话", Toast.LENGTH_SHORT);
                    return;
                }
                if (TextUtils.isEmpty(address)) {
                    ToastUtils.showToast(this, "请输入详细地址", Toast.LENGTH_SHORT);
                    return;
                }
                if (TextUtils.isEmpty(mCurrentProviceName)) {
                    ToastUtils.showToast(this, "请选择省", Toast.LENGTH_SHORT);
                    return;
                }
                if (TextUtils.isEmpty(mCurrentCityName)) {
                    ToastUtils.showToast(this, "请选择市", Toast.LENGTH_SHORT);
                    return;
                }
                if (TextUtils.isEmpty(mCurrentAreaName)) {
                    ToastUtils.showToast(this, "请选择区", Toast.LENGTH_SHORT);
                    return;
                }
                presenter.onToggleAddBtn(name,tel,mCurrentProviceName,mCurrentCityName,mCurrentAreaName,address,isDefault);
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
                tv_location_city.setText(mCurrentProviceName + " " + mCurrentCityName + " " + mCurrentAreaName);
                LogUtils.i(TAG, "所选省市区城市： " + province.getName() + " " + province.getId() + "\n" + city.getName()
                        + " " + city.getId() + "\n" + area.getName() + " " + area.getId() + "\n");
            }
        }
    }



    @Override
    public void setShrName(String shrName) {
        et_name.setText(shrName);
    }

    @Override
    public void setShrMobile(String shrMobile) {
        et_tel.setText(shrMobile);
    }

    @Override
    public void setShrProvince(String shrProvince) {
        this.mCurrentProviceName = shrProvince;
    }

    @Override
    public void setShrCity(String shrCity) {
        mCurrentCityName = shrCity;
        et_address.setText(mCurrentProviceName+" "+shrCity);
    }

    @Override
    public void setShrArea(String shrArea) {
        mCurrentAreaName = shrArea;
        tv_location_city.setText(mCurrentProviceName + " " + mCurrentCityName + " " + mCurrentAreaName);
    }

    @Override
    public void setShrAddress(String shrAddress) {
        et_address.setText(shrAddress);
    }

    @Override
    public void setIsDefault(boolean isDefault) {
        cb_moren.setChecked(isDefault);
    }

    @Override
    public void addAdressSuccess() {
        ToastUtils.showToast(this, "新增地址成功",Toast.LENGTH_SHORT);
        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    public void addAdressFailure(String msg) {
        ToastUtils.showToast(this, msg, Toast.LENGTH_SHORT);
    }

    @Override
    public void editAdressSuccess() {
        ToastUtils.showToast(this, "修改地址成功", Toast.LENGTH_SHORT);
        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    public void editAdressFailure(String msg) {
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
