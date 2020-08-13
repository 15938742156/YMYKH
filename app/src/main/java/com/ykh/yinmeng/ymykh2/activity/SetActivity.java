package com.ykh.yinmeng.ymykh2.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ykh.yinmeng.ymykh2.R;
import com.ykh.yinmeng.ymykh2.activity.login.LoginActivity;
import com.ykh.yinmeng.ymykh2.app.App;
import com.ykh.yinmeng.ymykh2.common.jpush.JpushClient;
import com.ykh.yinmeng.ymykh2.utils.Constant;
import com.ykh.yinmeng.ymykh2.utils.SharedPreferencesUtils;

/**设置Activity
 * @author Angel
 * */

public class SetActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_version,tv_title;
    private RelativeLayout rl_idea,rl_about;
    private LinearLayout ll_return;
    private Button bt_login;
    Intent intent;


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        //获取包管理者对象

    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_set);

    }

    @Override
    public void initViews() {
        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_title.setText("设置");
        ll_return = (LinearLayout) findViewById(R.id.ll_return);
//        rl_update = (RelativeLayout) findViewById(R.id.rl_update);
        rl_idea = (RelativeLayout) findViewById(R.id.rl_idea);
        rl_about = (RelativeLayout) findViewById(R.id.rl_about);
        bt_login = (Button) findViewById(R.id.bt_login);
        tv_version = (TextView) findViewById(R.id.tv_version);
        tv_version.setText("当前版本 "+String.valueOf(SetActivity.getLocalVersion(this)));
    }

    @Override
    public void initListeners() {
        ll_return.setOnClickListener(this);
        rl_idea.setOnClickListener(this);
        rl_about.setOnClickListener(this);
        bt_login.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_return:
                SetActivity.this.finish();
                break;
            case R.id.rl_idea:
                intent = new Intent(SetActivity.this,FeedbackActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_about:
                intent = new Intent(SetActivity.this,AboutMyActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_login:
                startActivity(new Intent(this, LoginActivity.class));
                SharedPreferencesUtils.getInstance(App.getContext()).put(Constant.SHARED_TOKEN, "");
                SharedPreferencesUtils.getInstance(App.getContext()).put(Constant.SHARED_AUTHENTICATION, 0);
                SharedPreferencesUtils.getInstance(App.getContext()).put(Constant.SHARED_NAME, "");
                SharedPreferencesUtils.getInstance(App.getContext()).put(Constant.SHARED_PHONE, "");
                SharedPreferencesUtils.getInstance(App.getContext()).put(Constant.SHARED_ALIACCOUNT, "");
                finishAll();
                JpushClient.getInstance().deleteAlias();
                break;


        }

    }
    public static String getLocalVersion(Context ctx) {
        String localVersion = null;
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionName;
//            LogUtil.d("TAG", "本软件的版本号。。" + localVersion+packageInfo.versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }

}
