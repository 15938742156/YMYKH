package com.ykh.yinmeng.ymykh2.activity.shimingrenzheng;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ykh.yinmeng.ymykh2.R;
import com.ykh.yinmeng.ymykh2.activity.BaseActivity;
import com.ykh.yinmeng.ymykh2.app.App;
import com.ykh.yinmeng.ymykh2.utils.Constant;
import com.ykh.yinmeng.ymykh2.utils.SharedPreferencesUtils;
import com.ykh.yinmeng.ymykh2.utils.ToastUtils;


public class ShimingrenzhengActivity extends BaseActivity implements View.OnClickListener,ShimingrenzhengMVPView {

    private ImageView iv_ocr_zhengmian,iv_ocr_fanmian;
    private TextView tv_title,tv_tel,tv_name,tv_card;
    private Button bt_next;
    private LinearLayout ll_return;
    private AlertDialog.Builder alertDialog;
    public ShimingrenzhengPresenter<ShimingrenzhengMVPView> shimingrenzhengPresenter;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        shimingrenzhengPresenter = new ShimingrenzhengPresenterImp<>();
        shimingrenzhengPresenter.onAttach(this);
        shimingrenzhengPresenter.onInit(this);
    }

    @Override
    protected void onDestroy() {
        shimingrenzhengPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_shimingrenzheng);
    }

    @Override
    public void initViews() {
        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_title.setText("实名认证");
        ll_return = (LinearLayout) findViewById(R.id.ll_return);
        iv_ocr_zhengmian = (ImageView) findViewById(R.id.iv_ocr_zhengmian);
        iv_ocr_fanmian = (ImageView) findViewById(R.id.iv_ocr_fanmian);
        tv_tel = (TextView) findViewById(R.id.tv_tel);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_card = (TextView) findViewById(R.id.tv_card);
        bt_next = (Button) findViewById(R.id.bt_next);
        alertDialog = new AlertDialog.Builder(this);

    }

    @Override
    public void initListeners() {
        ll_return.setOnClickListener(this);
        bt_next.setOnClickListener(this);

    }

    @Override
    public void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_return:
                ShimingrenzhengActivity.this.finish();
                break;
            case R.id.bt_next:
//                if (SharedPreferencesUtils.getInstance(this).get("shimingrenzheng", false)) {
//                    shimingrenzhengPresenter.shimingSuccess(this);
//                } else {
                    shimingrenzhengPresenter.onToggleNext(this);
//                }
                break;
        }
    }

    @Override
    public void showZhengMian(Bitmap bitmap) {
        iv_ocr_zhengmian.setImageBitmap(bitmap);
    }

    @Override
    public void showFanMian(Bitmap bitmap) {
        iv_ocr_fanmian.setImageBitmap(bitmap);
    }

    @Override
    public void showTel(String tel) {
        tv_tel.setText(tel);
    }

    @Override
    public void showName(String name) {
        tv_name.setText(name);
    }

    @Override
    public void showCard(String card) {
        tv_card.setText(card);
    }

    @Override
    public void shimingSuccess() {
        SharedPreferencesUtils.getInstance(App.getContext()).put(Constant.SHARED_AUTHENTICATION, 1);
        SharedPreferencesUtils.getInstance(this).put("shimingrenzheng", true);
        ToastUtils.showToast(this, "实名认证成功", Toast.LENGTH_SHORT);
        shimingrenzhengPresenter.shimingSuccess(this);
    }

    @Override
    public void showAlert(String title, String message) {
        alertText(title, message);
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
}
