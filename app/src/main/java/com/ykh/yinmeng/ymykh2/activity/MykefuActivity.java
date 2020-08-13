package com.ykh.yinmeng.ymykh2.activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ykh.yinmeng.ymykh2.R;


/***
 * 我的客服Activity
 * @author Angel
 */
public class MykefuActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_title,tv_boda,tv_weixin,tv_fuzhiweixin,tv_qq,tv_fuzhiQQ,tv_boda2,tv_jingli,tv_dianhua;
    private LinearLayout ll_return;
    Intent intent;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_kefu);
    }

    @Override
    public void initViews() {
        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_title.setText("我的客服");
        ll_return = (LinearLayout)findViewById(R.id.ll_return);
        tv_boda = (TextView) findViewById(R.id.tv_boda);
        tv_weixin = (TextView) findViewById(R.id.tv_weixin);
        tv_fuzhiweixin = (TextView) findViewById(R.id.tv_fuzhiweixin);
        tv_qq = (TextView) findViewById(R.id.tv_qq);
        tv_fuzhiQQ = (TextView) findViewById(R.id.tv_fuzhiQQ);
        tv_boda2 = (TextView) findViewById(R.id.tv_boda2);
        tv_jingli = (TextView) findViewById(R.id.tv_jingli);
        tv_dianhua = (TextView) findViewById(R.id.tv_dianhua);
    }

    @Override
    public void initListeners() {
        ll_return.setOnClickListener(this);
        tv_boda.setOnClickListener(this);
        tv_weixin.setOnClickListener(this);
        tv_fuzhiweixin.setOnClickListener(this);
        tv_qq.setOnClickListener(this);
        tv_fuzhiQQ.setOnClickListener(this);
        tv_boda2.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    /**
     * 调用拨号界面
     * @param phone 电话号码
     */
    private void call(String phone) {
        intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    @Override
    public void onClick(View v) {
        ClipboardManager copy;
        switch (v.getId()){
            case R.id.ll_return:
                MykefuActivity.this.finish();
                break;
            case R.id.tv_boda:
                call(tv_dianhua.getText().toString());
                break;
            case R.id.tv_fuzhiweixin:
                // 从API11开始android推荐使用android.content.ClipboardManager
                // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
                copy = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                copy.setText(tv_weixin.getText().toString());
                Toast.makeText(this,"复制成功",Toast.LENGTH_LONG).show();
                break;
            case R.id.tv_fuzhiQQ:
                copy = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                copy.setText(tv_qq.getText().toString());
                Toast.makeText(this,"复制成功",Toast.LENGTH_LONG).show();
                break;
            case R.id.tv_boda2:
                call(tv_jingli.getText().toString());
                break;
        }

    }
}
