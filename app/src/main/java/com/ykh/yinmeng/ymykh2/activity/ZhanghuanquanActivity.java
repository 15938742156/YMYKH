package com.ykh.yinmeng.ymykh2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ykh.yinmeng.ymykh2.R;
import com.ykh.yinmeng.ymykh2.activity.bangdingzhifubao.ZhifubaoActivity;
import com.ykh.yinmeng.ymykh2.activity.resetloginpassword.ResetLoginpasswordActivity;
import com.ykh.yinmeng.ymykh2.activity.resetpassword.ResetpasswordActivity;
import com.ykh.yinmeng.ymykh2.activity.resetphone.ResetphoneActivity;
import com.ykh.yinmeng.ymykh2.app.App;
import com.ykh.yinmeng.ymykh2.utils.Constant;
import com.ykh.yinmeng.ymykh2.utils.SharedPreferencesUtils;
import com.ykh.yinmeng.ymykh2.utils.StringReplaceUtil;
import com.ykh.yinmeng.ymykh2.utils.ToastUtils;


/***
 * 账户安全Activity
 * @author Angel
 */
public class ZhanghuanquanActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_title,tv_zhifubao;
    private LinearLayout ll_return;
    private RelativeLayout rl_phone,rl_denglupassword,rl_jiaoyipassword,rl_zhifubao;
    Intent intent;
    int isReal;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);

    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_zhanghuanquan);
    }

    @Override
    public void initViews() {
        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_title.setText("账户安全");
        tv_zhifubao = (TextView) findViewById(R.id.tv_zhifubao);
        String aliAccount = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_ALIACCOUNT);
        if (aliAccount.isEmpty()|| aliAccount == null){
            tv_zhifubao.setText("未绑定");
        }else {
            tv_zhifubao.setText(StringReplaceUtil.isMobileNum(aliAccount));
        }
        ll_return = (LinearLayout)findViewById(R.id.ll_return);
        rl_phone = (RelativeLayout) findViewById(R.id.rl_phone);
        rl_denglupassword = (RelativeLayout) findViewById(R.id.rl_denglupassword);
        rl_jiaoyipassword = (RelativeLayout) findViewById(R.id.rl_jiaoyipassword);
        rl_zhifubao = (RelativeLayout) findViewById(R.id.rl_zhifubao);

    }

    @Override
    public void initListeners() {
        ll_return.setOnClickListener(this);
        rl_phone.setOnClickListener(this);
        rl_denglupassword.setOnClickListener(this);
        rl_jiaoyipassword.setOnClickListener(this);
        rl_zhifubao.setOnClickListener(this);

    }

    @Override
    public void initData() {

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_return:
                ZhanghuanquanActivity.this.finish();
                break;
            case R.id.rl_phone:
                intent = new Intent(ZhanghuanquanActivity.this, ResetphoneActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_denglupassword:
                intent = new Intent(ZhanghuanquanActivity.this, ResetLoginpasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_jiaoyipassword:
                intent = new Intent(ZhanghuanquanActivity.this, ResetpasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_zhifubao:
                isReal = SharedPreferencesUtils.getInstance(this).get(Constant.SHARED_AUTHENTICATION, -1);
                if (isReal !=1 ){
                    ToastUtils.showToast(this, "亲爱的用户,为确保您的账户安全,进行此业务之前请先进行用户认证。", Toast.LENGTH_SHORT);
                }else {
                    intent = new Intent(ZhanghuanquanActivity.this, ZhifubaoActivity.class);
                    startActivity(intent);
                }
                break;
                default:
                    break;
        }
    }
}
